package com.taotao.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * fastdfs操作工具类
 * Created by niemengquan on 2017/8/9.
 */
public class FastDFSClientUtils {
    private static Logger logger = Logger.getLogger(FastDFSClientUtils.class);

    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    //使用StorageClient1进行上传
    private StorageClient1 storageClient1 = null;

    public FastDFSClientUtils(String conf) throws Exception {
        //路径总包含classpath的情况
        if(conf.contains("classpath:")){
            conf=conf.replace("classpath:",this.getClass().getResource("/").getPath());
        }
        /*//获取classpath路径下配置文件"fdfs_client.conf"的路径
        //conf直接写相对于classpath的位置，不需要写classpath:
        String configPath = this.getClass().getClassLoader().getResource(conf).getFile();*/
        System.out.println(conf);
        ClientGlobal.init(conf);

        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = trackerClient.getStoreStorage(trackerServer);
        storageClient1 = new StorageClient1(trackerServer, storageServer);
    }

    public String uploadFile(byte[] file_buff, String file_ext_name) throws Exception {

        String result = storageClient1.upload_file1(file_buff, file_ext_name, null);

        return result;
    }
    public String uploadFile(byte[] file_buff, String file_ext_name, NameValuePair[] metas) throws Exception {

        String result = storageClient1.upload_file1(file_buff, file_ext_name, metas);

        return result;
    }

    /**
     * 上传文件的方法
     * @param local_filename
     * @param file_ext_name
     * @return
     * @throws Exception
     */
    public String uploadFile(String local_filename, String file_ext_name) throws Exception {

        String result = storageClient1.upload_file1(local_filename, file_ext_name, null);

        return result;
    }
    /**
     * 根据文件路径上传文件的方法
     * @param localFileName 文件全路径方法
     * @return
     * @throws Exception
     */
    public String uploadFile(String localFileName) throws Exception {

        String result = storageClient1.upload_file1(localFileName, getFileExt(localFileName), null);

        return result;
    }
    /**
     * 根据fileId来删除一个文件（我们现在用的就是这样的方式，上传文件时直接将fileId保存在了数据库中）
     *
     * @param fileId
     *            file_id源码中的解释file_id the file id(including group name and filename);例如 group1/M00/00/00/ooYBAFM6MpmAHM91AAAEgdpiRC0012.xml
     * @return 0为成功，非0为失败，具体为错误代码
     */
    public  int deleteFile(String fileId) {
        try {
            int result = storageClient1.delete_file1(fileId);
            return result;
        } catch (Exception ex) {
            logger.error(ex);
            return 0;
        }
    }

    /**
     * 文件下载
     * @param fileId
     * @return 返回一个流
     */
    public  InputStream downloadFile(String fileId) {
        try {
            byte[] bytes = storageClient1.download_file1(fileId);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return inputStream;
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    /**
     * 获取文件后缀名（不带点）.
     *
     * @return 如："jpg" or "".
     */
    private static String getFileExt(String fileName) {
        if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
            return "";
        } else {
            return fileName.substring(fileName.lastIndexOf(".") + 1); // 不带最后的点
        }
    }
}
