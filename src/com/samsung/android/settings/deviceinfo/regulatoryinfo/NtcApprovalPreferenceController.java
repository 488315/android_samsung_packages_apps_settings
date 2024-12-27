package com.samsung.android.settings.deviceinfo.regulatoryinfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.SystemProperties;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NtcApprovalPreferenceController extends BasePreferenceController {
    private static final String TAG = "NtcApprovalPreferenceController";
    private final String NTC_APPROVAL_INFO_ETC_PATH;
    private final String NTC_APPROVAL_INFO_FILE;
    private String NTC_APPROVAL_INFO_PATH;
    private final String NTC_APPROVAL_INFO_SYSTEM_PATH;

    public NtcApprovalPreferenceController(Context context, String str) {
        super(context, str);
        this.NTC_APPROVAL_INFO_SYSTEM_PATH = "/system/serviceinfo";
        this.NTC_APPROVAL_INFO_ETC_PATH = "/system/etc";
        this.NTC_APPROVAL_INFO_FILE = "ntc_image.png";
        this.NTC_APPROVAL_INFO_PATH = ApnSettings.MVNO_NONE;
    }

    private void showNtcApprovalInfo() {
        String str = SystemProperties.get("persist.sys.omc_etcpath", ApnSettings.MVNO_NONE);
        try {
            if (new File("/system/serviceinfo/ntc_image.png").exists()) {
                this.NTC_APPROVAL_INFO_PATH = "/system/serviceinfo/ntc_image.png";
            } else if (new File("/system/etc/ntc_image.png").exists()) {
                this.NTC_APPROVAL_INFO_PATH = "/system/etc/ntc_image.png";
            } else {
                if (new File(str + "/ntc_image.png").exists()) {
                    this.NTC_APPROVAL_INFO_PATH = str + "/ntc_image.png";
                }
            }
            Log.d(TAG, "take NTC info from : " + this.NTC_APPROVAL_INFO_PATH);
        } catch (Exception e) {
            Log.w(TAG, "fail to find NTC Info file : " + e.getMessage());
        }
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(R.layout.sec_ntc_approval_layout, (ViewGroup) null);
        ((ImageView) inflate.findViewById(R.id.ntc_image))
                .setImageBitmap(BitmapFactory.decodeFile(this.NTC_APPROVAL_INFO_PATH));
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setView(inflate);
        builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
        builder.setOnCancelListener(null);
        builder.create().show();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return SemCscFeature.getInstance()
                        .getString("CscFeature_Setting_ConfigServiceInfoNtcApproval")
                        .toLowerCase()
                        .contains("ntcapproval")
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        showNtcApprovalInfo();
        return false;
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
