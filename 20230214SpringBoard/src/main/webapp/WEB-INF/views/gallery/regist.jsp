<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글 등록</title>
<style type="text/css">
.boxStyle{
	width : 75px;
	height : 85px;
	border : 2px solid #ccc;
	display : inline-block; /* inline : 다른 요소와 공존, block : 크기설정 가능 */
	margin : 5px;
}
.boxStyle img{
	width : 65px;
	height : 65px;
}
.boxStyle div{
	text-align:right;
	margin-right:5px;
	font-weight:bold;
}
</style>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<!-- vue -->
<script src="https://cdn.jsdelivr.net/npm/vue@2/dist/vue.js"></script>

<script type="text/javascript">
let app1;
let key=0; // 유저가 프로그램을 사용하는 동안 그 값을 계속 증가시킴, 유일성 확보


// 사용자 정의 UI 컴포넌트 등록하기
// 등록한 이후엔 마치 태그처럼 사용 가능
const imagebox={
	template: `
		<div class="boxStyle">
			<div @click="delImg(p_idx)"><a href="#">X</a></div>
			<img :src="p_src">	
		</div>
	`,
	/*이 컴포넌트를 태그로 호출하는 자가 넘긴 속성을 받으려면 props로 받아야 한다 */
	props: ['src', 'idx']
	, data() {
		return {
			// props의 용도 : 외부에서 전달된 속성 값을 보관하기 위한 변수
			// template에서 쓸 변수명 : 해당속성(property)
			// p_src의 용도 : 내부 템플릿에서 접근하기 위한 변수
			p_src:this.src,
			p_idx:this.idx
		};
	},
	methods:{
		delImg:function(idx) {
			alert("delImg : 삭제할 이미지 고유 idx" + idx);
			// imageList안의 모든 JSON이 보유한 idx 값을 비교하여, 일치하면 배열에서 제거
			for(let i= 0; i<app1.imageList.length; i++) {
				let json=app1.imageList[i] // 끄집어내기
				
				if(json.key==idx) {
					// app1.imageList.splice(요소번째, 지울 갯수);
					app1.imageList.splice(i, 1);
				}
			}
		}
	}
};
function init() {
	app1 = new Vue({
		el : "#app1",
		components: {
			imagebox
		},		
		data:{
			count:3,
			/*src:[]*/
			imageList:[] /*유저가 선택한 파일에 대한 정보, src, name,,,*/
		}
	});
}
// 사용자가 선택한 이미지가 이미 app1.imageList에 들어있는지 여부 판단하기
function checkDuplicate(filename) {
	let count=0;
	for(let i=0; i<app1.imageList.length; i++) {
		let json = app1.imageList[i];
		if (json.name==filename) { // 동일한 이름이 발견된다면
			count++;
			break;
		}
	}
	return count;
}

// 이미지 미리보기 구현하기
function preview(files) {
	console.log("files는 ", files);
	
	// 배열 안에 들어있는 파일 정보를 하나씩 읽어들여 화면에 출력
	for(let i=0; i<files.length; i++) {
		// 배열에 들어있는 파일 하나씩 꺼내기
		let file = files[i]; // 대상파일에 넣어주려고 꺼냄
		
		// app1.imageList 배열에 존재하지 않을 경우만 // 아무거나 만든 함수 checkDuplicate
		if(checkDuplicate(file.name) < 1) { // 1보다 작으면 발견된게 없으니까 추가
			let reader = new FileReader(); // 파일 입력스트림 생성
			// reader.onload=function(e) {
			reader.onload=(e)=>{
				// console.log("이미지 읽기 완료", e);
				console.log("file정보는 ", file);
				console.log("정보는 ", e.target.result); // 바이너리 정보 
				// app1.src[0]=e.target.result // app1꺼의 src0번째 배열의 이미지
				// app1.src.push(e.target.result);
				// console.log("app1.src[0]", app1.src[0]);
				
				// 이미지 정보를 낱개로 관리하지 말고 하나의 JSON으로 몰아버리자
				// DTO처럼 관리해보자
				let json=[]; //  empty			
				json['key']=key; // 고유 값 넣기, 추후 이미지 삭제시 고유 값을 사용하려고
				json['name']=file.name; // 중복 이미지 체크시 사용예정
				json['binary']=e.target.result; // img.src에 대입할 예정
				json['file']=file; // 파일 자제에 대한 모든 정보
				
				key++;
				
				console.log("key 값은 ", key);
				
				console.log("i 값은 ", i);
				
				// app1.imageList.push(file);
				app1.imageList.push(json);
			}
			// reader.readAsDataURL(대상파일); 파일 하나당 스트림 하나
			reader.readAsDataURL(file);
		}
	}
}
	// 갤러리 등록
	function regist() {
		// 기존 html의 폼을 이용하여 전송할 경우,. 제일 마지막에 일으킨 이벤트에 의해 등록된 이미지들만 전송하므로
		// 누적된 이미지는 전송할 수 없다
		// 해결 : form을 대체하는 formData 객체를 이용하여 개발자가 주도하여 폼을 구성하여 전송하면 됨
		// html:5 FormData + JQuery 사용이 쉽다
		/* 따라서 이 부분은 버린다
		$("#form1").attr({
			action : "/gallery/regist",
			method : "post",
			enctype : "multipart/form-data"
		});
		$("#form1").submit();
		*/
		let formData = new FormData(); // 비어있는 폼을 하나 생성
		// 개발자가 직접 파라미터를 구성할 수 있다
		formData.append("title", $("input[name='title']").val());
		formData.append("writer", $("input[name='writer']").val());
		formData.append("content", $("textarea[name='content']").val());
		// 파일 파라미터 채우기 (2개 이상)
		for (let i=0; i<app1.imageList.length; i++) {
			let file = app1.imageList[i].file; // 전송할 파일 자체를 끄집어내기
			formData.append("photo", file); // formData.append("변수명", file); 알아서 배열로 인식함
		}
		// ajax 비동기 전송
		$.ajax({
			url : "/gallery/regist",
			type: "POST",
			processData : false, // title=test&writer=ddd 등 문자열 화 방지
			contentType : false, // application/x-www 방지
			data : formData,			success:function(result, status, xhr) {
					alert(result);
			}

		});	
	}

$(function() {
	init();
	// 이미지를 선택하면,,, 
	$("input[name='photo']").change(function () {
		// files 배열은 read-only라서 조작이 불가능하다
		// console.log(this.files);
		preview(this.files);
		$("#bt_regist").click(function() {
			regist();
		});
	});
});

</script>
</head>
<body>
	<div class="container" id="app1">
		<div class="row">
			<div class="col">
				<form id="form1">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Enter title" name="title">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Enter writer" name="writer">
					</div>
					<div class="form-group">
						<textarea class="form-control" placeholder="Enter content" name="content"></textarea>
					</div>
					<!-- 파일업로드 -->
					<div class="form-group">
						<input type="file" class="form-control" name="photo" multiple>
					</div>
					<!-- 미리보기 -->
					<div class="form-group" id="previewArea">
					
						<!-- <template v-for="i in count"> -->
						<!-- <template> -->
						<!-- <template v-for="s in src"> -->
						<template v-for="json in imageList">
							<!-- <imagebox :src="src[0]"/> -->
							<!-- <imagebox :key="1" :src="s"/> -->
							<imagebox :key="json.key" :src="json.binary" :idx="json.key"/>
						</template>
						
					</div>
					<div class="form-group">
						<button type="button" class="btn btn-secondary" id="bt_regist">등록</button>
						<button type="button" class="btn btn-secondary" >목록</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>