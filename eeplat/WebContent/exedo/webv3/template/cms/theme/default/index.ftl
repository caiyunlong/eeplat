<#include "default/header.ftl">
	<div id="content">
		<div id="content_text" class="content_text">
			<#list Blog as data >
				<div>
					<h3><p><a href=${data.url} >${data.title}</a></p></h3>
					<p class="summary">������${data.date}��${data.user!"admin"}</p>
					<p>${data.content}</p>
					<p class="summary">������ ${data.cety} | �������� </p>
				</div>
			</#list>
			
		</div><!-- content_text -->
<#include "default/sidebar.ftl">
<#include "default/footer.ftl">
	
</div><!-- mainframe -->

</body>
</html>