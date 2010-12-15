package com.exedosoft.plat.action.customize.tools;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DODataSource;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.ExcuteSqlFile;

/**
 * 
 * 
 * �����ӣ������޸ĺ�ɾ��
 * 
 * ���Զ�������ɨ��
 * 
 * @author anolesoft
 * 
 */

public class DOExcuteSqlFile extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String excute() {

		DOBO bo = DOBO.getDOBOByName("do_datasource");
		DODataSource dss = DODataSource.getDataSourceByL10n(bo
				.getCorrInstance().getValue("l10n"));
		
		String fileName = null;
		
		URL url = DOGlobals.class.getResource("/globals.xml");
		String fullFilePath = url.getPath();
		String prefix = fullFilePath.substring(0, fullFilePath.toLowerCase()
				.indexOf("web-inf"));
		if("mysql".equalsIgnoreCase(dss.getDialect())){
			fileName = prefix + "/exedo/initsql/mysql.sql";
		}else if ("sqlserver".equalsIgnoreCase(dss.getDialect())){
			fileName = prefix + "/exedo/initsql/sqlserver2000.sql";
		}
		else if ("oracle".equalsIgnoreCase(dss.getDialect())){
			fileName = prefix + "/exedo/initsql/oracle.sql";
		}else if ("gae".equalsIgnoreCase(dss.getDialect())){
			fileName = prefix + "/exedo/initsql/gae.sql";
		}
		else if ("db2".equalsIgnoreCase(dss.getDialect())){
			fileName = prefix + "/exedo/initsql/db2.sql";
		}else{
			this.setEchoValue("����ֻ֧��mysql��oracle��sqlserver��db2��gaeĬ�ϳ�ʼ��!");
			return NO_FORWARD;
		}

		Connection con = null;
		try {
			con = dss.getConnection();
			DatabaseMetaData meta = con.getMetaData();
			String[] tblTypes = new String[] { "TABLE" };
			ResultSet rs = meta.getTables(null, null, null, tblTypes);
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME").toLowerCase();
				// ////////////��ǿ���¹���
				if(tableName.equalsIgnoreCase("do_org_dept")){
					rs.close();

					this.setEchoValue("ȱʡʵ�ֱ��Ѿ���ʼ������벻Ҫ�ظ���ʼ��!");
					changeTableDS(dss);
					return NO_FORWARD;
				}
			}
			rs.close();
	
			List<String> sqls = ExcuteSqlFile.readSqlFile(fileName);
			for(String sql:sqls){
				if(sql!=null && !sql.trim().equals("")){
					System.out.println("����ִ��SQL���:::" + sql);
					PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.execute();
				}
			}
			
		} catch (SQLException ex) {

			this.setEchoValue(ex.getMessage());
			return NO_FORWARD;

		} finally {
			try {
				if (!con.isClosed()) {
					con.close();
				}
			} catch (SQLException ex1) {
				this.setEchoValue(ex1.getMessage());
				return NO_FORWARD;

			}

		}
		
		
		changeTableDS(dss);

		
		this.setEchoValue("��ʼ����ɣ��벻Ҫ�ٴγ�ʼ����");
		return DEFAULT_FORWARD;
	}

	private void changeTableDS(DODataSource dss) {
		changeDataSource("do_org_dept",dss);
		changeDataSource("do_org_role",dss);
		changeDataSource("do_org_user",dss);
		changeDataSource("do_org_user_role",dss);
		changeDataSource("do_auth_owner",dss);
		changeDataSource("do_auth_suite",dss);
		changeDataSource("do_authorization",dss);
		changeDataSource("do_log",dss);
		changeDataSource("do_log_data",dss);
		changeDataSource("do_org_role_conflict",dss);
		changeDataSource("do_org_timespan",dss);

		changeDataSource("do_wfi_ni_dependency",dss);
		changeDataSource("do_wfi_nodeinstance",dss);
		changeDataSource("do_wfi_processinstance",dss);
		changeDataSource("do_wfi_varinstance",dss);
		changeDataSource("wf_person",dss);
		
		changeDataSource("do_wfi_his_ni_dependency",dss);
		changeDataSource("do_wfi_his_nodeinstance",dss);
		changeDataSource("do_wfi_his_processinstance",dss);
		changeDataSource("do_wfi_his_varinstance",dss);
		
		///���������Ե�����
		changeDataSource("t_expense",dss);
	}

	private void changeDataSource(String boName, DODataSource dss) {
		
		DOBO boDept = DOBO.getDOBOByName(boName);
		boDept.setDataBase(dss);
		try {
			DAOUtil.INSTANCE().store(boDept);
		} catch (ExedoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




}


