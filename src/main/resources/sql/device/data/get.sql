SELECT device_id,
       timestamp,
       light_level,
       temperature,
       humidity,
       moisture
FROM growbox.device_data
WHERE device_id = ?
  AND timestamp > ?
ORDER BY timestamp;