    #include <stdio.h>
    #include <stddef.h>
    #include <time.h>
    int main(void)
    {
    time_t timer;//time_t&#23601;&#26159;long int &#31867;&#22411;
    printf("%d\n",sizeof(time_t));
    struct tm *tblock;
    timer = time(NULL);//&#36825;&#19968;&#21477;&#20063;&#21487;&#20197;&#25913;&#25104;time(&timer);
    tblock = localtime(&timer);
    printf("Local time is: %s\n",asctime(tblock));
    return 0;
    } 
