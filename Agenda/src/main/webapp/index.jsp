<pre>
<a href="<%=request.getContextPath()%>/rest/contatos">/rest/contatos</a>
<a href="<%=request.getContextPath()%>/rest/contatos/991">/rest/contatos/1</a>
<a href="<%=request.getContextPath()%>/rest/arquivos/991">/rest/arquivos/1</a>
<a href="<%=request.getContextPath()%>/rest/contatos/992">/rest/contatos/2</a>
<a href="<%=request.getContextPath()%>/rest/arquivos/992">/rest/arquivos/2</a>
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