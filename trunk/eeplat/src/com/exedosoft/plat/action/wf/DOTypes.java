package com.exedosoft.plat.action.wf;

public class DOTypes {

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

	public final static int TYPE_AND_DECISION = 4;

	public final static int TYPE_XOR_DECISION = 5;

	/**
	 * �з־�Ҫ�кϣ���decision��Ҫ��conjunction,���Ի�����ȥ��ר�ŵĻ�ǩ���塣
	 */

	public final static int TYPE_AND_CONJUNCTION = 6;

	public final static int TYPE_OR_CONJUNCTION = 7;

	public final static int TYPE_SUBPROCESS = 8;

	public final static int DECISION_TYPE_SCRIPT = 1;

	public final static int DECISION_TYPE_JAVA = 2;
	
	
	
	
	
	
	
	
	/**
	 * �ڵ�ִ��״̬
	 */

	public final static int STATUS_FREE = 0;

	public final static int STATUS_INIT = 1;

	public final static int STATUS_RUN = 2;

	public final static int STATUS_FINISH = 3;

	public final static int STATUS_REJECT = 7;

	public final static int STATUS_HANGUP = 4;

	public final static int STATUS_RESUMING = 5;

	/**
	 * ����
	 */
	public final static int BACK_RETURN = 1;

	/**
	 * ȡ��
	 */
	public final static int BACK_WITHDRAW = 2;
	
	
	

}
