package com.elegant.essay.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.elegant.essay.config.OssConfigProperties;
import com.elegant.essay.enums.ErrorCodeEnum;
import com.elegant.essay.exception.BusinessException;
import com.elegant.essay.oss.service.IOssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-25 21:23
 */
@Slf4j
@Service
public class OssServiceImpl implements IOssService {

    @Autowired
    private OssConfigProperties ossConfigProperties;

    /**
     * 创建存储空间
     *
     * @param bucketName
     */
    @Override
    public void buildBucket(String bucketName) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(ossConfigProperties.getEndPoint(), ossConfigProperties.getAccessKeyId(),
                ossConfigProperties.getAccessKeySecret());
        // 创建存储空间。
        Bucket bucket = ossClient.createBucket(ossConfigProperties.getBucketName());
        // 关闭OSSClient。
        ossClient.shutdown();

    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    public String upLoadFile(MultipartFile file) {

        if (file == null) {
            throw new BusinessException(ErrorCodeEnum.FILE_NULL_ERR.getCode(), ErrorCodeEnum.FILE_NULL_ERR.getMsg());
        }
        UUID imgUid = UUID.randomUUID();
        String imgName = imgUid.toString().replaceAll("-", "");
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String objName = imgName + suffix;
        OSSClient ossClient = new OSSClient(ossConfigProperties.getEndPoint(), ossConfigProperties.getAccessKeyId(),
                ossConfigProperties.getAccessKeySecret());
        try {
            // 上传文件。
            ossClient.putObject(ossConfigProperties.getBucketName(), objName,
                    file.getInputStream());
        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCodeEnum.FILE_NOT_FOUND_ERR.getCode(), ErrorCodeEnum.FILE_NOT_FOUND_ERR.getMsg());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭OSSClient
        ossClient.shutdown();
        String imgUrl = ossConfigProperties.getOssFilePath() + objName;
        return imgUrl;
    }

    /**
     * 下载文件
     *
     * @throws IOException
     */
    @Override
    public void downLoadFile(String objectName) {
        OSSClient ossClient = new OSSClient(ossConfigProperties.getEndPoint(), ossConfigProperties.getAccessKeyId(),
                ossConfigProperties.getAccessKeySecret());
        // 创建OSSClient实例。
        // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = ossClient.getObject(ossConfigProperties.getBucketName(), objectName);
        // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
        InputStream content = ossObject.getObjectContent();
        try {
            if (content == null) {
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            while (true) {
                String line = null;
                line = reader.readLine();
                if (line == null) {
                    break;
                }
            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            content.close();
            // 关闭OSSClient
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 列举文件
     */
    @Override
    public void listFile() {

        OSSClient ossClient = new OSSClient(ossConfigProperties.getEndPoint(), ossConfigProperties.getAccessKeyId(),
                ossConfigProperties.getAccessKeySecret());
        ObjectListing objectListing = ossClient.listObjects(ossConfigProperties.getBucketName());
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            log.info(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 删除文件
     */
    @Override
    public void deleteFile(String objectName) {
        OSSClient ossClient = new OSSClient(ossConfigProperties.getEndPoint(), ossConfigProperties.getAccessKeyId(),
                ossConfigProperties.getAccessKeySecret());
        // 删除文件
        ossClient.deleteObject(ossConfigProperties.getBucketName(), objectName);
        // 关闭OSSClient
        ossClient.shutdown();
    }

    @Override
    public void writeFileToLocal(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.FILE_NOT_FOUND_ERR.getCode(), ErrorCodeEnum.FILE_NOT_FOUND_ERR.getMsg());
        }
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            // 可以是自定义的配置路径或者是当前项目中的路径
            String classPath = ResourceUtils.getURL("classpath:templates/orderImage/").getPath();
            String path = classPath + multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            File file = new File(path);
            //如果文件不存在，则创建新的文件
            if (!file.exists()) {
                log.debug("File Not Exists Path:{}" + path);
                //写入内容到文件里
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
                writer.write(reader.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("reader.close err:{}", e);
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("writer.close err:{}", e);
                }
            }
        }
    }
}
