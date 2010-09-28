package com.exedosoft.plat.queue;

import java.util.concurrent.TimeUnit;

import com.exedosoft.plat.bo.BOInstance;



public interface IBOInstanceQueue {

	/**
	 * �������Y������?�񒆁C�@�ʖv�L�p��?�C���꒼���ҁB
	 * 
	 * @param aMessage
	 *            ����
	 */
	public abstract void putMessage(BOInstance aMessage);

	/**
	 * ?����ڏ���?��I��꘢�����C�@�ʍ�?��s���ݔC�������C?�꒼����
	 * 
	 * @return ?��I��꘢����
	 */
	public abstract BOInstance takeMessage();

	/**
	 * �@�ʉ\�I?�C������������?�񒆁B
	 * 
	 * @param aMessage
	 *            ����
	 * @return �����ԉ� true�C������v?�ԉ� false
	 */
	public abstract boolean offerMessage(BOInstance aMessage);

	/**
	 * ���w��I����������?�񒆁C�@�ʖv�L�p��?�C�����Ҏw��I����??�i�@�ʗL�K�v�j�B
	 * 
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public abstract BOInstance offerMessage(long timeout, TimeUnit unit);

	/**
	 * ��?�񒆈ڏ��꘢�����B
	 * 
	 * @param aMessage
	 *            �҈ڏ��I����
	 */
	public abstract void removeMessage(BOInstance aMessage);

	/**
	 * ���f?�񐥔�?��B
	 * 
	 * @return ?��ԉ�true,��?�ԉ�false.
	 */
	public abstract boolean isEmpty();

}
