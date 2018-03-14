package ca.bcgov.didauth.rp;

import fi.trustnet.verifiablecredentials.VerifiableCredential;

public class Interpretation {

	static String interpretDidAuthCredential(VerifiableCredential vc) {

		if (vc.getIssuer() == null || vc.getSubject() == null) return null;

		if (! vc.getType().contains("Credential")) return null;
		if (! vc.getType().contains("DidAuthCredential")) return null;
		if (! (vc.getIssuer().toString().equals(vc.getSubject()))) return null;

		return vc.getSubject();
	}

	static VerifiableCredential interpretCredential(VerifiableCredential vc) {

		if (vc.getIssuer() == null || vc.getSubject() == null) return null;

		if (! vc.getType().contains("Credential")) return null;

		return vc;
	}
}
