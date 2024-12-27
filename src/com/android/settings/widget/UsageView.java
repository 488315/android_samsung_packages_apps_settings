package com.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.R$styleable;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UsageView extends FrameLayout {
    public final TextView[] mBottomLabels;
    public final TextView[] mLabels;
    public final UsageGraph mUsageGraph;

    public UsageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.usage_view, this);
        this.mUsageGraph = (UsageGraph) findViewById(R.id.usage_graph);
        TextView[] textViewArr = {
            (TextView) findViewById(R.id.label_bottom),
            (TextView) findViewById(R.id.label_middle),
            (TextView) findViewById(R.id.label_top)
        };
        this.mLabels = textViewArr;
        this.mBottomLabels =
                new TextView[] {
                    (TextView) findViewById(R.id.label_start),
                    (TextView) findViewById(R.id.label_end)
                };
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.UsageView, 0, 0);
        if (obtainStyledAttributes.hasValue(3)) {
            setSideLabels(obtainStyledAttributes.getTextArray(3));
        }
        if (obtainStyledAttributes.hasValue(2)) {
            setBottomLabels(obtainStyledAttributes.getTextArray(2));
        }
        if (obtainStyledAttributes.hasValue(4)) {
            int color = obtainStyledAttributes.getColor(4, 0);
            for (TextView textView : textViewArr) {
                textView.setTextColor(color);
            }
            for (TextView textView2 : this.mBottomLabels) {
                textView2.setTextColor(color);
            }
        }
        if (obtainStyledAttributes.hasValue(0)) {
            int i = obtainStyledAttributes.getInt(0, 0);
            if (i == 8388613) {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.graph_label_group);
                LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.label_group);
                linearLayout.removeView(linearLayout2);
                linearLayout.addView(linearLayout2);
                linearLayout2.setGravity(8388613);
                LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.bottom_label_group);
                View findViewById = linearLayout3.findViewById(R.id.bottom_label_space);
                linearLayout3.removeView(findViewById);
                linearLayout3.addView(findViewById);
            } else if (i != 8388611) {
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Unsupported gravity "));
            }
        }
        this.mUsageGraph.setAccentColor(obtainStyledAttributes.getColor(1, 0));
        obtainStyledAttributes.recycle();
        String language = Locale.getDefault().getLanguage();
        if (TextUtils.equals(language, new Locale("fa").getLanguage())
                || TextUtils.equals(language, new Locale("ur").getLanguage())) {
            findViewById(R.id.graph_label_group).setLayoutDirection(0);
            findViewById(R.id.bottom_label_group).setLayoutDirection(0);
        }
    }

    public void setAccentColor(int i) {
        this.mUsageGraph.setAccentColor(i);
    }

    public void setBottomLabels(CharSequence[] charSequenceArr) {
        if (charSequenceArr.length != this.mBottomLabels.length) {
            throw new IllegalArgumentException("Invalid number of labels");
        }
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.mBottomLabels;
            if (i >= textViewArr.length) {
                return;
            }
            textViewArr[i].setText(charSequenceArr[i]);
            i++;
        }
    }

    public void setDividerLoc(int i) {
        this.mUsageGraph.setDividerLoc(i);
    }

    public void setSideLabels(CharSequence[] charSequenceArr) {
        if (charSequenceArr.length != this.mLabels.length) {
            throw new IllegalArgumentException("Invalid number of labels");
        }
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.mLabels;
            if (i >= textViewArr.length) {
                return;
            }
            textViewArr[i].setText(charSequenceArr[i]);
            i++;
        }
    }
}
