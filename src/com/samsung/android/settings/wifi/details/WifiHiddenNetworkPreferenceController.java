package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.widget.TextView;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiHiddenNetworkPreferenceController extends AbstractPreferenceController
        implements Preference.OnPreferenceChangeListener {
    public SwitchPreferenceCompat mHiddenPref;
    public boolean mHiddenSsid;
    public LayoutPreference mHiddenWarningPref;
    public final String mSAScreenId;
    public final WifiEntry mWifiEntry;

    public WifiHiddenNetworkPreferenceController(Context context, WifiEntry wifiEntry, String str) {
        super(context);
        this.mHiddenSsid = true;
        this.mWifiEntry = wifiEntry;
        this.mSAScreenId = str;
    }

    public final void disableViewsIfAppropriate$2() {
        SwitchPreferenceCompat switchPreferenceCompat;
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry == null || !wifiEntry.isSaved()) {
            return;
        }
        if ((WifiUtils.isBlockedByEnterprise(this.mContext, wifiEntry.getSsid())
                        || WifiUtils.isNetworkLockedDown(
                                this.mContext, wifiEntry.getWifiConfiguration()))
                && (switchPreferenceCompat = this.mHiddenPref) != null) {
            switchPreferenceCompat.setEnabled(false);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (this.mWifiEntry != null) {
            this.mHiddenPref = null;
        } else {
            SwitchPreferenceCompat switchPreferenceCompat =
                    (SwitchPreferenceCompat) preferenceScreen.findPreference("hidden");
            this.mHiddenPref = switchPreferenceCompat;
            switchPreferenceCompat.setOnPreferenceChangeListener(this);
            this.mHiddenPref.setChecked(this.mHiddenSsid);
        }
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference("hidden_warning_text");
        this.mHiddenWarningPref = layoutPreference;
        TextView textView =
                (TextView) layoutPreference.mRootView.findViewById(R.id.wifi_error_text);
        if (textView != null) {
            textView.setText(R.string.wifi_hidden_network_warning_message);
        }
        updateSummary(this.mHiddenSsid);
        disableViewsIfAppropriate$2();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "hidden";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mWifiEntry == null;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (!"hidden".equals(preference.getKey())) {
            return false;
        }
        this.mHiddenSsid = ((Boolean) obj).booleanValue();
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("onPreferenceChange checked : "),
                this.mHiddenSsid,
                "WifiHiddenNetworkPreferenceController");
        ((SwitchPreferenceCompat) preference).setChecked(this.mHiddenSsid);
        disableViewsIfAppropriate$2();
        updateSummary(this.mHiddenSsid);
        SALogging.insertSALog(this.mHiddenSsid ? 1L : 0L, this.mSAScreenId, "1044");
        return true;
    }

    public final void updateSummary(boolean z) {
        if (this.mHiddenPref != null) {
            this.mHiddenPref.setSummary(
                    !z
                            ? this.mContext
                                    .getResources()
                                    .getString(R.string.wifi_hidden_network_summary)
                            : null);
        }
        LayoutPreference layoutPreference = this.mHiddenWarningPref;
        if (layoutPreference != null) {
            if (this.mWifiEntry != null) {
                layoutPreference.setVisible(false);
            } else {
                layoutPreference.setVisible(z);
            }
        }
    }
}
