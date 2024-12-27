package com.samsung.android.settings.wifi.develop.nearbywifi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.settings.wifi.develop.nearbywifi.adapter.BssidListAdapter;
import com.samsung.android.settings.wifi.develop.nearbywifi.adapter.SsidListAdapter;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.SsidInfo;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class NearbyWifiPreference extends Preference {
    public BssidListAdapter mBssidListAdapter;
    public View mBssidTitle;
    public RecyclerView mNearbyWifiRecyclerView;
    public RadarView mRadarView;
    public Repository mRepo;
    public String mSelectedSsid;
    public TextView mSsid;
    public final NearbyWifiPreference$$ExternalSyntheticLambda0 mSsidClickListener;
    public SsidListAdapter mSsidListAdapter;
    public View mSsidTitle;

    public NearbyWifiPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSsidClickListener = new NearbyWifiPreference$$ExternalSyntheticLambda0(this);
        setLayoutResource(R.layout.sec_wifi_development_nearbywifi_fragment);
    }

    public final void changeMode() {
        if (this.mSsidTitle.getVisibility() != 0) {
            showSsidList();
            return;
        }
        Repository repository = this.mRepo;
        SsidInfo ssidInfo = (SsidInfo) repository.ssidMap.get(this.mSelectedSsid);
        this.mSsidTitle.setVisibility(8);
        this.mBssidTitle.setVisibility(0);
        String str = ssidInfo.ssid;
        this.mSelectedSsid = str;
        this.mSsid.setText(str);
        ArrayList arrayList = ssidInfo.bssids;
        BssidListAdapter bssidListAdapter = new BssidListAdapter();
        bssidListAdapter.mBssidList = arrayList;
        this.mBssidListAdapter = bssidListAdapter;
        this.mNearbyWifiRecyclerView.setAdapter(bssidListAdapter);
        RadarView radarView = this.mRadarView;
        Repository repository2 = this.mRepo;
        ArrayList arrayList2 = ssidInfo.bssids;
        repository2.getClass();
        radarView.mRadarUnits = Repository.createRadarUnitByBssid(arrayList2);
        radarView.invalidate();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mRepo = Repository.LazyHolder.INSTANCE;
        this.mNearbyWifiRecyclerView =
                (RecyclerView) preferenceViewHolder.itemView.findViewById(R.id.wifi_scan_recycler);
        ArrayList arrayList = this.mRepo.ssidList;
        SsidListAdapter ssidListAdapter = new SsidListAdapter();
        ssidListAdapter.mSsidList = arrayList;
        this.mSsidListAdapter = ssidListAdapter;
        ssidListAdapter.mListener = this.mSsidClickListener;
        this.mNearbyWifiRecyclerView.setAdapter(ssidListAdapter);
        this.mNearbyWifiRecyclerView.setLayoutManager(new LinearLayoutManager(1));
        RadarView radarView =
                (RadarView) preferenceViewHolder.itemView.findViewById(R.id.radarCanvas);
        this.mRadarView = radarView;
        radarView.mRadarUnits = this.mRepo.createRadarUnitBySsid();
        radarView.invalidate();
        this.mSsidTitle = preferenceViewHolder.itemView.findViewById(R.id.ssid_title);
        this.mBssidTitle = preferenceViewHolder.itemView.findViewById(R.id.bssid_title);
        this.mSsid = (TextView) preferenceViewHolder.itemView.findViewById(R.id.ssid);
    }

    public final void showSsidList() {
        View view;
        if (this.mSsidTitle == null || (view = this.mBssidTitle) == null) {
            return;
        }
        view.setVisibility(8);
        this.mSsidTitle.setVisibility(0);
        this.mNearbyWifiRecyclerView.setAdapter(this.mSsidListAdapter);
        RadarView radarView = this.mRadarView;
        radarView.mRadarUnits = this.mRepo.createRadarUnitBySsid();
        radarView.invalidate();
    }

    public NearbyWifiPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSsidClickListener = new NearbyWifiPreference$$ExternalSyntheticLambda0(this);
        setLayoutResource(R.layout.sec_wifi_development_nearbywifi_fragment);
    }

    public NearbyWifiPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSsidClickListener = new NearbyWifiPreference$$ExternalSyntheticLambda0(this);
        setLayoutResource(R.layout.sec_wifi_development_nearbywifi_fragment);
    }

    public NearbyWifiPreference(Context context) {
        super(context);
        this.mSsidClickListener = new NearbyWifiPreference$$ExternalSyntheticLambda0(this);
        setLayoutResource(R.layout.sec_wifi_development_nearbywifi_fragment);
    }
}
