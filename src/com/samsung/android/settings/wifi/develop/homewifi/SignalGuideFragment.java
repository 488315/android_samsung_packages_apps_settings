package com.samsung.android.settings.wifi.develop.homewifi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.settings.R;

import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SignalGuideFragment extends Fragment {
    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        SemLog.d("HomeWifi.SignalGuideFragment", "onCreateView");
        return layoutInflater.inflate(
                R.layout.sec_wifi_development_homewifi_signal_guide_fragment, (ViewGroup) null);
    }
}
