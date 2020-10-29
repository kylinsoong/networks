when HTTP_REQUEST {
    HTTP::header replace X-Forwarded-For "10.1.1.10, [IP::remote_addr]"
}
