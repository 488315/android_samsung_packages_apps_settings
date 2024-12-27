package com.samsung.android.settings.uwb.labs.view.statistics;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.settings.uwb.labs.common.UwbStateChangedHandler;
import com.samsung.android.settings.uwb.labs.common.UwbStateChangedListener;
import com.samsung.android.settings.uwb.labs.control.statistics.StatisticsSummaryController;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class StatisticsSummaryFragmentController {
    public final Context mContext;
    public PreferenceViewHolder mHolder;
    public final StatisticsSummaryController mStatisticsControl;

    public StatisticsSummaryFragmentController(Context context) {
        Log.i("StatisticsSummaryFragmentController", "CREATE: StatisticsSummaryFragmentController");
        this.mContext = context;
        this.mStatisticsControl = new StatisticsSummaryController(context);
        UwbStateChangedHandler uwbStateChangedHandler = UwbStateChangedHandler.getInstance();
        ((ArrayList) uwbStateChangedHandler.listeners)
                .add(
                        new UwbStateChangedListener() { // from class:
                                                        // com.samsung.android.settings.uwb.labs.view.statistics.StatisticsSummaryFragmentController.1
                            @Override // com.samsung.android.settings.uwb.labs.common.UwbStateChangedListener
                            public final void onUpdatedState(final int i, int i2) {
                                Log.i(
                                        "StatisticsSummaryFragmentController",
                                        "onUpdatedState in StatisticsSummaryFragmentController");
                                StatisticsSummaryFragmentController
                                        statisticsSummaryFragmentController =
                                                StatisticsSummaryFragmentController.this;
                                if (statisticsSummaryFragmentController.mHolder != null) {
                                    ((Activity) statisticsSummaryFragmentController.mContext)
                                            .runOnUiThread(
                                                    new Runnable() { // from class:
                                                                     // com.samsung.android.settings.uwb.labs.view.statistics.StatisticsSummaryFragmentController.1.1
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            StatisticsSummaryFragmentController.this
                                                                    .startAnimation(i);
                                                        }
                                                    });
                                }
                            }
                        });
    }

    public final void startAnimation(int i) {
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) this.mHolder.findViewById(R.id.connection_status_animation);
        if (i == 1) {
            lottieAnimationView.setAnimation(R.raw.sec_uwb_labs_ani_connected);
        } else if (i != 2) {
            lottieAnimationView.setAnimation(R.raw.sec_uwb_labs_ani_disabled);
        } else {
            lottieAnimationView.setAnimation(R.raw.sec_uwb_labs_ani_enabled);
        }
        lottieAnimationView.setRepeatCount(-1);
        lottieAnimationView.playAnimation$1();
    }
}
