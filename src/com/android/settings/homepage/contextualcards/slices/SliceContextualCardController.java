package com.android.settings.homepage.contextualcards.slices;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.homepage.contextualcards.CardContentProvider;
import com.android.settings.homepage.contextualcards.CardDatabaseHelper;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settings.homepage.contextualcards.ContextualCardController;
import com.android.settings.homepage.contextualcards.ContextualCardFeatureProviderImpl;
import com.android.settings.homepage.contextualcards.ContextualCardFeedbackDialog;
import com.android.settings.homepage.contextualcards.ContextualCardUpdateListener;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.utils.ThreadUtils;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SliceContextualCardController implements ContextualCardController {
    public final Context mContext;

    public SliceContextualCardController(Context context) {
        this.mContext = context;
    }

    public boolean isFeedbackEnabled(String str) {
        return !TextUtils.isEmpty(str) && Build.IS_DEBUGGABLE;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void onDismissed(final ContextualCard contextualCard) {
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.homepage.contextualcards.slices.SliceContextualCardController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SliceContextualCardController sliceContextualCardController =
                                SliceContextualCardController.this;
                        ContextualCard contextualCard2 = contextualCard;
                        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                        if (featureFactoryImpl == null) {
                            sliceContextualCardController.getClass();
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                        Context context = sliceContextualCardController.mContext;
                        Intrinsics.checkNotNullParameter(context, "context");
                        ContextualCardFeatureProviderImpl contextualCardFeatureProviderImpl =
                                (ContextualCardFeatureProviderImpl)
                                        featureFactoryImpl.contextualCardFeatureProvider$delegate
                                                .getValue();
                        Context context2 = sliceContextualCardController.mContext;
                        String str = contextualCard2.mName;
                        SQLiteDatabase writableDatabase =
                                CardDatabaseHelper.getInstance(
                                                contextualCardFeatureProviderImpl.mContext)
                                        .getWritableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(
                                "dismissed_timestamp", Long.valueOf(System.currentTimeMillis()));
                        writableDatabase.update(
                                "cards", contentValues, "name=?", new String[] {str});
                        context2.getContentResolver()
                                .notifyChange(CardContentProvider.DELETE_CARD_URI, null);
                    }
                });
        showFeedbackDialog(contextualCard);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getMetricsFeatureProvider()
                .action(
                        this.mContext,
                        1665,
                        contextualCard.mSliceUri + "|" + contextualCard.mRankingScore);
    }

    public void showFeedbackDialog(ContextualCard contextualCard) {
        String string = this.mContext.getString(R.string.config_contextual_card_feedback_email);
        if (isFeedbackEnabled(string)) {
            Intent intent =
                    new Intent(this.mContext, (Class<?>) ContextualCardFeedbackDialog.class);
            intent.putExtra("card_name", contextualCard.mName.split("/")[r5.length - 1]);
            intent.putExtra("feedback_email", string);
            intent.addFlags(268435456);
            this.mContext.startActivity(intent);
        }
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void onActionClick(ContextualCard contextualCard) {}

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void onPrimaryClick(ContextualCard contextualCard) {}

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void setCardUpdateListener(
            ContextualCardUpdateListener contextualCardUpdateListener) {}
}
