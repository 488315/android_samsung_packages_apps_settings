package com.android.settings.network;

import android.content.Context;
import android.os.UserHandle;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.general.GeneralUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkResetPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public NetworkResetPreferenceController(Context context) {
        super(context);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "network_reset_pref";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !Utils.isWifiOnly(this.mContext)
                && SubscriptionUtil.isSimHardwareVisible(this.mContext)
                && UserHandle.myUserId() == 0;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        if (GeneralUtils.isResetEnabled(this.mContext)) {
            return;
        }
        preference.setEnabled(false);
    }
}
