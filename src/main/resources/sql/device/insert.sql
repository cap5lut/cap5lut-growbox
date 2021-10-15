INSERT INTO "cap5lut-growbox"."devices" (deviceId)
VALUES (?)
ON CONFLICT (deviceId) DO NOTHING;