= User Manual =

== Introduction ==
   * Name of the project: (smatch-webapi-server)
   * Version of the project: 1.0.0
   * Release notes: https://sweb.science.unitn.it/trac/sweb/wiki/ReleaseNotes/smatch-webapi-server/1.0.0
   * Javadocs: https://sweb.science.unitn.it/javadocs/smatch-webapi-server/1.0.0 

== Goal of the module ==
The goal of this module is to make S-Match services available as web services. 

This is achieved by embedding the Jetty web server and using it to process the HTTP requests for the matching services.

== Getting started ==
   * Requirements: Requires Java and Maven.
   * The maven pom.xml dependency (POM snippet)

{{{
<dependency>
    <groupId>it.unitn.disi</groupId>
    <artifactId>smatch-webapi-server</artifactId>
    <version>1.0.0</version>
</dependency>
}}}

   * How to install: 

To use as stand alone, download the Maven project and build it. Then execute the main class : {{{it.unitn.disi.smatch.webapi.server.WebApiServer }}}

To use it in another project, include the POM snippet in your project as a dependency.

   * How to configure: configuration of the port number for HTTP connection is done in smatch-webapi-model.properties file.

== How to use ==
   1. Basic use: 

The basic use of the server is to start and stop it. 

To start the server, execute the main class : {{{it.unitn.disi.smatch.webapi.server.WebApiServer }}}
To stop the server, kill its process.


   Advanced use: a theorical explanation of S-Match can be found at http://semanticmatching.org/s-match.html.

== Troubleshooting ==


== FAQ ==
frequently asked questions
   1. How can I change the Port of the S-Match Web API server?
 
This is done in the file smatch-webapi-model.properties, where the value of the port can be specified. The default value is 9090.

== Contacts ==
Moaz Reyad: reyad @ disi.unitn.it		