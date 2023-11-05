package com.stu.wemedia.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.wemedia.dtos.WmLoginDto;
import com.stu.model.wemedia.pojos.WmUser;
import com.stu.utils.common.JwtUtil;
import com.stu.wemedia.mapper.WmUserMapper;
import com.stu.wemedia.service.WmUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

    /**
     * 登录
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(WmLoginDto dto) {
        if (StringUtils.isBlank(dto.getName()) || StringUtils.isBlank(dto.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "用户名或密码为空");
        }
        WmUser wmUser = getOne(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName, dto.getName()));
        if (wmUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_DATA_NOT_EXIST);
        }
        String salt = wmUser.getSalt();
        String pswd = dto.getPassword();
        pswd = DigestUtils.md5DigestAsHex((pswd + salt).getBytes());
        if (pswd.equals(wmUser.getPassword())) {
            Map<String, Object> map = new HashMap<>();
            map.put("token", JwtUtil.getToken(wmUser.getId().longValue()));
            wmUser.setSalt("");
            wmUser.setPassword("");
            map.put("user", wmUser);
            return ResponseResult.okResult(map);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
    }
}