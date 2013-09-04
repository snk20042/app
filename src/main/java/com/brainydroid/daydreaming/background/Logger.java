package com.brainydroid.daydreaming.background;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.MessageFormat;

/**
 * Log required information if the application configuration requires so.
 *
 * @author Sébastien Lerique
 * @author Vincent Adam
 */
public class Logger {

    /** Whether to show debug toasts or not */
    public static boolean TOASTD = true;
    /** Whether to log at info level or not */
    public static boolean LOGI = true;
    /** Whether to log at debug level or not */
    public static boolean LOGD = true;
    /** Whether to log at verbose level or not */
    public static boolean LOGV = true;

    /**
     * Log at Error level.
     *
     * @param tag Tag to attach to the log message
     * @param messagePattern Log message in MessageFormat pattern format
     * @param messageArgs Optional arguments for the messagePattern pattern
     */
    public static void e(String tag, String messagePattern,
                         Object... messageArgs) {
        Log.e(tag, MessageFormat.format(messagePattern, messageArgs));
    }

    /**
     * Log at Warn level.
     *
     * @param tag Tag to attach to the log message
     * @param messagePattern Log message in MessageFormat pattern format
     * @param messageArgs Optional arguments for the messagePattern pattern
     */
    public static void w(String tag, String messagePattern,
                         Object... messageArgs) {
        Log.w(tag, MessageFormat.format(messagePattern, messageArgs));
    }

    /**
     * Log at Info level.
     *
     * @param tag Tag to attach to the log message
     * @param messagePattern Log message in MessageFormat pattern format
     * @param messageArgs Optional arguments for the messagePattern pattern
     */
    public static void i(String tag, String messagePattern,
                         Object... messageArgs) {
        if (LOGI) Log.i(tag, MessageFormat.format(messagePattern,
                messageArgs));
    }

    /**
     * Log at Debug level.
     *
     * @param tag Tag to attach to the log message
     * @param messagePattern Log message in MessageFormat pattern format
     * @param messageArgs Optional arguments for the messagePattern pattern
     */
    public static void d(String tag, String messagePattern,
                         Object... messageArgs) {
        if (LOGD) Log.d(tag, MessageFormat.format(messagePattern,
                messageArgs));
    }

    /**
     * Log at Verbose level.
     *
     * @param tag Tag to attach to the log message
     * @param messagePattern Log message in MessageFormat pattern format
     * @param messageArgs Optional arguments for the messagePattern pattern
     */
    public static void v(String tag, String messagePattern,
                         Object... messageArgs) {
        if (LOGV) Log.v(tag, MessageFormat.format(messagePattern,
                messageArgs));
    }

    /**
     * Toast log at Debug level.
     *
     * @param context Context from which the Toast is shown
     * @param messagePattern Log message in MessageFormat pattern format
     * @param messageArgs Optional arguments for the messagePattern pattern
     */
    public static void td(Context context, String messagePattern,
                          Object... messageArgs) {
        if (TOASTD) Toast.makeText(context,
                MessageFormat.format(messagePattern, messageArgs),
                Toast.LENGTH_LONG).show();
    }

}
