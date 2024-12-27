package com.samsung.android.settings.accessibility.vision.displaymode;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference;
import com.samsung.android.settings.accessibility.vision.controllers.CustomDisplayModePreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DisplayModeInfo extends SingleChoicePreference.SingleChoiceCandidateInfo {
    public final SingleChoicePreference.CheckItemDefaultKey checkItemDefaultKey;
    public final Context context;

    public DisplayModeInfo(
            Context context,
            String str,
            CharSequence charSequence,
            Drawable drawable,
            CustomDisplayModePreferenceController.AnonymousClass1 anonymousClass1,
            SingleChoicePreference.CheckItemDefaultKey checkItemDefaultKey) {
        super(str, charSequence, drawable, anonymousClass1, false);
        this.context = context;
        this.checkItemDefaultKey = checkItemDefaultKey;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference.SingleChoiceCandidateInfo, com.android.settingslib.widget.CandidateInfo
    public final Drawable loadIcon() {
        return this.key.equals(this.checkItemDefaultKey.getDefaultKey())
                ? new LayerDrawable(
                        new Drawable[] {
                            this.icon, this.context.getDrawable(R.drawable.display_mode_selected_bg)
                        })
                : this.icon;
    }
}
