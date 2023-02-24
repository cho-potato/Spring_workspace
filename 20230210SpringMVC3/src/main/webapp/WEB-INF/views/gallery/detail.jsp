<%@page import="com.edu.mvc3.domain.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	Board board=(Board)request.getAttribute("board");
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
	$("#bt_edit").click(function(){
		if(confirm("수정하시겠습니까?")){
			$("#form1").attr({
				action:"/board/edit",
				method:"post"
			});
			$("#form1").submit();
		}
	});
	$("#bt_del").click(function(){
		if(confirm("삭제하시겠습니까?")){	
			$("#form1").attr({
				action:"/board/delete",
				method:"post"
			});
			$("#form1").submit();
		}
	});
	$("#bt_list").click(function(){
		location.href="/board/list";
	});
})
</script>
</head>
<body>
<div class="container">
  <form id="form1">
  	<input type=hidden class="form-control" value="<%=board.getBoard_idx()%>" name="board_idx">
  		
    <div class="form-group">
      <input type="text" class="form-control" value="<%=board.getTitle()%>" name="title">
    </div>
    <div class="form-group">
      <input type="text" class="form-control" value="<%=board.getWriter()%>" name="writer">
    </div>
    <div class="form-group">
      <textarea type="text" class="form-control" name="content"><%=board.getContent()%></textarea>
    </div>
  </form>
    <div class="form-group form-check">
	    <button type="submit" class="btn btn-primary" id="bt_edit">수정</button>
	    <button type="submit" class="btn btn-primary" id="bt_del">삭제</button>
	    <button type="submit" class="btn btn-primary" id="bt_list">목록</button>
    </div>
</div>

</body>
</html>