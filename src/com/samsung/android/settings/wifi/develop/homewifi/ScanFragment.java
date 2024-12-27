package com.samsung.android.settings.wifi.develop.homewifi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ScanFragment extends Fragment {
    public RecyclerView mRecyclerView;
    public Repository mRepo;
    public SsidListAdapter mSsidListAdapter;

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        SemLog.d("HomeWifi.ScanFragment", "onCreateView");
        View inflate =
                layoutInflater.inflate(
                        R.layout.sec_wifi_development_homewifi_scan_fragment, (ViewGroup) null);
        this.mRepo = Repository.LazyHolder.INSTANCE;
        this.mRecyclerView = (RecyclerView) inflate.findViewById(R.id.wifi_scan_recycler);
        if (this.mSsidListAdapter == null) {
            this.mSsidListAdapter = new SsidListAdapter(this.mRepo.ssidList);
        }
        this.mRecyclerView.setAdapter(this.mSsidListAdapter);
        return inflate;
    }
}
