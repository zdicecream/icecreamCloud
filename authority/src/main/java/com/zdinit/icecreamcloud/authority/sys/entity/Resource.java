package com.zdinit.icecreamcloud.authority.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@TableName("sys_resource")
public class Resource extends Model<Resource> implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    private String resourceCode;

    private String resourceName;

    private Long pid;

    private Integer orders;

    private Integer type;

    private Integer leaf;

    @TableField(exist = false)
    private Long roleId;

    @TableField(exist = false)
    private List actionList;

    @TableField(exist = false)
    private List<Resource> children;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
