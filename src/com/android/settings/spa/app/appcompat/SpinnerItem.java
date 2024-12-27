package com.android.settings.spa.app.appcompat;

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
            "Lcom/android/settings/spa/app/appcompat/SpinnerItem;",
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
final class SpinnerItem {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ SpinnerItem[] $VALUES;
    public static final SpinnerItem All;
    public static final SpinnerItem Overridden;
    public static final SpinnerItem Suggested;
    private final int stringResId;

    static {
        SpinnerItem spinnerItem =
                new SpinnerItem("Suggested", 0, R.string.user_aspect_ratio_suggested_apps_label);
        Suggested = spinnerItem;
        SpinnerItem spinnerItem2 = new SpinnerItem("All", 1, R.string.filter_all_apps);
        All = spinnerItem2;
        SpinnerItem spinnerItem3 =
                new SpinnerItem("Overridden", 2, R.string.user_aspect_ratio_changed_apps_label);
        Overridden = spinnerItem3;
        SpinnerItem[] spinnerItemArr = {spinnerItem, spinnerItem2, spinnerItem3};
        $VALUES = spinnerItemArr;
        $ENTRIES = EnumEntriesKt.enumEntries(spinnerItemArr);
    }

    public SpinnerItem(String str, int i, int i2) {
        this.stringResId = i2;
    }

    public static SpinnerItem valueOf(String str) {
        return (SpinnerItem) Enum.valueOf(SpinnerItem.class, str);
    }

    public static SpinnerItem[] values() {
        return (SpinnerItem[]) $VALUES.clone();
    }

    public final int getStringResId() {
        return this.stringResId;
    }
}
