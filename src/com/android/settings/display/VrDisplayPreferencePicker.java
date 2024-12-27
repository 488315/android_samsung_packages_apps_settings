package com.android.settings.display;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.widget.CandidateInfo;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VrDisplayPreferencePicker extends RadioButtonPickerFragment {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VrCandidateInfo extends CandidateInfo {
        public final String label;
        public final int value;

        public VrCandidateInfo(Context context, int i, int i2) {
            super(true);
            this.value = i;
            this.label = context.getString(i2);
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return "vr_display_pref_" + this.value;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return null;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.label;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        Context context = getContext();
        arrayList.add(new VrCandidateInfo(context, 0, R.string.display_vr_pref_low_persistence));
        arrayList.add(new VrCandidateInfo(context, 1, R.string.display_vr_pref_off));
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        return SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                Settings.Secure.getIntForUser(
                        getContext().getContentResolver(), "vr_display_mode", 0, this.mUserId),
                "vr_display_pref_");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 921;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.vr_display_settings;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        str.getClass();
        if (str.equals("vr_display_pref_0")) {
            return Settings.Secure.putIntForUser(
                    getContext().getContentResolver(), "vr_display_mode", 0, this.mUserId);
        }
        if (str.equals("vr_display_pref_1")) {
            return Settings.Secure.putIntForUser(
                    getContext().getContentResolver(), "vr_display_mode", 1, this.mUserId);
        }
        return false;
    }
}
