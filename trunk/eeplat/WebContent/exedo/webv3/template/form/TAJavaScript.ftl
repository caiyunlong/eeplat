<a  id='${model.objUid}${model.data.uid}' style="${model.style?if_exists}"    href='#' value="${model.l10n}">${model.l10n}</a>
<script>
 
$('#${model.objUid}${model.data.uid}').bind('click',function(){
	   var dealBus = 'dataBus=setContext&contextKey=${model.data.bo.name}&contextValue=${model.data.uid}&invokeButtonUid=${model.objUid}&contextNIUid=${(model.data.map.contextniuid)?if_exists}&contextPIUid=${model.data.map.contextpiuid?if_exists}';
	   callPlatBus({'paras':dealBus});
	  <#if (model.doClickJs?exists)>	  
      	eval("${model.doClickJs}");
      </#if>
  }
);
</script>