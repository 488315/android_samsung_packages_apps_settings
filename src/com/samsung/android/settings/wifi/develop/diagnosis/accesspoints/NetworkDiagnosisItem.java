package com.samsung.android.settings.wifi.develop.diagnosis.accesspoints;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class NetworkDiagnosisItem extends LinearLayout {
    public final Context mContext;
    public TextView mSummary;
    public TextView mTitle;

    public NetworkDiagnosisItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mSummary = (TextView) findViewById(R.id.summary);
    }
}
