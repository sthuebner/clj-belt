(ns sthuebner.belt.datetime-test
  (:use midje.sweet)
  (:require [sthuebner.belt.datetime :as d])
  (:import [java.util Date]))


(fact "parsing"
  (d/parse "1970-01-01 00:00:00 +0000" "yyyy-MM-dd HH:mm:ss Z") => (Date. 0)
  (d/parse "1970-01-01 00:00:00 +0000" (d/date-format "yyyy-MM-dd HH:mm:ss Z")) => (Date. 0)

  (let [parse-iso (d/make-parser "yyyy-MM-dd HH:mm:ss Z")]
    (parse-iso "1970-01-01 00:00:00 +0000") => (Date. 0)))


(fact "formatting"
  (let [df (doto (d/date-format "yyyy-MM-dd HH:mm:ss Z")
             (.setTimeZone (java.util.TimeZone/getTimeZone "UTC")))]
    (d/format (Date. 0) df) => "1970-01-01 00:00:00 +0000"
    (d/format 0 df) => "1970-01-01 00:00:00 +0000"

    (let [format-iso (d/make-formatter df)]
      (format-iso (Date. 0)) => "1970-01-01 00:00:00 +0000")))



(fact "instantiating dates"
  (d/date 0) => (Date. 0)
  (d/date (Date. 0)) => (Date. 0))


(future-fact "date and time"
             (day (date)) => nil?)
