#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <string.h>

int main (int argc, char **argv)
{
    struct sockaddr_in servaddr;
    
    memset(&servaddr, 0, sizeof(servaddr));
    servaddr.sin_family = AF_INET; 
    servaddr.sin_port = htons(8877);
    inet_aton("192.168.1.100", &servaddr.sin_addr.s_addr);


    char ipAddress[INET_ADDRSTRLEN];
    inet_ntop(AF_INET, &(servaddr.sin_addr), ipAddress, INET_ADDRSTRLEN);

    //char *ip = inet_ntoa(&servaddr.sin_addr.s_addr);
    int port = ntohs(servaddr.sin_port);
    printf("%d:%d\n", &servaddr.sin_addr.s_addr, &servaddr.sin_port);
    printf("%s:%d\n", ipAddress, port);
}
