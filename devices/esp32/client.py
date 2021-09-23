import log
import urequests
import wlan

_base_url = None

def init(base_url:str):
    global _base_url
    _base_url = base_url

def upload_data(data):
    global _base_url
    log.debug("CLIENT: data upload: {}", data)
    result = urequests.request(
        "PUT", _base_url + "/api/device/data",
        json={
            "device_id": wlan.mac_address(),
            "timestamp": data[0],
            "light_level": data[1],
            "temperature": data[2],
            "humidity": data[3],
            "moisture": data[4]
        }
    )
    log.debug("CLIENT: data upload status: {}", result.status_code)


