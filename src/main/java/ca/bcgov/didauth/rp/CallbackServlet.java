package ca.bcgov.didauth.rp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jsonldjava.utils.JsonUtils;

import fi.trustnet.verifiablecredentials.VerifiableCredential;

public class CallbackServlet extends HttpServlet {

	private static final long serialVersionUID = -1283810968622203934L;

	private static Logger log = LoggerFactory.getLogger(CallbackServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<LinkedHashMap<String, Object>> jsonArray = (ArrayList<LinkedHashMap<String, Object>>) JsonUtils.fromReader(request.getReader());
		if (log.isInfoEnabled()) log.info("JSON:" + jsonArray);

		ArrayList<VerifiableCredential> vcs = new ArrayList<VerifiableCredential> ();
		ArrayList<VerifiableCredential> authorizations = new ArrayList<VerifiableCredential> ();

		// interpret Verifiable Credentials

		for (LinkedHashMap<String, Object> jsonLdObject : jsonArray) {

			VerifiableCredential vc = Interpretation.interpretCredential(VerifiableCredential.fromJsonLdObject(jsonLdObject));
			if (vc == null) continue;
			vcs.add(vc);

			String authenticatedDid = Interpretation.interpretDidAuthCredential(vc);

			if (authenticatedDid != null) {

				request.getSession().setAttribute("authenticatedDid", authenticatedDid);
			} else {

				VerifiableCredential authorization = vc;
				authorizations.add(authorization);
			}
		}

		// done

		request.getSession().setAttribute("vcs", vcs);
		request.getSession().setAttribute("authorizations", authorizations);
	}
}
