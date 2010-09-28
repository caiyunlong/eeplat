package com.exedosoft.wf.pt;

import java.util.List;

import com.exedosoft.plat.bo.BaseObject;
import com.exedosoft.plat.ui.DOPaneModel; //import com.exedosoft.plat.dao.DAOException;
//import com.exedosoft.plat.dao.HbmDAO;
//import com.exedosoft.plat.dao.WFDAO;

import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.bo.org.DOAuthorization;
import com.exedosoft.wf.wfi.NodeInstance;

import com.exedosoft.plat.DAOUtil;

/**
 * 
 * Ҫ���ǵ���ȡ�Ĺ��̣���������ʱ�Ƚϳ���ôӦ����һ����ȡ����Ķ���
 * ��ȡ������ڰ�
 * 
 * ��졢���졢�ڰ졢��� ���⻹��ת���ĸ��
 * 
 * ת����ָ�ύ����һ���ڵ㣬��һ���ڵ㻹δǩ�գ��Ѱ�����ڡ��Ѱ족����Ǳ��������Ҫ��
 *   
 *  �ڰ����Լ����ڰ���  ������ȡ  ���̲Ŵ����ڰ�
 * 

      ����ļ�������������̰�����ϣ���ӡ��ڰ��ļ������Զ�ת�Ƶ�������ļ����С�


/////����
select distinct curstate,node_uid,nodeDate, ni.OBJUID contextNIUid,wpi.objuid contextPIUid, instance_uid,pass_txt,reject_txt,user_uid from  
do_wfi_nodeinstance ni, DO_WFI_PROCESSINSTANCE wpi,do_org_user_role ur ,do_org_role r,do_authorization  a where wpi.objuid = ni.pi_uid and 
 ur.role_uid and r.objuid and a.parteruid='9' and a.ouuid= r.objuid  and node_uid = a.whatuid and ni.exestatus=2 and wpi.exestatus=2

////�ڰ�
select distinct wpi.objuid contextPIUid, curstate, instance_uid, user_uid from  
do_wfi_nodeinstance ni, DO_WFI_PROCESSINSTANCE wpi,do_org_user_role ur ,do_org_role r,do_authorization  a where wpi.objuid = ni.pi_uid and 
 ur.role_uid and r.objuid and a.parteruid='9' and a.ouuid= r.objuid  and node_uid = a.whatuid and ni.exestatus=3 and wpi.exestatus=2

 ////���
  * select distinct wpi.objuid contextPIUid, curstate, instance_uid, user_uid from  
do_wfi_his_nodeinstance ni, DO_WFI_his_PROCESSINSTANCE wpi,do_org_user_role ur ,do_org_role r,do_authorization  a where wpi.objuid = ni.pi_uid and 
 ur.role_uid and r.objuid and a.parteruid='9' and a.ouuid= r.objuid  and node_uid = a.whatuid 
 
 * 
 * ���µ����˼·: ����״̬�����������˼·�� Node Node��һ��������
 * Node�ж������ͣ��������Խ�����ֹ��̽�ģ���ۣ���xpdl,activity diagram,epc��event ������ģ���ȡ�
 * ����Node��һ�����������������ͣ����Կ��Ժܺõ�����΢����������̣����̶������õĺ��������ġ� Ϊ��֧����������������������͹�������ִ�л��ĸ��
 * �����̱�����ִ��ʱ�����еĽڵ㶼����һ�飬ÿ���ڵ���ִ��������ֻҪ������������ִ�У����ڵ㴦��STATUS_RUN״̬����
 * ����ÿ���ڵ㣨����ʼ�ڵ㣩��ִ�п�������������� 1����ǰһ���ڵ㴥�� 2�������������������
 * ����������һ��Rule.��������������Ҳ���Ǹ���״̬������������Էǳ��õĴ�����ˣ���ת�� ˳���������Կ���״̬�������������� ������ԭ���Ľ���:
 * �������̵ĸ����ԣ���ִ��״̬�������� ��Node �����͵ȡ����Ǳ���Node ,Process�����̶���Ĵ��ڡ� * Node
 * ��ִ��������DOService,�ο��ܻ������̵�ʵ�֣�Node �����Action. DOService���൱��Action. ��Node
 * ֻ�ܰ���һ��DOService ��DOService ���Լ���ִ��˳��
 */
