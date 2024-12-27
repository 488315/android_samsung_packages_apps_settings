package com.samsung.android.settings.languagepack.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.gts.GtsResources$$ExternalSyntheticLambda1;
import com.samsung.android.settings.languagepack.constant.LanguagePackConstant;
import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.data.PackageInfo;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.languagepack.manager.LanguagePackManager;
import com.samsung.android.settings.languagepack.manager.LanguagePackManager$$ExternalSyntheticLambda8;
import com.samsung.android.settings.languagepack.manager.LanguagePackManager$$ExternalSyntheticLambda9;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LanguagePackDownloadService extends Service
        implements LanguagePackManager.LangPackDownloadListener,
                LanguagePackManager.LangPackVersionUpdateListener {
    public static LanguagePackServiceBinder mLanguagePackService;
    public DownloadTask mCurrentTask;
    public LanguagePackManager mLanguagePackManager;
    public NotificationManager mNotificationManager;
    public String mResultBroadcastPackage;
    public final LanguagePackServiceBinder mBinder = new LanguagePackServiceBinder();
    public final Queue mQueue = new LinkedList();
    public boolean mProcessing = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DownloadTask {
        public final Notification.Builder mCachedNotificationBuilder;
        public final LanguageInfo mLanguageInfo;

        public DownloadTask(
                LanguagePackDownloadService languagePackDownloadService,
                LanguageInfo languageInfo) {
            this.mLanguageInfo = languageInfo;
            languageInfo.mStatus = Status.STATUS_WAITING;
            Intent intent =
                    new Intent(
                            languagePackDownloadService.getApplicationContext(),
                            (Class<?>) LanguagePackDownloadService.class);
            intent.putExtra("command", "cancel");
            Resources resources =
                    languagePackDownloadService.getApplicationContext().getResources();
            Notification.Action build =
                    new Notification.Action.Builder(
                                    Icon.createWithResource(
                                            languagePackDownloadService.getApplicationContext(),
                                            R.drawable.ic_cancel),
                                    resources.getString(android.R.string.cancel),
                                    PendingIntent.getService(
                                            languagePackDownloadService.getApplicationContext(),
                                            0,
                                            intent,
                                            201326592))
                            .build();
            Notification.Builder smallIcon =
                    new Notification.Builder(
                                    languagePackDownloadService.getApplicationContext(),
                                    "downloads_progress")
                            .setSmallIcon(R.drawable.ic_settings_noti_panel);
            LanguagePackServiceBinder languagePackServiceBinder =
                    LanguagePackDownloadService.mLanguagePackService;
            this.mCachedNotificationBuilder =
                    smallIcon
                            .setContentTitle(
                                    languagePackDownloadService.getString(
                                            R.string.sec_offline_lang_pack_dialog_download_text,
                                            new Object[] {languageInfo.getDisplayName()}))
                            .setShowWhen(true)
                            .setWhen(System.currentTimeMillis())
                            .setColor(
                                    languagePackDownloadService
                                            .getApplicationContext()
                                            .getResources()
                                            .getColor(R.color.langpack_noti_background_color))
                            .addAction(build);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LanguagePackServiceBinder extends Binder {
        public LanguagePackServiceBinder() {}

        public final void requestCancelTask(final String str) {
            synchronized (LanguagePackDownloadService.this.mQueue) {
                LanguagePackDownloadService.this.mQueue.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.languagepack.service.LanguagePackDownloadService$LanguagePackServiceBinder$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        LanguagePackDownloadService.DownloadTask downloadTask =
                                                (LanguagePackDownloadService.DownloadTask) obj;
                                        return downloadTask.mLanguageInfo.mLanguageCode.equals(str)
                                                && downloadTask.mLanguageInfo.mStatus
                                                        == LanguagePackDownloadService.Status
                                                                .STATUS_WAITING;
                                    }
                                })
                        .findFirst()
                        .ifPresentOrElse(
                                new Consumer() { // from class:
                                                 // com.samsung.android.settings.languagepack.service.LanguagePackDownloadService$LanguagePackServiceBinder$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        LanguagePackDownloadService.LanguagePackServiceBinder
                                                languagePackServiceBinder =
                                                        LanguagePackDownloadService
                                                                .LanguagePackServiceBinder.this;
                                        String str2 = str;
                                        LanguagePackDownloadService.DownloadTask downloadTask =
                                                (LanguagePackDownloadService.DownloadTask) obj;
                                        LanguagePackDownloadService languagePackDownloadService =
                                                LanguagePackDownloadService.this;
                                        synchronized (languagePackDownloadService.mQueue) {
                                            try {
                                                if (((LinkedList)
                                                                languagePackDownloadService.mQueue)
                                                        .contains(downloadTask)) {
                                                    downloadTask.mLanguageInfo.mStatus =
                                                            LanguagePackDownloadService.Status
                                                                    .STATUS_CANCEL;
                                                    ((LinkedList)
                                                                    languagePackDownloadService
                                                                            .mQueue)
                                                            .remove(downloadTask);
                                                }
                                            } catch (Throwable th) {
                                                throw th;
                                            }
                                        }
                                        LanguagePackDownloadService.LanguagePackServiceBinder
                                                languagePackServiceBinder2 =
                                                        LanguagePackDownloadService
                                                                .mLanguagePackService;
                                        Log.i(
                                                "LanguagePackDownloadService",
                                                "requestCancelTask() canceled : " + str2);
                                    }
                                },
                                new LanguagePackDownloadService$$ExternalSyntheticLambda0(
                                        2, this, str));
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ProcessingRunnable implements Runnable {
        public ProcessingRunnable() {}

        @Override // java.lang.Runnable
        public final void run() {
            DownloadTask downloadTask;
            while (true) {
                synchronized (LanguagePackDownloadService.this.mQueue) {
                    try {
                        downloadTask =
                                (DownloadTask)
                                        ((LinkedList) LanguagePackDownloadService.this.mQueue)
                                                .poll();
                        LanguagePackDownloadService languagePackDownloadService =
                                LanguagePackDownloadService.this;
                        languagePackDownloadService.mCurrentTask = downloadTask;
                        if (downloadTask == null) {
                            languagePackDownloadService.mProcessing = false;
                            languagePackDownloadService.stopForeground(true);
                            return;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                LanguagePackServiceBinder languagePackServiceBinder =
                        LanguagePackDownloadService.mLanguagePackService;
                Log.d(
                        "LanguagePackDownloadService",
                        "[processDownload] start download task : "
                                + downloadTask.mLanguageInfo.mLanguageCode);
                LanguagePackDownloadService languagePackDownloadService2 =
                        LanguagePackDownloadService.this;
                languagePackDownloadService2.getClass();
                languagePackDownloadService2.updateNotification(
                        LanguagePackDownloadService.makeProgressNotification(downloadTask, null));
                LanguageInfo languageInfo = downloadTask.mLanguageInfo;
                languageInfo.mToastType = 0;
                LanguagePackDownloadService.this.mLanguagePackManager.startDownloadAndInstall(
                        downloadTask);
                while (true) {
                    Status status = languageInfo.mStatus;
                    if (status == Status.STATUS_WAITING
                            || status == Status.STATUS_DOWNLOADING
                            || status == Status.STATUS_INSTALLING) {
                        try {
                            LanguagePackServiceBinder languagePackServiceBinder2 =
                                    LanguagePackDownloadService.mLanguagePackService;
                            Log.d(
                                    "LanguagePackDownloadService",
                                    "[processDownload] wait for task finish");
                            Thread.sleep(500L);
                        } catch (Exception unused) {
                        }
                    }
                }
                final LanguagePackDownloadService languagePackDownloadService3 =
                        LanguagePackDownloadService.this;
                final int i = languageInfo.mToastType;
                languagePackDownloadService3.getClass();
                final String displayName = languageInfo.getDisplayName();
                new Handler(Looper.getMainLooper())
                        .postDelayed(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.languagepack.service.LanguagePackDownloadService$$ExternalSyntheticLambda4
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        String str;
                                        LanguagePackDownloadService languagePackDownloadService4 =
                                                LanguagePackDownloadService.this;
                                        int i2 = i;
                                        String str2 = displayName;
                                        LanguagePackDownloadService.LanguagePackServiceBinder
                                                languagePackServiceBinder3 =
                                                        LanguagePackDownloadService
                                                                .mLanguagePackService;
                                        if ((i2 & 4) == 4) {
                                            str =
                                                    languagePackDownloadService4.getString(
                                                            R.string
                                                                    .sec_offline_lang_pack_toast_not_enough_space);
                                        } else if ((i2 & 1) == 1) {
                                            str =
                                                    languagePackDownloadService4.getString(
                                                            R.string
                                                                    .sec_offline_lang_pack_toast_download_problem,
                                                            new Object[] {str2});
                                        } else if ((i2 & 2) == 2) {
                                            str =
                                                    languagePackDownloadService4.getString(
                                                            R.string
                                                                    .sec_offline_lang_pack_toast_install_problem,
                                                            new Object[] {str2});
                                        } else {
                                            languagePackDownloadService4.getClass();
                                            str = null;
                                        }
                                        if (TextUtils.isEmpty(str)) {
                                            return;
                                        }
                                        Toast.makeText(
                                                        languagePackDownloadService4
                                                                .getApplicationContext(),
                                                        str,
                                                        1)
                                                .show();
                                    }
                                },
                                50L);
                languageInfo.mToastType = 0;
                Log.d(
                        "LanguagePackDownloadService",
                        "[processDownload] finish download task : " + languageInfo.mLanguageCode);
                LanguagePackDownloadService.this.mCurrentTask = null;
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class Status {
        public static final /* synthetic */ Status[] $VALUES;
        public static final Status STATUS_CANCEL;
        public static final Status STATUS_DOWNLOADING;
        public static final Status STATUS_ERROR;
        public static final Status STATUS_FINISH;
        public static final Status STATUS_INSTALLING;
        public static final Status STATUS_PAUSE;
        public static final Status STATUS_WAITING;

        /* JADX INFO: Fake field, exist only in values array */
        Status EF0;

        static {
            Status status = new Status("STATUS_NORMAL", 0);
            Status status2 = new Status("STATUS_WAITING", 1);
            STATUS_WAITING = status2;
            Status status3 = new Status("STATUS_DOWNLOADING", 2);
            STATUS_DOWNLOADING = status3;
            Status status4 = new Status("STATUS_INSTALLING", 3);
            STATUS_INSTALLING = status4;
            Status status5 = new Status("STATUS_ERROR", 4);
            STATUS_ERROR = status5;
            Status status6 = new Status("STATUS_CANCEL", 5);
            STATUS_CANCEL = status6;
            Status status7 = new Status("STATUS_FINISH", 6);
            STATUS_FINISH = status7;
            Status status8 = new Status("STATUS_PAUSE", 7);
            STATUS_PAUSE = status8;
            $VALUES =
                    new Status[] {
                        status, status2, status3, status4, status5, status6, status7, status8
                    };
        }

        public static Status valueOf(String str) {
            return (Status) Enum.valueOf(Status.class, str);
        }

        public static Status[] values() {
            return (Status[]) $VALUES.clone();
        }
    }

    public static Notification makeProgressNotification(DownloadTask downloadTask, Integer num) {
        return downloadTask
                .mCachedNotificationBuilder
                .setProgress(100, num == null ? 0 : num.intValue(), num == null)
                .build();
    }

    public final void createNotificationChannel(String str) {
        this.mNotificationManager.createNotificationChannel(
                new NotificationChannel(
                        str, getResources().getString(R.string.sec_language_pack_title), 2));
    }

    public final void enqueueDownloadTask(LanguageInfo languageInfo) {
        synchronized (this.mQueue) {
            try {
                DownloadTask downloadTask = this.mCurrentTask;
                if (downloadTask == null
                        ? false
                        : TextUtils.equals(
                                downloadTask.mLanguageInfo.mLanguageCode,
                                languageInfo.mLanguageCode)) {
                    return;
                }
                for (DownloadTask downloadTask2 : this.mQueue) {
                    if (downloadTask2 == null
                            ? false
                            : TextUtils.equals(
                                    downloadTask2.mLanguageInfo.mLanguageCode,
                                    languageInfo.mLanguageCode)) {
                        return;
                    }
                }
                DownloadTask downloadTask3 = new DownloadTask(this, languageInfo);
                ((LinkedList) this.mQueue).add(downloadTask3);
                if (!this.mProcessing) {
                    startForeground(
                            EnterpriseContainerConstants.SYSTEM_SIGNED_APP,
                            makeProgressNotification(downloadTask3, null),
                            1);
                    new Thread(new ProcessingRunnable()).start();
                }
                this.mProcessing = true;
            } finally {
            }
        }
    }

    @Override // com.samsung.android.settings.languagepack.manager.LanguagePackManager.LangPackDownloadListener
    public final void finishUninstall(LanguageInfo languageInfo) {
        Context applicationContext = getApplicationContext();
        Iterator it = languageInfo.mList.iterator();
        while (it.hasNext()) {
            if (((PackageInfo) it.next()).isPackageInstalled(applicationContext)) {
                return;
            }
        }
        Intent intent = new Intent("com.samsung.android.settings.action.LANGUAGE_PACK_REMOVED");
        intent.putExtra(SpeechRecognitionConst.Key.LOCALE, languageInfo.mLanguageCode);
        sendBroadcast(intent);
        Log.d(
                "LanguagePackDownloadService",
                "Broadcast intent LANGUAGE_PACK_REMOVED : "
                        + languageInfo.mLanguageCode
                        + " : "
                        + this.mResultBroadcastPackage);
    }

    @Override // com.samsung.android.settings.languagepack.manager.LanguagePackManager.LangPackVersionUpdateListener
    public final void finishUpdate() {
        getApplicationContext()
                .getSharedPreferences("language_pack_update_information", 0)
                .edit()
                .putLong("last_success_time", System.currentTimeMillis())
                .apply();
        Intent intent =
                new Intent("com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_VERSION");
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    @Override // com.samsung.android.settings.languagepack.manager.LanguagePackManager.LangPackDownloadListener
    public final void notifyUpdateStatus(Status status, LanguageInfo languageInfo) {
        Intent intent =
                new Intent("com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_RESULT");
        intent.setPackage(getPackageName());
        intent.putExtra("extra_status", status);
        intent.putExtra("extra_lang_code", languageInfo.mLanguageCode);
        sendBroadcast(intent);
        if (Status.STATUS_FINISH != status || languageInfo.mToastType != 0) {
            if (Status.STATUS_INSTALLING == status) {
                updateNotification(makeProgressNotification(this.mCurrentTask, null));
                return;
            }
            return;
        }
        if (this.mNotificationManager.getNotificationChannel("install_finish") == null) {
            createNotificationChannel("install_finish");
        }
        Intent intent2 = new Intent();
        if (ActivityEmbeddingUtils.isEmbeddingActivityEnabled(getApplicationContext())) {
            intent2.setAction("android.settings.SETTINGS_EMBED_DEEP_LINK_ACTIVITY");
            Intent intent3 = new Intent();
            intent3.setClassName(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.android.settings.Settings$LanguagePackSettingsActivity");
            intent2.putExtra(
                    "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI",
                    intent3.toUri(1));
            intent2.putExtra(
                    "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY",
                    "sec_general_device_settings");
        } else {
            intent2.setClassName(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.android.settings.Settings$LanguagePackSettingsActivity");
        }
        Context applicationContext = getApplicationContext();
        ArrayList arrayList = new ArrayList();
        arrayList.add(intent2);
        if (arrayList.isEmpty()) {
            throw new IllegalStateException(
                    "No intents added to TaskStackBuilder; cannot getPendingIntent");
        }
        Intent[] intentArr = (Intent[]) arrayList.toArray(new Intent[0]);
        intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
        this.mNotificationManager.notify(
                languageInfo.mOrder,
                new Notification.Builder(getApplicationContext(), "install_finish")
                        .setContentTitle(
                                getString(
                                        R.string.sec_offline_lang_pack_noti_title,
                                        new Object[] {languageInfo.getDisplayName()}))
                        .setContentText(getString(R.string.sec_offline_lang_pack_noti_desc))
                        .setContentIntent(
                                PendingIntent.getActivities(
                                        applicationContext, 0, intentArr, 201326592, null))
                        .setSmallIcon(R.drawable.ic_settings_noti_panel)
                        .setColor(
                                getApplicationContext()
                                        .getResources()
                                        .getColor(R.color.langpack_noti_background_color))
                        .setAutoCancel(true)
                        .build());
        new Handler(Looper.getMainLooper())
                .post(
                        new LanguagePackDownloadService$$ExternalSyntheticLambda0(
                                1, this, languageInfo));
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        this.mNotificationManager =
                (NotificationManager) getSystemService(NotificationManager.class);
        createNotificationChannel("downloads_progress");
        createNotificationChannel("install_finish");
        LanguagePackManager languagePackManager =
                LanguagePackManager.getInstance(getApplicationContext());
        this.mLanguagePackManager = languagePackManager;
        languagePackManager.setDownloadListener(this);
        this.mLanguagePackManager.setVersionUpdateListener(this);
        this.mLanguagePackManager.setVersionUpdateMap(
                (Map)
                        getApplicationContext()
                                .getSharedPreferences("language_pack_update_information", 0)
                                .getAll()
                                .entrySet()
                                .stream()
                                .filter(new LanguagePackDownloadService$$ExternalSyntheticLambda1())
                                .collect(
                                        Collectors.toMap(
                                                new GtsResources$$ExternalSyntheticLambda1(),
                                                new LanguagePackDownloadService$$ExternalSyntheticLambda2())));
        try {
            this.mLanguagePackManager.makeLanguageList();
        } catch (JSONException e) {
            Log.d("LanguagePackDownloadService", "error on makeLanguageList() : " + e.getMessage());
        }
    }

    @Override // android.app.Service
    public final void onDestroy() {
        this.mLanguagePackManager.setDownloadListener(null);
        this.mLanguagePackManager.setVersionUpdateListener(null);
        super.onDestroy();
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            return 1;
        }
        if (intent.hasExtra("package")) {
            String stringExtra = intent.getStringExtra("package");
            if (!TextUtils.isEmpty(stringExtra)) {
                this.mResultBroadcastPackage = stringExtra;
            }
        }
        String stringExtra2 = intent.getStringExtra("command");
        if (TextUtils.isEmpty(stringExtra2)) {
            try {
                tryToMakeUpdateInformationIfNeeded(this.mLanguagePackManager.makeLanguageList());
                return 1;
            } catch (JSONException unused) {
                return 1;
            }
        }
        stringExtra2.getClass();
        if (stringExtra2.equals("cancel")) {
            this.mLanguagePackManager.setCancel();
            return 1;
        }
        if (!stringExtra2.equals("download")) {
            return 1;
        }
        String stringExtra3 = intent.getStringExtra(SpeechRecognitionConst.Key.LOCALE);
        if (TextUtils.isEmpty(stringExtra3)) {
            return 1;
        }
        enqueueDownloadTask(this.mLanguagePackManager.getLanguageInfo(stringExtra3));
        return 1;
    }

    @Override // com.samsung.android.settings.languagepack.manager.LanguagePackManager.LangPackDownloadListener
    public final void publishProgress(long j, LanguageInfo languageInfo) {
        Intent intent =
                new Intent("com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_PROGRESS");
        intent.setPackage(getPackageName());
        intent.putExtra("extra_lang_code", languageInfo.mLanguageCode);
        intent.putExtra("extra_lang_progress", j);
        intent.putExtra("extra_total_size", Long.valueOf(languageInfo.mTotalLanguagePackSize));
        if (this.mCurrentTask != null) {
            updateNotification(
                    makeProgressNotification(
                            this.mCurrentTask,
                            Integer.valueOf(
                                    (int)
                                            Math.floor(
                                                    (j * 100)
                                                            / languageInfo
                                                                    .mTotalLanguagePackSize))));
        }
        sendBroadcast(intent);
    }

    public final void tryToMakeUpdateInformationIfNeeded(List list) {
        long j =
                getApplicationContext()
                        .getSharedPreferences("language_pack_update_information", 0)
                        .getLong("last_success_time", 0L);
        long currentTimeMillis = System.currentTimeMillis();
        LanguagePackManager languagePackManager = this.mLanguagePackManager;
        languagePackManager.getClass();
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            CopyOnWriteArrayList copyOnWriteArrayList = ((LanguageInfo) it.next()).mList;
            if (copyOnWriteArrayList != null) {
                arrayList.addAll(
                        (Collection)
                                copyOnWriteArrayList.stream()
                                        .filter(
                                                new LanguagePackManager$$ExternalSyntheticLambda8(
                                                        0, languagePackManager))
                                        .collect(Collectors.toList()));
            }
        }
        List list2 =
                (List)
                        arrayList.stream()
                                .map(new LanguagePackManager$$ExternalSyntheticLambda9(0))
                                .distinct()
                                .collect(Collectors.toList());
        if (currentTimeMillis - j > LanguagePackConstant.UPDATE_CHECK_PERIOD_MILLIS) {
            StringBuilder m =
                    SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                            currentTimeMillis,
                            "tryToMakeUpdateInformationIfNeeded() : start update(",
                            " : ");
            m.append(j);
            m.append(")");
            Log.d("LanguagePackDownloadService", m.toString());
            new Thread(new LanguagePackDownloadService$$ExternalSyntheticLambda0(0, this, list2))
                    .start();
        }
    }

    @Override // com.samsung.android.settings.languagepack.manager.LanguagePackManager.LangPackVersionUpdateListener
    public final void updateLatestVersion(String str, String str2) {
        Log.d(
                "LanguagePackDownloadService",
                "setUpdateInformation : [" + str + "] latest version is " + str2);
        getApplicationContext()
                .getSharedPreferences("language_pack_update_information", 0)
                .edit()
                .putString(str, str2)
                .apply();
    }

    public final void updateNotification(Notification notification) {
        if (this.mNotificationManager.getNotificationChannel("downloads_progress") == null) {
            createNotificationChannel("downloads_progress");
        }
        this.mNotificationManager.notify(
                EnterpriseContainerConstants.SYSTEM_SIGNED_APP, notification);
    }
}
