package com.samsung.android.settings.eternal.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.eternal.data.SupplierInfo;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.providercall.ProviderCallExecutorService;
import com.samsung.android.settings.eternal.providercall.ProviderCallTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SupplierManager {
    public final Context mContext;
    public final HashMap mDtdVersionHashMap;
    public final String mLatestDTDVersion;
    public final String mLatestVersionUID;
    public final HashMap mProviderVersions;
    public final List mResolveInfoHolder;
    public final HashMap mSupplierMap = new HashMap();
    public final HashMap mUIDs;

    public SupplierManager(Context context, PackageManager packageManager) {
        SupplierInfo supplierInfo;
        this.mContext = context;
        List<ResolveInfo> queryIntentContentProviders =
                packageManager.queryIntentContentProviders(
                        new Intent("com.samsung.android.intent.action.SCENE"), 128);
        this.mResolveInfoHolder = queryIntentContentProviders;
        if (queryIntentContentProviders == null || queryIntentContentProviders.isEmpty()) {
            EternalFileLog.d(
                    "SupplierManager", "collectResolveInfos() mResolveInfoHolder is empty");
            return;
        }
        EternalFileLog.d(
                "SupplierManager",
                "collectResolveInfos() resolveInfos size : " + this.mResolveInfoHolder.size());
        HashMap collectInfoFromProvider =
                collectInfoFromProvider("get_uid", NetworkAnalyticsConstants.DataPoints.UID);
        this.mUIDs = collectInfoFromProvider;
        if (collectInfoFromProvider.isEmpty()) {
            EternalFileLog.d("SupplierManager", "collectResolveInfos() uidHashMap is empty");
            return;
        }
        this.mDtdVersionHashMap = collectInfoFromProvider("get_dtd_ver", "dtd_version");
        this.mProviderVersions = collectInfoFromProvider("get_version", FieldName.VERSION);
        this.mLatestDTDVersion = ApnSettings.MVNO_NONE;
        for (ResolveInfo resolveInfo : this.mResolveInfoHolder) {
            Uri parse = Uri.parse("content://" + resolveInfo.providerInfo.authority);
            String str = (String) this.mUIDs.get(parse);
            if (TextUtils.isEmpty(str)) {
                EternalFileLog.e(
                        "SupplierManager",
                        "buildSupplierList() - [" + parse.toString() + "] UID is empty");
            } else {
                String str2 = (String) this.mDtdVersionHashMap.get(parse);
                String str3 = (String) this.mUIDs.get(parse);
                if (!TextUtils.isEmpty(str2) && this.mLatestDTDVersion.compareTo(str2) < 0) {
                    this.mLatestDTDVersion = str2;
                    this.mLatestVersionUID = str3;
                }
                SupplierInfo.Builder builder = new SupplierInfo.Builder();
                builder.mUri = parse;
                builder.mPackageName = resolveInfo.providerInfo.packageName;
                builder.mDTDVersion = (String) this.mDtdVersionHashMap.get(parse);
                Bundle bundle = resolveInfo.providerInfo.metaData;
                if (bundle != null) {
                    builder.mUnSupportedSolution =
                            bundle.getString(
                                    "com.samsung.android.eternal.unsupported_solution",
                                    ApnSettings.MVNO_NONE);
                }
                if (builder.mUri == null || TextUtils.isEmpty(builder.mPackageName)) {
                    EternalFileLog.i("SupplierInfo", "Supplier info build error");
                    supplierInfo = null;
                } else {
                    supplierInfo = new SupplierInfo(builder);
                }
                if (supplierInfo != null) {
                    this.mSupplierMap.put(str, supplierInfo);
                    EternalFileLog.i(
                            "SupplierManager",
                            "buildSupplierList() -  UID : [\" + uid + \"], URI : ["
                                    + parse.toString()
                                    + "] is injected");
                }
            }
        }
    }

    public final HashMap collectInfoFromProvider(String str, String str2) {
        ProviderCallExecutorService providerCallExecutorService;
        ExecutorService executorService;
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mResolveInfoHolder.iterator();
        while (it.hasNext()) {
            ProviderCallTask createTask =
                    ProviderCallTask.createTask(
                            this.mContext,
                            null,
                            Uri.parse(
                                    "content://"
                                            + ((ResolveInfo) it.next()).providerInfo.authority),
                            str,
                            null,
                            null);
            arrayList.add(createTask);
            providerCallExecutorService =
                    ProviderCallExecutorService.ProviderCallExecutorServiceHolder.INSTANCE;
            switch (str) {
                case "get_value_all":
                case "get_settings_value":
                case "set_value_all":
                case "set_value":
                case "get_label":
                case "get_value":
                    executorService = providerCallExecutorService.mSingleExecutorService;
                    break;
                default:
                    executorService = providerCallExecutorService.mMultipleExecutorService;
                    break;
            }
            executorService.execute(createTask);
        }
        ProviderCallExecutorService.ProviderCallExecutorServiceHolder.INSTANCE.getClass();
        HashMap hashMap = new HashMap();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            ProviderCallTask providerCallTask = (ProviderCallTask) it2.next();
            Bundle resultBundle = ProviderCallExecutorService.getResultBundle(providerCallTask);
            if (resultBundle != null) {
                Uri uri = providerCallTask.mUri;
                String string = resultBundle.getString(str2);
                if (str2.equals("dtd_version") && string == null) {
                    string = ApnSettings.MVNO_NONE;
                }
                hashMap.put(uri, string);
            }
        }
        return hashMap;
    }
}
