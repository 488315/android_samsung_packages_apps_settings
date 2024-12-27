package com.samsung.android.settings.actions.development;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.android.settings.R;

import com.samsung.android.settings.bixby.actionhandler.BaseActionHandler;
import com.sec.ims.IMSParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SettingsBixbyDialog extends DialogFragment {
    public BaseActionHandler mActionHandler;
    public EditText mKeyEditText;
    public TextView mResponseView;
    public EditText mValueEditText;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ResponseCallback
            implements com.samsung.android.sdk.bixby2.action.ResponseCallback {
        public ResponseCallback() {}

        @Override // com.samsung.android.sdk.bixby2.action.ResponseCallback
        public final void onComplete(String str) {
            SettingsBixbyDialog.this.mResponseView.setText(str);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        View inflate =
                LayoutInflater.from(getContext())
                        .inflate(R.layout.sec_settings_bixby_dialog, (ViewGroup) null);
        this.mResponseView = (TextView) inflate.findViewById(R.id.response);
        this.mKeyEditText = (EditText) inflate.findViewById(R.id.key);
        this.mValueEditText = (EditText) inflate.findViewById(R.id.value);
        final String string = getArguments().getString(IMSParameter.CALL.ACTION);
        AlertDialog.Builder title =
                new AlertDialog.Builder(getContext()).setView(inflate).setTitle(string);
        ((Button) inflate.findViewById(R.id.start_button))
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.actions.development.SettingsBixbyDialog.1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                SettingsBixbyDialog settingsBixbyDialog = SettingsBixbyDialog.this;
                                BaseActionHandler baseActionHandler =
                                        settingsBixbyDialog.mActionHandler;
                                Context context = settingsBixbyDialog.getContext();
                                String str = string;
                                String editable =
                                        SettingsBixbyDialog.this.mKeyEditText.getText().toString();
                                String editable2 =
                                        SettingsBixbyDialog.this
                                                .mValueEditText
                                                .getText()
                                                .toString();
                                HashMap hashMap = new HashMap();
                                if (hashMap.containsKey(editable)) {
                                    ((List) hashMap.get(editable)).add(editable2);
                                } else {
                                    hashMap.put(editable, new ArrayList(Arrays.asList(editable2)));
                                }
                                Bundle bundle2 = new Bundle();
                                bundle2.putSerializable("params", hashMap);
                                baseActionHandler.executeAction(
                                        context,
                                        str,
                                        bundle2,
                                        SettingsBixbyDialog.this.new ResponseCallback());
                            }
                        });
        return title.create();
    }
}
