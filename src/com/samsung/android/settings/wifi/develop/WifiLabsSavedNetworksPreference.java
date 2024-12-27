package com.samsung.android.settings.wifi.develop;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsNetworkInformationFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiLabsSavedNetworksPreference extends Preference {
    public PreferenceViewHolder mHolder;
    public int mSaved;
    public int mUnsecured;
    public int mUnused;

    /* renamed from: -$$Nest$mlaunchNetworkInformationFragment, reason: not valid java name */
    public static void m1332$$Nest$mlaunchNetworkInformationFragment(
            WifiLabsSavedNetworksPreference wifiLabsSavedNetworksPreference, String str) {
        wifiLabsSavedNetworksPreference.getClass();
        Bundle bundle = new Bundle();
        bundle.putString("filter", str);
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(wifiLabsSavedNetworksPreference.getContext());
        subSettingLauncher.setTitleRes(R.string.sec_wifi_network_information_title, null);
        String name = WifiLabsNetworkInformationFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mArguments = bundle;
        subSettingLauncher.launch();
    }

    public WifiLabsSavedNetworksPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSaved = -1;
        this.mUnused = -1;
        this.mUnsecured = -1;
        setLayoutResource(R.layout.sec_wifi_labs_saved_networks_preference);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mHolder = preferenceViewHolder;
        updatePreference(false, new int[] {this.mSaved, this.mUnused, this.mUnsecured});
    }

    public final void updatePreference(boolean z, int[] iArr) {
        this.mSaved = iArr[0];
        this.mUnused = iArr[1];
        this.mUnsecured = iArr[2];
        StringBuilder sb = new StringBuilder("saved - ");
        sb.append(this.mSaved);
        sb.append(", unused - ");
        sb.append(this.mUnused);
        sb.append(", unsecured - ");
        Preference$$ExternalSyntheticOutline0.m(
                sb, this.mUnsecured, "WifiLabsSavedNetworks.Preference");
        PreferenceViewHolder preferenceViewHolder = this.mHolder;
        if (preferenceViewHolder == null) {
            return;
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.saved_networks_count);
        TextView textView2 = (TextView) this.mHolder.findViewById(R.id.saved_networks_text);
        textView.setText(Integer.toString(this.mSaved));
        if (this.mSaved <= 1) {
            textView2.setText("Saved network");
        }
        final int i = 0;
        ((LinearLayout) this.mHolder.findViewById(R.id.saved_networks))
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.develop.WifiLabsSavedNetworksPreference.1
                            public final /* synthetic */ WifiLabsSavedNetworksPreference this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i) {
                                    case 0:
                                        Log.d(
                                                "WifiLabsSavedNetworks.Preference",
                                                "savedNetworksLayout clicked.");
                                        WifiLabsSavedNetworksPreference
                                                .m1332$$Nest$mlaunchNetworkInformationFragment(
                                                        this.this$0, "all");
                                        break;
                                    case 1:
                                        Log.d(
                                                "WifiLabsSavedNetworks.Preference",
                                                "unusedNetworksLayout clicked.");
                                        WifiLabsSavedNetworksPreference
                                                .m1332$$Nest$mlaunchNetworkInformationFragment(
                                                        this.this$0, "unused");
                                        break;
                                    default:
                                        Log.d(
                                                "WifiLabsSavedNetworks.Preference",
                                                "unsecureNetworksLayout clicked.");
                                        WifiLabsSavedNetworksPreference
                                                .m1332$$Nest$mlaunchNetworkInformationFragment(
                                                        this.this$0, "weak");
                                        break;
                                }
                            }
                        });
        final int i2 = 1;
        ((RelativeLayout) this.mHolder.findViewById(R.id.unused_networks))
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.develop.WifiLabsSavedNetworksPreference.1
                            public final /* synthetic */ WifiLabsSavedNetworksPreference this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i2) {
                                    case 0:
                                        Log.d(
                                                "WifiLabsSavedNetworks.Preference",
                                                "savedNetworksLayout clicked.");
                                        WifiLabsSavedNetworksPreference
                                                .m1332$$Nest$mlaunchNetworkInformationFragment(
                                                        this.this$0, "all");
                                        break;
                                    case 1:
                                        Log.d(
                                                "WifiLabsSavedNetworks.Preference",
                                                "unusedNetworksLayout clicked.");
                                        WifiLabsSavedNetworksPreference
                                                .m1332$$Nest$mlaunchNetworkInformationFragment(
                                                        this.this$0, "unused");
                                        break;
                                    default:
                                        Log.d(
                                                "WifiLabsSavedNetworks.Preference",
                                                "unsecureNetworksLayout clicked.");
                                        WifiLabsSavedNetworksPreference
                                                .m1332$$Nest$mlaunchNetworkInformationFragment(
                                                        this.this$0, "weak");
                                        break;
                                }
                            }
                        });
        ((TextView) this.mHolder.findViewById(R.id.unused_network_count))
                .setText(Integer.toString(this.mUnused));
        final int i3 = 2;
        ((RelativeLayout) this.mHolder.findViewById(R.id.unsecure_networks))
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.develop.WifiLabsSavedNetworksPreference.1
                            public final /* synthetic */ WifiLabsSavedNetworksPreference this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i3) {
                                    case 0:
                                        Log.d(
                                                "WifiLabsSavedNetworks.Preference",
                                                "savedNetworksLayout clicked.");
                                        WifiLabsSavedNetworksPreference
                                                .m1332$$Nest$mlaunchNetworkInformationFragment(
                                                        this.this$0, "all");
                                        break;
                                    case 1:
                                        Log.d(
                                                "WifiLabsSavedNetworks.Preference",
                                                "unusedNetworksLayout clicked.");
                                        WifiLabsSavedNetworksPreference
                                                .m1332$$Nest$mlaunchNetworkInformationFragment(
                                                        this.this$0, "unused");
                                        break;
                                    default:
                                        Log.d(
                                                "WifiLabsSavedNetworks.Preference",
                                                "unsecureNetworksLayout clicked.");
                                        WifiLabsSavedNetworksPreference
                                                .m1332$$Nest$mlaunchNetworkInformationFragment(
                                                        this.this$0, "weak");
                                        break;
                                }
                            }
                        });
        ((TextView) this.mHolder.findViewById(R.id.unsecure_network_count))
                .setText(Integer.toString(this.mUnsecured));
        if (z) {
            notifyChanged();
        }
    }

    public WifiLabsSavedNetworksPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiLabsSavedNetworksPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiLabsSavedNetworksPreference(Context context) {
        this(context, null);
    }
}
