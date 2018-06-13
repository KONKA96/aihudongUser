package com.test;  
  
import java.io.*;  
  
/** 
 * 文件对象 
 * @author wzztestin 
 * 
 */  
public class DownFileAccess implements Serializable {  
    /** 
     *  
     */  
    private static final long serialVersionUID = -2518013155676212866L;  
    //写入文件的流  
    RandomAccessFile oSavedFile;  
    //开始位置  
    long nPos;  
    boolean bFirst;  
  
    public DownFileAccess() throws IOException {  
        this("", 0,true);  
    }  
      
    /** 
     * 写入文件初始化 
     * @param sName 
     * @param nPos 
     * @throws IOException 
     */  
    public DownFileAccess(String sName, long nPos,boolean bFirst) throws IOException {  
        File wfile = new File(sName);  
        oSavedFile = new RandomAccessFile(wfile,"rw");  
        if(!bFirst){  
            oSavedFile.seek(wfile.length());  
        }  
        this.nPos = nPos;  
        this.bFirst = bFirst;  
    }  
      
    /** 
     * 写文件 
     * @param b 
     * @param nStart 
     * @param nLen 
     * @return 
     */  
    public synchronized int write(byte[] b, int nStart, int nLen) {  
        int n = -1;  
        try {  
            oSavedFile.write(b, nStart, nLen);  
            n = nLen;  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return n;  
    }  
}