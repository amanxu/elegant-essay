package com.elegant.essay.service.impl;

import com.alibaba.fastjson.JSON;
import com.elegant.essay.elastic.pojo.ElasticEssay;
import com.elegant.essay.enums.*;
import com.elegant.essay.exception.BusinessException;
import com.elegant.essay.jms.config.MsgConfigProperties;
import com.elegant.essay.mapper.*;
import com.elegant.essay.model.ElegantEssay;
import com.elegant.essay.model.ElegantUser;
import com.elegant.essay.model.EssayCategory;
import com.elegant.essay.model.dto.*;
import com.elegant.essay.model.pojo.ElasticPage;
import com.elegant.essay.model.vo.EssayVo;
import com.elegant.essay.model.vo.UserVo;
import com.elegant.essay.service.IEssayService;
import com.elegant.essay.utils.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.util.CloseableIterator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-22 20:52
 */
@Slf4j
@Service
public class EssayServiceImpl implements IEssayService {

    @Autowired
    private ElegantEssayMapper essayMapper;

    @Autowired
    private EssaySelfMapper essaySelfMapper;

    @Autowired
    private UserSelfMapper userSelfMapper;

    @Autowired
    private ElegantUserMapper elegantUserMapper;

    @Autowired
    private EssayCategoryMapper categoryMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private EssayCategoryMapper essayCategoryMapper;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private MsgConfigProperties mqConfigProperties;

    @Override
    public PageInfo<EssayVo> articlePage(EssayQueryDto queryDto) {

        // Sharding Sphere 强制走主库
        /*HintManager.getInstance().setMasterRouteOnly();*/

        PageHelper.startPage(queryDto.getPageNo(), queryDto.getPageSize());
        List<EssayVo> essayVos = essaySelfMapper.findByFilter(queryDto);
        if (CollectionUtils.isEmpty(essayVos)) {
            return new PageInfo<>(essayVos);
        }
        PageInfo<EssayVo> pageInfo = new PageInfo<>(essayVos);
        Set<Long> userIds = essayVos.stream().map(EssayVo::getUserId).collect(Collectors.toSet());
        List<ElegantUser> users = userSelfMapper.batchUsersByIds(userIds);
        Map<Long, String> usersMap = users.stream().collect(Collectors.toMap(ElegantUser::getUserId, ElegantUser::getRealName));
        pageInfo.setList(pageInfo.getList().stream().map(art -> {
            art.setStatusDesc(EssayStatusEnum.getMsgByCode(art.getStatus()));
            if (CollectionUtils.isNotEmpty(usersMap)) {
                art.setUserName(usersMap.get(art.getUserId()));
            }
            return art;
        }).collect(Collectors.toList()));
        /*log.info("PAGE LIST:{}", JSON.toJSONString(pageInfo.getList()));*/
        return pageInfo;
    }

    private ElegantEssay convertInToDb(EssayDto essayDto) {
        ElegantEssay elegantEssay = new ElegantEssay();
        elegantEssay.setTitle(essayDto.getTitle());
        elegantEssay.setAllowComment(essayDto.getAllowComment());
        elegantEssay.setContent(essayDto.getContent());
        elegantEssay.setImgUrl(essayDto.getImgUrl());
        elegantEssay.setStatus(essayDto.getStatus());
        elegantEssay.setSummary(essayDto.getSummary());
        elegantEssay.setUserId(essayDto.getUserId());
        elegantEssay.setSort(essayDto.getSort());
        elegantEssay.setCategoryId(essayDto.getCategoryId());
        elegantEssay.setPublishTime(essayDto.getPublishTime());
        elegantEssay.setDeleted(DeleteStatusEnum.NORMAL.getCode());
        elegantEssay.setCreateTime(new Date());
        elegantEssay.setUpdateTime(new Date());
        return elegantEssay;
    }

