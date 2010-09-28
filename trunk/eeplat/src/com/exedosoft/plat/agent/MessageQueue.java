package com.exedosoft.plat.agent;

import java.util.concurrent.TimeUnit;

/**
 * 
 * ����?��C��Y�������C�ڏ����������@�B
 * 
 * ����?��?���I�l?�H�H ���e�o?��C?????���B ?��?��?��?��?��C�e?�l?�ꉺ�B
 * 
 * 
 * @author Administrator
 * 
 */
public interface MessageQueue {
	

	/**
	 * �������Y������?�񒆁C�@�ʖv�L�p��?�C���꒼���ҁB
	 * 
	 * @param aMessage
	 *            ����
	 */
	public abstract void putMessage(Message aMessage);

	/**
	 * ?����ڏ���?��I��꘢�����C�@�ʍ�?��s���ݔC�������C?�꒼����
	 * 
	 * @return ?��I��꘢����
	 */
	public abstract Message takeMessage();

	/**
	 * �@�ʉ\�I?�C������������?�񒆁B
	 * 
	 * @param aMessage
	 *            ����
	 * @return �����ԉ� true�C������v?�ԉ� false
	 */
	public abstract boolean offerMessage(Message aMessage);

	/**
	 * ���w��I����������?�񒆁C�@�ʖv�L�p��?�C�����Ҏw��I����??�i�@�ʗL�K�v�j�B
	 * @param timeout
	 * @param unit
	 * @return
	 */	
	public abstract Message offerMessage(long timeout, TimeUnit unit);
	/**
	 * ��?�񒆈ڏ��꘢�����B
	 * 
	 * @param aMessage
	 *            �҈ڏ��I����
	 */
	public abstract void removeMessage(Message aMessage);

	/**
	 * ���f?�񐥔�?��B
	 * 
	 * @return ?��ԉ�true,��?�ԉ�false.
	 */
	public abstract boolean isEmpty();

}
