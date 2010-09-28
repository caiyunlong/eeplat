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
//import com.exedosoft.plat.dao.DAOException;
//import com.exedosoft.plat.dao.WFDAO;
import com.exedosoft.plat.util.DOGlobals;

/**
 * 
 * ֧�ַ�����Ȩ���û���Ȩ������ �û�Ȩ�ޱ����˼·�������ж�һ���Ƿ���Ҫ����Ȩ�����ã��������Ҫ�����ء�
 * �����Ҫ�����������û��ϵ�Ȩ��ȫ��ȥ�������½������á�
 * 
 * @author IBM
 * 
 */

public class DOAuthRoleSave extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 101111111111L;

	private static DOService setRoleExcel = DOService
			.getService("do.bx.role.update.xes.excel");

	private static DOService setRoleExcelNull = DOService
			.getService("do.bx.role.update.xes.excel.no");

	@Override
	public String excute() throws ExedoException {
		// TODO Auto-generated method stub

		String parterUid = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance().getValue("parterUid");
		if (parterUid == null) {
			return null;
		}

		// /////////////////////////isExcel �Ƿ���Ե�����excel
		String isExcel = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance().getValue("isExcel");
		if ("5".equals(isExcel)) {
			setRoleExcel.invokeUpdate(parterUid);
		} else {
			setRoleExcelNull.invokeUpdate(parterUid);
		}
		// /////////////////////end

		// /////////formModel��Ȩ������

		boInstanceAuth(parterUid, "tbclassxuequ");

		boInstanceAuth(parterUid, "tbgrade");

		boInstanceAuth(parterUid, "tbkemu");

		// tbclassxuequ
		formAuth(parterUid);

		menuAuth(parterUid);

		return null;
	}

	/**
	 * ���û������ݵ�Ȩ�ޣ�xes ��������ģ��꼶 ����Ŀ
	 * 
	 * @param parterUid
	 */
	private void boInstanceAuth(String parterUid, String boName) {

		List allAuthBIs = SessionParterFactory.getSessionParter()
				.getBIAuthConfigByRole(parterUid);
		DOBO boRole = OrgParter.getDefaultRole().getDoBO();
		BOInstance role = boRole.getInstance(parterUid);

		if (role == null) {
			System.out.println("Role is null!!!!!!!!!");
			return;
		}

		System.out.println("allAuthBIs:::" + allAuthBIs);

		String fmConfigs = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance().getValue(boName);

		DOBO authBO = DOBO.getDOBOByName(boName);

		if (fmConfigs == null) {
			return;
		}

		String[] fConfigs = fmConfigs.split(";");
//
//		WFDAO dao = new WFDAO();
//		dao.setAutoClose(false);
		try {
			for (int i = 0; i < fConfigs.length; i++) {
				String[] aConfig = fConfigs[i].split(",");
				if (aConfig[0] != null && !"".equals(aConfig[0])) {

					String dbAccess = "0";
					if (allAuthBIs != null
							&& allAuthBIs.contains(authBO.getObjUid()
									+ aConfig[0])) {
						dbAccess = "1";
					}
					if (aConfig[0] != null) {
						addAuth(aConfig[1], dbAccess, aConfig[0],  role,
								DOAuthorization.WHAT_BOINSTANCE, authBO
										.getObjUid());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		dao.closeSession();

	}

	/**
	 * ���ð�ť��Ȩ�� ,������excel?
	 * 
	 * @param parterUid
	 */

	private void formAuth(String parterUid) {

		List allAuthForms = SessionParterFactory.getSessionParter()
				.getFormAuthConfigByRole(parterUid);
		DOBO boRole = OrgParter.getDefaultRole().getDoBO();
		BOInstance role = boRole.getInstance(parterUid);

		if (role == null) {
			System.out.println("Role is null!!!!!!!!!");
			return;
		}

		String fmConfigs = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance().getValue("fmConfigs");
		String[] fConfigs = fmConfigs.split(";");
//
//		WFDAO dao = new WFDAO();
//		dao.setAutoClose(false);
		try {
			for (int i = 0; i < fConfigs.length; i++) {
				String[] aConfig = fConfigs[i].split(",");
				if (aConfig[0] != null && !"".equals(aConfig[0])) {

					String dbAccess = "0";
					if (allAuthForms != null
							&& allAuthForms.contains(aConfig[0])) {
						dbAccess = "1";
					}
					if (aConfig[0] != null) {
						addAuth(aConfig[1], dbAccess, aConfig[0],  role,
								DOAuthorization.WHAT_UI_FORM, null);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		dao.closeSession();
	}

	/**
	 * ���ò˵���Ȩ��
	 * 
	 * @param parterUid
	 */
	private void menuAuth(String parterUid) {
		List allAuthMenus = SessionParterFactory.getSessionParter()
				.getMenuAuthConfigByRole(parterUid);
		DOBO boRole = OrgParter.getDefaultRole().getDoBO();
		BOInstance role = boRole.getInstance(parterUid);

		System.out.println("Enter:::::::::::::DOAuthRoleSave");

		if (role == null) {
			System.out.println("User is null!!!!!!!!!");
			return;
		}

		String authcofig = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance().getValue("authcofig");
		String[] menuConfigs = authcofig.split(";");

//		WFDAO dao = new WFDAO();
//		dao.setAutoClose(false);
		try {
			for (int i = 0; i < menuConfigs.length; i++) {
				String[] aConfig = menuConfigs[i].split(",");
				if (aConfig[0] != null && !"".equals(aConfig[0])) {

					String dbAccess = "0";
					if (allAuthMenus != null
							&& allAuthMenus.contains(aConfig[0])) {
						dbAccess = "1";
					}
					if (aConfig[0] != null) {
						addAuth(aConfig[1], dbAccess, aConfig[0],  role,
								DOAuthorization.WHAT_UI_MENU, null);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		dao.closeSession();
	}

	// /////////��ɫ��������Ȩ��ֻ����Ա��������Ȩ
	private void addAuth(String uiAccess, String dbAccess, String dmmUid,
			 BOInstance role, int type, String whereDOBO) throws ExedoException
			 {
		if (!uiAccess.equals(dbAccess)) {

			System.out
					.println("22222222222222222222222222222222222222222222222222222");
			// DOAuthorization.removeDOAuthorizations(OrgParter.getDefaultRole(),
			// role.getUid(), type, dmmUid);
			DOAuthorization.removeDOAuthorizations(OrgParter.getDefaultRole(),
					role.getUid(), type, dmmUid, whereDOBO, null);

			if ("1".equals(uiAccess)) {
				DOAuthorization da = new DOAuthorization();
				da.setParterUid(OrgParter.getDefaultRole().getObjUid());
				da.setOuUid(role.getUid());
				da.setWhatType(type);
				da.setWhatUid(dmmUid);
				da.setWhereDOBO(whereDOBO);
				da.setAuthority(Boolean.TRUE);
				da.setIsInherit(Boolean.TRUE);
				DAOUtil.BUSI().store(da);
			}
		}
	}

	public static void main(String[] args) {

		String a = "402881c01be33416011be360f8d10007cc";
		System.out.println(a.substring(32));

	}

	// // /////////��ɫ��������Ȩ��ֻ����Ա��������Ȩ
	// private void authAForm(String uiAccess, String dbAccess, String dmmUid,
	// WFDAO dao, BOInstance role) throws DAOException {
	// if (!uiAccess.equals(dbAccess)) {
	//
	// DOAuthorization.removeDOAuthorizations(OrgParter.getDefaultRole(),
	// role.getUid(), DOAuthorization.WHAT_UI_FORM, dmmUid);
	// if ("1".equals(uiAccess)) {
	// DOAuthorization da = new DOAuthorization();
	// da.setParterUid(OrgParter.getDefaultRole().getObjUid());
	// da.setOuUid(role.getUid());
	// da.setWhatType(DOAuthorization.WHAT_UI_FORM);
	// da.setWhatUid(dmmUid);
	// da.setAuthority(Boolean.TRUE);
	// da.setIsInherit(Boolean.TRUE);
	// dao.store(da);
	// }
	// }
	// }
	//	
	//	
	// // /////////��ɫ��������Ȩ��ֻ����Ա��������Ȩ
	// private void authABOInstance(String uiAccess, String dbAccess, String
	// dmmUid,
	// WFDAO dao, BOInstance role) throws DAOException {
	// if (!uiAccess.equals(dbAccess)) {
	//
	// DOAuthorization.removeDOAuthorizations(OrgParter.getDefaultRole(),
	// role.getUid(), DOAuthorization.WHAT_BOINSTANCE, dmmUid);
	// if ("1".equals(uiAccess)) {
	// DOAuthorization da = new DOAuthorization();
	// da.setParterUid(OrgParter.getDefaultRole().getObjUid());
	// da.setOuUid(role.getUid());
	// da.setWhatType(DOAuthorization.WHAT_BOINSTANCE);
	// da.setWhatUid(dmmUid);
	// da.setAuthority(Boolean.TRUE);
	// da.setIsInherit(Boolean.TRUE);
	// dao.store(da);
	// }
	// }
	// }

}
