<%@ page contentType="text/html;charset=gb2312" %>
<%@ page language="java" import="java.io.*,java.sql.*,com.anolesoft.epiboly.smartdot.dc.*"%>
<%@ page language="java" import="com.exedosoft.plat.bo.DODataSource" %>
<jsp:useBean id="mySmartUpload" scope="page" class="com.anolesoft.epiboly.smartdot.dc.SmartUpload" />
<%!
//�����Ҫ�޸ĳ��������ݿ�������Ϣ



public Connection conn = null; 

public String docid = "";
public String filename = "";
public int filesize = 0;
%>
<%

System.out.println("fdsfdsfdsfdsfdsf");
	// Initialization
 	mySmartUpload.initialize(pageContext);
	// Upload
	mySmartUpload.upload();
	
	//ȡ��ע���Ա����ļ�
	//mySmartUpload.save("/jspdemo/upload"); 	
	//��DBMS�������� 
	try
	{ 
		conn = DODataSource.getDefaultCon(); 			
	} 
	catch(Exception ex)
	{ 
		System.err.println("aq.executeQuery: " + ex.getMessage()); 
	}  
	
	SmartFile myFile = null;
	
	docid = mySmartUpload.getRequest().getParameter("docid");
	System.out.println("docid="+docid);	
	
	String afileName = mySmartUpload.getRequest().getParameter("EDITFILE");
	System.out.println("fileName==" + afileName);
	System.out.println("fileName==" + afileName);
	System.out.println("fileName==" + afileName);
	System.out.println("fileName==" + afileName);
	System.out.println("fileName==" + afileName);
	System.out.println("fileName==" + afileName);
	System.out.println("fileName==" + afileName);
	
	for (int i=0;i<mySmartUpload.getFiles().getCount();i++)
	{
		myFile = mySmartUpload.getFiles().getFile(i);
		//debug only
		System.out.println("File=" + myFile.getFileName());  		
  		//�������ݼ��ļ�
  		if (!myFile.isMissing())
	 	{	 		
	 		filename = myFile.getFileName();
	 		filesize = myFile.getSize();	 		 		
	 		if(myFile.getFieldName().equalsIgnoreCase("EDITFILE") )	 //�����ļ�
	 		{
	 			System.out.println("���������ļ�"); 
				System.out.println("filename="+filename);
				System.out.println("filesize="+filesize);
				System.out.println("myFile.getFieldName()="+myFile.getFieldName());			
		 		myFile.saveAs(filename,mySmartUpload.SAVE_PHYSICAL);	 		
				java.io.File tfile = new java.io.File(filename);
				java.io.InputStream inStream=new java.io.FileInputStream(tfile);				
		 		try
				{
				    String strSql = "";
		 			if ( (docid == null)||(docid.length() == 0) ) // new doc
		 			{		 				 					 						
						strSql="insert into MyUploadTable(filename,filesize,filedata) values(?,?,?)";
						                   	                	
					}									
		 			else // edit doc
		 			{
		 			    //iCurDocID = Integer.parseInt(docid);
		 			    strSql="update MyUploadTable set filename=?,filesize=?,filedata=? where id="+docid;
		 				
		 			}
		 	
		 			PreparedStatement ps = conn.prepareStatement(strSql);
					ps.setString(1, filename);
					ps.setInt(2, filesize);                		
            		ps.setBinaryStream(3,inStream,inStream.available());
                    ps.executeUpdate();
                    ps.close();  
		 			out.println("����ɹ���<br>");
		 			out.println("���߱༭���ļ�: " + filename + "<br>");
					out.println("��С: " + filesize + " bytes<br>");
		 		}
		 		catch(Exception e)
				{
					out.println("��������: " + e.toString());				
				}
				inStream.close();
				//tfile.delete();			
			}
			else //�����ļ�
			{
				try
				{
					System.out.println("�������ļ�");
					System.out.println("filename="+filename);
					System.out.println("filesize="+filesize);
					System.out.println("myFile.getFieldName()="+myFile.getFieldName());
					out.println("�������ļ�: " + filename + "<br>");
					out.println("��С: " + filesize + " bytes<br>");
		 		}
		 		catch(Exception e)
				{
					out.println("��������: " + e.toString());				
				}			
			}					
	 	}  // end if (!myFile.isMissing())
	}// end for 
	conn.close();
%>

		
