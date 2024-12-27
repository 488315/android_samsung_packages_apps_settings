package com.samsung.android.settings.accessibility.advanced.flashnotification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.CameraFlashNotificationPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecCameraFlashNotificationPreferenceController
        extends CameraFlashNotificationPreferenceController
        implements AccessibilityObservableController,
                AccessibilityUsingFunction,
                A11yStatusLoggingProvider {
    private static final String TAG = "SecCameraFlashNotificationPreferenceController";
    private Set<String> installedAppSet;
    private Preference preference;

    public SecCameraFlashNotificationPreferenceController(Context context, String str) {
        super(context, str);
        this.installedAppSet = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence getApplicationLabel(String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            return packageManager.getPackageInfo(str, 0).applicationInfo.loadLabel(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "getApplicationLabel - pkg not exist. " + str, e);
            return ApnSettings.MVNO_NONE;
        }
    }

    private Set<String> getInstalledAppSet() {
        return this.installedAppSet;
    }

    private CharSequence getSingleAppSummaryOnSting(List<String> list) {
        return getApplicationLabel(list.get(0));
    }

    private CharSequence getTwoAppSummaryOnString(List<String> list) {
        List list2 =
                list.stream()
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.settings.accessibility.advanced.flashnotification.SecCameraFlashNotificationPreferenceController$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        CharSequence applicationLabel;
                                        applicationLabel =
                                                SecCameraFlashNotificationPreferenceController.this
                                                        .getApplicationLabel((String) obj);
                                        return applicationLabel;
                                    }
                                })
                        .filter(
                                new SecCameraFlashNotificationPreferenceController$$ExternalSyntheticLambda3())
                        .map(
                                new SecCameraFlashNotificationPreferenceController$$ExternalSyntheticLambda4())
                        .sorted(
                                new ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda3())
                        .toList();
        return list2.size() == 2
                ? String.format(
                        this.mContext
                                .getResources()
                                .getString(R.string.camera_flash_notification_2_apps),
                        list2.get(0),
                        list2.get(1))
                : ApnSettings.MVNO_NONE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getSummary$1(String str) {
        return getInstalledAppSet().contains(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTwoAppSummaryOnString$2(
            CharSequence charSequence) {
        return !TextUtils.isEmpty(charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$0(Preference preference, Set set) {
        this.installedAppSet = set;
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            this.preference = findPreference;
            findPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Uri uri = AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER;
        return AccessibilityRune.getFloatingFeatureBooleanValue(
                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_FLASH_NOTIFICATION")
                ? 0
                : 3;
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Object getDefaultValue(Context context) {
        return Boolean.FALSE;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.advanced.flashnotification.SecFlashNotificationsPreferenceFragment";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFunctionName() {
        return "CameraFlashNoti";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.accessibility_setting_list_ic_advanced);
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ Intent getLearnMoreButtonIntent(Context context) {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        if (getAvailabilityStatus() == 3) {
            return Map.of();
        }
        boolean threadEnabled = getThreadEnabled();
        Map.Entry entry = Map.entry("A11YS6010", threadEnabled ? "On" : "Off");
        if (!threadEnabled) {
            return Map.ofEntries(entry);
        }
        String string =
                Settings.Secure.getString(
                        this.mContext.getContentResolver(), "camera_flash_notification_app_list");
        if (TextUtils.isEmpty(string) || string.equals("all")) {
            return Map.ofEntries(entry, Map.entry("A11YS6009", "AllApps"));
        }
        Set installedPackageNameUnmodifiableSet =
                FlashNotificationUtil.getInstalledPackageNameUnmodifiableSet(context);
        Stream stream = Arrays.stream(string.split(ConstFlashNoti.APP_LIST_SEPARATOR_STRING));
        Objects.requireNonNull(installedPackageNameUnmodifiableSet);
        long count =
                stream.filter(
                                new SecCameraFlashNotificationPreferenceController$$ExternalSyntheticLambda0(
                                        1, installedPackageNameUnmodifiableSet))
                        .count();
        return Map.ofEntries(
                entry,
                Map.entry(
                        "A11YS6009",
                        count <= 5 ? "1-5Apps" : count <= 10 ? "6-10Apps" : "over10Apps"));
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        if (!getThreadEnabled()) {
            return ApnSettings.MVNO_NONE;
        }
        String string =
                Settings.Secure.getString(
                        this.mContext.getContentResolver(), "camera_flash_notification_app_list");
        if (string == null || string.equals("all")) {
            return this.mContext
                    .getResources()
                    .getString(R.string.camera_flash_notification_all_apps);
        }
        if (getInstalledAppSet() == null) {
            return ApnSettings.MVNO_NONE;
        }
        List<String> list =
                Arrays.stream(string.split(ConstFlashNoti.APP_LIST_SEPARATOR_STRING))
                        .filter(
                                new SecCameraFlashNotificationPreferenceController$$ExternalSyntheticLambda0(
                                        0, this))
                        .toList();
        return list.size() == 0
                ? ApnSettings.MVNO_NONE
                : list.size() == 1
                        ? getSingleAppSummaryOnSting(list)
                        : list.size() == 2
                                ? getTwoAppSummaryOnString(list)
                                : list.size() < getInstalledAppSet().size()
                                        ? this.mContext
                                                .getResources()
                                                .getQuantityString(
                                                        R.plurals.camera_flash_notification_n_apps,
                                                        list.size(),
                                                        Integer.valueOf(list.size()))
                                        : this.mContext
                                                .getResources()
                                                .getString(
                                                        R.string
                                                                .camera_flash_notification_all_apps);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(Settings.System.getUriFor("camera_flash_notification"));
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getText(R.string.flash_notification_camera_flash);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ int getUsingFunctionType() {
        return super.getUsingFunctionType();
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        boolean checked = super.setChecked(z);
        if (checked && this.preference != null) {
            String string =
                    Settings.Secure.getString(
                            this.mContext.getContentResolver(),
                            "camera_flash_notification_app_list");
            if (z && string != null && string.isEmpty()) {
                Settings.Secure.putString(
                        this.mContext.getContentResolver(),
                        "camera_flash_notification_app_list",
                        "all");
            }
            refreshSummary(this.preference);
        }
        return checked;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(final Preference preference) {
        super.updateState(preference);
        if (getInstalledAppSet() != null) {
            refreshSummary(preference);
            return;
        }
        Context context = this.mContext;
        FlashNotificationUtil.InstalledApplicationsListener installedApplicationsListener =
                new FlashNotificationUtil
                        .InstalledApplicationsListener() { // from class:
                                                           // com.samsung.android.settings.accessibility.advanced.flashnotification.SecCameraFlashNotificationPreferenceController$$ExternalSyntheticLambda5
                    @Override // com.samsung.android.settings.accessibility.advanced.flashnotification.FlashNotificationUtil.InstalledApplicationsListener
                    public final void onGetInstalledPackageNameUnmodifiableSet(Set set) {
                        SecCameraFlashNotificationPreferenceController.this.lambda$updateState$0(
                                preference, set);
                    }
                };
        Map map = FlashNotificationUtil.ALIAS_MAP;
        Executors.newSingleThreadExecutor()
                .execute(
                        new FlashNotificationUtil$$ExternalSyntheticLambda0(
                                context, installedApplicationsListener));
    }

    @Override // com.android.settings.accessibility.CameraFlashNotificationPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
