package com.followba.store.web.pojo;

import lombok.Data;

import java.util.Objects;
import java.util.Set;

/**
 * @author 祥霸
 * @since 1.0.0
 */
@Data
public class UserTag {
    private String userId;

    private Set<String> tags;

    @Override
    public int hashCode() {
        return Objects.hash(this.userId);
    }

    @Override
    public boolean equals(Object obj) {
        // 检查参数是否为 null，如果是，则返回 false
        if (obj == null) {
            return false;
        }

        // 检查参数是否与当前对象属于同一类
        if (this.userId.getClass() != obj.getClass()) {
            return false;
        }

        // 将参数转换为 类型，并比较字段值
        String targetUserId = (String) obj;
        return Objects.equals(this.userId, targetUserId);
    }
}
