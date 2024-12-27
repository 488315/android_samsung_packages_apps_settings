package com.samsung.android.settings.deviceinfo.statusinfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.qrcode.QrCodeGenerator;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.jvm.internal.Intrinsics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EidPreferenceController extends BasePreferenceController {
    private final String LOG_TAG;
    private final int QR_CODE_SIZE;
    private Activity mActivity;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.statusinfo.EidPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    public EidPreferenceController(Context context, String str) {
        super(context, str);
        this.LOG_TAG = "EidPreferenceController";
        this.QR_CODE_SIZE = 600;
    }

    private String getESimId() {
        String str = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("/efs/FactoryApp/eID"));
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    try {
                        str = bufferedReader.readLine();
                        bufferedReader.close();
                        inputStreamReader.close();
                        fileInputStream.close();
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Log.d("EidPreferenceController", "/eID/ - file null : " + e.getMessage());
        }
        if (TextUtils.isEmpty(str)) {
            for (UiccCardInfo uiccCardInfo :
                    ((TelephonyManager) this.mContext.getSystemService("phone"))
                            .getUiccCardsInfo()) {
                Log.d(
                        "EidPreferenceController",
                        "UiccSlotInfo info.getIsEuicc = " + uiccCardInfo.isEuicc());
                Log.d(
                        "EidPreferenceController",
                        "UiccSlotInfo info.getPhysicalSlotIndex = "
                                + uiccCardInfo.getPhysicalSlotIndex());
                if (uiccCardInfo.getPhysicalSlotIndex() == 1) {
                    str = uiccCardInfo.getEid();
                }
            }
        }
        return str;
    }

    private Bitmap getEidQrCode(String contents) {
        try {
            Intrinsics.checkNotNullParameter(contents, "contents");
            return QrCodeGenerator.encodeQrCode$default(600, contents);
        } catch (Exception e) {
            Log.w("EidPreferenceController", "Error when creating QR code width 600", e);
            return null;
        }
    }

    private void showEidQrCodeDialog(String str) {
        View inflate =
                this.mActivity
                        .getLayoutInflater()
                        .inflate(R.layout.sec_device_eid_dialog, (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setView(inflate);
        builder.setTitle(R.string.status_eid);
        ((TextView) inflate.findViewById(R.id.e_sim_id_value)).setText(str);
        ((ImageView) inflate.findViewById(R.id.e_sim_id_qrcode)).setImageBitmap(getEidQrCode(str));
        builder.setPositiveButton(R.string.ok, new AnonymousClass1());
        builder.show();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean hasSystemFeature =
                this.mContext
                        .getPackageManager()
                        .hasSystemFeature("android.hardware.telephony.euicc");
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isESimModel = ", "SecDeviceInfoUtils", hasSystemFeature);
        return hasSystemFeature ? 0 : 3;
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
        String eSimId = getESimId();
        return TextUtils.isEmpty(eSimId)
                ? this.mContext.getResources().getString(R.string.device_info_not_available)
                : eSimId;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        String eSimId = getESimId();
        if (TextUtils.isEmpty(eSimId)) {
            return true;
        }
        showEidQrCodeDialog(eSimId);
        return true;
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

    public void setHost(Fragment fragment) {
        this.mActivity = fragment.getActivity();
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
