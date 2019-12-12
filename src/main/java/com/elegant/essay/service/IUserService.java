package com.elegant.essay.service;

import com.elegant.essay.model.ElegantUser;
import com.elegant.essay.model.vo.UserVo;
import com.elegant.essay.model.dto.OperateDto;
import com.elegant.essay.model.dto.UserDto;
import com.elegant.essay.model.dto.UserQueryDto;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-23 22:56
 */
public interface IUserService {

    PageInfo<UserVo> pageList(UserQueryDto queryDto);

    void create(UserDto userDto);

    void modify(UserDto userDto);

    UserVo detail(Long id);

    void operate(OperateDto operateDto);

    List<UserVo> findUsersByName(String realName);

    ElegantUser findUserByAccount(String userName);

    ElegantUser jwtParseUserInfo(HttpServletRequest request);

    UserVo userInfoByToken(HttpServletRequest request);
}
