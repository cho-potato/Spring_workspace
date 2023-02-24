
<%@page import="com.edu.springboard.domain.ReBoard"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	List reboardList = (List)request.getAttribute("reboardList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script type="text/javascript">

$(function(){
	$("#bt_regist").click(function(){
		// alert("sfsd");
		location.href="/reboard/registform"; 
		// 보안된 디렉토리에 접근이 불가능해서 InternalResourceViewResolver를 이용할 수 있는 controller를 생성하여 연결
		// /reboard/registform->RegistFormController로 연결
	});
});
</script>
</head>
<body>
	<div class="container">
	  <table class="table">
	    <thead class="thead-dark">
	      <tr>
	        <th>No</th>
	        <th>Title</th>
	        <th>Writer</th>
	        <th>Date</th>
	        <th>Hit</th>
	      </tr>
	    </thead>
	    <tbody>
			<%for(int i = 0; i<reboardList.size(); i++) { %>
			<%ReBoard reboard =(ReBoard)reboardList.get(i); %>
	      <tr>
	        <td><%=i%></td>
	        <td><a href="/reboard/detail?reboard_idx=<%=reboard.getReboard_idx()%>"><%=reboard.getTitle()%></a></td>
	 		<td><%=reboard.getWriter()%></td>
	        <td><%=reboard.getRegdate()%></td>
	        <td><%=reboard.getHit()%></td>
	      </tr>
		<%} %>
	      <tr>
	        <td colspan="5">
	        	<button type="button" class="btn btn-info" id="bt_regist">글등록</button>
	        </td>
	      </tr>
	    </tbody>
	  </table>
  </div>
</body>
</html>