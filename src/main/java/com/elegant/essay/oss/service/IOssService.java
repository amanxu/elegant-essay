package com.elegant.essay.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-25 23:54
 */
public interface IOssService {

    /**
     * 创建存储空间
     *
     * @param bucketName
     */
    public void buildBucket(String bucketName);

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    String upLoadFile(MultipartFile file);

    /**
     * 下载文件
     *
     * @param objectName
     */
    void downLoadFile(String objectName);

    /**
     * 列举文件
     */
    void listFile();

    /**
     * 删除文件
     *
     * @param objectName
     */
    void deleteFile(String objectName);

    /**
     * 写文件流到本地
     *
     * @param multipartFile
     */
    public void writeFileToLocal(MultipartFile multipartFile);
}
