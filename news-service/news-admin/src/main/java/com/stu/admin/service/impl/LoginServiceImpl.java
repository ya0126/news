package com.stu.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.admin.mapper.AdUserMapper;
import com.stu.admin.service.LoginService;
import com.stu.model.admin.dtos.LoginDto;
import com.stu.model.admin.pojos.AdUser;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.utils.common.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录业务层实现类
 *
 * @author yaoh
 */
@Service
@Slf4j
public class LoginServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements LoginService {
    /**
     * 登录
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(LoginDto dto) {
        if (StringUtils.isBlank(dto.getName()) || StringUtils.isBlank(dto.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "用户名或密码为空");
        }
        AdUser adUser = getOne(Wrappers.<AdUser>lambdaQuery().eq(AdUser::getName, dto.getName()));
        if (adUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_DATA_NOT_EXIST);
        }
        String salt = adUser.getSalt();
        String password = dto.getPassword();
        password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        if (!password.equals(adUser.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        String token = JwtUtil.getToken((long) adUser.getId());
        Map<String, Object> result = new HashMap<>();
        adUser.setSalt("");
        adUser.setPassword("");
        result.put("user", adUser);
        result.put("token", token);
        return ResponseResult.okResult(result);
    }
}
