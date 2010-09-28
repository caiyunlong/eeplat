package com.exedosoft.plat.action.customize.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.gene.jquery.GeneUIMain;

public class DOGeneConfigTableSearch extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 123234L;

	private static Log log = LogFactory.getLog(DOGeneConfigTableSearch.class);

	/**
	 * Save �����������Parameter ȡֵʱ������auto_condition����ѯ�� �����
	 */

	public String excute() {
		
		DOBO bo = DOBO.getDOBOByName("do_bo");
		BOInstance instance = bo.getCorrInstance();
		if(instance==null){
			this.setEchoValue("û������!");
			return NO_FORWARD;
		}
		GeneUIMain gum = new GeneUIMain(instance.getValue("sqlStr"));
		gum.geneConfig();
		
		this.setEchoValue("��ʼ���ɹ�!");
		return DEFAULT_FORWARD;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
