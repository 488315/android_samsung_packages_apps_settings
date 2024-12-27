package com.samsung.android.settings.deviceinfo.statusinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ManufactureInfoController extends BasePreferenceController {
    private static final String KEY_MANUFACTURED_COUNTRY = "status_info_manufactured_country";
    private static final String KEY_MANUFACTURED_YEAR = "status_info_manufactured_year";
    private static final String LOG_TAG = "ManufactureInfoController";

    public ManufactureInfoController(Context context, String str) {
        super(context, str);
    }

    private String getManufacturedCountrySummary() {
        String stringFromEFS = getStringFromEFS("/efs/FactoryApp/copr");
        if (TextUtils.isEmpty(stringFromEFS)) {
            return null;
        }
        return translateManufacturedCountry(stringFromEFS);
    }

    private String getManufacturedYearSummary() {
        String stringFromEFS = getStringFromEFS("/efs/FactoryApp/cofpdata_year_code");
        if (TextUtils.isEmpty(stringFromEFS)) {
            return null;
        }
        try {
            return this.mContext.getString(
                    R.string.status_manufactured_year_value,
                    Integer.valueOf(Integer.parseInt(stringFromEFS)));
        } catch (NumberFormatException e) {
            Log.d(LOG_TAG, "year_code is invalid = " + e.getMessage());
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getStringFromEFS(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r5 = "ManufactureInfoController"
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
            goto L43
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
            java.lang.String r2 = "Fail to get value from : "
            java.lang.String r4 = " "
            java.lang.StringBuilder r2 = androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0.m(r2, r6, r4)
            java.lang.String r0 = r0.getMessage()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            android.util.Log.w(r5, r0)
        L43:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r6)
            java.lang.String r6 = " : "
            r0.append(r6)
            r0.append(r3)
            java.lang.String r6 = r0.toString()
            android.util.Log.d(r5, r6)
            if (r3 == 0) goto L64
            java.lang.String r5 = "\n"
            java.lang.String r6 = ""
            java.lang.String r1 = r3.replace(r5, r6)
        L64:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.statusinfo.ManufactureInfoController.getStringFromEFS(java.lang.String):java.lang.String");
    }

    private String translateManufacturedCountry(String str) {
        String upperCase = str.toUpperCase();
        upperCase.getClass();
        switch (upperCase) {
            case "INDONESIA":
                return this.mContext.getString(R.string.status_manufactured_indonesia);
            case "TURKIYE":
                return this.mContext.getString(R.string.status_manufactured_turkiye);
            case "CHINA":
                return this.mContext.getString(R.string.status_manufactured_china);
            case "INDIA":
                return this.mContext.getString(R.string.status_manufactured_india);
            case "KOREA":
                return this.mContext.getString(R.string.status_manufactured_korea);
            case "ARGENTINA":
                return this.mContext.getString(R.string.status_manufactured_argentina);
            case "VIETNAM":
                return this.mContext.getString(R.string.status_manufactured_vietnam);
            case "BRAZIL":
                return this.mContext.getString(R.string.status_manufactured_brazil);
            default:
                return this.mContext.getString(R.string.unknown);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!Rune.FEATURE_STATUS_INFO_REGULATORY_MANUFACTURE_INFO
                        || TextUtils.isEmpty(getManufacturedYearSummary())
                        || TextUtils.isEmpty(getManufacturedCountrySummary()))
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        String key = preference.getKey();
        key.getClass();
        String manufacturedCountrySummary =
                !key.equals(KEY_MANUFACTURED_YEAR)
                        ? !key.equals(KEY_MANUFACTURED_COUNTRY)
                                ? null
                                : getManufacturedCountrySummary()
                        : getManufacturedYearSummary();
        if (TextUtils.isEmpty(manufacturedCountrySummary)) {
            Log.d(LOG_TAG, "Manufacture info is null, Key = " + preference.getKey());
            manufacturedCountrySummary = this.mContext.getString(R.string.unknown);
        }
        preference.setSummary(manufacturedCountrySummary);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
