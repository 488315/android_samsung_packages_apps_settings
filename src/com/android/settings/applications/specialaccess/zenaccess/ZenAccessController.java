package com.android.settings.applications.specialaccess.zenaccess;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.Flags;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenAccessController extends BasePreferenceController {
    private static final String TAG = "ZenAccessController";

    public ZenAccessController(Context context, String str) {
        super(context, str);
    }

    public static void deleteRules(Context context, String str) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
        if (Flags.modesApi()) {
            notificationManager.removeAutomaticZenRules(str, true);
        } else {
            notificationManager.removeAutomaticZenRules(str);
        }
    }

    public static Set<String> getAutoApprovedPackages(Context context) {
        ArraySet arraySet = new ArraySet();
        arraySet.addAll(
                ((NotificationManager) context.getSystemService(NotificationManager.class))
                        .getEnabledNotificationListenerPackages());
        return arraySet;
    }

    public static Set<String> getPackagesRequestingNotificationPolicyAccess() {
        return getPackagesWithPermissions(
                new String[] {"android.permission.ACCESS_NOTIFICATION_POLICY"});
    }

    public static Set<String> getPackagesWithManageNotifications() {
        return getPackagesWithPermissions(new String[] {"android.permission.MANAGE_NOTIFICATIONS"});
    }

    public static Set<String> getPackagesWithPermissions(String[] strArr) {
        ArraySet arraySet = new ArraySet();
        try {
            List<PackageInfo> list =
                    AppGlobals.getPackageManager()
                            .getPackagesHoldingPermissions(
                                    strArr, 0L, ActivityManager.getCurrentUser())
                            .getList();
            if (list != null) {
                for (PackageInfo packageInfo : list) {
                    if (packageInfo.applicationInfo.enabled) {
                        arraySet.add(packageInfo.packageName);
                    }
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Cannot reach packagemanager", e);
        }
        return arraySet;
    }

    public static boolean hasAccess(Context context, String str) {
        return ((NotificationManager) context.getSystemService(NotificationManager.class))
                .isNotificationPolicyAccessGrantedForPackage(str);
    }

    public static void logSpecialPermissionChange(boolean z, String str, Context context) {
        int i = z ? 768 : 769;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(context, i, str);
    }

    public static void setAccess(Context context, String str, boolean z) {
        logSpecialPermissionChange(z, str, context);
        HashMap hashMap = new HashMap();
        hashMap.put("pkgname", str);
        hashMap.put("newValue", z ? "On" : "Off");
        SALogging.insertSALog(Integer.toString(180), Integer.toString(3914), hashMap, 0);
        ((NotificationManager) context.getSystemService(NotificationManager.class))
                .setNotificationPolicyAccessGranted(str, z);
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
