# DID Auth relying party - Scenarios

## Authentication Scenario 2: Administrative 

This is simply a self-asserted Verifiable Credential by an identity owner to authenticate their DID.

**Admin DID:** `did:btcr:xs95-wzv8-qqte-jz88`

	{
		"@context" : [ "https://w3id.org/credentials/v1", "https://w3id.org/security/v1" ],
		"type" : [ "Credential", "DidAuthCredential" ],
		"issuer" : "did:btcr:xs95-wzv8-qqte-jz88",
		"issued" : "2018-03-15",
		"claim" : {
			"id" : "did:btcr:xs95-wzv8-qqte-jz88",
			"publicKey" : "did:btcr:xs95-wzv8-qqte-jz88#key-1"
		},
		"signature" : {
			"type" : "EcdsaKoblitzSignature2016",
			"creator" : "did:btcr:xs95-wzv8-qqte-jz88#key-1",
			"created" : "2018-03-15T00:00:00Z",
			"domain" : null,
			"nonce" : "54c83860-a79b-4523-88f9-ad420c669c12",
			"signatureValue" : "MEUCIQC8JxrztioLbR5dfQwLBPnKMfE6RObSU9jpAJUr+YBxSQIgCbTbiabx8DMeKKgW1BfT+c+U9fy7DnhYjfH1xGFU8GY="
		}
	}

## Authorization Scenario 1: Claim Your Claims

This is a Verifiable Credential issued by VON that an "Org Manager" is authorized to represent an "Org" with capability "management".

**TheOrgBook DID:** `did:sov:MDBKSD4Cm5EhhXWzTynube`

**Org DID:** `did:sov:1234567890`

**Org Manager DID:** `did:sov:DavnUKB3kjn7VmVZXzEDL7`

	{
		"@context" : [ "https://w3id.org/credentials/v1", "https://w3id.org/security/v1" ],
		"type" : [ "Credential" ],
		"issuer" : "did:sov:MDBKSD4Cm5EhhXWzTynube",
		"issued" : "2018-03-15",
		"claim" : {
			"id" : "did:sov:1234567890",
			"management" : "did:sov:DavnUKB3kjn7VmVZXzEDL7"
		},
		"signature" : {
			"type" : "Ed25519Signature2018",
			"creator" : "did:sov:MDBKSD4Cm5EhhXWzTynube#key-1",
			"created" : "2018-03-15T00:00:00Z",
			"domain" : null,
			"nonce" : "d1458980-d63b-4c3c-aad9-f1d182f5b399",
			"signatureValue" : "CGG+Isa9QtpqS9NuKTATBipY2cb50fFlCKHyLPV+Z136m304IqHjWnP9M0QM6i4ilYuUcS2ibOJtjWPHfmk7AQ=="
		}
	}

## Authorization Scenario 2: Delegation

This is a Verifiable Credential issued by an "Org Manager" that an "Org Delegate" is authorized to represent an "Org" with capability "management".

**Org DID:** `did:sov:1234567890`

**Org Manager DID:** `did:sov:DavnUKB3kjn7VmVZXzEDL7`

**Org Delegate DID:** `did:v1:test:nym:rZdPg5VF6SqrVuEYEHAuDaeikkA2D8QBLRJQRnhz3pI`

	{
		"@context" : [ "https://w3id.org/credentials/v1", "https://w3id.org/security/v1" ],
		"type" : [ "Credential" ],
		"issuer" : "did:sov:DavnUKB3kjn7VmVZXzEDL7",
		"issued" : "2018-03-15",
		"claim" : {
			"id" : "did:sov:1234567890",
			"management" : "did:v1:test:nym:rZdPg5VF6SqrVuEYEHAuDaeikkA2D8QBLRJQRnhz3pI"
		},
		"signature" : {
			"type" : "Ed25519Signature2018",
			"creator" : "did:sov:DavnUKB3kjn7VmVZXzEDL7#key-1",
			"created" : "2018-03-15T00:00:00Z",
			"domain" : null,
			"nonce" : "17eb96ae-ccef-4500-bb5b-01e92a93abe7",
			"signatureValue" : "YuYSql92rC1PloE3TNSD/4LJ15I3zbbMtYB4D6BSWdnuvOJBX1WobbDC0/tAcuJ0xWkL47DpOibJXFsKIjlsDw=="
		}
	}
