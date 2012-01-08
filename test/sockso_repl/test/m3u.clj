
(ns sockso-repl.test.m3u
    (:use [sockso-repl.m3u])
    (:use [clojure.test]))

(deftest test-parsing-m3u-file
    (let [data (m3u-parse-file "test/playlist.m3u")]
        (is (= (count data) 2))
    ))

