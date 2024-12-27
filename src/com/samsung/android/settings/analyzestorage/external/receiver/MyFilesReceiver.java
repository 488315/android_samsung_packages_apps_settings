package com.samsung.android.settings.analyzestorage.external.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.storage.StorageManager;
import android.text.TextUtils;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceManager;

import com.android.settings.R;
import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;

import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.managers.NotificationMgr;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastReceiveCenter;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastType;
import com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter;
import com.samsung.android.settings.analyzestorage.presenter.utils.preference.PreferenceUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MyFilesReceiver extends BroadcastReceiver {
    public static void handleEject(String str) {
        StorageVolumeManager.StorageMountInfo mountInfoByUuid =
                StorageVolumeManager.getMountInfoByUuid(str);
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "handleEject() ] fsUuid : ", str, " , mountInfo : ");
        m.append(mountInfoByUuid != null ? mountInfoByUuid.toString() : "null");
        Log.d("MyFilesReceiver", m.toString());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        boolean z;
        if (intent == null) {
            Log.e("MyFilesReceiver", "onReceive() ] IGNORE - intent is null.");
        }
        String action = intent.getAction();
        Log.d("MyFilesReceiver", "onReceive() ] action : " + action);
        if (TextUtils.isEmpty(action)) {
            return;
        }
        action.getClass();
        switch (action.hashCode()) {
            case -1665311200:
                if (action.equals("android.intent.action.MEDIA_REMOVED")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case -1410684549:
                if (action.equals("android.os.storage.action.VOLUME_STATE_CHANGED")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case -19011148:
                if (action.equals("android.intent.action.LOCALE_CHANGED")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            case 502473491:
                if (action.equals("android.intent.action.TIMEZONE_CHANGED")) {
                    z = 3;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                String stringExtra = intent.getStringExtra("android.os.storage.extra.FS_UUID");
                if (TextUtils.isEmpty(stringExtra)) {
                    Log.e(
                            "MyFilesReceiver",
                            "handleMediaRemovedForSdCard() ] fsUuid is null. So, ignore"
                                + " MEDIA_REMOVED intent for SD card.");
                    break;
                } else {
                    StorageVolumeManager.updateStorageMountState(context);
                    handleEject(stringExtra);
                    break;
                }
            case true:
                String stringExtra2 = intent.getStringExtra("android.os.storage.extra.VOLUME_ID");
                int intExtra = intent.getIntExtra("android.os.storage.extra.VOLUME_STATE", -1);
                String stringExtra3 = intent.getStringExtra("android.os.storage.extra.FS_UUID");
                StringBuilder m =
                        PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                intExtra,
                                "handleVolumeStateChanged() ] volumeId : ",
                                stringExtra2,
                                " , state : ",
                                " , fsUuid : ");
                m.append(stringExtra3);
                Log.d("MyFilesReceiver", m.toString());
                if (TextUtils.isEmpty(stringExtra3)) {
                    int userIdFromVolumeId = UserInfoManager.getUserIdFromVolumeId(stringExtra2);
                    if (UserInfoManager.isCloneProfile(userIdFromVolumeId)) {
                        List list = StorageVolumeManager.sStorageMountInfoList;
                        StorageVolumeManager.ensureAppCloneStorageInfo(
                                (StorageManager) context.getSystemService("storage"));
                        if (intExtra != 2 && intExtra != 3) {
                            if (intExtra == 7 || intExtra == 8) {
                                PreferenceUtils.resetPrevMpGenerationForAnalyzeStorage(
                                        context, UserInfoManager.getMyUserId());
                                Log.d(
                                        "MyFilesReceiver",
                                        "handleCloneStorage() ] Clone Storage is de-activated."
                                            + " userId : "
                                                + userIdFromVolumeId);
                                break;
                            }
                        } else {
                            SharedPreferences.Editor edit =
                                    PreferenceManager.getDefaultSharedPreferences(context).edit();
                            edit.putBoolean("first_entry_after_clone_storage_activated", true);
                            edit.apply();
                            Log.d(
                                    "MyFilesReceiver",
                                    "handleCloneStorage() ] Clone Storage is activated. userId : "
                                            + userIdFromVolumeId);
                            break;
                        }
                    } else {
                        Log.e(
                                "MyFilesReceiver",
                                "handleCloneStorage() ] invalid info. userId : "
                                        + userIdFromVolumeId
                                        + " , volumeId : "
                                        + stringExtra2);
                        break;
                    }
                } else if (intExtra == 5) {
                    handleEject(stringExtra3);
                    StorageVolumeManager.StorageMountInfo mountInfoByUuid =
                            StorageVolumeManager.getMountInfoByUuid(stringExtra3);
                    if (mountInfoByUuid != null && DomainType.isSd(mountInfoByUuid.mDomainType)) {
                        PreferenceUtils.resetPrevMpGenerationForAnalyzeStorage(
                                context, UserInfoManager.getMyUserId());
                        break;
                    }
                } else if (intExtra == 0) {
                    StorageVolumeManager.updateStorageMountState(context);
                    break;
                } else if (intExtra == 2 || intExtra == 3) {
                    StorageVolumeManager.updateStorageMountState(context);
                    Log.d(
                            "MyFilesReceiver",
                            "handleMount() ] Call updateShortcutInfoAfterRequested()");
                    break;
                }
                break;
            case true:
                StringConverter.sCachedTime.evictAll();
                StringConverter.sCachedSize.evictAll();
                NotificationMgr notificationMgr = NotificationMgr.getInstance(context);
                notificationMgr.needOnGoingNotification(context);
                notificationMgr.updateChannelByID(
                        "my_files_channel_operation", context.getString(R.string.as_app_title));
                break;
            case true:
                StringConverter.sCachedTime.evictAll();
                BroadcastType broadcastType = BroadcastType.TIMEZONE_CHANGED;
                Log.d(
                        "MyFilesReceiver",
                        intent.getExtras() != null
                                ? intent.getExtras().getString("time-zone")
                                : "Intent's Extra is null.");
                int size = BroadcastReceiveCenter.sInstanceMap.size();
                for (int i = 0; i < size; i++) {
                    BroadcastReceiveCenter broadcastReceiveCenter =
                            (BroadcastReceiveCenter) BroadcastReceiveCenter.sInstanceMap.valueAt(i);
                    if (broadcastReceiveCenter != null) {
                        broadcastReceiveCenter.notify(
                                broadcastType, null, broadcastReceiveCenter.mListenerMap);
                    }
                }
                break;
        }
    }
}
