package com.samsung.android.settings.analyzestorage.presenter.page;

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
            "Lcom/samsung/android/settings/analyzestorage/presenter/page/NavigationMode;",
            ApnSettings.MVNO_NONE,
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class NavigationMode {
    public static final /* synthetic */ NavigationMode[] $VALUES;
    public static final NavigationMode AnalyzeStorageFilesFromDC;
    public static final NavigationMode Normal;

    static {
        NavigationMode navigationMode = new NavigationMode("Normal", 0);
        Normal = navigationMode;
        NavigationMode navigationMode2 = new NavigationMode("AnalyzeStorageFilesFromDC", 1);
        AnalyzeStorageFilesFromDC = navigationMode2;
        NavigationMode[] navigationModeArr = {navigationMode, navigationMode2};
        $VALUES = navigationModeArr;
        EnumEntriesKt.enumEntries(navigationModeArr);
    }

    public static NavigationMode valueOf(String str) {
        return (NavigationMode) Enum.valueOf(NavigationMode.class, str);
    }

    public static NavigationMode[] values() {
        return (NavigationMode[]) $VALUES.clone();
    }
}
