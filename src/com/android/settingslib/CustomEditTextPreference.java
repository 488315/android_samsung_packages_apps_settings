package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.EditTextPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Deprecated
/* loaded from: classes2.dex */
public class CustomEditTextPreference extends EditTextPreference {
    public CustomEditTextPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public CustomEditTextPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomEditTextPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
    }
}
