package com.exedosoft.plat.ui.jquery.form;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;

/*
 * �б�����£����һ���ֶ�ͼƬ��ʽ��ʾɾ��������ͨ��Static��Textʵ��.
 * Input Config������ͼƬ�����ӣ�
 * 
 */

public class DOValueStaticText extends DOBaseForm {

	public DOValueStaticText() {
		super();
	}


	public String getHtmlCode(DOIModel aModel) {

		DOFormModel property = (DOFormModel) aModel;
		return property.getInputConfig();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
