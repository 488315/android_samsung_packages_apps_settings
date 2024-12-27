package com.android.settings.spa.app.battery;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0010\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n"
                + "\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"
        },
        d2 = {
            "Lcom/android/settings/spa/app/battery/OptimizationModeSpinnerItem;",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "stringResId",
            ImsProfile.TIMER_NAME_I,
            "getStringResId",
            "()I",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
final class OptimizationModeSpinnerItem {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ OptimizationModeSpinnerItem[] $VALUES;
    private final int stringResId;

    static {
        OptimizationModeSpinnerItem[] optimizationModeSpinnerItemArr = {
            new OptimizationModeSpinnerItem("All", 0, R.string.filter_all_apps),
            new OptimizationModeSpinnerItem(
                    "Restricted", 1, R.string.filter_battery_restricted_title),
            new OptimizationModeSpinnerItem(
                    "Optimized", 2, R.string.filter_battery_optimized_title),
            new OptimizationModeSpinnerItem(
                    "Unrestricted", 3, R.string.filter_battery_unrestricted_title)
        };
        $VALUES = optimizationModeSpinnerItemArr;
        $ENTRIES = EnumEntriesKt.enumEntries(optimizationModeSpinnerItemArr);
    }

    public OptimizationModeSpinnerItem(String str, int i, int i2) {
        this.stringResId = i2;
    }

    public static OptimizationModeSpinnerItem valueOf(String str) {
        return (OptimizationModeSpinnerItem) Enum.valueOf(OptimizationModeSpinnerItem.class, str);
    }

    public static OptimizationModeSpinnerItem[] values() {
        return (OptimizationModeSpinnerItem[]) $VALUES.clone();
    }

    public final int getStringResId() {
        return this.stringResId;
    }
}
