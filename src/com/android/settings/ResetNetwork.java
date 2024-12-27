package com.android.settings;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.NetworkPolicyManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;

import com.android.settings.core.SubSettingLauncher;
import com.android.settings.network.ResetNetworkRestrictionViewBuilder;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.telephony.EuiccRacConnectivityDialogActivity;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.system.ResetDashboardFragment;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.general.ResetSettingsPreferenceFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class ResetNetwork extends ResetSettingsPreferenceFragment {
    public ActivityResultLauncher mActivityResultLauncher;
    public View mContentView;
    CheckBox mEsimCheckbox;
    View mEsimContainer;
    public final int mSubId = -1;
    public Spinner mSubscriptionSpinner;
    public List mSubscriptions;

    public final void establishInitialState(List list) {
        this.mSubscriptionSpinner =
                (Spinner) this.mContentView.findViewById(R.id.reset_network_subscription);
        this.mEsimContainer = this.mContentView.findViewById(R.id.erase_esim_container);
        this.mEsimCheckbox = (CheckBox) this.mContentView.findViewById(R.id.erase_esim);
        this.mSubscriptions = list;
        if (list == null || list.size() <= 0) {
            this.mSubscriptionSpinner.setVisibility(8);
        } else {
            int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
            if (!SubscriptionManager.isUsableSubscriptionId(defaultDataSubscriptionId)) {
                defaultDataSubscriptionId = SubscriptionManager.getDefaultVoiceSubscriptionId();
            }
            if (!SubscriptionManager.isUsableSubscriptionId(defaultDataSubscriptionId)) {
                defaultDataSubscriptionId = SubscriptionManager.getDefaultSmsSubscriptionId();
            }
            if (!SubscriptionManager.isUsableSubscriptionId(defaultDataSubscriptionId)) {
                defaultDataSubscriptionId = SubscriptionManager.getDefaultSubscriptionId();
            }
            this.mSubscriptions.size();
            ArrayList arrayList = new ArrayList();
            int i = 0;
            for (SubscriptionInfo subscriptionInfo : this.mSubscriptions) {
                if (subscriptionInfo.getSubscriptionId() == defaultDataSubscriptionId) {
                    i = arrayList.size();
                }
                arrayList.add(
                        subscriptionInfo.getSimSlotIndex() == 0
                                ? Settings.System.getString(
                                        getActivity().getContentResolver(), "select_name_1")
                                : Settings.System.getString(
                                        getActivity().getContentResolver(), "select_name_2"));
            }
            ArrayAdapter arrayAdapter =
                    new ArrayAdapter(
                            getActivity(), R.layout.sec_reset_network_spinner_item, arrayList);
            arrayAdapter.setDropDownViewResource(R.layout.sec_simple_spinner_dropdown_item);
            this.mSubscriptionSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
            this.mSubscriptionSpinner.setSelection(i);
            if (this.mSubscriptions.size() > 1) {
                this.mSubscriptionSpinner.setVisibility(0);
            } else {
                this.mSubscriptionSpinner.setVisibility(8);
            }
        }
        this.mEsimContainer.setVisibility(8);
    }

    public final List getActiveSubscriptionInfoList() {
        if (!SubscriptionUtil.isSimHardwareVisible(getActivity())) {
            return Collections.emptyList();
        }
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) getActivity().getSystemService(SubscriptionManager.class);
        if (subscriptionManager != null) {
            return (List)
                    Optional.ofNullable(subscriptionManager.getActiveSubscriptionInfoList())
                            .orElse(Collections.emptyList());
        }
        Log.w("ResetNetwork", "No SubscriptionManager");
        return Collections.emptyList();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return ResetDashboardFragment.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 83;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final String getResetButtonTitle() {
        return getString(R.string.reset_network_button_text);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_general";
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActivity().setTitle(R.string.sec_reset_mobile_network_settings);
        this.mActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts$StartActivityForResult(0),
                        new ActivityResultCallback() { // from class:
                                                       // com.android.settings.ResetNetwork$$ExternalSyntheticLambda0
                            @Override // androidx.activity.result.ActivityResultCallback
                            public final void onActivityResult(Object obj) {
                                ResetNetwork resetNetwork = ResetNetwork.this;
                                resetNetwork.getClass();
                                if (((ActivityResult) obj).mResultCode == -1) {
                                    resetNetwork.showFinalConfirmation();
                                } else if (resetNetwork.mContentView != null) {
                                    resetNetwork.establishInitialState(
                                            resetNetwork.getActiveSubscriptionInfoList());
                                }
                            }
                        });
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View build = new ResetNetworkRestrictionViewBuilder(getActivity()).build();
        if (build != null) {
            Log.w("ResetNetwork", "Access deny.");
            return build;
        }
        this.mContentView = layoutInflater.inflate(R.layout.sec_reset_network, (ViewGroup) null);
        establishInitialState(getActiveSubscriptionInfoList());
        return this.mContentView;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final void onResetButtonClick() {
        Resources resources = getActivity().getResources();
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(getActivity(), this);
        builder.mRequestCode = 55;
        builder.mTitle = resources.getText(R.string.reset_mobile_network_settings_title);
        builder.mActivityResultLauncher = this.mActivityResultLauncher;
        if (builder.show()) {
            return;
        }
        showFinalConfirmation();
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mContentView == null) {
            return;
        }
        List activeSubscriptionInfoList = getActiveSubscriptionInfoList();
        List list = this.mSubscriptions;
        if (list != null
                && list.size() == activeSubscriptionInfoList.size()
                && this.mSubscriptions.containsAll(activeSubscriptionInfoList)) {
            return;
        }
        Log.d("ResetNetwork", "subcription list changed");
        establishInitialState(activeSubscriptionInfoList);
    }

    public void showFinalConfirmation() {
        int i;
        int i2;
        boolean z;
        SubSettingLauncher subSettingLauncher;
        Bundle bundle = new Bundle();
        Context context = getContext();
        List list = this.mSubscriptions;
        if (list == null || list.size() <= 0) {
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                TelephonyManager createForSubscriptionId =
                        ((TelephonyManager) getActivity().getSystemService(TelephonyManager.class))
                                .createForSubscriptionId(this.mSubId);
                if (((NetworkPolicyManager) getActivity().getSystemService("netpolicy")) != null
                        && createForSubscriptionId.getSubscriberId() != null) {
                    i = this.mSubId;
                    i2 = -1;
                    if (this.mEsimContainer.getVisibility() == 0
                            || !this.mEsimCheckbox.isChecked()) {
                        bundle.putInt("resetNetworkOptions", 3);
                        bundle.putString("resetEsimPackage", null);
                        bundle.putInt("resetTelephonyNetPolicySubId", i);
                        bundle.putInt("resetApnSubId", i2);
                        bundle.putInt("resetImsSubId", -1);
                        z = false;
                    } else {
                        String packageName = context.getPackageName();
                        bundle.putInt("resetNetworkOptions", 3);
                        bundle.putString("resetEsimPackage", packageName);
                        bundle.putInt("resetTelephonyNetPolicySubId", i);
                        bundle.putInt("resetApnSubId", i2);
                        bundle.putInt("resetImsSubId", -1);
                        z = true;
                    }
                    subSettingLauncher = new SubSettingLauncher(context);
                    String name = ResetNetworkConfirm.class.getName();
                    SubSettingLauncher.LaunchRequest launchRequest =
                            subSettingLauncher.mLaunchRequest;
                    launchRequest.mDestinationName = name;
                    launchRequest.mArguments = bundle;
                    subSettingLauncher.setTitleRes(
                            R.string.sec_reset_mobile_network_settings, null);
                    launchRequest.mSourceMetricsCategory = 83;
                    if (z || !SubscriptionUtil.shouldShowRacDialogWhenErasingAllEsims(context)) {
                        subSettingLauncher.launch();
                    }
                    Intent intent = subSettingLauncher.toIntent();
                    int i3 = EuiccRacConnectivityDialogActivity.$r8$clinit;
                    Intent intent2 =
                            new Intent(
                                    context, (Class<?>) EuiccRacConnectivityDialogActivity.class);
                    intent2.putExtra("reset_mobile_netword_id", intent);
                    context.startActivity(intent2);
                    return;
                }
            }
            i = -1;
        } else {
            i =
                    ((SubscriptionInfo)
                                    this.mSubscriptions.get(
                                            this.mSubscriptionSpinner.getSelectedItemPosition()))
                            .getSubscriptionId();
            bundle.putInt("android.telephony.extra.SUBSCRIPTION_INDEX", i);
        }
        i2 = i;
        if (this.mEsimContainer.getVisibility() == 0) {}
        bundle.putInt("resetNetworkOptions", 3);
        bundle.putString("resetEsimPackage", null);
        bundle.putInt("resetTelephonyNetPolicySubId", i);
        bundle.putInt("resetApnSubId", i2);
        bundle.putInt("resetImsSubId", -1);
        z = false;
        subSettingLauncher = new SubSettingLauncher(context);
        String name2 = ResetNetworkConfirm.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest2 = subSettingLauncher.mLaunchRequest;
        launchRequest2.mDestinationName = name2;
        launchRequest2.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.sec_reset_mobile_network_settings, null);
        launchRequest2.mSourceMetricsCategory = 83;
        if (z) {}
        subSettingLauncher.launch();
    }
}
