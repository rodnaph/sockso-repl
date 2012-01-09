
(ns sockso-repl.api
    (:use [cheshire.core])
    (:require [sockso-repl.server :as server])
    (:require [clojure.java.io :as io]))

(defn- get-json
    "Fetch json from the configured server, specify resource and args"
    [resource & args]
    (let [url (format "http://%s%s"
              (server/prop-get :host)
              (apply format resource args))]
        (parse-stream (io/make-reader url {}) true)))

(defn- parse-search-result
    "Search results have a different ID format"
    [item]
    (let [re #"(\w\w)(\d+)"
          [type id] (rest (re-matches re (item :id)))]
        (assoc item :id id
                    :type type)))

(defn sockso-api
    "Fetch JSON from the API"
    [resource & args]
    (get-json (str "/api" resource) args))

(defn api-search
    "Search sockso, returns all kinds of music items"
    [query]
    (map parse-search-result
        (get-json (format "/json/search/%s" query))))

