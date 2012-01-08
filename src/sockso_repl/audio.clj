
(ns sockso-repl.audio
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
    (cmd (format "mpg123 %s" url)))

(defn play-id
    "Play some music by ID"
    [id]
    (play-url (format "http://%s/stream/%s" 
        (server/prop-get :host) id)))

