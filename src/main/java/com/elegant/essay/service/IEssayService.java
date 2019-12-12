package com.elegant.essay.service;

import com.elegant.essay.elastic.pojo.ElasticEssay;
import com.elegant.essay.model.ElegantEssay;
import com.elegant.essay.model.dto.EssayDto;
import com.elegant.essay.model.dto.EssayElasticQueryDto;
import com.elegant.essay.model.dto.EssayOperateDto;
import com.elegant.essay.model.dto.EssayQueryDto;
import com.elegant.essay.model.pojo.ElasticPage;
import com.elegant.essay.model.vo.EssayVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-22 20:48
 */
public interface IEssayService {

    void createEssay(EssayDto essayDto);

    void modify(EssayDto essayDto);

    void delete(Long id);

    EssayVo detail(Long id);

    Long essayCount();

    void publishDraft(EssayOperateDto operateDto);

    PageInfo<EssayVo> articlePage(EssayQueryDto queryDto);

    /**
     * 通过ES分页查询文章
     *
     * @param queryDto
     * @return
     */
    ElasticPage<ElasticEssay> elasticPageEssayByFilter(EssayElasticQueryDto queryDto);

    /**
     * 根据过滤条件检索文章列表
     *
     * @param queryDto
     * @return
     */
    List<ElasticEssay> elasticEssayByFilter(EssayElasticQueryDto queryDto);

    /**
     * 通过ES查询文章详情
     *
     * @param id
     * @return
     */
    ElasticEssay elasticDetail(Long id);

    /**
     * 根据ID集合批量查询ES记录
     *
     * @param ids
     * @return
     */
    List<ElasticEssay> elasticBatchQueryByIds(List<Long> ids);

    /**
     * 根据条件检索对象并返回对象的ID
     *
     * @param queryDto
     * @return
     */
    List<String> elasticQueryForIds(EssayElasticQueryDto queryDto);

    /**
     * 查询数据总数
     *
     * @return
     */
    Long elasticCountEssay();

    /**
     * 新增或更新索引对象
     *
     * @param elegantEssay
     */
    void saveOrUpdateIndex(ElegantEssay elegantEssay);

    /**
     * 根据ID删除索引中的记录
     *
     * @param id
     */
    void deleteIndexById(Long id);

    /**
     * 批量插入
     *
     * @param elegantEssays
     */
    void batchInsertEssay(List<ElegantEssay> elegantEssays);

    /**
     * 批量插入指定数量的数据
     *
     * @param totalNum
     * @param batchInsertNum
     */
    void batchCreateEssay(Integer totalNum, Integer batchInsertNum);

    /**
     * 向ES中批量插入文章
     *
     * @param elegantEssays
     */
    void bulkIndex(List<ElegantEssay> elegantEssays);

}
