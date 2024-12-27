package com.android.settings.notification.zen;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenDeleteRuleDialog extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1266;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Bundle arguments = getArguments();
        String string = arguments.getString("zen_rule_name");
        String string2 = arguments.getString("zen_rule_id");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.P.mMessage = getString(R.string.zen_mode_delete_rule_confirmation, string);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setPositiveButton(
                R.string.zen_mode_delete_rule_button,
                new DialogInterface.OnClickListener(
                        arguments,
                        string2) { // from class:
                                   // com.android.settings.notification.zen.ZenDeleteRuleDialog.1
                    public final /* synthetic */ Bundle val$arguments;

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        if (this.val$arguments != null) {
                            throw null;
                        }
                    }
                });
        AlertDialog create = builder.create();
        View findViewById = create.findViewById(android.R.id.message);
        if (findViewById != null) {
            findViewById.setTextDirection(5);
        }
        return create;
    }
}
