(ns clj-hello.core
  (:gen-class)
  (:import
   [jnr.ffi LibraryLoader]))

(gen-interface
  :name jnr.X
  :methods
  [[hello [int int] int]])

(defonce +JNRHello+
  (-> jnr.X
      (LibraryLoader/create)
      (.load "hello")))

(defn hello [a b]
  (.hello +JNRHello+ a b))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!" (hello 10 20)))
