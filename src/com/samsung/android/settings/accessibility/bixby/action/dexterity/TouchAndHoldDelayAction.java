package com.samsung.android.settings.accessibility.bixby.action.dexterity;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TouchAndHoldDelayAction extends BixbyControllerAction {
    public final String[] OPTION = {"VeryShort", "Short", "Medium", "Long"};
    public final int[] OPTION_VALUE = {300, 500, 1000, 1500};

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doSetAction(Context context, ParsedBundle parsedBundle) {
        String str;
        Bundle bundle = new Bundle();
        String str2 = parsedBundle.menuValue;
        int i = Settings.Secure.getInt(context.getContentResolver(), "long_press_timeout", 500);
        int i2 = 0;
        while (true) {
            String[] strArr = this.OPTION;
            if (i2 >= strArr.length) {
                str = "fail";
                break;
            }
            if (strArr[i2].equals(str2)) {
                int[] iArr = this.OPTION_VALUE;
                if (iArr[i2] == i) {
                    str = "already_set";
                } else {
                    Settings.Secure.putInt(
                            context.getContentResolver(), "long_press_timeout", iArr[i2]);
                    str = "success";
                }
            } else {
                i2++;
            }
        }
        bundle.putString("result", str);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.dexterity.controllers.TouchAndHoldPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.samsung.android.settings.accessibility.dexterity.TouchAndHoldFragment";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final int getTitleRes() {
        return R.string.touch_and_hold_delay_header;
    }
}
