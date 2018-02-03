var express = require('express');
var badServer = express();
var goodServer = express();
var proxy = express();
var request = require('request');

var loadBalance = true; // lul
var baseUrl = 'http://localhost:';

// Proxy
var proxyPort = 3001;

// socket hang up means you did not get a timely response
proxy.get('/', function (proxyReq, proxyRes) {
    console.log('Proxy server :' + proxyPort + ' has received a request. It will now forward it to the back-end server.');

    loadBalance = !loadBalance;

    var url = loadBalance ?  baseUrl + badServerPort : baseUrl + goodServerPort;

    request.get(url)
        .on('error', function () {
            console.log("Error due to the bad server")
        })
        .pipe(proxyRes);
});

proxy.listen(proxyPort);
console.log('Proxy listening on localhost:' + proxyPort);

// ---

// Server(s)
var badServerPort = 3002;
var goodServerPort = 3003;

// this server will eventually send an empty response after ~4 minutes
badServer.get('/', function (req, res) {
    // empty response
    console.log('Back-end server :' + badServerPort + ' has received a request. It will now do nothing and send an empty response.');
});

badServer.listen(badServerPort);
console.log('Server listening on localhost:' + badServerPort);

goodServer.get('/', function (req, res) {
    console.log('Back-end server :' + goodServerPort + ' has received a request. It will respond after 5 seconds');
    setTimeout(function() {
        res.write("Hello world");
        res.end();
        console.log("Response should be sent");
    }, 5 * 1000);
});

goodServer.listen(goodServerPort);
console.log('Server listening on localhost:' + goodServerPort);