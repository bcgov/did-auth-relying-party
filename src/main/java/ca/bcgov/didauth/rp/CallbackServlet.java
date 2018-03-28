package ca.bcgov.didauth.rp;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.utils.JsonUtils;

import fi.trustnet.verifiablecredentials.VerifiableCredential;
import uniresolver.ResolutionException;

public class CallbackServlet extends HttpServlet {

	private static final long serialVersionUID = -1283810968622203934L;

	private static Logger log = LoggerFactory.getLogger(CallbackServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<LinkedHashMap<String, Object>> jsonArray = (ArrayList<LinkedHashMap<String, Object>>) JsonUtils.fromReader(request.getReader());
		if (log.isInfoEnabled()) log.info("JSON:" + jsonArray);

		String authenticatedDid = null;
		ArrayList<VerifiableCredential> vcs = new ArrayList<VerifiableCredential> ();
		ArrayList<VerifiableCredential> authorizations = new ArrayList<VerifiableCredential> ();

		// interpret and verify Verifiable Credentials

		for (LinkedHashMap<String, Object> jsonLdObject : jsonArray) {

			VerifiableCredential vc = Interpretation.interpretCredential(VerifiableCredential.fromJsonLdObject(jsonLdObject));
			if (vc == null) continue;

			// verify

			try {

				boolean verify = Verification.verify(vc);
				if (! verify) throw new GeneralSecurityException("Invalid signature.");
			} catch (ResolutionException | GeneralSecurityException | JsonLdError ex) {

				if (log.isWarnEnabled()) log.warn("Unable to verify LD signature: " + ex.getMessage(), ex);
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unable to verify LD signature: " + ex.getMessage());
				response.getWriter().close();
				return;
			}

			// add Verifiable Credential

			vcs.add(vc);

			// DID Auth Verifiable Credential?

			if (Interpretation.interpretDidAuthCredential(vc) != null) {

				authenticatedDid = Interpretation.interpretDidAuthCredential(vc);
			} else {

				VerifiableCredential authorization = vc;
				authorizations.add(authorization);
			}
		}

		// set session attributes

		if (authenticatedDid != null) request.getSession().setAttribute("authenticatedDid", authenticatedDid);
		request.getSession().setAttribute("vcs", vcs);
		request.getSession().setAttribute("authorizations", authorizations);

		// write response

		LinkedHashMap<String, Object> jsonLdObject = new LinkedHashMap<String, Object> ();

		if (authenticatedDid != null) jsonLdObject.put("authenticatedDid", authenticatedDid);

		LinkedList<Object> jsonVcs = new LinkedList<Object> ();
		for (VerifiableCredential vc : vcs) jsonVcs.add(vc.getJsonLdObject());
		jsonLdObject.put("vcs", jsonVcs);

		LinkedList<Object> jsonAuthorizations = new LinkedList<Object> ();
		for (VerifiableCredential authorization : authorizations) jsonAuthorizations.add(authorization.getJsonLdObject());
		jsonLdObject.put("authorizations", jsonAuthorizations);

		response.setContentType("application/json");
		response.getWriter().println(JsonUtils.toString(jsonLdObject));
		response.getWriter().flush();
		response.getWriter().close();
	}
}
