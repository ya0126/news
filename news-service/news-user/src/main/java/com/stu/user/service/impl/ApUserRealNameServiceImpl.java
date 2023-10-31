package com.stu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stu.apis.wemedia.IWemediaClient;
import com.stu.common.constants.UserConstants;
import com.stu.model.common.dtos.PageResponseResult;
import com.stu.model.common.dtos.ResponseResult;
import com.stu.model.common.enums.AppHttpCodeEnum;
import com.stu.model.user.dtos.AuthDto;
import com.stu.model.user.pojos.ApUser;
import com.stu.model.user.pojos.ApUserRealname;
import com.stu.model.wemedia.pojos.WmUser;
import com.stu.user.mapper.ApUserMapper;
import com.stu.user.mapper.ApUserRealNameMapper;
import com.stu.user.service.ApUserRealNameService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户实名业务层实现类
 *
 * @author yaoh
 */
@Transactional
@Service
@Slf4j
public class ApUserRealNameServiceImpl extends ServiceImpl<ApUserRealNameMapper, ApUserRealname> implements ApUserRealNameService {

    @Autowired
    private ApUserMapper apUserMapper;
    @Autowired
    private IWemediaClient wemediaClient;

    /**
     * 认证列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult pageQuery(AuthDto dto) {
        //1.检查参数
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 分页条件检查
        dto.checkParam();

        // 2.查询
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<ApUserRealname> wrapper = new LambdaQueryWrapper<>();

        // 2.1 根据状态查询
        if (dto.getStatus() != null) {
            wrapper.eq(ApUserRealname::getStatus, dto.getStatus());
        }

        page = page(page, wrapper);
        ResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        pageResponseResult.setData(page.getRecords());
        return ResponseResult.okResult(pageResponseResult);
    }

    /**
     * 修改认证状态
     *
     * @param dto
     * @param status
     * @return
     */
    @Override
    public ResponseResult updateStatus(AuthDto dto, Short status) {
        //1.检查参数
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.修改认证状态
        ApUserRealname apUserRealname = new ApUserRealname();
        apUserRealname.setId(dto.getId());
        apUserRealname.setStatus(status);
        if (StringUtils.isNotBlank(dto.getMessage())) {
            apUserRealname.setReason(dto.getMessage());
        }
        updateById(apUserRealname);

        //3.如果审核状态是9，就是成功，需要创建自媒体账户
        if (status.equals(UserConstants.PASS_AUTH)) {
            ResponseResult responseResult = createWmUserAndAuthor(dto);
            if (responseResult != null) {
                return responseResult;
            }
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 创建自媒体账户
     *
     * @param dto
     * @return
     */
    private ResponseResult createWmUserAndAuthor(AuthDto dto) {
        Integer userRealnameId = dto.getId();
        // 1.查询用户认证信息
        ApUserRealname apUserRealname = getById(userRealnameId);
        if (apUserRealname == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        // 2.查询app端用户信息
        Integer userId = apUserRealname.getUserId();
        ApUser apUser = apUserMapper.selectById(userId);
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        // 3.创建自媒体账户
        WmUser wmUser = wemediaClient.findWmUserByName(apUser.getName());
        if (wmUser == null) {
            wmUser = new WmUser();
            wmUser.setApUserId(apUser.getId());
            wmUser.setCreatedTime(new Date());
            wmUser.setName(apUser.getName());
            wmUser.setPassword(apUser.getPassword());
            wmUser.setSalt(apUser.getSalt());
            wmUser.setPhone(apUser.getPhone());
            wmUser.setStatus(9);
            wemediaClient.saveWmUser(wmUser);
        }
        apUser.setFlag((short) 1);
        apUserMapper.updateById(apUser);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
