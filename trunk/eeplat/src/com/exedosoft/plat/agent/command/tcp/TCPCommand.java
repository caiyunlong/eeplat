package com.exedosoft.plat.agent.command.tcp;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;

import com.exedosoft.plat.agent.Command;
import com.exedosoft.plat.agent.Message;
import com.exedosoft.plat.agent.config.HumletGlobals;

/**
 * ��TCP/IP????�I�aAgent���ݓIICommand?�B
 * @author Administrator
 *
 */


public abstract class TCPCommand implements Command, Serializable {
	
	private Socket con;// �q?�[?��socket
	
	protected TCPCommand(){
		try {
			con = new Socket(HumletGlobals.getServerName(), HumletGlobals.getPort());
			con.setSoTimeout(HumletGlobals.getTimeOut());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public abstract Message excute(Message aMsg);
	
	public Socket getSocket() throws UnknownHostException, IOException{
		return con;
	}
	
	public  Collection getEvents(){return null;};

	
}
