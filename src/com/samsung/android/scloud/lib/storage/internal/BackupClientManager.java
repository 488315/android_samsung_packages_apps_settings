package com.samsung.android.scloud.lib.storage.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.os.ParcelFileDescriptor;
import android.util.JsonReader;
import android.util.JsonWriter;

import com.samsung.android.scloud.lib.storage.data.Body;
import com.samsung.android.scloud.lib.storage.data.Header;
import com.samsung.android.scloud.lib.storage.data.RecordDataSet;
import com.samsung.android.scloud.lib.storage.internal.BackupRecordDataHelper.CallbackHandler;
import com.samsung.android.scloud.oem.lib.LOG;
import com.samsung.android.scloud.oem.lib.common.IServiceHandler;
import com.samsung.android.settings.scloud.SCloudWifiConfigSyncImpl;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BackupClientManager extends CommonClientManager {
    public final SCloudWifiConfigSyncImpl recordDataClient;
    public final BackupRecordDataHelper recordDataHelper;
    public List recordDataSetList = new ArrayList();

    public BackupClientManager(
            Context context, final SCloudWifiConfigSyncImpl sCloudWifiConfigSyncImpl) {
        this.recordDataClient = sCloudWifiConfigSyncImpl;
        final BackupRecordDataHelper backupRecordDataHelper = new BackupRecordDataHelper();
        backupRecordDataHelper.fileDownloadResult = "NONE";
        backupRecordDataHelper.appInstallationResult = "NONE";
        backupRecordDataHelper.countDownLatch = new CountDownLatch(0);
        final Messenger[] messengerArr = {null};
        Handler handler = new Handler(context.getMainLooper());
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        handler.post(
                new Runnable() { // from class:
                                 // com.samsung.android.scloud.lib.storage.internal.BackupRecordDataHelper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BackupRecordDataHelper backupRecordDataHelper2 =
                                BackupRecordDataHelper.this;
                        Messenger[] messengerArr2 = messengerArr;
                        CountDownLatch countDownLatch2 = countDownLatch;
                        backupRecordDataHelper2.getClass();
                        messengerArr2[0] =
                                new Messenger(backupRecordDataHelper2.new CallbackHandler());
                        countDownLatch2.countDown();
                    }
                });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Messenger messenger = messengerArr[0];
        this.recordDataHelper = backupRecordDataHelper;
        final int i = 0;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "backupPrepare",
                        new IServiceHandler(this) { // from class:
                            // com.samsung.android.scloud.lib.storage.internal.BackupClientManager.1
                            public final /* synthetic */ BackupClientManager this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                ArrayList arrayList;
                                FileInputStream fileInputStream;
                                ObjectInputStream objectInputStream;
                                boolean z;
                                switch (i) {
                                    case 0:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_PREPARE");
                                        boolean z2 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Backup")
                                                                .getInt("result");
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z2);
                                    case 1:
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor) bundle.getParcelable("pfd");
                                        HashMap hashMap = new HashMap();
                                        Iterator it =
                                                ((ArrayList)
                                                                sCloudWifiConfigSyncImpl.getHeader(
                                                                        context2, null))
                                                        .iterator();
                                        while (it.hasNext()) {
                                            Header header = (Header) it.next();
                                            hashMap.put(
                                                    header.recordId,
                                                    Long.valueOf(header.timeStamp));
                                        }
                                        boolean z3 =
                                                parcelFileDescriptor != null
                                                        && IpcDataHandler.write(
                                                                parcelFileDescriptor, hashMap);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] GET_KEY_AND_DATE " + z3);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z3);
                                    case 2:
                                        LOG.i("BackupClientManager", "[" + str + "] BACKUP");
                                        ParcelFileDescriptor parcelFileDescriptor2 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        this.this$0.getClass();
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            ((ParcelFileDescriptor)
                                                                            bundle.getParcelable(
                                                                                    "upload_key_list_pfd"))
                                                                    .getFileDescriptor());
                                            try {
                                                objectInputStream =
                                                        new ObjectInputStream(fileInputStream);
                                            } catch (Throwable th) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (Throwable th2) {
                                                    th.addSuppressed(th2);
                                                }
                                                throw th;
                                            }
                                        } catch (IOException | ClassNotFoundException unused) {
                                            LOG.e(
                                                    "CommonClientManager",
                                                    "[" + str + "] can't read uploadKeyList",
                                                    null);
                                            arrayList = new ArrayList();
                                        }
                                        try {
                                            arrayList = (ArrayList) objectInputStream.readObject();
                                            LOG.i(
                                                    "CommonClientManager",
                                                    "["
                                                            + str
                                                            + "] getUploadKeyList file : "
                                                            + arrayList.size());
                                            objectInputStream.close();
                                            fileInputStream.close();
                                            Bundle bundle2 = new Bundle();
                                            if (parcelFileDescriptor2 == null) {
                                                LOG.i(
                                                        "BackupClientManager",
                                                        "[" + str + "] BACKUP: meta_pfd is null");
                                            } else {
                                                BackupRecordWriter backupRecordWriter =
                                                        new BackupRecordWriter(
                                                                parcelFileDescriptor2);
                                                try {
                                                    LOG.i(
                                                            "BackupClientManager",
                                                            "BACKUP " + arrayList.size());
                                                    JsonWriter jsonWriter =
                                                            backupRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    Iterator it2 =
                                                            ((ArrayList)
                                                                            sCloudWifiConfigSyncImpl
                                                                                    .getData(
                                                                                            context2,
                                                                                            arrayList))
                                                                    .iterator();
                                                    while (it2.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it2.next();
                                                        Header header2 = recordDataSet.header;
                                                        Body body = recordDataSet.body;
                                                        backupRecordWriter.addRecordAndFiles(
                                                                header2.recordId,
                                                                String.valueOf(header2.timeStamp),
                                                                body.itemData.toString(),
                                                                body.fileList);
                                                    }
                                                    bundle2.putBoolean("is_success", true);
                                                } finally {
                                                    try {
                                                        backupRecordWriter.end();
                                                        backupRecordWriter.close();
                                                    } catch (Exception e3) {
                                                        e3.printStackTrace();
                                                    }
                                                }
                                            }
                                            return bundle2;
                                        } finally {
                                        }
                                    case 3:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Backup", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                    case 4:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_PREPARE");
                                        BackupClientManager backupClientManager = this.this$0;
                                        if (bundle != null) {
                                            backupClientManager.recordDataHelper.sourceKey = str;
                                        }
                                        boolean z4 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Restore")
                                                                .getInt("result");
                                        backupClientManager.getClass();
                                        return CommonClientManager.getResult(z4);
                                    case 5:
                                        BackupClientManager backupClientManager2 = this.this$0;
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] RESTORE",
                                                        "BackupClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor3 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        ParcelFileDescriptor parcelFileDescriptor4 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable(
                                                                "download_path_map_pfd");
                                        BackupRecordReader backupRecordReader =
                                                new BackupRecordReader(parcelFileDescriptor3);
                                        JsonReader jsonReader = backupRecordReader.jsonReader;
                                        if (jsonReader != null) {
                                            try {
                                                jsonReader.beginArray();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        try {
                                            backupClientManager2.recordDataSetList =
                                                    backupRecordReader.getRecordDataSetList();
                                            sCloudWifiConfigSyncImpl.getClass();
                                            z =
                                                    IpcDataHandler.write(
                                                            parcelFileDescriptor4, new HashMap());
                                        } catch (IOException | JSONException e5) {
                                            LOG.i(
                                                    "BackupClientManager",
                                                    "[" + str + "] RESTORE " + e5);
                                            z = false;
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 6:
                                        sCloudWifiConfigSyncImpl.setData(
                                                context2, this.this$0.recordDataSetList);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] DOWNLOAD_COMPLETE true");
                                        return CommonClientManager.getResult(true);
                                    default:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Restore", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                }
                            }
                        });
        final int i2 = 1;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "getKeyAndDate",
                        new IServiceHandler(this) { // from class:
                            // com.samsung.android.scloud.lib.storage.internal.BackupClientManager.1
                            public final /* synthetic */ BackupClientManager this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                ArrayList arrayList;
                                FileInputStream fileInputStream;
                                ObjectInputStream objectInputStream;
                                boolean z;
                                switch (i2) {
                                    case 0:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_PREPARE");
                                        boolean z2 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Backup")
                                                                .getInt("result");
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z2);
                                    case 1:
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor) bundle.getParcelable("pfd");
                                        HashMap hashMap = new HashMap();
                                        Iterator it =
                                                ((ArrayList)
                                                                sCloudWifiConfigSyncImpl.getHeader(
                                                                        context2, null))
                                                        .iterator();
                                        while (it.hasNext()) {
                                            Header header = (Header) it.next();
                                            hashMap.put(
                                                    header.recordId,
                                                    Long.valueOf(header.timeStamp));
                                        }
                                        boolean z3 =
                                                parcelFileDescriptor != null
                                                        && IpcDataHandler.write(
                                                                parcelFileDescriptor, hashMap);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] GET_KEY_AND_DATE " + z3);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z3);
                                    case 2:
                                        LOG.i("BackupClientManager", "[" + str + "] BACKUP");
                                        ParcelFileDescriptor parcelFileDescriptor2 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        this.this$0.getClass();
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            ((ParcelFileDescriptor)
                                                                            bundle.getParcelable(
                                                                                    "upload_key_list_pfd"))
                                                                    .getFileDescriptor());
                                            try {
                                                objectInputStream =
                                                        new ObjectInputStream(fileInputStream);
                                            } catch (Throwable th) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (Throwable th2) {
                                                    th.addSuppressed(th2);
                                                }
                                                throw th;
                                            }
                                        } catch (IOException | ClassNotFoundException unused) {
                                            LOG.e(
                                                    "CommonClientManager",
                                                    "[" + str + "] can't read uploadKeyList",
                                                    null);
                                            arrayList = new ArrayList();
                                        }
                                        try {
                                            arrayList = (ArrayList) objectInputStream.readObject();
                                            LOG.i(
                                                    "CommonClientManager",
                                                    "["
                                                            + str
                                                            + "] getUploadKeyList file : "
                                                            + arrayList.size());
                                            objectInputStream.close();
                                            fileInputStream.close();
                                            Bundle bundle2 = new Bundle();
                                            if (parcelFileDescriptor2 == null) {
                                                LOG.i(
                                                        "BackupClientManager",
                                                        "[" + str + "] BACKUP: meta_pfd is null");
                                            } else {
                                                BackupRecordWriter backupRecordWriter =
                                                        new BackupRecordWriter(
                                                                parcelFileDescriptor2);
                                                try {
                                                    LOG.i(
                                                            "BackupClientManager",
                                                            "BACKUP " + arrayList.size());
                                                    JsonWriter jsonWriter =
                                                            backupRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    Iterator it2 =
                                                            ((ArrayList)
                                                                            sCloudWifiConfigSyncImpl
                                                                                    .getData(
                                                                                            context2,
                                                                                            arrayList))
                                                                    .iterator();
                                                    while (it2.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it2.next();
                                                        Header header2 = recordDataSet.header;
                                                        Body body = recordDataSet.body;
                                                        backupRecordWriter.addRecordAndFiles(
                                                                header2.recordId,
                                                                String.valueOf(header2.timeStamp),
                                                                body.itemData.toString(),
                                                                body.fileList);
                                                    }
                                                    bundle2.putBoolean("is_success", true);
                                                } finally {
                                                    try {
                                                        backupRecordWriter.end();
                                                        backupRecordWriter.close();
                                                    } catch (Exception e3) {
                                                        e3.printStackTrace();
                                                    }
                                                }
                                            }
                                            return bundle2;
                                        } finally {
                                        }
                                    case 3:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Backup", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                    case 4:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_PREPARE");
                                        BackupClientManager backupClientManager = this.this$0;
                                        if (bundle != null) {
                                            backupClientManager.recordDataHelper.sourceKey = str;
                                        }
                                        boolean z4 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Restore")
                                                                .getInt("result");
                                        backupClientManager.getClass();
                                        return CommonClientManager.getResult(z4);
                                    case 5:
                                        BackupClientManager backupClientManager2 = this.this$0;
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] RESTORE",
                                                        "BackupClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor3 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        ParcelFileDescriptor parcelFileDescriptor4 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable(
                                                                "download_path_map_pfd");
                                        BackupRecordReader backupRecordReader =
                                                new BackupRecordReader(parcelFileDescriptor3);
                                        JsonReader jsonReader = backupRecordReader.jsonReader;
                                        if (jsonReader != null) {
                                            try {
                                                jsonReader.beginArray();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        try {
                                            backupClientManager2.recordDataSetList =
                                                    backupRecordReader.getRecordDataSetList();
                                            sCloudWifiConfigSyncImpl.getClass();
                                            z =
                                                    IpcDataHandler.write(
                                                            parcelFileDescriptor4, new HashMap());
                                        } catch (IOException | JSONException e5) {
                                            LOG.i(
                                                    "BackupClientManager",
                                                    "[" + str + "] RESTORE " + e5);
                                            z = false;
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 6:
                                        sCloudWifiConfigSyncImpl.setData(
                                                context2, this.this$0.recordDataSetList);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] DOWNLOAD_COMPLETE true");
                                        return CommonClientManager.getResult(true);
                                    default:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Restore", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                }
                            }
                        });
        final int i3 = 2;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "backup",
                        new IServiceHandler(this) { // from class:
                            // com.samsung.android.scloud.lib.storage.internal.BackupClientManager.1
                            public final /* synthetic */ BackupClientManager this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                ArrayList arrayList;
                                FileInputStream fileInputStream;
                                ObjectInputStream objectInputStream;
                                boolean z;
                                switch (i3) {
                                    case 0:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_PREPARE");
                                        boolean z2 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Backup")
                                                                .getInt("result");
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z2);
                                    case 1:
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor) bundle.getParcelable("pfd");
                                        HashMap hashMap = new HashMap();
                                        Iterator it =
                                                ((ArrayList)
                                                                sCloudWifiConfigSyncImpl.getHeader(
                                                                        context2, null))
                                                        .iterator();
                                        while (it.hasNext()) {
                                            Header header = (Header) it.next();
                                            hashMap.put(
                                                    header.recordId,
                                                    Long.valueOf(header.timeStamp));
                                        }
                                        boolean z3 =
                                                parcelFileDescriptor != null
                                                        && IpcDataHandler.write(
                                                                parcelFileDescriptor, hashMap);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] GET_KEY_AND_DATE " + z3);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z3);
                                    case 2:
                                        LOG.i("BackupClientManager", "[" + str + "] BACKUP");
                                        ParcelFileDescriptor parcelFileDescriptor2 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        this.this$0.getClass();
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            ((ParcelFileDescriptor)
                                                                            bundle.getParcelable(
                                                                                    "upload_key_list_pfd"))
                                                                    .getFileDescriptor());
                                            try {
                                                objectInputStream =
                                                        new ObjectInputStream(fileInputStream);
                                            } catch (Throwable th) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (Throwable th2) {
                                                    th.addSuppressed(th2);
                                                }
                                                throw th;
                                            }
                                        } catch (IOException | ClassNotFoundException unused) {
                                            LOG.e(
                                                    "CommonClientManager",
                                                    "[" + str + "] can't read uploadKeyList",
                                                    null);
                                            arrayList = new ArrayList();
                                        }
                                        try {
                                            arrayList = (ArrayList) objectInputStream.readObject();
                                            LOG.i(
                                                    "CommonClientManager",
                                                    "["
                                                            + str
                                                            + "] getUploadKeyList file : "
                                                            + arrayList.size());
                                            objectInputStream.close();
                                            fileInputStream.close();
                                            Bundle bundle2 = new Bundle();
                                            if (parcelFileDescriptor2 == null) {
                                                LOG.i(
                                                        "BackupClientManager",
                                                        "[" + str + "] BACKUP: meta_pfd is null");
                                            } else {
                                                BackupRecordWriter backupRecordWriter =
                                                        new BackupRecordWriter(
                                                                parcelFileDescriptor2);
                                                try {
                                                    LOG.i(
                                                            "BackupClientManager",
                                                            "BACKUP " + arrayList.size());
                                                    JsonWriter jsonWriter =
                                                            backupRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    Iterator it2 =
                                                            ((ArrayList)
                                                                            sCloudWifiConfigSyncImpl
                                                                                    .getData(
                                                                                            context2,
                                                                                            arrayList))
                                                                    .iterator();
                                                    while (it2.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it2.next();
                                                        Header header2 = recordDataSet.header;
                                                        Body body = recordDataSet.body;
                                                        backupRecordWriter.addRecordAndFiles(
                                                                header2.recordId,
                                                                String.valueOf(header2.timeStamp),
                                                                body.itemData.toString(),
                                                                body.fileList);
                                                    }
                                                    bundle2.putBoolean("is_success", true);
                                                } finally {
                                                    try {
                                                        backupRecordWriter.end();
                                                        backupRecordWriter.close();
                                                    } catch (Exception e3) {
                                                        e3.printStackTrace();
                                                    }
                                                }
                                            }
                                            return bundle2;
                                        } finally {
                                        }
                                    case 3:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Backup", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                    case 4:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_PREPARE");
                                        BackupClientManager backupClientManager = this.this$0;
                                        if (bundle != null) {
                                            backupClientManager.recordDataHelper.sourceKey = str;
                                        }
                                        boolean z4 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Restore")
                                                                .getInt("result");
                                        backupClientManager.getClass();
                                        return CommonClientManager.getResult(z4);
                                    case 5:
                                        BackupClientManager backupClientManager2 = this.this$0;
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] RESTORE",
                                                        "BackupClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor3 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        ParcelFileDescriptor parcelFileDescriptor4 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable(
                                                                "download_path_map_pfd");
                                        BackupRecordReader backupRecordReader =
                                                new BackupRecordReader(parcelFileDescriptor3);
                                        JsonReader jsonReader = backupRecordReader.jsonReader;
                                        if (jsonReader != null) {
                                            try {
                                                jsonReader.beginArray();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        try {
                                            backupClientManager2.recordDataSetList =
                                                    backupRecordReader.getRecordDataSetList();
                                            sCloudWifiConfigSyncImpl.getClass();
                                            z =
                                                    IpcDataHandler.write(
                                                            parcelFileDescriptor4, new HashMap());
                                        } catch (IOException | JSONException e5) {
                                            LOG.i(
                                                    "BackupClientManager",
                                                    "[" + str + "] RESTORE " + e5);
                                            z = false;
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 6:
                                        sCloudWifiConfigSyncImpl.setData(
                                                context2, this.this$0.recordDataSetList);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] DOWNLOAD_COMPLETE true");
                                        return CommonClientManager.getResult(true);
                                    default:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Restore", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                }
                            }
                        });
        final int i4 = 3;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "backupComplete",
                        new IServiceHandler(this) { // from class:
                            // com.samsung.android.scloud.lib.storage.internal.BackupClientManager.1
                            public final /* synthetic */ BackupClientManager this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                ArrayList arrayList;
                                FileInputStream fileInputStream;
                                ObjectInputStream objectInputStream;
                                boolean z;
                                switch (i4) {
                                    case 0:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_PREPARE");
                                        boolean z2 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Backup")
                                                                .getInt("result");
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z2);
                                    case 1:
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor) bundle.getParcelable("pfd");
                                        HashMap hashMap = new HashMap();
                                        Iterator it =
                                                ((ArrayList)
                                                                sCloudWifiConfigSyncImpl.getHeader(
                                                                        context2, null))
                                                        .iterator();
                                        while (it.hasNext()) {
                                            Header header = (Header) it.next();
                                            hashMap.put(
                                                    header.recordId,
                                                    Long.valueOf(header.timeStamp));
                                        }
                                        boolean z3 =
                                                parcelFileDescriptor != null
                                                        && IpcDataHandler.write(
                                                                parcelFileDescriptor, hashMap);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] GET_KEY_AND_DATE " + z3);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z3);
                                    case 2:
                                        LOG.i("BackupClientManager", "[" + str + "] BACKUP");
                                        ParcelFileDescriptor parcelFileDescriptor2 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        this.this$0.getClass();
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            ((ParcelFileDescriptor)
                                                                            bundle.getParcelable(
                                                                                    "upload_key_list_pfd"))
                                                                    .getFileDescriptor());
                                            try {
                                                objectInputStream =
                                                        new ObjectInputStream(fileInputStream);
                                            } catch (Throwable th) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (Throwable th2) {
                                                    th.addSuppressed(th2);
                                                }
                                                throw th;
                                            }
                                        } catch (IOException | ClassNotFoundException unused) {
                                            LOG.e(
                                                    "CommonClientManager",
                                                    "[" + str + "] can't read uploadKeyList",
                                                    null);
                                            arrayList = new ArrayList();
                                        }
                                        try {
                                            arrayList = (ArrayList) objectInputStream.readObject();
                                            LOG.i(
                                                    "CommonClientManager",
                                                    "["
                                                            + str
                                                            + "] getUploadKeyList file : "
                                                            + arrayList.size());
                                            objectInputStream.close();
                                            fileInputStream.close();
                                            Bundle bundle2 = new Bundle();
                                            if (parcelFileDescriptor2 == null) {
                                                LOG.i(
                                                        "BackupClientManager",
                                                        "[" + str + "] BACKUP: meta_pfd is null");
                                            } else {
                                                BackupRecordWriter backupRecordWriter =
                                                        new BackupRecordWriter(
                                                                parcelFileDescriptor2);
                                                try {
                                                    LOG.i(
                                                            "BackupClientManager",
                                                            "BACKUP " + arrayList.size());
                                                    JsonWriter jsonWriter =
                                                            backupRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    Iterator it2 =
                                                            ((ArrayList)
                                                                            sCloudWifiConfigSyncImpl
                                                                                    .getData(
                                                                                            context2,
                                                                                            arrayList))
                                                                    .iterator();
                                                    while (it2.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it2.next();
                                                        Header header2 = recordDataSet.header;
                                                        Body body = recordDataSet.body;
                                                        backupRecordWriter.addRecordAndFiles(
                                                                header2.recordId,
                                                                String.valueOf(header2.timeStamp),
                                                                body.itemData.toString(),
                                                                body.fileList);
                                                    }
                                                    bundle2.putBoolean("is_success", true);
                                                } finally {
                                                    try {
                                                        backupRecordWriter.end();
                                                        backupRecordWriter.close();
                                                    } catch (Exception e3) {
                                                        e3.printStackTrace();
                                                    }
                                                }
                                            }
                                            return bundle2;
                                        } finally {
                                        }
                                    case 3:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Backup", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                    case 4:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_PREPARE");
                                        BackupClientManager backupClientManager = this.this$0;
                                        if (bundle != null) {
                                            backupClientManager.recordDataHelper.sourceKey = str;
                                        }
                                        boolean z4 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Restore")
                                                                .getInt("result");
                                        backupClientManager.getClass();
                                        return CommonClientManager.getResult(z4);
                                    case 5:
                                        BackupClientManager backupClientManager2 = this.this$0;
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] RESTORE",
                                                        "BackupClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor3 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        ParcelFileDescriptor parcelFileDescriptor4 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable(
                                                                "download_path_map_pfd");
                                        BackupRecordReader backupRecordReader =
                                                new BackupRecordReader(parcelFileDescriptor3);
                                        JsonReader jsonReader = backupRecordReader.jsonReader;
                                        if (jsonReader != null) {
                                            try {
                                                jsonReader.beginArray();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        try {
                                            backupClientManager2.recordDataSetList =
                                                    backupRecordReader.getRecordDataSetList();
                                            sCloudWifiConfigSyncImpl.getClass();
                                            z =
                                                    IpcDataHandler.write(
                                                            parcelFileDescriptor4, new HashMap());
                                        } catch (IOException | JSONException e5) {
                                            LOG.i(
                                                    "BackupClientManager",
                                                    "[" + str + "] RESTORE " + e5);
                                            z = false;
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 6:
                                        sCloudWifiConfigSyncImpl.setData(
                                                context2, this.this$0.recordDataSetList);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] DOWNLOAD_COMPLETE true");
                                        return CommonClientManager.getResult(true);
                                    default:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Restore", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                }
                            }
                        });
        final int i5 = 4;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "restorePrepare",
                        new IServiceHandler(this) { // from class:
                            // com.samsung.android.scloud.lib.storage.internal.BackupClientManager.1
                            public final /* synthetic */ BackupClientManager this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                ArrayList arrayList;
                                FileInputStream fileInputStream;
                                ObjectInputStream objectInputStream;
                                boolean z;
                                switch (i5) {
                                    case 0:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_PREPARE");
                                        boolean z2 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Backup")
                                                                .getInt("result");
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z2);
                                    case 1:
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor) bundle.getParcelable("pfd");
                                        HashMap hashMap = new HashMap();
                                        Iterator it =
                                                ((ArrayList)
                                                                sCloudWifiConfigSyncImpl.getHeader(
                                                                        context2, null))
                                                        .iterator();
                                        while (it.hasNext()) {
                                            Header header = (Header) it.next();
                                            hashMap.put(
                                                    header.recordId,
                                                    Long.valueOf(header.timeStamp));
                                        }
                                        boolean z3 =
                                                parcelFileDescriptor != null
                                                        && IpcDataHandler.write(
                                                                parcelFileDescriptor, hashMap);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] GET_KEY_AND_DATE " + z3);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z3);
                                    case 2:
                                        LOG.i("BackupClientManager", "[" + str + "] BACKUP");
                                        ParcelFileDescriptor parcelFileDescriptor2 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        this.this$0.getClass();
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            ((ParcelFileDescriptor)
                                                                            bundle.getParcelable(
                                                                                    "upload_key_list_pfd"))
                                                                    .getFileDescriptor());
                                            try {
                                                objectInputStream =
                                                        new ObjectInputStream(fileInputStream);
                                            } catch (Throwable th) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (Throwable th2) {
                                                    th.addSuppressed(th2);
                                                }
                                                throw th;
                                            }
                                        } catch (IOException | ClassNotFoundException unused) {
                                            LOG.e(
                                                    "CommonClientManager",
                                                    "[" + str + "] can't read uploadKeyList",
                                                    null);
                                            arrayList = new ArrayList();
                                        }
                                        try {
                                            arrayList = (ArrayList) objectInputStream.readObject();
                                            LOG.i(
                                                    "CommonClientManager",
                                                    "["
                                                            + str
                                                            + "] getUploadKeyList file : "
                                                            + arrayList.size());
                                            objectInputStream.close();
                                            fileInputStream.close();
                                            Bundle bundle2 = new Bundle();
                                            if (parcelFileDescriptor2 == null) {
                                                LOG.i(
                                                        "BackupClientManager",
                                                        "[" + str + "] BACKUP: meta_pfd is null");
                                            } else {
                                                BackupRecordWriter backupRecordWriter =
                                                        new BackupRecordWriter(
                                                                parcelFileDescriptor2);
                                                try {
                                                    LOG.i(
                                                            "BackupClientManager",
                                                            "BACKUP " + arrayList.size());
                                                    JsonWriter jsonWriter =
                                                            backupRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    Iterator it2 =
                                                            ((ArrayList)
                                                                            sCloudWifiConfigSyncImpl
                                                                                    .getData(
                                                                                            context2,
                                                                                            arrayList))
                                                                    .iterator();
                                                    while (it2.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it2.next();
                                                        Header header2 = recordDataSet.header;
                                                        Body body = recordDataSet.body;
                                                        backupRecordWriter.addRecordAndFiles(
                                                                header2.recordId,
                                                                String.valueOf(header2.timeStamp),
                                                                body.itemData.toString(),
                                                                body.fileList);
                                                    }
                                                    bundle2.putBoolean("is_success", true);
                                                } finally {
                                                    try {
                                                        backupRecordWriter.end();
                                                        backupRecordWriter.close();
                                                    } catch (Exception e3) {
                                                        e3.printStackTrace();
                                                    }
                                                }
                                            }
                                            return bundle2;
                                        } finally {
                                        }
                                    case 3:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Backup", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                    case 4:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_PREPARE");
                                        BackupClientManager backupClientManager = this.this$0;
                                        if (bundle != null) {
                                            backupClientManager.recordDataHelper.sourceKey = str;
                                        }
                                        boolean z4 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Restore")
                                                                .getInt("result");
                                        backupClientManager.getClass();
                                        return CommonClientManager.getResult(z4);
                                    case 5:
                                        BackupClientManager backupClientManager2 = this.this$0;
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] RESTORE",
                                                        "BackupClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor3 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        ParcelFileDescriptor parcelFileDescriptor4 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable(
                                                                "download_path_map_pfd");
                                        BackupRecordReader backupRecordReader =
                                                new BackupRecordReader(parcelFileDescriptor3);
                                        JsonReader jsonReader = backupRecordReader.jsonReader;
                                        if (jsonReader != null) {
                                            try {
                                                jsonReader.beginArray();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        try {
                                            backupClientManager2.recordDataSetList =
                                                    backupRecordReader.getRecordDataSetList();
                                            sCloudWifiConfigSyncImpl.getClass();
                                            z =
                                                    IpcDataHandler.write(
                                                            parcelFileDescriptor4, new HashMap());
                                        } catch (IOException | JSONException e5) {
                                            LOG.i(
                                                    "BackupClientManager",
                                                    "[" + str + "] RESTORE " + e5);
                                            z = false;
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 6:
                                        sCloudWifiConfigSyncImpl.setData(
                                                context2, this.this$0.recordDataSetList);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] DOWNLOAD_COMPLETE true");
                                        return CommonClientManager.getResult(true);
                                    default:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Restore", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                }
                            }
                        });
        final int i6 = 5;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "restore",
                        new IServiceHandler(this) { // from class:
                            // com.samsung.android.scloud.lib.storage.internal.BackupClientManager.1
                            public final /* synthetic */ BackupClientManager this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                ArrayList arrayList;
                                FileInputStream fileInputStream;
                                ObjectInputStream objectInputStream;
                                boolean z;
                                switch (i6) {
                                    case 0:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_PREPARE");
                                        boolean z2 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Backup")
                                                                .getInt("result");
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z2);
                                    case 1:
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor) bundle.getParcelable("pfd");
                                        HashMap hashMap = new HashMap();
                                        Iterator it =
                                                ((ArrayList)
                                                                sCloudWifiConfigSyncImpl.getHeader(
                                                                        context2, null))
                                                        .iterator();
                                        while (it.hasNext()) {
                                            Header header = (Header) it.next();
                                            hashMap.put(
                                                    header.recordId,
                                                    Long.valueOf(header.timeStamp));
                                        }
                                        boolean z3 =
                                                parcelFileDescriptor != null
                                                        && IpcDataHandler.write(
                                                                parcelFileDescriptor, hashMap);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] GET_KEY_AND_DATE " + z3);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z3);
                                    case 2:
                                        LOG.i("BackupClientManager", "[" + str + "] BACKUP");
                                        ParcelFileDescriptor parcelFileDescriptor2 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        this.this$0.getClass();
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            ((ParcelFileDescriptor)
                                                                            bundle.getParcelable(
                                                                                    "upload_key_list_pfd"))
                                                                    .getFileDescriptor());
                                            try {
                                                objectInputStream =
                                                        new ObjectInputStream(fileInputStream);
                                            } catch (Throwable th) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (Throwable th2) {
                                                    th.addSuppressed(th2);
                                                }
                                                throw th;
                                            }
                                        } catch (IOException | ClassNotFoundException unused) {
                                            LOG.e(
                                                    "CommonClientManager",
                                                    "[" + str + "] can't read uploadKeyList",
                                                    null);
                                            arrayList = new ArrayList();
                                        }
                                        try {
                                            arrayList = (ArrayList) objectInputStream.readObject();
                                            LOG.i(
                                                    "CommonClientManager",
                                                    "["
                                                            + str
                                                            + "] getUploadKeyList file : "
                                                            + arrayList.size());
                                            objectInputStream.close();
                                            fileInputStream.close();
                                            Bundle bundle2 = new Bundle();
                                            if (parcelFileDescriptor2 == null) {
                                                LOG.i(
                                                        "BackupClientManager",
                                                        "[" + str + "] BACKUP: meta_pfd is null");
                                            } else {
                                                BackupRecordWriter backupRecordWriter =
                                                        new BackupRecordWriter(
                                                                parcelFileDescriptor2);
                                                try {
                                                    LOG.i(
                                                            "BackupClientManager",
                                                            "BACKUP " + arrayList.size());
                                                    JsonWriter jsonWriter =
                                                            backupRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    Iterator it2 =
                                                            ((ArrayList)
                                                                            sCloudWifiConfigSyncImpl
                                                                                    .getData(
                                                                                            context2,
                                                                                            arrayList))
                                                                    .iterator();
                                                    while (it2.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it2.next();
                                                        Header header2 = recordDataSet.header;
                                                        Body body = recordDataSet.body;
                                                        backupRecordWriter.addRecordAndFiles(
                                                                header2.recordId,
                                                                String.valueOf(header2.timeStamp),
                                                                body.itemData.toString(),
                                                                body.fileList);
                                                    }
                                                    bundle2.putBoolean("is_success", true);
                                                } finally {
                                                    try {
                                                        backupRecordWriter.end();
                                                        backupRecordWriter.close();
                                                    } catch (Exception e3) {
                                                        e3.printStackTrace();
                                                    }
                                                }
                                            }
                                            return bundle2;
                                        } finally {
                                        }
                                    case 3:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Backup", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                    case 4:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_PREPARE");
                                        BackupClientManager backupClientManager = this.this$0;
                                        if (bundle != null) {
                                            backupClientManager.recordDataHelper.sourceKey = str;
                                        }
                                        boolean z4 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Restore")
                                                                .getInt("result");
                                        backupClientManager.getClass();
                                        return CommonClientManager.getResult(z4);
                                    case 5:
                                        BackupClientManager backupClientManager2 = this.this$0;
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] RESTORE",
                                                        "BackupClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor3 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        ParcelFileDescriptor parcelFileDescriptor4 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable(
                                                                "download_path_map_pfd");
                                        BackupRecordReader backupRecordReader =
                                                new BackupRecordReader(parcelFileDescriptor3);
                                        JsonReader jsonReader = backupRecordReader.jsonReader;
                                        if (jsonReader != null) {
                                            try {
                                                jsonReader.beginArray();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        try {
                                            backupClientManager2.recordDataSetList =
                                                    backupRecordReader.getRecordDataSetList();
                                            sCloudWifiConfigSyncImpl.getClass();
                                            z =
                                                    IpcDataHandler.write(
                                                            parcelFileDescriptor4, new HashMap());
                                        } catch (IOException | JSONException e5) {
                                            LOG.i(
                                                    "BackupClientManager",
                                                    "[" + str + "] RESTORE " + e5);
                                            z = false;
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 6:
                                        sCloudWifiConfigSyncImpl.setData(
                                                context2, this.this$0.recordDataSetList);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] DOWNLOAD_COMPLETE true");
                                        return CommonClientManager.getResult(true);
                                    default:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Restore", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                }
                            }
                        });
        final int i7 = 6;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "downloadComplete",
                        new IServiceHandler(this) { // from class:
                            // com.samsung.android.scloud.lib.storage.internal.BackupClientManager.1
                            public final /* synthetic */ BackupClientManager this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                ArrayList arrayList;
                                FileInputStream fileInputStream;
                                ObjectInputStream objectInputStream;
                                boolean z;
                                switch (i7) {
                                    case 0:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_PREPARE");
                                        boolean z2 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Backup")
                                                                .getInt("result");
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z2);
                                    case 1:
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor) bundle.getParcelable("pfd");
                                        HashMap hashMap = new HashMap();
                                        Iterator it =
                                                ((ArrayList)
                                                                sCloudWifiConfigSyncImpl.getHeader(
                                                                        context2, null))
                                                        .iterator();
                                        while (it.hasNext()) {
                                            Header header = (Header) it.next();
                                            hashMap.put(
                                                    header.recordId,
                                                    Long.valueOf(header.timeStamp));
                                        }
                                        boolean z3 =
                                                parcelFileDescriptor != null
                                                        && IpcDataHandler.write(
                                                                parcelFileDescriptor, hashMap);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] GET_KEY_AND_DATE " + z3);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z3);
                                    case 2:
                                        LOG.i("BackupClientManager", "[" + str + "] BACKUP");
                                        ParcelFileDescriptor parcelFileDescriptor2 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        this.this$0.getClass();
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            ((ParcelFileDescriptor)
                                                                            bundle.getParcelable(
                                                                                    "upload_key_list_pfd"))
                                                                    .getFileDescriptor());
                                            try {
                                                objectInputStream =
                                                        new ObjectInputStream(fileInputStream);
                                            } catch (Throwable th) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (Throwable th2) {
                                                    th.addSuppressed(th2);
                                                }
                                                throw th;
                                            }
                                        } catch (IOException | ClassNotFoundException unused) {
                                            LOG.e(
                                                    "CommonClientManager",
                                                    "[" + str + "] can't read uploadKeyList",
                                                    null);
                                            arrayList = new ArrayList();
                                        }
                                        try {
                                            arrayList = (ArrayList) objectInputStream.readObject();
                                            LOG.i(
                                                    "CommonClientManager",
                                                    "["
                                                            + str
                                                            + "] getUploadKeyList file : "
                                                            + arrayList.size());
                                            objectInputStream.close();
                                            fileInputStream.close();
                                            Bundle bundle2 = new Bundle();
                                            if (parcelFileDescriptor2 == null) {
                                                LOG.i(
                                                        "BackupClientManager",
                                                        "[" + str + "] BACKUP: meta_pfd is null");
                                            } else {
                                                BackupRecordWriter backupRecordWriter =
                                                        new BackupRecordWriter(
                                                                parcelFileDescriptor2);
                                                try {
                                                    LOG.i(
                                                            "BackupClientManager",
                                                            "BACKUP " + arrayList.size());
                                                    JsonWriter jsonWriter =
                                                            backupRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    Iterator it2 =
                                                            ((ArrayList)
                                                                            sCloudWifiConfigSyncImpl
                                                                                    .getData(
                                                                                            context2,
                                                                                            arrayList))
                                                                    .iterator();
                                                    while (it2.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it2.next();
                                                        Header header2 = recordDataSet.header;
                                                        Body body = recordDataSet.body;
                                                        backupRecordWriter.addRecordAndFiles(
                                                                header2.recordId,
                                                                String.valueOf(header2.timeStamp),
                                                                body.itemData.toString(),
                                                                body.fileList);
                                                    }
                                                    bundle2.putBoolean("is_success", true);
                                                } finally {
                                                    try {
                                                        backupRecordWriter.end();
                                                        backupRecordWriter.close();
                                                    } catch (Exception e3) {
                                                        e3.printStackTrace();
                                                    }
                                                }
                                            }
                                            return bundle2;
                                        } finally {
                                        }
                                    case 3:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Backup", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                    case 4:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_PREPARE");
                                        BackupClientManager backupClientManager = this.this$0;
                                        if (bundle != null) {
                                            backupClientManager.recordDataHelper.sourceKey = str;
                                        }
                                        boolean z4 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Restore")
                                                                .getInt("result");
                                        backupClientManager.getClass();
                                        return CommonClientManager.getResult(z4);
                                    case 5:
                                        BackupClientManager backupClientManager2 = this.this$0;
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] RESTORE",
                                                        "BackupClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor3 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        ParcelFileDescriptor parcelFileDescriptor4 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable(
                                                                "download_path_map_pfd");
                                        BackupRecordReader backupRecordReader =
                                                new BackupRecordReader(parcelFileDescriptor3);
                                        JsonReader jsonReader = backupRecordReader.jsonReader;
                                        if (jsonReader != null) {
                                            try {
                                                jsonReader.beginArray();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        try {
                                            backupClientManager2.recordDataSetList =
                                                    backupRecordReader.getRecordDataSetList();
                                            sCloudWifiConfigSyncImpl.getClass();
                                            z =
                                                    IpcDataHandler.write(
                                                            parcelFileDescriptor4, new HashMap());
                                        } catch (IOException | JSONException e5) {
                                            LOG.i(
                                                    "BackupClientManager",
                                                    "[" + str + "] RESTORE " + e5);
                                            z = false;
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 6:
                                        sCloudWifiConfigSyncImpl.setData(
                                                context2, this.this$0.recordDataSetList);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] DOWNLOAD_COMPLETE true");
                                        return CommonClientManager.getResult(true);
                                    default:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Restore", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                }
                            }
                        });
        final int i8 = 0;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "restoreFile",
                        new IServiceHandler(
                                this) { // from class:
                                        // com.samsung.android.scloud.lib.storage.internal.BackupClientManager.8
                            public final /* synthetic */ BackupClientManager this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                switch (i8) {
                                    case 0:
                                        this.this$0.getClass();
                                        return CommonClientManager.getFileDescriptor(bundle, str);
                                    default:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] REQUEST_CANCEL");
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                }
                            }
                        });
        final int i9 = 7;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "restoreComplete",
                        new IServiceHandler(this) { // from class:
                            // com.samsung.android.scloud.lib.storage.internal.BackupClientManager.1
                            public final /* synthetic */ BackupClientManager this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                ArrayList arrayList;
                                FileInputStream fileInputStream;
                                ObjectInputStream objectInputStream;
                                boolean z;
                                switch (i9) {
                                    case 0:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_PREPARE");
                                        boolean z2 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Backup")
                                                                .getInt("result");
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z2);
                                    case 1:
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor) bundle.getParcelable("pfd");
                                        HashMap hashMap = new HashMap();
                                        Iterator it =
                                                ((ArrayList)
                                                                sCloudWifiConfigSyncImpl.getHeader(
                                                                        context2, null))
                                                        .iterator();
                                        while (it.hasNext()) {
                                            Header header = (Header) it.next();
                                            hashMap.put(
                                                    header.recordId,
                                                    Long.valueOf(header.timeStamp));
                                        }
                                        boolean z3 =
                                                parcelFileDescriptor != null
                                                        && IpcDataHandler.write(
                                                                parcelFileDescriptor, hashMap);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] GET_KEY_AND_DATE " + z3);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(z3);
                                    case 2:
                                        LOG.i("BackupClientManager", "[" + str + "] BACKUP");
                                        ParcelFileDescriptor parcelFileDescriptor2 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        this.this$0.getClass();
                                        try {
                                            fileInputStream =
                                                    new FileInputStream(
                                                            ((ParcelFileDescriptor)
                                                                            bundle.getParcelable(
                                                                                    "upload_key_list_pfd"))
                                                                    .getFileDescriptor());
                                            try {
                                                objectInputStream =
                                                        new ObjectInputStream(fileInputStream);
                                            } catch (Throwable th) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (Throwable th2) {
                                                    th.addSuppressed(th2);
                                                }
                                                throw th;
                                            }
                                        } catch (IOException | ClassNotFoundException unused) {
                                            LOG.e(
                                                    "CommonClientManager",
                                                    "[" + str + "] can't read uploadKeyList",
                                                    null);
                                            arrayList = new ArrayList();
                                        }
                                        try {
                                            arrayList = (ArrayList) objectInputStream.readObject();
                                            LOG.i(
                                                    "CommonClientManager",
                                                    "["
                                                            + str
                                                            + "] getUploadKeyList file : "
                                                            + arrayList.size());
                                            objectInputStream.close();
                                            fileInputStream.close();
                                            Bundle bundle2 = new Bundle();
                                            if (parcelFileDescriptor2 == null) {
                                                LOG.i(
                                                        "BackupClientManager",
                                                        "[" + str + "] BACKUP: meta_pfd is null");
                                            } else {
                                                BackupRecordWriter backupRecordWriter =
                                                        new BackupRecordWriter(
                                                                parcelFileDescriptor2);
                                                try {
                                                    LOG.i(
                                                            "BackupClientManager",
                                                            "BACKUP " + arrayList.size());
                                                    JsonWriter jsonWriter =
                                                            backupRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    Iterator it2 =
                                                            ((ArrayList)
                                                                            sCloudWifiConfigSyncImpl
                                                                                    .getData(
                                                                                            context2,
                                                                                            arrayList))
                                                                    .iterator();
                                                    while (it2.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it2.next();
                                                        Header header2 = recordDataSet.header;
                                                        Body body = recordDataSet.body;
                                                        backupRecordWriter.addRecordAndFiles(
                                                                header2.recordId,
                                                                String.valueOf(header2.timeStamp),
                                                                body.itemData.toString(),
                                                                body.fileList);
                                                    }
                                                    bundle2.putBoolean("is_success", true);
                                                } finally {
                                                    try {
                                                        backupRecordWriter.end();
                                                        backupRecordWriter.close();
                                                    } catch (Exception e3) {
                                                        e3.printStackTrace();
                                                    }
                                                }
                                            }
                                            return bundle2;
                                        } finally {
                                        }
                                    case 3:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] BACKUP_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Backup", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                    case 4:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_PREPARE");
                                        BackupClientManager backupClientManager = this.this$0;
                                        if (bundle != null) {
                                            backupClientManager.recordDataHelper.sourceKey = str;
                                        }
                                        boolean z4 =
                                                1
                                                        == sCloudWifiConfigSyncImpl
                                                                .prepare(context2, "Restore")
                                                                .getInt("result");
                                        backupClientManager.getClass();
                                        return CommonClientManager.getResult(z4);
                                    case 5:
                                        BackupClientManager backupClientManager2 = this.this$0;
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] RESTORE",
                                                        "BackupClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor3 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("meta_pfd");
                                        ParcelFileDescriptor parcelFileDescriptor4 =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable(
                                                                "download_path_map_pfd");
                                        BackupRecordReader backupRecordReader =
                                                new BackupRecordReader(parcelFileDescriptor3);
                                        JsonReader jsonReader = backupRecordReader.jsonReader;
                                        if (jsonReader != null) {
                                            try {
                                                jsonReader.beginArray();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        try {
                                            backupClientManager2.recordDataSetList =
                                                    backupRecordReader.getRecordDataSetList();
                                            sCloudWifiConfigSyncImpl.getClass();
                                            z =
                                                    IpcDataHandler.write(
                                                            parcelFileDescriptor4, new HashMap());
                                        } catch (IOException | JSONException e5) {
                                            LOG.i(
                                                    "BackupClientManager",
                                                    "[" + str + "] RESTORE " + e5);
                                            z = false;
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 6:
                                        sCloudWifiConfigSyncImpl.setData(
                                                context2, this.this$0.recordDataSetList);
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] DOWNLOAD_COMPLETE true");
                                        return CommonClientManager.getResult(true);
                                    default:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] RESTORE_COMPLETE");
                                        sCloudWifiConfigSyncImpl.finish(context2, "Restore", null);
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                }
                            }
                        });
        final int i10 = 1;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "requestCancel",
                        new IServiceHandler(
                                this) { // from class:
                                        // com.samsung.android.scloud.lib.storage.internal.BackupClientManager.8
                            public final /* synthetic */ BackupClientManager this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                switch (i10) {
                                    case 0:
                                        this.this$0.getClass();
                                        return CommonClientManager.getFileDescriptor(bundle, str);
                                    default:
                                        LOG.i(
                                                "BackupClientManager",
                                                "[" + str + "] REQUEST_CANCEL");
                                        this.this$0.getClass();
                                        return CommonClientManager.getResult(true);
                                }
                            }
                        });
    }

    @Override // com.samsung.android.scloud.oem.lib.common.IClientHelper
    public final Object getClient() {
        return this.recordDataClient;
    }
}
