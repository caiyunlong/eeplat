/*****************************************主页面框架代码******************************************/
var position = "first"; //tab页显示顺序，first是显示在前面，last是显示在后面
var isHome = 1; //是否有首页   有是1   没有是0
var globalURL = "/eeplat/";
var globalService = globalURL + 'servicecontroller';
var globalPml= globalURL + 'mvccontroller';


//得到浏览器可用高度，赋给菜单  以及右边区域总div
function resscrEvt(height,width){
	if(height==undefined||width==undefined){
		height = $(window).height();
		width = $(window).width();
	}
	var mRightOffSet = $(".mRight:visible").offset();


///左边索引菜单
	$(".gFpage:eq(0)").css("height",height-mRightOffSet.top );
////右边主要显示区域
	$(".mRight:visible").css("height",height-mRightOffSet.top);
	$(".mRight:visible").css("width",width-mRightOffSet.left);
///树	 
	$(".tree").css("height",height-mRightOffSet.top);
//tab-pane
    $(".ui-tabs-panel").css("height",height-mRightOffSet.top-25);  
    $(".mRight:visible .ui-tabs-panel").css("width",width-mRightOffSet.left-$(".mRight:visible .lrschidren").width());   
  
    $(".mRight:visible").css("overflow","hidden");
    

}
//让菜单能伸展   如果这个方法放到类里执行 就会非常慢  所以没有放到类里，在这里判断如果有outlook菜单 则执行
$(function(){
	if($(".mHi").length>0){
		//所有菜单ul标记隐藏
		$(".mHi:gt(0)").hide(); 
		
		$(".mTitle").bind("click",function(){
	  		$(".mHi").hide(); 
			$(".mTitle-hover").removeClass("mTitle-hover");
			$(this).addClass("mTitle-hover");
			$(this).next('.mHi').fadeIn("normal");
	 	})
	}
});

//鼠标在菜单上时   更换背景
function bindMenuHoverCss(){
  $(".mMenu").bind("mouseover",function(){
		$(this).addClass("mMenu-hover");
  }).bind("mouseout",function(){
		$(this).removeClass("mMenu-hover");
  })
};

//点击菜单
function bindClickMenu() {
 $(".mMenu").bind("click",function(event){
 		//设置center总区域有滚动条
		//$(".mRight:eq(0)").css("overflow-y","auto");
		//$(".mRight:eq(0)").css("overflow-x","auto");
		
		$(".mMenu").removeClass("mMenu-hover2");
		$(".mMenu").removeClass("mMenu-hover");
		$(this).addClass("mMenu-hover2");
		
		
		//菜单id和tab  id有关联的
		var menuId = $(this).attr("id");
		//菜单title 等于 tab的 title
		var menuName = $(this).attr("name");
		//属性选择器   选择table 属性 tabId  值为 menuId的
		var paneId = $(this).attr("paneid");
		if(paneId==null || paneId.indexOf('mvccontroller')!=-1){
			createTab(menuId,menuName,paneId,'isMenu');
		}else{
			window.open(paneId);
		}	
		event.stopPropagation();

  })
};



function createNewTab(menuId,menuName,paneUrl,isMenu){
	
	var tabSelector = "#dvTab table[tabId='"+menuId+"']";
	if($(tabSelector).length==1){
		$(tabSelector).remove();
		$('#tab_' + menuId).remove();
	}
	createTab(menuId,menuName,paneUrl,isMenu);
}

