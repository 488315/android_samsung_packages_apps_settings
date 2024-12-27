package com.samsung.android.settings.wifi.mobileap.routine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.R;

import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.ParameterValues$$ExternalSyntheticLambda0;

import org.json.JSONObject;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConditionActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("WifiApConditionActivity", "onCreate()");
        getApplicationContext();
        View inflate =
                getLayoutInflater()
                        .inflate(R.layout.wifi_ap_routine_condition_config, (ViewGroup) null);
        final RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.radio_group_config);
        radioGroup.check(
                ParameterValues.fromIntent(getIntent())
                                .getBoolean("toggle_config_key", Boolean.TRUE)
                                .booleanValue()
                        ? R.id.on_btn
                        : R.id.off_btn);
        new AlertDialog.Builder(this)
                .setTitle(R.string.mobileap)
                .setView(inflate)
                .setPositiveButton(
                        R.string.common_done,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.wifi.mobileap.routine.WifiApConditionActivity$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                WifiApConditionActivity wifiApConditionActivity =
                                        WifiApConditionActivity.this;
                                RadioGroup radioGroup2 = radioGroup;
                                int i2 = WifiApConditionActivity.$r8$clinit;
                                wifiApConditionActivity.getClass();
                                int checkedRadioButtonId = radioGroup2.getCheckedRadioButtonId();
                                HashMap hashMap = new HashMap();
                                hashMap.put(
                                        "toggle_config_key",
                                        new ParameterValues.ParameterValue(
                                                Boolean.valueOf(
                                                        checkedRadioButtonId == R.id.on_btn)));
                                Intent intent = new Intent();
                                HashMap hashMap2 = new HashMap();
                                hashMap.entrySet()
                                        .forEach(
                                                new ParameterValues$$ExternalSyntheticLambda0(
                                                        hashMap2));
                                intent.putExtra(
                                        "intent_params", new JSONObject(hashMap2).toString());
                                wifiApConditionActivity.setResult(-1, intent);
                            }
                        })
                .setNegativeButton(
                        R.string.dlg_cancel,
                        new WifiApConditionActivity$$ExternalSyntheticLambda1())
                .setOnDismissListener(
                        new DialogInterface
                                .OnDismissListener() { // from class:
                                                       // com.samsung.android.settings.wifi.mobileap.routine.WifiApConditionActivity$$ExternalSyntheticLambda2
                            @Override // android.content.DialogInterface.OnDismissListener
                            public final void onDismiss(DialogInterface dialogInterface) {
                                WifiApConditionActivity wifiApConditionActivity =
                                        WifiApConditionActivity.this;
                                int i = WifiApConditionActivity.$r8$clinit;
                                wifiApConditionActivity.finish();
                            }
                        })
                .create()
                .show();
    }
}
