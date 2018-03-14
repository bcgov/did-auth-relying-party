# DID Auth relying party

This is a DID Auth relying party service that can verify incoming DID Auth messages expressed as [Verifiable Credentials](https://w3c.github.io/vc-data-model/) based on [Decentralized Identifiers](https://w3c-ccg.github.io/did-spec/).

DID Auth browser plugin:

 * https://github.com/peacekeeper/did-auth-extension

For use in DID-based authentication and authorization scenarios:

 * https://bcdevexchange.org/opportunities/opp-initial-reference-implementation-of-decentralized-authentication--did-auth--and-authorization-mechanisms

Example DID Auth relying party:

 * https://did-auth-relying-party.danubetech.com/

Ongoing work on DID Auth:

 * https://github.com/WebOfTrustInfo/rebooting-the-web-of-trust-spring2018/blob/master/draft-documents/did_auth_draft.md
 * https://github.com/WebOfTrustInfo/rebooting-the-web-of-trust-spring2018/blob/master/topics-and-advance-readings/DID%20Auth:%20Scope,%20Formats,%20and%20Protocols.md

## Typical Deployment

	        sign VCredentials using                                       lookup DID key using UniR
	       pre-configured DID and key                                      and verify VCredentials
	
	                  |                                                               ^
	                  v                                                               |
	           ________________                      ________________          ________________
	   ~o/    |                |     HTTP POST      |                |  HTTP  |                |
	   /|     | Web browser    | =================> | TheOrgBook     | <----> | DID Auth       |
	   / \    | with plugin    |                    | website        |        | RP Service     |
	          |________________|                    |________________|        |________________|
	
                               (untrusted connection)               (trust relationship)
                                                                    (e.g. local network) 

This shows a deployment involving an instance of the DID Auth relying party service.

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

## How to build

A Docker image can be built as follows:

	docker build -f ./Dockerfile -t did_auth_relying_party .	

