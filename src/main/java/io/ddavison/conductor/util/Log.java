package io.ddavison.conductor.util;

import io.ddavison.conductor.Locomotive;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

    public static Logger log;

    static {
        log = Logger.getLogger(Locomotive.class.getSimpleName());
    }

    public static void fatal(Object object) {
        fatal(object.toString());
    }

    public static void fatal(String message) {
        log.severe(message);
    }

    public static void warning(Object object) {
        warning(object.toString());
    }

    public static void warning(String message) {
        log.warning(message);
    }

    public static void debug(Object object) {
        debug(object.toString());
    }

    public static void debug(String message) {
        log.info(message);
    }

}
