package com.samsung.android.settings.wifi.advanced;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.core.SubSettingLauncher;
import com.android.wifitrackerlib.PasspointWifiEntry;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.details.WifiHotspot20Fragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class Hotspot20Preference extends Preference {
    public final Context mContext;
    public final String mFriendlyName;
    public final boolean mIsOAuth;
    public final OpenRoamingSettings mOpenRoamingSettings;
    public final PasspointConfiguration mPasspoint;
    public final String mScreenId;

    public Hotspot20Preference(Context context, PasspointConfiguration passpointConfiguration) {
        super(context);
        this.mContext = context;
        this.mPasspoint = passpointConfiguration;
        String friendlyName = passpointConfiguration.getHomeSp().getFriendlyName();
        this.mFriendlyName = friendlyName;
        this.mScreenId = "WIFI_260";
        this.mOpenRoamingSettings = new OpenRoamingSettings(context);
        this.mIsOAuth =
                TextUtils.equals(
                        "samsung.openroaming.net", passpointConfiguration.getHomeSp().getFqdn());
        setTitle(friendlyName);
    }

    public final void launchPasspointDetailsFragment() {
        Bundle m =
                AbsAdapter$1$$ExternalSyntheticOutline0.m(
                        "key_chosen_passpoint_key",
                        PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(
                                this.mPasspoint.getUniqueId()));
        m.putString("key_chosen_passpoint_friendly_name", this.mFriendlyName);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String canonicalName = WifiHotspot20Fragment.class.getCanonicalName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = canonicalName;
        launchRequest.mArguments = m;
        launchRequest.mSourceMetricsCategory = 106;
        launchRequest.mTitle = this.mFriendlyName;
        subSettingLauncher.launch();
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        boolean z;
        Log.d(
                "Hotspot20Preference",
                "Before calling EULA : isOAuth "
                        + this.mIsOAuth
                        + " isUserAllow "
                        + this.mOpenRoamingSettings.isUserAllowedOAuthAgreement());
        if (this.mIsOAuth) {
            if (this.mOpenRoamingSettings.isUserAllowedOAuthAgreement()) {
                launchPasspointDetailsFragment();
            } else {
                Intent intent = new Intent();
                intent.setPackage("com.samsung.android.net.wifi.wifiguider");
                intent.setAction("com.samsung.android.net.wifi.wifiguider.OPENROAMING_EULA");
                intent.putExtra("oauth_provider", "cisco");
                this.mContext.startActivity(intent);
            }
            z = true;
        } else {
            launchPasspointDetailsFragment();
            z = false;
        }
        if (TextUtils.isEmpty(this.mScreenId)) {
            return;
        }
        SALogging.insertSALog(z ? 1L : 0L, this.mScreenId, "1272", (String) null);
    }
}
