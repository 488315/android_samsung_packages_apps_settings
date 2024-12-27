package com.android.settings.homepage.contextualcards;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ContextualCardFeedbackDialog extends AlertActivity
        implements DialogInterface.OnClickListener {
    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        String stringExtra = getIntent().getStringExtra("card_name");
        Intent intent =
                new Intent(
                        "android.intent.action.SENDTO",
                        Uri.parse("mailto:" + getIntent().getStringExtra("feedback_email")));
        intent.putExtra(
                "android.intent.extra.SUBJECT",
                "Settings Contextual Card Feedback - " + stringExtra);
        intent.addFlags(268435456);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.e("CardFeedbackDialog", "Send feedback failed.", e);
        }
        finish();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mMessage = getText(R.string.contextual_card_feedback_confirm_message);
        alertParams.mPositiveButtonText = getText(R.string.contextual_card_feedback_send);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonText = getText(R.string.skip_label);
        setupAlert();
    }
}
