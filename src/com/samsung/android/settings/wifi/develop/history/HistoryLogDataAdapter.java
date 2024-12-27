package com.samsung.android.settings.wifi.develop.history;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.wifi.develop.history.model.HistoryLog;
import com.samsung.android.settings.wifi.develop.history.model.IssueDetectorLogParser;
import com.samsung.android.settings.wifi.develop.history.model.LogRepository;
import com.samsung.android.settings.wifi.develop.history.model.LogType;
import com.samsung.android.settings.wifi.develop.history.view.TotalHistoryPreference;

import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class HistoryLogDataAdapter extends RecyclerView.Adapter {
    public TotalHistoryPreference.AnonymousClass1 mItemClickListener;
    public final HashMap mLogSet = new HashMap();
    public ArrayList mTargetLog = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.develop.history.HistoryLogDataAdapter$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((HistoryLog) obj)
                    .time.compareTo((ChronoLocalDateTime<?>) ((HistoryLog) obj2).time);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HistoryLogViewHolder extends RecyclerView.ViewHolder {
        public final TextView data;
        public final TextView time;
        public final TextView type;

        /* renamed from: -$$Nest$mhandleClickEvent, reason: not valid java name */
        public static void m1338$$Nest$mhandleClickEvent(
                HistoryLogViewHolder historyLogViewHolder,
                TotalHistoryPreference.AnonymousClass1 anonymousClass1) {
            int bindingAdapterPosition = historyLogViewHolder.getBindingAdapterPosition();
            if (anonymousClass1 == null || bindingAdapterPosition == -1) {
                return;
            }
            TotalHistoryPreference totalHistoryPreference = TotalHistoryPreference.this;
            HistoryLog historyLog =
                    (HistoryLog)
                            totalHistoryPreference.mHistoryLogDataAdapter.mTargetLog.get(
                                    bindingAdapterPosition);
            Object obj = historyLog.type;
            LogType logType = LogType.WIFI_ON_OFF;
            if (obj != logType) {
                LogRepository logRepository = LogRepository.LazyHolder.INSTANCE;
                boolean equals = logType.equals(obj);
                String str = ApnSettings.MVNO_NONE;
                if (!equals) {
                    new ArrayList();
                    IssueDetectorLogParser.TryToConnectLog parseTryToConnectLog =
                            IssueDetectorLogParser.parseTryToConnectLog(historyLog);
                    if (!TextUtils.isEmpty(parseTryToConnectLog.ssid)) {
                        str = parseTryToConnectLog.ssid;
                    }
                }
                Intent intent = new Intent();
                intent.setClassName(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.android.settings.Settings$RouterHistoryActivity");
                intent.putExtra(":settings:fragment_args_key", str);
                intent.addFlags(268468224);
                totalHistoryPreference.mContext.startActivity(intent);
            }
        }

        public HistoryLogViewHolder(
                View view, final TotalHistoryPreference.AnonymousClass1 anonymousClass1) {
            super(view);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.history_layout);
            this.time = (TextView) view.findViewById(R.id.history_log_time);
            this.type = (TextView) view.findViewById(R.id.history_log_type);
            TextView textView = (TextView) view.findViewById(R.id.history_log_data);
            this.data = textView;
            final int i = 0;
            linearLayout.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.wifi.develop.history.HistoryLogDataAdapter.HistoryLogViewHolder.1
                        public final /* synthetic */ HistoryLogViewHolder this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            switch (i) {
                                case 0:
                                    HistoryLogViewHolder.m1338$$Nest$mhandleClickEvent(
                                            this.this$0, anonymousClass1);
                                    break;
                                default:
                                    HistoryLogViewHolder.m1338$$Nest$mhandleClickEvent(
                                            this.this$0, anonymousClass1);
                                    break;
                            }
                        }
                    });
            final int i2 = 1;
            textView.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.wifi.develop.history.HistoryLogDataAdapter.HistoryLogViewHolder.1
                        public final /* synthetic */ HistoryLogViewHolder this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            switch (i2) {
                                case 0:
                                    HistoryLogViewHolder.m1338$$Nest$mhandleClickEvent(
                                            this.this$0, anonymousClass1);
                                    break;
                                default:
                                    HistoryLogViewHolder.m1338$$Nest$mhandleClickEvent(
                                            this.this$0, anonymousClass1);
                                    break;
                            }
                        }
                    });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mTargetLog.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        HistoryLogViewHolder historyLogViewHolder = (HistoryLogViewHolder) viewHolder;
        HistoryLog historyLog = (HistoryLog) this.mTargetLog.get(i);
        historyLogViewHolder.time.setText(
                historyLog.time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        historyLogViewHolder.data.setText(historyLog.data);
        LogType logType = historyLog.type;
        String str = logType.toString();
        int ordinal = logType.ordinal();
        if (ordinal != 1) {
            if (ordinal != 2) {
                if (ordinal == 8) {
                    String str2 = historyLog.appName;
                    str =
                            historyLog.wifiState
                                    ? AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0
                                            .m("WIFI_ON by ", str2)
                                    : AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0
                                            .m("WIFI_OFF by ", str2);
                }
            } else if (!TextUtils.isEmpty(historyLog.targetSsid)) {
                str = logType.toString() + " to " + historyLog.targetSsid;
            }
        } else if (!TextUtils.isEmpty(historyLog.targetSsid)) {
            str = logType.toString() + " from " + historyLog.targetSsid;
        }
        historyLogViewHolder.type.setText(str);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new HistoryLogViewHolder(
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup,
                        R.layout.sec_wifi_development_history_log_data_item,
                        viewGroup,
                        false),
                this.mItemClickListener);
    }
}
