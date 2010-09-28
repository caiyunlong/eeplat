package com.exedosoft.plat.action.customize.tools;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;

public class CopyServiceParas extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3155689928718730452L;

	@Override
	public String excute() throws ExedoException {

		if (this.service==null || this.service.getTempSql() == null) {
			System.out.println("δ����SQL ���");
			this.setEchoValue("δ����SQL ���");
			return NO_FORWARD;
		}


		String serviceUid = this.actionForm.getValue("serviceUid");
		if(serviceUid==null){
			this.setEchoValue("û��ѡ�����");
			return  DEFAULT_FORWARD;
		}
		String[] checks = this.actionForm.getValueArray("checkinstance");
		if (checks == null) {
			System.out.println("û�����ݣ�");
			this.setEchoValue("û�����ݣ�");
			return NO_FORWARD;
		}
		
		try {
			this.service.beginBatch();
			DOBO boParaService = DOBO.getDOBOByName("DO_Parameter_Service");
			for(int i = 0; i < checks.length ; i++){
				BOInstance bi = boParaService.getInstance(checks[i]);
				bi.putValue("objuid", null);
				bi.putValue("serviceUid", serviceUid);
				this.service.addBatch(bi);			
			}
			this.service.endBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DEFAULT_FORWARD;

	}

}
