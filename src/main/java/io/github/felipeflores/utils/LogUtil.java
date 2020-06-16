package io.github.felipeflores.utils;

import net.logstash.logback.argument.StructuredArgument;
import org.slf4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

public class LogUtil {

    public final static String TYPE = "type";
    public final static String STACK_TRACE = "stack_trace";

    public static void error(Logger log, String tipo, Throwable cause) {
        error(log, tipo, cause, null);
    }
    public static void error(Logger log, String tipo, Throwable cause, StructuredArgument...arguments) {
        log.error("{} - {}",keyValue(TYPE, tipo), keyValue(STACK_TRACE, getStacktrace(cause)), arguments);
    }

    public static void info(Logger log, String tipo, StructuredArgument arguments) {
        log.info("{} - {}",keyValue(TYPE, tipo), arguments);
    }

    public static StringWriter getStacktrace(Throwable e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors;
    }
}
