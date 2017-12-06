<pre>
<a href="<%=request.getContextPath()%>/rest/denuncias">/rest/contatos</a>
<a href="<%=request.getContextPath()%>/rest/denuncias/1">/rest/contatos/1</a>
<a href="<%=request.getContextPath()%>/rest/arquivos/1">/rest/arquivos/1</a>
</pre>
<pre>
Converter Base64 em Arquivo
<form enctype="application/x-www-form-urlencoded"
		action="<%=request.getContextPath()%>/rest/arquivos/postFotoBase64"
		method="POST">
	FileName:
	<input name="fileName" type="text" />
	Base64:
	<textarea name="base64" type="textarea" cols="60" rows="10"></textarea>
	<!-- Botão de submit -->
	<input type="submit" value="Enviar arquivo" />
</form>
</pre>