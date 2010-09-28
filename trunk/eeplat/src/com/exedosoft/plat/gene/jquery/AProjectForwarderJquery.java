package com.exedosoft.plat.gene.jquery;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.BusiPackage;
import com.exedosoft.plat.bo.DOApplication;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DODataSource;
import com.exedosoft.plat.bo.DOResource;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOController;
import com.exedosoft.plat.ui.DOMenuModel;
import com.exedosoft.plat.ui.DOPaneLinks;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.jquery.menu.JqueryMenuXP;
import com.exedosoft.plat.ui.jquery.pane.ContentPane;
import com.exedosoft.plat.ui.jquery.pane.ContentPaneScroll;
import com.exedosoft.plat.ui.jquery.pane.LayOutLeft;
import com.exedosoft.plat.ui.jquery.pane.LayOutSplitPane;
import com.exedosoft.plat.ui.jquery.pane.MainPage;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.StringUtil;

/**
 * ʹ�÷ֲ�ʽ���ÿ⣬ʵ������̫���� ���ǿ��Զ�̬�л����ÿ� ����ʹ�õ��롢������ʽ��ʵ���Ŷ�Э������
 * 
 * ���롢�����ķ�ʽ����ʹ��xml�ļ���sql�ļ��� ���롢���������ȣ� 1������ 2��ҵ��� 3��ҵ����� 4������ 5����� 6�����
 * 
 * ����һ�� ���԰����ڡ� ����ĳ������֮��ģ�ȫ������ȥ
 * 
 * ������С���ȣ����ʵ���Ƿ��ǣ�
 * 
 * ���� ���� ���Ԫ�� �� ���ڵ� �˵�
 * 
 * 
 * 
 * 
 * 
 * @author IBM
 * 
 */

public class AProjectForwarderJquery {

	DOController layOutHeader = DOController
			.getControllerByName("com.exedosoft.plat.ui.jquery.pane.LayOutHeader");

	DOController layOutLeft = DOController.getControllerByName(LayOutLeft.class
			.getName());

	DOController mainPage = DOController.getControllerByName(MainPage.class
			.getName());

	DOController contentScroll = DOController
			.getControllerByName(ContentPaneScroll.class.getName());

	DOController ccSplitePane = DOController
			.getControllerByName(LayOutSplitPane.class.getName());

	DOController contentPane = DOController
			.getControllerByName(ContentPane.class.getName());
	/**
	 * MenuController,�����й�����
	 */
	DOController menuController = DOController
			.getControllerByName(JqueryMenuXP.class.getName());

