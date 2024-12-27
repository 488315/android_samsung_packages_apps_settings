package com.android.settings.spa.app.appinfo;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.Utils;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.jvm.internal.Intrinsics;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppButtonRepository {
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final PackageManager packageManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HomePackages {
        public final ComponentName currentDefaultHome;
        public final Set homePackages;

        public HomePackages(Set set, ComponentName componentName) {
            this.homePackages = set;
            this.currentDefaultHome = componentName;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof HomePackages)) {
                return false;
            }
            HomePackages homePackages = (HomePackages) obj;
            return Intrinsics.areEqual(this.homePackages, homePackages.homePackages)
                    && Intrinsics.areEqual(
                            this.currentDefaultHome, homePackages.currentDefaultHome);
        }

        public final int hashCode() {
            int hashCode = this.homePackages.hashCode() * 31;
            ComponentName componentName = this.currentDefaultHome;
            return hashCode + (componentName == null ? 0 : componentName.hashCode());
        }

        public final String toString() {
            return "HomePackages(homePackages="
                    + this.homePackages
                    + ", currentDefaultHome="
                    + this.currentDefaultHome
                    + ")";
        }
    }

    public AppButtonRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.packageManager = context.getPackageManager();
        this.devicePolicyManager = ContextsKt.getDevicePolicyManager(context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x005e, code lost:

       if (r7.packageManager.checkSignatures(r4, r3) >= 0) goto L20;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.settings.spa.app.appinfo.AppButtonRepository.HomePackages
            getHomePackageInfo() {
        /*
            r7 = this;
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            r0.<init>()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            android.content.pm.PackageManager r2 = r7.packageManager
            android.content.ComponentName r2 = r2.getHomeActivities(r1)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.Iterator r1 = r1.iterator()
        L19:
            boolean r4 = r1.hasNext()
            if (r4 == 0) goto L2d
            java.lang.Object r4 = r1.next()
            android.content.pm.ResolveInfo r4 = (android.content.pm.ResolveInfo) r4
            android.content.pm.ActivityInfo r4 = r4.activityInfo
            if (r4 == 0) goto L19
            r3.add(r4)
            goto L19
        L2d:
            java.util.Iterator r1 = r3.iterator()
        L31:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L68
            java.lang.Object r3 = r1.next()
            android.content.pm.ActivityInfo r3 = (android.content.pm.ActivityInfo) r3
            java.lang.String r4 = r3.packageName
            java.lang.String r5 = "packageName"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r5)
            r0.add(r4)
            android.os.Bundle r4 = r3.metaData
            if (r4 == 0) goto L31
            java.lang.String r6 = "android.app.home.alternate"
            java.lang.String r4 = r4.getString(r6)
            if (r4 == 0) goto L31
            java.lang.String r3 = r3.packageName
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
            android.content.pm.PackageManager r5 = r7.packageManager     // Catch: java.lang.Exception -> L61
            int r3 = r5.checkSignatures(r4, r3)     // Catch: java.lang.Exception -> L61
            if (r3 < 0) goto L61
            goto L62
        L61:
            r4 = 0
        L62:
            if (r4 == 0) goto L31
            r0.add(r4)
            goto L31
        L68:
            com.android.settings.spa.app.appinfo.AppButtonRepository$HomePackages r7 = new com.android.settings.spa.app.appinfo.AppButtonRepository$HomePackages
            r7.<init>(r0, r2)
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.appinfo.AppButtonRepository.getHomePackageInfo():com.android.settings.spa.app.appinfo.AppButtonRepository$HomePackages");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0072 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isAllowUninstallOrArchive(
            android.content.Context r5, android.content.pm.ApplicationInfo r6) {
        /*
            r4 = this;
            java.lang.String r0 = "context"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.lang.String r0 = "app"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.lang.Class<android.content.om.OverlayManager> r0 = android.content.om.OverlayManager.class
            java.lang.Object r0 = r5.getSystemService(r0)
            if (r0 == 0) goto L92
            android.content.om.OverlayManager r0 = (android.content.om.OverlayManager) r0
            r1 = 8388608(0x800000, float:1.17549435E-38)
            boolean r1 = com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt.hasFlag(r1, r6)
            r2 = 0
            if (r1 != 0) goto L22
            boolean r1 = r6.isArchived
            if (r1 != 0) goto L22
            return r2
        L22:
            android.app.admin.DevicePolicyManager r5 = com.android.settingslib.spaprivileged.framework.common.ContextsKt.getDevicePolicyManager(r5)
            java.lang.String r1 = r6.packageName
            int r3 = com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt.getUserId(r6)
            boolean r5 = com.android.settings.Utils.isProfileOrDeviceOwner(r5, r1, r3)
            if (r5 == 0) goto L33
            return r2
        L33:
            boolean r5 = r4.isDisallowControl(r6)
            if (r5 == 0) goto L3a
            return r2
        L3a:
            java.lang.String r5 = r6.packageName
            com.android.settings.spa.app.appinfo.AppButtonRepository$HomePackages r4 = r4.getHomePackageInfo()
            java.util.Set r1 = r4.homePackages
            boolean r1 = r1.contains(r5)
            r3 = 1
            if (r1 != 0) goto L4b
        L49:
            r4 = r2
            goto L70
        L4b:
            java.util.Set r1 = r4.homePackages
            int r1 = r1.size()
            if (r1 != r3) goto L54
            goto L6f
        L54:
            android.content.ComponentName r4 = r4.currentDefaultHome
            if (r4 == 0) goto L5d
            java.lang.String r4 = r4.getPackageName()
            goto L5e
        L5d:
            r4 = 0
        L5e:
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r4)
            if (r4 == 0) goto L49
            boolean r4 = com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.improveHomeAppBehavior()
            if (r4 == 0) goto L6f
            boolean r4 = r6.isSystemApp()
            goto L70
        L6f:
            r4 = r3
        L70:
            if (r4 == 0) goto L73
            return r2
        L73:
            boolean r4 = r6.isResourceOverlay()
            if (r4 == 0) goto L8d
            java.lang.String r4 = r6.packageName
            android.os.UserHandle r5 = com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt.getUserHandle(r6)
            android.content.om.OverlayInfo r4 = r0.getOverlayInfo(r4, r5)
            if (r4 == 0) goto L8d
            boolean r4 = r4.isEnabled()
            if (r4 != r3) goto L8d
            r4 = r3
            goto L8e
        L8d:
            r4 = r2
        L8e:
            if (r4 == 0) goto L91
            return r2
        L91:
            return r3
        L92:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "Required value was null."
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.appinfo.AppButtonRepository.isAllowUninstallOrArchive(android.content.Context,"
                    + " android.content.pm.ApplicationInfo):boolean");
    }

    public final boolean isDisallowControl(ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(app, "app");
        if (Utils.isDeviceProvisioningPackage(this.context.getResources(), app.packageName)
                || this.devicePolicyManager.isUninstallInQueue(app.packageName)) {
            return true;
        }
        Context context = this.context;
        Intrinsics.checkNotNullParameter(context, "context");
        return ContextsKt.getUserManager(context)
                .hasBaseUserRestriction("no_control_apps", ApplicationInfosKt.getUserHandle(app));
    }

    public final boolean isUninstallBlockedByAdmin(ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(app, "app");
        RestrictedLockUtils.EnforcedAdmin checkIfUninstallBlocked =
                RestrictedLockUtilsInternal.checkIfUninstallBlocked(
                        this.context, ApplicationInfosKt.getUserId(app), app.packageName);
        if (checkIfUninstallBlocked == null) {
            return false;
        }
        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                this.context, checkIfUninstallBlocked);
        return true;
    }
}
