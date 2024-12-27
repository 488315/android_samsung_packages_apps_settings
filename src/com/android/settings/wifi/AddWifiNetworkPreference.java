package com.android.settings.wifi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settingslib.RestrictedPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AddWifiNetworkPreference extends RestrictedPreference {
    public final Drawable mScanIconDrawable;

    public AddWifiNetworkPreference(Context context) {
        this(context, null);
    }

    @Override // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageButton imageButton = (ImageButton) preferenceViewHolder.findViewById(R.id.button_icon);
        imageButton.setImageDrawable(this.mScanIconDrawable);
        imageButton.setContentDescription(getContext().getString(R.string.wifi_dpp_scan_qr_code));
        imageButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.wifi.AddWifiNetworkPreference$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AddWifiNetworkPreference addWifiNetworkPreference =
                                AddWifiNetworkPreference.this;
                        addWifiNetworkPreference
                                .getContext()
                                .startActivity(
                                        WifiDppUtils.getEnrolleeQrCodeScannerIntent(
                                                addWifiNetworkPreference.getContext(), null));
                    }
                });
    }

    public AddWifiNetworkPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Drawable drawable;
        setLayoutResource(R.layout.preference_access_point);
        setWidgetLayoutResource(R.layout.wifi_button_preference_widget);
        setIcon(R.drawable.ic_add_24dp);
        setTitle(R.string.wifi_add_network);
        try {
            drawable = getContext().getDrawable(R.drawable.ic_scan_24dp);
        } catch (Resources.NotFoundException unused) {
            Log.e("AddWifiNetworkPreference", "Resource does not exist: 2131232156");
            drawable = null;
        }
        this.mScanIconDrawable = drawable;
        checkRestrictionAndSetDisabled("no_add_wifi_config");
    }
}
