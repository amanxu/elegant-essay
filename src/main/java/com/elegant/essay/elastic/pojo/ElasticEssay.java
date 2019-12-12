package com.elegant.essay.elastic.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-27 14:02
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "elegant-essay", type = "ElasticEssay")
public class ElasticEssay implements Serializable {

    private Long essayId;

    private String title;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZ",timezone="GMT+8")
    private Date publishTime;

    private String summary;

    private Integer allowComment;

    private Integer sort;

    private String content;

    private String imgUrl;

    private String status;

    private Integer deleted;

    private Long userId;

    private Integer categoryId;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Field(type = FieldType.Text,index = true)
    private String statusDesc;

    @Field(type = FieldType.Text,index = true)
    private String userName;

    private String categoryDesc;

}
