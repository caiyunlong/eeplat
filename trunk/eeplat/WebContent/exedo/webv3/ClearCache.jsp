﻿<%@ page contentType="text/html;charset=UTF-8"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%@page import="com.exedosoft.plat.CacheFactory"%>
<% 
CacheFactory.getCacheData().clear();
CacheFactory.getCacheRelation().getData().clear();
CacheFactory.getCacheData().fromSerialObject();
out.println("清除缓存成功！");
%>