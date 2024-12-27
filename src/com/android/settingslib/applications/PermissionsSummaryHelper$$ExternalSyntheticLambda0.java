package com.android.settingslib.applications;

import android.permission.PermissionControllerManager;
import android.permission.RuntimePermissionPresentationInfo;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PermissionsSummaryHelper$$ExternalSyntheticLambda0
        implements PermissionControllerManager.OnGetAppPermissionResultCallback {
    public final /* synthetic */ PermissionsSummaryHelper$PermissionsResultCallback f$0;

    public final void onGetAppPermissions(List list) {
        PermissionsSummaryHelper$PermissionsResultCallback
                permissionsSummaryHelper$PermissionsResultCallback = this.f$0;
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            RuntimePermissionPresentationInfo runtimePermissionPresentationInfo =
                    (RuntimePermissionPresentationInfo) it.next();
            i++;
            if (runtimePermissionPresentationInfo.isGranted()) {
                if (runtimePermissionPresentationInfo.isStandard()) {
                    arrayList.add(runtimePermissionPresentationInfo.getLabel());
                } else {
                    i2++;
                }
            }
        }
        Collator collator = Collator.getInstance();
        collator.setStrength(0);
        arrayList.sort(collator);
        permissionsSummaryHelper$PermissionsResultCallback.onPermissionSummaryResult(
                i, arrayList, i2);
    }
}
