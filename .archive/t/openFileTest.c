#include <stdio.h>
    #include <stdlib.h>
    #include <sys/types.h>
    #include <sys/stat.h>
    #include <fcntl.h>
    #include <errno.h> 

int main(int argc, char *argv[])
{

 if(argc != 2)
    {
        printf("\n Usage: %s <open file number> \n",argv[0]);
        return 1;
    }

int num = atoi(argv[1]);

printf("Open %d files start\n", num);
int fd, i;
            char filename[20] = "";

            for (i = 0; i < num; i++){
                    sprintf(filename, "test-%d", i);
                    fd = open(filename, O_RDONLY|O_CREAT, 0644);
                    if(fd > 0)
                            printf("%d\n", i);
                    else {
                            perror("File_Descriper_Test:");
                            break;
                    }
            }
            sleep(10000);
            exit(0);

}
