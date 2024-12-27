package com.samsung.android.settings.accessibility.home;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.view.accessibility.AccessibilityManager;

import androidx.lifecycle.Lifecycle;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.install.InstalledAppsManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class InstalledAppsPreferenceController extends BasePreferenceController
        implements A11yStatusLoggingProvider {
    private InstalledAppsManager installedAppsManager;
    private Preference preference;
    private List<RestrictedPreference> restrictedPreferences;

    public InstalledAppsPreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ComponentName lambda$getStatusLoggingData$2(
            ServiceInfo serviceInfo) {
        return new ComponentName(serviceInfo.packageName, serviceInfo.name);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getSummary$0(
            RestrictedPreference restrictedPreference) {
        return restrictedPreference.getExtras().getBoolean("checked");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAppsChanged(List<RestrictedPreference> list) {
        this.restrictedPreferences = list;
        updateState(this.preference);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.preference = findPreference;
        if (findPreference != null) {
            findPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
        }
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

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        String str;
        String str2;
        String str3;
        List<AccessibilityServiceInfo> installedAccessibilityServiceList =
                AccessibilityManager.getInstance(context).getInstalledAccessibilityServiceList();
        Set enabledServicesFromSettings =
                AccessibilityUtils.getEnabledServicesFromSettings(context);
        if (installedAccessibilityServiceList != null) {
            final int i = 0;
            Stream filter =
                    installedAccessibilityServiceList.stream()
                            .map(
                                    new Function() { // from class:
                                                     // com.samsung.android.settings.accessibility.home.InstalledAppsPreferenceController$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            ServiceInfo serviceInfo;
                                            ComponentName lambda$getStatusLoggingData$2;
                                            switch (i) {
                                                case 0:
                                                    return ((AccessibilityServiceInfo) obj)
                                                            .getResolveInfo();
                                                case 1:
                                                    serviceInfo = ((ResolveInfo) obj).serviceInfo;
                                                    return serviceInfo;
                                                default:
                                                    lambda$getStatusLoggingData$2 =
                                                            InstalledAppsPreferenceController
                                                                    .lambda$getStatusLoggingData$2(
                                                                            (ServiceInfo) obj);
                                                    return lambda$getStatusLoggingData$2;
                                            }
                                        }
                                    })
                            .filter(
                                    new InstalledAppsPreferenceController$$ExternalSyntheticLambda0(
                                            1));
            final int i2 = 1;
            Stream filter2 =
                    filter.map(
                                    new Function() { // from class:
                                                     // com.samsung.android.settings.accessibility.home.InstalledAppsPreferenceController$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            ServiceInfo serviceInfo;
                                            ComponentName lambda$getStatusLoggingData$2;
                                            switch (i2) {
                                                case 0:
                                                    return ((AccessibilityServiceInfo) obj)
                                                            .getResolveInfo();
                                                case 1:
                                                    serviceInfo = ((ResolveInfo) obj).serviceInfo;
                                                    return serviceInfo;
                                                default:
                                                    lambda$getStatusLoggingData$2 =
                                                            InstalledAppsPreferenceController
                                                                    .lambda$getStatusLoggingData$2(
                                                                            (ServiceInfo) obj);
                                                    return lambda$getStatusLoggingData$2;
                                            }
                                        }
                                    })
                            .filter(
                                    new InstalledAppsPreferenceController$$ExternalSyntheticLambda0(
                                            2));
            final int i3 = 2;
            Set set =
                    (Set)
                            filter2.map(
                                            new Function() { // from class:
                                                             // com.samsung.android.settings.accessibility.home.InstalledAppsPreferenceController$$ExternalSyntheticLambda2
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    ServiceInfo serviceInfo;
                                                    ComponentName lambda$getStatusLoggingData$2;
                                                    switch (i3) {
                                                        case 0:
                                                            return ((AccessibilityServiceInfo) obj)
                                                                    .getResolveInfo();
                                                        case 1:
                                                            serviceInfo =
                                                                    ((ResolveInfo) obj).serviceInfo;
                                                            return serviceInfo;
                                                        default:
                                                            lambda$getStatusLoggingData$2 =
                                                                    InstalledAppsPreferenceController
                                                                            .lambda$getStatusLoggingData$2(
                                                                                    (ServiceInfo)
                                                                                            obj);
                                                            return lambda$getStatusLoggingData$2;
                                                    }
                                                }
                                            })
                                    .collect(Collectors.toSet());
            ComponentName componentName = AccessibilityConstant.COMPONENT_NAME_ACCESSIBILITY_MENU;
            String str4 =
                    set.contains(componentName)
                            ? enabledServicesFromSettings.contains(componentName) ? "On" : "Off"
                            : "NotInstalled";
            ComponentName componentName2 =
                    AccessibilityConstant.COMPONENT_NAME_GOOGLE_SELECT_TO_SPEAK;
            String str5 =
                    set.contains(componentName2)
                            ? enabledServicesFromSettings.contains(componentName2) ? "On" : "Off"
                            : "NotInstalled";
            ComponentName componentName3 = AccessibilityConstant.COMPONENT_NAME_SWITCH_ACCESS;
            str3 =
                    set.contains(componentName3)
                            ? enabledServicesFromSettings.contains(componentName3) ? "On" : "Off"
                            : "NotInstalled";
            str = str4;
            str2 = str5;
        } else {
            str = "NotInstalled";
            str2 = str;
            str3 = str2;
        }
        return Map.of("A11YS6801", str, "A11YS6802", str2, "A11YS6803", str3);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        int size = this.restrictedPreferences.size();
        int count =
                (int)
                        this.restrictedPreferences.stream()
                                .filter(
                                        new InstalledAppsPreferenceController$$ExternalSyntheticLambda0(
                                                0))
                                .count();
        return size == 0
                ? this.mContext.getText(R.string.installed_apps_summary_no_app)
                : (size == 1 && count == 1)
                        ? this.mContext.getText(R.string.installed_apps_summary_one_app_enabled)
                        : count == 0
                                ? this.mContext
                                        .getResources()
                                        .getQuantityString(
                                                R.plurals.installed_apps_summary_no_enabled_app,
                                                size,
                                                Integer.valueOf(size))
                                : this.mContext.getString(
                                        R.string.installed_apps_summary,
                                        Integer.valueOf(count),
                                        Integer.valueOf(size));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(Lifecycle lifecycle) {
        InstalledAppsManager installedAppsManager =
                new InstalledAppsManager(this.mContext, lifecycle);
        this.installedAppsManager = installedAppsManager;
        installedAppsManager.listener =
                new InstalledAppsManager
                        .OnInstalledAppsChangedListener() { // from class:
                                                            // com.samsung.android.settings.accessibility.home.InstalledAppsPreferenceController$$ExternalSyntheticLambda1
                    @Override // com.samsung.android.settings.accessibility.install.InstalledAppsManager.OnInstalledAppsChangedListener
                    public final void onAppsChanged(List list) {
                        InstalledAppsPreferenceController.this.onAppsChanged(list);
                    }
                };
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        InstalledAppsManager installedAppsManager = this.installedAppsManager;
        if (((ArrayList) installedAppsManager.preferences).isEmpty()) {
            ((ArrayList) installedAppsManager.preferences)
                    .addAll(
                            InstalledAppsManager.getInstalledAccessibilityList(
                                    installedAppsManager.context));
        }
        this.restrictedPreferences = installedAppsManager.preferences;
        preference.setDotVisibility(!this.installedAppsManager.getNewAppList().isEmpty());
        preference.setDotContentDescription(
                this.mContext.getString(R.string.installed_apps_dot_badge_content_description));
        super.updateState(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
