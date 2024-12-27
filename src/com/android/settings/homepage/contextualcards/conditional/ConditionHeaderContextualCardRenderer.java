package com.android.settings.homepage.contextualcards.conditional;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settings.homepage.contextualcards.ContextualCardRenderer;
import com.android.settings.homepage.contextualcards.ControllerRendererPool;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConditionHeaderContextualCardRenderer implements ContextualCardRenderer {
    public final Context mContext;
    public final ControllerRendererPool mControllerRendererPool;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConditionHeaderCardHolder extends RecyclerView.ViewHolder {
        public final LinearLayout icons;

        public ConditionHeaderCardHolder(View view) {
            super(view);
            this.icons = (LinearLayout) view.findViewById(R.id.header_icons_container);
        }
    }

    public ConditionHeaderContextualCardRenderer(
            Context context, ControllerRendererPool controllerRendererPool) {
        this.mContext = context;
        this.mControllerRendererPool = controllerRendererPool;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardRenderer
    public final void bindView(RecyclerView.ViewHolder viewHolder, ContextualCard contextualCard) {
        ConditionHeaderContextualCard conditionHeaderContextualCard =
                (ConditionHeaderContextualCard) contextualCard;
        final ConditionHeaderCardHolder conditionHeaderCardHolder =
                (ConditionHeaderCardHolder) viewHolder;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        final SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        conditionHeaderCardHolder.icons.removeAllViews();
        conditionHeaderContextualCard.mConditionalCards.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.homepage.contextualcards.conditional.ConditionHeaderContextualCardRenderer$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ConditionHeaderContextualCardRenderer
                                conditionHeaderContextualCardRenderer =
                                        ConditionHeaderContextualCardRenderer.this;
                        ConditionHeaderContextualCardRenderer.ConditionHeaderCardHolder
                                conditionHeaderCardHolder2 = conditionHeaderCardHolder;
                        ImageView imageView =
                                (ImageView)
                                        LayoutInflater.from(
                                                        conditionHeaderContextualCardRenderer
                                                                .mContext)
                                                .inflate(
                                                        R.layout.conditional_card_header_icon,
                                                        (ViewGroup)
                                                                conditionHeaderCardHolder2.icons,
                                                        false);
                        imageView.setImageDrawable(((ContextualCard) obj).mIconDrawable);
                        conditionHeaderCardHolder2.icons.addView(imageView);
                    }
                });
        conditionHeaderCardHolder.itemView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.homepage.contextualcards.conditional.ConditionHeaderContextualCardRenderer$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ConditionHeaderContextualCardRenderer
                                conditionHeaderContextualCardRenderer =
                                        ConditionHeaderContextualCardRenderer.this;
                        MetricsFeatureProvider metricsFeatureProvider2 = metricsFeatureProvider;
                        conditionHeaderContextualCardRenderer.getClass();
                        metricsFeatureProvider2.action(
                                0, 373, VolteConstants.ErrorCode.SERVER_ERROR, 1, null);
                        ConditionContextualCardController conditionContextualCardController =
                                (ConditionContextualCardController)
                                        conditionHeaderContextualCardRenderer
                                                .mControllerRendererPool.getController(
                                                conditionHeaderContextualCardRenderer.mContext, 4);
                        conditionContextualCardController.mIsExpanded = true;
                        conditionContextualCardController.onConditionsChanged();
                    }
                });
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardRenderer
    public final RecyclerView.ViewHolder createViewHolder(View view, int i) {
        return new ConditionHeaderCardHolder(view);
    }
}
