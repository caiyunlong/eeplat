package com.exedosoft.wf;


import com.exedosoft.wf.pt.ProcessTemplate;
import com.exedosoft.wf.wfi.NodeInstance;
import com.exedosoft.wf.wfi.ProcessInstance;

import java.util.Collection;

/**
 * Ҫ�������̺ͽ������
 * @author Administrator
 *
 */

public interface WFEngine {

  /**
   * ���ݹ���ģ��Ͳ�������һ��������ʵ����
   * @param pt ������ģ��
   * @param wfiParameters ����������������Ҫ�Ĳ���
   * @return ������ʵ��
   */
  ProcessInstance startProcess(ProcessTemplate pt) throws WFException;
  
  
  /**
   * ����������ʵ�������ǵ�һ���ڵ㲢���ύ
   * @param pt
   * @return
   * @throws WFException
   */
  NodeInstance initProcess(ProcessTemplate pt) throws WFException;
  

  /**
   * ���Է��������
   * @param pi
   * @return
   * @throws WFException
   */
  Collection getProcesses() throws WFException;

  /**
   * �ָ�����/����ִ�и����̡�
   * @param pi ����ʵ��
   * @return
   * @throws WFException
   */
  ProcessInstance reStartProcess(ProcessInstance pi) throws WFException;

  /**
   * ���ݹ�����ģ�巵���ɸ�ģ�����ɵ�����ʵ��
   * @param pt ������ģ��
   * @return ʵ������
   */
  Collection getProcessInstances(ProcessTemplate pt) throws WFException;

  /**
   * ���ݹ�����ģ��Ψһ�������ع�����ģ��
   * @param processUID  ������ģ��Ψһ����
   * @return ������ģ��
   */
  ProcessTemplate loadProcessTemplate(String ptUID) throws WFException;


  /**
   * ���ݹ�����ʵ��Ψһ�������ع�����ʵ��
   * @param piUID
   * @return
   */
  ProcessInstance loadProcessInstance(String piUID) throws WFException;

  /**
   * ������ɱ��һ��������ʵ����
   * @param piUID ������ʵ��Ψһ��ʾ��
   * @throws WFException
   */
  void killProcessInstance(String piUID) throws WFException;

  /**
   * ����һ��������ʵ����
   * @param piUID  ������ʵ��Ψһ��ʾ
   * @throws WFException
   */
  ProcessInstance hangUpProcessInstance(String piUID) throws WFException;

//  /** ��������֧??
//   * ��ø���������ʵ�������һ��δִ�е�Node
//   * @param piUID ������ʵ��UID
//   * @return ������ʵ��
//   * @throws WFException �������쳣
//   */
//  NodeInstance  getLastFreeNodeInstance(Long piUID) throws WFException;
//
//
//  /**
//   * ��ø���������ʵ�������һ��δִ�е�Node
//   * @param piUID ������ʵ��UID
//   * @return ������ʵ��
//   * @throws WFException �������쳣
//   */
//  ActionInstance  getLastFreeNodeInstance(Long piUID) throws WFException;
//


  /**
   * ��ĳ��NodeInstance ���¿�ʼִ��
   * @param nodeUID
   * @return
   * @throws WFException
   */
  NodeInstance reStartFromNodeInstance(String nodUID) throws WFException;


  /**
   * ����ĳ��nodeInstance
   * @param piUID
   * @return
   * @throws WFException
   */
  NodeInstance hangUpNodeInstance(String nodeUID) throws WFException;


  /**
   * �ڸ����ڵ�ʵ��niǰ�����һ��NodeInstance
   * @throws WFException
   */
  NodeInstance insertBeforNodeInstance(NodeInstance ni, NodeInstance insertNi) throws
      WFException;

  /**
   * �ڸ����ڵ�ʵ��ni�������һ��NodeInstance
   * @throws WFException
   */
  NodeInstance insertAfterNodeInstance(NodeInstance ni, NodeInstance insertNi) throws
      WFException;


  /**
   *ɾ�������Ķ���ʵ��
   * @throws WFException
   */
  void deleteNodeInstance(NodeInstance ni) throws WFException;
  
  

  /**
   * ����Ա��Ψһ��ʾ����ȡ��Ա����Ҫ��ɹ����ļ���
   * @param Ա��Ψһ��ʾ
   */
  Collection getMyPending() throws WFException;

  /**
   * ����Ա��Ψһ��ʾ�͹�����ģ�壬��ȡ��Ա���ڸ������н�Ҫ��ɹ����ļ���
   * @param Ա��Ψһ��ʾ
   * @param ������ģ��Ψһ��ʾ
   */
  Collection getMyPending(String processTemplateUID) throws
      WFException;



  /*
   * ����Ա��Ψһ��ʾ����ȡ��Ա������ɹ����ļ���
   * @param Ա��Ψһ��ʾ
   */
  Collection getHaveDone() throws WFException;

  /**
   * ����Ա��Ψһ��ʾ�͹�����ģ�壬��ȡ��Ա���ڸù�������������ɹ����ļ���
   * @param Ա��Ψһ��ʾ
   * @param ����ģ��Ψһ��ʾ
   */
  Collection getHaveDone(String processTemplateUID) throws
      WFException;


  /**
   * ����Ա��Ψһ��ʾ����ȡ��Ա���ύ����Ĵ���������
   * @param Ա��Ψһ��ʾ
   */
  Collection getMyResult() throws WFException;

  /**
   * ����Ա��Ψһ��ʾ�͹�����ģ��Ψһ��ʾ����ȡ��Ա���ڸù����������ύ����Ĵ���������
   * @param Ա��Ψһ��ʾ
   */
  Collection getMyResult(String processTemplateUID) throws
      WFException;

}