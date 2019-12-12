package com.elegant.essay.sharding.config;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.elegant.essay.enums.ErrorCodeEnum;
import com.elegant.essay.exception.BusinessException;
import com.google.common.collect.Lists;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-17 14:48
 */
@Slf4j
@NoArgsConstructor
public class OrderItemHintShardingAlgorithm implements HintShardingAlgorithm {

    /*** 数据库单库分表总数*/
    private static int SHARDING_TABLE_NUM = 4;

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ShardingValue shardingValue) {
        log.debug("availableTargetNames={},shardingValue={}", JSON.toJSON(shardingValue), JSON.toJSON(shardingValue));
        List<String> shardingResult = Lists.newArrayList();
        availableTargetNames.forEach(targetName -> {
            String suffix = targetName.substring(targetName.lastIndexOf("_") + 1);
            if (!StringUtils.isNumber(suffix)) {
                throw new BusinessException(ErrorCodeEnum.SHARDING_RULE_CONF_ERR.getMsg());
            }
            ListShardingValue<Long> tmpSharding = (ListShardingValue<Long>) shardingValue;
            tmpSharding.getValues().forEach(value -> {
                if (value % SHARDING_TABLE_NUM == Long.parseLong(suffix)) {
                    shardingResult.add(targetName);
                }
            });
        });
        return shardingResult;
    }
}
