package com.elegant.essay.mapper;

import com.elegant.essay.model.dto.UserQuery;
import com.elegant.essay.model.vo.GoodsOrderBriefVO;
import com.elegant.essay.model.vo.GoodsOrderDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaoxu.nie
 */
public interface GoodsOrderSelfMapper {

    /**
     * 根据用户ID检索用户订单
     *
     * @param userId
     * @return
     */
    List<GoodsOrderBriefVO> findOrdersBriefByUserId(@Param("userId") Long userId);


    GoodsOrderDetailVO findOrderDetailById(UserQuery userQuery);
}