package com.android.settings.deletionhelper;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.text.format.Formatter;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.FooterPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutomaticStorageManagerDescriptionPreferenceController
        extends AbstractPreferenceController implements PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        FooterPreference footerPreference =
                (FooterPreference) preferenceScreen.findPreference("freed_bytes");
        Context context = footerPreference.getContext();
        ContentResolver contentResolver = context.getContentResolver();
        long j =
                Settings.Secure.getLong(
                        contentResolver, "automatic_storage_manager_bytes_cleared", 0L);
        long j2 =
                Settings.Secure.getLong(contentResolver, "automatic_storage_manager_last_run", 0L);
        if (j == 0 || j2 == 0 || !Utils.isStorageManagerEnabled(context)) {
            footerPreference.setTitle(R.string.automatic_storage_manager_text);
        } else {
            footerPreference.setTitle(
                    context.getString(
                            R.string.automatic_storage_manager_freed_bytes,
                            Formatter.formatFileSize(context, j),
                            DateUtils.formatDateTime(context, j2, 16)));
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "freed_bytes";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }
}
