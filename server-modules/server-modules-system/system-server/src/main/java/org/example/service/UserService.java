package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.system.entity.vo.UserVO;
import org.example.system.entity.vo.UsernamePasswordVO;
import org.example.system.query.UserQueryPage;

import java.security.NoSuchAlgorithmException;

/**
 * @author lihui
 * @since 2023/4/3
 */
public interface UserService {
    /**
     * 新增用户
     *
     * @param userVO
     * @return
     */
    String addUser(UserVO userVO);

    /**
     * 分页查看用户列表
     *
     * @param queryPage
     * @return
     */
    Page<UserVO> getUserList(UserQueryPage queryPage);

    /**
     * 查看用户详情
     *
     * @return
     */
    UserVO getUserInfo(String id);

    /**
     * 更新用户信息
     *
     * @param userVO
     * @return
     */
    Boolean updateUser(UserVO userVO);

    /**
     * 更新密码
     *
     * @param usernamePasswordVo
     * @return
     */
    Boolean updateUserPassword(UsernamePasswordVO usernamePasswordVo);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    Boolean deleteUser(String id);

    /**
     * 获取用户token有效时间
     *
     * @param id
     * @return 有效时间，单位毫秒
     */
    long getTokenExpireTime(String id);

    /**
     * 获取密钥
     *
     * @param id
     * @return
     */
    String getPublicKey(String id) throws NoSuchAlgorithmException;

    /**
     * 清理用户信息缓存
     *
     * @param userId
     */
    void clearUserCache(String userId);
}