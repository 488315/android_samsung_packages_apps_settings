package com.android.settings.vpn2;

import android.view.View;

import androidx.preference.Preference;

import com.android.internal.net.VpnProfile;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LegacyVpnPreference extends ManageablePreference {
    public VpnProfile mProfile;

    @Override // com.android.settings.widget.GearPreference, android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.settings_button && this.mHelper.mDisabledByAdmin) {
            performClick();
        } else {
            super.onClick(view);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.preference.Preference, java.lang.Comparable
    public final int compareTo(Preference preference) {
        if (!(preference instanceof LegacyVpnPreference)) {
            if (preference instanceof AppPreference) {
                return (this.mState == 3 || ((AppPreference) preference).mState != 3) ? -1 : 1;
            }
            return super.compareTo(preference);
        }
        LegacyVpnPreference legacyVpnPreference = (LegacyVpnPreference) preference;
        int i = legacyVpnPreference.mState - this.mState;
        if (i != 0) {
            return i;
        }
        int compareToIgnoreCase =
                this.mProfile.name.compareToIgnoreCase(legacyVpnPreference.mProfile.name);
        if (compareToIgnoreCase != 0) {
            return compareToIgnoreCase;
        }
        VpnProfile vpnProfile = this.mProfile;
        int i2 = vpnProfile.type;
        VpnProfile vpnProfile2 = legacyVpnPreference.mProfile;
        int i3 = i2 - vpnProfile2.type;
        return i3 == 0 ? vpnProfile.key.compareTo(vpnProfile2.key) : i3;
    }
}
