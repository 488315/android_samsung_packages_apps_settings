package com.android.settings.core;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.survey.SurveyMixin;
import com.android.settingslib.core.instrumentation.Instrumentable;
import com.android.settingslib.core.instrumentation.VisibilityLoggerMixin;
import com.android.settingslib.core.lifecycle.ObservableFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class InstrumentedFragment extends ObservableFragment implements Instrumentable {
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public VisibilityLoggerMixin mVisibilityLoggerMixin;

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        VisibilityLoggerMixin visibilityLoggerMixin =
                new VisibilityLoggerMixin(getMetricsCategory(), this.mMetricsFeatureProvider);
        this.mVisibilityLoggerMixin = visibilityLoggerMixin;
        this.mLifecycle.addObserver(visibilityLoggerMixin);
        this.mLifecycle.addObserver(new SurveyMixin(this, getClass().getSimpleName()));
        super.onAttach(context);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        Intent intent;
        VisibilityLoggerMixin visibilityLoggerMixin = this.mVisibilityLoggerMixin;
        FragmentActivity activity = getActivity();
        if (visibilityLoggerMixin.mSourceMetricsCategory == 0
                && activity != null
                && (intent = activity.getIntent()) != null) {
            visibilityLoggerMixin.mSourceMetricsCategory =
                    intent.getIntExtra(":settings:source_metrics", 0);
        }
        super.onResume();
    }
}
