package com.android.settings.slices;

import android.net.Uri;
import android.util.ArrayMap;

import androidx.picker.features.search.InitialSearchUtils$$ExternalSyntheticOutline0;

import com.android.settings.display.AlwaysOnDisplaySlice;
import com.android.settings.display.ScreenTimeoutPreferenceController;
import com.android.settings.flashlight.FlashlightSlice;
import com.android.settings.homepage.contextualcards.slices.BluetoothDevicesSlice;
import com.android.settings.homepage.contextualcards.slices.ContextualAdaptiveSleepSlice;
import com.android.settings.homepage.contextualcards.slices.DarkThemeSlice;
import com.android.settings.homepage.contextualcards.slices.FaceSetupSlice;
import com.android.settings.homepage.contextualcards.slices.LowStorageSlice;
import com.android.settings.location.LocationSlice;
import com.android.settings.media.MediaOutputIndicatorSlice;
import com.android.settings.media.RemoteMediaSlice;
import com.android.settings.network.ProviderModelSlice;
import com.android.settings.network.telephony.MobileDataSlice;
import com.android.settings.wifi.slice.WifiSlice;

import com.sec.ims.IMSParameter;
import com.sec.ims.gls.GlsIntent;
import com.sec.ims.settings.ImsProfile;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CustomSliceRegistry {
    public static final Uri ALWAYS_ON_SLICE_URI;
    public static final Uri BLUETOOTH_DEVICES_SLICE_URI;
    public static final Uri BLUETOOTH_URI;
    public static final Uri CONTEXTUAL_ADAPTIVE_SLEEP_URI;
    public static final Uri DARK_THEME_SLICE_URI;
    public static final Uri ENHANCED_4G_SLICE_URI;
    public static final Uri FACE_ENROLL_SLICE_URI;
    public static final Uri FLASHLIGHT_SLICE_URI;
    public static final Uri LOCATION_SLICE_URI;
    public static final Uri LOW_STORAGE_SLICE_URI;
    public static final Uri MEDIA_OUTPUT_INDICATOR_SLICE_URI;
    public static final Uri MOBILE_DATA_SLICE_URI;
    public static final Uri NFC_SLICE_URI;
    public static final Uri PROVIDER_MODEL_SLICE_URI;
    public static final Uri REMOTE_MEDIA_SLICE_URI;
    public static final Uri VOLUME_ALARM_URI;
    public static final Uri VOLUME_CALL_URI;
    public static final Uri VOLUME_MEDIA_URI;
    public static final Uri VOLUME_NOTIFICATION_URI;
    public static final Uri VOLUME_SEPARATE_RING_URI;
    public static final Uri VOLUME_SLICES_URI;
    public static final Uri WIFI_CALLING_PREFERENCE_URI;
    public static final Uri WIFI_CALLING_URI;
    public static final Uri WIFI_SLICE_URI;
    public static final Uri ZEN_MODE_SLICE_URI;
    static final Map<Uri, Class<? extends CustomSliceable>> sUriToSlice;

    static {
        Uri build =
                new Uri.Builder()
                        .scheme("content")
                        .authority("com.android.settings.slices")
                        .appendPath("intent")
                        .appendPath(ScreenTimeoutPreferenceController.PREF_NAME)
                        .build();
        CONTEXTUAL_ADAPTIVE_SLEEP_URI = build;
        BLUETOOTH_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "bluetooth");
        Uri m =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "bluetooth_devices");
        BLUETOOTH_DEVICES_SLICE_URI = m;
        ENHANCED_4G_SLICE_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "enhanced_4g_lte");
        Uri m2 =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "face_unlock_greeting_card");
        FACE_ENROLL_SLICE_URI = m2;
        Uri m3 =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "flashlight");
        FLASHLIGHT_SLICE_URI = m3;
        Uri m4 =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        GlsIntent.Extras.EXTRA_LOCATION);
        LOCATION_SLICE_URI = m4;
        Uri build2 =
                new Uri.Builder()
                        .scheme("content")
                        .authority("com.android.settings.slices")
                        .appendEncodedPath("intent")
                        .appendPath("low_storage")
                        .build();
        LOW_STORAGE_SLICE_URI = build2;
        NFC_SLICE_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "nfc_settings");
        Uri build3 =
                new Uri.Builder()
                        .scheme("content")
                        .authority("com.android.settings.slices")
                        .appendEncodedPath(IMSParameter.CALL.ACTION)
                        .appendPath("mobile_data")
                        .build();
        MOBILE_DATA_SLICE_URI = build3;
        Uri build4 =
                new Uri.Builder()
                        .scheme("content")
                        .authority("com.android.settings.slices")
                        .appendEncodedPath(IMSParameter.CALL.ACTION)
                        .appendPath("provider_model")
                        .build();
        PROVIDER_MODEL_SLICE_URI = build4;
        VOLUME_ALARM_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "alarm_volume");
        VOLUME_CALL_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "call_volume");
        VOLUME_MEDIA_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "media_volume");
        VOLUME_SEPARATE_RING_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "separate_ring_volume");
        VOLUME_NOTIFICATION_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "notification_volume");
        VOLUME_SLICES_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "volume_slices");
        WIFI_CALLING_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content", "com.android.settings.slices", "intent", "wifi_calling");
        WIFI_CALLING_PREFERENCE_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "wifi_calling_preference");
        Uri m5 =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        ImsProfile.PDN_WIFI);
        WIFI_SLICE_URI = m5;
        ZEN_MODE_SLICE_URI =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "zen_mode_toggle");
        Uri m6 =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        "intent",
                        "media_output_indicator");
        MEDIA_OUTPUT_INDICATOR_SLICE_URI = m6;
        Uri m7 =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "dark_theme");
        DARK_THEME_SLICE_URI = m7;
        Uri m8 =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "remote_media");
        REMOTE_MEDIA_SLICE_URI = m8;
        Uri m9 =
                InitialSearchUtils$$ExternalSyntheticOutline0.m(
                        "content",
                        "com.android.settings.slices",
                        IMSParameter.CALL.ACTION,
                        "always_on_display");
        ALWAYS_ON_SLICE_URI = m9;
        ArrayMap arrayMap = new ArrayMap();
        sUriToSlice = arrayMap;
        arrayMap.put(m3, FlashlightSlice.class);
        arrayMap.put(m4, LocationSlice.class);
        arrayMap.put(build3, MobileDataSlice.class);
        arrayMap.put(build4, ProviderModelSlice.class);
        arrayMap.put(m5, WifiSlice.class);
        arrayMap.put(m7, DarkThemeSlice.class);
        arrayMap.put(m9, AlwaysOnDisplaySlice.class);
        arrayMap.put(m6, MediaOutputIndicatorSlice.class);
        arrayMap.put(m8, RemoteMediaSlice.class);
        arrayMap.put(m2, FaceSetupSlice.class);
        arrayMap.put(build, ContextualAdaptiveSleepSlice.class);
        arrayMap.put(build2, LowStorageSlice.class);
        arrayMap.put(m, BluetoothDevicesSlice.class);
    }
}
