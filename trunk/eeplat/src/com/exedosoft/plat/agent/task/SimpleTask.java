package com.exedosoft.plat.agent.task;

import com.exedosoft.plat.agent.Message;
import com.exedosoft.plat.agent.Task;
import com.exedosoft.plat.agent.message.SimpleMessage;



public class SimpleTask implements Task {


	public Message perform(Message aMessage) {
		
		
		System.out.println("����?�s�C?�B�B�B�B�B�B�B�B�B�B�B");
		System.out.println("�C?����::" + this.getClass().getName());
		System.out.println("Hello word!!!!!!!!!!!!");
		
		SimpleMessage sm = SimpleMessage.wrapperSimpeMessage();
		sm.setMessageName("From Task1=============");
		return sm;
		
	}

	public Message rollBack(Message aMessage) {
		// TODO Auto-generated method stub
		
		return null;
		
	}

}
