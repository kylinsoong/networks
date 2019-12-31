/**
 *  * Author: Kylin Soong(kylinsoong.1214@gmail.com)
 *   * Date  : 2019-12-31
 *    */

#include "neth.h"

void err_sys(const char *fmt, ...)
{
    va_list ap;

    va_start(ap, fmt);
    printf("%s\n", fmt);
    va_end(ap);

    exit(1);
}
