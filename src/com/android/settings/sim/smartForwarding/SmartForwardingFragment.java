package com.android.settings.sim.smartForwarding;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.CallForwardingInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.TwoStatePreference;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.sim.smartForwarding.SmartForwardingActivity.AnonymousClass1;
import com.android.settingslib.core.instrumentation.Instrumentable;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SmartForwardingFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener, Instrumentable {
    public boolean turnOffSwitch;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1571;
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        setPreferencesFromResource(R.xml.smart_forwarding_switch, str);
        getActivity()
                .getActionBar()
                .setTitle(getResources().getString(R.string.smart_forwarding_title));
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) findPreference("smart_forwarding_switch");
        if (this.turnOffSwitch) {
            twoStatePreference.setChecked(false);
        }
        twoStatePreference.setOnPreferenceChangeListener(this);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "onPreferenceChange. Update value to ", "SmartForwarding", booleanValue);
        if (booleanValue) {
            String phoneNumber = SmartForwardingUtils.getPhoneNumber(getContext(), 0);
            String phoneNumber2 = SmartForwardingUtils.getPhoneNumber(getContext(), 1);
            String[] strArr = {phoneNumber2, phoneNumber};
            if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(phoneNumber2)) {
                Log.d("SmartForwarding", "Slot 0 or Slot 1 phone number missing.");
                FragmentManagerImpl supportFragmentManager =
                        getActivity().getSupportFragmentManager();
                supportFragmentManager.getClass();
                BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
                backStackRecord.replace(R.id.content_frame, new MDNHandlerFragment(), null);
                backStackRecord.commitInternal(false);
            } else {
                ((SmartForwardingActivity) getActivity()).enableSmartForwarding(strArr);
            }
            return false;
        }
        SmartForwardingActivity smartForwardingActivity = (SmartForwardingActivity) getActivity();
        smartForwardingActivity.getClass();
        TelephonyManager telephonyManager =
                (TelephonyManager) smartForwardingActivity.getSystemService(TelephonyManager.class);
        SubscriptionManager subscriptionManager =
                (SubscriptionManager)
                        smartForwardingActivity.getSystemService(SubscriptionManager.class);
        int activeModemCount = telephonyManager.getActiveModemCount();
        boolean[] zArr = new boolean[activeModemCount];
        for (int i = 0; i < activeModemCount; i++) {
            zArr[i] =
                    smartForwardingActivity
                            .getSharedPreferences(
                                    "smart_forwarding_pref_"
                                            + SubscriptionManager.getSubscriptionId(i),
                                    0)
                            .getBoolean("call_waiting_key", false);
        }
        int activeModemCount2 = telephonyManager.getActiveModemCount();
        CallForwardingInfo[] callForwardingInfoArr = new CallForwardingInfo[activeModemCount2];
        for (int i2 = 0; i2 < activeModemCount2; i2++) {
            SharedPreferences sharedPreferences =
                    smartForwardingActivity.getSharedPreferences(
                            "smart_forwarding_pref_" + SubscriptionManager.getSubscriptionId(i2),
                            0);
            callForwardingInfoArr[i2] =
                    sharedPreferences.contains("call_forwarding_enabled_key")
                            ? new CallForwardingInfo(
                                    sharedPreferences.getBoolean(
                                            "call_forwarding_enabled_key", false),
                                    sharedPreferences.getInt("call_forwarding_reason_key", 0),
                                    sharedPreferences.getString(
                                            "call_forwarding_number_key", ApnSettings.MVNO_NONE),
                                    sharedPreferences.getInt("call_forwarding_timekey", 1))
                            : null;
        }
        ListenableFuture submit =
                ((MoreExecutors.ListeningDecorator) smartForwardingActivity.service)
                        .submit(
                                (Runnable)
                                        new DisableSmartForwardingTask(
                                                telephonyManager, zArr, callForwardingInfoArr));
        submit.addListener(
                new Futures.CallbackListener(
                        submit,
                        smartForwardingActivity
                        .new AnonymousClass1(subscriptionManager, telephonyManager)),
                smartForwardingActivity.getMainExecutor());
        return true;
    }
}
