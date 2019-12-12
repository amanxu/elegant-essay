package com.elegant.essay.manager.controller;

import com.elegant.essay.annotation.PermessionLimit;
import com.elegant.essay.model.pojo.Result;
import com.elegant.essay.oss.service.IOssService;
import com.elegant.essay.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-25 23:48
 */
@Api(description = "管理后台|OSS文件操作API")
@Slf4j
@RestController
@RequestMapping
public class OssFileController {

    @Autowired
    private IOssService ossService;

    /**
     * 单文件上传
     *
     * @return
     */
    @PermessionLimit(limit = false)
    @ApiOperation(value = "单文件上传")
    @PostMapping("/oss/upLoadFile")
    public Result upLoadFile(@RequestParam("file") MultipartFile file) {

        String imgUrl = ossService.upLoadFile(file);
        return ResultUtil.success(imgUrl);
    }

    @PermessionLimit(limit = false)
    @ApiOperation(value = "多文件上传")
    @PostMapping("/oss/upLoadFiles")
    public Result upLoadFiles(HttpServletRequest request) {

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        List<String> imgUrls = files.stream().map(file -> {
            return ossService.upLoadFile(file);
        }).collect(Collectors.toList());
        return ResultUtil.success(imgUrls);
    }

    @PermessionLimit(limit = false)
    @ApiOperation(value = "上传文件并写文件到本地路径")
    @PostMapping("/fileLocal/upLoadFiles")
    public Result writeFileToLocal(@RequestParam("file") MultipartFile file) {
        ossService.writeFileToLocal(file);
        return ResultUtil.success();
    }
}
