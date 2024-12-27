package com.android.settings.security.trustagent;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.util.Log;

import com.android.settingslib.RestrictedLockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TrustAgentManager {
    static final String PERMISSION_PROVIDE_AGENT = "android.permission.PROVIDE_TRUST_AGENT";
    public static final Intent TRUST_AGENT_INTENT =
            new Intent("android.service.trust.TrustAgentService");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TrustAgentComponentInfo {
        public RestrictedLockUtils.EnforcedAdmin admin;
        public ComponentName componentName;
        public String summary;
        public String title;
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x00f8, code lost:

       if (r11 != null) goto L65;
    */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00f1, code lost:

       r11.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00f5, code lost:

       if (r11 != null) goto L65;
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00ef, code lost:

       if (r11 != null) goto L65;
    */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0153 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0138 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List getActiveTrustAgents(
            android.content.Context r16,
            com.android.internal.widget.LockPatternUtils r17,
            boolean r18) {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.security.trustagent.TrustAgentManager.getActiveTrustAgents(android.content.Context,"
                    + " com.android.internal.widget.LockPatternUtils, boolean):java.util.List");
    }

    public static ComponentName getComponentName(ResolveInfo resolveInfo) {
        if (resolveInfo.serviceInfo == null) {
            return null;
        }
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        return new ComponentName(serviceInfo.packageName, serviceInfo.name);
    }

    public static boolean shouldProvideTrust(
            PackageManager packageManager, ResolveInfo resolveInfo) {
        String str = resolveInfo.serviceInfo.packageName;
        if (packageManager.checkPermission(PERMISSION_PROVIDE_AGENT, str) == 0) {
            return true;
        }
        Log.w(
                "TrustAgentManager",
                "Skipping agent because package "
                        + str
                        + " does not have permission android.permission.PROVIDE_TRUST_AGENT.");
        return false;
    }
}
