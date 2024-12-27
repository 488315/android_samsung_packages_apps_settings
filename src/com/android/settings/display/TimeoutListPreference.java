package com.android.settings.display;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.RestrictedListPreference;
import com.android.settingslib.RestrictedLockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TimeoutListPreference extends RestrictedListPreference {
    public RestrictedLockUtils.EnforcedAdmin mAdmin;
    public final CharSequence[] mInitialEntries;
    public final CharSequence[] mInitialValues;

    public TimeoutListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInitialEntries = this.mEntries;
        this.mInitialValues = this.mEntryValues;
    }

    @Override // com.android.settings.CustomListPreference
    public final void onDialogCreated(AlertDialog alertDialog) {
        alertDialog.create();
        if (this.mAdmin != null) {
            alertDialog
                    .findViewById(R.id.admin_disabled_other_options)
                    .findViewById(R.id.admin_more_details_link)
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.android.settings.display.TimeoutListPreference.1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                            TimeoutListPreference.this.getContext(),
                                            TimeoutListPreference.this.mAdmin);
                                }
                            });
        }
    }

    @Override // com.android.settings.RestrictedListPreference,
              // com.android.settings.CustomListPreference
    public final void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {
        super.onPrepareDialogBuilder(builder, onClickListener);
        if (this.mAdmin == null) {
            builder.setView((View) null);
            return;
        }
        LayoutInflater layoutInflater =
                (LayoutInflater) getContext().getSystemService(LayoutInflater.class);
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getContext().getSystemService(DevicePolicyManager.class);
        View inflate =
                layoutInflater.inflate(
                        R.layout.admin_disabled_other_options_footer, (ViewGroup) null);
        builder.setView(inflate);
        TextView textView = (TextView) inflate.findViewById(R.id.admin_disabled_other_options_text);
        String string =
                devicePolicyManager
                        .getResources()
                        .getString(
                                "Settings.OTHER_OPTIONS_DISABLED_BY_ADMIN",
                                new TimeoutListPreference$$ExternalSyntheticLambda0());
        if (string != null) {
            textView.setText(string);
        }
    }
}
