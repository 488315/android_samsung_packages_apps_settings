package com.android.settings.network;

import android.content.Context;
import android.content.Intent;
import android.os.UserManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.mobile.dataservice.MobileNetworkInfoEntity;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity;
import com.android.settingslib.mobile.dataservice.UiccInfoEntity;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MobileNetworkSummaryController extends AbstractPreferenceController
        implements LifecycleObserver,
                PreferenceControllerMixin,
                MobileNetworkRepository.MobileNetworkCallback {
    public boolean mIsAirplaneModeOn;
    public final LifecycleOwner mLifecycleOwner;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public List mMobileNetworkInfoEntityList;
    public final MobileNetworkRepository mMobileNetworkRepository;
    public RestrictedPreference mPreference;
    public List mSubInfoEntityList;
    public List mUiccInfoEntityList;
    public final UserManager mUserManager;

    public static void $r8$lambda$4nYeAcxkLOLHcxNx6AaZQCyfxYg(
            MobileNetworkSummaryController mobileNetworkSummaryController, Preference preference) {
        mobileNetworkSummaryController.mMetricsFeatureProvider.logClickedPreference(
                preference, preference.getExtras().getInt("category"));
        Intent intent =
                new Intent("android.telephony.euicc.action.PROVISION_EMBEDDED_SUBSCRIPTION");
        intent.setPackage("com.android.phone");
        intent.putExtra("android.telephony.euicc.extra.FORCE_PROVISION", true);
        mobileNetworkSummaryController.mContext.startActivity(intent);
    }

    public MobileNetworkSummaryController(
            Context context, Lifecycle lifecycle, LifecycleOwner lifecycleOwner) {
        super(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mLifecycleOwner = lifecycleOwner;
        MobileNetworkRepository mobileNetworkRepository =
                MobileNetworkRepository.getInstance(context);
        this.mMobileNetworkRepository = mobileNetworkRepository;
        this.mIsAirplaneModeOn = mobileNetworkRepository.isAirplaneModeOn();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (RestrictedPreference) preferenceScreen.findPreference("mobile_network_list");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "mobile_network_list";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        List list;
        List list2;
        List list3 = this.mSubInfoEntityList;
        if (list3 == null
                || list3.isEmpty()
                || (list = this.mUiccInfoEntityList) == null
                || list.isEmpty()
                || (list2 = this.mMobileNetworkInfoEntityList) == null
                || list2.isEmpty()) {
            return MobileNetworkUtils.showEuiccSettingsDetecting(this.mContext).booleanValue()
                    ? this.mContext
                            .getResources()
                            .getString(R.string.mobile_network_summary_add_a_network)
                    : ApnSettings.MVNO_NONE;
        }
        if (this.mSubInfoEntityList.size() != 1) {
            return (CharSequence)
                    this.mSubInfoEntityList.stream()
                            .map(new MobileNetworkSummaryController$$ExternalSyntheticLambda1())
                            .collect(Collectors.joining(", "));
        }
        SubscriptionInfoEntity subscriptionInfoEntity =
                (SubscriptionInfoEntity) this.mSubInfoEntityList.get(0);
        String str = subscriptionInfoEntity.uniqueName;
        return (subscriptionInfoEntity.isEmbedded
                        || ((UiccInfoEntity) this.mUiccInfoEntityList.get(0)).isActive
                        || ((MobileNetworkInfoEntity) this.mMobileNetworkInfoEntityList.get(0))
                                .showToggleForPhysicalSim)
                ? str
                : this.mContext.getString(R.string.mobile_network_tap_to_activate, str);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return SubscriptionUtil.isSimHardwareVisible(this.mContext)
                && !Utils.isWifiOnly(this.mContext)
                && this.mUserManager.isAdminUser();
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public final void onAirplaneModeChanged(boolean z) {
        if (this.mIsAirplaneModeOn != z) {
            this.mIsAirplaneModeOn = z;
            update$1$1();
        }
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public final void onAllMobileNetworkInfoChanged(List list) {
        this.mMobileNetworkInfoEntityList = list;
        update$1$1();
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public final void onAllUiccInfoChanged(List list) {
        this.mUiccInfoEntityList = list;
        update$1$1();
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public final void onAvailableSubInfoChanged(List list) {
        this.mSubInfoEntityList = list;
        update$1$1();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mMobileNetworkRepository.removeRegister(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        MobileNetworkRepository mobileNetworkRepository = this.mMobileNetworkRepository;
        mobileNetworkRepository.addRegister(lifecycleOwner, this, -1);
        mobileNetworkRepository.updateEntity();
    }

    public final void update$1$1() {
        List list;
        List list2;
        RestrictedPreference restrictedPreference = this.mPreference;
        if (restrictedPreference == null || restrictedPreference.mHelper.mDisabledByAdmin) {
            return;
        }
        refreshSummary(restrictedPreference);
        this.mPreference.setOnPreferenceClickListener(null);
        this.mPreference.setFragment(null);
        this.mPreference.setEnabled(!this.mIsAirplaneModeOn);
        List list3 = this.mSubInfoEntityList;
        if (list3 != null
                && !list3.isEmpty()
                && (list = this.mUiccInfoEntityList) != null
                && !list.isEmpty()
                && (list2 = this.mMobileNetworkInfoEntityList) != null
                && !list2.isEmpty()) {
            this.mPreference.setFragment(MobileNetworkListFragment.class.getCanonicalName());
        } else if (MobileNetworkUtils.showEuiccSettingsDetecting(this.mContext).booleanValue()) {
            this.mPreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.network.MobileNetworkSummaryController$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            MobileNetworkSummaryController.$r8$lambda$4nYeAcxkLOLHcxNx6AaZQCyfxYg(
                                    MobileNetworkSummaryController.this, preference);
                            return true;
                        }
                    });
        } else {
            this.mPreference.setEnabled(false);
        }
    }
}
