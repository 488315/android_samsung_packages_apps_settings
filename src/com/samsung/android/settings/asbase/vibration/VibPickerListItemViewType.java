package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.settings.R;
import java.util.HashMap;
import java.util.Map;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class VibPickerListItemViewType {
    public static final /* synthetic */ VibPickerListItemViewType[] $VALUES;
    public static final VibPickerListItemViewType BOTTOM_VIEW;
    public static final VibPickerListItemViewType FOOTER_VIEW;
    public static final VibPickerListItemViewType MIDDLE_VIEW;
    public static final VibPickerListItemViewType TOP_VIEW;
    public static final Map typeMap;
    private final int type;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType$1, reason: invalid class name */
    public enum AnonymousClass1 extends VibPickerListItemViewType {
        @Override // com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType
        public final int getRoundMode() {
            return 3;
        }

        @Override // com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType
        public final View getView(LayoutInflater layoutInflater, ViewGroup viewGroup, Context context) {
            return layoutInflater.inflate(R.layout.sec_vibpicker_list_item_single_choice, viewGroup, false);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType$2, reason: invalid class name */
    public enum AnonymousClass2 extends VibPickerListItemViewType {
        @Override // com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType
        public final int getRoundMode() {
            return 0;
        }

        @Override // com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType
        public final View getView(LayoutInflater layoutInflater, ViewGroup viewGroup, Context context) {
            return layoutInflater.inflate(R.layout.sec_vibpicker_list_item_single_choice, viewGroup, false);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType$3, reason: invalid class name */
    public enum AnonymousClass3 extends VibPickerListItemViewType {
        @Override // com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType
        public final int getRoundMode() {
            return 12;
        }

        @Override // com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType
        public final View getView(LayoutInflater layoutInflater, ViewGroup viewGroup, Context context) {
            View inflate = layoutInflater.inflate(R.layout.sec_vibpicker_list_item_single_choice, viewGroup, false);
            inflate.semSetRoundedCorners(12);
            inflate.semSetRoundedCornerColor(12, context.getColor(R.color.sec_widget_round_and_bgcolor));
            return inflate;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType$4, reason: invalid class name */
    public enum AnonymousClass4 extends VibPickerListItemViewType {
        @Override // com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType
        public final int getRoundMode() {
            return 0;
        }

        @Override // com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType
        public final View getView(LayoutInflater layoutInflater, ViewGroup viewGroup, Context context) {
            return layoutInflater.inflate(R.layout.sec_vibpicker_footer, viewGroup, false);
        }
    }

    static {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1("TOP_VIEW", 0, 0);
        TOP_VIEW = anonymousClass1;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2("MIDDLE_VIEW", 1, 1);
        MIDDLE_VIEW = anonymousClass2;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3("BOTTOM_VIEW", 2, 2);
        BOTTOM_VIEW = anonymousClass3;
        AnonymousClass4 anonymousClass4 = new AnonymousClass4("FOOTER_VIEW", 3, 3);
        FOOTER_VIEW = anonymousClass4;
        $VALUES = new VibPickerListItemViewType[]{anonymousClass1, anonymousClass2, anonymousClass3, anonymousClass4};
        typeMap = new HashMap<Integer, VibPickerListItemViewType>() { // from class: com.samsung.android.settings.asbase.vibration.VibPickerListItemViewType.5
            {
                put(0, VibPickerListItemViewType.TOP_VIEW);
                put(1, VibPickerListItemViewType.MIDDLE_VIEW);
                put(2, VibPickerListItemViewType.BOTTOM_VIEW);
                put(3, VibPickerListItemViewType.FOOTER_VIEW);
            }
        };
    }

    public VibPickerListItemViewType(String str, int i, int i2) {
        this.type = i2;
    }

    public static int getViewTypeByPosition(int i, int i2) {
        return i == 0 ? TOP_VIEW.type : i == i2 + (-2) ? BOTTOM_VIEW.type : i == i2 + (-1) ? FOOTER_VIEW.type : MIDDLE_VIEW.type;
    }

    public static VibPickerListItemViewType valueOf(String str) {
        return (VibPickerListItemViewType) Enum.valueOf(VibPickerListItemViewType.class, str);
    }

    public static VibPickerListItemViewType[] values() {
        return (VibPickerListItemViewType[]) $VALUES.clone();
    }

    public abstract int getRoundMode();

    public final int getValue() {
        return this.type;
    }

    public abstract View getView(LayoutInflater layoutInflater, ViewGroup viewGroup, Context context);
}
