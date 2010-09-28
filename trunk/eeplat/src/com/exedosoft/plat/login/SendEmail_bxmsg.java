package com.exedosoft.plat.login;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.login.zidingyi.excel.MySqlOperationII;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

/**
 * 
 * this page must be the first page of huidian system. the classify default
 * config must initiazation.
 * 
 * @author aa
 * 
 */
public class SendEmail_bxmsg extends DOAbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public String excute() {
		String baoxiaoempuid = null;// ������;
		String baoxiaoid = null;// ���������;
		String baoxiaostate = null;// ������״̬;

		String receivepeople = null;// �����˲����쵼(����)����һ��������;
		
		//�����ʼ����Թ���Ա��ݷ���
		String manager_email = "uii2008@sohu.com";// ����Ա�����ַ;
		String baoxiaoemail = null;// �����˵�ַ;
		 String emailTo = null;// �����˵�ַ ;

		List users = new ArrayList();
		
		try {
			users = service.invokeSelect();
		} catch (Exception e) {
			return this.DEFAULT_FORWARD;
		}

		// �ж��Ƿ������ݽ��ܣ�
		if (users != null && users.size() > 0) {
			 // �������е����ݣ�
			
			 for (int n = 0; n < users.size(); n++) {
			 String s = users.get(n).toString();
			 String st = s.substring(s.indexOf("{") + 1, s.lastIndexOf("}"));
			 String[] sarray = st.split(",");
							
			 
			
			
			 // ��ÿ�����ݽ��д���ȡ����Ч���ԣ�
			 for (int i = 0; i < sarray.length; i++) {
			 String temp = sarray[i];
			 String[] nv = temp.split("=");
			
			 if (nv.length == 2 && "baoxiaoempuid".equals(nv[0].trim()))
			 baoxiaoempuid = nv[1];
			 if (nv.length == 2 && "baoxiaoid".equals(nv[0].trim()))
			 baoxiaoid = nv[1];
			 if (nv.length == 2 && "receivepeople".equals(nv[0].trim()))
			 receivepeople = nv[1];
			 if (nv.length == 2 && "baoxiaostate".equals(nv[0].trim()))
			 baoxiaostate = nv[1];			
			 }
			 
			 /**
			  * // ȡ�������ַ;
			  */
			 
			// �����������ַ
			baoxiaoemail = getEmail(baoxiaoempuid);
			// �����������ַ
			emailTo = getEmail(receivepeople);
			
			//LDAP sn ȡ��cn	
//			String baoxiaoemp =  LDAPPeopleUtil.getLDAPCNBySN(baoxiaoempuid);
			
			//do_org_user_link user_uid ȡ��user_cn		
			String baoxiaoemp = null;
			try {
				Connection conii = MySqlOperationII.getConnection();
				baoxiaoemp =  MySqlOperationII.getUserCNByUserUid(conii, baoxiaoempuid);
				conii.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/**
			 * �����ʼ����������
			 */
			// �ύ�������ı�����
			String emailTitle = "���п�����������";// �ʼ�����;
			String emailText = "���������: " + baoxiaoid + "��\n������: "
					+ baoxiaoemp + "��\n������״̬�� " + baoxiaostate + "��\n����������";// �ʼ�����;

			// �˻صı�����
			String emailTitle_back = "���ı��������˻�";// �ʼ�����;
			String emailText_back = "���������: " + baoxiaoid + "��\n������: "
					+ baoxiaoemp + "��\n������״̬�� " + baoxiaostate + "��";// �ʼ�����;

			// ����ͨ���ı�����
			String emailTitle_success = "���ı�����ͨ��������";// �ʼ�����;
			String emailText_success = "���������: " + baoxiaoid + "��\n������: "
					+ baoxiaoemp + "��\n������״̬�� " + baoxiaostate + "��";// �ʼ�����;
			//������ַ
			String webaddress = "\n\n\t��¼�Ϸ㱨��ϵͳ��\nhttp://127.0.0.1:8080/yiyi/zfbx_manager";
			

			// //���ݱ�����״̬�������ʼ���
			try {
				if (baoxiaostate.contains("�˻�")) {
					sendEmail(manager_email, baoxiaoemail, emailTitle_back,
							emailText_back+webaddress);
				} else if (baoxiaostate.contains("�ܾ�������ͨ��")) {
					sendEmail(manager_email, baoxiaoemail, emailTitle_success,
							emailText_success+webaddress);
				} else if (baoxiaostate != null) {
					sendEmail(manager_email, emailTo, emailTitle, emailText+webaddress);
				} else {
					return this.DEFAULT_FORWARD;
				}
			} catch (Exception error) {
				System.out.println("*.I am sorry to tell you the fail for "
						+ error);
				return this.DEFAULT_FORWARD;
			}

			 }

			 return this.DEFAULT_FORWARD;

		} else {
			return this.DEFAULT_FORWARD;
		}
	}
	////////////////////////////////////////////////////////////////
	// �����ʼ�
	private void sendEmail(String from, String to, String title, String text)
			throws AddressException, MessagingException {
		
		//**************************************************8
		//������
		to = "yuanxx@zephyr.com.cn";
		//*****************************************************8
		
		String smtpHost = "smtp." + from.substring(from.lastIndexOf("@")+1);
		String password = "yyfxyxx2008";
		
		final Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");

		
		   Session myMailSession = Session.getInstance(props);
		   myMailSession.setDebug(true); // ��DEBUGģʽ
		   Message msg = new MimeMessage(myMailSession);
		   msg.setFrom(new InternetAddress(from));
		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		   msg.setContent("I have a email!", "text/plain");
		   msg.setSentDate(new java.util.Date());
		   msg.setSubject(title);
		   msg.setText(text);
		   System.out.println("1.Please wait for sending two...");

		   // �����ʼ�
		   Transport myTransport = myMailSession.getTransport("smtp");
		   myTransport.connect(smtpHost, from, password);
		   myTransport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
		   myTransport.close();
		   // javax.mail.Transport.send(msg); // ���в���ʹ�á�
		   System.out.println("2.Your message had send!");
		

	}

