package com.samsung.android.gtscell.log;

import android.util.Log;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000,\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0003\n"
                + "\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\u00020\b2\u0006\u0010"
                + "\t\u001a\u00020\n"
                + "2\u0006\u0010\u000b\u001a\u00020\u00052\b\u0010\f\u001a\u0004\u0018\u00010\r"
                + "H\u0016¨\u0006\u000e"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/log/GLoggerImpl;",
            "Lcom/samsung/android/gtscell/log/GLoggerBase;",
            "debugLevel",
            "Lcom/samsung/android/gtscell/log/GLogger$DebugLevel;",
            "tag",
            ApnSettings.MVNO_NONE,
            "(Lcom/samsung/android/gtscell/log/GLogger$DebugLevel;Ljava/lang/String;)V",
            "message",
            ApnSettings.MVNO_NONE,
            "level",
            "Lcom/samsung/android/gtscell/log/GLogger$Level;",
            "msg",
            "throwable",
            ApnSettings.MVNO_NONE,
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public class GLoggerImpl extends GLoggerBase {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            k = 3,
            mv = {1, 1, 16})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GLogger.Level.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[GLogger.Level.ERROR.ordinal()] = 1;
            iArr[GLogger.Level.WARNING.ordinal()] = 2;
            iArr[GLogger.Level.INFO.ordinal()] = 3;
            iArr[GLogger.Level.DEBUG.ordinal()] = 4;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GLoggerImpl(GLogger.DebugLevel debugLevel, String tag) {
        super(debugLevel, tag);
        Intrinsics.checkParameterIsNotNull(debugLevel, "debugLevel");
        Intrinsics.checkParameterIsNotNull(tag, "tag");
    }

    @Override // com.samsung.android.gtscell.log.GLoggerBase
    public void message(GLogger.Level level, String msg, Throwable throwable) {
        Intrinsics.checkParameterIsNotNull(level, "level");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        int i = WhenMappings.$EnumSwitchMapping$0[level.ordinal()];
        if (i == 1) {
            Log.e("GTS_CELL", msg, throwable);
            return;
        }
        if (i == 2) {
            Log.w("GTS_CELL", msg, throwable);
        } else if (i == 3) {
            Log.i("GTS_CELL", msg, throwable);
        } else {
            if (i != 4) {
                return;
            }
            Log.d("GTS_CELL", msg, throwable);
        }
    }
}
