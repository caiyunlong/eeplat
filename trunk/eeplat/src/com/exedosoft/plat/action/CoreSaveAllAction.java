package com.exedosoft.plat.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.ActionFactory;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.SessionContext;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOParameter;
import com.exedosoft.plat.bo.DOParameterService;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.util.DOGlobals;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * 
 * ֻһ��Ҫ�޸�=======checkinstance objuid ��������ͦ�ࡣ
 * 
 * ǰ̨�ǲ�������Ϣȫ���������ˡ� �����ʱ���ָ���checkinstance���������֡�
 * 
 * ����������˲�һ�¡� ��������һ�£� 1,�޸�ǰ̨�����������������������ѶȱȽϴ� 2,�޸ĺ�̨ 3,ǰ��̨����޸�
 * 
 * ����DOService �ġ���������������������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: </
 * 
 * @author not attributable
 * @version 1.0
 */

public class CoreSaveAllAction extends DOAbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7581994809740062108L;

	private static Log log = LogFactory.getLog(CoreSaveAllAction.class);

	public CoreSaveAllAction() {
	}

	/**
	 * Save �����������Parameter ȡֵʱ������auto_condition����ѯ�� �����
	 */

	public String excute() {

		System.out.println("���������޸�Action::::::::::::::::::::::::::");

		if (this.service.getTempSql() == null) {
			System.out.println("δ����SQL ���");
			this.setEchoValue("δ����SQL ���");
		}

		String aKey = "checkinstance";
		String[] keys = this.actionForm.getValueArray(aKey);

		if (keys == null || keys.length == 0) {
			System.out.println("Key:" + aKey);
			System.out.println("û������");
			this.setEchoValue("û������");
			return NO_FORWARD;
		}

		List listKeys = Arrays.asList(keys);
		// checkinstance_hidden
		String[] key_hiddens = this.actionForm
				.getValueArray("checkinstance_hidden");
		// ////////////////�������

		// ///////////�����Ӧ��ҵ�����ʹ��Ҫˢ�µ�ҵ�����

		DOBO refreshBO = null;

		try {
			DOFormModel buttonForm = null;

			String invokeButtonID = this.actionForm.getValue("invokeButtonUid");
			if (invokeButtonID != null && !"".equals(invokeButtonID.trim())) {// ////////////���ȸ���������ť��ȡ
				buttonForm = DOFormModel.getFormModelByID(invokeButtonID);
			}
			if (buttonForm != null
					&& buttonForm.getTargetGridModels().size() <= 1) {
				if (buttonForm.getGridModel().getService() != null) {
					refreshBO = buttonForm.getGridModel().getService().getBo();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (refreshBO == null) {
			refreshBO = this.service.getBo();
			log.info("No InvokeButton's ID transed and refresh BO'name ::=="
					+ refreshBO.getName());
		}

		Connection con = null;

		try {
			con = this.service.getBo().getDataBase().getContextConnection();
			log.info("��ǰִ�е�SQL���:" + this.service.getTempSql());
			PreparedStatement pstmt = con.prepareStatement(this.service
					.getTempSql());

			int batchSize = 0;

			for (int len = 0; len < key_hiddens.length; len++) {

				String keyHidden = key_hiddens[len];
				if (!listKeys.contains(keyHidden)) {
					continue;
				}
				log
						.info("Select Data's ID::::::::::::::::::::::::"
								+ keyHidden);

				BOInstance oldInstance = refreshBO.refreshContext(keyHidden);
				String newKeyValue = null;

				int i = 1;
				for (Iterator it = this.service.retrieveParaServiceLinks()
						.iterator(); it.hasNext();) {
					DOParameterService dops = (DOParameterService) it.next();
					DOParameter dop = dops.getDop();

					String value = null;

					if (dop.getType() != null
							&& dop.getType().intValue() == DOParameter.TYPE_FORM
							&& dop.getDefaultValue() == null) {
						log.info("�����޸Ĳ���������:::" + dop.getName());

						String[] valueArray = this.actionForm.getValueArray(dop
								.getName());

						if (valueArray == null || valueArray.length <= len) {
							value = this.actionForm.getValue(dop.getName());
						} else {
							value = valueArray[len];
							if ("".equals(value)) {
								value = null;
							}
						}
					} else {
						value = dop.getValue();

					}

					if (dop.getType() != null
							&& dop.getType().intValue() == DOParameter.TYPE_KEY) {
						newKeyValue = value;
					}

					value = dops.getAfterPattermValue(value);
					this.service.putStatementAValue(pstmt, i, dops, value);
					i++;
				}
				batchSize++;
				if (oldInstance != null) {
					this.logOperation(this.service, oldInstance, newKeyValue);
				}
				pstmt.addBatch();
			}
			log.info("�����޸�::batchSize:::" + batchSize);
			pstmt.executeBatch();

		} catch (SQLException ex1) {
			ex1.printStackTrace();
			this.setEchoValue(ex1.getMessage());
			return NO_FORWARD;
		} finally {
			try {
				this.service.getBo().getDataBase().ifCloseConnection(con);
			} catch (SQLException ex1) {
				this.setEchoValue(ex1.getMessage());
				return NO_FORWARD;
			}
		}
		return DEFAULT_FORWARD;
	}

	/**
	 * ��־ҲҪ��Ƴ�һ����չ�ṹ���������Ƕ�do_log_data ���������ӡ�
	 * 
	 * @param uid
	 */
	public static void logOperation(DOService theService,
			BOInstance oldInstance, String uid) {

		if (theService.getIsLog() == null
				|| theService.getIsLog().intValue() == DOService.LOG_NO) {
			return;
		}

		BOInstance bi = theService.getBo().getInstance(uid);
		if (bi == null) {
			return;
		}
		SessionContext.getInstance().getThreadContext().put("old_instance",
				oldInstance);

		if (theService.getFilterClass() != null
				&& !"".equals(theService.getFilterClass().trim())) {

			DOAction ca = ActionFactory.getAction(theService.getFilterClass());
			ca.setService(theService);
			if (bi != null) {
				ca.setInstance(bi);
			}
			try {
				ca.excute();
			} catch (ExedoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		String optionType = null;

		if (theService.getLogType() != null
				&& !"".equals(theService.getLogType().trim())) {
			optionType = theService.getLogType();
		}

		switch (theService.getType().intValue()) {

		case DOService.TYPE_DELETE:
			optionType = "ɾ��";
			break;
		case DOService.TYPE_INSERT:
			optionType = "����";
			break;
		case DOService.TYPE_UPDATE_AUTO_PARA:
		case DOService.TYPE_UPDATE:

			optionType = "�޸�";
			break;
		}
		// insert into
		// do_log_data(objuid,table_name,col_name,who_uid,bo_uid,col_uid,oper_type,oper_data,
		// oper_time,oper_data_uid,oper_pane_uid,old_value,new_value)
		// values(?,?,?,?,?,?,?,?,?,?,?,?,?)

		DOService aLogService = DOService.getService("do_log_data_insert");
		BOInstance aLog = new BOInstance();
		aLog.putValue("TABLE_NAME", theService.getBo().getL10n());
		aLog.putValue("OPER_TYPE", optionType);
		aLog.putValue("OPER_DATA", bi.getName());
		aLog.putValue("oper_data_uid", bi.getUid());
		if (oldInstance != null) {
			aLog.putValue("old_value", oldInstance.getName() + "-------��ϸ��Ϣ��"
					+ oldInstance.toString());
		}

		try {
			aLogService.invokeUpdate(aLog);
		} catch (ExedoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}