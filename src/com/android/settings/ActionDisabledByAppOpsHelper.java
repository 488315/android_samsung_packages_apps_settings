package com.android.settings;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final class ActionDisabledByAppOpsHelper {
    public final Activity mActivity;
    public final ViewGroup mDialogView;

    public ActionDisabledByAppOpsHelper(Activity activity) {
        this.mActivity = activity;
        this.mDialogView =
                (ViewGroup)
                        LayoutInflater.from(activity)
                                .inflate(R.layout.support_details_dialog, (ViewGroup) null);
    }

    public void setSupportTitle(View view) {
        TextView textView = (TextView) view.findViewById(R.id.admin_support_dialog_title);
        if (textView == null) {
            return;
        }
        textView.setText(R.string.blocked_by_restricted_settings_title);
    }
}
