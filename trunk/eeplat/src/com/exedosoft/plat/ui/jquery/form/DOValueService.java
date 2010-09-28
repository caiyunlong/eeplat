package com.exedosoft.plat.ui.jquery.form;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.template.HtmlTemplateGenerator;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.DOGlobals;

public class DOValueService extends DOViewTemplate {

	private static Log log = LogFactory.getLog(DOValueService.class);

	public DOValueService() {

		this.templateFile = "form/TAService.ftl";
	}

	public String getHtmlCode(DOIModel aModel) {

		DOFormModel fm = (DOFormModel) aModel;
		return standardValueService(fm, null);

	}

	public String standardValueService(DOFormModel fm, String label) {

		if (fm.getLinkService() != null) {

			Map<String, Object> data = putData(fm);

			String s = "";
			try {
				s = HtmlTemplateGenerator.getContentFromTemplate(
						this.templateFile, data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return s;

		} else if (fm.getLinkPaneModel() != null) {
			return stardardOnlyPane(fm, label).toString();
		}
		return null;

	}

	public static StringBuffer stardardOnlyPane(DOFormModel fm, String label) {
		return stardardOnlyPane(fm, label, false);
	}

	public static StringBuffer stardardSelectPane(DOFormModel fm, String label) {
		return stardardOnlyPane(fm, label, true);
	}

	private static StringBuffer stardardOnlyPane(DOFormModel fm, String label,
			boolean isSelectPane) {

		if (label == null || "".equals(label.trim())) {
			label = fm.getValue();

			if (fm.getImage() != null) {
				label = getIcon(fm.getImage(), label);
			}
		}

		// /////////////�����趨���ضϳ���
		if (label != null && DOGlobals.LIST_SHOWVALUE_MAX > 0
				&& label.length() > DOGlobals.LIST_SHOWVALUE_MAX) {
			label = label.substring(0, DOGlobals.LIST_SHOWVALUE_MAX) + "...";
		}

		// /////////////End �����趨���ضϳ���

		if (fm.getLinkPaneModel() != null
				&& fm.getLinkPaneModel().getLinkType() != null
				&& fm.getLinkPaneModel().getLinkType().intValue() == DOPaneModel.LINKTYPE_RESOURCE) {

			return standardPlugInService(fm, label);
		}

		StringBuffer buffer = new StringBuffer("<a  class='exedo_link' href=\"");
		String targetPaneId = null;
		if (fm.getTargetPaneModel() != null) {
			targetPaneId = fm.getTargetPaneModel().getName();
		} else if (fm.getLinkPaneModel() != null
				&& fm.getLinkPaneModel().getTargetPane() != null) {
			targetPaneId = fm.getLinkPaneModel().getTargetPane().getName();
		} else {
			// targetPaneId = fm.getContainerPaneName();// //dojoButton
			// ���ý���Ԫ�����ڵ����
			targetPaneId = "_opener";// //////valueService ���õ����ķ�ʽ
		}

		/**
		 * �����button ��Ӧ��һ��paneModel��ʱ�򣬿���Ҫ�Ե�ǰ��formֵͬ�����������ˣ�
		 * ���ӵ����pangeModel���ܻ��õ����paneModel Cancel by weikx at 0705 ����getData
		 * ��ʽ��Ч�����������̨�ύ����
		 */

		appendJSPaneLink(fm, buffer);
		buffer.append("\" >").append(label).append("</a>\n");
		return buffer;
	}

	public static void appendJSPaneLink(DOFormModel fm, StringBuffer buffer) {

		BOInstance bi = fm.getData();

		buffer.append("javascript:invokeDomId='")
		.append(fm.getFullColID())
		.append("',loadPml({'pml':'");

		if (fm.getLinkPaneModel().getLinkType().intValue() == DOPaneModel.LINKTYPE_RESOURCE) {
			buffer
					.append(fm.getLinkPaneModel().getResource()
							.getResourcePath());
		} else {
			buffer.append(fm.getLinkPaneModel().getName());
		}

		if (bi != null) {
			buffer.append("','paras':'dataBus=setContext&contextKey=").append(
					bi.getBo().getName()).append("&contextValue=").append(
					bi.getUid());

			if (bi.getValue("contextniuid") != null) {
				buffer.append("&contextNIUid=").append(
						bi.getValue("contextniuid"));
			}

			if (bi.getValue("contextPIUid") != null) {
				buffer.append("&contextPIUid=").append(
						bi.getValue("contextPIUid"));
			}

		}
		
		
		if (fm.getLinkPaneModel().getPaneWidth() != null) {
			buffer.append("','pmlWidth':'").append(fm.getLinkPaneModel().getPaneWidth());
		}

		if (fm.getLinkPaneModel().getPaneHeight() != null) {
			buffer.append("','pmlHeight':'").append(fm.getLinkPaneModel().getPaneHeight());
		}
		

		buffer.append("','title':'").append(fm.getLinkPaneModel().getTitle())
				.append("','formName':'a").append(fm.getGridModel().getObjUid())
				.append("'");

		if (fm.getTargetPaneModel() != null) {
			buffer.append(",'target':'").append(
					fm.getTargetPaneModel().getName()).append("'");
		}
		buffer.append("})");
	}

	public static StringBuffer standardPlugInService(DOFormModel fm,
			String label) {

		if (fm.getLinkPaneModel() != null
				&& fm.getLinkPaneModel().getLinkType() != null
				&& fm.getLinkPaneModel().getLinkType().intValue() == DOPaneModel.LINKTYPE_RESOURCE) {

			StringBuffer buffer = new StringBuffer("<a  class='exedo_link'");

			if (fm.getData() != null) {

				StringBuffer href = new StringBuffer("window.open('").append(fm
						.getLinkPaneModel().getResource().getResourcePath());

				href.append("?contextInstanceUid=").append(
						fm.getData().getUid()).append("&contextClassUid=")
						.append(fm.getData().getBo().getObjUid());

				if (fm.getData() != null) {

					String contextNIUid = fm.getData().getValue("contextNIUid");
					if (contextNIUid != null && !"".equals(contextNIUid.trim())) {
						href.append("&contextNIUid=").append(contextNIUid)
								.append("");
					}
					String contextPIUid = fm.getData().getValue("contextPIUid");
					if (contextPIUid != null && !"".equals(contextPIUid.trim())) {
						href.append("&contextPIUid=").append(contextPIUid);
					}
				}

				href.append("')");

				buffer.append("  href='#' onclick=\"javascript:").append(href)
						.append("\"");

			}
			buffer.append(" > ").append(fm.getL10n()).append("</a>");

			return buffer;

		}

		return null;

	}

	private static String getIcon(String icon, String tooltip) {

		String aPath = DOGlobals.UPLOAD_URL + icon;

		aPath = aPath.trim().replaceAll(" ", "%20");
		StringBuffer buffer = new StringBuffer();
		// //////////��picture���͵����⴦��

		buffer.append("<img src=").append(aPath).append(" border='0'").append(
				" alt='").append(tooltip).append("' />");

		return buffer.toString();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
