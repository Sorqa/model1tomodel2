<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Join Up Form</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" 
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" 
            crossorigin="anonymous"></script>
    <script type="text/javascript">
    
    $(function(){	//jquery ready 이벤트 핸들러 함수
    	alert('DOM 오브젝트 준비됨');
    	$('#pwd').blur(function(){        	
    		checkPwd();
    	});
    });
    
    function add(){
    	console.log('계정 추가');
    	var ser = $('#joinForm').serialize();
        $.ajax({
            url:'join',
            method:'post',
            cache:false,
            data:ser,
            dataType:'json',  
            success:function(res){  
                alert(res.saved ? '저장되었습니다' : '저장 실패');
                
            },
            error:function(xhr, status, err){  // Corrected typo here
                alert('에러:' + err);
            }
        });
    }
    
    
    
    
    
    function checkDuplicate(){    	
        //alert('아이디 중복검사 시작');
        console.log('아이디 중복검사 시작');
        var ser = $('#joinForm').serialize();
        $.ajax({
            url:'checkDuplicate.jsp',
            method:'post',
            cache:false,
            data:ser,
            dataType:'json',  // Corrected typo here
            success:function(res){  // Corrected typo here
                alert(res.ok ? '유효한 아이디' : '사용할 수 없는 아이디');
                if(!res.ok) $('#uid').val('');
            },
            error:function(xhr, status, err){  // Corrected typo here
                alert('에러:' + err);
            }
        });
    }
    
    function checkPwd(){
    	var pwd = $('#pwd').val();
    	var len = pwd.length;
    	console.log('총 길이:' + len);
    	//alert(pwd);
    	var specialCharCnt = (pwd.match(/[^a-zA-Z0-9]/g) || []).length;
    	console.log('특수문자의 수:' + specialCharCnt);
    	var lowChaCnt = (pwd.match(/[a-z]/g) || []).length;
    	console.log('소문자의 수:'+lowChaCnt);
    	
    	var upperChaCnt =(pwd.match(/[A-Z]/g) || []).length;
    	console.log('대문자의 수:' + upperChaCnt);    	
    	
    	var upperChaCnt =(pwd.match(/[A-Z]/g) || []).length;
    	alert('대문자의 수:' + upperChaCnt);
    	
    	if(len>=8 && specialCharCnt>=2 && lowChaCnt>=2 && upperChaCnt){
    		console.log('유효한 암호');
    	}else{
    		alert('무효한 암호');
    	}
    	/*   	    	
    	var specialCharCnt = pwd.match(/[^a-zA-Z0-9]/g).length;
    	alert('특수문자의 수:' + specialCharCnt);*/
    	
    }
    
    function onHistoryInput(){
    	var hist = $('#history').val();
    	console.log('취미경력:' + hist);
    	$('#histOut').val(hist);
    }
    </script>
</head>
<body>
<main>
<h3>회원가입</h3>
<form id="joinForm" >

    <div>
        <label for="uid">아이디</label>
        <input type="text" name="uid" id="uid" value="smith">
        <button type="button" onClick="checkDuplicate();">중복검사</button>
    </div>
    <div>
        <label for="pwd">암 호</label>
        <input type="password" name="pwd" id="pwd" value="smith">
        <button type="button" onClick="checkPwd();">유효성 검사</button>
    </div>
    <div>    	
    	<label for="gender">성별</label>
    	남<input type="radio" name="gender" value="m"> 
    	여<input type="radio" name="gender" value="f"> 
    </div>
    <div>
    	<label for="hobby">취미</label>
    	게임<input type="checkbox" name="hobby" value="game"> 
    	독서<input type="checkbox" name="hobby" value="reading"> 
    	여행<input type="checkbox" name="hobby" value="travel"> 
    	낚시<input type="checkbox" name="hobby" value="fishing"> 
    	그림<input type="checkbox" name="hobby" value="drawing">    	
    </div>
    <div>
    	<label for="history">취미 경력</label>
    	<input type="range" name="history" id="history" min="0" max="100" oninput="onHistoryInput();">
    	<output id="histOut"></output>
    </div>
    <div>
    	<label for="age">나이</label>
    	<input type="number" name="age" id="age" value="20">
    </div>
    <div>
    	<label for="birth">생년월일</label>
    	<input type="date" name="birth" id="birth">
    </div>
    <div>
    	<label for="intro">간단한 개인소개</label>
    	<textarea rows="5" cols="10" placeholder="여기에 입력..." name="intro" id="intro"></textarea>
    </div>
    <div>
    	<button type="reset">취소</button>
    	<button type="button" onClick="add();">저장</button>
    </div>
</form>
</main>
</body>
</html>