function createTab(menuId,menuName,paneUrl,isMenu){
	
	var tabSelector = "#dvTab table[tabId='"+menuId+"']";
	
	if($(tabSelector).length==1){
		//如果这个tab已经存在，则设置成选中的css
		selectTabCss(tabSelector);
		return;
	}
 
	//如果tab页到7个  则关闭最后一个
	////关闭问题也挺复杂
	if($(".gTab table").length ==6){
		if(position=="first"){
			var tabId = $(".gTab table:last").attr("tabId");
			$(".gTab table:last").remove();
			$('#tab_' + tabId).remove();

		}else if(position=="last"){
			var tabId = $(".gTab table:eq("+isHome+")").attr("tabId");
			$(".gTab table:eq("+isHome+")").remove();
			$('#tab_' + tabId).remove();
			//$("#dvTab table:eq("+isHome+")").remove();

		}	
	}

	if(isMenu==null){
		isMenu = "";
	}

	//添加tab页
	if($("#dvTab table").length>0){
		$("#dvTab table:" + position).after("<TABLE class=\"\" tabId=\""
				+menuId+"\" paneId = \""
				+paneUrl+"\" isMenu=\""
				+isMenu+"\" title=\""
				+menuName+"\" style=\"WIDTH: 130px; ZOOM: 1\"><TBODY><TR><TD class=tLe><TD class=bdy><NOBR>"+menuName+"</NOBR></TD><TD class=btn><A class=TabCls>&nbsp;&nbsp;&nbsp;</A></TD><TD class=tRi></TD></TR></TBODY></TABLE>");
	}else {
		$("#dvTab").append("<TABLE class=\"\" tabId=\""
				+menuId+"\" paneId = \""
				+paneUrl+"\" isMenu=\""
				+isMenu+"\" title=\""
				+menuName+"\" style=\"WIDTH: 130px; ZOOM: 1\"><TBODY><TR><TD class=tLe><TD class=bdy><NOBR>"+menuName+"</NOBR></TD><TD class=btn><A class=TabCls>&nbsp;&nbsp;&nbsp;</A></TD><TD class=tRi></TD></TR></TBODY></TABLE>");
	}
	//设置新添加的tab页为选中的css
	var tabBtnSelector = tabSelector+" .btn";
	selectTabCss(tabSelector);
	//重新绑定事件
	bindTabClickCss(tabSelector);
	bindTabCloseCss(tabBtnSelector);
	bindTabCloseWindow(tabBtnSelector);
	
	
}


//右侧tab页事件   鼠标点击时更换css
function bindTabClickCss(tabSelector){
  if(tabSelector==undefined){
  		  $(".gTab table").bind("click",function(){
				tabClickCss(this);
		  })
  }else{
  		$(tabSelector).bind("click",function(){
				tabClickCss(tabSelector);
		})
  }
}
//鼠标点击tab处理  menuid attr("tabId")一一对应
function tabClickCss(tabSelector){
	selectTabCss(tabSelector);
	$(".mMenu").removeClass("mMenu-hover2");
	$(".mMenu").removeClass("mMenu-hover");
	var menuId = "#"+$(tabSelector).attr("tabId");
	///只有和菜单有对应关系的tab才更新css
	var isMenu = "#"+$(tabSelector).attr("isMenu");
	if(isMenu!=null &&  isMenu=='isMenu'){
		$(menuId).addClass("mMenu-hover2");
	}	
}
function selectTabCss(tabSelector){
	$(".gTab table").removeClass("on");
	$(tabSelector).addClass("on");
	
	//加载内容
	var paneUrl = $(tabSelector).attr("paneId");
//	showMainMsg("#mRight",32,32,"center","loadingImg","","n");
	var tabId = $(tabSelector).attr("tabId");
	$(".mRight").hide();
	if($('#tab_' + tabId).size()>0){
		$('#tab_' + tabId).show();
		resscrEvt();
	}else{
	  	$("#mRight").clone().attr("id",'tab_' + tabId).insertAfter("#mRight");
		
		$('#tab_' + tabId).load(paneUrl,function(){
			resscrEvt();
		});
		$('#tab_' + tabId).show();
	}
	//closeWin();
}
//控制tab也上的差号显示
function bindTabCloseCss(tabBtnSelector){
  if(tabBtnSelector==undefined){
  		  $(".btn").bind("mouseover",function(){
				$(this).children("a").removeClass("TabCls");
		  }).bind("mouseout",function(){
				$(this).children("a").addClass("TabCls");
		  })
  }else{
  		  $(tabBtnSelector).bind("mouseover",function(){
				$(this).children("a").removeClass("TabCls");
		  }).bind("mouseout",function(){
				$(this).children("a").addClass("TabCls");
		  })
  }
}
//给差号绑定关闭事件
function bindTabCloseWindow(tabBtnSelector){
  if(tabBtnSelector==undefined){
	  $(".btn").bind("click",function(){
			tabCloseWindow(this);
	  })
  }else{
	  $(tabBtnSelector).bind("click",function(){
			tabCloseWindow(tabBtnSelector);
	  })
  }
}
//关闭tab
function tabCloseWindow(tabBtnSelector){
	
	var tabId = $(tabBtnSelector).parents("table").attr("tabId");
	$(tabBtnSelector).parents("table").remove();
	$('#tab_' + tabId).remove();
	
	if($(".on").length==0){
				//如果没有被选中的tab页，则选中最后一个
				$(".mMenu").removeClass("mMenu-hover");
				//如果只有一个tab页   选中首页
				if($(".gTab table:eq(1)").length>0){
					selectTabCss(".gTab table:eq(1)");
				}else{
					selectTabCss(".gTab table:eq(0)");
				}
				//菜单跟tab同步    最后一个tab选中后  对应的菜单也要选中
				var menuId = "#"+$(".gTab table:eq(1)").attr("tabId");
				var isMenu = "#"+$(".gTab table:eq(1)").attr("isMenu");
				if(isMenu!=null &&  isMenu=='isMenu'){
					$(menuId).addClass("mMenu-hover");
				}	
			}
}

