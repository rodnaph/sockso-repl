
(ns sockso-repl.api
    (:use [cheshire.core])
    (:require [sockso-repl.server :as server])
    (:require [clojure.java.io :as io])
    (:require [clojure.string :as string]))

(defn- get-reader
    "Return a reader for a URL resource"
    [resource & args]
    (io/make-reader (format "http://%s%s" (server/prop-get :host) 
                                          (apply format resource args)) {}))

(defn- get-json
    "Fetch json from the configured server, specify resource and args"
    [resource & args]
    (parse-stream (get-reader resource args) true))

(defn- parse-search-result
    "Search results have a different ID format"
    [item]
    (let [re #"(\w\w)(\d+)"
          [type id] (rest (re-matches re (item :id)))]
        (assoc item :id id
                    :type type)))

; public methods

(defn sockso-api
    "Fetch JSON from the API"
    [resource & args]
    (get-json (str "/api" resource) args))

(defn sockso-search
    "Search sockso, returns all kinds of music items"
    [query]
    (map parse-search-result
        (get-json (format "/json/search/%s" query))))

(defn sockso-playlist
    "Return a playlist as a lazyseq of lines"
    ([url] (sockso-playlist url "m3u"))
    ([url type]
        (let [url (format "/%s/%s" type url)]
            (line-seq (get-reader url)))))

