package com.android.settings.applications.appinfo;

import android.app.ActivityManager;
import android.app.Application;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.OverlayManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.permission.PermissionControllerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settings.applications.ApplicationFeatureProvider;
import com.android.settings.applications.ApplicationFeatureProviderImpl;
import com.android.settings.applications.specialaccess.deviceadmin.DeviceAdminAdd;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.instrumentation.SettingsStatsLog;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.spa.app.appinfo.AppButtonRepository;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.ActionButtonsPreference;

import com.att.iqi.lib.IQIManager;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdhms.SemAppRestrictionManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppButtonsPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin,
                LifecycleObserver,
                OnResume,
                OnDestroy,
                OnPause,
                ApplicationsState.Callbacks {
    public static final String APP_CHG = "chg";
    private static final String ARG_PACKAGE_UID = "uid";
    private static final String GEAR_MANAGER_PKG_NAME = "com.samsung.android.app.watchmanager";
    private static final String KEY_ACTION_BUTTONS = "action_buttons";
    public static final String KEY_REMOVE_TASK_WHEN_FINISHING = "remove_task_when_finishing";
    private static final boolean LOCAL_LOGV = false;
    private static final int REQUEST_DUAL_APP_ARCHIVE = 16729;
    private static final int REQUEST_DUAL_APP_UNINSTALL = 2;
    private static final int RESULT_DUAL_APP_ARCHIVE_FAIL = 4;
    private static final boolean SEC_DEBUG = true;
    private static final String SIDE_SYNC_PKG_NAME = "com.sec.android.sidesync30";
    private static final String SIDE_SYNC_SOURCE_PKG_NAME = "com.sec.android.sidesync.source";
    private static final String TAG = "AppButtonsPrefCtl";
    private boolean mAccessedFromAutoRevoke;
    private final SettingsActivity mActivity;
    private AppButtonRepository mAppButtonRepository;
    ApplicationsState.AppEntry mAppEntry;
    private Intent mAppLaunchIntent;
    private final ApplicationFeatureProvider mApplicationFeatureProvider;
    private RestrictedLockUtils.EnforcedAdmin mAppsControlDisallowedAdmin;
    private boolean mAppsControlDisallowedBySystem;
    private final BroadcastReceiver mArchiveBroadcastReceiver;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private RelativeLayout mButton4Container;
    ActionButtonsPreference mButtonsPref;
    private final BroadcastReceiver mCheckKillProcessesReceiver;
    private boolean mDisableInProgress;
    final HashMap<String, String> mDisableOverridedPackages;
    private final DevicePolicyManager mDpm;
    private boolean mFinishing;
    private boolean mForceStopInProgress;
    private final InstrumentedPreferenceFragment mFragment;
    private int mHibernationEligibility;
    final HashSet<String> mHomePackages;
    private boolean mIsRestoringState;
    private boolean mIsSupportedArchive;
    private boolean mListeningToPackageRemove;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private final OverlayManager mOverlayManager;
    PackageInfo mPackageInfo;
    String mPackageName;
    private final BroadcastReceiver mPackageRemovedReceiver;
    private PermissionControllerManager mPermissionControllerManager;
    private final PackageManager mPm;
    private LinearLayout mProgress;
    private final int mRequestRemoveDeviceAdmin;
    private final int mRequestUninstall;
    private final BroadcastReceiver mRestoreBroadcastReceiver;
    private PreferenceScreen mScreen;
    private ApplicationsState.Session mSession;
    private PackageInstaller.SessionCallback mSessionCallback;
    private long mSessionId;
    private final String[] mShouldNotDisabledPackages;
    ApplicationsState mState;
    private final int mUserId;
    private final UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ArchiveButtonListener implements View.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AppButtonsPreferenceController this$0;

        public /* synthetic */ ArchiveButtonListener(
                AppButtonsPreferenceController appButtonsPreferenceController, int i) {
            this.$r8$classId = i;
            this.this$0 = appButtonsPreferenceController;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            switch (this.$r8$classId) {
                case 0:
                    SemDualAppManager semDualAppManager =
                            SemDualAppManager.getInstance(
                                    ((AbstractPreferenceController) this.this$0).mContext);
                    String str = this.this$0.mAppEntry.info.packageName;
                    if (semDualAppManager != null
                            && semDualAppManager.isSupported()
                            && SemDualAppManager.isDualAppId(
                                    SemDualAppManager.getDualAppProfileId())
                            && SemDualAppManager.isInstalledWhitelistedPackage(str)
                            && this.this$0.mUserId == 0) {
                        Intent intent = new Intent();
                        intent.setClassName(
                                "com.samsung.android.da.daagent",
                                "com.samsung.android.da.daagent.RemoveDualIM");
                        intent.putExtra("packageName", str);
                        intent.putExtra("userId", this.this$0.mUserId);
                        intent.putExtra("archive_button", true);
                        this.this$0.mFragment.startActivityForResult(
                                intent, AppButtonsPreferenceController.REQUEST_DUAL_APP_ARCHIVE);
                        break;
                    } else {
                        Intent intent2 = new Intent("com.android.settings.archive.action");
                        intent2.setPackage(
                                ((AbstractPreferenceController) this.this$0)
                                        .mContext.getPackageName());
                        try {
                            ((AbstractPreferenceController) this.this$0)
                                    .mContext
                                    .getPackageManager()
                                    .getPackageInstaller()
                                    .requestArchive(
                                            this.this$0.mPackageName,
                                            PendingIntent.getBroadcastAsUser(
                                                            ((AbstractPreferenceController)
                                                                            this.this$0)
                                                                    .mContext,
                                                            0,
                                                            intent2,
                                                            1107296256,
                                                            UserHandle.of(UserHandle.myUserId()))
                                                    .getIntentSender());
                            break;
                        } catch (Exception unused) {
                            Preference$$ExternalSyntheticOutline0.m(
                                    ((AbstractPreferenceController) this.this$0).mContext,
                                    R.string.archive_fail,
                                    new Object[] {this.this$0.mAppEntry.label},
                                    ((AbstractPreferenceController) this.this$0).mContext,
                                    0);
                            return;
                        }
                    }
                    break;
                case 1:
                    this.this$0.mMetricsFeatureProvider.action(
                            this.this$0.mActivity, 1775, this.this$0.getPackageNameForMetric());
                    PackageManager packageManager = this.this$0.mPm;
                    AppButtonsPreferenceController appButtonsPreferenceController = this.this$0;
                    if (!packageManager.isPackageStateProtected(
                            appButtonsPreferenceController.mAppEntry.info.packageName,
                            appButtonsPreferenceController.mUserId)) {
                        if (this.this$0.mAppsControlDisallowedAdmin != null
                                && !this.this$0.mAppsControlDisallowedBySystem) {
                            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                    this.this$0.mActivity, this.this$0.mAppsControlDisallowedAdmin);
                            break;
                        } else {
                            this.this$0.showDialogInner(2);
                            break;
                        }
                    } else {
                        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                this.this$0.mActivity,
                                RestrictedLockUtilsInternal.getDeviceOwner(this.this$0.mActivity));
                        break;
                    }
                    break;
                case 2:
                    Intent intent3 = new Intent("com.android.settings.unarchive.action");
                    intent3.setPackage(
                            ((AbstractPreferenceController) this.this$0).mContext.getPackageName());
                    try {
                        ((AbstractPreferenceController) this.this$0)
                                .mContext
                                .getPackageManager()
                                .getPackageInstaller()
                                .requestUnarchive(
                                        this.this$0.mPackageName,
                                        PendingIntent.getBroadcastAsUser(
                                                        ((AbstractPreferenceController) this.this$0)
                                                                .mContext,
                                                        0,
                                                        intent3,
                                                        1107296256,
                                                        UserHandle.of(UserHandle.myUserId()))
                                                .getIntentSender());
                        break;
                    } catch (Exception unused2) {
                        Preference$$ExternalSyntheticOutline0.m(
                                ((AbstractPreferenceController) this.this$0).mContext,
                                R.string.restore_fail,
                                new Object[] {this.this$0.mAppEntry.label},
                                ((AbstractPreferenceController) this.this$0).mContext,
                                0);
                        return;
                    }
                default:
                    if (this.this$0.mAccessedFromAutoRevoke) {
                        Log.i(
                                AppButtonsPreferenceController.TAG,
                                "sessionId: "
                                        + this.this$0.mSessionId
                                        + " uninstalling "
                                        + this.this$0.mPackageName
                                        + " with uid "
                                        + this.this$0.getUid()
                                        + ", reached from auto revoke");
                        SettingsStatsLog.write(
                                this.this$0.getUid(),
                                5,
                                this.this$0.mSessionId,
                                this.this$0.mPackageName);
                    }
                    AppButtonsPreferenceController appButtonsPreferenceController2 = this.this$0;
                    String str2 = appButtonsPreferenceController2.mAppEntry.info.packageName;
                    try {
                        ApplicationsState applicationsState =
                                appButtonsPreferenceController2.mState;
                        applicationsState.getClass();
                        if (str2 != null) {
                            try {
                                if (str2.length() > 0) {
                                    applicationsState.mRefreshCandidatePkgName = str2;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    AppButtonsPreferenceController appButtonsPreferenceController3 = this.this$0;
                    if (appButtonsPreferenceController3.mPackageInfo != null
                            && appButtonsPreferenceController3.mDpm.packageHasActiveAdmins(
                                    this.this$0.mPackageInfo.packageName)) {
                        this.this$0.stopListeningToPackageRemove();
                        Intent intent4 =
                                new Intent(this.this$0.mActivity, (Class<?>) DeviceAdminAdd.class);
                        intent4.putExtra("android.app.extra.DEVICE_ADMIN_PACKAGE_NAME", str2);
                        this.this$0.mMetricsFeatureProvider.action(
                                this.this$0.mActivity, 873, this.this$0.getPackageNameForMetric());
                        this.this$0.mFragment.startActivityForResult(
                                intent4, this.this$0.mRequestRemoveDeviceAdmin);
                        break;
                    } else {
                        SemDualAppManager semDualAppManager2 =
                                SemDualAppManager.getInstance(
                                        ((AbstractPreferenceController) this.this$0).mContext);
                        if (semDualAppManager2 == null
                                || !semDualAppManager2.isSupported()
                                || !SemDualAppManager.isInstalledWhitelistedPackage(str2)
                                || (!SemDualAppManager.isDualAppId(this.this$0.mUserId)
                                        && this.this$0.mUserId != 0)) {
                            String str3 = this.this$0.mDisableOverridedPackages.get(str2);
                            if (!TextUtils.isEmpty(str3)) {
                                Intent intent5 = new Intent(str3);
                                Log.i(
                                        AppButtonsPreferenceController.TAG,
                                        "DisableOverrided package : "
                                                + str2
                                                + " , intent : "
                                                + str3);
                                if (Utils.isIntentAvailable(
                                        ((AbstractPreferenceController) this.this$0).mContext,
                                        intent5)) {
                                    ((AbstractPreferenceController) this.this$0)
                                            .mContext.startActivity(intent5);
                                    break;
                                }
                            }
                            RestrictedLockUtils.EnforcedAdmin checkIfUninstallBlocked =
                                    RestrictedLockUtilsInternal.checkIfUninstallBlocked(
                                            this.this$0.mActivity, this.this$0.mUserId, str2);
                            boolean z =
                                    this.this$0.mAppsControlDisallowedBySystem
                                            || RestrictedLockUtilsInternal.hasBaseUserRestriction(
                                                    this.this$0.mActivity,
                                                    this.this$0.mUserId,
                                                    str2);
                            if (checkIfUninstallBlocked != null && !z) {
                                RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                        this.this$0.mActivity, checkIfUninstallBlocked);
                                break;
                            } else if (!AppUtils.isAutoDisabled(this.this$0.mAppEntry.info)
                                    && !AppUtils.isManualDisabled(this.this$0.mAppEntry.info)) {
                                AppButtonsPreferenceController appButtonsPreferenceController4 =
                                        this.this$0;
                                ApplicationInfo applicationInfo =
                                        appButtonsPreferenceController4.mAppEntry.info;
                                int i = applicationInfo.flags;
                                if ((i & 1) == 0) {
                                    if ((8388608 & i) != 0) {
                                        appButtonsPreferenceController4.uninstallPkg(str2, false);
                                        break;
                                    } else {
                                        appButtonsPreferenceController4.uninstallPkg(str2, true);
                                        break;
                                    }
                                } else if (applicationInfo.enabled
                                        && !appButtonsPreferenceController4.isDisabledUntilUsed()) {
                                    this.this$0.showDialogInner(0);
                                    break;
                                } else {
                                    MetricsFeatureProvider metricsFeatureProvider =
                                            this.this$0.mMetricsFeatureProvider;
                                    SettingsActivity settingsActivity = this.this$0.mActivity;
                                    AppButtonsPreferenceController appButtonsPreferenceController5 =
                                            this.this$0;
                                    metricsFeatureProvider.action(
                                            settingsActivity,
                                            appButtonsPreferenceController5.mAppEntry.info.enabled
                                                    ? 874
                                                    : 875,
                                            appButtonsPreferenceController5
                                                    .getPackageNameForMetric());
                                    AppButtonsPreferenceController appButtonsPreferenceController6 =
                                            this.this$0;
                                    appButtonsPreferenceController6.setGoogleCoreControl(
                                            appButtonsPreferenceController6
                                                    .mAppEntry
                                                    .info
                                                    .packageName,
                                            0);
                                    AsyncTask.execute(
                                            new DisableChangerRunnable(
                                                    this.this$0.mPm,
                                                    this.this$0.mAppEntry.info.packageName,
                                                    0));
                                    break;
                                }
                            } else {
                                ApplicationInfo applicationInfo2 = this.this$0.mAppEntry.info;
                                new SemAppRestrictionManager()
                                        .restrict(
                                                3,
                                                2,
                                                true,
                                                applicationInfo2.packageName,
                                                applicationInfo2.uid);
                                if (this.this$0.mFragment instanceof AppInfoDashboardFragment) {
                                    ((AppInfoDashboardFragment) this.this$0.mFragment).refreshUi();
                                }
                                this.this$0.updateUninstallButton();
                                break;
                            }
                        } else {
                            this.this$0.stopListeningToPackageRemove();
                            Intent intent6 = new Intent();
                            intent6.setClassName(
                                    "com.samsung.android.da.daagent",
                                    "com.samsung.android.da.daagent.RemoveDualIM");
                            intent6.putExtra("packageName", str2);
                            intent6.putExtra("userId", this.this$0.mUserId);
                            intent6.putExtra("archive_button", false);
                            this.this$0.mFragment.startActivityForResult(intent6, 2);
                            break;
                        }
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DisableChangerRunnable implements Runnable {
        public final String mPackageName;
        public final PackageManager mPm;
        public final int mState;

        public DisableChangerRunnable(PackageManager packageManager, String str, int i) {
            this.mPm = packageManager;
            this.mPackageName = str;
            this.mState = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.mPm.setApplicationEnabledSetting(this.mPackageName, this.mState, 0);
        }
    }

    public AppButtonsPreferenceController(
            SettingsActivity settingsActivity,
            InstrumentedPreferenceFragment instrumentedPreferenceFragment,
            Lifecycle lifecycle,
            String str,
            ApplicationsState applicationsState,
            int i,
            int i2) {
        super(settingsActivity, KEY_ACTION_BUTTONS);
        String[] split;
        this.mHomePackages = new HashSet<>();
        this.mListeningToPackageRemove = false;
        this.mFinishing = false;
        this.mForceStopInProgress = false;
        this.mDisableInProgress = false;
        this.mIsSupportedArchive = false;
        this.mIsRestoringState = false;
        this.mShouldNotDisabledPackages = new String[] {"com.android.statementservice"};
        this.mDisableOverridedPackages = new HashMap<>();
        this.mHibernationEligibility = -1;
        final int i3 = 0;
        this.mCheckKillProcessesReceiver =
                new BroadcastReceiver(
                        this) { // from class:
                                // com.android.settings.applications.appinfo.AppButtonsPreferenceController.1
                    public final /* synthetic */ AppButtonsPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        ApplicationsState.AppEntry appEntry;
                        ApplicationInfo applicationInfo;
                        switch (i3) {
                            case 0:
                                boolean z = getResultCode() != 0;
                                ApplicationsState.AppEntry appEntry2 = this.this$0.mAppEntry;
                                if (appEntry2 != null && appEntry2.info != null) {
                                    Log.d(
                                            AppButtonsPreferenceController.TAG,
                                            "Got broadcast response: Restart status for "
                                                    + this.this$0.mAppEntry.info.packageName
                                                    + " "
                                                    + z);
                                }
                                this.this$0.updateForceStopButtonInner(z);
                                break;
                            case 1:
                                String schemeSpecificPart =
                                        intent.getData().getSchemeSpecificPart();
                                if (!this.this$0.mFinishing
                                        && (appEntry = this.this$0.mAppEntry) != null
                                        && (applicationInfo = appEntry.info) != null
                                        && applicationInfo.packageName.equals(schemeSpecificPart)) {
                                    this.this$0.mActivity.finishAndRemoveTask();
                                    break;
                                }
                                break;
                            case 2:
                                if (this.this$0.mPackageName.equals(
                                        intent.getStringExtra(
                                                "android.content.pm.extra.PACKAGE_NAME"))) {
                                    if (intent.getIntExtra(
                                                    "android.content.pm.extra.UNARCHIVE_STATUS",
                                                    Integer.MIN_VALUE)
                                            != 0) {
                                        Intent intent2 =
                                                (Intent)
                                                        intent.getParcelableExtra(
                                                                "android.intent.extra.INTENT");
                                        if (intent2 == null) {
                                            Preference$$ExternalSyntheticOutline0.m(
                                                    context,
                                                    R.string.restore_fail,
                                                    new Object[] {this.this$0.mAppEntry.label},
                                                    context,
                                                    0);
                                            break;
                                        } else {
                                            context.startActivityAsUser(
                                                    intent2, new UserHandle(this.this$0.mUserId));
                                            break;
                                        }
                                    } else {
                                        this.this$0.mButton4.setVisibility(8);
                                        this.this$0.mProgress.setVisibility(0);
                                        this.this$0.mIsRestoringState = true;
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.restore_start,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    }
                                }
                                break;
                            default:
                                if (this.this$0.mPackageName.equals(
                                        intent.getStringExtra(
                                                "android.content.pm.extra.PACKAGE_NAME"))) {
                                    if (intent.getIntExtra(
                                                    "android.content.pm.extra.STATUS",
                                                    Integer.MIN_VALUE)
                                            != 0) {
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.archive_fail,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    } else {
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.archive_success,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i4 = 1;
        this.mPackageRemovedReceiver =
                new BroadcastReceiver(
                        this) { // from class:
                                // com.android.settings.applications.appinfo.AppButtonsPreferenceController.1
                    public final /* synthetic */ AppButtonsPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        ApplicationsState.AppEntry appEntry;
                        ApplicationInfo applicationInfo;
                        switch (i4) {
                            case 0:
                                boolean z = getResultCode() != 0;
                                ApplicationsState.AppEntry appEntry2 = this.this$0.mAppEntry;
                                if (appEntry2 != null && appEntry2.info != null) {
                                    Log.d(
                                            AppButtonsPreferenceController.TAG,
                                            "Got broadcast response: Restart status for "
                                                    + this.this$0.mAppEntry.info.packageName
                                                    + " "
                                                    + z);
                                }
                                this.this$0.updateForceStopButtonInner(z);
                                break;
                            case 1:
                                String schemeSpecificPart =
                                        intent.getData().getSchemeSpecificPart();
                                if (!this.this$0.mFinishing
                                        && (appEntry = this.this$0.mAppEntry) != null
                                        && (applicationInfo = appEntry.info) != null
                                        && applicationInfo.packageName.equals(schemeSpecificPart)) {
                                    this.this$0.mActivity.finishAndRemoveTask();
                                    break;
                                }
                                break;
                            case 2:
                                if (this.this$0.mPackageName.equals(
                                        intent.getStringExtra(
                                                "android.content.pm.extra.PACKAGE_NAME"))) {
                                    if (intent.getIntExtra(
                                                    "android.content.pm.extra.UNARCHIVE_STATUS",
                                                    Integer.MIN_VALUE)
                                            != 0) {
                                        Intent intent2 =
                                                (Intent)
                                                        intent.getParcelableExtra(
                                                                "android.intent.extra.INTENT");
                                        if (intent2 == null) {
                                            Preference$$ExternalSyntheticOutline0.m(
                                                    context,
                                                    R.string.restore_fail,
                                                    new Object[] {this.this$0.mAppEntry.label},
                                                    context,
                                                    0);
                                            break;
                                        } else {
                                            context.startActivityAsUser(
                                                    intent2, new UserHandle(this.this$0.mUserId));
                                            break;
                                        }
                                    } else {
                                        this.this$0.mButton4.setVisibility(8);
                                        this.this$0.mProgress.setVisibility(0);
                                        this.this$0.mIsRestoringState = true;
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.restore_start,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    }
                                }
                                break;
                            default:
                                if (this.this$0.mPackageName.equals(
                                        intent.getStringExtra(
                                                "android.content.pm.extra.PACKAGE_NAME"))) {
                                    if (intent.getIntExtra(
                                                    "android.content.pm.extra.STATUS",
                                                    Integer.MIN_VALUE)
                                            != 0) {
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.archive_fail,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    } else {
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.archive_success,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        this.mSessionCallback =
                new PackageInstaller
                        .SessionCallback() { // from class:
                                             // com.android.settings.applications.appinfo.AppButtonsPreferenceController.3
                    @Override // android.content.pm.PackageInstaller.SessionCallback
                    public final void onFinished(int i5, boolean z) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                new StringBuilder("[Request restore Success] PackageName : "),
                                AppButtonsPreferenceController.this.mPackageName,
                                AppButtonsPreferenceController.TAG);
                        AppButtonsPreferenceController.this.mButton4.setVisibility(0);
                        AppButtonsPreferenceController.this.mProgress.setVisibility(8);
                        AppButtonsPreferenceController appButtonsPreferenceController =
                                AppButtonsPreferenceController.this;
                        ApplicationsState applicationsState2 =
                                appButtonsPreferenceController.mState;
                        String str2 = appButtonsPreferenceController.mPackageName;
                        int i6 = appButtonsPreferenceController.mUserId;
                        applicationsState2.removePackage(i6, str2);
                        applicationsState2.addPackage(i6, str2);
                        AppButtonsPreferenceController.this.mIsRestoringState = false;
                    }

                    @Override // android.content.pm.PackageInstaller.SessionCallback
                    public final void onBadgingChanged(int i5) {}

                    @Override // android.content.pm.PackageInstaller.SessionCallback
                    public final void onCreated(int i5) {}

                    @Override // android.content.pm.PackageInstaller.SessionCallback
                    public final void onActiveChanged(int i5, boolean z) {}

                    @Override // android.content.pm.PackageInstaller.SessionCallback
                    public final void onProgressChanged(int i5, float f) {}
                };
        final int i5 = 2;
        this.mRestoreBroadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class:
                                // com.android.settings.applications.appinfo.AppButtonsPreferenceController.1
                    public final /* synthetic */ AppButtonsPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        ApplicationsState.AppEntry appEntry;
                        ApplicationInfo applicationInfo;
                        switch (i5) {
                            case 0:
                                boolean z = getResultCode() != 0;
                                ApplicationsState.AppEntry appEntry2 = this.this$0.mAppEntry;
                                if (appEntry2 != null && appEntry2.info != null) {
                                    Log.d(
                                            AppButtonsPreferenceController.TAG,
                                            "Got broadcast response: Restart status for "
                                                    + this.this$0.mAppEntry.info.packageName
                                                    + " "
                                                    + z);
                                }
                                this.this$0.updateForceStopButtonInner(z);
                                break;
                            case 1:
                                String schemeSpecificPart =
                                        intent.getData().getSchemeSpecificPart();
                                if (!this.this$0.mFinishing
                                        && (appEntry = this.this$0.mAppEntry) != null
                                        && (applicationInfo = appEntry.info) != null
                                        && applicationInfo.packageName.equals(schemeSpecificPart)) {
                                    this.this$0.mActivity.finishAndRemoveTask();
                                    break;
                                }
                                break;
                            case 2:
                                if (this.this$0.mPackageName.equals(
                                        intent.getStringExtra(
                                                "android.content.pm.extra.PACKAGE_NAME"))) {
                                    if (intent.getIntExtra(
                                                    "android.content.pm.extra.UNARCHIVE_STATUS",
                                                    Integer.MIN_VALUE)
                                            != 0) {
                                        Intent intent2 =
                                                (Intent)
                                                        intent.getParcelableExtra(
                                                                "android.intent.extra.INTENT");
                                        if (intent2 == null) {
                                            Preference$$ExternalSyntheticOutline0.m(
                                                    context,
                                                    R.string.restore_fail,
                                                    new Object[] {this.this$0.mAppEntry.label},
                                                    context,
                                                    0);
                                            break;
                                        } else {
                                            context.startActivityAsUser(
                                                    intent2, new UserHandle(this.this$0.mUserId));
                                            break;
                                        }
                                    } else {
                                        this.this$0.mButton4.setVisibility(8);
                                        this.this$0.mProgress.setVisibility(0);
                                        this.this$0.mIsRestoringState = true;
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.restore_start,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    }
                                }
                                break;
                            default:
                                if (this.this$0.mPackageName.equals(
                                        intent.getStringExtra(
                                                "android.content.pm.extra.PACKAGE_NAME"))) {
                                    if (intent.getIntExtra(
                                                    "android.content.pm.extra.STATUS",
                                                    Integer.MIN_VALUE)
                                            != 0) {
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.archive_fail,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    } else {
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.archive_success,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i6 = 3;
        this.mArchiveBroadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class:
                                // com.android.settings.applications.appinfo.AppButtonsPreferenceController.1
                    public final /* synthetic */ AppButtonsPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        ApplicationsState.AppEntry appEntry;
                        ApplicationInfo applicationInfo;
                        switch (i6) {
                            case 0:
                                boolean z = getResultCode() != 0;
                                ApplicationsState.AppEntry appEntry2 = this.this$0.mAppEntry;
                                if (appEntry2 != null && appEntry2.info != null) {
                                    Log.d(
                                            AppButtonsPreferenceController.TAG,
                                            "Got broadcast response: Restart status for "
                                                    + this.this$0.mAppEntry.info.packageName
                                                    + " "
                                                    + z);
                                }
                                this.this$0.updateForceStopButtonInner(z);
                                break;
                            case 1:
                                String schemeSpecificPart =
                                        intent.getData().getSchemeSpecificPart();
                                if (!this.this$0.mFinishing
                                        && (appEntry = this.this$0.mAppEntry) != null
                                        && (applicationInfo = appEntry.info) != null
                                        && applicationInfo.packageName.equals(schemeSpecificPart)) {
                                    this.this$0.mActivity.finishAndRemoveTask();
                                    break;
                                }
                                break;
                            case 2:
                                if (this.this$0.mPackageName.equals(
                                        intent.getStringExtra(
                                                "android.content.pm.extra.PACKAGE_NAME"))) {
                                    if (intent.getIntExtra(
                                                    "android.content.pm.extra.UNARCHIVE_STATUS",
                                                    Integer.MIN_VALUE)
                                            != 0) {
                                        Intent intent2 =
                                                (Intent)
                                                        intent.getParcelableExtra(
                                                                "android.intent.extra.INTENT");
                                        if (intent2 == null) {
                                            Preference$$ExternalSyntheticOutline0.m(
                                                    context,
                                                    R.string.restore_fail,
                                                    new Object[] {this.this$0.mAppEntry.label},
                                                    context,
                                                    0);
                                            break;
                                        } else {
                                            context.startActivityAsUser(
                                                    intent2, new UserHandle(this.this$0.mUserId));
                                            break;
                                        }
                                    } else {
                                        this.this$0.mButton4.setVisibility(8);
                                        this.this$0.mProgress.setVisibility(0);
                                        this.this$0.mIsRestoringState = true;
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.restore_start,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    }
                                }
                                break;
                            default:
                                if (this.this$0.mPackageName.equals(
                                        intent.getStringExtra(
                                                "android.content.pm.extra.PACKAGE_NAME"))) {
                                    if (intent.getIntExtra(
                                                    "android.content.pm.extra.STATUS",
                                                    Integer.MIN_VALUE)
                                            != 0) {
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.archive_fail,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    } else {
                                        Preference$$ExternalSyntheticOutline0.m(
                                                context,
                                                R.string.archive_success,
                                                new Object[] {this.this$0.mAppEntry.label},
                                                context,
                                                0);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        if (!(instrumentedPreferenceFragment
                instanceof ButtonActionDialogFragment.AppButtonsDialogListener)) {
            throw new IllegalArgumentException(
                    "Fragment should implement AppButtonsDialogListener");
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mApplicationFeatureProvider = featureFactoryImpl.getApplicationFeatureProvider();
        this.mState = applicationsState;
        this.mDpm = (DevicePolicyManager) settingsActivity.getSystemService("device_policy");
        this.mUserManager = (UserManager) settingsActivity.getSystemService("user");
        this.mPm = settingsActivity.getPackageManager();
        this.mOverlayManager =
                (OverlayManager) settingsActivity.getSystemService(OverlayManager.class);
        this.mPackageName = str;
        this.mActivity = settingsActivity;
        this.mFragment = instrumentedPreferenceFragment;
        this.mUserId = UserHandle.myUserId();
        this.mAppButtonRepository =
                new AppButtonRepository(settingsActivity.getApplicationContext());
        this.mPermissionControllerManager =
                (PermissionControllerManager)
                        settingsActivity.getSystemService(PermissionControllerManager.class);
        String string =
                !SemCscFeature.getInstance()
                                .getString(
                                        "CscFeature_Setting_ConfigAppAndIntentListForDisablingMenuOverride")
                                .isEmpty()
                        ? SemCscFeature.getInstance()
                                .getString(
                                        "CscFeature_Setting_ConfigAppAndIntentListForDisablingMenuOverride")
                        : ApnSettings.MVNO_NONE;
        try {
            if (!TextUtils.isEmpty(string)) {
                for (String str2 : string.split(";")) {
                    if (!TextUtils.isEmpty(str2)
                            && (split = str2.split("/")) != null
                            && split.length == 2) {
                        this.mDisableOverridedPackages.put(split[0], split[1]);
                    }
                }
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }
        this.mRequestUninstall = i;
        this.mRequestRemoveDeviceAdmin = i2;
        this.mAppLaunchIntent = this.mPm.getLaunchIntentForPackage(this.mPackageName);
        long longExtra =
                settingsActivity
                        .getIntent()
                        .getLongExtra("android.intent.action.AUTO_REVOKE_PERMISSIONS", 0L);
        this.mSessionId = longExtra;
        this.mAccessedFromAutoRevoke = longExtra != 0;
        if (str == null) {
            this.mFinishing = true;
            return;
        }
        this.mAppEntry = this.mState.getEntry(this.mUserId, str);
        this.mSession = this.mState.newSession(this, lifecycle);
        lifecycle.addObserver(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getPackageNameForMetric() {
        ApplicationInfo applicationInfo;
        ApplicationsState.AppEntry appEntry = this.mAppEntry;
        String str =
                (appEntry == null || (applicationInfo = appEntry.info) == null)
                        ? null
                        : applicationInfo.packageName;
        return str != null ? str : ApnSettings.MVNO_NONE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getUid() {
        if (this.mPackageInfo == null) {
            retrieveAppEntry();
        }
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo != null) {
            return packageInfo.applicationInfo.uid;
        }
        return -1;
    }

    private void handleDualAppArchiveResult(int i) {
        if (i == -1) {
            Context context = this.mContext;
            Preference$$ExternalSyntheticOutline0.m(
                    context,
                    R.string.archive_success,
                    new Object[] {this.mAppEntry.label},
                    context,
                    0);
        } else if (i == 4) {
            Context context2 = this.mContext;
            Preference$$ExternalSyntheticOutline0.m(
                    context2,
                    R.string.archive_fail,
                    new Object[] {this.mAppEntry.label},
                    context2,
                    0);
        }
    }

    private void initButtonPreference() {
        ActionButtonsPreference actionButtonsPreference =
                (ActionButtonsPreference) this.mScreen.findPreference(KEY_ACTION_BUTTONS);
        actionButtonsPreference.setButton1Text(R.string.launch_instant_app);
        actionButtonsPreference.setButton1Icon(R.drawable.ic_settings_open);
        actionButtonsPreference.setButton1OnClickListener(
                new AppButtonsPreferenceController$$ExternalSyntheticLambda1(this, 1));
        actionButtonsPreference.setButton2Text(R.string.uninstall_text);
        actionButtonsPreference.setButton2Icon(R.drawable.ic_settings_delete);
        actionButtonsPreference.setButton2OnClickListener(new ArchiveButtonListener(this, 3));
        actionButtonsPreference.setButton3Text(R.string.force_stop);
        actionButtonsPreference.setButton3Icon(R.drawable.ic_settings_force_stop);
        ArchiveButtonListener archiveButtonListener = new ArchiveButtonListener(this, 1);
        ActionButtonsPreference.ButtonInfo buttonInfo = actionButtonsPreference.mButton3Info;
        if (archiveButtonListener != buttonInfo.mListener) {
            buttonInfo.mListener = archiveButtonListener;
            actionButtonsPreference.notifyChanged();
        }
        ActionButtonsPreference.ButtonInfo buttonInfo2 = actionButtonsPreference.mButton3Info;
        if (buttonInfo2.mIsEnabled) {
            buttonInfo2.mIsEnabled = false;
            actionButtonsPreference.notifyChanged();
        }
        this.mButtonsPref = actionButtonsPreference;
    }

    private boolean isAppEligibleForHibernation() {
        int i = this.mHibernationEligibility;
        return (i == 1 || i == -1) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDisabledUntilUsed() {
        return this.mAppEntry.info.enabledSetting == 4;
    }

    private boolean isDisablingBtnBlocked(
            ApplicationsState.AppEntry appEntry, PackageInfo packageInfo) {
        boolean z = false;
        try {
            Bundle bundle =
                    this.mContext
                            .getPackageManager()
                            .getApplicationInfo(appEntry.info.packageName, 128)
                            .metaData;
            if (bundle != null && bundle.getBoolean("com.sec.android.app.blockdisabling", false)) {
                Log.i(TAG, "DisableBlocked by Meta : " + appEntry.info.packageName);
                z = true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Not found package : "), appEntry.info.packageName, TAG);
        }
        if ("com.evernote".equals(packageInfo.packageName)
                || "com.samsung.android.app.pinboard".equals(packageInfo.packageName)
                || "com.osp.app.signin".equals(packageInfo.packageName)) {
            return true;
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x00b1, code lost:

       if (r1.isPrivilegedApp() != false) goto L13;
    */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0095 A[Catch: NameNotFoundException -> 0x00b4, TryCatch #1 {NameNotFoundException -> 0x00b4, blocks: (B:3:0x000a, B:5:0x0016, B:9:0x0021, B:11:0x0035, B:13:0x003d, B:15:0x0053, B:17:0x0060, B:31:0x007b, B:34:0x0081, B:21:0x0092, B:23:0x0095, B:26:0x009f, B:28:0x00ad, B:37:0x0089), top: B:2:0x000a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isDisablingBtnUnblocked(
            com.android.settingslib.applications.ApplicationsState.AppEntry r10) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.applications.appinfo.AppButtonsPreferenceController.isDisablingBtnUnblocked(com.android.settingslib.applications.ApplicationsState$AppEntry):boolean");
    }

    private boolean isEnabledArchive(ApplicationInfo applicationInfo) {
        if (!Flags.archiving()) {
            Log.d(TAG, "Archiving flags is false");
            return false;
        }
        try {
            if (!this.mActivity.getPackageManager().isAppArchivable(applicationInfo.packageName)) {
                Log.d(TAG, "This app(" + applicationInfo.packageName + ") is not app archivable");
                return false;
            }
            int i = this.mUserId;
            if (i != 0) {
                RecyclerView$$ExternalSyntheticOutline0.m(
                        new StringBuilder("This user ("), this.mUserId, ") is not owner", TAG);
                return false;
            }
            if (AppUtils.isArchived(this.mContext, i, applicationInfo.packageName)) {
                AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder("This app ("),
                        applicationInfo.packageName,
                        ") is archived",
                        TAG);
                return true;
            }
            if (this.mAppButtonRepository == null) {
                this.mAppButtonRepository =
                        new AppButtonRepository(this.mActivity.getApplicationContext());
            }
            if (!this.mAppButtonRepository.isAllowUninstallOrArchive(
                    this.mActivity, applicationInfo)) {
                AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder("This app ("),
                        applicationInfo.packageName,
                        ") is allow uninstall or archive",
                        TAG);
                return false;
            }
            if (RestrictedLockUtilsInternal.checkIfUninstallBlocked(
                            this.mContext, this.mUserId, this.mPackageName)
                    != null) {
                AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder("this app ("),
                        applicationInfo.packageName,
                        ") is uninstall blocked by admin",
                        TAG);
                return false;
            }
            if (isAppEligibleForHibernation()) {
                AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder("this app ("),
                        applicationInfo.packageName,
                        ") is enable archive",
                        TAG);
                return true;
            }
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder("this app ("),
                    applicationInfo.packageName,
                    ") is eligible for hibernation",
                    TAG);
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder("This app("),
                    applicationInfo.packageName,
                    ") is not found",
                    TAG);
            return false;
        }
    }

    private boolean isInstantApp() {
        ApplicationsState.AppEntry appEntry = this.mAppEntry;
        return appEntry != null && AppUtils.isInstant(appEntry.info);
    }

    private boolean isKioskHomeBlocked(PackageInfo packageInfo) {
        String enterprisePolicyStringValue;
        return Utils.getEnterprisePolicyEnabled(
                                this.mContext,
                                "content://com.sec.knox.provider2/KioskMode",
                                "isKioskModeEnabled")
                        == 1
                && (enterprisePolicyStringValue =
                                Utils.getEnterprisePolicyStringValue(this.mContext))
                        != null
                && enterprisePolicyStringValue.equals(packageInfo.packageName);
    }

    private boolean isLaunchedInternal() {
        try {
            String launchedFromPackage =
                    ActivityManager.getService()
                            .getLaunchedFromPackage(this.mContext.getActivityToken());
            if (KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(launchedFromPackage)) {
                return true;
            }
            return "com.android.settings.intelligence".equals(launchedFromPackage);
        } catch (RemoteException unused) {
            Log.e(TAG, "Can not get the launched package from activity manager!");
            return true;
        }
    }

    private boolean isSettingsRunOnTop() {
        for (ActivityManager.RunningTaskInfo runningTaskInfo :
                ((ActivityManager)
                                this.mContext
                                        .getApplicationContext()
                                        .getSystemService(ActivityManager.class))
                        .getRunningTasks(2)) {
            String packageName = runningTaskInfo.baseActivity.getPackageName();
            Log.i(TAG, "topActivity : " + runningTaskInfo.topActivity.getClassName());
            Log.i(TAG, "taskPkgName : " + packageName);
            if (TextUtils.equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, packageName)
                    || TextUtils.equals("com.samsung.android.app.galaxyregistry", packageName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSystemModule() {
        ApplicationsState.AppEntry appEntry = this.mAppEntry;
        if (appEntry != null) {
            Context context = this.mContext;
            String str = appEntry.info.packageName;
            Intent intent = AppUtils.sBrowserIntent;
            if (ApplicationsState.getInstance((Application) context.getApplicationContext())
                            .mSystemModules
                            .containsKey(str)
                    || AppUtils.isMainlineModule(this.mPm, this.mAppEntry.info.packageName)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDialogClick$0() {
        forceStopPackage("com.att.iqi");
        this.mForceStopInProgress = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBottomButtonsLayout$3(View view) {
        launchApplication();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initButtonPreference$2(View view) {
        launchApplication();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshUi$1(int i) {
        this.mHibernationEligibility = i;
        updateArchiveButton();
    }

    private void launchApplication() {
        if (this.mAppLaunchIntent != null) {
            if (this.mAccessedFromAutoRevoke) {
                Log.i(
                        TAG,
                        "sessionId: "
                                + this.mSessionId
                                + " uninstalling "
                                + this.mPackageName
                                + " with uid "
                                + getUid()
                                + ", reached from auto revoke");
                SettingsStatsLog.write(getUid(), 6, this.mSessionId, this.mPackageName);
            }
            try {
                this.mContext.startActivityAsUser(
                        this.mAppLaunchIntent, new UserHandle(this.mUserId));
            } catch (ActivityNotFoundException e) {
                Log.w(TAG, "Could not launch intent for action: " + this.mAppLaunchIntent, e);
                updateOpenButton();
            }
            this.mMetricsFeatureProvider.action(this.mActivity, 1773, this.mPackageName);
        }
    }

    private void refreshAndFinishIfPossible(boolean z) {
        if (refreshUi()) {
            startListeningToPackageRemove();
        } else {
            setIntentAndFinish(z);
        }
    }

    private void setBottomButtonBar() {
        Log.d(TAG, "setBottomButtonBar");
        View findViewById = this.mActivity.findViewById(R.id.button_bar);
        if (findViewById == null) {
            Log.e(TAG, "setBottomButtonBar buttonBar null");
            return;
        }
        findViewById.setVisibility(0);
        View inflate =
                this.mActivity
                        .getLayoutInflater()
                        .inflate(R.layout.sec_bottom_bar_preference, (ViewGroup) null);
        ((LinearLayout) inflate.findViewById(R.id.bottom_bar))
                .setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        Button button = (Button) inflate.findViewById(R.id.button1);
        this.mButton1 = button;
        button.setVisibility(0);
        Button button2 = (Button) inflate.findViewById(R.id.button3);
        this.mButton2 = button2;
        button2.setVisibility(0);
        Button button3 = (Button) inflate.findViewById(R.id.button4);
        this.mButton3 = button3;
        button3.setVisibility(0);
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.button2_layout);
        this.mButton4Container = relativeLayout;
        relativeLayout.setVisibility(0);
        Button button4 = (Button) inflate.findViewById(R.id.button2);
        this.mButton4 = button4;
        button4.setVisibility(0);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.progress);
        this.mProgress = linearLayout;
        linearLayout.setVisibility(8);
        ((RelativeLayout) findViewById).addView(inflate);
    }

    private void setEnableButton(Button button, Boolean bool) {
        if (button != null) {
            button.setEnabled(bool.booleanValue());
            button.setAlpha(bool.booleanValue() ? 1.0f : 0.4f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGoogleCoreControl(String str, int i) {
        if (Rune.isChinaModel() && UserHandle.myUserId() == 0) {
            if (SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 0)
                    >= 32) {
                String[] strArr = {
                    "com.google.android.gms",
                    "com.google.android.configupdater",
                    "com.google.android.syncadapters.calendar"
                };
                for (int i2 = 0; i2 < 3; i2++) {
                    String str2 = strArr[i2];
                    if (str2.equals(str)) {
                        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                        "update control setting : ", str2, ", "),
                                i == 0,
                                TAG);
                        Settings.Global.putInt(
                                this.mContext.getContentResolver(),
                                "google_core_control",
                                i == 0 ? 1 : 0);
                        return;
                    }
                }
            }
        }
    }

    private void setIntentAndFinish(boolean z) {
        Intent intent = new Intent();
        intent.putExtra(APP_CHG, true);
        intent.putExtra(KEY_REMOVE_TASK_WHEN_FINISHING, z);
        this.mActivity.finishPreferencePanel(intent);
        this.mFinishing = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialogInner(int i) {
        ButtonActionDialogFragment buttonActionDialogFragment = new ButtonActionDialogFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("id", i);
        buttonActionDialogFragment.setArguments(bundle);
        buttonActionDialogFragment.setTargetFragment(this.mFragment, 0);
        buttonActionDialogFragment.show(this.mActivity.getSupportFragmentManager(), "dialog " + i);
    }

    private boolean signaturesMatch(String str, String str2) {
        if (str == null || str2 == null) {
            return false;
        }
        try {
            return this.mPm.checkSignatures(str, str2) >= 0;
        } catch (Exception unused) {
            return false;
        }
    }

    private void startListeningToPackageRemove() {
        if (this.mListeningToPackageRemove) {
            return;
        }
        this.mListeningToPackageRemove = true;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        this.mActivity.registerReceiver(this.mPackageRemovedReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopListeningToPackageRemove() {
        if (this.mListeningToPackageRemove) {
            this.mListeningToPackageRemove = false;
            this.mActivity.unregisterReceiver(this.mPackageRemovedReceiver);
        }
    }

    private void updateArchiveButton() {
        if (!this.mIsSupportedArchive || !isEnabledArchive(this.mAppEntry.info)) {
            this.mButton4Container.setVisibility(8);
            return;
        }
        boolean isArchived = AppUtils.isArchived(this.mContext, this.mAppEntry.info.packageName);
        if (this.mIsRestoringState && isArchived) {
            return;
        }
        this.mButton4Container.setVisibility(0);
        this.mButton4.setVisibility(0);
        this.mProgress.setVisibility(8);
        if (isArchived) {
            this.mButton4.setText(R.string.restore);
            this.mButton4.setCompoundDrawablesWithIntrinsicBounds(
                    (Drawable) null,
                    this.mActivity.getDrawable(R.drawable.sec_app_info_button_restore),
                    (Drawable) null,
                    (Drawable) null);
            this.mButton4.setOnClickListener(new ArchiveButtonListener(this, 2));
            this.mButton4.semSetButtonShapeEnabled(true);
            return;
        }
        this.mButton4.setText(R.string.archive);
        this.mButton4.setCompoundDrawablesWithIntrinsicBounds(
                (Drawable) null,
                this.mActivity.getDrawable(R.drawable.sec_app_info_button_archive),
                (Drawable) null,
                (Drawable) null);
        this.mButton4.setOnClickListener(new ArchiveButtonListener(this, 0));
        this.mButton4.semSetButtonShapeEnabled(true);
    }

    private void updateForceStopButtonsForException(
            ApplicationsState.AppEntry appEntry, PackageInfo packageInfo) {
        if (!SemCscFeature.getInstance()
                .getString("CscFeature_Setting_ConfigAppListForDisablingForceStopButton")
                .isEmpty()) {
            String string =
                    SemCscFeature.getInstance()
                            .getString(
                                    "CscFeature_Setting_ConfigAppListForDisablingForceStopButton");
            Log.d(TAG, "blockForceStopAppList : " + string);
            String[] split = string.split(",");
            int length = split.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (split[i].trim().equals(appEntry.info.packageName)) {
                    setEnableButton(this.mButton3, Boolean.FALSE);
                    break;
                }
                i++;
            }
        }
        if ("com.wssyncmldm".equals(appEntry.info.packageName)
                || "com.ws.dm".equals(appEntry.info.packageName)
                || "com.sec.downloadablekeystore".equals(appEntry.info.packageName)
                || "com.sec.android.fotaclient".equals(appEntry.info.packageName)) {
            setEnableButton(this.mButton3, Boolean.FALSE);
            return;
        }
        if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_EnableItsOn")
                && ("com.itsoninc.android.itsonclient".equals(packageInfo.packageName)
                        || "com.itsoninc.android.itsonservice".equals(packageInfo.packageName)
                        || "com.itsoninc.android.uid".equals(packageInfo.packageName))) {
            setEnableButton(this.mButton3, Boolean.FALSE);
            return;
        }
        if ("com.samsung.android.bbc.bbcagent".equals(packageInfo.packageName)) {
            setEnableButton(this.mButton3, Boolean.FALSE);
            return;
        }
        if ("com.samsung.android.themecenter".equals(packageInfo.packageName)) {
            setEnableButton(this.mButton3, Boolean.FALSE);
            return;
        }
        if ("com.samsung.android.localeoverlaymanager".equals(packageInfo.packageName)) {
            setEnableButton(this.mButton3, Boolean.FALSE);
        } else if (isKioskHomeBlocked(packageInfo)) {
            setEnableButton(this.mButton3, Boolean.FALSE);
        } else if ("com.samsung.android.da.daagent".equals(packageInfo.packageName)) {
            setEnableButton(this.mButton3, Boolean.FALSE);
        }
    }

    public void checkAdminRestrictionOnButtons(PackageInfo packageInfo) {
        Signature[] signatureArr;
        try {
            if (Utils.getEnterprisePolicyEnabled(
                            this.mContext,
                            "content://com.sec.knox.provider/RestrictionPolicy3",
                            "isStopSystemAppAllowed")
                    == 0) {
                PackageInfo packageInfo2 =
                        this.mPm.getPackageInfo(RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME, 64);
                if (packageInfo == null
                        || (signatureArr = packageInfo.signatures) == null
                        || !packageInfo2.signatures[0].equals(signatureArr[0])) {
                    return;
                }
                Log.i(TAG, "Buttoin1,2 Disabled by AdminRestriction : " + packageInfo.packageName);
                Button button = this.mButton2;
                Boolean bool = Boolean.FALSE;
                setEnableButton(button, bool);
                setEnableButton(this.mButton3, bool);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "Unable to get package info", e);
        }
    }

    public void forceStopPackage(String str) {
        MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeatureProvider;
        SettingsActivity settingsActivity = this.mActivity;
        metricsFeatureProvider.getClass();
        metricsFeatureProvider.action(
                MetricsFeatureProvider.getAttribution(settingsActivity),
                807,
                this.mFragment.getMetricsCategory(),
                0,
                str);
        ActivityManager activityManager =
                (ActivityManager) this.mActivity.getSystemService("activity");
        Log.d(TAG, "Stopping package " + str);
        if (android.app.Flags.appRestrictionsApi()) {
            activityManager.noteAppRestrictionEnabled(
                    str, this.mAppEntry.info.uid, 60, true, 4, "settings", 1, 0L);
        }
        activityManager.forceStopPackage(str);
        int userId = UserHandle.getUserId(this.mAppEntry.info.uid);
        ApplicationsState applicationsState = this.mState;
        applicationsState.removePackage(userId, str);
        applicationsState.addPackage(userId, str);
        ApplicationsState.AppEntry entry = this.mState.getEntry(userId, str);
        if (entry != null) {
            this.mAppEntry = entry;
        }
        updateForceStopButton();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mFinishing || isInstantApp() || isSystemModule()) ? 4 : 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_ACTION_BUTTONS;
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

    public void handleActivityResult(int i, int i2, Intent intent) {
        if (i == this.mRequestUninstall) {
            refreshAndFinishIfPossible(true);
        } else if (i == this.mRequestRemoveDeviceAdmin) {
            refreshAndFinishIfPossible(false);
        } else if (i == REQUEST_DUAL_APP_ARCHIVE) {
            handleDualAppArchiveResult(i2);
        }
    }

    public void handleDialogClick(int i) {
        if (i != 0) {
            if (i != 2) {
                return;
            }
            if (!TextUtils.equals(this.mAppEntry.info.packageName, "com.att.iqi")) {
                forceStopPackage(this.mAppEntry.info.packageName);
                return;
            }
            if (this.mForceStopInProgress) {
                Toast.makeText(this.mContext, R.string.force_stop_in_progress, 0).show();
                return;
            }
            IQIManager iQIManager = IQIManager.getInstance();
            if (iQIManager != null) {
                iQIManager.forceStopService(
                        this.mContext,
                        new Runnable() { // from class:
                                         // com.android.settings.applications.appinfo.AppButtonsPreferenceController$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppButtonsPreferenceController.this.lambda$handleDialogClick$0();
                            }
                        });
                this.mForceStopInProgress = true;
                return;
            }
            return;
        }
        this.mMetricsFeatureProvider.action(this.mActivity, 874, getPackageNameForMetric());
        if (!TextUtils.equals(this.mAppEntry.info.packageName, "com.att.iqi")) {
            setGoogleCoreControl(this.mAppEntry.info.packageName, 3);
            AsyncTask.execute(
                    new DisableChangerRunnable(this.mPm, this.mAppEntry.info.packageName, 3));
        } else {
            if (this.mDisableInProgress) {
                Toast.makeText(this.mContext, R.string.disable_app_in_progress, 0).show();
                return;
            }
            IQIManager iQIManager2 = IQIManager.getInstance();
            if (iQIManager2 != null) {
                iQIManager2.disableService();
                this.mDisableInProgress = true;
            }
        }
    }

    public boolean handleDisableable() {
        boolean z;
        String str = this.mAppEntry.info.packageName;
        Boolean bool = Boolean.FALSE;
        try {
            bool = Boolean.valueOf(this.mPm.getApplicationInfo(str, 0).enabled);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        Log.i(TAG, str + " isEnabled=" + bool);
        if (!this.mHomePackages.contains(this.mAppEntry.info.packageName)
                && (!isSystemPackage(this.mActivity.getResources(), this.mPm, this.mPackageInfo)
                        || isDisablingBtnUnblocked(this.mAppEntry))) {
            boolean booleanValue = bool.booleanValue();
            z = true;
            if (!booleanValue || isDisabledUntilUsed()) {
                this.mButton2.setText(R.string.enable_text);
                this.mButton2.setCompoundDrawablesWithIntrinsicBounds(
                        (Drawable) null,
                        this.mActivity.getDrawable(R.drawable.sec_app_info_button_enable),
                        (Drawable) null,
                        (Drawable) null);
                this.mDisableInProgress = false;
                Log.i(TAG, "handleDisableable enable case 1, disableable : true");
            } else {
                this.mButton2.setText(R.string.disable_text);
                this.mButton2.setCompoundDrawablesWithIntrinsicBounds(
                        (Drawable) null,
                        this.mActivity.getDrawable(R.drawable.sec_app_info_button_disable),
                        (Drawable) null,
                        (Drawable) null);
                if (isDisablingBtnBlocked(this.mAppEntry, this.mPackageInfo)) {
                    Log.i(TAG, "handleDisableable disable case 2, disableable : false");
                } else {
                    z =
                            true
                                    ^ ((ArraySet)
                                                    ((ApplicationFeatureProviderImpl)
                                                                    this
                                                                            .mApplicationFeatureProvider)
                                                            .getKeepEnabledPackages())
                                            .contains(this.mAppEntry.info.packageName);
                    AbsAdapter$$ExternalSyntheticOutline0.m(
                            "handleDisableable disable case 3, disableable : ", TAG, z);
                }
            }
            this.mDisableInProgress = false;
            return z;
        }
        this.mButton2.setText(R.string.disable_text);
        this.mButton2.setCompoundDrawablesWithIntrinsicBounds(
                (Drawable) null,
                this.mActivity.getDrawable(R.drawable.sec_app_info_button_disable),
                (Drawable) null,
                (Drawable) null);
        Log.i(TAG, "handleDisableable disable case 1");
        z = false;
        this.mDisableInProgress = false;
        return z;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void initBottomButtonsLayout() {
        if (!isAvailable() || this.mFinishing) {
            return;
        }
        Log.i(TAG, "initBottomButtonsLayout");
        setBottomButtonBar();
        this.mButton1.setText(R.string.launch_instant_app);
        this.mButton1.setCompoundDrawablesWithIntrinsicBounds(
                (Drawable) null,
                this.mActivity.getDrawable(R.drawable.sec_app_info_button_open),
                (Drawable) null,
                (Drawable) null);
        this.mButton1.setOnClickListener(
                new AppButtonsPreferenceController$$ExternalSyntheticLambda1(this, 0));
        this.mButton1.semSetButtonShapeEnabled(true);
        this.mButton2.setText(R.string.uninstall_text);
        this.mButton2.setCompoundDrawablesWithIntrinsicBounds(
                (Drawable) null,
                this.mActivity.getDrawable(R.drawable.sec_app_info_button_uninstall),
                (Drawable) null,
                (Drawable) null);
        this.mButton2.setOnClickListener(new ArchiveButtonListener(this, 3));
        this.mButton2.semSetButtonShapeEnabled(true);
        this.mButton3.setText(R.string.force_stop);
        this.mButton3.setCompoundDrawablesWithIntrinsicBounds(
                (Drawable) null,
                this.mActivity.getDrawable(R.drawable.sec_app_info_button_force_stop),
                (Drawable) null,
                (Drawable) null);
        this.mButton3.setOnClickListener(new ArchiveButtonListener(this, 1));
        this.mButton3.semSetButtonShapeEnabled(true);
        setEnableButton(this.mButton3, Boolean.FALSE);
        updateArchiveButton();
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

    public boolean isSystemPackage(
            Resources resources, PackageManager packageManager, PackageInfo packageInfo) {
        return com.android.settingslib.Utils.isSystemPackage(
                resources, packageManager, packageInfo);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        if (this.mPermissionControllerManager != null) {
            this.mPermissionControllerManager = null;
        }
        stopListeningToPackageRemove();
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageListChanged() {
        if (isAvailable()) {
            refreshUi();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mActivity.unregisterReceiver(this.mRestoreBroadcastReceiver);
        this.mActivity.unregisterReceiver(this.mArchiveBroadcastReceiver);
        this.mContext
                .getPackageManager()
                .getPackageInstaller()
                .unregisterSessionCallback(this.mSessionCallback);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (isAvailable()) {
            this.mAppsControlDisallowedBySystem =
                    RestrictedLockUtilsInternal.hasBaseUserRestriction(
                            this.mActivity, this.mUserId, "no_control_apps");
            this.mAppsControlDisallowedAdmin =
                    RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                            this.mActivity, this.mUserId, "no_control_apps");
            if (!refreshUi()) {
                setIntentAndFinish(false);
            }
        }
        this.mActivity.registerReceiver(
                this.mRestoreBroadcastReceiver,
                new IntentFilter("com.android.settings.unarchive.action"),
                2);
        this.mActivity.registerReceiver(
                this.mArchiveBroadcastReceiver,
                new IntentFilter("com.android.settings.archive.action"),
                2);
        this.mContext
                .getPackageManager()
                .getPackageInstaller()
                .registerSessionCallback(this.mSessionCallback);
    }

    public boolean refreshUi() {
        Log.i(TAG, "refreshUi()");
        if (this.mPackageName == null) {
            return false;
        }
        retrieveAppEntry();
        if (this.mAppEntry == null || this.mPackageInfo == null) {
            return false;
        }
        Log.i(TAG, "AppButton refreshUI for Pkg : " + this.mPackageInfo.packageName);
        ArrayList arrayList = new ArrayList();
        this.mPm.getHomeActivities(arrayList);
        this.mHomePackages.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo) it.next();
            String str = resolveInfo.activityInfo.packageName;
            this.mHomePackages.add(str);
            Bundle bundle = resolveInfo.activityInfo.metaData;
            if (bundle != null) {
                String string = bundle.getString("android.app.home.alternate");
                if (signaturesMatch(string, str)) {
                    this.mHomePackages.add(string);
                }
            }
        }
        this.mPermissionControllerManager.getHibernationEligibility(
                this.mPackageName,
                this.mContext.getMainExecutor(),
                new IntConsumer() { // from class:
                                    // com.android.settings.applications.appinfo.AppButtonsPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                        AppButtonsPreferenceController.this.lambda$refreshUi$1(i);
                    }
                });
        updateOpenButton();
        updateUninstallButton();
        updateForceStopButton();
        updateArchiveButton();
        checkAdminRestrictionOnButtons(this.mPackageInfo);
        return true;
    }

    public void retrieveAppEntry() {
        ApplicationsState.AppEntry entry = this.mState.getEntry(this.mUserId, this.mPackageName);
        this.mAppEntry = entry;
        if (entry == null) {
            this.mPackageInfo = null;
            return;
        }
        try {
            this.mPackageInfo =
                    this.mPm.getPackageInfoAsUser(
                            entry.info.packageName,
                            PackageManager.PackageInfoFlags.of(4299166272L),
                            this.mUserId);
            this.mPackageName = this.mAppEntry.info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Exception when retrieving package:" + this.mAppEntry.info.packageName, e);
            this.mPackageInfo = null;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setSupportedArchive(boolean z) {
        this.mIsSupportedArchive = z;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void uninstallPkg(String str, boolean z) {
        stopListeningToPackageRemove();
        Intent intent =
                new Intent("android.intent.action.UNINSTALL_PACKAGE", Uri.parse("package:" + str));
        intent.putExtra("android.intent.extra.UNINSTALL_ALL_USERS", z);
        this.mMetricsFeatureProvider.action(this.mActivity, 872, new Pair[0]);
        this.mFragment.startActivityForResult(intent, this.mRequestUninstall);
    }

    public void updateForceStopButton() {
        if (this.mButton3 == null) {
            return;
        }
        if (this.mDpm.packageHasActiveAdmins(this.mPackageInfo.packageName)) {
            Log.w(TAG, "User can't force stop device admin");
            updateForceStopButtonInner(false);
        } else {
            ApplicationsState.AppEntry appEntry = this.mAppEntry;
            if ((appEntry.info.flags & 2097152) == 0) {
                Log.w(TAG, "App is not explicitly stopped");
                updateForceStopButtonInner(true);
            } else if (AppUtils.isAppInstalled(appEntry)) {
                Intent intent =
                        new Intent(
                                "android.intent.action.QUERY_PACKAGE_RESTART",
                                Uri.fromParts("package", this.mAppEntry.info.packageName, null));
                intent.setPackage(RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME);
                intent.putExtra(
                        "android.intent.extra.PACKAGES",
                        new String[] {this.mAppEntry.info.packageName});
                intent.putExtra("android.intent.extra.UID", this.mAppEntry.info.uid);
                intent.putExtra(
                        "android.intent.extra.user_handle",
                        UserHandle.getUserId(this.mAppEntry.info.uid));
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Sending broadcast to query restart status for "),
                        this.mAppEntry.info.packageName,
                        TAG);
                this.mActivity.sendOrderedBroadcastAsUser(
                        intent,
                        UserHandle.CURRENT,
                        "android.permission.HANDLE_QUERY_PACKAGE_RESTART",
                        this.mCheckKillProcessesReceiver,
                        null,
                        0,
                        null,
                        null);
            } else {
                Log.w(TAG, "Archive app is not supported force stop");
                updateForceStopButtonInner(false);
            }
        }
        updateForceStopButtonsForException(this.mAppEntry, this.mPackageInfo);
    }

    public void updateForceStopButtonInner(boolean z) {
        if (this.mAppsControlDisallowedBySystem) {
            setEnableButton(this.mButton3, Boolean.FALSE);
        } else {
            setEnableButton(this.mButton3, Boolean.valueOf(z));
        }
    }

    public void updateOpenButton() {
        this.mAppLaunchIntent = this.mPm.getLaunchIntentForPackage(this.mPackageName);
        boolean z =
                SemDualAppManager.isDualAppId(this.mUserId)
                        && !SemDualAppManager.isInstalledWhitelistedPackage(this.mPackageName);
        Button button = this.mButton1;
        if (button != null) {
            if (this.mAppLaunchIntent == null || z) {
                button.setVisibility(8);
                return;
            }
            if (this.mContext.getResources().getConfiguration().semIsPopOver()
                    && !isLaunchedInternal()) {
                this.mButton1.setVisibility(8);
                return;
            }
            if (!isSettingsRunOnTop()) {
                Log.i(TAG, "launched on other process");
                this.mButton1.setVisibility(8);
            } else if (this.mPackageName.equals(this.mContext.getPackageName())) {
                this.mButton1.setVisibility(8);
            } else {
                this.mButton1.setVisibility(0);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x01d8, code lost:

       if (r10.mHomePackages.size() != 1) goto L88;
    */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0159, code lost:

       if (com.android.settings.Utils.isProfileOrDeviceOwner(r10.mDpm, r10.mPackageInfo.packageName, r10.mUserId) != false) goto L55;
    */
    /* JADX WARN: Removed duplicated region for block: B:100:0x00f7 A[EDGE_INSN: B:100:0x00f7->B:37:0x00f7 BREAK  A[LOOP:1: B:30:0x00d3->B:98:0x00f4], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x014d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateUninstallButton() {
        /*
            Method dump skipped, instructions count: 584
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.applications.appinfo.AppButtonsPreferenceController.updateUninstallButton():void");
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onAllSizesComputed() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onLauncherInfoChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onLoadEntriesCompleted() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageIconChanged() {}

    public /* bridge */ /* synthetic */ void onDisabledAppCheckCompleted(Boolean bool) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageSizeChanged(String str) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onRebuildComplete(ArrayList<ApplicationsState.AppEntry> arrayList) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onRunningStateChanged(boolean z) {}
}
