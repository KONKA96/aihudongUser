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
  //�����ļ�����ʱ�����ļ�������ķ�ʽ����  
  //����response.reset()���������еģ�>���治Ҫ���У��������һ����  
  
  response.reset();//���Լ�Ҳ���Բ���  
  response.setContentType("application/x-download");  
//application.getRealPath("/main/mvplayer/CapSetup.msi");��ȡ������·��  
  
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
//Ҫ���������仰������ᱨ��  
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
//���ﲻ�ܹر�    
//if(outp != null)  
  //{  
  //outp.close();  
  //outp = null;  
  //}  
  }  
%>
</body>
</html>