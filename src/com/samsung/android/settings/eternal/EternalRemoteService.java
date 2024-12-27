package com.samsung.android.settings.eternal;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.lib.episode.EpisodeConstant;
import com.samsung.android.lib.episode.EpisodeUtils;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.settings.eternal.defaultvalue.DefaultValueManager;
import com.samsung.android.settings.eternal.defaultvalue.JSONManager;
import com.samsung.android.settings.eternal.defaultvalue.XmlManager;
import com.samsung.android.util.SemLog;

import java.io.File;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EternalRemoteService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Messenger mMessenger = new Messenger(new EternalHandler(this));

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EternalHandler extends Handler {
        public final WeakReference mService;

        public EternalHandler(EternalRemoteService eternalRemoteService) {
            this.mService = new WeakReference(eternalRemoteService);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            final EternalRemoteService eternalRemoteService =
                    (EternalRemoteService) this.mService.get();
            if (eternalRemoteService != null) {
                int i = EternalRemoteService.$r8$clinit;
                if (message == null) {
                    SemLog.e("EternalRemoteService", "handleMessage() - msg is null");
                    return;
                }
                EpisodeUtils.isSettingAppSupportBnR();
                SemLog.d("EternalRemoteService", "handleMessage() - " + message.what);
                int i2 = message.what;
                if (i2 == 4096) {
                    File file = new File("/efs/sec_efs/SettingsBackup.json");
                    if (file.exists()) {
                        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                "deleteOldJSONFile result = ", "JSONManager", file.delete());
                    } else {
                        Log.d("JSONManager", "deleteOldJSONFile does not exist");
                    }
                    final int i3 = 0;
                    new Thread(
                                    new Runnable() { // from class:
                                        // com.samsung.android.settings.eternal.EternalRemoteService.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i3) {
                                                case 0:
                                                    DefaultValueManager defaultValueManager =
                                                            DefaultValueManager
                                                                    .DefaultValueManagerHolder
                                                                    .INSTANCE;
                                                    Context applicationContext =
                                                            eternalRemoteService
                                                                    .getApplicationContext();
                                                    defaultValueManager.migrationOldDefaultValue(
                                                            applicationContext);
                                                    Uri parse =
                                                            Uri.parse(
                                                                    "content://com.samsung.android.settings.eternal");
                                                    Bundle bundle = new Bundle();
                                                    defaultValueManager.mXmlManager.getClass();
                                                    HashMap keyListMapOfDTD =
                                                            XmlManager.getKeyListMapOfDTD(
                                                                    applicationContext);
                                                    Object obj =
                                                            keyListMapOfDTD == null
                                                                    ? null
                                                                    : (List)
                                                                            keyListMapOfDTD.get(
                                                                                    "Settings");
                                                    bundle.putSerializable(
                                                            "keyList",
                                                            obj != null
                                                                    ? (Serializable) obj
                                                                    : null);
                                                    try {
                                                        ContentResolver contentResolver =
                                                                applicationContext
                                                                        .getContentResolver();
                                                        String str = EpisodeConstant.DTD_VERSION;
                                                        Bundle call =
                                                                contentResolver.call(
                                                                        parse,
                                                                        "get_value",
                                                                        KnoxVpnPolicyConstants
                                                                                .NEW_FW,
                                                                        bundle);
                                                        if (call == null) {
                                                            Log.w(
                                                                    "DefaultValueManager",
                                                                    "fetchCreateDefaultValues()"
                                                                        + " settingsBackupResult is"
                                                                        + " null");
                                                        } else {
                                                            call.setClassLoader(
                                                                    Scene.class.getClassLoader());
                                                            ArrayList parcelableArrayList =
                                                                    call.getParcelableArrayList(
                                                                            "sceneList");
                                                            if (parcelableArrayList == null) {
                                                                Log.w(
                                                                        "DefaultValueManager",
                                                                        "fetchCreateDefaultValues()"
                                                                            + " backupSceneList is"
                                                                            + " null");
                                                            } else {
                                                                Log.d(
                                                                        "DefaultValueManager",
                                                                        "fetchCreateDefaultValues()"
                                                                            + " backupSceneList : "
                                                                                + parcelableArrayList
                                                                                        .size());
                                                                defaultValueManager.mJSONManager
                                                                        .getClass();
                                                                Log.d(
                                                                        "DefaultValueManager",
                                                                        "fetchCreateDefaultValues() updateDefaultJSONFile : "
                                                                                .concat(
                                                                                        JSONManager
                                                                                                        .updateDefaultJSONFile(
                                                                                                                parcelableArrayList)
                                                                                                ? "success"
                                                                                                : "fail"));
                                                            }
                                                        }
                                                        break;
                                                    } catch (Exception e) {
                                                        CloneBackend$$ExternalSyntheticOutline0.m(
                                                                e,
                                                                new StringBuilder(
                                                                        "fetchCreateDefaultValues()"
                                                                            + " "),
                                                                "DefaultValueManager");
                                                        return;
                                                    }
                                                default:
                                                    DefaultValueManager defaultValueManager2 =
                                                            DefaultValueManager
                                                                    .DefaultValueManagerHolder
                                                                    .INSTANCE;
                                                    defaultValueManager2.migrationOldDefaultValue(
                                                            eternalRemoteService
                                                                    .getApplicationContext());
                                                    ArrayList arrayList = new ArrayList();
                                                    defaultValueManager2.mJSONManager.getClass();
                                                    JSONManager.updateDefaultJSONFile(arrayList);
                                                    break;
                                            }
                                        }
                                    })
                            .start();
                    return;
                }
                if (i2 == 4097) {
                    final int i4 = 1;
                    new Thread(
                                    new Runnable() { // from class:
                                        // com.samsung.android.settings.eternal.EternalRemoteService.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i4) {
                                                case 0:
                                                    DefaultValueManager defaultValueManager =
                                                            DefaultValueManager
                                                                    .DefaultValueManagerHolder
                                                                    .INSTANCE;
                                                    Context applicationContext =
                                                            eternalRemoteService
                                                                    .getApplicationContext();
                                                    defaultValueManager.migrationOldDefaultValue(
                                                            applicationContext);
                                                    Uri parse =
                                                            Uri.parse(
                                                                    "content://com.samsung.android.settings.eternal");
                                                    Bundle bundle = new Bundle();
                                                    defaultValueManager.mXmlManager.getClass();
                                                    HashMap keyListMapOfDTD =
                                                            XmlManager.getKeyListMapOfDTD(
                                                                    applicationContext);
                                                    Object obj =
                                                            keyListMapOfDTD == null
                                                                    ? null
                                                                    : (List)
                                                                            keyListMapOfDTD.get(
                                                                                    "Settings");
                                                    bundle.putSerializable(
                                                            "keyList",
                                                            obj != null
                                                                    ? (Serializable) obj
                                                                    : null);
                                                    try {
                                                        ContentResolver contentResolver =
                                                                applicationContext
                                                                        .getContentResolver();
                                                        String str = EpisodeConstant.DTD_VERSION;
                                                        Bundle call =
                                                                contentResolver.call(
                                                                        parse,
                                                                        "get_value",
                                                                        KnoxVpnPolicyConstants
                                                                                .NEW_FW,
                                                                        bundle);
                                                        if (call == null) {
                                                            Log.w(
                                                                    "DefaultValueManager",
                                                                    "fetchCreateDefaultValues()"
                                                                        + " settingsBackupResult is"
                                                                        + " null");
                                                        } else {
                                                            call.setClassLoader(
                                                                    Scene.class.getClassLoader());
                                                            ArrayList parcelableArrayList =
                                                                    call.getParcelableArrayList(
                                                                            "sceneList");
                                                            if (parcelableArrayList == null) {
                                                                Log.w(
                                                                        "DefaultValueManager",
                                                                        "fetchCreateDefaultValues()"
                                                                            + " backupSceneList is"
                                                                            + " null");
                                                            } else {
                                                                Log.d(
                                                                        "DefaultValueManager",
                                                                        "fetchCreateDefaultValues()"
                                                                            + " backupSceneList : "
                                                                                + parcelableArrayList
                                                                                        .size());
                                                                defaultValueManager.mJSONManager
                                                                        .getClass();
                                                                Log.d(
                                                                        "DefaultValueManager",
                                                                        "fetchCreateDefaultValues() updateDefaultJSONFile : "
                                                                                .concat(
                                                                                        JSONManager
                                                                                                        .updateDefaultJSONFile(
                                                                                                                parcelableArrayList)
                                                                                                ? "success"
                                                                                                : "fail"));
                                                            }
                                                        }
                                                        break;
                                                    } catch (Exception e) {
                                                        CloneBackend$$ExternalSyntheticOutline0.m(
                                                                e,
                                                                new StringBuilder(
                                                                        "fetchCreateDefaultValues()"
                                                                            + " "),
                                                                "DefaultValueManager");
                                                        return;
                                                    }
                                                default:
                                                    DefaultValueManager defaultValueManager2 =
                                                            DefaultValueManager
                                                                    .DefaultValueManagerHolder
                                                                    .INSTANCE;
                                                    defaultValueManager2.migrationOldDefaultValue(
                                                            eternalRemoteService
                                                                    .getApplicationContext());
                                                    ArrayList arrayList = new ArrayList();
                                                    defaultValueManager2.mJSONManager.getClass();
                                                    JSONManager.updateDefaultJSONFile(arrayList);
                                                    break;
                                            }
                                        }
                                    })
                            .start();
                } else {
                    SemLog.e(
                            "EternalRemoteService",
                            "handleMessage() - Unsupported cmd : " + message.what);
                }
            }
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }
}
