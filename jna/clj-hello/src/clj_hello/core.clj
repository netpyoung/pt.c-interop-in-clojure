(ns clj-hello.core
  (:gen-class)
  (:import
   [com.sun.jna Native]))

(gen-interface
  :name jna.X
  :extends [com.sun.jna.Library]
  :methods
  [[hello [int int] int]])

(defonce +JNAHello+
  (Native/loadLibrary "hello" jna.X))

(defn hello [a b]
  (.hello +JNAHello+ a b))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!" (hello 10 20)))
