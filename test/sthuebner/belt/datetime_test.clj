(ns sthuebner.belt.datetime-test
  (:use midje.sweet)
  (:require [sthuebner.belt.datetime :as d])
  (:import [java.util Date TimeZone]))

(def default-pattern "yyyy-MM-dd HH:mm:ss Z")


(fact "creating date formats"
  (.toPattern (d/date-format default-pattern)) => default-pattern
  (.getTimeZone (d/date-format default-pattern)) => (TimeZone/getDefault)
  (.getTimeZone (d/date-format default-pattern :timezone "UTC")) => (TimeZone/getTimeZone "UTC")
  (.getTimeZone (d/date-format default-pattern :timezone "Europe/Tallinn")) => (TimeZone/getTimeZone "Europe/Tallinn"))


(fact "parsing"
  (d/parse "1970-01-01 00:00:00 +0000" default-pattern) => (Date. 0)
  (d/parse "1970-01-01 00:00:00 +0000" (d/date-format default-pattern)) => (Date. 0)

  (let [parse-iso (d/parser default-pattern)]
    (parse-iso "1970-01-01 00:00:00 +0000") => (Date. 0)))


(fact "formatting"
  (let [df (d/date-format default-pattern :timezone "UTC")]
    (d/format (Date. 0) df) => "1970-01-01 00:00:00 +0000"
    (d/format 0 df) => "1970-01-01 00:00:00 +0000"
    (d/format 0 default-pattern :timezone "UTC") => "1970-01-01 00:00:00 +0000"

    (let [format-iso (d/formatter df)]
      (format-iso (Date. 0)) => "1970-01-01 00:00:00 +0000")))



(fact "instantiating dates"
  (d/date 0) => (Date. 0)
  (d/date (Date. 0)) => (Date. 0))


(future-fact "date and time"
             (day (date)) => nil?)
