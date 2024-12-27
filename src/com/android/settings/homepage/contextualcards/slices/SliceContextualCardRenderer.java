package com.android.settings.homepage.contextualcards.slices;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.ArrayUtils;
import androidx.slice.Slice;
import androidx.slice.SliceMetadata;
import androidx.slice.core.SliceAction;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.widget.EventInfo;
import androidx.slice.widget.ListContent;
import androidx.slice.widget.SliceLiveData;
import androidx.slice.widget.SliceView;

import com.android.settings.R;
import com.android.settings.homepage.contextualcards.CardContentProvider;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settings.homepage.contextualcards.ContextualCardRenderer;
import com.android.settings.homepage.contextualcards.ControllerRendererPool;
import com.android.settings.homepage.contextualcards.logging.ContextualCardLogUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.utils.ThreadUtils;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SliceContextualCardRenderer
        implements ContextualCardRenderer, LifecycleObserver {
    public final Context mContext;
    public final ControllerRendererPool mControllerRendererPool;
    public final SliceFullCardRendererHelper mFullCardHelper;
    public final SliceHalfCardRendererHelper mHalfCardHelper;
    public final LifecycleOwner mLifecycleOwner;
    final Map<Uri, LiveData> mSliceLiveDataMap = new ArrayMap();
    final Set<RecyclerView.ViewHolder> mFlippedCardSet = new ArraySet();

    public SliceContextualCardRenderer(
            Context context,
            LifecycleOwner lifecycleOwner,
            ControllerRendererPool controllerRendererPool) {
        this.mContext = context;
        this.mLifecycleOwner = lifecycleOwner;
        this.mControllerRendererPool = controllerRendererPool;
        lifecycleOwner.getLifecycle().addObserver(this);
        this.mFullCardHelper = new SliceFullCardRendererHelper(context);
        this.mHalfCardHelper = new SliceHalfCardRendererHelper(context);
    }

    public static void resetCardView(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.findViewById(R.id.dismissal_view).setVisibility(8);
        (viewHolder.mItemViewType == R.layout.contextual_slice_half_tile
                        ? ((SliceHalfCardRendererHelper.HalfCardViewHolder) viewHolder).content
                        : ((SliceFullCardRendererHelper.SliceViewHolder) viewHolder).sliceView)
                .setVisibility(0);
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardRenderer
    public final void bindView(
            final RecyclerView.ViewHolder viewHolder, final ContextualCard contextualCard) {
        final Uri parse = Uri.parse(contextualCard.mSliceUri);
        if (!"content".equals(parse.getScheme())) {
            Log.w("SliceCardRenderer", "Invalid uri, skipping slice: " + parse);
            return;
        }
        if (viewHolder.mItemViewType != R.layout.contextual_slice_half_tile) {
            ((SliceFullCardRendererHelper.SliceViewHolder) viewHolder)
                    .sliceView.setSlice(contextualCard.mSlice);
        }
        LiveData liveData = this.mSliceLiveDataMap.get(parse);
        if (liveData == null) {
            Context context = this.mContext;
            SliceLiveData.OnErrorListener onErrorListener =
                    new SliceLiveData
                            .OnErrorListener() { // from class:
                                                 // com.android.settings.homepage.contextualcards.slices.SliceContextualCardRenderer$$ExternalSyntheticLambda0
                        @Override // androidx.slice.widget.SliceLiveData.OnErrorListener
                        public final void onSliceError(int i, Throwable th) {
                            final Uri uri = parse;
                            final SliceContextualCardRenderer sliceContextualCardRenderer =
                                    SliceContextualCardRenderer.this;
                            sliceContextualCardRenderer.getClass();
                            Log.w(
                                    "SliceCardRenderer",
                                    "Slice may be null. uri = " + uri + ", error = " + i);
                            ThreadUtils.postOnMainThread(
                                    new Runnable() { // from class:
                                                     // com.android.settings.homepage.contextualcards.slices.SliceContextualCardRenderer$$ExternalSyntheticLambda5
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            SliceContextualCardRenderer
                                                    sliceContextualCardRenderer2 =
                                                            SliceContextualCardRenderer.this;
                                            sliceContextualCardRenderer2
                                                    .mSliceLiveDataMap
                                                    .get(uri)
                                                    .removeObservers(
                                                            sliceContextualCardRenderer2
                                                                    .mLifecycleOwner);
                                        }
                                    });
                            sliceContextualCardRenderer
                                    .mContext
                                    .getContentResolver()
                                    .notifyChange(CardContentProvider.REFRESH_CARD_URI, null);
                        }
                    };
            androidx.collection.ArraySet arraySet = SliceLiveData.SUPPORTED_SPECS;
            SliceLiveData.SliceLiveDataImpl sliceLiveDataImpl =
                    new SliceLiveData.SliceLiveDataImpl(
                            context.getApplicationContext(), parse, onErrorListener);
            this.mSliceLiveDataMap.put(parse, sliceLiveDataImpl);
            liveData = sliceLiveDataImpl;
        }
        final View findViewById = viewHolder.itemView.findViewById(R.id.dismissal_swipe_background);
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        liveData.removeObservers(lifecycleOwner);
        if (findViewById != null) {
            findViewById.setVisibility(8);
        }
        liveData.observe(
                lifecycleOwner,
                new Observer() { // from class:
                                 // com.android.settings.homepage.contextualcards.slices.SliceContextualCardRenderer$$ExternalSyntheticLambda1
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        View view = findViewById;
                        Slice slice = (Slice) obj;
                        SliceContextualCardRenderer sliceContextualCardRenderer =
                                SliceContextualCardRenderer.this;
                        sliceContextualCardRenderer.getClass();
                        if (slice == null) {
                            return;
                        }
                        if (ArrayUtils.contains("error", slice.mHints)) {
                            Log.w(
                                    "SliceCardRenderer",
                                    "Slice has HINT_ERROR, skipping rendering. uri="
                                            + Uri.parse(slice.mUri));
                            sliceContextualCardRenderer
                                    .mSliceLiveDataMap
                                    .get(Uri.parse(slice.mUri))
                                    .removeObservers(sliceContextualCardRenderer.mLifecycleOwner);
                            sliceContextualCardRenderer
                                    .mContext
                                    .getContentResolver()
                                    .notifyChange(CardContentProvider.REFRESH_CARD_URI, null);
                            return;
                        }
                        RecyclerView.ViewHolder viewHolder2 = viewHolder;
                        int i = viewHolder2.mItemViewType;
                        final ContextualCard contextualCard2 = contextualCard;
                        if (i == R.layout.contextual_slice_half_tile) {
                            final SliceHalfCardRendererHelper sliceHalfCardRendererHelper =
                                    sliceContextualCardRenderer.mHalfCardHelper;
                            sliceHalfCardRendererHelper.getClass();
                            final SliceHalfCardRendererHelper.HalfCardViewHolder
                                    halfCardViewHolder =
                                            (SliceHalfCardRendererHelper.HalfCardViewHolder)
                                                    viewHolder2;
                            final SliceActionImpl sliceActionImpl =
                                    SliceMetadata.from(sliceHalfCardRendererHelper.mContext, slice)
                                            .mPrimaryAction;
                            halfCardViewHolder.icon.setImageDrawable(
                                    sliceActionImpl.mIcon.loadDrawable(
                                            sliceHalfCardRendererHelper.mContext));
                            halfCardViewHolder.title.setText(sliceActionImpl.mTitle);
                            halfCardViewHolder.content.setOnClickListener(
                                    new View
                                            .OnClickListener() { // from class:
                                                                 // com.android.settings.homepage.contextualcards.slices.SliceHalfCardRendererHelper$$ExternalSyntheticLambda0
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view2) {
                                            SliceHalfCardRendererHelper
                                                    sliceHalfCardRendererHelper2 =
                                                            SliceHalfCardRendererHelper.this;
                                            SliceAction sliceAction = sliceActionImpl;
                                            ContextualCard contextualCard3 = contextualCard2;
                                            SliceHalfCardRendererHelper.HalfCardViewHolder
                                                    halfCardViewHolder2 = halfCardViewHolder;
                                            sliceHalfCardRendererHelper2.getClass();
                                            try {
                                                sliceAction.getAction().send();
                                            } catch (PendingIntent.CanceledException unused) {
                                                Log.w(
                                                        "SliceHCRendererHelper",
                                                        "Failed to start intent "
                                                                + ((Object)
                                                                        sliceAction.getTitle()));
                                            }
                                            String buildCardClickLog =
                                                    ContextualCardLogUtils.buildCardClickLog(
                                                            contextualCard3,
                                                            0,
                                                            3,
                                                            halfCardViewHolder2
                                                                    .getBindingAdapterPosition());
                                            FeatureFactoryImpl featureFactoryImpl =
                                                    FeatureFactoryImpl._factory;
                                            if (featureFactoryImpl == null) {
                                                throw new UnsupportedOperationException(
                                                        "No feature factory configured");
                                            }
                                            featureFactoryImpl
                                                    .getMetricsFeatureProvider()
                                                    .action(
                                                            sliceHalfCardRendererHelper2.mContext,
                                                            1666,
                                                            buildCardClickLog);
                                        }
                                    });
                        } else {
                            final SliceFullCardRendererHelper sliceFullCardRendererHelper =
                                    sliceContextualCardRenderer.mFullCardHelper;
                            sliceFullCardRendererHelper.getClass();
                            final SliceFullCardRendererHelper.SliceViewHolder sliceViewHolder =
                                    (SliceFullCardRendererHelper.SliceViewHolder) viewHolder2;
                            SliceView sliceView = sliceViewHolder.sliceView;
                            sliceView.setScrollable();
                            sliceView.setTag(Uri.parse(contextualCard2.mSliceUri));
                            sliceView.setMode(2);
                            sliceView.setSlice(slice);
                            SliceView.OnSliceActionListener onSliceActionListener =
                                    new SliceView
                                            .OnSliceActionListener() { // from class:
                                                                       // com.android.settings.homepage.contextualcards.slices.SliceFullCardRendererHelper$$ExternalSyntheticLambda0
                                        @Override // androidx.slice.widget.SliceView.OnSliceActionListener
                                        public final void onSliceAction(EventInfo eventInfo) {
                                            SliceFullCardRendererHelper
                                                    sliceFullCardRendererHelper2 =
                                                            SliceFullCardRendererHelper.this;
                                            sliceFullCardRendererHelper2.getClass();
                                            int i2 = eventInfo.rowIndex;
                                            int bindingAdapterPosition =
                                                    sliceViewHolder.getBindingAdapterPosition();
                                            String buildCardClickLog =
                                                    ContextualCardLogUtils.buildCardClickLog(
                                                            contextualCard2,
                                                            i2,
                                                            eventInfo.actionType,
                                                            bindingAdapterPosition);
                                            FeatureFactoryImpl featureFactoryImpl =
                                                    FeatureFactoryImpl._factory;
                                            if (featureFactoryImpl == null) {
                                                throw new UnsupportedOperationException(
                                                        "No feature factory configured");
                                            }
                                            featureFactoryImpl
                                                    .getMetricsFeatureProvider()
                                                    .action(
                                                            sliceFullCardRendererHelper2.mContext,
                                                            1666,
                                                            buildCardClickLog);
                                        }
                                    };
                            sliceView.mSliceObserver = onSliceActionListener;
                            sliceView.mCurrentView.setSliceActionListener(onSliceActionListener);
                            sliceView.setShowTitleItems();
                            if (contextualCard2.mIsLargeCard) {
                                sliceView.mShowHeaderDivider = true;
                                ListContent listContent = sliceView.mListContent;
                                if (listContent != null
                                        && listContent.mHeaderContent != null
                                        && listContent.mRowItems.size() > 1) {
                                    listContent.mHeaderContent.mShowBottomDivider = true;
                                }
                                sliceView.setShowActionDividers();
                            }
                        }
                        if (view != null) {
                            view.setVisibility(0);
                        }
                    }
                });
        if (viewHolder.mItemViewType != R.layout.contextual_slice_sticky_tile) {
            ((Button) viewHolder.itemView.findViewById(R.id.keep))
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.android.settings.homepage.contextualcards.slices.SliceContextualCardRenderer$$ExternalSyntheticLambda3
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    SliceContextualCardRenderer sliceContextualCardRenderer =
                                            SliceContextualCardRenderer.this;
                                    RecyclerView.ViewHolder viewHolder2 = viewHolder;
                                    sliceContextualCardRenderer.mFlippedCardSet.remove(viewHolder2);
                                    SliceContextualCardRenderer.resetCardView(viewHolder2);
                                }
                            });
            ((Button) viewHolder.itemView.findViewById(R.id.remove))
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.android.settings.homepage.contextualcards.slices.SliceContextualCardRenderer$$ExternalSyntheticLambda4
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    SliceContextualCardRenderer sliceContextualCardRenderer =
                                            SliceContextualCardRenderer.this;
                                    ContextualCard contextualCard2 = contextualCard;
                                    RecyclerView.ViewHolder viewHolder2 = viewHolder;
                                    sliceContextualCardRenderer
                                            .mControllerRendererPool
                                            .getController(
                                                    sliceContextualCardRenderer.mContext,
                                                    contextualCard2.getCardType())
                                            .onDismissed(contextualCard2);
                                    sliceContextualCardRenderer.mFlippedCardSet.remove(viewHolder2);
                                    SliceContextualCardRenderer.resetCardView(viewHolder2);
                                    sliceContextualCardRenderer
                                            .mSliceLiveDataMap
                                            .get(Uri.parse(contextualCard2.mSliceUri))
                                            .removeObservers(
                                                    sliceContextualCardRenderer.mLifecycleOwner);
                                }
                            });
            ViewCompat.setAccessibilityDelegate(
                    viewHolder.mItemViewType == R.layout.contextual_slice_half_tile
                            ? ((SliceHalfCardRendererHelper.HalfCardViewHolder) viewHolder).content
                            : ((SliceFullCardRendererHelper.SliceViewHolder) viewHolder).sliceView,
                    new AccessibilityDelegateCompat() { // from class:
                                                        // com.android.settings.homepage.contextualcards.slices.SliceContextualCardRenderer.1
                        @Override // androidx.core.view.AccessibilityDelegateCompat
                        public final void onInitializeAccessibilityNodeInfo(
                                View view,
                                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                                    view, accessibilityNodeInfoCompat.mInfo);
                            accessibilityNodeInfoCompat.addAction(1048576);
                            accessibilityNodeInfoCompat.mInfo.setDismissable(true);
                        }

                        @Override // androidx.core.view.AccessibilityDelegateCompat
                        public final boolean performAccessibilityAction(
                                View view, int i, Bundle bundle) {
                            if (i == 1048576) {
                                SliceContextualCardRenderer sliceContextualCardRenderer =
                                        SliceContextualCardRenderer.this;
                                ControllerRendererPool controllerRendererPool =
                                        sliceContextualCardRenderer.mControllerRendererPool;
                                Context context2 = sliceContextualCardRenderer.mContext;
                                ContextualCard contextualCard2 = contextualCard;
                                controllerRendererPool
                                        .getController(context2, contextualCard2.getCardType())
                                        .onDismissed(contextualCard2);
                            }
                            return super.performAccessibilityAction(view, i, bundle);
                        }
                    });
            if (contextualCard.mIsPendingDismiss) {
                viewHolder.itemView.findViewById(R.id.dismissal_view).setVisibility(0);
                (viewHolder.mItemViewType == R.layout.contextual_slice_half_tile
                                ? ((SliceHalfCardRendererHelper.HalfCardViewHolder) viewHolder)
                                        .content
                                : ((SliceFullCardRendererHelper.SliceViewHolder) viewHolder)
                                        .sliceView)
                        .setVisibility(4);
                this.mFlippedCardSet.add(viewHolder);
            }
        }
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardRenderer
    public final RecyclerView.ViewHolder createViewHolder(View view, int i) {
        if (i == R.layout.contextual_slice_half_tile) {
            this.mHalfCardHelper.getClass();
            return new SliceHalfCardRendererHelper.HalfCardViewHolder(view);
        }
        this.mFullCardHelper.getClass();
        return new SliceFullCardRendererHelper.SliceViewHolder(view);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        this.mFlippedCardSet.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.homepage.contextualcards.slices.SliceContextualCardRenderer$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SliceContextualCardRenderer.this.getClass();
                        SliceContextualCardRenderer.resetCardView((RecyclerView.ViewHolder) obj);
                    }
                });
        this.mFlippedCardSet.clear();
    }
}
