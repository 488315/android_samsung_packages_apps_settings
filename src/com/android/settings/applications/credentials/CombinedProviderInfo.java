package com.android.settings.applications.credentials;

import android.app.admin.DevicePolicyManager;
import android.app.admin.PackagePolicy;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ServiceInfo;
import android.credentials.CredentialProviderInfo;
import android.os.UserHandle;
import android.service.autofill.AutofillServiceInfo;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CombinedProviderInfo {
    public final AutofillServiceInfo mAutofillServiceInfo;
    public final List mCredentialProviderInfos;
    public final boolean mIsDefaultAutofillProvider;
    public final boolean mIsPrimaryCredmanProvider;

    public CombinedProviderInfo(
            List list, AutofillServiceInfo autofillServiceInfo, boolean z, boolean z2) {
        if (list == null) {
            this.mCredentialProviderInfos = new ArrayList();
        } else {
            this.mCredentialProviderInfos = new ArrayList(list);
        }
        this.mAutofillServiceInfo = autofillServiceInfo;
        this.mIsDefaultAutofillProvider = z;
        this.mIsPrimaryCredmanProvider = z2;
    }

    public static List buildMergedList(List list, List list2, String str) {
        ComponentName unflattenFromString =
                str == null ? null : ComponentName.unflattenFromString(str);
        HashSet hashSet = new HashSet();
        HashMap hashMap = new HashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AutofillServiceInfo autofillServiceInfo = (AutofillServiceInfo) it.next();
            String str2 = autofillServiceInfo.getServiceInfo().packageName;
            if (!hashMap.containsKey(str2)) {
                hashMap.put(str2, new ArrayList());
            }
            ((List) hashMap.get(str2)).add(autofillServiceInfo);
            hashSet.add(str2);
        }
        HashMap hashMap2 = new HashMap();
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            CredentialProviderInfo credentialProviderInfo = (CredentialProviderInfo) it2.next();
            String str3 = credentialProviderInfo.getServiceInfo().packageName;
            if (!hashMap2.containsKey(str3)) {
                hashMap2.put(str3, new ArrayList());
            }
            ((List) hashMap2.get(str3)).add(credentialProviderInfo);
            hashSet.add(str3);
        }
        ArrayList arrayList = new ArrayList();
        Iterator it3 = hashSet.iterator();
        while (it3.hasNext()) {
            String str4 = (String) it3.next();
            List list3 = (List) hashMap.getOrDefault(str4, new ArrayList());
            List list4 = (List) hashMap2.getOrDefault(str4, new ArrayList());
            boolean z = false;
            AutofillServiceInfo autofillServiceInfo2 =
                    (list3 == null || list3.isEmpty()) ? null : (AutofillServiceInfo) list3.get(0);
            boolean z2 =
                    unflattenFromString != null
                            && unflattenFromString.getPackageName().equals(str4);
            if (list4 != null && !list4.isEmpty()) {
                z = ((CredentialProviderInfo) list4.get(0)).isPrimary();
            }
            arrayList.add(new CombinedProviderInfo(list4, autofillServiceInfo2, z2, z));
        }
        return arrayList;
    }

    public static CombinedProviderInfo getTopProvider(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CombinedProviderInfo combinedProviderInfo = (CombinedProviderInfo) it.next();
            if (combinedProviderInfo.mIsDefaultAutofillProvider) {
                return combinedProviderInfo;
            }
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            CombinedProviderInfo combinedProviderInfo2 = (CombinedProviderInfo) it2.next();
            if (combinedProviderInfo2.mIsPrimaryCredmanProvider) {
                return combinedProviderInfo2;
            }
        }
        return null;
    }

    public static void launchSettingsActivityIntent(
            Context context, CharSequence charSequence, CharSequence charSequence2, int i) {
        Intent intent;
        if (TextUtils.isEmpty(charSequence) || TextUtils.isEmpty(charSequence2)) {
            intent = null;
        } else {
            ComponentName componentName =
                    new ComponentName(String.valueOf(charSequence), String.valueOf(charSequence2));
            intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setComponent(componentName);
            intent.addFlags(268435456);
        }
        if (intent == null) {
            return;
        }
        try {
            context.startActivityAsUser(intent, UserHandle.of(i));
        } catch (ActivityNotFoundException e) {
            Log.e("CombinedProviderInfo", "Failed to open settings activity", e);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("pkg_name", charSequence.toString());
        SALogging.insertSALog("ST8500", "ST8501", hashMap, 0);
    }

    public final CharSequence getAppName(Context context) {
        CharSequence charSequence;
        ServiceInfo brandingService = getBrandingService();
        if (brandingService != null) {
            charSequence = brandingService.loadLabel(context.getPackageManager());
            if (brandingService.packageName.equals("com.samsung.android.samsungpassautofill")) {
                charSequence = context.getResources().getString(R.string.sec_samsung_pass_title);
            }
        } else {
            charSequence = ApnSettings.MVNO_NONE;
        }
        if (!TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        ApplicationInfo applicationInfo = getApplicationInfo();
        return (applicationInfo == null
                        || !TextUtils.isEmpty(
                                applicationInfo.loadLabel(context.getPackageManager())))
                ? ApnSettings.MVNO_NONE
                : applicationInfo.packageName;
    }

    public final ApplicationInfo getApplicationInfo() {
        return !((ArrayList) this.mCredentialProviderInfos).isEmpty()
                ? ((CredentialProviderInfo) ((ArrayList) this.mCredentialProviderInfos).get(0))
                        .getServiceInfo()
                        .applicationInfo
                : this.mAutofillServiceInfo.getServiceInfo().applicationInfo;
    }

    public final ServiceInfo getBrandingService() {
        AutofillServiceInfo autofillServiceInfo = this.mAutofillServiceInfo;
        if (autofillServiceInfo != null) {
            return autofillServiceInfo.getServiceInfo();
        }
        if (((ArrayList) this.mCredentialProviderInfos).isEmpty()) {
            return null;
        }
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) this.mCredentialProviderInfos).iterator();
        while (it.hasNext()) {
            CredentialProviderInfo credentialProviderInfo = (CredentialProviderInfo) it.next();
            String flattenToString = credentialProviderInfo.getComponentName().flattenToString();
            hashMap.put(flattenToString, credentialProviderInfo.getServiceInfo());
            arrayList.add(flattenToString);
        }
        Collections.sort(arrayList);
        return (ServiceInfo) hashMap.get(arrayList.get(0));
    }

    public final RestrictedLockUtils.EnforcedAdmin getDeviceAdminRestrictions(
            Context context, int i) {
        ApplicationInfo applicationInfo = getApplicationInfo();
        String str = applicationInfo != null ? applicationInfo.packageName : null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Context createContextAsUser = context.createContextAsUser(UserHandle.of(i), 0);
        boolean z = RestrictedLockUtilsInternal.DEBUG;
        PackagePolicy credentialManagerPolicy =
                ((DevicePolicyManager)
                                createContextAsUser.getSystemService(DevicePolicyManager.class))
                        .getCredentialManagerPolicy();
        if (credentialManagerPolicy == null
                || credentialManagerPolicy.isPackageAllowed(str, new HashSet())) {
            return null;
        }
        RestrictedLockUtils.EnforcedAdmin deviceOwner =
                RestrictedLockUtilsInternal.getDeviceOwner(createContextAsUser);
        return deviceOwner != null
                ? deviceOwner
                : RestrictedLockUtils.getProfileOrDeviceOwner(
                        createContextAsUser,
                        null,
                        UserHandle.of(
                                RestrictedLockUtilsInternal.getManagedProfileId(
                                        createContextAsUser, 0)));
    }

    public final String getSettingsActivity() {
        Iterator it = ((ArrayList) this.mCredentialProviderInfos).iterator();
        while (it.hasNext()) {
            CharSequence settingsActivity =
                    ((CredentialProviderInfo) it.next()).getSettingsActivity();
            if (!TextUtils.isEmpty(settingsActivity)) {
                return String.valueOf(settingsActivity);
            }
        }
        AutofillServiceInfo autofillServiceInfo = this.mAutofillServiceInfo;
        if (autofillServiceInfo == null) {
            return null;
        }
        String settingsActivity2 = autofillServiceInfo.getSettingsActivity();
        if (TextUtils.isEmpty(settingsActivity2)) {
            return null;
        }
        return settingsActivity2;
    }

    public final String getSettingsSubtitle() {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) this.mCredentialProviderInfos).iterator();
        while (it.hasNext()) {
            String valueOf =
                    String.valueOf(((CredentialProviderInfo) it.next()).getSettingsSubtitle());
            if (!TextUtils.isEmpty(valueOf) && !valueOf.equals("null")) {
                arrayList.add(valueOf);
            }
        }
        return arrayList.size() == 0 ? ApnSettings.MVNO_NONE : String.join(", ", arrayList);
    }
}
