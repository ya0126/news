package com.stu.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.user.dtos.LoginDto;
import com.stu.model.user.pojos.ApUser;
import com.stu.user.mapper.ApUserMapper;
import com.stu.user.service.ApUserService;
import com.stu.utils.common.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * apUser业务层实现类
 *
 * @author yaoh
 */
@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    /**
     * 登录
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(LoginDto dto) {
        if (StringUtils.isNotBlank(dto.getPhone()) && StringUtils.isNotBlank(dto.getPassword())) {
            ApUser apUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if (apUser == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.USER_DATA_NOT_EXIST, "用户不存在");
            }
            String salt = apUser.getSalt();
            String password = dto.getPassword();

            password = DigestUtils.md5DigestAsHex((password + salt).getBytes());

            if (!apUser.getPassword().equals(password)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("token", JwtUtil.getToken(apUser.getId().longValue()));
            apUser.setSalt("");
            apUser.setPassword("");
            map.put("user", apUser);
            return ResponseResult.okResult(map);
        } else {
            // 游客登录
            Map<String, Object> map = new HashMap<>();
            map.put("token", JwtUtil.getToken(0l));
            return ResponseResult.okResult(map);
        }
    }
}
