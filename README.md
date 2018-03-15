
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)


# DID Auth relying party

This is a DID Auth relying party that can verify incoming DID Auth messages expressed as [Verifiable Credentials](https://w3c.github.io/vc-data-model/) based on [Decentralized Identifiers](https://w3c-ccg.github.io/did-spec/).

## Technology Stack Used

 * [Decentralized Identifiers (DIDs)](https://w3c-ccg.github.io/did-spec/)
 * [DID Auth](https://github.com/WebOfTrustInfo/rebooting-the-web-of-trust-spring2018/blob/master/draft-documents/did_auth_draft.md)
 * [Verifiable Credentials](https://w3c.github.io/vc-data-model/)
 * [Linked Data Signatures](https://w3c-dvcg.github.io/ld-signatures/)
 * [Linked Data Cryptographic Suites](https://w3c-ccg.github.io/ld-cryptosuite-registry/)

## Third-Party Products/Libraries used and the License they are covert by

 * [verifiable-credentials-java](https://github.com/TrustNetFI/verifiable-credentials-java) - MIT
 * [ld-signatures-java](https://github.com/WebOfTrustInfo/ld-signatures-java) - MIT
 * [jsonld-java](https://github.com/jsonld-java/jsonld-java) - BSD-3-Clause
 * [universal-resolver-java](https://github.com/decentralized-identity/universal-resolver/tree/master/implementations/java) - Apache-2.0

## Project Status

Under development for a [BCDevExchange opportunity](https://bcdevexchange.org/opportunities/opp-initial-reference-implementation-of-decentralized-authentication--did-auth--and-authorization-mechanisms). Note that the underlying technologies (DIDs, DID Auth, Verifiable Credentials) are still heavily evolving.

All repositories related to this project:

 * https://github.com/bcgov/did-auth-extension - A DID Auth browser add-on
 * https://github.com/bcgov/did-auth-relying-party - A DID Auth relying party **(this repository)**
 * https://github.com/bcgov/http-did-auth-proxy - A DID Auth HTTP proxy

## Documentation

Architecture: [architecture.md](./docs/architecture.md)

Scenarios: [scenarios.md](./docs/scenarios.md)

DID Auth relying party sample deployment: https://did-auth-relying-party.danubetech.com/

## Deployment (Local Development)

Build with Maven:

	mvn clean install jetty:run

Build and run with Docker:

	docker build -f ./Dockerfile -t did_auth_relying_party .
	docker run -ti -p 8080:8080 did_auth_relying_party:latest

## Getting Help or Reporting an Issue

To report bugs/issues/feature requests, please file an [issue](../../issues).

## How to Contribute

If you would like to contribute, please see our [CONTRIBUTING](./CONTRIBUTING.md) guidelines.

Please note that this project is released with a [Contributor Code of Conduct](./CODE_OF_CONDUCT.md). 
By participating in this project you agree to abide by its terms.

## License

    Copyright 2018 Province of British Columbia

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
