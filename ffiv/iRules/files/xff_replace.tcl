when HTTP_REQUEST {
    HTTP::header replace X-Forwarded-For [IP::client_addr]
}
