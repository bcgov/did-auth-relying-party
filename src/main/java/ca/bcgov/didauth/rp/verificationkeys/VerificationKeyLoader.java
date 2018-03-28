package ca.bcgov.didauth.rp.verificationkeys;

import java.security.GeneralSecurityException;

public interface VerificationKeyLoader<KEY> {

	public abstract KEY verificationKey(uniresolver.did.PublicKey didPublicKey) throws GeneralSecurityException;
}
