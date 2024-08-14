<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Model1 게시판</title>
</head>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script type="text/javascript">
   function Logout() {
      $.ajax({
         url:'logout',
         method:'post',
         cache:false,
         data:{'b':'logout'},
         dataType:'json',
         success:function(res) {
            alert(res.logout ? '로그아웃 성공':'로그아웃 실패');                        
         },
         error:function(xhr,status,err){
            alert('에러:' + err);
         }
      });
      
   }
</script>

<body>
<main>
<h3>JSP개발방법론 Model 1을 사용한 게시판 프로젝트</h3></main>
<ul>
	<li><a href="loginForm">로그인</a>
	<li><a href="javascript:Logout();">로그아웃</a>
	<li><a href="boardAddForm">게시글 입력</a>
	<li><a href="boardList">게시글 목록</a>	
	<li><a href="joinForm">멤버 추가</a>
</ul>

</body>
</html>