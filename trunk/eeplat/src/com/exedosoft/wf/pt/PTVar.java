package com.exedosoft.wf.pt;

import com.exedosoft.plat.bo.BaseObject;
import com.exedosoft.plat.bo.DOBO;
/**
 * �����������ĸ������Ҫ ����������������ָ�������������жϵ�ֵ�ȡ� ���ڵĹ���������ֻ���Ǻ�ProcessTemplate ������ ��ʱ�����Ǻ�PTNode ������ ��һ������PTVar �� DOParameter �Ĺ�ϵ�� ��PTNode ����Ҳ����һ���ı�Ҫ�Եġ� ������ΪPTNode ��ӦPane,Pane�ڱ�������.(���������Ƕ�������Pane ������,��һ������.)
 * @author   anolesoft
 */
public class PTVar extends BaseObject {

	
	private static final long serialVersionUID = 4825992548081140898L;

	/**
	 * ��ʱPTVar ֻ��PT����������PTNode ����������һ�������Ӧ�õ������
	 */
	public static final int SCOPE_PVAR = 1;

	public static final int SCOPE_NVAR = 2;
	

	public static final int TYPE_NUM = 1;

	public static final int TYPE_STRING = 2;

	public static final int TYPE_TEXT = 3;

	public static final int TYPE_DOC = 4;

	public static final int TYPE_IMAGE = 5;

	public static final int TYPE_OBJ = 6;

	public static final int TYPE_URL = 7;
	
	public static final int TYPE_BO = 10;


	/**
	 * @uml.property  name="varName"
	 */
	private String varName;

	/**
	 * @uml.property  name="varInitValue"
	 */
	private String varInitValue;

	/**
	 * @uml.property  name="showAsWfiName"
	 */
	private Boolean showAsWfiName;

	/**
	 * @uml.property  name="varScope"
	 */
	private Integer varScope;

	/**
	 * @uml.property  name="varType"
	 */
	private Integer varType;
	/**
	 * ֻ��vartype is Type_Bo ʱ,��������
	 * @uml.property  name="doBO"
	 */
	private DOBO doBO;
	
	private ProcessTemplate processTemplate;
	
	

	/** default constructor */
	public PTVar() {
	}

	/**
	 * @return
	 * @uml.property  name="varName"
	 */
	public java.lang.String getVarName() {
		return this.varName;
	}

	/**
	 * @param varName
	 * @uml.property  name="varName"
	 */
	public void setVarName(java.lang.String varName) {
		this.varName = varName;
	}

	/**
	 * @param varScope
	 * @uml.property  name="varScope"
	 */
	public void setVarScope(Integer varScope) {
		this.varScope = varScope;
	}

	/**
	 * @param varType
	 * @uml.property  name="varType"
	 */
	public void setVarType(Integer varType) {
		this.varType = varType;
	}

	/**
	 * @return
	 * @uml.property  name="varInitValue"
	 */
	public java.lang.String getVarInitValue() {
		return this.varInitValue;
	}

	/**
	 * @param varInitValue
	 * @uml.property  name="varInitValue"
	 */
	public void setVarInitValue(java.lang.String varInitValue) {
		this.varInitValue = varInitValue;
	}


	/**
	 * @return
	 * @uml.property  name="showAsWfiName"
	 */
	public Boolean getShowAsWfiName() {
		return this.showAsWfiName;
	}

	/**
	 * @param showAsWfiName
	 * @uml.property  name="showAsWfiName"
	 */
	public void setShowAsWfiName(Boolean showAsWfiName) {
		this.showAsWfiName = showAsWfiName;
	}


	/**
	 * @return
	 * @uml.property  name="varType"
	 */
	public Integer getVarType() {
		return varType;
	}

	/**
	 * @return
	 * @uml.property  name="varScope"
	 */
	public Integer getVarScope() {
		return varScope;
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
	 * @uml.property  name="processTemplate"
	 */
	public ProcessTemplate getProcessTemplate() {
		return processTemplate;
	}

	/**
	 * @param processTemplate
	 * @uml.property  name="processTemplate"
	 */
	public void setProcessTemplate(ProcessTemplate processTemplate) {
		this.processTemplate = processTemplate;
	}



	/**
	 * ����PTVar ��������(WFIParamter����)��Ӧ��ֵ
	 * 
	 * @param pv
	 * @param wfiIOMaps
	 * @return ����(WFIParamter����)��Ӧ��ֵ
	 */
	// public String getCorrValue(Collection wfiIOMaps) {
	//
	// if(wfiIOMaps!=null){
	// for (Iterator it = wfiIOMaps.iterator(); it.hasNext(); ) {
	// WFIParameter wp = (WFIParameter) it.next();
	// //////////////////(��������������)��Ӧ��ֵ
	// if (getVarName() != null &&
	// getVarName().equalsIgnoreCase(wp.getKey())) {
	// return wp.getValue();
	// }
	// }
	// }
	// return null;
	// }
	/**
	 * ����PTVar ��������(WFIParamter����)��Ӧ��ֵ
	 * 
	 * @param pv
	 * @param wfiIOMaps
	 * @return ����(WFIParamter����)��Ӧ��ֵ
	 */
	// public byte[] getCorrBytes(Collection wfiIOMaps) {
	//
	// if(wfiIOMaps!=null){
	// for (Iterator it = wfiIOMaps.iterator(); it.hasNext(); ) {
	// WFIParameter wp = (WFIParameter) it.next();
	// //////////////////(��������������)��Ӧ��ֵ
	// if (getVarName() != null &&
	// getVarName().equalsIgnoreCase(wp.getKey())) {
	// return wp.getBlob();
	// }
	// }
	// }
	// return null;
	// }
	/**
	 * ����PTVar ��������(WFIParamter����)��Ӧ��ֵ
	 * 
	 * @param pv
	 * @param wfiIOMaps
	 * @return ����(WFIParamter����)��Ӧ��ֵ
	 */
	// public String getCorrFileName(Collection wfiIOMaps) {
	//
	// if(wfiIOMaps!=null){
	// for (Iterator it = wfiIOMaps.iterator(); it.hasNext(); ) {
	// WFIParameter wp = (WFIParameter) it.next();
	// //////////////////(��������������)��Ӧ��ֵ
	// if (getVarName() != null &&
	// getVarName().equalsIgnoreCase(wp.getKey())) {
	// return wp.getFileName();
	// }
	// }
	// }
	// return null;
	// }
}