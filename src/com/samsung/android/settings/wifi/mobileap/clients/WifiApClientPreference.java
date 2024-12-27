package com.samsung.android.settings.wifi.mobileap.clients;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApDateUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;
import com.samsung.android.util.SemLog;
import com.samsung.android.wifi.SemWifiApClientDetails;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApClientPreference extends WifiApPreference {
    public Context mContext;
    public String mDeviceName;
    public boolean mIsDataPausedByUserManually;
    public boolean mIsDeviceCurrentlyConnected;
    public boolean mIsOtpClient;
    public String mMac;
    public long mSetDataLimitValueInBytes;
    public int mSummaryColor;
    public long mTimeLimitValueInMillis;
    public long mTodayConsumedTimeInMillis;
    public long mTodayMobileDataUsageValueInBytes;
    public int mWarningColor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.WifiApClientPreference$2, reason: invalid class name */
    public final class AnonymousClass2 implements View.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiApClientPreference this$0;

        public /* synthetic */ AnonymousClass2(
                WifiApClientPreference wifiApClientPreference, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiApClientPreference;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            switch (this.$r8$classId) {
                case 0:
                    WifiApClientPreference.m1375$$Nest$mopenClientSettingActivity(this.this$0);
                    break;
                default:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.this$0.mContext);
                    builder.setPositiveButton(R.string.ok, new WifiApClientPreference$4$1());
                    builder.setTitle(R.string.wifi_ap_time_limit_reached);
                    builder.P.mMessage =
                            this.this$0.mContext.getString(
                                    R.string.wifi_ap_time_limit_reached_message_1);
                    builder.create().show();
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$mopenClientSettingActivity, reason: not valid java name */
    public static void m1375$$Nest$mopenClientSettingActivity(
            WifiApClientPreference wifiApClientPreference) {
        wifiApClientPreference.getClass();
        SALogging.insertSALog("TETH_013", "8075");
        Bundle bundle = new Bundle();
        bundle.putString(
                "intent_key_connected_client_name_detail", wifiApClientPreference.mDeviceName);
        bundle.putString("intent_key_connected_client_mac_detail", wifiApClientPreference.mMac);
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(wifiApClientPreference.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = 3400;
        launchRequest.mArguments = bundle;
        launchRequest.mDestinationName = WifiApClientSettings.class.getCanonicalName();
        subSettingLauncher.launch();
    }

    public final void updatePreference(SemWifiApClientDetails semWifiApClientDetails) {
        Drawable drawable;
        Drawable drawable2;
        SemLog.i("WifiApClientPreference", "Updating Preference1: " + this.mDeviceName);
        if (semWifiApClientDetails == null) {
            SemLog.i("WifiApClientPreference", "semWifiApClientDetails is null");
            return;
        }
        this.mDeviceName = semWifiApClientDetails.getClientDeviceName();
        semWifiApClientDetails.getClientIpAddress();
        String clientMacAddress = semWifiApClientDetails.getClientMacAddress();
        this.mMac = clientMacAddress;
        this.mIsOtpClient =
                WifiApFrameworkUtils.isOtpConnectedClient(this.mContext, clientMacAddress);
        this.mSetDataLimitValueInBytes = semWifiApClientDetails.getClientDataLimit();
        this.mTimeLimitValueInMillis = semWifiApClientDetails.getClientTimeLimit();
        this.mTodayConsumedTimeInMillis = semWifiApClientDetails.getClientTodayTotalTime();
        this.mIsDataPausedByUserManually = semWifiApClientDetails.isClientDataPausedByUser();
        semWifiApClientDetails.isClientInternetPaused();
        this.mTodayMobileDataUsageValueInBytes =
                semWifiApClientDetails.getClientTodayTotalMobileDataUsage();
        this.mIsDeviceCurrentlyConnected = semWifiApClientDetails.isClientConnected();
        semWifiApClientDetails.getClientRecentConnectionTimeStamp();
        if (WifiApFeatureUtils.isWifiApClientDeviceTypeSupported()) {
            int deviceType = semWifiApClientDetails.getDeviceType();
            if (deviceType == 1) {
                setIcon2(R.drawable.sec_wifi_ap_mobile_device);
            } else if (deviceType == 3) {
                setIcon2(R.drawable.sec_wifi_ap_tablet);
            } else if (deviceType == 4) {
                setIcon2(R.drawable.sec_wifi_ap_tv);
            } else if (deviceType == 7) {
                setIcon2(R.drawable.sec_wifi_ap_watch);
            } else if (deviceType != 8) {
                setIcon2(R.drawable.sec_wifi_ap_undefined);
            } else {
                setIcon2(R.drawable.sec_wifi_ap_vst_icon);
            }
        }
        setKey(this.mMac);
        if (TextUtils.isEmpty(this.mMac)) {
            setTitle(R.string.wifi_ap_other_devices);
        } else {
            setTitle(this.mDeviceName);
        }
        if (SystemProperties.getInt("ro.build.version.oneui", 0) >= 60100
                && this.mIsOtpClient
                && (drawable2 =
                                ((WifiApPreference) this)
                                        .mContext.getDrawable(R.drawable.sec_wifi_ap_guest_icon))
                        != null
                && this.mTitleIcon != drawable2) {
            this.mTitleIcon = drawable2;
            notifyChanged();
        }
        if (!this.mIsDeviceCurrentlyConnected) {
            if (WifiApFeatureUtils.isWifiApClientDeviceTypeSupported()
                    || !this.mSecondaryIconVisibility) {
                drawable = null;
            } else {
                setSecondaryIconDividerVisibility(false);
                drawable = null;
                setSecondaryIcon(null);
            }
            if (this.mSecondaryAlertIconVisibility) {
                setSecondaryAlertIcon(drawable);
            }
            if (!TextUtils.isEmpty(this.mMac)) {
                setSummary(R.string.wifi_ap_disconnected);
                setSummaryTextColor(this.mSummaryColor);
                return;
            }
            String convertDataSizeToLocaleString =
                    WifiApSettingsUtils.convertDataSizeToLocaleString(
                            this.mContext, this.mTodayMobileDataUsageValueInBytes);
            SpannableString spannableString = new SpannableString(convertDataSizeToLocaleString);
            spannableString.setSpan(
                    new ForegroundColorSpan(
                            WifiApSettingsUtils.getColorFromAttribute(
                                    getContext(), android.R.attr.textColorPrimary)),
                    0,
                    convertDataSizeToLocaleString.length(),
                    17);
            setSpannableSummary(spannableString);
            setSecondaryIconDividerVisibility(false);
            setSecondaryAlertIcon(null);
            return;
        }
        if (!WifiApFeatureUtils.isWifiApClientDeviceTypeSupported()
                && !this.mSecondaryIconVisibility) {
            setSecondaryIcon(this.mContext.getDrawable(R.drawable.ic_wifi_ap_setting));
        }
        final int usageValueInMB =
                this.mSetDataLimitValueInBytes > 0
                        ? (int)
                                ((new WifiApDataUsageConfig(this.mTodayMobileDataUsageValueInBytes)
                                                        .getUsageValueInMB()
                                                / new WifiApDataUsageConfig(
                                                                this.mSetDataLimitValueInBytes)
                                                        .getUsageValueInMB())
                                        * 100.0d)
                        : 0;
        if (this.mSetDataLimitValueInBytes > 0) {
            if (usageValueInMB >= 100) {
                setSummary(R.string.wifi_ap_data_limit_reached);
                setSummaryTextColor(this.mWarningColor);
            } else {
                setSummaryTextColor(this.mSummaryColor);
                String convertDataSizeToLocaleString2 =
                        WifiApSettingsUtils.convertDataSizeToLocaleString(
                                this.mContext, this.mTodayMobileDataUsageValueInBytes);
                StringBuilder m =
                        PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                convertDataSizeToLocaleString2, " / ");
                m.append(
                        WifiApSettingsUtils.convertDataSizeToLocaleString(
                                this.mContext, this.mSetDataLimitValueInBytes));
                SpannableString spannableString2 = new SpannableString(m.toString());
                if (usageValueInMB >= 90) {
                    spannableString2.setSpan(
                            new ForegroundColorSpan(this.mWarningColor),
                            0,
                            convertDataSizeToLocaleString2.length(),
                            17);
                } else {
                    spannableString2.setSpan(
                            new ForegroundColorSpan(
                                    WifiApSettingsUtils.getColorFromAttribute(
                                            getContext(), android.R.attr.textColorPrimary)),
                            0,
                            convertDataSizeToLocaleString2.length(),
                            17);
                }
                SpannableString spannableString3 = new SpannableString(ApnSettings.MVNO_NONE);
                long j = this.mTimeLimitValueInMillis;
                if (j > 0) {
                    String convertTimeToLocaleWithRemainingText =
                            WifiApDateUtils.convertTimeToLocaleWithRemainingText(
                                    this.mContext,
                                    WifiApDateUtils.convertTimeToMinutes(j)
                                            - WifiApDateUtils.convertTimeToMinutes(
                                                    this.mTodayConsumedTimeInMillis));
                    SpannableString spannableString4 =
                            new SpannableString(convertTimeToLocaleWithRemainingText);
                    spannableString4.setSpan(
                            new ForegroundColorSpan(
                                    WifiApSettingsUtils.getColorFromAttribute(
                                            getContext(), android.R.attr.textColorSecondary)),
                            0,
                            convertTimeToLocaleWithRemainingText.length(),
                            17);
                    spannableString3 = spannableString4;
                }
                if (spannableString3.toString().isEmpty()) {
                    setSpannableSummary(spannableString2);
                } else {
                    SpannableString valueOf =
                            SpannableString.valueOf(
                                    new SpannableStringBuilder(spannableString2)
                                            .append((CharSequence) "\n")
                                            .append((CharSequence) spannableString3));
                    Log.i(
                            "WifiApPreference",
                            "setSpannableSummary(builder): " + ((Object) valueOf));
                    setSummary(valueOf);
                }
            }
            if (usageValueInMB >= 90) {
                if (!WifiApFeatureUtils.isWifiApClientDeviceTypeSupported()) {
                    setSecondaryIconDividerVisibility(true);
                }
                setSecondaryAlertIcon(this.mContext.getDrawable(R.drawable.ic_wifi_ap_warning));
                this.mSecondaryAlertIconClickListener =
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientPreference.3
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(
                                                WifiApClientPreference.this.mContext);
                                builder.setPositiveButton(
                                        R.string.ok,
                                        new DialogInterface
                                                .OnClickListener() { // from class:
                                                                     // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientPreference.3.1
                                            @Override // android.content.DialogInterface.OnClickListener
                                            public final void onClick(
                                                    DialogInterface dialogInterface, int i) {
                                                if (usageValueInMB >= 100) {
                                                    SALogging.insertSALog("TETH_013", "8080");
                                                } else {
                                                    SALogging.insertSALog("TETH_013", "8079");
                                                }
                                            }
                                        });
                                int i = usageValueInMB;
                                AlertController.AlertParams alertParams = builder.P;
                                if (i >= 100) {
                                    builder.setTitle(R.string.wifi_ap_data_limit_reached);
                                    alertParams.mMessage =
                                            WifiApClientPreference.this.mContext.getString(
                                                    R.string.wifi_ap_data_limit_reached_message_4);
                                } else {
                                    builder.setTitle(R.string.wifi_ap_data_limit_almost_reached);
                                    alertParams.mMessage =
                                            String.format(
                                                    WifiApClientPreference.this.mContext.getString(
                                                            R.string
                                                                    .wifi_ap_data_limit_reached_message_3),
                                                    Integer.valueOf(usageValueInMB));
                                }
                                builder.create().show();
                            }
                        };
                notifyChanged();
            } else {
                setSecondaryIconDividerVisibility(false);
                setSecondaryAlertIcon(null);
            }
        } else {
            String convertDataSizeToLocaleString3 =
                    WifiApSettingsUtils.convertDataSizeToLocaleString(
                            this.mContext, this.mTodayMobileDataUsageValueInBytes);
            SpannableString spannableString5 = new SpannableString(convertDataSizeToLocaleString3);
            spannableString5.setSpan(
                    new ForegroundColorSpan(
                            WifiApSettingsUtils.getColorFromAttribute(
                                    getContext(), android.R.attr.textColorPrimary)),
                    0,
                    convertDataSizeToLocaleString3.length(),
                    17);
            SpannableString spannableString6 = new SpannableString(ApnSettings.MVNO_NONE);
            long j2 = this.mTimeLimitValueInMillis;
            if (j2 > 0) {
                String convertTimeToLocaleWithRemainingText2 =
                        WifiApDateUtils.convertTimeToLocaleWithRemainingText(
                                this.mContext,
                                WifiApDateUtils.convertTimeToMinutes(j2)
                                        - WifiApDateUtils.convertTimeToMinutes(
                                                this.mTodayConsumedTimeInMillis));
                SpannableString spannableString7 =
                        new SpannableString(convertTimeToLocaleWithRemainingText2);
                spannableString7.setSpan(
                        new ForegroundColorSpan(
                                WifiApSettingsUtils.getColorFromAttribute(
                                        getContext(), android.R.attr.textColorSecondary)),
                        0,
                        convertTimeToLocaleWithRemainingText2.length(),
                        17);
                spannableString6 = spannableString7;
            }
            if (spannableString6.toString().isEmpty()) {
                setSpannableSummary(spannableString5);
            } else {
                SpannableString valueOf2 =
                        SpannableString.valueOf(
                                new SpannableStringBuilder(spannableString5)
                                        .append((CharSequence) "\n")
                                        .append((CharSequence) spannableString6));
                Log.i("WifiApPreference", "setSpannableSummary(builder): " + ((Object) valueOf2));
                setSummary(valueOf2);
            }
            setSecondaryIconDividerVisibility(false);
            setSecondaryAlertIcon(null);
        }
        if (semWifiApClientDetails.isClientDataPausedByDataLimit() || usageValueInMB >= 100) {
            SemLog.i("WifiApClientPreference", "Data limit reached for " + this.mDeviceName);
            setSummary(R.string.wifi_ap_data_limit_reached);
            setSummaryTextColor(this.mWarningColor);
            return;
        }
        if (!semWifiApClientDetails.isClientDataPauseByTimeLimit()) {
            if (this.mIsDataPausedByUserManually) {
                SemLog.i("WifiApClientPreference", "Sharing paused for " + this.mDeviceName);
                setSummary(R.string.wifi_ap_sharing_paused);
                setSummaryTextColor(this.mSummaryColor);
                return;
            }
            return;
        }
        SemLog.i("WifiApClientPreference", "Time limit reached for " + this.mDeviceName);
        setSummary(R.string.wifi_ap_time_limit_reached);
        setSummaryTextColor(this.mWarningColor);
        if (!WifiApFeatureUtils.isWifiApClientDeviceTypeSupported()) {
            setSecondaryIconDividerVisibility(true);
        }
        setSecondaryAlertIcon(this.mContext.getDrawable(R.drawable.ic_wifi_ap_warning));
        this.mSecondaryAlertIconClickListener = new AnonymousClass2(this, 1);
        notifyChanged();
    }
}
