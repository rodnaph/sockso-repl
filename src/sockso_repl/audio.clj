
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

(defn play-url
    "Play the music specified by the ID (eg. al123, tr456, etc...)"
    [url]
    (println (format "Play: %s" url))
    (cmd (format "mpg123 %s" url)))

(defn play-id
    "Play some music by ID"
    [id]
    (let [m3u (server/url "/m3u/%s" id)]
        (loop [urls (m3u-parse-url m3u)]
            (if (not (empty? urls))
                (do (play-url (first urls))
                    (recur (rest urls)))))))

