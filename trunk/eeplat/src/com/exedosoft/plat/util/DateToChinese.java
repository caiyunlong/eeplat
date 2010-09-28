/**
 * 
 */
package com.exedosoft.plat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lizonghai
 *
 */
public class DateToChinese {

	static String[] chinese={"��","һ","��","��","��","��","��","��","��","��"};
	static String[] len={"ʮ"};
	static String[] ydm={"��","��","��"};
	
	public static String getDateToChinese(String s)
	{
		int sleng=s.length();
		String result="";
		for(int i=0;i<sleng;i++)
		{
			result+=chinese[Integer.parseInt(s.substring(i, i+1))];
		}
		return result; 
	}
	
	
	private static SimpleDateFormat dateFormat = null; 
	static 
	{ 
//	 ָ�����ڸ�ʽΪ��λ��/��λ�·�/��λ���ڣ�ע��yyyy/MM/dd���ִ�Сд�� 
	dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
//	 ����lenientΪfalse. ����SimpleDateFormat��ȽϿ��ɵ���֤���ڣ�����2007/02/29�ᱻ���ܣ���ת����2007/03/01 
	dateFormat.setLenient(false); 
	} 

	public static boolean isValidDate(String s) 
	{ 
	try 
	{ 
	dateFormat.parse(s); 
	return true; 
	} 
	catch (Exception e) 
	{ 
//	 ���throw java.text.ParseException����NullPointerException����˵����ʽ���� 
	return false; 
	} 
	} 
 
//	 ���������������Խ�һ�����ڰ�����ָ���ĸ�ʽ��� 
	public static String formatDate(Date d) 
	{ 
	return dateFormat.format(d); 
	} 

	//������յ����ڽ��
	public static String baoDateToChinese(String s)
	{
		String toresult="";
		String d[]=s.split(" ");
		s=d[0];
		boolean b=isValidDate(s);
		if(b)
		{
			String c[]=s.split("-");
			for(int i=0;i<c.length;i++)
			{
				toresult+=n2c(c[i])+ydm[i];
			}
			
		}
		else
		{
			toresult="����������ڸ�ʽ����ȷ,��ȷ����������ڸ�ʽΪyyyy-mm-dd,��2008-02-26";
		}
		return toresult;
	}
	
	//������յ����ڽ��
	public static String DateToChineseWithNum(String s)
	{
		String toresult="";
		String d[]=s.split(" ");
		s=d[0];
		boolean b=isValidDate(s);
		if(b)
		{
			String c[]=s.split("-");
			for(int i=0;i<c.length;i++)
			{
				toresult+=c[i]+ydm[i];
			}
			
		}
		else
		{
			toresult="����������ڸ�ʽ����ȷ,��ȷ����������ڸ�ʽΪyyyy-mm-dd,��2008-02-26";
		}
		return toresult;
	}
	
	public static String getCurrentDayStr() {

		return DateToChineseWithNum(String.format("%1$tY-%1$tm-%1$td", Calendar.getInstance()));
	}
	
	public static String n2c(String s)
	{
	    if(s.length()==2)
	    {  
	         if("1".equals(s.substring(0,1)))
	         {
	            if("0".equals(s.substring(1,2)))return len[0];
	            return len[0]+chinese[Integer.parseInt(s.substring(1, 2))];
	          }
	         if("0".equals(s.substring(0,1)))
	        	 return chinese[Integer.parseInt(s.substring(1, 2))];
	     if("0".equals(s.substring(1, 2)))return chinese[Integer.parseInt(s.substring(0, 1))]+len[0];
	        return chinese[Integer.parseInt(s.substring(0, 1))]+len[0]+chinese[Integer.parseInt(s.substring(1, 2))];
	     }
		return getDateToChinese(s);
	}
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO �Զ����ɷ������

		//System.out.print(DateToChinese.getDateToChinese("174923"));
		System.out.println(DateToChinese.baoDateToChinese("2008-12-23 "));
		System.out.println("��һ�ָ�ʽ");
		System.out.println(DateToChinese.DateToChineseWithNum("2008-12-23 "));
		Date date=new Date();
		System.out.println(date.toLocaleString());
		System.out.println(StringUtil.getCurrentDayStr());
		System.out.println(getCurrentDayStr());
		

	}

}
