package io.ddavison.conductor.util;

import io.ddavison.conductor.Locomotive;

import java.util.logging.Logger;

public class Log {

    public static Logger log;

    static {
        log = Logger.getLogger(Locomotive.class.getSimpleName());
    }

    public static void fatal(String message) {
        log.severe(message);
    }
}
