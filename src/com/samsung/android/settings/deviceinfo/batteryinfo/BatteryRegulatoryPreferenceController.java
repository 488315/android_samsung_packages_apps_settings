package com.samsung.android.settings.deviceinfo.batteryinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.text.format.DateFormat;

import androidx.picker.widget.SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BatteryRegulatoryPreferenceController extends BasePreferenceController {
    public static final String LOG_TAG = "BatteryRegulatoryPreferenceController";

    public BatteryRegulatoryPreferenceController(Context context, String str) {
        super(context, str);
    }

    private String getDisplaySummary(String str) {
        return TextUtils.isEmpty(str) ? this.mContext.getString(R.string.unknown) : str;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return ((Rune.FEATURE_BATTERY_INFO_REGULATORY
                                && ((BatteryManager)
                                                this.mContext.getSystemService(
                                                        BatteryManager.class))
                                        .semGetValueAsBoolean(106))
                        || "SM-A236B".equalsIgnoreCase(SystemProperties.get("ro.product.model")))
                ? 0
                : 3;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getBatteryInfoFromEFS(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r5 = "BatteryRegulatoryPreferenceController"
            java.io.File r0 = new java.io.File
            r0.<init>(r6)
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Exception -> L2b
            r2.<init>(r0)     // Catch: java.lang.Exception -> L2b
            int r0 = r2.available()     // Catch: java.lang.Throwable -> L21
            byte[] r0 = new byte[r0]     // Catch: java.lang.Throwable -> L21
            r2.read(r0)     // Catch: java.lang.Throwable -> L21
            java.lang.String r3 = new java.lang.String     // Catch: java.lang.Throwable -> L21
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L21
            r2.close()     // Catch: java.lang.Exception -> L1f
            goto L42
        L1f:
            r0 = move-exception
            goto L2d
        L21:
            r0 = move-exception
            r2.close()     // Catch: java.lang.Throwable -> L26
            goto L2a
        L26:
            r2 = move-exception
            r0.addSuppressed(r2)     // Catch: java.lang.Exception -> L2b
        L2a:
            throw r0     // Catch: java.lang.Exception -> L2b
        L2b:
            r0 = move-exception
            r3 = r1
        L2d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "Fail to get value from EFS : "
            r2.<init>(r4)
            java.lang.String r0 = r0.getMessage()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            android.util.Log.w(r5, r0)
        L42:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r6)
            java.lang.String r6 = " : "
            r0.append(r6)
            r0.append(r3)
            java.lang.String r6 = r0.toString()
            android.util.Log.i(r5, r6)
            if (r3 == 0) goto L63
            java.lang.String r5 = "\n"
            java.lang.String r6 = ""
            java.lang.String r1 = r3.replace(r5, r6)
        L63:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController.getBatteryInfoFromEFS(java.lang.String):java.lang.String");
    }

    public String getFormattedDate(Date date) {
        return new SimpleDateFormat(
                        DateFormat.getBestDateTimePattern(Locale.getDefault(), "MMMM dd,yyyy"),
                        Locale.getDefault())
                .format(date);
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        if (!shouldDisplayAsDualBattery()) {
            return getDisplaySummary(getSummaryForBattery_Main());
        }
        return this.mContext.getString(
                        R.string.device_info_battery_main,
                        getDisplaySummary(getSummaryForBattery_Main()))
                + "\n"
                + this.mContext.getString(
                        R.string.device_info_battery_sub,
                        getDisplaySummary(getSummaryForBattery_Sub()));
    }

    public abstract String getSummaryForBattery_Main();

    public abstract String getSummaryForBattery_Sub();

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public boolean isDetachableDualBattery() {
        return SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m(
                "/sys/class/power_supply/sbp-fg-2/qr_code");
    }

    public boolean isDualBattery() {
        return SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m(
                        "/sys/class/power_supply/sec_auth_2nd/qr_code")
                || SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m(
                        "/sys/class/power_supply/sbp-fg-2/qr_code");
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public abstract boolean shouldDisplayAsDualBattery();

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
