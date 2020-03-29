var http = require('http');
var url = require('url');
var fs = require('fs');
var f5 = require('f5-nodejs');

function customResHeaders(ifGet) {

  var headers = JSON.parse(fs.readFileSync('additional-response-headers.json', 'utf8'));

  var contentType = 'Content-Type';

  if(ifGet){
    headers[contentType] = 'text/html';
  } else {
    headers[contentType] = 'application/json';
  }

  return headers;
}

function randomBody() {
  return '<body>This is body content</body>';
}

function httpRequest(req, res)
{
  var contents = {};

  // headers
  const { headers } = req;
  contents['request-headers'] = headers;

  // body
  let body = [];
  req.on('data', (chunk) => {
    body.push(chunk);
  }).on('end', () => {
    body = Buffer.concat(body).toString();
  });
  contents['request-body'] = body;
  
  // httpVersion
  contents['httpVersion'] = req.httpVersion;

  // method
  contents['method'] = req.method;

  // url
  contents['rawUrl'] = req.url;
  var parse = url.parse(req.url, true);
  var urlSplits = {};
  urlSplits['host'] = parse.host;
  urlSplits['pathname'] = parse.pathname;
  urlSplits['search'] = parse.search;
  contents['url'] = urlSplits;
  
  //console.log(req.socket);
  var contentsJSON = JSON.stringify(contents);
  console.log(contentsJSON);

  if(req.method == 'GET') {
    fs.readFile('template.html', function(err, data) {
      if (err) {
        res.writeHead(200, customResHeaders(true));
        res.write(randomBody(), "ascii");
        res.end("\n", "ascii");
      } else {
        res.writeHead(200, customResHeaders(true));
        res.write(data);
        res.end();
      }
    });
  } else {
    res.writeHead(200, customResHeaders(false));
    res.end('{status: success}\n', "ascii");
  }
}

var plugin = new f5.ILXPlugin();
plugin.startHttpServer(httpRequest);






