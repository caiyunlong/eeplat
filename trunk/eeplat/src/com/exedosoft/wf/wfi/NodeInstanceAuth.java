package com.exedosoft.wf.wfi;

import java.io.Serializable;

import com.exedosoft.plat.bo.BaseObject;

/**
 * �ڵ�ʵ����Ȩ�޲��ڱ�����DO_Authorization �����档
 * ������Ȩ�ޱ�
 * Ȩ�޵��ж�����ֻͨ��sql��䡣
 * ���꽨ģ�����һ��
 * @author IBM
 *
 */
public class NodeInstanceAuth  extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2783516570467292021L;
	
	private String niUid;
	
	private String userUid;
	
	public String getNiUid() {
		return niUid;
	}

	public void setNiUid(String niUid) {
		this.niUid = niUid;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	private String col1;
	
	private String col2;
	

	
	
	

}
