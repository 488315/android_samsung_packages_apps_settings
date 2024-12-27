package com.android.settings.nfc;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Pair;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NfcForegroundPreferenceController extends BasePreferenceController
        implements PaymentBackend.Callback,
                Preference.OnPreferenceChangeListener,
                LifecycleObserver,
                OnStart,
                OnStop {
    private final String[] mListEntries;
    private final String[] mListValues;
    private MetricsFeatureProvider mMetricsFeatureProvider;
    private PaymentBackend mPaymentBackend;
    private ListPreference mPreference;

    public NfcForegroundPreferenceController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mListValues = context.getResources().getStringArray(R.array.nfc_payment_favor_values);
        this.mListEntries = context.getResources().getStringArray(R.array.nfc_payment_favor);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        PaymentBackend paymentBackend;
        ArrayList arrayList;
        return (Flags.walletRoleEnabled()
                        || !this.mContext
                                .getPackageManager()
                                .hasSystemFeature("android.hardware.nfc")
                        || (paymentBackend = this.mPaymentBackend) == null
                        || (arrayList = paymentBackend.mAppInfos) == null
                        || arrayList.isEmpty())
                ? 3
                : 0;
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
        return this.mListEntries[this.mPaymentBackend.isForegroundMode() ? 1 : 0];
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
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

    @Override // com.android.settings.nfc.PaymentBackend.Callback
    public void onPaymentAppsChanged() {
        updateState(this.mPreference);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (!(preference instanceof ListPreference)) {
            return false;
        }
        ListPreference listPreference = (ListPreference) preference;
        String str = (String) obj;
        listPreference.setSummary(this.mListEntries[listPreference.findIndexOfValue(str)]);
        int i = Integer.parseInt(str) != 0 ? 1 : 0;
        PaymentBackend paymentBackend = this.mPaymentBackend;
        Iterator it =
                ((UserManager)
                                paymentBackend
                                        .mContext
                                        .createContextAsUser(
                                                UserHandle.of(UserHandle.myUserId()), 0)
                                        .getSystemService(UserManager.class))
                        .getEnabledProfiles()
                        .iterator();
        while (it.hasNext()) {
            Settings.Secure.putIntForUser(
                    paymentBackend.mContext.getContentResolver(),
                    "nfc_payment_foreground",
                    i,
                    ((UserHandle) it.next()).getIdentifier());
        }
        this.mMetricsFeatureProvider.action(this.mContext, i != 0 ? 1622 : 1623, new Pair[0]);
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        PaymentBackend paymentBackend = this.mPaymentBackend;
        if (paymentBackend != null) {
            paymentBackend.mCallbacks.add(this);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        PaymentBackend paymentBackend = this.mPaymentBackend;
        if (paymentBackend != null) {
            paymentBackend.mCallbacks.remove(this);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setPaymentBackend(PaymentBackend paymentBackend) {
        this.mPaymentBackend = paymentBackend;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            listPreference.setIconSpaceReserved(false);
            listPreference.setValue(
                    this.mListValues[this.mPaymentBackend.isForegroundMode() ? 1 : 0]);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
