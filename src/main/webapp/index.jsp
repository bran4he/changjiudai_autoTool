<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">


<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	
	function getImgCode() {
		var xhr = new XMLHttpRequest();
		xhr.open("GET", "getCode", true);
		xhr.onreadystatechange = function(){
		    if (xhr.readyState == 4) {
		        if (xhr.status == 200) {
		            var text = xhr.responseText;
		            console.log(text);
		            document.getElementById("imgUrl").src = text;
		        }
		    }
		};
		xhr.send(null);
	}

	function login() {
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		var code = document.getElementById("imgCode").value;
		console.log(username, password, code);
		
		var xhr = new XMLHttpRequest();
		var getUrl = "login?username="+username+"&password="+password+"&imgCode="+code;
		console.info(getUrl);
		xhr.open("GET", getUrl, true);
		xhr.onreadystatechange = function(){
		    if (xhr.readyState == 4) {
		        if (xhr.status == 200) {
		            var text = xhr.responseText;
		            console.log(text);
		        }
		    }
		};
		xhr.send(null);
	}
	
	function exportXlsx() {
		//disable button
		$("#export").button('loading');
		var xhr = new XMLHttpRequest();
		xhr.open("GET", "exportXlsx", true);
		xhr.onreadystatechange = function(){
		    if (xhr.readyState == 4) {
		        if (xhr.status == 200) {
		            var text = xhr.responseText;
		            console.log(text);
		            //enable button
		            $("#export").button('reset');
		            //alert result
		            alert('export report successfully!');
		            document.getElementById("report").href = text;
		        }
		    }
		};
		xhr.send(null);
	}
	
	window.onload = function () {
		console.log("load...");
		
		$("#getImgCode").bind("click", getImgCode);
		$("#login").bind("click", login);
		$("#export").bind("click", exportXlsx);
	}
	
	</script>

</head>
<body>
<h2>Hello World!</h2>
<hr>
<div class="panel panel-success center-block" style="width:50%">
  <div class="panel-heading"><a href="#">Auto Panel <span class="badge">16</span></a></div>
  <div class="panel-body">
  
  <form class="form-horizontal" role="form">
  <div class="form-group">
    <label for="username" class="col-sm-2 control-label">User Name</label>
    <div class="col-sm-10">
      <input type="email" class="form-control" id="username" placeholder="input username">
    </div>
  </div>
  
  <div class="form-group">
    <label for="password" class="col-sm-2 control-label">Password</label>
    <div class="col-sm-10">
      <input type="password" class="form-control" id="password" placeholder="input password">
    </div>
  </div>
  
   <div class="form-group">
    <label for="imgCode" class="col-sm-2 control-label">Captcha</label>
    <div class="col-sm-10">
      <input class="form-control" id="imgCode" placeholder="Captcha Code">
    </div>
  </div>
  
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
    	<img alt="imgCode" id="imgUrl" src="">
    	<button type="button" class="btn btn-default btn-sm" id="getImgCode">imgCode</button>
    	<button type="button" class="btn btn-success btn-sm" id="login">lgoin sign</button>
    </div>
  </div>
  
</form>
<div class="panel-footer">
	<div class="pull-right">
		<button type="button" class="btn btn-danger btn-sm" id="export">exportReport</button>
		<a id="report" href="">Download Report</a>
	</div>
</div>
	</div>
</div>	
</body>
<script type="text/javascript">
$(function() {
	console.log('done');
});
</script>
</html>
