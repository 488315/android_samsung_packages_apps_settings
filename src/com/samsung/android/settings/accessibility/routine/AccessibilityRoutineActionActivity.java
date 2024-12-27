package com.samsung.android.settings.accessibility.routine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.R;

import com.samsung.android.sdk.routines.v3.data.ParameterValues;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityRoutineActionActivity extends AppCompatActivity {
    public AlertDialog routineActionDialog;

    public abstract View buildContentView();

    public abstract int getTitleResId();

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getTitleResId());
        builder.setView(buildContentView());
        builder.setPositiveButton(
                R.string.common_done,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionActivity$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        AccessibilityRoutineActionActivity accessibilityRoutineActionActivity =
                                AccessibilityRoutineActionActivity.this;
                        if (i != -1) {
                            accessibilityRoutineActionActivity.getClass();
                            return;
                        }
                        ParameterValues sendParameterValues =
                                accessibilityRoutineActionActivity.sendParameterValues();
                        Intent intent = new Intent();
                        intent.putExtra("intent_params", sendParameterValues.toJsonString());
                        accessibilityRoutineActionActivity.setResult(-1, intent);
                    }
                });
        builder.setNegativeButton(R.string.common_cancel, (DialogInterface.OnClickListener) null);
        builder.P.mOnDismissListener =
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.accessibility.routine.AccessibilityRoutineActionActivity$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        AccessibilityRoutineActionActivity.this.finish();
                    }
                };
        this.routineActionDialog = builder.show();
    }

    public abstract ParameterValues sendParameterValues();
}
