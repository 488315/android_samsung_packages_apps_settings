package com.samsung.android.settings.analyzestorage.presenter.managers.application;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"
        },
        d2 = {
            "com/samsung/android/settings/analyzestorage/presenter/managers/application/AppDataInterface$AppData",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/settings/analyzestorage/presenter/managers/application/AppDataInterface$AppData;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class AppDataInterface$AppData {
    public static final /* synthetic */ AppDataInterface$AppData[] $VALUES;
    public static final AppDataInterface$AppData TOTAL = null;

    static {
        AppDataInterface$AppData[] appDataInterface$AppDataArr = {
            new AppDataInterface$AppData("TOTAL", 0),
            new AppDataInterface$AppData("UNUSED", 1),
            new AppDataInterface$AppData("CACHE", 2)
        };
        $VALUES = appDataInterface$AppDataArr;
        EnumEntriesKt.enumEntries(appDataInterface$AppDataArr);
    }

    public static AppDataInterface$AppData valueOf(String str) {
        return (AppDataInterface$AppData) Enum.valueOf(AppDataInterface$AppData.class, str);
    }

    public static AppDataInterface$AppData[] values() {
        return (AppDataInterface$AppData[]) $VALUES.clone();
    }
}
