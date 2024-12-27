package com.samsung.android.settings.accessibility.advanced.flashnotification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.FlashNotificationsUtil;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.base.widget.AccessibilitySelectorWithWidgetPreference;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenFlashNotificationCustomAppsPreferenceController extends BasePreferenceController
        implements View.OnClickListener, DefaultLifecycleObserver, A11yStatusLoggingProvider {
    private static final String TAG = "ScreenFlashNotificationCustomAppsPreferenceController";
    private List<FlashNotificationUtil.ScreenFlashInfo> appList;
    private Set<String> installedAppSet;
    private final ContentObserver mScreenFlashModeObserver;
    private AccessibilitySelectorWithWidgetPreference radioPreference;

    public ScreenFlashNotificationCustomAppsPreferenceController(Context context, String str) {
        super(context, str);
        this.installedAppSet = null;
        this.appList = null;
        this.mScreenFlashModeObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationCustomAppsPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        ScreenFlashNotificationCustomAppsPreferenceController.this
                                .refreshRadioPreference();
                    }
                };
    }

    private CharSequence getCustomSummary() {
        if (getAppList() != null && getAppList().size() != 0) {
            return getAppList().size() == 1
                    ? getCustomSummarySingleApp()
                    : getAppList().size() == 2
                            ? getCustomSummaryTwoApps()
                            : this.mContext
                                    .getResources()
                                    .getQuantityString(
                                            R.plurals.camera_flash_notification_n_apps,
                                            getAppList().size(),
                                            Integer.valueOf(getAppList().size()));
        }
        if (isColorModeCustom()) {
            return this.mContext
                    .getResources()
                    .getString(R.string.screen_flash_notification_none_selected);
        }
        return null;
    }

    private String getCustomSummarySingleApp() {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            return packageManager
                    .getPackageInfo(getAppList().get(0).packageName, 0)
                    .applicationInfo
                    .loadLabel(packageManager)
                    .toString();
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "Something goes wrong. appList.size()=" + this.getAppList().size(), e);
            return null;
        }
    }

    private String getCustomSummaryTwoApps() {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            PackageInfo packageInfo =
                    packageManager.getPackageInfo(getAppList().get(0).packageName, 0);
            PackageInfo packageInfo2 =
                    packageManager.getPackageInfo(getAppList().get(1).packageName, 0);
            ArrayList arrayList = new ArrayList();
            arrayList.add(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            arrayList.add(packageInfo2.applicationInfo.loadLabel(packageManager).toString());
            arrayList.sort(
                    new ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda3());
            return String.format(
                    this.mContext
                            .getResources()
                            .getString(R.string.camera_flash_notification_2_apps),
                    arrayList.get(0),
                    arrayList.get(1));
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "Something goes wrong. appList.size()=" + this.getAppList().size(), e);
            return null;
        }
    }

    private List<FlashNotificationUtil.ScreenFlashInfo> getScreenFlashColorAppList() {
        return FlashNotificationUtil.getScreenFlashColorList(this.mContext).stream()
                .filter(
                        new Predicate() { // from class:
                                          // com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean lambda$getScreenFlashColorAppList$4;
                                lambda$getScreenFlashColorAppList$4 =
                                        ScreenFlashNotificationCustomAppsPreferenceController.this
                                                .lambda$getScreenFlashColorAppList$4(
                                                        (FlashNotificationUtil.ScreenFlashInfo)
                                                                obj);
                                return lambda$getScreenFlashColorAppList$4;
                            }
                        })
                .toList();
    }

    private boolean isColorModeCustom() {
        return Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "screen_flash_notification_color_mode",
                        0)
                == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean lambda$getScreenFlashColorAppList$4(
            FlashNotificationUtil.ScreenFlashInfo screenFlashInfo) {
        return screenFlashInfo.checked
                && getInstalledAppSet().contains(screenFlashInfo.packageName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDestroy$2(Set set) {
        this.installedAppSet = set;
        this.appList = getScreenFlashColorAppList();
        refreshRadioPreferenceWithAppList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$0(Set set) {
        this.installedAppSet = set;
        this.appList = getScreenFlashColorAppList();
        refreshRadioPreferenceWithAppList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerContentObserver$1(Uri uri) {
        this.mContext
                .getContentResolver()
                .registerContentObserver(uri, false, this.mScreenFlashModeObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$3(Set set) {
        this.installedAppSet = set;
        this.appList = getScreenFlashColorAppList();
        refreshRadioPreferenceWithAppList();
    }

    private void launchCustomAppPickerFragment() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ScreenFlashNotificationCustomAppPickerFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.launch();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshRadioPreference() {
        if (this.radioPreference != null) {
            if (getAvailabilityStatus() != 0) {
                this.radioPreference.setVisible(false);
                return;
            }
            this.radioPreference.setVisible(true);
            boolean isColorModeCustom = isColorModeCustom();
            AccessibilitySelectorWithWidgetPreference accessibilitySelectorWithWidgetPreference =
                    this.radioPreference;
            accessibilitySelectorWithWidgetPreference.isExtraWidgetEnabled = isColorModeCustom;
            accessibilitySelectorWithWidgetPreference.notifyChanged();
            this.radioPreference.setChecked(isColorModeCustom);
        }
    }

    private void refreshRadioPreferenceWithAppList() {
        if (this.radioPreference != null) {
            CharSequence customSummary = getCustomSummary();
            this.radioPreference.setSummary(customSummary);
            AccessibilitySelectorWithWidgetPreference accessibilitySelectorWithWidgetPreference =
                    this.radioPreference;
            if (customSummary == null) {
                this = null;
            }
            accessibilitySelectorWithWidgetPreference.setExtraWidgetOnClickListener(this);
        }
    }

    private void registerContentObserver() {
        List.of(
                        Settings.System.getUriFor("screen_flash_notification"),
                        Settings.System.getUriFor("screen_flash_notification_color_mode"),
                        Settings.Secure.getUriFor("screen_flash_notification_color_apps"))
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ScreenFlashNotificationCustomAppsPreferenceController.this
                                        .lambda$registerContentObserver$1((Uri) obj);
                            }
                        });
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference instanceof AccessibilitySelectorWithWidgetPreference) {
            AccessibilitySelectorWithWidgetPreference accessibilitySelectorWithWidgetPreference =
                    (AccessibilitySelectorWithWidgetPreference) findPreference;
            this.radioPreference = accessibilitySelectorWithWidgetPreference;
            accessibilitySelectorWithWidgetPreference.setExtraWidgetOnClickListener(this);
            AccessibilitySelectorWithWidgetPreference accessibilitySelectorWithWidgetPreference2 =
                    this.radioPreference;
            accessibilitySelectorWithWidgetPreference2.extraWidgetContentDescription =
                    this.mContext.getText(
                            R.string.screen_flash_notification_color_setting_icon_custom_apps);
            accessibilitySelectorWithWidgetPreference2.notifyChanged();
            this.radioPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
        }
    }

    public List<FlashNotificationUtil.ScreenFlashInfo> getAppList() {
        return this.appList;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Settings.System.getInt(
                                this.mContext.getContentResolver(), "screen_flash_notification", 0)
                        != 0
                ? 0
                : 2;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public Set<String> getInstalledAppSet() {
        return this.installedAppSet;
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
        Iterator<FlashNotificationUtil.ScreenFlashInfo> it;
        this.installedAppSet =
                FlashNotificationUtil.getInstalledPackageNameUnmodifiableSet(context);
        List<FlashNotificationUtil.ScreenFlashInfo> screenFlashColorAppList =
                getScreenFlashColorAppList();
        int size = screenFlashColorAppList.size();
        String str =
                isColorModeCustom()
                        ? size <= 5 ? "1-5Apps" : size <= 10 ? "6-10Apps" : "over10Apps"
                        : "AllApps";
        Iterator<FlashNotificationUtil.ScreenFlashInfo> it2 = screenFlashColorAppList.iterator();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (it2.hasNext()) {
            try {
                switch (FlashNotificationsUtil.getScreenColor(r3.color)) {
                    case EF0:
                        i++;
                        break;
                    case EF1:
                        i2++;
                        break;
                    case EF2:
                        i3++;
                        break;
                    case EF3:
                        i4++;
                        break;
                    case EF4:
                        i5++;
                        break;
                    case EF5:
                        i6++;
                        break;
                    case YELLOW:
                        i7++;
                        break;
                    case EF111:
                        i8++;
                        break;
                    case EF125:
                        i9++;
                        break;
                    case EF140:
                        i10++;
                        break;
                    case EF155:
                        i11++;
                        break;
                    case EF170:
                        i12++;
                        break;
                }
                it = it2;
            } catch (FlashNotificationsUtil.ScreenColorNotFoundException unused) {
                it = it2;
                Log.w(TAG, "getStatusLoggingData got wrong colorInt. info : " + it2.next());
            }
            it2 = it;
        }
        return Map.ofEntries(
                Map.entry("A11YS6014", str),
                Map.entry("A11YS6015", Integer.toString(i)),
                Map.entry("A11YS6016", Integer.toString(i2)),
                Map.entry("A11YS6017", Integer.toString(i3)),
                Map.entry("A11YS6018", Integer.toString(i4)),
                Map.entry("A11YS6019", Integer.toString(i5)),
                Map.entry("A11YS6020", Integer.toString(i6)),
                Map.entry("A11YS6021", Integer.toString(i7)),
                Map.entry("A11YS6022", Integer.toString(i8)),
                Map.entry("A11YS6023", Integer.toString(i9)),
                Map.entry("A11YS6024", Integer.toString(i10)),
                Map.entry("A11YS6025", Integer.toString(i11)),
                Map.entry("A11YS6026", Integer.toString(i12)));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey()) || getAppList() == null) {
            return super.handlePreferenceTreeClick(preference);
        }
        if (getAppList().size() == 0) {
            launchCustomAppPickerFragment();
        } else {
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "screen_flash_notification_color_mode", 1);
        }
        refreshRadioPreference();
        return true;
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

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        launchCustomAppPickerFragment();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        Context context = this.mContext;
        ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0
                screenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0 =
                        new ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0(
                                this, 2);
        Map map = FlashNotificationUtil.ALIAS_MAP;
        Executors.newSingleThreadExecutor()
                .execute(
                        new FlashNotificationUtil$$ExternalSyntheticLambda0(
                                context,
                                screenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0));
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        Context context = this.mContext;
        ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0
                screenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0 =
                        new ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0(
                                this, 0);
        Map map = FlashNotificationUtil.ALIAS_MAP;
        Executors.newSingleThreadExecutor()
                .execute(
                        new FlashNotificationUtil$$ExternalSyntheticLambda0(
                                context,
                                screenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0));
        registerContentObserver();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        try {
            this.mContext
                    .getContentResolver()
                    .unregisterContentObserver(this.mScreenFlashModeObserver);
        } catch (IllegalArgumentException unused) {
        }
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
        refreshRadioPreference();
        if (getInstalledAppSet() != null) {
            this.appList = getScreenFlashColorAppList();
            refreshRadioPreferenceWithAppList();
        } else {
            Context context = this.mContext;
            ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0
                    screenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0 =
                            new ScreenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0(
                                    this, 1);
            Map map = FlashNotificationUtil.ALIAS_MAP;
            Executors.newSingleThreadExecutor()
                    .execute(
                            new FlashNotificationUtil$$ExternalSyntheticLambda0(
                                    context,
                                    screenFlashNotificationCustomAppsPreferenceController$$ExternalSyntheticLambda0));
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
