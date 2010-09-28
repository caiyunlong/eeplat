package com.exedosoft.plat.action;

import java.util.List;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.bo.org.DOAuthorization;
import com.exedosoft.plat.bo.org.OrgParter;
import com.exedosoft.plat.bo.org.SessionParterFactory;
import com.exedosoft.plat.util.DOGlobals;

/**
 * 
 * ֧�ַ�����Ȩ���û���Ȩ������ �û�Ȩ�ޱ����˼·�������ж�һ���Ƿ���Ҫ����Ȩ�����ã��������Ҫ�����ء�
 * �����Ҫ�����������û��ϵ�Ȩ��ȫ��ȥ�������½������á�
 * 
 * @author IBM
 * 
 */

public class DOAuthUserSave extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2024127657008811808L;
	
	private static DOService setRoleExcel = DOService.getService("do.bx.role.update.xes.excel");

	
	private static DOService setRoleExcelNull = DOService.getService("do.bx.role.update.xes.excel.no");

	@Override
	public String excute() throws ExedoException {
		// TODO Auto-generated method stub

		String parterUid = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance().getValue("parterUid");
		if (parterUid == null) {
			return null;
		}
		
		
		///////////formModel��Ȩ������
		String fmConfigs = DOGlobals.getInstance().getSessoinContext()
		.getFormInstance().getValue("fmConfigs");
		
		
		
		
		///////////////////////////isExcel �Ƿ���Ե�����excel
		String isExcel = DOGlobals.getInstance().getSessoinContext()
		.getFormInstance().getValue("isExcel");
		if("5".equals(isExcel)){
			setRoleExcel.invokeUpdate(parterUid);
		}else{
			setRoleExcelNull.invokeUpdate(parterUid);
		}
///////////////////////end

		authMenu(parterUid);

		return null;
	}

	private void authMenu(String parterUid) {
		List allAuthMenus = SessionParterFactory.getSessionParter()
				.getMenuAuthConfigByAccount(parterUid);

		DOBO boUser = OrgParter.getDefaultEmployee().getDoBO();
		BOInstance user = boUser.getInstance(parterUid);

		System.out.println("Enter:::::::::::::DOAuthUserSave");

		if (user == null) {
			System.out.println("User is null!!!!!!!!!");
			return;
		}

		String authcofig = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance().getValue("authcofig");
		String[] menuConfigs = authcofig.split(";");

//		WFDAO dao = new WFDAO();
//		dao.setAutoClose(false);
		try {

			// /////////////////ɾ��Ȩ������
			for (int i = 0; i < menuConfigs.length; i++) {
				String[] aConfig = menuConfigs[i].split(",");
				if (aConfig[0] != null && !"".equals(aConfig[0])) {

					String dbAccess = "0";
					if (allAuthMenus.contains(aConfig[0])) {
						dbAccess = "1";
					}
					if (!dbAccess.equals(aConfig[1])) {
						DOAuthorization.removeDOAuthorizations(OrgParter
								.getDefaultEmployee(), user.getUid(),
								DOAuthorization.WHAT_UI_MENU, aConfig[0]);

					}

				}
			}

			// ////////////ɾ��֮���ټ���һ��Ȩ������
			allAuthMenus = SessionParterFactory.getSessionParter()
					.getMenuAuthConfigByAccount(parterUid);

			// //////////////////����Ȩ������

			for (int i = 0; i < menuConfigs.length; i++) {
				String[] aConfig = menuConfigs[i].split(",");
				String dbAccess = "0";
				if (allAuthMenus.contains(aConfig[0])) {
					dbAccess = "1";
				}
				if (!dbAccess.equals(aConfig[1])) {

					DOAuthorization da = new DOAuthorization();
					da.setParterUid(OrgParter.getDefaultEmployee().getObjUid());
					da.setOuUid(user.getUid());
					da.setWhatType(DOAuthorization.WHAT_UI_MENU);
					da.setWhatUid(aConfig[0]);
					if (("0".equals(aConfig[1]))) {
						da.setAuthority(Boolean.FALSE);
						da.setIsInherit(Boolean.TRUE);
					} else {
						da.setAuthority(Boolean.TRUE);
						da.setIsInherit(Boolean.TRUE);
					}
					DAOUtil.BUSI().store(da);

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	dao.closeSession();
	}

//	private void authAMenu(String isAccess, DOMenuModel dmm, WFDAO dao,
//			BOInstance user) throws DAOException {
//		if ((dmm.isAccess(user.getUid()) && "0".equals(isAccess))
//				|| (!dmm.isAccess(user.getUid()) && "1".equals(isAccess))) {
//
//			DOAuthorization.removeDOAuthorizations(OrgParter
//					.getDefaultEmployee(), user.getUid(),
//					DOAuthorization.WHAT_UI_MENU, dmm.getObjUid());
//
//			if ((dmm.isAccess(user.getUid()) && "0".equals(isAccess))
//					|| (!dmm.isAccess(user.getUid()) && "1".equals(isAccess))) {
//
//				DOAuthorization da = new DOAuthorization();
//				da.setParterUid(OrgParter.getDefaultEmployee().getObjUid());
//				da.setOuUid(user.getUid());
//				da.setWhatType(DOAuthorization.WHAT_UI_MENU);
//				da.setWhatUid(dmm.getObjUid());
//
//				if (("0".equals(isAccess))) {
//					da.setAuthority(Boolean.FALSE);
//				} else {
//					da.setAuthority(Boolean.TRUE);
//				}
//				dao.store(da);
//			}
//		}
//	}

}
