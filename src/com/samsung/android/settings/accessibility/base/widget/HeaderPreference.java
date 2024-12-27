package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.Preference;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HeaderPreference extends Preference {
    public HeaderPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.preference_category_header_container);
        setSelectable(false);
    }
}
