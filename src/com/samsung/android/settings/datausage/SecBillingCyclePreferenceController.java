package com.samsung.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkPolicyManager;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.os.INetworkManagementService;
import android.os.ServiceManager;
import android.os.UserManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.datausage.TemplatePreference;
import com.android.settings.datausage.lib.DataUsageLib;
import com.android.settings.network.MobileDataEnabledListener;
import com.android.settingslib.NetworkPolicyEditor;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBillingCyclePreferenceController extends BasePreferenceController
        implements MobileDataEnabledListener.Client, LifecycleObserver, OnCreate, OnStart, OnStop {
    private static final String TAG = "SecBillingCyclePreferenceController";
    private MobileDataEnabledListener mListener;
    private NetworkTemplate mNetworkTemplate;
    private SecPreference mSecPreference;
    private TemplatePreference.NetworkServices mServices;
    private int mSlotIndex;
    private int mSubId;
    private List<SubscriptionInfo> mSubscriptions;

    public SecBillingCyclePreferenceController(Context context, String str) {
        super(context, str);
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
        if ("billing_preference_sim_slot_0".equals(str)) {
            this.mSlotIndex = 0;
        } else {
            this.mSlotIndex = 1;
        }
        this.mSubscriptions = subscriptionManager.getCompleteActiveSubscriptionInfoList();
        this.mListener = new MobileDataEnabledListener(context, this);
    }

    private Intent getIntent() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("network_template", this.mNetworkTemplate);
        bundle.putInt("subscriptionid", this.mSubId);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = SecBillingCycleSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.billing_cycle, null);
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mArguments = bundle;
        return subSettingLauncher.toIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mSecPreference = (SecPreference) preferenceScreen.findPreference(getPreferenceKey());
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean isAdminUser = ((UserManager) this.mContext.getSystemService("user")).isAdminUser();
        if (this.mSubscriptions == null
                || ((Rune.isEnabledHidingByOpportunisticEsim(this.mContext)
                                && !this.mSubscriptions.get(this.mSlotIndex).isOpportunistic())
                        || TelephonyManager.getSimStateForSlotIndex(this.mSlotIndex) == 0
                        || !isAdminUser)) {
            return 2;
        }
        if (this.mSubscriptions.size() <= 1 || !DataUsageUtils.hasMobile(this.mContext)) {
            return (this.mSubscriptions.size() == 1
                            && this.mSlotIndex
                                    == SubscriptionManager.getPhoneId(
                                            SubscriptionManager.getDefaultDataSubscriptionId())
                            && DataUsageUtils.hasMobile(this.mContext))
                    ? 0
                    : 2;
        }
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        this.mServices.mPolicyEditor.read();
        int policyCycleDay = this.mServices.mPolicyEditor.getPolicyCycleDay(this.mNetworkTemplate);
        return policyCycleDay != -1
                ? this.mContext
                        .getResources()
                        .getStringArray(R.array.data_usage_billing_cycle_summary)[
                        policyCycleDay - 1]
                        .toString()
                : super.getSummary();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(getPreferenceKey())) {
            this.mContext.startActivity(getIntent());
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public void onCreate(Bundle bundle) {
        List<SubscriptionInfo> list = this.mSubscriptions;
        if (list != null) {
            list.sort(
                    new Comparator() { // from class:
                                       // com.samsung.android.settings.datausage.SecBillingCyclePreferenceController.1
                        public final Collator mCollator = Collator.getInstance();

                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return this.mCollator.compare(
                                    Integer.toString(((SubscriptionInfo) obj).getSimSlotIndex()),
                                    Integer.toString(((SubscriptionInfo) obj2).getSimSlotIndex()));
                        }
                    });
            if (this.mSubscriptions.size() == 1) {
                this.mSubId = this.mSubscriptions.get(0).getSubscriptionId();
                TemplatePreference.NetworkServices networkServices =
                        new TemplatePreference.NetworkServices();
                this.mServices = networkServices;
                networkServices.mNetworkService =
                        INetworkManagementService.Stub.asInterface(
                                ServiceManager.getService("network_management"));
                this.mServices.mTelephonyManager =
                        (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
                this.mServices.mUserManager =
                        (UserManager) this.mContext.getSystemService(UserManager.class);
                this.mServices.mPolicyManager =
                        (NetworkPolicyManager) this.mContext.getSystemService("netpolicy");
                TemplatePreference.NetworkServices networkServices2 = this.mServices;
                networkServices2.mPolicyEditor =
                        new NetworkPolicyEditor(networkServices2.mPolicyManager);
                this.mNetworkTemplate = DataUsageLib.getMobileTemplate(this.mContext, this.mSubId);
                this.mServices.mPolicyEditor.read();
                return;
            }
            if (this.mSubscriptions.size() > 1) {
                this.mSubId = this.mSubscriptions.get(this.mSlotIndex).getSubscriptionId();
                TemplatePreference.NetworkServices networkServices3 =
                        new TemplatePreference.NetworkServices();
                this.mServices = networkServices3;
                networkServices3.mNetworkService =
                        INetworkManagementService.Stub.asInterface(
                                ServiceManager.getService("network_management"));
                this.mServices.mTelephonyManager =
                        (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
                this.mServices.mUserManager =
                        (UserManager) this.mContext.getSystemService(UserManager.class);
                this.mServices.mPolicyManager =
                        (NetworkPolicyManager) this.mContext.getSystemService("netpolicy");
                TemplatePreference.NetworkServices networkServices4 = this.mServices;
                networkServices4.mPolicyEditor =
                        new NetworkPolicyEditor(networkServices4.mPolicyManager);
                this.mNetworkTemplate = DataUsageLib.getMobileTemplate(this.mContext, this.mSubId);
                this.mServices.mPolicyEditor.read();
            }
        }
    }

    @Override // com.android.settings.network.MobileDataEnabledListener.Client
    public void onMobileDataEnabledChange() {
        SecPreference secPreference = this.mSecPreference;
        if (secPreference != null) {
            updateState(secPreference);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mListener.start(SubscriptionManager.getDefaultDataSubscriptionId());
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mListener.stop();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        SecPreference secPreference = this.mSecPreference;
        if (secPreference != null) {
            try {
                secPreference.setEnabled(
                        this.mServices.mNetworkService.isBandwidthControlEnabled()
                                && this.mServices.mTelephonyManager.getDataEnabled(this.mSubId)
                                && this.mServices.mUserManager.isAdminUser());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            SecPreference secPreference2 = this.mSecPreference;
            secPreference2.getClass();
            SecPreferenceUtils.applySummaryColor(secPreference2, true);
        }
        super.updateState(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
