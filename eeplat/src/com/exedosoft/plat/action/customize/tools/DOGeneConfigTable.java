package com.exedosoft.plat.action.customize.tools;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.gene.jquery.ATableForwarderJquery;

public class DOGeneConfigTable extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(DOGeneConfigTable.class);

	/**
	 * Save �����������Parameter ȡֵʱ������auto_condition����ѯ�� �����
	 */

	public String excute() {

		DOBO bo = DOBO.getDOBOByName("do_datasource");
		String dataSourceUid = bo.getCorrInstance().getUid();

		String[] tables = this.actionForm.getValueArray("checkinstance");
		String bpUid = this.actionForm.getValue("bpUid");
		String[] keyCols = this.actionForm.getValueArray("keyCol");
		String[] valueCols = this.actionForm.getValueArray("valueCol");
		String[] hiddenTables = this.actionForm
				.getValueArray("checkinstance_hidden");

		List<BOInstance> allCheckInstances = new ArrayList<BOInstance>();
		StringBuilder echo = new StringBuilder();
		for (int i = 0; i < hiddenTables.length; i++) {
			String aHiddenTable = hiddenTables[i];
			for (int check = 0; check < tables.length; check++) {
				if (aHiddenTable.equals(tables[check])) {
					BOInstance aIns = new BOInstance();
					if (i < keyCols.length) {
						if (keyCols[i] != null && !keyCols[i].trim().equals("")) {
							aIns.putValue("tableName", aHiddenTable);
							aIns.putValue("keycol", keyCols[i]);
							aIns.putValue("valuecol", valueCols[i]);
							allCheckInstances.add(aIns);
						} else {
							echo.append("��::" + aHiddenTable
									+ "û�ж����������޷����г�ʼ��!");
						}
					} else {

						this.setEchoValue("������ڲ����������Ի�ѡ��firefox!");
						return NO_FORWARD;
					}
				}
			}
		}

		if (allCheckInstances.size() == 0) {
			this.setEchoValue("û��ѡ�����ݱ��û�ж�������!");
			return NO_FORWARD;
		}

		log.info("echo::" + echo);

		// ////////////////�������
		try {

			for (java.util.Iterator<BOInstance> it = allCheckInstances
					.iterator(); it.hasNext();) {
				BOInstance bi = it.next();
				String aTable = bi.getValue("tableName");
				String keycol = bi.getValue("keycol");
				String valuecol = bi.getValue("valuecol");

				ATableForwarderJquery af = new ATableForwarderJquery(aTable,
						keycol, valuecol, dataSourceUid, bpUid);
				af.forwardAll();
			}

		} catch (Exception ex1) {
			ex1.printStackTrace();
			this.setEchoValue(ex1.getMessage());
			return NO_FORWARD;

		}
		this.setEchoValue("��ʼ���ɹ�!");
		return DEFAULT_FORWARD;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
