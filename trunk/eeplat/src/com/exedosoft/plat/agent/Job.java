package com.exedosoft.plat.agent;

/**
 * ??�꘢Command�I���߁B<br>
 * �꘢Command�I���ߍݍ@��?���꘢Job.<br>
 * �꘢Job �\?�L����Task.<br>
 * �꘢Job���꘢?���B<br>
 * ����Task��?��?�s�I�B<br> 
 *
 * @author Administrator
 *
 */
public interface Job extends Runnable {
	
	/**
	 * ?��꘢Job??�ITasks;
	 *
	 */
	java.util.List<Task> getTasks();
	
	void removeMessage();
	

}
