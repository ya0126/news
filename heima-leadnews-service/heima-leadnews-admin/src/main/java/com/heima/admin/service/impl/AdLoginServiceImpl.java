package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.admin.mapper.AdUserMapper;
import com.heima.admin.service.AdLoginService;
import com.heima.model.admin.dtos.AdLoginDto;
import com.heima.model.admin.pojo.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台系统登录业务层service实现类
 *
 * @author yaoh
 */
@Service
@Slf4j
public class AdLoginServiceImpl implements AdLoginService {

    @Autowired
    private AdUserMapper adUserMapper;

    /**
     * 登录后台
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(AdLoginDto dto) {
        // 1.校验参数
        if (StringUtils.isBlank(dto.getName()) || StringUtils.isBlank(dto.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "用户名或密码为空");
        }

        // 2.查询用户
        AdUser adUser = adUserMapper.selectOne(Wrappers.<AdUser>lambdaQuery().eq(AdUser::getName, dto.getName()));
        if (adUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.AD_USER_DATA_NOT_EXIST);
        }

        // 3.比对密码
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
        log.info("用户登录成功，userId:{}", adUser.getId());
        return ResponseResult.okResult(result);
    }
}
