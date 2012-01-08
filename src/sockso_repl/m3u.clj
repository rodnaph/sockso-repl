
(ns sockso-repl.m3u
    (:import (java.io BufferedReader FileReader))
    (:require [sockso-repl.urls :as urls])
    (:require [clojure.string :as string]))

(defn- m3u-parse-info
    "Create a map from the info and URL lines"
    [info url]
    {:url url
     :info (string/join "," (rest (string/split info #",")))})

(defn- m3u-parse
    "Parse some m3u data"
    [data]
    (loop [lines (rest data) items '()]
        (if (empty? lines)
            (reverse items)
            (let [info (first lines)
                  url (second lines)]
                (recur (drop 2 lines)
                       (if (and info url)
                           (cons (m3u-parse-info info url) items)
                           items))))))

(defn m3u-parse-url
    "Parse the m3u data from a URL"
    [url]
    (m3u-parse (urls/url-get-lines url)))

(defn m3u-parse-file
    "Read an m3u file and return as a collection"
    [path]
    (let [in (BufferedReader. (FileReader. path))]
        (m3u-parse (line-seq in))))

