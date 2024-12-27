package com.android.settings.notification;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RingtonePreferenceControllerBase extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public static void $r8$lambda$wz34Fh1d7xtSUshSAMQdGfTqG5g(
            RingtonePreferenceControllerBase ringtonePreferenceControllerBase,
            Preference preference) {
        try {
            String title =
                    Ringtone.getTitle(
                            ringtonePreferenceControllerBase.mContext,
                            RingtoneManager.getActualDefaultRingtoneUri(
                                    ringtonePreferenceControllerBase.mContext,
                                    ringtonePreferenceControllerBase.getRingtoneType()),
                            false,
                            true);
            if (title != null) {
                ThreadUtils.postOnMainThread(
                        new RingtonePreferenceControllerBase$$ExternalSyntheticLambda0(
                                preference, title));
            }
        } catch (IllegalArgumentException e) {
            Log.w("PrefControllerMixin", "Error getting ringtone summary.", e);
        }
    }

    public abstract int getRingtoneType();

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ThreadUtils.postOnBackgroundThread(
                new RingtonePreferenceControllerBase$$ExternalSyntheticLambda0(this, preference));
    }
}
