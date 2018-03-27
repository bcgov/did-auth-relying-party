package ca.bcgov.didauth.rp.verificationkeys;

import java.security.GeneralSecurityException;

import org.apache.commons.codec.binary.Base64;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

public class Secp256k1VerificationKey2018Loader implements VerificationKeyLoader<ECKey> {

	private static final String DID_PUBLIC_KEY_TYPE = "Secp256k1VerificationKey2018";

	private static Logger log = LoggerFactory.getLogger(Secp256k1VerificationKey2018Loader.class);
	private static final Secp256k1VerificationKey2018Loader instance = new Secp256k1VerificationKey2018Loader();

	public static Secp256k1VerificationKey2018Loader get() {

		return instance;
	}

	@Override
	public ECKey verificationKey(uniresolver.did.PublicKey didPublicKey) throws GeneralSecurityException {

		if (! didPublicKey.isType(DID_PUBLIC_KEY_TYPE)) {

			if (log.isDebugEnabled()) log.debug("Public key " + didPublicKey.getId() + " is not of type " + DID_PUBLIC_KEY_TYPE);
			return null;
		}

		String verificationKeyString;

		verificationKeyString = didPublicKey.getPublicKeyBase64();
		if (verificationKeyString != null) return ECKey.fromPublicOnly(Base64.decodeBase64(verificationKeyString));
		verificationKeyString = didPublicKey.getPublicKeyBase58();
		if (verificationKeyString != null) return ECKey.fromPublicOnly(Base58.decode(verificationKeyString));
		verificationKeyString = didPublicKey.getPublicKeyHex();
		if (verificationKeyString != null) return ECKey.fromPublicOnly(Hex.decode(verificationKeyString));

		if (log.isDebugEnabled()) log.debug("Public key " + didPublicKey.getId() + " has no data.");
		return null;
	}
}
