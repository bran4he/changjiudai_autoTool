<html>
<head>
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
		
		var xhr = new XMLHttpRequest();
		xhr.open("GET", "exportXlsx", true);
		xhr.onreadystatechange = function(){
		    if (xhr.readyState == 4) {
		        if (xhr.status == 200) {
		            var text = xhr.responseText;
		            console.log(text);
		            document.getElementById("report").href = text;
		        }
		    }
		};
		xhr.send(null);
	}
	
	window.onload = function () {
		console.log("load...");
	}
	
	</script>

</head>
<body>
<h2>Hello World!</h2>
<hr>

	<label>username:</label>
	<input id="username"/><br>
	
	<label>password:</label>
	<input type="password" id="password"/><br>
	
	<label>imagecode:</label>
	<input id="imgCode"/><br>
	
	<input type="button" value="getImg" id="getImgCode" onclick="getImgCode()" style="width:80;height:30;" /><br>
	<input type="button"  value="lgoin&sign"  id="login" onclick="login()" style="width:80;height:30;"/>
	<img alt="	imgCode" id="imgUrl" src=""> <br>
	<input type="button"  value="exportReport"  id="export" onClick="exportXlsx()" style="width:80;height:30;" /><br>

	<a id="report" href="">click and download report</a>
	
</body>
</html>
