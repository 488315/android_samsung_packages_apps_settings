package com.samsung.android.settings.wifi.mobileap;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.android.settings.R;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApVzwHelp extends Fragment {
    public static final Logger LOGGER = Logger.getLogger(WifiApVzwHelp.class.getName());
    public View mView;

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_wifi_ap_help_vzw, (ViewGroup) null);
        this.mView = inflate;
        ((Button) inflate.findViewById(R.id.button_start_help))
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.wifi.mobileap.WifiApVzwHelp.1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                Intent intent = new Intent("android.intent.ACTION_MAIN");
                                intent.addCategory("android.intent.category.DEFAULT");
                                intent.setClassName(
                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                        "com.android.settings.wifi.mobileap.WifiApSettings");
                                intent.setFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                intent.putExtra("fromHelp", true);
                                try {
                                    WifiApVzwHelp.this.startActivity(intent);
                                } catch (Exception e) {
                                    WifiApVzwHelp.LOGGER.log(
                                            Level.SEVERE, "WifiApVzwHelp", (Throwable) e);
                                }
                            }
                        });
        return this.mView;
    }

    @Override // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {}
}
