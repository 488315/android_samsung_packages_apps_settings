package com.samsung.android.settings.development;

import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class StatusLoggingResultPreferenceController
        extends DeveloperOptionsPreferenceController implements PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "status_logging_result";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals("status_logging_result", preference.getKey())) {
            return false;
        }
        SALogging.getInstance(preference.getContext()).processStatusLogging(true);
        Toast.makeText(
                        preference.getContext(),
                        "Status Logging Result generation has been completed",
                        0)
                .show();
        return true;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !TextUtils.equals("user", Build.TYPE);
    }
}
