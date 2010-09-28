package com.exedosoft.plat.action.customize.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.StringUtil;

public class TransFormModelL10n extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3155689928718730452L;

	private static Log log = LogFactory.getLog(TransFormModelL10n.class);

	@Override
	public String excute() throws ExedoException {

		if (this.service == null || this.service.getTempSql() == null) {
			System.out.println("δ����SQL ���");
			this.setEchoValue("δ����SQL ���");
			return NO_FORWARD;
		}

//		String fileName = this.actionForm.getValue("fileName");
//
//		if (fileName == null) {
//			this.setEchoValue("�㻹û��ѡ���ļ���");
//			return NO_FORWARD;
//		}

//		fileName = DOGlobals.UPLOAD_TEMP.trim() + StringUtil.getCurrentDayStr()
//				+ "/" + fileName.trim();
//		log.info("�õ��ļ��ľ���·��:::" + fileName);
		try {
//			File file = new File(fileName);
//
//			FileReader fr = new FileReader(file);
//			BufferedReader in = new BufferedReader(fr);
//			// List allChs = new ArrayList();
//			StringBuffer sb = new StringBuffer();
//			this.service.beginBatch();
//			while (true) {
//				String aLine = in.readLine();
//				if (aLine == null) {
//					break;
//				}
//				String[] paras = aLine.split("=");
//				if (paras != null && paras.length > 1) {
//					Map map = new HashMap();
//					map.put("l10n_en", paras[0].toLowerCase());
//					map.put("l10n_china", paras[1]);
//					this.service.addBatch(map);
//				}
//			}
			this.service.beginBatch();
			String trans = this.actionForm.getValue("trans");
			String[] tranArray = trans.split("\n");
			for(int i = 0; i < tranArray.length; i++){
				String aLine = tranArray[i];
				if(aLine==null){
					break;
				}
				log.info("������ֶ�::" + aLine);
				String[] paras = aLine.split("=");
				if (paras != null && paras.length > 1) {
					Map map = new HashMap();
					map.put("l10n_en", paras[0].toLowerCase());
					map.put("l10n_china", paras[1]);
					this.service.addBatch(map);
				}
			}
			this.service.endBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setEchoValue("�������!");
		return DEFAULT_FORWARD;
	}

	public static void main(String[] args) {

		TransFormModelL10n tf = new TransFormModelL10n();
		DOService dos = DOService.getService("updateFormModelsetl10n");
		tf.setService(dos);
		try {
			tf.excute();
		} catch (ExedoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
