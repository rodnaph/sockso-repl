
(ns sockso-repl.player
    (:use [sockso-repl.m3u])
    (:require [sockso-repl.audio :as audio])
    (:require [sockso-repl.server :as server]))

(def playlist (atom '()))

(defn- play-next-track
    "Try and play the next track in the queue"
    []
    (if (not (empty? @playlist))
        (let [item (first @playlist)]
            (swap! playlist (partial drop 1))
            (audio/play-item item))))

(defn player-add-urls
    "Add URLs to play"
    [urls]
    (swap! playlist concat urls))

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
    @playlist)

(defn player-playlist-clear
    "Clears the playlist"
    []
    (reset! playlist '()))

