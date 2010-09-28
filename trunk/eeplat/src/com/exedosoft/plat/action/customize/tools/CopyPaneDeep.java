package com.exedosoft.plat.action.customize.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOFormTarget;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOPaneModel;

public class CopyPaneDeep extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5689928718730452223L;

	@Override
	public String excute() throws ExedoException {

		if (this.service == null || this.service.getTempSql() == null) {
			System.out.println("δ����SQL ���");
			this.setEchoValue("δ����SQL ���");
			return NO_FORWARD;
		}

		Transaction t = this.service.currentTransaction();
		try {
			t.begin();
			DOBO boPane = DOBO.getDOBOByName("do_ui_panemodel");
			DOBO boGrid = DOBO.getDOBOByName("do_ui_gridmodel");
			DOBO boForm = DOBO.getDOBOByName("do_ui_formmodel");
			DOBO boFormLink = DOBO.getDOBOByName("DO_UI_FormLinks");
			DOBO boFormTarget = DOBO.getDOBOByName("DO_UI_FormTargets");

			// /��Ҫ������������ӣ�������ڲ������ƣ�
			BOInstance biPane = boPane.getCorrInstance();
			DOPaneModel pm = DOPaneModel.getPaneModelByID(biPane.getUid());

			DOGridModel gm = pm.getGridModel();
			// //������ӵ��Ǳ��:::::��ʱֻʵ�����
			if (gm != null) {

				// /////����GridModel
				BOInstance biGrid = boGrid.getInstance(gm.getObjUid());
				biGrid.putValue("objuid", null);
				biGrid.putValue("name", biGrid.getValue("name") + "_copy");
				biGrid.putValue("l10n", biGrid.getValue("l10n") + "_copy");
				BOInstance newBiGrid = boGrid.getDInsertService().invokeUpdate(
						biGrid);

				// //����GridModel

				// /�������
				biPane.putValue("objuid", null);
				biPane.putValue("name", biPane.getValue("name") + "_copy");
				biPane.putValue("l10n", biPane.getValue("l10n") + "_copy");
				biPane.putValue("linkuid", newBiGrid.getUid());				
				boPane.getDInsertService().invokeUpdate(biPane);
				// /�������

				List<DOFormModel> fms = gm.getAllGridFormLinks();
				Map<DOFormModel,BOInstance> map = new HashMap<DOFormModel,BOInstance>();
				for (Iterator<DOFormModel> it = fms.iterator(); it.hasNext();) {
					DOFormModel aFm = it.next();
					// ///����FormModel
					BOInstance biForm = boForm.getInstance(aFm.getObjUid());
					biForm.putValue("objuid", null);
					biForm.putValue("gridModelUid", newBiGrid.getUid());
					BOInstance newBiForm = boForm.getDInsertService().invokeUpdate(biForm);
					map.put(aFm, newBiForm);
					// //����FormModel
					for(Iterator<DOFormTarget> itTargetGrid = aFm.getTargetGridModels().iterator();itTargetGrid.hasNext(); ){
						DOFormTarget aFt = itTargetGrid.next();
						BOInstance biFt = boFormTarget.getInstance(aFt.getObjUid());
						biFt.putValue("objuid", null);
						biFt.putValue("formUid", newBiForm.getUid());
						boFormTarget.getDInsertService().invokeUpdate(biFt);						
					}
				}

				/////linkForms ��Ҫ��������
				for (Iterator<DOFormModel> it = fms.iterator(); it.hasNext();) {
					DOFormModel aFm = it.next();
					BOInstance newBiForm = map.get(aFm);
					// //FormModel linkForms
					for(Iterator<DOFormModel> itLinkForms = aFm.getLinkForms().iterator(); itLinkForms.hasNext();){
						DOFormModel linkForm = itLinkForms.next();
						BOInstance newLinkBiForm = new BOInstance();
						if(map.get(linkForm)!=null){
							newLinkBiForm = map.get(linkForm);
						}else{
							newLinkBiForm = boForm.getInstance(linkForm.getObjUid());
						}
						//  
						Map<String,String> paras = new HashMap<String,String>();
						paras.put("formuid", newBiForm.getUid());
						paras.put("linkformuid", newLinkBiForm.getUid());
						boFormLink.getDInsertService().invokeUpdate(paras);
					}

				}

			}

			t.end();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		}
		this.setEchoValue("���Ƴɹ�,����������ڵ���в���!");
		return DEFAULT_FORWARD;

	}

}
