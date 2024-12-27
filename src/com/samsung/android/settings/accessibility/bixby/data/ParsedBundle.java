package com.samsung.android.settings.accessibility.bixby.data;

import android.os.Bundle;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ParsedBundle {
    public final String actionName;
    public final String bixbyLocale;
    public final String level;
    public final String levelValue;
    public final String menuValue;
    public final String paramValue;
    public final String unit;

    public ParsedBundle(Bundle bundle) {
        this.actionName = bundle.getString("ActionName", ApnSettings.MVNO_NONE);
        this.bixbyLocale = bundle.getString("BixbyLocale", ApnSettings.MVNO_NONE);
        this.level = bundle.getString("Level", ApnSettings.MVNO_NONE);
        this.levelValue = bundle.getString("LevelValue", ApnSettings.MVNO_NONE);
        this.menuValue = bundle.getString("menuValue", ApnSettings.MVNO_NONE);
        this.unit = bundle.getString("Unit", ApnSettings.MVNO_NONE);
        if (bundle.containsKey("AccessibilityMenu")) {
            this.paramValue = bundle.getString("AccessibilityMenu", ApnSettings.MVNO_NONE);
            return;
        }
        if (bundle.containsKey("FlashNotificationOption")) {
            this.paramValue = bundle.getString("FlashNotificationOption", ApnSettings.MVNO_NONE);
        } else if (bundle.containsKey("SoundDetectorOption")) {
            this.paramValue = bundle.getString("SoundDetectorOption", ApnSettings.MVNO_NONE);
        } else {
            this.paramValue = ApnSettings.MVNO_NONE;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ParsedBundle[{");
        String str = ApnSettings.MVNO_NONE;
        String str2 = this.paramValue;
        sb.append(
                ApnSettings.MVNO_NONE.equals(str2)
                        ? ApnSettings.MVNO_NONE
                        : ComposerKt$$ExternalSyntheticOutline0.m("paramValue=", str2, ", "));
        String str3 = this.actionName;
        sb.append(
                ApnSettings.MVNO_NONE.equals(str3)
                        ? ApnSettings.MVNO_NONE
                        : ComposerKt$$ExternalSyntheticOutline0.m("actionName=", str3, ", "));
        String str4 = this.menuValue;
        sb.append(
                ApnSettings.MVNO_NONE.equals(str4)
                        ? ApnSettings.MVNO_NONE
                        : ComposerKt$$ExternalSyntheticOutline0.m("menuValue=", str4, ", "));
        String str5 = this.unit;
        sb.append(
                ApnSettings.MVNO_NONE.equals(str5)
                        ? ApnSettings.MVNO_NONE
                        : ComposerKt$$ExternalSyntheticOutline0.m("unit=", str5, ", "));
        String str6 = this.level;
        sb.append(
                ApnSettings.MVNO_NONE.equals(str6)
                        ? ApnSettings.MVNO_NONE
                        : ComposerKt$$ExternalSyntheticOutline0.m("level=", str6, ", "));
        String str7 = this.levelValue;
        sb.append(
                ApnSettings.MVNO_NONE.equals(str7)
                        ? ApnSettings.MVNO_NONE
                        : ComposerKt$$ExternalSyntheticOutline0.m("levelValue=", str7, ", "));
        String str8 = this.bixbyLocale;
        if (!ApnSettings.MVNO_NONE.equals(str8)) {
            str = ComposerKt$$ExternalSyntheticOutline0.m("bixbyLocale=", str8, ", ");
        }
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, str, "}]");
    }
}
