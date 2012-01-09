
(ns sockso-repl.print
    (:require [clojure.string :as string]))

(defn error
    "Print an error message"
    [message]
    (println (format "ERROR: %s" message)))

(defn prompt
    "Prints the shell prompt"
    []
    (print "sockso=> ")
    (flush))

(defn welcome
    "Prints the welcome message"
    []
    (println "Welcome to the Sockso REPL")
    (println "Type 'help' for commands")
    (println ""))

(defn- format-music-item
    "Print a music item"
    [item & prefix]
    (format "#%s%s %s" 
        (if (empty? prefix) "" (first prefix))
        (get item "id")
        (get item "name")))

(defn music-items
    [lst]
    (doseq [item lst]
        (println (format-music-item item "ar"))))

(defn help
    "Prints help information"
    []
    (println "List of commands:")
    (println "")
    (println "\tconnect host=XXX")
    (println "\tserver")
    (println "\tsearch query=XXX")
    (println "\tartists")
    (println "\tplaylist")
    (println "\tplay id=XXX")
    (println "\texit")
    (println ""))

(defn server
    "Print server info"
    [host]
    (println (format "Server: %s" host)))

(defn search-results
    "Print a vector of search results"
    [results]
    (doseq [item results]
        (println (format-music-item item))))

(defn playlist
    "Print the current playlist"
    [playlist]
    (doseq [item playlist]
        (println (format "#tr%s %s" 
            (item :id)
            (item :name)))))

(defn artist-album
    "Print an artists album"
    [album]
    (println (format "\tAlbum: %s (#al%s)" 
        (get album "name")
        (get album "id"))))

(defn artist
    "Print an artists info"
    [artist]
    (println (format "Artist: %s (#ar%s)" 
        (get artist "name")
        (get artist "id")))
    (doseq [album (get artist "albums")]
        (artist-album album)))

