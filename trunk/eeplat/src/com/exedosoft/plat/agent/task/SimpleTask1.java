package com.exedosoft.plat.agent.task;

import com.exedosoft.plat.agent.Message;
import com.exedosoft.plat.agent.Task;
import com.exedosoft.plat.agent.message.SimpleMessage;

public class SimpleTask1 implements Task {

	public Message perform(Message aMessage) throws Exception {
		
		System.out.println("正在?行任?。。。。。。。。。。。");
		System.out.println("任?名称::" + this.getClass().getName());
		System.out.println("世界真的美好!!!!!!!!!!!!");
		SimpleMessage sm = SimpleMessage.wrapperSimpeMessage();
		sm.setMessageName("From Task2=============");
		return sm;
		
	}

	public Message rollBack(Message aMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
