package com.exedosoft.plat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.login.LoginDelegateList;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.Escape;

public class SSOController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8828111215284288122L;

	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	// private static Log log = LogFactory.getLog(ServiceController.class);

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public SSOController() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		/**
		 * ֲ��session
		 */
		SessionContext us = new SessionContext();
		System.out.println("��һ�γ�ʼ��Session=================");
		request.getSession().setAttribute("userInfo", us);

		// ///////////////////////////*************************����������
		// this.getServletConfig(), this
		// .getServletContext(),

		DOGlobals.getInstance().refreshContext(
				new DOServletContext(request, response));

		BOInstance formBI = getFormInstance(request);
		System.out.println("Get the form Instance::::::::::::::");
		System.out.println(formBI);

		DOGlobals.getInstance().getSessoinContext().setFormInstance(formBI);

		String serviceUid = request.getParameter("contextServiceUid"); // /////////ҵ��������uid
		DOService curService = null;
		if (serviceUid != null && !serviceUid.trim().equals("")) {
			curService = DOService.getServiceByID(serviceUid);
		} else {
			String serviceName = request.getParameter("contextServiceName");
			System.out.println("use contextServiceName::" + serviceName);
			if (serviceName != null && !serviceName.trim().equals("")) {
				curService = DOService.getService(serviceName);
			}
		}

		if (curService == null) {
			out.println("{error:noservice}");
			return;
		}

		String contextInstanceUid = formBI.getValue("contextInstanceUid");
		if (contextInstanceUid != null && !contextInstanceUid.trim().equals("")) {

			String contextClassUid = formBI.getValue("contextClassUid");
			BOInstance bi = null;
			if (contextClassUid != null && !contextClassUid.trim().equals("")) {
				DOBO bo = DOBO.getDOBOByID(contextClassUid);
				bi = bo.refreshContext(contextInstanceUid);
			} else if (curService.getBo() != null) {
				System.out.println("RefreshContextInstance:::::::::"
						+ contextInstanceUid);
				bi = curService.getBo().refreshContext(contextInstanceUid);
			}
		}

		/**
		 * ִ���û�������ض���¼Action �� LoginAction2 LoginAction�ȡ�
		 */
		String returnValue = null;
		try {
			returnValue = curService.invokeAll();
		} catch (ExedoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		boolean isDelegate = false;
		try {
			isDelegate = LoginDelegateList.isDelegate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}


		System.out.println("ResultValue is :" + returnValue);

		StringBuffer outHtml = new StringBuffer();

		System.out.println("use jslib:::" + DOGlobals.getValue("jslib"));

		if ("jquery".equals(DOGlobals.getValue("jslib"))) {

			// ////Ϊ�˱����ṹ��û��ʵ������
			outHtml.append("{returnPath:'").append("',targetPane:'");
			// /////////value
			String echoStr = DOGlobals.getInstance().getRuleContext()
					.getEchoValue();

			echoStr = echoStr.trim();
			if (echoStr == null || echoStr.trim().equals("")) {
				echoStr = "success";
				if(isDelegate){
					echoStr = "delegate";
				}
			}
			if (!formBI.getValue("randcode").equals(
					request.getSession().getAttribute("rand"))) {
				echoStr = "��֤�����";
			}

			outHtml.append("',returnValue:'").append(echoStr).append("'}");

		} else if ("ext".equals(DOGlobals.getValue("jslib"))) {

			if (!"success".equals(returnValue)) {
				returnValue = "�û���/�������,������!";
			} else {

				System.out.println(formBI.getValue("randcode"));
				System.out.println(request.getSession().getAttribute("rand"));

			}
			outHtml.append("{success:true,msg:\'").append(returnValue).append(
					"\'}");

		} else {
			// ////Ϊ�˱����ṹ��û��ʵ������
			outHtml.append("{returnPath:'").append("',targetPane:'");
			// /////////value
			String echoStr = DOGlobals.getInstance().getRuleContext()
					.getEchoValue();
			if (echoStr == null) {
				echoStr = "";
			}
			echoStr = echoStr.trim();
			outHtml.append("',returnValue:'").append(echoStr).append("'}");
		}

		out.println(outHtml);

	}


	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private BOInstance getFormInstance(HttpServletRequest request) {

		BOInstance formInstance = new BOInstance();
		Map formValues = request.getParameterMap();

		for (Iterator it = formValues.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String[] values = (String[]) formValues.get(key);

			if (values != null && values.length == 1) {
				formInstance.putValue(key, Escape.unescape(values[0]));
			} else {
				escapeValues(values);
				formInstance.putValue(key, values);
			}
		}
		return formInstance;
	}

	private void escapeValues(String[] values) {
		if (values == null) {
			return;
		}
		for (int i = 0; i < values.length; i++) {
			String aValue = values[i];
			values[i] = Escape.unescape(aValue);
		}

	}
}
