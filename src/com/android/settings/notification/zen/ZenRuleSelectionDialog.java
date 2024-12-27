package com.android.settings.notification.zen;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import java.text.Collator;
import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenRuleSelectionDialog extends InstrumentedDialogFragment {
    public static final boolean DEBUG = ZenModeSettingsBase.DEBUG;
    public static final AnonymousClass3 RULE_TYPE_COMPARATOR =
            new Comparator() { // from class:
                               // com.android.settings.notification.zen.ZenRuleSelectionDialog.3
                public final Collator mCollator = Collator.getInstance();

                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    ZenRuleInfo zenRuleInfo = (ZenRuleInfo) obj;
                    ZenRuleInfo zenRuleInfo2 = (ZenRuleInfo) obj2;
                    int compare =
                            this.mCollator.compare(
                                    zenRuleInfo.packageLabel, zenRuleInfo2.packageLabel);
                    return compare != 0
                            ? compare
                            : this.mCollator.compare(zenRuleInfo.title, zenRuleInfo2.title);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenRuleSelectionDialog$2, reason: invalid class name */
    public abstract class AnonymousClass2 {
        public final /* synthetic */ ZenRuleSelectionDialog this$0;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1270;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        View inflate =
                LayoutInflater.from(getContext())
                        .inflate(R.layout.zen_rule_type_selection, (ViewGroup) null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.zen_mode_choose_rule_type);
        builder.setView(inflate);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder.create();
    }
}
