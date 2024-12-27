package com.android.settings.applications;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.accessibility.ListDialogPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppDomainsPreference extends ListDialogPreference {
    public int mNumEntries;

    public AppDomainsPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDialogLayoutResId = R.layout.app_domains_dialog;
        this.mListItemLayout = R.layout.app_domains_item;
    }

    @Override // com.android.settings.accessibility.ListDialogPreference,
              // androidx.preference.Preference
    public final CharSequence getSummary() {
        Context context = getContext();
        if (this.mNumEntries == 0) {
            return context.getString(R.string.domain_urls_summary_none);
        }
        return context.getString(
                this.mNumEntries == 1
                        ? R.string.domain_urls_summary_one
                        : R.string.domain_urls_summary_some,
                super.getSummary());
    }

    @Override // com.android.settings.accessibility.ListDialogPreference
    public final void onBindListItem(View view, int i) {
        CharSequence titleAt = getTitleAt(i);
        if (titleAt != null) {
            ((TextView) view.findViewById(R.id.domain_name)).setText(titleAt);
        }
    }
}
