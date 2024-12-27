package com.samsung.android.settings.wifi.develop.history.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.settings.R;

import java.text.Format;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class HistoryView extends FrameLayout {
    public Format mDomainAxisFormat;
    public final TextView[] mDomainAxisLabels;
    public Object[] mDomainAxisValues;
    public final HistoryGraph mHistoryGraph;
    public final TextView mLegend1;
    public final TextView mLegend2;
    public long mMaxDomainValue;
    public final TextView[] mValueAxisLabels;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.develop.history.view.HistoryView$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    public HistoryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.wifi_history_view, this);
        HistoryGraph historyGraph = (HistoryGraph) findViewById(R.id.wifi_history_graph);
        this.mHistoryGraph = historyGraph;
        this.mValueAxisLabels =
                new TextView[] {
                    (TextView) findViewById(R.id.wifi_history_label_top),
                    (TextView) findViewById(R.id.wifi_history_label_middle),
                    (TextView) findViewById(R.id.wifi_history_label_bottom)
                };
        this.mDomainAxisLabels =
                new TextView[] {
                    (TextView) findViewById(R.id.wifi_history_label_start),
                    (TextView) findViewById(R.id.wifi_history_label_end)
                };
        TextView textView = (TextView) findViewById(R.id.wifi_history_legend1);
        this.mLegend1 = textView;
        textView.setVisibility(8);
        TextView textView2 = (TextView) findViewById(R.id.wifi_history_legend2);
        this.mLegend2 = textView2;
        textView2.setVisibility(8);
        historyGraph.mChartListener = new AnonymousClass1();
    }

    public final void clearPaths() {
        HistoryGraph historyGraph = this.mHistoryGraph;
        ((ArrayList) historyGraph.mPaths).clear();
        ((ArrayList) historyGraph.mLocalPaths).clear();
        ((ArrayList) historyGraph.mSecondPaths).clear();
        ((ArrayList) historyGraph.mLocalSecondPaths).clear();
    }

    public final void setValueAxisLabels(CharSequence[] charSequenceArr) {
        if (charSequenceArr.length != this.mValueAxisLabels.length) {
            throw new IllegalArgumentException("Invalid number of labels");
        }
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.mValueAxisLabels;
            if (i >= textViewArr.length) {
                return;
            }
            textViewArr[i].setText(charSequenceArr[i]);
            i++;
        }
    }
}
