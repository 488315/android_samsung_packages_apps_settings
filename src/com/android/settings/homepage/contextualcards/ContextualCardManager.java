package com.android.settings.homepage.contextualcards;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.homepage.contextualcards.logging.ContextualCardLogUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ContextualCardManager
        implements ContextualCardUpdateListener, LifecycleObserver, OnSaveInstanceState {
    static final long CARD_CONTENT_LOADER_TIMEOUT_MS = 1000;
    static final String KEY_CONTEXTUAL_CARDS = "key_contextual_cards";
    static final String KEY_GLOBAL_CARD_LOADER_TIMEOUT = "global_card_loader_timeout_key";
    public final Context mContext;
    boolean mIsFirstLaunch;
    public final Lifecycle mLifecycle;
    public ContextualCardUpdateListener mListener;
    List<String> mSavedCards;
    long mStartTime;
    final List<ContextualCard> mContextualCards = new ArrayList();
    public final List mLifecycleObservers = new ArrayList();
    final ControllerRendererPool mControllerRendererPool = new ControllerRendererPool();

    public ContextualCardManager(Context context, Lifecycle lifecycle, Bundle bundle) {
        this.mContext = context;
        this.mLifecycle = lifecycle;
        lifecycle.addObserver(this);
        if (bundle == null) {
            this.mIsFirstLaunch = true;
            this.mSavedCards = null;
        } else {
            this.mSavedCards = bundle.getStringArrayList(KEY_CONTEXTUAL_CARDS);
        }
        for (int i : getSettingsCards()) {
            setupController(i);
        }
    }

    public long getCardLoaderTimeout() {
        return Settings.Global.getLong(
                this.mContext.getContentResolver(), KEY_GLOBAL_CARD_LOADER_TIMEOUT, 1000L);
    }

    public List<ContextualCard> getCardsToKeep(List<ContextualCard> list) {
        if (this.mSavedCards == null) {
            final int i = 1;
            return (List)
                    list.stream()
                            .filter(
                                    new Predicate(
                                            this) { // from class:
                                                    // com.android.settings.homepage.contextualcards.ContextualCardManager$$ExternalSyntheticLambda6
                                        public final /* synthetic */ ContextualCardManager f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            int i2 = i;
                                            ContextualCardManager contextualCardManager = this.f$0;
                                            ContextualCard contextualCard = (ContextualCard) obj;
                                            switch (i2) {
                                                case 0:
                                                    return contextualCardManager.mSavedCards
                                                            .contains(contextualCard.mName);
                                                default:
                                                    return contextualCardManager.mContextualCards
                                                            .contains(contextualCard);
                                            }
                                        }
                                    })
                            .collect(Collectors.toList());
        }
        final int i2 = 0;
        List<ContextualCard> list2 =
                (List)
                        list.stream()
                                .filter(
                                        new Predicate(
                                                this) { // from class:
                                                        // com.android.settings.homepage.contextualcards.ContextualCardManager$$ExternalSyntheticLambda6
                                            public final /* synthetic */ ContextualCardManager f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                int i22 = i2;
                                                ContextualCardManager contextualCardManager =
                                                        this.f$0;
                                                ContextualCard contextualCard =
                                                        (ContextualCard) obj;
                                                switch (i22) {
                                                    case 0:
                                                        return contextualCardManager.mSavedCards
                                                                .contains(contextualCard.mName);
                                                    default:
                                                        return contextualCardManager
                                                                .mContextualCards.contains(
                                                                contextualCard);
                                                }
                                            }
                                        })
                                .collect(Collectors.toList());
        this.mSavedCards = null;
        return list2;
    }

    public List<ContextualCard> getCardsWithViewType(List<ContextualCard> list) {
        if (list.isEmpty()) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list);
        for (int i = 0; i < arrayList.size(); i++) {
            ContextualCard contextualCard = list.get(i);
            if (contextualCard.mCategory == 6) {
                ContextualCard.Builder builder = contextualCard.mBuilder;
                builder.mViewType = R.layout.contextual_slice_sticky_tile;
                arrayList.set(i, builder.build());
            }
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        int i2 = 1;
        while (i2 < arrayList2.size()) {
            int i3 = i2 - 1;
            ContextualCard contextualCard2 = (ContextualCard) arrayList2.get(i3);
            ContextualCard contextualCard3 = (ContextualCard) arrayList2.get(i2);
            if (contextualCard3.mCategory == 1 && contextualCard2.mCategory == 1) {
                ContextualCard.Builder builder2 = contextualCard2.mBuilder;
                builder2.mViewType = R.layout.contextual_slice_half_tile;
                arrayList2.set(i3, builder2.build());
                ContextualCard.Builder builder3 = contextualCard3.mBuilder;
                builder3.mViewType = R.layout.contextual_slice_half_tile;
                arrayList2.set(i2, builder3.build());
                i2++;
            }
            i2++;
        }
        return arrayList2;
    }

    public int[] getSettingsCards() {
        return new int[] {2};
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardUpdateListener
    public final void onContextualCardUpdated(Map map) {
        List list;
        final Set keySet = map.keySet();
        if (keySet.isEmpty()) {
            final Set of = Set.of(3, 4, 5);
            final int i = 0;
            list =
                    (List)
                            this.mContextualCards.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.android.settings.homepage.contextualcards.ContextualCardManager$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    int i2 = i;
                                                    Set set = of;
                                                    ContextualCard contextualCard =
                                                            (ContextualCard) obj;
                                                    switch (i2) {
                                                        case 0:
                                                            return set.contains(
                                                                    Integer.valueOf(
                                                                            contextualCard
                                                                                    .getCardType()));
                                                        default:
                                                            return !set.contains(
                                                                    Integer.valueOf(
                                                                            contextualCard
                                                                                    .getCardType()));
                                                    }
                                                }
                                            })
                                    .collect(Collectors.toList());
        } else {
            final int i2 = 1;
            list =
                    (List)
                            this.mContextualCards.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.android.settings.homepage.contextualcards.ContextualCardManager$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    int i22 = i2;
                                                    Set set = keySet;
                                                    ContextualCard contextualCard =
                                                            (ContextualCard) obj;
                                                    switch (i22) {
                                                        case 0:
                                                            return set.contains(
                                                                    Integer.valueOf(
                                                                            contextualCard
                                                                                    .getCardType()));
                                                        default:
                                                            return !set.contains(
                                                                    Integer.valueOf(
                                                                            contextualCard
                                                                                    .getCardType()));
                                                    }
                                                }
                                            })
                                    .collect(Collectors.toList());
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(list);
        arrayList.addAll(
                (Collection)
                        map.values().stream()
                                .flatMap(
                                        new EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3())
                                .collect(Collectors.toList()));
        for (ContextualCard contextualCard : this.mContextualCards) {
            StringBuilder sb = new StringBuilder("card getCategory : ");
            sb.append(contextualCard.mCategory);
            sb.append(" , type : ");
            sb.append(contextualCard.getCardType());
            sb.append(" name : ");
            Utils$$ExternalSyntheticOutline0.m(sb, contextualCard.mName, "ContextualCardManager");
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ContextualCard contextualCard2 = (ContextualCard) it.next();
            StringBuilder sb2 = new StringBuilder("new card getCategory : ");
            sb2.append(contextualCard2.mCategory);
            sb2.append(" , type : ");
            sb2.append(contextualCard2.getCardType());
            sb2.append(" name : ");
            Utils$$ExternalSyntheticOutline0.m(sb2, contextualCard2.mName, "ContextualCardManager");
        }
        this.mContextualCards.clear();
        this.mContextualCards.addAll(getCardsWithViewType(sortCards(arrayList)));
        Iterator<ContextualCard> it2 = this.mContextualCards.iterator();
        while (it2.hasNext()) {
            setupController(it2.next().getCardType());
        }
        if (this.mListener != null) {
            ArrayMap arrayMap = new ArrayMap();
            arrayMap.put(0, this.mContextualCards);
            this.mListener.onContextualCardUpdated(arrayMap);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnSaveInstanceState
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putStringArrayList(
                KEY_CONTEXTUAL_CARDS,
                (ArrayList)
                        this.mContextualCards.stream()
                                .map(new ContextualCardManager$$ExternalSyntheticLambda2(0))
                                .collect(
                                        Collectors.toCollection(
                                                new ContextualCardManager$$ExternalSyntheticLambda3())));
    }

    public void setupController(int i) {
        ContextualCardController controller =
                this.mControllerRendererPool.getController(this.mContext, i);
        if (controller == null) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    i, "Cannot find ContextualCardController for type ", "ContextualCardManager");
            return;
        }
        controller.setCardUpdateListener(this);
        if (!(controller instanceof LifecycleObserver)
                || ((ArrayList) this.mLifecycleObservers).contains(controller)) {
            return;
        }
        LifecycleObserver lifecycleObserver = (LifecycleObserver) controller;
        ((ArrayList) this.mLifecycleObservers).add(lifecycleObserver);
        this.mLifecycle.addObserver(lifecycleObserver);
    }

    public List<ContextualCard> sortCards(List<ContextualCard> list) {
        List<ContextualCard> list2 =
                (List)
                        list.stream()
                                .sorted(new ContextualCardManager$$ExternalSyntheticLambda4())
                                .collect(Collectors.toList());
        List list3 =
                (List)
                        list2.stream()
                                .filter(new ContextualCardManager$$ExternalSyntheticLambda5())
                                .collect(Collectors.toList());
        list2.removeAll(list3);
        list2.addAll(list3);
        return list2;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CardContentLoaderCallbacks implements LoaderManager.LoaderCallbacks {
        public Context mContext;
        public ContextualCardManager mListener;

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final Loader onCreateLoader(int i, Bundle bundle) {
            if (i == 1) {
                return new ContextualCardLoader(this.mContext);
            }
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Unknown loader id: "));
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoadFinished(Loader loader, Object obj) {
            List<ContextualCard> list = (List) obj;
            ContextualCardManager contextualCardManager = this.mListener;
            if (contextualCardManager != null) {
                long currentTimeMillis =
                        System.currentTimeMillis() - contextualCardManager.mStartTime;
                Log.d("ContextualCardManager", "Total loading time = " + currentTimeMillis);
                List<ContextualCard> cardsToKeep = contextualCardManager.getCardsToKeep(list);
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                SettingsMetricsFeatureProvider metricsFeatureProvider =
                        featureFactoryImpl.getMetricsFeatureProvider();
                if (!contextualCardManager.mIsFirstLaunch) {
                    contextualCardManager.onContextualCardUpdated(
                            (Map)
                                    cardsToKeep.stream()
                                            .collect(
                                                    Collectors.groupingBy(
                                                            new ContextualCardManager$$ExternalSyntheticLambda2(
                                                                    1))));
                    metricsFeatureProvider.action(
                            contextualCardManager.mContext,
                            1663,
                            ContextualCardLogUtils.buildCardListLog(cardsToKeep));
                    return;
                }
                if (currentTimeMillis <= contextualCardManager.getCardLoaderTimeout()) {
                    contextualCardManager.onContextualCardUpdated(
                            (Map)
                                    list.stream()
                                            .collect(
                                                    Collectors.groupingBy(
                                                            new ContextualCardManager$$ExternalSyntheticLambda2(
                                                                    1))));
                    metricsFeatureProvider.action(
                            contextualCardManager.mContext,
                            1663,
                            ContextualCardLogUtils.buildCardListLog(list));
                } else {
                    metricsFeatureProvider.action(
                            0,
                            1685,
                            VolteConstants.ErrorCode.SERVER_ERROR,
                            (int) currentTimeMillis,
                            null);
                }
                metricsFeatureProvider.action(
                        contextualCardManager.mContext,
                        1662,
                        (int) (System.currentTimeMillis() - contextualCardManager.mStartTime));
                contextualCardManager.mIsFirstLaunch = false;
            }
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoaderReset(Loader loader) {}
    }
}
