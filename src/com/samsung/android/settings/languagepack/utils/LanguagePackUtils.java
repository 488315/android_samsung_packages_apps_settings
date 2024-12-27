package com.samsung.android.settings.languagepack.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.text.TextUtils;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.network.apn.ApnPreference$$ExternalSyntheticOutline0;

import com.samsung.android.settings.languagepack.apkverifier.GetSha1FromApk;
import com.sec.ims.configuration.DATA;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class LanguagePackUtils {
    public static void addDeltaDownloadFailList(Context context, String str) {
        SharedPreferences.Editor edit =
                context.getSharedPreferences("language_pack_delta_update", 0).edit();
        Set<String> stringSet =
                context.getSharedPreferences("language_pack_delta_update", 0)
                        .getStringSet("language_pack_delta_update_fail_list", null);
        if (stringSet == null || stringSet.isEmpty()) {
            stringSet = new HashSet<>();
        }
        stringSet.add(str);
        edit.putStringSet("language_pack_delta_update_fail_list", stringSet);
        edit.apply();
    }

    public static String getApkSourceDir(Context context, String str) {
        try {
            ApplicationInfo applicationInfo =
                    context.getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo == null) {
                return null;
            }
            return applicationInfo.publicSourceDir;
        } catch (PackageManager.NameNotFoundException unused) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "getApkSourceDir::Cannot find ", str, "LanguagePackUtils");
            return null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSHA1DigestValue(Context context, String str) {
        try {
            String sha1 = new GetSha1FromApk(context).getSha1(getApkSourceDir(context, str));
            return !TextUtils.isEmpty(sha1)
                    ? URLEncoder.encode(sha1, "UTF-8")
                    : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        }
    }

    public static boolean isDirectoryExists(Context context, String str) {
        File file =
                new File(
                        context.getFilesDir(),
                        ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                new StringBuilder("LanguagePack"), File.separator, str));
        return file.exists() && file.isDirectory();
    }

    public static boolean isNotActiveNetworksWarning(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        NetworkCapabilities networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (networkCapabilities != null && networkCapabilities.hasCapability(16)) {
            return false;
        }
        ApnPreference$$ExternalSyntheticOutline0.m(
                context, R.string.sec_offline_lang_pack_toast_no_networks_available, context, 0);
        return true;
    }

    public static boolean showMobileDataWarningMessage(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        NetworkCapabilities networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (networkCapabilities == null ? false : networkCapabilities.hasTransport(1)) {
            return false;
        }
        ConnectivityManager connectivityManager2 =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        NetworkCapabilities networkCapabilities2 =
                connectivityManager2.getNetworkCapabilities(
                        connectivityManager2.getActiveNetwork());
        return networkCapabilities2 == null ? false : networkCapabilities2.hasTransport(0);
    }
}
