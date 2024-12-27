package com.android.settingslib.spa.framework.common;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0010\n"
                + "\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001¨\u0006\u0002"
        },
        d2 = {
            "Lcom/android/settingslib/spa/framework/common/LogEvent;",
            ApnSettings.MVNO_NONE,
            "frameworks__base__packages__SettingsLib__Spa__spa__android_common__SeslSpaLib"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class LogEvent {
    public static final /* synthetic */ LogEvent[] $VALUES;
    public static final LogEvent ENTRY_CLICK;
    public static final LogEvent ENTRY_SWITCH;
    public static final LogEvent PAGE_ENTER;
    public static final LogEvent PAGE_LEAVE;

    static {
        LogEvent logEvent = new LogEvent("PAGE_ENTER", 0);
        PAGE_ENTER = logEvent;
        LogEvent logEvent2 = new LogEvent("PAGE_LEAVE", 1);
        PAGE_LEAVE = logEvent2;
        LogEvent logEvent3 = new LogEvent("ENTRY_CLICK", 2);
        ENTRY_CLICK = logEvent3;
        LogEvent logEvent4 = new LogEvent("ENTRY_SWITCH", 3);
        ENTRY_SWITCH = logEvent4;
        LogEvent[] logEventArr = {logEvent, logEvent2, logEvent3, logEvent4};
        $VALUES = logEventArr;
        EnumEntriesKt.enumEntries(logEventArr);
    }

    public static LogEvent valueOf(String str) {
        return (LogEvent) Enum.valueOf(LogEvent.class, str);
    }

    public static LogEvent[] values() {
        return (LogEvent[]) $VALUES.clone();
    }
}
