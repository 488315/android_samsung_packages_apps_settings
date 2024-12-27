package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;
import androidx.preference.internal.PreferenceImageView;

import com.android.settings.R;

import com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecIntelligenceServicePreference extends SecPreference {
    public OnClickListener mListener;
    public PackageInfo mPackageInfo;
    public int mStatus;
    public ImageButton mStatusIcon;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickListener {}

    public SecIntelligenceServicePreference(Context context) {
        super(context, null);
        this.mStatus = 0;
        this.mListener = null;
        setWidgetLayoutResource(R.layout.sec_intelligence_service_download_icon);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        view.setPaddingRelative(
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_intelligence_service_item_padding_start),
                0,
                view.getPaddingEnd(),
                0);
        PreferenceImageView preferenceImageView =
                (PreferenceImageView) preferenceViewHolder.itemView.findViewById(android.R.id.icon);
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) preferenceImageView.getLayoutParams();
        layoutParams.width =
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_intelligence_service_ic_size);
        layoutParams.height =
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_intelligence_service_ic_size);
        preferenceImageView.setLayoutParams(layoutParams);
        View findViewById = preferenceViewHolder.itemView.findViewById(R.id.icon_frame);
        findViewById.setMinimumWidth(
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_intelligence_service_item_padding_end));
        findViewById.setPaddingRelative(
                0,
                0,
                getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_intelligence_service_item_padding_end),
                0);
        ImageButton imageButton =
                (ImageButton) preferenceViewHolder.itemView.findViewById(R.id.status_icon);
        this.mStatusIcon = imageButton;
        imageButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.usefulfeature.intelligenceservice.SecIntelligenceServicePreference.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        PackageInfo packageInfo;
                        SecIntelligenceServicePreference secIntelligenceServicePreference =
                                SecIntelligenceServicePreference.this;
                        OnClickListener onClickListener =
                                secIntelligenceServicePreference.mListener;
                        if (onClickListener == null
                                || (packageInfo = secIntelligenceServicePreference.mPackageInfo)
                                        == null) {
                            return;
                        }
                        try {
                            ((IntelligenceServiceSettings) onClickListener)
                                    .getContext()
                                    .startActivity(packageInfo.getQIPPopupIntent());
                        } catch (ActivityNotFoundException e) {
                            Log.e(
                                    "IntelligenceServiceSettings",
                                    "ActivityNotFoundException " + e.getMessage());
                        }
                    }
                });
        updateStatusIcon(this.mStatus);
    }

    public final void updateStatusIcon(int i) {
        ImageButton imageButton = this.mStatusIcon;
        if (imageButton == null) {
            return;
        }
        if (i == 0) {
            imageButton.setVisibility(8);
            return;
        }
        if (i == 1) {
            imageButton.setVisibility(0);
            this.mStatusIcon.setImageResource(R.drawable.langpack_download);
        } else if (i == 2) {
            imageButton.setVisibility(0);
            this.mStatusIcon.setImageResource(R.drawable.langpack_update);
        }
    }
}
