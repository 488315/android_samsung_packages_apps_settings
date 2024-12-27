package com.android.settingslib.applications;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ServiceListing {
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final String mIntentAction;
    public boolean mListening;
    public final String mNoun;
    public final String mPermission;
    public final String mSetting;
    public final String mTag;
    public final HashSet mEnabledServices = new HashSet();
    public final List mServices = new ArrayList();
    public final List mCallbacks = new ArrayList();
    public final AnonymousClass1 mSettingsObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.android.settingslib.applications.ServiceListing.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    ServiceListing.this.reload();
                }
            };
    public final AnonymousClass2 mPackageReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settingslib.applications.ServiceListing.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ServiceListing.this.reload();
                }
            };
    public final boolean mAddDeviceLockedFlags = false;
    public final Predicate mValidator = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {
        void onServicesReloaded(List list);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settingslib.applications.ServiceListing$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settingslib.applications.ServiceListing$2] */
    public ServiceListing(
            Context context, String str, String str2, String str3, String str4, String str5) {
        this.mContentResolver = context.getContentResolver();
        this.mContext = context;
        this.mTag = str;
        this.mSetting = str2;
        this.mIntentAction = str3;
        this.mPermission = str4;
        this.mNoun = str5;
    }

    public final void reload() {
        this.mEnabledServices.clear();
        String string = Settings.Secure.getString(this.mContentResolver, this.mSetting);
        if (string != null && !ApnSettings.MVNO_NONE.equals(string)) {
            for (String str : string.split(":")) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                if (unflattenFromString != null) {
                    this.mEnabledServices.add(unflattenFromString);
                }
            }
        }
        ((ArrayList) this.mServices).clear();
        Iterator it =
                this.mContext
                        .getPackageManager()
                        .queryIntentServicesAsUser(
                                new Intent(this.mIntentAction),
                                this.mAddDeviceLockedFlags ? 786564 : 132,
                                ActivityManager.getCurrentUser())
                        .iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
            String str2 = serviceInfo.permission;
            String str3 = this.mPermission;
            if (str3.equals(str2)) {
                Predicate predicate = this.mValidator;
                if (predicate == null || predicate.test(serviceInfo)) {
                    ((ArrayList) this.mServices).add(serviceInfo);
                }
            } else {
                Slog.w(
                        this.mTag,
                        "Skipping "
                                + this.mNoun
                                + " service "
                                + serviceInfo.packageName
                                + "/"
                                + serviceInfo.name
                                + ": it does not require the permission "
                                + str3);
            }
        }
        Iterator it2 = ((ArrayList) this.mCallbacks).iterator();
        while (it2.hasNext()) {
            ((Callback) it2.next()).onServicesReloaded(this.mServices);
        }
    }

    public final void setEnabled(ComponentName componentName, boolean z) {
        if (z) {
            this.mEnabledServices.add(componentName);
        } else {
            this.mEnabledServices.remove(componentName);
        }
        Iterator it = this.mEnabledServices.iterator();
        StringBuilder sb = null;
        while (it.hasNext()) {
            ComponentName componentName2 = (ComponentName) it.next();
            if (sb == null) {
                sb = new StringBuilder();
            } else {
                sb.append(':');
            }
            sb.append(componentName2.flattenToString());
        }
        Settings.Secure.putString(
                this.mContentResolver,
                this.mSetting,
                sb != null ? sb.toString() : ApnSettings.MVNO_NONE);
    }

    public final void setListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        AnonymousClass1 anonymousClass1 = this.mSettingsObserver;
        AnonymousClass2 anonymousClass2 = this.mPackageReceiver;
        if (!z) {
            this.mContext.unregisterReceiver(anonymousClass2);
            this.mContentResolver.unregisterContentObserver(anonymousClass1);
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiver(anonymousClass2, intentFilter);
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor(this.mSetting), false, anonymousClass1);
    }
}
