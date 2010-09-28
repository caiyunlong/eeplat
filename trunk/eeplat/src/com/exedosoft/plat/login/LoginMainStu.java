

package com.exedosoft.plat.login;



 import com.exedosoft.plat.bo.BOInstance;
 import java.util.List;
 import java.util.ArrayList;
 import java.net.URLDecoder;
 import java.util.Iterator;
 import java.util.Hashtable;
 import com.exedosoft.plat.util.id.UUIDHex;
 import com.exedosoft.plat.bo.*;
 import java.lang.*;
 import java.text.*;
 import java.util.Iterator;
 import java.net.URLEncoder;

import com.exedosoft.plat.util.DOGlobals;
 import com.exedosoft.plat.util.StringUtil;
  import com.exedosoft.plat.SessionContext;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.exedosoft.plat.bo.DOService;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//
//import cn.com.jit.assp.ias.principal.UserPrincipal;
//import cn.com.jit.assp.ias.saml.X509.X509Constants;
//import cn.com.jit.assp.ias.saml.saml11.SAMLAttributes;
//import cn.com.jit.assp.ias.saml.saml11.SAMLConstants;
//import cn.com.jit.assp.ias.sp.saml11.SPUtil;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.SessionContext;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOService;

public class LoginMainStu {

	private static Log log = LogFactory.getLog(LoginMainStu.class);
	private static DOService insertLoginLog = DOService.getService("do.log.insert");
	// ////////////ˢ�µ�ǰ��¼��λ
	private static DOBO aDeptBO = DOBO.getDOBOByName("do.bx.dept");



	// private static Object lockObj = new Object();

