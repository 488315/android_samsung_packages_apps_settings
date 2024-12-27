package com.android.settings.notification.zen;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenRuleNameDialog extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1269;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006f  */
    @Override // androidx.fragment.app.DialogFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.Dialog onCreateDialog(android.os.Bundle r9) {
        /*
            r8 = this;
            android.os.Bundle r9 = r8.getArguments()
            java.lang.String r0 = "extra_zen_condition_id"
            android.os.Parcelable r0 = r9.getParcelable(r0)
            android.net.Uri r0 = (android.net.Uri) r0
            java.lang.String r1 = "zen_rule_name"
            java.lang.String r9 = r9.getString(r1)
            r1 = 0
            r2 = 1
            if (r9 != 0) goto L18
            r3 = r2
            goto L19
        L18:
            r3 = r1
        L19:
            android.content.Context r4 = r8.getContext()
            android.view.LayoutInflater r5 = android.view.LayoutInflater.from(r4)
            r6 = 2131561521(0x7f0d0c31, float:1.8748445E38)
            r7 = 0
            android.view.View r1 = r5.inflate(r6, r7, r1)
            r5 = 2131366335(0x7f0a11bf, float:1.835256E38)
            android.view.View r5 = r1.findViewById(r5)
            android.widget.EditText r5 = (android.widget.EditText) r5
            if (r3 != 0) goto L42
            r5.setText(r9)
            android.text.Editable r6 = r5.getText()
            int r6 = r6.length()
            r5.setSelection(r6)
        L42:
            r5.setSelectAllOnFocus(r2)
            androidx.appcompat.app.AlertDialog$Builder r2 = new androidx.appcompat.app.AlertDialog$Builder
            r2.<init>(r4)
            boolean r4 = android.service.notification.ZenModeConfig.isValidEventConditionId(r0)
            boolean r0 = android.service.notification.ZenModeConfig.isValidScheduleConditionId(r0)
            if (r3 == 0) goto L60
            if (r4 == 0) goto L5a
            r0 = 2132031451(0x7f1437db, float:1.9701576E38)
            goto L63
        L5a:
            if (r0 == 0) goto L60
            r0 = 2132031453(0x7f1437dd, float:1.970158E38)
            goto L63
        L60:
            r0 = 2132031610(0x7f14387a, float:1.9701899E38)
        L63:
            r2.setTitle(r0)
            r2.setView(r1)
            if (r3 == 0) goto L6f
            r0 = 2132031450(0x7f1437da, float:1.9701574E38)
            goto L72
        L6f:
            r0 = 2132024099(0x7f141b23, float:1.9686665E38)
        L72:
            com.android.settings.notification.zen.ZenRuleNameDialog$1 r1 = new com.android.settings.notification.zen.ZenRuleNameDialog$1
            r1.<init>()
            r2.setPositiveButton(r0, r1)
            r8 = 2132019384(0x7f1408b8, float:1.9677101E38)
            r2.setNegativeButton(r8, r7)
            androidx.appcompat.app.AlertDialog r8 = r2.create()
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.notification.zen.ZenRuleNameDialog.onCreateDialog(android.os.Bundle):android.app.Dialog");
    }
}
