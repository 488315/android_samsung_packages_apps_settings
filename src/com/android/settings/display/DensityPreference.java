package com.android.settings.display;

import android.content.Context;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.BidiFormatter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import android.view.WindowManagerGlobal;
import android.widget.EditText;

import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.CustomEditTextPreferenceCompat;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.text.NumberFormat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DensityPreference extends CustomEditTextPreferenceCompat {
    public DensityPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        registerDependency();
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        NumberFormat numberFormat = NumberFormat.getInstance();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        setSummary(
                getContext()
                        .getString(
                                R.string.density_pixel_summary,
                                bidiFormatter.unicodeWrap(
                                        numberFormat.format(
                                                (int)
                                                        (Math.min(
                                                                        displayMetrics.widthPixels,
                                                                        displayMetrics.heightPixels)
                                                                / displayMetrics.density)))));
    }

    @Override // com.android.settingslib.CustomEditTextPreferenceCompat
    public final void onBindDialogView(View view) {
        super.onBindDialogView(view);
        EditText editText = (EditText) view.findViewById(android.R.id.edit);
        if (editText != null) {
            editText.setInputType(2);
            StringBuilder sb = new StringBuilder();
            sb.append(
                    (int)
                            (Math.min(r3.widthPixels, r3.heightPixels)
                                    / getContext().getResources().getDisplayMetrics().density));
            sb.append(ApnSettings.MVNO_NONE);
            editText.setText(sb.toString());
            StringBuilder sb2 = Utils.sBuilder;
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(editText);
        }
    }

    @Override // com.android.settingslib.CustomEditTextPreferenceCompat
    public final void onDialogClosed(boolean z) {
        if (z) {
            try {
                DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
                final int max =
                        Math.max(
                                (Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels)
                                                * 160)
                                        / Math.max(Integer.parseInt(this.mText), FileType.XLSX),
                                120);
                final int myUserId = UserHandle.myUserId();
                AsyncTask.execute(
                        new Runnable() { // from class:
                                         // com.android.settingslib.display.DisplayDensityConfiguration$$ExternalSyntheticLambda0
                            public final /* synthetic */ int f$0 = 0;

                            @Override // java.lang.Runnable
                            public final void run() {
                                try {
                                    WindowManagerGlobal.getWindowManagerService()
                                            .setForcedDisplayDensityForUser(
                                                    this.f$0, max, myUserId);
                                } catch (RemoteException unused) {
                                    Log.w(
                                            "DisplayDensityConfig",
                                            "Unable to save forced display density setting");
                                }
                            }
                        });
            } catch (Exception e) {
                Slog.e("DensityPreference", "Couldn't save density", e);
            }
        }
    }
}
