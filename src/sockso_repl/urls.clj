
(ns sockso-repl.urls
    (:use [clojure.java.io :as io]))

(defn url-get [url]
    (loop [in (io/make-reader url {})
           data ""]
        (let [line (.readLine in)]
            (if (nil? line)
                data
                (recur in (str data line))))))

