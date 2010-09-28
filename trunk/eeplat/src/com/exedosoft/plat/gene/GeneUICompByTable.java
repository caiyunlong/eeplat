package com.exedosoft.plat.gene;

import java.util.Iterator;
import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.bo.DODataSource;
import com.exedosoft.plat.bo.DOBOProperty;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.bo.DOServiceRedirect;
import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.ui.DOController;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;

import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.jquery.form.TPane;
import com.exedosoft.plat.ui.jquery.form.TService;
import com.exedosoft.plat.ui.jquery.form.TClose;
import com.exedosoft.plat.ui.jquery.form.my97.DatePicker;
import com.exedosoft.plat.ui.jquery.form.DOInputText;
import com.exedosoft.plat.ui.jquery.form.DOTextArea;
import com.exedosoft.plat.ui.jquery.form.DOValueDate;
import com.exedosoft.plat.ui.jquery.form.DOValueSimple;

import com.exedosoft.plat.ui.jquery.grid.GridList;

import com.exedosoft.plat.ui.jquery.pane.ContentPane;
import com.exedosoft.plat.ui.jquery.pane.ContentPaneScroll;
import com.exedosoft.plat.util.StringUtil;

public class GeneUICompByTable  {

	private static Log log = LogFactory.getLog(GeneUICompByTable.class);

	private final String SQL_SELECT_SERVICE = "select service.* from DO_Service service,DO_BO bo where service.bouid = bo.objuid  bo.name = ?";


	private DOController gridList = null;

	private DOController gridBrowse = null;

	private DOController gridInsert = null;

	private DOController gridUpdate = null;

	
	private DOController formTextArea = null;
	
	
	private DOController formTextC = null;

	private DOController formValueTextC = null;

	private DOController formValueDate = null;

	private DOController formTimeC = null;

	private DOController formSaveButton = null;

	private DOController formCloseButton = null;

	private DOController paneC = null;

	private DOController paneOverFlow = null;

	private DOController  formDelete = null;

	// /////////////////////����������ͻ�ȡbp,��Ϊ������������ʵ��ʵ����ʱ��ִ����

	private String geneATable = "";

	private String boUID;

	public GeneUICompByTable(String aTable, String aBOUID) {
		aTable = StringUtil.getDotName(aTable);
		this.geneATable = aTable;
		this.boUID = aBOUID;
	}

