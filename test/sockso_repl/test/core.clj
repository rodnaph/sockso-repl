
(ns sockso-repl.test.core
  (:use [sockso-repl.core])
  (:use [clojure.test]))

(deftest test-parsing-a-command-into-map
    (let [cmd "connect 127.0.0.1:4444"]
        (is (= (parse-command-str cmd)
            {:name :connect :args ("127.0.0.1:4444")}
        ))
    ))

