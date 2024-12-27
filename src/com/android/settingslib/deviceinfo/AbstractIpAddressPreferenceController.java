package com.android.settingslib.deviceinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractIpAddressPreferenceController
        extends AbstractConnectivityPreferenceController {
    public static final String[] CONNECTIVITY_INTENTS = {
        "android.net.conn.CONNECTIVITY_CHANGE",
        "android.net.wifi.LINK_CONFIGURATION_CHANGED",
        "android.net.wifi.STATE_CHANGE",
        "android.intent.action.ANY_DATA_STATE"
    };
    static final String KEY_IP_ADDRESS = "wifi_ip_address";
    public final ConnectivityManager mCM;
    public Preference mIpAddress;

    public AbstractIpAddressPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
        this.mCM = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mIpAddress = preferenceScreen.findPreference(KEY_IP_ADDRESS);
        updateConnectivity();
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final String[] getConnectivityIntents() {
        return CONNECTIVITY_INTENTS;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_IP_ADDRESS;
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final void updateConnectivity() {
        ConnectivityManager connectivityManager = this.mCM;
        LinkProperties linkProperties =
                connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork());
        String str = null;
        if (linkProperties != null) {
            Iterator it = linkProperties.getAllLinkAddresses().iterator();
            if (it.hasNext()) {
                StringBuilder sb = new StringBuilder();
                while (it.hasNext()) {
                    sb.append(((LinkAddress) it.next()).getAddress().getHostAddress());
                    if (it.hasNext()) {
                        sb.append("\n");
                    }
                }
                str = sb.toString();
            }
        }
        if (str != null) {
            this.mIpAddress.setSummary(str);
        } else {
            this.mIpAddress.setSummary(R.string.status_unavailable);
        }
    }
}
