package com.yuantek.mi.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Test
    public void testContext() {
        System.out.println(wac);
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        String content = mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("username", "jojo")
                .param("age", "18")
                .param("size", "15")
                .param("page", "3")
                .param("sort", "age,desc")
        )
                .andExpect(status().isOk())
                // $.length(): 返回来的东西是一个集合,集合的长度为3
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void whenGetInfoSuccess() throws Exception {
        String content = mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("tom"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

    // 测试先行,保证我的代码按照我的想法执行
    @Test
    public void whenGetInfoFail() throws Exception {
        mockMvc.perform(get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().is4xxClientError());
    }

    // 前后端是用json来交流
    @Test
    public void whenCreateSuccess() throws Exception {
        Date date = new Date();
        String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":" + date.getTime() + "}";
        String res = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(res);//{"id":"1","username":"tom","password":null,"birthday":1538838619019}
    }

    @Test
    public void whenUpdateSuccess() throws Exception {
        //Date date = new Date();
        long epTime = LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String content = "{\"username\":\"xiMin\",\"password\":null,\"birthday\":" + epTime + "}";
        String res = mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(res);//{"id":"1","username":"tom","password":null,"birthday":1538838619019}
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void whenUploadSuccess() throws Exception {
        String content = mockMvc.perform(fileUpload("/file")
                .file(new MockMultipartFile(
                        "file",
                        "test.txt",
                        "multipart/form-data",
                        "hello upload".getBytes("UTF-8")))
        )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);

    }
}
