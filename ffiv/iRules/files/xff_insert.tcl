when HTTP_REQUEST {
    HTTP::header insert X-Forwarded-For [IP::remote_addr]
}
