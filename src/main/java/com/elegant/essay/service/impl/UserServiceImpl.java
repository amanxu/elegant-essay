package com.elegant.essay.service.impl;

import com.elegant.essay.enums.CommonStatusEnum;
import com.elegant.essay.enums.ErrorCodeEnum;
import com.elegant.essay.enums.UserTypeEnum;
import com.elegant.essay.exception.BusinessException;
import com.elegant.essay.mapper.ElegantUserMapper;
import com.elegant.essay.mapper.UserSelfMapper;
import com.elegant.essay.model.ElegantUser;
import com.elegant.essay.model.dto.OperateDto;
import com.elegant.essay.model.dto.UserDto;
import com.elegant.essay.model.dto.UserQueryDto;
import com.elegant.essay.model.vo.UserVo;
import com.elegant.essay.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-23 23:03
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private ElegantUserMapper userMapper;

    @Resource
    private UserSelfMapper userSelfMapper;

    @Override
    public PageInfo<UserVo> pageList(UserQueryDto queryDto) {

        PageHelper.startPage(queryDto.getPageNo(), queryDto.getPageSize());
        List<UserVo> userVos = userSelfMapper.pageList(queryDto);
        PageInfo<UserVo> pageInfo = new PageInfo<>(userVos);
        pageInfo.setList(userVos.stream().map(userVo -> {
            userVo.setUserTypeDesc(UserTypeEnum.getMsgByCode(userVo.getUserType()));
            return userVo;
        }).collect(Collectors.toList()));
        return pageInfo;
    }

    @Override
    public void create(UserDto userDto) {
        ElegantUser account = userSelfMapper.findUserByAccount(userDto.getUserName());
        if (account != null) {
            throw new BusinessException(ErrorCodeEnum.USER_EXIST_ERR.getMsg());
        }
        ElegantUser elegantUser = new ElegantUser();
        elegantUser.setUserName(userDto.getUserName());
        elegantUser.setRealName(userDto.getRealName());
        elegantUser.setPassword(userDto.getPassword());
        elegantUser.setPhone(userDto.getPhone());
        elegantUser.setEmail(userDto.getEmail());
        elegantUser.setUserType(userDto.getUserType());
        elegantUser.setDeleted(CommonStatusEnum.NORMAL.getCode());
        elegantUser.setCreateTime(new Date());
        int result = userMapper.insert(elegantUser);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.USER_ADD_ERR.getMsg());
        }
    }

    @Override
    public void modify(UserDto userDto) {
        ElegantUser elegantUser = userSelfMapper.findUserByAccount(userDto.getUserName());
        if (elegantUser != null && !elegantUser.getUserId().equals(userDto.getUserId())) {
            throw new BusinessException(ErrorCodeEnum.USER_EXIST_ERR.getMsg());
        }
        elegantUser.setUserName(userDto.getUserName());
        elegantUser.setRealName(userDto.getRealName());
        elegantUser.setPassword(userDto.getPassword());
        elegantUser.setPhone(userDto.getPhone());
        elegantUser.setEmail(userDto.getEmail());
        elegantUser.setUserType(userDto.getUserType());
        elegantUser.setDeleted(CommonStatusEnum.NORMAL.getCode());
        elegantUser.setUpdateTime(new Date());
        int result = userMapper.updateByPrimaryKey(elegantUser);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.USER_MODIFY_ERR.getMsg());
        }
    }

    @Override
    public UserVo detail(Long id) {
        ElegantUser elegantUser = userMapper.selectByPrimaryKey(id);
        UserVo userVo = new UserVo();
        userVo.setUserName(elegantUser.getUserName());
        userVo.setRealName(elegantUser.getRealName());
        userVo.setPhone(elegantUser.getPhone());
        userVo.setUserType(elegantUser.getUserType());
        userVo.setEmail(elegantUser.getEmail());
        userVo.setPassword(elegantUser.getPassword());
        userVo.setUserTypeDesc(UserTypeEnum.getMsgByCode(elegantUser.getUserType()));
        return userVo;
    }

    @Override
    public void operate(OperateDto operateDto) {
        ElegantUser elegantUser = userMapper.selectByPrimaryKey(operateDto.getId());
        if (elegantUser == null) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_EXIST_ERR.getMsg());
        }
        if (CommonStatusEnum.DELETED.getCode().equals(operateDto.getOperateType())) {
            elegantUser.setDeleted(CommonStatusEnum.DELETED.getCode());
        } else if (CommonStatusEnum.NORMAL.getCode().equals(operateDto.getOperateType())) {
            elegantUser.setDeleted(CommonStatusEnum.NORMAL.getCode());
        }
        int result = userMapper.updateByPrimaryKey(elegantUser);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.OPERATE_USER_ERR.getMsg());
        }
    }

    @Override
    public ElegantUser findUserByAccount(String userName) {
        return userSelfMapper.findUserByAccount(userName);
    }

    @Override
    public List<UserVo> findUsersByName(String realName) {

        List<UserVo> userVos = userSelfMapper.findUsersByRealName(realName);
        return userVos;
    }

    @Override
    public ElegantUser jwtParseUserInfo(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("CLAIMS");
        ElegantUser elegantUser = new ElegantUser();
        elegantUser.setUserName((String) claims.get("username"));
        elegantUser.setUserId(claims.get("userId") == null ? null : ((Long) claims.get("userId")).longValue());
        elegantUser.setUserType((Integer) claims.get("userType"));
        return elegantUser;
    }

    @Override
    public UserVo userInfoByToken(HttpServletRequest request) {
        ElegantUser user = jwtParseUserInfo(request);
        if (user == null) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_EXIST_ERR.getMsg());
        }
        ElegantUser elegantUser = userMapper.selectByPrimaryKey(user.getUserId());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(elegantUser, userVo);
        if (userVo.getUserType() == 0) {
            userVo.setRoles(Lists.newArrayList("admin"));
        } else {
            userVo.setRoles(Lists.newArrayList("normal"));
        }
        return userVo;
    }


}
