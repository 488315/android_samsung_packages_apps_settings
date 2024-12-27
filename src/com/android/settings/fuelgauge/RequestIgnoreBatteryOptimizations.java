package com.android.settings.fuelgauge;

import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerWhitelistManager;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RequestIgnoreBatteryOptimizations extends AlertActivity
        implements DialogInterface.OnClickListener {
    public String mPackageName;
    public PowerWhitelistManager mPowerWhitelistManager;

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
        this.mPowerWhitelistManager.addToWhitelist(this.mPackageName);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mPowerWhitelistManager =
                (PowerWhitelistManager) getSystemService(PowerWhitelistManager.class);
        Uri data = getIntent().getData();
        if (data == null) {
            Objects.toString(getIntent());
            finish();
            return;
        }
        String schemeSpecificPart = data.getSchemeSpecificPart();
        this.mPackageName = schemeSpecificPart;
        if (schemeSpecificPart == null) {
            Objects.toString(getIntent());
            finish();
            return;
        }
        if (((PowerManager) getSystemService(PowerManager.class))
                .isIgnoringBatteryOptimizations(this.mPackageName)) {
            finish();
            return;
        }
        if (getPackageManager()
                        .checkPermission(
                                "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS",
                                this.mPackageName)
                != 0) {
            finish();
            return;
        }
        try {
            ApplicationInfo applicationInfo =
                    getPackageManager().getApplicationInfo(this.mPackageName, 0);
            AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
            CharSequence loadSafeLabel =
                    applicationInfo.loadSafeLabel(getPackageManager(), 1000.0f, 5);
            alertParams.mTitle = getText(R.string.high_power_prompt_title);
            alertParams.mMessage =
                    getString(R.string.high_power_prompt_body, new Object[] {loadSafeLabel});
            alertParams.mPositiveButtonText = getText(R.string.allow);
            alertParams.mNegativeButtonText = getText(R.string.deny);
            alertParams.mPositiveButtonListener = this;
            alertParams.mNegativeButtonListener = this;
            setupAlert();
        } catch (PackageManager.NameNotFoundException unused) {
            finish();
        }
    }
}
