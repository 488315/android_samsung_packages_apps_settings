package com.samsung.android.settings.eternal.utils;

import android.util.Log;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.util.SemLog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class KnoxRestrictionsFileLog {
    public static Logger sLogger;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class KnoxRestrictionsFileLogHolder {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            try {
                final SimpleDateFormat simpleDateFormat =
                        new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
                FileHandler fileHandler =
                        new FileHandler(
                                "/data/log/knoxRestrictions.log",
                                KnoxsdkFileLog.LOG_FILE_SIZE_LIMIT,
                                2,
                                true);
                fileHandler.setFormatter(
                        new Formatter() { // from class:
                                          // com.samsung.android.settings.eternal.utils.KnoxRestrictionsFileLog.1
                            @Override // java.util.logging.Formatter
                            public final String format(LogRecord logRecord) {
                                Date date = new Date();
                                date.setTime(System.currentTimeMillis());
                                StringBuilder sb = new StringBuilder(80);
                                sb.append(simpleDateFormat.format(date));
                                sb.append(logRecord.getMessage());
                                return sb.toString();
                            }
                        });
                Logger logger = Logger.getLogger(KnoxRestrictionsFileLog.class.getName());
                KnoxRestrictionsFileLog.sLogger = logger;
                logger.addHandler(fileHandler);
                KnoxRestrictionsFileLog.sLogger.setLevel(Level.ALL);
                KnoxRestrictionsFileLog.sLogger.setUseParentHandlers(false);
                Log.d("Eternal/KnoxRestrictionsFileLog", "init success");
            } catch (IOException e) {
                Log.d("Eternal/KnoxRestrictionsFileLog", "init failure : " + e.getMessage());
            }
        }
    }

    public static void d(String str, String str2) {
        fileLog(str, str2);
        SemLog.d(str, str2);
    }

    public static void fileLog(String str, String str2) {
        int i = KnoxRestrictionsFileLogHolder.$r8$clinit;
        Logger logger = sLogger;
        if (logger != null) {
            logger.log(
                    Level.INFO,
                    MotionLayout$$ExternalSyntheticOutline0.m(" ", str, " : ", str2, "\n"));
        }
    }
}
