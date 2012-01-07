
(ns sockso-repl.print
    (:require [clojure.string :as string]))

(defn error
    "Print an error message"
    [message]
    (println (format "ERROR: %s" message)))

(defn prompt
    "Prints the shell prompt"
    []
    (print "=> ")
    (flush))

(defn welcome
    "Prints the welcome message"
    []
    (println "Welcome to the Sockso REPL")
    (println "Type 'help' for commands")
    (println ""))

(defn help
    "Prints help information"
    []
    (println "List of commands:")
    (println "")
    (println "\tconnect host=XXX")
    (println "\tserver")
    (println "\tsearch query=XXX")
    (println "\tplay id=XXX")
    (println "\texit")
    (println ""))

(defn server
    "Print server info"
    [host]
    (println (format "Server: %s" host)))

(defn format-search-result
    "Format a search result"
    [res]
    (format "#%s %s" 
        (get res "id")
        (get res "name")))

(defn search-results
    "Print a vector of search results"
    [results]
    (println (string/join "\n" (map format-search-result results))))

