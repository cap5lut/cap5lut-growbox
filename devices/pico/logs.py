from utime import localtime

debug_enabled = False


def log(level, message, *args):
    now = localtime()
    now = "{:04d}-{:02d}-{:02d} {:02d}:{:02d}:{:02d}".format(*now)
    print("{} {:5s} {}".format(now, level, message.format(*args)))


def debug(message, *args):
    if debug_enabled:
        log("DEBUG", message, *args)


def info(message, *args):
    log("INFO", message, *args)


def error(message, *args):
    log("ERROR", message, *args)
