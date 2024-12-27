package com.android.settings.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.settings.widget.SecDividerItemDecorator;
import com.samsung.android.settings.widget.SecNoItemView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class EmptyTextSettings extends SettingsPreferenceFragment {
    public SecNoItemView mEmpty;

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        int dimensionPixelSize =
                getContext().getResources().getDimensionPixelSize(R.dimen.empty_text_layout_height);
        this.mEmpty =
                (SecNoItemView)
                        ((LayoutInflater) getSystemService("layout_inflater"))
                                .inflate(R.layout.sec_no_item_layout, (ViewGroup) null);
        ((ViewGroup) view.findViewById(android.R.id.list_container))
                .addView(this.mEmpty, new ViewGroup.LayoutParams(-1, dimensionPixelSize));
        setEmptyView(this.mEmpty);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecDividerItemDecorator(
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_app_list_item_icon_min_width)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_item_padding_start)),
                                getContext(),
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
        getListView().setImportantForAccessibility(2);
    }

    public final void setEmptyText(int i) {
        SecNoItemView secNoItemView = this.mEmpty;
        secNoItemView.mMainText.setText(secNoItemView.getContext().getString(i));
    }
}
