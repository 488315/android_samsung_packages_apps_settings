package com.android.settings.wifi;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.HelpUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiScanningRequiredFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public Intent getHelpIntent(Context context) {
        return HelpUtils.getHelpIntent(
                context,
                context.getString(R.string.help_uri_wifi_scanning_required),
                context.getClass().getName());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1373;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Context context = getContext();
        context.getContentResolver();
        if (i != -3) {
            if (i != -1) {
                return;
            }
            ((WifiManager) context.getSystemService(WifiManager.class))
                    .setScanAlwaysAvailable(true);
            Toast.makeText(
                            context,
                            context.getString(R.string.wifi_settings_scanning_required_enabled),
                            0)
                    .show();
            getTargetFragment().onActivityResult(getTargetRequestCode(), -1, null);
            return;
        }
        Intent helpIntent = getHelpIntent(getContext());
        if (helpIntent != null) {
            try {
                getActivity().startActivityForResult(helpIntent, 0);
            } catch (ActivityNotFoundException unused) {
                Log.e(
                        "WifiScanReqFrag",
                        "Activity was not found for intent, " + helpIntent.toString());
            }
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.wifi_settings_scanning_required_title);
        builder.setView(R.layout.wifi_settings_scanning_required_view);
        builder.setPositiveButton(R.string.wifi_settings_scanning_required_turn_on, this);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        if (!TextUtils.isEmpty(getContext().getString(R.string.help_uri_wifi_scanning_required))) {
            builder.setNeutralButton(R.string.learn_more, this);
        }
        return builder.create();
    }
}
