import dht
import log
import machine
import utime

_sensor_light: machine.ADC = None
_sensor_air: dht.DHT11 = None
_sensor_soil: machine.ADC = None


def _init_adc(pin: int, atten=machine.ADC.ATTN_11DB, width=machine.ADC.WIDTH_12BIT):
    adc = machine.ADC(machine.Pin(pin))
    adc.atten(atten)
    adc.width(width)
    return adc


def init(pin_light: int, pin_air: int, pin_soil: int):
    global _sensor_light, _sensor_air, _sensor_soil
    log.debug("SENSORS: init light={} air={} soil={}", pin_light, pin_air, pin_soil)
    _sensor_light = _init_adc(pin_light)
    _sensor_air = dht.DHT11(machine.Pin(pin_air))
    _sensor_soil = _init_adc(pin_soil)


def measure():
    global _sensor_light, _sensor_air, _sensor_soil
    _sensor_air.measure()
    data = (
        utime.time() + 946684800,
        _sensor_light.read(),
        _sensor_air.temperature(),
        _sensor_air.humidity(),
        _sensor_soil.read()
    )
    log.debug("SENSORS: data {}", data)
    return data

