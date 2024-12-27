package com.samsung.android.gtscell.log;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.zt.config.BuildConfig;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000.\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0011\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0003\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\bf\u0018\u0000"
                + " \u00122\u00020\u0001:\u0003\u0012\u0013\u0014J-\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\bJ-\u0010"
                + "\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\bJ5\u0010"
                + "\t\u001a\u00020\u00032\u0006\u0010\n"
                + "\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\fJ-\u0010\r"
                + "\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\bJ\u0010\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0010H&J-\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\bJ5\u0010\u0011\u001a\u00020\u00032\u0006\u0010\n"
                + "\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H&¢\u0006\u0002\u0010\f¨\u0006\u0015"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/log/GLogger;",
            ApnSettings.MVNO_NONE,
            BuildConfig.BUILD_TYPE,
            ApnSettings.MVNO_NONE,
            "msg",
            ApnSettings.MVNO_NONE,
            "obj",
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/String;[Ljava/lang/Object;)V",
            "error",
            "throwable",
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V",
            "info",
            "setLevel",
            "level",
            "Lcom/samsung/android/gtscell/log/GLogger$Level;",
            "warning",
            "Companion",
            "DebugLevel",
            "Level",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public interface GLogger {
    public static final int BUF_LIMIT = 100;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;
    public static final String TAG = "GTS_CELL";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u00009\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010\b\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0006\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002*\u0001\r"
                    + "\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0011\u001a\u00020\r"
                    + "2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002¢\u0006\u0002\u0010\u0014J\u0014\u0010\u0015\u001a\u00020\u00062\n"
                    + "\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u0017H\u0007J\u001c\u0010\u0015\u001a\u00020\u00062\n"
                    + "\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u00172\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0010H\u0007J\u0018\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u001c\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00102\n"
                    + "\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u0017H\u0007J$\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00102\n"
                    + "\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u00172\u0006\u0010\u0012\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n"
                    + "\u0000R!\u0010\u0005\u001a\u00020\u00068FX\u0087\u0084\u0002¢\u0006\u0012\n"
                    + "\u0004\b\n"
                    + "\u0010\u000b\u0012\u0004\b\u0007\u0010\u0002\u001a\u0004\b\b\u0010"
                    + "\tR\u0010\u0010\f\u001a\u00020\r"
                    + "X\u0082\u0004¢\u0006\u0004\n"
                    + "\u0002\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0086T¢\u0006\u0002\n"
                    + "\u0000¨\u0006\u0019"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/log/GLogger$Companion;",
                ApnSettings.MVNO_NONE,
                "()V",
                "BUF_LIMIT",
                ApnSettings.MVNO_NONE,
                "Global",
                "Lcom/samsung/android/gtscell/log/GLogger;",
                "Global$annotations",
                "getGlobal",
                "()Lcom/samsung/android/gtscell/log/GLogger;",
                "Global$delegate",
                "Lkotlin/Lazy;",
                "GlobalLevel",
                "com/samsung/android/gtscell/log/GLogger$Companion$debugLevelOf$1",
                "Lcom/samsung/android/gtscell/log/GLogger$Companion$debugLevelOf$1;",
                "TAG",
                ApnSettings.MVNO_NONE,
                "debugLevelOf",
                "level",
                "Lcom/samsung/android/gtscell/log/GLogger$Level;",
                "(Lcom/samsung/android/gtscell/log/GLogger$Level;)Lcom/samsung/android/gtscell/log/GLogger$Companion$debugLevelOf$1;",
                "getLogger",
                "clazz",
                "Ljava/lang/Class;",
                "tag",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        public static final int BUF_LIMIT = 100;

        /* renamed from: Global$delegate, reason: from kotlin metadata */
        private static final Lazy Global;
        private static final GLogger$Companion$debugLevelOf$1 GlobalLevel;
        public static final String TAG = "GTS_CELL";

        static {
            final Companion companion = new Companion();
            $$INSTANCE = companion;
            GlobalLevel = companion.debugLevelOf(Level.INFO);
            Global =
                    LazyKt__LazyJVMKt.lazy(
                            new Function0() { // from class:
                                              // com.samsung.android.gtscell.log.GLogger$Companion$Global$2
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final GLogger mo1068invoke() {
                                    return GLogger.Companion.this.getLogger(ApnSettings.MVNO_NONE);
                                }
                            });
        }

        private Companion() {}

        /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.gtscell.log.GLogger$Companion$debugLevelOf$1] */
        private final GLogger$Companion$debugLevelOf$1 debugLevelOf(final Level level) {
            return new DebugLevel() { // from class:
                                      // com.samsung.android.gtscell.log.GLogger$Companion$debugLevelOf$1
                private GLogger.Level level;

                {
                    this.level = GLogger.Level.this;
                }

                @Override // com.samsung.android.gtscell.log.GLogger.DebugLevel
                public GLogger.Level getLevel() {
                    return this.level;
                }

                @Override // com.samsung.android.gtscell.log.GLogger.DebugLevel
                public void setLevel(GLogger.Level level2) {
                    Intrinsics.checkParameterIsNotNull(level2, "<set-?>");
                    this.level = level2;
                }
            };
        }

        public final GLogger getGlobal() {
            return (GLogger) Global.getValue();
        }

        public final GLogger getLogger(String tag) {
            Intrinsics.checkParameterIsNotNull(tag, "tag");
            return new GLoggerImpl(GlobalLevel, tag);
        }

        public final GLogger getLogger(String tag, Level level) {
            Intrinsics.checkParameterIsNotNull(tag, "tag");
            Intrinsics.checkParameterIsNotNull(level, "level");
            return new GLoggerImpl(debugLevelOf(level), tag);
        }

        public final GLogger getLogger(Class<?> clazz) {
            Intrinsics.checkParameterIsNotNull(clazz, "clazz");
            return getLogger(clazz.getSimpleName());
        }

        public final GLogger getLogger(Class<?> clazz, Level level) {
            Intrinsics.checkParameterIsNotNull(clazz, "clazz");
            Intrinsics.checkParameterIsNotNull(level, "level");
            return getLogger(clazz.getSimpleName(), level);
        }

        public final GLogger getLogger(String tag, Class<?> clazz) {
            Intrinsics.checkParameterIsNotNull(tag, "tag");
            Intrinsics.checkParameterIsNotNull(clazz, "clazz");
            return getLogger(tag + " " + clazz.getSimpleName());
        }

        public final GLogger getLogger(String tag, Class<?> clazz, Level level) {
            Intrinsics.checkParameterIsNotNull(tag, "tag");
            Intrinsics.checkParameterIsNotNull(clazz, "clazz");
            Intrinsics.checkParameterIsNotNull(level, "level");
            return getLogger(tag + " " + clazz.getSimpleName(), level);
        }

        public static /* synthetic */ void Global$annotations() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\u0012\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\b"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/log/GLogger$DebugLevel;",
                ApnSettings.MVNO_NONE,
                "level",
                "Lcom/samsung/android/gtscell/log/GLogger$Level;",
                "getLevel",
                "()Lcom/samsung/android/gtscell/log/GLogger$Level;",
                "setLevel",
                "(Lcom/samsung/android/gtscell/log/GLogger$Level;)V",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public interface DebugLevel {
        Level getLevel();

        void setLevel(Level level);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\f\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0010\n"
                    + "\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/log/GLogger$Level;",
                ApnSettings.MVNO_NONE,
                "(Ljava/lang/String;I)V",
                "ERROR",
                "WARNING",
                "INFO",
                "DEBUG",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public enum Level {
        ERROR,
        WARNING,
        INFO,
        DEBUG
    }

    static GLogger getGlobal() {
        return INSTANCE.getGlobal();
    }

    static GLogger getLogger(Class<?> cls) {
        return INSTANCE.getLogger(cls);
    }

    void debug(String msg, Object... obj);

    void error(String msg, Object... obj);

    void error(Throwable throwable, String msg, Object... obj);

    void info(String msg, Object... obj);

    GLogger setLevel(Level level);

    void warning(String msg, Object... obj);

    void warning(Throwable throwable, String msg, Object... obj);

    static GLogger getLogger(Class<?> cls, Level level) {
        return INSTANCE.getLogger(cls, level);
    }

    static GLogger getLogger(String str) {
        return INSTANCE.getLogger(str);
    }

    static GLogger getLogger(String str, Level level) {
        return INSTANCE.getLogger(str, level);
    }

    static GLogger getLogger(String str, Class<?> cls) {
        return INSTANCE.getLogger(str, cls);
    }

    static GLogger getLogger(String str, Class<?> cls, Level level) {
        return INSTANCE.getLogger(str, cls, level);
    }
}
