package com.elegant.essay.manager.controller;

import com.elegant.essay.annotation.PermessionLimit;
import com.elegant.essay.model.dto.OperateDto;
import com.elegant.essay.model.dto.UserDto;
import com.elegant.essay.model.dto.UserQueryDto;
import com.elegant.essay.model.pojo.Result;
import com.elegant.essay.model.vo.UserVo;
import com.elegant.essay.service.IUserService;
import com.elegant.essay.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-23 15:03
 */
@Api(description = "管理后台|用户管理API")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "用户列表")
    @PostMapping("/list")
    public Result pageList(@Validated @RequestBody UserQueryDto queryDto) {

        return ResultUtil.success(userService.pageList(queryDto));
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/create")
    public Result create(@Validated @RequestBody UserDto userDto) {
        userService.create(userDto);
        return ResultUtil.success();
    }

    @ApiOperation(value = "修改用户")
    @PostMapping("/update")
    public Result update(@Validated @RequestBody UserDto userDto) {
        userService.modify(userDto);
        return ResultUtil.success();
    }

    @ApiModelProperty(value = "查询用户详情")
    @GetMapping("/detail")
    public Result detail(@Param("id") Long id) {
        return ResultUtil.success(userService.detail(id));
    }

    @ApiOperation(value = "用户黑|白名单")
    @PostMapping("/operate")
    public Result operate(@Validated @RequestBody OperateDto operateDto) {

        userService.operate(operateDto);
        return ResultUtil.success();
    }

    @ApiOperation(value = "根据用户名查询用户列表")
    @GetMapping("/listByName")
    public Result findUsersByName(@Validated @RequestParam("realName")
                                  @NotBlank(message = "姓名不能为空") String realName) {
        return ResultUtil.success(userService.findUsersByName(realName));
    }

    @ApiOperation(value = "根据token获取用户信息")
    @PostMapping("/tokenUserInfo")
    public Result<UserVo> userDetailByToken(HttpServletRequest request) {
        return ResultUtil.success(userService.userInfoByToken(request));
    }

}
