package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.sim.ChooseSimActivity;

import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.google.android.setupdesign.util.TextViewPartnerStyler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RecyclerItemAdapter extends RecyclerView.Adapter {
    public final boolean applyPartnerHeavyThemeResource;
    public final ChooseSimActivity.DisableableItem itemHierarchy;
    public OnItemSelectedListener listener;
    public final boolean useFullDynamicColor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnItemSelectedListener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class PatchedLayerDrawable extends LayerDrawable {
        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public final boolean getPadding(Rect rect) {
            return super.getPadding(rect)
                    && !(rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0);
        }
    }

    public RecyclerItemAdapter(
            ChooseSimActivity.DisableableItem disableableItem, boolean z, boolean z2) {
        this.applyPartnerHeavyThemeResource = z;
        this.useFullDynamicColor = z2;
        disableableItem.observers.add(this);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.itemHierarchy.visible ? 1 : 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        ChooseSimActivity.DisableableItem disableableItem = this.itemHierarchy;
        disableableItem.getClass();
        int i2 = disableableItem.id;
        if (i2 > 0) {
            return i2;
        }
        return -1L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        ChooseSimActivity.DisableableItem disableableItem = this.itemHierarchy;
        disableableItem.getClass();
        return disableableItem.layoutRes;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        ChooseSimActivity.DisableableItem disableableItem = this.itemHierarchy;
        disableableItem.getClass();
        boolean z = disableableItem.enabled;
        itemViewHolder.isEnabled = z;
        itemViewHolder.itemView.setClickable(z);
        itemViewHolder.itemView.setEnabled(z);
        itemViewHolder.itemView.setFocusable(z);
        itemViewHolder.item = disableableItem;
        View view = itemViewHolder.itemView;
        ((TextView) view.findViewById(R.id.sud_items_title)).setText(disableableItem.title);
        TextView textView = (TextView) view.findViewById(R.id.sud_items_summary);
        CharSequence charSequence = disableableItem.summary;
        if (charSequence == null || charSequence.length() <= 0) {
            textView.setVisibility(8);
        } else {
            textView.setText(charSequence);
            textView.setVisibility(0);
        }
        view.setContentDescription(null);
        view.findViewById(R.id.sud_items_icon_container).setVisibility(8);
        view.setId(disableableItem.id);
        if (view.getId() != R.id.sud_layout_header) {
            Context context = view.getContext();
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_LAYOUT_MARGIN_START;
            boolean isPartnerConfigAvailable =
                    partnerConfigHelper.isPartnerConfigAvailable(partnerConfig);
            PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LAYOUT_MARGIN_END;
            boolean isPartnerConfigAvailable2 =
                    partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2);
            if (PartnerStyleHelper.shouldApplyPartnerResource(view)
                    && (isPartnerConfigAvailable || isPartnerConfigAvailable2)) {
                int dimension =
                        isPartnerConfigAvailable
                                ? (int)
                                        PartnerConfigHelper.get(context)
                                                .getDimension(context, partnerConfig, 0.0f)
                                : view.getPaddingStart();
                int dimension2 =
                        isPartnerConfigAvailable2
                                ? (int)
                                        PartnerConfigHelper.get(context)
                                                .getDimension(context, partnerConfig2, 0.0f)
                                : view.getPaddingEnd();
                if (dimension != view.getPaddingStart() || dimension2 != view.getPaddingEnd()) {
                    view.setPadding(
                            dimension, view.getPaddingTop(), dimension2, view.getPaddingBottom());
                }
            }
        }
        if (PartnerStyleHelper.shouldApplyPartnerHeavyThemeResource(view)) {
            TextView textView2 = (TextView) view.findViewById(R.id.sud_items_title);
            if (PartnerStyleHelper.shouldApplyPartnerHeavyThemeResource(textView2)) {
                TextViewPartnerStyler.applyPartnerCustomizationStyle(
                        textView2,
                        new TextViewPartnerStyler.TextPartnerConfigs(
                                null,
                                null,
                                PartnerConfig.CONFIG_ITEMS_TITLE_TEXT_SIZE,
                                PartnerConfig.CONFIG_ITEMS_TITLE_FONT_FAMILY,
                                null,
                                null,
                                null,
                                null,
                                PartnerStyleHelper.getLayoutGravity(textView2.getContext())));
            }
            TextView textView3 = (TextView) view.findViewById(R.id.sud_items_summary);
            if (textView3.getVisibility() == 8 && (view instanceof LinearLayout)) {
                ((LinearLayout) view).setGravity(16);
            }
            if (PartnerStyleHelper.shouldApplyPartnerHeavyThemeResource(textView3)) {
                TextViewPartnerStyler.applyPartnerCustomizationStyle(
                        textView3,
                        new TextViewPartnerStyler.TextPartnerConfigs(
                                null,
                                null,
                                PartnerConfig.CONFIG_ITEMS_SUMMARY_TEXT_SIZE,
                                PartnerConfig.CONFIG_ITEMS_SUMMARY_FONT_FAMILY,
                                null,
                                null,
                                PartnerConfig.CONFIG_ITEMS_SUMMARY_MARGIN_TOP,
                                null,
                                PartnerStyleHelper.getLayoutGravity(textView3.getContext())));
            }
            Context context2 = view.getContext();
            PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(context2);
            PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_ITEMS_PADDING_TOP;
            float dimension3 =
                    partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)
                            ? PartnerConfigHelper.get(context2)
                                    .getDimension(context2, partnerConfig3, 0.0f)
                            : view.getPaddingTop();
            PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(context2);
            PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_ITEMS_PADDING_BOTTOM;
            float dimension4 =
                    partnerConfigHelper4.isPartnerConfigAvailable(partnerConfig4)
                            ? PartnerConfigHelper.get(context2)
                                    .getDimension(context2, partnerConfig4, 0.0f)
                            : view.getPaddingBottom();
            if (dimension3 != view.getPaddingTop() || dimension4 != view.getPaddingBottom()) {
                view.setPadding(
                        view.getPaddingStart(),
                        (int) dimension3,
                        view.getPaddingEnd(),
                        (int) dimension4);
            }
            PartnerConfigHelper partnerConfigHelper5 = PartnerConfigHelper.get(context2);
            PartnerConfig partnerConfig5 = PartnerConfig.CONFIG_ITEMS_MIN_HEIGHT;
            if (partnerConfigHelper5.isPartnerConfigAvailable(partnerConfig5)) {
                view.setMinimumHeight(
                        (int)
                                PartnerConfigHelper.get(context2)
                                        .getDimension(context2, partnerConfig5, 0.0f));
            }
        }
        TextView textView4 = (TextView) view.findViewById(R.id.sud_items_title);
        TextView textView5 = (TextView) view.findViewById(R.id.sud_items_summary);
        textView4.setEnabled(disableableItem.enabled);
        textView5.setEnabled(disableableItem.enabled);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Drawable background;
        View m =
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup, i, viewGroup, false);
        final ItemViewHolder itemViewHolder = new ItemViewHolder(m);
        if (!"noBackground".equals(m.getTag())) {
            TypedArray obtainStyledAttributes =
                    viewGroup
                            .getContext()
                            .obtainStyledAttributes(R$styleable.SudRecyclerItemAdapter);
            Drawable drawable = obtainStyledAttributes.getDrawable(1);
            if (drawable == null) {
                drawable = obtainStyledAttributes.getDrawable(2);
                background = null;
            } else {
                background = m.getBackground();
                if (background == null) {
                    background =
                            (!this.applyPartnerHeavyThemeResource || this.useFullDynamicColor)
                                    ? obtainStyledAttributes.getDrawable(0)
                                    : new ColorDrawable(
                                            PartnerConfigHelper.get(m.getContext())
                                                    .getColor(
                                                            m.getContext(),
                                                            PartnerConfig
                                                                    .CONFIG_LAYOUT_BACKGROUND_COLOR));
                }
            }
            if (drawable == null || background == null) {
                Log.e(
                        "RecyclerItemAdapter",
                        "Cannot resolve required attributes. selectableItemBackground="
                                + drawable
                                + " background="
                                + background);
            } else {
                m.setBackgroundDrawable(
                        new PatchedLayerDrawable(new Drawable[] {background, drawable}));
            }
            obtainStyledAttributes.recycle();
        }
        m.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.google.android.setupdesign.items.RecyclerItemAdapter.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ChooseSimActivity.DisableableItem disableableItem = itemViewHolder.item;
                        OnItemSelectedListener onItemSelectedListener =
                                RecyclerItemAdapter.this.listener;
                    }
                });
        return itemViewHolder;
    }
}
