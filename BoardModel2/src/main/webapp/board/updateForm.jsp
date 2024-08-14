<%@page import="com.test.model1board.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script type="text/javascript">
function updateit(){
	   
   if('${uid}' === '${board.author}')
   {   	
	   let title = $('#title').val();  
   	   let contents = $('#contents').val();
	   
      let obj = {}
  	 
  	  obj.bnum = ${board.bnum};
  	  obj.title = title;
  	  obj.contents = contents;
  	$.ajax({
  		url:'../boardUpdate',
  		method:'post',
  		cache:false,
  		data:obj,
  		dataType:'json',
  		success:function(res){
  			alert(res.updated ? '수정완료' : '수정 실패');
  			if(res.updated){
  				location.href="../boardDetail/${board.bnum}";
  			}
  		},
  		error:function(xhr, status, err){  // Corrected typo here
              alert('에러:' + err);
          }
  	});
   }else alert("not qualified");
}
    

</script>
</head>
<body>
<form id="updateForm">
<label for="bnum">번호</label>${board.bnum}
<input type="hidden" id="bnum" name="bnum" value="${board.bnum}"> 
<label for="title">수정할 제목</label>
<input type="text" name="title" id="title" value="${board.title}">
<label for="contents">수정할 내용</label>
<textarea name="contents" id="contents" rows="5" cols="20" placeholder="${board.contents}"></textarea>
<div>
	    <button type="reset">취소</button>
	    <button type="button" onclick="updateit()">저장</button>
	</div>
</form>
</body>
</html>