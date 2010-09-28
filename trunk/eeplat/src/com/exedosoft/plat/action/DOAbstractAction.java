package com.exedosoft.plat.action;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Category;

import com.exedosoft.plat.DOThreadContext;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;

/**
 * @author  IBM
 */

public abstract class DOAbstractAction implements Serializable, DOAction {
	
	
	public static final String DEFAULT_FORWARD = "success";
	public static final String NO_FORWARD = "noforward";


	protected static Category logger = Category
			.getInstance(DOAbstractAction.class);

	protected DOService service;


	protected BOInstance actionForm;

	protected boolean isInRuleScope = false;// ////////�Ƿ���rulescope�£�һ�������л����¶���,����������Ե�Ŀ�����ڲ��ԡ�

	public DOAbstractAction() {
	}

	/**
	 * ���ڲ��Եĳ�ʼ�����ݿ����ӷ���
	 * 
	 */
	public void initTransConnection() {

		try {
			if (DOGlobals.getInstance().getRuleContext().getConnection(
					this.service.getBo().getDataBase()) != null) {
				isInRuleScope = true;
			} else {
				this.service.getBo().getDataBase().registerConnection(true);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * ���ڲ��ԵĹر����ݿ����ӷ���
	 * 
	 */

	public void ifCloseTransConnection() {
		if (!isInRuleScope) {
			this.service.getBo().getDataBase().closeContextCon(true);
		}
	}

	/**
	 * @param aService
	 * @uml.property  name="service"
	 */
	public void setService(DOService aService) {
		service = aService;
		actionForm = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance();
	}
	


	/**
	 * ֱ��ת�����������(�е�Web Container ��֧��) ����Ҫ����һ���������ӵĶ��� ����֧��web framework��ajax web
	 * framework. paras �� instance ����ͨ��ע��ʵ�֡�
	 */

	public abstract String excute() throws ExedoException;

	public void setInstance(BOInstance instance) {
		DOThreadContext context = DOGlobals.getInstance().getRuleContext();
		
		if(service!=null){
			context.put(service.getName(), instance);
		}
		
		//////��ʱע�͵� �϶����е���ģ�����Ӱ��ȫ�֣�����������������������������������������������
//		context.setInstance(instance);

	//////////���ڷſ�
		context.setInstance(instance);

	

	}

	public void setInstances(List instances) {
		// TODO Auto-generated method stub
		DOThreadContext context = DOGlobals.getInstance().getRuleContext();
	//	context.put(service.getName(), instances);
		context.setInstances(instances);

	}
	
	public void setEchoValue(String aValue){
		
		DOGlobals.getInstance().getRuleContext().setEchoValue(aValue);

		
	}
	
	public String getEchoValue(){
		
			return  DOGlobals.getInstance().getRuleContext().getEchoValue();
	}


}