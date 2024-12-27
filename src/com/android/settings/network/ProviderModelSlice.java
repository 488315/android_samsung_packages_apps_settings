package com.android.settings.network;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.UserManager;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyDisplayInfo;
import android.text.Html;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.display.AlwaysOnDisplaySlice$$ExternalSyntheticLambda0;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.network.telephony.NetworkProviderWorker;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settings.slices.SliceBroadcastReceiver;
import com.android.settings.slices.SliceBuilderUtils;
import com.android.settings.wifi.WifiPickerTrackerHelper;
import com.android.settings.wifi.slice.WifiSlice;
import com.android.settings.wifi.slice.WifiSliceItem;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.Utils;
import com.android.settingslib.WirelessUtils;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.wifi.WifiUtils;
import com.android.wifitrackerlib.MergedCarrierEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.volte2.data.VolteConstants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt___StringsKt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProviderModelSlice extends WifiSlice {
    public final ProviderModelSliceHelper mHelper;
    public final SharedPreferences mSharedPref;

    public ProviderModelSlice(Context context) {
        super(context);
        this.mHelper = getHelper();
        this.mSharedPref = getSharedPreference();
    }

    public ListBuilder.RowBuilder createEthernetRow() {
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        Drawable drawable = this.mContext.getDrawable(R.drawable.ic_settings_ethernet);
        if (drawable != null) {
            drawable.setTintList(Utils.getColorAttr(this.mContext, android.R.attr.colorAccent));
            rowBuilder.setTitleItem(com.android.settings.Utils.createIconWithDrawable(drawable));
        }
        rowBuilder.mTitle = this.mContext.getText(R.string.ethernet);
        rowBuilder.mTitleLoading = false;
        rowBuilder.mSubtitle =
                this.mContext.getText(R.string.to_switch_networks_disconnect_ethernet);
        rowBuilder.mSubtitleLoading = false;
        return rowBuilder;
    }

    public boolean defaultSubscriptionIsUsable(int i) {
        return SubscriptionManager.isUsableSubscriptionId(i);
    }

    public void doCarrierNetworkAction(boolean z, boolean z2, int i) {
        MergedCarrierEntry mergedCarrierEntry;
        NetworkProviderWorker worker = getWorker();
        if (worker == null) {
            return;
        }
        if (z) {
            if (worker.mWifiPickerTrackerHelper.isCarrierNetworkProvisionEnabled(i)) {
                return;
            }
            worker.mWifiPickerTrackerHelper.setCarrierNetworkEnabled(z2);
        } else if (z2
                && (mergedCarrierEntry =
                                worker.mWifiPickerTrackerHelper.mWifiPickerTracker
                                        .getMergedCarrierEntry())
                        != null
                && mergedCarrierEntry.canConnect()) {
            mergedCarrierEntry.connect(null);
        }
    }

    @Override // com.android.settings.wifi.slice.WifiSlice, com.android.settings.slices.Sliceable
    public final Class getBackgroundWorkerClass() {
        UserManager userManager;
        Context context = this.mContext;
        boolean z = false;
        if (context != null
                && (userManager = (UserManager) context.getSystemService(UserManager.class))
                        != null) {
            z = userManager.isGuestUser();
        }
        if (z) {
            return null;
        }
        return NetworkProviderWorker.class;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final PendingIntent getBroadcastIntent(Context context) {
        Uri uri = CustomSliceRegistry.PROVIDER_MODEL_SLICE_URI;
        return PendingIntent.getBroadcast(
                context,
                0,
                new Intent(uri.toString())
                        .addFlags(268435456)
                        .setData(uri)
                        .setClass(context, SliceBroadcastReceiver.class),
                167772160);
    }

    public ProviderModelSliceHelper getHelper() {
        return new ProviderModelSliceHelper(this.mContext, this);
    }

    @Override // com.android.settings.wifi.slice.WifiSlice,
              // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        return SliceBuilderUtils.buildSearchResultPageIntent(
                        VolteConstants.ErrorCode.RTP_TIME_OUT,
                        R.string.menu_key_network,
                        this.mContext,
                        NetworkProviderSettings.class.getName(),
                        ApnSettings.MVNO_NONE,
                        this.mContext.getText(R.string.provider_internet_settings).toString())
                .setClassName(this.mContext.getPackageName(), SubSettings.class.getName())
                .setData(CustomSliceRegistry.PROVIDER_MODEL_SLICE_URI);
    }

    public AlertDialog getMobileDataDisableDialog(final int i, String str) {
        return new AlertDialog.Builder(this.mContext)
                .setTitle(R.string.mobile_data_disable_title)
                .setMessage(this.mContext.getString(R.string.mobile_data_disable_message, str))
                .setNegativeButton(
                        android.R.string.cancel,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.network.ProviderModelSlice$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i2) {
                                NetworkProviderWorker worker = ProviderModelSlice.this.getWorker();
                                if (worker != null) {
                                    worker.notifySliceChange();
                                }
                            }
                        })
                .setPositiveButton(
                        android.R.string.call_notification_screening_text,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.network.ProviderModelSlice$$ExternalSyntheticLambda1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i2) {
                                ProviderModelSlice providerModelSlice = ProviderModelSlice.this;
                                int i3 = i;
                                providerModelSlice.getClass();
                                Log.d("ProviderModelSlice", "setMobileDataEnabled: false");
                                MobileNetworkUtils.setMobileDataEnabled(
                                        i3, providerModelSlice.mContext, false, false);
                                providerModelSlice.doCarrierNetworkAction(true, false, i3);
                                SharedPreferences sharedPreferences =
                                        providerModelSlice.mSharedPref;
                                if (sharedPreferences != null) {
                                    SharedPreferences.Editor edit = sharedPreferences.edit();
                                    edit.putBoolean("PrefHasTurnedOffMobileData", false);
                                    edit.apply();
                                }
                            }
                        })
                .create();
    }

    public SharedPreferences getSharedPreference() {
        return this.mContext.getSharedPreferences("ProviderModelSlice", 0);
    }

    @Override // com.android.settings.wifi.slice.WifiSlice,
              // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        UserManager userManager;
        int i;
        IconCompat createIconWithDrawable;
        String str;
        CharSequence charSequence;
        SubscriptionManager subscriptionManager;
        Uri uri = CustomSliceRegistry.PROVIDER_MODEL_SLICE_URI;
        ProviderModelSliceHelper providerModelSliceHelper = this.mHelper;
        ListBuilder listBuilder = new ListBuilder(providerModelSliceHelper.mContext, uri);
        listBuilder.mImpl.setColor(-1);
        listBuilder.mImpl.setKeywords(
                (Set)
                        Arrays.stream(
                                        TextUtils.split(
                                                providerModelSliceHelper.mContext.getString(
                                                        R.string.keywords_internet),
                                                ","))
                                .map(new AlwaysOnDisplaySlice$$ExternalSyntheticLambda0())
                                .collect(Collectors.toSet()));
        Context context = this.mContext;
        if ((context == null
                        || (userManager = (UserManager) context.getSystemService(UserManager.class))
                                == null)
                ? false
                : userManager.isGuestUser()) {
            Log.e("ProviderModelSlice", "Guest user is not allowed to configure Internet!");
            EventLog.writeEvent(1397638484, "227470877", -1, "User is a guest");
            return listBuilder.build();
        }
        NetworkProviderWorker worker = getWorker();
        if (worker != null) {
            i = 6;
        } else {
            Log.d("ProviderModelSlice", "network provider worker is null.");
            i = 0;
        }
        NetworkProviderWorker worker2 = getWorker();
        if ((worker2 == null ? 1 : worker2.mInternetType) == 4) {
            Log.d("ProviderModelSlice", "get Ethernet item which is connected");
            listBuilder.addRow(createEthernetRow());
            i--;
        }
        if (!WirelessUtils.isAirplaneModeOn(providerModelSliceHelper.mContext)) {
            boolean z =
                    (WirelessUtils.isAirplaneModeOn(providerModelSliceHelper.mContext)
                                    || (subscriptionManager =
                                                    providerModelSliceHelper.mSubscriptionManager)
                                            == null
                                    || providerModelSliceHelper.mTelephonyManager == null
                                    || subscriptionManager.getActiveSubscriptionIdList().length
                                            <= 0)
                            ? false
                            : true;
            Log.d("ProviderModelSlice", "hasCarrier: " + z);
            if (z) {
                if (providerModelSliceHelper.mSubscriptionManager != null
                        && SubscriptionManager.getDefaultDataSubscriptionId() != -1) {
                    providerModelSliceHelper.mTelephonyManager =
                            providerModelSliceHelper.mTelephonyManager.createForSubscriptionId(
                                    SubscriptionManager.getDefaultDataSubscriptionId());
                }
                if (worker != null) {
                    Context context2 = worker.mContext;
                    MobileMappings.Config config = worker.mConfig;
                    TelephonyDisplayInfo telephonyDisplayInfo = worker.mTelephonyDisplayInfo;
                    int i2 = worker.mDefaultDataSubId;
                    WifiPickerTrackerHelper wifiPickerTrackerHelper =
                            worker.mWifiPickerTrackerHelper;
                    if (wifiPickerTrackerHelper == null
                            || !wifiPickerTrackerHelper.isCarrierNetworkActive()) {
                        str =
                                MobileIconGroupExtKt.getSummaryForSub(
                                        (SignalIcon$MobileIconGroup)
                                                ((HashMap) MobileMappings.mapIconSets(config))
                                                        .get(
                                                                telephonyDisplayInfo
                                                                                        .getOverrideNetworkType()
                                                                                == 0
                                                                        ? Integer.toString(
                                                                                telephonyDisplayInfo
                                                                                        .getNetworkType())
                                                                        : MobileMappings
                                                                                .toDisplayIconKey(
                                                                                        telephonyDisplayInfo
                                                                                                .getOverrideNetworkType())),
                                        context2,
                                        i2);
                    } else {
                        str =
                                MobileIconGroupExtKt.getSummaryForSub(
                                        TelephonyIcons.CARRIER_MERGED_WIFI, context2, i2);
                    }
                } else {
                    str = ApnSettings.MVNO_NONE;
                }
                String mobileTitle = providerModelSliceHelper.getMobileTitle();
                if (providerModelSliceHelper.mTelephonyManager.isDataEnabled()) {
                    ServiceState serviceState =
                            providerModelSliceHelper.mTelephonyManager.getServiceState();
                    NetworkRegistrationInfo networkRegistrationInfo =
                            serviceState == null
                                    ? null
                                    : serviceState.getNetworkRegistrationInfo(2, 1);
                    if (networkRegistrationInfo == null
                            ? false
                            : networkRegistrationInfo.isRegistered()) {
                        String str2 = str;
                        if (MobileNetworkUtils.activeNetworkIsCellular(
                                providerModelSliceHelper.mContext)) {
                            Context context3 = providerModelSliceHelper.mContext;
                            str2 =
                                    context3.getString(
                                            R.string.preference_summary_default_combination,
                                            context3.getString(
                                                    R.string.mobile_data_connection_active),
                                            str);
                        }
                        Intrinsics.checkNotNullParameter(str2, "<this>");
                        boolean contains = StringsKt___StringsKt.contains(str2, "</", false);
                        charSequence = str2;
                        if (contains) {
                            CharSequence fromHtml = Html.fromHtml(str2, 0);
                            Intrinsics.checkNotNullExpressionValue(fromHtml, "fromHtml(...)");
                            charSequence = fromHtml;
                        }
                    } else {
                        charSequence =
                                providerModelSliceHelper.mContext.getString(
                                        R.string.mobile_data_no_connection);
                    }
                } else {
                    charSequence =
                            providerModelSliceHelper.mContext.getString(
                                    R.string.mobile_data_off_summary);
                }
                Drawable drawable =
                        providerModelSliceHelper.mContext.getDrawable(
                                R.drawable.ic_signal_strength_zero_bar_no_internet);
                try {
                    drawable = providerModelSliceHelper.getMobileDrawable(drawable);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                IconCompat createIconWithDrawable2 =
                        com.android.settings.Utils.createIconWithDrawable(drawable);
                PendingIntent broadcastIntent =
                        providerModelSliceHelper.mSliceable.getBroadcastIntent(
                                providerModelSliceHelper.mContext);
                SliceAction sliceAction =
                        new SliceAction(broadcastIntent, createIconWithDrawable2, 0, mobileTitle);
                SliceAction sliceAction2 =
                        new SliceAction(
                                broadcastIntent,
                                "mobile_toggle",
                                providerModelSliceHelper.mTelephonyManager.isDataEnabled());
                ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
                rowBuilder.mTitle = mobileTitle;
                rowBuilder.mTitleLoading = false;
                rowBuilder.setTitleItem(createIconWithDrawable2);
                rowBuilder.addEndItem(sliceAction2);
                rowBuilder.mPrimaryAction = sliceAction;
                rowBuilder.mSubtitle = charSequence;
                rowBuilder.mSubtitleLoading = false;
                listBuilder.addRow(rowBuilder);
                i--;
            }
        }
        boolean isWifiEnabled = this.mWifiManager.isWifiEnabled();
        Context context4 = this.mContext;
        Uri uri2 = CustomSliceRegistry.WIFI_SLICE_URI;
        SliceAction sliceAction3 =
                new SliceAction(
                        PendingIntent.getBroadcast(
                                context4,
                                0,
                                new Intent(uri2.toString())
                                        .setData(uri2)
                                        .setClass(context4, SliceBroadcastReceiver.class)
                                        .putExtra(
                                                "android.app.slice.extra.TOGGLE_STATE",
                                                !isWifiEnabled)
                                        .addFlags(268435456),
                                201326592),
                        null,
                        isWifiEnabled);
        ListBuilder.RowBuilder rowBuilder2 = new ListBuilder.RowBuilder();
        rowBuilder2.mTitle = context4.getString(R.string.wifi_settings);
        rowBuilder2.mTitleLoading = false;
        rowBuilder2.mPrimaryAction = sliceAction3;
        listBuilder.addRow(rowBuilder2);
        int i3 = i - 1;
        if (!isWifiEnabled) {
            Log.d("ProviderModelSlice", "Wi-Fi is disabled");
            return listBuilder.build();
        }
        ArrayList arrayList =
                (worker == null || worker.mCachedResults == null)
                        ? null
                        : new ArrayList(worker.mCachedResults);
        if (arrayList == null || arrayList.size() <= 0) {
            Log.d("ProviderModelSlice", "Wi-Fi list is empty");
            return listBuilder.build();
        }
        Optional findFirst =
                arrayList.stream()
                        .filter(new ProviderModelSliceHelper$$ExternalSyntheticLambda1())
                        .findFirst();
        WifiSliceItem wifiSliceItem =
                findFirst.isPresent() ? (WifiSliceItem) findFirst.get() : null;
        if (wifiSliceItem != null) {
            Log.d("ProviderModelSlice", "get Wi-Fi item which is connected");
            listBuilder.addRow(getWifiSliceItemRow(wifiSliceItem));
            i3 = i - 2;
        }
        Log.d(
                "ProviderModelSlice",
                "get Wi-Fi items which are not connected. Wi-Fi items : " + arrayList.size());
        Iterator it =
                ((List)
                                arrayList.stream()
                                        .filter(new ProviderModelSlice$$ExternalSyntheticLambda2())
                                        .limit((long) (i3 - 1))
                                        .collect(Collectors.toList()))
                        .iterator();
        while (it.hasNext()) {
            listBuilder.addRow(getWifiSliceItemRow((WifiSliceItem) it.next()));
        }
        Log.d("ProviderModelSlice", "add See-All");
        CharSequence text = this.mContext.getText(R.string.previous_connected_see_all);
        Drawable drawable2 = this.mContext.getDrawable(R.drawable.ic_arrow_forward);
        if (drawable2 != null) {
            drawable2.setTint(
                    Utils.getColorAttrDefaultColor(
                            this.mContext, android.R.attr.colorControlNormal));
            createIconWithDrawable = com.android.settings.Utils.createIconWithDrawable(drawable2);
        } else {
            createIconWithDrawable =
                    com.android.settings.Utils.createIconWithDrawable(new ColorDrawable(0));
        }
        ListBuilder.RowBuilder rowBuilder3 = new ListBuilder.RowBuilder();
        rowBuilder3.setTitleItem(createIconWithDrawable);
        rowBuilder3.mTitle = text;
        rowBuilder3.mTitleLoading = false;
        rowBuilder3.mPrimaryAction =
                SliceAction.createDeeplink(
                        PendingIntent.getActivity(this.mContext, 0, getIntent(), 67108864),
                        createIconWithDrawable,
                        0,
                        text);
        listBuilder.addRow(rowBuilder3);
        return listBuilder.build();
    }

    @Override // com.android.settings.wifi.slice.WifiSlice,
              // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.PROVIDER_MODEL_SLICE_URI;
    }

    @Override // com.android.settings.wifi.slice.WifiSlice
    public final IconCompat getWifiSliceItemLevelIcon(WifiSliceItem wifiSliceItem) {
        if (wifiSliceItem.mConnectedState == 2) {
            NetworkProviderWorker worker = getWorker();
            if ((worker == null ? 1 : worker.mInternetType) != 2) {
                int colorAttrDefaultColor =
                        Utils.getColorAttrDefaultColor(
                                this.mContext, android.R.attr.colorControlNormal);
                Context context = this.mContext;
                int i = wifiSliceItem.mLevel;
                if (i < 0) {
                    WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                            .m(i, "Wi-Fi level is out of range! level:", "WifiUtils");
                    i = 0;
                } else if (i >= 5) {
                    WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                            .m(i, "Wi-Fi level is out of range! level:", "WifiUtils");
                    i = 4;
                }
                Drawable drawable =
                        context.getDrawable(
                                wifiSliceItem.mShouldShowXLevelIcon
                                        ? WifiUtils.NO_INTERNET_WIFI_PIE[i]
                                        : WifiUtils.WIFI_PIE[i]);
                drawable.setTint(colorAttrDefaultColor);
                return com.android.settings.Utils.createIconWithDrawable(drawable);
            }
        }
        return super.getWifiSliceItemLevelIcon(wifiSliceItem);
    }

    public NetworkProviderWorker getWorker() {
        return (NetworkProviderWorker)
                SliceBackgroundWorker.getInstance(CustomSliceRegistry.PROVIDER_MODEL_SLICE_URI);
    }

    @Override // com.android.settings.wifi.slice.WifiSlice,
              // com.android.settings.slices.CustomSliceable
    public final void onNotifyChange(Intent intent) {
        SharedPreferences sharedPreferences;
        ProviderModelSliceHelper providerModelSliceHelper = this.mHelper;
        if (providerModelSliceHelper.mSubscriptionManager == null) {
            return;
        }
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        Log.d("ProviderModelSlice", "defaultSubId:" + defaultDataSubscriptionId);
        if (defaultSubscriptionIsUsable(defaultDataSubscriptionId)) {
            boolean hasExtra = intent.hasExtra("android.app.slice.extra.TOGGLE_STATE");
            boolean booleanExtra =
                    intent.getBooleanExtra(
                            "android.app.slice.extra.TOGGLE_STATE",
                            providerModelSliceHelper.mTelephonyManager.isDataEnabled());
            if (hasExtra) {
                if (!booleanExtra
                        && (sharedPreferences = this.mSharedPref) != null
                        && sharedPreferences.getBoolean("PrefHasTurnedOffMobileData", true)) {
                    String mobileTitle = providerModelSliceHelper.getMobileTitle();
                    if (mobileTitle.equals(
                            this.mContext.getString(R.string.mobile_data_settings_title))) {
                        mobileTitle =
                                this.mContext.getString(
                                        R.string.mobile_data_disable_message_default_carrier);
                    }
                    AlertDialog mobileDataDisableDialog =
                            getMobileDataDisableDialog(defaultDataSubscriptionId, mobileTitle);
                    if (mobileDataDisableDialog == null) {
                        Log.d("ProviderModelSlice", "AlertDialog is null");
                        return;
                    } else {
                        mobileDataDisableDialog.getWindow().setType(2009);
                        mobileDataDisableDialog.show();
                        return;
                    }
                }
                Log.d("ProviderModelSlice", "setMobileDataEnabled: " + booleanExtra);
                MobileNetworkUtils.setMobileDataEnabled(
                        defaultDataSubscriptionId, this.mContext, booleanExtra, false);
            }
            if (!hasExtra) {
                booleanExtra = MobileNetworkUtils.isMobileDataEnabled(this.mContext);
            }
            doCarrierNetworkAction(hasExtra, booleanExtra, defaultDataSubscriptionId);
        }
    }
}
