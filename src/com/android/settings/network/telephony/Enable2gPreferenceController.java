package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.network.CarrierConfigCache;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class Enable2gPreferenceController extends TelephonyTogglePreferenceController {
    private static final long BITMASK_2G = 32843;
    private static final String LOG_TAG = "Enable2gPreferenceController";
    private CarrierConfigCache mCarrierConfigCache;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private RestrictedSwitchPreference mRestrictedPreference;
    private SubscriptionManager mSubscriptionManager;
    private TelephonyManager mTelephonyManager;

    public Enable2gPreferenceController(Context context, String str) {
        super(context, str);
        this.mCarrierConfigCache = CarrierConfigCache.getInstance(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mSubscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mRestrictedPreference = null;
    }

    private String getSimCardName() {
        SubscriptionInfo subById =
                SubscriptionUtil.getSubById(this.mSubscriptionManager, this.mSubId);
        if (subById == null) {
            return ApnSettings.MVNO_NONE;
        }
        CharSequence displayName = subById.getDisplayName();
        return TextUtils.isEmpty(displayName) ? ApnSettings.MVNO_NONE : displayName.toString();
    }

    private boolean isDisabledByAdmin() {
        RestrictedSwitchPreference restrictedSwitchPreference = this.mRestrictedPreference;
        return restrictedSwitchPreference != null
                && restrictedSwitchPreference.mHelper.mDisabledByAdmin;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mRestrictedPreference =
                (RestrictedSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        if (this.mTelephonyManager == null) {
            Log.w(LOG_TAG, "Telephony manager not yet initialized");
            this.mTelephonyManager =
                    (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        }
        return (SubscriptionManager.isUsableSubscriptionId(i)
                        && this.mTelephonyManager.isRadioInterfaceCapabilitySupported(
                                "CAPABILITY_USES_ALLOWED_NETWORK_TYPES_BITMASK"))
                ? 0
                : 2;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public Enable2gPreferenceController init(int i) {
        this.mSubId = i;
        this.mTelephonyManager =
                ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(this.mSubId);
        return this;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return (isDisabledByAdmin()
                        || (this.mTelephonyManager.getAllowedNetworkTypesForReason(3) & BITMASK_2G)
                                == 0)
                ? false
                : true;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        long j;
        if (isDisabledByAdmin() || !SubscriptionManager.isUsableSubscriptionId(this.mSubId)) {
            return false;
        }
        long allowedNetworkTypesForReason =
                this.mTelephonyManager.getAllowedNetworkTypesForReason(3);
        if (((allowedNetworkTypesForReason & BITMASK_2G) != 0) == z) {
            return false;
        }
        if (z) {
            j = allowedNetworkTypesForReason | BITMASK_2G;
            Log.i(LOG_TAG, "Enabling 2g. Allowed network types: " + j);
        } else {
            j = allowedNetworkTypesForReason & (-32844);
            Log.i(LOG_TAG, "Disabling 2g. Allowed network types: " + j);
        }
        this.mTelephonyManager.setAllowedNetworkTypesForReason(3, j);
        this.mMetricsFeatureProvider.action(this.mContext, 1761, z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (isDisabledByAdmin()
                || preference == null
                || !SubscriptionManager.isUsableSubscriptionId(this.mSubId)) {
            return;
        }
        preference.setSummary(this.mContext.getString(R.string.enable_2g_summary));
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
