<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>    
<jsp:useBean id="form" class="com.test.join.FormProc"/>
{"saved":<%=form.saveForm(request) %>}

