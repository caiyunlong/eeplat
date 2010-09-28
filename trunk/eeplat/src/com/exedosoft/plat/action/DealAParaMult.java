package com.exedosoft.plat.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0 
 */

public class DealAParaMult extends DOAbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4798265805984034747L;
	private static Log log = LogFactory.getLog(DealAParaMult.class);

	public DealAParaMult() {
	}

	public String excute()  {

		if (this.service==null || this.service.getTempSql() == null) {
			System.out.println("δ����SQL ���");
			this.setEchoValue("δ����SQL ���");
		}


		String[] checks = this.actionForm.getValueArray("checkinstance");
		if (checks == null) {
			System.out.println("û������");
			this.setEchoValue("û������");
			return NO_FORWARD;
		}
		
		try {
			this.service.beginBatch();
			for(int i = 0; i < checks.length ; i++){
				Map aMap = new HashMap();
				aMap.put(this.service.getBo().getKeyCol(), checks[i]);
				this.service.addBatch(aMap);			
			}
			this.service.endBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DEFAULT_FORWARD;
	}

}