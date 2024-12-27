package com.android.settings.localepicker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Pair;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.TickButtonPreference;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TermsOfAddressBaseController extends BasePreferenceController {
    private static final Executor sExecutor = Executors.newSingleThreadExecutor();
    private MetricsFeatureProvider mMetricsFeatureProvider;
    private TickButtonPreference mPreference;
    private PreferenceScreen mPreferenceScreen;
    private TermsOfAddressHelper mTermsOfAddressHelper;

    public TermsOfAddressBaseController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$displayPreference$0() {
        TermsOfAddressHelper termsOfAddressHelper = this.mTermsOfAddressHelper;
        int grammaticalGenderType = getGrammaticalGenderType();
        termsOfAddressHelper.mGrammaticalInflectionManager.setSystemWideGrammaticalGender(
                grammaticalGenderType);
        termsOfAddressHelper.mSystemGrammaticalGender = grammaticalGenderType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$displayPreference$1(Preference preference) {
        sExecutor.execute(
                new Runnable() { // from class:
                                 // com.android.settings.localepicker.TermsOfAddressBaseController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TermsOfAddressBaseController.this.lambda$displayPreference$0();
                    }
                });
        setSelected(this.mPreference);
        this.mMetricsFeatureProvider.action(this.mContext, getMetricsActionKey(), new Pair[0]);
        return true;
    }

    private void setSelected(TickButtonPreference tickButtonPreference) {
        for (int i = 1; i < this.mPreferenceScreen.getPreferenceCount(); i++) {
            TickButtonPreference tickButtonPreference2 =
                    (TickButtonPreference) this.mPreferenceScreen.getPreference(i);
            tickButtonPreference2.setSelected(
                    tickButtonPreference2.getKey().equals(tickButtonPreference.getKey()));
        }
    }

    private void updatePreferences() {
        TickButtonPreference tickButtonPreference = this.mPreference;
        if (tickButtonPreference == null) {
            return;
        }
        tickButtonPreference.setSelected(
                this.mTermsOfAddressHelper.mSystemGrammaticalGender == getGrammaticalGenderType());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceScreen = preferenceScreen;
        TickButtonPreference tickButtonPreference =
                (TickButtonPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = tickButtonPreference;
        tickButtonPreference.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.android.settings.localepicker.TermsOfAddressBaseController$$ExternalSyntheticLambda1
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        boolean lambda$displayPreference$1;
                        lambda$displayPreference$1 =
                                TermsOfAddressBaseController.this.lambda$displayPreference$1(
                                        preference);
                        return lambda$displayPreference$1;
                    }
                });
        updatePreferences();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public abstract int getGrammaticalGenderType();

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public abstract int getMetricsActionKey();

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

    public void setTermsOfAddressHelper(TermsOfAddressHelper termsOfAddressHelper) {
        this.mTermsOfAddressHelper = termsOfAddressHelper;
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
