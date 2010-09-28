package com.exedosoft.plat.ui.jquery.tree;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.DOServletContext;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOParameter;
import com.exedosoft.plat.bo.DOParameterService;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOTreeModel;
import com.exedosoft.plat.util.DOGlobals;

/**
 * Servlet implementation class for Servlet: LoadTreeSvl
 * 
 */
public class LoadTreeSvl extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5836729213406076319L;

	private static final String CONTENT_TYPE = "text/xml; charset=utf-8";

	private static Log log = LogFactory.getLog(LoadTreeSvl.class);

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LoadTreeSvl() {
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

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType(CONTENT_TYPE);

		// this.getServletConfig(), this
		// .getServletContext(),
		// //////////////////////////��ʼ��ȫ��������
		DOGlobals.getInstance().refreshContext(
				new DOServletContext(request, response));

		DOGlobals.getInstance().getSessoinContext().getThreadContext()
				.initDataBus();

		String instanceUid = request.getParameter("contextInstanceUid");
		// System.out.println("instanceUid" + instn)
		String treeModelUid = request.getParameter("treeModelUid");
		String containerPaneUid = request.getParameter("containerPaneUid");

		// System.out.println("Content Pane==========" + containerPaneUid);

		String initOnly = request.getParameter("initOnly");
		String dropDownID = request.getParameter("dropDownID");

		String result = null;
		// HbmDAO dao = new HbmDAO();
		// dao.setAutoClose(false);
		try {

			DOTreeModel treeModel = DAOUtil.INSTANCE().getByObjUid(DOTreeModel.class,
					treeModelUid);
			if (dropDownID != null) {
				treeModel.setDropDownID(dropDownID);
			}

			// //////////////��ʼ����ǰ�����Ļ���1
			if (instanceUid != null) {
				if (treeModel.getParent() != null) {
					DOService parentService = null;
					if (treeModel.getParent().getService() != null) {
						parentService = treeModel.getParent().getService();
					} else {
						parentService = this.getIfParentService(treeModel
								.getParent());
					}
					if (parentService != null) {
						parentService.getBo().refreshContext(instanceUid);
					}

				}
			}
			if (initOnly != null) {
				log.info("instanceUid::" + instanceUid);
				log.info("treeModelUid::" + treeModelUid);
				/**
				 * ����ǳ�ʼ�������Ļ�����ִ�е�����ͷ��ء�
				 */
				return;
			}

			DOPaneModel containerPaneModel = null;
			if (containerPaneUid != null) {
				containerPaneModel = DOPaneModel
						.getPaneModelByID(containerPaneUid);
				treeModel.setContainerPane(containerPaneModel);

			}

			log.info("treeModel.getStatus()::::" + treeModel.getStatus());

			switch (treeModel.getStatus()) {

			case DOTreeModel.STATUS_SELFLINK_ONLY:
				result = dealSelfLinkOnly(treeModel, instanceUid);
				break;
			case DOTreeModel.STATUS_SELFLINK_CHILD:
				result = this.dealSelfLinkChild(treeModel, instanceUid);
				break;
			case DOTreeModel.STATUS_CHILD_NULL:
				result = this.dealChildNull(treeModel, instanceUid);
				break;
			case DOTreeModel.STATUS_CHILD_ONE:
				result = this.dealChildOne(treeModel, instanceUid);
				break;
			case DOTreeModel.STATUS_CHILD_MULT:
				result = this.dealChildMult(treeModel, instanceUid);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// finally {
		// dao.closeSession();
		// }
		PrintWriter out = response.getWriter();
		System.out.println(result);
		out.println(result);

	}

	/**
	 * @param treeModel
	 */
	private String dealSelfLinkOnly(DOTreeModel treeModel, String instanceUid) {

		List list = treeModel.getService().invokeSelect(instanceUid);
		addListforNew(list, treeModel);
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n<tree>\n");

		dealSelfLinkOnlyHelper(treeModel, list, buffer, instanceUid);

		buffer.append("</tree>\n");
		return buffer.toString();
	}

	/**
	 * @param treeModel
	 * @param instanceUid
	 * @param list
	 * @param buffer
	 */
	private void dealSelfLinkOnlyHelper(DOTreeModel treeModel, List list,
			StringBuffer buffer, String instanceUid) {

		for (Iterator it = list.iterator(); it.hasNext();) {
			BOInstance instance = (BOInstance) it.next();
			buffer.append("\n<tree text=\"");

			buffer.append(instance.getName());

			this.appendIconXML(treeModel, buffer);

			List listChilds = treeModel.getService().invokeSelect(
					instance.getUid());

			// ////////ִ������Ĵ����������������
			if ((listChilds != null && listChilds.size() > 0) || // //////���������
					(treeModel.getChildren() != null && treeModel.getChildren()
							.size() > 0) // ///���滹�в��
			) {

				appendSrc(treeModel.getObjUid(), treeModel, instance.getUid(),
						buffer);
			}

			appendAction(treeModel, buffer, instance, instanceUid);
			buffer.append("\"/>\n"); // / xml �رշ���
		}

	}

	/**
	 * @param treeModel
	 */
	private String dealSelfLinkChild(DOTreeModel treeModel, String instanceUid) {

		List list = treeModel.getService().invokeSelect(instanceUid);

		addListforNew(list, treeModel);
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n<tree>\n");
		dealSelfLinkOnlyHelper(treeModel, list, buffer, instanceUid);
		dealMultChildHelper(treeModel, buffer, instanceUid);
		buffer.append("</tree>\n");
		return buffer.toString();
	}

	/**
	 * ����һ���ڵ�ӵ�ж���ӽڵ�����
	 * 
	 * @param treeModel
	 * @return
	 */
	private String dealChildMult(DOTreeModel treeModel, String instanceUid) {

		// ////���û������Service,չ��ʱû��������ݿ�����
		// ////////������Ϊ����ʹ��
		if (treeModel.getService() == null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\n<tree>\n");
			dealMultChildHelper(treeModel, buffer, instanceUid);
			buffer.append("</tree>\n");
			return buffer.toString();
		}

		// ///////////��ǰ�Ĳ�ѯ
		List list = treeModel.getService().invokeSelect(instanceUid);

		// ///////////�ж����������child,������һ��child�������ӣ��������child��Ӧ�Ĳ�ѯû��ֵ��
		// ��ô������ΪSTATUS_CHILD_ONE����
		List children = treeModel.getChildren();
		if (children.size() == 2) {

			DOTreeModel commModel = null;
			DOTreeModel selfModel = null;
			DOTreeModel childModel0 = (DOTreeModel) children.toArray()[0];
			DOTreeModel childModel1 = (DOTreeModel) children.toArray()[1];

			if (childModel0.isSelf()) {
				selfModel = childModel0;
				commModel = childModel1;
			}
			if (childModel1.isSelf()) {
				selfModel = childModel1;
				commModel = childModel0;
			}
			if (commModel != null && selfModel != null) {
				List listChilds = selfModel.getService().invokeSelect(
						instanceUid);
				if (listChilds.size() == 0) {

					StringBuffer buffer = new StringBuffer();
					buffer.append("<tree>\n");

					dealChildOneHelper(treeModel, instanceUid, list, buffer,
							commModel);
					buffer.append("</tree>\n");
					return buffer.toString();
				}
			}
		}

		// ///////////////////end �ж�

		int i = 0;

		if (list != null) {
			i = list.size();
		}
		addListforNew(list, treeModel);
		StringBuffer buffer = new StringBuffer();
		buffer.append("<tree>\n");
		for (Iterator it = list.iterator(); it.hasNext();) {

			// //����һ���ӽڵ�
			BOInstance instance = (BOInstance) it.next();

			buffer.append("\n<tree text=\"");
			buffer.append(instance.getName());
			this.appendIconXML(treeModel, buffer);
			appendAction(treeModel, buffer, instance, instanceUid);
			buffer.append("\">\n");
			if (i != 0) {
				dealMultChildHelper(treeModel, buffer, instance.getUid());
			}
			buffer.append("</tree>");
		}
		buffer.append("</tree>\n");
		return buffer.toString();
	}

	/**
	 * @param treeModel
	 * @param instanceUid
	 * @param buffer
	 */
	private void dealMultChildHelper(DOTreeModel treeModel,
			StringBuffer buffer, String instanceUid) {

		for (Iterator itChild = treeModel.getChildren().iterator(); itChild
				.hasNext();) {
			DOTreeModel childModel = (DOTreeModel) itChild.next();
			childModel.setDropDownID(treeModel.getDropDownID());
			if (childModel.isSelf()) {
				log.info("Enter is Self::::" + childModel.getL10n());
				setTempPara(instanceUid, treeModel, childModel);
				List list = childModel.getService().invokeSelect();
				childModel.setContainerPane(treeModel.getContainerPane());
				dealSelfLinkOnlyHelper(childModel, list, buffer, instanceUid);
			} else {

				if (childModel.getService() == null) {
					buffer.append("\n<tree text=\"").append(
							childModel.getL10n());
					this.appendIconXML(treeModel, buffer);

					appendSrc(childModel.getObjUid(), treeModel, instanceUid,
							buffer);
					buffer.append("\"/>\n"); // / xml �رշ���

				} else {

					List listChilds = childModel.getService().invokeSelect(
							instanceUid);

					if (listChilds != null && listChilds.size() > 0) {

						buffer.append("\n<tree text=\"").append(
								childModel.getL10n());
						this.appendIconXML(treeModel, buffer);

						appendSrc(childModel.getObjUid(), treeModel,
								instanceUid, buffer);
						buffer.append("\"/>\n"); // / xml �رշ���
					}

				}
			}

		}
	}

	private void setTempPara(String instanceUid, DOTreeModel treeModel,
			DOTreeModel selfLinkModel) {

		for (Iterator it = selfLinkModel.getService()
				.retrieveParaServiceLinks().iterator(); it.hasNext();) {
			DOParameterService dops = (DOParameterService) it.next();
			DOParameter dop = dops.getDop();
			if (dop.getParaBO().getObjUid().equals(
					treeModel.getService().getBo().getObjUid())) {
				selfLinkModel.getService().addTempParaValue(dops, instanceUid);
				return;
			}
		}

	}

	/**
	 * @param list
	 * @param buffer
	 */
	private String dealChildNull(DOTreeModel treeModel, String instanceUid) {

		List list = treeModel.getService().invokeSelect(instanceUid);
		addListforNew(list, treeModel);
		StringBuffer buffer = new StringBuffer();
		buffer.append("<tree>\n");
		for (Iterator it = list.iterator(); it.hasNext();) {
			BOInstance instance = (BOInstance) it.next();
			buffer.append("\n<tree text=\"");
			buffer.append(instance.getName());
			this.appendIconXML(treeModel, buffer);
			appendAction(treeModel, buffer, instance, instanceUid);
			buffer.append("\"/>\n"); // / xml �رշ���
		}
		buffer.append("</tree>\n");
		return buffer.toString();
	}

	/**
	 * @param list
	 * @param buffer
	 */
	private String dealChildOne(DOTreeModel treeModel, String instanceUid) {

		List list = treeModel.getService().invokeSelect(instanceUid);
		addListforNew(list, treeModel);
		StringBuffer buffer = new StringBuffer();
		buffer.append("<tree>\n");

		DOTreeModel childModel = (DOTreeModel) treeModel.getChildren()
				.toArray()[0];
		dealChildOneHelper(treeModel, instanceUid, list, buffer, childModel);
		buffer.append("</tree>\n");
		return buffer.toString();
	}

	private void dealChildOneHelper(DOTreeModel treeModel, String instanceUid,
			List list, StringBuffer buffer, DOTreeModel childModel) {
		childModel.setDropDownID(treeModel.getDropDownID());
		for (Iterator it = list.iterator(); it.hasNext();) {
			BOInstance instance = (BOInstance) it.next();
			buffer.append("\n<tree text=\"");
			buffer.append(instance.getName());
			this.appendIconXML(treeModel, buffer);

			List listChilds = childModel.getService().invokeSelect(
					instance.getUid());
			if (listChilds != null && listChilds.size() > 0) {

				appendSrc(childModel.getObjUid(), treeModel, instance.getUid(),
						buffer);
			}

			appendAction(treeModel, buffer, instance, instanceUid);
			buffer.append("\"/>\n"); // / xml �رշ���
		}
	}

	/**
	 * @param treeModel
	 * @param instanceUid
	 * @param buffer
	 * @param childModel
	 */
	private void appendSrc(String linkTreeModelUid, DOTreeModel treeModel,
			String instanceUid, StringBuffer buffer) {
		if (instanceUid == null) {
			return;
		}
		buffer.append("\" src=\"");
		appendBaseUrl(linkTreeModelUid, instanceUid, buffer);
		if (treeModel.getContainerPane() != null) {
			buffer.append("&amp;containerPaneUid=").append(
					treeModel.getContainerPane().getObjUid());
		}
		if (treeModel.getDropDownID() != null) {
			buffer.append("&amp;dropDownID=").append(treeModel.getDropDownID());

		}

		appendIconURL(treeModel, buffer, true);
	}

	public static void appendIconURL(DOTreeModel treeModel,
			StringBuffer buffer, boolean isXmlTrans) {

		// if (treeModel.getService() != null) {
		//
		// System.out.println("ICON:::::::::::::::"
		// + treeModel.getService().getBo().getIcon());
		// }

		String andSymbol = "&";
		if (isXmlTrans) {
			andSymbol = "&amp;";
		}

		if (treeModel.getIcon() != null
				&& !treeModel.getIcon().trim().equals("")) {
			buffer.append(andSymbol).append("icon=")
					.append(treeModel.getIcon());
		} else {
			if (treeModel.getService() != null
					&& treeModel.getService().getBo().getIcon() != null
					&& !treeModel.getService().getBo().getIcon().trim().equals(
							"")) {
				buffer.append(andSymbol).append("icon=").append(
						treeModel.getService().getBo().getIcon());
			}

		}

		if (treeModel.getOpenIcon() != null
				&& !treeModel.getOpenIcon().trim().equals("")) {
			buffer.append(andSymbol).append("openIcon=").append(
					treeModel.getOpenIcon());
		} else {

			if (treeModel.getService() != null
					&& treeModel.getService().getBo().getOpenIcon() != null
					&& !treeModel.getService().getBo().getOpenIcon().trim()
							.equals("")) {
				buffer.append(andSymbol).append("openIcon=").append(
						treeModel.getService().getBo().getOpenIcon());
			}
		}
	}

	private void appendIconXML(DOTreeModel treeModel, StringBuffer buffer) {

		if (treeModel.getIcon() != null
				&& !treeModel.getIcon().trim().equals("")) {
			buffer.append("\" icon=\"").append(treeModel.getIcon());
		} else {
			if (treeModel.getService() != null
					&& treeModel.getService().getBo().getIcon() != null
					&& !treeModel.getService().getBo().getIcon().trim().equals(
							"")) {

				buffer.append("\" icon=\"").append(
						treeModel.getService().getBo().getIcon());
			}

		}

		if (treeModel.getOpenIcon() != null
				&& !treeModel.getOpenIcon().trim().equals("")) {
			buffer.append("\" openIcon=\"").append(treeModel.getOpenIcon());
		} else {

			if (treeModel.getService() != null
					&& treeModel.getService().getBo().getOpenIcon() != null
					&& !treeModel.getService().getBo().getOpenIcon().trim()
							.equals("")) {
				buffer.append("\" openIcon=\"").append(
						treeModel.getService().getBo().getOpenIcon());
			}
		}
	}

	/**
	 * @param linkTreeModelUid
	 * @param instanceUid
	 * @param buffer
	 */
	private void appendBaseUrl(String linkTreeModelUid, String instanceUid,
			StringBuffer buffer) {
		buffer.append("/").append(DOGlobals.URL).append(
				"/loadtreesvl?treeModelUid=");
		buffer.append(linkTreeModelUid);
		buffer.append("&amp;contextInstanceUid=").append(instanceUid);
	}

	/**
	 * @param treeModel
	 * @param buffer
	 * @param instance
	 * @param parentUid
	 *            TODO
	 */
	private void appendAction(DOTreeModel treeModel, StringBuffer buffer,
			BOInstance instance, String parentUid) {

		if (treeModel.getActionPane() != null
				&& treeModel.getDropDownID() == null) {

			appendActionUrl(treeModel, buffer, instance, parentUid);

			if (treeModel.getInitPane() != null) {

				String initUrl = treeModel.getInitPane().getFullCorrHref(
						instance, null);
				initUrl = initUrl.replaceAll("&", "&amp;");
				// /////////////////////ȷ��targetPaneID
				String targetPaneId = treeModel.getInitPane().getName();
				if (treeModel.getInitPane().getTargetPane() != null) {
					targetPaneId = treeModel.getInitPane().getTargetPane()
							.getName();
				}
				// //////////////////////ȷ��targetPaneID
				// $("#divMain").load();
				// loadPml({
				// 'pml':'${model.linkPaneModel.name}',
				// 'title':'${model.linkPaneModel.title}',
				// 'formName':'a${model.gridModel.objUid}'
				// <#if (item.targetPaneModel)?exists>
				// ,'target':'${model.targetPaneModel.name}'
				// </#if> }
				// );

				buffer.append(";loadPml({'pml':'").append(
						treeModel.getActionPane().getName()).append(
						"','pmlName':'").append(
						treeModel.getActionPane().getName()).append(
						"','title':'").append(instance.getName()).append(
						"','target':'").append(
						treeModel.getTargetPane().getName()).append("'} );");

				// buffer.append("\"   action=\"javascript:$('#"
				// + treeModel.getTargetPane().getName().replace(".", "_")
				// + "').empty().load('"
				// + treeModel.getActionPane().getFullCorrHref(instance,
				// null).replaceAll("&", "&amp;") + "");
				// buffer.append("');");
			}

		} else if (treeModel.getDropDownID() != null) {

			// buffer.append("\"  action=\"javascript:dojo.widget.byId('").append(
			// treeModel.getDropDownID()).append("').onSetValues('")
			// .append(instance.getName()).append("','").append(
			// instance.getUid()).append("','").append(
			// treeModel.getDropDownID()).append("');");

		}
	}

	/**
	 * ��������ڵ�ʱ��������Action's URL
	 * 
	 * @param treeModel
	 * @param buffer
	 * @param instance
	 * @param parentUid
	 */
	private void appendActionUrl(DOTreeModel treeModel, StringBuffer buffer,
			BOInstance instance, String parentUid) {

		String actionUrl = "";
		String targetPaneId = "";
		if (treeModel.getNoRecordPane() != null && instance != null
				&& instance.getUid().equals("tree.new.record")) {
			actionUrl = treeModel.getNoRecordPane().getFullCorrHref(instance,
					null);
			// /////////////////////ȷ��targetPaneID
			targetPaneId = treeModel.getNoRecordPane().getName();
			if (treeModel.getNoRecordPane().getTargetPane() != null) {
				targetPaneId = treeModel.getNoRecordPane().getTargetPane()
						.getName();
			}

		} else {
			actionUrl = treeModel.getActionPane().getFullCorrHref(instance,
					null);
			// /////////////////////ȷ��targetPaneID
			targetPaneId = treeModel.getTargetPaneID();
		}

		if (treeModel.getParent() != null && !treeModel.isSelf()) {///������һ��������������Ͻ�Ӧ��һֱ���µ����ϲ�(��Ҫ����㴦������xtree2.js��һ����ֻ�н����������֪���ϲ�ڵ�Ķ�Ӧ�����ҿ���Ϊ���ڵ������)
			actionUrl = new StringBuffer(actionUrl).append(
					"&dataBus=setContext&contextKey=").append(
					treeModel.getParent().getService().getBo().getName())
					.append("&contextValue=").append(parentUid).toString();
		}

		actionUrl = actionUrl.replaceAll("&", "&amp;");

		buffer.append("\"   action=\"javascript:loadPml({'pml':'").append(
				actionUrl).append("','pmlName':'").append(
				treeModel.getActionPane().getName()).append("','title':'")
				.append(instance.getName()).append("','target':'").append(
						treeModel.getTargetPane().getName()).append("'} );");

		// buffer.append("\" id='").append(actionUrl).append(
		// "'  action=\"javascript:$('#"
		// + treeModel.getTargetPane().getName().replace(".", "_")
		// + "').empty().load('" + actionUrl);
		// $('#webfx-tree-row:eq(0)').parent('table .gMain').next('tr
		// .gRi').next('div #divMain').next('div #mRight')
		// if (!treeModel.isSelf() && parentUid != null) {
		// buffer.append("','");
		// this.appendBaseUrl(treeModel.getObjUid(), parentUid, buffer);
		// buffer.append("&amp;initOnly=init");
		// }
		// buffer.append("'");
		// // if (treeModel.getActionPane().getIsCache() != null
		// // && !treeModel.getActionPane().getIsCache().booleanValue()) {
		// buffer.append(",false");
		// }
		// buffer.append("');");
	}

	private void addListforNew(List list, DOTreeModel treeModel) {

		if (list != null && list.size() > 0) {
			return;
		}
		if (treeModel.getParent() != null) {
			return;
		}
		DOService service = treeModel.getService();
		if (service == null || service.getBo() == null) {
			return;
		}
		if (list == null) {
			list = new ArrayList();
		}
		BOInstance instance = new BOInstance();
		instance.setUid("tree.new.record");
		instance.setBo(service.getBo());
		instance.putValue(service.getBo().getValueCol(), "����");
		list.add(instance);

	}

	public DOService getIfParentService(DOTreeModel treeModel) {

		if (treeModel.getStatus() == DOTreeModel.STATUS_CHILD_MULT) {
			if (treeModel.getService() == null) {
				if (treeModel.getParent() != null) {
					if (treeModel.getParent().getService() != null) {
						return treeModel.getParent().getService();
					} else {
						return getIfParentService(treeModel.getParent());
					}
				}

			}
		}
		return null;
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

}