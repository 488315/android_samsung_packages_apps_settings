package com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.presenter.managers.FeatureManager$UiFeature;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AsIndicatorAdapter extends BaseAdapter {
    public final Context context;
    public int currentIndex;
    public List supportedList = new ArrayList();
    public final List indicatorList = new ArrayList();

    public AsIndicatorAdapter(Context context) {
        this.context = context;
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return this.supportedList.size();
    }

    public final Integer getCurrentSupportedType() {
        int size = this.supportedList.size();
        int i = this.currentIndex;
        if (size > i) {
            return (Integer) this.supportedList.get(i);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        return (ImageView) ((ArrayList) this.indicatorList).get(i);
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        Context context;
        ImageView imageView = view instanceof ImageView ? (ImageView) view : null;
        if (imageView == null) {
            Object systemService =
                    (viewGroup == null || (context = viewGroup.getContext()) == null)
                            ? null
                            : context.getSystemService("layout_inflater");
            Intrinsics.checkNotNull(
                    systemService,
                    "null cannot be cast to non-null type android.view.LayoutInflater");
            View inflate =
                    ((LayoutInflater) systemService)
                            .inflate(R.layout.as_home_indicator, viewGroup, false);
            imageView = inflate instanceof ImageView ? (ImageView) inflate : null;
        }
        if (imageView != null) {
            imageView.setColorFilter(
                    this.context.getColor(
                            i == this.currentIndex
                                    ? R.color.as_home_indicator_icon_selected_color
                                    : R.color.as_home_indicator_icon_unselected_color));
            ((ArrayList) this.indicatorList).add(i, imageView);
            imageView.setVisibility(this.supportedList.size() > 1 ? 0 : 8);
            if (!FeatureManager$UiFeature.isDefaultTheme(this.context)) {
                imageView.setColorFilter(this.context.getColor(R.color.as_theme_main_text_color));
                imageView.setAlpha(i == this.currentIndex ? 0.4f : 0.2f);
            }
            String string =
                    i == this.currentIndex
                            ? this.context.getString(R.string.selected)
                            : this.context.getString(R.string.not_selected);
            Intrinsics.checkNotNull(string);
            imageView.setContentDescription(
                    string
                            + ", "
                            + this.context.getString(
                                    R.string.as_indicator_description,
                                    Integer.valueOf(i + 1),
                                    Integer.valueOf(this.supportedList.size())));
        }
        return imageView;
    }
}
