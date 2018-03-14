<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="com.github.jsonldjava.utils.JsonUtils" %>
<%@ page import="fi.trustnet.verifiablecredentials.VerifiableCredential" %>
<!DOCTYPE html>
<html >

	<head>

		<meta charset="utf-8">
		<base href="/" />
		<title>Example DID Auth RP</title>

		<style>
			* { font-family: sans; }
			h1 { font-size: 16pt; }
			body { margin: 30px; }
			button { margin: 20px; padding: 10px; }
			table { background-color: #fee; padding-left: 10px; margin-bottom: 20px; }
			td { padding: 5px; }
			td.code { font-family: monospace; font-size: 12pt; }
			td.padbottom { margin-bottom: 20px; }
			pre { font-family: monospace; }
		</style>

		<script type="text/javascript">
			function onButtonClick() {
				var url = window.location.protocol + "//" + window.location.host + "/";
				window.postMessage({ type: "DID_AUTH", callback: url + "callback" }, "*");
			}
			function onDetailsClick() {
				var details = document.getElementById("details");
				if (details.style.display === "none") {
					details.style.display = "block";
				} else {
					details.style.display = "none";
				}
			}
		</script>

	</head>
	
	<body>

		<h1>DID Auth Example</h1>
		<img src="did-auth-icon.png" align="middle">
		<button onclick="onButtonClick()">DID Auth</button>
		<hr noshade>

		<% if (session.getAttribute("authenticatedDid") != null) { %>
			<p>Authenticated: <strong><%= session.getAttribute("authenticatedDid") %></strong></p> 
		<% } else { %>
			<p>Not authenticated.</p>
		<% } %>

		<% if (session.getAttribute("authorizations") != null && ((List<VerifiableCredential>) session.getAttribute("authorizations")).size() > 0) { %>
			<% for (VerifiableCredential authorization : ((List<VerifiableCredential>) session.getAttribute("authorizations"))) { %>
				<% LinkedHashMap<String, Object> claims = new LinkedHashMap<String, Object> (authorization.getJsonLdClaimsObject()); %>
				<% claims.remove("id"); %>
				<table>
					<tr colspan="5"><td><p><b>Verifiable Credential:</b></p></td></tr>
					<tr>
					<td><img src="person.png"></td>
					<td class="code"><%= authorization.getIssuer() %></td>
					<td><img src="arrow.png"></td>
					<td class="code"><%= authorization.getSubject() %></td>
					<td><img src="person.png"></td>
					</tr>
					<tr><td class="padbottom"><p>Claims:</p></td><td colspan="4" class="code padbottom"><p><%= claims %></p></td></tr>
				</table>
			<% } %>
		<% } else { %>
			<p>No additional authorizations.</p>
		<% } %>

		<% if (session.getAttribute("vcs") != null && ((List<VerifiableCredential>) session.getAttribute("vcs")).size() > 0) { %>
			<hr noshade>
			<button onclick="onDetailsClick()">View Details</button>
			<div id="details" style="display: none">
			<% for (VerifiableCredential vc : ((List<VerifiableCredential>) session.getAttribute("vcs"))) { %>
				<pre><%= JsonUtils.toPrettyString(vc.getJsonLdObject()) %></pre>
			<% } %>
			</div>
		<% } %>

	</body>

</html>
