package com.exedosoft.plat.util.sequence;

import com.exedosoft.plat.bo.code.DOCodeItem;

/**
 * ���д�������ȡ����
 * @author anolesoft
 *
 */
public class DOMAXIDFactory {
	
	public static SequenceBuilder getSequenceBuilder(int type){
		SequenceBuilder  sb = null;
		
		switch(type){
		case DOCodeItem.ITEM_TYPE_SEQUENCE_INDI:
			sb = DOMAXIDBuilderIndi.getInstance();
			break;
		case DOCodeItem.ITEM_TYPE_SEQUENCE_OTHER:
			sb = UserDefineIDSequence.getInstance();
			break;
		default:
			sb = DOMAXIDBuilder.getInstance();

		}
		return sb;
	}
}
