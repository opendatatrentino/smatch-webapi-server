smatch-webapi-server
====================

S-Match Web API Server

This is a quick guide for using the S-Match Web API.

The Web API has three components: a server, a model and a client. To use the Web API you need to start the server and either call the HTTP API directly or use the Java client in your project.

# Using the server

1. Download the server

` git clone https://github.com/opendatatrentino/smatch-webapi-server.git`

You can configure the HTTP server from the file `smatch-webapi-server.properties` in the directory `src\main\resources\`.

2. Build the server

` cd smatch-webapi-server`

 `      mvn clean install`

3. Start the server

`mvn exec:java -Dexec.mainClass="it.unitn.disi.smatch.webapi.server.WebApiServer"`

# Using the client

1. Include the following POM snippet in your Maven project

` <dependency>`
 `   <groupId>it.unitn.disi</groupId>`
 `   <artifactId>smatch-webapi-client</artifactId>`
`    <version>${smatch-webapi-version}</version>`
`</dependency>`

The value of smatch-webapi-version which is available now in Maven Central is 1.0.0-RC.

2. Call the api.Match method like in the following example:

>         String sourceContextRoot = "neve luogo";
>         String targetContextRoot = "luogo";
>         
>         List<String> sourceContextNodes = Arrays.asList("codice",
> 				"nome",
> 				"nome breve",
> 				"quota",
> 				"latitudine",
> 				"longitudine");
>         
>         List<String> targetContextNodes = Arrays.asList("nome",
> 				"latitudine",
> 				"longitudine");

>         Correspondence correspondence = api.match(sourceContextRoot, sourceContextNodes, targetContextRoot, targetContextNodes);

# Using the Web HTTP API directly

1. Send a POST to `/webapi/smatch` with a request like this in the body : 

> { "request": { "parameters": { "Source": { "neve luogo": ["codice", "nome", "nome breve", "quota", "latitudine", "longitudine"] }, "Target": { "luogo": ["nome", "latitudine", "longitudine"] } } } }

The response will be similar to this:

> { "Correspondence": [{ "source": "\neve luogo", "target": "\luogo", "relation": ">" }, { "source": "\neve luogo\nome", "target": "\luogo\nome", "relation": ">" }] }

# Using External Data

S-Match comes with an internal data that supports English language only. If you want to support another language, such as Italian, you need to use external data. 

To use external data, set the `smatch.webapi.matching.properties-file` value in smatch-webapi-server.properties file to the path of the properties file that comes with the data. For example:

`smatch.webapi.matching.properties-file=/home/italianWordNet/s-match.properties`

And make sure that the properties file has the following settings which disables the use of internal files:
 
> Global.LinguisticOracle.WordNet.UseInternalFiles=false

> Global.SenseMatcher.InMemoryWordNetBinaryArray.UseInternalFiles=false

# More Information
Here you can find the explaination of web API : https://github.com/opendatatrentino/smatch-webapi-server/wiki/WebAPI
