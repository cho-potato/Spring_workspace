<%@page import="com.edu.db.domain.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	List boardList = (List)request.getAttribute("boardList");
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
		location.href="/board/registform"; 
		// 보안된 디렉토리에 접근이 불가능해서 InternalResourceViewResolver를 이용할 수 있는 controller를 생성하여 연결
		// /board/registform->RegistFormController로 연결
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
			<%for(int i = 0; i<boardList.size(); i++) { %>
			<%Board board =(Board)boardList.get(i); %>
	      <tr>
	        <td><%=i%></td>
	        <td><a href="/board/detail?board_idx=<%=board.getBoard_idx()%>"><%=board.getTitle()%></a></td>
	 		<td><%=board.getWriter()%></td>
	        <td><%=board.getRegdate()%></td>
	        <td><%=board.getHit()%></td>
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