package com.stu.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户关注信息表
 *
 * @author yaoh
 */
@Data
@TableName("ap_user_follow")
public class ApUserFollow implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 关注作者id
     */
    @TableField("follow_id")
    private Integer flowerId;
    /**
     * 关注度
     */
    @TableField("level")
    private Short level;
    /**
     * 是否动态通知
     */
    @TableField("is_notice")
    private Boolean isNotice;
    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

    public ApUserFollow(Integer userId, Integer flowerId) {
        this.userId = userId;
        this.flowerId = flowerId;
        this.level = 0;
        this.isNotice = true;
        this.createdTime = new Date();
    }
}
