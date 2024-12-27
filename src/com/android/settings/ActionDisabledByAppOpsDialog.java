package com.android.settings;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.settingslib.HelpUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class ActionDisabledByAppOpsDialog extends Activity
        implements DialogInterface.OnDismissListener {
    public ActionDisabledByAppOpsHelper mDialogHelper;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final ActionDisabledByAppOpsHelper actionDisabledByAppOpsHelper =
                new ActionDisabledByAppOpsHelper(this);
        this.mDialogHelper = actionDisabledByAppOpsHelper;
        final String string = getString(R.string.help_url_action_disabled_by_restricted_settings);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.okay, (DialogInterface.OnClickListener) null);
        builder.setView(actionDisabledByAppOpsHelper.mDialogView);
        if (!TextUtils.isEmpty(string)) {
            builder.setNeutralButton(
                    R.string.learn_more,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.ActionDisabledByAppOpsHelper$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            ActionDisabledByAppOpsHelper actionDisabledByAppOpsHelper2 =
                                    ActionDisabledByAppOpsHelper.this;
                            String str = string;
                            Activity activity = actionDisabledByAppOpsHelper2.mActivity;
                            Intent helpIntent =
                                    HelpUtils.getHelpIntent(
                                            activity, str, activity.getClass().getName());
                            if (helpIntent != null) {
                                activity.startActivity(helpIntent);
                            }
                        }
                    });
        }
        ViewGroup viewGroup = actionDisabledByAppOpsHelper.mDialogView;
        actionDisabledByAppOpsHelper.setSupportTitle(viewGroup);
        ((TextView) viewGroup.findViewById(R.id.admin_support_msg))
                .setText(R.string.blocked_by_restricted_settings_content);
        builder.P.mOnDismissListener = this;
        builder.show();
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
        ((AppOpsManager) getSystemService(AppOpsManager.class))
                .setMode(119, intent.getIntExtra("android.intent.extra.UID", -1), stringExtra, 1);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        finish();
    }

    @Override // android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ActionDisabledByAppOpsHelper actionDisabledByAppOpsHelper = this.mDialogHelper;
        ViewGroup viewGroup = actionDisabledByAppOpsHelper.mDialogView;
        actionDisabledByAppOpsHelper.setSupportTitle(viewGroup);
        ((TextView) viewGroup.findViewById(R.id.admin_support_msg))
                .setText(R.string.blocked_by_restricted_settings_content);
        Intent intent2 = getIntent();
        String stringExtra = intent2.getStringExtra("android.intent.extra.PACKAGE_NAME");
        ((AppOpsManager) getSystemService(AppOpsManager.class))
                .setMode(119, intent2.getIntExtra("android.intent.extra.UID", -1), stringExtra, 1);
    }
}