public class PTNode extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1185569976922912331L;

	public final static int TYPE_ACTIVITY = 1;

	public final static int TYPE_SERVICE_AUTO = 21;

	public final static int TYPE_SELF = 11;

	/**
	 * �Զ��Ļ�ǩ֧�֣�����DOAuthorization��getAuthConfigUsers ÿ����ǩ�������Ϊxml�ļ���
	 * 
	 */

	public final static int TYPE_SELF_CONJUNCTION = 16;

	public final static int TYPE_START = 2;

	public final static int TYPE_END = 3;

	/**
	 * ��֧����������
	 */
	public final static int TYPE_AND_DECISION = 4;

	/**
	 * ��֧������
	 */
	public final static int TYPE_XOR_DECISION = 5;

	/**
	 * �з־�Ҫ�кϣ���decision��Ҫ��conjunction,���Ի�����ȥ��ר�ŵĻ�ǩ���塣
	 * 
	 * OR_CONJUNCTION ��ʱʡ�Ե�
	 */

	public final static int TYPE_AND_CONJUNCTION = 6;

	public final static int TYPE_OR_CONJUNCTION = 7;

	public final static int TYPE_SUBPROCESS = 8;

	/**
	 * �ֶ���ʽҲ��һ��script ��ʽ �ֶ�������һ������������˭�����������б� һ���ڵ��������ж���ڵ㣬ÿ���ڵ��ֶ�Ӧ����û�
	 * 
	 */
	public final static int DECISION_TYPE_SCRIPT = 1;

	public final static int DECISION_TYPE_JAVA = 2;

	/**
	 * Ȩ�ޱ�ʽ
	 */
	public final static int AUTH_TYPE_AUTHTABLE = 0;

	/**
	 * Ȩ�ޱ�ʽ���Թ�����ʵ���� �����ʱ���Բ�ʵ��
	 */
	public final static int AUTH_TYPE_AUTHTABLE_INSTANCE = 10;

	/**
	 * ʹ�ýű�
	 */
	public final static int AUTH_TYPE_SCRIPT = 1;

	/**
	 * ʹ��java��
	 */
	public final static int AUTH_TYPE_JAVA = 2;

	/**
	 * ����ӵ����Ȩ�� (һ��ָ������)
	 */

	public final static int AUTH_TYPE_DATA_OWNER = 3;

	/**
	 * ָ���û�
	 */
	public final static int AUTH_TYPE_SCHEDULE_USER = 8;

	/**
	 * ָ������,��ʱ��ʵ��
	 */
	public final static int AUTH_TYPE_SCHEDULE_DEPT = 12;
	/**
	 * ָ����ɫ
	 */
	public final static int AUTH_TYPE_SCHEDULE_ROLE = 16;

	private String nodeName;

	private String nodeDesc;

	/**
	 * ������script��Ҳ������java class
	 */
	private Integer decisionType;

	private String decisionExpression;

	private ProcessTemplate processTemplate;

	private Integer nodeType;

	private String specName;

	private DOService autoExcutesService;

	/**
	 * ��conditon ����ʱ����� node �Ϳ��Դ��� ֧����������condtion
	 */
	private String conditon;

	/**
	 * ��ǰ����״̬��ʾ,���״̬���������ִ��״̬�����ǽڵ�ִ�е���һ����Ҫ��ʾʲô״̬��
	 * ��Ȼ��һ�����̿���ͬʱ�ж����ڵ㡣ÿ���ڵ㶼�п�������nodeStateShow�� �����һ������Ϊ׼��
	 * ��ʵ��Ӧ���У����һ���ڵ��ִ�в���Ӱ���������̵�״̬�����Բ�����nodeStateShow ��������Ͳ��ᴦ��
	 */
	private String nodeStateShow;

	private String nodeStateShowBack;

	private DOPaneModel pane;///����

	private DOPaneModel donePane;///�Ѱ�

	private DOPaneModel resultPane;///���

	private Integer authType;

	/**
	 * Ȩ�޵���һ�ֿ����ֶΡ� Ȩ�޿��������� 1��ͨ��DOAuthrizaiton�� 2������������Class ���������class
	 */
	private String accessClass;

	private String passTxt;

	private String rejectTxt;

	private String nodeExt1;

	private String nodeExt2;

	private String retNodeUID;
	
	private Integer x;
	
	private Integer y;
	
	public Integer getX() {
		return x;
	}

	public void setX(Integer px) {
		this.x = px;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer py) {
		this.y = py;
	}



	/** default constructor */
	public PTNode() {
	}

	/**
	 * @return
	 */
	public java.lang.String getNodeName() {
		return this.nodeName;
	}

	/**
	 * @param nodeName
	 */
	public void setNodeName(java.lang.String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return
	 */
	public java.lang.String getNodeDesc() {
		return this.nodeDesc;
	}

	/**
	 * @param nodeDesc
	 */
	public void setNodeDesc(java.lang.String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}

	/**
	 * @param nodeType
	 */
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public void setNodeType(String sType) {

		if (sType.equals("start")) {
			this.nodeType = 2;
		} else if (sType.equals("end")) {
			this.nodeType = 3;
		} else if (sType.equals("activity")) {
			this.nodeType = 1;
		} else if (sType.equals("auto")) {
			this.nodeType = 21;
		} else if (sType.equals("andDecision")) {
			this.nodeType = 4;
		} else if (sType.equals("xorDecision")) {
			this.nodeType = 5;
		} else if (sType.equals("andConjuction")) {
			this.nodeType = 6;
		} else if (sType.equals("subFlow")) {
			this.nodeType = 8;
		}

	}

	public String getNodeTypeStr() {

		if (this.nodeType == null) {
			return "activity";
		}
		switch (this.nodeType.intValue()) {
		case 2:
			return "start";
		case 3:
			return "end";
		case 21:
			return "auto";
		case 4:
			return "andDecision";
		case 5:
			return "xorDecision";
		case 6:
			return "andConjuction";
		case 8:
			return "subFlow";
		default:
			return "activity";

		}
	}

	/**
	 * @return
	 */
	public java.lang.String getDecisionExpression() {
		return this.decisionExpression;
	}

	/**
	 * @param decisionExpression
	 */
	public void setDecisionExpression(java.lang.String decisionExpression) {
		this.decisionExpression = decisionExpression;
	}

	/**
	 * @return
	 */
	public ProcessTemplate getProcessTemplate() {
		return processTemplate;
	}

	/**
	 * @param processTemplate
	 */
	public void setProcessTemplate(ProcessTemplate processTemplate) {
		this.processTemplate = processTemplate;
	}

	/**
	 * @return
	 */
	public Integer getNodeType() {
		return nodeType;
	}

	/**
	 * @return
	 */
	public String getSpecName() {
		return specName;
	}

	/**
	 * @param specName
	 */
	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public DOPaneModel getPane() {
		return pane;
	}

	/**
	 * @param pane
	 */
	public void setPane(DOPaneModel pane) {
		this.pane = pane;
	}

	public DOPaneModel getDonePane() {
		return donePane;
	}

	public void setDonePane(DOPaneModel donePane) {
		this.donePane = donePane;
	}

	public DOPaneModel getResultPane() {
		return resultPane;
	}

	public void setResultPane(DOPaneModel resultPane) {
		this.resultPane = resultPane;
	}

	public String getNodeExt1() {
		return nodeExt1;
	}

	public void setNodeExt1(String nodeExt1) {
		this.nodeExt1 = nodeExt1;
	}

	public String getNodeExt2() {
		return nodeExt2;
	}

	public void setNodeExt2(String nodeExt2) {
		this.nodeExt2 = nodeExt2;
	}

	public String getRetNodeUID() {
		return retNodeUID;
	}

	public void setRetNodeUID(String nodeExt5) {
		this.retNodeUID = nodeExt5;
	}

	public String getPassTxt() {
		return passTxt;
	}

	public void setPassTxt(String passTxt) {
		this.passTxt = passTxt;
	}

	public String getRejectTxt() {
		return rejectTxt;
	}

	public void setRejectTxt(String rejectTxt) {
		this.rejectTxt = rejectTxt;
	}

	public String getAccessClass() {
		return accessClass;
	}

	public void setAccessClass(String accessClass) {
		this.accessClass = accessClass;
	}

	public DOService getAutoExcutesService() {
		return autoExcutesService;
	}

	public void setAutoExcutesService(DOService autoExcutesService) {
		this.autoExcutesService = autoExcutesService;
	}

	public String getConditon() {
		return conditon;
	}

	public void setConditon(String conditon) {
		this.conditon = conditon;
	}

	public String getNodeStateShow() {
		return nodeStateShow;
	}

	public void setNodeStateShow(String nodeStateShow) {
		this.nodeStateShow = nodeStateShow;
	}

	public String getNodeStateShowBack() {
		return nodeStateShowBack; 
	}

	public void setNodeStateShowBack(String nodeStateShowBack) {
		this.nodeStateShowBack = nodeStateShowBack;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	/**
	 * ���ǰ���ڵ�
	 * 
	 * @return
	 */
	public static PTNode getNodeById(String aID) {

		return DAOUtil.INSTANCE().getByObjUid(PTNode.class, aID);
		// HbmDAO dao = new HbmDAO();
		// String hql = "select pn from PTNode pn where pn.id = ?";
		// try {
		// List list = dao.list(hql, aID);
		// if(list!=null && list.size()>0){
		// return (PTNode)list.get(0);
		// }
		// } catch (DAOException e) {
		// e.printStackTrace();
		// return null;
		// }
		// return null;
	}

	/**
	 * ���ǰ���ڵ�
	 * 
	 * @return
	 */
	public java.util.List getPreNodes() {

		String hql = "select node.* from DO_PT_Node_Denpendency nd,DO_PT_Node node where nd.Pre_N_UID = node.objuid and  nd.Post_N_UID = ? ";
		return DAOUtil.INSTANCE().select(PTNode.class, hql, this.getObjUid());

		// HbmDAO dao = new HbmDAO();
		// String hql =
		// "select nd.preNode from NodeDenpendency nd where nd.postNode.id = ?";
		// try {
		// return dao.list(hql, getObjUid().toString());
		// } catch (DAOException e) {
		// e.printStackTrace();
		// return null;
		// }
	}

	/**
	 * ��ú����ڵ�
	 * 
	 * @return
	 */
	public List getPostNodes() {

		String hql = "select node.* from DO_PT_Node_Denpendency nd,DO_PT_Node node where nd.Pre_N_UID = node.objuid and  nd.Pre_N_UID = ? ";
		return DAOUtil.INSTANCE().select(PTNode.class, hql, this.getObjUid());

		// HbmDAO dao = new HbmDAO();
		// String hql =
		// "select nd.postNode from NodeDenpendency nd where  nd.preNode.id = ?";
		// try {
		// return dao.list(hql, getObjUid().toString());
		// } catch (DAOException e) {
		// e.printStackTrace();
		// return null;
		// }
	}

	/**
	 * ��ú����ڵ�������ϵ
	 * 
	 * @return
	 */
	public List getPostNodeDepes() {

		String hql = "select nd.* from DO_PT_Node_Denpendency nd,DO_PT_Node node where nd.Pre_N_UID = node.objuid and  nd.Pre_N_UID = ? ";
		return DAOUtil.INSTANCE().select(NodeDenpendency.class, hql, this
				.getObjUid());

		// HbmDAO dao = new HbmDAO();
		// String hql =
		// "select nd from NodeDenpendency nd where  nd.preNode.id = ?";
		// try {
		// return dao.list(hql, getObjUid().toString());
		// } catch (DAOException e) {
		// e.printStackTrace();
		// return null;
		// }
	}

	/**
	 * ��ȡ�������е��ƶ�ʵ����nodeInstance��Ŀ��
	 * 
	 * @return
	 */

	public List getCorrRunNodeInstances(String piUid) {

		String hql = "select ni.* from do_wfi_nodeinstance ni where  ni.NODE_UID = ? and ni.exeStatus = 2 ";
		return DAOUtil.BUSI().select(NodeInstance.class, hql, this.getObjUid());

		// WFDAO dao = new WFDAO();
		// String hql =
		// "select ni from NodeInstance ni where  ni.nodeUid = ? and ni.processInstance.id = ? and ni.exeStatus = 2 and ni.processInstance.exeStatus = 2";
		// try {
		// List list = dao.list(hql, this.getObjUid(),piUid);
		// return list;
		// } catch (DAOException e) {
		// e.printStackTrace();
		// return null;
		// }

	}

	// public String toString() {
	// return nodeName;
	// }

	public boolean isAccess() {

		// System.out.println("Hello world!!!!!!!!!!!!!!!!!!");
		//		
		// System.out.println(this.getObjUid());

		return DOAuthorization.isAccess(new Integer(
				DOAuthorization.WHAT_WF_NODE), this.getObjUid(), null, null);

		// if (this.getPane() == null) {
		// return true;
		// }
		// return this.getPane().isAccess();
	}

	/**
	 * @return
	 * @uml.property name="decisionType"
	 */
	public Integer getDecisionType() {
		return decisionType;
	}

	/**
	 * @param decisionType
	 * @uml.property name="decisionType"
	 */
	public void setDecisionType(Integer decisionType) {
		this.decisionType = decisionType;
	}

	public static void main(String[] args) {

		String aFile = "c:\\aaa\\bbb\\�й�\\dd.txt";
		System.out.println(aFile.substring(aFile.lastIndexOf("\\") + 1));

	}

}
