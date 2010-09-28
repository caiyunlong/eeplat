package com.exedosoft.plat.agent.command;

import java.io.IOException;
import java.net.ServerSocket;

import com.exedosoft.plat.agent.CommandServlet;
import com.exedosoft.plat.agent.config.HumletGlobals;
import com.exedosoft.plat.agent.message.MessageIn;
/**
 * CommandServlet�I㞏�??�B
 * 
 * @author Administrator
 * 
 */
public class SimpleCommandServlet implements CommandServlet {

	private ServerSocket ss;

	private boolean listening = true;

	public SimpleCommandServlet() {
		
		System.out.println("㞏�CommandServlet����??......");
		Init();// ���n��
		Listen();// ?��
	}

	public void Init() {
		try {
			
			ss = new ServerSocket(HumletGlobals.getPort(), HumletGlobals.getCmdSvrBlockMax());
			System.out.println("㞏�CommandServlet���n������......");

		} catch (IOException ie) {
			System.out.println("�ٖ@�ݒ[���F"+ HumletGlobals.getPort() + "�@?��");
			ie.printStackTrace();
		}
	}

	public void Listen() {
		try {
			
			while (listening){
				System.out.println("㞏�CommandServlet����?��......");
				new Thread(new MessageIn(ss.accept())).start();
			}
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new SimpleCommandServlet();
	}

}
