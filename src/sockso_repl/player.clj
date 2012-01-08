
(ns sockso-repl.player
    (:use [sockso-repl.m3u])
    (:require [sockso-repl.audio :as audio])
    (:require [sockso-repl.server :as server]))

(def p (atom '()))

(defn- play-next-track
    "Try and play the next track in the queue"
    []
    (if (not (empty? @p))
        (let [item (first @p)]
            (swap! p (partial drop 1))
            (audio/play-item item))))

(defn player-add-urls
    "Add URLs to play"
    [urls]
    (swap! p concat urls))

(defn player-add-id
    "Play some music by ID"
    [id]
    (let [m3u (server/url "/m3u/%s" id)]
        (player-add-urls (m3u-parse-url m3u))))

(defn player-start
    "Start the player agent"
    []
    (future (loop []
             (Thread/sleep 1000)
             (play-next-track)
             (recur))))

(defn player-playlist
    "Returns the current playlist"
    []
    @p)

(defn player-playlist-clear
    "Clears the playlist"
    []
    (reset! p '()))

