when HTTP_REQUEST {
    if { [HTTP::uri] starts_with "/apigw/mapi/" and [HTTP::uri] ends_with ".do" } {
        pool_1
    } else {
        pool_2
    }
}
