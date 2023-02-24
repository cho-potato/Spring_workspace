<%@page import="com.edu.mvc3.domain.Board"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%
	List boardList = (List)request.getAttribute("boardList");
	out.print("boardList : 게시물 수는, "+boardList.size());
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
<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#bt_regist").click(function() {
		// location.href="/board/lit.jsp"; 는 보안된 디렉토리가 아니었을 때 가능 
		location.href="/board/registform"; // 등록 폼 보여주기
	});
});

</script>

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col m-5">
				<table class="table">
					<thead class="thead-dark">
						<tr>
							<th>NO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th>HIT</th>
						</tr>
					</thead>
					<tbody>
					<%for(int i=0; i<boardList.size(); i++) { %>
					<%Board board = (Board)boardList.get(i); %>
						<tr>
							<td><%=i %></td>
							<td><a href="/board/detail?board_idx=<%=board.getBoard_idx()%>"><%=board.getTitle()%></a></td>
							<td><%=board.getWriter()%></td>
							<td><%=board.getRegdate()%></td>
							<td><%=board.getHit()%></td>
						</tr>
						<% }%>
						<tr>
							<td colspan="5">
								<button type="button" class="btn btn-success" id="bt_regist">Success</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>