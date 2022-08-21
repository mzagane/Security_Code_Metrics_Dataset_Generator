#include <stdio.h>

#define x 10      

int main()
{
    #ifdef x
      printf("hello\n");      // this is compiled as  x is defined
   #else
      printf("bye\n");       // this isn't compiled
   #endif
   
   return 0;
}
