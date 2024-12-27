package com.android.settings.datausage;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DataSaverPreference extends SecPreference implements DataSaverBackend.Listener {
    public final DataSaverBackend mDataSaverBackend;

    public DataSaverPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDataSaverBackend = new DataSaverBackend(context);
        SecPreferenceUtils.applySummaryColor(this, true);
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        registerDependency();
        this.mDataSaverBackend.addListener(this);
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDataSaverChanged(boolean z) {
        setSummary(z ? R.string.switch_on_text : R.string.switch_off_text);
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        super.onDetached();
        this.mDataSaverBackend.remListener(this);
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onAllowlistStatusChanged(int i, boolean z) {}

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDenylistStatusChanged(int i, boolean z) {}
}
