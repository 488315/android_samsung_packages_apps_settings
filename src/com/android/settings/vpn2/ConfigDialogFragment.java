package com.android.settings.vpn2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.VpnManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.LegacyVpnProfileStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnProfile;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConfigDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener,
                DialogInterface.OnShowListener,
                View.OnClickListener,
                ConfirmLockdownFragment.ConfirmLockdownListener {
    public Context mContext;
    public int mIsUserAddProfilesAllowed;
    public int mIsUserChangeProfilesAllowed;
    public VpnManager mService;

    public static void show(
            VpnSettings vpnSettings,
            VpnProfile vpnProfile,
            boolean z,
            boolean z2,
            String[] strArr,
            String[] strArr2) {
        if (vpnSettings.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ImsProfile.SERVICE_PROFILE, vpnProfile);
            bundle.putBoolean("editing", z);
            bundle.putBoolean("exists", z2);
            bundle.putStringArray("user_cert", strArr);
            bundle.putStringArray("ca_cert", strArr2);
            ConfigDialogFragment configDialogFragment = new ConfigDialogFragment();
            configDialogFragment.setArguments(bundle);
            configDialogFragment.setTargetFragment(vpnSettings, 0);
            configDialogFragment.show(vpnSettings.getFragmentManager(), "vpnconfigdialog");
        }
    }

    public final void connect(VpnProfile vpnProfile, boolean z) {
        save(vpnProfile, z);
        if (vpnProfile.key.equals(VpnUtils.getLockdownVpn())) {
            return;
        }
        this.mService.setAlwaysOnVpnPackageForUser(UserHandle.myUserId(), null, false, null);
        VpnUtils.clearLockdownVpn(this.mContext);
        try {
            this.mService.startLegacyVpn(vpnProfile);
        } catch (IllegalStateException unused) {
            Toast.makeText(this.mContext, R.string.vpn_no_network, 1).show();
        } catch (UnsupportedOperationException unused2) {
            Log.e("ConfigDialogFragment", "Attempted to start an unsupported VPN type.");
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setMessage(R.string.vpn_start_unsupported);
            builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
            builder.show();
        }
    }

    public final boolean disconnect(VpnProfile vpnProfile) {
        try {
            LegacyVpnInfo legacyVpnInfo = this.mService.getLegacyVpnInfo(UserHandle.myUserId());
            if (legacyVpnInfo == null || !vpnProfile.key.equals(legacyVpnInfo.key)) {
                return true;
            }
            return VpnUtils.disconnectLegacyVpn(getContext());
        } catch (RemoteException e) {
            Log.e("ConfigDialogFragment", "Failed to disconnect", e);
            return false;
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 545;
    }

    @Override // com.android.settings.core.instrumentation.InstrumentedDialogFragment,
              // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mService = (VpnManager) context.getSystemService(VpnManager.class);
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        dismissInternal(false, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x00b3, code lost:

       connect(r1, r10);
    */
    @Override // android.content.DialogInterface.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onClick(android.content.DialogInterface r13, int r14) {
        /*
            Method dump skipped, instructions count: 234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.vpn2.ConfigDialogFragment.onClick(android.content.DialogInterface,"
                    + " int):void");
    }

    @Override // com.android.settings.vpn2.ConfirmLockdownFragment.ConfirmLockdownListener
    public final void onConfirmLockdown(Bundle bundle, boolean z, boolean z2) {
        connect((VpnProfile) bundle.getParcelable(ImsProfile.SERVICE_PROFILE), z);
        dismissInternal(false, false);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Bundle arguments = getArguments();
        ConfigDialog configDialog =
                new ConfigDialog(
                        getActivity(),
                        this,
                        arguments.getParcelable(ImsProfile.SERVICE_PROFILE),
                        arguments.getBoolean("editing"),
                        arguments.getBoolean("exists"),
                        arguments.getStringArray("user_cert"),
                        arguments.getStringArray("ca_cert"));
        configDialog.setOnShowListener(this);
        return configDialog;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        String[] strArr = {"false"};
        this.mIsUserAddProfilesAllowed =
                Utils.getEnterprisePolicyEnabled(
                        getActivity(),
                        "content://com.sec.knox.provider2/vpnPolicy",
                        "isUserAddProfilesAllowed",
                        strArr);
        this.mIsUserChangeProfilesAllowed =
                Utils.getEnterprisePolicyEnabled(
                        getActivity(),
                        "content://com.sec.knox.provider2/vpnPolicy",
                        "isUserChangeProfilesAllowed",
                        strArr);
        if (Utils.getEnterprisePolicyEnabled(
                        getActivity(),
                        "content://com.sec.knox.provider/RestrictionPolicy4",
                        "isVpnAllowed")
                == 0) {
            Log.v("ConfigDialogFragment", "Restriction Policy applied");
            Toast.makeText(getContext(), getResources().getText(R.string.vpn_block_add), 0).show();
            dismissInternal(false, false);
        }
    }

    @Override // android.content.DialogInterface.OnShowListener
    public final void onShow(DialogInterface dialogInterface) {
        ((AlertDialog) this.mDialog).getButton(-1).setOnClickListener(this);
    }

    public final void save(VpnProfile vpnProfile, boolean z) {
        LegacyVpnProfileStore.put("VPN_" + vpnProfile.key, vpnProfile.encode(true));
        disconnect(vpnProfile);
        updateLockdownVpn(vpnProfile, z);
    }

    public final void updateLockdownVpn(VpnProfile vpnProfile, boolean z) {
        if (!z) {
            if (vpnProfile.key.equals(VpnUtils.getLockdownVpn())) {
                VpnUtils.clearLockdownVpn(this.mContext);
            }
        } else {
            if (!vpnProfile.isValidLockdownProfile()) {
                Toast.makeText(this.mContext, R.string.vpn_lockdown_config_error, 1).show();
                return;
            }
            this.mService.setAlwaysOnVpnPackageForUser(UserHandle.myUserId(), null, false, null);
            Context context = this.mContext;
            LegacyVpnProfileStore.put("LOCKDOWN_VPN", vpnProfile.key.getBytes());
            VpnUtils.getVpnManager(context).updateLockdownVpn();
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        onClick(this.mDialog, -1);
    }
}
