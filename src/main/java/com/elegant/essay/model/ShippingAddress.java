package com.elegant.essay.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoxu.nie
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress implements Serializable {
    private static final long serialVersionUID = 8616960497249532940L;
    private Long addressId;

    private Long userId;

    private Long areaId;

    private Long streetId;

    private String zipCode;

    private String addressDesc;

    private String cellphone;

    private String consigneeName;

    private Integer isDefault;

    private Date createTime;

    private Date updateTime;

}