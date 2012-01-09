
(ns sockso-repl.m3u
    (:import (java.io BufferedReader FileReader))
    (:require [sockso-repl.urls :as urls])
    (:require [clojure.string :as string]))

(defn- parse-track
    "Parse a pair of track info lines from m3u"
    [[info url]]
    {:url url
     :id (second (re-matches #".*stream/(\d+).*" url))
     :type "tr"
     :name (string/join "," (rest (string/split info #",")))})

(defn- track-seq
    "Creates a sequence of pairs of lines for track info. Input should be all lines of m3u"
    [lines]
    (partition 2 (rest lines)))

(defn- m3u-parse
    "Parse some m3u data"
    [lines]
    (map parse-track (track-seq lines)))

(defn m3u-parse-url
    "Parse the m3u data from a URL"
    [url]
    (m3u-parse (urls/url-get-lines url)))

(defn m3u-parse-file
    "Read an m3u file and return as a collection"
    [path]
    (let [in (BufferedReader. (FileReader. path))]
        (m3u-parse (line-seq in))))

