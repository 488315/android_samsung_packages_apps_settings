package com.android.settings.notification.zen;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.SecPreferenceUtils;

import com.android.settingslib.CustomDialogPreferenceCompat;
import com.android.settingslib.notification.ZenDurationDialog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenDurationDialogPreference extends CustomDialogPreferenceCompat {
    public ZenDurationDialogPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setSingleLineTitle(false);
        SecPreferenceUtils.applySummaryColor(this, true);
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {
        new ZenDurationDialog(getContext()).setupDialog(builder);
    }

    public ZenDurationDialogPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setSingleLineTitle(false);
        SecPreferenceUtils.applySummaryColor(this, true);
    }

    public ZenDurationDialogPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setSingleLineTitle(false);
        SecPreferenceUtils.applySummaryColor(this, true);
    }
}
