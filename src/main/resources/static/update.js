$(document).ready(function(e){
	initContent();
	initNaverEditor();
	initEvtHandler();
//	reloadContent();
});

var oEditors = new Array();
var contentHtml = null;

var initContent = function(){
	var content = localStorage.getItem("content");
	
	if(content === undefined || content === null){
		contentHtml = "";
		return;
	}
	
	var parser = new DOMParser();
	contentHtml = parser.parseFromString(content, "text/html").body.textContent;
};

var initNaverEditor = function(){
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "content",
		sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
		fCreator: "createSEditor2",
		htParams: {
			bUseToolbar: true,
			bUseVerticalResizer: true,
			bUseModeChanger: true
		},
		fOnAppLoad : function(){
			oEditors.getById["content"].exec("PASTE_HTML", [contentHtml]);
		}
	});
};

var reloadContent = function(){
	oEditors.getById["content"].exec("SET_IR", [""]);
	oEditors.getById["content"].exec("PASTE_HTML", [contentHtml]);
};

var initEvtHandler = function(){
	$("#updateBtn").off().on("click", function(e){
		var editorRes = oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		
		if(editorRes == true){
			updateEditor();
		} else{
		    console.log("FAIL");
		    return;
		}
	});
	
	$("#cancelBtn").off().on("click", function(e){
		location.href= "/";
	});
};

var updateEditor = function(){
	var formData = new FormData();
    formData.append("content", $("#content").val());

    $.ajax({
		type : "POST", 
		url : "/updateEditor.json",
		datatype : "json",
		data : formData,
		enctype : "multipart/form-data",
		processData : false,
		contentType : false,
		success : function(data){
			if(data.MSG == "SUCCESS"){
				console.log("SUCCESS");
				alert("수정 되었습니다.");
				localStorage.setItem("content", data.DATA);
				location.href= "/";
			} else {
				console.log("FAIL");
				console.log(data.LOG);
				alert("데이터셋 활용 등록 실패했습니다.");
			}
		},
		error : function(jqXHR, textStatus, errorThrown){
			console.log("FAIL");
		}		
	});
};