	public void geneConfig() {

		initController();
//
//		HbmDAO dao = new HbmDAO();
//		dao.setAutoClose(false);

		// //first gene component!
//		this.geneForms(dao);
		
		DODataSource dss = DODataSource.parseGlobals();
		Transaction t = dss.getTransaction();
		
	

		// ///////////////////second generator grid and panes
		try {
			t.begin();
			List sers = DAOUtil.INSTANCE().select(DOService.class, SQL_SELECT_SERVICE,this.geneATable);

			/**
			 * ����Service����ui�����
			 */
			for (Iterator itServ = sers.iterator(); itServ.hasNext();) {

				DOService aService = (DOService) itServ.next();

				if (aService.getName().endsWith("browse")) {
					genePaneAndGrid( aService, gridBrowse, "");
					genePaneAndGrid( aService, gridUpdate, ".update");
				} else if (aService.getName().endsWith("insert")) {
					continue;
				} else if (aService.getName().endsWith("update")) {
					continue;
				} else if (aService.getName().endsWith("list")) {
					genePaneAndGrid( aService, gridList, "");
					genePaneAndGrid( aService, gridInsert, ".insert");
				}
			}



		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			t.end();
		}
	}

//	private void geneForms(HbmDAO dao) {
//
//		try {
//			List list = dao.list(selectProperty, this.geneATable);
//			for (Iterator it = list.iterator(); it.hasNext();) {
//				DOBOProperty prop = (DOBOProperty) it.next();
//				DOFormModel formModel = new DOFormModel();
//				formModel.setRelationProperty(prop);
//				if (prop.isNumberType()) {
//					formModel.setExedojoType("RealNumber");
//				}
//
//				formModel.setL10n(prop.getColName());
//				dao.store(formModel);
//				log.info("���ڴ�" + prop.getColName() + "���ɽ������.....");
//			}
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}



	/**
	 * @param dao
	 * @param aService
	 * @throws ExedoException 
	 * @throws DAOException
	 */
	private void genePaneAndGrid(DOService aService,
			DOController controller, String aName) throws ExedoException  {

		DOGridModel gridM = new DOGridModel();
		gridM.setCategory(aService.getBo());
		gridM.setCaption(aService.getL10n());
		gridM.setName("grid_" + aService.getName() + aName);
		gridM.setL10n("grid_" + aService.getName() + aName);

		gridM.setService(aService);
		gridM.setController(controller);
		gridM.setColNum(Integer.valueOf(2));
		gridM.setCategory(aService.getBo());
		DAOUtil.INSTANCE().store(gridM);

		int i = 1;
		
		/////��������� ԭ����aService.retrieveProperties
		for (Iterator itProp = aService.getBo().retrieveProperties().iterator(); itProp
				.hasNext();) {

			DOBOProperty prop = (DOBOProperty) itProp.next();
			if (prop.isKeyCol()) {
				continue;
			}
			DOFormModel formM = new DOFormModel();
			formM.setRelationProperty(prop);
			
			
			/**
			 *  * �ͻ�����֤���ã���Ϊ�����֣���;���� �������ͣ�Integer RealNumber EMail Text Others 2, ���� ��,
				 * ����Script Լ��
				 * 
			 */
				
			
			if (prop.isNumberType()) {
				
				String  exedoType = "RealNumber";
				formM.setExedojoType(exedoType);
			}else if(!prop.isDateOrTimeType()){
				formM.setExedojoType(";"+ (int)(prop.getDbSize().intValue()/2));
			}

			formM.setL10n(prop.getColName());
			formM.setGridModel(gridM);

			
			formM.setOrderNum(Integer.valueOf(i * 5));
			if (prop.isDateOrTimeType()) {
				if ("".equals(aName)) {
					formM.setController(formValueDate);
				} else {
					formM.setController(formTimeC);
				}
			} else {
				if ("".equals(aName)) {
					formM.setController(formValueTextC);
				} else {
					
					if(prop.getDbSize()!=null && prop.getDbSize().intValue()>500){
						formM.setController(formTextArea);
						formM.setIsNewLine(DOFormModel.NEWLINE_YES);
					}else{
						formM.setController(formTextC);
					}
				}

			}
			DAOUtil.INSTANCE().store(formM);
			i++;
		}

		if (aService.getName().endsWith(".browse") && "".equals(aName)) {
			geneCloseButtonForm(aService, gridM);

		} else if (aName != null && !aName.equals("")) {
			geneSaveButtonForm(aService, aName, gridM);
		}

		// ��ÿ��Grid���oһ��Pane

		DOPaneModel pane = new DOPaneModel();
		pane.setCategory(aService.getBo());
		pane.setName("pane_" + aService.getName() + aName);

		// ///��һ������ �ǲ������Ʋ��ú�Servie һ��
		pane.setTitle(aService.getName() + "paneModel" + aName);
		pane.setL10n(aService.getName() + "paneModel" + aName);
		pane.setLinkType(Integer.valueOf(DOPaneModel.LINKTYPE_GRIDMODEL));
		pane.setLinkUID(gridM.getObjUid());
		if (aService.getName().endsWith(".browse")) {
			pane.setController(paneOverFlow);
		} else {
			pane.setController(paneC);
		}
		DAOUtil.INSTANCE().store(pane);
	}

	/**
	 * @param dao
	 * @param aService
	 * @param aName
	 * @param gridM
	 * @throws ExedoException 
	 * @throws DAOException
	 *             SELECT fm.*,ug.controllerUid,ug.orderNum FROM DO_UI_FormModel
	 *             fm ,DO_UI_GridLinks ug where fm.objuid = ug.formModelUid and
	 *             fm.objuid = ?
	 */
	private void geneSaveButtonForm( DOService aService,
			String aName, DOGridModel gridM) throws ExedoException  {

		// ///װ�绰С��ͨ, 83747268
		// //װ�绰��˾�绰, 61758100

		DOFormModel formM = new DOFormModel();
		formM.setL10n("����");
		DOService linkService = DOService.getService(aService.getBo().getName()
				+ aName);
		formM.setLinkService(linkService);
		formM.setIsNewLine(1);
		formM.setNameColspan(Integer.valueOf(0));
		formM.setIsOutGridAction(DOFormModel.OUTGRID_BOTTOM);
		// formM.setValueColspan(Integer.valueOf(2));
		formM.setAlign("center");

		
		formM.setGridModel(gridM);
		formM.setOrderNum(Integer.valueOf(1000));
		formM.setController(formSaveButton);

		DAOUtil.INSTANCE().store(formM);

	

	}

	/**
	 * @param dao
	 * @param aService
	 * @param aName
	 * @param gridM
	 * @throws ExedoException 
	 * @throws DAOException
	 *             SELECT fm.*,ug.controllerUid,ug.orderNum FROM DO_UI_FormModel
	 *             fm ,DO_UI_GridLinks ug where fm.objuid = ug.formModelUid and
	 *             fm.objuid = ?
	 */
	private void geneCloseButtonForm( DOService aService,
			DOGridModel gridM) throws ExedoException  {

		// ///װ�绰С��ͨ, 83747268
		// //װ�绰��˾�绰, 61758100

		DOFormModel formM = new DOFormModel();
		formM.setL10n("�ر�");
//		DOService linkService = DOService.getService(aService.getBo().getName()
//				+ ".delete");
//		formM.setLinkService(linkService);
		formM.setIsNewLine(1);
		formM.setIsOutGridAction(DOFormModel.OUTGRID_BOTTOM);
		formM.setNameColspan(Integer.valueOf(0));
		// formM.setValueColspan(Integer.valueOf(2));
		formM.setAlign("center");
		
		formM.setGridModel(gridM);
		formM.setOrderNum(Integer.valueOf(1000));
		formM.setController(formCloseButton);

		DAOUtil.INSTANCE().store(formM);


	}

	private void initController() {

		gridList = DOController.getControllerByName(GridList.class
				.getName());



		formTextC = DOController.getControllerByName(DOInputText.class
				.getName());
		
		formTextArea = DOController.getControllerByName(DOTextArea.class
				.getName());

		formValueTextC = DOController.getControllerByName(DOValueSimple.class
				.getName());

		formValueDate = DOController.getControllerByName(DOValueDate.class
				.getName());

		formTimeC = DOController.getControllerByName(DatePicker.class
				.getName());

		paneC = DOController.getControllerByName(ContentPane.class
				.getName());

		formSaveButton = DOController.getControllerByName(TService.class
				.getName());

		paneOverFlow = DOController
				.getControllerByName(ContentPaneScroll.class.getName());

		formCloseButton = DOController.getControllerByName(TClose.class.getName());

	
		

	}

	

	public static void main(String[] args) {



	}

}
