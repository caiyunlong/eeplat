package com.exedosoft.wf;
/**
 *
 * <p>Title: </p>
 * <p>�����������쳣�����������л򽻻������п��ܳ��ֵ��쳣������</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class WFException extends Exception {

  public WFException() {
   super("�����������쳣");
  }

  public WFException(String message) {
    super(message);
  }

  public WFException(String message, Throwable cause) {
    super(message, cause);
  }

  public WFException(Throwable cause) {
    super(cause);
  }
  public static void main(String[] args) {
    WFException WFException1 = new WFException();
  }
}