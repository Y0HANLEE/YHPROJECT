<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex05_void</title>
</head>
<body>
	<h1>FILE Upload</h1>
	<h2>파일업료드</h2>
	<form action="/sample/exUploadPost" method="post" enctype="multipart/form-data">
		<div>
			<input type="file" name="files">
		</div>
		<div>
			<input type="file" name="files">
		</div>
		<div>
			<input type="file" name="files">
		</div>
		<div>
			<input type="file" name="files">
		</div>
		<div>
			<input type="file" name="files">
		</div>
		<div>
			<input type="submit" value="upload">
		</div>		
	</form>	
</body>
</html>