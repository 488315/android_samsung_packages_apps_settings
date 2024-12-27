package com.samsung.android.settings.asbase.utils;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import kotlin.Metadata;
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
                + "\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\u0005\u0010\u0004¨\u0006\u0006"
        },
        d2 = {
            "Lcom/samsung/android/settings/asbase/utils/SelectionColorSetType;",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "selectedId",
            ImsProfile.TIMER_NAME_I,
            "unselectedId",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class SelectionColorSetType {
    public static final /* synthetic */ SelectionColorSetType[] $VALUES;
    public static final SelectionColorSetType Icon;
    public static final SelectionColorSetType Text;
    private final int selectedId;
    private final int unselectedId;

    static {
        SelectionColorSetType selectionColorSetType =
                new SelectionColorSetType(
                        0,
                        R.color.sec_widget_multi_button_selected_icon_color,
                        R.color.sec_widget_multi_button_unselected_icon_color,
                        "Icon");
        Icon = selectionColorSetType;
        SelectionColorSetType selectionColorSetType2 =
                new SelectionColorSetType(
                        1,
                        R.color.sec_widget_multi_button_selected_color,
                        R.color.sec_widget_multi_button_unselected_color,
                        "Text");
        Text = selectionColorSetType2;
        SelectionColorSetType[] selectionColorSetTypeArr = {
            selectionColorSetType, selectionColorSetType2
        };
        $VALUES = selectionColorSetTypeArr;
        EnumEntriesKt.enumEntries(selectionColorSetTypeArr);
    }

    public SelectionColorSetType(int i, int i2, int i3, String str) {
        this.selectedId = i2;
        this.unselectedId = i3;
    }

    public static SelectionColorSetType valueOf(String str) {
        return (SelectionColorSetType) Enum.valueOf(SelectionColorSetType.class, str);
    }

    public static SelectionColorSetType[] values() {
        return (SelectionColorSetType[]) $VALUES.clone();
    }

    public final int getColorId(boolean z) {
        return z ? this.selectedId : this.unselectedId;
    }
}
