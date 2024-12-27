package com.android.settings.development.autofill;

import android.content.ContentResolver;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.autofill.AutofillManager;
import android.widget.Toast;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutofillResetOptionsPreferenceController
        extends DeveloperOptionsPreferenceController implements PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "autofill_reset_developer_options";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals("autofill_reset_developer_options", preference.getKey())) {
            return false;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Settings.Global.putInt(
                contentResolver, "autofill_logging_level", AutofillManager.DEFAULT_LOGGING_LEVEL);
        Settings.Global.putInt(contentResolver, "autofill_max_partitions_size", 10);
        Settings.Global.putInt(contentResolver, "autofill_max_visible_datasets", 0);
        Toast.makeText(this.mContext, R.string.autofill_reset_developer_options_complete, 0).show();
        return true;
    }
}
