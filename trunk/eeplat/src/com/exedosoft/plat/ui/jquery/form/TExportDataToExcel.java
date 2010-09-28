package com.exedosoft.plat.ui.jquery.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.DOGlobals;

/**
 * ����ѯ���������excel�Ŀ�������ʵ�����۸ð�ť������������У����ǽ���б��У�������ʹ�á�
 * 
 * @author ZhangYunHe
 * 
 */
public class TExportDataToExcel extends DOViewTemplate {

	public TExportDataToExcel() {
		this.templateFile = "form/TExportDataToExcel.ftl";
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		DOFormModel btn = (DOFormModel) doimodel;

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("contextPath", DOGlobals.PRE_FULL_FOLDER);
		data.put("webmodule", DOGlobals.URL);
		data.put("model", btn);

		DOGridModel grid = btn.getGridModel();// �ҵĸ��ڵ�,���
		DOPaneModel panel = grid.getContainerPane();// ��ñ����ϼ����������
		List children = panel.getParent().retrieveChildren();// ���������ϼ�������壬��������л�á�������塱�͡������塱
		if (children != null && children.size() == 2) {
			if (grid.getName().toLowerCase().indexOf("conditionpanel") != -1) {// �ж���λ�ڡ�������塱
				// �������������ť���ڡ����������У���Ҫ��á�����б��еġ����񡱵�ID
				DOPaneModel result = (DOPaneModel) children.get(1);// ������
				if (result != null) {
					if (result.getDOGridModel() != null) {
						data.put("serviceID", result.getDOGridModel()
								.getService().getObjUid());// �����ID
					}
				}
			}

			if (grid.getName().toLowerCase().indexOf("resultpanel") != -1) {// �ж����Ƿ�λ�ڡ������塱
				// �������������ť���ڡ�����б��У���Ҫ��á����������е�form��ID
				DOPaneModel condition = (DOPaneModel) children.get(0);
				if (condition != null) {
					if (condition.getDOGridModel() != null) {
						data.put("formID", "a"+condition
								.getDOGridModel().getObjUid());// FORM��ID
					}
				}
			}
		}

		return data;
	}

}
