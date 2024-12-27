package com.android.settings.applications.appinfo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.overlay.FeatureFactoryImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InstantAppButtonDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public String mPackageName;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1986;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Context context = getContext();
        PackageManager packageManager = context.getPackageManager();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(context, 923, this.mPackageName);
        packageManager.deletePackageAsUser(this.mPackageName, null, 0, UserHandle.myUserId());
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        this.mPackageName = getArguments().getString("packageName");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton(R.string.clear_instant_app_data, this);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setTitle(R.string.clear_instant_app_data);
        builder.setMessage(R.string.clear_instant_app_confirmation);
        return builder.create();
    }
}
