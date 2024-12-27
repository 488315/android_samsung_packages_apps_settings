package com.android.settings.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.homepage.HomepageUtils;
import com.samsung.android.settings.widget.HomepagePreferenceHelper;

import java.util.Iterator;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class HomepagePreference extends SecPreference
        implements HomepagePreferenceLayoutHelper.HomepagePreferenceLayout {
    public final HomepagePreferenceLayoutHelper mHelper;
    public HomepagePreferenceHelper mPreferenceHelper;

    public HomepagePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Trace.beginSection("HomepagePreference1");
        this.mHelper = new HomepagePreferenceLayoutHelper(this);
        Trace.endSection();
    }

    @Override // com.android.settings.widget.HomepagePreferenceLayoutHelper.HomepagePreferenceLayout
    public final HomepagePreferenceLayoutHelper getHelper() {
        return this.mHelper;
    }

    public final HomepagePreferenceHelper getPreferenceHelper() {
        if (this.mPreferenceHelper == null) {
            this.mPreferenceHelper = new HomepagePreferenceHelper(this);
        }
        return this.mPreferenceHelper;
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        Trace.beginSection("HomepagePreference#onBindViewHolder");
        Trace.beginSection("HomepagePreference#onBindViewHolder.super");
        super.onBindViewHolder(preferenceViewHolder);
        Trace.endSection();
        this.mHelper.onBindViewHolder(preferenceViewHolder);
        Trace.endSection();
    }

    @Override // androidx.preference.Preference
    public final void setOrder(int i) {
        if (!Rune.supportGoodSettings(getContext())
                || getPreferenceHelper().getOrder() == Integer.MAX_VALUE) {
            super.setOrder(i);
        } else {
            super.setOrder(getPreferenceHelper().getOrder());
        }
    }

    @Override // androidx.preference.Preference
    public final void setSummary(CharSequence charSequence) {
        CharSequence charSequence2 = charSequence;
        if (!HomepageUtils.SEPARATORS_REPLACEMENT_SKIP_LIST.contains(getKey())) {
            String str = (String) charSequence;
            boolean isEmpty = TextUtils.isEmpty(str);
            charSequence2 = str;
            if (!isEmpty) {
                Iterator it = HomepageUtils.SEPARATORS.iterator();
                String str2 = str;
                while (it.hasNext()) {
                    String str3 = (String) it.next();
                    if (str2.contains(str3)) {
                        str2 = str2.replace(str3, Utils.getTopLevelSummarySeparator(getContext()));
                        Log.d(
                                "HomepagePreference",
                                "Replace Separator from " + str + " to " + str2);
                    }
                }
                charSequence2 = str2;
            }
        }
        super.setSummary(charSequence2);
    }

    public final void setVisibleByGoodSettings(Boolean bool) {
        if (Rune.supportGoodSettings(getContext())) {
            HomepagePreferenceHelper preferenceHelper = getPreferenceHelper();
            preferenceHelper.getClass();
            preferenceHelper.mVisible = Optional.of(bool);
            setVisible(bool.booleanValue());
        }
    }

    public HomepagePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Trace.beginSection("HomepagePreference2");
        this.mHelper = new HomepagePreferenceLayoutHelper(this);
        Trace.endSection();
    }

    public HomepagePreference(Context context) {
        super(context, null);
        Trace.beginSection("HomepagePreference4");
        this.mHelper = new HomepagePreferenceLayoutHelper(this);
        Trace.endSection();
    }

    public HomepagePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Trace.beginSection("HomepagePreference3");
        this.mHelper = new HomepagePreferenceLayoutHelper(this);
        Trace.endSection();
    }
}
