
(ns sockso-repl.m3u
    (:import (java.io BufferedReader FileReader))
    (:require [sockso-repl.urls :as urls]))

(defn- m3u-parse
    "Parse some m3u data"
    [lines]
    (filter #(re-matches #"^[^#].*" %) lines))

(defn m3u-parse-url
    "Parse the m3u data from a URL"
    [url]
    (m3u-parse (urls/url-get-lines url)))

(defn m3u-parse-file
    "Read an m3u file and return as a collection"
    [path]
    (let [in (BufferedReader. (FileReader. path))]
        (m3u-parse (line-seq in))))

