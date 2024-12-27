package com.android.settings.homepage.contextualcards.conditional;

import android.content.Context;
import android.util.Log;

import androidx.viewpager2.widget.ViewPager2$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConditionManager {
    final List<ConditionalCardController> mCardControllers;
    public boolean mIsListeningToStateChange;
    public final ConditionContextualCardController mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DisplayableChecker implements Callable {
        public final ConditionalCardController mController;

        public DisplayableChecker(ConditionalCardController conditionalCardController) {
            this.mController = conditionalCardController;
        }

        @Override // java.util.concurrent.Callable
        public final Object call() {
            ConditionalCardController conditionalCardController = this.mController;
            if (conditionalCardController.isDisplayable()) {
                return conditionalCardController.buildContextualCard();
            }
            return null;
        }
    }

    public ConditionManager(
            Context context, ConditionContextualCardController conditionContextualCardController) {
        Context applicationContext = context.getApplicationContext();
        ArrayList arrayList = new ArrayList();
        this.mCardControllers = arrayList;
        this.mListener = conditionContextualCardController;
        arrayList.add(new AirplaneModeConditionController(applicationContext, this));
        arrayList.add(new BackgroundDataConditionController(applicationContext, this));
        arrayList.add(new BatterySaverConditionController(applicationContext, this));
        arrayList.add(new CellularDataConditionController(applicationContext, this));
        arrayList.add(new DndConditionCardController(applicationContext, this));
        arrayList.add(new HotspotConditionController(applicationContext, this));
        arrayList.add(new NightDisplayConditionController(applicationContext, this));
        arrayList.add(new RingerVibrateConditionController(applicationContext, this));
        arrayList.add(new RingerMutedConditionController(applicationContext, this));
        arrayList.add(new WorkModeConditionController(applicationContext, this));
        arrayList.add(new GrayscaleConditionController(applicationContext, this));
    }

    public final ConditionalCardController getController(long j) {
        for (ConditionalCardController conditionalCardController : this.mCardControllers) {
            if (conditionalCardController.getId() == j) {
                return conditionalCardController;
            }
        }
        throw new IllegalStateException(
                ViewPager2$$ExternalSyntheticOutline0.m(j, "Cannot find controller for "));
    }

    public final void onConditionChanged() {
        ConditionContextualCardController conditionContextualCardController = this.mListener;
        if (conditionContextualCardController != null) {
            conditionContextualCardController.onConditionsChanged();
        }
    }

    public final void startMonitoringStateChange() {
        if (this.mIsListeningToStateChange) {
            Log.d(
                    "ConditionManager",
                    "Already listening to condition state changes, skipping monitor setup");
        } else {
            this.mIsListeningToStateChange = true;
            Iterator<ConditionalCardController> it = this.mCardControllers.iterator();
            while (it.hasNext()) {
                it.next().startMonitoringStateChange();
            }
        }
        onConditionChanged();
    }
}
