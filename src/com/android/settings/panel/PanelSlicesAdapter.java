package com.android.settings.panel;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.widget.EventInfo;
import androidx.slice.widget.SliceView;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.google.android.setupdesign.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PanelSlicesAdapter extends RecyclerView.Adapter {
    static final int MAX_NUM_OF_SLICES = 9;
    public final int mMetricsCategory;
    public final PanelFragment mPanelFragment;
    public final List mSliceLiveData;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SliceRowViewHolder extends RecyclerView.ViewHolder
            implements DividerItemDecoration.DividedViewHolder {
        public static final /* synthetic */ int $r8$clinit = 0;
        final LinearLayout mSliceSliderLayout;
        final SliceView sliceView;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.panel.PanelSlicesAdapter$SliceRowViewHolder$2, reason: invalid class name */
        public final class AnonymousClass2 extends View.AccessibilityDelegate {
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(
                    View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(
                        new AccessibilityNodeInfo.AccessibilityAction(
                                16,
                                view.getResources()
                                        .getString(
                                                R.string.accessibility_action_label_panel_slice)));
            }
        }

        public SliceRowViewHolder(View view) {
            super(view);
            SliceView sliceView = (SliceView) view.findViewById(R.id.slice_view);
            this.sliceView = sliceView;
            sliceView.setMode(2);
            sliceView.setShowTitleItems();
            sliceView.setImportantForAccessibility(2);
            this.mSliceSliderLayout = (LinearLayout) view.findViewById(R.id.slice_slider_layout);
        }

        @Override // com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder
        public final boolean isDividerAllowedAbove() {
            return false;
        }

        @Override // com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder
        public final boolean isDividerAllowedBelow() {
            return false;
        }

        public void setActionLabel(View view) {
            view.setAccessibilityDelegate(new AnonymousClass2());
        }

        public void updateActionLabel() {
            SliceView sliceView = this.sliceView;
            if (sliceView == null) {
                return;
            }
            LinearLayout linearLayout = (LinearLayout) sliceView.findViewById(R.id.row_view);
            if (linearLayout != null) {
                setActionLabel(linearLayout);
            } else {
                if (this.sliceView.getTag(R.id.tag_row_view) != null) {
                    return;
                }
                this.sliceView.setTag(R.id.tag_row_view, new Object());
                this.sliceView.addOnLayoutChangeListener(
                        new View
                                .OnLayoutChangeListener() { // from class:
                                                            // com.android.settings.panel.PanelSlicesAdapter.SliceRowViewHolder.1
                            @Override // android.view.View.OnLayoutChangeListener
                            public final void onLayoutChange(
                                    View view,
                                    int i,
                                    int i2,
                                    int i3,
                                    int i4,
                                    int i5,
                                    int i6,
                                    int i7,
                                    int i8) {
                                SliceView sliceView2 = SliceRowViewHolder.this.sliceView;
                                int i9 = SliceRowViewHolder.$r8$clinit;
                                LinearLayout linearLayout2 =
                                        (LinearLayout) sliceView2.findViewById(R.id.row_view);
                                if (linearLayout2 != null) {
                                    SliceRowViewHolder.this.setActionLabel(linearLayout2);
                                    SliceRowViewHolder.this.sliceView.removeOnLayoutChangeListener(
                                            this);
                                }
                            }
                        });
            }
        }
    }

    public PanelSlicesAdapter(PanelFragment panelFragment, Map map, int i) {
        this.mPanelFragment = panelFragment;
        this.mSliceLiveData = new ArrayList(map.values());
        this.mMetricsCategory = i;
    }

    public List<LiveData> getData() {
        return ((ArrayList) this.mSliceLiveData).subList(0, getItemCount());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return Math.min(((ArrayList) this.mSliceLiveData).size(), 9);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return this.mPanelFragment.mPanel.getViewType();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final SliceRowViewHolder sliceRowViewHolder = (SliceRowViewHolder) viewHolder;
        final Slice slice =
                (Slice) ((LiveData) ((ArrayList) this.mSliceLiveData).get(i)).getValue();
        if (slice != null && !Arrays.asList(slice.mHints).contains("error")) {
            Iterator it = Arrays.asList(slice.mItems).iterator();
            while (it.hasNext()) {
                if (((SliceItem) it.next()).mFormat.equals("slice")) {
                    sliceRowViewHolder.sliceView.setSlice(slice);
                    sliceRowViewHolder.sliceView.setVisibility(0);
                    sliceRowViewHolder.sliceView.setShowActionDividers();
                    SliceView sliceView = sliceRowViewHolder.sliceView;
                    SliceView.OnSliceActionListener onSliceActionListener =
                            new SliceView
                                    .OnSliceActionListener() { // from class:
                                                               // com.android.settings.panel.PanelSlicesAdapter$SliceRowViewHolder$$ExternalSyntheticLambda0
                                @Override // androidx.slice.widget.SliceView.OnSliceActionListener
                                public final void onSliceAction(EventInfo eventInfo) {
                                    PanelSlicesAdapter.SliceRowViewHolder sliceRowViewHolder2 =
                                            PanelSlicesAdapter.SliceRowViewHolder.this;
                                    sliceRowViewHolder2.getClass();
                                    FeatureFactoryImpl featureFactoryImpl =
                                            FeatureFactoryImpl._factory;
                                    if (featureFactoryImpl == null) {
                                        throw new UnsupportedOperationException(
                                                "No feature factory configured");
                                    }
                                    featureFactoryImpl
                                            .getMetricsFeatureProvider()
                                            .action(
                                                    0,
                                                    1658,
                                                    PanelSlicesAdapter.this.mMetricsCategory,
                                                    eventInfo.actionType,
                                                    Uri.parse(slice.mUri).getLastPathSegment());
                                }
                            };
                    sliceView.mSliceObserver = onSliceActionListener;
                    sliceView.mCurrentView.setSliceActionListener(onSliceActionListener);
                    sliceRowViewHolder.updateActionLabel();
                    return;
                }
            }
        }
        sliceRowViewHolder.updateActionLabel();
        sliceRowViewHolder.sliceView.setVisibility(8);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        return new SliceRowViewHolder(
                i == 1
                        ? from.inflate(R.layout.panel_slice_slider_row, viewGroup, false)
                        : from.inflate(R.layout.panel_slice_row, viewGroup, false));
    }
}
