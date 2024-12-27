package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.view.View;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.widget.BannerMessagePreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TwilightLocationPreferenceController extends BasePreferenceController {
    private final LocationManager mLocationManager;
    private final MetricsFeatureProvider mMetricsFeatureProvider;

    public TwilightLocationPreferenceController(Context context, String str) {
        super(context, str);
        this.mLocationManager = (LocationManager) context.getSystemService(LocationManager.class);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$0(
            BannerMessagePreference bannerMessagePreference, View view) {
        this.mMetricsFeatureProvider.logClickedPreference(
                bannerMessagePreference, getMetricsCategory());
        launchLocationSettings();
    }

    private void launchLocationSettings() {
        Intent intent = new Intent();
        intent.setClass(this.mContext, Settings.LocationSettingsActivity.class);
        this.mContext.startActivity(intent);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        final BannerMessagePreference bannerMessagePreference =
                (BannerMessagePreference) preferenceScreen.findPreference(getPreferenceKey());
        bannerMessagePreference.setPositiveButtonText$1(R.string.twilight_mode_launch_location);
        bannerMessagePreference.setPositiveButtonOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.display.TwilightLocationPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        TwilightLocationPreferenceController.this.lambda$displayPreference$0(
                                bannerMessagePreference, view);
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mLocationManager.isLocationEnabled() ? 2 : 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
