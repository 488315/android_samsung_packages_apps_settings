package com.android.settings.homepage.contextualcards.legacysuggestion;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.core.view.SeslTouchTargetDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.datetime.timezone.TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settings.homepage.contextualcards.ContextualCardController;
import com.android.settings.homepage.contextualcards.ContextualCardRenderer;
import com.android.settings.homepage.contextualcards.ControllerRendererPool;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LegacySuggestionContextualCardRenderer implements ContextualCardRenderer {
    public final Context mContext;
    public final ControllerRendererPool mControllerRendererPool;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LegacySuggestionViewHolder extends RecyclerView.ViewHolder {
        public final View closeButton;
        public final TextView title;

        public LegacySuggestionViewHolder(final View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.closeButton = view.findViewById(com.android.settings.R.id.close_button);
            view.getViewTreeObserver()
                    .addOnGlobalLayoutListener(
                            new ViewTreeObserver
                                    .OnGlobalLayoutListener() { // from class:
                                                                // com.android.settings.homepage.contextualcards.legacysuggestion.LegacySuggestionContextualCardRenderer.1
                                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                                public final void onGlobalLayout() {
                                    final View findViewById =
                                            view.findViewById(
                                                    com.android.settings.R.id.suggestion_conatiner);
                                    if (findViewById == null) {
                                        return;
                                    }
                                    findViewById.post(
                                            new Runnable() { // from class:
                                                             // com.android.settings.homepage.contextualcards.legacysuggestion.LegacySuggestionContextualCardRenderer.1.1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    View findViewById2 =
                                                            view.findViewById(
                                                                    com.android.settings.R.id
                                                                            .close_button);
                                                    if (findViewById2 == null) {
                                                        return;
                                                    }
                                                    SeslTouchTargetDelegate
                                                            seslTouchTargetDelegate =
                                                                    new SeslTouchTargetDelegate(
                                                                            findViewById);
                                                    Rect rect = new Rect();
                                                    Rect rect2 = new Rect();
                                                    findViewById.getHitRect(rect);
                                                    findViewById2.getHitRect(rect2);
                                                    int i = rect.right - rect2.right;
                                                    int min =
                                                            Math.min(
                                                                            findViewById2
                                                                                    .getWidth(),
                                                                            findViewById2
                                                                                    .getHeight())
                                                                    / 2;
                                                    seslTouchTargetDelegate.addTouchDelegate(
                                                            findViewById2,
                                                            SeslTouchTargetDelegate.ExtraInsets.of(
                                                                    min, min, i, min));
                                                    findViewById.setTouchDelegate(
                                                            seslTouchTargetDelegate);
                                                }
                                            });
                                    findViewById
                                            .getViewTreeObserver()
                                            .removeOnGlobalLayoutListener(this);
                                }
                            });
        }
    }

    public LegacySuggestionContextualCardRenderer(
            Context context, ControllerRendererPool controllerRendererPool) {
        this.mContext = context;
        this.mControllerRendererPool = controllerRendererPool;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardRenderer
    public final void bindView(
            RecyclerView.ViewHolder viewHolder, final ContextualCard contextualCard) {
        LegacySuggestionViewHolder legacySuggestionViewHolder =
                (LegacySuggestionViewHolder) viewHolder;
        final ContextualCardController controller =
                this.mControllerRendererPool.getController(
                        this.mContext, contextualCard.getCardType());
        legacySuggestionViewHolder.title.setText(contextualCard.mSummaryText);
        final int i = 0;
        legacySuggestionViewHolder.itemView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.homepage.contextualcards.legacysuggestion.LegacySuggestionContextualCardRenderer$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                controller.onPrimaryClick(contextualCard);
                                break;
                            default:
                                controller.onDismissed(contextualCard);
                                break;
                        }
                    }
                });
        View view = legacySuggestionViewHolder.closeButton;
        StringBuilder sb = new StringBuilder();
        TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                this.mContext, com.android.settings.R.string.dlg_close, sb, " ");
        sb.append(this.mContext.getString(com.android.settings.R.string.button_tts));
        view.setContentDescription(sb.toString());
        final int i2 = 1;
        legacySuggestionViewHolder.closeButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.homepage.contextualcards.legacysuggestion.LegacySuggestionContextualCardRenderer$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        switch (i2) {
                            case 0:
                                controller.onPrimaryClick(contextualCard);
                                break;
                            default:
                                controller.onDismissed(contextualCard);
                                break;
                        }
                    }
                });
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardRenderer
    public final RecyclerView.ViewHolder createViewHolder(View view, int i) {
        return new LegacySuggestionViewHolder(view);
    }
}
