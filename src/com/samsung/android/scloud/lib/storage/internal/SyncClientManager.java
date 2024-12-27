package com.samsung.android.scloud.lib.storage.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.os.ParcelFileDescriptor;
import android.util.JsonReader;
import android.util.JsonWriter;

import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.scloud.lib.storage.data.Header;
import com.samsung.android.scloud.lib.storage.data.RecordDataSet;
import com.samsung.android.scloud.lib.storage.internal.SyncRecordDataHelper.CallbackHandler;
import com.samsung.android.scloud.oem.lib.LOG;
import com.samsung.android.scloud.oem.lib.common.IServiceHandler;
import com.samsung.android.settings.scloud.SCloudWifiConfigSyncImpl;
import com.sec.ims.IMSParameter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SyncClientManager extends CommonClientManager {
    public final SyncRecordDataHelper recordDataHelper;
    public final SCloudWifiConfigSyncImpl syncDataClient;

    public SyncClientManager(
            Context context, final SCloudWifiConfigSyncImpl sCloudWifiConfigSyncImpl) {
        this.syncDataClient = sCloudWifiConfigSyncImpl;
        final SyncRecordDataHelper syncRecordDataHelper = new SyncRecordDataHelper();
        syncRecordDataHelper.fileDownloadResult = "NONE";
        syncRecordDataHelper.countDownLatch = new CountDownLatch(0);
        final Messenger[] messengerArr = {null};
        Handler handler = new Handler(context.getMainLooper());
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        handler.post(
                new Runnable() { // from class:
                                 // com.samsung.android.scloud.lib.storage.internal.SyncRecordDataHelper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SyncRecordDataHelper syncRecordDataHelper2 = SyncRecordDataHelper.this;
                        Messenger[] messengerArr2 = messengerArr;
                        CountDownLatch countDownLatch2 = countDownLatch;
                        syncRecordDataHelper2.getClass();
                        messengerArr2[0] =
                                new Messenger(syncRecordDataHelper2.new CallbackHandler());
                        countDownLatch2.countDown();
                    }
                });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Messenger messenger = messengerArr[0];
        this.recordDataHelper = syncRecordDataHelper;
        final int i = 0;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "prepare",
                        new IServiceHandler() { // from class:
                                                // com.samsung.android.scloud.lib.storage.internal.SyncClientManager.1
                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                StringBuilder sb;
                                switch (i) {
                                    case 0:
                                        LOG.i("SyncClientManager", "[" + str + "] Sync Prepare");
                                        return ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .prepare(context2, "Sync");
                                    case 1:
                                        LOG.i(
                                                "SyncClientManager",
                                                "[" + str + "] GET_LOCAL_CHANGES");
                                        List header =
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .getHeader(context2, bundle);
                                        Bundle bundle2 = new Bundle();
                                        ArrayList arrayList = (ArrayList) header;
                                        int size = arrayList.size();
                                        if (size > 0) {
                                            String[] strArr = new String[size];
                                            long[] jArr = new long[size];
                                            String[] strArr2 = new String[size];
                                            String[] strArr3 = new String[size];
                                            for (int i2 = 0; i2 < size; i2++) {
                                                Header header2 = (Header) arrayList.get(i2);
                                                strArr[i2] = header2.localId;
                                                strArr3[i2] = header2.recordId;
                                                jArr[i2] = header2.timeStamp;
                                                strArr2[i2] = header2.statue;
                                            }
                                            bundle2.putStringArray("local_id", strArr);
                                            bundle2.putStringArray("record_id", strArr3);
                                            bundle2.putLongArray(
                                                    PhoneRestrictionPolicy.TIMESTAMP, jArr);
                                            bundle2.putStringArray(
                                                    IMSParameter.CALL.STATUS, strArr2);
                                        }
                                        bundle2.putBoolean("is_success", true);
                                        return bundle2;
                                    case 2:
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] UPLOAD", "SyncClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("record_pfd");
                                        String[] stringArray = bundle.getStringArray("record_id");
                                        String[] stringArray2 = bundle.getStringArray("local_id");
                                        boolean z = false;
                                        z = false;
                                        z = false;
                                        if (stringArray != null
                                                && stringArray2 != null
                                                && parcelFileDescriptor != null) {
                                            SyncRecordWriter syncRecordWriter =
                                                    new SyncRecordWriter(parcelFileDescriptor);
                                            try {
                                                try {
                                                    JsonWriter jsonWriter =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    HashMap hashMap = new HashMap();
                                                    ArrayList arrayList2 = new ArrayList();
                                                    int length = stringArray2.length;
                                                    for (int i3 = 0; i3 < length; i3++) {
                                                        hashMap.put(
                                                                stringArray2[i3], stringArray[i3]);
                                                        arrayList2.add(stringArray2[i3]);
                                                    }
                                                    List data =
                                                            ((SCloudWifiConfigSyncImpl)
                                                                            sCloudWifiConfigSyncImpl)
                                                                    .getData(context2, arrayList2);
                                                    syncRecordWriter.openObject();
                                                    Iterator it = ((ArrayList) data).iterator();
                                                    while (it.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it.next();
                                                        LOG.i("SyncClientManager", "dataSet : ");
                                                        JSONObject jSONObject =
                                                                recordDataSet.body.itemData;
                                                        Header header3 = recordDataSet.header;
                                                        String str2 = header3.recordId;
                                                        if (str2 == null) {
                                                            str2 =
                                                                    (String)
                                                                            hashMap.get(
                                                                                    header3.localId);
                                                            jSONObject.put("record_id", str2);
                                                        }
                                                        syncRecordWriter.addRecordAndFiles(
                                                                str2,
                                                                String.valueOf(header3.timeStamp),
                                                                jSONObject.toString(),
                                                                recordDataSet.body.fileList);
                                                    }
                                                    JsonWriter jsonWriter2 =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter2 != null) {
                                                        try {
                                                            jsonWriter2.endArray();
                                                            syncRecordWriter.jsonWriter.endObject();
                                                            syncRecordWriter.jsonWriter.flush();
                                                        } catch (IOException e3) {
                                                            e3.printStackTrace();
                                                        }
                                                    }
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e4) {
                                                        e = e4;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                } catch (Exception e5) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD parsing error : "
                                                                    + e5.getMessage(),
                                                            null);
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e6) {
                                                        e = e6;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                }
                                                z = true;
                                            } catch (Throwable th) {
                                                try {
                                                    syncRecordWriter.end();
                                                    syncRecordWriter.close();
                                                } catch (Exception e7) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD error : " + e7.getMessage(),
                                                            null);
                                                }
                                                throw th;
                                            }
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 3:
                                        Bundle m2 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] DELETE", "SyncClientManager");
                                        String[] stringArray3 = bundle.getStringArray("record_id");
                                        ArrayList arrayList3 = new ArrayList();
                                        for (String str3 : stringArray3) {
                                            arrayList3.add(
                                                    new RecordDataSet(
                                                            new Header(0L, str3, null, "delete"),
                                                            null));
                                        }
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .setData(context2, arrayList3);
                                        m2.putBoolean("is_success", true);
                                        return m2;
                                    case 4:
                                        Bundle m3 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] UPLOAD_COMPLETE",
                                                        "SyncClientManager");
                                        String[] stringArray4 = bundle.getStringArray("record_id");
                                        String[] stringArray5 = bundle.getStringArray("local_id");
                                        ArrayList arrayList4 = new ArrayList();
                                        boolean z2 = false;
                                        int i4 = 0;
                                        z2 = false;
                                        if (stringArray4 != null && stringArray5 != null) {
                                            int length2 = stringArray4.length;
                                            boolean z3 = false;
                                            while (i4 < length2) {
                                                arrayList4.add(
                                                        new RecordDataSet(
                                                                new Header(
                                                                        0L,
                                                                        stringArray4[i4],
                                                                        stringArray5[i4],
                                                                        "uploadComplete"),
                                                                null));
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .setData(context2, arrayList4);
                                                i4++;
                                                z3 = true;
                                            }
                                            z2 = z3;
                                        }
                                        m3.putBoolean("is_success", z2);
                                        return m3;
                                    case 5:
                                        Bundle m4 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] FINISH", "SyncClientManager");
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .finish(context2, "sync", bundle);
                                        return m4;
                                    default:
                                        ((SyncClientManager) sCloudWifiConfigSyncImpl).getClass();
                                        return CommonClientManager.getFileDescriptor(bundle, str);
                                }
                            }
                        });
        final int i2 = 1;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "getLocalChanges",
                        new IServiceHandler() { // from class:
                                                // com.samsung.android.scloud.lib.storage.internal.SyncClientManager.1
                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                StringBuilder sb;
                                switch (i2) {
                                    case 0:
                                        LOG.i("SyncClientManager", "[" + str + "] Sync Prepare");
                                        return ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .prepare(context2, "Sync");
                                    case 1:
                                        LOG.i(
                                                "SyncClientManager",
                                                "[" + str + "] GET_LOCAL_CHANGES");
                                        List header =
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .getHeader(context2, bundle);
                                        Bundle bundle2 = new Bundle();
                                        ArrayList arrayList = (ArrayList) header;
                                        int size = arrayList.size();
                                        if (size > 0) {
                                            String[] strArr = new String[size];
                                            long[] jArr = new long[size];
                                            String[] strArr2 = new String[size];
                                            String[] strArr3 = new String[size];
                                            for (int i22 = 0; i22 < size; i22++) {
                                                Header header2 = (Header) arrayList.get(i22);
                                                strArr[i22] = header2.localId;
                                                strArr3[i22] = header2.recordId;
                                                jArr[i22] = header2.timeStamp;
                                                strArr2[i22] = header2.statue;
                                            }
                                            bundle2.putStringArray("local_id", strArr);
                                            bundle2.putStringArray("record_id", strArr3);
                                            bundle2.putLongArray(
                                                    PhoneRestrictionPolicy.TIMESTAMP, jArr);
                                            bundle2.putStringArray(
                                                    IMSParameter.CALL.STATUS, strArr2);
                                        }
                                        bundle2.putBoolean("is_success", true);
                                        return bundle2;
                                    case 2:
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] UPLOAD", "SyncClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("record_pfd");
                                        String[] stringArray = bundle.getStringArray("record_id");
                                        String[] stringArray2 = bundle.getStringArray("local_id");
                                        boolean z = false;
                                        z = false;
                                        z = false;
                                        if (stringArray != null
                                                && stringArray2 != null
                                                && parcelFileDescriptor != null) {
                                            SyncRecordWriter syncRecordWriter =
                                                    new SyncRecordWriter(parcelFileDescriptor);
                                            try {
                                                try {
                                                    JsonWriter jsonWriter =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    HashMap hashMap = new HashMap();
                                                    ArrayList arrayList2 = new ArrayList();
                                                    int length = stringArray2.length;
                                                    for (int i3 = 0; i3 < length; i3++) {
                                                        hashMap.put(
                                                                stringArray2[i3], stringArray[i3]);
                                                        arrayList2.add(stringArray2[i3]);
                                                    }
                                                    List data =
                                                            ((SCloudWifiConfigSyncImpl)
                                                                            sCloudWifiConfigSyncImpl)
                                                                    .getData(context2, arrayList2);
                                                    syncRecordWriter.openObject();
                                                    Iterator it = ((ArrayList) data).iterator();
                                                    while (it.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it.next();
                                                        LOG.i("SyncClientManager", "dataSet : ");
                                                        JSONObject jSONObject =
                                                                recordDataSet.body.itemData;
                                                        Header header3 = recordDataSet.header;
                                                        String str2 = header3.recordId;
                                                        if (str2 == null) {
                                                            str2 =
                                                                    (String)
                                                                            hashMap.get(
                                                                                    header3.localId);
                                                            jSONObject.put("record_id", str2);
                                                        }
                                                        syncRecordWriter.addRecordAndFiles(
                                                                str2,
                                                                String.valueOf(header3.timeStamp),
                                                                jSONObject.toString(),
                                                                recordDataSet.body.fileList);
                                                    }
                                                    JsonWriter jsonWriter2 =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter2 != null) {
                                                        try {
                                                            jsonWriter2.endArray();
                                                            syncRecordWriter.jsonWriter.endObject();
                                                            syncRecordWriter.jsonWriter.flush();
                                                        } catch (IOException e3) {
                                                            e3.printStackTrace();
                                                        }
                                                    }
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e4) {
                                                        e = e4;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                } catch (Exception e5) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD parsing error : "
                                                                    + e5.getMessage(),
                                                            null);
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e6) {
                                                        e = e6;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                }
                                                z = true;
                                            } catch (Throwable th) {
                                                try {
                                                    syncRecordWriter.end();
                                                    syncRecordWriter.close();
                                                } catch (Exception e7) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD error : " + e7.getMessage(),
                                                            null);
                                                }
                                                throw th;
                                            }
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 3:
                                        Bundle m2 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] DELETE", "SyncClientManager");
                                        String[] stringArray3 = bundle.getStringArray("record_id");
                                        ArrayList arrayList3 = new ArrayList();
                                        for (String str3 : stringArray3) {
                                            arrayList3.add(
                                                    new RecordDataSet(
                                                            new Header(0L, str3, null, "delete"),
                                                            null));
                                        }
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .setData(context2, arrayList3);
                                        m2.putBoolean("is_success", true);
                                        return m2;
                                    case 4:
                                        Bundle m3 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] UPLOAD_COMPLETE",
                                                        "SyncClientManager");
                                        String[] stringArray4 = bundle.getStringArray("record_id");
                                        String[] stringArray5 = bundle.getStringArray("local_id");
                                        ArrayList arrayList4 = new ArrayList();
                                        boolean z2 = false;
                                        int i4 = 0;
                                        z2 = false;
                                        if (stringArray4 != null && stringArray5 != null) {
                                            int length2 = stringArray4.length;
                                            boolean z3 = false;
                                            while (i4 < length2) {
                                                arrayList4.add(
                                                        new RecordDataSet(
                                                                new Header(
                                                                        0L,
                                                                        stringArray4[i4],
                                                                        stringArray5[i4],
                                                                        "uploadComplete"),
                                                                null));
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .setData(context2, arrayList4);
                                                i4++;
                                                z3 = true;
                                            }
                                            z2 = z3;
                                        }
                                        m3.putBoolean("is_success", z2);
                                        return m3;
                                    case 5:
                                        Bundle m4 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] FINISH", "SyncClientManager");
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .finish(context2, "sync", bundle);
                                        return m4;
                                    default:
                                        ((SyncClientManager) sCloudWifiConfigSyncImpl).getClass();
                                        return CommonClientManager.getFileDescriptor(bundle, str);
                                }
                            }
                        });
        final int i3 = 2;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "upload",
                        new IServiceHandler() { // from class:
                                                // com.samsung.android.scloud.lib.storage.internal.SyncClientManager.1
                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                StringBuilder sb;
                                switch (i3) {
                                    case 0:
                                        LOG.i("SyncClientManager", "[" + str + "] Sync Prepare");
                                        return ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .prepare(context2, "Sync");
                                    case 1:
                                        LOG.i(
                                                "SyncClientManager",
                                                "[" + str + "] GET_LOCAL_CHANGES");
                                        List header =
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .getHeader(context2, bundle);
                                        Bundle bundle2 = new Bundle();
                                        ArrayList arrayList = (ArrayList) header;
                                        int size = arrayList.size();
                                        if (size > 0) {
                                            String[] strArr = new String[size];
                                            long[] jArr = new long[size];
                                            String[] strArr2 = new String[size];
                                            String[] strArr3 = new String[size];
                                            for (int i22 = 0; i22 < size; i22++) {
                                                Header header2 = (Header) arrayList.get(i22);
                                                strArr[i22] = header2.localId;
                                                strArr3[i22] = header2.recordId;
                                                jArr[i22] = header2.timeStamp;
                                                strArr2[i22] = header2.statue;
                                            }
                                            bundle2.putStringArray("local_id", strArr);
                                            bundle2.putStringArray("record_id", strArr3);
                                            bundle2.putLongArray(
                                                    PhoneRestrictionPolicy.TIMESTAMP, jArr);
                                            bundle2.putStringArray(
                                                    IMSParameter.CALL.STATUS, strArr2);
                                        }
                                        bundle2.putBoolean("is_success", true);
                                        return bundle2;
                                    case 2:
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] UPLOAD", "SyncClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("record_pfd");
                                        String[] stringArray = bundle.getStringArray("record_id");
                                        String[] stringArray2 = bundle.getStringArray("local_id");
                                        boolean z = false;
                                        z = false;
                                        z = false;
                                        if (stringArray != null
                                                && stringArray2 != null
                                                && parcelFileDescriptor != null) {
                                            SyncRecordWriter syncRecordWriter =
                                                    new SyncRecordWriter(parcelFileDescriptor);
                                            try {
                                                try {
                                                    JsonWriter jsonWriter =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    HashMap hashMap = new HashMap();
                                                    ArrayList arrayList2 = new ArrayList();
                                                    int length = stringArray2.length;
                                                    for (int i32 = 0; i32 < length; i32++) {
                                                        hashMap.put(
                                                                stringArray2[i32],
                                                                stringArray[i32]);
                                                        arrayList2.add(stringArray2[i32]);
                                                    }
                                                    List data =
                                                            ((SCloudWifiConfigSyncImpl)
                                                                            sCloudWifiConfigSyncImpl)
                                                                    .getData(context2, arrayList2);
                                                    syncRecordWriter.openObject();
                                                    Iterator it = ((ArrayList) data).iterator();
                                                    while (it.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it.next();
                                                        LOG.i("SyncClientManager", "dataSet : ");
                                                        JSONObject jSONObject =
                                                                recordDataSet.body.itemData;
                                                        Header header3 = recordDataSet.header;
                                                        String str2 = header3.recordId;
                                                        if (str2 == null) {
                                                            str2 =
                                                                    (String)
                                                                            hashMap.get(
                                                                                    header3.localId);
                                                            jSONObject.put("record_id", str2);
                                                        }
                                                        syncRecordWriter.addRecordAndFiles(
                                                                str2,
                                                                String.valueOf(header3.timeStamp),
                                                                jSONObject.toString(),
                                                                recordDataSet.body.fileList);
                                                    }
                                                    JsonWriter jsonWriter2 =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter2 != null) {
                                                        try {
                                                            jsonWriter2.endArray();
                                                            syncRecordWriter.jsonWriter.endObject();
                                                            syncRecordWriter.jsonWriter.flush();
                                                        } catch (IOException e3) {
                                                            e3.printStackTrace();
                                                        }
                                                    }
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e4) {
                                                        e = e4;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                } catch (Exception e5) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD parsing error : "
                                                                    + e5.getMessage(),
                                                            null);
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e6) {
                                                        e = e6;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                }
                                                z = true;
                                            } catch (Throwable th) {
                                                try {
                                                    syncRecordWriter.end();
                                                    syncRecordWriter.close();
                                                } catch (Exception e7) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD error : " + e7.getMessage(),
                                                            null);
                                                }
                                                throw th;
                                            }
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 3:
                                        Bundle m2 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] DELETE", "SyncClientManager");
                                        String[] stringArray3 = bundle.getStringArray("record_id");
                                        ArrayList arrayList3 = new ArrayList();
                                        for (String str3 : stringArray3) {
                                            arrayList3.add(
                                                    new RecordDataSet(
                                                            new Header(0L, str3, null, "delete"),
                                                            null));
                                        }
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .setData(context2, arrayList3);
                                        m2.putBoolean("is_success", true);
                                        return m2;
                                    case 4:
                                        Bundle m3 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] UPLOAD_COMPLETE",
                                                        "SyncClientManager");
                                        String[] stringArray4 = bundle.getStringArray("record_id");
                                        String[] stringArray5 = bundle.getStringArray("local_id");
                                        ArrayList arrayList4 = new ArrayList();
                                        boolean z2 = false;
                                        int i4 = 0;
                                        z2 = false;
                                        if (stringArray4 != null && stringArray5 != null) {
                                            int length2 = stringArray4.length;
                                            boolean z3 = false;
                                            while (i4 < length2) {
                                                arrayList4.add(
                                                        new RecordDataSet(
                                                                new Header(
                                                                        0L,
                                                                        stringArray4[i4],
                                                                        stringArray5[i4],
                                                                        "uploadComplete"),
                                                                null));
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .setData(context2, arrayList4);
                                                i4++;
                                                z3 = true;
                                            }
                                            z2 = z3;
                                        }
                                        m3.putBoolean("is_success", z2);
                                        return m3;
                                    case 5:
                                        Bundle m4 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] FINISH", "SyncClientManager");
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .finish(context2, "sync", bundle);
                                        return m4;
                                    default:
                                        ((SyncClientManager) sCloudWifiConfigSyncImpl).getClass();
                                        return CommonClientManager.getFileDescriptor(bundle, str);
                                }
                            }
                        });
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "download",
                        new IServiceHandler() { // from class:
                                                // com.samsung.android.scloud.lib.storage.internal.SyncClientManager.4
                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                boolean z;
                                Bundle m =
                                        BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                "[", str, "] DOWNLOAD", "SyncClientManager");
                                ParcelFileDescriptor parcelFileDescriptor =
                                        (ParcelFileDescriptor) bundle.getParcelable("record_pfd");
                                SyncClientManager.this.recordDataHelper.sourceKey = str;
                                new ArrayList();
                                SyncRecordReader syncRecordReader =
                                        new SyncRecordReader(parcelFileDescriptor);
                                JsonReader jsonReader = syncRecordReader.jsonReader;
                                if (jsonReader != null) {
                                    try {
                                        jsonReader.beginArray();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                try {
                                    try {
                                        sCloudWifiConfigSyncImpl.setData(
                                                context2, syncRecordReader.getRecordDataSetList());
                                        syncRecordReader.close();
                                        z = true;
                                    } catch (IOException | JSONException e3) {
                                        LOG.i("SyncClientManager", "[" + str + "] DOWNLOAD " + e3);
                                        syncRecordReader.close();
                                        z = false;
                                    }
                                    m.putBoolean("is_success", z);
                                    return m;
                                } catch (Throwable th) {
                                    syncRecordReader.close();
                                    throw th;
                                }
                            }
                        });
        final int i4 = 3;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "delete",
                        new IServiceHandler() { // from class:
                                                // com.samsung.android.scloud.lib.storage.internal.SyncClientManager.1
                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                StringBuilder sb;
                                switch (i4) {
                                    case 0:
                                        LOG.i("SyncClientManager", "[" + str + "] Sync Prepare");
                                        return ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .prepare(context2, "Sync");
                                    case 1:
                                        LOG.i(
                                                "SyncClientManager",
                                                "[" + str + "] GET_LOCAL_CHANGES");
                                        List header =
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .getHeader(context2, bundle);
                                        Bundle bundle2 = new Bundle();
                                        ArrayList arrayList = (ArrayList) header;
                                        int size = arrayList.size();
                                        if (size > 0) {
                                            String[] strArr = new String[size];
                                            long[] jArr = new long[size];
                                            String[] strArr2 = new String[size];
                                            String[] strArr3 = new String[size];
                                            for (int i22 = 0; i22 < size; i22++) {
                                                Header header2 = (Header) arrayList.get(i22);
                                                strArr[i22] = header2.localId;
                                                strArr3[i22] = header2.recordId;
                                                jArr[i22] = header2.timeStamp;
                                                strArr2[i22] = header2.statue;
                                            }
                                            bundle2.putStringArray("local_id", strArr);
                                            bundle2.putStringArray("record_id", strArr3);
                                            bundle2.putLongArray(
                                                    PhoneRestrictionPolicy.TIMESTAMP, jArr);
                                            bundle2.putStringArray(
                                                    IMSParameter.CALL.STATUS, strArr2);
                                        }
                                        bundle2.putBoolean("is_success", true);
                                        return bundle2;
                                    case 2:
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] UPLOAD", "SyncClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("record_pfd");
                                        String[] stringArray = bundle.getStringArray("record_id");
                                        String[] stringArray2 = bundle.getStringArray("local_id");
                                        boolean z = false;
                                        z = false;
                                        z = false;
                                        if (stringArray != null
                                                && stringArray2 != null
                                                && parcelFileDescriptor != null) {
                                            SyncRecordWriter syncRecordWriter =
                                                    new SyncRecordWriter(parcelFileDescriptor);
                                            try {
                                                try {
                                                    JsonWriter jsonWriter =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    HashMap hashMap = new HashMap();
                                                    ArrayList arrayList2 = new ArrayList();
                                                    int length = stringArray2.length;
                                                    for (int i32 = 0; i32 < length; i32++) {
                                                        hashMap.put(
                                                                stringArray2[i32],
                                                                stringArray[i32]);
                                                        arrayList2.add(stringArray2[i32]);
                                                    }
                                                    List data =
                                                            ((SCloudWifiConfigSyncImpl)
                                                                            sCloudWifiConfigSyncImpl)
                                                                    .getData(context2, arrayList2);
                                                    syncRecordWriter.openObject();
                                                    Iterator it = ((ArrayList) data).iterator();
                                                    while (it.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it.next();
                                                        LOG.i("SyncClientManager", "dataSet : ");
                                                        JSONObject jSONObject =
                                                                recordDataSet.body.itemData;
                                                        Header header3 = recordDataSet.header;
                                                        String str2 = header3.recordId;
                                                        if (str2 == null) {
                                                            str2 =
                                                                    (String)
                                                                            hashMap.get(
                                                                                    header3.localId);
                                                            jSONObject.put("record_id", str2);
                                                        }
                                                        syncRecordWriter.addRecordAndFiles(
                                                                str2,
                                                                String.valueOf(header3.timeStamp),
                                                                jSONObject.toString(),
                                                                recordDataSet.body.fileList);
                                                    }
                                                    JsonWriter jsonWriter2 =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter2 != null) {
                                                        try {
                                                            jsonWriter2.endArray();
                                                            syncRecordWriter.jsonWriter.endObject();
                                                            syncRecordWriter.jsonWriter.flush();
                                                        } catch (IOException e3) {
                                                            e3.printStackTrace();
                                                        }
                                                    }
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e4) {
                                                        e = e4;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                } catch (Exception e5) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD parsing error : "
                                                                    + e5.getMessage(),
                                                            null);
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e6) {
                                                        e = e6;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                }
                                                z = true;
                                            } catch (Throwable th) {
                                                try {
                                                    syncRecordWriter.end();
                                                    syncRecordWriter.close();
                                                } catch (Exception e7) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD error : " + e7.getMessage(),
                                                            null);
                                                }
                                                throw th;
                                            }
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 3:
                                        Bundle m2 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] DELETE", "SyncClientManager");
                                        String[] stringArray3 = bundle.getStringArray("record_id");
                                        ArrayList arrayList3 = new ArrayList();
                                        for (String str3 : stringArray3) {
                                            arrayList3.add(
                                                    new RecordDataSet(
                                                            new Header(0L, str3, null, "delete"),
                                                            null));
                                        }
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .setData(context2, arrayList3);
                                        m2.putBoolean("is_success", true);
                                        return m2;
                                    case 4:
                                        Bundle m3 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] UPLOAD_COMPLETE",
                                                        "SyncClientManager");
                                        String[] stringArray4 = bundle.getStringArray("record_id");
                                        String[] stringArray5 = bundle.getStringArray("local_id");
                                        ArrayList arrayList4 = new ArrayList();
                                        boolean z2 = false;
                                        int i42 = 0;
                                        z2 = false;
                                        if (stringArray4 != null && stringArray5 != null) {
                                            int length2 = stringArray4.length;
                                            boolean z3 = false;
                                            while (i42 < length2) {
                                                arrayList4.add(
                                                        new RecordDataSet(
                                                                new Header(
                                                                        0L,
                                                                        stringArray4[i42],
                                                                        stringArray5[i42],
                                                                        "uploadComplete"),
                                                                null));
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .setData(context2, arrayList4);
                                                i42++;
                                                z3 = true;
                                            }
                                            z2 = z3;
                                        }
                                        m3.putBoolean("is_success", z2);
                                        return m3;
                                    case 5:
                                        Bundle m4 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] FINISH", "SyncClientManager");
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .finish(context2, "sync", bundle);
                                        return m4;
                                    default:
                                        ((SyncClientManager) sCloudWifiConfigSyncImpl).getClass();
                                        return CommonClientManager.getFileDescriptor(bundle, str);
                                }
                            }
                        });
        final int i5 = 4;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "uploadComplete",
                        new IServiceHandler() { // from class:
                                                // com.samsung.android.scloud.lib.storage.internal.SyncClientManager.1
                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                StringBuilder sb;
                                switch (i5) {
                                    case 0:
                                        LOG.i("SyncClientManager", "[" + str + "] Sync Prepare");
                                        return ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .prepare(context2, "Sync");
                                    case 1:
                                        LOG.i(
                                                "SyncClientManager",
                                                "[" + str + "] GET_LOCAL_CHANGES");
                                        List header =
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .getHeader(context2, bundle);
                                        Bundle bundle2 = new Bundle();
                                        ArrayList arrayList = (ArrayList) header;
                                        int size = arrayList.size();
                                        if (size > 0) {
                                            String[] strArr = new String[size];
                                            long[] jArr = new long[size];
                                            String[] strArr2 = new String[size];
                                            String[] strArr3 = new String[size];
                                            for (int i22 = 0; i22 < size; i22++) {
                                                Header header2 = (Header) arrayList.get(i22);
                                                strArr[i22] = header2.localId;
                                                strArr3[i22] = header2.recordId;
                                                jArr[i22] = header2.timeStamp;
                                                strArr2[i22] = header2.statue;
                                            }
                                            bundle2.putStringArray("local_id", strArr);
                                            bundle2.putStringArray("record_id", strArr3);
                                            bundle2.putLongArray(
                                                    PhoneRestrictionPolicy.TIMESTAMP, jArr);
                                            bundle2.putStringArray(
                                                    IMSParameter.CALL.STATUS, strArr2);
                                        }
                                        bundle2.putBoolean("is_success", true);
                                        return bundle2;
                                    case 2:
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] UPLOAD", "SyncClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("record_pfd");
                                        String[] stringArray = bundle.getStringArray("record_id");
                                        String[] stringArray2 = bundle.getStringArray("local_id");
                                        boolean z = false;
                                        z = false;
                                        z = false;
                                        if (stringArray != null
                                                && stringArray2 != null
                                                && parcelFileDescriptor != null) {
                                            SyncRecordWriter syncRecordWriter =
                                                    new SyncRecordWriter(parcelFileDescriptor);
                                            try {
                                                try {
                                                    JsonWriter jsonWriter =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    HashMap hashMap = new HashMap();
                                                    ArrayList arrayList2 = new ArrayList();
                                                    int length = stringArray2.length;
                                                    for (int i32 = 0; i32 < length; i32++) {
                                                        hashMap.put(
                                                                stringArray2[i32],
                                                                stringArray[i32]);
                                                        arrayList2.add(stringArray2[i32]);
                                                    }
                                                    List data =
                                                            ((SCloudWifiConfigSyncImpl)
                                                                            sCloudWifiConfigSyncImpl)
                                                                    .getData(context2, arrayList2);
                                                    syncRecordWriter.openObject();
                                                    Iterator it = ((ArrayList) data).iterator();
                                                    while (it.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it.next();
                                                        LOG.i("SyncClientManager", "dataSet : ");
                                                        JSONObject jSONObject =
                                                                recordDataSet.body.itemData;
                                                        Header header3 = recordDataSet.header;
                                                        String str2 = header3.recordId;
                                                        if (str2 == null) {
                                                            str2 =
                                                                    (String)
                                                                            hashMap.get(
                                                                                    header3.localId);
                                                            jSONObject.put("record_id", str2);
                                                        }
                                                        syncRecordWriter.addRecordAndFiles(
                                                                str2,
                                                                String.valueOf(header3.timeStamp),
                                                                jSONObject.toString(),
                                                                recordDataSet.body.fileList);
                                                    }
                                                    JsonWriter jsonWriter2 =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter2 != null) {
                                                        try {
                                                            jsonWriter2.endArray();
                                                            syncRecordWriter.jsonWriter.endObject();
                                                            syncRecordWriter.jsonWriter.flush();
                                                        } catch (IOException e3) {
                                                            e3.printStackTrace();
                                                        }
                                                    }
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e4) {
                                                        e = e4;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                } catch (Exception e5) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD parsing error : "
                                                                    + e5.getMessage(),
                                                            null);
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e6) {
                                                        e = e6;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                }
                                                z = true;
                                            } catch (Throwable th) {
                                                try {
                                                    syncRecordWriter.end();
                                                    syncRecordWriter.close();
                                                } catch (Exception e7) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD error : " + e7.getMessage(),
                                                            null);
                                                }
                                                throw th;
                                            }
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 3:
                                        Bundle m2 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] DELETE", "SyncClientManager");
                                        String[] stringArray3 = bundle.getStringArray("record_id");
                                        ArrayList arrayList3 = new ArrayList();
                                        for (String str3 : stringArray3) {
                                            arrayList3.add(
                                                    new RecordDataSet(
                                                            new Header(0L, str3, null, "delete"),
                                                            null));
                                        }
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .setData(context2, arrayList3);
                                        m2.putBoolean("is_success", true);
                                        return m2;
                                    case 4:
                                        Bundle m3 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] UPLOAD_COMPLETE",
                                                        "SyncClientManager");
                                        String[] stringArray4 = bundle.getStringArray("record_id");
                                        String[] stringArray5 = bundle.getStringArray("local_id");
                                        ArrayList arrayList4 = new ArrayList();
                                        boolean z2 = false;
                                        int i42 = 0;
                                        z2 = false;
                                        if (stringArray4 != null && stringArray5 != null) {
                                            int length2 = stringArray4.length;
                                            boolean z3 = false;
                                            while (i42 < length2) {
                                                arrayList4.add(
                                                        new RecordDataSet(
                                                                new Header(
                                                                        0L,
                                                                        stringArray4[i42],
                                                                        stringArray5[i42],
                                                                        "uploadComplete"),
                                                                null));
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .setData(context2, arrayList4);
                                                i42++;
                                                z3 = true;
                                            }
                                            z2 = z3;
                                        }
                                        m3.putBoolean("is_success", z2);
                                        return m3;
                                    case 5:
                                        Bundle m4 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] FINISH", "SyncClientManager");
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .finish(context2, "sync", bundle);
                                        return m4;
                                    default:
                                        ((SyncClientManager) sCloudWifiConfigSyncImpl).getClass();
                                        return CommonClientManager.getFileDescriptor(bundle, str);
                                }
                            }
                        });
        final int i6 = 5;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "finish",
                        new IServiceHandler() { // from class:
                                                // com.samsung.android.scloud.lib.storage.internal.SyncClientManager.1
                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                StringBuilder sb;
                                switch (i6) {
                                    case 0:
                                        LOG.i("SyncClientManager", "[" + str + "] Sync Prepare");
                                        return ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .prepare(context2, "Sync");
                                    case 1:
                                        LOG.i(
                                                "SyncClientManager",
                                                "[" + str + "] GET_LOCAL_CHANGES");
                                        List header =
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .getHeader(context2, bundle);
                                        Bundle bundle2 = new Bundle();
                                        ArrayList arrayList = (ArrayList) header;
                                        int size = arrayList.size();
                                        if (size > 0) {
                                            String[] strArr = new String[size];
                                            long[] jArr = new long[size];
                                            String[] strArr2 = new String[size];
                                            String[] strArr3 = new String[size];
                                            for (int i22 = 0; i22 < size; i22++) {
                                                Header header2 = (Header) arrayList.get(i22);
                                                strArr[i22] = header2.localId;
                                                strArr3[i22] = header2.recordId;
                                                jArr[i22] = header2.timeStamp;
                                                strArr2[i22] = header2.statue;
                                            }
                                            bundle2.putStringArray("local_id", strArr);
                                            bundle2.putStringArray("record_id", strArr3);
                                            bundle2.putLongArray(
                                                    PhoneRestrictionPolicy.TIMESTAMP, jArr);
                                            bundle2.putStringArray(
                                                    IMSParameter.CALL.STATUS, strArr2);
                                        }
                                        bundle2.putBoolean("is_success", true);
                                        return bundle2;
                                    case 2:
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] UPLOAD", "SyncClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("record_pfd");
                                        String[] stringArray = bundle.getStringArray("record_id");
                                        String[] stringArray2 = bundle.getStringArray("local_id");
                                        boolean z = false;
                                        z = false;
                                        z = false;
                                        if (stringArray != null
                                                && stringArray2 != null
                                                && parcelFileDescriptor != null) {
                                            SyncRecordWriter syncRecordWriter =
                                                    new SyncRecordWriter(parcelFileDescriptor);
                                            try {
                                                try {
                                                    JsonWriter jsonWriter =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    HashMap hashMap = new HashMap();
                                                    ArrayList arrayList2 = new ArrayList();
                                                    int length = stringArray2.length;
                                                    for (int i32 = 0; i32 < length; i32++) {
                                                        hashMap.put(
                                                                stringArray2[i32],
                                                                stringArray[i32]);
                                                        arrayList2.add(stringArray2[i32]);
                                                    }
                                                    List data =
                                                            ((SCloudWifiConfigSyncImpl)
                                                                            sCloudWifiConfigSyncImpl)
                                                                    .getData(context2, arrayList2);
                                                    syncRecordWriter.openObject();
                                                    Iterator it = ((ArrayList) data).iterator();
                                                    while (it.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it.next();
                                                        LOG.i("SyncClientManager", "dataSet : ");
                                                        JSONObject jSONObject =
                                                                recordDataSet.body.itemData;
                                                        Header header3 = recordDataSet.header;
                                                        String str2 = header3.recordId;
                                                        if (str2 == null) {
                                                            str2 =
                                                                    (String)
                                                                            hashMap.get(
                                                                                    header3.localId);
                                                            jSONObject.put("record_id", str2);
                                                        }
                                                        syncRecordWriter.addRecordAndFiles(
                                                                str2,
                                                                String.valueOf(header3.timeStamp),
                                                                jSONObject.toString(),
                                                                recordDataSet.body.fileList);
                                                    }
                                                    JsonWriter jsonWriter2 =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter2 != null) {
                                                        try {
                                                            jsonWriter2.endArray();
                                                            syncRecordWriter.jsonWriter.endObject();
                                                            syncRecordWriter.jsonWriter.flush();
                                                        } catch (IOException e3) {
                                                            e3.printStackTrace();
                                                        }
                                                    }
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e4) {
                                                        e = e4;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                } catch (Exception e5) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD parsing error : "
                                                                    + e5.getMessage(),
                                                            null);
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e6) {
                                                        e = e6;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                }
                                                z = true;
                                            } catch (Throwable th) {
                                                try {
                                                    syncRecordWriter.end();
                                                    syncRecordWriter.close();
                                                } catch (Exception e7) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD error : " + e7.getMessage(),
                                                            null);
                                                }
                                                throw th;
                                            }
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 3:
                                        Bundle m2 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] DELETE", "SyncClientManager");
                                        String[] stringArray3 = bundle.getStringArray("record_id");
                                        ArrayList arrayList3 = new ArrayList();
                                        for (String str3 : stringArray3) {
                                            arrayList3.add(
                                                    new RecordDataSet(
                                                            new Header(0L, str3, null, "delete"),
                                                            null));
                                        }
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .setData(context2, arrayList3);
                                        m2.putBoolean("is_success", true);
                                        return m2;
                                    case 4:
                                        Bundle m3 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] UPLOAD_COMPLETE",
                                                        "SyncClientManager");
                                        String[] stringArray4 = bundle.getStringArray("record_id");
                                        String[] stringArray5 = bundle.getStringArray("local_id");
                                        ArrayList arrayList4 = new ArrayList();
                                        boolean z2 = false;
                                        int i42 = 0;
                                        z2 = false;
                                        if (stringArray4 != null && stringArray5 != null) {
                                            int length2 = stringArray4.length;
                                            boolean z3 = false;
                                            while (i42 < length2) {
                                                arrayList4.add(
                                                        new RecordDataSet(
                                                                new Header(
                                                                        0L,
                                                                        stringArray4[i42],
                                                                        stringArray5[i42],
                                                                        "uploadComplete"),
                                                                null));
                                                ((SCloudWifiConfigSyncImpl)
                                                                sCloudWifiConfigSyncImpl)
                                                        .setData(context2, arrayList4);
                                                i42++;
                                                z3 = true;
                                            }
                                            z2 = z3;
                                        }
                                        m3.putBoolean("is_success", z2);
                                        return m3;
                                    case 5:
                                        Bundle m4 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] FINISH", "SyncClientManager");
                                        ((SCloudWifiConfigSyncImpl) sCloudWifiConfigSyncImpl)
                                                .finish(context2, "sync", bundle);
                                        return m4;
                                    default:
                                        ((SyncClientManager) sCloudWifiConfigSyncImpl).getClass();
                                        return CommonClientManager.getFileDescriptor(bundle, str);
                                }
                            }
                        });
        final int i7 = 6;
        ((HashMap) this.serviceHandlerMap)
                .put(
                        "getFileDescriptor",
                        new IServiceHandler() { // from class:
                                                // com.samsung.android.scloud.lib.storage.internal.SyncClientManager.1
                            @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                            public final Bundle handleServiceAction(
                                    Context context2, Object obj, String str, Bundle bundle) {
                                StringBuilder sb;
                                switch (i7) {
                                    case 0:
                                        LOG.i("SyncClientManager", "[" + str + "] Sync Prepare");
                                        return ((SCloudWifiConfigSyncImpl) this)
                                                .prepare(context2, "Sync");
                                    case 1:
                                        LOG.i(
                                                "SyncClientManager",
                                                "[" + str + "] GET_LOCAL_CHANGES");
                                        List header =
                                                ((SCloudWifiConfigSyncImpl) this)
                                                        .getHeader(context2, bundle);
                                        Bundle bundle2 = new Bundle();
                                        ArrayList arrayList = (ArrayList) header;
                                        int size = arrayList.size();
                                        if (size > 0) {
                                            String[] strArr = new String[size];
                                            long[] jArr = new long[size];
                                            String[] strArr2 = new String[size];
                                            String[] strArr3 = new String[size];
                                            for (int i22 = 0; i22 < size; i22++) {
                                                Header header2 = (Header) arrayList.get(i22);
                                                strArr[i22] = header2.localId;
                                                strArr3[i22] = header2.recordId;
                                                jArr[i22] = header2.timeStamp;
                                                strArr2[i22] = header2.statue;
                                            }
                                            bundle2.putStringArray("local_id", strArr);
                                            bundle2.putStringArray("record_id", strArr3);
                                            bundle2.putLongArray(
                                                    PhoneRestrictionPolicy.TIMESTAMP, jArr);
                                            bundle2.putStringArray(
                                                    IMSParameter.CALL.STATUS, strArr2);
                                        }
                                        bundle2.putBoolean("is_success", true);
                                        return bundle2;
                                    case 2:
                                        Bundle m =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] UPLOAD", "SyncClientManager");
                                        ParcelFileDescriptor parcelFileDescriptor =
                                                (ParcelFileDescriptor)
                                                        bundle.getParcelable("record_pfd");
                                        String[] stringArray = bundle.getStringArray("record_id");
                                        String[] stringArray2 = bundle.getStringArray("local_id");
                                        boolean z = false;
                                        z = false;
                                        z = false;
                                        if (stringArray != null
                                                && stringArray2 != null
                                                && parcelFileDescriptor != null) {
                                            SyncRecordWriter syncRecordWriter =
                                                    new SyncRecordWriter(parcelFileDescriptor);
                                            try {
                                                try {
                                                    JsonWriter jsonWriter =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter != null) {
                                                        try {
                                                            jsonWriter.beginArray();
                                                        } catch (IOException e2) {
                                                            e2.printStackTrace();
                                                        }
                                                    }
                                                    HashMap hashMap = new HashMap();
                                                    ArrayList arrayList2 = new ArrayList();
                                                    int length = stringArray2.length;
                                                    for (int i32 = 0; i32 < length; i32++) {
                                                        hashMap.put(
                                                                stringArray2[i32],
                                                                stringArray[i32]);
                                                        arrayList2.add(stringArray2[i32]);
                                                    }
                                                    List data =
                                                            ((SCloudWifiConfigSyncImpl) this)
                                                                    .getData(context2, arrayList2);
                                                    syncRecordWriter.openObject();
                                                    Iterator it = ((ArrayList) data).iterator();
                                                    while (it.hasNext()) {
                                                        RecordDataSet recordDataSet =
                                                                (RecordDataSet) it.next();
                                                        LOG.i("SyncClientManager", "dataSet : ");
                                                        JSONObject jSONObject =
                                                                recordDataSet.body.itemData;
                                                        Header header3 = recordDataSet.header;
                                                        String str2 = header3.recordId;
                                                        if (str2 == null) {
                                                            str2 =
                                                                    (String)
                                                                            hashMap.get(
                                                                                    header3.localId);
                                                            jSONObject.put("record_id", str2);
                                                        }
                                                        syncRecordWriter.addRecordAndFiles(
                                                                str2,
                                                                String.valueOf(header3.timeStamp),
                                                                jSONObject.toString(),
                                                                recordDataSet.body.fileList);
                                                    }
                                                    JsonWriter jsonWriter2 =
                                                            syncRecordWriter.jsonWriter;
                                                    if (jsonWriter2 != null) {
                                                        try {
                                                            jsonWriter2.endArray();
                                                            syncRecordWriter.jsonWriter.endObject();
                                                            syncRecordWriter.jsonWriter.flush();
                                                        } catch (IOException e3) {
                                                            e3.printStackTrace();
                                                        }
                                                    }
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e4) {
                                                        e = e4;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                } catch (Exception e5) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD parsing error : "
                                                                    + e5.getMessage(),
                                                            null);
                                                    try {
                                                        syncRecordWriter.end();
                                                        syncRecordWriter.close();
                                                    } catch (Exception e6) {
                                                        e = e6;
                                                        sb = new StringBuilder("UPLOAD error : ");
                                                        sb.append(e.getMessage());
                                                        LOG.e(
                                                                "SyncClientManager",
                                                                sb.toString(),
                                                                null);
                                                        z = true;
                                                        m.putBoolean("is_success", z);
                                                        return m;
                                                    }
                                                }
                                                z = true;
                                            } catch (Throwable th) {
                                                try {
                                                    syncRecordWriter.end();
                                                    syncRecordWriter.close();
                                                } catch (Exception e7) {
                                                    LOG.e(
                                                            "SyncClientManager",
                                                            "UPLOAD error : " + e7.getMessage(),
                                                            null);
                                                }
                                                throw th;
                                            }
                                        }
                                        m.putBoolean("is_success", z);
                                        return m;
                                    case 3:
                                        Bundle m2 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] DELETE", "SyncClientManager");
                                        String[] stringArray3 = bundle.getStringArray("record_id");
                                        ArrayList arrayList3 = new ArrayList();
                                        for (String str3 : stringArray3) {
                                            arrayList3.add(
                                                    new RecordDataSet(
                                                            new Header(0L, str3, null, "delete"),
                                                            null));
                                        }
                                        ((SCloudWifiConfigSyncImpl) this)
                                                .setData(context2, arrayList3);
                                        m2.putBoolean("is_success", true);
                                        return m2;
                                    case 4:
                                        Bundle m3 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[",
                                                        str,
                                                        "] UPLOAD_COMPLETE",
                                                        "SyncClientManager");
                                        String[] stringArray4 = bundle.getStringArray("record_id");
                                        String[] stringArray5 = bundle.getStringArray("local_id");
                                        ArrayList arrayList4 = new ArrayList();
                                        boolean z2 = false;
                                        int i42 = 0;
                                        z2 = false;
                                        if (stringArray4 != null && stringArray5 != null) {
                                            int length2 = stringArray4.length;
                                            boolean z3 = false;
                                            while (i42 < length2) {
                                                arrayList4.add(
                                                        new RecordDataSet(
                                                                new Header(
                                                                        0L,
                                                                        stringArray4[i42],
                                                                        stringArray5[i42],
                                                                        "uploadComplete"),
                                                                null));
                                                ((SCloudWifiConfigSyncImpl) this)
                                                        .setData(context2, arrayList4);
                                                i42++;
                                                z3 = true;
                                            }
                                            z2 = z3;
                                        }
                                        m3.putBoolean("is_success", z2);
                                        return m3;
                                    case 5:
                                        Bundle m4 =
                                                BackupClientManager$6$$ExternalSyntheticOutline0.m(
                                                        "[", str, "] FINISH", "SyncClientManager");
                                        ((SCloudWifiConfigSyncImpl) this)
                                                .finish(context2, "sync", bundle);
                                        return m4;
                                    default:
                                        ((SyncClientManager) this).getClass();
                                        return CommonClientManager.getFileDescriptor(bundle, str);
                                }
                            }
                        });
    }

    @Override // com.samsung.android.scloud.oem.lib.common.IClientHelper
    public final Object getClient() {
        return this.syncDataClient;
    }
}
