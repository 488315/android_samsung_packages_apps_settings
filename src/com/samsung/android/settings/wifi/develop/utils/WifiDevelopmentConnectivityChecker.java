package com.samsung.android.settings.wifi.develop.utils;

import android.content.Context;
import android.net.Network;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiDevelopmentConnectivityChecker {
    public final Network mCleartextDnsNetwork;
    public final Context mContext;
    public StringBuilder mLatestProbeRequest = new StringBuilder();
    public final Network mNetwork;
    public final URL mTestHttpUrl;
    public final URL mTestHttpsUrl;

    public WifiDevelopmentConnectivityChecker(Context context, Network network) {
        this.mContext = context;
        this.mNetwork = network;
        this.mCleartextDnsNetwork = network.getPrivateDnsBypassingCopy();
        this.mTestHttpUrl =
                TextUtils.isEmpty(ApnSettings.MVNO_NONE)
                        ? makeURL("http://connectivitycheck.gstatic.com/generate_204")
                        : makeURL(ApnSettings.MVNO_NONE);
        this.mTestHttpsUrl =
                TextUtils.isEmpty(ApnSettings.MVNO_NONE)
                        ? makeURL("https://www.google.com/generate_204")
                        : makeURL(ApnSettings.MVNO_NONE);
    }

    public static Charset extractCharset(String str) {
        if (str == null) {
            return StandardCharsets.UTF_8;
        }
        Matcher matcher = Pattern.compile("; *charset=\"?([^ ;\"]+)\"?", 2).matcher(str);
        if (!matcher.find()) {
            return StandardCharsets.UTF_8;
        }
        try {
            return Charset.forName(matcher.group(1));
        } catch (IllegalArgumentException unused) {
            return StandardCharsets.UTF_8;
        }
    }

    public static String logValidationProbe(int i, int i2, long j) {
        long j2 = j / 1000;
        StringBuilder sb = new StringBuilder(ApnSettings.MVNO_NONE);
        int i3 = ValidationProbeEvent.Decoder.$r8$clinit;
        sb.append(
                i != 0
                        ? i != 1
                                ? i != 2
                                        ? ValidationProbeEvent.probeName.UNKNOWN
                                        : ValidationProbeEvent.probeName.PROBE_HTTPS
                                : ValidationProbeEvent.probeName.PROBE_HTTP
                        : ValidationProbeEvent.probeName.PROBE_DNS);
        return String.format("%s (%d, %dms)", sb.toString(), Integer.valueOf(i2), Long.valueOf(j2));
    }

    public static URL makeURL(String str) {
        if (str == null) {
            return null;
        }
        try {
            return new URL(str);
        } catch (MalformedURLException unused) {
            Log.e("WifiDevelopmentConnectivityCheck", "Bad URL: ".concat(str));
            return null;
        }
    }

    public static String readAsString(InputStream inputStream, int i, Charset charset) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
        char[] cArr = new char[1000];
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (i2 < i) {
            int read = inputStreamReader.read(cArr, 0, Math.min(i - i2, 1000));
            if (read < 0) {
                break;
            }
            i2 += read;
            sb.append(cArr, 0, read);
        }
        return sb.toString();
    }

    public static InetAddress[] sendDnsProbe(String str, Network network) {
        Log.i("WifiDevelopmentConnectivityCheck", "sendDnsProbe : " + str);
        InetAddress[] inetAddressArr = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        try {
            InetAddress.clearDnsCache();
            inetAddressArr = network.getAllByName(str);
        } catch (UnknownHostException unused) {
        }
        if (elapsedRealtimeNanos > 0) {
            SystemClock.elapsedRealtimeNanos();
        }
        return inetAddressArr;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(21:23|(3:24|25|(1:27)(1:70))|(15:32|33|(1:35)(1:66)|36|(4:38|(1:40)|41|42)|43|44|45|(1:47)(1:64)|(2:61|62)|51|52|(1:54)|55|(3:57|(1:59)|60))|67|68|33|(0)(0)|36|(0)|43|44|45|(0)(0)|(1:49)|61|62|51|52|(0)|55|(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0134, code lost:

       r9 = 0;
    */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x010e A[Catch: Exception -> 0x0134, TryCatch #1 {Exception -> 0x0134, blocks: (B:45:0x0108, B:47:0x010e, B:49:0x012b, B:61:0x012e, B:64:0x011b), top: B:44:0x0108 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x011b A[Catch: Exception -> 0x0134, TryCatch #1 {Exception -> 0x0134, blocks: (B:45:0x0108, B:47:0x010e, B:49:0x012b, B:61:0x012e, B:64:0x011b), top: B:44:0x0108 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getDnsTestStep(java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 363
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.develop.utils.WifiDevelopmentConnectivityChecker.getDnsTestStep(java.lang.String):java.lang.String");
    }

    public final HttpURLConnection makeProbeConnection(URL url, boolean z) {
        HttpURLConnection httpURLConnection =
                (HttpURLConnection) this.mCleartextDnsNetwork.openConnection(url);
        httpURLConnection.setInstanceFollowRedirects(z);
        httpURLConnection.setConnectTimeout(EnterpriseContainerConstants.SYSTEM_SIGNED_APP);
        httpURLConnection.setReadTimeout(EnterpriseContainerConstants.SYSTEM_SIGNED_APP);
        httpURLConnection.setRequestProperty("Connection", "close");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko)"
                    + " Chrome/60.0.3112.32 Safari/537.36");
        return httpURLConnection;
    }

    public final void runHttpTest(String str) {
        sendHttpProbe(TextUtils.isEmpty(str) ? this.mTestHttpUrl : makeURL(str), 1);
    }

    public final void runHttpsTest(String str) {
        sendHttpProbe(TextUtils.isEmpty(str) ? this.mTestHttpsUrl : makeURL(str), 2);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0165  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.settings.wifi.develop.utils.TestProbeResult sendHttpProbe(
            java.net.URL r26, int r27) {
        /*
            Method dump skipped, instructions count: 423
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.develop.utils.WifiDevelopmentConnectivityChecker.sendHttpProbe(java.net.URL,"
                    + " int):com.samsung.android.settings.wifi.develop.utils.TestProbeResult");
    }
}
