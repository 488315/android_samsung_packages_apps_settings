package com.android.settings.homepage.contextualcards;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.slice.ArrayUtils;
import androidx.slice.Slice;
import androidx.slice.SliceConvert;
import androidx.slice.SliceMetadata;
import androidx.slice.SliceViewManagerWrapper;
import androidx.slice.core.SliceAction;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.widget.RowContent;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.utils.ThreadUtils;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EligibleCardChecker implements Callable {
    ContextualCard mCard;
    public final Context mContext;

    public EligibleCardChecker(Context context, ContextualCard contextualCard) {
        this.mContext = context;
        this.mCard = contextualCard;
    }

    public Slice bindSlice(Uri uri) {
        SliceViewManagerWrapper sliceViewManagerWrapper =
                new SliceViewManagerWrapper(this.mContext);
        EligibleCardChecker$$ExternalSyntheticLambda0
                eligibleCardChecker$$ExternalSyntheticLambda0 =
                        new EligibleCardChecker$$ExternalSyntheticLambda0();
        sliceViewManagerWrapper.registerSliceCallback(
                uri, eligibleCardChecker$$ExternalSyntheticLambda0);
        Slice wrap =
                sliceViewManagerWrapper.isAuthoritySuspended(uri.getAuthority())
                        ? null
                        : SliceConvert.wrap(
                                sliceViewManagerWrapper.mManager.bindSlice(
                                        uri, sliceViewManagerWrapper.mSpecs),
                                sliceViewManagerWrapper.mContext);
        ThreadUtils.postOnMainThread(
                new EligibleCardChecker$$ExternalSyntheticLambda1(
                        sliceViewManagerWrapper,
                        uri,
                        eligibleCardChecker$$ExternalSyntheticLambda0,
                        0));
        return wrap;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        ContextualCard contextualCard;
        long currentTimeMillis = System.currentTimeMillis();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        if (isCardEligibleToDisplay(this.mCard)) {
            metricsFeatureProvider.action(
                    0, 1686, VolteConstants.ErrorCode.SERVER_ERROR, 1, this.mCard.mSliceUri);
            contextualCard = this.mCard;
        } else {
            metricsFeatureProvider.action(
                    0, 1686, VolteConstants.ErrorCode.SERVER_ERROR, 0, this.mCard.mSliceUri);
            contextualCard = null;
        }
        ContextualCard contextualCard2 = contextualCard;
        metricsFeatureProvider.action(
                0,
                1684,
                VolteConstants.ErrorCode.SERVER_ERROR,
                (int) (System.currentTimeMillis() - currentTimeMillis),
                this.mCard.mSliceUri);
        return contextualCard2;
    }

    public boolean isCardEligibleToDisplay(ContextualCard contextualCard) {
        if (contextualCard.mRankingScore < 0.0d) {
            return false;
        }
        Uri parse = Uri.parse(contextualCard.mSliceUri);
        if (!"content".equals(parse.getScheme())) {
            return false;
        }
        Slice bindSlice = bindSlice(parse);
        if (bindSlice == null || ArrayUtils.contains("error", bindSlice.mHints)) {
            Log.w("EligibleCardChecker", "Failed to bind slice, not eligible for display " + parse);
            return false;
        }
        ContextualCard.Builder builder = contextualCard.mBuilder;
        builder.mSlice = bindSlice;
        this.mCard = builder.build();
        if (isSliceToggleable(bindSlice)) {
            builder.mHasInlineAction = true;
            this.mCard = builder.build();
        }
        return true;
    }

    public boolean isSliceToggleable(Slice slice) {
        SliceMetadata from = SliceMetadata.from(this.mContext, slice);
        ArrayList arrayList = new ArrayList();
        SliceActionImpl sliceActionImpl = from.mPrimaryAction;
        if (sliceActionImpl == null || !sliceActionImpl.isToggle()) {
            List list = from.mSliceActions;
            if (list == null || list.size() <= 0) {
                RowContent rowContent = from.mHeaderContent;
                if (rowContent != null) {
                    arrayList.addAll(rowContent.mToggleItems);
                }
            } else {
                for (int i = 0; i < from.mSliceActions.size(); i++) {
                    SliceAction sliceAction = (SliceAction) from.mSliceActions.get(i);
                    if (sliceAction.isToggle()) {
                        arrayList.add(sliceAction);
                    }
                }
            }
        } else {
            arrayList.add(sliceActionImpl);
        }
        return !arrayList.isEmpty();
    }
}
