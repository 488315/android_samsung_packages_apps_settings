package com.samsung.android.settings.deviceinfo.aboutphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.samsung.android.settings.Rune;
import com.sec.ims.settings.ImsProfile;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PcoValueReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        int i;
        Log.i("PcoValueReceiver", "onReceive");
        if (!Rune.isVzwModel()) {
            Log.d("PcoValueReceiver", "For VZW Only");
            return;
        }
        if (intent == null) {
            Log.d("PcoValueReceiver", "intent == null");
            return;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            Log.d("PcoValueReceiver", "action == null");
            return;
        }
        this.mContext = context;
        if (!"com.android.internal.telephony.CARRIER_SIGNAL_PCO_VALUE".equals(action)) {
            if ("com.samsung.intent.action.SIMHOTSWAP".equals(action)) {
                Log.i("PcoValueReceiver", "Remove Pref file as Sim removed");
                Log.d("PcoValueReceiver", "resetPref START");
                this.mContext.getSharedPreferences("SecPcoValueReceiver", 0).edit().clear().apply();
                return;
            }
            return;
        }
        Log.i("PcoValueReceiver", "Start update state");
        int intExtra = intent.getIntExtra("subscription", -1);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(intExtra, "subId: ", "PcoValueReceiver");
        if (intExtra == -1) {
            Log.i("PcoValueReceiver", "Invalid subscriptionId");
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras == null) {
            Log.i("PcoValueReceiver", "Invalid CARRIER_SIGNAL_PCO_VALUE broadcast. No extras");
            return;
        }
        Set<String> keySet = extras.keySet();
        if (keySet == null || keySet.isEmpty()) {
            Log.i("PcoValueReceiver", "Invalid CARRIER_SIGNAL_PCO_VALUE broadcast. No pdns");
            return;
        }
        String string = extras.getString("apnType");
        byte[] byteArray = extras.getByteArray("pcoValue");
        if (TextUtils.isEmpty(string)) {
            Log.i(
                    "PcoValueReceiver",
                    "Invalid CARRIER_SIGNAL_PCO_VALUE broadcast. incorrect apn type");
            return;
        }
        if (byteArray != null) {
            boolean z = true;
            if (byteArray.length == 1) {
                if ((string.equals("default")
                                || string.equals("hipri")
                                || string.equals(ImsProfile.PDN_IMS))
                        && byteArray[0] - 48 == 5) {
                    Log.i(
                            "PcoValueReceiver",
                            "Received pco value '"
                                    + i
                                    + "' on pdn '"
                                    + string
                                    + "'. Set 'Phone number' to Unknown");
                } else {
                    z = false;
                }
                Log.i(
                        "PcoValueReceiver",
                        "Update PhoneNumberUnknownStateValue: "
                                + z
                                + " for subId("
                                + intExtra
                                + ")");
                SharedPreferences.Editor edit =
                        this.mContext.getSharedPreferences("SecPcoValueReceiver", 0).edit();
                StringBuilder sb = new StringBuilder("PhoneNumberUnknownStatesub_");
                sb.append(intExtra);
                edit.putBoolean(sb.toString(), z).apply();
                return;
            }
        }
        Log.i(
                "PcoValueReceiver",
                "Pco key doesn't contain valid pco value for apn " + string + ")");
    }
}