	public void forwardBaseUI(String projectUid) {
		
		DOApplication project = DOApplication.getApplicationByID(projectUid);

		if (DOBO.getDOBOByName(project.getName() + "_global_bo") != null) {
			return;
		}
		// HbmDAO dao = new HbmDAO();
		// dao.setAutoClose(false);

		// //������ҵ���,ͬ��ҵ���
		BusiPackage bp = new BusiPackage();
		bp.setApplication(project);
		bp.setL10n(project.getL10n());
		bp.setName(project.getName());

		DOBO aBO = new DOBO();
		aBO.setType(DOBO.TYPE_BUSINESS);
		aBO.setName(project.getName() + "_global_bo");
		aBO.setL10n(project.getL10n() + "_ȫ��");

		DODataSource dds = DODataSource.parseGlobals();

		Transaction t = dds.getTransaction();

		t.begin();

		DOService aService = DOService.getService("DO_UI_PaneLinks_copy");
		try {
			// //////////����ҵ���
			DAOUtil.INSTANCE().currentDataSource(dds);

			DAOUtil.INSTANCE().store(bp);

			// //����ҵ�����
			aBO.setPakage(bp);

			DAOUtil.INSTANCE().store(aBO);
			
			///////������֯Ȩ����ص�ҵ�������ʼ���Ĺ�������

			DOService updateBPService = DOService.getService("DO_BusiPackage_Update_copy");
			BusiPackage bporg = BusiPackage.getPackageByName("dorgauth");
			bporg.setApplication(project);
			DAOUtil.INSTANCE().store(bporg,updateBPService);
			

			BusiPackage bpAuthSys = BusiPackage.getPackageByName("auth_system_imp");
			bpAuthSys.setApplication(project);
			DAOUtil.INSTANCE().store(bpAuthSys,updateBPService);

			
			BusiPackage bpLog = BusiPackage.getPackageByName("log_default_imp");
			bpLog.setApplication(project);
			DAOUtil.INSTANCE().store(bpLog,updateBPService);
			

			BusiPackage liuchengceshi = BusiPackage.getPackageByName("liuchengceshi");
			liuchengceshi.setApplication(project);
			DAOUtil.INSTANCE().store(liuchengceshi,updateBPService);
			
			BusiPackage gongzuoliu = BusiPackage.getPackageByName("gongzuoliu");
			gongzuoliu.setApplication(project);
			DAOUtil.INSTANCE().store(gongzuoliu,updateBPService);
			
			BusiPackage wf_history = BusiPackage.getPackageByName("wf_history");
			wf_history.setApplication(project);
			DAOUtil.INSTANCE().store(wf_history,updateBPService);

			/**
			 * �洢Ӧ�õĸ����
			 */
			DOPaneModel pmRoot = new DOPaneModel();
			pmRoot.setCategory(aBO);
			pmRoot.setName("pane_" + project.getName());
			pmRoot.setL10n(project.getL10n() + "_�����");
			pmRoot.setTitle(project.getDescription());

			// //////////////ccLayOutPane
			pmRoot.setController(contentPane);

			DAOUtil.INSTANCE().store(pmRoot);

			// ///////ҵ����󷢲�Ϊһ��Ӧ��
			project.setDobo(aBO);
			DAOUtil.INSTANCE().store(project);
			
			////ͷ��jsp
			DOResource rs = new DOResource();
			rs.setResourceName("jspheader_" + project.getName());
			rs.setResourcePath(project.getName() + "/FormHeader.jsp");
			rs.setResourceType(1);
			DAOUtil.INSTANCE().store(rs);
																	
			/**
			 * �����µ�ͷ���
			 */
			DOPaneModel pmTop = new DOPaneModel();

			pmTop.setController(layOutHeader);
			pmTop.setLinkType(DOPaneModel.LINKTYPE_RESOURCE);
			pmTop.setLinkUID(rs.getObjUid());
			pmTop.setLayoutAlign("top");
			pmTop.setCategory(aBO);
			pmTop.setL10n(project.getL10n() + "_ͷ���");
			pmTop.setName(project.getName() + "_headerPane");
			DAOUtil.INSTANCE().store(pmTop);

			/**
			 * ����������ͷ���Ĺ�����ϵ
			 */
			DOPaneLinks pmTopLink = new DOPaneLinks();
			pmTopLink.setParentPane(pmRoot);
			pmTopLink.setChildPane(pmTop);
			pmTopLink.setOrderNum(new Integer(5));
			DAOUtil.INSTANCE().store(pmTopLink, aService);

			/**
			 * �����·������
			 */
			DOPaneModel pmBottom = new DOPaneModel();
			pmBottom.setCategory(aBO);
			pmBottom.setL10n(project.getL10n() + "_�����������");
			pmBottom.setName(project.getName() + "_mainpane");

			pmBottom.setController(ccSplitePane);
			DAOUtil.INSTANCE().store(pmBottom);
			/**
			 * ���������͹����������Ĺ���
			 */

			DOPaneLinks pmBottomLink = new DOPaneLinks();
			pmBottomLink.setParentPane(pmRoot);
			pmBottomLink.setChildPane(pmBottom);
			pmBottomLink.setOrderNum(new Integer(10));
			DAOUtil.INSTANCE().store(pmBottomLink, aService);

			// /������

			DOMenuModel dmRoot = new DOMenuModel();
			dmRoot.setCategory(aBO);
			dmRoot.setController(menuController); 
			// ////////ͬ��DOMenuModel
			dmRoot.setName(project.getName() + "_root");
			dmRoot.setL10n("��ӭʹ��" + project.getL10n());
			DAOUtil.INSTANCE().store(dmRoot);
			
			
			////���²˵���ȡ��sql���
		 	DOService menuService = DOService.getService("s_menumodel_byName");
		 	menuService.setMainSql("select * from do_ui_menumodel where name = '" + dmRoot.getName() + "'");
		 	DAOUtil.INSTANCE().store(menuService);
		 	 

			DOMenuModel dmBP = new DOMenuModel();
			dmBP.setCategory(aBO);
			dmBP.setParentMenu(dmRoot);
			dmBP.setController(menuController);
			// ////////ͬ��DOMenuModel
			dmBP.setName(project.getName() + "_bp");
			dmBP.setL10n(project.getL10n() + "�˵�");
			DAOUtil.INSTANCE().store(dmBP);
			
			
			///////��֯Ȩ����صĲ˵� ��ʼ�������̸��˵�����
			DOMenuModel dmmAuth = DOMenuModel.getMenuModelByName("dorgauth_bp");
			dmmAuth.setParentMenu(dmRoot);
			DAOUtil.INSTANCE().store(dmmAuth);
						
			///�ѹ�������صĲ˵���ʼ�������̸��˵�����
			DOMenuModel dorgauth_flow_test  = DOMenuModel.getMenuModelByName("dorgauth_flow_test");
			dorgauth_flow_test .setParentMenu(dmRoot);
			DAOUtil.INSTANCE().store(dorgauth_flow_test );
						
			
			
			////����̨jsp
			rs = new DOResource();
			rs.setResourceName("workbenchjsp_" + project.getName());
			rs.setResourcePath(project.getName() + "/workbench.jsp");
			rs.setResourceType(1);
			DAOUtil.INSTANCE().store(rs);
			
			DOPaneModel pmContent = new DOPaneModel();// ///��������ʾ����
			pmContent.setCategory(aBO);
			pmContent.setName(project.getName() + "_MainContent");
			pmContent.setL10n(project.getL10n() + "����������ʾ");
			pmContent.setController(mainPage);
			pmContent.setLinkType(DOPaneModel.LINKTYPE_RESOURCE);
			pmContent.setLinkUID(rs.getObjUid());
			DAOUtil.INSTANCE().store(pmContent);

			DOPaneLinks pmContentLink = new DOPaneLinks();
			pmContentLink.setParentPane(pmBottom);// //bottomPaneΪ����
			pmContentLink.setChildPane(pmContent);
			pmContentLink.setOrderNum(new Integer(17));
			DAOUtil.INSTANCE().store(pmContentLink, aService);

			DOPaneModel pmLeft = new DOPaneModel();
			pmLeft.setController(layOutLeft);
			pmLeft.setLinkType(DOPaneModel.LINKTYPE_MENU);
			pmLeft.setLinkUID(dmRoot.getObjUid());
			pmLeft.setCategory(aBO);
			pmLeft.setL10n(project.getL10n() + "����������");
			pmLeft.setName(project.getName() + "_leftindex_pane");

			/**
			 * ������ߵ���ߵ������˵�
			 */
			pmLeft.setTargetPane(pmContent);
			DAOUtil.INSTANCE().store(pmLeft);

			DOPaneLinks pmLeftLink = new DOPaneLinks();
			pmLeftLink.setParentPane(pmBottom);// //bottomPaneΪ����
			pmLeftLink.setChildPane(pmLeft);
			pmLeftLink.setOrderNum(new Integer(15));
			DAOUtil.INSTANCE().store(pmLeftLink, aService);
			
////���copy
			this.copyDir(project);

		} catch (Exception e) {

			t.rollback();
			e.printStackTrace();
		} finally {
			t.end();
		}
	
		// /ִ��Ŀ¼copy����
	}

