#define SIZE 3

void main()
{
    float x[SIZE];
    float *fp;
    int   i;
					/* initialize the array x         */
					/* use a "cast" to force i        */
					/* into the equivalent float      */
    for (i = 0; i < SIZE; i++)
	x[i] = 0.5*(float)i;
					/* print x                        */
}
