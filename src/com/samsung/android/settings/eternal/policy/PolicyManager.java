package com.samsung.android.settings.eternal.policy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.eternal.data.SupplierInfo;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.manager.SupplierManager;
import com.samsung.android.settings.eternal.policy.provider.DefaultPolicy;
import com.samsung.android.settings.eternal.policy.provider.LibraryPolicy;
import com.samsung.android.settings.eternal.policy.provider.PolicyItem;
import com.samsung.android.settings.eternal.policy.provider.PolicyProvider;
import com.samsung.android.settings.eternal.policy.provider.SCPMPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PolicyManager {
    public BackupPolicy mBackupPolicy;
    public RestorePolicy mRestorePolicy;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class LazyHolder {
        public static final PolicyManager sInstance = new PolicyManager();
    }

    public final void initialize(Context context, SupplierManager supplierManager) {
        SupplierInfo supplierInfo =
                (SupplierInfo) supplierManager.mSupplierMap.get(supplierManager.mLatestVersionUID);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (supplierInfo == null) {
            EternalFileLog.e("Eternal/PolicyManager", "buildPolicyProvider() supplierInfo is null");
        } else {
            StringBuilder sb = new StringBuilder("buildPolicyProvider() latest dtd : ");
            sb.append(supplierInfo.mDTDVersion);
            sb.append(" / package : ");
            String str = supplierInfo.mPackageName;
            sb.append(str);
            EternalFileLog.i("Eternal/PolicyManager", sb.toString());
            Context context2 = null;
            if (TextUtils.isEmpty(str)) {
                Log.e("EternalUtils", "Fatal case : No pkgName - " + str);
            } else {
                try {
                    Context createPackageContext = context.createPackageContext(str, 0);
                    EternalFileLog.d(
                            "EternalUtils",
                            "getRemoteContextByPkgName() Get Context from pkg: " + str);
                    context2 = createPackageContext;
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e(
                            "EternalUtils",
                            "getRemoteContextByPkgName() Fatal case : No PKG with pkgName  - "
                                    + str);
                }
            }
            LibraryPolicy libraryPolicy = new LibraryPolicy();
            libraryPolicy.mContext = context2;
            libraryPolicy.initializePolicy();
            linkedHashMap.put("LibraryPolicy", libraryPolicy);
        }
        SCPMPolicy sCPMPolicy = new SCPMPolicy();
        sCPMPolicy.mContext = context;
        sCPMPolicy.initializePolicy();
        linkedHashMap.put("SCPMPolicy", sCPMPolicy);
        linkedHashMap.put("DefaultPolicy", new DefaultPolicy());
        TreeMap treeMap = new TreeMap();
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            PolicyProvider policyProvider = (PolicyProvider) ((Map.Entry) it.next()).getValue();
            if (policyProvider.isValidProvider()) {
                treeMap.put(policyProvider.getPolicyVersion(), policyProvider);
            } else {
                EternalFileLog.w(
                        "Eternal/PolicyManager",
                        "getLatestPolicyProvider() Invalid provider : "
                                + policyProvider.getPolicyId()
                                + " / version : "
                                + policyProvider.getPolicyVersion()
                                + " / sizeLimitAllow : "
                                + ((ArrayList) policyProvider.mBackupSizeAllowedItems).size()
                                + " / restoreRestriction : "
                                + ((ArrayList) policyProvider.mRestoreRestrictedItems).size()
                                + " / deferredRestoration : "
                                + ((ArrayList) policyProvider.mDeferredRestorationItems).size());
            }
        }
        PolicyProvider defaultPolicy =
                (treeMap.isEmpty() || treeMap.lastEntry() == null)
                        ? new DefaultPolicy()
                        : (PolicyProvider) treeMap.lastEntry().getValue();
        EternalFileLog.i(
                "Eternal/PolicyManager",
                "initialize() latestPolicyProvider : "
                        + defaultPolicy.getPolicyId()
                        + " / "
                        + defaultPolicy.getPolicyVersion());
        final HashMap hashMap = new HashMap();
        supplierManager.mSupplierMap.forEach(
                new BiConsumer() { // from class:
                                   // com.samsung.android.settings.eternal.policy.PolicyManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        hashMap.put((String) obj, ((SupplierInfo) obj2).mDTDVersion);
                    }
                });
        final BackupPolicy backupPolicy = new BackupPolicy();
        backupPolicy.mBackupSizeLimitAllowItems = new HashSet();
        backupPolicy.mPolicyValidator = new PolicyValidator(hashMap);
        backupPolicy.mPolicyId = defaultPolicy.getPolicyId();
        defaultPolicy.mBackupSizeAllowedItems.stream()
                .filter(
                        new Predicate() { // from class:
                                          // com.samsung.android.settings.eternal.policy.BackupPolicy$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return BackupPolicy.this.mPolicyValidator.isValidPolicy(
                                        (PolicyItem) obj);
                            }
                        })
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.eternal.policy.BackupPolicy$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((HashSet) BackupPolicy.this.mBackupSizeLimitAllowItems)
                                        .add(((PolicyItem) obj).getKey());
                            }
                        });
        EternalFileLog.i(
                "Eternal/BackupPolicy",
                "initializeBackupPolicy() "
                        + backupPolicy.mPolicyId
                        + " / "
                        + ((HashSet) backupPolicy.mBackupSizeLimitAllowItems).size());
        this.mBackupPolicy = backupPolicy;
        final RestorePolicy restorePolicy = new RestorePolicy();
        restorePolicy.mRestoreRestrictionItems = new ArrayList();
        restorePolicy.mPolicyValidator = new PolicyValidator(hashMap);
        restorePolicy.mPolicyId = defaultPolicy.getPolicyId();
        final int i = 0;
        defaultPolicy.mRestoreRestrictedItems.stream()
                .filter(
                        new Predicate() { // from class:
                                          // com.samsung.android.settings.eternal.policy.RestorePolicy$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                int i2 = i;
                                RestorePolicy restorePolicy2 = restorePolicy;
                                PolicyItem policyItem = (PolicyItem) obj;
                                switch (i2) {
                                }
                                return restorePolicy2.mPolicyValidator.isValidPolicy(policyItem);
                            }
                        })
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.eternal.policy.RestorePolicy$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i2 = i;
                                RestorePolicy restorePolicy2 = restorePolicy;
                                PolicyItem policyItem = (PolicyItem) obj;
                                switch (i2) {
                                    case 0:
                                        ((ArrayList) restorePolicy2.mRestoreRestrictionItems)
                                                .add(policyItem.getKey());
                                        break;
                                    default:
                                        ((ArrayList) restorePolicy2.mDeferredRestorationItems)
                                                .add(policyItem.getKey());
                                        break;
                                }
                            }
                        });
        ArrayList arrayList = new ArrayList();
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_SETTINGS_CONFIG_RESTORE_EXCLUDED_ITEMS");
        if (!TextUtils.isEmpty(string)) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
            while (stringTokenizer.hasMoreTokens()) {
                arrayList.add(stringTokenizer.nextToken());
            }
        }
        ((ArrayList) restorePolicy.mRestoreRestrictionItems).addAll(arrayList);
        EternalFileLog.i(
                "Eternal/RestorePolicy",
                "initializeRestorePolicy() "
                        + restorePolicy.mPolicyId
                        + " / "
                        + ((ArrayList) restorePolicy.mRestoreRestrictionItems).size()
                        + " (FloatingFeature - "
                        + arrayList.size()
                        + ")");
        restorePolicy.mDeferredRestorationItems = new ArrayList();
        restorePolicy.mPolicyValidator = new PolicyValidator(hashMap);
        restorePolicy.mPolicyId = defaultPolicy.getPolicyId();
        final int i2 = 1;
        defaultPolicy.mDeferredRestorationItems.stream()
                .filter(
                        new Predicate() { // from class:
                                          // com.samsung.android.settings.eternal.policy.RestorePolicy$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                int i22 = i2;
                                RestorePolicy restorePolicy2 = restorePolicy;
                                PolicyItem policyItem = (PolicyItem) obj;
                                switch (i22) {
                                }
                                return restorePolicy2.mPolicyValidator.isValidPolicy(policyItem);
                            }
                        })
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.eternal.policy.RestorePolicy$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i22 = i2;
                                RestorePolicy restorePolicy2 = restorePolicy;
                                PolicyItem policyItem = (PolicyItem) obj;
                                switch (i22) {
                                    case 0:
                                        ((ArrayList) restorePolicy2.mRestoreRestrictionItems)
                                                .add(policyItem.getKey());
                                        break;
                                    default:
                                        ((ArrayList) restorePolicy2.mDeferredRestorationItems)
                                                .add(policyItem.getKey());
                                        break;
                                }
                            }
                        });
        EternalFileLog.i(
                "Eternal/RestorePolicy",
                "initializeDeferredPolicy() "
                        + restorePolicy.mPolicyId
                        + " / "
                        + ((ArrayList) restorePolicy.mDeferredRestorationItems).size());
        this.mRestorePolicy = restorePolicy;
    }
}
