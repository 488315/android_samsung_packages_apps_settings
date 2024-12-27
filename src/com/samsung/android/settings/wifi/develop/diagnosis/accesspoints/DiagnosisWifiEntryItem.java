package com.samsung.android.settings.wifi.develop.diagnosis.accesspoints;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;
import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class DiagnosisWifiEntryItem extends LinearLayout {
    public final Context mContext;
    public TextView mSsid;
    public TextView mSummary;
    public WifiEntry mWifiEntry;

    public DiagnosisWifiEntryItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSsid = (TextView) findViewById(R.id.title);
        this.mSummary = (TextView) findViewById(R.id.summary);
    }

    public final void setWifiEntry(WifiEntry wifiEntry) {
        this.mWifiEntry = wifiEntry;
        this.mSsid.setText(wifiEntry.getSsid());
        this.mSsid.setContentDescription(this.mWifiEntry.getSsid());
        if (TextUtils.isEmpty(this.mWifiEntry.getSummary(true))) {
            this.mSummary.setVisibility(8);
        } else {
            this.mSummary.setVisibility(0);
            this.mSummary.setText(this.mWifiEntry.getSummary(true));
        }
    }
}
