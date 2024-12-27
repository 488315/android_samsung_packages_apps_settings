package com.android.settings.slices;

import android.net.Uri;

import androidx.picker.features.search.InitialSearchUtils$$ExternalSyntheticOutline0;

import com.sec.ims.IMSParameter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RestrictedSliceUtils {
    public static final Uri NOTIFY_OPEN_NETWORKS_SLICE_URI =
            InitialSearchUtils$$ExternalSyntheticOutline0.m(
                    "content",
                    "com.android.settings.slices",
                    IMSParameter.CALL.ACTION,
                    "notify_open_networks");
    public static final Uri AUTO_TURN_ON_WIFI_SLICE_URI =
            InitialSearchUtils$$ExternalSyntheticOutline0.m(
                    "content",
                    "com.android.settings.slices",
                    IMSParameter.CALL.ACTION,
                    "enable_wifi_wakeup");
    public static final Uri USB_TETHERING_SLICE_URI =
            InitialSearchUtils$$ExternalSyntheticOutline0.m(
                    "content",
                    "com.android.settings.slices",
                    IMSParameter.CALL.ACTION,
                    "enable_usb_tethering");
    public static final Uri BLUETOOTH_TETHERING_SLICE_URI =
            InitialSearchUtils$$ExternalSyntheticOutline0.m(
                    "content",
                    "com.android.settings.slices",
                    IMSParameter.CALL.ACTION,
                    "enable_bluetooth_tethering_2");
}
