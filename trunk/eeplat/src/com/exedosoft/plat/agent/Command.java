package com.exedosoft.plat.agent;

import java.util.Collection;

/**
 * ?���q?�[?�p?�s�꘢Command.
 * Command��Agent?�������B
 * 
 * @author Administrator
 *
 */
public interface Command {
	
	Message  excute(Message aMsg);
	
    String getCommandID();

	/**
	 */
	public abstract Collection getEvents();

}
