package ca.bcgov.didauth.rp;

import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPublicKey;

import org.bitcoinj.core.ECKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jsonldjava.core.JsonLdError;

import ca.bcgov.didauth.rp.verificationkeys.Ed25519VerificationKey2018Loader;
import ca.bcgov.didauth.rp.verificationkeys.RsaVerificationKey2018Loader;
import ca.bcgov.didauth.rp.verificationkeys.Secp256k1VerificationKey2018Loader;
import fi.trustnet.verifiablecredentials.VerifiableCredential;
import info.weboftrust.ldsignatures.LdSignature;
import info.weboftrust.ldsignatures.suites.SignatureSuites;
import info.weboftrust.ldsignatures.validator.EcdsaKoblitzSignature2016LdValidator;
import info.weboftrust.ldsignatures.validator.Ed25519Signature2018LdValidator;
import info.weboftrust.ldsignatures.validator.LdValidator;
import info.weboftrust.ldsignatures.validator.RsaSignature2017LdValidator;
import uniresolver.ResolutionException;
import uniresolver.client.ClientUniResolver;
import uniresolver.did.DIDDocument;
import uniresolver.did.PublicKey;
import uniresolver.result.ResolutionResult;

public class Verification {

	private static Logger log = LoggerFactory.getLogger(Verification.class);

	public static boolean verify(VerifiableCredential vc) throws ResolutionException, GeneralSecurityException, JsonLdError {

		// discover issuer's verification key

		ClientUniResolver clientUniResolver = new ClientUniResolver();
		clientUniResolver.setResolveUri("https://uniresolver.io/1.0/identifiers/");

		URI issuer = vc.getIssuer();
		if (log.isDebugEnabled()) log.debug("Issuer DID: " + issuer.toString());

		LdSignature ldSignature = vc.getLdSignature();
		if (ldSignature == null) {

			if (log.isWarnEnabled()) log.warn("No LD signature found on Verifiable Credential.");
			return false;
		}

		String signatureType = ldSignature.getType();
		if (log.isDebugEnabled()) log.debug("LD signature type: " + signatureType);

		ResolutionResult resolutionResult = clientUniResolver.resolve(issuer.toString());
		DIDDocument didDocument = resolutionResult.getDidDocument();

		LdValidator<?> ldValidator = null;
		Object verifyingKey = null;

		if (SignatureSuites.SIGNATURE_SUITE_ED25519SIGNATURE2018.getTerm().equals(signatureType)) {

			for (PublicKey didPublicKey : didDocument.getPublicKeys()) if (verifyingKey == null) verifyingKey = Ed25519VerificationKey2018Loader.get().verificationKey(didPublicKey);
			ldValidator = new Ed25519Signature2018LdValidator((byte[]) verifyingKey);
		} else if (SignatureSuites.SIGNATURE_SUITE_ECDSAKOBLITZSIGNATURE2016.getTerm().equals(signatureType)) {

			for (PublicKey didPublicKey : didDocument.getPublicKeys()) if (verifyingKey == null) verifyingKey = Secp256k1VerificationKey2018Loader.get().verificationKey(didPublicKey);
			ldValidator = new EcdsaKoblitzSignature2016LdValidator((ECKey) verifyingKey);
		} else if (SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2017.getTerm().equals(signatureType)) {

			for (PublicKey didPublicKey : didDocument.getPublicKeys()) if (verifyingKey == null) verifyingKey = RsaVerificationKey2018Loader.get().verificationKey(didPublicKey);
			ldValidator = new RsaSignature2017LdValidator((RSAPublicKey) verifyingKey);
		}

		if (log.isDebugEnabled()) log.debug("LD validator: " + ldValidator.getClass().getSimpleName());
		if (log.isDebugEnabled()) log.debug("Verifying key: " + verifyingKey.getClass().getSimpleName());

		if (ldValidator == null || verifyingKey == null) return false;

		boolean verified = ldValidator.validate(vc.getJsonLdObject());
		if (log.isDebugEnabled()) log.debug("Verified: " + verified);

		return verified;
	}
}
