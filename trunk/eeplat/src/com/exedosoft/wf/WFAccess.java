package com.exedosoft.wf;

import com.exedosoft.wf.wfi.NodeInstance;

/**
 * isAccess ��������NodeInstance ������ BOInstance
 * ��Ҫ��isAccess ���Է����ж�ǰ�� ��̵�
 * @author anolesoft
 *
 */
public interface WFAccess {
	
	boolean isAccess(NodeInstance ni);

}
