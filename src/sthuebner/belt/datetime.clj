(ns sthuebner.belt.datetime
  (:refer-clojure :exclude [format])
  (:import [java.text SimpleDateFormat]
           [java.util Date]))

;;;; ===================================
;;;; Parsers and Formatters

(defmulti date-format class)

(defmethod date-format SimpleDateFormat
  [this]
  this)

(defmethod date-format String
  [^String s]
  (SimpleDateFormat. s))


(defn parse [s format]
  (.parse (date-format format) s))

(defn make-parser [format]
  (let [format (date-format format)]
    (fn [^String s]
      (parse s format))))


(defn format [d format]
  (.format (date-format format) d))

(defn make-formatter [formaat]
  (let [formaat (date-format formaat)]
    (fn [d]
      (format d formaat))))


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


