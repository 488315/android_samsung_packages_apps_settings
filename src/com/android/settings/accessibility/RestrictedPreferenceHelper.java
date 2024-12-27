package com.android.settings.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.settings.R;
import com.android.settings.development.Enable16kUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RestrictedPreferenceHelper {
    public final AppOpsManager mAppOps;
    public final Context mContext;
    public final DevicePolicyManager mDpm;

    public RestrictedPreferenceHelper(Context context) {
        this.mContext = context;
        this.mDpm = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
    }

    public static void putBasicExtras(
            RestrictedPreference restrictedPreference,
            String str,
            CharSequence charSequence,
            CharSequence charSequence2,
            CharSequence charSequence3,
            int i,
            String str2,
            ComponentName componentName) {
        Bundle extras = restrictedPreference.getExtras();
        extras.putString("preference_key", str);
        extras.putCharSequence(UniversalCredentialUtil.AGENT_TITLE, charSequence);
        extras.putCharSequence("intro", charSequence2);
        extras.putCharSequence(UniversalCredentialUtil.AGENT_SUMMARY, charSequence3);
        extras.putParcelable("component_name", componentName);
        extras.putInt("animated_image_res", i);
        extras.putString("html_description", str2);
        extras.putInt("metrics_category", 4);
    }

    public static void putSettingsExtras(
            RestrictedPreference restrictedPreference, String str, String str2) {
        Bundle extras = restrictedPreference.getExtras();
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        extras.putString(
                "settings_title",
                restrictedPreference
                        .getContext()
                        .getText(R.string.accessibility_menu_item_settings)
                        .toString());
        extras.putString("settings_component_name", new ComponentName(str, str2).flattenToString());
    }

    public static void putTileServiceExtras(
            RestrictedPreference restrictedPreference, String str, String str2) {
        Bundle extras = restrictedPreference.getExtras();
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        extras.putString(
                "tile_service_component_name", new ComponentName(str, str2).flattenToString());
    }

    public final List createAccessibilityActivityPreferenceList(List list) {
        Set enabledServicesFromSettings =
                AccessibilityUtils.getEnabledServicesFromSettings(this.mContext);
        List permittedAccessibilityServices =
                this.mDpm.getPermittedAccessibilityServices(UserHandle.myUserId());
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            AccessibilityShortcutInfo accessibilityShortcutInfo =
                    (AccessibilityShortcutInfo) list.get(i);
            ActivityInfo activityInfo = accessibilityShortcutInfo.getActivityInfo();
            ComponentName componentName = accessibilityShortcutInfo.getComponentName();
            boolean contains = enabledServicesFromSettings.contains(componentName);
            AccessibilityActivityPreference accessibilityActivityPreference =
                    new AccessibilityActivityPreference(
                            this.mContext,
                            componentName.getPackageName(),
                            activityInfo.applicationInfo.uid,
                            accessibilityShortcutInfo);
            setRestrictedPreferenceEnabled(
                    accessibilityActivityPreference, permittedAccessibilityServices, contains);
            arrayList.add(accessibilityActivityPreference);
        }
        return arrayList;
    }

    public final List createAccessibilityServicePreferenceList(List list) {
        Set enabledServicesFromSettings =
                AccessibilityUtils.getEnabledServicesFromSettings(this.mContext);
        List permittedAccessibilityServices =
                this.mDpm.getPermittedAccessibilityServices(UserHandle.myUserId());
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            AccessibilityServiceInfo accessibilityServiceInfo =
                    (AccessibilityServiceInfo) list.get(i);
            ResolveInfo resolveInfo = accessibilityServiceInfo.getResolveInfo();
            String str = resolveInfo.serviceInfo.packageName;
            if (!str.contains("android.apps.accessibility.voiceaccess")
                    || !Enable16kUtils.isPageAgnosticModeOn(this.mContext)) {
                boolean contains =
                        enabledServicesFromSettings.contains(
                                new ComponentName(str, resolveInfo.serviceInfo.name));
                AccessibilityServicePreference accessibilityServicePreference =
                        new AccessibilityServicePreference(
                                this.mContext,
                                str,
                                resolveInfo.serviceInfo.applicationInfo.uid,
                                accessibilityServiceInfo,
                                contains);
                setRestrictedPreferenceEnabled(
                        accessibilityServicePreference, permittedAccessibilityServices, contains);
                arrayList.add(accessibilityServicePreference);
            }
        }
        return arrayList;
    }

    public final void setRestrictedPreferenceEnabled(
            RestrictedPreference restrictedPreference, List list, boolean z) {
        boolean z2;
        boolean z3 = true;
        boolean z4 = list == null || list.contains(restrictedPreference.getPackageName());
        if (Flags.enhancedConfirmationModeApisEnabled()
                && android.security.Flags.extendEcmToAllSettings()) {
            restrictedPreference.mHelper.checkEcmRestrictionAndSetDisabled(
                    "android:bind_accessibility_service", restrictedPreference.getPackageName());
            if (restrictedPreference.mHelper.mDisabledByEcm) {
                z4 = false;
            }
            if (z4 || z) {
                restrictedPreference.setEnabled(true);
                return;
            }
            RestrictedLockUtils.EnforcedAdmin checkIfAccessibilityServiceDisallowed =
                    RestrictedLockUtilsInternal.checkIfAccessibilityServiceDisallowed(
                            this.mContext,
                            UserHandle.myUserId(),
                            restrictedPreference.getPackageName());
            if (checkIfAccessibilityServiceDisallowed != null) {
                restrictedPreference.setDisabledByAdmin(checkIfAccessibilityServiceDisallowed);
                return;
            } else {
                if (restrictedPreference.mHelper.mDisabledByEcm) {
                    return;
                }
                restrictedPreference.setEnabled(false);
                return;
            }
        }
        if (z4) {
            try {
                AppOpsManager appOpsManager = this.mAppOps;
                com.android.settingslib.RestrictedPreferenceHelper restrictedPreferenceHelper =
                        restrictedPreference.mHelper;
                int noteOpNoThrow =
                        appOpsManager.noteOpNoThrow(
                                119,
                                restrictedPreferenceHelper != null
                                        ? restrictedPreferenceHelper.uid
                                        : -1,
                                restrictedPreference.getPackageName());
                z4 =
                        !this.mContext
                                        .getResources()
                                        .getBoolean(
                                                android.R.bool
                                                        .config_focusScrollContainersInTouchMode)
                                || noteOpNoThrow == 0
                                || noteOpNoThrow == 3;
                z2 = z4;
            } catch (Exception unused) {
                z2 = true;
            }
        } else {
            z2 = false;
        }
        if (z4 || z) {
            restrictedPreference.setEnabled(true);
            return;
        }
        RestrictedLockUtils.EnforcedAdmin checkIfAccessibilityServiceDisallowed2 =
                RestrictedLockUtilsInternal.checkIfAccessibilityServiceDisallowed(
                        this.mContext,
                        UserHandle.myUserId(),
                        restrictedPreference.getPackageName());
        if (checkIfAccessibilityServiceDisallowed2 != null) {
            restrictedPreference.setDisabledByAdmin(checkIfAccessibilityServiceDisallowed2);
            return;
        }
        if (z2) {
            restrictedPreference.setEnabled(false);
            return;
        }
        com.android.settingslib.RestrictedPreferenceHelper restrictedPreferenceHelper2 =
                restrictedPreference.mHelper;
        if (!restrictedPreferenceHelper2.mDisabledByEcm) {
            restrictedPreferenceHelper2.mDisabledByEcm = true;
            restrictedPreferenceHelper2.updateDisabledState();
        } else {
            z3 = false;
        }
        if (z3) {
            restrictedPreference.notifyChanged();
        }
    }
}
