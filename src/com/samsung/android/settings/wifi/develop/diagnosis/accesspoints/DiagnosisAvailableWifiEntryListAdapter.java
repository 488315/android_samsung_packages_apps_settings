package com.samsung.android.settings.wifi.develop.diagnosis.accesspoints;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.wifi.develop.diagnosis.WifiDiagnosisSettings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DiagnosisAvailableWifiEntryListAdapter extends RecyclerView.Adapter {
    public final Context mContext;
    public WifiDiagnosisSettings mListener;
    public List mSavedWifiEntries = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedWifiEntry {
        public WifiEntry mWifiEntry;
    }

    public DiagnosisAvailableWifiEntryListAdapter(Context context, List list) {
        this.mContext = context;
        updateAllAccessPoints(list);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List list = this.mSavedWifiEntries;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        DiagnosisWifiEntryHolder diagnosisWifiEntryHolder = (DiagnosisWifiEntryHolder) viewHolder;
        List list = this.mSavedWifiEntries;
        if (list == null || ((ArrayList) list).isEmpty()) {
            return;
        }
        DiagnosisWifiEntryItem diagnosisWifiEntryItem = diagnosisWifiEntryHolder.mCell;
        final WifiEntry wifiEntry =
                ((SavedWifiEntry) ((ArrayList) this.mSavedWifiEntries).get(i)).mWifiEntry;
        diagnosisWifiEntryItem.setWifiEntry(wifiEntry);
        diagnosisWifiEntryItem.mSsid.setTextColor(
                diagnosisWifiEntryItem.mContext.getColor(R.color.wifi_preference_title_color));
        diagnosisWifiEntryItem.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.develop.diagnosis.accesspoints.DiagnosisAvailableWifiEntryListAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DiagnosisAvailableWifiEntryListAdapter
                                diagnosisAvailableWifiEntryListAdapter =
                                        DiagnosisAvailableWifiEntryListAdapter.this;
                        WifiEntry wifiEntry2 = wifiEntry;
                        diagnosisAvailableWifiEntryListAdapter.getClass();
                        wifiEntry2.connect(null);
                        WifiDiagnosisSettings wifiDiagnosisSettings =
                                diagnosisAvailableWifiEntryListAdapter.mListener;
                        if (wifiDiagnosisSettings != null) {
                            Log.d("WifiDiagnosisSettings", "onStartConnecting");
                            wifiDiagnosisSettings.mStepEntry = wifiEntry2;
                            wifiDiagnosisSettings.updateStepLog();
                        }
                    }
                });
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

    public final void updateAllAccessPoints(Collection collection) {
        ArrayList arrayList = new ArrayList(this.mSavedWifiEntries);
        ((ArrayList) this.mSavedWifiEntries).clear();
        ArrayList arrayList2 = (ArrayList) collection;
        arrayList2.sort(WifiEntry.TITLE_COMPARATOR);
        final ArrayList arrayList3 = new ArrayList();
        if (arrayList2.size() != 0) {
            arrayList2.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.wifi.develop.diagnosis.accesspoints.DiagnosisAvailableWifiEntryListAdapter$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            List list = arrayList3;
                            WifiEntry wifiEntry = (WifiEntry) obj;
                            DiagnosisAvailableWifiEntryListAdapter.SavedWifiEntry savedWifiEntry =
                                    new DiagnosisAvailableWifiEntryListAdapter.SavedWifiEntry();
                            savedWifiEntry.mWifiEntry = wifiEntry;
                            wifiEntry.getTitle();
                            list.add(savedWifiEntry);
                        }
                    });
        }
        this.mSavedWifiEntries = arrayList3;
        Log.d(
                "DiagnosisAvailableWifiEntryListAdapter",
                "updateAllAccessPoints "
                        + ((ArrayList) this.mSavedWifiEntries).size()
                        + " "
                        + arrayList.size());
        notifyDataSetChanged();
    }
}
