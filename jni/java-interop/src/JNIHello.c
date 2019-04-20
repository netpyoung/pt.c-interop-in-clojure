#include "hello_JNIHello.h"
#include "hello.h"

JNIEXPORT jint JNICALL Java_hello_JNIHello_Hello(JNIEnv* env, jclass obj, jint a, jint b)
{
	return (int)hello(a, b);
}
