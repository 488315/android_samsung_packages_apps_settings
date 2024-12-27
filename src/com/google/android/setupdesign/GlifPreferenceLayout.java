package com.google.android.setupdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.ForceTwoPaneHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.template.RecyclerMixin;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class GlifPreferenceLayout extends GlifRecyclerLayout {
    public GlifPreferenceLayout(Context context) {
        super(context);
    }

    @Override // com.google.android.setupdesign.GlifRecyclerLayout,
              // com.google.android.setupdesign.GlifLayout,
              // com.google.android.setupcompat.internal.TemplateLayout
    public final ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    @Override // com.google.android.setupdesign.GlifRecyclerLayout,
              // com.google.android.setupdesign.GlifLayout,
              // com.google.android.setupcompat.internal.TemplateLayout
    public final View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            if (GlifLayout.isEmbeddedActivityOnePaneEnabled(getContext())) {
                i = R.layout.sud_glif_preference_embedded_template;
            } else {
                Context context = getContext();
                Logger logger = ForceTwoPaneHelper.LOG;
                i =
                        PartnerConfigHelper.isForceTwoPaneEnabled(context)
                                ? ForceTwoPaneHelper.getForceTwoPaneStyleLayout(
                                        getContext(), R.layout.sud_glif_preference_template)
                                : R.layout.sud_glif_preference_template;
            }
        }
        return super.onInflateTemplate(layoutInflater, i);
    }

    @Override // com.google.android.setupdesign.GlifRecyclerLayout,
              // com.google.android.setupcompat.internal.TemplateLayout
    public final void onTemplateInflated() {
        LayoutInflater from = LayoutInflater.from(getContext());
        Context context = getContext();
        Logger logger = ForceTwoPaneHelper.LOG;
        boolean isForceTwoPaneEnabled = PartnerConfigHelper.isForceTwoPaneEnabled(context);
        int i = R.layout.sud_glif_preference_recycler_view;
        if (isForceTwoPaneEnabled) {
            i =
                    ForceTwoPaneHelper.getForceTwoPaneStyleLayout(
                            getContext(), R.layout.sud_glif_preference_recycler_view);
        }
        this.recyclerMixin =
                new RecyclerMixin(this, (RecyclerView) from.inflate(i, (ViewGroup) this, false));
    }

    public GlifPreferenceLayout(Context context, int i, int i2) {
        super(context, i, i2);
    }

    public GlifPreferenceLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GlifPreferenceLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
