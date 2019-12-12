package com.elegant.essay.utils;

import com.elegant.essay.model.pojo.ElasticPage;
import org.springframework.data.domain.Page;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-27 16:48
 */
public class ElasticPageUtil<T> {

    public static ElasticPage convertToEsPage(Page page) {
        ElasticPage elasticPage = new ElasticPage();
        elasticPage.setPageNo(page.getSize());
        elasticPage.setPageSize(page.getSize());
        elasticPage.setTotalElement(page.getTotalElements());
        elasticPage.setTotalPage(page.getTotalPages());
        elasticPage.setContent(page.getContent());
        return elasticPage;
    }
}
