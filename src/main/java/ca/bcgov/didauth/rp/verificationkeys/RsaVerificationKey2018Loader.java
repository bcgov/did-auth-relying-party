package ca.bcgov.didauth.rp.verificationkeys;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RsaVerificationKey2018Loader implements VerificationKeyLoader<RSAPublicKey> {

	private static final String DID_PUBLIC_KEY_TYPE = "RsaVerificationKey2018";

	private static Logger log = LoggerFactory.getLogger(RsaVerificationKey2018Loader.class);
	private static final RsaVerificationKey2018Loader instance = new RsaVerificationKey2018Loader();

	private static KeyFactory keyFactory;

	static {

		try {

			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException ex) {

			throw new ExceptionInInitializerError(ex);
		}
	}

	public static RsaVerificationKey2018Loader get() {

		return instance;
	}

	@Override
	public RSAPublicKey verificationKey(uniresolver.did.PublicKey didPublicKey) throws GeneralSecurityException {

		if (! didPublicKey.isType(DID_PUBLIC_KEY_TYPE)) {

			if (log.isDebugEnabled()) log.debug("Public key " + didPublicKey.getId() + " is not of type " + DID_PUBLIC_KEY_TYPE);
			return null;
		}

		String verifyingKeyString = didPublicKey.getPublicKeyPem();
		if (verifyingKeyString == null) {

			if (log.isDebugEnabled()) log.debug("Public key " + didPublicKey.getId() + " has no data.");
			return null;
		}

		verifyingKeyString = verifyingKeyString.replace("-----BEGIN PUBLIC KEY-----", "");
		verifyingKeyString = verifyingKeyString.replace("-----END PUBLIC KEY-----", "");
		verifyingKeyString = verifyingKeyString.replace("\\r", "");
		verifyingKeyString = verifyingKeyString.replace("\\n", "");

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(verifyingKeyString));

		return (RSAPublicKey) keyFactory.generatePublic(keySpec);

	}
}