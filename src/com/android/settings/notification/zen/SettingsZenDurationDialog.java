package com.android.settings.notification.zen;

import android.app.Dialog;
import android.os.Bundle;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.notification.ZenDurationDialog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SettingsZenDurationDialog extends InstrumentedDialogFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1341;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        return new ZenDurationDialog(getContext()).createDialog();
    }
}
