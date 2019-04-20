cmake_minimum_required(VERSION 3.10)

project (hello_jni)

set (CMAKE_BUILD_TYPE Debug)

include_directories (include ${PROJECT_BINARY_DIR})
file (GLOB SOURCES "src/*.c")

# set(JAVA_AWT_LIBRARY NotNeeded)
# set(JAVA_JVM_LIBRARY NotNeeded)
# set(JAVA_INCLUDE_PATH2 NotNeeded)
set(JAVA_AWT_INCLUDE_PATH NotNeeded)
find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

add_library(hello_jni SHARED ${SOURCES})

if (MSVC)
    target_link_libraries(hello_jni "../c-hello/Debug/hello")
endif (MSVC)

if (UNIX)
   target_link_libraries(hello_jni "-L../c-hello" hello)
endif (UNIX)
