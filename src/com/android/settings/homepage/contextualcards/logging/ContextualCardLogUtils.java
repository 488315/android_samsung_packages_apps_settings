package com.android.settings.homepage.contextualcards.logging;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;

import com.android.settings.homepage.contextualcards.ContextualCard;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ContextualCardLogUtils {
    public static String buildCardClickLog(ContextualCard contextualCard, int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(contextualCard.mSliceUri);
        sb.append("|");
        sb.append(contextualCard.mRankingScore);
        sb.append("|");
        sb.append(i);
        sb.append("|");
        int i4 = 2;
        if (i2 != 0) {
            if (i2 == 2) {
                i4 = 3;
            } else if (i2 != 3) {
                RecordingInputConnection$$ExternalSyntheticOutline0.m(
                        i2, "unknown type ", "ContextualCardLogUtils");
                i4 = 0;
            } else {
                i4 = 1;
            }
        }
        sb.append(i4);
        sb.append("|");
        sb.append(i3);
        return sb.toString();
    }

    public static String buildCardListLog(List list) {
        StringBuilder sb = new StringBuilder();
        sb.append(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ContextualCard contextualCard = (ContextualCard) it.next();
            sb.append("|");
            sb.append(contextualCard.mSliceUri);
            sb.append("|");
            sb.append(contextualCard.mRankingScore);
        }
        return sb.toString();
    }
}
