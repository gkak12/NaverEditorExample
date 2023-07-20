$(document).ready(function(e){
	initNaverEditor();
	initEvtHandler();
});

var oEditors = new Array();

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
		}
	});
};

var initEvtHandler = function(){
	$("#registBtn").off().on("click", function(e){
		var editorRes = oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		
		if(editorRes == true){
			insertEditor();
		} else{
		    console.log("FAIL");
		    return;
		}
	});
	
	$("#cancelBtn").off().on("click", function(e){
		location.href= "/";
	});
};

var insertEditor = function(){
	var formData = new FormData();
    formData.append("content", $("#content").val());

    $.ajax({
		type : "POST", 
		url : "/insertEditor.json",
		datatype : "json",
		data : formData,
		enctype : "multipart/form-data",
		processData : false,
		contentType : false,
		success : function(data){
			if(data.MSG == "SUCCESS"){
				console.log("SUCCESS");
				alert("등록 되었습니다.");
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