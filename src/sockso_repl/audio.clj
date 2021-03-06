
(ns sockso-repl.audio
    (:use [sockso-repl.m3u])
    (:require [sockso-repl.urls :as urls])
    (:require [sockso-repl.server :as server]))

(defn- cmd 
    "Execute a command and block until it returns"
    [p] 
    (.. Runtime 
        getRuntime 
        (exec (str p))
        (waitFor))) 

(defn play-item
    "Play a music item map"
    [item]
    (println (format "\nPlay: %s" (item :name)))
    (cmd (format "mpg123 %s" (item :url))))

(defn play-url
    "Play the music specified by the ID (eg. al123, tr456, etc...)"
    [url]
    (play-item {:name url :url url}))

