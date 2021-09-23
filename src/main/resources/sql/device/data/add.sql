INSERT INTO growbox.device_data(device_id, timestamp, light_level, temperature, humidity, moisture)
VALUES (?, ?, ?, ?, ?, ?)
ON CONFLICT (device_id, timestamp)
    DO UPDATE SET light_level = EXCLUDED.light_level,
                  temperature = EXCLUDED.temperature,
                  humidity    = EXCLUDED.humidity,
                  moisture    = EXCLUDED.moisture;