package com.exedosoft.plat.ui.jquery.tree;

import java.util.HashMap;
import java.util.Map;

import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOTreeModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.DOGlobals;


/***
 *������ajax����ʽ,��ò���dwr��ʽ�������Ǵ�ͳ���Ǹ���ʽ��
 *
 * @author aa
 *
 */
public class JquerySimpleTree extends DOViewTemplate {
	
	//����ִ�е�js:resscrEvt();$('.mRight:eq(0)').css(\"overflow-y\",\"hidden\")
    //resscrEvt()����������߶ȵ����ұ�����ĸ߶ȣ�$('.mRight:eq(0)').css(\"overflow-y\",\"hidden\")�����ܿ�ܵĹ��������ء������Ƚ���.
//	
//	public String getHtmlCode(DOIModel aModel) {
//		
//		DOTreeModel treeModel = (DOTreeModel)aModel;
//		
//		String treeModelUrl = "/"+DOGlobals.URL + "/loadtreesvl?treeModelUid=" + treeModel.getObjUid();
//		String containerPane = "";
//		String treeId = "#"+treeModel.getNodeName();
//	    if(treeModel.getContainerPane()!=null){
//	    	containerPane = treeModel.getContainerPane().getObjUid();
//	    }
//		
//		StringBuilder sb = new StringBuilder();
//		sb.append("<script language=\"javascript\">");
//		sb.append("$(function(){webFXTreeHandler.resetContext();var tree = new WebFXLoadTree('"+treeModel.getL10n()+"','"+treeModelUrl+"');$(\""+treeId+"\").append(tree.toHtml());resscrEvt();$('.mRight:eq(0)').css(\"overflow-y\",\"hidden\")})")
//		.append("</script>"); 
//		sb.append("<table width=100% cellspacing=0>");
//		sb.append("<tr><td width=100%>");
//		sb.append("<div class=tree id='").append(treeModel.getNodeName()).append("'></div>");
//		sb.append("</td><td>");
//		sb.append("<div class='treeLine'></div>");
//		sb.append("</td></tr>");
//		sb.append("</table>");
// 
//		return sb.toString();
//	}

	public JquerySimpleTree(){
		this.templateFile = "tree/SimpleTree.ftl";
	}
	
	public Map<String, Object> putData(DOIModel doimodel) {

		DOTreeModel treeModel = (DOTreeModel) doimodel;
		Map<String, Object> data = new HashMap<String, Object>();
		String treeModelUrl = "/"+DOGlobals.URL + "/loadtreesvl?treeModelUid=" + treeModel.getObjUid();
		data.put("model", treeModel);
		data.put("treeModelUrl", treeModelUrl);
		return data;
	}
	
}
