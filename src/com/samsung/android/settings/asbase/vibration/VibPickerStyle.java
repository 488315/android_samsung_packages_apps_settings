package com.samsung.android.settings.asbase.vibration;

import com.android.settings.R;
import java.util.HashMap;
import java.util.Map;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class VibPickerStyle {
    public static final /* synthetic */ VibPickerStyle[] $VALUES;
    public static final VibPickerStyle CALL_SETTING;
    public static final VibPickerStyle CALL_SETTING_RINGTONES;
    public static final VibPickerStyle SPLANNER;
    public static final Map packageMap;
    private final String packageName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.VibPickerStyle$1, reason: invalid class name */
    public enum AnonymousClass1 extends VibPickerStyle {
        @Override // com.samsung.android.settings.asbase.vibration.VibPickerStyle
        public final int getStyle() {
            return R.style.ThemeSPlanner;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.VibPickerStyle$2, reason: invalid class name */
    public enum AnonymousClass2 extends VibPickerStyle {
        @Override // com.samsung.android.settings.asbase.vibration.VibPickerStyle
        public final int getStyle() {
            return R.style.ThemeCallSetting;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.VibPickerStyle$3, reason: invalid class name */
    public enum AnonymousClass3 extends VibPickerStyle {
        @Override // com.samsung.android.settings.asbase.vibration.VibPickerStyle
        public final int getStyle() {
            return R.style.ThemeCallSetting;
        }
    }

    static {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1("SPLANNER", 0, "splanner");
        SPLANNER = anonymousClass1;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2("CALL_SETTING", 1, "call_setting");
        CALL_SETTING = anonymousClass2;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3("CALL_SETTING_RINGTONES", 2, "call_setting_ringtones");
        CALL_SETTING_RINGTONES = anonymousClass3;
        $VALUES = new VibPickerStyle[]{anonymousClass1, anonymousClass2, anonymousClass3};
        packageMap = new HashMap<String, VibPickerStyle>() { // from class: com.samsung.android.settings.asbase.vibration.VibPickerStyle.4
            {
                put("splanner", VibPickerStyle.SPLANNER);
                put("call_setting", VibPickerStyle.CALL_SETTING);
                put("call_setting_ringtones", VibPickerStyle.CALL_SETTING_RINGTONES);
            }
        };
    }

    public VibPickerStyle(String str, int i, String str2) {
        this.packageName = str2;
    }

    public static VibPickerStyle valueOf(String str) {
        return (VibPickerStyle) Enum.valueOf(VibPickerStyle.class, str);
    }

    public static VibPickerStyle[] values() {
        return (VibPickerStyle[]) $VALUES.clone();
    }

    public abstract int getStyle();
}
