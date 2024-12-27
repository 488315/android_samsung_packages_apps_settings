package com.samsung.android.settings.eternal.log;

import android.os.UserHandle;
import android.util.Log;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EternalFileLog {
    public static final DateTimeFormatter dateFormat =
            DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS", Locale.getDefault());
    public static int mUserId;
    public static FileHandler sFileHandler;
    public static Logger sLogger;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class EternalFileLogHolder {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            try {
                EternalFileLog.mUserId = UserHandle.semGetMyUserId();
                FileHandler fileHandler =
                        new FileHandler(
                                "/data/log/eternal.log",
                                KnoxsdkFileLog.LOG_FILE_SIZE_LIMIT,
                                2,
                                true);
                EternalFileLog.sFileHandler = fileHandler;
                fileHandler.setFormatter(
                        new Formatter() { // from class:
                                          // com.samsung.android.settings.eternal.log.EternalFileLog.1
                            @Override // java.util.logging.Formatter
                            public final String format(LogRecord logRecord) {
                                LocalDateTime now = LocalDateTime.now();
                                StringBuilder sb = new StringBuilder(80);
                                sb.append(now.format(EternalFileLog.dateFormat));
                                sb.append(logRecord.getMessage());
                                return sb.toString();
                            }
                        });
                Logger logger = Logger.getLogger(EternalFileLog.class.getName());
                EternalFileLog.sLogger = logger;
                logger.addHandler(EternalFileLog.sFileHandler);
                EternalFileLog.sLogger.setLevel(Level.ALL);
                EternalFileLog.sLogger.setUseParentHandlers(false);
                Log.d("Eternal/EternalFileLog", "init success");
            } catch (IOException e) {
                Log.d("Eternal/EternalFileLog", "init failure : " + e.getMessage());
            }
        }
    }

    public static void d(String str, String str2) {
        fileLog(str, getLogPrefix() + str2);
        Log.d(str, getLogPrefix() + str2);
    }

    public static void e(String str, String str2) {
        fileLog(str, getLogPrefix() + str2);
        Log.e(str, getLogPrefix() + str2);
    }

    public static void fileLog(String str, String str2) {
        int i = EternalFileLogHolder.$r8$clinit;
        if (sLogger != null) {
            sLogger.log(Level.INFO, String.format(" %s : %s%n", str, str2));
        }
    }

    public static String getLogPrefix() {
        return Anchor$$ExternalSyntheticOutline0.m(new StringBuilder("["), mUserId, "] ");
    }

    public static void i(String str, String str2) {
        fileLog(str, getLogPrefix() + str2);
        Log.i(str, getLogPrefix() + str2);
    }

    public static void w(String str, String str2) {
        fileLog(str, getLogPrefix() + str2);
        Log.w(str, getLogPrefix() + str2);
    }
}
