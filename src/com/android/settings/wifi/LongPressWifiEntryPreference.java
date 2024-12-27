package com.android.settings.wifi;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceViewHolder;

import com.android.settingslib.RestrictedLockUtils;
import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LongPressWifiEntryPreference extends WifiEntryPreference {
    public final Fragment mFragment;

    public LongPressWifiEntryPreference(Context context, Fragment fragment, WifiEntry wifiEntry) {
        super(context, wifiEntry);
        this.mFragment = fragment;
        checkRestrictionAndSetDisabled();
    }

    public void checkRestrictionAndSetDisabled() {
        if (this.mWifiEntry.hasAdminRestrictions()) {
            Context context = getContext();
            RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner =
                    context != null
                            ? RestrictedLockUtils.getProfileOrDeviceOwner(
                                    context, null, context.getUser())
                            : null;
            if (profileOrDeviceOwner == null) {
                profileOrDeviceOwner = new RestrictedLockUtils.EnforcedAdmin();
                profileOrDeviceOwner.enforcedRestriction = "no_add_wifi_config";
            }
            setDisabledByAdmin(profileOrDeviceOwner);
        }
    }

    @Override // com.android.settings.wifi.WifiEntryPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Fragment fragment = this.mFragment;
        if (fragment != null) {
            preferenceViewHolder.itemView.setOnCreateContextMenuListener(fragment);
            preferenceViewHolder.itemView.setTag(this);
            preferenceViewHolder.itemView.setLongClickable(true);
        }
    }

    @Override // com.android.settings.wifi.WifiEntryPreference
    public final void refresh() {
        super.refresh();
        setEnabled(shouldEnabled());
    }

    public boolean shouldEnabled() {
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry == null) {
            return false;
        }
        boolean canConnect = wifiEntry.canConnect();
        if (canConnect) {
            return canConnect;
        }
        if (wifiEntry.canDisconnect() || wifiEntry.isSaved()) {
            return true;
        }
        return canConnect;
    }
}
