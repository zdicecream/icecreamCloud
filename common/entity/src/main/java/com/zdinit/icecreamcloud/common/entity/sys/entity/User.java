package com.zdinit.icecreamcloud.common.entity.sys.entity;

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
public class User implements Serializable{

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

    private List roleList;

    private List resourceList;


}
