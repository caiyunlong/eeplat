package com.exedosoft.plat.gene;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.bo.BusiPackage;
import com.exedosoft.plat.bo.DOApplication;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DODataSource;

import com.exedosoft.plat.ui.DOController;
import com.exedosoft.plat.ui.DOMenuLinks;
import com.exedosoft.plat.ui.DOMenuModel;
import com.exedosoft.plat.ui.DOPaneLinks;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.jquery.pane.ContentPane;
import com.exedosoft.plat.ui.jquery.pane.LayOutSplitPane;


/**
 * ʹ�÷ֲ�ʽ���ÿ⣬ʵ������̫����
 * ���ǿ��Զ�̬�л����ÿ�
 * ����ʹ�õ��롢������ʽ��ʵ���Ŷ�Э������
 * 
 * ���롢�����ķ�ʽ����ʹ��xml�ļ���sql�ļ���
 * ���롢���������ȣ�
 * 1������
 * 2��ҵ���
 * 3��ҵ�����
 * 4������
 * 5�����
 * 6�����
 * 
 * ����һ�� ���԰����ڡ�
 * ����ĳ������֮��ģ�ȫ������ȥ
 * 
 *   ������С���ȣ����ʵ���Ƿ��ǣ�
 *   
 *   ����  ���� ���Ԫ��  �� ���ڵ�  �˵�  
 * 
 * 
 * 
 * 
 * 
 * @author IBM
 *
 */

public class AProjectForwarder {

	DOController ccLayOutPane = DOController
			.getControllerByName(LayOutSplitPane.class.getName());

	DOController ccSplitePane = DOController
			.getControllerByName(LayOutSplitPane.class.getName());

	DOController cContentPane = DOController
			.getControllerByName(ContentPane.class.getName());

	/**
	 * ����˵�������
	 */
	DOController ccTopPane = DOController
			.getControllerByName("topMenuController");

	/**
	 * ContentPane,�����й�����
	 */
	DOController ccTreePane = DOController
			.getControllerByName("exedo_pane_controller_overflow");
	
	

	/**
	 * MenuController,�����й�����
	 */
	DOController menuController = DOController
			.getControllerByName("com.exedosoft.plat.ui.defaultimp.menu.DOJOMenuXP");

