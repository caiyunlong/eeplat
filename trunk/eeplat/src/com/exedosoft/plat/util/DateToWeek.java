package com.exedosoft.plat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateToWeek {

	public static void main(String[] args) {
		SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatD = new SimpleDateFormat("E");
		Date date = null;
		String aa = "2008-08-14";
		try {
			date = formatYMD.parse(aa);
			// ��String ת��Ϊ���ϸ�ʽ������
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(formatD.format(date));
		
		
		Date d=new Date();   
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
        System.out.println("��������ڣ�"+df.format(d));   
        System.out.println("����ǰ�����ڣ�" + df.format(new Date(d.getTime() - 2 * 24 * 60 * 60 * 1000)));   
        System.out.println("���������ڣ�" + df.format(new Date(d.getTime() + 3 * 24 * 60 * 60 * 1000)));
        
        Calendar calendar=Calendar.getInstance();   
        calendar.setTime(new Date());
         System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//��������� 
         calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);//�����ڼ�1   
         System.out.println(calendar.get(Calendar.DATE));//��1֮�������Top
	}
	
}


