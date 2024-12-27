package com.android.settings.connecteddevice.audiosharing;

import android.R;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AudioSharingDialogHelper {
    public static void updateMessageStyle(AlertDialog alertDialog) {
        TextView textView = (TextView) alertDialog.findViewById(R.id.message);
        if (textView == null) {
            Log.w("AudioSharingDialogHelper", "Fail to update dialog: message view is null");
            return;
        }
        textView.setTypeface(Typeface.create("sans-serif", 0));
        textView.setTextDirection(5);
        textView.setTextAlignment(4);
        textView.setTextSize(1, 14.0f);
    }
}
