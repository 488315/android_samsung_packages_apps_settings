package com.android.settings.slices;

import android.app.Flags;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SubscriptionManager;
import android.telephony.ims.ImsMmTelManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.android.settings.bluetooth.BluetoothSliceBuilder;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SliderPreferenceController;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.network.ims.VolteQueryImsState;
import com.android.settings.network.ims.WifiCallingQueryImsState;
import com.android.settings.network.telephony.Enhanced4gLteSliceHelper;
import com.android.settings.notification.zen.ZenModeSliceBuilder;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.calling.WifiCallingSliceHelper;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SliceBroadcastReceiver extends BroadcastReceiver {
    public static BasePreferenceController getPreferenceController(Context context, String str) {
        Cursor indexedSliceData = new SlicesDatabaseAccessor(context).getIndexedSliceData(str);
        try {
            SliceData buildSliceData =
                    SlicesDatabaseAccessor.buildSliceData(indexedSliceData, null, false);
            indexedSliceData.close();
            String str2 = buildSliceData.mPreferenceController;
            try {
                return BasePreferenceController.createInstance(context, str2);
            } catch (IllegalStateException unused) {
                return BasePreferenceController.createInstance(context, str2, buildSliceData.mKey);
            }
        } catch (Throwable th) {
            try {
                indexedSliceData.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        char c;
        char c2;
        int i;
        int i2 = 0;
        String action = intent.getAction();
        String stringExtra = intent.getStringExtra("com.android.settings.slice.extra.key");
        Uri uri = CustomSliceRegistry.CONTEXTUAL_ADAPTIVE_SLEEP_URI;
        Uri parse = Uri.parse(action);
        Map<Uri, Class<? extends CustomSliceable>> map = CustomSliceRegistry.sUriToSlice;
        if (map.containsKey(parse != null ? parse.buildUpon().clearQuery().build() : null)) {
            Uri parse2 = Uri.parse(action);
            CustomSliceable.createInstance(
                            context,
                            map.get(
                                    parse2 != null
                                            ? parse2.buildUpon().clearQuery().build()
                                            : null))
                    .onNotifyChange(intent);
            return;
        }
        Uri data = intent.getData();
        action.getClass();
        switch (action.hashCode()) {
            case -2075790298:
                if (action.equals("com.android.settings.slice.action.TOGGLE_CHANGED")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -2013863560:
                if (action.equals(
                        "com.android.settings.mobilenetwork.action.ENHANCED_4G_LTE_CHANGED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -932197342:
                if (action.equals("com.android.settings.bluetooth.action.BLUETOOTH_MODE_CHANGED")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -362341757:
                if (action.equals(
                        "com.android.settings.wifi.calling.action.WIFI_CALLING_CHANGED")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -86230637:
                if (action.equals(
                        "com.android.settings.slice.action.WIFI_CALLING_PREFERENCE_WIFI_PREFERRED")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 17552563:
                if (action.equals("com.android.settings.slice.action.SLIDER_CHANGED")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 176882490:
                if (action.equals(
                        "com.android.settings.slice.action.WIFI_CALLING_PREFERENCE_WIFI_ONLY")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 495970216:
                if (action.equals(
                        "com.android.settings.slice.action.WIFI_CALLING_PREFERENCE_CELLULAR_PREFERRED")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1913359032:
                if (action.equals("com.android.settings.notification.ZEN_MODE_CHANGED")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                boolean booleanExtra =
                        intent.getBooleanExtra("android.app.slice.extra.TOGGLE_STATE", false);
                if (TextUtils.isEmpty(stringExtra)) {
                    throw new IllegalStateException(
                            "No key passed to Intent for toggle controller");
                }
                BasePreferenceController preferenceController =
                        getPreferenceController(context, stringExtra);
                if (!(preferenceController instanceof TogglePreferenceController)) {
                    throw new IllegalStateException(
                            AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                    "Toggle action passed for a non-toggle key: ", stringExtra));
                }
                if (!preferenceController.isAvailable()) {
                    Log.w(
                            "SettSliceBroadcastRec",
                            "Can't update " + stringExtra + " since the setting is unavailable");
                    if (preferenceController.hasAsyncUpdate()) {
                        return;
                    }
                    context.getContentResolver().notifyChange(data, null);
                    return;
                }
                ((TogglePreferenceController) preferenceController).setChecked(booleanExtra);
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                featureFactoryImpl
                        .getMetricsFeatureProvider()
                        .action(0, 1372, 0, booleanExtra ? 1 : 0, stringExtra);
                if (preferenceController.hasAsyncUpdate()) {
                    return;
                }
                context.getContentResolver().notifyChange(data, null);
                return;
            case 1:
                FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
                if (featureFactoryImpl2 == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                featureFactoryImpl2.getSlicesFeatureProvider().getClass();
                Enhanced4gLteSliceHelper enhanced4gLteSliceHelper =
                        new Enhanced4gLteSliceHelper(context);
                boolean booleanExtra2 =
                        intent.getBooleanExtra("android.app.slice.extra.TOGGLE_STATE", false);
                if (booleanExtra2
                        != intent.getBooleanExtra("android.app.slice.extra.TOGGLE_STATE", true)) {
                    enhanced4gLteSliceHelper
                            .mContext
                            .getContentResolver()
                            .notifyChange(CustomSliceRegistry.ENHANCED_4G_SLICE_URI, null);
                    return;
                }
                int defaultVoiceSubscriptionId =
                        SubscriptionManager.getDefaultVoiceSubscriptionId();
                if (!SubscriptionManager.isValidSubscriptionId(defaultVoiceSubscriptionId)) {
                    enhanced4gLteSliceHelper
                            .mContext
                            .getContentResolver()
                            .notifyChange(CustomSliceRegistry.ENHANCED_4G_SLICE_URI, null);
                    return;
                }
                VolteQueryImsState queryImsState =
                        enhanced4gLteSliceHelper.queryImsState(defaultVoiceSubscriptionId);
                if (queryImsState.isEnabledByUser() && queryImsState.isAllowUserControl()) {
                    i2 = 1;
                }
                if (booleanExtra2 == i2) {
                    enhanced4gLteSliceHelper
                            .mContext
                            .getContentResolver()
                            .notifyChange(CustomSliceRegistry.ENHANCED_4G_SLICE_URI, null);
                    return;
                }
                if (queryImsState.isVoLteProvisioned()) {
                    enhanced4gLteSliceHelper.setEnhanced4gLteModeSetting(
                            defaultVoiceSubscriptionId, booleanExtra2);
                }
                enhanced4gLteSliceHelper
                        .mContext
                        .getContentResolver()
                        .notifyChange(CustomSliceRegistry.ENHANCED_4G_SLICE_URI, null);
                return;
            case 2:
                IntentFilter intentFilter = BluetoothSliceBuilder.INTENT_FILTER;
                boolean booleanExtra3 =
                        intent.getBooleanExtra("android.app.slice.extra.TOGGLE_STATE", false);
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                if (booleanExtra3) {
                    defaultAdapter.enable();
                    return;
                } else {
                    defaultAdapter.disable();
                    return;
                }
            case 3:
                FeatureFactoryImpl featureFactoryImpl3 = FeatureFactoryImpl._factory;
                if (featureFactoryImpl3 == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                featureFactoryImpl3.getSlicesFeatureProvider().getClass();
                WifiCallingSliceHelper wifiCallingSliceHelper = new WifiCallingSliceHelper(context);
                int defaultVoiceSubscriptionId2 =
                        SubscriptionManager.getDefaultVoiceSubscriptionId();
                if (!SubscriptionManager.isValidSubscriptionId(defaultVoiceSubscriptionId2)
                        || !intent.hasExtra("android.app.slice.extra.TOGGLE_STATE")) {
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(
                            defaultVoiceSubscriptionId2,
                            "action not taken: subId ",
                            "WifiCallingSliceHelper");
                } else if (wifiCallingSliceHelper
                        .queryImsState(defaultVoiceSubscriptionId2)
                        .isWifiCallingProvisioned()) {
                    boolean isWifiCallingEnabled = wifiCallingSliceHelper.isWifiCallingEnabled();
                    boolean z =
                            !intent.getBooleanExtra(
                                    "android.app.slice.extra.TOGGLE_STATE", isWifiCallingEnabled);
                    Intent wifiCallingCarrierActivityIntent =
                            wifiCallingSliceHelper.getWifiCallingCarrierActivityIntent(
                                    defaultVoiceSubscriptionId2);
                    if (z == isWifiCallingEnabled
                            || (wifiCallingCarrierActivityIntent != null && z)) {
                        Log.w(
                                "WifiCallingSliceHelper",
                                "action not taken: subId "
                                        + defaultVoiceSubscriptionId2
                                        + " from "
                                        + isWifiCallingEnabled
                                        + " to "
                                        + z);
                    } else {
                        try {
                            ImsMmTelManager.createForSubscriptionId(defaultVoiceSubscriptionId2)
                                    .setVoWiFiSettingEnabled(z);
                        } catch (IllegalArgumentException e) {
                            Log.e(
                                    "WifiCallingSliceHelper",
                                    "handleWifiCallingChanged: Exception",
                                    e);
                        }
                    }
                } else {
                    Log.w(
                            "WifiCallingSliceHelper",
                            "action not taken: subId "
                                    + defaultVoiceSubscriptionId2
                                    + " needs provision");
                }
                wifiCallingSliceHelper
                        .mContext
                        .getContentResolver()
                        .notifyChange(CustomSliceRegistry.WIFI_CALLING_URI, null);
                return;
            case 4:
            case 6:
            case 7:
                FeatureFactoryImpl featureFactoryImpl4 = FeatureFactoryImpl._factory;
                if (featureFactoryImpl4 == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                featureFactoryImpl4.getSlicesFeatureProvider().getClass();
                WifiCallingSliceHelper wifiCallingSliceHelper2 =
                        new WifiCallingSliceHelper(context);
                int defaultVoiceSubscriptionId3 =
                        SubscriptionManager.getDefaultVoiceSubscriptionId();
                if (SubscriptionManager.isValidSubscriptionId(defaultVoiceSubscriptionId3)) {
                    boolean isCarrierConfigManagerKeyEnabled =
                            wifiCallingSliceHelper2.isCarrierConfigManagerKeyEnabled(
                                    defaultVoiceSubscriptionId3, "editable_wfc_mode_bool", false);
                    boolean isCarrierConfigManagerKeyEnabled2 =
                            wifiCallingSliceHelper2.isCarrierConfigManagerKeyEnabled(
                                    defaultVoiceSubscriptionId3,
                                    "carrier_wfc_supports_wifi_only_bool",
                                    true);
                    WifiCallingQueryImsState queryImsState2 =
                            wifiCallingSliceHelper2.queryImsState(defaultVoiceSubscriptionId3);
                    if (isCarrierConfigManagerKeyEnabled
                            && queryImsState2.isWifiCallingProvisioned()
                            && queryImsState2.isEnabledByUser()
                            && queryImsState2.isAllowUserControl()) {
                        ImsMmTelManager createForSubscriptionId =
                                ImsMmTelManager.createForSubscriptionId(
                                        defaultVoiceSubscriptionId3);
                        try {
                            int voWiFiModeSetting = createForSubscriptionId.getVoWiFiModeSetting();
                            String action2 = intent.getAction();
                            action2.getClass();
                            switch (action2.hashCode()) {
                                case -86230637:
                                    if (action2.equals(
                                            "com.android.settings.slice.action.WIFI_CALLING_PREFERENCE_WIFI_PREFERRED")) {
                                        c2 = 0;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 176882490:
                                    if (action2.equals(
                                            "com.android.settings.slice.action.WIFI_CALLING_PREFERENCE_WIFI_ONLY")) {
                                        c2 = 1;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 495970216:
                                    if (action2.equals(
                                            "com.android.settings.slice.action.WIFI_CALLING_PREFERENCE_CELLULAR_PREFERRED")) {
                                        c2 = 2;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                default:
                                    c2 = 65535;
                                    break;
                            }
                            switch (c2) {
                                case 0:
                                    i = -1;
                                    i2 = 2;
                                    break;
                                case 1:
                                    if (isCarrierConfigManagerKeyEnabled2) {
                                        i = -1;
                                        break;
                                    }
                                    i = -1;
                                    i2 = -1;
                                    break;
                                case 2:
                                    i = -1;
                                    i2 = 1;
                                    break;
                                default:
                                    i = -1;
                                    i2 = -1;
                                    break;
                            }
                            if (i2 != i && i2 != voWiFiModeSetting) {
                                try {
                                    createForSubscriptionId.setVoWiFiModeSetting(i2);
                                } catch (IllegalArgumentException e2) {
                                    Log.e(
                                            "WifiCallingSliceHelper",
                                            "handleWifiCallingPreferenceChanged: Exception",
                                            e2);
                                }
                            }
                        } catch (IllegalArgumentException e3) {
                            Log.e(
                                    "WifiCallingSliceHelper",
                                    "handleWifiCallingPreferenceChanged: Exception",
                                    e3);
                            return;
                        }
                    }
                }
                wifiCallingSliceHelper2
                        .mContext
                        .getContentResolver()
                        .notifyChange(CustomSliceRegistry.WIFI_CALLING_PREFERENCE_URI, null);
                return;
            case 5:
                int intExtra = intent.getIntExtra("android.app.slice.extra.RANGE_VALUE", -1);
                if (TextUtils.isEmpty(stringExtra)) {
                    throw new IllegalArgumentException(
                            "No key passed to Intent for slider controller. Use extra:"
                                + " com.android.settings.slice.extra.key");
                }
                if (intExtra == -1) {
                    throw new IllegalArgumentException(
                            "Invalid position passed to Slider controller");
                }
                BasePreferenceController preferenceController2 =
                        getPreferenceController(context, stringExtra);
                if (!(preferenceController2 instanceof SliderPreferenceController)) {
                    throw new IllegalArgumentException(
                            AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                    "Slider action passed for a non-slider key: ", stringExtra));
                }
                if (!preferenceController2.isAvailable()) {
                    Log.w(
                            "SettSliceBroadcastRec",
                            "Can't update " + stringExtra + " since the setting is unavailable");
                    context.getContentResolver().notifyChange(data, null);
                    return;
                }
                SliderPreferenceController sliderPreferenceController =
                        (SliderPreferenceController) preferenceController2;
                int min = sliderPreferenceController.getMin();
                int max = sliderPreferenceController.getMax();
                if (intExtra < min || intExtra > max) {
                    StringBuilder m =
                            RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                                    "Invalid position passed to Slider controller. Expected between"
                                        + " ",
                                    " and ",
                                    min,
                                    max,
                                    " but found ");
                    m.append(intExtra);
                    throw new IllegalArgumentException(m.toString());
                }
                sliderPreferenceController.setSliderPosition(intExtra);
                FeatureFactoryImpl featureFactoryImpl5 = FeatureFactoryImpl._factory;
                if (featureFactoryImpl5 == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                featureFactoryImpl5
                        .getMetricsFeatureProvider()
                        .action(0, 1372, 0, intExtra, stringExtra);
                context.getContentResolver().notifyChange(data, null);
                return;
            case '\b':
                IntentFilter intentFilter2 = ZenModeSliceBuilder.INTENT_FILTER;
                boolean booleanExtra4 =
                        intent.getBooleanExtra("android.app.slice.extra.TOGGLE_STATE", false);
                if (Flags.modesApi()) {
                    NotificationManager.from(context)
                            .setZenMode(booleanExtra4 ? 1 : 0, null, "ZenModeSliceBuilder", true);
                    return;
                } else {
                    NotificationManager.from(context)
                            .setZenMode(booleanExtra4 ? 1 : 0, null, "ZenModeSliceBuilder");
                    return;
                }
            default:
                return;
        }
    }
}
