package com.elegant.essay.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaoxu.nie
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsOrder implements Serializable {
    private static final long serialVersionUID = -161963611516957131L;
    private Long orderId;

    private BigDecimal price;

    private BigDecimal payAmount;

    private String payAccount;

    private BigDecimal goodsWeight;

    private Integer payStatus;

    private Integer orderStatus;

    private Integer deleted;

    private Long userId;

    private String userName;

    private Integer payWay;

    private String tradeNum;

    private String cellphone;

    private String shippingAddress;

    private Date payTime;

    private Date consignmentTime;

    private Date completeTime;

    private Date createTime;

    private Date updateTime;
}