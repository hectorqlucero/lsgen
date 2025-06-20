(ns sk.models.util
  (:require
   [noir.session :as session]
   [sk.migrations :refer [config]]
   [sk.models.crud :refer [db Query]]
   [clojure.core :refer [random-uuid]]))

(defn get-session-id []
  (try
    (if (session/get :user_id) (session/get :user_id) 0)
    (catch Exception e (.getMessage e))))

(defn user-level []
  (let [id   (get-session-id)
        type (if (nil? id)
               nil
               (:level (first (Query db ["select level from users where id = ?" id]))))]
    type))

(defn user-email []
  (let [id    (get-session-id)
        email (if (nil? id)
                nil
                (:username (first (Query db ["select username from users where id = ?" id]))))]
    email))

(defn user-name []
  (let [id (get-session-id)
        username (if (nil? id)
                   nil
                   (:name (first (Query db ["select CONCAT(firstname,' ',lastname) as name from users where id = ?" id]))))]
    username))

(defn seconds->string [seconds]
  (let [n seconds
        day (int (/ n (* 24 3600)))
        day-desc (if (= day 1) " day " " days ")

        n (mod n (* 24 3600))
        hour (int (/ n 3600))
        hour-desc (if (= hour 1) " hour " " hours ")

        n (mod n 3600)
        minutes (int (/ n 60))
        minutes-desc (if (= minutes 1) " minute " " minutes ")

        n (mod n 60)
        seconds (int n)
        seconds-desc (if (= seconds 1) " second " " seconds ")

        minutes-desc (str day day-desc hour hour-desc minutes minutes-desc)
        seconds-desc (str day day-desc hour hour-desc minutes minutes-desc seconds seconds-desc)]
    minutes-desc))

(defn image-link
  [image-name]
  (let [path (str (:path config) image-name "?" (random-uuid))
        img-link (str "<img src='" path "' alt='" image-name "' width=32 height=32>")]
    img-link))

(defn year-options
  [table date-field]
  (let [sql (str "SELECT DISTINCT YEAR(" date-field ") AS year FROM " table)]
    (Query db sql)))

(defn month-options
  [table date-field]
  (let [sql (str "SELECT DISTINCT MONTH(" date-field ") AS month, MONTHNAME(" date-field ") AS month_name FROM " table)]
    (Query db sql)))

(comment
  (month-options "billing" "bill_date")
  (year-options "billing" "bill_date")
  (seconds->string 90061))