	// �����û���ȡ�������ַ
	private String getEmail(String user) {
		String mail = null;
		LDAPEntry fullEntry = null;
		LDAPConnection lc = null;
		LDAPAttributeSet set = null;
		LDAPSearchResults rs = null;
		final String MY_HOST = "localhost";
		final int MY_PORT = 389;
		final String ENTRYDN = "o=zephyr.com.cn";
		final String[] attrNames = { "sn", "mobile", "mail" };

		try {
			lc = new LDAPConnection();
			lc.connect(MY_HOST, MY_PORT);
			String password = "secret";
			try {
				lc.bind(3, "cn=Manager,o=zephyr.com.cn", password
						.getBytes("UTF8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			rs = lc.search(ENTRYDN, LDAPConnection.SCOPE_SUB, "sn=" + user,
					attrNames, false);

			while (rs.hasMore()) {
				try {
					fullEntry = rs.next();
					set = fullEntry.getAttributeSet();
					Iterator<?> attrs = set.iterator();
					while (attrs.hasNext()) {
						LDAPAttribute attribute = (LDAPAttribute) attrs.next();
						String name = attribute.getName();
						String value = attribute.getStringValue();
						if ("mail".equals(name)) {
							mail = value;
						}
					}
				} catch (LDAPException e) {
					System.out.println("Error:   " + e.toString());
					continue;
				}
			}
		} catch (LDAPException e) {
			System.err.print("�����쳣��   ");
			e.printStackTrace();
		} finally {
			if (lc != null && lc.isConnected()) {
				try {
					lc.disconnect();
				} catch (LDAPException e) {
					System.err.print("�����쳣��   1" + e.toString());
				}
			}
		}
		return mail;
	}

	public static void main(String[] args) {
		
		
	}
}
