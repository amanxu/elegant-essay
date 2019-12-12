package com.elegant.essay.service;

import com.elegant.essay.model.dto.ShippingAddressDto;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-21 10:18
 */
public interface IShippingAddressService {

    /**
     * APP 端用户新增收获地址
     *
     * @param shippingAddressDto
     */
    void createAddress(ShippingAddressDto shippingAddressDto);

    /**
     * 修改用户地址
     *
     * @param shippingAddressDto
     */
    void modifyAddress(ShippingAddressDto shippingAddressDto);
}
