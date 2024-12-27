package com.android.settings.deviceinfo.simstatus;

import android.R;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.deviceinfo.PhoneNumberUtil;
import com.android.settings.deviceinfo.simstatus.SimStatusDialogController.CellBroadcastServiceConnection;
import com.android.settings.deviceinfo.simstatus.SimStatusDialogController.SimStatusDialogTelephonyCallback;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SimStatusDialogFragment extends InstrumentedDialogFragment {
    public static final int[] sViewIdsInDigitFormat =
            IntStream.of(
                            SimStatusDialogController.ICCID_INFO_VALUE_ID,
                            SimStatusDialogController.PHONE_NUMBER_VALUE_ID)
                    .sorted()
                    .toArray();
    public SimStatusDialogController mController;
    public View mRootView;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1246;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Bundle arguments = getArguments();
        int i = arguments.getInt("arg_key_sim_slot");
        String string = arguments.getString("arg_key_dialog_title");
        this.mController = new SimStatusDialogController(this, this.mLifecycle, i);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        String str = null;
        builder.setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null);
        this.mRootView =
                LayoutInflater.from(alertParams.mContext)
                        .inflate(com.android.settings.R.layout.dialog_sim_status, (ViewGroup) null);
        SimStatusDialogController simStatusDialogController = this.mController;
        if (simStatusDialogController.mSubscriptionInfo != null) {
            simStatusDialogController.mTelephonyManager =
                    simStatusDialogController
                            .getTelephonyManager()
                            .createForSubscriptionId(
                                    simStatusDialogController.mSubscriptionInfo
                                            .getSubscriptionId());
            simStatusDialogController.mTelephonyCallback =
                    simStatusDialogController.new SimStatusDialogTelephonyCallback();
            boolean z =
                    Resources.getSystem()
                                    .getBoolean(R.bool.config_smma_notification_supported_over_ims)
                            && simStatusDialogController.getTelephonyManager().getPhoneType() != 2;
            simStatusDialogController.mShowLatestAreaInfo = z;
            if (z) {
                simStatusDialogController.mCellBroadcastServiceConnection =
                        simStatusDialogController.new CellBroadcastServiceConnection();
                Intent intent = new Intent("android.telephony.CellBroadcastService");
                PackageManager packageManager =
                        simStatusDialogController.mContext.getPackageManager();
                List<ResolveInfo> queryIntentServices =
                        packageManager.queryIntentServices(
                                new Intent("android.telephony.CellBroadcastService"), 1048576);
                if (queryIntentServices.size() != 1) {
                    Log.e(
                            "SimStatusDialogCtrl",
                            "getCellBroadcastServicePackageName: found "
                                    + queryIntentServices.size()
                                    + " CBS packages");
                }
                Iterator<ResolveInfo> it = queryIntentServices.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        Log.e(
                                "SimStatusDialogCtrl",
                                "getCellBroadcastServicePackageName: package name not found");
                        break;
                    }
                    ServiceInfo serviceInfo = it.next().serviceInfo;
                    if (serviceInfo != null) {
                        String str2 = serviceInfo.packageName;
                        if (TextUtils.isEmpty(str2)) {
                            Log.e(
                                    "SimStatusDialogCtrl",
                                    "getCellBroadcastServicePackageName: found a CBS package but"
                                        + " packageName is null/empty");
                        } else {
                            if (packageManager.checkPermission(
                                            "android.permission.READ_PRIVILEGED_PHONE_STATE", str2)
                                    == 0) {
                                DialogFragment$$ExternalSyntheticOutline0.m(
                                        "getCellBroadcastServicePackageName: ",
                                        str2,
                                        "SimStatusDialogCtrl");
                                str = str2;
                                break;
                            }
                            SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                                    "getCellBroadcastServicePackageName: ",
                                    str2,
                                    " does not have READ_PRIVILEGED_PHONE_STATE permission",
                                    "SimStatusDialogCtrl");
                        }
                    }
                }
                if (!TextUtils.isEmpty(str)) {
                    intent.setPackage(str);
                    SimStatusDialogController.CellBroadcastServiceConnection
                            cellBroadcastServiceConnection =
                                    simStatusDialogController.mCellBroadcastServiceConnection;
                    if (cellBroadcastServiceConnection == null
                            || cellBroadcastServiceConnection.mService != null) {
                        Log.d(
                                "SimStatusDialogCtrl",
                                "skipping bindService because connection already exists");
                    } else if (!simStatusDialogController.mContext.bindService(
                            intent, cellBroadcastServiceConnection, 1)) {
                        Log.e("SimStatusDialogCtrl", "Unable to bind to service");
                    }
                }
            } else {
                int i2 = SimStatusDialogController.OPERATOR_INFO_LABEL_ID;
                SimStatusDialogFragment simStatusDialogFragment = simStatusDialogController.mDialog;
                simStatusDialogFragment.removeSettingFromScreen(i2);
                simStatusDialogFragment.removeSettingFromScreen(
                        SimStatusDialogController.OPERATOR_INFO_VALUE_ID);
            }
            simStatusDialogController.updateSubscriptionStatus();
        }
        builder.setView(this.mRootView);
        AlertDialog create = builder.create();
        create.getWindow().setFlags(8192, 8192);
        return create;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        SimStatusDialogController simStatusDialogController = this.mController;
        if (simStatusDialogController.mShowLatestAreaInfo) {
            SimStatusDialogController.CellBroadcastServiceConnection
                    cellBroadcastServiceConnection =
                            simStatusDialogController.mCellBroadcastServiceConnection;
            if (cellBroadcastServiceConnection != null
                    && cellBroadcastServiceConnection.mService != null) {
                simStatusDialogController.mContext.unbindService(cellBroadcastServiceConnection);
            }
            simStatusDialogController.mCellBroadcastServiceConnection = null;
        }
        super.onDestroy();
    }

    public final void removeSettingFromScreen(int i) {
        View findViewById = this.mRootView.findViewById(i);
        if (findViewById != null) {
            findViewById.setVisibility(8);
        }
    }

    public final void setSettingVisibility(int i, boolean z) {
        View findViewById = this.mRootView.findViewById(i);
        if (findViewById != null) {
            findViewById.setVisibility(z ? 0 : 8);
        }
    }

    public final void setText(int i, CharSequence charSequence) {
        if (!isAdded()) {
            Log.d("SimStatusDialog", "Fragment not attached yet.");
            return;
        }
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
