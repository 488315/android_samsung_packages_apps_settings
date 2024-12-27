package com.samsung.android.settings.wifi.develop.diagnosis.accesspoints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.settings.wifi.develop.history.model.HistoryLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DiagnosisStepLogListAdapter extends RecyclerView.Adapter {
    public final Context mContext;
    public List mLogs;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DiagnosisStepHolder extends RecyclerView.ViewHolder {
        public final DiagnosisStepItem mItem;

        public DiagnosisStepHolder(DiagnosisStepItem diagnosisStepItem) {
            super(diagnosisStepItem);
            this.mItem = diagnosisStepItem;
        }
    }

    public DiagnosisStepLogListAdapter(Context context) {
        this.mContext = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List list = this.mLogs;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        HistoryLog historyLog = (HistoryLog) this.mLogs.get(i);
        DiagnosisStepItem diagnosisStepItem = ((DiagnosisStepHolder) viewHolder).mItem;
        diagnosisStepItem.mTitle.setText(historyLog.type.toString());
        diagnosisStepItem.mTimestamp.setText(historyLog.time.toString());
        diagnosisStepItem.mSummary.setText(historyLog.data);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DiagnosisStepHolder(
                (DiagnosisStepItem)
                        LayoutInflater.from(this.mContext)
                                .inflate(R.layout.sec_diagnosis_step_layout, viewGroup, false));
    }
}
