package com.exedosoft.plat.action;

import java.util.HashMap;
import java.util.Map;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;

/**
 * 
 * ��Ϣ������
 * 
 * @author IBM
 * 
 */

public class DOMsgSender extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 101111111111L;

	private static DOService msgInsert = DOService.getService("DO.MSG.Insert");

	private static DOService msgDelete = DOService.getService("DO.MSG.Delete");

	private static DOService msgReadInsert = DOService
			.getService("DO.MSG.READ.Insert");

	private static DOService msgReadDelete = DOService
			.getService("DO.MSG.READ.delete.by.msguid");

	@Override
	public String excute() throws ExedoException {
		// TODO Auto-generated method stub

		String msgstatus = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance().getValue("msgstatus");

		String recieveids = "";

		// /////////�����ϵ�״̬
		if ("2".equals(msgstatus) || "3".equals(msgstatus)) {

			// ////////����ɾ��
			msgReadDelete.invokeUpdate();
			msgDelete.invokeUpdate();
			recieveids = DOGlobals.getInstance().getSessoinContext()
					.getFormInstance().getValue("recieveids_u");
		} else {
			recieveids = DOGlobals.getInstance().getSessoinContext()
					.getFormInstance().getValue("recieveids");
		}

		System.out.println("���н�����::::::::::" + recieveids);

		if (recieveids == null || recieveids.equals("")) {
			this.setEchoValue("��û��ѡ���ռ���");
			return null;
		}

		// //////////ת��Ϊ���ݿ��״̬,�ظ�Ҳ������
		if ("2".equals(msgstatus) || "5".equals(msgstatus)) {
			DOGlobals.getInstance().getSessoinContext().getFormInstance()
					.putValue("msgstatus", "1");
		} else if ("3".equals(msgstatus)) {
			DOGlobals.getInstance().getSessoinContext().getFormInstance()
					.putValue("msgstatus", "0");
		}

		Map map = new HashMap();
		String[] recieveArray = recieveids.split(",");
		if (recieveArray != null && recieveArray.length > 0) {
			System.out.println("Msg Send Service:::" + msgInsert);
			System.out.println("Send Msg Error::::::::::::::");

			BOInstance aInstance = msgInsert.invokeUpdate();
			msgReadInsert.beginBatch();
			for (int i = 0; i < recieveArray.length; i++) {
				String recieveuid = recieveArray[i];
				if (recieveuid != null && recieveuid.trim().length() > 0) {
					map.put("recieveuid", recieveuid);
					msgReadInsert.addBatch(map);
				}
			}
			msgReadInsert.endBatch();
		}
		return null;
	}

}
