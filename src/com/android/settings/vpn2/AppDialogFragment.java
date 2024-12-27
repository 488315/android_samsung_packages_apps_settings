package com.android.settings.vpn2;

import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.net.VpnManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.internal.net.VpnConfig;
import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppDialogFragment extends InstrumentedDialogFragment {
    public DevicePolicyManager mDevicePolicyManager;
    public AppManagementFragment.AnonymousClass1 mListener;
    public PackageInfo mPackageInfo;
    public UserManager mUserManager;
    public VpnManager mVpnManager;

    public static void show(
            Fragment fragment,
            AppManagementFragment.AnonymousClass1 anonymousClass1,
            PackageInfo packageInfo,
            String str,
            boolean z,
            boolean z2) {
        if (fragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("package", packageInfo);
            bundle.putString("label", str);
            bundle.putBoolean("managing", z);
            bundle.putBoolean("connected", z2);
            AppDialogFragment appDialogFragment = new AppDialogFragment();
            appDialogFragment.mListener = anonymousClass1;
            appDialogFragment.setArguments(bundle);
            appDialogFragment.setTargetFragment(fragment, 0);
            appDialogFragment.show(fragment.getFragmentManager(), "vpnappdialog");
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 546;
    }

    public final boolean isUiRestricted$1() {
        if (this.mUserManager.hasUserRestriction(
                "no_config_vpn",
                UserHandle.of(UserHandle.getUserId(this.mPackageInfo.applicationInfo.uid)))) {
            return true;
        }
        return this.mPackageInfo.packageName.equals(
                this.mDevicePolicyManager.getAlwaysOnVpnPackage());
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        dismissInternal(false, false);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mPackageInfo = (PackageInfo) getArguments().getParcelable("package");
        this.mUserManager = UserManager.get(getContext());
        this.mDevicePolicyManager =
                (DevicePolicyManager)
                        getContext()
                                .createContextAsUser(
                                        UserHandle.of(
                                                UserHandle.getUserId(
                                                        this.mPackageInfo.applicationInfo.uid)),
                                        0)
                                .getSystemService(DevicePolicyManager.class);
        this.mVpnManager = (VpnManager) getContext().getSystemService(VpnManager.class);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Bundle arguments = getArguments();
        String string = arguments.getString("label");
        boolean z = arguments.getBoolean("managing");
        boolean z2 = arguments.getBoolean("connected");
        if (z) {
            return new AppDialog(getActivity(), this, this.mPackageInfo, string);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        alertParams.mMessage = getActivity().getString(R.string.vpn_disconnect_confirm);
        builder.setNegativeButton(
                getActivity().getString(R.string.vpn_cancel),
                (DialogInterface.OnClickListener) null);
        if (z2 && !isUiRestricted$1()) {
            builder.setPositiveButton(
                    getActivity().getString(R.string.vpn_disconnect),
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.vpn2.AppDialogFragment.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            AppDialogFragment.this.onDisconnect();
                        }
                    });
        }
        return builder.create();
    }

    public final void onDisconnect() {
        if (isUiRestricted$1()) {
            return;
        }
        int userId = UserHandle.getUserId(this.mPackageInfo.applicationInfo.uid);
        String str = this.mPackageInfo.packageName;
        VpnConfig vpnConfig = this.mVpnManager.getVpnConfig(userId);
        if (str.equals(vpnConfig != null ? vpnConfig.user : null)) {
            this.mVpnManager.setAlwaysOnVpnPackageForUser(userId, null, false, null);
            this.mVpnManager.prepareVpn(this.mPackageInfo.packageName, "[Legacy VPN]", userId);
        }
    }
}
