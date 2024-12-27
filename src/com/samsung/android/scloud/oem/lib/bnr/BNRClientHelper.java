package com.samsung.android.scloud.oem.lib.bnr;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.scloud.oem.lib.LOG;
import com.samsung.android.scloud.oem.lib.common.IClientHelper;
import com.samsung.android.scloud.oem.lib.common.IServiceHandler;
import com.samsung.android.scloud.oem.lib.utils.FileTool;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BNRClientHelper extends IClientHelper {
    public String OPERATION;
    public final List downloadFileList;
    public final List processedKeyList;
    public final List restoreFileList;
    public final Map serviceHandlerMap;

    public BNRClientHelper() {
        HashMap hashMap = new HashMap();
        this.serviceHandlerMap = hashMap;
        this.processedKeyList = new ArrayList();
        this.restoreFileList = new ArrayList();
        this.downloadFileList = new ArrayList();
        this.OPERATION = ApnSettings.MVNO_NONE;
        final int i = 0;
        hashMap.put(
                "getClientInfo",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        FileInputStream fileInputStream;
                        switch (i) {
                            case 0:
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 1:
                                new Bundle();
                                int i2 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i3 = bundle.getInt("max_count");
                                StringBuilder m =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i2, "[", str, "] GET_ITEM_KEY, start: ", ", max: ");
                                m.append(i3);
                                LOG.i("BNRClientHelper", m.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 2:
                                new Bundle();
                                int i4 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i5 = bundle.getInt("max_count");
                                StringBuilder m2 =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i4,
                                                "[",
                                                str,
                                                "] GET_FILE_META, start: ",
                                                ", max: ");
                                m2.append(i5);
                                LOG.i("BNRClientHelper", m2.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 3:
                                Bundle bundle2 = new Bundle();
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("to_upload_list");
                                if (((ParcelFileDescriptor) bundle.getParcelable("file_descriptor"))
                                        == null) {
                                    LOG.i(
                                            "BNRClientHelper",
                                            "[" + str + "] BACKUP_ITEM, pfd is null");
                                    bundle2.putBoolean("is_success", false);
                                    return bundle2;
                                }
                                bundle.getLong("max_size");
                                if (stringArrayList != null) {
                                    StringBuilder m3 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] BACKUP_ITEM, toUploadList: ");
                                    m3.append(stringArrayList.size());
                                    LOG.i("BNRClientHelper", m3.toString());
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 4:
                                boolean z = bundle.getBoolean("is_success");
                                new Bundle();
                                LOG.i("BNRClientHelper", "[" + str + "] BACKUP_COMPLETE, " + z);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor)
                                                bundle.getParcelable("file_descriptor");
                                new Bundle();
                                ArrayList arrayList = new ArrayList();
                                new ArrayList();
                                LOG.i(
                                        "BNRClientHelper",
                                        "[" + str + "] RESTORE_ITEM, count: " + arrayList.size());
                                AssetFileDescriptor.AutoCloseInputStream autoCloseInputStream = 0;
                                try {
                                    try {
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor());
                                        } catch (IOException | JSONException e) {
                                            e = e;
                                            fileInputStream = null;
                                        } catch (Throwable th) {
                                            th = th;
                                            if (autoCloseInputStream != 0) {
                                                try {
                                                    autoCloseInputStream.close();
                                                } catch (IOException e2) {
                                                    e2.printStackTrace();
                                                }
                                            }
                                            throw th;
                                        }
                                        try {
                                            byte[] bArr =
                                                    new byte
                                                            [(int)
                                                                    parcelFileDescriptor
                                                                            .getStatSize()];
                                            fileInputStream.read(bArr);
                                            JSONArray jSONArray = new JSONArray(new String(bArr));
                                            for (int i6 = 0; i6 < jSONArray.length(); i6++) {
                                                JSONObject optJSONObject =
                                                        jSONArray.optJSONObject(i6);
                                                String optString =
                                                        optJSONObject.optString(
                                                                GoodSettingsContract.EXTRA_NAME
                                                                        .POLICY_KEY);
                                                BNRItem bNRItem =
                                                        new BNRItem(
                                                                optJSONObject.optLong(
                                                                        PhoneRestrictionPolicy
                                                                                .TIMESTAMP),
                                                                optString,
                                                                optJSONObject.optString("value"));
                                                LOG.d(
                                                        "BNRClientHelper",
                                                        "convertToBNRItems: "
                                                                + optString
                                                                + ", "
                                                                + bNRItem.timestamp);
                                                arrayList.add(bNRItem);
                                            }
                                            fileInputStream.close();
                                        } catch (IOException | JSONException e3) {
                                            e = e3;
                                            e.printStackTrace();
                                            if (fileInputStream != null) {
                                                fileInputStream.close();
                                            }
                                            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                    obj);
                                            throw null;
                                        }
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                } catch (Throwable th2) {
                                    th = th2;
                                    autoCloseInputStream = "[";
                                }
                        }
                    }
                });
        final int i2 = 0;
        hashMap.put(
                "backupPrepare",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper.2
                    public final /* synthetic */ BNRClientHelper this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i2) {
                            case 0:
                                LOG.i("BNRClientHelper", "[" + str + "] BACKUP_PREPARE");
                                BNRClientHelper bNRClientHelper = this.this$0;
                                bNRClientHelper.OPERATION = "backup";
                                ((ArrayList) bNRClientHelper.processedKeyList).clear();
                                ((ArrayList) bNRClientHelper.restoreFileList).clear();
                                ((ArrayList) bNRClientHelper.downloadFileList).clear();
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 1:
                                Bundle bundle2 = new Bundle();
                                String str2 = bundle.getString("path") + "_scloud_dwnload";
                                BNRClientHelper bNRClientHelper2 = this.this$0;
                                BNRClientHelper.access$500(bNRClientHelper2, 1, str2);
                                LOG.d(
                                        "BNRClientHelper",
                                        "[" + str + "] RESTORE_FILE, " + bundle.getString("path"));
                                if (FileTool.fileCopy(
                                        bundle.getString("path"),
                                        bundle.getString("path") + "_scloud_origin")) {
                                    BNRClientHelper.access$500(
                                            bNRClientHelper2,
                                            2,
                                            bundle.getString("path") + "_scloud_origin");
                                    if (FileTool.fileCopy(
                                            bundle.getString("path") + "_scloud_dwnload",
                                            bundle.getString("path"))) {
                                        bundle2.putBoolean("is_success", true);
                                    } else {
                                        bundle2.putBoolean("is_success", false);
                                    }
                                } else {
                                    bundle2.putBoolean("is_success", false);
                                }
                                return bundle2;
                            case 2:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("BNRClientHelper", "[" + str + "] RESTORE_COMPLETE, " + z);
                                Bundle bundle3 = new Bundle();
                                bundle3.putBoolean("is_success", true);
                                BNRClientHelper bNRClientHelper3 = this.this$0;
                                if (z) {
                                    StringBuilder m =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[",
                                                    str,
                                                    "] RESTORE_COMPLETE, restoredKeyList size : ");
                                    m.append(
                                            ((ArrayList) bNRClientHelper3.processedKeyList).size());
                                    LOG.i("BNRClientHelper", m.toString());
                                    if (((ArrayList) bNRClientHelper3.processedKeyList).size()
                                            >= 0) {
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        ArrayList arrayList =
                                                (ArrayList) bNRClientHelper3.processedKeyList;
                                        throw null;
                                    }
                                    if (((ArrayList) bNRClientHelper3.restoreFileList).size() > 0) {
                                        Iterator it =
                                                ((ArrayList) bNRClientHelper3.restoreFileList)
                                                        .iterator();
                                        while (it.hasNext()) {
                                            String str3 = (String) it.next();
                                            File file = new File(str3);
                                            if (file.exists()) {
                                                StringBuilder m2 =
                                                        SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                .m(
                                                                        "[",
                                                                        str,
                                                                        "] clearPreRestoredData()"
                                                                            + " delete, name : ",
                                                                        str3,
                                                                        ", deleted : ");
                                                m2.append(file.delete());
                                                LOG.i("BNRClientHelper", m2.toString());
                                            }
                                        }
                                    }
                                    if (((ArrayList) bNRClientHelper3.downloadFileList).size()
                                            > 0) {
                                        Iterator it2 =
                                                ((ArrayList) bNRClientHelper3.downloadFileList)
                                                        .iterator();
                                        while (it2.hasNext()) {
                                            String str4 = (String) it2.next();
                                            File file2 = new File(str4);
                                            if (file2.exists()) {
                                                StringBuilder m3 =
                                                        SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                .m(
                                                                        "[",
                                                                        str,
                                                                        "] clearPreRestoredData()"
                                                                            + " delete, name : ",
                                                                        str4,
                                                                        ", deleted : ");
                                                m3.append(file2.delete());
                                                LOG.i("BNRClientHelper", m3.toString());
                                            }
                                        }
                                        ((ArrayList) bNRClientHelper3.downloadFileList).clear();
                                    }
                                } else {
                                    BNRClientHelper.access$300(bNRClientHelper3, obj);
                                    bundle3.putBoolean("is_success", true);
                                }
                                return bundle3;
                            case 3:
                                StringBuilder m4 =
                                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                "[", str, "] GET_FILE_PATH, ");
                                BNRClientHelper bNRClientHelper4 = this.this$0;
                                m4.append(bNRClientHelper4.OPERATION);
                                LOG.i("BNRClientHelper", m4.toString());
                                String string = bundle.getString("path");
                                boolean z2 = bundle.getBoolean("external");
                                new Bundle();
                                LOG.d(
                                        "BNRClientHelper",
                                        "[" + str + "] GET_FILE_PATH, " + string + ", " + z2);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                String str5 = bNRClientHelper4.OPERATION;
                                throw null;
                            default:
                                LOG.i("BNRClientHelper", "[" + str + "] RESTORE_PREPARE");
                                BNRClientHelper bNRClientHelper5 = this.this$0;
                                bNRClientHelper5.OPERATION = "restore";
                                BNRClientHelper.access$300(bNRClientHelper5, obj);
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i3 = 1;
        hashMap.put(
                "getItemKey",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        FileInputStream fileInputStream;
                        switch (i3) {
                            case 0:
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 1:
                                new Bundle();
                                int i22 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i32 = bundle.getInt("max_count");
                                StringBuilder m =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i22,
                                                "[",
                                                str,
                                                "] GET_ITEM_KEY, start: ",
                                                ", max: ");
                                m.append(i32);
                                LOG.i("BNRClientHelper", m.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 2:
                                new Bundle();
                                int i4 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i5 = bundle.getInt("max_count");
                                StringBuilder m2 =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i4,
                                                "[",
                                                str,
                                                "] GET_FILE_META, start: ",
                                                ", max: ");
                                m2.append(i5);
                                LOG.i("BNRClientHelper", m2.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 3:
                                Bundle bundle2 = new Bundle();
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("to_upload_list");
                                if (((ParcelFileDescriptor) bundle.getParcelable("file_descriptor"))
                                        == null) {
                                    LOG.i(
                                            "BNRClientHelper",
                                            "[" + str + "] BACKUP_ITEM, pfd is null");
                                    bundle2.putBoolean("is_success", false);
                                    return bundle2;
                                }
                                bundle.getLong("max_size");
                                if (stringArrayList != null) {
                                    StringBuilder m3 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] BACKUP_ITEM, toUploadList: ");
                                    m3.append(stringArrayList.size());
                                    LOG.i("BNRClientHelper", m3.toString());
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 4:
                                boolean z = bundle.getBoolean("is_success");
                                new Bundle();
                                LOG.i("BNRClientHelper", "[" + str + "] BACKUP_COMPLETE, " + z);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor)
                                                bundle.getParcelable("file_descriptor");
                                new Bundle();
                                ArrayList arrayList = new ArrayList();
                                new ArrayList();
                                LOG.i(
                                        "BNRClientHelper",
                                        "[" + str + "] RESTORE_ITEM, count: " + arrayList.size());
                                AssetFileDescriptor.AutoCloseInputStream autoCloseInputStream = 0;
                                try {
                                    try {
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor());
                                        } catch (IOException | JSONException e) {
                                            e = e;
                                            fileInputStream = null;
                                        } catch (Throwable th) {
                                            th = th;
                                            if (autoCloseInputStream != 0) {
                                                try {
                                                    autoCloseInputStream.close();
                                                } catch (IOException e2) {
                                                    e2.printStackTrace();
                                                }
                                            }
                                            throw th;
                                        }
                                        try {
                                            byte[] bArr =
                                                    new byte
                                                            [(int)
                                                                    parcelFileDescriptor
                                                                            .getStatSize()];
                                            fileInputStream.read(bArr);
                                            JSONArray jSONArray = new JSONArray(new String(bArr));
                                            for (int i6 = 0; i6 < jSONArray.length(); i6++) {
                                                JSONObject optJSONObject =
                                                        jSONArray.optJSONObject(i6);
                                                String optString =
                                                        optJSONObject.optString(
                                                                GoodSettingsContract.EXTRA_NAME
                                                                        .POLICY_KEY);
                                                BNRItem bNRItem =
                                                        new BNRItem(
                                                                optJSONObject.optLong(
                                                                        PhoneRestrictionPolicy
                                                                                .TIMESTAMP),
                                                                optString,
                                                                optJSONObject.optString("value"));
                                                LOG.d(
                                                        "BNRClientHelper",
                                                        "convertToBNRItems: "
                                                                + optString
                                                                + ", "
                                                                + bNRItem.timestamp);
                                                arrayList.add(bNRItem);
                                            }
                                            fileInputStream.close();
                                        } catch (IOException | JSONException e3) {
                                            e = e3;
                                            e.printStackTrace();
                                            if (fileInputStream != null) {
                                                fileInputStream.close();
                                            }
                                            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                    obj);
                                            throw null;
                                        }
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                } catch (Throwable th2) {
                                    th = th2;
                                    autoCloseInputStream = "[";
                                }
                        }
                    }
                });
        final int i4 = 2;
        hashMap.put(
                "getFileMeta",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        FileInputStream fileInputStream;
                        switch (i4) {
                            case 0:
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 1:
                                new Bundle();
                                int i22 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i32 = bundle.getInt("max_count");
                                StringBuilder m =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i22,
                                                "[",
                                                str,
                                                "] GET_ITEM_KEY, start: ",
                                                ", max: ");
                                m.append(i32);
                                LOG.i("BNRClientHelper", m.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 2:
                                new Bundle();
                                int i42 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i5 = bundle.getInt("max_count");
                                StringBuilder m2 =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i42,
                                                "[",
                                                str,
                                                "] GET_FILE_META, start: ",
                                                ", max: ");
                                m2.append(i5);
                                LOG.i("BNRClientHelper", m2.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 3:
                                Bundle bundle2 = new Bundle();
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("to_upload_list");
                                if (((ParcelFileDescriptor) bundle.getParcelable("file_descriptor"))
                                        == null) {
                                    LOG.i(
                                            "BNRClientHelper",
                                            "[" + str + "] BACKUP_ITEM, pfd is null");
                                    bundle2.putBoolean("is_success", false);
                                    return bundle2;
                                }
                                bundle.getLong("max_size");
                                if (stringArrayList != null) {
                                    StringBuilder m3 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] BACKUP_ITEM, toUploadList: ");
                                    m3.append(stringArrayList.size());
                                    LOG.i("BNRClientHelper", m3.toString());
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 4:
                                boolean z = bundle.getBoolean("is_success");
                                new Bundle();
                                LOG.i("BNRClientHelper", "[" + str + "] BACKUP_COMPLETE, " + z);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor)
                                                bundle.getParcelable("file_descriptor");
                                new Bundle();
                                ArrayList arrayList = new ArrayList();
                                new ArrayList();
                                LOG.i(
                                        "BNRClientHelper",
                                        "[" + str + "] RESTORE_ITEM, count: " + arrayList.size());
                                AssetFileDescriptor.AutoCloseInputStream autoCloseInputStream = 0;
                                try {
                                    try {
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor());
                                        } catch (IOException | JSONException e) {
                                            e = e;
                                            fileInputStream = null;
                                        } catch (Throwable th) {
                                            th = th;
                                            if (autoCloseInputStream != 0) {
                                                try {
                                                    autoCloseInputStream.close();
                                                } catch (IOException e2) {
                                                    e2.printStackTrace();
                                                }
                                            }
                                            throw th;
                                        }
                                        try {
                                            byte[] bArr =
                                                    new byte
                                                            [(int)
                                                                    parcelFileDescriptor
                                                                            .getStatSize()];
                                            fileInputStream.read(bArr);
                                            JSONArray jSONArray = new JSONArray(new String(bArr));
                                            for (int i6 = 0; i6 < jSONArray.length(); i6++) {
                                                JSONObject optJSONObject =
                                                        jSONArray.optJSONObject(i6);
                                                String optString =
                                                        optJSONObject.optString(
                                                                GoodSettingsContract.EXTRA_NAME
                                                                        .POLICY_KEY);
                                                BNRItem bNRItem =
                                                        new BNRItem(
                                                                optJSONObject.optLong(
                                                                        PhoneRestrictionPolicy
                                                                                .TIMESTAMP),
                                                                optString,
                                                                optJSONObject.optString("value"));
                                                LOG.d(
                                                        "BNRClientHelper",
                                                        "convertToBNRItems: "
                                                                + optString
                                                                + ", "
                                                                + bNRItem.timestamp);
                                                arrayList.add(bNRItem);
                                            }
                                            fileInputStream.close();
                                        } catch (IOException | JSONException e3) {
                                            e = e3;
                                            e.printStackTrace();
                                            if (fileInputStream != null) {
                                                fileInputStream.close();
                                            }
                                            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                    obj);
                                            throw null;
                                        }
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                } catch (Throwable th2) {
                                    th = th2;
                                    autoCloseInputStream = "[";
                                }
                        }
                    }
                });
        final int i5 = 3;
        hashMap.put(
                "backupItem",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        FileInputStream fileInputStream;
                        switch (i5) {
                            case 0:
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 1:
                                new Bundle();
                                int i22 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i32 = bundle.getInt("max_count");
                                StringBuilder m =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i22,
                                                "[",
                                                str,
                                                "] GET_ITEM_KEY, start: ",
                                                ", max: ");
                                m.append(i32);
                                LOG.i("BNRClientHelper", m.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 2:
                                new Bundle();
                                int i42 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i52 = bundle.getInt("max_count");
                                StringBuilder m2 =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i42,
                                                "[",
                                                str,
                                                "] GET_FILE_META, start: ",
                                                ", max: ");
                                m2.append(i52);
                                LOG.i("BNRClientHelper", m2.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 3:
                                Bundle bundle2 = new Bundle();
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("to_upload_list");
                                if (((ParcelFileDescriptor) bundle.getParcelable("file_descriptor"))
                                        == null) {
                                    LOG.i(
                                            "BNRClientHelper",
                                            "[" + str + "] BACKUP_ITEM, pfd is null");
                                    bundle2.putBoolean("is_success", false);
                                    return bundle2;
                                }
                                bundle.getLong("max_size");
                                if (stringArrayList != null) {
                                    StringBuilder m3 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] BACKUP_ITEM, toUploadList: ");
                                    m3.append(stringArrayList.size());
                                    LOG.i("BNRClientHelper", m3.toString());
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 4:
                                boolean z = bundle.getBoolean("is_success");
                                new Bundle();
                                LOG.i("BNRClientHelper", "[" + str + "] BACKUP_COMPLETE, " + z);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor)
                                                bundle.getParcelable("file_descriptor");
                                new Bundle();
                                ArrayList arrayList = new ArrayList();
                                new ArrayList();
                                LOG.i(
                                        "BNRClientHelper",
                                        "[" + str + "] RESTORE_ITEM, count: " + arrayList.size());
                                AssetFileDescriptor.AutoCloseInputStream autoCloseInputStream = 0;
                                try {
                                    try {
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor());
                                        } catch (IOException | JSONException e) {
                                            e = e;
                                            fileInputStream = null;
                                        } catch (Throwable th) {
                                            th = th;
                                            if (autoCloseInputStream != 0) {
                                                try {
                                                    autoCloseInputStream.close();
                                                } catch (IOException e2) {
                                                    e2.printStackTrace();
                                                }
                                            }
                                            throw th;
                                        }
                                        try {
                                            byte[] bArr =
                                                    new byte
                                                            [(int)
                                                                    parcelFileDescriptor
                                                                            .getStatSize()];
                                            fileInputStream.read(bArr);
                                            JSONArray jSONArray = new JSONArray(new String(bArr));
                                            for (int i6 = 0; i6 < jSONArray.length(); i6++) {
                                                JSONObject optJSONObject =
                                                        jSONArray.optJSONObject(i6);
                                                String optString =
                                                        optJSONObject.optString(
                                                                GoodSettingsContract.EXTRA_NAME
                                                                        .POLICY_KEY);
                                                BNRItem bNRItem =
                                                        new BNRItem(
                                                                optJSONObject.optLong(
                                                                        PhoneRestrictionPolicy
                                                                                .TIMESTAMP),
                                                                optString,
                                                                optJSONObject.optString("value"));
                                                LOG.d(
                                                        "BNRClientHelper",
                                                        "convertToBNRItems: "
                                                                + optString
                                                                + ", "
                                                                + bNRItem.timestamp);
                                                arrayList.add(bNRItem);
                                            }
                                            fileInputStream.close();
                                        } catch (IOException | JSONException e3) {
                                            e = e3;
                                            e.printStackTrace();
                                            if (fileInputStream != null) {
                                                fileInputStream.close();
                                            }
                                            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                    obj);
                                            throw null;
                                        }
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                } catch (Throwable th2) {
                                    th = th2;
                                    autoCloseInputStream = "[";
                                }
                        }
                    }
                });
        final int i6 = 3;
        hashMap.put(
                "getFilePath",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper.2
                    public final /* synthetic */ BNRClientHelper this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i6) {
                            case 0:
                                LOG.i("BNRClientHelper", "[" + str + "] BACKUP_PREPARE");
                                BNRClientHelper bNRClientHelper = this.this$0;
                                bNRClientHelper.OPERATION = "backup";
                                ((ArrayList) bNRClientHelper.processedKeyList).clear();
                                ((ArrayList) bNRClientHelper.restoreFileList).clear();
                                ((ArrayList) bNRClientHelper.downloadFileList).clear();
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 1:
                                Bundle bundle2 = new Bundle();
                                String str2 = bundle.getString("path") + "_scloud_dwnload";
                                BNRClientHelper bNRClientHelper2 = this.this$0;
                                BNRClientHelper.access$500(bNRClientHelper2, 1, str2);
                                LOG.d(
                                        "BNRClientHelper",
                                        "[" + str + "] RESTORE_FILE, " + bundle.getString("path"));
                                if (FileTool.fileCopy(
                                        bundle.getString("path"),
                                        bundle.getString("path") + "_scloud_origin")) {
                                    BNRClientHelper.access$500(
                                            bNRClientHelper2,
                                            2,
                                            bundle.getString("path") + "_scloud_origin");
                                    if (FileTool.fileCopy(
                                            bundle.getString("path") + "_scloud_dwnload",
                                            bundle.getString("path"))) {
                                        bundle2.putBoolean("is_success", true);
                                    } else {
                                        bundle2.putBoolean("is_success", false);
                                    }
                                } else {
                                    bundle2.putBoolean("is_success", false);
                                }
                                return bundle2;
                            case 2:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("BNRClientHelper", "[" + str + "] RESTORE_COMPLETE, " + z);
                                Bundle bundle3 = new Bundle();
                                bundle3.putBoolean("is_success", true);
                                BNRClientHelper bNRClientHelper3 = this.this$0;
                                if (z) {
                                    StringBuilder m =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[",
                                                    str,
                                                    "] RESTORE_COMPLETE, restoredKeyList size : ");
                                    m.append(
                                            ((ArrayList) bNRClientHelper3.processedKeyList).size());
                                    LOG.i("BNRClientHelper", m.toString());
                                    if (((ArrayList) bNRClientHelper3.processedKeyList).size()
                                            >= 0) {
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        ArrayList arrayList =
                                                (ArrayList) bNRClientHelper3.processedKeyList;
                                        throw null;
                                    }
                                    if (((ArrayList) bNRClientHelper3.restoreFileList).size() > 0) {
                                        Iterator it =
                                                ((ArrayList) bNRClientHelper3.restoreFileList)
                                                        .iterator();
                                        while (it.hasNext()) {
                                            String str3 = (String) it.next();
                                            File file = new File(str3);
                                            if (file.exists()) {
                                                StringBuilder m2 =
                                                        SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                .m(
                                                                        "[",
                                                                        str,
                                                                        "] clearPreRestoredData()"
                                                                            + " delete, name : ",
                                                                        str3,
                                                                        ", deleted : ");
                                                m2.append(file.delete());
                                                LOG.i("BNRClientHelper", m2.toString());
                                            }
                                        }
                                    }
                                    if (((ArrayList) bNRClientHelper3.downloadFileList).size()
                                            > 0) {
                                        Iterator it2 =
                                                ((ArrayList) bNRClientHelper3.downloadFileList)
                                                        .iterator();
                                        while (it2.hasNext()) {
                                            String str4 = (String) it2.next();
                                            File file2 = new File(str4);
                                            if (file2.exists()) {
                                                StringBuilder m3 =
                                                        SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                .m(
                                                                        "[",
                                                                        str,
                                                                        "] clearPreRestoredData()"
                                                                            + " delete, name : ",
                                                                        str4,
                                                                        ", deleted : ");
                                                m3.append(file2.delete());
                                                LOG.i("BNRClientHelper", m3.toString());
                                            }
                                        }
                                        ((ArrayList) bNRClientHelper3.downloadFileList).clear();
                                    }
                                } else {
                                    BNRClientHelper.access$300(bNRClientHelper3, obj);
                                    bundle3.putBoolean("is_success", true);
                                }
                                return bundle3;
                            case 3:
                                StringBuilder m4 =
                                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                "[", str, "] GET_FILE_PATH, ");
                                BNRClientHelper bNRClientHelper4 = this.this$0;
                                m4.append(bNRClientHelper4.OPERATION);
                                LOG.i("BNRClientHelper", m4.toString());
                                String string = bundle.getString("path");
                                boolean z2 = bundle.getBoolean("external");
                                new Bundle();
                                LOG.d(
                                        "BNRClientHelper",
                                        "[" + str + "] GET_FILE_PATH, " + string + ", " + z2);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                String str5 = bNRClientHelper4.OPERATION;
                                throw null;
                            default:
                                LOG.i("BNRClientHelper", "[" + str + "] RESTORE_PREPARE");
                                BNRClientHelper bNRClientHelper5 = this.this$0;
                                bNRClientHelper5.OPERATION = "restore";
                                BNRClientHelper.access$300(bNRClientHelper5, obj);
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i7 = 4;
        hashMap.put(
                "backupComplete",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        FileInputStream fileInputStream;
                        switch (i7) {
                            case 0:
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 1:
                                new Bundle();
                                int i22 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i32 = bundle.getInt("max_count");
                                StringBuilder m =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i22,
                                                "[",
                                                str,
                                                "] GET_ITEM_KEY, start: ",
                                                ", max: ");
                                m.append(i32);
                                LOG.i("BNRClientHelper", m.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 2:
                                new Bundle();
                                int i42 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i52 = bundle.getInt("max_count");
                                StringBuilder m2 =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i42,
                                                "[",
                                                str,
                                                "] GET_FILE_META, start: ",
                                                ", max: ");
                                m2.append(i52);
                                LOG.i("BNRClientHelper", m2.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 3:
                                Bundle bundle2 = new Bundle();
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("to_upload_list");
                                if (((ParcelFileDescriptor) bundle.getParcelable("file_descriptor"))
                                        == null) {
                                    LOG.i(
                                            "BNRClientHelper",
                                            "[" + str + "] BACKUP_ITEM, pfd is null");
                                    bundle2.putBoolean("is_success", false);
                                    return bundle2;
                                }
                                bundle.getLong("max_size");
                                if (stringArrayList != null) {
                                    StringBuilder m3 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] BACKUP_ITEM, toUploadList: ");
                                    m3.append(stringArrayList.size());
                                    LOG.i("BNRClientHelper", m3.toString());
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 4:
                                boolean z = bundle.getBoolean("is_success");
                                new Bundle();
                                LOG.i("BNRClientHelper", "[" + str + "] BACKUP_COMPLETE, " + z);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor)
                                                bundle.getParcelable("file_descriptor");
                                new Bundle();
                                ArrayList arrayList = new ArrayList();
                                new ArrayList();
                                LOG.i(
                                        "BNRClientHelper",
                                        "[" + str + "] RESTORE_ITEM, count: " + arrayList.size());
                                AssetFileDescriptor.AutoCloseInputStream autoCloseInputStream = 0;
                                try {
                                    try {
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor());
                                        } catch (IOException | JSONException e) {
                                            e = e;
                                            fileInputStream = null;
                                        } catch (Throwable th) {
                                            th = th;
                                            if (autoCloseInputStream != 0) {
                                                try {
                                                    autoCloseInputStream.close();
                                                } catch (IOException e2) {
                                                    e2.printStackTrace();
                                                }
                                            }
                                            throw th;
                                        }
                                        try {
                                            byte[] bArr =
                                                    new byte
                                                            [(int)
                                                                    parcelFileDescriptor
                                                                            .getStatSize()];
                                            fileInputStream.read(bArr);
                                            JSONArray jSONArray = new JSONArray(new String(bArr));
                                            for (int i62 = 0; i62 < jSONArray.length(); i62++) {
                                                JSONObject optJSONObject =
                                                        jSONArray.optJSONObject(i62);
                                                String optString =
                                                        optJSONObject.optString(
                                                                GoodSettingsContract.EXTRA_NAME
                                                                        .POLICY_KEY);
                                                BNRItem bNRItem =
                                                        new BNRItem(
                                                                optJSONObject.optLong(
                                                                        PhoneRestrictionPolicy
                                                                                .TIMESTAMP),
                                                                optString,
                                                                optJSONObject.optString("value"));
                                                LOG.d(
                                                        "BNRClientHelper",
                                                        "convertToBNRItems: "
                                                                + optString
                                                                + ", "
                                                                + bNRItem.timestamp);
                                                arrayList.add(bNRItem);
                                            }
                                            fileInputStream.close();
                                        } catch (IOException | JSONException e3) {
                                            e = e3;
                                            e.printStackTrace();
                                            if (fileInputStream != null) {
                                                fileInputStream.close();
                                            }
                                            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                    obj);
                                            throw null;
                                        }
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                } catch (Throwable th2) {
                                    th = th2;
                                    autoCloseInputStream = "[";
                                }
                        }
                    }
                });
        final int i8 = 4;
        hashMap.put(
                "restorePrepare",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper.2
                    public final /* synthetic */ BNRClientHelper this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i8) {
                            case 0:
                                LOG.i("BNRClientHelper", "[" + str + "] BACKUP_PREPARE");
                                BNRClientHelper bNRClientHelper = this.this$0;
                                bNRClientHelper.OPERATION = "backup";
                                ((ArrayList) bNRClientHelper.processedKeyList).clear();
                                ((ArrayList) bNRClientHelper.restoreFileList).clear();
                                ((ArrayList) bNRClientHelper.downloadFileList).clear();
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 1:
                                Bundle bundle2 = new Bundle();
                                String str2 = bundle.getString("path") + "_scloud_dwnload";
                                BNRClientHelper bNRClientHelper2 = this.this$0;
                                BNRClientHelper.access$500(bNRClientHelper2, 1, str2);
                                LOG.d(
                                        "BNRClientHelper",
                                        "[" + str + "] RESTORE_FILE, " + bundle.getString("path"));
                                if (FileTool.fileCopy(
                                        bundle.getString("path"),
                                        bundle.getString("path") + "_scloud_origin")) {
                                    BNRClientHelper.access$500(
                                            bNRClientHelper2,
                                            2,
                                            bundle.getString("path") + "_scloud_origin");
                                    if (FileTool.fileCopy(
                                            bundle.getString("path") + "_scloud_dwnload",
                                            bundle.getString("path"))) {
                                        bundle2.putBoolean("is_success", true);
                                    } else {
                                        bundle2.putBoolean("is_success", false);
                                    }
                                } else {
                                    bundle2.putBoolean("is_success", false);
                                }
                                return bundle2;
                            case 2:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("BNRClientHelper", "[" + str + "] RESTORE_COMPLETE, " + z);
                                Bundle bundle3 = new Bundle();
                                bundle3.putBoolean("is_success", true);
                                BNRClientHelper bNRClientHelper3 = this.this$0;
                                if (z) {
                                    StringBuilder m =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[",
                                                    str,
                                                    "] RESTORE_COMPLETE, restoredKeyList size : ");
                                    m.append(
                                            ((ArrayList) bNRClientHelper3.processedKeyList).size());
                                    LOG.i("BNRClientHelper", m.toString());
                                    if (((ArrayList) bNRClientHelper3.processedKeyList).size()
                                            >= 0) {
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        ArrayList arrayList =
                                                (ArrayList) bNRClientHelper3.processedKeyList;
                                        throw null;
                                    }
                                    if (((ArrayList) bNRClientHelper3.restoreFileList).size() > 0) {
                                        Iterator it =
                                                ((ArrayList) bNRClientHelper3.restoreFileList)
                                                        .iterator();
                                        while (it.hasNext()) {
                                            String str3 = (String) it.next();
                                            File file = new File(str3);
                                            if (file.exists()) {
                                                StringBuilder m2 =
                                                        SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                .m(
                                                                        "[",
                                                                        str,
                                                                        "] clearPreRestoredData()"
                                                                            + " delete, name : ",
                                                                        str3,
                                                                        ", deleted : ");
                                                m2.append(file.delete());
                                                LOG.i("BNRClientHelper", m2.toString());
                                            }
                                        }
                                    }
                                    if (((ArrayList) bNRClientHelper3.downloadFileList).size()
                                            > 0) {
                                        Iterator it2 =
                                                ((ArrayList) bNRClientHelper3.downloadFileList)
                                                        .iterator();
                                        while (it2.hasNext()) {
                                            String str4 = (String) it2.next();
                                            File file2 = new File(str4);
                                            if (file2.exists()) {
                                                StringBuilder m3 =
                                                        SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                .m(
                                                                        "[",
                                                                        str,
                                                                        "] clearPreRestoredData()"
                                                                            + " delete, name : ",
                                                                        str4,
                                                                        ", deleted : ");
                                                m3.append(file2.delete());
                                                LOG.i("BNRClientHelper", m3.toString());
                                            }
                                        }
                                        ((ArrayList) bNRClientHelper3.downloadFileList).clear();
                                    }
                                } else {
                                    BNRClientHelper.access$300(bNRClientHelper3, obj);
                                    bundle3.putBoolean("is_success", true);
                                }
                                return bundle3;
                            case 3:
                                StringBuilder m4 =
                                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                "[", str, "] GET_FILE_PATH, ");
                                BNRClientHelper bNRClientHelper4 = this.this$0;
                                m4.append(bNRClientHelper4.OPERATION);
                                LOG.i("BNRClientHelper", m4.toString());
                                String string = bundle.getString("path");
                                boolean z2 = bundle.getBoolean("external");
                                new Bundle();
                                LOG.d(
                                        "BNRClientHelper",
                                        "[" + str + "] GET_FILE_PATH, " + string + ", " + z2);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                String str5 = bNRClientHelper4.OPERATION;
                                throw null;
                            default:
                                LOG.i("BNRClientHelper", "[" + str + "] RESTORE_PREPARE");
                                BNRClientHelper bNRClientHelper5 = this.this$0;
                                bNRClientHelper5.OPERATION = "restore";
                                BNRClientHelper.access$300(bNRClientHelper5, obj);
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i9 = 5;
        hashMap.put(
                "restoreItem",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        FileInputStream fileInputStream;
                        switch (i9) {
                            case 0:
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 1:
                                new Bundle();
                                int i22 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i32 = bundle.getInt("max_count");
                                StringBuilder m =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i22,
                                                "[",
                                                str,
                                                "] GET_ITEM_KEY, start: ",
                                                ", max: ");
                                m.append(i32);
                                LOG.i("BNRClientHelper", m.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 2:
                                new Bundle();
                                int i42 =
                                        bundle.getInt(
                                                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                                int i52 = bundle.getInt("max_count");
                                StringBuilder m2 =
                                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                                i42,
                                                "[",
                                                str,
                                                "] GET_FILE_META, start: ",
                                                ", max: ");
                                m2.append(i52);
                                LOG.i("BNRClientHelper", m2.toString());
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 3:
                                Bundle bundle2 = new Bundle();
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("to_upload_list");
                                if (((ParcelFileDescriptor) bundle.getParcelable("file_descriptor"))
                                        == null) {
                                    LOG.i(
                                            "BNRClientHelper",
                                            "[" + str + "] BACKUP_ITEM, pfd is null");
                                    bundle2.putBoolean("is_success", false);
                                    return bundle2;
                                }
                                bundle.getLong("max_size");
                                if (stringArrayList != null) {
                                    StringBuilder m3 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] BACKUP_ITEM, toUploadList: ");
                                    m3.append(stringArrayList.size());
                                    LOG.i("BNRClientHelper", m3.toString());
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 4:
                                boolean z = bundle.getBoolean("is_success");
                                new Bundle();
                                LOG.i("BNRClientHelper", "[" + str + "] BACKUP_COMPLETE, " + z);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor)
                                                bundle.getParcelable("file_descriptor");
                                new Bundle();
                                ArrayList arrayList = new ArrayList();
                                new ArrayList();
                                LOG.i(
                                        "BNRClientHelper",
                                        "[" + str + "] RESTORE_ITEM, count: " + arrayList.size());
                                AssetFileDescriptor.AutoCloseInputStream autoCloseInputStream = 0;
                                try {
                                    try {
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor());
                                        } catch (IOException | JSONException e) {
                                            e = e;
                                            fileInputStream = null;
                                        } catch (Throwable th) {
                                            th = th;
                                            if (autoCloseInputStream != 0) {
                                                try {
                                                    autoCloseInputStream.close();
                                                } catch (IOException e2) {
                                                    e2.printStackTrace();
                                                }
                                            }
                                            throw th;
                                        }
                                        try {
                                            byte[] bArr =
                                                    new byte
                                                            [(int)
                                                                    parcelFileDescriptor
                                                                            .getStatSize()];
                                            fileInputStream.read(bArr);
                                            JSONArray jSONArray = new JSONArray(new String(bArr));
                                            for (int i62 = 0; i62 < jSONArray.length(); i62++) {
                                                JSONObject optJSONObject =
                                                        jSONArray.optJSONObject(i62);
                                                String optString =
                                                        optJSONObject.optString(
                                                                GoodSettingsContract.EXTRA_NAME
                                                                        .POLICY_KEY);
                                                BNRItem bNRItem =
                                                        new BNRItem(
                                                                optJSONObject.optLong(
                                                                        PhoneRestrictionPolicy
                                                                                .TIMESTAMP),
                                                                optString,
                                                                optJSONObject.optString("value"));
                                                LOG.d(
                                                        "BNRClientHelper",
                                                        "convertToBNRItems: "
                                                                + optString
                                                                + ", "
                                                                + bNRItem.timestamp);
                                                arrayList.add(bNRItem);
                                            }
                                            fileInputStream.close();
                                        } catch (IOException | JSONException e3) {
                                            e = e3;
                                            e.printStackTrace();
                                            if (fileInputStream != null) {
                                                fileInputStream.close();
                                            }
                                            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                    obj);
                                            throw null;
                                        }
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                } catch (Throwable th2) {
                                    th = th2;
                                    autoCloseInputStream = "[";
                                }
                        }
                    }
                });
        final int i10 = 1;
        hashMap.put(
                "restoreFile",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper.2
                    public final /* synthetic */ BNRClientHelper this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i10) {
                            case 0:
                                LOG.i("BNRClientHelper", "[" + str + "] BACKUP_PREPARE");
                                BNRClientHelper bNRClientHelper = this.this$0;
                                bNRClientHelper.OPERATION = "backup";
                                ((ArrayList) bNRClientHelper.processedKeyList).clear();
                                ((ArrayList) bNRClientHelper.restoreFileList).clear();
                                ((ArrayList) bNRClientHelper.downloadFileList).clear();
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 1:
                                Bundle bundle2 = new Bundle();
                                String str2 = bundle.getString("path") + "_scloud_dwnload";
                                BNRClientHelper bNRClientHelper2 = this.this$0;
                                BNRClientHelper.access$500(bNRClientHelper2, 1, str2);
                                LOG.d(
                                        "BNRClientHelper",
                                        "[" + str + "] RESTORE_FILE, " + bundle.getString("path"));
                                if (FileTool.fileCopy(
                                        bundle.getString("path"),
                                        bundle.getString("path") + "_scloud_origin")) {
                                    BNRClientHelper.access$500(
                                            bNRClientHelper2,
                                            2,
                                            bundle.getString("path") + "_scloud_origin");
                                    if (FileTool.fileCopy(
                                            bundle.getString("path") + "_scloud_dwnload",
                                            bundle.getString("path"))) {
                                        bundle2.putBoolean("is_success", true);
                                    } else {
                                        bundle2.putBoolean("is_success", false);
                                    }
                                } else {
                                    bundle2.putBoolean("is_success", false);
                                }
                                return bundle2;
                            case 2:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("BNRClientHelper", "[" + str + "] RESTORE_COMPLETE, " + z);
                                Bundle bundle3 = new Bundle();
                                bundle3.putBoolean("is_success", true);
                                BNRClientHelper bNRClientHelper3 = this.this$0;
                                if (z) {
                                    StringBuilder m =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[",
                                                    str,
                                                    "] RESTORE_COMPLETE, restoredKeyList size : ");
                                    m.append(
                                            ((ArrayList) bNRClientHelper3.processedKeyList).size());
                                    LOG.i("BNRClientHelper", m.toString());
                                    if (((ArrayList) bNRClientHelper3.processedKeyList).size()
                                            >= 0) {
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        ArrayList arrayList =
                                                (ArrayList) bNRClientHelper3.processedKeyList;
                                        throw null;
                                    }
                                    if (((ArrayList) bNRClientHelper3.restoreFileList).size() > 0) {
                                        Iterator it =
                                                ((ArrayList) bNRClientHelper3.restoreFileList)
                                                        .iterator();
                                        while (it.hasNext()) {
                                            String str3 = (String) it.next();
                                            File file = new File(str3);
                                            if (file.exists()) {
                                                StringBuilder m2 =
                                                        SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                .m(
                                                                        "[",
                                                                        str,
                                                                        "] clearPreRestoredData()"
                                                                            + " delete, name : ",
                                                                        str3,
                                                                        ", deleted : ");
                                                m2.append(file.delete());
                                                LOG.i("BNRClientHelper", m2.toString());
                                            }
                                        }
                                    }
                                    if (((ArrayList) bNRClientHelper3.downloadFileList).size()
                                            > 0) {
                                        Iterator it2 =
                                                ((ArrayList) bNRClientHelper3.downloadFileList)
                                                        .iterator();
                                        while (it2.hasNext()) {
                                            String str4 = (String) it2.next();
                                            File file2 = new File(str4);
                                            if (file2.exists()) {
                                                StringBuilder m3 =
                                                        SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                .m(
                                                                        "[",
                                                                        str,
                                                                        "] clearPreRestoredData()"
                                                                            + " delete, name : ",
                                                                        str4,
                                                                        ", deleted : ");
                                                m3.append(file2.delete());
                                                LOG.i("BNRClientHelper", m3.toString());
                                            }
                                        }
                                        ((ArrayList) bNRClientHelper3.downloadFileList).clear();
                                    }
                                } else {
                                    BNRClientHelper.access$300(bNRClientHelper3, obj);
                                    bundle3.putBoolean("is_success", true);
                                }
                                return bundle3;
                            case 3:
                                StringBuilder m4 =
                                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                "[", str, "] GET_FILE_PATH, ");
                                BNRClientHelper bNRClientHelper4 = this.this$0;
                                m4.append(bNRClientHelper4.OPERATION);
                                LOG.i("BNRClientHelper", m4.toString());
                                String string = bundle.getString("path");
                                boolean z2 = bundle.getBoolean("external");
                                new Bundle();
                                LOG.d(
                                        "BNRClientHelper",
                                        "[" + str + "] GET_FILE_PATH, " + string + ", " + z2);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                String str5 = bNRClientHelper4.OPERATION;
                                throw null;
                            default:
                                LOG.i("BNRClientHelper", "[" + str + "] RESTORE_PREPARE");
                                BNRClientHelper bNRClientHelper5 = this.this$0;
                                bNRClientHelper5.OPERATION = "restore";
                                BNRClientHelper.access$300(bNRClientHelper5, obj);
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i11 = 2;
        hashMap.put(
                "restoreComplete",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.bnr.BNRClientHelper.2
                    public final /* synthetic */ BNRClientHelper this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i11) {
                            case 0:
                                LOG.i("BNRClientHelper", "[" + str + "] BACKUP_PREPARE");
                                BNRClientHelper bNRClientHelper = this.this$0;
                                bNRClientHelper.OPERATION = "backup";
                                ((ArrayList) bNRClientHelper.processedKeyList).clear();
                                ((ArrayList) bNRClientHelper.restoreFileList).clear();
                                ((ArrayList) bNRClientHelper.downloadFileList).clear();
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 1:
                                Bundle bundle2 = new Bundle();
                                String str2 = bundle.getString("path") + "_scloud_dwnload";
                                BNRClientHelper bNRClientHelper2 = this.this$0;
                                BNRClientHelper.access$500(bNRClientHelper2, 1, str2);
                                LOG.d(
                                        "BNRClientHelper",
                                        "[" + str + "] RESTORE_FILE, " + bundle.getString("path"));
                                if (FileTool.fileCopy(
                                        bundle.getString("path"),
                                        bundle.getString("path") + "_scloud_origin")) {
                                    BNRClientHelper.access$500(
                                            bNRClientHelper2,
                                            2,
                                            bundle.getString("path") + "_scloud_origin");
                                    if (FileTool.fileCopy(
                                            bundle.getString("path") + "_scloud_dwnload",
                                            bundle.getString("path"))) {
                                        bundle2.putBoolean("is_success", true);
                                    } else {
                                        bundle2.putBoolean("is_success", false);
                                    }
                                } else {
                                    bundle2.putBoolean("is_success", false);
                                }
                                return bundle2;
                            case 2:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("BNRClientHelper", "[" + str + "] RESTORE_COMPLETE, " + z);
                                Bundle bundle3 = new Bundle();
                                bundle3.putBoolean("is_success", true);
                                BNRClientHelper bNRClientHelper3 = this.this$0;
                                if (z) {
                                    StringBuilder m =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[",
                                                    str,
                                                    "] RESTORE_COMPLETE, restoredKeyList size : ");
                                    m.append(
                                            ((ArrayList) bNRClientHelper3.processedKeyList).size());
                                    LOG.i("BNRClientHelper", m.toString());
                                    if (((ArrayList) bNRClientHelper3.processedKeyList).size()
                                            >= 0) {
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        ArrayList arrayList =
                                                (ArrayList) bNRClientHelper3.processedKeyList;
                                        throw null;
                                    }
                                    if (((ArrayList) bNRClientHelper3.restoreFileList).size() > 0) {
                                        Iterator it =
                                                ((ArrayList) bNRClientHelper3.restoreFileList)
                                                        .iterator();
                                        while (it.hasNext()) {
                                            String str3 = (String) it.next();
                                            File file = new File(str3);
                                            if (file.exists()) {
                                                StringBuilder m2 =
                                                        SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                .m(
                                                                        "[",
                                                                        str,
                                                                        "] clearPreRestoredData()"
                                                                            + " delete, name : ",
                                                                        str3,
                                                                        ", deleted : ");
                                                m2.append(file.delete());
                                                LOG.i("BNRClientHelper", m2.toString());
                                            }
                                        }
                                    }
                                    if (((ArrayList) bNRClientHelper3.downloadFileList).size()
                                            > 0) {
                                        Iterator it2 =
                                                ((ArrayList) bNRClientHelper3.downloadFileList)
                                                        .iterator();
                                        while (it2.hasNext()) {
                                            String str4 = (String) it2.next();
                                            File file2 = new File(str4);
                                            if (file2.exists()) {
                                                StringBuilder m3 =
                                                        SeslRoundedCorner$$ExternalSyntheticOutline0
                                                                .m(
                                                                        "[",
                                                                        str,
                                                                        "] clearPreRestoredData()"
                                                                            + " delete, name : ",
                                                                        str4,
                                                                        ", deleted : ");
                                                m3.append(file2.delete());
                                                LOG.i("BNRClientHelper", m3.toString());
                                            }
                                        }
                                        ((ArrayList) bNRClientHelper3.downloadFileList).clear();
                                    }
                                } else {
                                    BNRClientHelper.access$300(bNRClientHelper3, obj);
                                    bundle3.putBoolean("is_success", true);
                                }
                                return bundle3;
                            case 3:
                                StringBuilder m4 =
                                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                "[", str, "] GET_FILE_PATH, ");
                                BNRClientHelper bNRClientHelper4 = this.this$0;
                                m4.append(bNRClientHelper4.OPERATION);
                                LOG.i("BNRClientHelper", m4.toString());
                                String string = bundle.getString("path");
                                boolean z2 = bundle.getBoolean("external");
                                new Bundle();
                                LOG.d(
                                        "BNRClientHelper",
                                        "[" + str + "] GET_FILE_PATH, " + string + ", " + z2);
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                String str5 = bNRClientHelper4.OPERATION;
                                throw null;
                            default:
                                LOG.i("BNRClientHelper", "[" + str + "] RESTORE_PREPARE");
                                BNRClientHelper bNRClientHelper5 = this.this$0;
                                bNRClientHelper5.OPERATION = "restore";
                                BNRClientHelper.access$300(bNRClientHelper5, obj);
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
    }

    public static void access$300(BNRClientHelper bNRClientHelper, Object obj) {
        if (((ArrayList) bNRClientHelper.processedKeyList).size() > 0) {
            LOG.i(
                    "BNRClientHelper",
                    "remove restored data in previous failed restoring.. - "
                            + ((ArrayList) bNRClientHelper.processedKeyList).size());
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
            ArrayList arrayList = (ArrayList) bNRClientHelper.processedKeyList;
            throw null;
        }
        if (((ArrayList) bNRClientHelper.restoreFileList).size() > 0) {
            LOG.i(
                    "BNRClientHelper",
                    "remove restored files in previous failed restoring.. - "
                            + ((ArrayList) bNRClientHelper.restoreFileList).size());
            Iterator it = ((ArrayList) bNRClientHelper.restoreFileList).iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                FileTool.fileCopy(str + "_scloud_origin", str);
            }
            ((ArrayList) bNRClientHelper.restoreFileList).clear();
        }
        if (((ArrayList) bNRClientHelper.downloadFileList).size() > 0) {
            Iterator it2 = ((ArrayList) bNRClientHelper.downloadFileList).iterator();
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                File file = new File(str2);
                if (file.exists()) {
                    StringBuilder m =
                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                    "clearPreRestoredData() delete, name : ", str2, ", deleted : ");
                    m.append(file.delete());
                    LOG.i("BNRClientHelper", m.toString());
                }
            }
            ((ArrayList) bNRClientHelper.downloadFileList).clear();
        }
    }

    public static void access$500(BNRClientHelper bNRClientHelper, int i, String str) {
        if (i == 0) {
            ((ArrayList) bNRClientHelper.processedKeyList).add(str);
            return;
        }
        if (i == 1) {
            ((ArrayList) bNRClientHelper.downloadFileList).add(str);
        } else if (i != 2) {
            bNRClientHelper.getClass();
        } else {
            ((ArrayList) bNRClientHelper.restoreFileList).add(str);
        }
    }

    @Override // com.samsung.android.scloud.oem.lib.common.IClientHelper
    public final Object getClient() {
        return null;
    }

    @Override // com.samsung.android.scloud.oem.lib.common.IClientHelper
    public final IServiceHandler getServiceHandler(String str) {
        return (IServiceHandler) ((HashMap) this.serviceHandlerMap).get(str);
    }
}
