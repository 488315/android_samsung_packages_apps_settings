package com.android.settings.nfc;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.applications.defaultapps.DefaultAppPreferenceController;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NfcDefaultPaymentPreferenceController extends DefaultAppPreferenceController
        implements PaymentBackend.Callback, LifecycleObserver, OnResume, OnPause {
    public Context mContext;
    public PaymentBackend mPaymentBackend;
    public Preference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PaymentDefaultAppInfo extends DefaultAppInfo {
        public PaymentBackend.PaymentAppInfo mInfo;

        @Override // com.android.settingslib.applications.DefaultAppInfo,
                  // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return this.mInfo.icon;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference = preferenceScreen.findPreference("nfc_payment_app");
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final DefaultAppInfo getDefaultAppInfo() {
        PaymentBackend.PaymentAppInfo paymentAppInfo;
        PaymentBackend paymentBackend = this.mPaymentBackend;
        if (paymentBackend == null || (paymentAppInfo = paymentBackend.mDefaultAppInfo) == null) {
            return null;
        }
        PaymentDefaultAppInfo paymentDefaultAppInfo =
                new PaymentDefaultAppInfo(
                        this.mContext,
                        this.mPackageManager,
                        paymentAppInfo.userHandle.getIdentifier(),
                        paymentAppInfo.componentName);
        paymentDefaultAppInfo.mInfo = paymentAppInfo;
        return paymentDefaultAppInfo;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "nfc_payment_app";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.nfc")
                || NfcAdapter.getDefaultAdapter(this.mContext) == null) {
            return false;
        }
        if (this.mPaymentBackend == null) {
            this.mPaymentBackend = new PaymentBackend(this.mContext);
        }
        ArrayList arrayList = this.mPaymentBackend.mAppInfos;
        return (arrayList == null || arrayList.isEmpty()) ? false : true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        PaymentBackend paymentBackend = this.mPaymentBackend;
        if (paymentBackend != null) {
            paymentBackend.mCallbacks.remove(this);
            this.mPaymentBackend.mSettingsPackageMonitor.unregister();
        }
    }

    @Override // com.android.settings.nfc.PaymentBackend.Callback
    public final void onPaymentAppsChanged() {
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        PaymentBackend paymentBackend = this.mPaymentBackend;
        if (paymentBackend != null) {
            paymentBackend.mCallbacks.add(this);
            PaymentBackend paymentBackend2 = this.mPaymentBackend;
            Context context = paymentBackend2.mContext;
            paymentBackend2.mSettingsPackageMonitor.register(
                    context, context.getMainLooper(), false);
            paymentBackend2.refresh();
        }
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        preference.setIconSpaceReserved(true);
    }
}
