package com.samsung.android.settings.wifi.develop.btm;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BtmViewHolder extends RecyclerView.ViewHolder {
    public final Switch btmSwitch;
    public final TextView configKey;
    public String mConfigKey;
    public final TextView reason;

    public BtmViewHolder(final Context context, View view) {
        super(view);
        this.configKey = (TextView) view.findViewById(R.id.btm_disabled_ap);
        this.reason = (TextView) view.findViewById(R.id.btm_disabled_reason);
        Switch r0 = (Switch) view.findViewById(R.id.btm_disable_switch);
        this.btmSwitch = r0;
        r0.setOnCheckedChangeListener(
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.samsung.android.settings.wifi.develop.btm.BtmViewHolder$$ExternalSyntheticLambda0
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        BtmViewHolder btmViewHolder = BtmViewHolder.this;
                        Context context2 = context;
                        btmViewHolder.getClass();
                        Log.i("BtmViewHolder", "Set PERSONALIZED_CONN_OPTION config to " + z);
                        btmViewHolder.btmSwitch.setChecked(z);
                        if (z) {
                            btmViewHolder.reason.setText("BTM option changed");
                            String str = btmViewHolder.mConfigKey;
                            SemWifiManager semWifiManager =
                                    (SemWifiManager)
                                            context2.getSystemService(
                                                    WiFiManagerExt.SEM_WIFI_SERVICE);
                            if (semWifiManager != null) {
                                semWifiManager.setBtmOptionUserEnabled(str);
                                return;
                            }
                            return;
                        }
                        btmViewHolder.reason.setText("Default BTM option");
                        String str2 = btmViewHolder.mConfigKey;
                        SemWifiManager semWifiManager2 =
                                (SemWifiManager)
                                        context2.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
                        if (semWifiManager2 != null) {
                            semWifiManager2.setBtmOptionUserDisabled(str2);
                        }
                    }
                });
        view.findViewById(R.id.btm_switch_touch_area)
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.wifi.develop.btm.BtmViewHolder$$ExternalSyntheticLambda1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                BtmViewHolder.this.btmSwitch.performClick();
                            }
                        });
    }
}
