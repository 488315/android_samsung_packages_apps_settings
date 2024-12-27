package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecGearPreference extends SecRestrictedPreference implements View.OnClickListener {
    public final Context mContext;
    public OnGearClickListener mOnGearClickListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnGearClickListener {
        void onGearClick(SecGearPreference secGearPreference);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RoleDescriptionAccessibilityDelegate extends AccessibilityDelegateCompat {
        public final String mRoleDescription;

        public RoleDescriptionAccessibilityDelegate(String str) {
            this.mRoleDescription = str;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(
                View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                    view, accessibilityNodeInfoCompat.mInfo);
            accessibilityNodeInfoCompat.setRoleDescription(this.mRoleDescription);
        }
    }

    public SecGearPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final int getSecondTargetResId() {
        return R.layout.sec_widget_preference_gear;
    }

    @Override // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.settings_button_container);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.settings_button);
        if (imageView != null) {
            ViewCompat.setAccessibilityDelegate(
                    imageView,
                    new RoleDescriptionAccessibilityDelegate(
                            this.mContext.getString(R.string.button_tts)));
            StringBuilder sb = new StringBuilder();
            if (getTitle() != null) {
                sb.append(((Object) getTitle()) + " ");
            }
            if (getSummary() != null) {
                sb.append(((Object) getSummary()) + " ");
            }
            sb.append(this.mContext.getResources().getString(R.string.settings_label));
            imageView.setContentDescription(sb);
        }
        if (this.mOnGearClickListener != null) {
            findViewById.setVisibility(0);
            findViewById.setOnClickListener(this);
        } else {
            findViewById.setVisibility(8);
            findViewById.setOnClickListener(null);
        }
        preferenceViewHolder.findViewById(R.id.icon_frame).setVisibility(8);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        OnGearClickListener onGearClickListener;
        if (view.getId() != R.id.settings_button_container
                || (onGearClickListener = this.mOnGearClickListener) == null) {
            return;
        }
        onGearClickListener.onGearClick(this);
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final boolean shouldHideSecondTarget() {
        return this.mOnGearClickListener == null;
    }
}
