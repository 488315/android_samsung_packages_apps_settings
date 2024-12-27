package com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Outline;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseLongArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.core.view.ViewGroupKt;
import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.analyzestorage.data.constant.AnalyzeStorageConstants$UiItemType;
import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.data.model.OverViewItemInfo;
import com.samsung.android.settings.analyzestorage.presenter.constant.CloudConstants$CloudType;
import com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.SettingsCloudAccountManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.FeatureManager$UiFeature;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageOperationManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.presenter.utils.ConfigurationUtils;
import com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils;
import com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter;
import com.samsung.android.settings.analyzestorage.ui.manager.AsUsageInfoManager;
import com.samsung.android.settings.analyzestorage.ui.utils.UiUtils;
import com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageDetailItem;
import com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageProgressBar;
import com.samsung.android.settings.analyzestorage.ui.widget.MountPopupMenu;
import com.samsung.android.settings.analyzestorage.ui.widget.viewholder.ViewPagerViewHolder;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.view.animation.SineInOut80;

import kotlin.Lazy;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ViewPagerAdapter extends RecyclerView.Adapter {
    public final FragmentActivity activity;
    public final ValueAnimator appearAnimator;
    public final Context context;
    public final OverViewController controller;
    public final ValueAnimator disappearAnimator;
    public final SparseArray holderArray;
    public boolean isLandScape;
    public boolean isShowMoreItem;
    public final ValueAnimator progressAnimator;
    public final int showMoreItemCount;
    public List storageList;
    public final int[] usageDetailHeight;

    public ViewPagerAdapter(FragmentActivity activity, List list, OverViewController controller) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(controller, "controller");
        this.activity = activity;
        this.storageList = list;
        this.controller = controller;
        this.context = activity.getBaseContext();
        this.holderArray = new SparseArray();
        this.usageDetailHeight = new int[4];
        this.showMoreItemCount = 2;
        this.progressAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(400L);
        this.disappearAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(150L);
        this.appearAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(200L);
    }

    public static void setCloudAccount(
            CloudConstants$CloudType cloudConstants$CloudType, TextView textView) {
        SettingsCloudAccountManager.AccountInfo accountInfo;
        int ordinal = cloudConstants$CloudType.ordinal();
        if (ordinal == 0 || ordinal == 1) {
            textView.setVisibility(0);
            if (SettingsCloudAccountManager.sInstance == null) {
                synchronized (SettingsCloudAccountManager.class) {
                    if (SettingsCloudAccountManager.sInstance == null) {
                        SettingsCloudAccountManager settingsCloudAccountManager =
                                new SettingsCloudAccountManager();
                        settingsCloudAccountManager.accountInfoMap =
                                new EnumMap(CloudConstants$CloudType.class);
                        SettingsCloudAccountManager.sInstance = settingsCloudAccountManager;
                    }
                }
            }
            SettingsCloudAccountManager settingsCloudAccountManager2 =
                    SettingsCloudAccountManager.sInstance;
            textView.setText(
                    (settingsCloudAccountManager2 == null
                                    || (accountInfo =
                                                    (SettingsCloudAccountManager.AccountInfo)
                                                            ((EnumMap)
                                                                            settingsCloudAccountManager2
                                                                                    .accountInfoMap)
                                                                    .get(cloudConstants$CloudType))
                                            == null)
                            ? null
                            : accountInfo.accountId);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.storageList.size();
    }

    public final int getMinHeight(boolean z) {
        return this.usageDetailHeight[
                (z ? 0 : this.showMoreItemCount) + (this.isLandScape ? 1 : 0)];
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        String string;
        final int i2 = 0;
        final int i3 = 1;
        final ViewPagerViewHolder viewPagerViewHolder = (ViewPagerViewHolder) viewHolder;
        final int intValue = ((Number) this.storageList.get(i)).intValue();
        AsUsageInfoManager.Companion companion = AsUsageInfoManager.Companion;
        Context context = this.context;
        Intrinsics.checkNotNullExpressionValue(context, "context");
        List<AnalyzeStorageConstants$UiItemType> usageInfoList =
                companion.getInstance(context).getUsageInfoList(intValue);
        this.holderArray.put(intValue, viewPagerViewHolder);
        viewPagerViewHolder.setIsRecyclable(false);
        TextView textView = viewPagerViewHolder.storageName;
        Context context2 = this.context;
        if (intValue == 0) {
            String str = StoragePathUtils.sInternalStorageRoot;
            string = context2.getString(R.string.as_internal_storage);
        } else if (intValue == 1) {
            string = context2.getString(R.string.as_sd_card);
        } else if (intValue == 2) {
            String str2 = StoragePathUtils.sInternalStorageRoot;
            string = context2.getString(R.string.as_app_clone_storage);
        } else if (intValue == 101) {
            string = context2.getString(R.string.as_google_drive);
        } else {
            if (intValue != 102) {
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                intValue, "There is no user friendly name for : "));
            }
            string = context2.getString(R.string.as_one_drive);
        }
        textView.setText(string);
        viewPagerViewHolder.usageDetailItem.setInitItem(
                this.activity,
                usageInfoList,
                intValue,
                this.isLandScape,
                this.controller,
                ConfigurationUtils.isInRTLMode(this.context),
                this.isShowMoreItem);
        final UsageProgressBar usageProgressBar = viewPagerViewHolder.usageProgressBar;
        usageProgressBar.getClass();
        usageProgressBar.setClipToOutline(true);
        usageProgressBar.setOutlineProvider(
                new ViewOutlineProvider() { // from class:
                                            // com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageProgressBar$setInitItem$1
                    @Override // android.view.ViewOutlineProvider
                    public final void getOutline(View view, Outline outline) {
                        Intrinsics.checkNotNullParameter(view, "view");
                        Intrinsics.checkNotNullParameter(outline, "outline");
                        outline.setRoundRect(
                                0,
                                0,
                                view.getWidth(),
                                view.getHeight(),
                                UsageProgressBar.this
                                        .getResources()
                                        .getDimension(
                                                R.dimen.as_home_usage_progress_rounded_corner));
                    }
                });
        for (AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType :
                usageInfoList) {
            View view = new View(usageProgressBar.getContext());
            if (analyzeStorageConstants$UiItemType.getColorResId() != -1) {
                view.setBackgroundColor(
                        usageProgressBar
                                .getContext()
                                .getColor(analyzeStorageConstants$UiItemType.getColorResId()));
            }
            view.setLayoutParams(usageProgressBar.progressLayoutParams);
            view.setTag(Float.valueOf(0.0f));
            usageProgressBar.getInnerProgress().addView(view);
        }
        usageProgressBar.progressAnimator.setInterpolator(new SineInOut80());
        usageProgressBar.progressAnimator.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageProgressBar$setAnimator$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        UsageProgressBar.this.progress =
                                Float.parseFloat(animation.getAnimatedValue().toString());
                        UsageProgressBar usageProgressBar2 = UsageProgressBar.this;
                        usageProgressBar2.weightSum = 0.0f;
                        usageProgressBar2.invalidate();
                        LinearLayout innerProgress = UsageProgressBar.this.getInnerProgress();
                        UsageProgressBar usageProgressBar3 = UsageProgressBar.this;
                        int childCount = innerProgress.getChildCount();
                        for (int i4 = 0; i4 < childCount; i4++) {
                            View childAt = innerProgress.getChildAt(i4);
                            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                            LinearLayout.LayoutParams layoutParams2 =
                                    layoutParams instanceof LinearLayout.LayoutParams
                                            ? (LinearLayout.LayoutParams) layoutParams
                                            : null;
                            if (layoutParams2 != null) {
                                Object tag = childAt.getTag();
                                Float f = tag instanceof Float ? (Float) tag : null;
                                if (f != null) {
                                    float floatValue = f.floatValue();
                                    float f2 = usageProgressBar3.progress;
                                    float f3 = usageProgressBar3.weightSum;
                                    float f4 = f2 - f3;
                                    if (f4 <= 0.0f) {
                                        f4 = 0.0f;
                                    } else if (floatValue < f4) {
                                        f4 = floatValue;
                                    }
                                    layoutParams2.weight = f4;
                                    usageProgressBar3.weightSum = f3 + floatValue;
                                    childAt.setLayoutParams(layoutParams2);
                                }
                            }
                        }
                        UsageProgressBar.this
                                .getInnerProgress()
                                .setWeightSum(UsageProgressBar.this.progress);
                        UsageProgressBar.this
                                .getInnerProgress()
                                .setLayoutParams(
                                        new LinearLayout.LayoutParams(
                                                0,
                                                -1,
                                                (UsageProgressBar.this.progress * r11.used)
                                                        / r11.total));
                    }
                });
        usageProgressBar.progressAnimator.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageProgressBar$setAnimator$2
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        UsageProgressBar.this.progress = 1.0f;
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        UsageProgressBar.this.progress = 0.0f;
                    }
                });
        if (intValue == 0 && StorageUsageManager.getStorageFreeSpace(0) < 1000000000) {
            viewPagerViewHolder.asUsageRate.setTextColor(
                    this.context.getColor(R.color.as_error_color));
        }
        this.controller
                .getUsageDetailData(intValue)
                .observe(
                        this.activity,
                        new Observer() { // from class:
                                         // com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.ViewPagerAdapter$bindDataObserver$1
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                ArrayList arrayList;
                                ArrayList arrayList2 = (ArrayList) obj;
                                if (arrayList2 != null) {
                                    ViewPagerAdapter viewPagerAdapter = this;
                                    SparseLongArray sparseLongArray =
                                            (SparseLongArray)
                                                    viewPagerAdapter.controller.mStorageUsedSize
                                                            .getValue();
                                    if (sparseLongArray != null) {
                                        int i4 = intValue;
                                        long j = sparseLongArray.get(i4);
                                        long storageTotalSize =
                                                StorageUsageManager.getStorageTotalSize(i4);
                                        int i5 =
                                                storageTotalSize != 0
                                                        ? (int) ((100 * j) / storageTotalSize)
                                                        : 0;
                                        String formatFileSize =
                                                StringConverter.formatFileSize(
                                                        2, j, viewPagerAdapter.context);
                                        ViewPagerViewHolder viewPagerViewHolder2 =
                                                viewPagerViewHolder;
                                        viewPagerViewHolder2.usedSize.setText(
                                                formatFileSize.concat(" / "));
                                        viewPagerViewHolder2.totalSize.setText(
                                                StringConverter.formatFileSize(
                                                        1,
                                                        storageTotalSize,
                                                        viewPagerAdapter.context));
                                        viewPagerViewHolder2.asUsageRate.setText(
                                                String.format(
                                                        Locale.getDefault(),
                                                        TextUtils.equals(
                                                                        Locale.getDefault()
                                                                                .getDisplayLanguage(),
                                                                        new Locale("tr")
                                                                                .getDisplayLanguage())
                                                                ? "%%%d"
                                                                : "%d%%",
                                                        Integer.valueOf(i5)));
                                        viewPagerViewHolder2.usageDetailItem.updateSize(arrayList2);
                                        int indexOf =
                                                viewPagerAdapter.storageList.indexOf(
                                                        Integer.valueOf(i4));
                                        UsageProgressBar usageProgressBar2 =
                                                viewPagerViewHolder2.usageProgressBar;
                                        usageProgressBar2.getClass();
                                        usageProgressBar2.total = storageTotalSize;
                                        usageProgressBar2.used = j;
                                        int size = arrayList2.size();
                                        LinearLayout innerProgress =
                                                usageProgressBar2.getInnerProgress();
                                        int childCount = innerProgress.getChildCount();
                                        int i6 = 0;
                                        while (i6 < childCount) {
                                            View childAt = innerProgress.getChildAt(i6);
                                            if (size > i6) {
                                                Object obj2 = arrayList2.get(i6);
                                                Intrinsics.checkNotNullExpressionValue(
                                                        obj2, "get(...)");
                                                float longValue =
                                                        r11.mSize.longValue()
                                                                / usageProgressBar2.used;
                                                arrayList = arrayList2;
                                                childAt.setLayoutParams(
                                                        new LinearLayout.LayoutParams(
                                                                0, -1, longValue));
                                                childAt.setTag(Float.valueOf(longValue));
                                                AnalyzeStorageConstants$UiItemType
                                                        analyzeStorageConstants$UiItemType2 =
                                                                ((OverViewItemInfo) obj2).mType;
                                                if (analyzeStorageConstants$UiItemType2
                                                                .getColorResId()
                                                        != -1) {
                                                    childAt.setBackgroundColor(
                                                            usageProgressBar2
                                                                    .getContext()
                                                                    .getColor(
                                                                            analyzeStorageConstants$UiItemType2
                                                                                    .getColorResId()));
                                                }
                                            } else {
                                                arrayList = arrayList2;
                                            }
                                            i6++;
                                            arrayList2 = arrayList;
                                        }
                                        usageProgressBar2
                                                .getInnerProgress()
                                                .setLayoutParams(
                                                        new LinearLayout.LayoutParams(
                                                                0,
                                                                -1,
                                                                usageProgressBar2.used
                                                                        / usageProgressBar2.total));
                                        String string2 =
                                                viewPagerAdapter.context.getString(
                                                        R.string.as_indicator_description,
                                                        Integer.valueOf(indexOf + 1),
                                                        Integer.valueOf(
                                                                viewPagerAdapter.storageList
                                                                        .size()));
                                        CharSequence text =
                                                viewPagerViewHolder2.storageName.getText();
                                        CharSequence text2 =
                                                viewPagerViewHolder2.asUsageRate.getText();
                                        String string3 =
                                                viewPagerAdapter.context.getString(
                                                        R.string.as_chart_label_used);
                                        String string4 =
                                                viewPagerAdapter.context.getString(
                                                        R.string.as_view_pager_description,
                                                        formatFileSize,
                                                        viewPagerViewHolder2.totalSize.getText());
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(string2);
                                        sb.append(", ");
                                        sb.append((Object) text);
                                        sb.append(", ");
                                        sb.append((Object) text2);
                                        viewPagerViewHolder2.motionLayout.setContentDescription(
                                                BackStackRecord$$ExternalSyntheticOutline0.m(
                                                        sb, string3, ", ", string4));
                                        if (usageProgressBar2.progress == 0.0f) {
                                            Animation loadAnimation =
                                                    AnimationUtils.loadAnimation(
                                                            viewPagerAdapter.context,
                                                            R.anim.as_sa_home_usage_rate_anim);
                                            loadAnimation.setStartOffset(i5 * 20);
                                            viewPagerViewHolder2.asUsageRate.startAnimation(
                                                    loadAnimation);
                                            viewPagerViewHolder2.usedSize.startAnimation(
                                                    loadAnimation);
                                            usageProgressBar2.progressAnimator.setDuration(
                                                    (usageProgressBar2.total != 0
                                                                    ? (int)
                                                                            ((usageProgressBar2.used
                                                                                            * 100)
                                                                                    / r5)
                                                                    : 0)
                                                            * 20);
                                            usageProgressBar2.progressAnimator.start();
                                        }
                                        if (DomainType.isSd(i4)) {
                                            viewPagerAdapter.updateMountVisibility(
                                                    !StorageVolumeManager.mounted(i4));
                                            viewPagerAdapter.updateMountState(
                                                    null, false, StorageVolumeManager.mounted(i4));
                                        } else if (DomainType.isCloud(i4)) {
                                            CloudConstants$CloudType.Companion.getClass();
                                            ViewPagerAdapter.setCloudAccount(
                                                    CloudConstants$CloudType.Companion
                                                            .fromDomainType(i4),
                                                    (TextView)
                                                            viewPagerViewHolder2
                                                                    .accountAddress$delegate
                                                                    .getValue());
                                        }
                                    }
                                }
                            }
                        });
        if (DomainType.isCloud(intValue)) {
            CloudConstants$CloudType.Companion.getClass();
            setCloudAccount(
                    CloudConstants$CloudType.Companion.fromDomainType(intValue),
                    (TextView) viewPagerViewHolder.accountAddress$delegate.getValue());
        }
        boolean isSd = DomainType.isSd(intValue);
        Lazy lazy = viewPagerViewHolder.mountButton$delegate;
        Lazy lazy2 = viewPagerViewHolder.moreOptionIcon$delegate;
        if (isSd) {
            boolean mounted = StorageVolumeManager.mounted(1);
            final ImageView imageView = (ImageView) lazy2.getValue();
            Context context3 = imageView.getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            imageView.setAlpha(FeatureManager$UiFeature.isDefaultTheme(context3) ? 1.0f : 0.6f);
            imageView.setVisibility(mounted ? 0 : 8);
            imageView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.ViewPagerAdapter$setMoreOption$2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            switch (i3) {
                                case 0:
                                    StorageOperationManager.StorageOperationManagerHolder.INSTANCE
                                            .storageOperation(
                                                    ((ViewPagerAdapter) imageView).context, 0, 1);
                                    break;
                                default:
                                    LoggingHelper.insertEventLogging(
                                            PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA(), 8851);
                                    Context context4 = ((ImageView) imageView).getContext();
                                    Intrinsics.checkNotNullExpressionValue(
                                            context4, "getContext(...)");
                                    Intrinsics.checkNotNull(view2);
                                    new MountPopupMenu(context4, view2).popupMenu.show();
                                    break;
                            }
                        }
                    });
            String string2 = imageView.getContext().getString(R.string.as_more_options);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            UiUtils.setAccessibilityForWidget(string2, imageView, Button.class.getName());
            ((LinearLayout) lazy.getValue())
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.ViewPagerAdapter$setMoreOption$2
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    switch (i2) {
                                        case 0:
                                            StorageOperationManager.StorageOperationManagerHolder
                                                    .INSTANCE
                                                    .storageOperation(
                                                            ((ViewPagerAdapter) this).context,
                                                            0,
                                                            1);
                                            break;
                                        default:
                                            LoggingHelper.insertEventLogging(
                                                    PageType.ANALYZE_STORAGE_HOME
                                                            .getScreenIdForSA(),
                                                    8851);
                                            Context context4 = ((ImageView) this).getContext();
                                            Intrinsics.checkNotNullExpressionValue(
                                                    context4, "getContext(...)");
                                            Intrinsics.checkNotNull(view2);
                                            new MountPopupMenu(context4, view2).popupMenu.show();
                                            break;
                                    }
                                }
                            });
            updateMountVisibility(!mounted);
        }
        AsUsageInfoManager.Companion companion2 = AsUsageInfoManager.Companion;
        Context context4 = this.context;
        Intrinsics.checkNotNullExpressionValue(context4, "context");
        int size = companion2.getInstance(context4).getUsageInfoList(0).size();
        UsageDetailItem usageDetailItem = viewPagerViewHolder.usageDetailItem;
        ViewGroupKt.get(usageDetailItem).measure(0, 0);
        int measuredHeight = ViewGroupKt.get(usageDetailItem).getMeasuredHeight();
        int dimensionPixelSize =
                (this.context
                                        .getResources()
                                        .getDimensionPixelSize(
                                                R.dimen.as_home_usage_detail_padding_vertical)
                                * 2)
                        + this.context
                                .getResources()
                                .getDimensionPixelSize(R.dimen.as_basic_list_divider_height);
        int dimensionPixelSize2 =
                this.context
                        .getResources()
                        .getDimensionPixelSize(R.dimen.as_home_usage_detail_padding_bottom);
        int i4 = size * measuredHeight;
        int[] iArr = this.usageDetailHeight;
        iArr[1] = (i4 / 2) + dimensionPixelSize2;
        iArr[0] = i4 + dimensionPixelSize2;
        int i5 = this.showMoreItemCount;
        int i6 = measuredHeight * 4;
        iArr[i5 + 1] = (i6 / 2) + dimensionPixelSize2;
        iArr[i5] = i6 + dimensionPixelSize2;
        Context context5 = this.context;
        Intrinsics.checkNotNullExpressionValue(context5, "context");
        if (companion2.getInstance(context5).dividerPosition.indexOfKey(0) >= 0) {
            iArr[1] = iArr[1] + dimensionPixelSize;
            iArr[0] = iArr[0] + dimensionPixelSize;
        }
        usageDetailItem.setMinimumHeight(getMinHeight(this.isShowMoreItem));
        updateLayout(Integer.valueOf(intValue));
        Context context6 = this.context;
        Intrinsics.checkNotNullExpressionValue(context6, "context");
        if (!FeatureManager$UiFeature.isDefaultTheme(context6)) {
            int color = this.context.getColor(R.color.as_theme_main_text_color);
            int color2 = this.context.getColor(R.color.as_theme_icon_color);
            viewPagerViewHolder.storageName.setTextColor(color);
            viewPagerViewHolder.asUsageRate.setTextColor(color);
            viewPagerViewHolder.asUsageLabel.setTextColor(color);
            viewPagerViewHolder.usedSize.setTextColor(color);
            viewPagerViewHolder.totalSize.setTextColor(color);
            viewPagerViewHolder.getShowMore().setTextColor(color);
            ((View) viewPagerViewHolder.divider$delegate.getValue()).setBackgroundColor(color2);
            ((TextView) viewPagerViewHolder.mountSubTitle$delegate.getValue()).setTextColor(color);
            if (((ImageView) lazy2.getValue()).getVisibility() == 0) {
                ((ImageView) lazy2.getValue()).setColorFilter(color2);
            }
            if (((LinearLayout) lazy.getValue()).getVisibility() == 0) {
                ((LinearLayout) lazy.getValue())
                        .setBackground(
                                this.context.getDrawable(R.drawable.as_contained_button_bg_theme));
                ((TextView) viewPagerViewHolder.mountButtonText$delegate.getValue())
                        .setTextColor(color);
            }
        }
        boolean isSd2 = DomainType.isSd(intValue);
        Lazy lazy3 = viewPagerViewHolder.showMoreContainer$delegate;
        if (!isSd2 || StorageVolumeManager.sIsSdcardMounted) {
            ((LinearLayout) lazy3.getValue()).setVisibility(0);
        } else {
            ((LinearLayout) lazy3.getValue()).setVisibility(4);
        }
        String string3 =
                this.context.getString(
                        this.isShowMoreItem ? R.string.as_show_less : R.string.as_show_more);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        viewPagerViewHolder.getShowMore().setText(string3);
        UiUtils.setAccessibilityForWidget(
                string3, viewPagerViewHolder.getShowMore(), Button.class.getName());
        Context context7 = this.context;
        Intrinsics.checkNotNullExpressionValue(context7, "context");
        if (Settings.System.getInt(context7.getContentResolver(), "show_button_background", 0)
                > 0) {
            TextView showMore = viewPagerViewHolder.getShowMore();
            Context context8 = showMore.getContext();
            Intrinsics.checkNotNullExpressionValue(context8, "getContext(...)");
            showMore.setBackgroundResource(
                    FeatureManager$UiFeature.isDefaultTheme(context8)
                            ? R.drawable.as_view_more_button_shape
                            : R.drawable.as_view_more_theme_button_shape);
            showMore.setTextColor(
                    showMore.getContext().getResources().getColor(R.color.as_white_background));
        }
        ((LinearLayout) lazy3.getValue())
                .setOnClickListener(
                        new View.OnClickListener() { // from class:
                            // com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.ViewPagerAdapter$setShowMoreItem$2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                String screenIdForSA =
                                        PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA();
                                ViewPagerAdapter viewPagerAdapter = ViewPagerAdapter.this;
                                int i7 = intValue;
                                LoggingHelper.insertEventLogging(
                                        screenIdForSA,
                                        i7 != 0
                                                ? i7 != 1
                                                        ? i7 != 101
                                                                ? viewPagerAdapter.isShowMoreItem
                                                                        ? 8870
                                                                        : 8864
                                                                : viewPagerAdapter.isShowMoreItem
                                                                        ? 8890
                                                                        : 8884
                                                        : viewPagerAdapter.isShowMoreItem
                                                                ? 8850
                                                                : 8844
                                                : viewPagerAdapter.isShowMoreItem ? 8838 : 8814);
                                ViewPagerAdapter viewPagerAdapter2 = ViewPagerAdapter.this;
                                boolean z = !viewPagerAdapter2.isShowMoreItem;
                                viewPagerAdapter2.isShowMoreItem = z;
                                viewPagerAdapter2.progressAnimator.setStartDelay(z ? 0L : 200L);
                                ViewPagerAdapter.this.progressAnimator.start();
                                ViewPagerAdapter.this.disappearAnimator.start();
                                Context context9 = ViewPagerAdapter.this.context;
                                Intrinsics.checkNotNullExpressionValue(
                                        context9, "access$getContext$p(...)");
                                ViewPagerAdapter viewPagerAdapter3 = ViewPagerAdapter.this;
                                String string4 =
                                        viewPagerAdapter3.context.getString(
                                                viewPagerAdapter3.isShowMoreItem
                                                        ? R.string.expand
                                                        : R.string.collapse);
                                Object systemService = context9.getSystemService("accessibility");
                                Intrinsics.checkNotNull(
                                        systemService,
                                        "null cannot be cast to non-null type"
                                            + " android.view.accessibility.AccessibilityManager");
                                AccessibilityManager accessibilityManager =
                                        (AccessibilityManager) systemService;
                                if (accessibilityManager.isEnabled()) {
                                    AccessibilityEvent obtain =
                                            AccessibilityEvent.obtain(
                                                    NetworkAnalyticsConstants.DataPoints
                                                            .FLAG_SOURCE_PORT);
                                    obtain.getText().clear();
                                    obtain.getText().add(string4);
                                    obtain.setPackageName(context9.getPackageName());
                                    accessibilityManager.sendAccessibilityEvent(obtain);
                                }
                            }
                        });
        this.progressAnimator.setInterpolator(new PathInterpolator(0.4f, 0.6f, 0.0f, 1.0f));
        this.progressAnimator.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.ViewPagerAdapter$setExpandCollapseAnimator$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        ViewPagerAdapter viewPagerAdapter = ViewPagerAdapter.this;
                        int minHeight =
                                viewPagerAdapter.getMinHeight(viewPagerAdapter.isShowMoreItem);
                        viewPagerViewHolder.usageDetailItem.setMinimumHeight(
                                ViewPagerAdapter.this.getMinHeight(!r1.isShowMoreItem)
                                        + ((int)
                                                (Float.parseFloat(
                                                                animation
                                                                        .getAnimatedValue()
                                                                        .toString())
                                                        * (minHeight - r1))));
                    }
                });
        final int i7 = 1;
        this.disappearAnimator.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.ViewPagerAdapter$setAppearAnimator$1
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        switch (i7) {
                            case 1:
                                Intrinsics.checkNotNullParameter(animation, "animation");
                                viewPagerViewHolder.getShowMore().setAlpha(0.0f);
                                ViewPagerAdapter viewPagerAdapter = this;
                                String string4 =
                                        viewPagerAdapter.context.getString(
                                                viewPagerAdapter.isShowMoreItem
                                                        ? R.string.as_show_less
                                                        : R.string.as_show_more);
                                Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                                if (!this.isShowMoreItem) {
                                    UsageDetailItem usageDetailItem2 =
                                            viewPagerViewHolder.usageDetailItem;
                                    usageDetailItem2.isShowMoreItem = false;
                                    usageDetailItem2.setChildVisible();
                                }
                                viewPagerViewHolder.getShowMore().setText(string4);
                                UiUtils.setAccessibilityForWidget(
                                        string4,
                                        viewPagerViewHolder.getShowMore(),
                                        Button.class.getName());
                                ViewPagerAdapter viewPagerAdapter2 = this;
                                viewPagerAdapter2.appearAnimator.setStartDelay(
                                        viewPagerAdapter2.isShowMoreItem ? 100L : 50L);
                                this.appearAnimator.start();
                                break;
                            default:
                                super.onAnimationEnd(animation);
                                break;
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animation) {
                        switch (i7) {
                            case 0:
                                Intrinsics.checkNotNullParameter(animation, "animation");
                                Animation loadAnimation =
                                        AnimationUtils.loadAnimation(
                                                this.context, R.anim.as_appear_anim);
                                if (this.isShowMoreItem) {
                                    UsageDetailItem usageDetailItem2 =
                                            viewPagerViewHolder.usageDetailItem;
                                    Intrinsics.checkNotNull(loadAnimation);
                                    usageDetailItem2.setAppearAnimation(loadAnimation, true);
                                }
                                viewPagerViewHolder.getShowMore().startAnimation(loadAnimation);
                                viewPagerViewHolder.getShowMore().setAlpha(1.0f);
                                break;
                            default:
                                Intrinsics.checkNotNullParameter(animation, "animation");
                                Animation loadAnimation2 =
                                        AnimationUtils.loadAnimation(
                                                this.context, R.anim.as_disappear_anim);
                                if (!this.isShowMoreItem) {
                                    UsageDetailItem usageDetailItem3 =
                                            viewPagerViewHolder.usageDetailItem;
                                    Intrinsics.checkNotNull(loadAnimation2);
                                    usageDetailItem3.setAppearAnimation(loadAnimation2, false);
                                }
                                viewPagerViewHolder.getShowMore().startAnimation(loadAnimation2);
                                break;
                        }
                    }
                });
        final int i8 = 0;
        this.appearAnimator.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.ViewPagerAdapter$setAppearAnimator$1
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        switch (i8) {
                            case 1:
                                Intrinsics.checkNotNullParameter(animation, "animation");
                                viewPagerViewHolder.getShowMore().setAlpha(0.0f);
                                ViewPagerAdapter viewPagerAdapter = this;
                                String string4 =
                                        viewPagerAdapter.context.getString(
                                                viewPagerAdapter.isShowMoreItem
                                                        ? R.string.as_show_less
                                                        : R.string.as_show_more);
                                Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                                if (!this.isShowMoreItem) {
                                    UsageDetailItem usageDetailItem2 =
                                            viewPagerViewHolder.usageDetailItem;
                                    usageDetailItem2.isShowMoreItem = false;
                                    usageDetailItem2.setChildVisible();
                                }
                                viewPagerViewHolder.getShowMore().setText(string4);
                                UiUtils.setAccessibilityForWidget(
                                        string4,
                                        viewPagerViewHolder.getShowMore(),
                                        Button.class.getName());
                                ViewPagerAdapter viewPagerAdapter2 = this;
                                viewPagerAdapter2.appearAnimator.setStartDelay(
                                        viewPagerAdapter2.isShowMoreItem ? 100L : 50L);
                                this.appearAnimator.start();
                                break;
                            default:
                                super.onAnimationEnd(animation);
                                break;
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animation) {
                        switch (i8) {
                            case 0:
                                Intrinsics.checkNotNullParameter(animation, "animation");
                                Animation loadAnimation =
                                        AnimationUtils.loadAnimation(
                                                this.context, R.anim.as_appear_anim);
                                if (this.isShowMoreItem) {
                                    UsageDetailItem usageDetailItem2 =
                                            viewPagerViewHolder.usageDetailItem;
                                    Intrinsics.checkNotNull(loadAnimation);
                                    usageDetailItem2.setAppearAnimation(loadAnimation, true);
                                }
                                viewPagerViewHolder.getShowMore().startAnimation(loadAnimation);
                                viewPagerViewHolder.getShowMore().setAlpha(1.0f);
                                break;
                            default:
                                Intrinsics.checkNotNullParameter(animation, "animation");
                                Animation loadAnimation2 =
                                        AnimationUtils.loadAnimation(
                                                this.context, R.anim.as_disappear_anim);
                                if (!this.isShowMoreItem) {
                                    UsageDetailItem usageDetailItem3 =
                                            viewPagerViewHolder.usageDetailItem;
                                    Intrinsics.checkNotNull(loadAnimation2);
                                    usageDetailItem3.setAppearAnimation(loadAnimation2, false);
                                }
                                viewPagerViewHolder.getShowMore().startAnimation(loadAnimation2);
                                break;
                        }
                    }
                });
        viewPagerViewHolder.motionLayout.setProgress(this.isLandScape ? 1.0f : 0.0f);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View inflate =
                LayoutInflater.from(this.context).inflate(R.layout.as_viewpager, parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(...)");
        return new ViewPagerViewHolder(inflate);
    }

    public final void setLayoutState(int i) {
        this.isLandScape = i == 1;
        SparseArray sparseArray = this.holderArray;
        int size = sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            sparseArray.keyAt(i2);
            ViewPagerViewHolder viewPagerViewHolder = (ViewPagerViewHolder) sparseArray.valueAt(i2);
            viewPagerViewHolder.motionLayout.setProgress(this.isLandScape ? 1.0f : 0.0f);
            viewPagerViewHolder.usageDetailItem.updateColumnCount(this.isLandScape);
        }
    }

    public final void updateLayout(Integer num) {
        ViewPagerViewHolder viewPagerViewHolder;
        if (num == null
                || (viewPagerViewHolder =
                                (ViewPagerViewHolder) this.holderArray.get(num.intValue()))
                        == null) {
            return;
        }
        viewPagerViewHolder.usageDetailItem.setMinimumHeight(getMinHeight(this.isShowMoreItem));
        UiUtils.updatePadding(
                viewPagerViewHolder.summaryContainer,
                DomainType.isCloud(num.intValue()) ? -1 : R.dimen.as_viewpager_padding_top,
                -1,
                -1);
        LinearLayout linearLayout = viewPagerViewHolder.usageRateContainer;
        int i =
                DomainType.isCloud(num.intValue())
                        ? R.dimen.as_home_rate_padding_account_top
                        : R.dimen.as_home_rate_padding_top;
        boolean z = this.isLandScape;
        int i2 = R.dimen.as_progress_container_padding_bottom_land;
        UiUtils.updatePadding(
                linearLayout, i, -1, z ? R.dimen.as_progress_container_padding_bottom_land : -1);
        LinearLayout linearLayout2 = viewPagerViewHolder.progressContainer;
        boolean z2 = this.isLandScape;
        int i3 =
                z2
                        ? R.dimen.as_progress_container_padding_top_land
                        : R.dimen.as_home_rate_padding_bottom;
        if (!z2) {
            i2 = R.dimen.as_home_rate_padding_bottom;
        }
        UiUtils.updatePadding(linearLayout2, i3, R.dimen.as_viewpager_padding_horizontal, i2);
    }

    public final void updateMountState(Integer num, boolean z, boolean z2) {
        Unit unit;
        ViewPagerViewHolder viewPagerViewHolder = (ViewPagerViewHolder) this.holderArray.get(1);
        if (viewPagerViewHolder != null) {
            Lazy lazy = viewPagerViewHolder.mountSubTitle$delegate;
            if (num != null) {
                ((TextView) lazy.getValue()).setText(num.intValue());
                ((TextView) lazy.getValue()).setVisibility(0);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                ((TextView) lazy.getValue()).setVisibility(8);
            }
            ((LinearLayout) viewPagerViewHolder.mountButton$delegate.getValue())
                    .setVisibility(z ^ true ? 0 : 8);
            ((ImageView) viewPagerViewHolder.moreOptionIcon$delegate.getValue())
                    .setVisibility(z2 ? 0 : 8);
        }
    }

    public final void updateMountVisibility(boolean z) {
        ViewPagerViewHolder viewPagerViewHolder = (ViewPagerViewHolder) this.holderArray.get(1);
        if (viewPagerViewHolder != null) {
            int i = z ? 4 : 0;
            viewPagerViewHolder.usageRateContainer.setVisibility(i);
            viewPagerViewHolder.progressContainer.setVisibility(i);
            viewPagerViewHolder.usageDetailItem.setVisibility(i);
            viewPagerViewHolder.getShowMore().setVisibility(i);
            ((View) viewPagerViewHolder.divider$delegate.getValue()).setVisibility(i);
            viewPagerViewHolder.asUsageRate.setVisibility(i);
            viewPagerViewHolder.asUsageLabel.setVisibility(i);
            viewPagerViewHolder.mountView.setVisibility(z ? 0 : 8);
        }
    }
}
