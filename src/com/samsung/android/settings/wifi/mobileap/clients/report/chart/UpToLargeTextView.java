package com.samsung.android.settings.wifi.mobileap.clients.report.chart;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class UpToLargeTextView extends AppCompatTextView {
    public final DwUpToLarge mUpToLarge;

    public UpToLargeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mUpToLarge = new DwUpToLarge();
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        DwUpToLarge dwUpToLarge = this.mUpToLarge;
        dwUpToLarge.getClass();
        if (configuration != null) {
            float f = configuration.fontScale;
            if (f != dwUpToLarge.mCurrentFontScale) {
                dwUpToLarge.mCurrentFontScale = f;
                if (f > 1.2f) {
                    dwUpToLarge.mCurrentFontScale = 1.2f;
                }
                setTextSize(0, dwUpToLarge.mDefaultTextSize * dwUpToLarge.mCurrentFontScale);
            }
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        DwUpToLarge dwUpToLarge = this.mUpToLarge;
        dwUpToLarge.getClass();
        dwUpToLarge.mCurrentFontScale = getResources().getConfiguration().fontScale;
        float textSize = getTextSize();
        float f = dwUpToLarge.mCurrentFontScale;
        float f2 = textSize / f;
        dwUpToLarge.mDefaultTextSize = f2;
        if (f > 1.2f) {
            dwUpToLarge.mCurrentFontScale = 1.2f;
        }
        setTextSize(0, f2 * dwUpToLarge.mCurrentFontScale);
    }

    public UpToLargeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mUpToLarge = new DwUpToLarge();
    }

    public UpToLargeTextView(Context context) {
        super(context, null);
        this.mUpToLarge = new DwUpToLarge();
    }
}
