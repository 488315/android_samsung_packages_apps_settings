package com.android.settings.development;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PageAgnosticWarningActivity extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AlertDialog create =
                new AlertDialog.Builder(this)
                        .setTitle(
                                Enable16kUtils.isUsing16kbPages()
                                        ? getString(R.string.page_agnostic_16k_pages_title)
                                        : getString(R.string.page_agnostic_4k_pages_title))
                        .setMessage(
                                Html.fromHtml(
                                        Enable16kUtils.isUsing16kbPages()
                                                ? getString(R.string.page_agnostic_16k_pages_text)
                                                : getString(R.string.page_agnostic_4k_pages_text),
                                        63))
                        .setCancelable(false)
                        .setPositiveButton(
                                android.R.string.ok,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.android.settings.development.PageAgnosticWarningActivity.1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        PageAgnosticWarningActivity.this.finish();
                                    }
                                })
                        .create();
        create.show();
        ((TextView) create.findViewById(android.R.id.message))
                .setMovementMethod(LinkMovementMethod.getInstance());
    }
}
