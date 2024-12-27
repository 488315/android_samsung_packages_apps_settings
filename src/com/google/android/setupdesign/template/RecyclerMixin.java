package com.google.android.setupdesign.template;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.DividerItemDecoration;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.google.android.setupdesign.view.HeaderRecyclerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RecyclerMixin implements Mixin {
    public Drawable defaultDivider;
    public Drawable divider;
    public final DividerItemDecoration dividerDecoration;
    public int dividerInsetEnd;
    public int dividerInsetStart;
    public final View header;
    public final boolean isDividerDisplay;
    public final RecyclerView recyclerView;
    public final TemplateLayout templateLayout;

    public RecyclerMixin(TemplateLayout templateLayout, RecyclerView recyclerView) {
        this.isDividerDisplay = true;
        this.templateLayout = templateLayout;
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(templateLayout.getContext());
        this.dividerDecoration = dividerItemDecoration;
        this.recyclerView = recyclerView;
        templateLayout.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        if (recyclerView instanceof HeaderRecyclerView) {
            this.header = ((HeaderRecyclerView) recyclerView).header;
        }
        Context context = templateLayout.getContext();
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.sudDividerShown, typedValue, true);
        boolean z = typedValue.data != 0;
        if (PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
            PartnerConfigHelper partnerConfigHelper =
                    PartnerConfigHelper.get(recyclerView.getContext());
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_ITEMS_DIVIDER_SHOWN;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                z =
                        PartnerConfigHelper.get(recyclerView.getContext())
                                .getBoolean(recyclerView.getContext(), partnerConfig, z);
            }
        }
        this.isDividerDisplay = z;
        if (z) {
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
    }

    public final void updateDivider() {
        TemplateLayout templateLayout = this.templateLayout;
        if (templateLayout.isLayoutDirectionResolved()) {
            Drawable drawable = this.defaultDivider;
            DividerItemDecoration dividerItemDecoration = this.dividerDecoration;
            if (drawable == null) {
                this.defaultDivider = dividerItemDecoration.divider;
            }
            Drawable drawable2 = this.defaultDivider;
            int i = this.dividerInsetStart;
            int i2 = this.dividerInsetEnd;
            InsetDrawable insetDrawable =
                    templateLayout.getLayoutDirection() == 1
                            ? new InsetDrawable(drawable2, i2, 0, i, 0)
                            : new InsetDrawable(drawable2, i, 0, i2, 0);
            this.divider = insetDrawable;
            dividerItemDecoration.getClass();
            dividerItemDecoration.dividerIntrinsicHeight = insetDrawable.getIntrinsicHeight();
            dividerItemDecoration.divider = insetDrawable;
        }
    }
}
