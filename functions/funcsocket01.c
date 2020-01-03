#include <stdio.h>
#include <sys/socket.h>
#include <stdlib.h>

int main (int argc, char **argv)
{
    int n;

    for(n = 0 ; n < 1024 ; n ++) {
        printf("%d - socket descriptor: %d\n", n, socket(AF_INET, SOCK_STREAM, 0));
    }
}
