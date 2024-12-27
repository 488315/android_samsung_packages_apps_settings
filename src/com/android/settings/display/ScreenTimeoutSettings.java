package com.android.settings.display;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.SensorPrivacyManager;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.FooterPreference;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScreenTimeoutSettings extends RadioButtonPickerFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.screen_timeout_settings);
    AdaptiveSleepBatterySaverPreferenceController mAdaptiveSleepBatterySaverPreferenceController;
    AdaptiveSleepCameraStatePreferenceController mAdaptiveSleepCameraStatePreferenceController;
    AdaptiveSleepPreferenceController mAdaptiveSleepController;
    AdaptiveSleepPermissionPreferenceController mAdaptiveSleepPermissionController;
    RestrictedLockUtils.EnforcedAdmin mAdmin;
    Context mContext;
    public DevicePolicyManager mDevicePolicyManager;
    FooterPreference mDisableOptionsPreference;
    public CharSequence[] mInitialEntries;
    public CharSequence[] mInitialValues;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    FooterPreference mPowerConsumptionPreference;
    public ScreenTimeoutSettings$$ExternalSyntheticLambda2 mPrivacyChangedListener;
    public SensorPrivacyManager mPrivacyManager;
    public FooterPreference mPrivacyPreference;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.display.ScreenTimeoutSettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    AdaptiveSleepBatterySaverPreferenceController
                            adaptiveSleepBatterySaverPreferenceController =
                                    ScreenTimeoutSettings.this
                                            .mAdaptiveSleepBatterySaverPreferenceController;
                    adaptiveSleepBatterySaverPreferenceController.initializePreference();
                    adaptiveSleepBatterySaverPreferenceController.mPreference.setVisible(
                            adaptiveSleepBatterySaverPreferenceController.isPowerSaveMode());
                    ScreenTimeoutSettings.this.mAdaptiveSleepController.updatePreference();
                }
            };
    public boolean mIsUserAuthenticated = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.display.ScreenTimeoutSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    ScreenTimeoutSettings.SEARCH_INDEX_DATA_PROVIDER;
            if (!AdaptiveSleepPreferenceController.isAdaptiveSleepSupported(context)) {
                return null;
            }
            Resources resources = context.getResources();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            searchIndexableRaw.title = resources.getString(R.string.adaptive_sleep_title);
            ((SearchIndexableData) searchIndexableRaw).key = "adaptive_sleep";
            searchIndexableRaw.keywords = resources.getString(R.string.adaptive_sleep_title);
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ProtectedSelectorWithWidgetPreference extends SelectorWithWidgetPreference {
        public final ScreenTimeoutSettings mScreenTimeoutSettings;
        public final long mTimeoutMs;

        public static void $r8$lambda$mOYRFa9tXr4tKQ0H9p4EQIHuepY(
                ProtectedSelectorWithWidgetPreference protectedSelectorWithWidgetPreference) {
            protectedSelectorWithWidgetPreference.mScreenTimeoutSettings.mIsUserAuthenticated =
                    true;
            super.onClick();
        }

        public ProtectedSelectorWithWidgetPreference(
                Context context, String str, ScreenTimeoutSettings screenTimeoutSettings) {
            super(context);
            BaseSearchIndexProvider baseSearchIndexProvider =
                    ScreenTimeoutSettings.SEARCH_INDEX_DATA_PROVIDER;
            this.mTimeoutMs = Long.parseLong(str);
            this.mScreenTimeoutSettings = screenTimeoutSettings;
        }

        @Override // com.android.settingslib.widget.SelectorWithWidgetPreference,
                  // androidx.preference.TwoStatePreference, androidx.preference.Preference
        public final void onClick() {
            ScreenTimeoutSettings screenTimeoutSettings = this.mScreenTimeoutSettings;
            if (screenTimeoutSettings.mIsUserAuthenticated
                    || this.mChecked
                    || this.mTimeoutMs <= Long.parseLong(screenTimeoutSettings.getDefaultKey())) {
                super.onClick();
            } else {
                WifiDppUtils.showLockScreen(
                        getContext(),
                        new Runnable() { // from class:
                                         // com.android.settings.display.ScreenTimeoutSettings$ProtectedSelectorWithWidgetPreference$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ScreenTimeoutSettings.ProtectedSelectorWithWidgetPreference
                                        .$r8$lambda$mOYRFa9tXr4tKQ0H9p4EQIHuepY(
                                                ScreenTimeoutSettings
                                                        .ProtectedSelectorWithWidgetPreference
                                                        .this);
                            }
                        });
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class TimeoutCandidateInfo extends CandidateInfo {
        public final String mKey;
        public final CharSequence mLabel;

        public TimeoutCandidateInfo(String str, CharSequence charSequence) {
            super(true);
            this.mLabel = charSequence;
            this.mKey = str;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return this.mKey;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return null;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.mLabel;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.display.ScreenTimeoutSettings$1] */
    public ScreenTimeoutSettings() {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        long longValue = getMaxScreenTimeout(getContext()).longValue();
        if (this.mInitialValues != null) {
            int i = 0;
            while (true) {
                CharSequence[] charSequenceArr = this.mInitialValues;
                if (i >= charSequenceArr.length) {
                    break;
                }
                if (Long.parseLong(charSequenceArr[i].toString()) <= longValue) {
                    arrayList.add(
                            new TimeoutCandidateInfo(
                                    this.mInitialValues[i].toString(), this.mInitialEntries[i]));
                }
                i++;
            }
        } else {
            Log.e("ScreenTimeout", "Screen timeout options do not exist.");
        }
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        Context context = getContext();
        return context == null
                ? Long.toString(30000L)
                : Long.toString(
                        Settings.System.getLong(
                                context.getContentResolver(), "screen_off_timeout", 30000L));
    }

    public final Long getMaxScreenTimeout(Context context) {
        DevicePolicyManager devicePolicyManager;
        if (context == null
                || (devicePolicyManager =
                                (DevicePolicyManager)
                                        context.getSystemService(DevicePolicyManager.class))
                        == null) {
            return Long.MAX_VALUE;
        }
        RestrictedLockUtils.EnforcedAdmin checkIfMaximumTimeToLockIsSet =
                RestrictedLockUtilsInternal.checkIfMaximumTimeToLockIsSet(context);
        this.mAdmin = checkIfMaximumTimeToLockIsSet;
        if (checkIfMaximumTimeToLockIsSet != null) {
            return Long.valueOf(
                    devicePolicyManager.getMaximumTimeToLock(null, UserHandle.myUserId()));
        }
        return Long.MAX_VALUE;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1852;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.screen_timeout_settings;
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.settings.display.ScreenTimeoutSettings$$ExternalSyntheticLambda2] */
    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mDevicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mInitialEntries = getResources().getStringArray(R.array.screen_timeout_entries);
        this.mInitialValues = getResources().getStringArray(R.array.screen_timeout_values);
        this.mAdaptiveSleepController = new AdaptiveSleepPreferenceController(context);
        this.mAdaptiveSleepPermissionController =
                new AdaptiveSleepPermissionPreferenceController(context);
        this.mAdaptiveSleepCameraStatePreferenceController =
                new AdaptiveSleepCameraStatePreferenceController(context, getLifecycle());
        this.mAdaptiveSleepBatterySaverPreferenceController =
                new AdaptiveSleepBatterySaverPreferenceController(context);
        FooterPreference footerPreference = new FooterPreference(context);
        this.mPrivacyPreference = footerPreference;
        footerPreference.setIcon(R.drawable.ic_privacy_shield_24dp);
        this.mPrivacyPreference.setTitle(R.string.adaptive_sleep_privacy);
        this.mPrivacyPreference.setSelectable(false);
        this.mPrivacyPreference.setLayoutResource(R.layout.preference_footer);
        this.mPrivacyManager = SensorPrivacyManager.getInstance(context);
        this.mPrivacyChangedListener =
                new SensorPrivacyManager
                        .OnSensorPrivacyChangedListener() { // from class:
                                                            // com.android.settings.display.ScreenTimeoutSettings$$ExternalSyntheticLambda2
                    public final void onSensorPrivacyChanged(int i, boolean z) {
                        ScreenTimeoutSettings.this.mAdaptiveSleepController.updatePreference();
                    }
                };
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((DisplayFeatureProviderImpl) featureFactoryImpl.displayFeatureProvider$delegate.getValue())
                .getClass();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        AdaptiveSleepPermissionPreferenceController adaptiveSleepPermissionPreferenceController =
                this.mAdaptiveSleepPermissionController;
        adaptiveSleepPermissionPreferenceController.initializePreference();
        adaptiveSleepPermissionPreferenceController.mPreference.setVisible(
                !AdaptiveSleepPreferenceController.hasSufficientPermission(
                        adaptiveSleepPermissionPreferenceController.mPackageManager));
        AdaptiveSleepCameraStatePreferenceController adaptiveSleepCameraStatePreferenceController =
                this.mAdaptiveSleepCameraStatePreferenceController;
        adaptiveSleepCameraStatePreferenceController.initializePreference();
        adaptiveSleepCameraStatePreferenceController.mPreference.setVisible(
                adaptiveSleepCameraStatePreferenceController.isCameraLocked());
        AdaptiveSleepBatterySaverPreferenceController
                adaptiveSleepBatterySaverPreferenceController =
                        this.mAdaptiveSleepBatterySaverPreferenceController;
        adaptiveSleepBatterySaverPreferenceController.initializePreference();
        adaptiveSleepBatterySaverPreferenceController.mPreference.setVisible(
                adaptiveSleepBatterySaverPreferenceController.isPowerSaveMode());
        this.mAdaptiveSleepController.updatePreference();
        this.mContext.registerReceiver(
                this.mReceiver, new IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED"));
        this.mPrivacyManager.addSensorPrivacyListener(2, this.mPrivacyChangedListener);
        this.mIsUserAuthenticated = false;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((DisplayFeatureProviderImpl) featureFactoryImpl.displayFeatureProvider$delegate.getValue())
                .getClass();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mPrivacyManager.removeSensorPrivacyListener(2, this.mPrivacyChangedListener);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        Context context = getContext();
        if (context == null) {
            return true;
        }
        try {
            long parseLong = Long.parseLong(str);
            this.mMetricsFeatureProvider.action(context, 1754, (int) parseLong);
            Settings.System.putLong(context.getContentResolver(), "screen_off_timeout", parseLong);
            return true;
        } catch (NumberFormatException e) {
            Log.e("ScreenTimeout", "could not persist screen timeout setting", e);
            return true;
        }
    }

    public void setupDisabledFooterPreference() {
        String string =
                this.mDevicePolicyManager
                        .getResources()
                        .getString(
                                "Settings.OTHER_OPTIONS_DISABLED_BY_ADMIN",
                                new Supplier() { // from class:
                                                 // com.android.settings.display.ScreenTimeoutSettings$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        ScreenTimeoutSettings screenTimeoutSettings =
                                                ScreenTimeoutSettings.this;
                                        BaseSearchIndexProvider baseSearchIndexProvider =
                                                ScreenTimeoutSettings.SEARCH_INDEX_DATA_PROVIDER;
                                        return screenTimeoutSettings
                                                .getResources()
                                                .getString(R.string.admin_disabled_other_options);
                                    }
                                });
        String string2 = getResources().getString(R.string.admin_more_details);
        FooterPreference footerPreference = new FooterPreference(getContext());
        this.mDisableOptionsPreference = footerPreference;
        footerPreference.setTitle(string);
        this.mDisableOptionsPreference.setSelectable(false);
        this.mDisableOptionsPreference.setLearnMoreText(string2);
        this.mDisableOptionsPreference.setLearnMoreAction(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.display.ScreenTimeoutSettings$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ScreenTimeoutSettings screenTimeoutSettings = ScreenTimeoutSettings.this;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                ScreenTimeoutSettings.SEARCH_INDEX_DATA_PROVIDER;
                        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                screenTimeoutSettings.getContext(), screenTimeoutSettings.mAdmin);
                    }
                });
        this.mDisableOptionsPreference.setIcon(R.drawable.ic_info_outline_24dp);
        this.mPrivacyPreference.setOrder(2147483645);
        this.mDisableOptionsPreference.setOrder(2147483646);
    }

    public void setupPowerConsumptionFooterPreference() {
        FooterPreference footerPreference = new FooterPreference(getContext());
        this.mPowerConsumptionPreference = footerPreference;
        footerPreference.setTitle(R.string.power_consumption_footer_summary);
        this.mPowerConsumptionPreference.setSelectable(false);
        this.mPowerConsumptionPreference.setIcon(R.drawable.ic_info_outline_24dp);
        this.mPrivacyPreference.setOrder(2147483645);
        this.mPowerConsumptionPreference.setOrder(2147483646);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void updateCandidates() {
        String defaultKey = getDefaultKey();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        ArrayList arrayList = (ArrayList) getCandidates();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CandidateInfo candidateInfo = (CandidateInfo) it.next();
            ProtectedSelectorWithWidgetPreference protectedSelectorWithWidgetPreference =
                    new ProtectedSelectorWithWidgetPreference(
                            getPrefContext(), candidateInfo.getKey(), this);
            bindPreference(
                    protectedSelectorWithWidgetPreference,
                    candidateInfo.getKey(),
                    candidateInfo,
                    defaultKey);
            preferenceScreen.addPreference(protectedSelectorWithWidgetPreference);
        }
        long parseLong = Long.parseLong(defaultKey);
        long longValue = getMaxScreenTimeout(getContext()).longValue();
        if (!arrayList.isEmpty() && parseLong > longValue) {
            ((ProtectedSelectorWithWidgetPreference)
                            preferenceScreen.getPreference(arrayList.size() - 1))
                    .setChecked(true);
        }
        FooterPreference footerPreference = new FooterPreference(this.mContext);
        this.mPrivacyPreference = footerPreference;
        footerPreference.setIcon(R.drawable.ic_privacy_shield_24dp);
        this.mPrivacyPreference.setTitle(R.string.adaptive_sleep_privacy);
        this.mPrivacyPreference.setSelectable(false);
        this.mPrivacyPreference.setLayoutResource(R.layout.preference_footer);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((DisplayFeatureProviderImpl) featureFactoryImpl.displayFeatureProvider$delegate.getValue())
                .getClass();
        if (AdaptiveSleepPreferenceController.isAdaptiveSleepSupported(getContext())) {
            AdaptiveSleepPermissionPreferenceController
                    adaptiveSleepPermissionPreferenceController =
                            this.mAdaptiveSleepPermissionController;
            adaptiveSleepPermissionPreferenceController.initializePreference();
            if (!AdaptiveSleepPreferenceController.hasSufficientPermission(
                    adaptiveSleepPermissionPreferenceController.mPackageManager)) {
                preferenceScreen.addPreference(
                        adaptiveSleepPermissionPreferenceController.mPreference);
            }
            AdaptiveSleepCameraStatePreferenceController
                    adaptiveSleepCameraStatePreferenceController =
                            this.mAdaptiveSleepCameraStatePreferenceController;
            adaptiveSleepCameraStatePreferenceController.initializePreference();
            preferenceScreen.addPreference(
                    adaptiveSleepCameraStatePreferenceController.mPreference);
            adaptiveSleepCameraStatePreferenceController.initializePreference();
            adaptiveSleepCameraStatePreferenceController.mPreference.setVisible(
                    adaptiveSleepCameraStatePreferenceController.isCameraLocked());
            AdaptiveSleepPreferenceController adaptiveSleepPreferenceController =
                    this.mAdaptiveSleepController;
            adaptiveSleepPreferenceController.updatePreference();
            preferenceScreen.addPreference(adaptiveSleepPreferenceController.mPreference);
            AdaptiveSleepBatterySaverPreferenceController
                    adaptiveSleepBatterySaverPreferenceController =
                            this.mAdaptiveSleepBatterySaverPreferenceController;
            adaptiveSleepBatterySaverPreferenceController.initializePreference();
            preferenceScreen.addPreference(
                    adaptiveSleepBatterySaverPreferenceController.mPreference);
            adaptiveSleepBatterySaverPreferenceController.initializePreference();
            adaptiveSleepBatterySaverPreferenceController.mPreference.setVisible(
                    adaptiveSleepBatterySaverPreferenceController.isPowerSaveMode());
            preferenceScreen.addPreference(this.mPrivacyPreference);
        }
        if (this.mAdmin != null) {
            setupDisabledFooterPreference();
            preferenceScreen.addPreference(this.mDisableOptionsPreference);
        } else {
            setupPowerConsumptionFooterPreference();
            preferenceScreen.addPreference(this.mPowerConsumptionPreference);
        }
    }
}
