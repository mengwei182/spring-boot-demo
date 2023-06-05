package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.RoleResourceRelation;
import org.example.entity.User;
import org.example.entity.UserRoleRelation;
import org.example.entity.vo.ResourceVo;
import org.example.entity.vo.TokenVo;
import org.example.entity.vo.UserInfoVo;
import org.example.entity.vo.UsernamePasswordVo;
import org.example.error.SystemServerErrorResult;
import org.example.error.exception.CommonException;
import org.example.mapper.ResourceMapper;
import org.example.mapper.RoleResourceRelationMapper;
import org.example.mapper.UserMapper;
import org.example.mapper.UserRoleRelationMapper;
import org.example.service.BaseService;
import org.example.service.cache.UserCacheService;
import org.example.usercontext.UserContext;
import org.example.util.CommonUtils;
import org.example.util.ImageVerifyCodeUtils;
import org.example.util.TokenUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Slf4j
@Service
public class BaseServiceImpl implements BaseService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserCacheService userCacheService;
    @Resource
    private RoleResourceRelationMapper roleResourceRelationMapper;
    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;
    @Resource
    private ResourceMapper resourceMapper;

    /**
     * 登录
     *
     * @param usernamePasswordVo
     * @return
     */
    @Override
    public String login(UsernamePasswordVo usernamePasswordVo) {
        String username = usernamePasswordVo.getUsername();
        String password = usernamePasswordVo.getPassword();
        if (!StringUtils.hasLength(username)) {
            throw new CommonException(SystemServerErrorResult.USERNAME_NULL);
        }
        if (!StringUtils.hasLength(password)) {
            throw new CommonException(SystemServerErrorResult.PASSWORD_NULL);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new CommonException(SystemServerErrorResult.USER_NOT_EXIST);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CommonException(SystemServerErrorResult.PASSWORD_ERROR);
        }
        Date loginTime = new Date();
        UserInfoVo userInfoVo = CommonUtils.transformObject(user, UserInfoVo.class);
        // 查询并设置登录用户的resource数据
        List<UserRoleRelation> userRoleRelations = userRoleRelationMapper.selectList(new LambdaQueryWrapper<UserRoleRelation>().eq(UserRoleRelation::getUserId, user.getId()));
        List<RoleResourceRelation> roleResourceRelations = roleResourceRelationMapper.selectList(new LambdaQueryWrapper<RoleResourceRelation>().in(RoleResourceRelation::getRoleId, userRoleRelations.stream().map(UserRoleRelation::getRoleId).collect(Collectors.toList())));
        List<org.example.entity.Resource> resources = resourceMapper.selectList(new LambdaQueryWrapper<org.example.entity.Resource>().in(org.example.entity.Resource::getId, roleResourceRelations.stream().map(RoleResourceRelation::getResourceId).collect(Collectors.toList())));
        userInfoVo.setResources(CommonUtils.transformList(resources, ResourceVo.class));
        TokenVo<?> tokenVo = new TokenVo<>(user.getId(), loginTime, 60 * 60L, userInfoVo);
        String token = TokenUtils.sign(tokenVo);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(User::getLoginTime, new Date()).eq(User::getId, user.getId());
        userMapper.update(null, updateWrapper);
        return token;
    }

    /**
     * 登出
     *
     * @return
     */
    @Override
    public Boolean logout() {
        UserContext.remove();
        return true;
    }

    /**
     * 获取图片验证码
     *
     * @param response
     * @throws IOException
     */
    @Override
    public void generateImageVerifyCode(HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        String verifyCode = ImageVerifyCodeUtils.outputVerifyImage(130, 30, servletOutputStream, 6);
        userCacheService.setImageVerifyCode(UserContext.get().getUserId(), verifyCode, 5L);
        servletOutputStream.close();
    }
}