    private EssayVo convertDbToVo(ElegantEssay elegantEssay) {
        EssayVo essayVo = new EssayVo();
        essayVo.setEssayId(elegantEssay.getUserId());
        essayVo.setTitle(elegantEssay.getTitle());
        essayVo.setAllowComment(elegantEssay.getAllowComment());
        essayVo.setContent(elegantEssay.getContent());
        essayVo.setStatus(elegantEssay.getStatus());
        essayVo.setStatusDesc(EssayStatusEnum.getMsgByCode(elegantEssay.getStatus()));
        essayVo.setSummary(elegantEssay.getSummary());
        essayVo.setUserId(elegantEssay.getUserId());
        essayVo.setImgUrl(elegantEssay.getImgUrl());
        essayVo.setSort(elegantEssay.getSort());
        essayVo.setPublishTime(elegantEssay.getPublishTime());
        return essayVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createEssay(EssayDto essayDto) {
        ElegantEssay elegantEssay = convertInToDb(essayDto);
        int result = essayMapper.insert(elegantEssay);
        if (result < 0) {
            throw new BusinessException(ErrorCodeEnum.ADD_ERR.getCode(), ErrorCodeEnum.ADD_ERR.getMsg());
        }
        // 方案一直接打入ES；方案二打入Kafka消息中间件
        /*saveOrUpdateIndex(elegantEssay);*/
    }

    /**
     * 批量插入ES索引对象
     *
     * @param elegantEssays
     */
    @Override
    public void bulkIndex(List<ElegantEssay> elegantEssays) {

        List<IndexQuery> indexQueries = elegantEssays.stream().map(elegantEssay -> {
            return assembleIndexQuery(elegantEssay);
        }).collect(Collectors.toList());
        elasticsearchTemplate.bulkIndex(indexQueries);
    }

    /**
     * 组装es索引对象
     *
     * @param elegantEssay
     * @return
     */
    private IndexQuery assembleIndexQuery(ElegantEssay elegantEssay) {
        ElasticEssay elasticEssay = new ElasticEssay();
        BeanUtils.copyProperties(elegantEssay, elasticEssay);
        ElegantUser elegantUser = elegantUserMapper.selectByPrimaryKey(elegantEssay.getUserId());
        if (elegantUser != null) {
            elasticEssay.setUserName(elegantUser.getRealName());
        }
        EssayCategory essayCategory = categoryMapper.selectByPrimaryKey(elegantEssay.getCategoryId());
        if (essayCategory != null) {
            elasticEssay.setCategoryDesc(essayCategory.getCategoryName());
        }
        elasticEssay.setStatusDesc(EssayStatusEnum.getMsgByCode(elasticEssay.getStatus()));
        // 打入ES
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(String.valueOf(elegantEssay.getEssayId()));
        indexQuery.setObject(elasticEssay);
        indexQuery.setIndexName("elegant-essay");
        indexQuery.setType("ElasticEssay");
        return indexQuery;
    }

    @Override
    public void modify(EssayDto essayDto) {

        ElegantEssay elegantEssay = essayMapper.selectByPrimaryKey(essayDto.getEssayId());
        if (elegantEssay == null) {
            throw new BusinessException(ErrorCodeEnum.ART_NOT_EXIST_ERR.getCode(), ErrorCodeEnum.ART_NOT_EXIST_ERR.getMsg());
        }
        int result = essayMapper.updateByPrimaryKey(convertInToDb(essayDto));
        if (result < 0) {
            throw new BusinessException(ErrorCodeEnum.UPDATE_ERR.getCode(), ErrorCodeEnum.UPDATE_ERR.getMsg());
        }
    }

    @Override
    public void delete(Long id) {
        ElegantEssay elegantEssay = essayMapper.selectByPrimaryKey(id);
        if (elegantEssay == null) {
            throw new BusinessException(ErrorCodeEnum.ART_NOT_EXIST_ERR.getCode(), ErrorCodeEnum.ART_NOT_EXIST_ERR.getMsg());
        }
        elegantEssay.setDeleted(CommonStatusEnum.DELETED.getCode());
    }

    @Override
    public EssayVo detail(Long id) {
        ElegantEssay elegantEssay = essayMapper.selectByPrimaryKey(id);
        if (elegantEssay == null) {
            throw new BusinessException(ErrorCodeEnum.ART_NOT_EXIST_ERR.getCode(), ErrorCodeEnum.ART_NOT_EXIST_ERR.getMsg());
        }
        return convertDbToVo(elegantEssay);
    }

    @Override
    public Long essayCount() {
        return essayMapper.essayCount();
    }

    @Override
    public void publishDraft(EssayOperateDto operateDto) {
        ElegantEssay elegantEssay = essayMapper.selectByPrimaryKey(operateDto.getId());
        if (elegantEssay == null) {
            throw new BusinessException(ErrorCodeEnum.ART_NOT_EXIST_ERR.getCode(), ErrorCodeEnum.ART_NOT_EXIST_ERR.getMsg());
        }
        if (EssayStatusEnum.PUBLISHED.getCode().equals(operateDto.getOperateType())) {
            elegantEssay.setStatus(EssayStatusEnum.PUBLISHED.getCode());
        } else {
            elegantEssay.setStatus(EssayStatusEnum.DRAFT.getCode());
        }
        essayMapper.updateByPrimaryKey(elegantEssay);
    }


    @Override
    public ElasticPage<ElasticEssay> elasticPageEssayByFilter(EssayElasticQueryDto queryDto) {
        Pageable pageable = PageRequest.of(queryDto.getPageNo() - 1, queryDto.getPageSize());
        QueryStringQueryBuilder builder = QueryBuilders.queryStringQuery(queryDto.getContent());

        // 1.方式1使用repository；2.方式二使用elasticSearchTemplate
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(builder).build();
        AggregatedPage<ElasticEssay> elegantEssays = elasticsearchTemplate.queryForPage(searchQuery, ElasticEssay.class);
        log.info("QueryStringQueryBuilder:{}", JSON.toJSONString(elegantEssays.getContent()));
        // 1.匹配对应字段，通过essayId降序排列
        SortBuilder sortBuilderDesc = SortBuilders.fieldSort("essayId").order(SortOrder.DESC);
        SearchQuery searchQueryMatch = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(matchQuery("categoryDesc", queryDto.getCategoryName())).withSort(sortBuilderDesc).build();
        List<ElasticEssay> elasticEssaysMatch = elasticsearchTemplate.queryForList(searchQueryMatch, ElasticEssay.class);
        log.info("匹配对应字段 MATCH:{}", JSON.toJSONString(elasticEssaysMatch));
        // 2.匹配任意字段,并通过essayId升序排列
        SortBuilder sortBuilder = SortBuilders.fieldSort("essayId").order(SortOrder.DESC);
        SearchQuery searchQueryMatchAll = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(matchAllQuery()).withSort(sortBuilder).build();
        List<ElasticEssay> elasticEssaysMatchAll = elasticsearchTemplate.queryForList(searchQueryMatchAll, ElasticEssay.class);
        log.info("匹配任意字段 MATCH:{}", JSON.toJSONString(elasticEssaysMatchAll));
        // 3.高亮搜索结果
        SearchQuery searchQueryHeightLight = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(matchPhraseQuery("userName", queryDto.getUserName())).withHighlightFields(new HighlightBuilder.Field("userName")).build();
        List<ElasticEssay> elasticEssaysLight = elasticsearchTemplate.queryForList(searchQueryHeightLight, ElasticEssay.class);
        log.info("高亮搜索结果 MATCH:{}", JSON.toJSONString(elasticEssaysLight));
        // 4.多个字段匹配查询
        MultiMatchQueryBuilder multiMatchQuery = multiMatchQuery(queryDto.getContent(), "title", "summary");
        SearchQuery searchQueryMultiMatch = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(multiMatchQuery).build();
        List<ElasticEssay> elasticEssayMultiMatch = elasticsearchTemplate.queryForList(searchQueryMultiMatch, ElasticEssay.class);
        log.info("多条件匹配，匹配标题和摘要 MULTI MATCH:{}", JSON.toJSONString(elasticEssayMultiMatch));

        /*return ElasticPageUtil.convertToEsPage(elegantEssays);*/
        return new ElasticPage<ElasticEssay>(elasticEssaysMatchAll);
    }

    @Override
    public List<ElasticEssay> elasticEssayByFilter(EssayElasticQueryDto queryDto) {

        // 方式一使用repository,方式二使用elasticSearchTemplate
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(queryDto.getContent());
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        CloseableIterator<ElasticEssay> iteratorEssay = elasticsearchTemplate.stream(searchQuery, ElasticEssay.class);
        ArrayList<ElasticEssay> elasticEssays = Lists.newArrayList();
        while (iteratorEssay.hasNext()) {
            elasticEssays.add(iteratorEssay.next());
        }
        /*List<ElasticEssay> elasticEssays = elasticsearchTemplate.queryForList(searchQuery, ElasticEssay.class);*/
        log.info("QueryStringQueryBuilder:{}", JSON.toJSONString(elasticEssays));
        if (CollectionUtils.isEmpty(elasticEssays)) {
            return Collections.emptyList();
        }
        return elasticEssays;
    }

    @Override
    public ElasticEssay elasticDetail(Long id) {
        // 方式一使用repository;方式二elasticsearchTemplate
        GetQuery getQuery = new GetQuery();
        getQuery.setId(id.toString());
        ElasticEssay essay = elasticsearchTemplate.queryForObject(getQuery, ElasticEssay.class);
        return essay;
    }

    @Override
    public List<ElasticEssay> elasticBatchQueryByIds(List<Long> ids) {
        // 方式一 termsQuery
        TermsQueryBuilder termsQuery = QueryBuilders.termsQuery("essayId", ids);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termsQuery).build();
        List<ElasticEssay> elasticEssays = elasticsearchTemplate.queryForList(searchQuery, ElasticEssay.class);
        // 方式二 types 是对应Document中的type
        IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery("ElasticEssay").addIds(JSON.toJSONString(ids));
        SearchQuery IdsSearchQuery = new NativeSearchQueryBuilder().withQuery(idsQueryBuilder).build();
        List<ElasticEssay> queryForList = elasticsearchTemplate.queryForList(IdsSearchQuery, ElasticEssay.class);
        log.info("IdsSearchQuery:{}", JSON.toJSONString(queryForList));
        return elasticEssays;
    }

