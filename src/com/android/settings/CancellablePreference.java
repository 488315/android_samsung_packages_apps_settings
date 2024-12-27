package com.android.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.applications.ProcessStatsDetail;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class CancellablePreference extends Preference implements View.OnClickListener {
    public boolean mCancellable;
    public ProcessStatsDetail.AnonymousClass1 mListener;

    public CancellablePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWidgetLayoutResource(R.layout.cancel_pref_widget);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.cancel);
        imageView.setVisibility(this.mCancellable ? 0 : 4);
        imageView.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        ProcessStatsDetail.AnonymousClass1 anonymousClass1 = this.mListener;
        if (anonymousClass1 != null) {
            final String packageName = anonymousClass1.val$service.getPackageName();
            final String className = anonymousClass1.val$service.getClassName();
            ProcessStatsDetail.AnonymousClass2 anonymousClass2 = ProcessStatsDetail.sEntryCompare;
            final ProcessStatsDetail processStatsDetail = ProcessStatsDetail.this;
            processStatsDetail.getClass();
            try {
                if ((processStatsDetail
                                        .getActivity()
                                        .getPackageManager()
                                        .getApplicationInfo(packageName, 0)
                                        .flags
                                & 1)
                        == 0) {
                    processStatsDetail
                            .getActivity()
                            .stopService(new Intent().setClassName(packageName, className));
                    processStatsDetail.updateRunningServices();
                    return;
                }
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(processStatsDetail.getActivity());
                builder.setTitle(R.string.runningservicedetails_stop_dlg_title);
                builder.setMessage(R.string.runningservicedetails_stop_dlg_text);
                builder.setPositiveButton(
                        R.string.dlg_ok,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.applications.ProcessStatsDetail.5
                            public final /* synthetic */ String val$name;
                            public final /* synthetic */ String val$pkg;

                            public AnonymousClass5(
                                    final String packageName2, final String className2) {
                                r2 = packageName2;
                                r3 = className2;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                ProcessStatsDetail processStatsDetail2 = ProcessStatsDetail.this;
                                String str = r2;
                                String str2 = r3;
                                AnonymousClass2 anonymousClass22 = ProcessStatsDetail.sEntryCompare;
                                processStatsDetail2
                                        .getActivity()
                                        .stopService(new Intent().setClassName(str, str2));
                                processStatsDetail2.updateRunningServices();
                            }
                        });
                builder.setNegativeButton(
                        R.string.dlg_cancel, (DialogInterface.OnClickListener) null);
                builder.show();
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("ProcessStatsDetail", "Can't find app " + packageName2, e);
            }
        }
    }
}
