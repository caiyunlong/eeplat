package com.exedosoft.plat.agent.task;

import com.exedosoft.plat.agent.Message;
import com.exedosoft.plat.agent.Task;
import com.exedosoft.plat.agent.message.SimpleMessage;



public class SimpleTask implements Task {


	public Message perform(Message aMessage) {
		
		
		System.out.println("惓嵼?峴擟?丅丅丅丅丅丅丅丅丅丅丅");
		System.out.println("擟?柤徧::" + this.getClass().getName());
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
