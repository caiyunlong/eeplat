package com.exedosoft.plat.agent;
import java.io.Serializable;

public interface Message  extends Serializable{
	
	/**
	 * ��??��?��
	 */
	public static  int MSG_RES_REQ = 1;
	
	/**
	 * ���d�C�s�v����
	 */
	public static  int MSG_RES = 2;
	
	/**
	 *�����v�L??Task,�m?�v��Messages
	 *���v����
	 * @return
	 */
	
	public static int MSG_REQ = 3;
	
	
	
	
	

	public String getMessageID();
	
	
	public int getMessageType();
	
	/**
	 * 
	 * ��Command ?��Message ?�C���vCommand??���ȁC??
	 * ��?��[���Message ����?�C�˔\?����??�ITasks
	 * 
	 * @return
	 */

	public String getCommandID();


	/**
	 */
	public abstract String getEventID();
	

	
	
}
