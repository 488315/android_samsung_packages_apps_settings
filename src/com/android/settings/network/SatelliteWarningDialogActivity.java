package com.android.settings.network;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/network/SatelliteWarningDialogActivity;",
            "Landroidx/fragment/app/FragmentActivity;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class SatelliteWarningDialogActivity extends FragmentActivity {
    public int warningType = -1;

    public final String getTypeString(int i) {
        if (i == 0) {
            String string = getString(R.string.wifi);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            return string;
        }
        if (i == 1) {
            String string2 = getString(R.string.bluetooth);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            return string2;
        }
        if (i != 2) {
            return ApnSettings.MVNO_NONE;
        }
        String string3 = getString(R.string.airplane_mode);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        return string3;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        int intExtra = getIntent().getIntExtra("extra_type_of_satellite_warning_dialog", -1);
        this.warningType = intExtra;
        if (intExtra == -1) {
            finish();
        }
        super.onCreate(bundle);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String string = getString(R.string.satellite_warning_dialog_title);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String format =
                String.format(
                        string, Arrays.copyOf(new Object[] {getTypeString(this.warningType)}, 1));
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = format;
        String string2 = getString(R.string.satellite_warning_dialog_content);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        alertParams.mMessage =
                String.format(
                        string2, Arrays.copyOf(new Object[] {getTypeString(this.warningType)}, 1));
        builder.setPositiveButton(
                R.string.okay,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.network.SatelliteWarningDialogActivity$onCreate$builder$1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        SatelliteWarningDialogActivity.this.finish();
                    }
                });
        builder.create().show();
    }
}
