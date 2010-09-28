package com.exedosoft.wf.pt;


import java.io.Serializable;
import java.util.Iterator;
import java.util.List;


import com.exedosoft.plat.bo.BaseObject;
import com.exedosoft.plat.bo.DOBO;
//import com.exedosoft.plat.dao.DAOException;
//import com.exedosoft.plat.dao.HbmDAO;
//import com.exedosoft.plat.dao.WFDAO;
import com.exedosoft.plat.ui.DOPaneModel;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.wf.wfi.ProcessInstance;

/**
 * ���ؾ����ھ���Ĺ��������ۡ��������������������ɣ���һ���������������������ɡ� <p> Title: </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: </p>
 * @author  not attributable
 * @version  1.0
 */

public class ProcessTemplate extends BaseObject  implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 2050600762872364939L;


	private String ptName;


	private String ptDesc;

	/**
	 * �������أ��Ƿ������ڶ�̬�������ķ���
	 *
	 */
	private Boolean active;



	private Boolean isModify;

	/**
	 * ���̶�Ӧ����ҵ�����ÿ��������ʵ��һ����Ӧ������ҵ���������ҵ����ص�ҵ����� ���߷�������������һ��ҵ����������һ�����̡� ����ÿ��ҵ������ж���һ�����̵�Ԫ�ء� ҵ�����������������ԣ����������̡� ������ҵ�����������棬ҵ�������֯ҵ��
	 */
	private DOBO doBO;
	

	private DOBO doBO2;
	

	private DOBO doBO3;
	

	private DOPaneModel pane;

	public ProcessTemplate() {
	}

	/** minimal constructor */
	public ProcessTemplate(Long objUID, java.lang.String ptName) {

		this.ptName = ptName;
	}

	/**
	 * @return
	 * @uml.property  name="ptName"
	 */
	public java.lang.String getPtName() {
		return this.ptName;
	}

	/**
	 * @param ptName
	 * @uml.property  name="ptName"
	 */
	public void setPtName(java.lang.String ptName) {
		this.ptName = ptName;
	}

	/**
	 * @return
	 * @uml.property  name="ptDesc"
	 */
	public java.lang.String getPtDesc() {
		return this.ptDesc;
	}

	/**
	 * @param ptDesc
	 * @uml.property  name="ptDesc"
	 */
	public void setPtDesc(java.lang.String ptDesc) {
		this.ptDesc = ptDesc;
	}

	/**
	 * @return
	 * @uml.property  name="active"
	 */
	public Boolean getActive() {
		return this.active;
	}

	/**
	 * @param active
	 * @uml.property  name="active"
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return
	 * @uml.property  name="isModify"
	 */
	public Boolean getIsModify() {
		return isModify;
	}

	/**
	 * @param isModify
	 * @uml.property  name="isModify"
	 */
	public void setIsModify(Boolean isModify) {
		this.isModify = isModify;
	}

	/**
	 * @return
	 * @uml.property  name="doBO"
	 */
	public DOBO getDoBO() {
		return doBO;
	}

	/**
	 * @param doBO
	 * @uml.property  name="doBO"
	 */
	public void setDoBO(DOBO doBO) {
		this.doBO = doBO;
	}
	
	/**
	 * @return
	 * @uml.property  name="pane"
	 */
	public DOPaneModel getPane() {
		return pane;
	}

	/**
	 * @param pane
	 * @uml.property  name="pane"
	 */
	public void setPane(DOPaneModel pane) {
		this.pane = pane;
	}
	

	
	public List retrievePtVars(){
		
//		HbmDAO dao = new HbmDAO();
		String hql = "select ptvar.* from DO_PT_Var ptvar where  ptvar.pt_Uid = ? ";
		return  DAOUtil.INSTANCE().select(PTVar.class, hql, this.getObjUid());
//		try {
//			return dao.list(hql, this.getObjUid());
//		} catch (DAOException e) {
//			e.printStackTrace();
//			return null;
//		}
		
	}


	/**
	 * ��ÿ��ԶԹ�����ʵ������������PTVar
	 * 
	 * @return
	 */

	/**
	 * ��ø�ģ��Ľڵ�֮���������ϵ
	 * 
	 * @return
	 */
	public List getNodeDependency() {

//		HbmDAO dao = new HbmDAO();
		String hql = "select nd.* from DO_PT_Node_Denpendency nd,DO_PT_Node node where nd.Pre_N_UID = node.objuid and  node.PT_UID = ? ";
		return  DAOUtil.INSTANCE().select(com.exedosoft.wf.pt.NodeDenpendency.class, hql, this.getObjUid());
//		try {
//			return dao.list(hql, this.getObjUid());
//		} catch (DAOException e) {
//			e.printStackTrace();
//			return null;
//		}
	}

	/**
	 * ��ø�ģ��Ľڵ�֮���������ϵ
	 * 
	 * @return
	 */
	public List retrieveNodes() {

//		HbmDAO dao = new HbmDAO();
		String hql = "select node.* from DO_PT_Node node where  node.PT_UID = ? ";
		return DAOUtil.INSTANCE().select(PTNode.class, hql, this.getObjUid());
//		try {
//			return dao.list(hql, this.getObjUid());
//		} catch (DAOException e) {
//			e.printStackTrace();
//			return null;
//		}
	}

	/**
	 * ��ȡ���ģ�嶨���Start �ڵ㣬Ҳ��Ψһ��һ���ڵ㡣
	 * 
	 * @return
	 */
	public PTNode getStartNode() {

		List nodes = this.retrieveNodes();
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			PTNode node = (PTNode) it.next();
			if (node.getNodeType() != null
					&& node.getNodeType().intValue() == PTNode.TYPE_START) {
				return node;
			}
		}
		return null;

	}
	
	public PTNode getFirstActivityNode() {

		PTNode startNode = this.getStartNode();
		if(startNode==null){
			return null;
		}
		List<PTNode> firsts = startNode.getPostNodes();
		if(firsts!=null && firsts.size()>0){
			return firsts.get(0);
		}
		return null;

	}

	public boolean isAccess() {

		PTNode startNode = this.getStartNode();
		if (startNode == null) {
			return false;
		}
		List postNodes = startNode.getPostNodes();
		for (Iterator it = postNodes.iterator(); it.hasNext();) {
			PTNode aPostNode = (PTNode) it.next();
			if (aPostNode.isAccess()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��ø�ģ��Ľڵ�View֮�������
	 * 
	 * @return
	 */
	public String toString() {
		return ptName;
	}

	public static ProcessTemplate getPTByName(String aName) {
		
		return  DAOUtil.INSTANCE().getByProperty(ProcessTemplate.class, "PT_Name", aName);

//		HbmDAO dao = new HbmDAO();
//		String hql = "select pt from ProcessTemplate pt where pt.ptName = ?";
//		try {
//			List list = dao.list(hql, aName);
//			if (list.size() > 0) {
//				return (ProcessTemplate) list.get(0);
//			}
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return null;

	}
	
	public static ProcessTemplate getPTByID(String aID) {
		
		return DAOUtil.INSTANCE().getByObjUid(ProcessTemplate.class, aID);

//		HbmDAO dao = new HbmDAO();
//		String hql = "select pt from ProcessTemplate pt where pt.id = ?";
//		try {
//			List list = dao.list(hql, aID);
//			if (list.size() > 0) {
//				return (ProcessTemplate) list.get(0);
//			}
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return null;
	}
	
	public List getProcessInstances(){
		
//		WFDAO dao = new WFDAO();
		String hql = "select pi.* from do_wfi_processinstance pi where pi.PT_UID = ?";
		return DAOUtil.BUSI().select(ProcessInstance.class, hql, this.getObjUid());
		
		
//		List list = null;
//		try {
//			list = dao.list(hql,this.getObjUid());
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
		
	}


	public DOBO getDoBO2() {
		return doBO2;
	}


	public void setDoBO2(DOBO doBO2) {
		this.doBO2 = doBO2;
	}


	public DOBO getDoBO3() {
		return doBO3;
	}


	public void setDoBO3(DOBO doBO3) {
		this.doBO3 = doBO3;
	}
	
	public static void main(String[] args){
		}



}