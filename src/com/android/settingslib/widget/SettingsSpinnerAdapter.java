package com.android.settingslib.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsSpinnerAdapter extends ArrayAdapter {
    public final LayoutInflater mDefaultInflater;

    public SettingsSpinnerAdapter(Context context) {
        super(context, R.layout.settings_spinner_view);
        setDropDownViewResource(R.layout.settings_spinner_dropdown_view);
        this.mDefaultInflater = LayoutInflater.from(context);
    }
}
