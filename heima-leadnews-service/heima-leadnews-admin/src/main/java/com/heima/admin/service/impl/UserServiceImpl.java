package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.UserMapper;
import com.heima.admin.service.UserService;
import com.heima.model.admin.pojo.AdUser;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, AdUser> implements UserService {
}
