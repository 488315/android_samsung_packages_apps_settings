package com.android.settings.development;

import android.content.Context;
import android.debug.PairDevice;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AdbPairedDevicePreference extends Preference {
    public PairDevice mPairedDevice;

    public AdbPairedDevicePreference(PairDevice pairDevice, Context context) {
        super(context);
        this.mPairedDevice = pairDevice;
        setWidgetLayoutResource(R.layout.preference_widget_gear_optional_background);
        PairDevice pairDevice2 = this.mPairedDevice;
        setTitle(pairDevice2.name);
        setSummary(
                pairDevice2.connected
                        ? getContext().getText(R.string.adb_wireless_device_connected_summary)
                        : ApnSettings.MVNO_NONE);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.settings_button);
        View findViewById2 = preferenceViewHolder.findViewById(R.id.settings_button_no_background);
        findViewById.setVisibility(4);
        findViewById2.setVisibility(0);
    }
}
