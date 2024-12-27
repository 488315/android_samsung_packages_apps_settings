package com.samsung.android.settings.vpn.bixby;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.security.LegacyVpnProfileStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

import com.android.internal.net.VpnProfile;
import com.android.settings.R;

import com.google.android.collect.Lists;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.ParameterValues$$ExternalSyntheticLambda0;
import com.samsung.android.sdk.routines.v3.internal.ExtraKey;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RoutineConfigActivity extends Activity {
    public String mSelectedProfileKey = ApnSettings.MVNO_NONE;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        if (bundle != null) {
            this.mSelectedProfileKey =
                    bundle.getString("selected_profile_key", ApnSettings.MVNO_NONE);
        }
        String stringExtra = getIntent().getStringExtra(ExtraKey.TAG.a);
        if (((UserManager) getSystemService("user")).hasUserRestriction("no_config_vpn")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.vpn_settings_not_available);
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mCancelable = true;
            final int i = 0;
            alertParams.mOnCancelListener =
                    new DialogInterface.OnCancelListener(
                            this) { // from class:
                                    // com.samsung.android.settings.vpn.bixby.RoutineConfigActivity.2
                        public final /* synthetic */ RoutineConfigActivity this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnCancelListener
                        public final void onCancel(DialogInterface dialogInterface) {
                            switch (i) {
                                case 0:
                                    this.this$0.finish();
                                    break;
                                case 1:
                                    this.this$0.finish();
                                    break;
                                default:
                                    this.this$0.finish();
                                    break;
                            }
                        }
                    };
            final int i2 = 0;
            builder.setPositiveButton(
                    R.string.common_ok,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.vpn.bixby.RoutineConfigActivity.1
                        public final /* synthetic */ RoutineConfigActivity this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            switch (i2) {
                                case 0:
                                    this.this$0.finish();
                                    break;
                                case 1:
                                    RoutineConfigActivity routineConfigActivity = this.this$0;
                                    Intent intent = new Intent("android.settings.VPN_SETTINGS");
                                    intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                                    routineConfigActivity.startActivity(intent);
                                    this.this$0.finish();
                                    break;
                                case 2:
                                    this.this$0.finish();
                                    break;
                                default:
                                    this.this$0.finish();
                                    break;
                            }
                        }
                    });
            builder.show();
            return;
        }
        final ArrayList newArrayList = Lists.newArrayList();
        boolean z = false;
        for (String str : LegacyVpnProfileStore.list("VPN_")) {
            VpnProfile decode = VpnProfile.decode(str, LegacyVpnProfileStore.get("VPN_" + str));
            if (decode != null) {
                newArrayList.add(decode);
            }
        }
        newArrayList.sort(new VpnHelper.AnonymousClass1());
        Log.d("Vpn.RoutineActivity", "onCreate: loadVpnProfiles: size=" + newArrayList.size());
        if (newArrayList.size() < 1) {
            int i3 =
                    "connect_vpn".equals(stringExtra)
                            ? R.string.vpn_routine_dialog_message_add_profile_connect
                            : R.string.vpn_routine_dialog_message_add_profile_disconnect;
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setMessage(i3);
            AlertController.AlertParams alertParams2 = builder2.P;
            alertParams2.mCancelable = true;
            final int i4 = 1;
            alertParams2.mOnCancelListener =
                    new DialogInterface.OnCancelListener(
                            this) { // from class:
                                    // com.samsung.android.settings.vpn.bixby.RoutineConfigActivity.2
                        public final /* synthetic */ RoutineConfigActivity this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnCancelListener
                        public final void onCancel(DialogInterface dialogInterface) {
                            switch (i4) {
                                case 0:
                                    this.this$0.finish();
                                    break;
                                case 1:
                                    this.this$0.finish();
                                    break;
                                default:
                                    this.this$0.finish();
                                    break;
                            }
                        }
                    };
            final int i5 = 2;
            builder2.setNegativeButton(
                    R.string.common_cancel,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.vpn.bixby.RoutineConfigActivity.1
                        public final /* synthetic */ RoutineConfigActivity this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i32) {
                            switch (i5) {
                                case 0:
                                    this.this$0.finish();
                                    break;
                                case 1:
                                    RoutineConfigActivity routineConfigActivity = this.this$0;
                                    Intent intent = new Intent("android.settings.VPN_SETTINGS");
                                    intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                                    routineConfigActivity.startActivity(intent);
                                    this.this$0.finish();
                                    break;
                                case 2:
                                    this.this$0.finish();
                                    break;
                                default:
                                    this.this$0.finish();
                                    break;
                            }
                        }
                    });
            final int i6 = 1;
            builder2.setPositiveButton(
                    R.string.settings_button,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.vpn.bixby.RoutineConfigActivity.1
                        public final /* synthetic */ RoutineConfigActivity this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i32) {
                            switch (i6) {
                                case 0:
                                    this.this$0.finish();
                                    break;
                                case 1:
                                    RoutineConfigActivity routineConfigActivity = this.this$0;
                                    Intent intent = new Intent("android.settings.VPN_SETTINGS");
                                    intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                                    routineConfigActivity.startActivity(intent);
                                    this.this$0.finish();
                                    break;
                                case 2:
                                    this.this$0.finish();
                                    break;
                                default:
                                    this.this$0.finish();
                                    break;
                            }
                        }
                    });
            builder2.show();
            return;
        }
        int size = newArrayList.size();
        String[] strArr = new String[size];
        for (int i7 = 0; i7 < newArrayList.size(); i7++) {
            strArr[i7] = ((VpnProfile) newArrayList.get(i7)).name;
        }
        Log.d("Vpn.RoutineActivity", "showChooseVpnProfileDialog: list=" + size);
        int i8 =
                "connect_vpn".equals(stringExtra)
                        ? R.string.vpn_routine_label_connect
                        : R.string.vpn_routine_label_disconnect;
        View inflate =
                getLayoutInflater().inflate(R.layout.vpn_routine_dialog_profiles, (ViewGroup) null);
        final ListView listView =
                (ListView) inflate.findViewById(R.id.vpn_routine_dialog_profiles_lv);
        listView.setAdapter(
                (ListAdapter)
                        new ArrayAdapter(this, R.layout.vpn_routine_dialog_profile_item, strArr));
        listView.setChoiceMode(1);
        if (!this.mSelectedProfileKey.isEmpty()) {
            boolean z2 = false;
            for (int i9 = 0; i9 < newArrayList.size(); i9++) {
                if (((VpnProfile) newArrayList.get(i9)).key.equals(this.mSelectedProfileKey)) {
                    listView.setItemChecked(i9, true);
                    z2 = true;
                }
            }
            z = z2;
        }
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        builder3.setTitle(i8);
        builder3.setView(inflate);
        AlertController.AlertParams alertParams3 = builder3.P;
        alertParams3.mCancelable = true;
        final int i10 = 2;
        alertParams3.mOnCancelListener =
                new DialogInterface.OnCancelListener(
                        this) { // from class:
                                // com.samsung.android.settings.vpn.bixby.RoutineConfigActivity.2
                    public final /* synthetic */ RoutineConfigActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        switch (i10) {
                            case 0:
                                this.this$0.finish();
                                break;
                            case 1:
                                this.this$0.finish();
                                break;
                            default:
                                this.this$0.finish();
                                break;
                        }
                    }
                };
        final int i11 = 3;
        builder3.setNegativeButton(
                R.string.common_cancel,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.vpn.bixby.RoutineConfigActivity.1
                    public final /* synthetic */ RoutineConfigActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i32) {
                        switch (i11) {
                            case 0:
                                this.this$0.finish();
                                break;
                            case 1:
                                RoutineConfigActivity routineConfigActivity = this.this$0;
                                Intent intent = new Intent("android.settings.VPN_SETTINGS");
                                intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                                routineConfigActivity.startActivity(intent);
                                this.this$0.finish();
                                break;
                            case 2:
                                this.this$0.finish();
                                break;
                            default:
                                this.this$0.finish();
                                break;
                        }
                    }
                });
        builder3.setPositiveButton(
                R.string.common_done,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.vpn.bixby.RoutineConfigActivity.6
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i12) {
                        VpnProfile vpnProfile =
                                (VpnProfile) newArrayList.get(listView.getCheckedItemPosition());
                        Log.d(
                                "Vpn.RoutineActivity",
                                "showChooseVpnProfileDialog: selected profile:" + vpnProfile.name);
                        HashMap hashMap = new HashMap();
                        String str2 = vpnProfile.key;
                        hashMap.put(
                                "key_vpn_param",
                                new ParameterValues.ParameterValue(
                                        str2 == null ? null : "0_".concat(str2)));
                        hashMap.put(
                                "key_vpn_label_param",
                                new ParameterValues.ParameterValue(vpnProfile.name));
                        RoutineConfigActivity routineConfigActivity = RoutineConfigActivity.this;
                        Intent intent = new Intent();
                        HashMap hashMap2 = new HashMap();
                        hashMap.entrySet()
                                .forEach(new ParameterValues$$ExternalSyntheticLambda0(hashMap2));
                        intent.putExtra("intent_params", new JSONObject(hashMap2).toString());
                        routineConfigActivity.setResult(-1, intent);
                        RoutineConfigActivity.this.finish();
                    }
                });
        final AlertDialog create = builder3.create();
        listView.setOnItemClickListener(
                new AdapterView
                        .OnItemClickListener() { // from class:
                                                 // com.samsung.android.settings.vpn.bixby.RoutineConfigActivity.9
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(
                            AdapterView adapterView, View view, int i12, long j) {
                        create.getButton(-1).setEnabled(true);
                        RoutineConfigActivity.this.mSelectedProfileKey =
                                ((VpnProfile) newArrayList.get(i12)).key;
                    }
                });
        create.show();
        create.getButton(-1).setEnabled(z);
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mSelectedProfileKey.isEmpty()) {
            return;
        }
        bundle.putString("selected_profile_key", this.mSelectedProfileKey);
    }
}