    @Override
    public List<String> elasticQueryForIds(EssayElasticQueryDto queryDto) {
        // 以根据分类查询为例，返回所查询的分类的文章的id集合
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("categoryDesc", queryDto.getCategoryName())).build();
        List<String> ids = elasticsearchTemplate.queryForIds(searchQuery);
        return ids;
    }

    @Override
    public Long elasticCountEssay() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().build();
        long count = elasticsearchTemplate.count(searchQuery, ElasticEssay.class);
        return count;
    }

    @Override
    public void saveOrUpdateIndex(ElegantEssay elegantEssay) {
        IndexQuery indexQuery = assembleIndexQuery(elegantEssay);
        elasticsearchTemplate.index(indexQuery);
    }

    @Override
    public void deleteIndexById(Long id) {
        String result = elasticsearchTemplate.delete(ElasticEssay.class, id.toString());
        log.info("Delete Result：{}", result);
    }

    @Override
    public void batchInsertEssay(List<ElegantEssay> elegantEssays) {
        int result = essayMapper.batchInsertEssay(elegantEssays);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.ADD_ERR.getCode(), ErrorCodeEnum.ADD_ERR.getMsg());
        }
        // 方案一直接打入ES；
        /*bulkIndex(elegantEssays);*/

        // 方案二打入Kafka
        /*kafkaTemplate.send("ESSAY_ES_TOPIC", JSON.toJSONString(elegantEssays));*/

        // 方案三打入RocketMQ消息中间件
       /* rocketMQTemplate.asyncSend(mqConfigProperties.getEssayTopic(), JSON.toJSONString(elegantEssays), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("Rocket MQ Send Success!!");
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("Rocket MQ Send Exception!!{}", throwable);
            }
        });*/
    }

    @Override
    public void batchCreateEssay(Integer totalNum, Integer batchInsertNum) {
        Random random = new Random();
        // 批量查询用户
        UserQueryDto userQueryDto = new UserQueryDto();
        userQueryDto.setPageNo(1);
        userQueryDto.setPageSize(1000);
        List<UserVo> userVos = userSelfMapper.pageList(userQueryDto);
        Integer userSize = userVos.size();
        // 批量查询分类
        int categorySize = 0;
        List<EssayCategory> categories = essayCategoryMapper.findCategoriesByLevel(null);
        if (CollectionUtils.isNotEmpty(categories)) {
            categorySize = categories.size();
        }
        // 文章内容
        String essayContent = EssayConstants.essayBuf.toString();
        Integer essayLength = essayContent.length();
        Integer bound = essayLength - 80;
        List<ElegantEssay> essayList = Lists.newArrayList();
        for (int i = 1; i < totalNum; i++) {
            ElegantEssay elegantEssay = new ElegantEssay();
            if (categorySize > 0) {
                elegantEssay.setCategoryId(categories.get(random.nextInt(categorySize)).getId());
            }
            elegantEssay.setAllowComment(random.nextInt(10) % 2);
            elegantEssay.setUserId(userVos.get(random.nextInt(userSize)).getUserId());
            elegantEssay.setPublishTime(new Date());
            if (random.nextInt(10) % 2 == 0) {
                elegantEssay.setStatus(EssayStatusEnum.PUBLISHED.getCode());
            } else {
                elegantEssay.setStatus(EssayStatusEnum.DRAFT.getCode());
            }
            elegantEssay.setSort(random.nextInt(5));
            int titleBound = random.nextInt(bound);
            elegantEssay.setTitle(essayContent.substring(titleBound, titleBound + 10));
            int summaryBound = random.nextInt(bound);
            elegantEssay.setSummary(essayContent.substring(summaryBound, summaryBound + 20));
            int contentBound = random.nextInt(bound);
            elegantEssay.setContent(essayContent.substring(contentBound, contentBound + 50));
            elegantEssay.setImgUrl("https://elegant-essay.oss-cn-beijing.aliyuncs.com/f1fa8595cc134c1a8481109743c48697.png");
            elegantEssay.setDeleted(DeleteStatusEnum.NORMAL.getCode());
            elegantEssay.setCreateTime(new Date());
            elegantEssay.setUpdateTime(new Date());
            essayList.add(elegantEssay);
            log.error("Error EssayServiceImpl batchCreateEssay:{}", JSON.toJSONString(elegantEssay));
            /*log.info("Info EssayServiceImpl batchCreateEssay:{}", JSON.toJSONString(elegantEssay));*/

            batchInsertEssay(essayList);
            essayList.clear();
        }
    }

}
