# Empty stream

This project demonstrates what happens when there are two servers behind a reverse proxy and one server fails to send a response.

In Chrome, you will see an ERR_EMPTY_RESPONSE before being sent the actual webpage due to Chrome magic.

Running the Java test, however, will cause the stream to error and no update will be received.

This project runs three express servers:

1. localhost:3001 is the load balancer / reverse proxy. <- Call this one in your browser / tests
2. localhost:3002 is the misbehaving server that never returns a response.
3. localhost:3003 is our working server that returns some content.

## Download Code

`git clone `

## Install Project Dependencies

`npm install`

## Start Server

`cd empty-stream\express-reverse-proxy`

`node index.js`

## Run the Demo JUnit test

From root directory:

`gradlew test`

Alternatively, open in IDE and run the JUnit test there

## Test In Chrome

Also open Chrome and navigate to localhost:3001

The first page load will work succesfully. If you refresh, it will spin, return an empty ERR_EMPTY_RESPONSE message after 4 minutes, and then automatically try again and "load balance" to the correct site.

This happens because Chrome will re-query a site when it finds the ERR_EMPTY_RESPONSE message.
