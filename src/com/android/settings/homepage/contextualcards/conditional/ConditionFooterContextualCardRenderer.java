package com.android.settings.homepage.contextualcards.conditional;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settings.homepage.contextualcards.ContextualCardRenderer;
import com.android.settings.homepage.contextualcards.ControllerRendererPool;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConditionFooterContextualCardRenderer implements ContextualCardRenderer {
    public final Context mContext;
    public final ControllerRendererPool mControllerRendererPool;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConditionFooterCardHolder extends RecyclerView.ViewHolder {}

    public ConditionFooterContextualCardRenderer(
            Context context, ControllerRendererPool controllerRendererPool) {
        this.mContext = context;
        this.mControllerRendererPool = controllerRendererPool;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardRenderer
    public final void bindView(RecyclerView.ViewHolder viewHolder, ContextualCard contextualCard) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        final SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        viewHolder.itemView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.homepage.contextualcards.conditional.ConditionFooterContextualCardRenderer$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ConditionFooterContextualCardRenderer
                                conditionFooterContextualCardRenderer =
                                        ConditionFooterContextualCardRenderer.this;
                        MetricsFeatureProvider metricsFeatureProvider2 = metricsFeatureProvider;
                        conditionFooterContextualCardRenderer.getClass();
                        metricsFeatureProvider2.action(
                                0, 373, VolteConstants.ErrorCode.SERVER_ERROR, 0, null);
                        ConditionContextualCardController conditionContextualCardController =
                                (ConditionContextualCardController)
                                        conditionFooterContextualCardRenderer
                                                .mControllerRendererPool.getController(
                                                conditionFooterContextualCardRenderer.mContext, 5);
                        conditionContextualCardController.mIsExpanded = false;
                        conditionContextualCardController.onConditionsChanged();
                    }
                });
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardRenderer
    public final RecyclerView.ViewHolder createViewHolder(View view, int i) {
        return new ConditionFooterCardHolder(view);
    }
}