var workbenchPath = "";
function loadWorkbench(path){
	
	if(path!=null){
		workbenchPath = path;
	}

	if($("#tab_workbench_container").size()>0){
		$(".mRight").hide();
		$("#tab_workbench_container").show();
	}else{
	  	$("#mRight").clone().attr("id",'tab_workbench_container').insertAfter("#mRight");
		$("#tab_workbench_container").load(globalURL + workbenchPath);
		$("#mRight").hide();
		$("#tab_workbench_container").show();

//		$("#mRight").load(globalURL + "exedo/webv3/workbench/workbench.jsp");
	}	
}


function closeWin(){
//	$("#fullBg").css("display","none");
//	$(".alertClose").parent("#msg").css("display","none");
	$("#main_msg").remove();
	$("#fullBg").remove();
}
/*****************************************遮罩层***************************************************/
/*
position:遮罩层显示在哪个区域，例如显示在aa  div里面：#aa 就可以
msgW:弹出框的宽
msgH:弹出框的高
align:显示位置  left,right,center  目前有三个值
type:类型，loading和loadingImg,登录现在用的是loading,loadingImg是一个图片，代表加载内容.如果自定义则给空值.
content:弹出框里面得内容，自定义
isClose:是否有关闭按钮
*/
function showMainMsg(position,msgW,msgH,align,type,content,isClose){
	$("body").prepend("<DIV id=fullBg></DIV><DIV id=main_msg></DIV>");
	
	if(type=="loading"){
		content="<div>&nbsp;&nbsp;请稍后......</div>";
	}else if(type=="loadingImg"){
		content= "<div class=index-loading></div>";
	}
	
	
	if(align=="left"){
		$("#main_msg").css({top:$(position).offset().top,left:$(position).offset().left,width:msgW,height:msgH});
	}else if(align=="center"){
		$("#main_msg").css({top:$(position).offset().top+($(position).height()-msgH)/2,left:$(position).offset().left+($(position).width()-msgH)/2,width:msgW,height:msgH});
	}else if(align=="right"){
		$("#main_msg").css({top:$(position).offset().top,left:$(position).offset().left+$(position).width()-msgW-5,width:msgW,height:msgH});
	}
	
	
	$("#fullBg").css({top:$(position).offset().top,left:$(position).offset().left,width:$(position).width(),height:$(position).height()});
	//显示关闭按钮
	if(isClose=="y"||isClose=="Y"){
		var closeImg="<div class='alertClose'></div>";
		$("#main_msg").append(closeImg);
	}
	
	$("#main_msg").append(content);
	
	//关闭按钮事件
	if(isClose=="y"||isClose=="Y"){
		$(".alertClose").bind("click",function(){
			closeWin();
		})
	}
}

