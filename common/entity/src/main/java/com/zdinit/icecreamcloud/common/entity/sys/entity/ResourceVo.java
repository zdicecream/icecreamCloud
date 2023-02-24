package com.zdinit.icecreamcloud.common.entity.sys.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zd
 * @since 2021-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceVo implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    private String resourceCode;

    private String resourceName;

    private Long pid;

    private Integer orders;

    private Integer type;

    private Integer leaf;

    private Long roleId;

    private List actionList;

    private List<ResourceVo> children;

}
