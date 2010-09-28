package com.exedosoft.plat.action.wf;

import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.wf.WFException;
import com.exedosoft.wf.WFUtil;
import com.exedosoft.wf.wfi.NodeInstance;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.MVCController;

public class DOBackTaskAndSave extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6384956483993324561L;

	public String excute() {
		DOBO ptNI = DOBO.getDOBOByName("do_wfi_nodeinstance");
		if (ptNI.getCorrInstance() == null) {
			this.setEchoValue("��ǰ�����������Ķ�ʧ,�����²���!");
			return null;
		}
		NodeInstance ni = NodeInstance.getNodeInstanceByID(ptNI
				.getCorrInstance().getUid());
		WFUtil.refreshWFPara(ni.getProcessInstance());

		try {
			this.service.invokeUpdate();
		} catch (ExedoException e1) {
			this.setEchoValue(e1.getMessage());
			return NO_FORWARD;
		}
	
		try {
			ni.returnBack();
		} catch (WFException e) {
			this.setEchoValue(e.getMessage());
			return NO_FORWARD;
		}
		return DEFAULT_FORWARD;
	}

}