	public static int makeLogin(BOInstance user,HttpServletRequest request) {
	

		user.putValue("deptuid", user.getValue("dept_uid"));


		BOInstance biDept = null;
		if (aDeptBO != null) {
			biDept = aDeptBO.refreshContext(user.getValue("dept_uid"));
			System.out.println("��½����:::::::::;" + biDept);
			if (biDept != null) {
				user.putValue("deptobject", biDept);
				user.putValue("deptname", biDept.getName());
			}
		}
		
	    List gradeCounts=null;
	    DOService studentGrade = DOService.getService("tbstudent.browseGrade");
	    gradeCounts=studentGrade.invokeSelect(user.getUid());
	    String gradeInfor="";
	    for (Iterator it =gradeCounts.iterator();it.hasNext();){
	    	BOInstance dData=(BOInstance) it.next();
	    	gradeInfor=dData.getValue("fdgrade");
	    }
	    
	    
		System.out.println("gradeInfor::" + gradeInfor);
		user.putValue("grade_login", gradeInfor);

		// /////////////����ר�ã�user ͬʱЯ������������ createb by weikx at 070706
		// ����login����ȥ��

		if (biDept != null) {
			user.putValue("deptcode", biDept.getValue("code"));
		}
		
		

		SessionContext us = (SessionContext) request.getSession().getAttribute(
				"userInfo");
		if(us == null){
			us = new SessionContext();
			request.getSession().setAttribute("userInfo", us);
		}


	
		us.setSysTreeRoot(user.getValue("user_name"));
		us.setUser(user);
		us.setIp(DOGlobals.getInstance().getServletContext().getRequest()
				.getRemoteAddr());
		us.setSessionuid(DOGlobals.getInstance().getServletContext().getRequest().getSession().getId());
		
//	
//		BOInstance aInsertLog = new BOInstance();
//		aInsertLog.putValue("userName", user.getName());
//		aInsertLog.putValue("ip", us.getIp());
//		aInsertLog.putValue("sessionid", us.getSessionuid());
//		try {
//			// synchronized(lockObj){
//			insertLoginLog.invokeUpdate(aInsertLog);
//			// }
//		} catch (ExedoException e) {
//			// TODO Auto-generated catch block
//			log.info(e.fillInStackTrace());
//		}

		// /////������ĵ�ϵͳ
//		DOService findMaxDegree = DOService
//				.getService("do.bx.findmaxdegreeofuser");
//
//		String maxDegree = "0";
//		if (findMaxDegree != null) {
//			maxDegree = findMaxDegree.invokeSelectGetAValue(user.getUid());
//
//			if (maxDegree == null || "".equals(maxDegree.trim())) {
//				maxDegree = "0";
//			}
//			log.info("���û���Ȩ�޼�����:" + maxDegree);
//		}

		// System.out.println("llllllllllll"
		// + DOGlobals.getInstance().getServletContext().getRequest()
		// .getSession().getAttribute("userInfo"));

		// ///////���ڹ�����Դ����Ŀ��Ϊ�˸��õ�����̽�󣬲ɿ󣬵��ʿ������ʶ��ӵ�����
		// /////////1,̽��,2,�ɿ�,3,�ɿ�̽���� ..........�����չ
		// /////////
		List roles = getCorrRoles(user.getUid());
		if (roles.size() > 0) {
			int iJudge = 0;
			for (Iterator it = roles.iterator(); it.hasNext();) {
				String aRole = (String) it.next();
				if ("qtsy_sq_phsq".equalsIgnoreCase(aRole)) {
					user.putValue("gtType", "2");
					iJudge++;
				}
				if ("qtsy_sq_tkq".equalsIgnoreCase(aRole)) {
					user.putValue("gtType", "1");
					iJudge++;
				}
				if("qtsy_leader".equalsIgnoreCase(aRole)){
					iJudge++;
					iJudge++;
					user.putValue("gtType", "3");
					user.putValue("readonly", "readonly");
				}
			}
			if (iJudge >= 2) {
				user.putValue("gtType", "3");
			}
			if("admin".equals(user.getName())){
				user.putValue("gtType", "3");
			}
			log.info("������Դ��ר�ã����û��������:" + user.getValue("gtType"));

		}
		
	//	jit();
	
		
		/////////////////��ʼ��ȫ�ֲ���,��ʵӦ�÷���DOGlobals ������Ϊȫ�ֱ���������ƽ̨����ֱ��֧��ȫ�ֱ�����ֻ��������
		DOBO doGlobals = DOBO.getDOBOByName("do.globals");
		if(doGlobals!=null){
			doGlobals.refreshContext("000000");
		}

		//100000
		
		return 5;
	}
	
	
//	private static void jit(){
//		
//		
//		
//		UserPrincipal p = SPUtil.getUserPrincipal(DOGlobals.getInstance().getServletContext().getRequest());
////		��ȡ�û�����ID
//		String tokenId=(String)p.getAttribute(SAMLConstants.KEY_SAML_ATTR_ID);
////		��ȡ�û����Լ���
//		SAMLAttributes attrs =(SAMLAttributes)p.getAttribute
//		(SAMLConstants.KEY_SAML_ATTR_STATEMENT_ATTRIBUTES);
////		�����֤���û����ɲ�ѯ֤���DN
//			List values=attrs.getAttributeValue(
//		X509Constants.KEY_SAML_X509_SUBJECT_DN,
//		X509Constants.VALUE_X509_NAMESPACE);
//		String subjectDN=(String)values.get(0); //֤������Ϊ��ֵ����
//	
//				
//		System.out.println(subjectDN);
//		System.out.println(subjectDN);
//		System.out.println(subjectDN);
//		System.out.println(subjectDN);
//		System.out.println(subjectDN);
//		System.out.println(subjectDN);
//	
//		
//
//		
//		
//	}

	private HashMap getAllDepts() {
		DOService searchDepts = DOService.getService("do.bx.dept.list");
		HashMap allDepts = new HashMap();
		List depts = searchDepts.invokeSelect();
		if (depts != null) {
			for (Iterator it = depts.iterator(); it.hasNext();) {
				BOInstance deptBI = (BOInstance) it.next();
				allDepts.put(deptBI.getValue("code"), deptBI.getName());
			}
		}
		return allDepts;
	}

	public static List getCorrRoles(String accountUid) {

		DOService findUserService = DOService
				.getService("do.bx.role.findbyuserid");
		if (findUserService == null) {
			return null;
		}
		List listRoles = findUserService.invokeSelect(accountUid);
		List corrRoles = new ArrayList();
		if (listRoles != null) {
			for (Iterator roles = listRoles.iterator(); roles.hasNext();) {
				BOInstance boRole = (BOInstance) roles.next();
				// System.out.println(boRole);
				corrRoles.add(boRole.getValue("roleid"));
			}
			return corrRoles;
		}
		return null;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoginMainStu lm = new LoginMainStu();
		List roles = lm.getCorrRoles("6669");
		System.out.println(roles);

	}

}
