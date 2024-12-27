package com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.google.android.flexbox.FlexLine;
import com.google.android.flexbox.FlexboxLayout;
import com.samsung.android.settings.analyzestorage.data.model.RecommendCardInfo;
import com.samsung.android.settings.analyzestorage.presenter.controllers.RecommendCardController;
import com.samsung.android.settings.analyzestorage.presenter.managers.AsRecommendCardInfoManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.FeatureManager$UiFeature;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RecommendCardViewPagerAdapter extends RecyclerView.Adapter {
    public final ValueAnimator appearAnimator;
    public final Context context;
    public final RecommendCardController controller;
    public final ValueAnimator disappearAnimator;
    public final SparseArray holderArray;
    public boolean isLandScape;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PagerViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout confirmButtonContainer;
        public TextView confirmRecommendCardButton;
        public TextView descriptionRecommendCard;
        public NestedScrollView descriptionRecommendCardContainer;
        public LinearLayout linearLayout;
        public LinearLayout notNowButtonContainer;
        public TextView notNowRecommendCardButton;
        public LinearLayout recommendCardButtonContainer;
        public LinearLayout recommendCardMainContainer;
        public TextView recommendCardMainTitle;
        public FlexboxLayout recommendCardSubList;
    }

    public RecommendCardViewPagerAdapter(
            FragmentActivity activity, RecommendCardController recommendCardController) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.controller = recommendCardController;
        Context baseContext = activity.getBaseContext();
        Intrinsics.checkNotNullExpressionValue(baseContext, "getBaseContext(...)");
        this.context = baseContext;
        this.holderArray = new SparseArray();
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(150L);
        Intrinsics.checkNotNullExpressionValue(duration, "setDuration(...)");
        this.disappearAnimator = duration;
        ValueAnimator duration2 = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(150L);
        Intrinsics.checkNotNullExpressionValue(duration2, "setDuration(...)");
        this.appearAnimator = duration2;
    }

    public static void initButtonShape(int i, TextView textView) {
        Context context = textView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        textView.setBackgroundResource(
                FeatureManager$UiFeature.isDefaultTheme(context)
                        ? R.drawable.as_recommend_card_button_shape
                        : R.drawable.as_recommend_card_theme_button_shape);
        Drawable background = textView.getBackground();
        Context context2 = textView.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        background.setAlpha(FeatureManager$UiFeature.isDefaultTheme(context2) ? 255 : 178);
        Resources resources = textView.getContext().getResources();
        Context context3 = textView.getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        textView.setTextColor(
                resources.getColor(
                        FeatureManager$UiFeature.isDefaultTheme(context3)
                                ? R.color.as_recommend_card_viewpager_round_stroke_background
                                : R.color.langpack_download_icon_bg_color));
        Context context4 = textView.getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
        textView.setAlpha(FeatureManager$UiFeature.isDefaultTheme(context4) ? 1.0f : 0.7f);
        textView.setPadding(
                i,
                textView.getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.button_shape_padding_top),
                i,
                textView.getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.button_shape_padding_bottom));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        ArrayList arrayList;
        RecommendCardController recommendCardController = this.controller;
        if (recommendCardController == null
                || (arrayList = recommendCardController.supportedRecommendCardListTemp) == null) {
            return 0;
        }
        return arrayList.size();
    }

    /* JADX WARN: Removed duplicated region for block: B:141:0x0377  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0404  */
    /* JADX WARN: Removed duplicated region for block: B:151:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01de A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0164 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBindViewHolder(
            androidx.recyclerview.widget.RecyclerView.ViewHolder r22, int r23) {
        /*
            Method dump skipped, instructions count: 1052
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.RecommendCardViewPagerAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder,"
                    + " int):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View inflate =
                LayoutInflater.from(this.context)
                        .inflate(R.layout.as_recommend_card_view_pager, parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(...)");
        PagerViewHolder pagerViewHolder = new PagerViewHolder(inflate);
        View requireViewById = inflate.requireViewById(R.id.linearLayout);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        pagerViewHolder.linearLayout = (LinearLayout) requireViewById;
        View requireViewById2 = inflate.requireViewById(R.id.not_now_recommend_card_btn);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
        pagerViewHolder.notNowRecommendCardButton = (TextView) requireViewById2;
        View requireViewById3 = inflate.requireViewById(R.id.description_recommend_card);
        Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById(...)");
        pagerViewHolder.descriptionRecommendCard = (TextView) requireViewById3;
        View requireViewById4 = inflate.requireViewById(R.id.confirm_recommend_card_btn);
        Intrinsics.checkNotNullExpressionValue(requireViewById4, "requireViewById(...)");
        pagerViewHolder.confirmRecommendCardButton = (TextView) requireViewById4;
        View requireViewById5 = inflate.requireViewById(R.id.sub_list_recommend_card);
        Intrinsics.checkNotNullExpressionValue(requireViewById5, "requireViewById(...)");
        pagerViewHolder.recommendCardSubList = (FlexboxLayout) requireViewById5;
        View requireViewById6 = inflate.requireViewById(R.id.main_title_recommend_card);
        Intrinsics.checkNotNullExpressionValue(requireViewById6, "requireViewById(...)");
        pagerViewHolder.recommendCardMainTitle = (TextView) requireViewById6;
        View requireViewById7 = inflate.requireViewById(R.id.main_title_recommend_card_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById7, "requireViewById(...)");
        pagerViewHolder.recommendCardMainContainer = (LinearLayout) requireViewById7;
        View requireViewById8 = inflate.requireViewById(R.id.description_recommend_card_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById8, "requireViewById(...)");
        pagerViewHolder.descriptionRecommendCardContainer = (NestedScrollView) requireViewById8;
        View requireViewById9 = inflate.requireViewById(R.id.not_now_recommend_card_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById9, "requireViewById(...)");
        pagerViewHolder.notNowButtonContainer = (LinearLayout) requireViewById9;
        View requireViewById10 = inflate.requireViewById(R.id.confirm_recommend_card_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById10, "requireViewById(...)");
        pagerViewHolder.confirmButtonContainer = (LinearLayout) requireViewById10;
        View requireViewById11 = inflate.requireViewById(R.id.as_recommend_card_button_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById11, "requireViewById(...)");
        pagerViewHolder.recommendCardButtonContainer = (LinearLayout) requireViewById11;
        return pagerViewHolder;
    }

    public final void updateLayout(Integer num) {
        final PagerViewHolder pagerViewHolder;
        if (num == null
                || (pagerViewHolder = (PagerViewHolder) this.holderArray.get(num.intValue()))
                        == null) {
            return;
        }
        final RecommendCardInfo asRecommendCardInfo =
                AsRecommendCardInfoManager.Companion.getInstance(this.context)
                        .getAsRecommendCardInfo(num.intValue(), this.controller);
        pagerViewHolder.descriptionRecommendCard.post(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.RecommendCardViewPagerAdapter$updateLayout$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        int dimensionPixelSize;
                        int lineCount =
                                RecommendCardViewPagerAdapter.PagerViewHolder.this
                                        .descriptionRecommendCard.getLineCount();
                        RecommendCardViewPagerAdapter recommendCardViewPagerAdapter = this;
                        RecommendCardViewPagerAdapter.PagerViewHolder pagerViewHolder2 =
                                RecommendCardViewPagerAdapter.PagerViewHolder.this;
                        RecommendCardInfo recommendCardInfo = asRecommendCardInfo;
                        recommendCardViewPagerAdapter.getClass();
                        NestedScrollView nestedScrollView =
                                pagerViewHolder2.descriptionRecommendCardContainer;
                        ViewGroup.LayoutParams layoutParams = nestedScrollView.getLayoutParams();
                        if (recommendCardViewPagerAdapter.isLandScape) {
                            if (recommendCardInfo.subList == null || !(!r10.isEmpty())) {
                                layoutParams.height =
                                        recommendCardViewPagerAdapter
                                                .context
                                                .getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .as_recommend_description_scroll_view_height_landscape);
                            } else {
                                layoutParams.height =
                                        recommendCardViewPagerAdapter
                                                .context
                                                .getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .as_recommend_description_scroll_view_height_landscape_subview);
                                LinearLayout linearLayout = pagerViewHolder2.linearLayout;
                                linearLayout.setPadding(
                                        linearLayout.getPaddingLeft(),
                                        recommendCardViewPagerAdapter
                                                .context
                                                .getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .as_recommend_card_viewpager_padding_top_landscape_sub_list),
                                        linearLayout.getPaddingRight(),
                                        recommendCardViewPagerAdapter
                                                .context
                                                .getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .as_recommend_card_viewpager_padding_bottom_landscape_sub_list));
                                LinearLayout.LayoutParams layoutParams2 =
                                        new LinearLayout.LayoutParams(
                                                -1,
                                                recommendCardViewPagerAdapter
                                                        .context
                                                        .getResources()
                                                        .getDimensionPixelSize(
                                                                R.dimen
                                                                        .as_recommend_button_height));
                                layoutParams2.topMargin = 0;
                                layoutParams2.setMarginEnd(
                                        recommendCardViewPagerAdapter
                                                .context
                                                .getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .as_recommend_card_viewpager_padding));
                                pagerViewHolder2.recommendCardButtonContainer.setLayoutParams(
                                        layoutParams2);
                            }
                        } else {
                            if (recommendCardInfo.subList != null && (!r10.isEmpty())) {
                                FlexboxLayout flexboxLayout = pagerViewHolder2.recommendCardSubList;
                                flexboxLayout.getClass();
                                ArrayList arrayList =
                                        new ArrayList(flexboxLayout.mFlexLines.size());
                                for (FlexLine flexLine : flexboxLayout.mFlexLines) {
                                    if (flexLine.getItemCountNotGone() != 0) {
                                        arrayList.add(flexLine);
                                    }
                                }
                                layoutParams.height =
                                        recommendCardViewPagerAdapter
                                                .context
                                                .getResources()
                                                .getDimensionPixelSize(
                                                        arrayList.size() < 2
                                                                ? R.dimen
                                                                        .as_recommend_description_scroll_view_height_subview_1line
                                                                : R.dimen
                                                                        .as_recommend_description_scroll_view_height_subview_2line);
                            } else if (lineCount <= 2) {
                                dimensionPixelSize =
                                        recommendCardViewPagerAdapter
                                                .context
                                                .getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .as_recommend_card_viewpager_padding_top);
                                layoutParams.height =
                                        recommendCardViewPagerAdapter
                                                .context
                                                .getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .as_recommend_description_scroll_view_height);
                                nestedScrollView.setPadding(0, dimensionPixelSize, 0, 0);
                                nestedScrollView.setLayoutParams(layoutParams);
                            }
                        }
                        dimensionPixelSize = 0;
                        nestedScrollView.setPadding(0, dimensionPixelSize, 0, 0);
                        nestedScrollView.setLayoutParams(layoutParams);
                    }
                });
    }
}
