
(ns sockso-repl.urls
    (:use [cheshire.core])
    (:use [clojure.java.io :as io]))

(defn url-get 
    "Fetch data from the specified URL"
    [url]
    (loop [in (io/make-reader url {})
           data ""]
        (let [line (.readLine in)]
            (if (nil? line)
                data
                (recur in (str data line))))))

(defn url-get-json 
    "Fetch and parse JSON from the specified URL"
    [url]
    (parse-string (url-get url)))

