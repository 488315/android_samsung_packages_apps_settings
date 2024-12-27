package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.PrimarySwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecPrimarySwitchPreference extends PrimarySwitchPreference {
    public SecPrimarySwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        setLayoutResource(R.layout.sec_preference_primary_switch);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.preference.Preference, java.lang.Comparable
    public final int compareTo(Preference preference) {
        double order = getOrder();
        double order2 = preference.getOrder();
        return order != order2 ? order > order2 ? 1 : -1 : super.compareTo(preference);
    }
}
