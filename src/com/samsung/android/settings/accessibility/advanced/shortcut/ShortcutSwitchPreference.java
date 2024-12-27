package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settingslib.RestrictedLockUtils;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.StringBuilderUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ShortcutSwitchPreference extends TwoStatePreference {
    public final Context mContext;
    public boolean mDisabledByAdmin;
    public boolean mDisabledByAppOps;
    public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public final String mGetKey;
    public final String mGetTitle;
    public String mPackageName;
    public PreferenceViewHolder mPreferenceViewHolder;
    public final HashMap mShortcutPosition;
    public int mUid;
    public final int mUserShortcutType;
    public View mView;

    public ShortcutSwitchPreference(Context context, ShortcutCandidate shortcutCandidate, int i) {
        super(context, null);
        this.mShortcutPosition = new HashMap();
        this.mPackageName = null;
        this.mUid = -1;
        setLayoutResource(R.layout.shortcut_switch_preference);
        this.mContext = context;
        this.mGetKey = shortcutCandidate.getKey();
        this.mGetTitle = shortcutCandidate.getValue();
        this.mUserShortcutType = i;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mPreferenceViewHolder = preferenceViewHolder;
        View view = preferenceViewHolder.itemView;
        this.mView = view;
        view.setEnabled(true);
        HashMap hashMap = this.mShortcutPosition;
        String str = this.mGetKey;
        int i = preferenceViewHolder.mPreLayoutPosition;
        if (i == -1) {
            i = preferenceViewHolder.mPosition;
        }
        hashMap.put(str, Integer.valueOf(i));
        View view2 = this.mView;
        if (view2 instanceof ShortcutSwitchPreferenceLayout) {
            ShortcutSwitchPreferenceLayout shortcutSwitchPreferenceLayout =
                    (ShortcutSwitchPreferenceLayout) view2;
            shortcutSwitchPreferenceLayout.isChecked = this.mChecked;
            shortcutSwitchPreferenceLayout.summary = (String) getTitle();
        }
        this.mView.setBackgroundColor(
                this.mChecked
                        ? getContext().getColor(R.color.sec_widget_bottom_bar_button_ripple_color)
                        : getContext().getColor(android.R.color.transparent));
        updateCheckBox();
    }

    @Override // androidx.preference.Preference
    public final void performClick() {
        boolean z = this.mDisabledByAdmin;
        if (!z && !this.mDisabledByAppOps) {
            super.performClick();
            return;
        }
        if (z) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    getContext(), this.mEnforcedAdmin);
            return;
        }
        Context context = getContext();
        String str = this.mPackageName;
        int i = this.mUid;
        Intent intent = new Intent("android.settings.SHOW_RESTRICTED_SETTING_DIALOG");
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra("android.intent.extra.UID", i);
        context.startActivity(intent);
    }

    public final void setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        if (this.mEnforcedAdmin != enforcedAdmin) {
            this.mEnforcedAdmin = enforcedAdmin;
            boolean z = false;
            boolean z2 = enforcedAdmin != null;
            this.mDisabledByAdmin = z2;
            if (!z2 && !this.mDisabledByAppOps) {
                z = true;
            }
            setEnabled(z);
        }
    }

    public final void setDisabledByAppOps(int i, String str) {
        if (TextUtils.equals(this.mPackageName, str)) {
            return;
        }
        boolean z = !TextUtils.isEmpty(str);
        this.mDisabledByAppOps = z;
        this.mPackageName = str;
        this.mUid = i;
        setEnabled((this.mDisabledByAdmin || z) ? false : true);
    }

    @Override // androidx.preference.Preference
    public final void setIcon(Drawable drawable) {
        if (drawable instanceof AdaptiveIconDrawable) {
            float f = this.mContext.getResources().getDisplayMetrics().density;
            int i = (int) (90.0f * f);
            int i2 = (int) (f * 80.0f);
            LayerDrawable layerDrawable =
                    new LayerDrawable(
                            new Drawable[] {
                                ((AdaptiveIconDrawable) drawable).getBackground(),
                                new WrappedDrawable(drawable)
                            });
            layerDrawable.setLayerSize(0, i, i);
            layerDrawable.setLayerSize(1, i2, i2);
            layerDrawable.setLayerGravity(1, 17);
            drawable = layerDrawable;
        }
        super.setIcon(drawable);
    }

    public final void updateCheckBox() {
        View view = this.mView;
        if (view != null) {
            RelativeLayout relativeLayout =
                    (RelativeLayout) view.findViewById(R.id.number_container);
            TextView textView = (TextView) this.mView.findViewById(R.id.number_textview);
            CheckBox checkBox = (CheckBox) this.mView.findViewById(R.id.checkbox);
            Integer num = (Integer) this.mShortcutPosition.get(this.mGetKey);
            if (num != null) {
                int intValue = num.intValue();
                PreferenceViewHolder preferenceViewHolder = this.mPreferenceViewHolder;
                int i = preferenceViewHolder.mPreLayoutPosition;
                if (i == -1) {
                    i = preferenceViewHolder.mPosition;
                }
                if (intValue == i) {
                    if (!this.mChecked) {
                        checkBox.setChecked(false);
                        relativeLayout.setVisibility(4);
                        this.mView.setContentDescription(this.mGetTitle);
                        return;
                    }
                    String shortcutService =
                            SecAccessibilityUtils.getShortcutService(
                                    this.mContext, this.mUserShortcutType);
                    if (TextUtils.isEmpty(shortcutService)) {
                        return;
                    }
                    List asList = Arrays.asList(shortcutService.split(String.valueOf(':')));
                    for (int i2 = 0; i2 < asList.size(); i2++) {
                        if (this.mGetKey.equals(asList.get(i2))) {
                            int i3 = i2 + 1;
                            String valueOf = String.valueOf(i3);
                            checkBox.setChecked(true);
                            if (this.mUserShortcutType == 16) {
                                this.mView.setContentDescription(this.mGetTitle);
                                return;
                            }
                            relativeLayout.setVisibility(0);
                            textView.setText(
                                    StringBuilderUtils.convertNumberToArabicNumber(valueOf));
                            this.mView.setContentDescription(
                                    this.mGetTitle
                                            + " "
                                            + this.mContext.getString(
                                                    R.string.shortcut_position_description,
                                                    Integer.valueOf(i3)));
                            return;
                        }
                    }
                }
            }
        }
    }
}
