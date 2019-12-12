package com.elegant.essay.controller;

import com.elegant.essay.annotation.PermessionLimit;
import com.elegant.essay.model.dto.ShippingAddressDto;
import com.elegant.essay.model.pojo.Result;
import com.elegant.essay.service.IShippingAddressService;
import com.elegant.essay.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-21 9:58
 */
@PermessionLimit(limit = false)
@Api(description = "APP用户收货地址API")
@RestController
@RequestMapping(value = "/address")
public class AppShippingAddressController {

    @Autowired
    private IShippingAddressService shippingAddressService;

    @ApiOperation(value = "APP新增用户收货地址")
    @PostMapping(value = "/create")
    public Result createAddress(@RequestBody @Validated ShippingAddressDto addressDto) {
        shippingAddressService.createAddress(addressDto);
        return ResultUtil.success();
    }

    @ApiOperation(value = "APP修改用户收货地址")
    @PostMapping(value = "/modify")
    public Result modifyAddress(@RequestBody @Validated ShippingAddressDto addressDto) {
        shippingAddressService.modifyAddress(addressDto);
        return ResultUtil.success();
    }
}
