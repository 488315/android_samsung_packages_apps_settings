package com.google.android.setupdesign.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;

import com.google.android.setupcompat.util.Logger;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class DeviceHelper {
    public static final String DEVICE_NAME = "device_name";
    public static final String GET_DEVICE_NAME_METHOD = "getDeviceName";
    public static final String SUW_AUTHORITY = "com.google.android.setupwizard.partner";
    public static final Logger LOG = new Logger("DeviceHelper");
    public static final String TAG = "DeviceHelper";
    public static Bundle deviceName = null;

    public static CharSequence getDeviceName(Context context) {
        Partner partner;
        Bundle bundle = deviceName;
        if (bundle == null || bundle.isEmpty()) {
            try {
                deviceName =
                        context.getContentResolver()
                                .call(
                                        new Uri.Builder()
                                                .scheme("content")
                                                .authority(SUW_AUTHORITY)
                                                .build(),
                                        GET_DEVICE_NAME_METHOD,
                                        (String) null,
                                        (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(TAG, "device name unknown; return the device name as default value");
            }
        }
        Bundle bundle2 = deviceName;
        if (bundle2 != null && !bundle2.isEmpty()) {
            return deviceName.getCharSequence(GET_DEVICE_NAME_METHOD, null);
        }
        synchronized (Partner.class) {
            if (!Partner.searched) {
                PackageManager packageManager = context.getPackageManager();
                Iterator<ResolveInfo> it =
                        packageManager
                                .queryBroadcastReceivers(
                                        new Intent(
                                                "com.android.setupwizard.action.PARTNER_CUSTOMIZATION"),
                                        1835520)
                                .iterator();
                while (it.hasNext()) {
                    ActivityInfo activityInfo = it.next().activityInfo;
                    if (activityInfo != null) {
                        ApplicationInfo applicationInfo = activityInfo.applicationInfo;
                        if ((applicationInfo.flags & 1) != 0) {
                            try {
                                Partner.partner =
                                        new Partner(
                                                packageManager.getResourcesForApplication(
                                                        applicationInfo),
                                                applicationInfo.packageName);
                                break;
                            } catch (PackageManager.NameNotFoundException unused2) {
                                Log.w(
                                        "(setupdesign) Partner",
                                        "Failed to find resources for "
                                                + applicationInfo.packageName);
                            }
                        } else {
                            continue;
                        }
                    }
                }
                Partner.searched = true;
            }
            partner = Partner.partner;
        }
        if (partner != null) {
            try {
                String string =
                        partner.resources.getString(
                                partner.resources.getIdentifier(
                                        "device_name", "string", partner.packageName));
                if (!TextUtils.isEmpty(string)) {
                    return string;
                }
                LOG.w("The overlayDeviceName is null!");
            } catch (Resources.NotFoundException unused3) {
            }
        }
        return context.getString(R.string.sud_default_device_name);
    }
}
