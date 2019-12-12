package com.elegant.essay.service.impl;

import com.elegant.essay.enums.ErrorCodeEnum;
import com.elegant.essay.exception.BusinessException;
import com.elegant.essay.mapper.ShippingAddressMapper;
import com.elegant.essay.model.ShippingAddress;
import com.elegant.essay.model.dto.ShippingAddressDto;
import com.elegant.essay.service.IShippingAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-21 10:19
 */
@Service
public class ShippingAddressServiceImpl implements IShippingAddressService {

    @Resource
    private ShippingAddressMapper shippingAddressMapper;

    @Override
    public void createAddress(ShippingAddressDto shippingAddressDto) {

        ShippingAddress shippingAddress = convertDtoToDbPojo(shippingAddressDto);
        shippingAddress.setCreateTime(new Date());
        int result = shippingAddressMapper.insert(shippingAddress);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.USER_ADDRESS_ADD_ERR.getCode(), ErrorCodeEnum.USER_ADDRESS_ADD_ERR.getMsg());
        }
    }

    @Override
    public void modifyAddress(ShippingAddressDto shippingAddressDto) {
        ShippingAddress shippingAddress = shippingAddressMapper.selectByPrimaryKey(shippingAddressDto.getId());
        if (shippingAddress == null) {
            throw new BusinessException(ErrorCodeEnum.USER_ADDRESS_NOT_EXISTS_ERR.getMsg());
        }
        shippingAddress = convertDtoToDbPojo(shippingAddressDto);
        int result = shippingAddressMapper.insertSelective(shippingAddress);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.USER_ADDRESS_MODIFY_ERR.getMsg());
        }
    }

    private ShippingAddress convertDtoToDbPojo(ShippingAddressDto shippingAddressDto) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddressId(shippingAddressDto.getId());
        shippingAddress.setAreaId(shippingAddressDto.getAreaId());
        shippingAddress.setStreetId(shippingAddressDto.getStreetId());
        shippingAddress.setUserId(shippingAddressDto.getUserId());
        shippingAddress.setAddressDesc(shippingAddressDto.getAddressDesc());
        shippingAddress.setCellphone(shippingAddressDto.getCellphone());
        shippingAddress.setConsigneeName(shippingAddressDto.getConsigneeName());
        shippingAddress.setIsDefault(shippingAddressDto.getIsDefault());
        shippingAddress.setZipCode(shippingAddressDto.getZipCode());
        return shippingAddress;
    }
}
