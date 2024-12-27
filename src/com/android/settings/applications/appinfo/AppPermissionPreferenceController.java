package com.android.settings.applications.appinfo;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.icu.text.ListFormatter;
import android.os.Handler;
import android.permission.PermissionControllerManager;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.PermissionsSummaryHelper$$ExternalSyntheticLambda0;
import com.android.settingslib.applications.PermissionsSummaryHelper$PermissionsResultCallback;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppPermissionPreferenceController extends AppInfoPreferenceControllerBase
        implements LifecycleObserver, OnStart, OnStop {
    private static final String EXTRA_HIDE_INFO_BUTTON = "hideInfoButton";
    private static final long INVALID_SESSION_ID = 0;
    private static final String TAG = "PermissionPrefControl";
    private final PackageManager.OnPermissionsChangedListener mOnPermissionsChangedListener;
    private final PackageManager mPackageManager;
    private String mPackageName;
    final PermissionsSummaryHelper$PermissionsResultCallback mPermissionCallback;
    private int mUserId;

    public AppPermissionPreferenceController(Context context, String str) {
        super(context, str);
        this.mPermissionCallback =
                new PermissionsSummaryHelper$PermissionsResultCallback() { // from class:
                                                                           // com.android.settings.applications.appinfo.AppPermissionPreferenceController.1
                    @Override // com.android.settingslib.applications.PermissionsSummaryHelper$PermissionsResultCallback
                    public final void onPermissionSummaryResult(int i, List list, int i2) {
                        String string;
                        AppPermissionPreferenceController appPermissionPreferenceController =
                                AppPermissionPreferenceController.this;
                        Resources resources =
                                ((AbstractPreferenceController) appPermissionPreferenceController)
                                        .mContext.getResources();
                        if (i == 0) {
                            string =
                                    resources.getString(
                                            R.string
                                                    .runtime_permissions_summary_no_permissions_requested);
                        } else {
                            ArrayList arrayList = new ArrayList(list);
                            if (i2 > 0) {
                                arrayList.add(
                                        ((AbstractPreferenceController)
                                                        appPermissionPreferenceController)
                                                .mContext
                                                .getResources()
                                                .getQuantityString(
                                                        R.plurals
                                                                .sec_runtime_permissions_additional_count,
                                                        i2,
                                                        Integer.valueOf(i2)));
                            }
                            string =
                                    arrayList.size() == 0
                                            ? resources.getString(
                                                    R.string
                                                            .runtime_permissions_summary_no_permissions_granted)
                                            : ListFormatter.getInstance().format(arrayList);
                        }
                        appPermissionPreferenceController.mPreference.setSummary(string);
                    }
                };
        this.mOnPermissionsChangedListener =
                new PackageManager
                        .OnPermissionsChangedListener() { // from class:
                                                          // com.android.settings.applications.appinfo.AppPermissionPreferenceController$$ExternalSyntheticLambda0
                    public final void onPermissionsChanged(int i) {
                        AppPermissionPreferenceController.this.lambda$new$0(i);
                    }
                };
        this.mPackageManager = context.getPackageManager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i) {
        updateState(this.mPreference);
    }

    private void startManagePermissionsActivity() {
        Intent intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
        intent.setPackage(this.mPackageManager.getPermissionControllerPackageName());
        intent.putExtra(
                "android.intent.extra.PACKAGE_NAME", this.mParent.mAppEntry.info.packageName);
        intent.putExtra(EXTRA_HIDE_INFO_BUTTON, true);
        FragmentActivity activity = this.mParent.getActivity();
        Intent intent2 = activity != null ? activity.getIntent() : null;
        if (intent2 != null) {
            String action = intent2.getAction();
            long longExtra =
                    intent2.getLongExtra("android.intent.action.AUTO_REVOKE_PERMISSIONS", 0L);
            if ((action != null && action.equals("android.intent.action.AUTO_REVOKE_PERMISSIONS"))
                    || longExtra != 0) {
                while (longExtra == 0) {
                    longExtra = new Random().nextLong();
                }
                intent.putExtra("android.intent.action.AUTO_REVOKE_PERMISSIONS", longExtra);
            }
        }
        if (activity != null) {
            try {
                activity.startActivityForResult(intent, 1);
            } catch (ActivityNotFoundException unused) {
                Log.w(TAG, "No app can handle android.intent.action.MANAGE_APP_PERMISSIONS");
            }
        }
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        startManagePermissionsActivity();
        return true;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mPackageManager.addOnPermissionsChangeListener(this.mOnPermissionsChangedListener);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mPackageManager.removeOnPermissionsChangeListener(this.mOnPermissionsChangedListener);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setPackageName(String str) {
        this.mPackageName = str;
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        ((PermissionControllerManager)
                        this.mContext.getSystemService(PermissionControllerManager.class))
                .getAppPermissions(
                        this.mPackageName,
                        new PermissionsSummaryHelper$$ExternalSyntheticLambda0(
                                this.mPermissionCallback),
                        (Handler) null);
        if (preference instanceof SecPreference) {
            SecPreference secPreference = (SecPreference) preference;
            secPreference.getClass();
            SecPreferenceUtils.applySummaryColor(secPreference, true);
        }
        this.mPreference.setEnabled(
                !AppUtils.isArchived(this.mContext, this.mUserId, this.mPackageName));
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
