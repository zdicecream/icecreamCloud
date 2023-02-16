package com.zdinit.icecreamcloud.common.entity.sys.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zd
 * @since 2021-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Group implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    private String groupName;

    private String groupCode;

    private Long pid;

    private Integer state;

}
