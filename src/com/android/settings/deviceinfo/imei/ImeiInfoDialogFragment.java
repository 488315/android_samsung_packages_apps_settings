package com.android.settings.deviceinfo.imei;

import android.R;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.deviceinfo.PhoneNumberUtil;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Arrays;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ImeiInfoDialogFragment extends InstrumentedDialogFragment {
    static final String TAG = "ImeiInfoDialog";
    public static final int[] sViewIdsInDigitFormat =
            IntStream.of(
                            ImeiInfoDialogController.ID_MEID_NUMBER_VALUE,
                            ImeiInfoDialogController.ID_MIN_NUMBER_VALUE,
                            ImeiInfoDialogController.ID_IMEI_VALUE,
                            ImeiInfoDialogController.ID_IMEI_SV_VALUE)
                    .sorted()
                    .toArray();
    public View mRootView;

    public static void show(int i, Fragment fragment, String str) {
        FragmentManager childFragmentManager = fragment.getChildFragmentManager();
        if (childFragmentManager.findFragmentByTag(TAG) == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("arg_key_slot_id", i);
            bundle.putString("arg_key_dialog_title", str);
            ImeiInfoDialogFragment imeiInfoDialogFragment = new ImeiInfoDialogFragment();
            imeiInfoDialogFragment.setArguments(bundle);
            imeiInfoDialogFragment.show(childFragmentManager, TAG);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1240;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        int simState;
        Bundle arguments = getArguments();
        int i = arguments.getInt("arg_key_slot_id");
        String string = arguments.getString("arg_key_dialog_title");
        ImeiInfoDialogController imeiInfoDialogController = new ImeiInfoDialogController(this, i);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        builder.setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null);
        this.mRootView =
                LayoutInflater.from(alertParams.mContext)
                        .inflate(com.android.settings.R.layout.dialog_imei_info, (ViewGroup) null);
        TelephonyManager telephonyManager = imeiInfoDialogController.mTelephonyManager;
        if (telephonyManager == null) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    i, "TelephonyManager for this slot is null. Invalid slot? id=", TAG);
        } else if (telephonyManager.getPhoneType() == 2) {
            Resources resources = getContext().getResources();
            setText(
                    ImeiInfoDialogController.ID_MEID_NUMBER_VALUE,
                    imeiInfoDialogController.getMeid());
            int i2 = ImeiInfoDialogController.ID_MIN_NUMBER_VALUE;
            SubscriptionInfo subscriptionInfo = imeiInfoDialogController.mSubscriptionInfo;
            setText(
                    i2,
                    subscriptionInfo != null
                            ? imeiInfoDialogController.mTelephonyManager.getCdmaMin(
                                    subscriptionInfo.getSubscriptionId())
                            : ApnSettings.MVNO_NONE);
            if (resources.getBoolean(com.android.settings.R.bool.config_msid_enable)) {
                setText(
                        com.android.settings.R.id.min_number_label,
                        resources.getString(com.android.settings.R.string.status_msid_number));
            }
            setText(
                    ImeiInfoDialogController.ID_PRL_VERSION_VALUE,
                    imeiInfoDialogController.getCdmaPrlVersion());
            if ((imeiInfoDialogController.mSubscriptionInfo == null
                            || !imeiInfoDialogController.isCdmaLteEnabled())
                    && (imeiInfoDialogController.mSubscriptionInfo != null
                            || (simState =
                                            imeiInfoDialogController.mTelephonyManager.getSimState(
                                                    i))
                                    == 1
                            || simState == 0)) {
                View findViewById =
                        this.mRootView.findViewById(ImeiInfoDialogController.ID_GSM_SETTINGS);
                if (findViewById != null) {
                    findViewById.setVisibility(8);
                }
            } else {
                setText(
                        ImeiInfoDialogController.ID_IMEI_VALUE,
                        imeiInfoDialogController.mTelephonyManager.getImei(i));
                setText(
                        ImeiInfoDialogController.ID_IMEI_SV_VALUE,
                        imeiInfoDialogController.mTelephonyManager.getDeviceSoftwareVersion(i));
            }
        } else {
            setText(
                    ImeiInfoDialogController.ID_IMEI_VALUE,
                    imeiInfoDialogController.mTelephonyManager.getImei(i));
            setText(
                    ImeiInfoDialogController.ID_IMEI_SV_VALUE,
                    imeiInfoDialogController.mTelephonyManager.getDeviceSoftwareVersion(i));
            View findViewById2 =
                    this.mRootView.findViewById(ImeiInfoDialogController.ID_CDMA_SETTINGS);
            if (findViewById2 != null) {
                findViewById2.setVisibility(8);
            }
        }
        builder.setView(this.mRootView);
        return builder.create();
    }

    public final void setText(int i, CharSequence charSequence) {
        TextView textView = (TextView) this.mRootView.findViewById(i);
        if (textView == null) {
            return;
        }
        if (TextUtils.isEmpty(charSequence)) {
            charSequence =
                    getResources().getString(com.android.settings.R.string.device_info_default);
        } else if (Arrays.binarySearch(sViewIdsInDigitFormat, i) >= 0) {
            charSequence = PhoneNumberUtil.expandByTts(charSequence);
        }
        textView.setText(charSequence);
    }
}
