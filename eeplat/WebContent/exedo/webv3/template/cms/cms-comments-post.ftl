<!-- ���������ύ����ҳ�棬֮����ת-->
<#assign  s=JspTaglibs["/WEB-INF/c.tld"]>
<@s.redirect url="${get_options().comment_redirect_url}" + "?page_id=" + "${comment_posts_id.posts_id}"/>