	public void forwardBaseUI(String projectUid) {

		DOApplication project = DOApplication.getApplicationByID(projectUid);
//		HbmDAO dao = new HbmDAO();
//		dao.setAutoClose(false);

		// //������ҵ���,ͬ��ҵ���
		BusiPackage bp = new BusiPackage();
		bp.setApplication(project);
		bp.setL10n(project.getL10n());
		bp.setName(project.getName());

		DOBO aBO = new DOBO();
		aBO.setType(DOBO.TYPE_BUSINESS);
		aBO.setName(project.getName() + "global.bo");
		aBO.setL10n(project.getL10n() + "ȫ��ҵ�����");
		
		DODataSource dds = DODataSource.parseGlobals();
		
		Transaction t = dds.getTransaction();
		
		t.begin();
		
		try {
			// //////////����ҵ���
			DAOUtil.INSTANCE().store(bp);

			// //����ҵ�����
			aBO.setPakage(bp);
			DAOUtil.INSTANCE().store(aBO);

			/**
			 * �洢Ӧ�õĸ����
			 */
			DOPaneModel pmRoot = new DOPaneModel();
			pmRoot.setCategory(aBO);
			pmRoot.setName("pane_" + project.getName());
			pmRoot.setL10n("�����" + project.getL10n());
			pmRoot.setTitle(project.getDescription());
			pmRoot.setLayOutType(Integer.valueOf(DOPaneModel.LAYOUT_VERTICAL));

			// //////////////ccLayOutPane
			pmRoot.setController(ccLayOutPane);

			DAOUtil.INSTANCE().store(pmRoot);

			// ///////ҵ����󷢲�Ϊһ��Ӧ��
		//	project.setDobo(aBO);
			DAOUtil.INSTANCE().store(project);

			/**
			 * ��pane_mainMenuBar copy��Ϣ����
			 */
			DOPaneModel fromPmTop = DOPaneModel
					.getPaneModelByName("BaseAnoleHeader");
			/**
			 * �����µ�ͷ���
			 */
			DOPaneModel pmTop = new DOPaneModel();

			pmTop.setController(fromPmTop.getController());
			pmTop.setLayOutType(fromPmTop.getLayOutType());
			pmTop.setLinkType(fromPmTop.getLinkType());
			pmTop.setLinkUID(fromPmTop.getLinkUID());
			pmTop.setLayoutAlign("top");
			pmTop.setCategory(aBO);

			pmTop.setTargetPane(fromPmTop.getTargetPane());
			pmTop.setL10n(project.getL10n() + "ͷ���");
			pmTop.setName(project.getName() + "header_pane");
			DAOUtil.INSTANCE().store(pmTop);

			/**
			 * ����������ͷ���Ĺ�����ϵ
			 */
			DOPaneLinks pmTopLink = new DOPaneLinks();
			pmTopLink.setParentPane(pmRoot);
			pmTopLink.setChildPane(pmTop);
			pmTopLink.setOrderNum(new Integer(5));
			DAOUtil.INSTANCE().store(pmTopLink);

			/**
			 * �����·������
			 */
			DOPaneModel pmBottom = new DOPaneModel();
			pmBottom.setCategory(aBO);
			pmBottom.setL10n(project.getL10n() + "�����������");
			pmBottom.setName(project.getName() + "main_pane");
			pmBottom.setLayOutType(Integer
					.valueOf(DOPaneModel.LAYOUT_HORIZONTAL));
			// ///////splitPane

			pmBottom.setController(ccSplitePane);
			pmBottom.setLayoutAlign("client");
			DAOUtil.INSTANCE().store(pmBottom);
			/**
			 * ���������͹����������Ĺ���
			 */

			DOPaneLinks pmBottomLink = new DOPaneLinks();
			pmBottomLink.setParentPane(pmRoot);
			pmBottomLink.setChildPane(pmBottom);
			pmBottomLink.setOrderNum(new Integer(10));
			DAOUtil.INSTANCE().store(pmBottomLink);

			/**
			 * ��������������
			 */

			DOPaneModel fromPmLeft = DOPaneModel
					.getPaneModelByName("pane_zf_left_xp");
			DOPaneModel pmLeft = new DOPaneModel();

			

			pmLeft.setController(fromPmLeft.getController());
			pmLeft.setLayOutType(fromPmLeft.getLayOutType());
			pmLeft.setLinkType(fromPmLeft.getLinkType());
			pmLeft.setLinkUID(fromPmLeft.getLinkUID());
			pmLeft.setLayoutAlign(fromPmLeft.getLayoutAlign());
			pmLeft.setCategory(aBO);

			pmLeft.setTargetPane(fromPmLeft.getTargetPane());
			pmLeft.setL10n(project.getL10n() + "����������");
			pmLeft.setName(project.getName() + "leftindex_pane");

			pmLeft.setController(ccTreePane);
			
			DAOUtil.INSTANCE().store(pmLeft);
			

			DOPaneLinks pmLeftLink = new DOPaneLinks();
			pmLeftLink.setParentPane(pmBottom);// //bottomPaneΪ����
			pmLeftLink.setChildPane(pmLeft);
			pmLeftLink.setOrderNum(new Integer(15));
			DAOUtil.INSTANCE().store(pmLeftLink);

			DOPaneModel pmContent = new DOPaneModel();// ///��������ʾ����
			pmContent.setCategory(aBO);
			pmContent.setName(project.getName() + "_MainContent");
			pmContent.setL10n(project.getL10n() + "����������ʾ");


			pmContent.setController(cContentPane);
			DAOUtil.INSTANCE().store(pmContent);

			DOPaneLinks pmContentLink = new DOPaneLinks();
			pmContentLink.setParentPane(pmBottom);// //bottomPaneΪ����
			pmContentLink.setChildPane(pmContent);
			pmContentLink.setOrderNum(new Integer(17));
			DAOUtil.INSTANCE().store(pmContentLink);

			/**
			 * ������ߵ���ߵ������˵�
			 */
			pmLeft.setTargetPane(pmContent);
			
			
			DOMenuModel dmRoot = new DOMenuModel();
			dmRoot.setCategory(aBO);
			dmRoot.setController(menuController);
			//////////ͬ��DOMenuModel
			dmRoot.setName(project.getName()+"_root");
			dmRoot.setL10n(project.getL10n()+"_���˵�");
			DAOUtil.INSTANCE().store(dmRoot);
			
			
			DOMenuModel dmBP = new DOMenuModel();
			dmBP.setCategory(aBO);
			dmBP.setParentMenu(dmRoot);
			dmBP.setController(menuController);
			//////////ͬ��DOMenuModel
			dmBP.setName(project.getName()+"_bp");
			dmBP.setL10n(project.getL10n()+"_��ҵ�����");
			DAOUtil.INSTANCE().store(dmBP);
			
			DOMenuLinks links = new DOMenuLinks();
			links.setLinkType(DOMenuLinks.LINKTYPE_PANEMODEL);
			links.setLinkUID(pmLeft.getObjUid());
			links.setMenuModel(dmRoot);
			links.setOrderNum(Integer.valueOf(1000));
			DAOUtil.INSTANCE().store(links);
				
			
			DAOUtil.INSTANCE().store(pmLeft);

			
		 

		} catch (Exception e) {
			
			t.rollback();
			e.printStackTrace();
		}
		finally {
			t.end();
		}
	}
	
	public static void main(String[] args){
		AProjectForwarder  af = new AProjectForwarder();
		af.forwardBaseUI("297e276a0d1f7763010d1f776e5f0001");
		
	}
}
