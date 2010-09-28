package com.exedosoft.plat.reporter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;

public class TimerAxis {

	private static Log log = LogFactory.getLog(TimerAxis.class);

	/**
	 * ��ȡʱ���ᣬ����һ���µ�����
	 * 
	 * @param calendar
	 * @param theYear
	 * @param theMonth
	 * @return
	 */
	public static String getMonthTimerAxis(Calendar calendar, String theYear,
			String theMonth) {

		if (calendar == null || theYear == null || theMonth == null) {
			return "����Ĳ�������Ϊ�գ�";

		}

		calendar.set(Calendar.YEAR, Integer.parseInt(theYear));
		
		calendar.set(Calendar.MONTH, Integer.parseInt(theMonth) - 1);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		
		
		// ///////////////������ж�����
		StringBuilder out = new StringBuilder();
		int daysofmonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int i = 1; i <= daysofmonth; i++) {
			out
					.append("<th  nowrap='nowrap'  align='center' sort='asc' title='");
			out.append(theMonth);
			out.append("��");
			out.append(i);
			out.append("��' ");

			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
					|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				out.append(" style='background:white'");
			}

			out.append(" >");
			// out.append("<a href='javascript:loadReporterPaneMine(");
			// out.append(i);
			// out.append(",");
			// out.append(theMonth);
			// out.append(",");
			// out.append(theYear);
			// out.append(")'>");
			// pane_gt_reporter_list_by_mine.jsp
			out.append(i);
			out.append("</th>");
			// </a>
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return out.toString();
	}

	/**
	 * ��ȡһ��ʱ���ᣬ��ȡһ��ʮ�����µ�����
	 * 
	 * @param theYear
	 * @return
	 */
	public static String getYearTimerAxis(String theYear) {

		StringBuilder out = new StringBuilder();
		for (int i = 1; i <= 12; i++) {
			out
					.append("<th  nowrap='nowrap'  align='center' sort='asc' title='");
			out.append(theYear);
			out.append("��");
			out.append(i);
			out.append("��' ");
			out.append(" >");

			// out.append("<a href='javascript:loadReporterPaneMine(");
			// out.append(i);
			// out.append(",");
			// out.append(i);
			// out.append(",");
			// out.append(theYear);
			// out.append(")'>");
			// pane_gt_reporter_list_by_mine.jsp
			out.append(i);
			out.append("��</th>");
			// </a>
		}

		return out.toString();

	}

	/**
	 * ʱ���� ���������ʾ
	 * 
	 * @param aMonth
	 * @param aYear
	 * @return
	 */
	public static String getLeftAlt(String aMonth, String aYear) {

		if (aMonth == null || aYear == null) {
			log.warn("WHEN Get Left ALT MONTH or YEAR MayBe NULL");
			return "";
		}
		int iMonth = Integer.parseInt(aMonth);
		int iYear = Integer.parseInt(aYear);

		iMonth = iMonth + 1;
		if (iMonth == 13) {
			iMonth = 1;
			iYear = iYear + 1;
		}

		if ("-1".equals(aMonth)) {
			iYear = iYear + 1;
		}

		StringBuilder sb = new StringBuilder().append(iYear).append("��");

		if (!"-1".equals(aMonth)) {
			sb.append(iMonth).append("��");
		}
		return sb.toString();
	}

	/**
	 * ʱ���� �ҹ�������ʾ
	 * 
	 * @param aMonth
	 * @param aYear
	 * @return
	 */
	public static String getRightAlt(String aMonth, String aYear) {

		if (aMonth == null || aYear == null) {
			log.warn("WHEN Get Right ALT MONTH or YEAR MayBe NULL");
			return "";
		}
		int iMonth = Integer.parseInt(aMonth);
		int iYear = Integer.parseInt(aYear);

		iMonth = iMonth - 1;
		if (iMonth == 0) {
			iMonth = 12;
			iYear = iYear - 1;
		}

		if ("-1".equals(aMonth)) {
			iYear = iYear - 1;
		}

		StringBuilder sb = new StringBuilder().append(iYear).append("��");

		if (!"-1".equals(aMonth)) {
			sb.append(iMonth).append("��");
		}
		return sb.toString();
	}

}
