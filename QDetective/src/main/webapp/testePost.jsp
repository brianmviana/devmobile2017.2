<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="service" class="domain.DenunciaService" />
<jsp:useBean id="denuncia" class="domain.Denuncia" />
<!DOCTYPE html>
<html>
<head>
<style>
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #333333;
}

li {
	float: left;
}

li a {
	display: block;
	color: white;
	text-align: center;
	padding: 16px;
	text-decoration: none;
}

li a:hover {
	background-color: #111111;
}
</style>
</head>
<body>
	<nav class="navbar navbar-inverse">
		<ul class="nav navbar-nav">
			<li><a href="index.jsp">Home</a></li>
			<li><a href="testeGet.jsp">Teste Get API</a></li>
			<li><a href="testePostBase64.jsp">Teste Post Base64 API</a></li>
			<li><a href="testePost.jsp">Teste Post Imagem</a></li>
		</ul>
	</nav>
	<pre>
Upload do arquivo
<form enctype="multipart/form-data"
			action="<%=request.getContextPath()%>/rest/arquivos" method="POST">
	<input name="file" type="file" /> <br /> <br /> 
	<input type="submit" value="Enviar arquivo" />
</form>
</pre>
</body>
</html>