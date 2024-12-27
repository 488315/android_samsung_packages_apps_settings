package com.android.settingslib.emergencynumber;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.emergency.EmergencyNumber;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.android.settings.homepage.contextualcards.ContextualCardManager$$ExternalSyntheticLambda3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EmergencyNumberUtils {
    public static final Uri EMERGENCY_NUMBER_OVERRIDE_AUTHORITY =
            new Uri.Builder().scheme("content").authority("com.android.emergency.gesture").build();
    static final String FALL_BACK_NUMBER = "112";
    public final CarrierConfigManager mCarrierConfigManager;
    public final Context mContext;
    public final TelephonyManager mTelephonyManager;

    public EmergencyNumberUtils(Context context) {
        this.mContext = context;
        if (context.getPackageManager().hasSystemFeature("android.hardware.telephony")) {
            this.mTelephonyManager =
                    (TelephonyManager) context.getSystemService(TelephonyManager.class);
            this.mCarrierConfigManager =
                    (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        } else {
            this.mTelephonyManager = null;
            this.mCarrierConfigManager = null;
        }
    }

    public final String getDefaultPoliceNumber() {
        List arrayList;
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null) {
            return "112";
        }
        Map<Integer, List<EmergencyNumber>> emergencyNumberList =
                telephonyManager.getEmergencyNumberList(1);
        if (emergencyNumberList == null || emergencyNumberList.isEmpty()) {
            Log.w("EmergencyNumberUtils", "Unable to retrieve emergency number lists!");
            arrayList = new ArrayList();
        } else {
            ArrayMap arrayMap = new ArrayMap();
            for (Map.Entry<Integer, List<EmergencyNumber>> entry : emergencyNumberList.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    Integer key = entry.getKey();
                    int intValue = key.intValue();
                    List<EmergencyNumber> value = entry.getValue();
                    Log.d(
                            "EmergencyNumberUtils",
                            "Emergency numbers for subscription id " + entry.getKey());
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    for (EmergencyNumber emergencyNumber : value) {
                        boolean contains = emergencyNumber.getEmergencyNumberSources().contains(16);
                        Log.d(
                                "EmergencyNumberUtils",
                                "Number "
                                        + emergencyNumber
                                        + ", isFromPrioritizedSource "
                                        + contains);
                        if (contains) {
                            arrayList2.add(emergencyNumber);
                        } else {
                            arrayList3.add(emergencyNumber);
                        }
                    }
                    arrayList2.addAll(arrayList3);
                    if (!arrayList2.isEmpty()) {
                        ArrayList arrayList4 = new ArrayList(arrayList2);
                        PersistableBundle configForSubId =
                                this.mCarrierConfigManager.getConfigForSubId(intValue);
                        final String[] stringArray =
                                configForSubId == null
                                        ? null
                                        : configForSubId.getStringArray(
                                                "emergency_number_prefix_string_array");
                        arrayMap.put(
                                key,
                                (List)
                                        arrayList4.stream()
                                                .map(
                                                        new Function() { // from class:
                                                            // com.android.settingslib.emergencynumber.EmergencyNumberUtils$$ExternalSyntheticLambda0
                                                            @Override // java.util.function.Function
                                                            public final Object apply(Object obj) {
                                                                EmergencyNumberUtils
                                                                        emergencyNumberUtils =
                                                                                EmergencyNumberUtils
                                                                                        .this;
                                                                String[] strArr = stringArray;
                                                                emergencyNumberUtils.getClass();
                                                                String number =
                                                                        ((EmergencyNumber) obj)
                                                                                .getNumber();
                                                                if (strArr == null
                                                                        || strArr.length == 0) {
                                                                    return number;
                                                                }
                                                                for (String str : strArr) {
                                                                    if (number.indexOf(str) == 0) {
                                                                        Log.d(
                                                                                "EmergencyNumberUtils",
                                                                                "Removing prefix "
                                                                                        + str
                                                                                        + " from "
                                                                                        + number);
                                                                        return number.substring(
                                                                                str.length());
                                                                    }
                                                                }
                                                                return number;
                                                            }
                                                        })
                                                .collect(
                                                        Collectors.toCollection(
                                                                new ContextualCardManager$$ExternalSyntheticLambda3())));
                    }
                }
            }
            if (arrayMap.isEmpty()) {
                Log.w("EmergencyNumberUtils", "No promoted emergency number found!");
            }
            arrayList =
                    (List)
                            arrayMap.get(
                                    Integer.valueOf(
                                            SubscriptionManager.getDefaultSubscriptionId()));
        }
        return (arrayList == null || arrayList.isEmpty()) ? "112" : (String) arrayList.get(0);
    }

    public final String getPoliceNumber() {
        Bundle call =
                this.mContext
                        .getContentResolver()
                        .call(
                                EMERGENCY_NUMBER_OVERRIDE_AUTHORITY,
                                "GET_EMERGENCY_NUMBER_OVERRIDE",
                                (String) null,
                                (Bundle) null);
        String string = call != null ? call.getString("emergency_gesture_call_number") : null;
        return TextUtils.isEmpty(string) ? getDefaultPoliceNumber() : string;
    }
}
