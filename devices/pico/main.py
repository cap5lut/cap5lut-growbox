from devices import DeviceError, DHT11, GroundResistor, LED, PhotoResistor
import logs
from machine import deepsleep, lightsleep
from micropython import const
from utime import localtime, ticks_ms, ticks_diff

_INTERVAL = const(5_000)

_PIN_ACTIVITY_LED = const(25)
_PIN_AIR_SENSOR = const(1)
_PIN_LIGHT_SENSOR = const(27)
_PIN_GROUND_SENSOR = const(26)

logs.info("growbox-pico v0.1.0 - by cap5lut")

logs.info("initializing ...")
logs.debug_enabled = True
activity_led = LED(_PIN_ACTIVITY_LED)
activity_led.on()
air_sensor = DHT11(_PIN_AIR_SENSOR)
light_sensor = PhotoResistor(_PIN_LIGHT_SENSOR)
ground_sensor = GroundResistor(_PIN_GROUND_SENSOR)
lightsleep(2_000)
activity_led.off()

while True:
    activity_led.on()
    logs.info("reading ...")
    start = ticks_ms()
    while True:
        error_delay = 500
        try:
            air_data = air_sensor.measure()
            light_data = light_sensor.measure()
            ground_data = ground_sensor.measure()
            logs.debug("light: {:.2f}%, air: {:2.1f}°c {:2.1f}%, ground: {:.2f}%", light_data * 100, air_data[0], air_data[1], ground_data * 100)
            break
        except DeviceError as error:
            logs.error("{} (device={}, cause={})", error, error.device, error.cause)
            lightsleep(error_delay)
            error_delay = error_delay * 3.5
    activity_led.off()
    logs.info("sleeping ...")
    lightsleep(_INTERVAL - ticks_diff(ticks_ms(), start))