	void copyDir(DOApplication dop) {


		URL url = DOGlobals.class.getResource("/globals.xml");
		String fullFilePath = url.getPath();
		String prefix = fullFilePath.substring(0, fullFilePath.toLowerCase()
				.indexOf("web-inf"));

		try {
			StringUtil.copyDirectiory(prefix + dop.getName(), prefix
					+ "exedo/baseproject/");

			dealAFile(prefix + dop.getName() +"/index.jsp","pane_dorgauth","pane_" + dop.getName());
			dealAFile(prefix + dop.getName() +"/FormHeader.jsp","baseproject/logoff.jsp",dop.getName() + "/logoff.jsp");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void dealAFile(String theFile,String theSrc, String theRepla)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		File indexFile = new File(theFile);

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(indexFile),"utf-8"));   
		StringBuffer sb = new StringBuffer();
		while (true) {
			String aLine = in.readLine();
			if(aLine==null){
				break;
			}
			System.out.println(aLine);
			if(aLine.indexOf(theSrc)!=-1){
				aLine =	aLine.replace(theSrc, theRepla); 
			}
			sb.append(aLine).append("\n\r");
		}
		in.close();

		
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new  FileOutputStream(indexFile),"utf-8"));
		out.append(sb.toString());
		out.flush();
		out.close();
	}

	public static void main(String[] args) throws ExedoException {

		

		DOService resourceInsert  = DOService.getService("do_bo_insert");
		Map paras = new HashMap();
		paras.put("resourceName", "jspheader_aaaa");
		paras.put("resourcePath", "aaaa/FormHeader.jsp");
		paras.put("resourceType", "1");
//		BOInstance newResource = resourceInsert.invokeUpdate(paras);
//
//		////ͷ��jsp
//		DOResource rs = DOResource.getResourceByID(newResource.getUid());
		System.out.println("Hello:::::::::" + resourceInsert);
																
	}

}
