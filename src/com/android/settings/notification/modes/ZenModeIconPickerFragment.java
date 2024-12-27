package com.android.settings.notification.modes;

import android.content.Context;

import com.android.settings.R;

import com.google.common.collect.ImmutableList;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeIconPickerFragment extends ZenModeFragmentBase {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return ImmutableList.construct(
                new ZenModeIconPickerIconPreferenceController(context, this, this.mBackend),
                new ZenModeIconPickerListPreferenceController(
                        context,
                        this,
                        new IconOptionsProviderImpl(((ZenModesFragmentBase) this).mContext),
                        this.mBackend));
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 142;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.modes_icon_picker;
    }
}
