package com.samsung.android.settings.eternal.manager;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SemSystemProperties;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0;

import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.lib.episode.EpisodeConstant;
import com.samsung.android.lib.episode.EpisodeUtils;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.settings.eternal.data.BackupInfo;
import com.samsung.android.settings.eternal.data.EpisodeHolder;
import com.samsung.android.settings.eternal.data.SupplierInfo;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.providercall.ProviderCallExecutorService;
import com.samsung.android.settings.eternal.providercall.ProviderCallTask;
import com.samsung.android.settings.eternal.utils.EternalUtils;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EternalManager {
    public final Context mContext;
    public final SupplierManager mSupplierManager;

    public EternalManager(Context context) {
        this.mContext = context;
        this.mSupplierManager = new SupplierManager(context, context.getPackageManager());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final EpisodeHolder getValues(BackupInfo backupInfo, HashMap hashMap) {
        HashMap hashMap2;
        Scene.Builder builder;
        String str;
        EpisodeHolder episodeHolder = new EpisodeHolder();
        ArrayList launchablePackageList = EternalUtils.getLaunchablePackageList(this.mContext);
        SupplierManager supplierManager = this.mSupplierManager;
        if (supplierManager.mSupplierMap.isEmpty()) {
            EternalFileLog.e("Eternal/EternalManager", "getValues() - isSupplierEmpty");
            return episodeHolder;
        }
        if (hashMap == null || hashMap.isEmpty()) {
            hashMap2 = new HashMap();
            Log.e("Eternal/EternalManager", "getValues() uid is empty");
            Iterator it = supplierManager.mSupplierMap.keySet().iterator();
            while (it.hasNext()) {
                hashMap2.put((String) it.next(), null);
            }
        } else {
            hashMap2 = hashMap;
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : hashMap2.entrySet()) {
            String str2 = (String) entry.getKey();
            Uri uri =
                    supplierManager.mSupplierMap.get(str2) == null
                            ? null
                            : ((SupplierInfo) supplierManager.mSupplierMap.get(str2)).mUri;
            if (uri == null) {
                EternalFileLog.e(
                        "Eternal/EternalManager", "getValues() [" + str2 + "] uri is null");
            } else {
                List list = (List) entry.getValue();
                Bundle bundle = new Bundle();
                if (list != null) {
                    bundle.putSerializable("keyList", (Serializable) list);
                }
                bundle.putInt("requestFrom", backupInfo.mRequestFrom);
                bundle.putString(
                        "packageList",
                        EpisodeUtils.compressString(
                                EpisodeUtils.convertListToString(launchablePackageList)));
                Context context = this.mContext;
                String str3 = EpisodeConstant.DTD_VERSION;
                ProviderCallTask createTask =
                        ProviderCallTask.createTask(
                                context,
                                str2,
                                uri,
                                "get_value",
                                KnoxVpnPolicyConstants.NEW_FW,
                                bundle);
                arrayList.add(createTask);
                ProviderCallExecutorService providerCallExecutorService =
                        ProviderCallExecutorService.ProviderCallExecutorServiceHolder.INSTANCE;
                providerCallExecutorService.getClass();
                providerCallExecutorService.mSingleExecutorService.execute(createTask);
            }
        }
        ProviderCallExecutorService.ProviderCallExecutorServiceHolder.INSTANCE.getClass();
        for (Map.Entry entry2 : ProviderCallExecutorService.getResultByUid(arrayList).entrySet()) {
            String str4 = (String) entry2.getKey();
            Bundle bundle2 = (Bundle) entry2.getValue();
            if (bundle2 == null) {
                SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                        "getValues() - [",
                        str4,
                        "] fromSupplier is null",
                        "Eternal/EternalManager");
            } else {
                try {
                    bundle2.setClassLoader(Scene.class.getClassLoader());
                    ArrayList parcelableArrayList = bundle2.getParcelableArrayList("sceneList");
                    if (parcelableArrayList == null) {
                        EternalFileLog.e(
                                "Eternal/EternalManager",
                                "##### getValues() - [" + str4 + "] is null");
                    } else {
                        EternalFileLog.d(
                                "Eternal/EternalManager",
                                "getValues() - [" + str4 + "] " + parcelableArrayList.size());
                        SourceInfo sourceInfo = new SourceInfo();
                        sourceInfo.mVersion = bundle2.getString(FieldName.VERSION);
                        sourceInfo.mDTDVersion = bundle2.getString("dtd_version");
                        sourceInfo.mDeviceType =
                                SemSystemProperties.get("ro.build.characteristics");
                        episodeHolder.putBackupSceneList(str4, parcelableArrayList);
                        episodeHolder.mSourceInfoMap.put(str4, sourceInfo);
                    }
                } catch (Exception e) {
                    StringBuilder m =
                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                    "getValues() - [", str4, "] Exception ");
                    m.append(TextUtils.isEmpty(e.getMessage()) ? "message null" : e.getMessage());
                    EternalFileLog.d("Eternal/EternalManager", m.toString());
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 8; i++) {
            String str5 = KeyListManager.GENERAL_KEY_LIST[i];
            builder = new Scene.Builder(str5);
            str5.getClass();
            switch (str5) {
                case "/GeneralInfo/OneUIVersion":
                    Context context2 = this.mContext;
                    if (SemSystemProperties.getInt("ro.build.version.oneui", 0) > 0) {
                        int i2 = SemSystemProperties.getInt("ro.build.version.oneui", 0);
                        int i3 = i2 / EnterpriseContainerConstants.SYSTEM_SIGNED_APP;
                        int i4 = (i2 % EnterpriseContainerConstants.SYSTEM_SIGNED_APP) / 100;
                        int i5 = i2 % 100;
                        str =
                                i5 == 0
                                        ? String.valueOf(i3) + "." + String.valueOf(i4)
                                        : String.valueOf(i3)
                                                + "."
                                                + String.valueOf(i4)
                                                + "."
                                                + String.valueOf(i5);
                    } else if (EternalUtils.isSemAvailable(context2)) {
                        int i6 = Build.VERSION.SEM_PLATFORM_INT - 90000;
                        str =
                                String.valueOf(i6 / EnterpriseContainerConstants.SYSTEM_SIGNED_APP)
                                        + "."
                                        + String.valueOf(
                                                (i6
                                                                % EnterpriseContainerConstants
                                                                        .SYSTEM_SIGNED_APP)
                                                        / 100);
                    } else {
                        str = "1.0";
                    }
                    builder.setValue(str, false);
                    arrayList2.add(builder.build());
                    break;
                case "/GeneralInfo/CreatedTime":
                    if (backupInfo.mRequestFrom == 2) {
                        break;
                    } else {
                        builder.setValue(
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                                        .format(new Date()),
                                false);
                        arrayList2.add(builder.build());
                        break;
                    }
                case "/GeneralInfo/DeviceType":
                    builder.setValue(SemSystemProperties.get("ro.build.characteristics"), false);
                    arrayList2.add(builder.build());
                    break;
                case "/GeneralInfo/OSVersion":
                    builder.setValue(Integer.valueOf(Build.VERSION.SDK_INT), false);
                    arrayList2.add(builder.build());
                    break;
                case "/GeneralInfo/PackageList":
                    builder.setValue(
                            EpisodeUtils.compressString(
                                    EpisodeUtils.convertListToString(
                                            EternalUtils.getLaunchablePackageList(this.mContext))),
                            false);
                    arrayList2.add(builder.build());
                    break;
                case "/GeneralInfo/BuildNum":
                    builder.setValue(Build.DISPLAY, false);
                    arrayList2.add(builder.build());
                    break;
                case "/GeneralInfo/InitialOsVersion":
                    builder.setValue(
                            String.valueOf(
                                    SemSystemProperties.getInt(
                                            TouchPadGestureSettingsController.FIRST_API_LEVEL, 0)),
                            false);
                    arrayList2.add(builder.build());
                    break;
                case "/GeneralInfo/Version":
                    builder.setValue("2.00", false);
                    arrayList2.add(builder.build());
                    break;
                default:
                    arrayList2.add(builder.build());
                    break;
            }
        }
        ((ArrayList) episodeHolder.mGeneralInfoList).clear();
        ((ArrayList) episodeHolder.mGeneralInfoList).addAll(arrayList2);
        episodeHolder.mBackupInfo = backupInfo;
        return episodeHolder;
    }
}
