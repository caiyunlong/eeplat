package com.exedosoft.plat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.bo.org.OrgParter;
import com.exedosoft.plat.bo.org.OrgParterValue;
import com.exedosoft.plat.bo.org.SessionParter;
import com.exedosoft.plat.util.DOGlobals;

public class SessionParterAbp implements SessionParter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 222L;

	public List getParterAuths() {

		String accountUid = DOGlobals.getInstance().getSessoinContext()
				.getUser().getUid();

		return this.getParterAuths(accountUid);
	}

	public List getParterAuths(String accountUid) {

		// //������û��ͽ�ɫ����parter Ȩ�޹���

		List allAuths = new ArrayList();
		// //�����û�
		OrgParter userPater = OrgParter.getDefaultEmployee();
		OrgParterValue pv = new OrgParterValue(userPater, accountUid);
		allAuths.add(pv);

		// ////////�����ɫ
		OrgParter roleParter = OrgParter.getDefaultRole();
		appendRoles(allAuths, accountUid, roleParter);

		return allAuths;
	}

	/**
	 * 
	 * @param allAuths
	 * @param accountUid
	 * @param parter
	 */

	private void appendRoles(List allAuths, String accountUid, OrgParter parter) {

		if (parter.isRole()) {
			DOService findUserService = DOService
					.getService("do.bx.role.findbyuseridcontext");
			List listRoles = findUserService.invokeSelect(accountUid);
			if (listRoles != null) {
				for (Iterator roles = listRoles.iterator(); roles.hasNext();) {
					BOInstance boRole = (BOInstance) roles.next();
					OrgParterValue pv = new OrgParterValue(parter, boRole
							.getUid(), boRole.getName());
					allAuths.add(pv);
				}
			}
		}
	}

	public List getMenuAuthConfigByAccount(String accountUid) {

		// //////////�Ȼ�ȡ��ɫ�˵���Ȩ��

		List pureMenuUids = new ArrayList();
		DOService rfService = DOService
				.getService("do.bx.role.findbyuserid.xes.ids_xes");
		List rfList = rfService.invokeSelect(accountUid);

		for (Iterator it = rfList.iterator(); it.hasNext();) {
			BOInstance bi = (BOInstance) it.next();
			pureMenuUids.add(bi.getValue("whatuid"));
		}
		System.out.println("List::::::" + rfList);
		
		
		

		// ////////////////�û�������Ȩ��Ҫ�Ƴ���Ȩ��
//		DOService fxService = DOService
//				.getService("auth.menu.user.access.0.xes");
//		List fxList = fxService.invokeSelect(accountUid);
//		for (Iterator it = fxList.iterator(); it.hasNext();) {
//			BOInstance bi = (BOInstance) it.next();
//			pureMenuUids.remove(bi.getValue("whatuid"));
//		}
		// //////////////�ټ����û�������Ȩ��Ȩ��
//		DOService aService = DOService
//				.getService("auth.menu.user.access.1.xes");
//		List list = aService.invokeSelect(accountUid);
//		for (Iterator it = list.iterator(); it.hasNext();) {
//			BOInstance bi = (BOInstance) it.next();
//			pureMenuUids.add(bi.getValue("whatuid"));
//		}
		return pureMenuUids;
	}

	public List getMenuAuthConfigByRole(String roleUid) {
		return null;

//		List pureMenuUids = new ArrayList();
//		DOService rfService = DOService
//				.getService("do.bx.role.findbyroleid.xes.ids");
//		List rfList = rfService.invokeSelect(roleUid);
//
//		for (Iterator it = rfList.iterator(); it.hasNext();) {
//			BOInstance bi = (BOInstance) it.next();
//			pureMenuUids.add(bi.getValue("whatuid"));
//		}
//		return pureMenuUids;
	}

	public List getFormAuthConfigByAccount(String countUid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getFormAuthConfigByRole(String roleUid) {
		return null;
//
//		List pureMenuUids = new ArrayList();
//		DOService rfService = DOService
//				.getService("do.bx.role.findbyroleid.xes.ids.formmodels");
//		List rfList = rfService.invokeSelect(roleUid);
//
//		for (Iterator it = rfList.iterator(); it.hasNext();) {
//			BOInstance bi = (BOInstance) it.next();
//			pureMenuUids.add(bi.getValue("whatuid"));
//		}
//		return pureMenuUids;
	}

	public List getBIAuthConfigByAccount(String countUid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getBIAuthConfigByRole(String roleUid) {
		return null;
//		List pureMenuUids = new ArrayList();
//		DOService rfService = DOService
//				.getService("do.bx.role.findbyroleid.xes.ids.bis");
//		List rfList = rfService.invokeSelect(roleUid);
//
//		for (Iterator it = rfList.iterator(); it.hasNext();) {
//			BOInstance bi = (BOInstance) it.next();
//			pureMenuUids.add(bi.getValue("wheredobo") + bi.getValue("whatuid"));
//		}
//		return pureMenuUids;
	}

	public List getWfNIAuthConfigByAccount(String countUid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getWfNIAuthConfigByRole(String roleUid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getWfNodeAuthConfigByAccount(String countUid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getWfNodeAuthConfigByRole(String roleUid) {
		// TODO Auto-generated method stub
		return null;
	}
}
