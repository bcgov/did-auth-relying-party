# DID Auth relying party - Architecture

	        sign VCredentials using                                lookup DID key using Universal Resolver
	       pre-configured DID and key                                      and verify VCredentials
	
	                  |                                                               ^
	                  v                                                               |
	           ________________                      ________________          ________________
	   ~o/    |                |     HTTP POST      |                |  HTTP  |                |
	   /|     | Web browser    | =================> | TheOrgBook     | <----> | DID Auth       |
	   / \    | with add-on    |                    | website        |        | relying party  |
	          |________________|                    |________________|        |________________|
	
                               (untrusted connection)               (trust relationship)
                                                                    (e.g. local network) 

This shows a deployment involving the DID Auth browser add-on and an instance of the DID Auth relying party.
