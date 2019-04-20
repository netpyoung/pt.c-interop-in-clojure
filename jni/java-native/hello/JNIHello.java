package hello;

public class JNIHello {
    static {
        System.loadLibrary("hello_jni");
    }
    public static native int Hello(int a, int b);
}
