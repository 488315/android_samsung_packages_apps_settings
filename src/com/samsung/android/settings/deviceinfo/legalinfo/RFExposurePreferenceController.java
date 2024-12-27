package com.samsung.android.settings.deviceinfo.legalinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SemSystemProperties;
import android.text.TextUtils;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RFExposurePreferenceController extends BasePreferenceController {
    public RFExposurePreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean isCountryEUR() {
        ArrayList<String> arrayList =
                new ArrayList<
                        String>() { // from class:
                                    // com.samsung.android.settings.deviceinfo.legalinfo.RFExposurePreferenceController.1
                    {
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "AL", "ALB", "AD", "AND");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "AT", "AUT", "BE", "BEL");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "BA", "BIH", "BG", "BGR");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "HR", "HRV", "CY", "CYP");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "CZ", "CZE", "DK", "DNK");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "EE", "EST", "FI", "FIN");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "FR", "FRA", "DE", "DEU");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "GR", "GRC", "VA", "VAT");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "HU", "HUN", "GL", "GRL");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "IS", "ISL", "IE", "IRL");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "IT", "ITA", "XK", "XKX");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "LV", "LVA", "LI", "LIE");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "LT", "LTU", "LU", "LUX");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "MK", "MKD", "MT", "MLT");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "MC", "MCO", "ME", "MNE");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "NL", "NLD", "NO", "NOR");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "PL", "POL", "PT", "PRT");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "RO", "ROU", "SM", "SMR");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "RS", "RSB", "SK", "SVK");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "SI", "SVN", "ES", "ESP");
                        RFExposurePreferenceController$1$$ExternalSyntheticOutline0.m(
                                this, "SE", "SWE", "CH", "CHE");
                        add("GB");
                        add("GBR");
                    }
                };
        String countryIso = SemSystemProperties.getCountryIso();
        return !TextUtils.isEmpty(countryIso) && arrayList.contains(countryIso.toUpperCase());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return SemCscFeature.getInstance().getBoolean("CscFeature_Setting_SupportRFExposure")
                ? 0
                : 3;
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
