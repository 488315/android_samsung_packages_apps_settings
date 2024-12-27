package com.samsung.android.settings.wifi.develop.diagnosis.accesspoints;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class DiagnosisStepItem extends LinearLayout {
    public boolean isSummaryVisible;
    public TextView mSummary;
    public TextView mTimestamp;
    public TextView mTitle;

    public DiagnosisStepItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isSummaryVisible = false;
        ((LinearLayout) this).mContext = context;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mTimestamp = (TextView) findViewById(R.id.time);
        this.mSummary = (TextView) findViewById(R.id.summary);
        ((ImageView) findViewById(R.id.view_more))
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.wifi.develop.diagnosis.accesspoints.DiagnosisStepItem$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                DiagnosisStepItem diagnosisStepItem = DiagnosisStepItem.this;
                                if (diagnosisStepItem.isSummaryVisible) {
                                    diagnosisStepItem.isSummaryVisible = false;
                                    diagnosisStepItem.mSummary.setVisibility(8);
                                } else {
                                    diagnosisStepItem.isSummaryVisible = true;
                                    diagnosisStepItem.mSummary.setVisibility(0);
                                }
                            }
                        });
    }
}
