(ns clj-hello.core
  (:gen-class)
  (:import
   [wrapper.hello hello_lib]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!" (hello_lib/hello 10 20)))
