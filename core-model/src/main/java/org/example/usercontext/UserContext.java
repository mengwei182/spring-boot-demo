package org.example.usercontext;

import lombok.Data;
import org.example.system.entity.vo.UserVO;

import java.io.Serializable;

/**
 * @author lihui
 * @since 2022/10/26
 */
@Data
public final class UserContext implements Serializable {
    private static final ThreadLocal<UserVO> USER_CONTEXTS = new ThreadLocal<>();

    private UserContext() {
    }

    public static UserVO get() {
        return USER_CONTEXTS.get();
    }

    public static void set(UserVO data) {
        USER_CONTEXTS.set(data);
    }

    public static void remove() {
        USER_CONTEXTS.remove();
    }
}