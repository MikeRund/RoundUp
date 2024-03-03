## Round Up feature overview ## 
This project is for the Starling Software Engineer Technical Challenge.
The function of this code is to round up the outgoing transactions of a sandbox customers simulated data and store it in a new saving goal using Starlings API.

The code is split into client, service and utils packages. 

The client package contains Java classes designed to interact with Starlings API, and store any neccessary data. For example, the Account class can perform an http request to retrieve the accountUid, 
and the SavingSpaceClient handles functionility related to saving spaces, i.e. creating a saving space.

The serivce package contains the RoundUpService class. It is from here that first the round up amount is calculated, and then moved into a new saving space.
This is achieved by utilizing the client classes, which can perform all neccessary api calls to obtain the needed data from the API and allow the class to create a new space.

The utils packages contain classes that provide utilities. The Constants class simply holds constants in one place, and the RequestBuilder was made to simplify the process of making Http requests with Http client.
The access token necceasy for all API calls is stored in a config.properties file that can only be accessed from the RequestBuilder.
