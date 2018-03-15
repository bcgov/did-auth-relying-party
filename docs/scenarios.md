# DID Auth relying party - Scenarios

## Authentication Scenario 2: Administrative 

This is simply a self-asserted Verifiable Credential by an identity owner to authenticate their DID.

**Admin DID:** `did:btcr:xkrn-xzcr-qqlv-j6sl`

	{
		"type": ["Credential", "DidAuthCredential"],
		"issuer": "did:btcr:xkrn-xzcr-qqlv-j6sl",
		"issued": "2010-01-01",
		"claim": {
			"id": "did:btcr:xkrn-xzcr-qqlv-j6sl",
			"publicKey": "did:btcr:xkrn-xzcr-qqlv-j6sl#keys-1"
		},
		"proof": {
			"type": "Secp256k1Signature2018",
			"creator": "did:btcr:xkrn-xzcr-qqlv-j6sl#keys-1",
			"created": "2018-01-01T21:19:10Z",
			"nonce": "...",
			"signatureValue": "..."
		}
	}

## Authorization Scenario 1: Claim Your Claims

This is a Verifiable Credential issued by VON that an "Org Manager" is authorized to represent an "Org" with capability "management".

**TheOrgBook DID:** `did:sov:0000000000`

**Org DID:** `did:sov:1234567890`

**Org Manager DID:** `did:sov:DavnUKB3kjn7VmVZXzEDL7`

	{
		"type": ["Credential"],
		"issuer": "did:sov:0000000000",
		"issued": "2010-01-01",
		"claim": {
			"id": "did:sov:1234567890",
			"management": "did:sov:DavnUKB3kjn7VmVZXzEDL7"
		},
		"proof": {
			"type": "Ed25519Signature2018",
			"created": "2018-01-01T21:19:10Z",
			"creator": "did:sov:0000000000#keys-1",
			"nonce": "...",
			"signatureValue": "..."
		}
	}

## Authorization Scenario 2: Delegation

This is a Verifiable Credential issued by an "Org Manager" that an "Org Delegate" is authorized to represent an "Org" with capability "management".

**Org DID:** `did:sov:1234567890`

**Org Manager DID:** `did:sov:DavnUKB3kjn7VmVZXzEDL7`

**Org Delegate DID:** `did:v1:test:nym:3AEJTDMSxDDQpyUftjuoeZ2Bazp4Bswj1ce7FJGybCUu`

	{
		"type": ["Credential"],
		"issuer": "did:sov:DavnUKB3kjn7VmVZXzEDL7",
		"issued": "2010-01-01",
		"claim": {
			"id": "did:sov:1234567890",
			"management": "did:v1:test:nym:3AEJTDMSxDDQpyUftjuoeZ2Bazp4Bswj1ce7FJGybCUu"
		},
		"proof": {
			"type": "Ed25519Signature2018",
			"created": "2018-01-01T21:19:10Z",
			"creator": "did:sov:DavnUKB3kjn7VmVZXzEDL7#keys-1",
			"nonce": "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e",
			"signatureValue": "..."
		}
	}
