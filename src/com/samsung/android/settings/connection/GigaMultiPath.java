package com.samsung.android.settings.connection;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GigaMultiPath extends Fragment implements CompoundButton.OnCheckedChangeListener {
    public AnonymousClass8 mAirplaneModeObserver;
    public Handler mHoldingHandler;
    public boolean mIsMobileDataEnabling;
    public boolean mIsNetworkEnabling;
    public boolean mIsWiFiEnabling;
    public AnonymousClass8 mMobileDataObserver;
    public MptcpStateReceiver mMptcpStateReceiver;
    public ProgressDialog mProgressDialog;
    public AnonymousClass7 mRemoveProgress;
    public SettingsMainSwitchBar mSwitchBar;
    public TelephonyManager mTelephonyManager;
    public WifiManager mWifiManager;
    public AlertDialog.Builder mAlertDialog = null;
    public boolean mIsSupport5G = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MptcpStateReceiver extends BroadcastReceiver {
        public MptcpStateReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int intExtra;
            String action = intent.getAction();
            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                    "MPTCPStateReceiver: onReceive action=",
                    action,
                    "GigaMultiPath",
                    "com.samsung.android.mptcp.MPTCP_STATE")) {
                ProgressDialog progressDialog = GigaMultiPath.this.mProgressDialog;
                if (progressDialog != null && progressDialog.isShowing()) {
                    GigaMultiPath.this.closeProgressDialog();
                }
                GigaMultiPath.this.mSwitchBar.setEnabled(true);
                Log.d("GigaMultiPath", "MPTCP State intent is received");
                if (intent.getIntExtra("mptcp_state", 0) == 1) {
                    GigaMultiPath.this.mSwitchBar.setChecked(true);
                    return;
                } else {
                    Log.d("GigaMultiPath", "Cannot Enable MPTCP");
                    GigaMultiPath.this.mSwitchBar.setChecked(false);
                    return;
                }
            }
            if (!"android.net.wifi.WIFI_STATE_CHANGED".equals(action)
                    || 2 == (intExtra = intent.getIntExtra("wifi_state", 4))) {
                return;
            }
            GigaMultiPath.this.getClass();
            boolean z = intExtra == 3;
            GigaMultiPath gigaMultiPath = GigaMultiPath.this;
            if (gigaMultiPath.mIsNetworkEnabling && gigaMultiPath.mIsWiFiEnabling && z) {
                gigaMultiPath.mIsWiFiEnabling = false;
                if (gigaMultiPath.mIsMobileDataEnabling) {
                    return;
                }
                gigaMultiPath.mIsNetworkEnabling = false;
                gigaMultiPath.sendMptcpStartBroadCast$1(
                        ((SeslSwitchBar) gigaMultiPath.mSwitchBar).mSwitch.isChecked());
                return;
            }
            if (gigaMultiPath.mIsWiFiEnabling) {
                gigaMultiPath.mIsWiFiEnabling = false;
                if (z) {
                    gigaMultiPath.sendMptcpStartBroadCast$1(
                            ((SeslSwitchBar) gigaMultiPath.mSwitchBar).mSwitch.isChecked());
                    return;
                }
                ProgressDialog progressDialog2 = gigaMultiPath.mProgressDialog;
                if (progressDialog2 != null && progressDialog2.isShowing()) {
                    GigaMultiPath.this.closeProgressDialog();
                }
                GigaMultiPath.this.mSwitchBar.setEnabled(true);
                GigaMultiPath gigaMultiPath2 = GigaMultiPath.this;
                gigaMultiPath2.mSwitchBar.setChecked(
                        Settings.System.getInt(
                                        gigaMultiPath2.getContext().getContentResolver(),
                                        "mptcp_value",
                                        0)
                                != 0);
            }
        }
    }

    public final void closeProgressDialog() {
        this.mHoldingHandler.removeCallbacks(this.mRemoveProgress);
        this.mRemoveProgress = null;
        this.mProgressDialog.dismiss();
        this.mProgressDialog = null;
    }

    public final boolean isSimValid$1() {
        int phoneCount = this.mTelephonyManager.getPhoneCount();
        for (int i = 0; i < phoneCount; i++) {
            if (this.mTelephonyManager.getSimState(i) != 1) {
                String subscriberId = this.mTelephonyManager.getSubscriberId();
                return subscriberId != null && subscriberId.contains("45006");
            }
        }
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        this.mSwitchBar.setChecked(
                Settings.System.getInt(getContext().getContentResolver(), "mptcp_value", 0) != 0);
        this.mSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, final boolean z) {
        if (z
                == (Settings.System.getInt(getContext().getContentResolver(), "mptcp_value", 0)
                        != 0)) {
            return;
        }
        if (z) {
            if (Settings.System.getInt(getContext().getContentResolver(), "smart_bonding", 0)
                    != 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                this.mAlertDialog = builder;
                builder.setTitle(R.string.multi_path_title);
                this.mAlertDialog.setMessage(R.string.multi_path_samrtbonding_off);
                final int i = 0;
                this.mAlertDialog.setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.connection.GigaMultiPath.1
                            public final /* synthetic */ GigaMultiPath this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i2) {
                                switch (i) {
                                    case 0:
                                        Settings.System.putInt(
                                                this.this$0.getContext().getContentResolver(),
                                                "smart_bonding",
                                                0);
                                        if (!ConnectionsUtils.isWifiEnabled(
                                                        this.this$0.getActivity())
                                                || !ConnectionsUtils.isMobileNetworkEnabled(
                                                        this.this$0.getActivity())) {
                                            this.this$0.onCheckedChanged(null, true);
                                            break;
                                        } else {
                                            this.this$0.mSwitchBar.setEnabled(false);
                                            GigaMultiPath gigaMultiPath = this.this$0;
                                            gigaMultiPath.sendMptcpStartBroadCast$1(
                                                    ((SeslSwitchBar) gigaMultiPath.mSwitchBar)
                                                            .mSwitch.isChecked());
                                            this.this$0.showProgressDialog$1$1();
                                            break;
                                        }
                                        break;
                                    case 1:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                    case 2:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                    case 3:
                                        GigaMultiPath gigaMultiPath2 = this.this$0;
                                        gigaMultiPath2.mIsMobileDataEnabling = true;
                                        gigaMultiPath2.mTelephonyManager.setDataEnabled(true);
                                        this.this$0.mSwitchBar.setEnabled(false);
                                        this.this$0.showProgressDialog$1$1();
                                        break;
                                    default:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                }
                            }
                        });
                final int i2 = 1;
                this.mAlertDialog.setNegativeButton(
                        android.R.string.cancel,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.connection.GigaMultiPath.1
                            public final /* synthetic */ GigaMultiPath this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i22) {
                                switch (i2) {
                                    case 0:
                                        Settings.System.putInt(
                                                this.this$0.getContext().getContentResolver(),
                                                "smart_bonding",
                                                0);
                                        if (!ConnectionsUtils.isWifiEnabled(
                                                        this.this$0.getActivity())
                                                || !ConnectionsUtils.isMobileNetworkEnabled(
                                                        this.this$0.getActivity())) {
                                            this.this$0.onCheckedChanged(null, true);
                                            break;
                                        } else {
                                            this.this$0.mSwitchBar.setEnabled(false);
                                            GigaMultiPath gigaMultiPath = this.this$0;
                                            gigaMultiPath.sendMptcpStartBroadCast$1(
                                                    ((SeslSwitchBar) gigaMultiPath.mSwitchBar)
                                                            .mSwitch.isChecked());
                                            this.this$0.showProgressDialog$1$1();
                                            break;
                                        }
                                        break;
                                    case 1:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                    case 2:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                    case 3:
                                        GigaMultiPath gigaMultiPath2 = this.this$0;
                                        gigaMultiPath2.mIsMobileDataEnabling = true;
                                        gigaMultiPath2.mTelephonyManager.setDataEnabled(true);
                                        this.this$0.mSwitchBar.setEnabled(false);
                                        this.this$0.showProgressDialog$1$1();
                                        break;
                                    default:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                }
                            }
                        });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (!ConnectionsUtils.isWifiEnabled(getActivity())
                    && !ConnectionsUtils.isMobileNetworkEnabled(getActivity())) {
                this.mSwitchBar.setChecked(false);
                Toast.makeText(getActivity(), R.string.multi_path_toast_msg, 1).show();
                return;
            }
            if (!ConnectionsUtils.isWifiEnabled(getActivity())) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                this.mAlertDialog = builder2;
                builder2.setTitle(R.string.multi_path_title);
                getActivity();
                if (ConnectionsUtils.isSupport5GConcept()) {
                    this.mAlertDialog.setMessage(R.string.multi_path_wifi_msg_5g);
                } else {
                    this.mAlertDialog.setMessage(R.string.multi_path_wifi_msg);
                }
                this.mAlertDialog.setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.connection.GigaMultiPath.3
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i3) {
                                GigaMultiPath gigaMultiPath = GigaMultiPath.this;
                                if (gigaMultiPath.mWifiManager != null) {
                                    if (ConnectionsUtils.isWifiEnabled(
                                            gigaMultiPath.getActivity())) {
                                        GigaMultiPath.this.mSwitchBar.setEnabled(false);
                                        GigaMultiPath.this.showProgressDialog$1$1();
                                        GigaMultiPath.this.sendMptcpStartBroadCast$1(z);
                                    } else {
                                        GigaMultiPath gigaMultiPath2 = GigaMultiPath.this;
                                        gigaMultiPath2.mIsWiFiEnabling = true;
                                        gigaMultiPath2.mWifiManager.setWifiEnabled(true);
                                        GigaMultiPath.this.mSwitchBar.setEnabled(false);
                                        GigaMultiPath.this.showProgressDialog$1$1();
                                    }
                                }
                            }
                        });
                final int i3 = 2;
                this.mAlertDialog.setNegativeButton(
                        android.R.string.cancel,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.connection.GigaMultiPath.1
                            public final /* synthetic */ GigaMultiPath this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i22) {
                                switch (i3) {
                                    case 0:
                                        Settings.System.putInt(
                                                this.this$0.getContext().getContentResolver(),
                                                "smart_bonding",
                                                0);
                                        if (!ConnectionsUtils.isWifiEnabled(
                                                        this.this$0.getActivity())
                                                || !ConnectionsUtils.isMobileNetworkEnabled(
                                                        this.this$0.getActivity())) {
                                            this.this$0.onCheckedChanged(null, true);
                                            break;
                                        } else {
                                            this.this$0.mSwitchBar.setEnabled(false);
                                            GigaMultiPath gigaMultiPath = this.this$0;
                                            gigaMultiPath.sendMptcpStartBroadCast$1(
                                                    ((SeslSwitchBar) gigaMultiPath.mSwitchBar)
                                                            .mSwitch.isChecked());
                                            this.this$0.showProgressDialog$1$1();
                                            break;
                                        }
                                        break;
                                    case 1:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                    case 2:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                    case 3:
                                        GigaMultiPath gigaMultiPath2 = this.this$0;
                                        gigaMultiPath2.mIsMobileDataEnabling = true;
                                        gigaMultiPath2.mTelephonyManager.setDataEnabled(true);
                                        this.this$0.mSwitchBar.setEnabled(false);
                                        this.this$0.showProgressDialog$1$1();
                                        break;
                                    default:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                }
                            }
                        });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (!ConnectionsUtils.isMobileNetworkEnabled(getActivity())) {
                AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                this.mAlertDialog = builder3;
                builder3.setTitle(R.string.multi_path_title);
                this.mAlertDialog.setMessage(R.string.multi_path_mobile_msg);
                final int i4 = 3;
                this.mAlertDialog.setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.connection.GigaMultiPath.1
                            public final /* synthetic */ GigaMultiPath this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i22) {
                                switch (i4) {
                                    case 0:
                                        Settings.System.putInt(
                                                this.this$0.getContext().getContentResolver(),
                                                "smart_bonding",
                                                0);
                                        if (!ConnectionsUtils.isWifiEnabled(
                                                        this.this$0.getActivity())
                                                || !ConnectionsUtils.isMobileNetworkEnabled(
                                                        this.this$0.getActivity())) {
                                            this.this$0.onCheckedChanged(null, true);
                                            break;
                                        } else {
                                            this.this$0.mSwitchBar.setEnabled(false);
                                            GigaMultiPath gigaMultiPath = this.this$0;
                                            gigaMultiPath.sendMptcpStartBroadCast$1(
                                                    ((SeslSwitchBar) gigaMultiPath.mSwitchBar)
                                                            .mSwitch.isChecked());
                                            this.this$0.showProgressDialog$1$1();
                                            break;
                                        }
                                        break;
                                    case 1:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                    case 2:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                    case 3:
                                        GigaMultiPath gigaMultiPath2 = this.this$0;
                                        gigaMultiPath2.mIsMobileDataEnabling = true;
                                        gigaMultiPath2.mTelephonyManager.setDataEnabled(true);
                                        this.this$0.mSwitchBar.setEnabled(false);
                                        this.this$0.showProgressDialog$1$1();
                                        break;
                                    default:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                }
                            }
                        });
                final int i5 = 4;
                this.mAlertDialog.setNegativeButton(
                        android.R.string.cancel,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.connection.GigaMultiPath.1
                            public final /* synthetic */ GigaMultiPath this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i22) {
                                switch (i5) {
                                    case 0:
                                        Settings.System.putInt(
                                                this.this$0.getContext().getContentResolver(),
                                                "smart_bonding",
                                                0);
                                        if (!ConnectionsUtils.isWifiEnabled(
                                                        this.this$0.getActivity())
                                                || !ConnectionsUtils.isMobileNetworkEnabled(
                                                        this.this$0.getActivity())) {
                                            this.this$0.onCheckedChanged(null, true);
                                            break;
                                        } else {
                                            this.this$0.mSwitchBar.setEnabled(false);
                                            GigaMultiPath gigaMultiPath = this.this$0;
                                            gigaMultiPath.sendMptcpStartBroadCast$1(
                                                    ((SeslSwitchBar) gigaMultiPath.mSwitchBar)
                                                            .mSwitch.isChecked());
                                            this.this$0.showProgressDialog$1$1();
                                            break;
                                        }
                                        break;
                                    case 1:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                    case 2:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                    case 3:
                                        GigaMultiPath gigaMultiPath2 = this.this$0;
                                        gigaMultiPath2.mIsMobileDataEnabling = true;
                                        gigaMultiPath2.mTelephonyManager.setDataEnabled(true);
                                        this.this$0.mSwitchBar.setEnabled(false);
                                        this.this$0.showProgressDialog$1$1();
                                        break;
                                    default:
                                        this.this$0.mSwitchBar.setChecked(false);
                                        break;
                                }
                            }
                        });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
        }
        this.mSwitchBar.setEnabled(false);
        showProgressDialog$1$1();
        sendMptcpStartBroadCast$1(z);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.multi_path_title);
        }
        this.mTelephonyManager = (TelephonyManager) getContext().getSystemService("phone");
        this.mWifiManager = (WifiManager) getContext().getSystemService(ImsProfile.PDN_WIFI);
        int phoneCount = this.mTelephonyManager.getPhoneCount();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= phoneCount) {
                break;
            }
            if (this.mTelephonyManager.getSimState(i) != 1) {
                z = true;
                break;
            }
            i++;
        }
        if (!z) {
            Toast.makeText(getActivity(), R.string.multi_path_nosim_msg, 1).show();
        } else if (!isSimValid$1()) {
            Toast.makeText(getActivity(), R.string.multi_path_nolg_sim, 1).show();
        } else if (ConnectionsUtils.isRoaming(getContext())) {
            Toast.makeText(getActivity(), R.string.multi_path_roaming, 1).show();
        } else if (ConnectionsUtils.isAirplaneModeEnabled(getContext())) {
            Toast.makeText(getActivity(), R.string.multi_path_airplane, 1).show();
        } else {
            WifiManager wifiManager = this.mWifiManager;
            if (wifiManager != null && wifiManager.getWifiApState() == 13) {
                Toast.makeText(getActivity(), R.string.multi_path_mobile_hotspot, 1).show();
            }
        }
        getContext();
        if (ConnectionsUtils.isSupport5GConcept()) {
            this.mIsSupport5G = true;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(R.layout.sec_airplane_mode_settings, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.airplane_mode_desc_text_view))
                .setText(
                        this.mIsSupport5G ? R.string.multi_path_body_5G : R.string.multi_path_body);
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mSwitchBar.removeOnSwitchChangeListener(this);
        this.mSwitchBar.hide();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mMptcpStateReceiver != null) {
            getActivity().unregisterReceiver(this.mMptcpStateReceiver);
            this.mMptcpStateReceiver = null;
        }
        getContext().getContentResolver().unregisterContentObserver(this.mAirplaneModeObserver);
        getContext().getContentResolver().unregisterContentObserver(this.mMobileDataObserver);
    }

    /* JADX WARN: Type inference failed for: r2v6, types: [com.samsung.android.settings.connection.GigaMultiPath$8] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.samsung.android.settings.connection.GigaMultiPath$8] */
    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        IntentFilter m =
                ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.mptcp.MPTCP_STATE",
                        "android.net.wifi.WIFI_STATE_CHANGED");
        if (this.mMptcpStateReceiver == null) {
            this.mMptcpStateReceiver = new MptcpStateReceiver();
        }
        getActivity().registerReceiver(this.mMptcpStateReceiver, m, 2);
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uriFor = Settings.System.getUriFor("airplane_mode_on");
        if (this.mAirplaneModeObserver == null) {
            final int i = 0;
            this.mAirplaneModeObserver =
                    new ContentObserver(
                            this,
                            new Handler()) { // from class:
                                             // com.samsung.android.settings.connection.GigaMultiPath.8
                        public final /* synthetic */ GigaMultiPath this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            switch (i) {
                                case 0:
                                    if (!ConnectionsUtils.isRoaming(this.this$0.getContext())
                                            && this.this$0.isSimValid$1()) {
                                        this.this$0.mSwitchBar.setEnabled(
                                                !ConnectionsUtils.isAirplaneModeEnabled(
                                                        r1.getContext()));
                                        break;
                                    }
                                    break;
                                default:
                                    GigaMultiPath gigaMultiPath = this.this$0;
                                    if (!gigaMultiPath.mIsNetworkEnabling
                                            || !gigaMultiPath.mIsMobileDataEnabling) {
                                        if (gigaMultiPath.mIsMobileDataEnabling) {
                                            gigaMultiPath.mIsMobileDataEnabling = false;
                                            gigaMultiPath.sendMptcpStartBroadCast$1(
                                                    ((SeslSwitchBar) gigaMultiPath.mSwitchBar)
                                                            .mSwitch.isChecked());
                                            break;
                                        }
                                    } else {
                                        gigaMultiPath.mIsMobileDataEnabling = false;
                                        if (!gigaMultiPath.mIsWiFiEnabling) {
                                            gigaMultiPath.mIsNetworkEnabling = false;
                                            gigaMultiPath.sendMptcpStartBroadCast$1(
                                                    ((SeslSwitchBar) gigaMultiPath.mSwitchBar)
                                                            .mSwitch.isChecked());
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    };
        }
        contentResolver.registerContentObserver(uriFor, true, this.mAirplaneModeObserver);
        ContentResolver contentResolver2 = getContext().getContentResolver();
        Uri uriFor2 = Settings.Global.getUriFor("mobile_data");
        if (this.mMobileDataObserver == null) {
            final int i2 = 1;
            this.mMobileDataObserver =
                    new ContentObserver(
                            this,
                            new Handler()) { // from class:
                                             // com.samsung.android.settings.connection.GigaMultiPath.8
                        public final /* synthetic */ GigaMultiPath this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            switch (i2) {
                                case 0:
                                    if (!ConnectionsUtils.isRoaming(this.this$0.getContext())
                                            && this.this$0.isSimValid$1()) {
                                        this.this$0.mSwitchBar.setEnabled(
                                                !ConnectionsUtils.isAirplaneModeEnabled(
                                                        r1.getContext()));
                                        break;
                                    }
                                    break;
                                default:
                                    GigaMultiPath gigaMultiPath = this.this$0;
                                    if (!gigaMultiPath.mIsNetworkEnabling
                                            || !gigaMultiPath.mIsMobileDataEnabling) {
                                        if (gigaMultiPath.mIsMobileDataEnabling) {
                                            gigaMultiPath.mIsMobileDataEnabling = false;
                                            gigaMultiPath.sendMptcpStartBroadCast$1(
                                                    ((SeslSwitchBar) gigaMultiPath.mSwitchBar)
                                                            .mSwitch.isChecked());
                                            break;
                                        }
                                    } else {
                                        gigaMultiPath.mIsMobileDataEnabling = false;
                                        if (!gigaMultiPath.mIsWiFiEnabling) {
                                            gigaMultiPath.mIsNetworkEnabling = false;
                                            gigaMultiPath.sendMptcpStartBroadCast$1(
                                                    ((SeslSwitchBar) gigaMultiPath.mSwitchBar)
                                                            .mSwitch.isChecked());
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    };
        }
        contentResolver2.registerContentObserver(uriFor2, true, this.mMobileDataObserver);
        if (ConnectionsUtils.isRoaming(getContext())
                || ConnectionsUtils.isAirplaneModeEnabled(getContext())
                || !isSimValid$1()) {
            this.mSwitchBar.setEnabled(false);
        }
        boolean z =
                Settings.System.getInt(getContext().getContentResolver(), "mptcp_value", 0) != 0;
        if (this.mSwitchBar.isEnabled()
                && z != ((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked()) {
            this.mSwitchBar.setChecked(z);
        }
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog != null
                && progressDialog.isShowing()
                && z == ((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked()) {
            this.mSwitchBar.setEnabled(true);
            closeProgressDialog();
        }
    }

    public final void sendMptcpStartBroadCast$1(boolean z) {
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.mptcp.MPTCP_START");
        intent.putExtra("mptcp_start", z ? 1 : 0);
        Log.d("GigaMultiPath", "Sending MPTCP Start Stop broadcast to MPTCP Service: " + intent);
        getContext().sendBroadcast(intent);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.samsung.android.settings.connection.GigaMultiPath$7] */
    public final void showProgressDialog$1$1() {
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                this.mProgressDialog.dismiss();
            }
            this.mProgressDialog = null;
        }
        ProgressDialog progressDialog2 = new ProgressDialog(getContext());
        this.mProgressDialog = progressDialog2;
        progressDialog2.setIndeterminate(true);
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setMessage(getText(R.string.mptcp_processing_dialog_message));
        this.mProgressDialog.show();
        if (this.mHoldingHandler == null) {
            this.mHoldingHandler = new Handler();
        }
        if (this.mRemoveProgress == null) {
            this.mRemoveProgress =
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.connection.GigaMultiPath.7
                        @Override // java.lang.Runnable
                        public final void run() {
                            ProgressDialog progressDialog3 = GigaMultiPath.this.mProgressDialog;
                            if (progressDialog3 == null || !progressDialog3.isShowing()) {
                                return;
                            }
                            GigaMultiPath.this.mSwitchBar.setChecked(false);
                            GigaMultiPath.this.mSwitchBar.setEnabled(true);
                            GigaMultiPath.this.closeProgressDialog();
                        }
                    };
        }
        this.mHoldingHandler.postDelayed(this.mRemoveProgress, 15000L);
    }
}
