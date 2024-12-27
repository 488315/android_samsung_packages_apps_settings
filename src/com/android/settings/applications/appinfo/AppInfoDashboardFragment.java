package com.android.settings.applications.appinfo;

import android.app.Activity;
import android.app.ActivityThread;
import android.app.AppOpsManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.app.ecm.EnhancedConfirmationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.credentials.PermissionUtils;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.applications.manageapplications.ManageApplications;
import com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesDetailsPreferenceController;
import com.android.settings.applications.specialaccess.pictureinpicture.PictureInPictureDetailPreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.applications.AppCommonUtils;
import com.samsung.android.settings.applications.appinfo.SecAppSettingPreferenceController;
import com.samsung.android.settings.applications.appinfo.SecDefaultDCMHomeShortcutPreferenceController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppInfoDashboardFragment extends DashboardFragment
        implements ApplicationsState.Callbacks,
                ButtonActionDialogFragment.AppButtonsDialogListener {
    static final int REQUEST_UNINSTALL = 0;
    static final int UNINSTALL_ALL_USERS_MENU = 1;
    static final int UNINSTALL_UPDATES = 2;
    public AppButtonsPreferenceController mAppButtonsPreferenceController;
    public ApplicationsState.AppEntry mAppEntry;
    public RestrictedLockUtils.EnforcedAdmin mAppsControlDisallowedAdmin;
    public boolean mAppsControlDisallowedBySystem;
    public DevicePolicyManager mDpm;
    boolean mFinishing;
    public boolean mInitialized;
    public boolean mListeningToPackageRemove;
    public PackageInfo mPackageInfo;
    public String mPackageName;
    public PackageManager mPm;
    public boolean mShowUninstalled;
    public ApplicationsState mState;
    public int mUid;
    public int mUserId;
    public UserManager mUserManager;
    public boolean mUpdatedSysApp = false;
    public final List mCallbacks = new ArrayList();
    final BroadcastReceiver mPackageRemovedReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.applications.appinfo.AppInfoDashboardFragment.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (AppInfoDashboardFragment.this.mFinishing) {
                        return;
                    }
                    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    if (AppInfoDashboardFragment.this.mAppEntry.info.isResourceOverlay()
                            && TextUtils.equals(
                                    AppInfoDashboardFragment.this.mPackageInfo.overlayTarget,
                                    schemeSpecificPart)) {
                        AppInfoDashboardFragment.this.refreshUi();
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {
        void refreshUi();
    }

    public static void showLockScreen(Context context, final Runnable runnable) {
        if (!((KeyguardManager) context.getSystemService(KeyguardManager.class))
                .isKeyguardSecure()) {
            runnable.run();
            return;
        }
        BiometricPrompt.AuthenticationCallback authenticationCallback =
                new BiometricPrompt
                        .AuthenticationCallback() { // from class:
                                                    // com.android.settings.applications.appinfo.AppInfoDashboardFragment.1
                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationSucceeded(
                            BiometricPrompt.AuthenticationResult authenticationResult) {
                        runnable.run();
                    }

                    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                    public final void onAuthenticationError(int i, CharSequence charSequence) {}
                };
        BiometricPrompt.Builder useDefaultTitle =
                new BiometricPrompt.Builder(context).setUseDefaultSubtitle().setUseDefaultTitle();
        BiometricManager biometricManager =
                (BiometricManager) context.getSystemService(BiometricManager.class);
        if (biometricManager.canAuthenticate(33023) == 0) {
            useDefaultTitle.setAllowedAuthenticators(33023);
            useDefaultTitle.setSubtitle(biometricManager.getStrings(33023).getPromptMessage());
        }
        BiometricPrompt build = useDefaultTitle.build();
        final Handler handler = new Handler(Looper.getMainLooper());
        build.authenticate(
                new CancellationSignal(),
                new Executor() { // from class:
                                 // com.android.settings.applications.appinfo.AppInfoDashboardFragment$$ExternalSyntheticLambda1
                    @Override // java.util.concurrent.Executor
                    public final void execute(Runnable runnable2) {
                        handler.post(runnable2);
                    }
                },
                authenticationCallback);
    }

    public static void startAppInfoFragment(
            Class cls,
            int i,
            Bundle bundle,
            SettingsPreferenceFragment settingsPreferenceFragment,
            ApplicationsState.AppEntry appEntry) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("package", appEntry.info.packageName);
        bundle.putInt(NetworkAnalyticsConstants.DataPoints.UID, appEntry.info.uid);
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(settingsPreferenceFragment.getContext());
        String name = cls.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(i, null);
        subSettingLauncher.setResultListener(settingsPreferenceFragment, 1);
        launchRequest.mSourceMetricsCategory = settingsPreferenceFragment.getMetricsCategory();
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        retrieveAppEntry();
        if (this.mPackageInfo == null) {
            return null;
        }
        String packageName$1 = getPackageName$1();
        ArrayList arrayList = new ArrayList();
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        arrayList.add(
                new AppHeaderViewPreferenceController(
                        context, this, packageName$1, settingsLifecycle));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object obj = (AbstractPreferenceController) it.next();
            ((ArrayList) this.mCallbacks).add((Callback) obj);
        }
        arrayList.add(
                new InstantAppButtonsPreferenceController(
                        context, this, packageName$1, settingsLifecycle));
        AppButtonsPreferenceController appButtonsPreferenceController =
                new AppButtonsPreferenceController(
                        (SettingsActivity) getActivity(),
                        this,
                        settingsLifecycle,
                        packageName$1,
                        this.mState,
                        0,
                        5);
        this.mAppButtonsPreferenceController = appButtonsPreferenceController;
        appButtonsPreferenceController.setSupportedArchive(Flags.archiving());
        arrayList.add(this.mAppButtonsPreferenceController);
        arrayList.add(
                new AppBatteryPreferenceController(
                        context, this, packageName$1, getUid(), settingsLifecycle));
        arrayList.add(new AppMemoryPreferenceController(context, this, settingsLifecycle));
        arrayList.add(new DefaultHomeShortcutPreferenceController(context, packageName$1));
        arrayList.add(new DefaultBrowserShortcutPreferenceController(context, packageName$1));
        arrayList.add(new DefaultPhoneShortcutPreferenceController(context, packageName$1));
        arrayList.add(new DefaultEmergencyShortcutPreferenceController(context, packageName$1));
        arrayList.add(new DefaultSmsShortcutPreferenceController(context, packageName$1));
        arrayList.add(new SecDefaultDCMHomeShortcutPreferenceController(context, packageName$1));
        return arrayList;
    }

    public boolean ensureDisplayableModule(Activity activity) {
        Context applicationContext = activity.getApplicationContext();
        String str = this.mPackageName;
        Intent intent = AppUtils.sBrowserIntent;
        Boolean bool =
                (Boolean)
                        ApplicationsState.getInstance(
                                        (Application) applicationContext.getApplicationContext())
                                .mSystemModules
                                .get(str);
        if (!(bool == null ? false : bool.booleanValue())) {
            return true;
        }
        this.mFinishing = true;
        Log.w("AppInfoDashboard", "Package is hidden module, exiting: " + this.mPackageName);
        activity.finishAndRemoveTask();
        return false;
    }

    public boolean ensurePackageInfoAvailable(Activity activity) {
        if (this.mPackageInfo != null) {
            return true;
        }
        this.mFinishing = true;
        Log.w(
                "AppInfoDashboard",
                "Package info not available. Is this package already uninstalled?");
        activity.finishAndRemoveTask();
        return false;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        FragmentActivity activity = getActivity();
        Set set = AppCommonUtils.SEARCH_CLASS_NAME_LIST;
        if (activity == null || activity.getCallingActivity() == null) {
            return null;
        }
        if (((ArraySet) AppCommonUtils.SEARCH_CLASS_NAME_LIST)
                .contains(activity.getCallingActivity().getClassName())) {
            return ManageApplications.class.getName();
        }
        return null;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AppInfoDashboard";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 20;
    }

    public int getNumberOfUserWithPackageInstalled(String str) {
        int i = 0;
        for (UserInfo userInfo : this.mUserManager.getAliveUsers()) {
            try {
                if ((this.mPm.getApplicationInfoAsUser(str, 128, userInfo.id).flags & 8388608)
                        != 0) {
                    i++;
                }
            } catch (PackageManager.NameNotFoundException unused) {
                StringBuilder m =
                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                "Package: ", str, " not found for user: ");
                m.append(userInfo.id);
                Log.e("AppInfoDashboard", m.toString());
            }
        }
        return i;
    }

    public final String getPackageName$1() {
        String str = this.mPackageName;
        if (str != null) {
            return str;
        }
        Bundle arguments = getArguments();
        String string = arguments != null ? arguments.getString("package") : null;
        this.mPackageName = string;
        if (string == null) {
            Intent intent =
                    arguments == null
                            ? getActivity().getIntent()
                            : (Intent) arguments.getParcelable("intent");
            if (intent != null) {
                try {
                    this.mPackageName = intent.getData().getSchemeSpecificPart();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    this.mPackageName = null;
                }
            }
        }
        return this.mPackageName;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_app_info_settings;
    }

    public final int getUid() {
        int i = this.mUid;
        if (i > 0) {
            return i;
        }
        Bundle arguments = getArguments();
        int i2 = -1;
        int i3 =
                arguments != null ? arguments.getInt(NetworkAnalyticsConstants.DataPoints.UID) : -1;
        this.mUid = i3;
        if (i3 <= 0) {
            Intent intent =
                    arguments == null
                            ? getActivity().getIntent()
                            : (Intent) arguments.getParcelable("intent");
            if (intent != null && intent.getExtras() != null) {
                i2 = intent.getIntExtra("uId", -1);
                this.mUid = i2;
            }
            this.mUid = i2;
        }
        return this.mUid;
    }

    @Override // com.android.settings.applications.appinfo.ButtonActionDialogFragment.AppButtonsDialogListener
    public final void handleDialogClick(int i) {
        AppButtonsPreferenceController appButtonsPreferenceController =
                this.mAppButtonsPreferenceController;
        if (appButtonsPreferenceController != null) {
            appButtonsPreferenceController.handleDialogClick(i);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setAutoRemoveInsetCategory(false);
        if (getListView() != null) {
            getListView().mDrawLastRoundedCorner = false;
            getListView().setOverScrollMode(2);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 0) {
            getActivity().invalidateOptionsMenu();
        }
        AppButtonsPreferenceController appButtonsPreferenceController =
                this.mAppButtonsPreferenceController;
        if (appButtonsPreferenceController != null) {
            appButtonsPreferenceController.handleActivityResult(i, i2, intent);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        String packageName$1 = getPackageName$1();
        TimeSpentInAppPreferenceController timeSpentInAppPreferenceController =
                (TimeSpentInAppPreferenceController) use(TimeSpentInAppPreferenceController.class);
        if (packageName$1 == null || this.mAppEntry == null) {
            getActivity().finishActivity(1);
            getActivity().finishAndRemoveTask();
            return;
        }
        ((SecAppSettingPreferenceController) use(SecAppSettingPreferenceController.class))
                .setPackageName(packageName$1)
                .setParentFragment(this);
        timeSpentInAppPreferenceController.setPackageName(packageName$1);
        timeSpentInAppPreferenceController.setParentFragment(this);
        timeSpentInAppPreferenceController.initLifeCycleOwner(this);
        ((AppDataUsagePreferenceController) use(AppDataUsagePreferenceController.class))
                .setParentFragment(this);
        AppInstallerInfoPreferenceController appInstallerInfoPreferenceController =
                (AppInstallerInfoPreferenceController)
                        use(AppInstallerInfoPreferenceController.class);
        appInstallerInfoPreferenceController.setPackageName(packageName$1);
        appInstallerInfoPreferenceController.setParentFragment(this);
        ((AppInstallerPreferenceCategoryController)
                        use(AppInstallerPreferenceCategoryController.class))
                .setChildren(Arrays.asList(appInstallerInfoPreferenceController));
        ((AppNotificationPreferenceController) use(AppNotificationPreferenceController.class))
                .setParentFragment(this);
        ((AppOpenByDefaultPreferenceController) use(AppOpenByDefaultPreferenceController.class))
                .setPackageName(packageName$1)
                .setParentFragment(this);
        ((AppPermissionPreferenceController) use(AppPermissionPreferenceController.class))
                .setParentFragment(this);
        ((AppPermissionPreferenceController) use(AppPermissionPreferenceController.class))
                .setPackageName(packageName$1);
        ((AppPermissionPreferenceController) use(AppPermissionPreferenceController.class))
                .setUserId(UserHandle.myUserId());
        ((AppSettingPreferenceController) use(AppSettingPreferenceController.class))
                .setPackageName(packageName$1)
                .setParentFragment(this);
        ((AppAllServicesPreferenceController) use(AppAllServicesPreferenceController.class))
                .setParentFragment(this);
        ((AppAllServicesPreferenceController) use(AppAllServicesPreferenceController.class))
                .setPackageName(packageName$1);
        ((AppStoragePreferenceController) use(AppStoragePreferenceController.class))
                .setParentFragment(this);
        ((AppVersionPreferenceController) use(AppVersionPreferenceController.class))
                .setParentFragment(this);
        ((InstantAppDomainsPreferenceController) use(InstantAppDomainsPreferenceController.class))
                .setParentFragment(this);
        HibernationSwitchPreferenceController hibernationSwitchPreferenceController =
                (HibernationSwitchPreferenceController)
                        use(HibernationSwitchPreferenceController.class);
        hibernationSwitchPreferenceController.setParentFragment(this);
        hibernationSwitchPreferenceController.setPackage(packageName$1);
        hibernationSwitchPreferenceController.setSupportedArchive(Flags.archiving());
        WriteSystemSettingsPreferenceController writeSystemSettingsPreferenceController =
                (WriteSystemSettingsPreferenceController)
                        use(WriteSystemSettingsPreferenceController.class);
        writeSystemSettingsPreferenceController.setParentFragment(this);
        DrawOverlayDetailPreferenceController drawOverlayDetailPreferenceController =
                (DrawOverlayDetailPreferenceController)
                        use(DrawOverlayDetailPreferenceController.class);
        drawOverlayDetailPreferenceController.setParentFragment(this);
        PictureInPictureDetailPreferenceController pictureInPictureDetailPreferenceController =
                (PictureInPictureDetailPreferenceController)
                        use(PictureInPictureDetailPreferenceController.class);
        pictureInPictureDetailPreferenceController.setPackageName(packageName$1);
        pictureInPictureDetailPreferenceController.setParentFragment(this);
        ExternalSourceDetailPreferenceController externalSourceDetailPreferenceController =
                (ExternalSourceDetailPreferenceController)
                        use(ExternalSourceDetailPreferenceController.class);
        externalSourceDetailPreferenceController.setPackageName(packageName$1);
        externalSourceDetailPreferenceController.setParentFragment(this);
        InteractAcrossProfilesDetailsPreferenceController
                interactAcrossProfilesDetailsPreferenceController =
                        (InteractAcrossProfilesDetailsPreferenceController)
                                use(InteractAcrossProfilesDetailsPreferenceController.class);
        interactAcrossProfilesDetailsPreferenceController.setPackageName(packageName$1);
        interactAcrossProfilesDetailsPreferenceController.setParentFragment(this);
        AlarmsAndRemindersDetailPreferenceController alarmsAndRemindersDetailPreferenceController =
                (AlarmsAndRemindersDetailPreferenceController)
                        use(AlarmsAndRemindersDetailPreferenceController.class);
        alarmsAndRemindersDetailPreferenceController.setPackageName(packageName$1);
        alarmsAndRemindersDetailPreferenceController.setParentFragment(this);
        LongBackgroundTasksDetailsPreferenceController
                longBackgroundTasksDetailsPreferenceController =
                        (LongBackgroundTasksDetailsPreferenceController)
                                use(LongBackgroundTasksDetailsPreferenceController.class);
        longBackgroundTasksDetailsPreferenceController.setPackageName(packageName$1);
        longBackgroundTasksDetailsPreferenceController.setParentFragment(this);
        AdvancedAppInfoPreferenceCategoryController advancedAppInfoPreferenceCategoryController =
                (AdvancedAppInfoPreferenceCategoryController)
                        use(AdvancedAppInfoPreferenceCategoryController.class);
        advancedAppInfoPreferenceCategoryController.setChildren(
                Arrays.asList(
                        writeSystemSettingsPreferenceController,
                        drawOverlayDetailPreferenceController,
                        pictureInPictureDetailPreferenceController,
                        externalSourceDetailPreferenceController,
                        interactAcrossProfilesDetailsPreferenceController,
                        alarmsAndRemindersDetailPreferenceController,
                        longBackgroundTasksDetailsPreferenceController));
        advancedAppInfoPreferenceCategoryController.setAppEntry(this.mAppEntry);
        ((AppLocalePreferenceController) use(AppLocalePreferenceController.class))
                .setParentFragment(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Intent intent;
        super.onCreate(bundle);
        this.mFinishing = false;
        FragmentActivity activity = getActivity();
        if (!Utils.isDeviceProvisioned(activity) && Utils.isFrpChallengeRequired(activity)) {
            Log.i("AppInfoDashboard", "Refusing to start because device is not provisioned");
            activity.finish();
            return;
        }
        this.mDpm = (DevicePolicyManager) activity.getSystemService("device_policy");
        this.mUserManager = (UserManager) activity.getSystemService("user");
        this.mPm = activity.getPackageManager();
        if (ensurePackageInfoAvailable(activity) && ensureDisplayableModule(activity)) {
            startListeningToPackageRemove();
            setHasOptionsMenu(true);
            setAutoRemoveInsetCategory(false);
            if (!AppUtils.isInstant(this.mPackageInfo.applicationInfo)) {
                removePreference("instant_app_buttons_inset");
                removePreference("instant_app_buttons_inset2");
            }
            Intent intent2 =
                    new Intent("com.sec.android.intent.action.SEC_APPLICATION_SETTINGS")
                            .setPackage(getPackageName$1());
            ResolveInfo resolveActivity =
                    getContext().getPackageManager().resolveActivity(intent2, 0);
            if (resolveActivity != null) {
                Intent intent3 = new Intent(intent2.getAction());
                ActivityInfo activityInfo = resolveActivity.activityInfo;
                intent = intent3.setClassName(activityInfo.packageName, activityInfo.name);
            } else {
                intent = null;
            }
            if (intent == null) {
                removePreference("app_setting_preference_inset1");
                removePreference("app_setting_preference_inset2");
            }
            replaceEnterpriseStringTitle(
                    "interact_across_profiles",
                    "Settings.CONNECTED_WORK_AND_PERSONAL_APPS_TITLE",
                    R.string.interact_across_profiles_title);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menu.add(0, 2, 0, R.string.app_factory_reset).setShowAsAction(0);
        menu.add(0, 1, 1, R.string.uninstall_all_users_text).setShowAsAction(0);
        menu.add(0, 4, 0, R.string.app_restricted_settings_lockscreen_title).setShowAsAction(0);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        if (ensurePackageInfoAvailable(getActivity())) {
            super.onCreatePreferences(bundle, str);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        AppButtonsPreferenceController appButtonsPreferenceController =
                this.mAppButtonsPreferenceController;
        if (appButtonsPreferenceController != null) {
            appButtonsPreferenceController.initBottomButtonsLayout();
        }
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (this.mListeningToPackageRemove) {
            this.mListeningToPackageRemove = false;
            getContext().unregisterReceiver(this.mPackageRemovedReceiver);
        }
        super.onDestroy();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            uninstallPkg$1(this.mAppEntry.info.packageName, true);
            return true;
        }
        if (itemId == 2) {
            uninstallPkg$1(this.mAppEntry.info.packageName, false);
            return true;
        }
        if (itemId != 4) {
            return super.onOptionsItemSelected(menuItem);
        }
        showLockScreen(
                getContext(),
                new Runnable() { // from class:
                                 // com.android.settings.applications.appinfo.AppInfoDashboardFragment$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppInfoDashboardFragment appInfoDashboardFragment =
                                AppInfoDashboardFragment.this;
                        appInfoDashboardFragment.getClass();
                        if (com.android.internal.hidden_from_bootclasspath.android.permission.flags
                                        .Flags.enhancedConfirmationModeApisEnabled()
                                && android.security.Flags.extendEcmToAllSettings()) {
                            try {
                                ((EnhancedConfirmationManager)
                                                appInfoDashboardFragment
                                                        .getContext()
                                                        .getSystemService(
                                                                EnhancedConfirmationManager.class))
                                        .clearRestriction(
                                                appInfoDashboardFragment.getPackageName$1());
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.e(
                                        "AppInfoDashboard",
                                        "Exception when retrieving package:"
                                                + appInfoDashboardFragment.getPackageName$1(),
                                        e);
                            }
                        } else {
                            ((AppOpsManager)
                                            appInfoDashboardFragment
                                                    .getContext()
                                                    .getSystemService(AppOpsManager.class))
                                    .setMode(
                                            119,
                                            appInfoDashboardFragment.getUid(),
                                            appInfoDashboardFragment.getPackageName$1(),
                                            0);
                        }
                        appInfoDashboardFragment.getActivity().invalidateOptionsMenu();
                        Toast.makeText(
                                        appInfoDashboardFragment.getContext(),
                                        appInfoDashboardFragment
                                                .getContext()
                                                .getString(
                                                        R.string
                                                                .toast_allows_restricted_settings_successfully,
                                                        appInfoDashboardFragment.mAppEntry.label),
                                        1)
                                .show();
                    }
                });
        return true;
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageListChanged() {
        if (refreshUi()) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(AppButtonsPreferenceController.APP_CHG, true);
        ((SettingsActivity) getActivity()).finishPreferencePanel(intent);
        this.mFinishing = true;
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageSizeChanged(String str) {
        if (TextUtils.equals(str, this.mPackageName)) {
            refreshUi();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        boolean z;
        boolean z2;
        if (this.mFinishing) {
            return;
        }
        super.onPrepareOptionsMenu(menu);
        MenuItem findItem = menu.findItem(1);
        if (findItem != null) {
            ApplicationsState.AppEntry appEntry = this.mAppEntry;
            if (appEntry.info.isArchived) {
                findItem.setVisible(false);
            } else {
                findItem.setVisible(
                        shouldShowUninstallForAll(appEntry)
                                && !this.mAppsControlDisallowedBySystem);
                if (findItem.isVisible()) {
                    RestrictedLockUtilsInternal.setMenuItemAsDisabledByAdmin(
                            getActivity(), findItem, this.mAppsControlDisallowedAdmin);
                }
            }
        }
        MenuItem findItem2 = menu.findItem(4);
        if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags
                        .enhancedConfirmationModeApisEnabled()
                && android.security.Flags.extendEcmToAllSettings()) {
            try {
                z =
                        ((EnhancedConfirmationManager)
                                        getSystemService(EnhancedConfirmationManager.class))
                                .isClearRestrictionAllowed(getPackageName$1());
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(
                        "AppInfoDashboard",
                        "Exception when retrieving package:" + getPackageName$1(),
                        e);
            }
        } else {
            try {
                if (((AppOpsManager) getSystemService(AppOpsManager.class))
                                .noteOpNoThrow(119, getUid(), getPackageName$1())
                        == 1) {
                    z = true;
                }
            } catch (Exception unused) {
            }
            z = false;
        }
        findItem2.setVisible(z);
        boolean z3 = (this.mAppEntry.info.flags & 128) != 0;
        this.mUpdatedSysApp = z3;
        if (z3 && this.mPackageInfo != null) {
            if (ActivityThread.getPackageManager()
                    .isSystemCompressedPackage(
                            this.mPackageInfo.packageName, UserHandle.myUserId())) {
                Log.i("AppInfoDashboard", "isSystemCompressedPackage, set mUpdatedSysApp false");
            } else {
                ApplicationInfo applicationInfo =
                        this.mPm.getApplicationInfo(this.mAppEntry.info.packageName, 128);
                Bundle bundle = applicationInfo.metaData;
                if (bundle != null
                        && bundle.getBoolean("com.samsung.system.essential")
                        && applicationInfo.isSystemApp()) {
                    Log.i(
                            "AppInfoDashboard",
                            "set mUpdatedSysApp false because application ("
                                    + this.mAppEntry.info.packageName
                                    + ") has specific metdatada");
                }
                z2 = true;
                this.mUpdatedSysApp = z2;
            }
            z2 = false;
            this.mUpdatedSysApp = z2;
        }
        MenuItem findItem3 = menu.findItem(2);
        if (findItem3 != null) {
            findItem3.setVisible(
                    (!this.mUserManager.isAdminUser()
                                    || !this.mUpdatedSysApp
                                    || this.mAppsControlDisallowedBySystem
                                    || PermissionUtils.hasPermission(
                                            getActivity(),
                                            this.mAppEntry.info.packageName,
                                            "android.permission.CONTROL_KEYGUARD")
                                    || getContext()
                                            .getResources()
                                            .getBoolean(R.bool.config_disable_uninstall_update))
                            ? false
                            : true);
            if (findItem3.isVisible()) {
                RestrictedLockUtilsInternal.setMenuItemAsDisabledByAdmin(
                        getActivity(), findItem3, this.mAppsControlDisallowedAdmin);
                PackageInfo packageInfo = this.mPackageInfo;
                if (packageInfo == null
                        || !"com.samsung.knox.securefolder".equals(packageInfo.packageName)) {
                    return;
                }
                findItem3.setEnabled(false);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        this.mAppsControlDisallowedAdmin =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        activity, this.mUserId, "no_control_apps");
        this.mAppsControlDisallowedBySystem =
                RestrictedLockUtilsInternal.hasBaseUserRestriction(
                        activity, this.mUserId, "no_control_apps");
        if (!refreshUi()) {
            Intent intent = new Intent();
            intent.putExtra(AppButtonsPreferenceController.APP_CHG, true);
            ((SettingsActivity) getActivity()).finishPreferencePanel(intent);
            this.mFinishing = true;
        }
        getActivity().invalidateOptionsMenu();
    }

    public boolean refreshUi() {
        retrieveAppEntry();
        if (this.mAppEntry == null || this.mPackageInfo == null) {
            return false;
        }
        Iterator it = ((ArrayList) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).refreshUi();
        }
        if (this.mAppButtonsPreferenceController.isAvailable()) {
            this.mAppButtonsPreferenceController.refreshUi();
        }
        if (this.mInitialized) {
            try {
                ApplicationInfo applicationInfo =
                        getActivity()
                                .getPackageManager()
                                .getApplicationInfo(
                                        this.mAppEntry.info.packageName,
                                        PackageManager.ApplicationInfoFlags.of(4299162112L));
                if (SemDualAppManager.isDualAppId(this.mUserId)
                        && !SemDualAppManager.isInstalledWhitelistedPackage(
                                this.mAppEntry.info.packageName)) {
                    return false;
                }
                boolean z = this.mAppEntry.info.isArchived;
                if (!this.mShowUninstalled && !z) {
                    return (applicationInfo.flags & 8388608) != 0;
                }
                updatePreferenceStates();
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        } else {
            this.mInitialized = true;
            this.mShowUninstalled = (this.mAppEntry.info.flags & 8388608) == 0;
        }
        return true;
    }

    public void retrieveAppEntry() {
        FragmentActivity activity = getActivity();
        if (activity == null || this.mFinishing) {
            return;
        }
        if (this.mState == null) {
            ApplicationsState applicationsState =
                    ApplicationsState.getInstance(activity.getApplication());
            this.mState = applicationsState;
            applicationsState.newSession(this, getSettingsLifecycle());
        }
        this.mUserId = UserHandle.myUserId();
        ApplicationsState.AppEntry entry =
                this.mState.getEntry(UserHandle.myUserId(), getPackageName$1());
        this.mAppEntry = entry;
        if (entry == null) {
            Log.w("AppInfoDashboard", "Missing AppEntry; maybe reinstalling?");
            this.mPackageInfo = null;
            return;
        }
        try {
            this.mPackageInfo =
                    activity.getPackageManager()
                            .getPackageInfoAsUser(
                                    this.mAppEntry.info.packageName,
                                    PackageManager.PackageInfoFlags.of(4299166272L),
                                    this.mUserId);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(
                    "AppInfoDashboard",
                    "Exception when retrieving package:" + this.mAppEntry.info.packageName,
                    e);
        }
    }

    public boolean shouldShowUninstallForAll(ApplicationsState.AppEntry appEntry) {
        PackageInfo packageInfo;
        if (this.mUpdatedSysApp
                || appEntry == null
                || (appEntry.info.flags & 1) != 0
                || (packageInfo = this.mPackageInfo) == null
                || this.mDpm.packageHasActiveAdmins(packageInfo.packageName)
                || UserHandle.myUserId() != 0
                || this.mUserManager.getUsers().size() < 2) {
            return false;
        }
        return ((getNumberOfUserWithPackageInstalled(this.mPackageName) < 2
                                && (appEntry.info.flags & 8388608) != 0)
                        || AppUtils.isInstant(appEntry.info)
                        || "com.samsung.knox.securefolder".equals(this.mPackageInfo.packageName))
                ? false
                : true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean shouldSkipForInitialSUW() {
        return true;
    }

    public void startListeningToPackageRemove() {
        if (this.mListeningToPackageRemove) {
            return;
        }
        this.mListeningToPackageRemove = true;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        getContext().registerReceiver(this.mPackageRemovedReceiver, intentFilter);
    }

    public final void uninstallPkg$1(String str, boolean z) {
        if (this.mListeningToPackageRemove) {
            this.mListeningToPackageRemove = false;
            getContext().unregisterReceiver(this.mPackageRemovedReceiver);
        }
        Intent intent =
                new Intent("android.intent.action.UNINSTALL_PACKAGE", Uri.parse("package:" + str));
        intent.putExtra("android.intent.extra.UNINSTALL_ALL_USERS", z);
        this.mMetricsFeatureProvider.action(getContext(), 872, new Pair[0]);
        startActivityForResult(intent, 0);
    }

    public static void startAppInfoFragment(
            Class cls, ApplicationInfo applicationInfo, Context context, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("package", applicationInfo.packageName);
        bundle.putInt(NetworkAnalyticsConstants.DataPoints.UID, applicationInfo.uid);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = cls.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        launchRequest.mUserHandle = UserHandle.getUserHandleForUid(applicationInfo.uid);
        launchRequest.mSourceMetricsCategory = i;
        subSettingLauncher.launch();
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onAllSizesComputed() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLauncherInfoChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLoadEntriesCompleted() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageIconChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRebuildComplete(ArrayList arrayList) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRunningStateChanged(boolean z) {}
}
