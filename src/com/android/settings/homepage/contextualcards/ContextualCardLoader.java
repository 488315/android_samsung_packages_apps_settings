package com.android.settings.homepage.contextualcards;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.homepage.contextualcards.logging.ContextualCardLogUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settingslib.utils.AsyncLoaderCompat;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.MoreExecutors;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ContextualCardLoader extends AsyncLoaderCompat {
    static final String CONTEXTUAL_CARD_COUNT = "contextual_card_count";
    static final int DEFAULT_CARD_COUNT = 3;
    public final Context mContext;
    Uri mNotifyUri;
    public final AnonymousClass1 mObserver;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.homepage.contextualcards.ContextualCardLoader$1] */
    public ContextualCardLoader(Context context) {
        super(context);
        this.mObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.homepage.contextualcards.ContextualCardLoader.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        ContextualCardLoader contextualCardLoader = ContextualCardLoader.this;
                        if (contextualCardLoader.mStarted) {
                            contextualCardLoader.mNotifyUri = uri;
                            contextualCardLoader.onForceLoad();
                        }
                    }
                };
        this.mContext = context.getApplicationContext();
    }

    public List<ContextualCard> filterEligibleCards(List<ContextualCard> list) {
        if (list.isEmpty()) {
            return list;
        }
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(list.size());
        ArrayList arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        try {
            arrayList2 =
                    newFixedThreadPool.invokeAll(
                            (List)
                                    list.stream()
                                            .map(
                                                    new Function() { // from class:
                                                                     // com.android.settings.homepage.contextualcards.ContextualCardLoader$$ExternalSyntheticLambda2
                                                        @Override // java.util.function.Function
                                                        public final Object apply(Object obj) {
                                                            return new EligibleCardChecker(
                                                                    ContextualCardLoader.this
                                                                            .mContext,
                                                                    (ContextualCard) obj);
                                                        }
                                                    })
                                            .collect(Collectors.toList()),
                            400L,
                            TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Log.w("ContextualCardLoader", "Failed to get eligible states for all cards", e);
        }
        newFixedThreadPool.shutdown();
        for (int i = 0; i < arrayList2.size(); i++) {
            Future future = (Future) arrayList2.get(i);
            if (future.isCancelled()) {
                Log.w(
                        "ContextualCardLoader",
                        "Timeout getting eligible state for card: "
                                + Uri.parse(list.get(i).mSliceUri));
            } else {
                try {
                    ContextualCard contextualCard = (ContextualCard) future.get();
                    if (contextualCard != null) {
                        arrayList.add(contextualCard);
                    }
                } catch (Exception e2) {
                    Log.w("ContextualCardLoader", "Failed to get eligible state for card", e2);
                }
            }
        }
        return arrayList;
    }

    public int getCardCount() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), CONTEXTUAL_CARD_COUNT, 3);
    }

    public Cursor getContextualCardsFromProvider() {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        Context context = this.mContext;
        Intrinsics.checkNotNullParameter(context, "context");
        final ContextualCardFeatureProviderImpl contextualCardFeatureProviderImpl =
                (ContextualCardFeatureProviderImpl)
                        featureFactoryImpl.contextualCardFeatureProvider$delegate.getValue();
        SQLiteDatabase readableDatabase =
                CardDatabaseHelper.getInstance(contextualCardFeatureProviderImpl.mContext)
                        .getReadableDatabase();
        final long currentTimeMillis = System.currentTimeMillis() - 86400000;
        Cursor query =
                readableDatabase.query(
                        "cards",
                        null,
                        "dismissed_timestamp < ? OR dismissed_timestamp IS NULL",
                        new String[] {String.valueOf(currentTimeMillis)},
                        null,
                        null,
                        "score DESC");
        ((MoreExecutors.ListeningDecorator) ThreadUtils.getBackgroundExecutor())
                .submit(
                        new Callable() { // from class:
                                         // com.android.settings.homepage.contextualcards.ContextualCardFeatureProviderImpl$$ExternalSyntheticLambda0
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                return Integer.valueOf(
                                        ContextualCardFeatureProviderImpl.this.resetDismissedTime(
                                                currentTimeMillis));
                            }
                        });
        return query;
    }

    public List<ContextualCard> getDisplayableCards(List<ContextualCard> list) {
        List<ContextualCard> filterEligibleCards = filterEligibleCards(list);
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        final int cardCount = getCardCount();
        final int i = 0;
        filterEligibleCards.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.homepage.contextualcards.ContextualCardLoader$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i) {
                            case 0:
                                List list2 = arrayList;
                                int i2 = cardCount;
                                List list3 = arrayList3;
                                ContextualCard contextualCard = (ContextualCard) obj;
                                if (contextualCard.mCategory == 6) {
                                    if (list2.size() >= i2) {
                                        list3.add(contextualCard);
                                        break;
                                    } else {
                                        list2.add(contextualCard);
                                        break;
                                    }
                                }
                                break;
                            default:
                                List list4 = arrayList;
                                int i3 = cardCount;
                                List list5 = arrayList3;
                                ContextualCard contextualCard2 = (ContextualCard) obj;
                                if (contextualCard2.mCategory != 6) {
                                    if (list4.size() >= i3) {
                                        list5.add(contextualCard2);
                                        break;
                                    } else {
                                        list4.add(contextualCard2);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                });
        final int size = cardCount - arrayList.size();
        final int i2 = 1;
        filterEligibleCards.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.homepage.contextualcards.ContextualCardLoader$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i2) {
                            case 0:
                                List list2 = arrayList2;
                                int i22 = size;
                                List list3 = arrayList3;
                                ContextualCard contextualCard = (ContextualCard) obj;
                                if (contextualCard.mCategory == 6) {
                                    if (list2.size() >= i22) {
                                        list3.add(contextualCard);
                                        break;
                                    } else {
                                        list2.add(contextualCard);
                                        break;
                                    }
                                }
                                break;
                            default:
                                List list4 = arrayList2;
                                int i3 = size;
                                List list5 = arrayList3;
                                ContextualCard contextualCard2 = (ContextualCard) obj;
                                if (contextualCard2.mCategory != 6) {
                                    if (list4.size() >= i3) {
                                        list5.add(contextualCard2);
                                        break;
                                    } else {
                                        list4.add(contextualCard2);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                });
        arrayList2.addAll(arrayList);
        if (!CardContentProvider.DELETE_CARD_URI.equals(this.mNotifyUri)) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl
                    .getMetricsFeatureProvider()
                    .action(
                            this.mContext,
                            1664,
                            ContextualCardLogUtils.buildCardListLog(arrayList3));
        }
        return arrayList2;
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        ArrayList arrayList = new ArrayList();
        if (this.mContext.getResources().getBoolean(R.bool.config_use_legacy_suggestion)) {
            Log.d("ContextualCardLoader", "Skipping - in legacy suggestion mode");
            return arrayList;
        }
        Cursor contextualCardsFromProvider = getContextualCardsFromProvider();
        try {
            if (contextualCardsFromProvider.getCount() > 0) {
                contextualCardsFromProvider.moveToFirst();
                while (!contextualCardsFromProvider.isAfterLast()) {
                    ContextualCard contextualCard = new ContextualCard(contextualCardsFromProvider);
                    if (Uri.parse(contextualCard.mSliceUri)
                            .equals(CustomSliceRegistry.BLUETOOTH_DEVICES_SLICE_URI)) {
                        ContextualCard.Builder builder = contextualCard.mBuilder;
                        builder.mIsLargeCard = true;
                        arrayList.add(builder.build());
                    } else {
                        arrayList.add(contextualCard);
                    }
                    contextualCardsFromProvider.moveToNext();
                }
            }
            contextualCardsFromProvider.close();
            return getDisplayableCards(arrayList);
        } catch (Throwable th) {
            if (contextualCardsFromProvider != null) {
                try {
                    contextualCardsFromProvider.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // com.android.settingslib.utils.AsyncLoaderCompat
    public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {}

    @Override // com.android.settingslib.utils.AsyncLoaderCompat, androidx.loader.content.Loader
    public final void onStartLoading() {
        super.onStartLoading();
        this.mNotifyUri = null;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri uri = CardContentProvider.REFRESH_CARD_URI;
        AnonymousClass1 anonymousClass1 = this.mObserver;
        contentResolver.registerContentObserver(uri, false, anonymousClass1);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        CardContentProvider.DELETE_CARD_URI, false, anonymousClass1);
    }

    @Override // com.android.settingslib.utils.AsyncLoaderCompat, androidx.loader.content.Loader
    public final void onStopLoading() {
        onCancelLoad();
        this.mContext.getContentResolver().unregisterContentObserver(this.mObserver);
    }
}
