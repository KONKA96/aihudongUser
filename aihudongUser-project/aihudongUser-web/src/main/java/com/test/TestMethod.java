package com.test;

import java.io.File;

public class TestMethod {  
    public TestMethod() {  
        try {  
           /* DownFileInfoBean bean = new DownFileInfoBean(  
                    "http://cdn.market.hiapk.com/data/upload//2012/09_27/17/car.wu.wei.kyo.shandian_174928.apk", "D:\\temp",  
                    "shandian_174928.apk", 5,true,null); */ 
            File file = new File("D:\\AsjClient-1.0.1-2018-03-27-10-30-08.exe"); 
            DownFileInfoBean bean = new DownFileInfoBean(null, "C:\\Intel", 
                    "AsjClient-1.0.1-2018-03-27-10-30-08.exe", 5,false,file);
            DownFileFetch fileFetch = new DownFileFetch(bean);  
            fileFetch.start();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static void main(String[] args) {  
        new TestMethod();  
    }  
}