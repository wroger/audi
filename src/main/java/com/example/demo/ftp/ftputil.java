package com.example.demo.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
public class ftputil {
    
    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${ftp_ip}")
    private String ftpIp;
    @Value("${ftp_port}")
    private Integer ftpPort;
    @Value("${ftp_user}")
    private String ftpUser;
    @Value("${ftp_password}")
    private String ftpPass;

    private FTPClient ftpClient;

    private boolean connectServer(String ip,int port,String user,String pwd){
        ftpClient=new FTPClient();
        Boolean isSuccess=false;
        try {
            ftpClient.connect(ip);
            isSuccess=ftpClient.login(user,pwd);
        } catch (IOException e) {
            logger.error("连接ftp服务器失败",e);
        }
        return isSuccess;
    }

    public boolean uploadFile(String remotePath, List<File>fileList) throws IOException {
        boolean upload=true;
        FileInputStream fileInputStream=null;
        //connect to ftpServer
        if (connectServer(ftpIp,ftpPort,ftpUser,ftpPass)){
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for (File fileItem:fileList
                        ) {
                    fileInputStream=new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(),fileInputStream);
                }
            } catch (IOException e) {
                logger.error("上传文件异常",e);
                upload=false;
            }finally {
                fileInputStream.close();
                ftpClient.disconnect();
            }
        }
        return upload;
    }

    public boolean uploadToFtp(String remotePath, String fileName, File file) throws IOException {
        boolean upload = true;
        // connect to ftpServer
        if (connectServer(ftpIp, ftpPort, ftpUser, ftpPass)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();

                // 上传文件 参数：上传后的文件名，输入流
                upload = ftpClient.storeFile(fileName, new FileInputStream(file));
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                upload = false;
            } finally {
                ftpClient.disconnect();
            }
        }
        return upload;
    }
}