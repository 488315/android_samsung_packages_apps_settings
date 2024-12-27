package com.samsung.android.settings.development;

import android.os.SystemProperties;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EnableMessageSandboxingImageTaggingPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "enable_message_sandboxing_image_tagging";
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        try {
            SystemProperties.set(
                    "persist.kumiho.watermark", ((Boolean) obj).booleanValue() ? "true" : "false");
            return true;
        } catch (IllegalArgumentException e) {
            Log.e(
                    "PrefControllerMixin",
                    "Fail to set message sandboxing image tagging property: " + e.getMessage());
            return true;
        }
    }

    @Override // com.android.settings.core.PreferenceControllerMixin
    public final void updateNonIndexableKeys(List list) {
        list.add("enable_message_sandboxing_image_tagging");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        try {
            preference.setVisible(false);
        } catch (IllegalArgumentException e) {
            Log.e(
                    "PrefControllerMixin",
                    "Fail to get message sandboxing image tagging property: " + e.getMessage());
        }
    }
}
