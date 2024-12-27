package com.android.settings.datausage;

import android.content.Context;
import android.net.NetworkTemplate;
import android.util.AttributeSet;

import androidx.preference.PreferenceCategory;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TemplatePreferenceCategory extends PreferenceCategory implements TemplatePreference {
    public int mSubId;
    public NetworkTemplate mTemplate;

    public TemplatePreferenceCategory(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.settings.datausage.TemplatePreference
    public final void setTemplate(NetworkTemplate networkTemplate, int i) {
        this.mTemplate = networkTemplate;
        this.mSubId = i;
    }
}
