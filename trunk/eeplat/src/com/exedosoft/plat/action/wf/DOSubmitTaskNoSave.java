package com.exedosoft.plat.action.wf;

import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.wf.WFException;
import com.exedosoft.wf.WFUtil;
import com.exedosoft.wf.wfi.NodeInstance;

public class DOSubmitTaskNoSave extends DOAbstractAction {


	private static final long serialVersionUID = -7966629819816418584L;

	public String excute() {
		
		DOBO ptNI = DOBO.getDOBOByName("do_wfi_nodeinstance");
		
		if(ptNI.getCorrInstance()==null){
			this.setEchoValue("��ǰ�����������Ķ�ʧ,�����²���!");
			return null;
		}
		NodeInstance ni = NodeInstance.getNodeInstanceByID(ptNI.getCorrInstance().getUid());
		WFUtil.refreshWFPara(ni.getProcessInstance());
		
		try {
			ni.perform();
		} catch (WFException e) {
		   this.setEchoValue(e.getLocalizedMessage());
		   return null;
		}
		return DEFAULT_FORWARD;
	}

}
