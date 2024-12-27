package com.android.settings.wifi;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkRequestErrorDialogFragment extends InstrumentedDialogFragment {
    public WifiManager.NetworkRequestUserSelectionCallback mRejectCallback;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class ERROR_DIALOG_TYPE {
        public static final /* synthetic */ ERROR_DIALOG_TYPE[] $VALUES;
        public static final ERROR_DIALOG_TYPE ABORT;
        public static final ERROR_DIALOG_TYPE TIME_OUT;

        static {
            ERROR_DIALOG_TYPE error_dialog_type = new ERROR_DIALOG_TYPE("TIME_OUT", 0);
            TIME_OUT = error_dialog_type;
            ERROR_DIALOG_TYPE error_dialog_type2 = new ERROR_DIALOG_TYPE("ABORT", 1);
            ABORT = error_dialog_type2;
            $VALUES = new ERROR_DIALOG_TYPE[] {error_dialog_type, error_dialog_type2};
        }

        public static ERROR_DIALOG_TYPE valueOf(String str) {
            return (ERROR_DIALOG_TYPE) Enum.valueOf(ERROR_DIALOG_TYPE.class, str);
        }

        public static ERROR_DIALOG_TYPE[] values() {
            return (ERROR_DIALOG_TYPE[]) $VALUES.clone();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1373;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        rejectNetworkRequestAndFinish();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        ERROR_DIALOG_TYPE error_dialog_type = ERROR_DIALOG_TYPE.TIME_OUT;
        ERROR_DIALOG_TYPE error_dialog_type2 =
                getArguments() != null
                        ? (ERROR_DIALOG_TYPE) getArguments().getSerializable("DIALOG_ERROR_TYPE")
                        : error_dialog_type;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (error_dialog_type2 == error_dialog_type) {
            builder.setMessage(R.string.network_connection_timeout_dialog_message);
            final int i = 0;
            builder.setPositiveButton(
                    R.string.network_connection_timeout_dialog_ok,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.wifi.NetworkRequestErrorDialogFragment$$ExternalSyntheticLambda0
                        public final /* synthetic */ NetworkRequestErrorDialogFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            int i3 = i;
                            NetworkRequestErrorDialogFragment networkRequestErrorDialogFragment =
                                    this.f$0;
                            switch (i3) {
                                case 0:
                                    if (networkRequestErrorDialogFragment.getActivity() != null) {
                                        networkRequestErrorDialogFragment.dismissInternal(
                                                false, false);
                                        NetworkRequestDialogActivity networkRequestDialogActivity =
                                                (NetworkRequestDialogActivity)
                                                        networkRequestErrorDialogFragment
                                                                .getActivity();
                                        networkRequestDialogActivity.mHandler
                                                .sendEmptyMessageDelayed(0, 30000L);
                                        networkRequestDialogActivity.mShowingErrorDialog = false;
                                        if (!networkRequestDialogActivity.mIsSpecifiedSsid) {
                                            NetworkRequestDialogFragment
                                                    networkRequestDialogFragment =
                                                            new NetworkRequestDialogFragment();
                                            networkRequestDialogActivity.mDialogFragment =
                                                    networkRequestDialogFragment;
                                            networkRequestDialogFragment.show(
                                                    networkRequestDialogActivity
                                                            .getSupportFragmentManager(),
                                                    "NetworkRequestDialogActivity");
                                            break;
                                        } else {
                                            networkRequestDialogActivity.mMatchedConfig = null;
                                            networkRequestDialogActivity.showProgressDialog$1(
                                                    networkRequestDialogActivity.getString(
                                                            R.string
                                                                    .network_connection_searching_message));
                                            break;
                                        }
                                    }
                                    break;
                                case 1:
                                    networkRequestErrorDialogFragment
                                            .rejectNetworkRequestAndFinish();
                                    break;
                                default:
                                    networkRequestErrorDialogFragment
                                            .rejectNetworkRequestAndFinish();
                                    break;
                            }
                        }
                    });
            final int i2 = 1;
            builder.setNegativeButton(
                    R.string.cancel,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.wifi.NetworkRequestErrorDialogFragment$$ExternalSyntheticLambda0
                        public final /* synthetic */ NetworkRequestErrorDialogFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i22) {
                            int i3 = i2;
                            NetworkRequestErrorDialogFragment networkRequestErrorDialogFragment =
                                    this.f$0;
                            switch (i3) {
                                case 0:
                                    if (networkRequestErrorDialogFragment.getActivity() != null) {
                                        networkRequestErrorDialogFragment.dismissInternal(
                                                false, false);
                                        NetworkRequestDialogActivity networkRequestDialogActivity =
                                                (NetworkRequestDialogActivity)
                                                        networkRequestErrorDialogFragment
                                                                .getActivity();
                                        networkRequestDialogActivity.mHandler
                                                .sendEmptyMessageDelayed(0, 30000L);
                                        networkRequestDialogActivity.mShowingErrorDialog = false;
                                        if (!networkRequestDialogActivity.mIsSpecifiedSsid) {
                                            NetworkRequestDialogFragment
                                                    networkRequestDialogFragment =
                                                            new NetworkRequestDialogFragment();
                                            networkRequestDialogActivity.mDialogFragment =
                                                    networkRequestDialogFragment;
                                            networkRequestDialogFragment.show(
                                                    networkRequestDialogActivity
                                                            .getSupportFragmentManager(),
                                                    "NetworkRequestDialogActivity");
                                            break;
                                        } else {
                                            networkRequestDialogActivity.mMatchedConfig = null;
                                            networkRequestDialogActivity.showProgressDialog$1(
                                                    networkRequestDialogActivity.getString(
                                                            R.string
                                                                    .network_connection_searching_message));
                                            break;
                                        }
                                    }
                                    break;
                                case 1:
                                    networkRequestErrorDialogFragment
                                            .rejectNetworkRequestAndFinish();
                                    break;
                                default:
                                    networkRequestErrorDialogFragment
                                            .rejectNetworkRequestAndFinish();
                                    break;
                            }
                        }
                    });
        } else {
            builder.setMessage(R.string.network_connection_errorstate_dialog_message);
            final int i3 = 2;
            builder.setPositiveButton(
                    R.string.okay,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.wifi.NetworkRequestErrorDialogFragment$$ExternalSyntheticLambda0
                        public final /* synthetic */ NetworkRequestErrorDialogFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i22) {
                            int i32 = i3;
                            NetworkRequestErrorDialogFragment networkRequestErrorDialogFragment =
                                    this.f$0;
                            switch (i32) {
                                case 0:
                                    if (networkRequestErrorDialogFragment.getActivity() != null) {
                                        networkRequestErrorDialogFragment.dismissInternal(
                                                false, false);
                                        NetworkRequestDialogActivity networkRequestDialogActivity =
                                                (NetworkRequestDialogActivity)
                                                        networkRequestErrorDialogFragment
                                                                .getActivity();
                                        networkRequestDialogActivity.mHandler
                                                .sendEmptyMessageDelayed(0, 30000L);
                                        networkRequestDialogActivity.mShowingErrorDialog = false;
                                        if (!networkRequestDialogActivity.mIsSpecifiedSsid) {
                                            NetworkRequestDialogFragment
                                                    networkRequestDialogFragment =
                                                            new NetworkRequestDialogFragment();
                                            networkRequestDialogActivity.mDialogFragment =
                                                    networkRequestDialogFragment;
                                            networkRequestDialogFragment.show(
                                                    networkRequestDialogActivity
                                                            .getSupportFragmentManager(),
                                                    "NetworkRequestDialogActivity");
                                            break;
                                        } else {
                                            networkRequestDialogActivity.mMatchedConfig = null;
                                            networkRequestDialogActivity.showProgressDialog$1(
                                                    networkRequestDialogActivity.getString(
                                                            R.string
                                                                    .network_connection_searching_message));
                                            break;
                                        }
                                    }
                                    break;
                                case 1:
                                    networkRequestErrorDialogFragment
                                            .rejectNetworkRequestAndFinish();
                                    break;
                                default:
                                    networkRequestErrorDialogFragment
                                            .rejectNetworkRequestAndFinish();
                                    break;
                            }
                        }
                    });
        }
        return builder.create();
    }

    public final void rejectNetworkRequestAndFinish() {
        if (getActivity() != null) {
            WifiManager.NetworkRequestUserSelectionCallback networkRequestUserSelectionCallback =
                    this.mRejectCallback;
            if (networkRequestUserSelectionCallback != null) {
                networkRequestUserSelectionCallback.reject();
            }
            getActivity().finish();
        }
    }
}
