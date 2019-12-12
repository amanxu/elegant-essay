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
public class ArtisanGoods implements Serializable {

    private static final long serialVersionUID = -1989248698680462206L;
    private Long goodsId;

    private String goodsName;

    private String goodsIcon;

    private String goodsBrief;

    private String goodsDesc;

    private BigDecimal price;

    private BigDecimal promotionPrice;

    private Integer category;

    private Integer salesVolume;

    private Integer pageView;

    private Integer stocks;

    private Integer sort;

    private Integer deleted;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Date updateTime;

}