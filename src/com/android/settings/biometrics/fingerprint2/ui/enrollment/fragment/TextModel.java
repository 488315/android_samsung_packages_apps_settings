package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TextModel {
    public final int footerMessageFive;
    public final int footerMessageFour;
    public final int footerMessageSix;
    public final int footerMessageThree;
    public final int footerMessageTwo;
    public final int footerTitleOne;
    public final int headerText;

    public TextModel(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.footerMessageTwo = i;
        this.footerMessageThree = i2;
        this.footerMessageFour = i3;
        this.footerMessageFive = i4;
        this.footerMessageSix = i5;
        this.footerTitleOne = i6;
        this.headerText = i7;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextModel)) {
            return false;
        }
        TextModel textModel = (TextModel) obj;
        return this.footerMessageTwo == textModel.footerMessageTwo
                && this.footerMessageThree == textModel.footerMessageThree
                && this.footerMessageFour == textModel.footerMessageFour
                && this.footerMessageFive == textModel.footerMessageFive
                && this.footerMessageSix == textModel.footerMessageSix
                && this.footerTitleOne == textModel.footerTitleOne
                && this.headerText == textModel.headerText;
    }

    public final int hashCode() {
        return Integer.hashCode(
                        R.string.security_settings_fingerprint_enroll_introduction_v3_message)
                + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                        this.headerText,
                        KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                R.string
                                        .security_settings_fingerprint_enroll_introduction_footer_title_2,
                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                        this.footerTitleOne,
                                        KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                                R.string
                                                        .security_settings_fingerprint_enroll_introduction_no_thanks,
                                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0
                                                        .m(
                                                                this.footerMessageSix,
                                                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                this
                                                                                        .footerMessageFive,
                                                                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0
                                                                                        .m(
                                                                                                this
                                                                                                        .footerMessageFour,
                                                                                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0
                                                                                                        .m(
                                                                                                                this
                                                                                                                        .footerMessageThree,
                                                                                                                Integer
                                                                                                                                .hashCode(
                                                                                                                                        this
                                                                                                                                                .footerMessageTwo)
                                                                                                                        * 31,
                                                                                                                31),
                                                                                                31),
                                                                                31),
                                                                31),
                                                31),
                                        31),
                                31),
                        31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TextModel(footerMessageTwo=");
        sb.append(this.footerMessageTwo);
        sb.append(", footerMessageThree=");
        sb.append(this.footerMessageThree);
        sb.append(", footerMessageFour=");
        sb.append(this.footerMessageFour);
        sb.append(", footerMessageFive=");
        sb.append(this.footerMessageFive);
        sb.append(", footerMessageSix=");
        sb.append(this.footerMessageSix);
        sb.append(", negativeButton=2132027835, footerTitleOne=");
        sb.append(this.footerTitleOne);
        sb.append(", footerTitleTwo=2132027832, headerText=");
        return Anchor$$ExternalSyntheticOutline0.m(
                sb, this.headerText, ", descriptionText=2132027838)");
    }
}
