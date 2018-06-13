<%@ page language="java" contentType="application/x-download"
    pageEncoding="gb2312"%>
<%@page import="java.net.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%  
  //关于文件下载时采用文件流输出的方式处理：  
  //加上response.reset()，并且所有的％>后面不要换行，包括最后一个；  
  
  response.reset();//可以加也可以不加  
  response.setContentType("application/x-download");  
//application.getRealPath("/main/mvplayer/CapSetup.msi");获取的物理路径  
  
//String filedownload = request.getSession().getServletContext().getRealPath("/")+"/upload";
String filedownload = null;
String filedisplay = null;
	try{
		String path = request.getServletContext().getRealPath("/");
		File file = new File(path+"/upload");
		File[] fileList = file.listFiles();
		List<String> fileVersion = new ArrayList<>();
		for (File file2 : fileList) {
			String[] split = file2.getName().split("-");
			if(split.length>=3) {
				fileVersion.add(split[1]);
			}
		}
		Collections.sort(fileVersion);
		String encode =null;
		for (File file2 : fileList) {
			if(file2.getName().contains(fileVersion.get(fileVersion.size()-1))) {
				String[] split = file2.getName().split("-");
				filedownload = file2.toString(); 
				filedisplay = file2.getName();
			}
		}
		
		filedisplay = URLEncoder.encode(filedisplay,"UTF-8");  
		response.addHeader("Content-Disposition","attachment;filename=" + filedisplay); 
	}catch(Exception e)  {
		System.out.println("Error!");  
		  e.printStackTrace();
		  request.getRequestDispatcher("/index/error").forward(request, response);
	}
  java.io.OutputStream outp = null;  
  java.io.FileInputStream in = null;  
  try  
  {  
  outp = response.getOutputStream();  
  in = new FileInputStream(filedownload);  
  
  byte[] b = new byte[1024];  
  int i = 0;  
  
  while((i = in.read(b)) > 0)  
  {  
  outp.write(b, 0, i);  
  }  
//    
outp.flush();  
//要加以下两句话，否则会报错  
//java.lang.IllegalStateException: getOutputStream() has already been called for //this response    
out.clear();  
out = pageContext.pushBody();  
}  
  catch(Exception e)  
  {  
  System.out.println("Error!");  
  e.printStackTrace();  
  }  
  finally  
  {  
  if(in != null)  
  {  
  in.close();  
  in = null;  
  }  
//这里不能关闭    
//if(outp != null)  
  //{  
  //outp.close();  
  //outp = null;  
  //}  
  }  
%>
</body>
</html>