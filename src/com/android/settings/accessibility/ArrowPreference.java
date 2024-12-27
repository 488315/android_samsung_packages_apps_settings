package com.android.settings.accessibility;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.content.res.TypedArrayUtils;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ArrowPreference extends BackgroundPreference {
    public ArrowPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (this.mBackgroundId != R.drawable.settingslib_switch_bar_bg_on) {
            this.mBackgroundId = R.drawable.settingslib_switch_bar_bg_on;
            notifyChanged();
        }
        setWidgetLayoutResource(R.layout.preference_widget_arrow);
    }

    public ArrowPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ArrowPreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.preferenceStyle, android.R.attr.preferenceStyle));
    }

    public ArrowPreference(Context context) {
        this(context, null);
    }
}
