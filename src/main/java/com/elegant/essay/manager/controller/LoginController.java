package com.elegant.essay.manager.controller;

import com.elegant.essay.annotation.PermessionLimit;
import com.elegant.essay.config.JwtConfigProperties;
import com.elegant.essay.enums.ErrorCodeEnum;
import com.elegant.essay.model.ElegantUser;
import com.elegant.essay.model.pojo.Result;
import com.elegant.essay.model.dto.UserLoginDto;
import com.elegant.essay.service.IUserService;
import com.elegant.essay.utils.JwtHelper;
import com.elegant.essay.utils.ResultUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-10-20 11:57
 */
@Api(description = "管理后台|用户登陆API")
@Slf4j
@Controller
public class LoginController {

    @Autowired
    private JwtConfigProperties jwtConfigProperties;

    @Autowired
    private IUserService userService;

    @PermessionLimit(limit = false)
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result loginDo(@RequestBody UserLoginDto userLoginDto) {

        if (StringUtils.isBlank(userLoginDto.getUsername()) || StringUtils.isBlank(userLoginDto.getPassword())) {
            return ResultUtil.error(ErrorCodeEnum.ACCOUNT_PWD_NULL_ERR.getMsg());
        }
        ElegantUser user = userService.findUserByAccount(userLoginDto.getUsername());
        if (user == null) {
            return ResultUtil.error(ErrorCodeEnum.USER_NOT_EXIST_ERR.getMsg());
        }
        if (!user.getPassword().equals(userLoginDto.getPassword())) {
            return ResultUtil.error(ErrorCodeEnum.ACCOUNT_PWD_ERR.getMsg());
        }
        String authToken = JwtHelper.createJwt(user.getUserId(), user.getUserName(), user.getUserType(),
                jwtConfigProperties.getBase64Secret(), jwtConfigProperties.getExpiresTime());
        authToken = "bearer;" + authToken;
        return ResultUtil.success(authToken);
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success();
    }


}
