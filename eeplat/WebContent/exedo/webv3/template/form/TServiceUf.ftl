<#include "TFormBase.ftl">
<#--参数通过formName传递-->
<button type="button" style="${model.style?if_exists}"   id='${model.objUid}' <#compress><@JudgeStyle model/></#compress> >${model.l10n}</button>
<script>
 function fnCB${model.objUid}(){
  	<#if (!((model.inputConstraint)?exists && model.inputConstraint=='noCloseOpener'))>
	    <#if (model.gridModel.containerPane.name)?exists>
		try{
			if($('#F' + '${model.gridModel.containerPane.name}').size()>0){
	  			$('#F' + '${model.gridModel.containerPane.name}').dialog('close');
	  		}else{
	  			$('#' + '${model.gridModel.containerPane.name}').parents(".ui-dialog-content").dialog('close');
	  			<#if (model.gridModel.containerPane.parent)?exists>	
	  				$('#F' + '${model.gridModel.containerPane.parent.name}').dialog('close');
	  			</#if>
		  	}  	
	  	}catch(e){
	  	}		
	  	</#if>
	 </#if>
	 
	<#if ((model.inputConfig)?exists && model.inputConfig=='closeTab')>
	 	<#if (model.gridModel.containerPane.name)?exists>
			var tabBtnSelector = "#dvTab table[tabId='${model.gridModel.containerPane.name}'] .btn";
	  		tabCloseWindow(tabBtnSelector);
	  		<#if (model.gridModel.containerPane.parent)?exists>	 
			    tabBtnSelector = "#dvTab table[tabId='${model.gridModel.containerPane.parent.name}'] .btn";
			    tabCloseWindow(tabBtnSelector);
		 	</#if>
	  	</#if>
	 </#if>
	  
  	 <#if ((model.inputConfig)?exists && model.inputConfig=='loadParent')>
  		reloadTree();     
  	</#if>
   
 }

$('#${model.objUid}').bind('click',function(){
        callService({'btn':this,
                 <#if (model.note)?exists>
                 'msg':'${model.note}',
                 </#if>
        		 'callType':'uf',
        		 'serviceUid':'${model.linkService.objUid}',
        		 'paras':'invokeButtonUid=${model.objUid}',
		         'callback':fnCB${model.objUid},
		         'formName':'${model.targetForms}'
		         <#if (model.linkPaneModel)?exists>
		         ,'pml':'${model.linkPaneModel.name}'
		         	<#if (model.linkPaneModel.title)?exists>
		         		,'title':'${model.linkPaneModel.title}'
		         	 </#if>
		         ,'pmlWidth':'${model.linkPaneModel.paneWidth?if_exists}'
	   			 ,'pmlHeight':'${model.linkPaneModel.paneHeight?if_exists}'
		         <#else>
		         ,'pml':'${model.gridModel.containerPane.name}'
		         </#if>
		         
			    <#if (model.targetPaneModel)?exists>	         
		         	,'target':'${model.targetPaneModel.name}'
		        <#else>
		        	,'target':'${model.gridModel.containerPane.name}'	 	
				</#if>
		         <#if (model.echoJs)?exists>	         
		         ,'echoJs':'${model.echoJs}'
		         </#if>
		         });
  }
);
</script>