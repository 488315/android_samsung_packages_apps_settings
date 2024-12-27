package com.samsung.android.settings.wifi.develop.diagnosis.accesspoints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DiagnosisConnectedWifiEntryListAdapter extends RecyclerView.Adapter {
    public final Context mContext;
    public WifiEntry mWifiEntry;

    public DiagnosisConnectedWifiEntryListAdapter(Context context) {
        this.mContext = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        DiagnosisWifiEntryItem diagnosisWifiEntryItem =
                ((DiagnosisWifiEntryHolder) viewHolder).mCell;
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry == null) {
            return;
        }
        diagnosisWifiEntryItem.setWifiEntry(wifiEntry);
        diagnosisWifiEntryItem.mSsid.setTextColor(
                diagnosisWifiEntryItem.mContext.getColor(R.color.sec_highlight_text_color));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DiagnosisWifiEntryHolder(
                (DiagnosisWifiEntryItem)
                        LayoutInflater.from(this.mContext)
                                .inflate(
                                        R.layout.sec_diagnosis_access_point_cell,
                                        viewGroup,
                                        false));
    }
}
