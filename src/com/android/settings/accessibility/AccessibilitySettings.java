package com.android.settings.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.view.InputDevice;
import android.view.accessibility.AccessibilityManager;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.content.PackageMonitor;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.development.Enable16kUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.sdhms.SemAppRestrictionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilitySettings extends DashboardFragment
        implements InputManager.InputDeviceListener {
    final AccessibilitySettingsContentObserver mSettingsContentObserver;
    static final String CATEGORY_INTERACTION_CONTROL = "interaction_control_category";
    public static final String[] CATEGORIES = {
        "screen_reader_category",
        "captions_category",
        "audio_category",
        "display_category",
        "speech_category",
        CATEGORY_INTERACTION_CONTROL,
        "physical_keyboard_options_category",
        "user_installed_services_category"
    };
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass3(R.xml.accessibility_settings);
    public final Handler mHandler = new Handler();
    public final AnonymousClass1 mUpdateRunnable =
            new Runnable() { // from class:
                             // com.android.settings.accessibility.AccessibilitySettings.1
                @Override // java.lang.Runnable
                public final void run() {
                    if (AccessibilitySettings.this.getActivity() != null) {
                        AccessibilitySettings.this.onContentChanged();
                    }
                }
            };
    public final AnonymousClass2 mSettingsPackageMonitor =
            new PackageMonitor() { // from class:
                                   // com.android.settings.accessibility.AccessibilitySettings.2
                public final void onPackageAdded(String str, int i) {
                    sendUpdate();
                }

                public final void onPackageAppeared(String str, int i) {
                    sendUpdate();
                }

                public final void onPackageDisappeared(String str, int i) {
                    sendUpdate();
                }

                public final void onPackageModified(String str) {
                    sendUpdate();
                }

                public final void onPackageRemoved(String str, int i) {
                    sendUpdate();
                }

                public final void sendUpdate() {
                    AccessibilitySettings accessibilitySettings = AccessibilitySettings.this;
                    accessibilitySettings.mHandler.postDelayed(
                            accessibilitySettings.mUpdateRunnable, 1000L);
                }
            };
    public final Map mCategoryToPrefCategoryMap = new ArrayMap();
    final Map<Preference, PreferenceCategory> mServicePreferenceToPreferenceCategoryMap =
            new ArrayMap();
    public final Map mPreBundledServiceComponentToCategoryMap = new ArrayMap();
    public boolean mNeedPreferencesUpdate = false;
    public boolean mIsForeground = true;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.accessibility.AccessibilitySettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            ((AccessibilitySearchFeatureProviderImpl)
                            featureFactoryImpl.accessibilitySearchFeatureProvider$delegate
                                    .getValue())
                    .getClass();
            return null;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.accessibility.AccessibilitySettings$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.accessibility.AccessibilitySettings$2] */
    public AccessibilitySettings() {
        Collection values =
                AccessibilityShortcutController.getFrameworkShortcutFeaturesMap().values();
        ArrayList arrayList = new ArrayList(values.size());
        Iterator it = values.iterator();
        while (it.hasNext()) {
            String settingKey =
                    ((AccessibilityShortcutController.FrameworkFeatureInfo) it.next())
                            .getSettingKey();
            if (settingKey != null) {
                arrayList.add(settingKey);
            }
        }
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "accessibility_button_targets",
                "accessibility_shortcut_target_service",
                "accessibility_qs_targets",
                "accessibility_sticky_keys");
        arrayList.add("accessibility_slow_keys");
        arrayList.add("accessibility_bounce_keys");
        arrayList.add("accessibility_direct_access_target_service");
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                new AccessibilitySettingsContentObserver(this.mHandler);
        this.mSettingsContentObserver = accessibilitySettingsContentObserver;
        accessibilitySettingsContentObserver.registerKeysToObserverCallback(
                arrayList,
                new AccessibilitySettingsContentObserver
                        .ContentObserverCallback() { // from class:
                                                     // com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticLambda1
                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str) {
                        String[] strArr = AccessibilitySettings.CATEGORIES;
                        AccessibilitySettings.this.onContentChanged();
                    }
                });
    }

    public static CharSequence getServiceDescription(
            Context context, final AccessibilityServiceInfo accessibilityServiceInfo, boolean z) {
        if (!z || !accessibilityServiceInfo.crashed) {
            return accessibilityServiceInfo.loadDescription(context.getPackageManager());
        }
        List restrictedList = new SemAppRestrictionManager(context).getRestrictedList(0);
        return (restrictedList == null
                        || !restrictedList.stream()
                                .anyMatch(
                                        new Predicate() { // from class:
                                                          // com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticLambda2
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                AccessibilityServiceInfo accessibilityServiceInfo2 =
                                                        accessibilityServiceInfo;
                                                String[] strArr = AccessibilitySettings.CATEGORIES;
                                                return accessibilityServiceInfo2
                                                        .getResolveInfo()
                                                        .serviceInfo
                                                        .packageName
                                                        .equals(
                                                                ((SemAppRestrictionManager
                                                                                        .AppRestrictionInfo)
                                                                                obj)
                                                                        .getPackageName());
                                            }
                                        }))
                ? context.getText(R.string.accessibility_description_state_stopped)
                : context.getText(R.string.accessibility_description_state_stopped_by_deep_sleep);
    }

    public static CharSequence getServiceSummary(
            Context context, AccessibilityServiceInfo accessibilityServiceInfo, boolean z) {
        if (z && accessibilityServiceInfo.crashed) {
            return context.getText(R.string.accessibility_summary_state_stopped);
        }
        if (AccessibilityUtil.getAccessibilityServiceFragmentType(accessibilityServiceInfo) != 1) {
            return context.getText(z ? R.string.switch_on_text : R.string.switch_off_text);
        }
        AccessibilityUtil.getUserShortcutTypesFromSettings(
                context,
                new ComponentName(
                        accessibilityServiceInfo.getResolveInfo().serviceInfo.packageName,
                        accessibilityServiceInfo.getResolveInfo().serviceInfo.name));
        return accessibilityServiceInfo.loadSummary(context.getPackageManager());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AccessibilitySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_settings;
    }

    public final void initializePreBundledServicesMapFromArray(int i, String str) {
        String[] stringArray = getResources().getStringArray(i);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) ((ArrayMap) this.mCategoryToPrefCategoryMap).get(str);
        for (int i2 = 0; i2 < stringArray.length; i2++) {
            if (!stringArray[i2].contains("android.apps.accessibility.voiceaccess")
                    || !Enable16kUtils.isPageAgnosticModeOn(getContext())) {
                ((ArrayMap) this.mPreBundledServiceComponentToCategoryMap)
                        .put(
                                ComponentName.unflattenFromString(stringArray[i2]),
                                preferenceCategory);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((AccessibilityHearingAidPreferenceController)
                        use(AccessibilityHearingAidPreferenceController.class))
                .setFragmentManager(getFragmentManager());
    }

    public void onContentChanged() {
        if (this.mIsForeground) {
            updateAllPreferences();
        } else {
            this.mNeedPreferencesUpdate = true;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        for (int i = 0; i < 8; i++) {
            String[] strArr = CATEGORIES;
            ((ArrayMap) this.mCategoryToPrefCategoryMap)
                    .put(strArr[i], (PreferenceCategory) findPreference(strArr[i]));
        }
        updateAllPreferences();
        this.mNeedPreferencesUpdate = false;
        FragmentActivity activity = getActivity();
        register(activity, activity.getMainLooper(), false);
        this.mSettingsContentObserver.register(getContentResolver());
        InputManager inputManager = (InputManager) getSystemService(InputManager.class);
        if (inputManager == null) {
            return;
        }
        inputManager.registerInputDeviceListener(this, null);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        unregister();
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                this.mSettingsContentObserver;
        ContentResolver contentResolver = getContentResolver();
        accessibilitySettingsContentObserver.getClass();
        contentResolver.unregisterContentObserver(accessibilitySettingsContentObserver);
        InputManager inputManager = (InputManager) getSystemService(InputManager.class);
        if (inputManager != null) {
            inputManager.unregisterInputDeviceListener(this);
        }
        super.onDestroy();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {
        this.mHandler.postDelayed(this.mUpdateRunnable, 1000L);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mNeedPreferencesUpdate = true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mNeedPreferencesUpdate) {
            updateAllPreferences();
            this.mNeedPreferencesUpdate = false;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mIsForeground = true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        this.mIsForeground = false;
        super.onStop();
    }

    public void updateAllPreferences() {
        ArrayList arrayList =
                new ArrayList(this.mServicePreferenceToPreferenceCategoryMap.keySet());
        boolean z = false;
        for (int i = 0; i < arrayList.size(); i++) {
            Preference preference = (Preference) arrayList.get(i);
            this.mServicePreferenceToPreferenceCategoryMap
                    .get(preference)
                    .removePreference(preference);
        }
        initializePreBundledServicesMapFromArray(
                R.array.config_preinstalled_screen_reader_services, "screen_reader_category");
        initializePreBundledServicesMapFromArray(
                R.array.config_preinstalled_captions_services, "captions_category");
        initializePreBundledServicesMapFromArray(
                R.array.config_preinstalled_audio_services, "audio_category");
        initializePreBundledServicesMapFromArray(
                R.array.config_preinstalled_display_services, "display_category");
        initializePreBundledServicesMapFromArray(
                R.array.config_preinstalled_speech_services, "speech_category");
        initializePreBundledServicesMapFromArray(
                R.array.config_preinstalled_interaction_control_services,
                CATEGORY_INTERACTION_CONTROL);
        ((ArrayMap) this.mPreBundledServiceComponentToCategoryMap)
                .put(
                        AccessibilityUtils.ACCESSIBILITY_MENU_IN_SYSTEM,
                        (PreferenceCategory)
                                ((ArrayMap) this.mCategoryToPrefCategoryMap)
                                        .get(CATEGORY_INTERACTION_CONTROL));
        Context prefContext = getPrefContext();
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(prefContext);
        RestrictedPreferenceHelper restrictedPreferenceHelper =
                new RestrictedPreferenceHelper(prefContext);
        List createAccessibilityActivityPreferenceList =
                restrictedPreferenceHelper.createAccessibilityActivityPreferenceList(
                        accessibilityManager.getInstalledAccessibilityShortcutListAsUser(
                                prefContext, UserHandle.myUserId()));
        List createAccessibilityServicePreferenceList =
                restrictedPreferenceHelper.createAccessibilityServicePreferenceList(
                        new ArrayList(accessibilityManager.getInstalledAccessibilityServiceList()));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(createAccessibilityActivityPreferenceList);
        arrayList2.addAll(createAccessibilityServicePreferenceList);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory)
                        ((ArrayMap) this.mCategoryToPrefCategoryMap)
                                .get("user_installed_services_category");
        int size = arrayList2.size();
        for (int i2 = 0; i2 < size; i2++) {
            RestrictedPreference restrictedPreference = (RestrictedPreference) arrayList2.get(i2);
            ComponentName componentName =
                    (ComponentName)
                            restrictedPreference.getExtras().getParcelable("component_name");
            PreferenceCategory preferenceCategory2 =
                    ((ArrayMap) this.mPreBundledServiceComponentToCategoryMap)
                                    .containsKey(componentName)
                            ? (PreferenceCategory)
                                    ((ArrayMap) this.mPreBundledServiceComponentToCategoryMap)
                                            .get(componentName)
                            : preferenceCategory;
            preferenceCategory2.addPreference(restrictedPreference);
            this.mServicePreferenceToPreferenceCategoryMap.put(
                    restrictedPreference, preferenceCategory2);
        }
        updateCategoryOrderFromArray(
                R.array.config_order_screen_reader_services, "screen_reader_category");
        updateCategoryOrderFromArray(R.array.config_order_captions_services, "captions_category");
        updateCategoryOrderFromArray(R.array.config_order_audio_services, "audio_category");
        updateCategoryOrderFromArray(
                R.array.config_order_interaction_control_services, CATEGORY_INTERACTION_CONTROL);
        updateCategoryOrderFromArray(R.array.config_order_display_services, "display_category");
        updateCategoryOrderFromArray(R.array.config_order_speech_services, "speech_category");
        if (preferenceCategory.getPreferenceCount() == 0) {
            getPreferenceScreen().removePreference(preferenceCategory);
        } else {
            getPreferenceScreen().addPreference(preferenceCategory);
        }
        updatePreferenceCategoryVisibility("screen_reader_category");
        updatePreferenceCategoryVisibility("speech_category");
        updatePreferenceCategoryVisibility("physical_keyboard_options_category");
        ArrayList arrayList3 = new ArrayList();
        getPreferenceControllers()
                .forEach(new AccessibilitySettings$$ExternalSyntheticLambda3(0, arrayList3));
        arrayList3.forEach(new AccessibilitySettings$$ExternalSyntheticLambda3(1, this));
        if (((ArrayMap) this.mCategoryToPrefCategoryMap)
                .containsKey("physical_keyboard_options_category")) {
            int[] deviceIds = InputDevice.getDeviceIds();
            int length = deviceIds.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                InputDevice device = InputDevice.getDevice(deviceIds[i3]);
                if (device == null || device.isVirtual() || !device.isFullKeyboard()) {
                    i3++;
                } else {
                    Iterator<List<AbstractPreferenceController>> it =
                            getPreferenceControllers().iterator();
                    loop3:
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        for (AbstractPreferenceController abstractPreferenceController :
                                it.next()) {
                            if (abstractPreferenceController
                                            .getPreferenceKey()
                                            .equals("toggle_keyboard_bounce_keys")
                                    || abstractPreferenceController
                                            .getPreferenceKey()
                                            .equals("toggle_keyboard_slow_keys")
                                    || abstractPreferenceController
                                            .getPreferenceKey()
                                            .equals("toggle_keyboard_sticky_keys")) {
                                if (abstractPreferenceController.isAvailable()) {
                                    z = true;
                                    break loop3;
                                }
                            }
                        }
                    }
                }
            }
            ((PreferenceCategory)
                            ((ArrayMap) this.mCategoryToPrefCategoryMap)
                                    .get("physical_keyboard_options_category"))
                    .setVisible(z);
            if (z) {
                findPreference("toggle_keyboard_bounce_keys")
                        .setSummary(getContext().getString(R.string.bounce_keys_summary, 500));
                findPreference("toggle_keyboard_slow_keys")
                        .setSummary(getContext().getString(R.string.slow_keys_summary, 500));
            }
        }
    }

    public final void updateCategoryOrderFromArray(int i, String str) {
        String[] stringArray = getResources().getStringArray(i);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) ((ArrayMap) this.mCategoryToPrefCategoryMap).get(str);
        int preferenceCount = preferenceCategory.getPreferenceCount();
        int length = stringArray.length;
        for (int i2 = 0; i2 < preferenceCount; i2++) {
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                if (preferenceCategory.getPreference(i2).getKey().equals(stringArray[i3])) {
                    preferenceCategory.getPreference(i2).setOrder(i3);
                    break;
                }
                i3++;
            }
        }
    }

    public final void updatePreferenceCategoryVisibility(String str) {
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) ((ArrayMap) this.mCategoryToPrefCategoryMap).get(str);
        preferenceCategory.setVisible(preferenceCategory.getPreferenceCount() != 0);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(int i) {}

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {}
}
