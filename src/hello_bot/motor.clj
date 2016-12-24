(ns hello-bot.motor)

(defrecord Motor [forward-key reverse-key])

(defn forward [{:keys [forward-key reverse-key]}]
  {forward-key :high
   reverse-key :low})

(defn reverse [{:keys [forward-key reverse-key]}]
  {forward-key :low
   reverse-key :high})

(defn stop [{:keys [forward-key reverse-key]}]
  {forward-key :low
   reverse-key :low})
