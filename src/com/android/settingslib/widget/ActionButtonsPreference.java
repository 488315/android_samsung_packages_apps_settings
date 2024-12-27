package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ActionButtonsPreference extends Preference {
    public final List mBtnBackgroundStyle1;
    public final List mBtnBackgroundStyle2;
    public final List mBtnBackgroundStyle3;
    public final List mBtnBackgroundStyle4;
    public final ButtonInfo mButton1Info;
    public final ButtonInfo mButton2Info;
    public final ButtonInfo mButton3Info;
    public final ButtonInfo mButton4Info;
    public View mDivider1;
    public View mDivider2;
    public View mDivider3;
    public final List mVisibleButtonInfos;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ButtonInfo {
        public Button mButton;
        public Drawable mIcon;
        public boolean mIsEnabled = true;
        public boolean mIsVisible = true;
        public View.OnClickListener mListener;
        public CharSequence mText;

        public final boolean isVisible() {
            return this.mButton.getVisibility() == 0;
        }

        public final void setUpButton() {
            this.mButton.setText(this.mText);
            this.mButton.setOnClickListener(this.mListener);
            this.mButton.setEnabled(this.mIsEnabled);
            this.mButton.setCompoundDrawablesWithIntrinsicBounds(
                    (Drawable) null, this.mIcon, (Drawable) null, (Drawable) null);
            if (!this.mIsVisible || (TextUtils.isEmpty(this.mText) && this.mIcon == null)) {
                this.mButton.setVisibility(8);
            } else {
                this.mButton.setVisibility(0);
            }
        }
    }

    public ActionButtonsPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        this.mButton3Info = new ButtonInfo();
        this.mButton4Info = new ButtonInfo();
        this.mVisibleButtonInfos = new ArrayList(4);
        this.mBtnBackgroundStyle1 = new ArrayList(1);
        this.mBtnBackgroundStyle2 = new ArrayList(2);
        this.mBtnBackgroundStyle3 = new ArrayList(3);
        this.mBtnBackgroundStyle4 = new ArrayList(4);
        init$13();
    }

    public static void setupBackgrounds(List list, List list2) {
        for (int i = 0; i < list2.size(); i++) {
            ((ButtonInfo) list.get(i)).mButton.setBackground((Drawable) list2.get(i));
        }
    }

    public static void setupRtlBackgrounds(List list, List list2) {
        for (int size = list2.size() - 1; size >= 0; size--) {
            ((ButtonInfo) list.get((list2.size() - 1) - size))
                    .mButton.setBackground((Drawable) list2.get(size));
        }
    }

    public final void fetchDrawableArray(List list, TypedArray typedArray) {
        for (int i = 0; i < typedArray.length(); i++) {
            list.add(getContext().getDrawable(typedArray.getResourceId(i, 0)));
        }
    }

    public final void init$13() {
        setLayoutResource(R.layout.settingslib_action_buttons);
        setSelectable(false);
        Resources resources = getContext().getResources();
        fetchDrawableArray(
                this.mBtnBackgroundStyle1, resources.obtainTypedArray(R.array.background_style1));
        fetchDrawableArray(
                this.mBtnBackgroundStyle2, resources.obtainTypedArray(R.array.background_style2));
        fetchDrawableArray(
                this.mBtnBackgroundStyle3, resources.obtainTypedArray(R.array.background_style3));
        fetchDrawableArray(
                this.mBtnBackgroundStyle4, resources.obtainTypedArray(R.array.background_style4));
    }

    @Override // androidx.preference.Preference
    public final void notifyChanged() {
        super.notifyChanged();
        if (((ArrayList) this.mVisibleButtonInfos).isEmpty()) {
            return;
        }
        ((ArrayList) this.mVisibleButtonInfos).clear();
        updateLayout$1$2();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        this.mButton1Info.mButton = (Button) preferenceViewHolder.findViewById(R.id.button1);
        this.mButton2Info.mButton = (Button) preferenceViewHolder.findViewById(R.id.button2);
        this.mButton3Info.mButton = (Button) preferenceViewHolder.findViewById(R.id.button3);
        this.mButton4Info.mButton = (Button) preferenceViewHolder.findViewById(R.id.button4);
        this.mDivider1 = preferenceViewHolder.findViewById(R.id.divider1);
        this.mDivider2 = preferenceViewHolder.findViewById(R.id.divider2);
        this.mDivider3 = preferenceViewHolder.findViewById(R.id.divider3);
        this.mButton1Info.setUpButton();
        this.mButton2Info.setUpButton();
        this.mButton3Info.setUpButton();
        this.mButton4Info.setUpButton();
        if (!((ArrayList) this.mVisibleButtonInfos).isEmpty()) {
            ((ArrayList) this.mVisibleButtonInfos).clear();
        }
        updateLayout$1$2();
    }

    public final void setButton1Enabled(boolean z) {
        ButtonInfo buttonInfo = this.mButton1Info;
        if (z != buttonInfo.mIsEnabled) {
            buttonInfo.mIsEnabled = z;
            notifyChanged();
        }
    }

    public final void setButton1Icon(int i) {
        if (i == 0) {
            return;
        }
        try {
            this.mButton1Info.mIcon = getContext().getDrawable(i);
            notifyChanged();
        } catch (Resources.NotFoundException unused) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Resource does not exist: ", "ActionButtonPreference");
        }
    }

    public final void setButton1OnClickListener(View.OnClickListener onClickListener) {
        ButtonInfo buttonInfo = this.mButton1Info;
        if (onClickListener != buttonInfo.mListener) {
            buttonInfo.mListener = onClickListener;
            notifyChanged();
        }
    }

    public final void setButton1Text(int i) {
        String string = getContext().getString(i);
        if (TextUtils.equals(string, this.mButton1Info.mText)) {
            return;
        }
        this.mButton1Info.mText = string;
        notifyChanged();
    }

    public final void setButton2Icon(int i) {
        if (i == 0) {
            return;
        }
        try {
            this.mButton2Info.mIcon = getContext().getDrawable(i);
            notifyChanged();
        } catch (Resources.NotFoundException unused) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Resource does not exist: ", "ActionButtonPreference");
        }
    }

    public final void setButton2OnClickListener(View.OnClickListener onClickListener) {
        ButtonInfo buttonInfo = this.mButton2Info;
        if (onClickListener != buttonInfo.mListener) {
            buttonInfo.mListener = onClickListener;
            notifyChanged();
        }
    }

    public final void setButton2Text(int i) {
        String string = getContext().getString(i);
        if (TextUtils.equals(string, this.mButton2Info.mText)) {
            return;
        }
        this.mButton2Info.mText = string;
        notifyChanged();
    }

    public final void setButton3Icon(int i) {
        if (i == 0) {
            return;
        }
        try {
            this.mButton3Info.mIcon = getContext().getDrawable(i);
            notifyChanged();
        } catch (Resources.NotFoundException unused) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Resource does not exist: ", "ActionButtonPreference");
        }
    }

    public final void setButton3Text(int i) {
        String string = getContext().getString(i);
        if (TextUtils.equals(string, this.mButton3Info.mText)) {
            return;
        }
        this.mButton3Info.mText = string;
        notifyChanged();
    }

    public final void updateLayout$1$2() {
        if (this.mButton1Info.isVisible()) {
            ((ArrayList) this.mVisibleButtonInfos).add(this.mButton1Info);
        }
        if (this.mButton2Info.isVisible()) {
            ((ArrayList) this.mVisibleButtonInfos).add(this.mButton2Info);
        }
        if (this.mButton3Info.isVisible()) {
            ((ArrayList) this.mVisibleButtonInfos).add(this.mButton3Info);
        }
        if (this.mButton4Info.isVisible()) {
            ((ArrayList) this.mVisibleButtonInfos).add(this.mButton4Info);
        }
        boolean z = getContext().getResources().getConfiguration().getLayoutDirection() == 1;
        int size = ((ArrayList) this.mVisibleButtonInfos).size();
        if (size != 1) {
            if (size != 2) {
                if (size != 3) {
                    if (size != 4) {
                        Log.e(
                                "ActionButtonPreference",
                                "No visible buttons info, skip background settings.");
                    } else if (z) {
                        setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle4);
                    } else {
                        setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle4);
                    }
                } else if (z) {
                    setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle3);
                } else {
                    setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle3);
                }
            } else if (z) {
                setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle2);
            } else {
                setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle2);
            }
        } else if (z) {
            setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle1);
        } else {
            setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle1);
        }
        if (this.mDivider1 != null
                && this.mButton1Info.isVisible()
                && this.mButton2Info.isVisible()) {
            this.mDivider1.setVisibility(0);
        }
        if (this.mDivider2 != null
                && this.mButton3Info.isVisible()
                && (this.mButton1Info.isVisible() || this.mButton2Info.isVisible())) {
            this.mDivider2.setVisibility(0);
        }
        if (this.mDivider3 == null
                || ((ArrayList) this.mVisibleButtonInfos).size() <= 1
                || !this.mButton4Info.isVisible()) {
            return;
        }
        this.mDivider3.setVisibility(0);
    }

    public ActionButtonsPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        this.mButton3Info = new ButtonInfo();
        this.mButton4Info = new ButtonInfo();
        this.mVisibleButtonInfos = new ArrayList(4);
        this.mBtnBackgroundStyle1 = new ArrayList(1);
        this.mBtnBackgroundStyle2 = new ArrayList(2);
        this.mBtnBackgroundStyle3 = new ArrayList(3);
        this.mBtnBackgroundStyle4 = new ArrayList(4);
        init$13();
    }

    public ActionButtonsPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        this.mButton3Info = new ButtonInfo();
        this.mButton4Info = new ButtonInfo();
        this.mVisibleButtonInfos = new ArrayList(4);
        this.mBtnBackgroundStyle1 = new ArrayList(1);
        this.mBtnBackgroundStyle2 = new ArrayList(2);
        this.mBtnBackgroundStyle3 = new ArrayList(3);
        this.mBtnBackgroundStyle4 = new ArrayList(4);
        init$13();
    }

    public ActionButtonsPreference(Context context) {
        super(context);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        this.mButton3Info = new ButtonInfo();
        this.mButton4Info = new ButtonInfo();
        this.mVisibleButtonInfos = new ArrayList(4);
        this.mBtnBackgroundStyle1 = new ArrayList(1);
        this.mBtnBackgroundStyle2 = new ArrayList(2);
        this.mBtnBackgroundStyle3 = new ArrayList(3);
        this.mBtnBackgroundStyle4 = new ArrayList(4);
        init$13();
    }
}
