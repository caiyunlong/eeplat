package com.exedosoft.plat.login;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOService;

/**
 * 
 * ֧�ַ�����Ȩ���û���Ȩ������ �û�Ȩ�ޱ����˼·�������ж�һ���Ƿ���Ҫ����Ȩ�����ã��������Ҫ�����ء�
 * �����Ҫ�����������û��ϵ�Ȩ��ȫ��ȥ�������½������á�
 * 
 * @author IBM
 * 
 */
public class LoginDelegateList extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10111112323L;

	@Override
	public String excute() throws ExedoException {
		// TODO Auto-generated method stub

		List<BOInstance> rets = getDelegates();
		this.setInstances(rets);
		return this.DEFAULT_FORWARD;
	}

	public  static List<BOInstance> getDelegates() {
		
		DOBO boUser = DOBO.getDOBOByName("do_org_user");
		
		List<BOInstance> rets = new ArrayList<BOInstance>();
		DOService dosDelegate = DOService
				.getService("do_org_user_delegate_findbydelegateUid");
		List<BOInstance> delegates = dosDelegate.invokeSelect();
		boolean isDelegate = false;
		Date nowTime = new Date(System.currentTimeMillis());

		if (delegates != null && delegates.size() > 0) {
			for (Iterator<BOInstance> it = delegates.iterator(); it.hasNext();) {
				BOInstance bi = it.next();
				if (bi.getValue("starttime") == null
						&& bi.getValue("endtime") == null) {
					isDelegate = true;
					rets.add(boUser.getInstance(bi.getValue("user_uid")));
				} else if (bi.getValue("starttime") != null
						&& bi.getValue("endtime") != null) {
					Date startTime = bi.getDateValue("starttime");
					Date endTime = bi.getDateValue("endtime");
					if (startTime.before(nowTime) && endTime.after(nowTime)) {
						isDelegate = true;
						rets.add(boUser.getInstance(bi.getValue("user_uid")));
					}
				} else if (bi.getValue("starttime") != null) {
					Date startTime = bi.getDateValue("starttime");
					if (startTime.before(nowTime)) {
						isDelegate = true;
						rets.add(boUser.getInstance(bi.getValue("user_uid")));
					}
				} else if (bi.getValue("endtime") != null) {
					Date endTime = bi.getDateValue("endtime");
					if (endTime.after(nowTime)) {
						isDelegate = true;
						rets.add(boUser.getInstance(bi.getValue("user_uid")));
					}
				}
			}
		}
		return rets;
	}

	public static boolean isDelegate() {
		DOService dosDelegate = DOService
				.getService("do_org_user_delegate_findbydelegateUid");
		if(dosDelegate==null){
			return false;
		}
		List<BOInstance> delegates = dosDelegate.invokeSelect();
		boolean isDelegate = false;
		Date nowTime = new Date(System.currentTimeMillis());

		if (delegates != null && delegates.size() > 0) {
			for (Iterator<BOInstance> it = delegates.iterator(); it.hasNext();) {
				BOInstance bi = it.next();
				if (bi.getValue("starttime") == null
						&& bi.getValue("endtime") == null) {
					isDelegate = true;
					break;
				} else if (bi.getValue("starttime") != null
						&& bi.getValue("endtime") != null) {
					Date startTime = bi.getDateValue("starttime");
					Date endTime = bi.getDateValue("endtime");
					if (startTime.before(nowTime) && endTime.after(nowTime)) {
						isDelegate = true;
						break;
					}
				} else if (bi.getValue("starttime") != null) {
					Date startTime = bi.getDateValue("starttime");
					if (startTime.before(nowTime)) {
						isDelegate = true;
						break;
					}
				} else if (bi.getValue("endtime") != null) {
					Date endTime = bi.getDateValue("endtime");
					if (endTime.after(nowTime)) {
						isDelegate = true;
						break;
					}
				}
			}
		}
		return isDelegate;
	}

}
