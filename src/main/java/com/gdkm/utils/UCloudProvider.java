package com.gdkm.utils;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.DownloadStreamBean;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import com.gdkm.exception.LinuxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

@Service
public class UCloudProvider {

    @Value("${ucloud.ufile.publicKey}")
    private String publicKey;

    @Value("${ucloud.ufile.privateKey}")
    private String privateKey;

    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;

    @Value("${ucloud.ufile.region}")
    private String region;

    @Value("${ucloud.ufile.proxySuffix}")
    private String proxySuffix;

    @Value("${ucloud.ufile.expiresDuration}")
    private Integer expiresDuration;

    //同步上传
    public String upload(InputStream fileStream, String mimeType, String fileName,String head) {
        String generatedFileName;
        String[] filePaths = fileName.split("\\.");
        System.out.println(filePaths.toString());
        if (filePaths.length > 1) {
            if (head!=null) {
                generatedFileName = head + "/" + UUID.randomUUID().toString() +"name"+filePaths[filePaths.length - 2]+ "." + filePaths[filePaths.length - 1];
            }else {
                generatedFileName =   "/" + UUID.randomUUID().toString() +"name"+filePaths[filePaths.length - 2]+ "." + filePaths[filePaths.length - 1];
            }
        } else {
            throw new LinuxException("文件上传错误");
        }

        try {
            // 对象相关API的授权器
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
            // 对象操作需要ObjectConfig来配置您的地区和域名后缀
            ObjectConfig config = new ObjectConfig(region, proxySuffix);
            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generatedFileName)
                    .toBucket(bucketName)
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();
            if (response != null && response.getRetCode() == 0) {
                String url = UfileClient.object(objectAuthorization, config)
                        .getDownloadUrlFromPrivateBucket(generatedFileName, bucketName, expiresDuration)
                        .createUrl();
                return url;
            } else {
                throw new LinuxException("文件上传错误");
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new LinuxException("文件上传错误");
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new LinuxException("文件上传错误");
        }
    }

    public void getStream(String url) {
        OutputStream outputStream = null;
//        OutputStream os = null;
//        os = new FileOutputStream(new File(localDir, saveName));

        try {
            ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse res = attributes.getResponse();


            String name ="";
            //找到文件的后缀
            int lastIndexOf1 = url.lastIndexOf("?");
            name = url.substring(0,lastIndexOf1);
            int lastIndexOf2 = name.lastIndexOf("/");
            name = name.substring(lastIndexOf2+1);
            int lastIndexOf3 = name.lastIndexOf("name");
            name = name.substring(lastIndexOf3+4);

            OutputStream os= res.getOutputStream();
            //设置响应头
            res.setContentType("application/force-download");// 设置强制下载不打开
            res.setHeader("Content-disposition","attachment;filename=\"" + URLEncoder.encode(name, "UTF-8")+"\"");
            res.setHeader("Context-Type", "application/xmsdownload");


            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
            // 对象操作需要ObjectConfig来配置您的地区和域名后缀
            ObjectConfig config = new ObjectConfig(region, proxySuffix);

            DownloadStreamBean response = UfileClient.object(objectAuthorization, config)
                    .getStream(url)
                    /**
                     * 重定向流
                     *
                     * 默认不重定向流，下载的流会以InputStream的形式在Response中回调，并且不会回调下载进度 onProgress;
                     *
                     * 如果配置了重定向的输出流，则Response {@link DownloadStreamBean}的 InputStream = null,
                     * 因为流已被重定向导流到OutputStream，并且会回调进度 onProgress。
                     */
                    /**
                     * 指定progress callback的间隔
                     */
//                .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置读写流Buffer的大小, Default = 256 KB, MIN = 4 KB, MAX = 4 MB
                     */
//                    .setBufferSize(4 << 20)
                    /**
                     * 配置进度监听
                     */
//                    .setOnProgressListener(new OnProgressListener() {
//                        @Override
//                        public void onProgress(long bytesWritten, long contentLength) {
//                            JLog.D(TAG, String.format("[progress] = %d%% - [%d/%d]", (int) (bytesWritten * 1.f / contentLength * 100), bytesWritten, contentLength));
//                        }
//                    })
                    .redirectStream(os)
                    .execute();

//            JLog.D(TAG, String.format("[res] = %s", (response == null ? "null" : response.toString())));
        } catch (UfileServerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (UfileClientException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url="http://gdkmlzh.cn-gd.ufileos.com/img%2F5e3af915-8nnn3c8-4f23-9953-name25ccb46363c1.jpg?UCloudPublicKey=7XUUrIZu_COBbktItgfRT0tYkbMP_GSP-OWjXsTe&Signature=ovOwiakYC8%2Fqc4jXMrkusxj%2FufI%3D&Expires=1576561190";
        String name ="";
        //找到文件的后缀
        int lastIndexOf1 = url.lastIndexOf("?");
        name = url.substring(0,lastIndexOf1);
        System.out.println(name);
        int lastIndexOf2 = name.lastIndexOf("/");
        name = name.substring(lastIndexOf2+1);
        System.out.println(name);
        int lastIndexOf3 = name.lastIndexOf("name");
        name = name.substring(lastIndexOf3+4);
        System.out.println(name);
    }
}

