<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="service" class="domain.DenunciaService" />
<jsp:useBean id="denuncia" class="domain.Denuncia" />
<!DOCTYPE html>
<html>
<head>
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #f2f2f2
}

th {
	background-color: #4169E1;
	color: white;
}

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
	<ul class="nav navbar-nav">
		<li><a href="index.jsp">Home</a></li>
		<li><a href="testeGet.jsp">Teste Get API</a></li>
		<li><a href="testePostBase64.jsp">Teste Post Base64 API</a></li>
		<li><a href="testePost.jsp">Teste Post Imagem</a></li>
	</ul>
	<table border="0">
		<tr>
			<th>ID</th>
			<th>Descrição</th>
			<th>Data</th>
			<th>Usuario</th>
			<th>Categoria</th>
			<th>Localização</th>
			<th>Arquivo</thS>
		</tr>
		<c:forEach var="denuncia" items="${service.lista}">
			<tr>
				<td>${denuncia.id}</td>
				<td width="400px">${denuncia.descricao}</td>
				<td>${denuncia.data.time}</td>
				<td>${denuncia.usuario}</td>
				<td>${denuncia.categoria.descricao}</td>
				<td><a
					href="https://www.google.com.br/maps/@${denuncia.latitude},${denuncia.longitude},11.35z">Acessar
						Google Maps</a></td>
				<td>
				<a href="<%=request.getContextPath()%>/rest/arquivos/${denuncia.id}">${denuncia.uriMidia}</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>