package com.android.settings.wifi;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.network.NetworkProviderSettings;
import com.android.settings.network.NetworkProviderSettings$$ExternalSyntheticLambda8;
import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConnectedWifiEntryPreference extends LongPressWifiEntryPreference
        implements View.OnClickListener {
    public NetworkProviderSettings$$ExternalSyntheticLambda8 mOnGearClickListener;

    public ConnectedWifiEntryPreference(Context context, Fragment fragment, WifiEntry wifiEntry) {
        super(context, fragment, wifiEntry);
        setWidgetLayoutResource(R.layout.preference_widget_gear_optional_background);
    }

    @Override // com.android.settings.wifi.LongPressWifiEntryPreference,
              // com.android.settings.wifi.WifiEntryPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.settings_button);
        findViewById.setOnClickListener(this);
        boolean canSignIn = this.mWifiEntry.canSignIn();
        preferenceViewHolder
                .findViewById(R.id.settings_button_no_background)
                .setVisibility(canSignIn ? 4 : 0);
        findViewById.setVisibility(canSignIn ? 0 : 4);
        preferenceViewHolder.findViewById(R.id.two_target_divider).setVisibility(canSignIn ? 0 : 4);
    }

    @Override // com.android.settings.wifi.WifiEntryPreference, android.view.View.OnClickListener
    public final void onClick(View view) {
        NetworkProviderSettings$$ExternalSyntheticLambda8
                networkProviderSettings$$ExternalSyntheticLambda8;
        if (view.getId() != R.id.settings_button
                || (networkProviderSettings$$ExternalSyntheticLambda8 = this.mOnGearClickListener)
                        == null) {
            return;
        }
        networkProviderSettings$$ExternalSyntheticLambda8.getClass();
        NetworkProviderSettings.SearchIndexProvider searchIndexProvider =
                NetworkProviderSettings.SEARCH_INDEX_DATA_PROVIDER;
        networkProviderSettings$$ExternalSyntheticLambda8.f$0.launchNetworkDetailsFragment(
                networkProviderSettings$$ExternalSyntheticLambda8.f$1);
    }
}
