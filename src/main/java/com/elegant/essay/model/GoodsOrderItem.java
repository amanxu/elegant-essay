package com.elegant.essay.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiaoxu.nie
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsOrderItem implements Serializable {
    private static final long serialVersionUID = -5661075032770610750L;
    private Long orderItemId;

    private Long goodsId;

    private String goodsName;

    private String goodsIcon;

    private BigDecimal price;

    private Integer tradeStatus;

    private Integer quantity;

    private Long orderId;

}