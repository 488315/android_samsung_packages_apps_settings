package com.android.settings.display;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.display.DisplayDensityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScreenZoomPreference extends Preference {
    public ScreenZoomPreference(Context context, AttributeSet attributeSet) {
        super(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.preferenceStyle, android.R.attr.preferenceStyle));
        DisplayDensityUtils displayDensityUtils = new DisplayDensityUtils(context);
        if (displayDensityUtils.mCurrentIndex < 0) {
            setVisible(false);
            setEnabled(false);
        } else if (TextUtils.isEmpty(getSummary())) {
            setSummary(
                    displayDensityUtils
                            .mDefaultDisplayDensityEntries[displayDensityUtils.mCurrentIndex]);
        }
    }
}
