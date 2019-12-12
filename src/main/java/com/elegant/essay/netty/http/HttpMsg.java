package com.elegant.essay.netty.http;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-05 18:10
 */
@Setter
@Getter
@ToString
public class HttpMsg implements Serializable {

    private Integer msgType;
}
