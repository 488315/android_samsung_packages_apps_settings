package com.samsung.android.settings.languagepack.appsstub;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.settings.languagepack.appsstub.saccount.SaInfo;
import com.samsung.android.settings.languagepack.appsstub.saccount.SaInfoUtils;
import com.samsung.android.settings.languagepack.data.DownloadInfo;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.languagepack.utils.LanguagePackUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RequestStubApi {
    public final Context mContext;

    public RequestStubApi(Context context) {
        this.mContext = context;
    }

    public static RequestStubApi getInstance(Context context) {
        if (context != null) {
            return new RequestStubApi(context);
        }
        throw new IllegalArgumentException("context is null");
    }

    public static String requestURL(String str, SaInfo saInfo) {
        Log.i(
                "RequestStubApi",
                "[requestURL] start to request url to server!, saInfo = "
                        + Boolean.valueOf(saInfo.hasInfo()));
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.setConnectTimeout(15000);
                    httpURLConnection.setReadTimeout(
                            EnterpriseContainerConstants.SYSTEM_SIGNED_APP);
                    httpURLConnection.setRequestMethod("GET");
                    if (saInfo.hasInfo()) {
                        saInfo.printLog();
                        httpURLConnection.setRequestProperty("x-vas-auth-appId", saInfo.appId);
                        httpURLConnection.setRequestProperty("x-vas-auth-token", saInfo.token);
                        httpURLConnection.setRequestProperty("x-vas-auth-url", saInfo.url);
                    }
                    int responseCode = httpURLConnection.getResponseCode();
                    Log.i("RequestStubApi", "[requestURL] response code = " + responseCode);
                    if (responseCode == 200) {
                        try {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            try {
                                InputStreamReader inputStreamReader =
                                        new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                                try {
                                    BufferedReader bufferedReader =
                                            new BufferedReader(inputStreamReader);
                                    try {
                                        char[] cArr = new char[4096];
                                        while (true) {
                                            int read = bufferedReader.read(cArr);
                                            if (read == -1) {
                                                break;
                                            }
                                            sb.append(new String(cArr, 0, read));
                                        }
                                        bufferedReader.close();
                                        inputStreamReader.close();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                    } finally {
                                    }
                                } finally {
                                }
                            } catch (Throwable th) {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                }
                                throw th;
                            }
                        } catch (IOException e) {
                            Log.e("RequestStubApi", "[requestURL] exception : " + e.getMessage());
                        }
                    }
                    httpURLConnection.disconnect();
                    Log.i("RequestStubApi", "[requestURL] conn disconnected");
                } catch (Throwable th3) {
                    httpURLConnection.disconnect();
                    Log.i("RequestStubApi", "[requestURL] conn disconnected");
                    throw th3;
                }
            } else {
                Log.i("RequestStubApi", "[requestURL] conn is null!");
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return sb.toString();
    }

    public final DownloadInfo getDownloadInfo(String str) {
        String str2;
        SaInfo saInfo = SaInfoUtils.getSaInfo(this.mContext);
        try {
            str2 =
                    String.valueOf(
                            this.mContext.getPackageManager().getPackageInfo(str, 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            android.util.Log.d(
                    "LanguagePackUtils", "getVersionCode() Exception :: " + e.getMessage());
            str2 = "-1";
        }
        String sHA1DigestValue = LanguagePackUtils.getSHA1DigestValue(this.mContext, str);
        RequestUrlBuilder requestUrlBuilder = new RequestUrlBuilder(this.mContext, 0);
        requestUrlBuilder.appID = str;
        requestUrlBuilder.saInfo = saInfo;
        requestUrlBuilder.versionCode = str2;
        requestUrlBuilder.hashValue = sHA1DigestValue;
        String requestURL = requestURL(requestUrlBuilder.build(), saInfo);
        Log.i("RequestStubApi", "[getDownloadInfo] xml = " + requestURL);
        if (TextUtils.isEmpty(requestURL)) {
            Log.e("XmlParser", "Input XML is wrong!");
            return null;
        }
        String[] strArr = XmlParser.APPID_TAG;
        XmlParser.getParsedString(requestURL, strArr[0], strArr[1]);
        String[] strArr2 = XmlParser.RETCODE_TAG;
        String parsedString = XmlParser.getParsedString(requestURL, strArr2[0], strArr2[1]);
        String[] strArr3 = XmlParser.RETMSG_TAG;
        String parsedString2 = XmlParser.getParsedString(requestURL, strArr3[0], strArr3[1]);
        String[] strArr4 = XmlParser.DOWNLOADURI_TAG;
        String parsedString3 = XmlParser.getParsedString(requestURL, strArr4[0], strArr4[1]);
        String[] strArr5 = XmlParser.CONTENTSIZE_TAG;
        String parsedString4 = XmlParser.getParsedString(requestURL, strArr5[0], strArr5[1]);
        String[] strArr6 = XmlParser.DELTA_DOWNLOADURI_TAG;
        String parsedString5 = XmlParser.getParsedString(requestURL, strArr6[0], strArr6[1]);
        String[] strArr7 = XmlParser.DELTA_CONTENTSIZE_TAG;
        String parsedString6 = XmlParser.getParsedString(requestURL, strArr7[0], strArr7[1]);
        String[] strArr8 = XmlParser.INSTALLED_BINARY_HASH_VALUE_TAG;
        String parsedString7 = XmlParser.getParsedString(requestURL, strArr8[0], strArr8[1]);
        String[] strArr9 = XmlParser.UPDATE_BINARY_HASH_VALUE_TAG;
        String parsedString8 = XmlParser.getParsedString(requestURL, strArr9[0], strArr9[1]);
        String[] strArr10 = XmlParser.VERCODE_TAG;
        XmlParser.getParsedString(requestURL, strArr10[0], strArr10[1]);
        String[] strArr11 = XmlParser.VERNAME_TAG;
        XmlParser.getParsedString(requestURL, strArr11[0], strArr11[1]);
        String[] strArr12 = XmlParser.PRODUCTID_TAG;
        XmlParser.getParsedString(requestURL, strArr12[0], strArr12[1]);
        String[] strArr13 = XmlParser.PRODUCTNAME_TAG;
        XmlParser.getParsedString(requestURL, strArr13[0], strArr13[1]);
        String[] strArr14 = XmlParser.SIGNATURE_TAG;
        return new DownloadInfo(
                parsedString,
                parsedString2,
                parsedString3,
                parsedString4,
                parsedString5,
                parsedString6,
                parsedString7,
                parsedString8,
                XmlParser.getParsedString(requestURL, strArr14[0], strArr14[1]));
    }

    public final String getDownloadInfoByXML(String str) {
        SaInfo saInfo = SaInfoUtils.getSaInfo(this.mContext);
        RequestUrlBuilder requestUrlBuilder = new RequestUrlBuilder(this.mContext, 1);
        requestUrlBuilder.appID = str;
        requestUrlBuilder.saInfo = saInfo;
        String requestURL = requestURL(requestUrlBuilder.build(), saInfo);
        Log.i("RequestStubApi", "[getDownloadInfoByXML] xml = " + requestURL);
        return requestURL;
    }
}
