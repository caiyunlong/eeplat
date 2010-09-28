package com.exedosoft.plat.action.customize.tools;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.gene.jquery.PropertyManager;

public class DOGeneConfigAddProperty extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4555077593344493040L;

	@Override
	public String excute() throws ExedoException {
		
		DOBO bo = DOBO.getDOBOByName("do_bo");
		BOInstance instance = bo.getCorrInstance();
		if(instance==null){
			this.setEchoValue("û������!");
			return NO_FORWARD;
		}
		
		String colName =  this.actionForm.getValue("col_name");
		String type = this.actionForm.getValue("dbtype");
		String dbsize = this.actionForm.getValue("dbsize");
		
		if(colName==null || type ==null || dbsize==null){
			this.setEchoValue("�ֶ����ƻ������ͻ��߳���û�ж���!");
			return NO_FORWARD;
		}

		
		DOBO thisBO = DOBO.getDOBOByID(instance.getUid());
		
		PropertyManager pm = new PropertyManager();
		pm.addProperty(thisBO, colName,Integer.parseInt( type ),Integer.parseInt(dbsize));

		return DEFAULT_FORWARD;
	}

}
