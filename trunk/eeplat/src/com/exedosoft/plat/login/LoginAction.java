package com.exedosoft.plat.login;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.util.DOGlobals;

/**
 * 
 * this page must be the first page of huidian system. the classify default
 * config must initiazation.
 * 
 * @author aa
 * 
 */
public class LoginAction extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2070715238350882833L;

	public String excute() {

		List users = new ArrayList();

		if (DOGlobals.LOGIN_CA == DOGlobals.LOGIN_CA_YES) {

			// HttpServletRequest request =
			// DOGlobals.getInstance().getServletContext().getRequest();
			// String caID =
			// (String)request.getSession().getAttribute("UserId");
			// if(caID==null || "".equals(caID.trim())){
			// this.setEchoValue("�Բ�����û��ͨ��CA��֤��");
			// return "notpass";
			// }
			// System.out.println(caID);
			// users = findByCaCode.invokeSelect(caID);
		} else {
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
			if ("0".equals(fdstate)) {
				this.setEchoValue("���û��Ѷ��ᣬ���ϵͳ����Ա��ϵ��");
				return NO_FORWARD;
			}
			LoginMain.makeLogin(user, DOGlobals.getInstance()
					.getServletContext().getRequest());
			
			return "success";
		} else {
			this.setEchoValue("�û������������������!");
			return "notpass";
		}

	}

}
