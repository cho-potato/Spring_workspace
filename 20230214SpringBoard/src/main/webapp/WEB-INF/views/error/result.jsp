<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body style="background : RED">
이ㅇ용 불편 ㅈㅅ
<p>
<%
	RuntimeException e= (RuntimeException)request.getAttribute("e");
	out.print(e.getMessage());
%>
<p>
<a href="/reboard/list"> 메인으로 </a>
</body>
</html>