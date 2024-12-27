package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import androidx.collection.SimpleArrayMap;

import com.android.settings.R;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;
import com.google.android.gms.common.wrappers.Wrappers;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class zac {
    public static final SimpleArrayMap zaa = new SimpleArrayMap();
    public static Locale zab;

    public static String zaa(Context context) {
        String packageName = context.getPackageName();
        try {
            PackageManagerWrapper zza = Wrappers.zza.zza(context);
            return zza.zza
                    .getPackageManager()
                    .getApplicationLabel(
                            zza.zza.getPackageManager().getApplicationInfo(packageName, 0))
                    .toString();
        } catch (PackageManager.NameNotFoundException | NullPointerException unused) {
            String str = context.getApplicationInfo().name;
            return TextUtils.isEmpty(str) ? packageName : str;
        }
    }

    public static String zad(Context context, int i) {
        Resources resources = context.getResources();
        String zaa2 = zaa(context);
        if (i == 1) {
            return resources.getString(R.string.common_google_play_services_install_text, zaa2);
        }
        if (i == 2) {
            PackageManager packageManager = context.getPackageManager();
            if (DeviceProperties.zzd == null) {
                DeviceProperties.zzd =
                        Boolean.valueOf(
                                packageManager.hasSystemFeature("android.hardware.type.watch"));
            }
            if (DeviceProperties.zzd.booleanValue()) {
                if (DeviceProperties.zze == null) {
                    DeviceProperties.zze =
                            Boolean.valueOf(
                                    context.getPackageManager().hasSystemFeature("cn.google"));
                }
                DeviceProperties.zze.booleanValue();
            }
            return resources.getString(R.string.common_google_play_services_update_text, zaa2);
        }
        if (i == 3) {
            return resources.getString(R.string.common_google_play_services_enable_text, zaa2);
        }
        if (i == 5) {
            return zah(context, "common_google_play_services_invalid_account_text", zaa2);
        }
        if (i == 7) {
            return zah(context, "common_google_play_services_network_error_text", zaa2);
        }
        if (i == 9) {
            return resources.getString(R.string.common_google_play_services_unsupported_text, zaa2);
        }
        if (i == 20) {
            return zah(context, "common_google_play_services_restricted_profile_text", zaa2);
        }
        switch (i) {
            case 16:
                return zah(context, "common_google_play_services_api_unavailable_text", zaa2);
            case 17:
                return zah(context, "common_google_play_services_sign_in_failed_text", zaa2);
            case 18:
                return resources.getString(
                        R.string.common_google_play_services_updating_text, zaa2);
            default:
                return resources.getString(
                        R.string.common_google_play_services_unknown_issue, zaa2);
        }
    }

    public static String zag(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                return resources.getString(R.string.common_google_play_services_install_title);
            case 2:
                return resources.getString(R.string.common_google_play_services_update_title);
            case 3:
                return resources.getString(R.string.common_google_play_services_enable_title);
            case 4:
            case 6:
            case 18:
                return null;
            case 5:
                Log.e(
                        "GoogleApiAvailability",
                        "An invalid account was specified when connecting. Please provide a valid"
                            + " account.");
                return zai(context, "common_google_play_services_invalid_account_title");
            case 7:
                Log.e(
                        "GoogleApiAvailability",
                        "Network error occurred. Please retry request later.");
                return zai(context, "common_google_play_services_network_error_title");
            case 8:
                Log.e(
                        "GoogleApiAvailability",
                        "Internal error occurred. Please see logs for detailed information");
                return null;
            case 9:
                Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
                return null;
            case 10:
                Log.e(
                        "GoogleApiAvailability",
                        "Developer error occurred. Please see logs for detailed information");
                return null;
            case 11:
                Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
                return null;
            case 12:
            case 13:
            case 14:
            case 15:
            case 19:
            default:
                StringBuilder sb = new StringBuilder(33);
                sb.append("Unexpected error code ");
                sb.append(i);
                Log.e("GoogleApiAvailability", sb.toString());
                return null;
            case 16:
                Log.e(
                        "GoogleApiAvailability",
                        "One of the API components you attempted to connect to is not available.");
                return null;
            case 17:
                Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
                return zai(context, "common_google_play_services_sign_in_failed_title");
            case 20:
                Log.e(
                        "GoogleApiAvailability",
                        "The current user profile is restricted and could not use authenticated"
                            + " features.");
                return zai(context, "common_google_play_services_restricted_profile_title");
        }
    }

    public static String zah(Context context, String str, String str2) {
        Resources resources = context.getResources();
        String zai = zai(context, str);
        if (zai == null) {
            zai = resources.getString(R.string.common_google_play_services_unknown_issue);
        }
        return String.format(resources.getConfiguration().locale, zai, str2);
    }

    public static String zai(Context context, String str) {
        Resources resources;
        SimpleArrayMap simpleArrayMap = zaa;
        synchronized (simpleArrayMap) {
            try {
                Locale locale = context.getResources().getConfiguration().getLocales().get(0);
                if (!locale.equals(zab)) {
                    simpleArrayMap.clear();
                    zab = locale;
                }
                String str2 = (String) simpleArrayMap.get(str);
                if (str2 != null) {
                    return str2;
                }
                int i = GooglePlayServicesUtil.$r8$clinit;
                try {
                    resources =
                            context.getPackageManager()
                                    .getResourcesForApplication("com.google.android.gms");
                } catch (PackageManager.NameNotFoundException unused) {
                    resources = null;
                }
                if (resources == null) {
                    return null;
                }
                int identifier = resources.getIdentifier(str, "string", "com.google.android.gms");
                if (identifier == 0) {
                    Log.w(
                            "GoogleApiAvailability",
                            str.length() != 0
                                    ? "Missing resource: ".concat(str)
                                    : new String("Missing resource: "));
                    return null;
                }
                String string = resources.getString(identifier);
                if (TextUtils.isEmpty(string)) {
                    Log.w(
                            "GoogleApiAvailability",
                            str.length() != 0
                                    ? "Got empty resource: ".concat(str)
                                    : new String("Got empty resource: "));
                    return null;
                }
                zaa.put(str, string);
                return string;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
