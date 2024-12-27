package com.android.settings.datetime.timezone;

import android.content.res.Resources;
import android.icu.text.CaseMap;
import android.icu.text.Edits;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SpannableUtil {
    public static final CaseMap.Title TITLE_CASE_MAP = CaseMap.toTitle().sentences().noLowercase();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SpannableFormattable implements Formattable {
        public final Spannable mSpannable;

        public SpannableFormattable(Spannable spannable) {
            this.mSpannable = spannable;
        }

        @Override // java.util.Formattable
        public final void formatTo(Formatter formatter, int i, int i2, int i3) {
            int length;
            CharSequence charSequence = this.mSpannable;
            if (i3 != -1 && i3 < charSequence.length()) {
                charSequence = charSequence.subSequence(0, i3);
            }
            boolean z = (i & 1) != 0;
            if (i2 != -1 && (length = i2 - charSequence.length()) > 0) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                if (!z) {
                    spannableStringBuilder.append((CharSequence) " ".repeat(length));
                }
                spannableStringBuilder.append(charSequence);
                if (z) {
                    spannableStringBuilder.append((CharSequence) " ".repeat(length));
                }
                charSequence = spannableStringBuilder;
            }
            try {
                formatter.out().append(charSequence);
            } catch (IOException e) {
                Log.e("SpannableUtil", "error in SpannableFormattable", e);
            }
        }
    }

    public static Spannable getResourcesText(Resources resources, int i, Object... objArr) {
        Locale locale = resources.getConfiguration().getLocales().get(0);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        for (int i2 = 0; i2 < objArr.length; i2++) {
            Object obj = objArr[i2];
            if (obj instanceof Spannable) {
                objArr[i2] = new SpannableFormattable((Spannable) obj);
            }
        }
        new Formatter(spannableStringBuilder, locale).format(resources.getString(i), objArr);
        return spannableStringBuilder;
    }

    public static boolean isAdditionalSign(char c) {
        return Locale.getDefault().getLanguage().equals("or")
                ? c == 2817 || c == 2818 || c == 2819 || c == 2902 || c == 2903
                : Locale.getDefault().getLanguage().equals("hi")
                        ? c >= 2385 && c <= 2391
                        : c >= 2385 && c <= 2391;
    }

    public static boolean isJoiningCharacter(char c) {
        return Objects.equals(Locale.getDefault().getLanguage(), "or")
                ? isMatra(c) || isAdditionalSign(c)
                : Objects.equals(Locale.getDefault().getLanguage(), "hi")
                        ? isMatra(c)
                                || isAdditionalSign(c)
                                || isVowel(c)
                                || c == 2403
                                || c == 2365
                                || c == 2364
                        : isMatra(c)
                                || isAdditionalSign(c)
                                || isVowel(c)
                                || c == 2403
                                || c == 2365
                                || c == 2364;
    }

    public static boolean isMatra(char c) {
        return Locale.getDefault().getLanguage().equals("or")
                ? c >= 2876 && c <= 2893
                : Locale.getDefault().getLanguage().equals("hi")
                        ? c >= 2366 && c <= 2381
                        : c >= 2366 && c <= 2381;
    }

    public static boolean isVowel(char c) {
        return Locale.getDefault().getLanguage().equals("hi")
                ? c >= 2305 && c <= 2308
                : c >= 2305 && c <= 2308;
    }

    public static CharSequence titleCaseSentences(Locale locale, CharSequence charSequence) {
        CaseMap.Title title = TITLE_CASE_MAP;
        Spannable spannable = (Spannable) charSequence;
        Edits edits = new Edits();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        title.apply(locale, null, spannable, spannableStringBuilder, edits);
        if (!edits.hasChanges()) {
            return spannable;
        }
        Edits.Iterator coarseChangesIterator = edits.getCoarseChangesIterator();
        ArrayList arrayList = new ArrayList();
        while (coarseChangesIterator.next()) {
            arrayList.add(
                    new int[] {
                        coarseChangesIterator.sourceIndex(),
                        coarseChangesIterator.oldLength(),
                        coarseChangesIterator.destinationIndex(),
                        coarseChangesIterator.newLength()
                    });
        }
        Collections.reverse(arrayList);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(spannable);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int[] iArr = (int[]) it.next();
            int i = iArr[0];
            int i2 = i + iArr[1];
            int i3 = iArr[2];
            spannableStringBuilder2.replace(
                    i, i2, (CharSequence) spannableStringBuilder, i3, i3 + iArr[3]);
        }
        return spannableStringBuilder2;
    }
}
