package com.google.common.util.concurrent;

import java.util.logging.Logger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LazyLogger {
    public volatile Logger logger;
    public final String loggerName;

    public LazyLogger(Class cls) {
        this.loggerName = cls.getName();
    }

    public final Logger get() {
        Logger logger = this.logger;
        if (logger != null) {
            return logger;
        }
        synchronized (this) {
            try {
                Logger logger2 = this.logger;
                if (logger2 != null) {
                    return logger2;
                }
                Logger logger3 = Logger.getLogger(this.loggerName);
                this.logger = logger3;
                return logger3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
