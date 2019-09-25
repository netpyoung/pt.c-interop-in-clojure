#include <stdlib.h>
#include <stdio.h>
#include "hello.h"

int hello(int a, int b)
{
        return a + b;
}

TestUnion* test_malloc(int t)
{
        TestUnion* ptr = (TestUnion*)malloc(SIZE);
        switch (t)
        {
        case 1:
        {
                AStruct* ret = (AStruct*)ptr;
                ret->type = A;
                ret->a1 = 11;
                return (TestUnion*)ret;
        }
        case 2:
        {
                BStruct* ret = (BStruct*)ptr;
                ret->type = B;
                ret->b1 = 21;
                ret->b2 = 22;
                return (TestUnion*)ret;
        }
        default:
        {
                ptr->type = NONE;
                return ptr;
        }
        }
}

void test_free(TestUnion* u)
{
        free(u);
}

char* inc_chararr(char* message)
{
        char* c;
        for (c = message; *c != '\0'; ++c)
        {
                printf("%c\n", *c);
                *c = *c + 1;
        }
        return message;
}
