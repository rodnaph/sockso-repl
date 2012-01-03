
(ns sockso-repl.urls)

(defn- url-read [^java.io.BufferedReader in]
    (loop [data ""]
        (let [line (.readLine in)]
            (if (nil? line)
                data
                (recur (str data line))))))

(defn url-get [url]
    (let [url (java.net.URL. url)
          stream (java.io.InputStreamReader. (.openStream url))
          in (java.io.BufferedReader. stream)]
        (println (format "View url: %s" url))
        (println "Data: " (url-read in))))

