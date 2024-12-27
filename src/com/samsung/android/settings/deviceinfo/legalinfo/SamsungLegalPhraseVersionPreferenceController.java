package com.samsung.android.settings.deviceinfo.legalinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.android.app.swlpcontract.SWLPContract;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SamsungLegalPhraseVersionPreferenceController extends BasePreferenceController {
    private static final String TAG = "LegalPhraseVersionCtr";

    public SamsungLegalPhraseVersionPreferenceController(Context context, String str) {
        super(context, str);
    }

    private String getDiagnosticDataVer() {
        if ((!Utils.hasPackage(this.mContext, "com.sec.android.diagmonagent")
                        && !Utils.hasPackage(this.mContext, "com.samsung.android.dqagent"))
                || Settings.System.getInt(
                                this.mContext.getContentResolver(), "samsung_errorlog_agree", 0)
                        == 0) {
            return ApnSettings.MVNO_NONE;
        }
        return SWLPContract.getVersionByUri(this.mContext, SWLPContract.URI_DIAG_VERSION, false);
    }

    private String getEULAVer() {
        Uri uri = SWLPContract.URI_EULA_VERSION;
        Log.d(TAG, "URI_EULA_VERSION as Latest  : " + Rune.isUSA());
        return SWLPContract.getVersionByUri(this.mContext, uri, Rune.isUSA());
    }

    private String getLegalVersionInformation() {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(getEULAVer())) {
            sb.append(
                    this.mContext.getString(R.string.sec_legal_phrase_version_summary_eula)
                            + ": "
                            + getEULAVer()
                            + "\n");
        }
        if (!TextUtils.isEmpty(getPPVer())) {
            sb.append(
                    this.mContext.getString(R.string.sec_legal_phrase_version_summary_pp)
                            + ": "
                            + getPPVer()
                            + "\n");
        }
        if (!TextUtils.isEmpty(getDiagnosticDataVer())) {
            sb.append(
                    this.mContext.getString(R.string.sec_legal_phrase_version_summary_diag)
                            + ": "
                            + getDiagnosticDataVer()
                            + "\n");
        }
        sb.append("\n");
        return SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                .m(this.mContext, R.string.sec_legal_phrase_version_notice, sb);
    }

    private String getPPVer() {
        if (!supportShowPPVersion()) {
            return ApnSettings.MVNO_NONE;
        }
        return SWLPContract.getVersionByUri(this.mContext, SWLPContract.URI_PP_VERSION, false);
    }

    private boolean supportShowPPVersion() {
        return !Rune.isChinaModel();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        return getLegalVersionInformation();
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
