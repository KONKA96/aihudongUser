package com.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Component
public class DownFile {
	public static void downloadSftpFile(String ftpHost, String ftpUserName,  
            String ftpPassword, int ftpPort, String ftpPath, String localPath,  
            String fileName) throws JSchException {  
        Session session = null;  
        Channel channel = null;  
  
        JSch jsch = new JSch();  
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort);  
        session.setPassword(ftpPassword);  
        session.setTimeout(100000);  
        Properties config = new Properties();  
        config.put("StrictHostKeyChecking", "no");  
        session.setConfig(config);  
        session.connect();  
  
        channel = session.openChannel("sftp");  
        channel.connect();  
        ChannelSftp chSftp = (ChannelSftp) channel;  
  
        String ftpFilePath = ftpPath + "/" + fileName;  
        String localFilePath = localPath + File.separatorChar + fileName;  
  
        try {  
            chSftp.get(ftpFilePath, localPath);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            chSftp.quit();  
            channel.disconnect();  
            session.disconnect();  
        }  
  
    }  
	
	  
	    private static void closeChannel(Channel channel) {  
	        if (channel != null) {  
	            if (channel.isConnected()) {  
	                channel.disconnect();  
	            }  
	        }  
	    }  
	  
	    private static void closeSession(Session session) {  
	        if (session != null) {  
	            if (session.isConnected()) {  
	                session.disconnect();  
	            }  
	        }  
	    }
}
