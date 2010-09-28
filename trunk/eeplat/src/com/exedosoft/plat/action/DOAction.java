package com.exedosoft.plat.action;

import java.io.Serializable;
import java.util.List;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;

public interface DOAction extends Serializable{

	
  /**
   * ֱ��ת�����������(�е�Web Container ��֧��) ����Ҫ����һ���������ӵĶ���
   * ����֧��web framework��ajax web framework.
   * paras �� instance ����ͨ��ע��ʵ�֡�
   */
	String excute() throws ExedoException;

	/**
	 * ��ǰAction�����õ���Service
	 * @param aService
	 */
    void setService(DOService aService);

    /**
     * ��ִ�н���������һ��BOInstaace������exedo ������
     * @param instance
     */
    
    void setInstance(BOInstance instance);
    
    /**
     * ��ִ�н���������BOInstance �б�����exedo������
     * @param instances
     */
    
    void setInstances(List instances);
}