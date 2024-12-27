package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.content.res.TypedArrayUtils;

import com.android.settings.R;
import com.android.settingslib.RestrictedPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecRestrictedPreference extends RestrictedPreference {
    public SecRestrictedPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.sec_preference_two_target);
    }

    public SecRestrictedPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecRestrictedPreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.preferenceStyle, android.R.attr.preferenceStyle));
    }

    public SecRestrictedPreference(Context context) {
        this(context, null);
    }
}
