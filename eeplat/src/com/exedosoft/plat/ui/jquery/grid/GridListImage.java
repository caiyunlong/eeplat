package com.exedosoft.plat.ui.jquery.grid;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.StringUtil;

/**
 * 
 * @author aa
 * 
 */
/**
 * @author lenovo
 *  
 *  �������Ϊ��ӡ��˵Ŀؼ����ָ�ΪCMS����ͼƬչʾ�ؼ�
 *  չʾԭ��
 *  ����exedo/webv3/template/cms/themeĿ¼�µ��ļ��У�û���ļ���Ϊһ������
 *
 */
public class GridListImage extends DOViewTemplate {

	private static Log log = LogFactory.getLog(GridListImage.class);

	public GridListImage() {
		this.templateFile = "grid/GridListImage.ftl";
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> putData(DOIModel doimodel) {
		int pageNo = 1;
		int pageNum = 0;
		//��ȡ����Ŀ¼
		List ztl = getThemesDir();
		Map<String, Object> data = new HashMap<String, Object>();
		DOGridModel gm = (DOGridModel) doimodel;
		
		if (gm.getRowSize() != null) {
			pageNum = gm.getRowSize().intValue();
		}

		data.put("rowSize", pageNum);
		int resultSize = ztl.size(); //gridModel.getService().getResultSize();
		int pageSize = StringUtil.getPageSize(resultSize, pageNum);
		data.put("pageSize", pageSize);
		data.put("resultSize", resultSize);
		data.put("pageNo", pageNo);


		data.put("cms", ztl);
		data.put("model", gm);
		data.put("contextPath", DOGlobals.PRE_FULL_FOLDER);
		if(gm.getContainerPane()!=null){
			data.put("pmlName", gm.getContainerPane().getName());
		}
		data.put("formName", "a" + gm.getObjUid());
		//ÿ����ʾ���������������û���룬Ĭ��Ϊ3
		int datarowSize ;
		String rowTmp = DOGlobals.getInstance().getSessoinContext().getFormInstance().getValue("rowSize");
		if ( rowTmp != null && ! "".equals(rowTmp)){
			datarowSize = Integer.parseInt(rowTmp);
		}else {
			datarowSize = 3;
		}
		//��ȡ��ʾͼ��ť
		List  list = gm.getAllGridFormLinks();
		DOFormModel fm ;
		for ( int i = 0 ; i < list.size(); i++ ){
			fm = (DOFormModel) list.get(i);
			if ( fm.getL10n().equals("startusing")){
				data.put("fm", fm);
				break ;
			}else{
				data.put("fm", "");
			}
		}
		data.put("datarowSize", datarowSize);
	
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public List  getThemesDir(){
		//classesĿ¼
		String path = this.getClass().getResource("/").getPath();
		File current_dir = new File(path);
		//��ȡWEB-INFOĿ¼�ĸ�Ŀ¼
		File root_dir = new File(new File(current_dir.getParent()).getParent() + "/exedo/webv3/template/cms/theme/");
		File[] listDirs = root_dir.listFiles();
		DOService service = DOService .getService("cms_options_list");
		BOInstance bo = new BOInstance();
		List<BOInstance> l = service.invokeSelect(bo);
		String current_theme = "";
		if ( !l.isEmpty()){
			for(BOInstance b : l){
				System.out.println(b.getValue("opt_key"));
				if(b.getValue("opt_key").equals("themes_dir")){
					
					current_theme = b.getValue("opt_value");
				}
				
			}
		}
		
		List cl = new ArrayList();
		for(File f : listDirs){
			if (f.isDirectory()){
				Map map = new HashMap();
				map.put("theme_dir", f.getName());
				if (f.getName() .equals(current_theme)){
					map.put("current_theme",current_theme) ;
				}
				cl.add(map);
			}
		}
		
		return cl ;
	}

//	public static List<BOInstance> getListData(DOGridModel gridModel,
//			Map<String, Object> data) {
//		List<BOInstance> list;
//		int pageNo = 1;
//		int pageNum = 0;
//	 
//		File rootPath = new File("/yiyi/exedo/webv3/template/cms/theme");
//		File[] fileList = rootPath.listFiles();
//		if (DOGlobals.getInstance().getSessoinContext().getFormInstance()
//				.getValue("pageNo") != null) {
//			try {
//				pageNo = Integer.parseInt(DOGlobals.getInstance()
//						.getSessoinContext().getFormInstance().getValue("pageNo"));
//			} catch (Exception e) {
//
//			}
//		}
//		// pageNo = DOGlobals.getInstance().getSessoinContext().splitPageContext
//		// .getPageNo(gridModel.getService());
//		// log.info("SplitPage Filter Table Get PageNO:::" + pageNo);
//
//		if (gridModel.getRowSize() != null) {
//			pageNum = gridModel.getRowSize().intValue();
//		}
//
//		if (pageNum <= 0) {
//			list = gridModel.getService().invokeSelect();
//		} else {
//			data.put("rowSize", pageNum);
//			int resultSize = 3; //gridModel.getService().getResultSize();
//			int pageSize = StringUtil.getPageSize(resultSize, pageNum);
//			data.put("pageSize", pageSize);
//			data.put("resultSize", resultSize);
//			data.put("pageNo", pageNo);
//
//			list = gridModel.getService().invokeSelect(pageNo, pageNum);
//
//		}
//		
//		/////����ڶ�����ͳ���ã�
//		DOService secondService = gridModel.getSecondService();
//		if(secondService!=null){
//			List secondResult = secondService.invokeSelect();
//			if(secondResult.size() > 0){
//				BOInstance statistics = (BOInstance)secondResult.get(0);
//				data.put("statistics", statistics.getMap());
//				StringBuilder sb = new StringBuilder();
//				List<DOFormModel> listFm = gridModel.getStatisticOutGridFormLinks();
//				if(listFm!=null && listFm.size()>0){
//					for(Iterator<DOFormModel> it = listFm.iterator();it.hasNext();){
//						DOFormModel aFm = it.next();
//						aFm.setData(statistics);
//						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;").append(aFm.getL10n()).append(":").append(aFm.getValue()).append("&nbsp;&nbsp;&nbsp;&nbsp;");
//					}
//				}
//				data.put("statistics_details", sb.toString());
//				
//			}
//		}
//		
//		return list;
//	}

}
