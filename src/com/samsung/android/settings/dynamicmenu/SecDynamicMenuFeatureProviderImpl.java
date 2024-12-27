package com.samsung.android.settings.dynamicmenu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecDynamicMenuFeatureProviderImpl {
    public final Context mContext;

    public SecDynamicMenuFeatureProviderImpl(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x009a, code lost:

       if (r6 == null) goto L31;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.ArrayList getBundleListFromUri(
            android.net.Uri r6, android.content.Context r7, java.util.List r8) {
        /*
            java.lang.String r0 = "put bundle desktopModeEnabled : "
            java.lang.String r1 = "acquire provider : "
            java.lang.String r2 = "SecDynamicMenuFeatureProviderImpl"
            r3 = 0
            if (r7 == 0) goto La4
            android.content.ContentResolver r4 = r7.getContentResolver()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8e java.lang.Throwable -> L90
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L8e java.lang.Throwable -> L90
            java.lang.String r1 = r6.getAuthority()     // Catch: java.lang.Throwable -> L8e java.lang.Throwable -> L90
            r5.append(r1)     // Catch: java.lang.Throwable -> L8e java.lang.Throwable -> L90
            java.lang.String r1 = r5.toString()     // Catch: java.lang.Throwable -> L8e java.lang.Throwable -> L90
            android.util.Log.i(r2, r1)     // Catch: java.lang.Throwable -> L8e java.lang.Throwable -> L90
            android.content.ContentProviderClient r6 = r4.acquireUnstableContentProviderClient(r6)     // Catch: java.lang.Throwable -> L8e java.lang.Throwable -> L90
            if (r6 != 0) goto L38
            java.lang.String r7 = "provider is null."
            android.util.Log.i(r2, r7)     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36
            if (r6 == 0) goto L32
            r6.close()
        L32:
            return r3
        L33:
            r7 = move-exception
            r3 = r6
            goto L9e
        L36:
            r7 = move-exception
            goto L92
        L38:
            java.lang.String r1 = "provider acquired successfully."
            android.util.Log.i(r2, r1)     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            android.os.Bundle r1 = new android.os.Bundle     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            r1.<init>()     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            android.content.res.Resources r7 = r7.getResources()     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            android.content.res.Configuration r7 = r7.getConfiguration()     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            int r7 = r7.semDesktopModeEnabled     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            r4 = 1
            if (r4 != r7) goto L51
            goto L52
        L51:
            r4 = 0
        L52:
            java.lang.String r7 = "desktopModeEnabled"
            r1.putBoolean(r7, r4)     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            java.lang.String r7 = "categoryKeys"
            java.util.ArrayList r8 = (java.util.ArrayList) r8     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            r1.putStringArrayList(r7, r8)     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            r7.<init>(r0)     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            r7.append(r4)     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            android.util.Log.i(r2, r7)     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            java.lang.String r7 = "get_menu_list"
            android.os.Bundle r7 = r6.call(r7, r3, r1)     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            if (r7 == 0) goto L85
            java.lang.Class<com.samsung.android.settings.external.DynamicMenuData> r8 = com.samsung.android.settings.external.DynamicMenuData.class
            java.lang.ClassLoader r8 = r8.getClassLoader()     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            r7.setClassLoader(r8)     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            java.lang.String r8 = "menu_list"
            java.util.ArrayList r3 = r7.getParcelableArrayList(r8)     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
            goto L8a
        L85:
            java.lang.String r7 = "getBundleListFromUri bundle is null"
            android.util.Log.i(r2, r7)     // Catch: java.lang.Throwable -> L33 java.lang.Throwable -> L36 java.lang.Throwable -> L36
        L8a:
            r6.close()
            goto L9d
        L8e:
            r7 = move-exception
            goto L9e
        L90:
            r7 = move-exception
            r6 = r3
        L92:
            java.lang.String r8 = "can not call the provider"
            android.util.Log.i(r2, r8)     // Catch: java.lang.Throwable -> L33
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L33
            if (r6 == 0) goto L9d
            goto L8a
        L9d:
            return r3
        L9e:
            if (r3 == 0) goto La3
            r3.close()
        La3:
            throw r7
        La4:
            java.lang.String r6 = "mContext or uri or null"
            android.util.Log.i(r2, r6)
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.dynamicmenu.SecDynamicMenuFeatureProviderImpl.getBundleListFromUri(android.net.Uri,"
                    + " android.content.Context, java.util.List):java.util.ArrayList");
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0056, code lost:

       if (r3 == null) goto L29;
    */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.settings.external.DynamicMenuData getBundleFromKey(
            android.net.Uri r4, java.lang.String r5) {
        /*
            r3 = this;
            android.content.Context r0 = r3.mContext
            java.lang.String r1 = "SecDynamicMenuFeatureProviderImpl"
            r2 = 0
            if (r0 == 0) goto L60
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            if (r0 == 0) goto Le
            goto L60
        Le:
            android.content.Context r3 = r3.mContext
            android.content.ContentResolver r3 = r3.getContentResolver()
            android.content.ContentProviderClient r3 = r3.acquireUnstableContentProviderClient(r4)     // Catch: java.lang.Throwable -> L4a java.lang.Throwable -> L4c
            if (r3 != 0) goto L20
            if (r3 == 0) goto L1f
            r3.close()
        L1f:
            return r2
        L20:
            java.lang.String r4 = "select_menu"
            android.os.Bundle r4 = r3.call(r4, r5, r2)     // Catch: java.lang.Throwable -> L3c java.lang.Throwable -> L3f
            if (r4 == 0) goto L41
            java.lang.Class<com.samsung.android.settings.external.DynamicMenuData> r5 = com.samsung.android.settings.external.DynamicMenuData.class
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch: java.lang.Throwable -> L3c java.lang.Throwable -> L3f
            r4.setClassLoader(r5)     // Catch: java.lang.Throwable -> L3c java.lang.Throwable -> L3f
            java.lang.String r5 = "menu"
            android.os.Parcelable r4 = r4.getParcelable(r5)     // Catch: java.lang.Throwable -> L3c java.lang.Throwable -> L3f
            com.samsung.android.settings.external.DynamicMenuData r4 = (com.samsung.android.settings.external.DynamicMenuData) r4     // Catch: java.lang.Throwable -> L3c java.lang.Throwable -> L3f
            r2 = r4
            goto L46
        L3c:
            r4 = move-exception
            r2 = r3
            goto L5a
        L3f:
            r4 = move-exception
            goto L4e
        L41:
            java.lang.String r4 = "getBundleFromKey bundle is null"
            android.util.Log.i(r1, r4)     // Catch: java.lang.Throwable -> L3c java.lang.Throwable -> L3f
        L46:
            r3.close()
            goto L59
        L4a:
            r4 = move-exception
            goto L5a
        L4c:
            r4 = move-exception
            r3 = r2
        L4e:
            java.lang.String r5 = "can not call the provider"
            android.util.Log.d(r1, r5)     // Catch: java.lang.Throwable -> L3c
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L3c
            if (r3 == 0) goto L59
            goto L46
        L59:
            return r2
        L5a:
            if (r2 == 0) goto L5f
            r2.close()
        L5f:
            throw r4
        L60:
            java.lang.String r3 = "getBundleFromKey mContext or uri or key is null"
            android.util.Log.i(r1, r3)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.dynamicmenu.SecDynamicMenuFeatureProviderImpl.getBundleFromKey(android.net.Uri,"
                    + " java.lang.String):com.samsung.android.settings.external.DynamicMenuData");
    }

    public final Uri getUriFromIntent(Intent intent) {
        if (this.mContext == null || intent == null) {
            Log.i(
                    "SecDynamicMenuFeatureProviderImpl",
                    "getUriFromIntent mContext or intent is null");
            return null;
        }
        Intent intent2 = new Intent("com.samsung.android.settings.DYNAMIC_MENU_PROVIDER");
        if (intent.getComponent() != null) {
            intent2.setPackage(intent.getComponent().getPackageName());
        }
        List<ResolveInfo> queryIntentContentProviders =
                this.mContext.getPackageManager().queryIntentContentProviders(intent2, 0);
        if (queryIntentContentProviders == null || queryIntentContentProviders.isEmpty()) {
            Log.i("SecDynamicMenuFeatureProviderImpl", "getUriFromIntent providers is null");
        } else {
            ProviderInfo providerInfo = queryIntentContentProviders.get(0).providerInfo;
            if (providerInfo != null && !TextUtils.isEmpty(providerInfo.authority)) {
                return new Uri.Builder()
                        .scheme("content")
                        .authority(providerInfo.authority)
                        .build();
            }
        }
        return null;
    }
}
