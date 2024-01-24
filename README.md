pega-proof-of-concept-stubs
================================

Stub microservice proof of concept for future PEGA work.

## Public API

| Path                                                                     | Description                    |
|--------------------------------------------------------------------------|--------------------------------|
| [POST /pega-proof-of-concept-stubs/submit-payload](#post-submit-payload) | Endpoint to submit a string to |

## POST /submit-payload
Endpoint to submit a string to, designed to simulate pega.

**Example request**

```json
{
    "data": "exampleString"
}
```

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").