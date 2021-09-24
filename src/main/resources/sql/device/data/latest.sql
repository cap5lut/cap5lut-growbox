SELECT device_id,
       timestamp,
       light_level,
       temperature,
       humidity,
       moisture
FROM growbox.device_data
WHERE device_id = ?
ORDER BY timestamp
LIMIT 1;