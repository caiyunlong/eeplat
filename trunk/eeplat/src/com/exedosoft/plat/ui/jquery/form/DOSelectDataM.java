package com.exedosoft.plat.ui.jquery.form;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;

/**
 * 
 * ѡ��ͬ���͵Ķ��ҵ��������ֵ��ִ�й�����������
 * �ж����Эͬ��ɣ�����
 * 
 *1, DOSelectInvoke:���������������,	if (property.getInputConfig() != null) {
			
			////////////////���ص�inputType formModel Name
			buffer.append("<input name=\"").append(property.getInputConfig())
					.append("\" type=\"hidden\"").append(" id=\"").append(
							property.getInputConfig()).append("\" ");

			if (property.getValue() != null
					&& !property.getValue().trim().equals("")) {
				buffer.append(" value=\"").append(property.getValue()).append(
						"\"");
			}
			buffer.append(" />");
		}
		������DOValueService ����ѷ���ʵ��ֵ����ȥ�ˣ�
			buffer.append(",doAjax.selectInvoke='").append(fm.getFullColName())
					.append("'");

 2,DOSelectDataM:��������������ֵ���ݸ�js,		if (fm.getInputConfig() != null
				&& fm.getInputConfig().indexOf(";") != -1) {

			String[] types = fm.getInputConfig().split(",");
			buffer.append(",doAjax.inputType='").append(types[0]).append("'");
			buffer.append(",doAjax.inputTypeValue='").append(types[1]).append(
					"'");

		}

 3,ExedoAjax ���� selectData ִ�У�
 		 if($(this.inputType)!=null && this.inputTypeValue!=null ){
		   $(this.inputType).value = this.inputTypeValue;
		 }
	    

 * 
 * 
 * 
 * @author anolesoft
 *
 */
public class DOSelectDataM extends DOBaseForm {

	@Override
	public String getHtmlCode(DOIModel property) {

		DOFormModel fm = (DOFormModel) property;
		StringBuffer buffer = new StringBuffer("<button ");


		// ////����װ��
		buffer.append(getDecoration(fm));
		// ///////end ����װ��
		buffer.append(" title='").append(fm.getL10n()).append("'");
		// /// ԭ���Ŀ���::::�������,��������formmodel.id, ��ô�����ǿ��½����ϳ�������formmodel ������button
		// ////////���ڵĿ���:::ֱ��ʹ��fm.id ��̨���Եõ����id.
		String buttonId = fm.getObjUid();

		buffer.append(" id='").append(buttonId).append("'");
		buffer.append(" onclick=\"javascript:doAjax.initExedo()");

		if (fm.getLinkService() != null) {
			buffer.append(",doAjax.actionName='").append(
					fm.getLinkService().getObjUid()).append("'");
		}

		buffer.append(",doAjax.confirmScript='");
		if (fm.getEchoJs() != null) {
			buffer.append(fm.getEscapeEchoJs());
		} else if (fm.getController().getOtherScript() != null) {
			buffer.append(fm.getController().getEscapeOtherScript());
		} else {
			buffer.append("true");
		}
		buffer.append("'");

		String theForm = fm.getTargetForms();

		buffer.append(",doAjax.formName='").append(theForm).append("'");

		// /////////�����û��Զ����onclick�¼�
		if (fm.getDoClickJs() != null && !"".equals(fm.getDoClickJs().trim())) {
			buffer.append(",doAjax.doClickJs='")
					.append(fm.getEscapeDOClickJs()).append("'");
		}
		if (fm.getController().getStandbyAttrs() != null) {
			buffer.append(",").append(fm.getController().getStandbyAttrs());
		}

		if (fm.getContainerPaneName() != null) {
			buffer.append(",doAjax.invokePaneName='").append(
					fm.getContainerPaneName()).append("'");
		}

		////��������ֵ��ʲô���⣿��������������������������
		if (fm.getInputConfig() != null
				&& fm.getInputConfig().indexOf(";") != -1) {

			String[] types = fm.getInputConfig().split(",");
			buffer.append(",doAjax.inputType='").append(types[0]).append("'");
			buffer.append(",doAjax.inputTypeValue='").append(types[1]).append(
					"'"); 

		}

		// inputTypeValue
		// inputType

		buffer.append(",doAjax.selectDataM('");
		buffer.append(theForm);
		buffer.append("')\" >\n");

		buffer.append(fm.getL10n());
		buffer.append("\n</button>");
		return buffer.toString();
	}

}
