package com.samsung.android.settings.wifi.details;

import android.os.AsyncTask;

import java.net.HttpURLConnection;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CheckManageRouterTask extends AsyncTask {
    public WifiManageRouterPreferenceController mCallback;
    public int mResponse;
    public HttpURLConnection urlConnection;

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005e, code lost:

       if (r5 == null) goto L24;
    */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0060, code lost:

       r5.disconnect();
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0074, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0071, code lost:

       if (r5 == null) goto L24;
    */
    @Override // android.os.AsyncTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object doInBackground(java.lang.Object[] r6) {
        /*
            r5 = this;
            java.lang.String[] r6 = (java.lang.String[]) r6
            java.lang.String r0 = "Go to Webpage: reach to finally"
            java.lang.String r1 = "CheckManageRouterTask"
            java.lang.String r2 = "Go To Webpage: HTTP Response "
            int r3 = r5.mResponse
            if (r3 == 0) goto L11
            java.lang.Integer r5 = java.lang.Integer.valueOf(r3)
            goto L75
        L11:
            java.net.URL r3 = new java.net.URL     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            r4 = 0
            r6 = r6[r4]     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            r3.<init>(r6)     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            java.net.URLConnection r6 = r3.openConnection()     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            java.net.HttpURLConnection r6 = (java.net.HttpURLConnection) r6     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            r5.urlConnection = r6     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            r3 = 10000(0x2710, float:1.4013E-41)
            r6.setConnectTimeout(r3)     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            java.net.HttpURLConnection r6 = r5.urlConnection     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            int r6 = r6.getResponseCode()     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            r3.append(r6)     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            java.lang.String r2 = r3.toString()     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            android.util.Log.d(r1, r2)     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L4d java.net.MalformedURLException -> L4f
            android.util.Log.d(r1, r0)
            java.net.HttpURLConnection r5 = r5.urlConnection
            if (r5 == 0) goto L49
            r5.disconnect()
        L49:
            r5 = r6
            goto L75
        L4b:
            r6 = move-exception
            goto L76
        L4d:
            r6 = move-exception
            goto L51
        L4f:
            r6 = move-exception
            goto L64
        L51:
            java.lang.String r2 = "Go to Webpage: Error opening connection"
            android.util.Log.d(r1, r2)     // Catch: java.lang.Throwable -> L4b
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L4b
            android.util.Log.d(r1, r0)
            java.net.HttpURLConnection r5 = r5.urlConnection
            if (r5 == 0) goto L74
        L60:
            r5.disconnect()
            goto L74
        L64:
            java.lang.String r2 = "Go to Webpage: Error getting URL"
            android.util.Log.d(r1, r2)     // Catch: java.lang.Throwable -> L4b
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L4b
            android.util.Log.d(r1, r0)
            java.net.HttpURLConnection r5 = r5.urlConnection
            if (r5 == 0) goto L74
            goto L60
        L74:
            r5 = 0
        L75:
            return r5
        L76:
            android.util.Log.d(r1, r0)
            java.net.HttpURLConnection r5 = r5.urlConnection
            if (r5 == 0) goto L80
            r5.disconnect()
        L80:
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.CheckManageRouterTask.doInBackground(java.lang.Object[]):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x005b, code lost:

       if (r2.isConnected() != false) goto L26;
    */
    @Override // android.os.AsyncTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPostExecute(java.lang.Object r3) {
        /*
            r2 = this;
            java.lang.Integer r3 = (java.lang.Integer) r3
            if (r3 == 0) goto L63
            int r0 = r3.intValue()
            r2.mResponse = r0
            com.samsung.android.settings.wifi.details.WifiManageRouterPreferenceController r0 = r2.mCallback
            if (r0 == 0) goto L6a
            int r0 = r3.intValue()
            if (r0 == 0) goto L6a
            com.samsung.android.settings.wifi.details.WifiManageRouterPreferenceController r2 = r2.mCallback
            int r3 = r3.intValue()
            r2.getClass()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Gateway Http response - "
            r0.<init>(r1)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "WifiManageRouterPrefCtrl"
            android.util.Log.d(r1, r0)
            r2.mHttpResponse = r3
            androidx.preference.Preference r0 = r2.mManageRouterPref
            if (r0 == 0) goto L6a
            r1 = 200(0xc8, float:2.8E-43)
            if (r3 == r1) goto L3e
            r1 = 401(0x191, float:5.62E-43)
            if (r3 != r1) goto L5e
        L3e:
            com.android.wifitrackerlib.WifiEntry r3 = r2.mWifiEntry
            if (r3 == 0) goto L5e
            com.android.wifitrackerlib.WifiEntry$ConnectedInfo r3 = r3.getConnectedInfo()
            if (r3 == 0) goto L5e
            android.net.ConnectivityManager r2 = r2.mConnectivityManager
            android.net.NetworkInfo r2 = r2.getActiveNetworkInfo()
            if (r2 == 0) goto L5e
            int r3 = r2.getType()
            r1 = 1
            if (r3 != r1) goto L5e
            boolean r2 = r2.isConnected()
            if (r2 == 0) goto L5e
            goto L5f
        L5e:
            r1 = 0
        L5f:
            r0.setVisible(r1)
            goto L6a
        L63:
            java.lang.String r2 = "CheckManageRouterTask"
            java.lang.String r3 = "Go to Webpage: HTTP response is null"
            android.util.Log.d(r2, r3)
        L6a:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.CheckManageRouterTask.onPostExecute(java.lang.Object):void");
    }
}
