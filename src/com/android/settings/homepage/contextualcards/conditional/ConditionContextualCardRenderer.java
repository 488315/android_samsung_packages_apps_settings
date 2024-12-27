package com.android.settings.homepage.contextualcards.conditional;

import android.R;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
public final class ConditionContextualCardRenderer implements ContextualCardRenderer {
    public final Context mContext;
    public final ControllerRendererPool mControllerRendererPool;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConditionalCardHolder extends RecyclerView.ViewHolder {
        public final ImageView icon;
        public final TextView summary;
        public final TextView title;

        public ConditionalCardHolder(View view) {
            super(view);
            this.icon = (ImageView) view.findViewById(R.id.icon);
            this.title = (TextView) view.findViewById(R.id.title);
            this.summary = (TextView) view.findViewById(R.id.summary);
        }
    }

    public ConditionContextualCardRenderer(
            Context context, ControllerRendererPool controllerRendererPool) {
        this.mContext = context;
        this.mControllerRendererPool = controllerRendererPool;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardRenderer
    public final void bindView(RecyclerView.ViewHolder viewHolder, ContextualCard contextualCard) {
        ConditionalCardHolder conditionalCardHolder = (ConditionalCardHolder) viewHolder;
        final ConditionalContextualCard conditionalContextualCard =
                (ConditionalContextualCard) contextualCard;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        final SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        metricsFeatureProvider.visible(
                this.mContext,
                VolteConstants.ErrorCode.SERVER_ERROR,
                conditionalContextualCard.mMetricsConstant,
                0);
        final int i = 1;
        conditionalCardHolder
                .itemView
                .findViewById(com.android.settings.R.id.content)
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.homepage.contextualcards.conditional.ConditionContextualCardRenderer$$ExternalSyntheticLambda0
                            public final /* synthetic */ ConditionContextualCardRenderer f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i) {
                                    case 0:
                                        ConditionContextualCardRenderer
                                                conditionContextualCardRenderer = this.f$0;
                                        MetricsFeatureProvider metricsFeatureProvider2 =
                                                metricsFeatureProvider;
                                        ConditionalContextualCard conditionalContextualCard2 =
                                                conditionalContextualCard;
                                        conditionContextualCardRenderer.getClass();
                                        metricsFeatureProvider2.action(
                                                view.getContext(),
                                                376,
                                                conditionalContextualCard2.mMetricsConstant);
                                        conditionContextualCardRenderer
                                                .mControllerRendererPool
                                                .getController(
                                                        conditionContextualCardRenderer.mContext, 3)
                                                .onActionClick(conditionalContextualCard2);
                                        break;
                                    default:
                                        ConditionContextualCardRenderer
                                                conditionContextualCardRenderer2 = this.f$0;
                                        MetricsFeatureProvider metricsFeatureProvider3 =
                                                metricsFeatureProvider;
                                        ConditionalContextualCard conditionalContextualCard3 =
                                                conditionalContextualCard;
                                        metricsFeatureProvider3.action(
                                                conditionContextualCardRenderer2.mContext,
                                                375,
                                                conditionalContextualCard3.mMetricsConstant);
                                        conditionContextualCardRenderer2
                                                .mControllerRendererPool
                                                .getController(
                                                        conditionContextualCardRenderer2.mContext,
                                                        3)
                                                .onPrimaryClick(conditionalContextualCard3);
                                        break;
                                }
                            }
                        });
        conditionalCardHolder.icon.setImageDrawable(conditionalContextualCard.mIconDrawable);
        conditionalCardHolder.title.setText(conditionalContextualCard.mTitleText);
        conditionalCardHolder.summary.setText(conditionalContextualCard.mSummaryText);
        CharSequence charSequence = conditionalContextualCard.mActionText;
        boolean z = !TextUtils.isEmpty(charSequence);
        Button button =
                (Button)
                        conditionalCardHolder.itemView.findViewById(
                                com.android.settings.R.id.first_action);
        if (!z) {
            button.setVisibility(8);
            return;
        }
        button.setVisibility(0);
        button.setText(charSequence);
        final int i2 = 0;
        button.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.homepage.contextualcards.conditional.ConditionContextualCardRenderer$$ExternalSyntheticLambda0
                    public final /* synthetic */ ConditionContextualCardRenderer f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                ConditionContextualCardRenderer conditionContextualCardRenderer =
                                        this.f$0;
                                MetricsFeatureProvider metricsFeatureProvider2 =
                                        metricsFeatureProvider;
                                ConditionalContextualCard conditionalContextualCard2 =
                                        conditionalContextualCard;
                                conditionContextualCardRenderer.getClass();
                                metricsFeatureProvider2.action(
                                        view.getContext(),
                                        376,
                                        conditionalContextualCard2.mMetricsConstant);
                                conditionContextualCardRenderer
                                        .mControllerRendererPool
                                        .getController(conditionContextualCardRenderer.mContext, 3)
                                        .onActionClick(conditionalContextualCard2);
                                break;
                            default:
                                ConditionContextualCardRenderer conditionContextualCardRenderer2 =
                                        this.f$0;
                                MetricsFeatureProvider metricsFeatureProvider3 =
                                        metricsFeatureProvider;
                                ConditionalContextualCard conditionalContextualCard3 =
                                        conditionalContextualCard;
                                metricsFeatureProvider3.action(
                                        conditionContextualCardRenderer2.mContext,
                                        375,
                                        conditionalContextualCard3.mMetricsConstant);
                                conditionContextualCardRenderer2
                                        .mControllerRendererPool
                                        .getController(conditionContextualCardRenderer2.mContext, 3)
                                        .onPrimaryClick(conditionalContextualCard3);
                                break;
                        }
                    }
                });
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardRenderer
    public final RecyclerView.ViewHolder createViewHolder(View view, int i) {
        return new ConditionalCardHolder(view);
    }
}
