package com.android.settings.network;

import android.graphics.drawable.Drawable;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConnectedEthernetNetworkController extends AbstractPreferenceController
        implements InternetUpdater.InternetChangeListener {
    public int mInternetType;
    public Preference mPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference("connected_ethernet_network");
        Drawable drawable = this.mContext.getDrawable(R.drawable.ic_settings_ethernet);
        if (drawable != null) {
            drawable.setTintList(
                    Utils.getColorAttr(this.mContext, android.R.attr.colorControlActivated));
            this.mPreference.setIcon(drawable);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "connected_ethernet_network";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mInternetType == 4;
    }

    @Override // com.android.settings.network.InternetUpdater.InternetChangeListener
    public final void onInternetTypeChanged(int i) {
        this.mInternetType = i;
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setVisible(isAvailable());
        }
    }
}