/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
/*****************************编码处理***************************************************************/
//UrlCode 处理代码
function urlCodeDeal(str){
	if(str.length==0||null==str){
		return "";
	}
	var paras = new Array();
	paras = str.split('&');
	var result ="";
	for(var i = 0; i < paras.length; i++){
	    var name_V =   new Array();
	    name_V = paras[i].split('=');
	    if(i==0){
	    	result += name_V[0]+"=";
	    }else{
	    	result +="&" + name_V[0]+"=";
	    }
	    if(name_V.length>1){
	    	//之前空格被用+替换了, 参数中加号用空格替换回来 
	    	result += encodeURIComponent(escape(decodeURIComponent(name_V[1].split("+").join("%20"))));  
	    }
	}
	return result;
}


/*****************************************弹出层代码******************************************/
function popupDialog(id,title,href,width,height){


		createFloatDiv(id,title);
		var t = $('#jqmC' + id);
		$('#F' + id).jqm({
			ajax: href,
			target: t,
			modal: true, 
			onHide: function(h) { 
						t.html('Please Wait...');  // Clear Content HTML on Hide.
						h.o.remove(); // remove overlay
						h.w.fadeOut(0); // hide window
					    if($("#F" + id).length > 0){
						       $("#F" + id).remove();
						}
					},
			overlay: 0}).jqDrag('.jqmdTC').jqResize('.jqHandle'); 
		
	     if(width!=null && width>0){
	    	 $('#F' + id).width(parseInt(width));
	     }
	     if(height!=null && height>0){
	    	 $('#F' + id).height(parseInt(height));
	     }
		
	     $('#F' + id).jqmShow();


	     
		 if($(".jqmDialog").length > 1){
	         $('#F' + id).css("top",$(".jqmDialog").offset().top + 20 );
			 $('#F' + id).css("left",$(".jqmDialog").offset().left + 20 +$(".jqmDialog").width()/2);
		 }
}
function createFloatDiv(id,title) {
	     var htmlStr = "<div id='F" + id  + "' class='jqmDialog'> \n"
 		+" <div class='jqmdTL'><div class='jqmdTR'><div class='jqmdTC'>" + title + "</div></div></div> \n"
 		+" <input type='image' src='" +globalURL +"exedo/webv3/js/jquery-plugin/dialog/close.gif' class='jqmdX jqmClose' /> \n"
		+" <div class='jqmContent' id='jqmC" + id + "'> \n"
		+" <p>Please wait... <img src='" +globalURL +"exedo/webv3/js/jquery-plugin/dialog/busy.gif' alt='loading' /></p>  \n </div> \n"
		+"	<div class='jqHandle'></div>  \n  </div> \n";
		$(document.body).append(htmlStr); 
};


/**
 * 刷新树 
 * @param type =1 刷新 选中的节点，否则刷新 选中节点的上层
 * @return
 */
function reloadTree(type){
	  try{
		if(webFXTreeHandler==null || webFXTreeHandler.selected==null){
			return;
		}
		oldSelectName = webFXTreeHandler.selected.text;
	    if(type == 1){  
	    	webFXTreeHandler.selected.reload();
	    }else{
	    	if(webFXTreeHandler.selected.parentNode.src!=null && webFXTreeHandler.selected.parentNode.src!=''){
	    		webFXTreeHandler.selected.parentNode.reload();
	    	}else{
	    		if(webFXTreeHandler.selected.parentNode.parentNode.src!=null
	    				&& webFXTreeHandler.selected.parentNode.parentNode.src!=''){
	    			webFXTreeHandler.selected.parentNode.parentNode.reload();
	    		}	
	    	}	
	    }
	  }catch(e){
	  
	  }
}
