(ns hello-bot.motor)

(defrecord Motor [forward-key reverse-key])

(defn forward [{:keys [forward-key reverse-key]}]
  {forward-key :on
   reverse-key :off})

(defn reverse [{:keys [forward-key reverse-key]}]
  {forward-key :off
   reverse-key :on})

(defn stop [{:keys [forward-key reverse-key]}]
  {forward-key :off
   reverse-key :off})
