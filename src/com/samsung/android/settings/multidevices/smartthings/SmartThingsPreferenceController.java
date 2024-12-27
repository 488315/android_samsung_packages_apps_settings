package com.samsung.android.settings.multidevices.smartthings;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.SystemProperties;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SmartThingsPreferenceController extends BasePreferenceController {
    private static final String ST_PACKAGE_NAME = "com.samsung.android.oneconnect";
    private static final String TAG = "SmartThingsPreferenceController";

    public SmartThingsPreferenceController(Context context, String str) {
        super(context, str);
    }

    public static String getAppTitle(Context context) {
        return ("CN".equalsIgnoreCase(SystemProperties.get("ro.csc.countryiso_code"))
                        || "china".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code")))
                ? isSepVersionHigherThan15_1()
                        ? context.getString(R.string.smartthings_title)
                        : "Samsung Connect"
                : context.getString(R.string.smartthings_title);
    }

    private static int getSepVersion() {
        return Build.VERSION.SEM_PLATFORM_INT;
    }

    private boolean isPackageInstalled() {
        boolean z =
                this.mContext.getPackageManager().getLaunchIntentForPackage(ST_PACKAGE_NAME)
                        != null;
        SemLog.i(TAG, "isPackageInstalled, " + z);
        return z;
    }

    private static boolean isSepVersionHigherThan15_1() {
        int sepVersion = getSepVersion();
        if (sepVersion <= 0) {
            return false;
        }
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "SEPVersion =",
                        ".",
                        sepVersion / EnterpriseContainerConstants.SYSTEM_SIGNED_APP,
                        (sepVersion % EnterpriseContainerConstants.SYSTEM_SIGNED_APP) / 100,
                        " verString=");
        m.append(sepVersion);
        SemLog.i(TAG, m.toString());
        return sepVersion >= 150100;
    }

    private boolean isSupportSmartThings() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestInstallApp$0(DialogInterface dialogInterface, int i) {
        navigateToStore();
    }

    private void navigateToSmartThings() {
        SemLog.i(TAG, "navigateToSmartThings, ");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("scapp://launch/"));
        intent.putExtra("executor", "settings_connected_devices");
        intent.addFlags(872415232);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            SemLog.e(TAG, "navigateToSmartThings, " + e.getMessage());
            navigateToStore();
        }
    }

    private void navigateToStore() {
        SemLog.i(TAG, "navigateToStore, ");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("samsungapps://ProductDetail/com.samsung.android.oneconnect"));
        intent.addFlags(335544352);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            SemLog.e(TAG, "navigateToStore, " + e.getMessage());
        }
    }

    private void requestInstallApp() {
        AlertDialog.Builder title =
                new AlertDialog.Builder(this.mContext).setTitle(ApnSettings.MVNO_NONE);
        Context context = this.mContext;
        title.setMessage(
                        context.getString(
                                R.string.smartthings_dialog_message, getAppTitle(context)))
                .setPositiveButton(
                        this.mContext.getString(R.string.smartthings_dialog_download),
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.multidevices.smartthings.SmartThingsPreferenceController$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                SmartThingsPreferenceController.this.lambda$requestInstallApp$0(
                                        dialogInterface, i);
                            }
                        })
                .setNegativeButton(
                        this.mContext.getString(R.string.smartthings_dialog_cancel),
                        new SmartThingsPreferenceController$$ExternalSyntheticLambda1())
                .create()
                .show();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreference secPreference =
                (SecPreference) preferenceScreen.findPreference(getPreferenceKey());
        if (secPreference == null) {
            SemLog.w(TAG, "displayPreference, preference is null");
            return;
        }
        SemLog.i(TAG, "displayPreference, ");
        secPreference.setTitle(getAppTitle(this.mContext));
        Drawable drawable = this.mContext.getDrawable(R.drawable.ic_smartthings);
        drawable.setColorFilter(
                new PorterDuffColorFilter(
                        Color.parseColor(
                                this.mContext
                                        .getResources()
                                        .getString(R.color.sec_smartthings_icon_color)),
                        PorterDuff.Mode.SRC_ATOP));
        secPreference.setIcon(drawable);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !isSupportSmartThings() ? 3 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        SemLog.i(TAG, "handlePreferenceTreeClick, " + preference.getKey());
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        if (isPackageInstalled()) {
            navigateToSmartThings();
            return true;
        }
        requestInstallApp();
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
