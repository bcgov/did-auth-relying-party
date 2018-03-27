package ca.bcgov.didauth.rp.verificationkeys;

import java.security.GeneralSecurityException;

import org.abstractj.kalium.NaCl;
import org.apache.commons.codec.binary.Base64;
import org.bitcoinj.core.Base58;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

public class Ed25519VerificationKey2018Loader implements VerificationKeyLoader<byte[]> {

	private static final String DID_PUBLIC_KEY_TYPE = "Ed25519VerificationKey2018";

	private static Logger log = LoggerFactory.getLogger(Ed25519VerificationKey2018Loader.class);
	private static final Ed25519VerificationKey2018Loader instance = new Ed25519VerificationKey2018Loader();

	static {

		NaCl.init();
	}

	public static Ed25519VerificationKey2018Loader get() {

		return instance;
	}

	@Override
	public byte[] verificationKey(uniresolver.did.PublicKey didPublicKey) throws GeneralSecurityException {

		if (! didPublicKey.isType(DID_PUBLIC_KEY_TYPE)) {

			if (log.isDebugEnabled()) log.debug("Public key " + didPublicKey.getId() + " is not of type " + DID_PUBLIC_KEY_TYPE);
			return null;
		}

		String verificationKeyString;

		verificationKeyString = didPublicKey.getPublicKeyBase64();
		if (verificationKeyString != null) return Base64.decodeBase64(verificationKeyString);
		verificationKeyString = didPublicKey.getPublicKeyBase58();
		if (verificationKeyString != null) return Base58.decode(verificationKeyString);
		verificationKeyString = didPublicKey.getPublicKeyHex();
		if (verificationKeyString != null) return Hex.decode(verificationKeyString);

		if (log.isDebugEnabled()) log.debug("Public key " + didPublicKey.getId() + " has no data.");
		return null;
	}
}