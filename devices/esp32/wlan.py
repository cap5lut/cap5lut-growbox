import log
import network
import ubinascii
import utime

_interface = network.WLAN(network.STA_IF)
_interface.active(False)


def connect(ssid: str, password: str):
    log.debug("WLAN: connecting ssid={}", ssid)
    _interface.active(True)
    _interface.connect(ssid, password)


def isconnected():
    return _interface.isconnected()


def await_connection(timeout=10_000, delay=100):
    start = utime.ticks_ms()
    while not isconnected() and utime.ticks_diff(utime.ticks_ms(), start) < timeout:
        utime.sleep_ms(delay)
    return isconnected()


def disconnect():
    log.debug("WLAN: disconnecting")
    _interface.active(False)


def mac_address():
    return ubinascii.hexlify(_interface.config("mac")).upper()

