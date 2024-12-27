package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Telephony;
import android.telephony.SubscriptionInfo;
import android.telephony.ims.ImsManager;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.network.CarrierConfigCache;
import com.android.settings.network.SubscriptionUtil;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ContactDiscoveryPreferenceController extends TelephonyTogglePreferenceController
        implements LifecycleObserver {
    private static final String TAG = "ContactDiscoveryPref";
    private static final Uri UCE_URI =
            Uri.withAppendedPath(Telephony.SimInfo.CONTENT_URI, "ims_rcs_uce_enabled");
    private CarrierConfigCache mCarrierConfigCache;
    private FragmentManager mFragmentManager;
    private ImsManager mImsManager;
    private ContentObserver mUceSettingObserver;
    public Preference preference;

    public ContactDiscoveryPreferenceController(Context context, String str) {
        super(context, str);
        this.mImsManager = (ImsManager) this.mContext.getSystemService(ImsManager.class);
        this.mCarrierConfigCache = CarrierConfigCache.getInstance(context);
    }

    private CharSequence getCarrierDisplayName(Context context) {
        for (SubscriptionInfo subscriptionInfo :
                SubscriptionUtil.getAvailableSubscriptions(context)) {
            if (this.mSubId == subscriptionInfo.getSubscriptionId()) {
                return SubscriptionUtil.getUniqueSubscriptionDisplayName(context, subscriptionInfo);
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    private void registerUceObserver() {
        this.mUceSettingObserver =
                new ContentObserver(
                        this.mContext
                                .getMainThreadHandler()) { // from class:
                                                           // com.android.settings.network.telephony.ContactDiscoveryPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        onChange(z, null);
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        Log.d(
                                ContactDiscoveryPreferenceController.TAG,
                                "UCE setting changed, re-evaluating.");
                        ContactDiscoveryPreferenceController contactDiscoveryPreferenceController =
                                ContactDiscoveryPreferenceController.this;
                        ((TwoStatePreference) contactDiscoveryPreferenceController.preference)
                                .setChecked(
                                        contactDiscoveryPreferenceController.getThreadEnabled());
                    }
                };
        this.mContext
                .getContentResolver()
                .registerContentObserver(UCE_URI, true, this.mUceSettingObserver);
    }

    private void showContentDiscoveryDialog() {
        int i = this.mSubId;
        CharSequence carrierDisplayName = getCarrierDisplayName(this.preference.getContext());
        ContactDiscoveryDialogFragment contactDiscoveryDialogFragment =
                new ContactDiscoveryDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("sub_id_key", i);
        bundle.putCharSequence("carrier_name_key", carrierDisplayName);
        contactDiscoveryDialogFragment.setArguments(bundle);
        contactDiscoveryDialogFragment.show(
                this.mFragmentManager, "discovery_dialog:" + this.mSubId);
    }

    private void unregisterUceObserver() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mUceSettingObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.preference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        this.mCarrierConfigCache.getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        return (configForSubId == null
                        || !(configForSubId.getBoolean("use_rcs_presence_bool", false)
                                || configForSubId.getBoolean(
                                        "ims.rcs_bulk_capability_exchange_bool", false)))
                ? 2
                : 0;
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

    public void init(FragmentManager fragmentManager, int i) {
        this.mFragmentManager = fragmentManager;
        this.mSubId = i;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return MobileNetworkUtils.isContactDiscoveryEnabled(this.mImsManager, this.mSubId);
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

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        unregisterUceObserver();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        registerUceObserver();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (z) {
            showContentDiscoveryDialog();
            return false;
        }
        MobileNetworkUtils.setContactDiscoveryEnabled(this.mImsManager, this.mSubId, false);
        return true;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
