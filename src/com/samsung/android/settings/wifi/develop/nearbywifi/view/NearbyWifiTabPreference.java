package com.samsung.android.settings.wifi.develop.nearbywifi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.google.android.material.tabs.TabLayout;
import com.samsung.android.settings.wifi.develop.OnTabSelectedListener;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class NearbyWifiTabPreference extends Preference {
    public OnTabSelectedListener mOnTabSelectedListener;
    public TabLayout mTabLayout;

    public NearbyWifiTabPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.sec_wifi_development_nearbywifi_tab_preference);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Log.i("NearbyWifi.TabPreference", "onBindViewHolder: mTabLayout=" + this.mTabLayout);
        if (this.mTabLayout == null) {
            TabLayout tabLayout = (TabLayout) preferenceViewHolder.findViewById(R.id.tab_layout);
            this.mTabLayout = tabLayout;
            tabLayout.seslSetSubTabStyle();
            this.mTabLayout.addOnTabSelectedListener$1(
                    new TabLayout
                            .OnTabSelectedListener() { // from class:
                                                       // com.samsung.android.settings.wifi.develop.nearbywifi.view.NearbyWifiTabPreference.1
                        @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                        public final void onTabSelected(TabLayout.Tab tab) {
                            int i = tab.position;
                            MainClearConfirm$$ExternalSyntheticOutline0.m(
                                    i, "onTabSelected: ", "NearbyWifi.TabPreference");
                            OnTabSelectedListener onTabSelectedListener =
                                    NearbyWifiTabPreference.this.mOnTabSelectedListener;
                            if (onTabSelectedListener != null) {
                                onTabSelectedListener.onTabSelected(i);
                            }
                        }

                        @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                        public final void onTabUnselected(TabLayout.Tab tab) {}
                    });
        }
    }

    public NearbyWifiTabPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NearbyWifiTabPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NearbyWifiTabPreference(Context context) {
        this(context, null);
    }
}
