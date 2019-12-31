#include <stdio.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <string.h>
#include <errno.h>

#define SA struct sockaddr

#define SERV_PORT   8877    /* TCP and UDP */
#define LISTENQ     1024    /* 2nd argument to listen() */
#define MAXLINE     4096    /* max text line length */

int      Socket(int, int, int);
int      Accept(int, SA *, socklen_t *);

void     Bind(int, const SA *, socklen_t);
void     Listen(int, int);
void     Close(int);
void     Str_echo(int);
void     Writen(int, void *, size_t);
void     err_sys(const char *);

pid_t    Fork(void);

ssize_t  writen(int, const void *, size_t);

/**
 * Author: Kylin Soong(kylinsoong.1214@gmail.com)
 *
 * TCPServer
 */

int main(int argc, char **argv)
{
    int                listenfd, connfd;
    pid_t              childpid;
    socklen_t          clilen;
    struct sockaddr_in cliaddr, servaddr;

    listenfd = Socket(AF_INET, SOCK_STREAM, 0);

    memset(&servaddr, 0, sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
    servaddr.sin_port = htons(SERV_PORT);

    Bind(listenfd, (SA *) &servaddr, sizeof(servaddr)); 

    Listen(listenfd, LISTENQ);

    for(;;) {

        clilen = sizeof(cliaddr);
        connfd = Accept(listenfd, (SA *) &cliaddr, &clilen);

        if ((childpid = Fork()) == 0) {
            Close(listenfd);
            Str_echo(connfd); 
            exit(0);
        }
    
        Close(connfd);
    }

    return 1;
}

int Socket(int family, int type, int protocol)
{
    int listen_sock;
    if ( (listen_sock = socket(family, type, protocol)) < 0) {
        printf("socket error\n");
        exit(1);
    }
    return (listen_sock);
}

void Bind(int fd, const struct sockaddr *sa, socklen_t salen)
{
    if (bind(fd, sa, salen) < 0) 
        err_sys("bind error");
}

void Listen(int fd, int backlog)
{
    char *ptr;

    if ((ptr = getenv("LISTENQ")) != NULL) { /*4can override 2nd argument with environment variable */
        backlog = atoi(ptr);
    }

    if (listen(fd, backlog) < 0) 
        err_sys("listen error");
}

int Accept(int fd, struct sockaddr *sa, socklen_t *salenptr)
{
    int sock;

    if ((sock = accept(fd, sa, salenptr)) < 0) 
        err_sys("accept error");

    return (sock);
}

pid_t Fork(void)
{
    pid_t pid;

    if ((pid = fork()) == -1)
        err_sys("fork error");
    return (pid);
}

void Close(int fd)
{
    if (close(fd) == -1) 
        err_sys("close error");
}

void Str_echo(int sockfd)
{
    ssize_t n;
    char    buf[MAXLINE];
    
again:
    while ( (n = read(sockfd, buf, MAXLINE)) > 0) {
        Writen(sockfd, buf, n);
    }
}

void Writen(int fd, void *ptr, size_t nbytes){

    if (writen(fd, ptr, nbytes) != nbytes)
        err_sys("writen error");
}

ssize_t writen(int fd, const void *vptr, size_t n) 
{
    size_t     nleft;
    ssize_t    nwritten;
    const char *ptr;

    ptr = vptr;
    nleft = n;

    while (nleft > 0) {
        if ((nwritten = write(fd, ptr, nleft)) <= 0) {
            if (nwritten < 0 && errno == EINTR)
                nwritten = 0; 
            else
                return (-1);
        }
        nleft -= nwritten;
        ptr   += nwritten;
    }

    return (n);
}

void err_sys(const char *fmt) 
{
    printf("%s\n", fmt);
    exit(1);
}
