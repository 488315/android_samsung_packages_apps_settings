package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.Preference;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecDividerPreference extends Preference {
    public SecDividerPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.sec_preference_divider);
    }

    public SecDividerPreference(Context context) {
        this(context, null);
    }
}
