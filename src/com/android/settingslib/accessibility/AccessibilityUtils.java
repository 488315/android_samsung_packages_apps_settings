package com.android.settingslib.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.view.accessibility.AccessibilityManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AccessibilityUtils {
    public static Set getEnabledServicesFromSettings(Context context) {
        return getEnabledServicesFromSettings(context, UserHandle.myUserId());
    }

    public static void setAccessibilityServiceState(
            Context context, ComponentName componentName, boolean z) {
        int myUserId = UserHandle.myUserId();
        Set enabledServicesFromSettings = getEnabledServicesFromSettings(context, myUserId);
        if (enabledServicesFromSettings.isEmpty()) {
            enabledServicesFromSettings = new ArraySet(1);
        }
        if (z) {
            enabledServicesFromSettings.add(componentName);
        } else {
            enabledServicesFromSettings.remove(componentName);
            HashSet hashSet = new HashSet();
            hashSet.clear();
            List<AccessibilityServiceInfo> installedAccessibilityServiceList =
                    AccessibilityManager.getInstance(context)
                            .getInstalledAccessibilityServiceList();
            if (installedAccessibilityServiceList != null) {
                Iterator<AccessibilityServiceInfo> it =
                        installedAccessibilityServiceList.iterator();
                while (it.hasNext()) {
                    ServiceInfo serviceInfo = it.next().getResolveInfo().serviceInfo;
                    hashSet.add(new ComponentName(serviceInfo.packageName, serviceInfo.name));
                }
            }
            Iterator it2 = enabledServicesFromSettings.iterator();
            while (it2.hasNext() && !hashSet.contains((ComponentName) it2.next())) {}
        }
        StringBuilder sb = new StringBuilder();
        Iterator it3 = enabledServicesFromSettings.iterator();
        while (it3.hasNext()) {
            sb.append(((ComponentName) it3.next()).flattenToString());
            sb.append(':');
        }
        int length = sb.length();
        if (length > 0) {
            sb.deleteCharAt(length - 1);
        }
        Settings.Secure.putStringForUser(
                context.getContentResolver(),
                "enabled_accessibility_services",
                sb.toString(),
                myUserId);
    }

    public static Set getEnabledServicesFromSettings(Context context, int i) {
        String stringForUser =
                Settings.Secure.getStringForUser(
                        context.getContentResolver(), "enabled_accessibility_services", i);
        if (TextUtils.isEmpty(stringForUser)) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                new TextUtils.SimpleStringSplitter(':');
        simpleStringSplitter.setString(stringForUser);
        Iterator<String> it = simpleStringSplitter.iterator();
        while (it.hasNext()) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(it.next());
            if (unflattenFromString != null) {
                hashSet.add(unflattenFromString);
            }
        }
        return hashSet;
    }
}
