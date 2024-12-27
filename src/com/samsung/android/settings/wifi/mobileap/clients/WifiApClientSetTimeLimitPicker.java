package com.samsung.android.settings.wifi.mobileap.clients;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.picker.app.SeslTimePickerDialog;

import com.android.settings.R;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApConnectedDeviceUtils;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApClientSetTimeLimitPicker extends SeslTimePickerDialog {
    public final Context mContext;
    public int mHourOfDay;
    public final String mMacAddress;
    public int mMinutes;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSetTimeLimitPicker$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            SALogging.insertSALog(0L, "TETH_014", "8087");
        }
    }

    public WifiApClientSetTimeLimitPicker(
            Context context,
            String str,
            WifiApClientSetTimeLimitPreferenceController$$ExternalSyntheticLambda0
                    wifiApClientSetTimeLimitPreferenceController$$ExternalSyntheticLambda0,
            int i,
            int i2) {
        super(
                context,
                wifiApClientSetTimeLimitPreferenceController$$ExternalSyntheticLambda0,
                i,
                i2,
                true);
        setTitle(R.string.wifi_ap_time_limit);
        this.mContext = context;
        this.mMacAddress = str;
        this.mHourOfDay = i;
        this.mMinutes = i2;
        if (i > 0 || i2 > 0) {
            setButton(
                    -2,
                    context.getString(R.string.data_limit_cancel),
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSetTimeLimitPicker.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            SALogging.insertSALog(2L, "TETH_014", "8087");
                            WifiApClientSetTimeLimitPicker wifiApClientSetTimeLimitPicker =
                                    WifiApClientSetTimeLimitPicker.this;
                            WifiApConnectedDeviceUtils.setWifiApClientTimeLimit(
                                    wifiApClientSetTimeLimitPicker.mContext,
                                    wifiApClientSetTimeLimitPicker.mMacAddress,
                                    0L);
                        }
                    });
            setButton(
                    -3,
                    context.getResources().getString(R.string.dlg_cancel),
                    new AnonymousClass2());
        }
    }

    @Override // androidx.picker.app.SeslTimePickerDialog, androidx.appcompat.app.AlertDialog,
              // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog,
              // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        validatePositiveButtonEnabling();
    }

    @Override // androidx.picker.app.SeslTimePickerDialog,
              // androidx.picker.widget.SeslTimePicker.OnTimeChangedListener
    public final void onTimeChanged(int i, int i2) {
        this.mHourOfDay = i;
        this.mMinutes = i2;
        validatePositiveButtonEnabling();
    }

    public final void validatePositiveButtonEnabling() {
        long wifiApClientUsedTimeToday =
                WifiApConnectedDeviceUtils.getWifiApClientUsedTimeToday(
                        this.mContext, this.mMacAddress);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        int minutes = ((int) timeUnit.toMinutes(wifiApClientUsedTimeToday)) % 60;
        int hours = (int) timeUnit.toHours(wifiApClientUsedTimeToday);
        if ((this.mHourOfDay * 60) + this.mMinutes <= (hours * 60) + minutes) {
            Log.i(
                    "WifiApClientSetTimeLimitPicker",
                    "Disabling POSITIVE BUTTON - mHourOfDay: "
                            + this.mHourOfDay
                            + ", usedHourOfDay: "
                            + hours
                            + " - mMinutes: "
                            + this.mMinutes
                            + "usedMinutes: "
                            + minutes);
            getButton(-1).setEnabled(false);
            return;
        }
        Log.i(
                "WifiApClientSetTimeLimitPicker",
                "Enabling POSITIVE BUTTON - mHourOfDay: "
                        + this.mHourOfDay
                        + ", usedHourOfDay: "
                        + hours
                        + " - mMinutes: "
                        + this.mMinutes
                        + "usedMinutes: "
                        + minutes);
        getButton(-1).setEnabled(true);
    }
}
