package com.samsung.android.scloud.oem.lib.backup.file;

import android.content.Context;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.JsonWriter;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

import com.samsung.android.scloud.lib.storage.internal.BackupClientManager$6$$ExternalSyntheticOutline0;
import com.samsung.android.scloud.oem.lib.LOG;
import com.samsung.android.scloud.oem.lib.backup.BackupMetaManager;
import com.samsung.android.scloud.oem.lib.common.IClientHelper;
import com.samsung.android.scloud.oem.lib.common.IServiceHandler;
import com.samsung.android.scloud.oem.lib.utils.SCloudUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FileClientManager extends IClientHelper {
    public final ArrayList processedKeyList;
    public final Map serviceHandlerMap;

    public FileClientManager() {
        HashMap hashMap = new HashMap();
        this.serviceHandlerMap = hashMap;
        this.processedKeyList = new ArrayList();
        new ArrayList();
        final int i = 0;
        hashMap.put(
                "backupPrepare",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i2 = 7;
        hashMap.put(
                "getFileMeta",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i2) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i3 = 8;
        hashMap.put(
                "backupComplete",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i3) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i4 = 9;
        hashMap.put(
                "restorePrepare",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i4) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i5 = 10;
        hashMap.put(
                "transactionBegin",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i5) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i6 = 11;
        hashMap.put(
                "getFileList",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i6) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i7 = 0;
        hashMap.put(
                "getLargeFileList",
                new IServiceHandler(
                        this) { // from class:
                                // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.7
                    public final /* synthetic */ FileClientManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i7) {
                            case 0:
                                if (((ParcelFileDescriptor) bundle.getParcelable("pfd")) != null) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                LOG.i(
                                        "FileClientManager",
                                        "[" + str + "] GET_LARGE_FILE_LIST false");
                                this.this$0.getClass();
                                Bundle bundle2 = new Bundle();
                                bundle2.putBoolean("is_success", false);
                                return bundle2;
                            default:
                                boolean z = bundle.getBoolean("is_success");
                                new Bundle();
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                FileClientManager fileClientManager = this.this$0;
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    ArrayList arrayList = fileClientManager.processedKeyList;
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                ArrayList arrayList2 = fileClientManager.processedKeyList;
                                throw null;
                        }
                    }
                });
        final int i8 = 12;
        hashMap.put(
                "getLargeHashList",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i8) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i9 = 13;
        hashMap.put(
                "restoreFile",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i9) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i10 = 1;
        hashMap.put(
                "transactionEnd",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i10) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i11 = 1;
        hashMap.put(
                "restoreComplete",
                new IServiceHandler(
                        this) { // from class:
                                // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.7
                    public final /* synthetic */ FileClientManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i11) {
                            case 0:
                                if (((ParcelFileDescriptor) bundle.getParcelable("pfd")) != null) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                LOG.i(
                                        "FileClientManager",
                                        "[" + str + "] GET_LARGE_FILE_LIST false");
                                this.this$0.getClass();
                                Bundle bundle2 = new Bundle();
                                bundle2.putBoolean("is_success", false);
                                return bundle2;
                            default:
                                boolean z = bundle.getBoolean("is_success");
                                new Bundle();
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                FileClientManager fileClientManager = this.this$0;
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    ArrayList arrayList = fileClientManager.processedKeyList;
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                ArrayList arrayList2 = fileClientManager.processedKeyList;
                                throw null;
                        }
                    }
                });
        final int i12 = 2;
        hashMap.put(
                "deleteRestoreFile",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i12) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i13 = 3;
        hashMap.put(
                "checkAndUpdateReuseDB",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i13) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i14 = 4;
        hashMap.put(
                "completeFile",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i14) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i15 = 5;
        hashMap.put(
                "clearReuseFileDB",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i15) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
        final int i16 = 6;
        hashMap.put(
                "requestCancel",
                new IServiceHandler() { // from class:
                                        // com.samsung.android.scloud.oem.lib.backup.file.FileClientManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        switch (i16) {
                            case 0:
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                throw null;
                            case 1:
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] TRANSACTION_END", "FileClientManager");
                                try {
                                    String string = bundle.getString("record");
                                    String string2 = bundle.getString("id");
                                    if (string != null) {
                                        new JSONObject(string);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string2);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_END: Exception",
                                            e);
                                    return m;
                                }
                            case 2:
                                ArrayList<String> stringArrayList =
                                        bundle.getStringArrayList("path_list");
                                if (stringArrayList != null) {
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "[", str, "] DELETE_RESTORE_FILE: ");
                                    m2.append(stringArrayList.size());
                                    LOG.i("FileClientManager", m2.toString());
                                    Iterator<String> it = stringArrayList.iterator();
                                    while (it.hasNext()) {
                                        new File(context.getFilesDir(), it.next()).delete();
                                    }
                                }
                                return new Bundle();
                            case 3:
                                Bundle m3 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] CHECK_AND_UPDATE_REUSE_DB",
                                                "FileClientManager");
                                m3.putBoolean("is_success", true);
                                return m3;
                            case 4:
                                Bundle m4 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] COMPLETE_FILE", "FileClientManager");
                                m4.putBoolean("is_success", true);
                                return m4;
                            case 5:
                                return BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                        "[", str, "] CLEAR_REUSE_FILE_DB", "FileClientManager");
                            case 6:
                                Bundle m5 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] REQUEST_CANCEL", "FileClientManager");
                                BackupMetaManager.getInstance(context).setCanceled(str, true);
                                return m5;
                            case 7:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_META");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("meta_pfd");
                                Bundle bundle2 = new Bundle();
                                if (parcelFileDescriptor == null) {
                                    LOG.i(
                                            "FileClientManager",
                                            "[" + str + "] GET_FILE_META: meta_pfd is null");
                                    return bundle2;
                                }
                                FileClientHelper fileClientHelper = null;
                                try {
                                    JsonWriter jsonWriter =
                                            new JsonWriter(
                                                    new FileWriter(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()));
                                    FileClientHelper fileClientHelper2 = new FileClientHelper();
                                    fileClientHelper2.jsonWriter = jsonWriter;
                                    fileClientHelper2.sourceKey = str;
                                    try {
                                        LOG.d("FileClientHelper", "[" + str + "] open");
                                        try {
                                            jsonWriter.beginArray();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                                obj);
                                        throw null;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileClientHelper = fileClientHelper2;
                                        if (fileClientHelper != null) {
                                            LOG.d(
                                                    "FileClientHelper",
                                                    "[" + fileClientHelper.sourceKey + "] release");
                                            try {
                                                JsonWriter jsonWriter2 =
                                                        fileClientHelper.jsonWriter;
                                                if (jsonWriter2 != null) {
                                                    jsonWriter2.endArray();
                                                    fileClientHelper.jsonWriter.flush();
                                                    fileClientHelper.jsonWriter.close();
                                                }
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        try {
                                            parcelFileDescriptor.close();
                                            throw th;
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            case 8:
                                boolean z = bundle.getBoolean("is_success");
                                LOG.i("FileClientManager", "[" + str + "] BACKUP_COMPLETE: " + z);
                                BackupMetaManager.getInstance(context).setCanceled(str, false);
                                if (z) {
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    throw null;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 9:
                                LOG.i("FileClientManager", "[" + str + "] RESTORE_PREPARE");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 10:
                                Bundle m6 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[",
                                                str,
                                                "] TRANSACTION_BEGIN",
                                                "FileClientManager");
                                try {
                                    String string3 = bundle.getString("record");
                                    String string4 = bundle.getString("id");
                                    if (string3 != null) {
                                        new JSONObject(string3);
                                    }
                                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                    SCloudUtil.makeSHA1Hash(string4);
                                    throw null;
                                } catch (UnsupportedEncodingException
                                        | NoSuchAlgorithmException
                                        | JSONException e5) {
                                    LOG.e(
                                            "FileClientManager",
                                            "[" + str + "] TRANSACTION_BEGIN: Exception",
                                            e5);
                                    m6.putBoolean("is_success", false);
                                    return m6;
                                }
                            case 11:
                                LOG.i("FileClientManager", "[" + str + "] GET_FILE_LIST");
                                new Bundle();
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            case 12:
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                            default:
                                Bundle m7 =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] RESTORE_FILE", "FileClientManager");
                                if (bundle.getString("path") == null) {
                                    return m7;
                                }
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                                throw null;
                        }
                    }
                });
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
