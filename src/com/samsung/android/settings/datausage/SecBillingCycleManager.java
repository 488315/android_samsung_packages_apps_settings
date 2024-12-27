package com.samsung.android.settings.datausage;

import android.content.Context;
import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.NetworkTemplate;
import android.os.INetworkManagementService;
import android.os.ServiceManager;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.datausage.TemplatePreference;
import com.android.settingslib.NetworkPolicyEditor;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBillingCycleManager {
    public static SecBillingCycleManager sInstance;
    public final Context mContext;
    public final EnterpriseDeviceManager mEDM;
    public NetworkTemplate mNetworkTemplate;
    public final RestrictionPolicy mRestrictionPolicy;
    public final TemplatePreference.NetworkServices mServices;
    public int mSlotNumber;
    public BillingCycleUpdater mUIUpdater;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface BillingCycleUpdater {}

    public SecBillingCycleManager(Context context) {
        TemplatePreference.NetworkServices networkServices =
                new TemplatePreference.NetworkServices();
        this.mServices = networkServices;
        this.mEDM = null;
        this.mRestrictionPolicy = null;
        this.mContext = context;
        networkServices.mNetworkService =
                INetworkManagementService.Stub.asInterface(
                        ServiceManager.getService("network_management"));
        NetworkPolicyManager networkPolicyManager =
                (NetworkPolicyManager) context.getSystemService("netpolicy");
        networkServices.mPolicyManager = networkPolicyManager;
        networkServices.mPolicyEditor = new NetworkPolicyEditor(networkPolicyManager);
        networkServices.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        SubscriptionManager.from(context);
        networkServices.mUserManager = UserManager.get(context);
        this.mNetworkTemplate =
                DataUsageUtils.getDefaultTemplate(
                        context, DataUsageUtils.getDefaultSubscriptionId(context));
        this.mEDM = EnterpriseDeviceManager.getInstance(context);
        EnterpriseDeviceManager enterpriseDeviceManager = this.mEDM;
        if (enterpriseDeviceManager != null) {
            this.mRestrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();
        }
    }

    public static SecBillingCycleManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SecBillingCycleManager(context.getApplicationContext());
        }
        sInstance.mServices.mPolicyEditor.read();
        return sInstance;
    }

    public final boolean getAvailableBillingCycleSetting() {
        boolean hasActiveSim = DataUsageUtils.hasActiveSim(this.mContext);
        boolean hasMobileData = DataUsageUtils.hasMobileData(this.mContext);
        boolean isAdminUser = ((UserManager) this.mContext.getSystemService("user")).isAdminUser();
        boolean z = hasMobileData && hasActiveSim && isAdminUser;
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "getAvailableBillingCycleSetting() : ",
                        z,
                        " hasMobileData : ",
                        hasMobileData,
                        " hasSim : ");
        m.append(hasActiveSim);
        m.append(" isAdminUser : ");
        m.append(isAdminUser);
        Log.d("SecBillingCycleSettings.SecBillingCycleManager", m.toString());
        return z;
    }

    public final int getCycleDay(NetworkTemplate networkTemplate) {
        int policyCycleDay =
                networkTemplate != null
                        ? this.mServices.mPolicyEditor.getPolicyCycleDay(networkTemplate)
                        : -1;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                policyCycleDay,
                "getCycleDay Cycle day : ",
                "SecBillingCycleSettings.SecBillingCycleManager");
        return policyCycleDay;
    }

    public final long getPolicyLimitBytes() {
        NetworkPolicy policy;
        NetworkTemplate networkTemplate = this.mNetworkTemplate;
        long j = -1;
        if (networkTemplate != null
                && (policy = this.mServices.mPolicyEditor.getPolicy(networkTemplate)) != null) {
            j = policy.limitBytes;
        }
        Log.d(
                "SecBillingCycleSettings.SecBillingCycleManager",
                "getPolicyLimitBytes() Limit bytes : " + j);
        return j;
    }

    public final long getPolicyWarningBytes() {
        NetworkPolicy policy;
        NetworkTemplate networkTemplate = this.mNetworkTemplate;
        long j = -1;
        if (networkTemplate != null
                && (policy = this.mServices.mPolicyEditor.getPolicy(networkTemplate)) != null) {
            j = policy.warningBytes;
        }
        Log.d(
                "SecBillingCycleSettings.SecBillingCycleManager",
                "getPolicyWarningBytes() Warning bytes : " + j);
        return j;
    }

    public final Long getTetheringDataWarningValue() {
        return this.mSlotNumber == 0
                ? Long.valueOf(
                        Settings.Global.getLong(
                                this.mContext.getContentResolver(),
                                "tethering_data_warning_sim_slot_0",
                                -1L))
                : Long.valueOf(
                        Settings.Global.getLong(
                                this.mContext.getContentResolver(),
                                "tethering_data_warning_sim_slot_1",
                                -1L));
    }

    public final boolean isDataLimitRestrict() {
        RestrictionPolicy restrictionPolicy = this.mRestrictionPolicy;
        boolean z =
                (restrictionPolicy == null || restrictionPolicy.isUserMobileDataLimitAllowed())
                        ? false
                        : true;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isDataLimitRestrict : ", "SecBillingCycleSettings.SecBillingCycleManager", z);
        return z;
    }

    public final void setPolicyLimitBytes(long j) {
        Log.d("SecBillingCycleSettings.SecBillingCycleManager", "setPolicyLimitBytes() " + j);
        this.mServices.mPolicyEditor.setPolicyLimitBytes(this.mNetworkTemplate, j);
        updateScreen();
    }

    public final void setPolicyLimitBytesByDefault() {
        long j =
                this.mServices.mPolicyEditor.getPolicy(this.mNetworkTemplate) != null
                        ? (long) (r0.warningBytes * 1.2d)
                        : 0L;
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) this.mContext.getSystemService("carrier_config");
        long max =
                Math.max(
                        (carrierConfigManager == null || carrierConfigManager.getConfig() == null)
                                ? -1L
                                : Math.max(
                                        5000000000L,
                                        carrierConfigManager
                                                .getConfig()
                                                .getLong("data_limit_threshold_bytes_long")),
                        j);
        Log.d("SecBillingCycleSettings.SecBillingCycleManager", "getDefaultLimitBytes:" + max);
        setPolicyLimitBytes(max);
    }

    public final void setPolicyWarningBytes(long j) {
        Log.d("SecBillingCycleSettings.SecBillingCycleManager", "setPolicyWarningBytes() " + j);
        this.mServices.mPolicyEditor.setPolicyWarningBytes(this.mNetworkTemplate, j);
        updateScreen();
    }

    public final void setTetheringDataWarningValue(Long l) {
        if (this.mSlotNumber == 0) {
            Settings.Global.putLong(
                    this.mContext.getContentResolver(),
                    "tethering_data_warning_sim_slot_0",
                    l.longValue());
        } else {
            Settings.Global.putLong(
                    this.mContext.getContentResolver(),
                    "tethering_data_warning_sim_slot_1",
                    l.longValue());
        }
        updateScreen();
    }

    public final void updateScreen() {
        if (this.mUIUpdater != null) {
            try {
                Thread.sleep(50L);
                ((SecBillingCycleSettings) this.mUIUpdater).updatePreferenceStates();
            } catch (InterruptedException e) {
                Log.e("SecBillingCycleSettings.SecBillingCycleManager", "error: " + e);
            }
        }
    }
}
