package com.zdinit.icecreamcloud.common.entity.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zd
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class User extends Model<User>{

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String showName;

    private String mobile;

    private String email;

    private LocalDateTime createTime;

    private LocalDateTime lastLoginTime;

    private Integer loginCount;

    private Long groupId;

    private Integer state;

    @TableField(exist = false)
    private List roleList;

    @TableField(exist = false)
    private List resourceList;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
