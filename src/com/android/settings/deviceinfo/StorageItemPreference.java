package com.android.settings.deviceinfo;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.deviceinfo.storage.StorageUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageItemPreference extends Preference {
    public ProgressBar mProgressBar;
    public int mProgressPercent;
    public long mStorageSize;

    public StorageItemPreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        ProgressBar progressBar = (ProgressBar) preferenceViewHolder.findViewById(R.id.progress);
        this.mProgressBar = progressBar;
        if (progressBar != null && this.mProgressPercent != -1) {
            progressBar.setMax(100);
            this.mProgressBar.setProgress(this.mProgressPercent);
        }
        super.onBindViewHolder(preferenceViewHolder);
    }

    public final void setStorageSize(long j, final long j2, boolean z) {
        if (z) {
            ValueAnimator ofObject =
                    ValueAnimator.ofObject(
                            new StorageItemPreference$$ExternalSyntheticLambda0(),
                            Long.valueOf(this.mStorageSize),
                            Long.valueOf(j));
            ofObject.setDuration(1000L);
            ofObject.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // com.android.settings.deviceinfo.StorageItemPreference$$ExternalSyntheticLambda1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            StorageItemPreference storageItemPreference =
                                    StorageItemPreference.this;
                            long j3 = j2;
                            storageItemPreference.getClass();
                            storageItemPreference.updateProgressBarAndSizeInfo(
                                    ((Long) valueAnimator.getAnimatedValue()).longValue(), j3);
                        }
                    });
            ofObject.start();
        } else {
            updateProgressBarAndSizeInfo(j, j2);
        }
        this.mStorageSize = j;
    }

    public final void updateProgressBarAndSizeInfo(long j, long j2) {
        setSummary(StorageUtils.getStorageSizeLabel(getContext(), j));
        int i = j2 == 0 ? 0 : (int) ((j * 100) / j2);
        this.mProgressPercent = i;
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar == null || i == -1) {
            return;
        }
        progressBar.setMax(100);
        this.mProgressBar.setProgress(this.mProgressPercent);
    }

    public StorageItemPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mProgressPercent = -1;
        setLayoutResource(com.android.settings.R.layout.storage_item);
    }
}
