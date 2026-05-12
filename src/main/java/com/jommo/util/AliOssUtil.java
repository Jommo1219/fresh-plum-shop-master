package com.jommo.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;

import java.io.InputStream;
import java.util.List;

public class AliOssUtil {
    private static final String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "your_access_key_id";
    private static final String SECRET_ACCESS_KEY = "your_secret_access_key";
    private static final String BUCKET_NAME = "fresh-plum-shop";

    /**
     * 上传文件到OSS
     *
     * @param objectName  文件路径（包含文件名），例如 "images/avatar.jpg"
     * @param inputStream 文件输入流
     * @return 文件访问URL
     */
    public static String uploadFile(String objectName, InputStream inputStream) {
        validateObjectName(objectName);

        OSS ossClient = createClient();
        try {
            // 自动创建Bucket（如果不存在）
            if (!ossClient.doesBucketExist(BUCKET_NAME)) {
                ossClient.createBucket(BUCKET_NAME);
            }

            ossClient.putObject(BUCKET_NAME, objectName, inputStream);
            return generateUrl(objectName);
        } finally {
            shutdownClient(ossClient);
        }
    }

    /**
     * 删除单个文件
     *
     * @param objectName 文件路径
     * @return 是否删除成功
     */
    public static boolean deleteFile(String objectName) {
        validateObjectName(objectName);

        OSS ossClient = createClient();
        try {
            if (ossClient.doesObjectExist(BUCKET_NAME, objectName)) {
                ossClient.deleteObject(BUCKET_NAME, objectName);
                return true;
            }
            return false;
        } finally {
            shutdownClient(ossClient);
        }
    }

    /**
     * 批量删除文件
     *
     * @param objectNames 文件路径列表
     * @return 成功删除的文件列表
     */
    public static List<String> batchDeleteFiles(List<String> objectNames) {
        if (objectNames == null || objectNames.isEmpty()) {
            throw new IllegalArgumentException("文件列表不能为空");
        }

        OSS ossClient = createClient();
        try {
            DeleteObjectsRequest request = new DeleteObjectsRequest(BUCKET_NAME)
                    .withKeys(objectNames)
                    .withEncodingType("url");

            DeleteObjectsResult result = ossClient.deleteObjects(request);
            return result.getDeletedObjects();
        } finally {
            shutdownClient(ossClient);
        }
    }

    // 公共方法私有化实现细节
    private static OSS createClient() {
        return new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, SECRET_ACCESS_KEY);
    }

    private static void shutdownClient(OSS ossClient) {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }

    private static String generateUrl(String objectName) {
        return "https://" + BUCKET_NAME + "."
                + ENDPOINT.substring(ENDPOINT.lastIndexOf("/") + 1)
                + "/" + objectName;
    }

    private static void validateObjectName(String objectName) {
        if (objectName == null || objectName.trim().isEmpty()) {
            throw new IllegalArgumentException("文件路径不能为空");
        }
    }

    // 异常处理统一方法
    private static void handleOssException(OSSException oe) {
        System.err.println("OSS操作失败：" + oe.getMessage());
        System.err.println("错误代码：" + oe.getErrorCode());
        System.err.println("请求ID：" + oe.getRequestId());
        System.err.println("主机ID：" + oe.getHostId());
    }

    private static void handleClientException(ClientException ce) {
        System.err.println("客户端异常：" + ce.getMessage());
    }
}