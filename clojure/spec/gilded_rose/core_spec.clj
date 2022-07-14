(ns gilded-rose.core-spec
(:require [clojure.test :refer :all]
          [gilded-rose.core :refer [update-quality item]]))

(deftest common-item-keeps-name-test
  (is (= "foo" (:name (first (update-quality [(item "foo" 0 0)]))))))

(deftest common-item-decreases-quality-in-one-test
  (is (= 7 (:quality (first (update-quality [(item "foo" 10 8)]))))))

(deftest common-item-decreases-sell-in-in-one-test
  (is (= 9 (:sell-in (first (update-quality [(item "foo" 10 8)]))))))

(deftest aged-brie-item-increases-sell-in-in-one-test
  (is (= 9 (:quality (first (update-quality [(item "Aged Brie" 10 8)]))))))

(deftest old-backstage-passes-increases-quality-twice-if-sell-in-minor-than-5-test
  (is (= 11 (:quality (first (update-quality [(item "Backstage passes to a TAFKAL80ETC concert" 3 8)]))))))

(deftest old-backstage-passes-increases-quality-twice-if-sell-in-minor-than-or-equal-10-test
  (is (= 10 (:quality (first (update-quality [(item "Backstage passes to a TAFKAL80ETC concert" 10 8)]))))))

(deftest old-backstage-passes-increases-quality-once-if-sell-greater-than-11-test
  (is (= 9 (:quality (first (update-quality [(item "Backstage passes to a TAFKAL80ETC concert" 11 8)]))))))

(deftest sulfuras-never-change-quality
  (is (= 8 (:quality (first (update-quality [(item "Sulfuras" 10 8)]))))))

(deftest quality-is-never-negative-test
  (is (= 0 (:quality (first (update-quality [(item "foo" 10 0)])))))
  (is (= 0 (:quality (first (update-quality [(item "Sulfuras" 10 0)]))))))
