package com.android.settings.core.instrumentation;

import android.content.Context;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.Instrumentable;
import com.android.settingslib.core.instrumentation.VisibilityLoggerMixin;
import com.android.settingslib.core.lifecycle.ObservableDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class InstrumentedDialogFragment extends ObservableDialogFragment
        implements Instrumentable {
    public int mDialogId = 0;
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mLifecycle.addObserver(
                new VisibilityLoggerMixin(getMetricsCategory(), this.mMetricsFeatureProvider));
        this.mLifecycle.onAttach();
    }
}
