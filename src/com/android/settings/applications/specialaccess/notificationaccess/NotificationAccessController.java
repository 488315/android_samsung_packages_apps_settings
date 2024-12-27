package com.android.settings.applications.specialaccess.notificationaccess;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationAccessController extends BasePreferenceController {
    public NotificationAccessController(Context context, String str) {
        super(context, str);
    }

    public static void deleteRules(final Context context, final ComponentName componentName) {
        AsyncTask.execute(
                new Runnable() { // from class:
                                 // com.android.settings.applications.specialaccess.notificationaccess.NotificationAccessController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationAccessController.lambda$deleteRules$0(context, componentName);
                    }
                });
    }

    public static boolean hasAccess(Context context, ComponentName componentName) {
        return ((NotificationManager) context.getSystemService(NotificationManager.class))
                .isNotificationListenerAccessGranted(componentName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteRules$0(
            Context context, ComponentName componentName) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
        if (notificationManager.isNotificationPolicyAccessGrantedForPackage(
                componentName.getPackageName())) {
            return;
        }
        notificationManager.removeAutomaticZenRules(componentName.getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setAccess$1(
            Context context, ComponentName componentName, boolean z) {
        ((NotificationManager) context.getSystemService(NotificationManager.class))
                .setNotificationListenerAccessGranted(componentName, z);
    }

    public static void logSpecialPermissionChange(boolean z, String str, Context context) {
        int i = z ? 776 : 777;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(context, i, str);
    }

    public static void setAccess(
            final Context context, final ComponentName componentName, final boolean z) {
        logSpecialPermissionChange(z, componentName.getPackageName(), context);
        HashMap hashMap = new HashMap();
        hashMap.put("pkgname", componentName.getPackageName());
        hashMap.put("newValue", z ? "On" : "Off");
        SALogging.insertSALog(Integer.toString(179), Integer.toString(3917), hashMap, 0);
        AsyncTask.execute(
                new Runnable() { // from class:
                                 // com.android.settings.applications.specialaccess.notificationaccess.NotificationAccessController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationAccessController.lambda$setAccess$1(context, componentName, z);
                    }
                });
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
