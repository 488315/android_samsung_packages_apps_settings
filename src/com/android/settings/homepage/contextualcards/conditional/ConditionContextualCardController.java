package com.android.settings.homepage.contextualcards.conditional;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settings.homepage.contextualcards.ContextualCardController;
import com.android.settings.homepage.contextualcards.ContextualCardUpdateListener;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConditionContextualCardController
        implements ContextualCardController, LifecycleObserver, OnStart, OnStop {
    public final ConditionManager mConditionManager;
    public final Context mContext;
    public boolean mIsExpanded;
    public ContextualCardUpdateListener mListener;

    public ConditionContextualCardController(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        ConditionManager conditionManager = new ConditionManager(applicationContext, this);
        this.mConditionManager = conditionManager;
        conditionManager.startMonitoringStateChange();
    }

    public Map<Integer, List<ContextualCard>> buildConditionalCardsWithFooterOrHeader(
            List<ContextualCard> list) {
        List list2;
        List list3;
        List list4;
        ArrayMap arrayMap = new ArrayMap();
        if (list.isEmpty() || (list.size() > 0 && !this.mIsExpanded)) {
            list2 = Collections.EMPTY_LIST;
        } else {
            list2 = (List) list.stream().collect(Collectors.toList());
            if (list2.size() % 2 == 1) {
                int size = list2.size() - 1;
                ContextualCard.Builder builder =
                        ((ConditionalContextualCard) list2.get(size)).mBuilder;
                builder.mViewType = R.layout.conditional_card_full_tile;
                list2.set(size, builder.build());
            }
        }
        arrayMap.put(3, list2);
        if (list.isEmpty() || !this.mIsExpanded || list.size() <= 0) {
            list3 = Collections.EMPTY_LIST;
        } else {
            ArrayList arrayList = new ArrayList();
            ConditionFooterContextualCard.Builder builder2 =
                    new ConditionFooterContextualCard.Builder();
            builder2.mName = "condition_footer";
            builder2.mRankingScore = -99999.0d;
            builder2.mViewType = R.layout.conditional_card_footer;
            arrayList.add(new ConditionFooterContextualCard(builder2));
            list3 = arrayList;
        }
        arrayMap.put(5, list3);
        if (list.isEmpty() || this.mIsExpanded || list.size() <= 0) {
            list4 = Collections.EMPTY_LIST;
        } else {
            ArrayList arrayList2 = new ArrayList();
            ConditionHeaderContextualCard.Builder builder3 =
                    new ConditionHeaderContextualCard.Builder();
            builder3.mConditionalCards = list;
            builder3.mName = "condition_header";
            builder3.mRankingScore = -99999.0d;
            builder3.mViewType = R.layout.conditional_card_header;
            arrayList2.add(new ConditionHeaderContextualCard(builder3));
            list4 = arrayList2;
        }
        arrayMap.put(4, list4);
        return arrayMap;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void onActionClick(ContextualCard contextualCard) {
        this.mConditionManager
                .getController(((ConditionalContextualCard) contextualCard).mConditionId)
                .onActionClick();
    }

    public final void onConditionsChanged() {
        if (this.mListener == null) {
            return;
        }
        ConditionManager conditionManager = this.mConditionManager;
        conditionManager.getClass();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<ConditionalCardController> it = conditionManager.mCardControllers.iterator();
        while (it.hasNext()) {
            arrayList2.add(
                    ((MoreExecutors.ListeningDecorator) ThreadUtils.getBackgroundExecutor())
                            .submit(
                                    (Callable)
                                            new ConditionManager.DisplayableChecker(
                                                    conditionManager.getController(
                                                            it.next().getId()))));
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            try {
                ContextualCard contextualCard =
                        (ContextualCard) ((Future) it2.next()).get(20L, TimeUnit.MILLISECONDS);
                if (contextualCard != null) {
                    arrayList.add(contextualCard);
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                Log.w(
                        "ConditionManager",
                        "Failed to get displayable state for card, likely timeout. Skipping",
                        e);
            }
        }
        this.mListener.onContextualCardUpdated(buildConditionalCardsWithFooterOrHeader(arrayList));
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void onPrimaryClick(ContextualCard contextualCard) {
        this.mConditionManager
                .getController(((ConditionalContextualCard) contextualCard).mConditionId)
                .onPrimaryClick(this.mContext);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        this.mConditionManager.startMonitoringStateChange();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        ConditionManager conditionManager = this.mConditionManager;
        if (!conditionManager.mIsListeningToStateChange) {
            Log.d("ConditionManager", "Not listening to condition state changes, skipping");
            return;
        }
        Iterator<ConditionalCardController> it = conditionManager.mCardControllers.iterator();
        while (it.hasNext()) {
            it.next().stopMonitoringStateChange();
        }
        conditionManager.mIsListeningToStateChange = false;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void setCardUpdateListener(
            ContextualCardUpdateListener contextualCardUpdateListener) {
        this.mListener = contextualCardUpdateListener;
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void onDismissed(ContextualCard contextualCard) {}
}
