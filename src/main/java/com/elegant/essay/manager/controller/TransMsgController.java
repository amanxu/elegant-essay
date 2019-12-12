package com.elegant.essay.manager.controller;

import com.elegant.essay.annotation.PermessionLimit;
import com.elegant.essay.model.pojo.Result;
import com.elegant.essay.service.ITransMsgService;
import com.elegant.essay.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-24 13:28
 */
@Api(description = "RocketMQ事务消息Bean模式")
@Slf4j
@PermessionLimit(limit = false)
@RestController
@RequestMapping("/transMsg")
public class TransMsgController {

    @Autowired
    private ITransMsgService transMsgService;

    @ApiOperation(value = "生产数据")
    @GetMapping(value = "/producer")
    public Result transMsgProducer() {
        transMsgService.producerTransMsg();
        return ResultUtil.success();
    }

    @ApiOperation(value = "XA事务测试接口")
    @GetMapping(value = "/xaTrans")
    public Result xaDataSourceTrans(@RequestParam("isRollback") boolean isRollback) {
        transMsgService.xaTransRollback(isRollback);
        return ResultUtil.success();
    }
}
