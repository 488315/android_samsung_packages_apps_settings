package com.android.settings.biometrics.activeunlock;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ActiveUnlockContentListener {
    public String mContent;
    public final OnContentChangedListener mContentChangedListener;
    public final String mContentKey;
    public final Context mContext;
    public final String mLogTag;
    public final String mMethodName;
    public final Uri mUri;
    public boolean mSubscribed = false;
    public final AnonymousClass1 mContentObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    ActiveUnlockContentListener.this.getContentFromUri();
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnContentChangedListener {
        void onContentChanged(String str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0066, code lost:

       if (r0 != false) goto L18;
    */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ActiveUnlockContentListener(
            android.content.Context r5,
            com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener
                            .OnContentChangedListener
                    r6,
            java.lang.String r7,
            java.lang.String r8,
            java.lang.String r9) {
        /*
            r4 = this;
            r4.<init>()
            r0 = 0
            r4.mSubscribed = r0
            com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener$1 r1 = new com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener$1
            android.os.Handler r2 = new android.os.Handler
            android.os.Looper r3 = android.os.Looper.getMainLooper()
            r2.<init>(r3)
            r1.<init>(r2)
            r4.mContentObserver = r1
            r4.mContext = r5
            r4.mContentChangedListener = r6
            r4.mLogTag = r7
            r4.mMethodName = r8
            r4.mContentKey = r9
            r5.getContentResolver()
            android.content.ContentResolver r6 = r5.getContentResolver()
            java.lang.String r7 = "active_unlock_provider"
            java.lang.String r6 = android.provider.Settings.Secure.getString(r6, r7)
            java.lang.String r7 = "ActiveUnlockStatusUtils"
            r8 = 0
            if (r6 != 0) goto L39
            java.lang.String r5 = "authority not set"
            android.util.Log.i(r7, r5)
        L37:
            r6 = r8
            goto L6f
        L39:
            android.content.pm.PackageManager r5 = r5.getPackageManager()
            r1 = 1048576(0x100000, double:5.180654E-318)
            android.content.pm.PackageManager$ComponentInfoFlags r9 = android.content.pm.PackageManager.ComponentInfoFlags.of(r1)
            android.content.pm.ProviderInfo r5 = r5.resolveContentProvider(r6, r9)
            if (r5 != 0) goto L50
            java.lang.String r5 = "could not find provider"
            android.util.Log.i(r7, r5)
            goto L37
        L50:
            java.lang.String r9 = r5.authority
            boolean r9 = r6.equals(r9)
            if (r9 == 0) goto L69
            android.content.pm.ApplicationInfo r5 = r5.applicationInfo
            if (r5 != 0) goto L62
            java.lang.String r5 = "application info is null"
            android.util.Log.e(r7, r5)
            goto L66
        L62:
            boolean r0 = r5.isSystemApp()
        L66:
            if (r0 == 0) goto L69
            goto L6f
        L69:
            java.lang.String r5 = "authority not valid"
            android.util.Log.e(r7, r5)
            goto L37
        L6f:
            if (r6 == 0) goto L8d
            android.net.Uri$Builder r5 = new android.net.Uri$Builder
            r5.<init>()
            java.lang.String r7 = "content"
            android.net.Uri$Builder r5 = r5.scheme(r7)
            android.net.Uri$Builder r5 = r5.authority(r6)
            java.lang.String r6 = "getSummary"
            android.net.Uri$Builder r5 = r5.appendPath(r6)
            android.net.Uri r5 = r5.build()
            r4.mUri = r5
            goto L8f
        L8d:
            r4.mUri = r8
        L8f:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener.<init>(android.content.Context,"
                    + " com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener$OnContentChangedListener,"
                    + " java.lang.String, java.lang.String, java.lang.String):void");
    }

    public final void getContentFromUri() {
        Uri uri = this.mUri;
        String str = this.mLogTag;
        if (uri == null) {
            Log.e(str, "Uri null when trying to fetch content");
            return;
        }
        ContentProviderClient acquireContentProviderClient =
                this.mContext.getContentResolver().acquireContentProviderClient(this.mUri);
        try {
            Bundle call = acquireContentProviderClient.call(this.mMethodName, null, null);
            if (call == null) {
                Log.e(str, "Null bundle returned from contentProvider");
                return;
            }
            String string = call.getString(this.mContentKey);
            if (TextUtils.equals(this.mContent, string)) {
                return;
            }
            this.mContent = string;
            this.mContentChangedListener.onContentChanged(string);
        } catch (RemoteException e) {
            Log.e(str, "Failed to call contentProvider", e);
        } finally {
            acquireContentProviderClient.close();
        }
    }

    public final synchronized void subscribe() {
        if (!this.mSubscribed && this.mUri != null) {
            this.mSubscribed = true;
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(this.mUri, true, this.mContentObserver);
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                                     // com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActiveUnlockContentListener.this.getContentFromUri();
                        }
                    });
        }
    }

    public final synchronized void unsubscribe() {
        if (this.mSubscribed && this.mUri != null) {
            this.mSubscribed = false;
            this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        }
    }
}
