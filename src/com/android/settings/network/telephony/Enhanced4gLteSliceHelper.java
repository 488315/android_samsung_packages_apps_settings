package com.android.settings.network.telephony;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.ims.ImsMmTelManager;
import android.util.Log;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.R;
import com.android.settings.network.ims.VolteQueryImsState;
import com.android.settings.slices.SliceBroadcastReceiver;
import com.android.settingslib.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class Enhanced4gLteSliceHelper {
    public final Context mContext;

    public Enhanced4gLteSliceHelper(Context context) {
        this.mContext = context;
    }

    public final Slice createEnhanced4gLteSlice(Uri uri) {
        int defaultVoiceSubscriptionId = SubscriptionManager.getDefaultVoiceSubscriptionId();
        if (!SubscriptionManager.isValidSubscriptionId(defaultVoiceSubscriptionId)) {
            Log.d("Enhanced4gLteSlice", "Invalid subscription Id");
            return null;
        }
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) this.mContext.getSystemService(CarrierConfigManager.class);
        PersistableBundle configForSubId =
                carrierConfigManager != null
                        ? carrierConfigManager.getConfigForSubId(defaultVoiceSubscriptionId)
                        : null;
        if (!(configForSubId != null
                ? configForSubId.getBoolean("hide_enhanced_4g_lte_bool", false)
                : false)) {
            CarrierConfigManager carrierConfigManager2 =
                    (CarrierConfigManager)
                            this.mContext.getSystemService(CarrierConfigManager.class);
            PersistableBundle configForSubId2 =
                    carrierConfigManager2 != null
                            ? carrierConfigManager2.getConfigForSubId(defaultVoiceSubscriptionId)
                            : null;
            if (configForSubId2 != null
                    ? configForSubId2.getBoolean("editable_enhanced_4g_lte_bool", true)
                    : true) {
                VolteQueryImsState queryImsState = queryImsState(defaultVoiceSubscriptionId);
                if (!queryImsState.isVoLteProvisioned()) {
                    Log.d(
                            "Enhanced4gLteSlice",
                            "Setting is either not provisioned or not enabled by Platform");
                    return null;
                }
                try {
                    return getEnhanced4gLteSlice(
                            queryImsState.isEnabledByUser(), uri, defaultVoiceSubscriptionId);
                } catch (IllegalArgumentException e) {
                    Log.e(
                            "Enhanced4gLteSlice",
                            "Unable to read the current Enhanced 4g LTE status",
                            e);
                    return null;
                }
            }
        }
        Log.d("Enhanced4gLteSlice", "Setting is either hidden or not editable");
        return null;
    }

    public final Slice getEnhanced4gLteSlice(boolean z, Uri uri, int i) {
        IconCompat createWithResource =
                IconCompat.createWithResource(this.mContext, R.drawable.ic_launcher_settings);
        ListBuilder listBuilder = new ListBuilder(this.mContext, uri);
        listBuilder.mImpl.setColor(
                Utils.getColorAttrDefaultColor(this.mContext, android.R.attr.colorAccent));
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle = getEnhanced4glteModeTitle(i);
        rowBuilder.mTitleLoading = false;
        Intent intent =
                new Intent("com.android.settings.mobilenetwork.action.ENHANCED_4G_LTE_CHANGED");
        intent.setClass(this.mContext, SliceBroadcastReceiver.class);
        rowBuilder.addEndItem(
                new SliceAction(
                        PendingIntent.getBroadcast(this.mContext, 0, intent, 335544320), null, z));
        Intent intent2 = new Intent("android.settings.NETWORK_OPERATOR_SETTINGS");
        intent2.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        intent2.addFlags(268435456);
        rowBuilder.mPrimaryAction =
                SliceAction.createDeeplink(
                        PendingIntent.getActivity(this.mContext, 0, intent2, 67108864),
                        createWithResource,
                        0,
                        getEnhanced4glteModeTitle(i));
        listBuilder.mImpl.addRow(rowBuilder);
        return listBuilder.build();
    }

    public final CharSequence getEnhanced4glteModeTitle(int i) {
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) this.mContext.getSystemService(CarrierConfigManager.class);
        PersistableBundle configForSubId =
                carrierConfigManager != null ? carrierConfigManager.getConfigForSubId(i) : null;
        int i2 =
                configForSubId != null
                        ? configForSubId.getInt("enhanced_4g_lte_title_variant_int", 0)
                        : 0;
        CarrierConfigManager carrierConfigManager2 =
                (CarrierConfigManager) this.mContext.getSystemService(CarrierConfigManager.class);
        PersistableBundle configForSubId2 =
                carrierConfigManager2 != null ? carrierConfigManager2.getConfigForSubId(i) : null;
        boolean z =
                configForSubId2 != null
                        ? configForSubId2.getBoolean("show_4g_for_lte_data_icon_bool", false)
                        : false;
        CharSequence[] textArray =
                this.mContext
                        .getResources()
                        .getTextArray(R.array.enhanced_4g_lte_mode_title_variant);
        char c = 1;
        if (i2 != 1) {
            c = z ? (char) 2 : (char) 0;
        }
        return textArray[c];
    }

    public VolteQueryImsState queryImsState(int i) {
        return new VolteQueryImsState(this.mContext, i);
    }

    public void setEnhanced4gLteModeSetting(int i, boolean z) {
        ImsMmTelManager createForSubscriptionId;
        if (SubscriptionManager.isValidSubscriptionId(i)
                && (createForSubscriptionId = ImsMmTelManager.createForSubscriptionId(i)) != null) {
            try {
                createForSubscriptionId.setAdvancedCallingSettingEnabled(z);
            } catch (IllegalArgumentException e) {
                Log.w(
                        "Enhanced4gLteSlice",
                        "Unable to change the Enhanced 4g LTE to " + z + ". subId=" + i,
                        e);
            }
        }
    }
}
