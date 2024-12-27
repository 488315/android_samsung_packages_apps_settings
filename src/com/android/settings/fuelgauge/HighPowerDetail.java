package com.android.settings.fuelgauge;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.fuelgauge.PowerAllowlistBackend;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class HighPowerDetail extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener, View.OnClickListener {
    PowerAllowlistBackend mBackend;
    BatteryUtils mBatteryUtils;
    boolean mIsEnabled;
    public CharSequence mLabel;
    public Checkable mOptionOff;
    public Checkable mOptionOn;
    String mPackageName;
    int mPackageUid;

    public static CharSequence getSummary(
            Context context, PowerAllowlistBackend powerAllowlistBackend, String str, int i) {
        return context.getString(
                (powerAllowlistBackend.isSysAllowlisted(str)
                                || powerAllowlistBackend.isDefaultActiveApp(i, str))
                        ? R.string.high_power_system
                        : powerAllowlistBackend.isAllowlisted(i, str)
                                ? R.string.high_power_on
                                : R.string.high_power_off);
    }

    public static void logSpecialPermissionChange(boolean z, String str, Context context) {
        int i = z ? 765 : 764;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(context, i, str);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 540;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view == this.mOptionOn) {
            this.mIsEnabled = true;
            updateViews();
        } else if (view == this.mOptionOff) {
            this.mIsEnabled = false;
            updateViews();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0057, code lost:

       if (r3.mBackend.isAllowlisted(r3.mPackageUid, r3.mPackageName) != false) goto L10;
    */
    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r4) {
        /*
            r3 = this;
            super.onCreate(r4)
            android.content.Context r4 = r3.getContext()
            com.android.settings.fuelgauge.BatteryUtils r0 = com.android.settings.fuelgauge.BatteryUtils.getInstance(r4)
            r3.mBatteryUtils = r0
            com.android.settingslib.fuelgauge.PowerAllowlistBackend r0 = com.android.settingslib.fuelgauge.PowerAllowlistBackend.getInstance(r4)
            r3.mBackend = r0
            android.os.Bundle r0 = r3.getArguments()
            java.lang.String r1 = "package"
            java.lang.String r0 = r0.getString(r1)
            r3.mPackageName = r0
            android.os.Bundle r0 = r3.getArguments()
            java.lang.String r1 = "uid"
            int r0 = r0.getInt(r1)
            r3.mPackageUid = r0
            android.content.pm.PackageManager r4 = r4.getPackageManager()
            r0 = 0
            java.lang.String r1 = r3.mPackageName     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L3d
            android.content.pm.ApplicationInfo r1 = r4.getApplicationInfo(r1, r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L3d
            java.lang.CharSequence r4 = r1.loadLabel(r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L3d
            r3.mLabel = r4     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L3d
            goto L41
        L3d:
            java.lang.String r4 = r3.mPackageName
            r3.mLabel = r4
        L41:
            android.os.Bundle r4 = r3.getArguments()
            java.lang.String r1 = "default_on"
            boolean r4 = r4.getBoolean(r1)
            if (r4 != 0) goto L59
            com.android.settingslib.fuelgauge.PowerAllowlistBackend r4 = r3.mBackend
            java.lang.String r1 = r3.mPackageName
            int r2 = r3.mPackageUid
            boolean r4 = r4.isAllowlisted(r2, r1)
            if (r4 == 0) goto L5a
        L59:
            r0 = 1
        L5a:
            r3.mIsEnabled = r0
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.fuelgauge.HighPowerDetail.onCreate(android.os.Bundle):void");
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.P.mTitle = this.mLabel;
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setView(R.layout.ignore_optimizations_content);
        if (!this.mBackend.isSysAllowlisted(this.mPackageName)) {
            builder.setPositiveButton(R.string.done, this);
        }
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        Fragment targetFragment = getTargetFragment();
        if (targetFragment == null || targetFragment.getActivity() == null) {
            return;
        }
        targetFragment.onActivityResult(getTargetRequestCode(), 0, null);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mOptionOn = setup(this.mDialog.findViewById(R.id.ignore_on), true);
        this.mOptionOff = setup(this.mDialog.findViewById(R.id.ignore_off), false);
        updateViews();
    }

    public final void setPowerAllowlisted(FragmentActivity fragmentActivity, String str, int i) {
        if (this.mBackend == null) {
            this.mBackend = PowerAllowlistBackend.getInstance(fragmentActivity);
        }
        boolean isAllowlisted = this.mBackend.isAllowlisted(i, str);
        logSpecialPermissionChange(!isAllowlisted, this.mPackageName, fragmentActivity);
        if (isAllowlisted) {
            this.mBackend.removeApp(-1, str);
            return;
        }
        this.mBackend.addApp(-1, str);
        if (this.mBatteryUtils == null) {
            BatteryUtils batteryUtils = BatteryUtils.getInstance(fragmentActivity);
            this.mBatteryUtils = batteryUtils;
            batteryUtils.setForceAppStandby(this.mPackageUid, 0, 9, this.mPackageName);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Checkable setup(View view, boolean z) {
        ((TextView) view.findViewById(android.R.id.title))
                .setText(z ? R.string.ignore_optimizations_on : R.string.ignore_optimizations_off);
        ((TextView) view.findViewById(android.R.id.summary))
                .setText(
                        z
                                ? R.string.ignore_optimizations_on_desc
                                : R.string.ignore_optimizations_off_desc);
        view.setClickable(true);
        view.setOnClickListener(this);
        if (!z && this.mBackend.isSysAllowlisted(this.mPackageName)) {
            view.setEnabled(false);
        }
        return (Checkable) view;
    }

    public final void updateViews() {
        this.mOptionOn.setChecked(this.mIsEnabled);
        this.mOptionOff.setChecked(!this.mIsEnabled);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            boolean z = this.mIsEnabled;
            if (z != this.mBackend.isAllowlisted(this.mPackageUid, this.mPackageName)) {
                logSpecialPermissionChange(z, this.mPackageName, getContext());
                if (z) {
                    BatteryUtils batteryUtils = this.mBatteryUtils;
                    if (batteryUtils != null) {
                        batteryUtils.setForceAppStandby(this.mPackageUid, 0, 9, this.mPackageName);
                    }
                    this.mBackend.addApp(this.mPackageUid, this.mPackageName);
                    return;
                }
                this.mBackend.removeApp(this.mPackageUid, this.mPackageName);
            }
        }
    }
}
