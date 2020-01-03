#include <stdio.h>
#include <sys/socket.h>
#include <stdlib.h>

int main (int argc, char **argv)
{
    int sockfd;

    if ( (sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        printf("socket error");
        exit(1);
    }

    printf("socket descriptor: %d\n", sockfd);
}
