package com.android.settings.wifi;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface WifiConfigUiBase2 {
    void dispatchSubmit();

    Context getContext();

    Button getForgetButton();

    LayoutInflater getLayoutInflater();

    Button getSubmitButton();

    void setCancelButton(CharSequence charSequence);

    void setForgetButton(CharSequence charSequence);

    void setSubmitButton(CharSequence charSequence);

    void setTitle(int i);

    void setTitle(CharSequence charSequence);
}
