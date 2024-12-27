package com.google.android.setupdesign;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.sim.ChooseSimActivity;

import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.ForceTwoPaneHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.items.ItemInflater;
import com.google.android.setupdesign.items.RecyclerItemAdapter;
import com.google.android.setupdesign.template.RecyclerMixin;
import com.google.android.setupdesign.template.RecyclerViewScrollHandlingDelegate;
import com.google.android.setupdesign.template.RequireScrollMixin;
import com.google.android.setupdesign.util.PartnerStyleHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class GlifRecyclerLayout extends GlifLayout {
    public RecyclerMixin recyclerMixin;

    public GlifRecyclerLayout(Context context) {
        this(context, 0, 0);
    }

    @Override // com.google.android.setupdesign.GlifLayout,
              // com.google.android.setupcompat.internal.TemplateLayout
    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.sud_recycler_view;
        }
        return super.findContainer(i);
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public final View findManagedViewById(int i) {
        View findViewById;
        View view = this.recyclerMixin.header;
        return (view == null || (findViewById = view.findViewById(i)) == null)
                ? super.findViewById(i)
                : findViewById;
    }

    public final void init$1(AttributeSet attributeSet, int i) {
        boolean z;
        boolean z2;
        if (isInEditMode()) {
            return;
        }
        RecyclerMixin recyclerMixin = this.recyclerMixin;
        TemplateLayout templateLayout = recyclerMixin.templateLayout;
        Context context = templateLayout.getContext();
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.SudRecyclerMixin, i, 0);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            ItemInflater itemInflater = new ItemInflater(context);
            XmlResourceParser xml = itemInflater.resources.getXml(resourceId);
            try {
                Object inflate = itemInflater.inflate(xml);
                xml.close();
                ChooseSimActivity.DisableableItem disableableItem =
                        (ChooseSimActivity.DisableableItem) inflate;
                if (templateLayout instanceof GlifLayout) {
                    GlifLayout glifLayout = (GlifLayout) templateLayout;
                    z2 = glifLayout.shouldApplyPartnerHeavyThemeResource();
                    z = glifLayout.useFullDynamicColor();
                } else {
                    z = false;
                    z2 = false;
                }
                RecyclerItemAdapter recyclerItemAdapter =
                        new RecyclerItemAdapter(disableableItem, z2, z);
                recyclerItemAdapter.setHasStableIds(obtainStyledAttributes.getBoolean(4, false));
                recyclerMixin.recyclerView.setAdapter(recyclerItemAdapter);
            } catch (Throwable th) {
                xml.close();
                throw th;
            }
        }
        if (recyclerMixin.isDividerDisplay) {
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, -1);
            if (dimensionPixelSize != -1) {
                recyclerMixin.dividerInsetStart = dimensionPixelSize;
                recyclerMixin.dividerInsetEnd = 0;
                recyclerMixin.updateDivider();
            } else {
                int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(3, 0);
                int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(2, 0);
                if (PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
                    PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
                    PartnerConfig partnerConfig = PartnerConfig.CONFIG_LAYOUT_MARGIN_START;
                    if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                        dimensionPixelSize2 =
                                (int)
                                        PartnerConfigHelper.get(context)
                                                .getDimension(context, partnerConfig, 0.0f);
                    }
                    PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
                    PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LAYOUT_MARGIN_END;
                    if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                        dimensionPixelSize3 =
                                (int)
                                        PartnerConfigHelper.get(context)
                                                .getDimension(context, partnerConfig2, 0.0f);
                    }
                }
                recyclerMixin.dividerInsetStart = dimensionPixelSize2;
                recyclerMixin.dividerInsetEnd = dimensionPixelSize3;
                recyclerMixin.updateDivider();
            }
            obtainStyledAttributes.recycle();
        } else {
            obtainStyledAttributes.recycle();
        }
        registerMixin(RecyclerMixin.class, this.recyclerMixin);
        RequireScrollMixin requireScrollMixin =
                (RequireScrollMixin) getMixin(RequireScrollMixin.class);
        requireScrollMixin.delegate =
                new RecyclerViewScrollHandlingDelegate(
                        requireScrollMixin, this.recyclerMixin.recyclerView);
        View findManagedViewById = findManagedViewById(R.id.sud_landscape_content_area);
        if (findManagedViewById != null) {
            tryApplyPartnerCustomizationContentPaddingTopStyle(findManagedViewById);
        }
        updateLandscapeMiddleHorizontalSpacing();
    }

    @Override // com.google.android.setupdesign.GlifLayout,
              // com.google.android.setupcompat.internal.TemplateLayout
    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            if (GlifLayout.isEmbeddedActivityOnePaneEnabled(getContext())) {
                i = R.layout.sud_glif_recycler_embedded_template;
            } else {
                Context context = getContext();
                Logger logger = ForceTwoPaneHelper.LOG;
                i =
                        PartnerConfigHelper.isForceTwoPaneEnabled(context)
                                ? ForceTwoPaneHelper.getForceTwoPaneStyleLayout(
                                        getContext(), R.layout.sud_glif_recycler_template)
                                : R.layout.sud_glif_recycler_template;
            }
        }
        return super.onInflateTemplate(layoutInflater, i);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        RecyclerMixin recyclerMixin = this.recyclerMixin;
        if (recyclerMixin.divider == null) {
            recyclerMixin.updateDivider();
        }
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public void onTemplateInflated() {
        View findViewById = findViewById(R.id.sud_recycler_view);
        if (!(findViewById instanceof RecyclerView)) {
            throw new IllegalStateException(
                    "GlifRecyclerLayout should use a template with recycler view");
        }
        this.recyclerMixin = new RecyclerMixin(this, (RecyclerView) findViewById);
    }

    public GlifRecyclerLayout(Context context, int i) {
        this(context, i, 0);
    }

    public GlifRecyclerLayout(Context context, int i, int i2) {
        super(context, i, i2);
        init$1(null, 0);
    }

    public GlifRecyclerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init$1(attributeSet, 0);
    }

    @TargetApi(11)
    public GlifRecyclerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init$1(attributeSet, i);
    }
}
