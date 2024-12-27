package com.samsung.android.settings.analyzestorage.presenter.managers.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.text.TextUtils;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceManager;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.utils.CollectionUtils;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager;
import com.samsung.android.settings.analyzestorage.presenter.utils.preference.PreferenceUtils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BroadcastHandler$1 extends BroadcastReceiver {
    public final /* synthetic */ StorageBroadcastHandler this$0;

    public BroadcastHandler$1(StorageBroadcastHandler storageBroadcastHandler) {
        this.this$0 = storageBroadcastHandler;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, final Intent intent) {
        this.this$0.mHandler.postAtFrontOfQueue(
                new Runnable() { // from class:
                    // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastHandler$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BroadcastHandler$1 broadcastHandler$1 = BroadcastHandler$1.this;
                        final Context context2 = context;
                        Intent intent2 = intent;
                        StorageBroadcastHandler storageBroadcastHandler = broadcastHandler$1.this$0;
                        storageBroadcastHandler.getClass();
                        String action = intent2.getAction();
                        Log.d("StorageBroadcastHandler", "handleBroadcast() ] action : " + action);
                        if (TextUtils.isEmpty(action)) {
                            return;
                        }
                        action.getClass();
                        String str = null;
                        r4 = null;
                        r4 = null;
                        StorageVolumeManager.StorageMountInfo storageMountInfo = null;
                        str = null;
                        if (action.equals("android.intent.action.MEDIA_REMOVED")) {
                            Optional ofNullable =
                                    Optional.ofNullable(
                                            intent2.getStringExtra(
                                                    "android.os.storage.extra.FS_UUID"));
                            final ConcurrentHashMap concurrentHashMap =
                                    storageBroadcastHandler.mPendingEjectIntentMap;
                            Objects.requireNonNull(concurrentHashMap);
                            ofNullable.ifPresent(
                                    new Consumer() { // from class:
                                        // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.StorageBroadcastHandler$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            concurrentHashMap.remove((String) obj);
                                        }
                                    });
                            String stringExtra =
                                    intent2.getStringExtra("android.os.storage.extra.FS_UUID");
                            if (TextUtils.isEmpty(stringExtra)) {
                                Log.e("StorageVolumeUtils", "getFsUuid() ] EXTRA_FS_UUID is null.");
                                Uri data = intent2.getData();
                                if (data == null) {
                                    Log.d("StorageVolumeUtils", "getFsUuid() ] data is null");
                                } else {
                                    String path = data.getPath();
                                    if (path == null) {
                                        Log.d("StorageVolumeUtils", "getFsUuid() ] path is null");
                                    } else {
                                        str =
                                                path.substring(
                                                        path.lastIndexOf(File.separatorChar) + 1);
                                    }
                                }
                            } else {
                                str = stringExtra;
                            }
                            String m =
                                    ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("/storage"), File.separator, str);
                            Bundle bundle = new Bundle();
                            bundle.putString("path", m);
                            bundle.putInt("domainType", 1);
                            ((BroadcastReceiveCenter)
                                            storageBroadcastHandler.mNotifyBroadcastListener)
                                    .notifyDynamicBroadcast(BroadcastType.MEDIA_EJECTED, bundle);
                            StorageVolumeManager.updateStorageMountState(context2);
                            return;
                        }
                        if (action.equals("android.os.storage.action.VOLUME_STATE_CHANGED")) {
                            int intExtra =
                                    intent2.getIntExtra(
                                            "android.os.storage.extra.VOLUME_STATE", -1);
                            String stringExtra2 =
                                    intent2.getStringExtra("android.os.storage.extra.FS_UUID");
                            String stringExtra3 =
                                    intent2.getStringExtra("android.os.storage.extra.VOLUME_ID");
                            Log.d(
                                    "StorageBroadcastHandler",
                                    "handleVolumeStateChanged() ] state : "
                                            + intExtra
                                            + " , fsUuid : "
                                            + stringExtra2
                                            + " , volumeId : "
                                            + stringExtra3);
                            if (TextUtils.isEmpty(stringExtra2)) {
                                int userIdFromVolumeId =
                                        UserInfoManager.getUserIdFromVolumeId(stringExtra3);
                                if (!UserInfoManager.isCloneProfile(userIdFromVolumeId)) {
                                    Log.e(
                                            "StorageBroadcastHandler",
                                            "handleCloneStorage() ] invalid info. userId : "
                                                    + userIdFromVolumeId);
                                    return;
                                }
                                List list = StorageVolumeManager.sStorageMountInfoList;
                                StorageVolumeManager.ensureAppCloneStorageInfo(
                                        (StorageManager) context2.getSystemService("storage"));
                                Bundle bundle2 = new Bundle();
                                bundle2.putString(
                                        "path",
                                        "/storage/emulated" + File.separator + userIdFromVolumeId);
                                bundle2.putInt("domainType", 2);
                                if (intExtra == 2 || intExtra == 3) {
                                    SharedPreferences.Editor edit =
                                            PreferenceManager.getDefaultSharedPreferences(context2)
                                                    .edit();
                                    edit.putBoolean(
                                            "first_entry_after_clone_storage_activated", true);
                                    edit.apply();
                                    ((BroadcastReceiveCenter)
                                                    storageBroadcastHandler
                                                            .mNotifyBroadcastListener)
                                            .notifyDynamicBroadcast(
                                                    BroadcastType.MEDIA_MOUNTED, bundle2);
                                    Log.d(
                                            "StorageBroadcastHandler",
                                            "handleCloneStorage() ] notifyDynamicBroadcast"
                                                + " MEDIA_MOUNTED");
                                    return;
                                }
                                if (intExtra == 7 || intExtra == 8) {
                                    PreferenceUtils.resetPrevMpGenerationForAnalyzeStorage(
                                            context2, UserInfoManager.getMyUserId());
                                    ((BroadcastReceiveCenter)
                                                    storageBroadcastHandler
                                                            .mNotifyBroadcastListener)
                                            .notifyDynamicBroadcast(
                                                    BroadcastType.MEDIA_REMOVED, bundle2);
                                    Log.d(
                                            "StorageBroadcastHandler",
                                            "handleCloneStorage() ] notifyDynamicBroadcast"
                                                + " MEDIA_REMOVED");
                                    return;
                                }
                                return;
                            }
                            if (intExtra == 5) {
                                storageBroadcastHandler.mPendingEjectIntentMap.put(
                                        stringExtra2, intent2);
                                return;
                            }
                            if (intExtra == 0) {
                                Intent intent3 =
                                        (Intent)
                                                storageBroadcastHandler.mPendingEjectIntentMap.get(
                                                        stringExtra2);
                                if (intent3 != null
                                        && intent3.getIntExtra(
                                                        "android.os.storage.extra.VOLUME_STATE", -1)
                                                == 5) {
                                    StorageVolumeManager.StorageMountInfo mountInfoByUuid =
                                            StorageVolumeManager.getMountInfoByUuid(stringExtra2);
                                    StringBuilder m2 =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "handleEject() ] fsUuid : ",
                                                    stringExtra2,
                                                    " , mountInfo : ");
                                    m2.append(
                                            mountInfoByUuid != null
                                                    ? mountInfoByUuid.toString()
                                                    : null);
                                    Log.d("StorageBroadcastHandler", m2.toString());
                                    BroadcastType broadcastType = BroadcastType.MEDIA_EJECTED;
                                    if (mountInfoByUuid != null) {
                                        storageBroadcastHandler.notifyStorageBroadcast(
                                                broadcastType, mountInfoByUuid);
                                    } else {
                                        Bundle bundle3 = new Bundle();
                                        bundle3.putString(
                                                "path",
                                                "/mnt/media_rw" + File.separator + stringExtra2);
                                        bundle3.putInt("domainType", -1);
                                        ((BroadcastReceiveCenter)
                                                        storageBroadcastHandler
                                                                .mNotifyBroadcastListener)
                                                .notifyDynamicBroadcast(broadcastType, bundle3);
                                    }
                                    storageBroadcastHandler.mPendingEjectIntentMap.remove(
                                            stringExtra2);
                                }
                                final String stringExtra4 =
                                        intent2.getStringExtra("android.os.storage.extra.FS_UUID");
                                Log.d(
                                        "StorageBroadcastHandler",
                                        "handleUnMount() ] fsUuid : " + stringExtra4);
                                StorageVolumeManager.StorageMountInfo storageMountInfo2 =
                                        (StorageVolumeManager.StorageMountInfo)
                                                Optional.ofNullable(
                                                                StorageVolumeManager
                                                                        .getMountInfoByUuid(
                                                                                stringExtra4))
                                                        .orElseGet(
                                                                new Supplier() { // from class:
                                                                    // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.StorageBroadcastHandler$$ExternalSyntheticLambda0
                                                                    @Override // java.util.function.Supplier
                                                                    public final Object get() {
                                                                        Context context3 = context2;
                                                                        String str2 = stringExtra4;
                                                                        Log.d(
                                                                                "StorageBroadcastHandler",
                                                                                "handleUnMount() ]"
                                                                                    + " mount info"
                                                                                    + " is empty."
                                                                                    + " First"
                                                                                    + " updateStorageMountState,"
                                                                                    + " and reload"
                                                                                    + " mount info."
                                                                                    + " ");
                                                                        StorageVolumeManager
                                                                                .updateStorageMountState(
                                                                                        context3);
                                                                        return StorageVolumeManager
                                                                                .getMountInfoByUuid(
                                                                                        str2);
                                                                    }
                                                                });
                                if (storageMountInfo2 != null) {
                                    Log.d(
                                            "StorageBroadcastHandler",
                                            "handleUnMount() ] mountInfo : " + storageMountInfo2);
                                    storageBroadcastHandler.notifyStorageBroadcast(
                                            BroadcastType.MEDIA_UNMOUNTED, storageMountInfo2);
                                }
                                StorageVolumeManager.updateStorageMountState(context2);
                                return;
                            }
                            if (intExtra != 2 && intExtra != 3) {
                                if (intExtra == 7 || intExtra == 8) {
                                    storageBroadcastHandler.mPendingEjectIntentMap.remove(
                                            stringExtra2);
                                    Bundle bundle4 = new Bundle();
                                    String str2 = "/mnt/media_rw" + File.separator + stringExtra2;
                                    bundle4.putString("path", str2);
                                    bundle4.putInt("domainType", -1);
                                    Log.d(
                                            "StorageBroadcastHandler",
                                            "handleRemove() ] fsUuid : "
                                                    + stringExtra2
                                                    + " , ejectedDeviceFullPath : "
                                                    + str2);
                                    ((BroadcastReceiveCenter)
                                                    storageBroadcastHandler
                                                            .mNotifyBroadcastListener)
                                            .notifyDynamicBroadcast(
                                                    BroadcastType.MEDIA_REMOVED, bundle4);
                                    StorageVolumeManager.updateStorageMountState(context2);
                                    return;
                                }
                                return;
                            }
                            StorageVolumeManager.updateStorageMountState(context2);
                            String stringExtra5 =
                                    intent2.getStringExtra("android.os.storage.extra.VOLUME_ID");
                            Log.d(
                                    "StorageBroadcastHandler",
                                    "handleMount() ] volumeId : " + stringExtra5);
                            if (stringExtra5 != null && !TextUtils.isEmpty(stringExtra5)) {
                                synchronized (StorageVolumeManager.class) {
                                    try {
                                        Iterator it =
                                                CollectionUtils.getEmptyListIfNull(
                                                                StorageVolumeManager
                                                                        .sStorageMountInfoList)
                                                        .iterator();
                                        while (true) {
                                            if (!it.hasNext()) {
                                                break;
                                            }
                                            StorageVolumeManager.StorageMountInfo
                                                    storageMountInfo3 =
                                                            (StorageVolumeManager.StorageMountInfo)
                                                                    it.next();
                                            if (stringExtra5.equals(storageMountInfo3.mVolumeId)) {
                                                storageMountInfo = storageMountInfo3;
                                                break;
                                            }
                                        }
                                    } finally {
                                    }
                                }
                            }
                            StringBuilder sb = new StringBuilder("handleMount() ] mountInfo : ");
                            sb.append(
                                    storageMountInfo != null
                                            ? storageMountInfo.toString()
                                            : "null");
                            Log.d("StorageBroadcastHandler", sb.toString());
                            storageBroadcastHandler.notifyStorageBroadcast(
                                    BroadcastType.MEDIA_MOUNTED, storageMountInfo);
                        }
                    }
                });
    }
}
