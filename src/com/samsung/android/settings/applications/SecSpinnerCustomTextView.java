package com.samsung.android.settings.applications;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSpinnerCustomTextView extends AppCompatTextView {
    public SecSpinnerCustomTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Utils.setMaxFontScale(context, this);
    }
}
