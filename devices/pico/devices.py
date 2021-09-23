import array
import machine
import micropython
import utime

class DeviceError(Exception):
    def __init__(self, device: _Device, message: str = "An error occurred", cause: Exception = None):
        self._device = device
        self._cause = cause
        self._suppressed = []
        super().__init__(message)

    @property
    def device(self):
        return self._device

    @property
    def cause(self):
        return self._cause

    @property
    def suppressed(self):
        return tuple(self._suppressed)

    def suppress(self, exception: Exception):
        self._suppressed.append(exception)

class _Device:
    def __init__(self, id):
        self._id = id

    @property
    def id(self):
        return self._id

class _ADCDevice(_Device):
    MAX_VALUE = micropython.const(65535)

    def __init__(self, pin: int, converter):
        self._adc = machine.ADC(pin)
        self._converter = converter
        super().__init__(pin)

    def measure(self):
        try:
            value = self._adc.read_u16()
            return self._converter(value)
        except Exception as cause:
            raise DeviceError(self, "failed to read data", cause)

class DHT11(_Device):
    MAX_UNCHANGED = micropython.const(100)
    MIN_INTERVAL_US = micropython.const(200000)
    HIGH_LEVEL = micropython.const(50)
    EXPECTED_PULSES = micropython.const(84)

    def __init__(self, pin: int):
        self._pin = machine.Pin(pin, machine.Pin.OUT, machine.Pin.PULL_DOWN)
        super().__init__(pin)

    def measure(self):
        self._request_data()
        pulses = self._read_pulses()
        buffer = self._pulses_to_buffer(pulses)
        self._verify_checksum(buffer)

        return (buffer[2] + buffer[3] / 10, buffer[0] + buffer[1] / 10)

    def _request_data(self):
        self._pin.init(machine.Pin.OUT, machine.Pin.PULL_DOWN)
        self._pin.value(1)
        utime.sleep_ms(50)
        self._pin.value(0)
        utime.sleep_ms(18)

    @micropython.native
    def _read_pulses(self):
        pin = self._pin
        pin.init(machine.Pin.IN, machine.Pin.PULL_UP)

        value = 1
        idx = 0
        transitions = bytearray(DHT11.EXPECTED_PULSES)
        unchanged = 0
        timestamp = utime.ticks_us()

        while unchanged < DHT11.MAX_UNCHANGED:
            if value != pin.value():
                if idx >= DHT11.EXPECTED_PULSES:
                    raise DeviceError(self, "expected {} pulses, got {}".format(DHT11.EXPECTED_PULSES, idx))
                now = utime.ticks_us()
                transitions[idx] = now - timestamp
                timestamp = now
                idx += 1

                value = 1 - value
                unchanged = 0
            else:
                unchanged += 1
        pin.init(machine.Pin.OUT, machine.Pin.PULL_DOWN)
        if idx != DHT11.EXPECTED_PULSES:
            raise DeviceError(self, "expected {} pulses, got {}".format(DHT11.EXPECTED_PULSES, idx))
        return transitions[4:]

    def _pulses_to_buffer(self, pulses):
        binary = 0
        for idx in range(0, len(pulses), 2):
            binary = binary << 1 | int(pulses[idx] > DHT11.HIGH_LEVEL)

        buffer = array.array("B")
        for shift in range(4, -1, -1):
            buffer.append(binary >> shift * 8 & 0xFF)
        return buffer

    def _verify_checksum(self, buffer):
        checksum = 0
        for buf in buffer[0:4]:
            checksum += buf
        if checksum & 0xFF != buffer[4]:
            raise DeviceError(self, "invalid checksum")

    def __str__(self):
        return "DHT11(pin={})".format(self.id)

class GroundResistor(_ADCDevice):
    def __init__(self, pin):
        super().__init__(pin, lambda value: 1 - (value / _ADCDevice.MAX_VALUE))

    def __str__(self):
        return "GroundResistor(pin={})".format(self.id)

class LED(_Device):
    def __init__(self, pin: int):
        self._pin = machine.Pin(pin, machine.Pin.OUT)
        self._pin.value(0)
        self._state = 0
        super().__init__(pin)

    @property
    def state(self):
        return self._state

    def set(self, state: int):
        if self._state != state:
            try:
                self._pin.value(state)
            except Exception as cause:
                raise DeviceError(self, "failed to set state to {}".format(state), cause)
            self._state = state

    def on(self):
        self.set(1)

    def off(self):
        self.set(0)

    def toggle(self):
        if self._state == 0:
            self.on()
        else:
            self.off()

    def __str__(self):
        return "LED(pin={}, state={})".format(self.id, self._state)

class PhotoResistor(_ADCDevice):
    def __init__(self, pin):
        super().__init__(pin, lambda value: 1 - (value / _ADCDevice.MAX_VALUE))

    def __str__(self):
        return "PhotoResistor(pin={})".format(self.id)
