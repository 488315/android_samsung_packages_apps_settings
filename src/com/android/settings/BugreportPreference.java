package com.android.settings;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.CustomDialogPreferenceCompat;

import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class BugreportPreference extends CustomDialogPreferenceCompat {
    public TextView mFullSummary;
    public CheckedTextView mFullTitle;
    public TextView mInteractiveSummary;
    public CheckedTextView mInteractiveTitle;

    public BugreportPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            Context context = getContext();
            if (this.mFullTitle.isChecked()) {
                Log.v("BugreportPreference", "Taking full bugreport right away");
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                featureFactoryImpl
                        .getMetricsFeatureProvider()
                        .action(context, IKnoxCustomManager.Stub.TRANSACTION_readFile, new Pair[0]);
                try {
                    ActivityManager.getService().requestFullBugReport();
                    return;
                } catch (RemoteException e) {
                    Log.e("BugreportPreference", "error taking bugreport (bugreportType=Full)", e);
                    return;
                }
            }
            Log.v("BugreportPreference", "Taking interactive bugreport right away");
            FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
            if (featureFactoryImpl2 == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl2
                    .getMetricsFeatureProvider()
                    .action(context, IKnoxCustomManager.Stub.TRANSACTION_getTcpDump, new Pair[0]);
            try {
                ActivityManager.getService().requestInteractiveBugReport();
            } catch (RemoteException e2) {
                Log.e(
                        "BugreportPreference",
                        "error taking bugreport (bugreportType=Interactive)",
                        e2);
            }
        }
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {
        View inflate = View.inflate(getContext(), R.layout.bugreport_options_dialog, null);
        this.mInteractiveTitle =
                (CheckedTextView) inflate.findViewById(R.id.bugreport_option_interactive_title);
        this.mInteractiveSummary =
                (TextView) inflate.findViewById(R.id.bugreport_option_interactive_summary);
        this.mFullTitle = (CheckedTextView) inflate.findViewById(R.id.bugreport_option_full_title);
        this.mFullSummary = (TextView) inflate.findViewById(R.id.bugreport_option_full_summary);
        View.OnClickListener onClickListener2 =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.BugreportPreference.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BugreportPreference bugreportPreference = BugreportPreference.this;
                        if (view == bugreportPreference.mFullTitle
                                || view == bugreportPreference.mFullSummary) {
                            bugreportPreference.mInteractiveTitle.setChecked(false);
                            BugreportPreference.this.mFullTitle.setChecked(true);
                        }
                        BugreportPreference bugreportPreference2 = BugreportPreference.this;
                        CheckedTextView checkedTextView = bugreportPreference2.mInteractiveTitle;
                        if (view == checkedTextView
                                || view == bugreportPreference2.mInteractiveSummary) {
                            checkedTextView.setChecked(true);
                            BugreportPreference.this.mFullTitle.setChecked(false);
                        }
                    }
                };
        this.mInteractiveTitle.setOnClickListener(onClickListener2);
        this.mFullTitle.setOnClickListener(onClickListener2);
        this.mInteractiveSummary.setOnClickListener(onClickListener2);
        this.mFullSummary.setOnClickListener(onClickListener2);
        builder.setPositiveButton(17042557, onClickListener);
        builder.setView(inflate);
    }
}
