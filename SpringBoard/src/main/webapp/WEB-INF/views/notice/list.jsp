
<%@page import="com.edu.springboard.domain.Notice"%>
<%@page import="com.edu.springboard.domain.ReBoard"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<script src="https://cdn.jsdelivr.net/npm/vue@2/dist/vue.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script type="text/javascript">
let app1;
const row= {
		template: `
		<tr>
			<td>1</td>
			<td><a href="#" @Click="getDetail(obj.notice_idx)">{{obj.title}}</a></td>
			<td>{{obj.writer}}</td>
			<td>{{obj.regdate}}</td>
			<td>{{obj.hit}}</td>
		</tr>
		`,
		props:['notice'],
		data(){
			return {
				obj:this.notice
			};
		},
		methods:{
			getDetail:function(notice_idx) {
				alert(notice_idx);
				location.href="/notice/detail?notice_idx="+notice_idx;
			}
		}
};

function init () {
	app1=new Vue({
		el:"#app1",
		components:{
			row
		},
		data:{
			noticeList:[]
		}
	});
}

// 목록 가져오기
function getList() {
	$.ajax({
		url : "/rest/notices",
		type : "GET",
		success : function (result, status, xhr) {
			console.log("서버에서 전송된 결과 : ", result);
			app1.noticeList=result;
		}
	})
}

$(function(){
	init();
	getList();
	
	$("#bt_regist").click(function(){
		location.href="/notice/registform"; 
	});
});


</script>
</head>
<body>
	<div class="container" id="app1">
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
	    	<template v-for="notice in noticeList">
	    		<row :key="notice.notice_idx" :notice="notice"/>		
	    	</template>
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