<%@page import="java.util.Date"%>
<%@page import="com.test.model1board.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<style type="text/css">
main { width:fit-content; margin:0.5em auto; }
main>h3 { width:fit-content; margin:0.2em auto; border-bottom:3px double black;}
table { border-spacing: 0; border-collapse: collapse; border:1px solid black;}
th {background-color:#eef; text-align:right; border-right:3px double black;}
th,td { padding:0.2em 0.5em; border-bottom:1px dashed black; }
tr:last-child>td { width:20em; height:5em; overflow: auto;}
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script type="text/javascript">
function deleteit(){
	 if('${uid}' === '${board.author}'){
	let obj = {}
		
	$.ajax({
		url:'../delete/${board.bnum}',
		method:'post',
		cache:false,
		data:obj,
		dataType:'json',
		success:function(res){
			alert(res.deleted ? '삭제완료' : '삭제 불가능');
		},
		error:function(xhr, status, err){  // Corrected typo here
            alert('에러:' + err);
        }
	});}else alert("같은 이용자가 아닙니다");	
}

</script>
</head>
<body>
<main>
   <h3>게시글 상세보기</h3>
   <table>
      <tr><th>번호</th><td>${board.bnum}</td></tr>
      <tr><th>제목</th><td>${board.title}</td></tr>
      <tr><th>작성자</th><td>${board.author}</td></tr>
    
      <tr><th>작성일</th><td><fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss"/></td></tr>
      <tr><th>조회수</th><td>${board.hit}</td></tr>
      <tr><th>내용</th><td>${board.contents}</td></tr>
   </table>
   
   <button type="button" onclick="deleteit()">삭제</button>
   <a href="../updateForm/${board.bnum}">수정</a>
   
</main>
</body>
</html>