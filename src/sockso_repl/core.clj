
(ns sockso-repl.core
    (:gen-class)
    (:require [sockso-repl.urls :as urls])
    (:require [sockso-repl.server :as server])
    (:require [sockso-repl.print :as print])
    (:require [sockso-repl.player :as player])
    (:require [clojure.string :as string]))

(defmulti command :name)

(defmacro defcommand [name comment args & body]
    `(defmethod command ~name
        [{args# :args}]
            (let [{:keys [~@args]} args#] 
                (do ~@body))))

(defcommand :connect
    "Connect to a new server"
    [host]
    (server/prop-set :host host)
    (print/server host))

(defcommand :server
    "Show current server information"
    []
    (print/server (server/prop-get :host)))

(defcommand :search
    "Search the current server"
    [query]
    (let [url (server/url "/json/search/%s" query)]
        (print/search-results (urls/url-get-json url))))

(defcommand :help
    "Print a help information"
    []
    (print/help))

(defcommand :play
    "Play the specified music item"
    [id]
    (player/player-add-id id))

(defcommand :artists
    "Display artists"
    []
    (let [url (server/url "/api/artists?limit=-1")]
        (print/music-items (urls/url-get-json url))))

(defcommand :artist
    "Display an individual artist"
    [id]
    (print/artist (urls/url-get-json 
        (server/url "/api/artists/%s" (.substring id 2)))))

(defcommand :playlist
    "Show the current playlist"
    []
    (print/playlist (player/player-playlist)))

(defcommand :playlist-clear
    "Clears the playlist"
    []
    (player/player-playlist-clear))

(defcommand :exit
    "Exit the REPL"
    []
    false)

(defcommand :default
    "Print error on known command"
    []
    (print/error "Unknown command"))

(defn parse-command-arg
    "Parse a command arg pair into a map with :name and :value keys"
    [pair]
    (let [[name value] (string/split pair #"=")]
        (hash-map (keyword name) value)))

(defn parse-command-args
    "Parses name/value argument pairs into a map" 
    [lst]
    (reduce #(merge %1 (parse-command-arg %2)) {} lst))

(defn parse-command-str
    "Parse command string into map of name and arguments"
    [cmd]
    (let [[name & args] (string/split cmd #"\s+")]
        {:name (keyword name)
         :args (parse-command-args args)}))

(defn run-repl
    "Read commands and execute them"
    []
    (print/welcome)
    (loop [] 
        (print/prompt)
        (let [line (read-line)]
            (if (> (.length line) 0)
                (command (parse-command-str line)))
        (recur))))

(defn -main[]
    (player/player-start)
    (run-repl))

