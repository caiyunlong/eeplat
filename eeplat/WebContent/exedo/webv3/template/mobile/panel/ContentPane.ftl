<div data-role="content">
    <script>
         $('#${model.name}').bind('pagehide',function(event, ui){
            if(ui.nextPage){
	              if( $(this).attr("id")===ui.nextPage.attr("id") ){
	                  if( $(this).attr("id")===ui.nextPage.attr("id") ){
	                 	   $(this).remove();
	                 	 }
	              }
             }
          });
          
        $('#${model.name}').bind('pagebeforeshow',function(event){
        ////��һ����active Ҳ�п����ظ���nnd Ҫ��һ��ͳһ�Ľ������
        
             if($.mobile.activePage!=null &&  ($.mobile.activePage.attr("id")=='${model.name}')){
                  if($.mobile.activePage!=null &&  ($.mobile.activePage.attr("id")=='${model.name}')){
                    	$.mobile.activePage.remove();
                      $.mobile.urlHistory.stack.pop();  	
                 }
             }
				     return true;
				});
     </script>
 	${items_html}
</div>
