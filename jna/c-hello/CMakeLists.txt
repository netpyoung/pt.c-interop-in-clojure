cmake_minimum_required(VERSION 3.10)

project (hello)

set (CMAKE_BUILD_TYPE Debug)

include_directories (include ${PROJECT_BINARY_DIR})
file (GLOB SOURCES "src/*.c")

add_library(hello SHARED ${SOURCES})
