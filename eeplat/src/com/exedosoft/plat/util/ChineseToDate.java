package com.exedosoft.plat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;


/**
 * for����. ��һ��ֻ֧��28-����-2008 ���ָ�ʽ�� ���� java.sql.Date
 */
public class ChineseToDate {

	static String[] chinese = { "һ", "��", "��", "��", "��", "��", "��", "��", "��",
			"ʮ" };

	private static SimpleDateFormat dateFormat = null;
	static {
		// ָ�����ڸ�ʽΪ��λ��/��λ�·�/��λ���ڣ�ע��dd-MM-yyyy���ִ�Сд��
		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		// ����lenientΪfalse.
		// ����SimpleDateFormat��ȽϿ��ɵ���֤���ڣ�����2007/02/29�ᱻ���ܣ���ת����2007/03/01
		dateFormat.setLenient(false);
	}

	/**
	 * for����. ��һ��ֻ֧������ 28-����-2008 ���ָ�ʽ�� ���� java.sql.Date
	 * @throws ParseException 

	 */
	public static java.sql.Date getChineseToSqlDate(String s) throws ParseException
			 {

		// ����ȥ��
		s = s.replaceAll("��", "");
		s = s.replaceAll("ʮһ", "11");
		s = s.replaceAll("ʮ��", "12");
		for (int i = 0; i < chinese.length; i++) {
			s = s.replaceAll(chinese[i], String.valueOf(i + 1));
		}
		java.util.Date d = dateFormat.parse(s);
		return new java.sql.Date(d.getTime());
	}

	/**
	 * @param args
	 * @throws ParseException 
	 */

	public static void main(String[] args) throws ParseException {
		// TODO �Զ����ɷ������
		//System.out.println(ChineseToDate.getChineseToSqlDate("28-����-2008"));
		
		HashMap t = new HashMap();
		t.put("a", null);

	}

}
