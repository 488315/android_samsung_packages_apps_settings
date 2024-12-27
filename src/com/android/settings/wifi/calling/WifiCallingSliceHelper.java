package com.android.settings.wifi.calling;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.ims.ImsMmTelManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.R;
import com.android.settings.network.ims.WifiCallingQueryImsState;
import com.android.settings.slices.SliceBroadcastReceiver;
import com.android.settingslib.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiCallingSliceHelper {
    public final Context mContext;

    public WifiCallingSliceHelper(Context context) {
        this.mContext = context;
    }

    public static int getWfcMode(final ImsMmTelManager imsMmTelManager) {
        FutureTask futureTask =
                new FutureTask(
                        new Callable() { // from class:
                                         // com.android.settings.wifi.calling.WifiCallingSliceHelper.1
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                int i;
                                try {
                                    i = imsMmTelManager.getVoWiFiModeSetting();
                                } catch (IllegalArgumentException e) {
                                    Log.e(
                                            "WifiCallingSliceHelper",
                                            "getResourceIdForWfcMode: Exception",
                                            e);
                                    i = -1;
                                }
                                return Integer.valueOf(i);
                            }
                        });
        Executors.newSingleThreadExecutor().execute(futureTask);
        return ((Integer) futureTask.get(2000L, TimeUnit.MILLISECONDS)).intValue();
    }

    public final Slice createWifiCallingPreferenceSlice(Uri uri) {
        int defaultVoiceSubscriptionId = SubscriptionManager.getDefaultVoiceSubscriptionId();
        CharSequence charSequence = null;
        if (!SubscriptionManager.isValidSubscriptionId(defaultVoiceSubscriptionId)) {
            Log.d("WifiCallingSliceHelper", "Invalid Subscription Id");
            return null;
        }
        boolean isCarrierConfigManagerKeyEnabled =
                isCarrierConfigManagerKeyEnabled(
                        defaultVoiceSubscriptionId, "editable_wfc_mode_bool", false);
        boolean isCarrierConfigManagerKeyEnabled2 =
                isCarrierConfigManagerKeyEnabled(
                        defaultVoiceSubscriptionId, "carrier_wfc_supports_wifi_only_bool", true);
        if (!isCarrierConfigManagerKeyEnabled) {
            Log.d("WifiCallingSliceHelper", "Wifi calling preference is not editable");
            return null;
        }
        if (!queryImsState(defaultVoiceSubscriptionId).isReadyToWifiCalling()) {
            Log.d(
                    "WifiCallingSliceHelper",
                    "Wifi calling is either not provisioned or not enabled by platform");
            return null;
        }
        try {
            ImsMmTelManager createForSubscriptionId =
                    ImsMmTelManager.createForSubscriptionId(defaultVoiceSubscriptionId);
            boolean isWifiCallingEnabled = isWifiCallingEnabled();
            int wfcMode = getWfcMode(createForSubscriptionId);
            if (!isWifiCallingEnabled) {
                Resources resourcesForSubId =
                        SubscriptionManager.getResourcesForSubId(
                                this.mContext, defaultVoiceSubscriptionId);
                return getNonActionableWifiCallingSlice(
                        resourcesForSubId.getText(R.string.wifi_calling_mode_title),
                        resourcesForSubId.getText(R.string.wifi_calling_turn_on),
                        uri,
                        getActivityIntent());
            }
            IconCompat createWithResource =
                    IconCompat.createWithResource(this.mContext, R.drawable.wifi_signal);
            Resources resourcesForSubId2 =
                    SubscriptionManager.getResourcesForSubId(
                            this.mContext, defaultVoiceSubscriptionId);
            ListBuilder listBuilder = new ListBuilder(this.mContext, uri);
            listBuilder.mImpl.setColor(
                    Utils.getColorAttrDefaultColor(this.mContext, android.R.attr.colorAccent));
            ListBuilder.HeaderBuilder headerBuilder = new ListBuilder.HeaderBuilder();
            headerBuilder.mTitle = resourcesForSubId2.getText(R.string.wifi_calling_mode_title);
            headerBuilder.mTitleLoading = false;
            headerBuilder.mPrimaryAction =
                    SliceAction.createDeeplink(
                            getActivityIntent(),
                            createWithResource,
                            0,
                            resourcesForSubId2.getText(R.string.wifi_calling_mode_title));
            if (!com.android.settings.Utils.isSettingsIntelligence(this.mContext)) {
                Resources resourcesForSubId3 =
                        SubscriptionManager.getResourcesForSubId(
                                this.mContext, defaultVoiceSubscriptionId);
                if (wfcMode == 0) {
                    charSequence = resourcesForSubId3.getText(17043507);
                } else if (wfcMode == 1) {
                    charSequence = resourcesForSubId3.getText(17043506);
                } else if (wfcMode == 2) {
                    charSequence = resourcesForSubId3.getText(17043508);
                }
                headerBuilder.mSubtitle = charSequence;
                headerBuilder.mSubtitleLoading = false;
            }
            listBuilder.mImpl.setHeader(headerBuilder);
            if (isCarrierConfigManagerKeyEnabled2) {
                listBuilder.addRow(
                        wifiPreferenceRowBuilder(
                                17043507,
                                defaultVoiceSubscriptionId,
                                "com.android.settings.slice.action.WIFI_CALLING_PREFERENCE_WIFI_ONLY",
                                wfcMode == 0));
            }
            listBuilder.addRow(
                    wifiPreferenceRowBuilder(
                            17043508,
                            defaultVoiceSubscriptionId,
                            "com.android.settings.slice.action.WIFI_CALLING_PREFERENCE_WIFI_PREFERRED",
                            wfcMode == 2));
            listBuilder.addRow(
                    wifiPreferenceRowBuilder(
                            17043506,
                            defaultVoiceSubscriptionId,
                            "com.android.settings.slice.action.WIFI_CALLING_PREFERENCE_CELLULAR_PREFERRED",
                            wfcMode == 1));
            return listBuilder.build();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Log.e("WifiCallingSliceHelper", "Unable to get wifi calling preferred mode", e);
            return null;
        }
    }

    public final Slice createWifiCallingSlice(Uri uri) {
        int defaultVoiceSubscriptionId = SubscriptionManager.getDefaultVoiceSubscriptionId();
        if (!queryImsState(defaultVoiceSubscriptionId).isReadyToWifiCalling()) {
            Log.d(
                    "WifiCallingSliceHelper",
                    "Wifi calling is either not provisioned or not enabled by Platform");
            return null;
        }
        boolean isWifiCallingEnabled = isWifiCallingEnabled();
        if (getWifiCallingCarrierActivityIntent(defaultVoiceSubscriptionId) != null
                && !isWifiCallingEnabled) {
            Log.d("WifiCallingSliceHelper", "Needs Activation");
            Resources resourcesForSubId =
                    SubscriptionManager.getResourcesForSubId(
                            this.mContext, defaultVoiceSubscriptionId);
            return getNonActionableWifiCallingSlice(
                    resourcesForSubId.getText(R.string.wifi_calling_settings_title),
                    resourcesForSubId.getText(
                            R.string.wifi_calling_settings_activation_instructions),
                    uri,
                    getActivityIntent());
        }
        IconCompat createWithResource =
                IconCompat.createWithResource(this.mContext, R.drawable.wifi_signal);
        Resources resourcesForSubId2 =
                SubscriptionManager.getResourcesForSubId(this.mContext, defaultVoiceSubscriptionId);
        ListBuilder listBuilder = new ListBuilder(this.mContext, uri);
        listBuilder.mImpl.setColor(
                Utils.getColorAttrDefaultColor(this.mContext, android.R.attr.colorAccent));
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle = resourcesForSubId2.getText(R.string.wifi_calling_settings_title);
        rowBuilder.mTitleLoading = false;
        rowBuilder.addEndItem(
                new SliceAction(
                        getBroadcastIntent(
                                "com.android.settings.wifi.calling.action.WIFI_CALLING_CHANGED",
                                isWifiCallingEnabled),
                        null,
                        isWifiCallingEnabled));
        rowBuilder.mPrimaryAction =
                SliceAction.createDeeplink(
                        getActivityIntent(),
                        createWithResource,
                        0,
                        resourcesForSubId2.getText(R.string.wifi_calling_settings_title));
        listBuilder.mImpl.addRow(rowBuilder);
        return listBuilder.build();
    }

    public final PendingIntent getActivityIntent() {
        Intent intent = new Intent("android.settings.WIFI_CALLING_SETTINGS");
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        intent.addFlags(268435456);
        return PendingIntent.getActivity(this.mContext, 0, intent, 67108864);
    }

    public final PendingIntent getBroadcastIntent(String str, boolean z) {
        Intent intent = new Intent(str);
        intent.setClass(this.mContext, SliceBroadcastReceiver.class);
        intent.addFlags(268435456);
        intent.putExtra("android.app.slice.extra.TOGGLE_STATE", z);
        return PendingIntent.getBroadcast(this.mContext, 0, intent, 335544320);
    }

    public final Slice getNonActionableWifiCallingSlice(
            CharSequence charSequence,
            CharSequence charSequence2,
            Uri uri,
            PendingIntent pendingIntent) {
        IconCompat createWithResource =
                IconCompat.createWithResource(this.mContext, R.drawable.wifi_signal);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle = charSequence;
        rowBuilder.mTitleLoading = false;
        rowBuilder.mPrimaryAction =
                SliceAction.createDeeplink(pendingIntent, createWithResource, 1, charSequence);
        if (!com.android.settings.Utils.isSettingsIntelligence(this.mContext)) {
            rowBuilder.setSubtitle(charSequence2);
        }
        ListBuilder listBuilder = new ListBuilder(this.mContext, uri);
        listBuilder.mImpl.setColor(
                Utils.getColorAttrDefaultColor(this.mContext, android.R.attr.colorAccent));
        listBuilder.mImpl.addRow(rowBuilder);
        return listBuilder.build();
    }

    public final Intent getWifiCallingCarrierActivityIntent(int i) {
        PersistableBundle configForSubId;
        ComponentName unflattenFromString;
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) this.mContext.getSystemService(CarrierConfigManager.class);
        if (carrierConfigManager == null
                || (configForSubId = carrierConfigManager.getConfigForSubId(i)) == null) {
            return null;
        }
        String string = configForSubId.getString("wfc_emergency_address_carrier_app_string");
        if (TextUtils.isEmpty(string)
                || (unflattenFromString = ComponentName.unflattenFromString(string)) == null) {
            return null;
        }
        Intent intent = new Intent();
        intent.setComponent(unflattenFromString);
        return intent;
    }

    public final boolean isCarrierConfigManagerKeyEnabled(int i, String str, boolean z) {
        PersistableBundle configForSubId;
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) this.mContext.getSystemService(CarrierConfigManager.class);
        if (carrierConfigManager == null
                || (configForSubId = carrierConfigManager.getConfigForSubId(i)) == null) {
            return false;
        }
        return configForSubId.getBoolean(str, z);
    }

    public final boolean isWifiCallingEnabled() {
        WifiCallingQueryImsState queryImsState =
                queryImsState(SubscriptionManager.getDefaultVoiceSubscriptionId());
        return queryImsState.isEnabledByUser() && queryImsState.isAllowUserControl();
    }

    public WifiCallingQueryImsState queryImsState(int i) {
        return new WifiCallingQueryImsState(this.mContext, i);
    }

    public final ListBuilder.RowBuilder wifiPreferenceRowBuilder(
            int i, int i2, String str, boolean z) {
        IconCompat createWithResource =
                IconCompat.createWithResource(this.mContext, R.drawable.radio_button_check);
        Resources resourcesForSubId = SubscriptionManager.getResourcesForSubId(this.mContext, i2);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle = resourcesForSubId.getText(i);
        rowBuilder.mTitleLoading = false;
        rowBuilder.mTitleAction =
                new SliceAction(
                        getBroadcastIntent(str, z),
                        createWithResource,
                        resourcesForSubId.getText(i),
                        z);
        rowBuilder.mTitleIcon = null;
        rowBuilder.mTitleImageMode = 0;
        rowBuilder.mTitleActionLoading = false;
        return rowBuilder;
    }
}
