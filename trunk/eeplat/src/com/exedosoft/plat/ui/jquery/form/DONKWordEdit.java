package com.exedosoft.plat.ui.jquery.form;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.Escape;

/**
 * ���ڿ���word ��Ҫ���ֲ���������ֻ����ֻ��/��д���ܸ㶨�� ����������inputconfig�Ķ��塣 ��inputconfig = 1 ��ֻ��
 * (ֻҪinputconfig����ĵ�һ���ַ���1) Ĭ���ǿ��޸� = 3 �������� Ĭ�� = -3 ���������� = 5 ��ʾ�ۼ� Ĭ�� = -5
 * ����ʾ�ۼ�
 * 
 * 
 * 
 * 
 * ������ѡ������,����
 * 
 */
public class DONKWordEdit extends DOBaseForm {

	public String getHtmlCode(DOIModel iModel) {

		DOFormModel fm = (DOFormModel) iModel;

		StringBuffer href = new StringBuffer("javascript:window.open('")
				.append(DOGlobals.PRE_FULL_FOLDER)
				.append("upload.jsp?colName=").append(fm.getFullColID());

		href
				.append("','','width=300,height=150,left=200,top=200')");

		StringBuffer buffer = new StringBuffer();

		buffer.append("<input   name=\"").append(fm.getFullColName()).append(
				"\"").append(" id=\"").append(fm.getFullColID()).append("\"");

		if (fm.getValue() != null && !fm.getValue().trim().equals("")) {
			buffer.append("type=\"hidden\"");
		}

		buffer.append(this.appendValidateConfig(fm));

		if (fm.getValue() != null && !fm.getValue().trim().equals("")) {
			buffer.append(" value=\"").append(fm.getValue()).append("\"");
		}
		// if (isReadOnly(property)) {
		buffer.append(" readonly=\"readonly\" ");
		// }

		// ////����װ��
		buffer.append(getDecoration(fm));

		// ///////end ����װ��
		buffer.append(" title='").append(fm.getL10n()).append("'");

		buffer.append(" />");

		if (fm.getValue() != null && !fm.getValue().trim().equals("")) {

			String userName = "test";
			try {
				userName = DOGlobals.getInstance().getSessoinContext()
						.getUser().getName();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buffer.append("<a  class='exedo_link' target='_blank' href=/");
			try {
				buffer.append(DOGlobals.URL).append(
						"/nkoa/openawordedit.jsp?docName=").append(
								URLEncoder.encode(Escape.escape(fm.getValue()),"utf-8") )
						.append("&colName=")
						.append(fm.getFullColID()).append("&contextBIUid=")
						.append(fm.getData().getUid()).append("&userName=").append(
								Escape.escape(userName)).append("&isSaveLiuhen=")
						.append("t").append("&isShowLjcx=").append("t").append(">");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (fm.getInputConfig() != null) {
				buffer.append("&inputconifg=").append(fm.getInputConfig());
			}

			buffer.append(fm.getL10n());
			buffer.append("</a>");
		} else {

			buffer.append("<input type='button' value='�ϴ�'");

			buffer.append(" onclick=\"").append(href).append(" \"");
			buffer.append(" />\n");

		}

		if (fm.isNotNull()) {
			buffer.append("&nbsp;<font color='red'>*</font>");
		}

		return buffer.toString();

		// //////////����ֻ��
		// OCX_OBJ. SetReadOnly (true,"");

	}

}
