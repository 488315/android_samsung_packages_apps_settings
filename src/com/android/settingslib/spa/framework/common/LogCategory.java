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
            "Lcom/android/settingslib/spa/framework/common/LogCategory;",
            ApnSettings.MVNO_NONE,
            "frameworks__base__packages__SettingsLib__Spa__spa__android_common__SeslSpaLib"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class LogCategory {
    public static final /* synthetic */ LogCategory[] $VALUES;
    public static final LogCategory FRAMEWORK;
    public static final LogCategory VIEW;

    static {
        LogCategory logCategory = new LogCategory("DEFAULT", 0);
        LogCategory logCategory2 = new LogCategory("FRAMEWORK", 1);
        FRAMEWORK = logCategory2;
        LogCategory logCategory3 = new LogCategory("VIEW", 2);
        VIEW = logCategory3;
        LogCategory[] logCategoryArr = {logCategory, logCategory2, logCategory3};
        $VALUES = logCategoryArr;
        EnumEntriesKt.enumEntries(logCategoryArr);
    }

    public static LogCategory valueOf(String str) {
        return (LogCategory) Enum.valueOf(LogCategory.class, str);
    }

    public static LogCategory[] values() {
        return (LogCategory[]) $VALUES.clone();
    }
}
