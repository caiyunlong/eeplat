package com.exedosoft.plat.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.DOAccess;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.js.RunJs;

public class AccessUtil {

	private static HashMap authClasses = new HashMap();
	
	
	public static final int CONDITION_TYPE_DEFAULT = 0;
	
	public static final int CONDITION_TYPE_SCRIPT = 1;

	public static final int CONDITION_TYPE_CLASS = 2;
	
	

	

	private static Log log = LogFactory.getLog(AccessUtil.class);

	/**
	 * ��ʱ�����ַ���,��һ���ٿ���script.
	 * 
	 * @param conditionType
	 *            TODO
	 * 
	 * @return
	 */
	public static boolean isAccess(String condition, BOInstance aBI,
			int conditionType) {

		/**
		 * ��ʱ�����������aBIΪnull ֱ�ӷ���true. ��ִ�������class���߽ű���֤
		 */
		
		/**
		 * condition==null
		 */
		if (condition == null) {
			return true;
		}
		
		if(conditionType == CONDITION_TYPE_DEFAULT){
			return true;
		}

		if (conditionType == CONDITION_TYPE_CLASS) {
			return getClassAccess(condition, aBI);
		}  else {


			String isTrue = RunJs.evaluate(condition, aBI);
			if (isTrue != null && isTrue.equals("true")) {
				return true;
			} else {
				return false;
			}

		}

	}

	private static boolean getClassAccess(String accessClass, BOInstance aBI) {

		if (accessClass != null && !"".equals(accessClass.trim())) {
			DOAccess wfa = null;
			try {

				if (authClasses.get(accessClass) != null) {
					wfa = (DOAccess) authClasses.get(accessClass);
				} else {
					Class caClass = Class.forName(accessClass);
					wfa = (DOAccess) caClass.newInstance();
					authClasses.put(accessClass, wfa);
				}
				boolean ret = wfa.isAccess(aBI);
				return ret;

			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
				// /////////////////Ӧ����return false;
				return false;
			} catch (Exception ex1) {
				ex1.printStackTrace();
				return false;
			}
		}
		return false;

	}
}
