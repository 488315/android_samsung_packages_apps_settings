package com.android.settings.dream;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.dream.DreamBackend;
import com.android.settingslib.utils.ColorUtil;

import java.util.HashSet;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DreamAdapter extends RecyclerView.Adapter {
    public final List mItemList;
    public final SparseIntArray mLayouts;
    public int mLastSelectedPos = -1;
    public boolean mEnabled = true;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DreamViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;
        public final Button mCustomizeButton;
        public final float mDisabledAlphaValue;
        public final ImageView mPreviewPlaceholderView;
        public final ImageView mPreviewView;
        public final TextView mSummaryView;
        public final TextView mTitleView;

        public DreamViewHolder(View view, Context context) {
            super(view);
            this.mContext = context;
            this.mPreviewView = (ImageView) view.findViewById(R.id.preview);
            this.mPreviewPlaceholderView = (ImageView) view.findViewById(R.id.preview_placeholder);
            this.mTitleView = (TextView) view.findViewById(R.id.title_text);
            this.mSummaryView = (TextView) view.findViewById(R.id.summary_text);
            this.mCustomizeButton = (Button) view.findViewById(R.id.customize_button);
            this.mDisabledAlphaValue = ColorUtil.getDisabledAlpha(context);
        }

        public final void setEnabledStateOnViews(View view, boolean z) {
            view.setEnabled(z);
            if (!(view instanceof ViewGroup)) {
                view.setAlpha(z ? 1.0f : this.mDisabledAlphaValue);
                return;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                setEnabledStateOnViews(viewGroup.getChildAt(childCount), z);
            }
        }
    }

    public DreamAdapter(List list) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        this.mLayouts = sparseIntArray;
        this.mItemList = list;
        sparseIntArray.append(0, R.layout.dream_preference_layout);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mItemList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        ((DreamPickerController.DreamItem) this.mItemList.get(i)).getClass();
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        final DreamViewHolder dreamViewHolder = (DreamViewHolder) viewHolder;
        final DreamPickerController.DreamItem dreamItem =
                (DreamPickerController.DreamItem) this.mItemList.get(i);
        dreamViewHolder.mTitleView.setText(dreamItem.mDreamInfo.caption);
        DreamBackend.DreamInfo dreamInfo = dreamItem.mDreamInfo;
        CharSequence charSequence = dreamInfo.description;
        int i2 = 8;
        if (TextUtils.isEmpty(charSequence)) {
            dreamViewHolder.mSummaryView.setVisibility(8);
        } else {
            dreamViewHolder.mSummaryView.setText(charSequence);
            dreamViewHolder.mSummaryView.setVisibility(0);
        }
        Drawable drawable =
                dreamItem.isActive()
                        ? dreamViewHolder.mContext.getDrawable(R.drawable.ic_dream_check_circle)
                        : dreamInfo.icon.mutate();
        int dimensionPixelSize =
                dreamViewHolder
                        .mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.dream_item_icon_size);
        drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        dreamViewHolder.mTitleView.setCompoundDrawablesRelative(drawable, null, null, null);
        dreamViewHolder.itemView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.dream.DreamAdapter$DreamViewHolder$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DreamBackend dreamBackend;
                        HashSet hashSet;
                        MetricsFeatureProvider metricsFeatureProvider;
                        DreamAdapter.DreamViewHolder dreamViewHolder2 =
                                DreamAdapter.DreamViewHolder.this;
                        DreamPickerController.DreamItem dreamItem2 = dreamItem;
                        int i3 = i;
                        dreamViewHolder2.getClass();
                        DreamPickerController dreamPickerController = DreamPickerController.this;
                        DreamBackend.DreamInfo dreamInfo2 = dreamItem2.mDreamInfo;
                        dreamPickerController.mActiveDream = dreamInfo2;
                        dreamBackend = dreamPickerController.mBackend;
                        dreamBackend.setActiveDream(dreamInfo2.componentName);
                        hashSet = dreamPickerController.mCallbacks;
                        hashSet.forEach(
                                new DreamPickerController$DreamItem$$ExternalSyntheticLambda0());
                        metricsFeatureProvider = dreamPickerController.mMetricsFeatureProvider;
                        metricsFeatureProvider.action(
                                0, 1788, 47, 1, dreamInfo2.componentName.flattenToString());
                        DreamAdapter dreamAdapter = DreamAdapter.this;
                        int i4 = dreamAdapter.mLastSelectedPos;
                        if (i4 > -1 && i4 != i3) {
                            dreamAdapter.notifyItemChanged(i4);
                        }
                        dreamAdapter.notifyItemChanged(i3);
                    }
                });
        boolean isActive = dreamItem.isActive();
        DreamAdapter dreamAdapter = DreamAdapter.this;
        if (isActive) {
            dreamAdapter.mLastSelectedPos = i;
            dreamViewHolder.itemView.setSelected(true);
            dreamViewHolder.itemView.setClickable(false);
        } else {
            dreamViewHolder.itemView.setSelected(false);
            dreamViewHolder.itemView.setClickable(true);
        }
        Drawable drawable2 = dreamInfo.previewImage;
        if (drawable2 != null) {
            dreamViewHolder.mPreviewView.setImageDrawable(drawable2);
            dreamViewHolder.mPreviewView.setClipToOutline(true);
            dreamViewHolder.mPreviewPlaceholderView.setVisibility(8);
        } else {
            dreamViewHolder.mPreviewView.setImageDrawable(null);
            dreamViewHolder.mPreviewPlaceholderView.setVisibility(0);
        }
        dreamViewHolder.mCustomizeButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.dream.DreamAdapter$DreamViewHolder$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DreamBackend dreamBackend;
                        Context context;
                        DreamPickerController.DreamItem dreamItem2 =
                                DreamPickerController.DreamItem.this;
                        DreamPickerController dreamPickerController = DreamPickerController.this;
                        dreamBackend = dreamPickerController.mBackend;
                        context = ((AbstractPreferenceController) dreamPickerController).mContext;
                        dreamBackend.getClass();
                        DreamBackend.DreamInfo dreamInfo2 = dreamItem2.mDreamInfo;
                        if (dreamInfo2 == null || dreamInfo2.settingsComponentName == null) {
                            return;
                        }
                        context.startActivity(
                                new Intent()
                                        .setComponent(dreamInfo2.settingsComponentName)
                                        .addFlags(276824064));
                    }
                });
        Button button = dreamViewHolder.mCustomizeButton;
        if (dreamItem.isActive()
                && dreamInfo.settingsComponentName != null
                && dreamAdapter.mEnabled) {
            i2 = 0;
        }
        button.setVisibility(i2);
        dreamViewHolder.mCustomizeButton.setSelected(false);
        dreamViewHolder.mCustomizeButton.setContentDescription(
                dreamViewHolder
                        .mContext
                        .getResources()
                        .getString(R.string.customize_button_description, dreamInfo.caption));
        dreamViewHolder.setEnabledStateOnViews(dreamViewHolder.itemView, dreamAdapter.mEnabled);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DreamViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(this.mLayouts.get(i), viewGroup, false),
                viewGroup.getContext());
    }
}
