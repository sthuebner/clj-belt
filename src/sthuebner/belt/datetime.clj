(ns sthuebner.belt.datetime
  (:refer-clojure :exclude [format])
  (:import [java.text SimpleDateFormat]
           [java.util Date TimeZone]))

;;;; ===================================
;;;; Parsers and Formatters

(defmulti date-format (fn [o & opts] (class o)))

(defmethod date-format SimpleDateFormat
  [^SimpleDateFormat o & {:keys [timezone]}]
  (let [sdf (.clone o)]
    (if timezone
      (.setTimeZone sdf (TimeZone/getTimeZone timezone)))
    sdf))

(defmethod date-format String
  [^String s & opts]
  (apply date-format (SimpleDateFormat. s) opts))


(defn parser [format & opts]
  (let [format (apply date-format format opts)]
    (fn [s]
      (.parse format s))))

(defn formatter [format & opts]
  (let [format (apply date-format format opts)]
    (fn [d]
      (.format format d))))


(defn parse [s format & opts]
  ((apply parser format opts) s))

(defn format [d format & opts]
  ((apply formatter format opts) d))


;;;; ===================================
;;;; Converting between different date types

(defmulti to-ms class)

(defmethod to-ms Long
  [n] n)

(defmethod to-ms java.util.Date
  [o]
  (.getTime o))

(defmethod to-ms org.joda.time.base.AbstractInstant
  [o]
  (.getMillis o))


(defn local-date
  [x]
  (org.joda.time.LocalDate. (to-ms x)))


(defn date
  ([] (Date.))
  ([o]
     (Date. (to-ms o))))
