(ns clj-hello.playground
  (:import
   [wrapper.hello hello_h$TestEnum]
   [wrapper.hello hello_h$TestUnion]
   [wrapper.hello hello_h$AStruct hello_h$BStruct]
   [wrapper.hello hello_lib]
   [java.foreign Scope]
   [java.foreign.memory Pointer LayoutType Struct]
   [java.foreign NativeTypes]
   [java.foreign Libraries]
   [java.lang.invoke MethodHandles]))

;; Panama -

;;  Jextract
;; * Goal: auto-generate bundles of annotated interfaces from a C header file.
;;   - The generated jar bundle contains headers, structs, callbacks interfaces
;; * Jextract parses headers (clang), infers layouts, picks Java carrier types
;;   – The generated bundle is platform dependent

;; ========================================
;; Ref.
;; ========================================
;; API: https://download.java.net/java/early_access/panama/docs/api/
;; example: https://hg.openjdk.java.net/panama/dev/raw-file/4810a7de75cb/doc/panama_foreign.html
;; pdf: https://www.jcp.org/aboutJava/communityprocess/ec-public/materials/2019-03-12/Project_Panama_Status_Update_March_2019.pdf

;; ===============================
;; Demo. Scope
;; ===============================
(def scope (.fork (hello_lib/scope)))
(def cstring (.allocateCString scope "helloworld"))
(def x (Pointer/toString cstring))
(.close scope)
(def y (Pointer/toString cstring))
;;-> Scope is not alive

(hello_lib/inc_chararr cstring)


(def arr
  (.allocateArray
    scope
    NativeTypes/DOUBLE
    (into-array Double/TYPE [-10, 12, 14, 16, 18, -3, 14, 12, 16, 16])))

(.get arr 0)
;;=> -10.0
(.set arr 0 1)
;;=> nil

(.get arr 0)
;;=> 1.0

(.length arr)
;;=> 10

(->> arr
     ;; https://download.java.net/java/early_access/panama/docs/api/java.base/java/util/stream/Stream.html
     (.iterate)
     (.toArray)
     (map #(.get %)))
;;=> (1.0 12.0 14.0 16.0 18.0 -3.0 14.0 12.0 16.0 16.0)


(def null (Pointer/ofNull))

;; ===============================
;; Demo. LayoutType
;; ===============================
(def layouttype-a (LayoutType/ofStruct hello_h$AStruct))
(def layouttype-b (LayoutType/ofStruct hello_h$BStruct))
(.toString layouttype-b)
;;=> "LayoutTypeImpl{carrier=interface wrapper.hello.hello_h$BStruct, layout=[i32(type)i32(b1)i32(b2)](BStruct)}"
(def layout-b (.layout layouttype-b))
(.bitsSize layout-b)
;;=> 96

(Struct/sizeOf hello_h$BStruct)
;;=> 12
(Struct/sizeOf hello_h$AStruct)
;;=> 8
(Struct/sizeOf hello_h$TestUnion)
;;=> 8


(def a (hello_lib/test_malloc 1))
(def instance-a (.allocate scope layouttype-a))
(def buffer-a (.asDirectByteBuffer a 12))
(def buffer-instance-a (.asDirectByteBuffer instance-a 12))
(.put buffer-instance-a buffer-a)
;;=> #object[java.nio.DirectByteBuffer 0x4189b11a "java.nio.DirectByteBuffer[pos=12 lim=12 cap=12]"]
(.type$get (.get a))
;;=> 1
(.type$get (.get instance-a))
;;=> 1
(.a1$get (.get instance-a))
;;=> 11


(def b (hello_lib/test_malloc 2))
(def instance-b (.allocate scope layouttype-b))
(def buffer-b (.asDirectByteBuffer b 12))
(def buffer-instance-b (.asDirectByteBuffer instance-b 12))
(.put buffer-instance-b buffer-b)
(= hello_lib/B (.type$get (.get b)))
(.type$get (.get instance-b))
(.b1$get (.get instance-b))
;;=> 21
(.b2$get (.get instance-b))
;;=> 22
(.b1$set (.get instance-b) 99)
;;=> nil
(.b1$get (.get instance-b))
;;=> 99


(def lt-p (.pointer layouttype-b))
(def bp (.allocate scope lt-p))
(.set bp b)
(.b1$get (.get (.get bp)))


;; Libraries / MethodHandles
(def lib (Libraries/bind
           (MethodHandles/lookup)
           hello_lib))
;; _lib.getpid();
