/**
 * Author: Kylin Soong(kylinsoong.1214@gmail.com)
 * Date  : 2019-12-31
 */

#include "neth.h"

void Str_echo(int sockfd)
{
    ssize_t n;
    char    buf[MAXLINE];
    
again:
    while ( (n = read(sockfd, buf, MAXLINE)) > 0) {
        Writen(sockfd, buf, n);

        if (n < 0 && errno == EINTR)
            goto again;
        else if (n < 0)
            err_sys("str_echo: read error");
    }
}


