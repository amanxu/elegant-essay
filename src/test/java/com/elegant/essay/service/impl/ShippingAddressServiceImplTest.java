package com.elegant.essay.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-13 15:44
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShippingAddressServiceImplTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        //构造MockMvc
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void createAddress() throws Exception {
        mvc.perform(post("/address/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("userId", "8")
                .param("areaId", "2")
                .param("streetId", "6")
                .param("zipCode", "473200")
                .param("addressDesc", "河南省南阳市方城县")
                .param("cellphone", "16602116670")
                .param("consigneeName", "聂晓旭")
                .param("isDefault", "0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                //使用jsonPath解析返回值，判断具体的内容
                .andExpect(content().string(Matchers.containsString("OK")));
        //使用jsonPath解析返回值，判断具体的内容
               /* .andExpect(jsonPath("$.errcode", is(0)));*/

    }

}