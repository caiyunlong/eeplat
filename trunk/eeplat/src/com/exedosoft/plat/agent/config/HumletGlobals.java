package com.exedosoft.plat.agent.config;

/**
 * �n?�I�S�ǔz�u�����B �Agent?�s�I�n���C�[�����B
 * @author  Administrator
 */

public class HumletGlobals {

	/**
	 * ��?�햼��
	 * @uml.property  name="serverName"
	 */
	private static String serverName;

	/**
	 * ��?��[��
	 * @uml.property  name="port"
	 */
	private static String port;

	/**
	 * CommanSerlet��?��\�ڎ�I�ő�q?�[?��?��?�x�B
	 * @uml.property  name="cmdSvrBlockMax"
	 */
	private static String cmdSvrBlockMax;
	
	/**
	 * inpool �ő�?��?�x
	 * @uml.property  name="inQueueMax"
	 */
	private static String inQueueMax;
	
	/**
	 * �ő�?���I������
	 * @uml.property  name="processMax"
	 */
	private static String processMax;	
		
	/**
	 * �q?�[?�ڒ�???�B
	 * @uml.property  name="timeOut"
	 */
	private static String timeOut;

	static {

		serverName = "127.0.0.1";
		port = "315";
		timeOut = "1000";
		cmdSvrBlockMax = "10";
		processMax = "10";
	}

	/**
	 * @return
	 * @uml.property  name="serverName"
	 */
	public static String getServerName() {
		return serverName;
	}

	/**
	 * @return
	 * @uml.property  name="port"
	 */
	public static int getPort() {
		return Integer.parseInt(port);
	}

	/**
	 * @return
	 * @uml.property  name="timeOut"
	 */
	public static int getTimeOut() {
		return Integer.parseInt(timeOut);
	}
	
	/**
	 * @return
	 * @uml.property  name="cmdSvrBlockMax"
	 */
	public static int getCmdSvrBlockMax(){
		return Integer.parseInt(cmdSvrBlockMax);		
	}

	/**
	 * @return
	 * @uml.property  name="processMax"
	 */
	public static int getProcessMax() {
		return Integer.parseInt(processMax);
	}
	
	/**
	 * @return
	 * @uml.property  name="inQueueMax"
	 */
	public static int getInQueueMax(){
		return Integer.parseInt(inQueueMax);
	}

	
	

}
