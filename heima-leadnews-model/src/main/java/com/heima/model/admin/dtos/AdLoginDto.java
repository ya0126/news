package com.heima.model.admin.dtos;

import lombok.Data;

/**
 * 后台登录dto
 *
 * @author yaoh
 */
@Data
public class AdLoginDto {

    /**
     * 用户名
     */
    private String name;

    /**
     * 登录密码
     */
    private String password;
}
