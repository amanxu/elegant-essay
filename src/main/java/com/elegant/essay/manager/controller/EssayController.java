package com.elegant.essay.manager.controller;

import com.elegant.essay.annotation.PermessionLimit;
import com.elegant.essay.elastic.pojo.ElasticEssay;
import com.elegant.essay.model.dto.*;
import com.elegant.essay.model.pojo.ElasticPage;
import com.elegant.essay.model.pojo.Result;
import com.elegant.essay.model.vo.EssayVo;
import com.elegant.essay.service.IEssayService;
import com.elegant.essay.utils.ResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-22 19:17
 */
@PermessionLimit(limit = false)
@Slf4j
@Api(description = "管理后台|文章管理API")
@RequestMapping("/article")
@RestController
public class EssayController {

    @Autowired
    private IEssayService essayService;

    @ApiOperation(value = "文章分页查询")
    @PostMapping("/list")
    public Result<PageInfo<EssayVo>> pageList(@Validated @RequestBody EssayQueryDto queryDto) {
        return ResultUtil.success(essayService.articlePage(queryDto));
    }

    @ApiOperation(value = "新增文章")
    @PostMapping("/create")
    public Result create(@Validated @RequestBody EssayDto essayDto) {
        essayService.createEssay(essayDto);
        return ResultUtil.success();
    }

    @ApiOperation(value = "修改文章")
    @PostMapping("/modify")
    public Result modify(@Validated @RequestBody EssayDto essayDto) {
        essayService.modify(essayDto);
        return ResultUtil.success();
    }

    @ApiOperation(value = "文章详情")
    @PostMapping("/detail")
    public Result detail(@RequestBody IdQuery idQuery) {

        return ResultUtil.success(essayService.detail(idQuery.getId()));
    }

    @ApiOperation(value = "删除文章")
    @PostMapping("/delete")
    public Result delete(@RequestBody IdQuery idQuery) {
        essayService.delete(idQuery.getId());
        return ResultUtil.success();
    }

    @ApiOperation(value = "查询文章总数|跨库跨表")
    @PostMapping("/essayCount")
    public Result<Long> essayCount() {
        return ResultUtil.success(essayService.essayCount());
    }

    @ApiOperation(value = "ES文章分页查询")
    @PostMapping("/elasticPage")
    public Result<ElasticPage<ElasticEssay>> elasticPageList(@Validated @RequestBody EssayElasticQueryDto queryDto) {
        ElasticPage<ElasticEssay> essayPage = essayService.elasticPageEssayByFilter(queryDto);
        return ResultUtil.success(essayPage);
    }

    @ApiOperation(value = "ES文章列表查询")
    @PostMapping("/elasticEssays")
    public Result<List<ElasticEssay>> elasticList(@Validated @RequestBody EssayElasticQueryDto queryDto) {
        return ResultUtil.success(essayService.elasticEssayByFilter(queryDto));
    }

    @ApiOperation(value = "ES文章详情")
    @PostMapping("/elasticDetail")
    public Result<ElasticEssay> EsDetail(@RequestBody IdQuery idQuery) {

        return ResultUtil.success(essayService.elasticDetail(idQuery.getId()));
    }


    @ApiOperation(value = "ES根据过滤条件检索对象的ID")
    @PostMapping("/elasticIdsByFilter")
    public Result<List<String>> queryForIdsByFilter(@Validated @RequestBody EssayElasticQueryDto queryDto) {

        return ResultUtil.success(essayService.elasticQueryForIds(queryDto));
    }

    @ApiOperation(value = "ES查询文章总数")
    @PostMapping("/elasticCount")
    public Result<Long> elasticCountEssay() {

        return ResultUtil.success(essayService.elasticCountEssay());
    }

    @ApiOperation(value = "ES根据ID集合批量查询文章")
    @PostMapping("/batchQueryByIds")
    public Result<ElasticEssay> batchQueryByIds(@Validated @RequestBody ElasticIdsDto idsDto) {

        return ResultUtil.success(essayService.elasticBatchQueryByIds(idsDto.getIds()));
    }

    @ApiOperation(value = "ES根据ID删除Index对象")
    @PostMapping("/deleteIndexById")
    public Result<Long> deleteIndexById(@Validated @RequestBody IdQuery idQuery) {
        essayService.deleteIndexById(idQuery.getId());
        return ResultUtil.success();
    }

    @ApiOperation(value = "测试批量插入文章")
    @GetMapping("/batchInsertEssay")
    public Result batchInsertEssay(@RequestParam("totalNum")Integer totalNum,@RequestParam("batchInsertNum")Integer batchInsertNum) {
        essayService.batchCreateEssay(totalNum,batchInsertNum);
        return ResultUtil.success();
    }

}
