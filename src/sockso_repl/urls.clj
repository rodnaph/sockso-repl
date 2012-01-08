
(ns sockso-repl.urls
    (:use [cheshire.core])
    (:use [clojure.java.io :as io])
    (:require [clojure.string :as string]))

(defn url-get-lines
    "Fetch data from the specified URL"
    [url]
    (let [in (io/make-reader url {})]
        (line-seq in)))

(defn url-get
    "Fetch data from a URL"
    [url]
    (string/join (url-get-lines url)))

(defn url-get-json 
    "Fetch and parse JSON from the specified URL"
    [url]
    (parse-string (url-get url)))

