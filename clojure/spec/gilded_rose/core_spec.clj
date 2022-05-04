(ns gilded-rose.core-spec
(:require [clojure.test :refer :all]
          [gilded-rose.core :refer [update-quality item]]))

(deftest common-item-keeps-name-test
  (is (= "foo" (:name (first (update-quality [(item "foo" 0 0)]))))))

(deftest common-item-decreases-quality-in-one-test
  (is (= 7 (:quality (first (update-quality [(item "foo" 10 8)]))))))