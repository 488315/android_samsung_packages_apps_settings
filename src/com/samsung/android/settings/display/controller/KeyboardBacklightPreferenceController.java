package com.samsung.android.settings.display.controller;

import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.settings.Rune;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class KeyboardBacklightPreferenceController extends SecDisplayPreferenceBaseController
        implements PreferenceControllerMixin {
    public SecPreferenceScreen mPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference = (SecPreferenceScreen) preferenceScreen.findPreference("key_backlight");
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "key_backlight";
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final ArrayList getUriListRequiringObservation() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(Settings.System.getUriFor("key_backlight_timeout"));
        return arrayList;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return false;
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final void updatePreference$11() {
        String str;
        if (this.mPreference == null) {
            return;
        }
        String[] stringArray =
                this.mContext.getResources().getStringArray(R.array.key_backlight_values);
        String[] stringArray2 =
                this.mContext.getResources().getStringArray(R.array.key_backlight_entries);
        String valueOf =
                String.valueOf(
                        Settings.System.getInt(
                                this.mContentResolver, "key_backlight_timeout", 3000));
        if (stringArray2.length <= 0 || (str = stringArray2[1]) == null) {
            str = null;
        }
        int i = 0;
        while (true) {
            if (i < stringArray2.length) {
                String str2 = stringArray[i];
                if (str2 != null && str2.equals(valueOf)) {
                    str = stringArray2[i];
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        this.mPreference.setSummary(str);
    }
}
