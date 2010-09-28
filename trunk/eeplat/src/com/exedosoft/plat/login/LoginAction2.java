package com.exedosoft.plat.login;

/**
 * ע�� �Ұ� date ת������ datetime ע��Ĺ���
 */
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.MVCController;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;

//
// //�޸ı�������
// public static final String MODIFY_SELF_PWD = "modify_self_pwd";
// //�û�����
// public static final String MANAGE_CLERK = "manage_clerk_p";
// //���ݲ�ѯ
// public static final String BROWSER_IMAGE = "browser_image_p";
// //ɨ���ϴ�
// public static final String SCAN_UPDATE = "scan_update_p";
// //����ظ�����
// public static final String CHECK_REDUPLICATE = "check_reduplicate_p";

public class LoginAction2 extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5564360845175975061L;

	private static Log log = LogFactory.getLog(LoginAction2.class);
//	private static DOService findByCaCode = DOService.getService("do.bx.user.browse.findbycacode");

	
	

	public String excute() {
		List users = new ArrayList();
		
		if(DOGlobals.LOGIN_CA == DOGlobals.LOGIN_CA_YES){
			
//		    HttpServletRequest request = 	DOGlobals.getInstance().getServletContext().getRequest();
//		    String caID =  (String)request.getSession().getAttribute("UserId");
//		    if(caID==null || "".equals(caID.trim())){
//		    	this.setEchoValue("�Բ�����û��ͨ��CA��֤��");
//		    	return "notpass";
//		    }
//		    System.out.println(caID);
//		    users = findByCaCode.invokeSelect(caID);
		}else{
			System.out.println(this.actionForm);
			users = service.invokeSelect();
		}
	
		if (users != null && users.size() > 0) {
			BOInstance user = (BOInstance) users.get(0);
			Date invalidTime = user.getDateValue("invalidTime");
			if (invalidTime != null) {
				System.out.println("���û��Ĺ���ʱ��::" + invalidTime);
				if (invalidTime.before(new Date(System.currentTimeMillis()))) {
					this.setEchoValue("���û��˻��Ѿ����ڣ�");
					return NO_FORWARD;
				}
			}
			
			String fdstate = user.getValue("fdstate");
			if("0".equals(fdstate)){
				this.setEchoValue("���û��Ѷ��ᣬ���ϵͳ����Ա��ϵ��");
				return NO_FORWARD;
			}
			LoginMain.makeLogin(user,DOGlobals.getInstance().getServletContext().getRequest());

			return "success";
		} else {
			this.setEchoValue("�û������������������!");
			return "notpass";
		}
	}
}


// ///////////�����ר��
//
//if (this.actionForm.getValue("inner_user") != null
//		&& Integer.parseInt(maxDegree) < 6) {
//	this.setEchoValue("�Բ�����ֻ����ý���û�����!");
//	return "notpass";
//}

// ////////////�����ר��

//BOInstance aDegreeIns = new BOInstance();
//aDegreeIns.putValue("secret_name", user.getName());
//aDegreeIns.putValue("secret_id", maxDegree);
//
//DOBO aDegreeBO = DOBO.getDOBOByName("sea.docsecret");
//DOGlobals.getInstance().getSessoinContext().putCorrInstance(
//		aDegreeBO, aDegreeIns);
// //////////////////������ĵ�ϵͳ
