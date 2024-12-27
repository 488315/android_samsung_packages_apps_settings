package com.android.settings.gestures;

import android.content.Context;
import android.hardware.display.AmbientDisplayConfiguration;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class GestureSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.gestures);
    public AmbientDisplayConfiguration mAmbientDisplayConfig;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.gestures.GestureSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ((ArrayList) nonIndexableKeys).add("gesture_prevent_ringing_summary");
            return nonIndexableKeys;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "GestureSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 459;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.gestures;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        PickupGesturePreferenceController pickupGesturePreferenceController =
                (PickupGesturePreferenceController) use(PickupGesturePreferenceController.class);
        if (this.mAmbientDisplayConfig == null) {
            this.mAmbientDisplayConfig = new AmbientDisplayConfiguration(context);
        }
        pickupGesturePreferenceController.setConfig(this.mAmbientDisplayConfig);
        DoubleTapScreenPreferenceController doubleTapScreenPreferenceController =
                (DoubleTapScreenPreferenceController)
                        use(DoubleTapScreenPreferenceController.class);
        if (this.mAmbientDisplayConfig == null) {
            this.mAmbientDisplayConfig = new AmbientDisplayConfiguration(context);
        }
        doubleTapScreenPreferenceController.setConfig(this.mAmbientDisplayConfig);
    }
}
