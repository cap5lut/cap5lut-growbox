import client
import log
import machine
import sensors
import wlan
import utime

log.debug_enabled = True
log.info("growbox v0.1.0 by cap5lut | machine: {:s}", wlan.mac_address())
client.init("http://192.168.0.189:8080")
sensors.init(pin_light=34, pin_air=23, pin_soil=32)

for i in range(10):
    wlan.connect("Vodafone-0E6C", "AbRrRdKtxmhReryP")
    data = []
    for sample in range(10):
        data.append(sensors.measure())
        utime.sleep_ms(2_000)

    if wlan.await_connection():
        client.upload_data(data)
    else:
        log.error("Could not connect to WLAN")

    log.debug("Shutting down")
    wlan.disconnect()
#machine.deepsleep(30_000)

