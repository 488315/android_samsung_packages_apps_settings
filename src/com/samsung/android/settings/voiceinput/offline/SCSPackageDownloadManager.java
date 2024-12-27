package com.samsung.android.settings.voiceinput.offline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.samsung.android.settings.voiceinput.PackageUtils;
import com.samsung.android.util.SemLog;

import io.reactivex.internal.operators.observable.ObservableHide;
import io.reactivex.subjects.PublishSubject;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SCSPackageDownloadManager {
    private static String TAG = "@VoiceIn: SCSPackageDownloadManager";
    private static BroadcastReceiver mBroadcastReceiver;
    private static HashMap<String, String> mHashMap = new HashMap<>();
    private static PublishSubject mInstallResultSubject = new PublishSubject();
    private static Context mLocalContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.voiceinput.offline.SCSPackageDownloadManager$1, reason: invalid class name */
    class AnonymousClass1 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
            SemLog.d(
                    SCSPackageDownloadManager.TAG,
                    "onReceive result : " + intent.getAction() + " " + schemeSpecificPart);
            if (schemeSpecificPart != null) {
                String str = (String) SCSPackageDownloadManager.mHashMap.get(schemeSpecificPart);
                String valueOf =
                        String.valueOf(PackageUtils.getVersionCode(context, schemeSpecificPart));
                LanguageData languageData = new LanguageData();
                languageData.language = str;
                languageData.version = valueOf;
                SCSPackageDownloadManager.m1312$$Nest$smnotifyInstalled(languageData);
                SCSPackageDownloadManager.mHashMap.remove(schemeSpecificPart);
                if (SCSPackageDownloadManager.mHashMap.isEmpty()) {
                    SCSPackageDownloadManager.m1313$$Nest$smunregisterReceiver();
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class LanguageData {
        String language;
        String version;
    }

    /* renamed from: -$$Nest$smnotifyInstalled, reason: not valid java name */
    public static void m1312$$Nest$smnotifyInstalled(LanguageData languageData) {
        mInstallResultSubject.onNext(languageData);
        SemLog.d(TAG, "notifying for language " + languageData);
    }

    /* renamed from: -$$Nest$smunregisterReceiver, reason: not valid java name */
    public static void m1313$$Nest$smunregisterReceiver() {
        SemLog.d(TAG, "unregister");
        try {
            try {
                BroadcastReceiver broadcastReceiver = mBroadcastReceiver;
                if (broadcastReceiver != null) {
                    mLocalContext.unregisterReceiver(broadcastReceiver);
                    SemLog.d(TAG, "receiver unregistered successfully");
                }
            } catch (IllegalArgumentException e) {
                SemLog.e(TAG, "Exception while unregistering receiver: " + e);
            }
        } finally {
            mBroadcastReceiver = null;
            mLocalContext = null;
        }
    }

    public static void addDownloadPackage(Context context, String str, String str2) {
        SemLog.d(TAG, "add download Package " + str + " " + str2);
        if (mLocalContext == null) {
            mLocalContext = context;
        }
        mHashMap.put(str2, str);
        SemLog.d(TAG, "registering receiver =" + mBroadcastReceiver);
        if (mBroadcastReceiver != null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        mBroadcastReceiver = anonymousClass1;
        mLocalContext.registerReceiver(anonymousClass1, intentFilter);
        SemLog.d(TAG, "receiver registered successfully");
    }

    public static ObservableHide getInstallResult() {
        PublishSubject publishSubject = mInstallResultSubject;
        publishSubject.getClass();
        return new ObservableHide(publishSubject);
    }
}
