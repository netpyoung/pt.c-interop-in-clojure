(ns clj-hello.core
  (:gen-class)
  (:import [hello JNIHello]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"
           (JNIHello/Hello 10 20)))
