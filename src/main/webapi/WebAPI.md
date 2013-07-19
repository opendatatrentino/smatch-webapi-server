# Getting Started 


## Service description 

S-Match is a semantic matching framework which is used in the context of open data semantifiying pipeline to find the correspondence between two schemas. The schemas can be taken from tabular data or from a definition of type of entity. A table has a name and a list of columns and a type of entity has a name and list of attributes.

### Usage 

The s-match service has only one web service that can be called from any web client using JSON format in the input and receiving JSON format in the output.

## Authentication 

Currently no authentication is required.

## Common conventions 

The input Context is a simple tree of strings. The root of the tree is the name of the table or the type of entity, and the children of the tree are the columns of the table or the attribute names of the type of entity. Contexts are simple trees of one level only and if there is more than one level in the input, they will be ignored.

### Requests 

The URI pattern for an API call is:

<URL>:<port>/match

### Common Parameters 

No common parameter defined.

## Environments and Versions 

The service is based on a modified version of s-match.

# Methods 

----
## Match 
----
### Method desciption 
Matches two contexts and returns the correspondence between them.


### HTTP request method 

POST

### Input parameters 

||'''''Name'''''||'''''Type'''''||'''''Description'''''||'''''Mandatory/Optional'''''||
||Source||Context||The source context is a root node and a list of children||Mandatory||
||Target||Context||The target context is a root node and a list of children||Mandatory||

`
{
	"request": {
		"parameters": {
			"Source": {
				"neve luogo": ["codice",
				"nome",
				"nome breve",
				"quota",
				"latitudine",
				"longitudine"]
			},
			"Target": {
				"luogo": ["nome",
				"latitudine",
				"longitudine"]
			}
		}
	}
}
`

### Response syntax and format 
Gives the syntax of the output, for example:

The response is a json structure with the following syntax:
`
{
	"Correspondence": [{
		"source": "\\luogo",
		"target": "\\neve luogo",
		"relation": ">"
	},
	{
		"source": "\\luogo\\nome",
		"target": "\\neve luogo\\nome",
		"relation": ">"
	}]
}

`


### Usage scenarios 

'''Success:''' describe the scenario, e.g., there are results

Invocation URL example:

`
Webapi/match?json={"request": {"parameters": {"Source": {"neve luogo": ["codice","nome"]},"Target": {"luogo": ["nome","latitudine","longitudine"]}}}}
`

Response example:
 * HTTP code: 200
 * Payload:
`
   {
	"Correspondence": [{
		"source": "\\luogo",
		"target": "\\neve luogo",
		"relation": ">"
	},
	{
		"source": "\\luogo\\nome",
		"target": "\\neve luogo\\nome",
		"relation": ">"
	}]
}


`
