package com.android.settings.survey;

import androidx.fragment.app.Fragment;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SurveyMixin implements LifecycleObserver, OnResume {
    public final Fragment mFragment;

    public SurveyMixin(Fragment fragment, String str) {
        this.mFragment = fragment;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        if (this.mFragment.getActivity() != null && FeatureFactoryImpl._factory == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
    }
}
