package com.exedosoft.plat.agent.command.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.exedosoft.plat.agent.Message;
import com.exedosoft.plat.agent.config.HumletGlobals;

/**
 * ????���{�ICommand
 * 
 * @author Administrator
 * 
 */
public class TextCommand extends TCPCommand {




	public TextCommand() {
	
	}

	public Message excute(Message aMsg) {
		
		String sen;
		byte b[];
		try {

			b = new byte[1024];
			OutputStream out =  getSocket().getOutputStream();
			sen = "Hello world�I  �H�V�^�I�k���D";
			b = sen.getBytes();
			System.out.println("�q?�[??�C��???�I�������I");
			out.write(b);
			out.flush();
			out.close();
			getSocket().close();
		} catch (IOException ie) {
			ie.toString();
		}
		return aMsg;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TextCommand tc = new TextCommand();
		tc.excute(null);

	}

	public String getCommandID() {
		// TODO Auto-generated method stub
		return "aTest";
	}
}
