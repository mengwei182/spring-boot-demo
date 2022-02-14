package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.cache.UserCacheService;
import org.example.common.global.I18nMessage;
import org.example.common.model.CommonResult;
import org.example.common.model.CustomUserDetails;
import org.example.common.model.Page;
import org.example.common.model.QueryPage;
import org.example.common.util.CommonUtils;
import org.example.common.util.PageUtils;
import org.example.entity.*;
import org.example.mapper.*;
import org.example.security.service.JwtTokenService;
import org.example.service.UserService;
import org.example.util.ImageVerifyCodeUtils;
import org.example.util.MessageUtils;
import org.example.vo.TokenVo;
import org.example.vo.UserInfoVo;
import org.example.vo.UsernamePasswordVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Resource
    private JwtTokenService jwtTokenService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;
    @Resource
    private RoleResourceRelationMapper roleResourceRelationMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserCacheService userCacheService;

    @Override
    public CommonResult login(UsernamePasswordVo usernamePasswordVo) {
        String username = usernamePasswordVo.getUsername();
        String password = usernamePasswordVo.getPassword();
        if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
            return CommonResult.failed(I18nMessage.NOT_LOGGED_EXPIRED);
        }
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            return CommonResult.failed(I18nMessage.USER_NOT_EXIST);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return CommonResult.failed(I18nMessage.USER_PASSWORD_ERROR);
        }
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) this.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenService.generateToken(customUserDetails);
            userCacheService.setUser(customUserDetails.getUser());
            userMapper.updateLoginTime(customUserDetails.getUser().getId(), new Date());
            return CommonResult.success(new TokenVo(token, tokenHead));
        } catch (Exception e) {
            log.error("login error,username:{},message:{}", usernamePasswordVo.getUsername(), e.getMessage());
        }
        return CommonResult.failed(I18nMessage.USER_LOGIN_FAIL);
    }

    @Override
    public CommonResult logout(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenService.getUsername(token);
        CustomUserDetails customUserDetails = (CustomUserDetails) loadUserByUsername(username);
        userCacheService.deleteUserByUserId(customUserDetails.getUser().getId());
        return CommonResult.success();
    }

    @Override
    public CommonResult register(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
            return CommonResult.failed(I18nMessage.USERNAME_PASSWORD_NOTNULL);
        }
        User u = userMapper.getUserByUsername(username);
        if (u != null) {
            return CommonResult.failed(I18nMessage.USER_EXIST);
        }
        u = new User();
        BeanUtils.copyProperties(user, u);
        u.setId(CommonUtils.getUUID());
        u.setPassword(passwordEncoder.encode(password));
        userMapper.addUser(u);
        return CommonResult.success();
    }

    @Override
    public CommonResult getPhoneVerifyCode(String phone) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((int) (Math.random() * 10));
        }
        String verifyCode = sb.toString();
        if (MessageUtils.sendMessage(phone, verifyCode)) {
            userCacheService.setPhoneVerifyCode(phone, verifyCode);
            return CommonResult.success(I18nMessage.VERIFY_CODE_SEND_SUCCESS);
        }
        return CommonResult.failed(I18nMessage.VERIFY_CODE_SEND_FAIL);
    }

    @Override
    public void getImageVerifyCode(HttpServletResponse response, String account) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        String verifyCode = ImageVerifyCodeUtils.outputVerifyImage(130, 30, servletOutputStream, 6);
        userCacheService.setImageVerifyCode(account, verifyCode);
        servletOutputStream.close();
    }

    @Override
    public CommonResult getUserInfo(Principal principal) {
        String username = principal.getName();
        if (!StringUtils.hasLength(username)) {
            return CommonResult.failed(I18nMessage.USER_NOT_EXIST);
        }
        User user = userCacheService.getUserByUsername(username);
        if (user == null) {
            user = userMapper.getUserByUsername(username);
        }
        return CommonResult.success(buildUserVo(user));
    }

    @Override
    public CommonResult getUserInfoById(String userId) {
        User user = userCacheService.getUserBuUserId(userId);
        if (user == null) {
            user = userMapper.getUserByUserId(userId);
        }
        return CommonResult.success(buildUserVo(user));
    }

    private UserInfoVo buildUserVo(User user) {
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);
        List<Role> roles = roleMapper.getRolesByUserId(user.getId());
        userInfoVo.setRoles(roles);
        List<Menu> menus = menuMapper.getMenusByUserId(user.getId());
        userInfoVo.setMenus(menus);
        return userInfoVo;
    }

    @Override
    public CommonResult getUserList(QueryPage queryPage) {
        List<User> userList = userMapper.getUserList(queryPage);
        Integer total = userMapper.getUserListCount(queryPage);
        Page page = PageUtils.wrapper(queryPage, userList, total);
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateUser(User user) {
        userMapper.updateUser(user);
        userCacheService.setUser(user);
        return CommonResult.success();
    }

    @Override
    public CommonResult updateUserPassword(UsernamePasswordVo usernamePasswordVo) {
        User user = userMapper.getUserByUserId(usernamePasswordVo.getId());
        if (user != null) {
            return CommonResult.failed(I18nMessage.USER_EXIST);
        }
        String phone = usernamePasswordVo.getPhone();
        String phoneVerifyCode = usernamePasswordVo.getPhoneVerifyCode();
        if (!StringUtils.hasLength(phoneVerifyCode)) {
            return CommonResult.failed(I18nMessage.VERIFY_CODE_NOTNULL);
        }
        String phoneVerifyCodeCache = userCacheService.getPhoneVerifyCode(phone);
        if (!StringUtils.hasLength(phoneVerifyCodeCache)) {
            return CommonResult.failed(I18nMessage.VERIFY_CODE_EXPIRED);
        }
        userMapper.updatePassword(usernamePasswordVo.getId(), passwordEncoder.encode(usernamePasswordVo.getPassword()));
        userCacheService.deletePhoneVerifyCode(phone);
        userCacheService.setUser(userMapper.getUserByUserId(usernamePasswordVo.getId()));
        return CommonResult.success();
    }

    @Override
    public CommonResult updateUserRole(UserRoleRelation userRoleRelation) {
        if (userMapper.getUserByUserId(userRoleRelation.getUserId()) == null) {
            return CommonResult.failed(I18nMessage.USER_NOT_EXIST);
        }
        userRoleRelationMapper.deleteUserRoleRelationByRoleId(userRoleRelation.getUserId());
        List<String> roleIds = userRoleRelation.getRoleIds();
        if (CollectionUtils.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                UserRoleRelation urr = new UserRoleRelation();
                urr.setId(CommonUtils.getUUID());
                urr.setUserId(userRoleRelation.getUserId());
                urr.setRoleId(roleId);
                userRoleRelationMapper.addUserRoleRelation(urr);
            });
        }
        return CommonResult.success();
    }

    @Override
    public CommonResult deleteUser(String userId) {
        userMapper.deleteUserByUserId(userId);
        userRoleRelationMapper.deleteUserRoleRelationByUserId(userId);
        userCacheService.deleteUserByUserId(userId);
        return CommonResult.success();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        Set<UserGrantedAuthority> authorities = new HashSet<>();
        List<RoleResourceRelation> roleResourceRelations = roleResourceRelationMapper.getRoleResourceRelations(user.getId());
        for (RoleResourceRelation roleResourceRelation : roleResourceRelations) {
            authorities.add(new UserGrantedAuthority(roleResourceRelation.getResourceId()));
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        customUserDetails.setAuthorities(authorities);
        return customUserDetails;
    }
}