package com.elegant.essay.mapper;

import com.elegant.essay.model.ShippingAddress;

public interface ShippingAddressMapper {
    int deleteByPrimaryKey(Long addressId);

    int insert(ShippingAddress record);

    int insertSelective(ShippingAddress record);

    ShippingAddress selectByPrimaryKey(Long addressId);

    int updateByPrimaryKeySelective(ShippingAddress record);

    int updateByPrimaryKey(ShippingAddress record);
}