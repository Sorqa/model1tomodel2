<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 입력</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script type="text/javascript">
function add(){
	if(!confirm('작성된 글을 저장할까요?')) return;
	
	var ser = $('#addForm').serialize();
    $.ajax({
        url:'add',
        method:'post',
        cache:false,
        data:ser,
        dataType:'json',  
        success:function(res){  
            alert(res.saved ? '저장되었습니다' : '저장 실패');      
            if(res.saved){
            	location.href = "boardDetail/"+res.bnum;
            }
        },
        error:function(xhr, status, err){  // Corrected typo here
            alert('에러:' + err);
        }
    });
}
</script>
</head>
<body>
<h3>게시글 입력</h3>
<form id="addForm" >
 

<!-- 제목,내용,AJAX 요청 -->
	<div>
		<label for="title">제목</label>
	    <input type="text" name="title" id="title">
	</div>
	
	<div>
		<label for="contents">내용</label>
	    <textarea name="contents" id="contents" rows="5" cols="20" placeholder="여기에 작성"></textarea>
	</div>
	<div>
	    <button type="reset">취소</button>
	    <button type="button" onclick="add()">추가</button>
	</div>
</form>
</body>
</html>