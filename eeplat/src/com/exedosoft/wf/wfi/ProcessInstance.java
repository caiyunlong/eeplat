package com.exedosoft.wf.wfi;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.BaseObject;
import com.exedosoft.plat.bo.DOBO;
//import com.exedosoft.plat.dao.DAOException;
//import com.exedosoft.plat.dao.HbmDAO;
//import com.exedosoft.plat.dao.WFDAO;
import com.exedosoft.wf.*;
import com.exedosoft.wf.pt.PTNode;
import com.exedosoft.wf.pt.ProcessTemplate;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.ExedoException;


/**
 * @todo
 * @todo  Ӧ�ü�һ������,�ӻ�����ʧ��
 */

public class ProcessInstance extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2602916331402415908L;

	public static int STATUS_INIT = 1;

	public static int STATUS_RUN = 2;

	public static int STATUS_FINISH = 3;

	public static int STATUS_HANGUP = 4;

	public static int STATUS_RESUMING = 5;

	public static int STATUS_FAILURE = 6;

	public static int STATUS_KILLED = 7;

	// /////////////////


	private String wfiName;


	private String ptName;

	private String wfiDesc;




	private String ptUid;


	private Integer exeStatus;

	/**
	 * ��ǰ����״̬��ʾ,���״̬���������ִ��״̬�����ǽڵ�ִ�е���һ����Ҫ��ʾʲô״̬�� ��Ȼ��һ�����̿���ͬʱ�ж����ڵ㡣ÿ���ڵ㶼�п�������nodeStateShow�� �����һ������Ϊ׼�� ��ʵ��Ӧ���У����һ���ڵ��ִ�в���Ӱ���������̵�״̬�����Բ�����nodeStateShow ��������Ͳ��ᴦ��
	 */
	private String curState;


	private Timestamp curStateTime;


	private String curStateUser;


	private String rejectTxt;


	private Timestamp startTime;


	private String startUser;


	private String instanceUid;


	private String instanceUid2;


	private String instanceUid3;

	/** default constructor */
	public ProcessInstance() {
	}

	/** minimal constructor */
	public ProcessInstance(Long objUID, java.lang.String wfiName,
			java.lang.String ptName) {
		this.wfiName = wfiName;
		this.ptName = ptName;
	}

	/**
	 * @return
	 * @uml.property  name="wfiName"
	 */
	public java.lang.String getWfiName() {
		return this.wfiName;
	}

	/**
	 * @param wfiName
	 * @uml.property  name="wfiName"
	 */
	public void setWfiName(java.lang.String wfiName) {
		this.wfiName = wfiName;
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
	 * @uml.property  name="wfiDesc"
	 */
	public java.lang.String getWfiDesc() {
		return this.wfiDesc;
	}

	/**
	 * @param wfiDesc
	 * @uml.property  name="wfiDesc"
	 */
	public void setWfiDesc(java.lang.String wfiDesc) {
		this.wfiDesc = wfiDesc;
	}

	/**
	 * @param exeStatus
	 * @uml.property  name="exeStatus"
	 */
	public void setExeStatus(Integer exeStatus) {
		this.exeStatus = exeStatus;
	}


	public com.exedosoft.wf.pt.ProcessTemplate getProcessTemplate() {
		
		return DAOUtil.INSTANCE().getByObjUid(ProcessTemplate.class,
					this.ptUid);
//		HbmDAO dao = new HbmDAO();
//		ProcessTemplate pt = null;
//		try {
//			pt = (ProcessTemplate) dao.retrieve(ProcessTemplate.class,
//					this.ptUid);
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return pt;
	}

	/**
	 * @return
	 * @uml.property  name="exeStatus"
	 */
	public Integer getExeStatus() {
		return exeStatus;
	}
	
	public static ProcessInstance getProcessInstance(String piUid){
		
		String hql = "select pi.* from do_wfi_processinstance pi where pi.objuid = ?";
		try {
			List list = DAOUtil.BUSI().select(ProcessInstance.class, hql, piUid);
			if (list != null && list.size() > 0) {
				return (ProcessInstance)list.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	public static ProcessInstance getHisProcessInstance(String piUid){
		
		String hql = "select pi.* from do_wfi_his_processinstance pi where pi.objuid = ?";
		try {
			List list = DAOUtil.BUSI().select(ProcessInstance.class, hql, piUid);
			if (list != null && list.size() > 0) {
				return (ProcessInstance)list.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	
	/**
	 * ������ɱ��һ��������ʵ����
	 * 
	 * @throws WFException
	 */
	public static ProcessInstance getProcessInstanceByBusiUId(String aInstanceUid) {
//
//		WFDAO dao = new WFDAO();
		String hql = "select pi.* from do_wfi_processinstance pi where pi.INSTANCE_UID = ?";
		try {
			List list = DAOUtil.BUSI().select(ProcessInstance.class, hql, aInstanceUid);
			if (list != null && list.size() > 0) {
				return (ProcessInstance)list.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * ������ɱ��һ��������ʵ����
	 * 
	 * @throws WFException
	 */
	public static boolean isExistsOfInstanceUid(String aInstanceUid) {
//
//		WFDAO dao = new WFDAO();
		String hql = "select pi.* from do_wfi_processinstance pi where pi.INSTANCE_UID = ?";
		try {
			List list = DAOUtil.BUSI().select(ProcessInstance.class, hql, aInstanceUid);
			if (list != null && list.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * ������ɱ��һ��������ʵ����
	 * 
	 * @throws WFException
	 */
	public void killProcessInstance() throws WFException {
//		WFDAO dao = new WFDAO();
		setExeStatus(new Integer(ProcessInstance.STATUS_KILLED));
		try {
			DAOUtil.BUSI().store(this);
		} catch (Exception ex) {
			throw new WFException("ɱ��������ʵ��ʱ����", ex);
		}

	}

	/**
	 * ����һ��������ʵ����
	 * 
	 * @throws WFException
	 */
	public ProcessInstance hangUpProcessInstance() throws WFException {
//		WFDAO dao = new WFDAO();
		setExeStatus(new Integer(ProcessInstance.STATUS_HANGUP));
		try {
			DAOUtil.BUSI().store(this);
		} catch (Exception ex) {
			throw new WFException("����һ��������ʱ����", ex);
		}
		return this;
	}

	/**
	 * ��øù�����ʵ����Ӧ�����б���
	 * 
	 * @return ��������
	 */
	public List retrieveVarInstances() {
//		WFDAO dao = new WFDAO();
		String hql = "select vi.* from do_wfi_varinstance vi where vi.PI_UID= ?";
		try {
			return DAOUtil.BUSI().select(VarInstance.class, hql, this.getObjUid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List retrieveNodeInstances() {
//
//		WFDAO dao = new WFDAO();
		String hql = "select ni.* from do_wfi_nodeinstance ni where ni.PI_UID = ?";
		try {
			return DAOUtil.BUSI().select(NodeInstance.class, hql, this.getObjUid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����ִ�б�����
	 */
	public ProcessInstance reStartProcess() throws WFException {
//		WFDAO dao = new WFDAO();
//		dao.setAutoClose(false);
		NodeInstance niStart = null;
		try {
			this.setExeStatus(new Integer(ProcessInstance.STATUS_RUN));
			DAOUtil.BUSI().store(this);
			for (Iterator it = this.retrieveNodeInstances().iterator(); it.hasNext();) {
				NodeInstance ni = (NodeInstance) it.next();
				if (ni.getNodeType() != null
						&& ni.getNodeType().intValue() == PTNode.TYPE_START) {
					niStart = ni;
					break;
				}
			}
		} catch (Exception ex) {
			throw new WFException("�޷�������������", ex);
		}
//		finally {
//			dao.closeSession();
//		}
		niStart.execute();
		return this;
	}

	/**
	 * ��ȡ���ģ�嶨���Start �ڵ�ʵ����
	 * 
	 * @return
	 */
	public NodeInstance getStartNode() {

		List nodes = this.retrieveNodeInstances();
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			NodeInstance node = (NodeInstance) it.next();

			if (node.getNodeType() != null
					&& node.getNodeType().intValue() == PTNode.TYPE_START) {
//				List preNodes = node.getPreNodes();
//				if (preNodes != null && preNodes.size() > 0) {
					return node;
				//}
			}
		}
		return null;

	}

	/**
	 * ��ȡ��ʼ���˹��ڵ㡣
	 * 
	 * @return
	 */
	public NodeInstance getFirstActivityNode() {

		List nodes = this.retrieveNodeInstances();
		
		PTNode fnode = this.getProcessTemplate().getFirstActivityNode();
		if(fnode==null){
			return null;
		}
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			NodeInstance node = (NodeInstance) it.next();
			
			if (node.getExeStatus()!=NodeInstance.STATUS_FINISH && node.getNodeUid().equals(fnode.getObjUid())) {
				return node;
			}
		}
		return null;

	}

	public String getCreator() {

		NodeInstance aStartNodeI = this.getStartNode();
		if (aStartNodeI != null) {
			for (Iterator it = aStartNodeI.getPostNodes().iterator(); it
					.hasNext();) {
				NodeInstance fan = (NodeInstance) it.next();
				if (fan.getPerformer() != null) {
					return fan.getPerformer();
				}

			}
		}
		return null;

	}

	public List getRunNodes() {

		List list = new ArrayList();

		for (Iterator it = this.retrieveNodeInstances().iterator(); it
				.hasNext();) {
			NodeInstance ni = (NodeInstance) it.next();
			if (ni.getExeStatus() != null
					&& ni.getExeStatus().intValue() == NodeInstance.STATUS_RUN) {
				list.add(ni);
			}
		}
		return list;

	}

//	public String getParentNodeInstanceUID() {

//		WFDAO dao = new WFDAO();
//		String hql = "select pi.wfiExeTask.parentNI from DO_WFI_PROCESSINSTANCE pi where pi.wfiExeTask is not null and pi.id = ?";
		
//		List list = null;
//		try {
//			list = dao.list(hql, this.getObjUid());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (list == null || list.size() == 0) {
//			return null;
//		} else {
//			return (String) list.get(0);
//		}
//	}

	/**
	 * һ�������ȡ�ز�����ֱ��ȡ�ص���ʼ�ڵ㡣
	 * 
	 * @throws WFException
	 */
	public void withDrawStartNode() throws WFException {

		if (getExeStatus() != null
				&& (getExeStatus().intValue() != ProcessInstance.STATUS_RUN)) {
			throw new WFException("�����Ѳ��������У��޷����أ�");
		}

//		WFDAO dao = new WFDAO();
//		dao.setAutoClose(false);
		try {
			NodeInstance startNode = this.getStartNode();

			List postNodes = startNode.getPostNodes();
			for (Iterator it = postNodes.iterator(); it.hasNext();) {
				NodeInstance aPost = (NodeInstance) it.next();
				aPost.setExeStatus(new Integer(NodeInstance.STATUS_RUN));
				aPost.setBackType(new Integer(NodeInstance.BACK_WITHDRAW));
				DAOUtil.BUSI().store(aPost);
			}

			for (Iterator it = this.retrieveNodeInstances().iterator(); it
					.hasNext();) {
				NodeInstance aNI = (NodeInstance) it.next();
				if (!(aNI != null && aNI.getNodeType() != null
						&& aNI.getNodeType().intValue() == PTNode.TYPE_START || (aNI
						.getBackType() != null))) {

					aNI.setExeStatus(new Integer(NodeInstance.STATUS_INIT));
					DAOUtil.BUSI().store(aNI);
				}
			}

		} catch (Exception e) {
			throw new WFException("�������ʱ����:" + this, e);
		} 
//		finally {
//			dao.closeSession();
//		}

	}

	/**
	 * ֱ��ȡ�ص���ʼ�ڵ㣬���ҳ�ʼ�����̰�ҵ��instanceuid��Ϊ��
	 * 
	 * @throws WFException
	 */
	public void withDrawStartNodeAndInitProcess() throws WFException {

		this.setInstanceUid(null);
//		WFDAO dao = new WFDAO();
		try {
			DAOUtil.BUSI().store(this);
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
		this.withDrawStartNode();
	}

	/**
	 * @return
	 * @uml.property  name="instanceUid"
	 */
	public String getInstanceUid() {
		return instanceUid;
	}

	public BOInstance getBOInstance() {
		return this.getProcessTemplate().getDoBO().getInstance(
				this.getInstanceUid());

	}

	/**
	 * @param instanceUid
	 * @uml.property  name="instanceUid"
	 */
	public void setInstanceUid(String instanceUid) {
		this.instanceUid = instanceUid;
	}



	public String toString() {
		return wfiName;
	}

	/**
	 * @return
	 * @uml.property  name="ptUid"
	 */
	public String getPtUid() {
		return ptUid;
	}

	/**
	 * @param ptUid
	 * @uml.property  name="ptUid"
	 */
	public void setPtUid(String ptUid) {
		this.ptUid = ptUid;
	}

	/**
	 * @return
	 * @uml.property  name="instanceUid2"
	 */
	public String getInstanceUid2() {
		return instanceUid2;
	}

	/**
	 * @param instanceUid2
	 * @uml.property  name="instanceUid2"
	 */
	public void setInstanceUid2(String instanceUid2) {
		this.instanceUid2 = instanceUid2;
	}

	/**
	 * @return
	 * @uml.property  name="instanceUid3"
	 */
	public String getInstanceUid3() {
		return instanceUid3;
	}

	/**
	 * @param instanceUid3
	 * @uml.property  name="instanceUid3"
	 */
	public void setInstanceUid3(String instanceUid3) {
		this.instanceUid3 = instanceUid3;
	}

	/**
	 * @return
	 * @uml.property  name="startTime"
	 */
	public Timestamp getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 * @uml.property  name="startTime"
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return
	 * @uml.property  name="startUser"
	 */
	public String getStartUser() {
		return startUser;
	}

	/**
	 * @param startUser
	 * @uml.property  name="startUser"
	 */
	public void setStartUser(String startUser) {
		this.startUser = startUser;
	}

	/**
	 * @return
	 * @uml.property  name="curState"
	 */
	public String getCurState() {
		return curState;
	}

	/**
	 * @param curState
	 * @uml.property  name="curState"
	 */
	public void setCurState(String curState) {
		this.curState = curState;
	}

	/**
	 * @return
	 * @uml.property  name="curStateTime"
	 */
	public Timestamp getCurStateTime() {
		return curStateTime;
	}

	/**
	 * @param curStateTime
	 * @uml.property  name="curStateTime"
	 */
	public void setCurStateTime(Timestamp curStateTime) {
		this.curStateTime = curStateTime;
	}

	/**
	 * @return
	 * @uml.property  name="curStateUser"
	 */
	public String getCurStateUser() {
		return curStateUser;
	}

	/**
	 * @param curStateUser
	 * @uml.property  name="curStateUser"
	 */
	public void setCurStateUser(String curStateUser) {
		this.curStateUser = curStateUser;
	}

	/**
	 * @return
	 * @uml.property  name="rejectTxt"
	 */
	public String getRejectTxt() {
		return rejectTxt;
	}

	/**
	 * @param rejectTxt
	 * @uml.property  name="rejectTxt"
	 */
	public void setRejectTxt(String rejectTxt) {
		this.rejectTxt = rejectTxt;
	}
	
	public static void main(String[] args) throws ExedoException{
		
//		ProcessInstance pi = new ProcessInstance();
//		pi.setCurState("aaa");
//		pi.setPtName("fsdfsfdsf");
//		pi.setInstanceUid("111111");
//		pi.setRejectTxt("fsdfdsf");
//		
//		com.exedosoft.wf.wfi.ProcessInstance pp = new com.exedosoft.wf.wfi.ProcessInstance();
//		
//		DAOUtil.BUSI().store(pi);
//			
		
		DOBO aBO = DOBO.getDOBOByName("do_wfi_processinstance");
		System.out.println("DOBO::::" + aBO);

	}

}