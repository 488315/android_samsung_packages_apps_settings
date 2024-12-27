package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FooterButton implements View.OnClickListener {
    public FooterBarMixin.AnonymousClass1 buttonListener;
    public final int buttonType;
    public View.OnClickListener onClickListener;
    public View.OnClickListener onClickListenerWhenDisabled;
    public CharSequence text;
    public final int theme;
    public boolean enabled = true;
    public int visibility = 0;
    public int clickCount = 0;

    public FooterButton(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.SucFooterButton);
        this.text = obtainStyledAttributes.getString(1);
        this.onClickListener = null;
        int i = obtainStyledAttributes.getInt(2, 0);
        if (i < 0 || i > 8) {
            throw new IllegalArgumentException("Not a ButtonType");
        }
        this.buttonType = i;
        this.theme = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
    }

    public final PersistableBundle getMetrics(String str) {
        String str2;
        PersistableBundle persistableBundle = new PersistableBundle();
        String concat = str.concat("_text");
        String charSequence = this.text.toString();
        Parcelable.Creator<CustomEvent> creator = CustomEvent.CREATOR;
        if (charSequence.length() > 50) {
            charSequence =
                    AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                            charSequence.substring(0, 49), "…");
        }
        persistableBundle.putString(concat, charSequence);
        String concat2 = str.concat("_type");
        switch (this.buttonType) {
            case 1:
                str2 = "ADD_ANOTHER";
                break;
            case 2:
                str2 = "CANCEL";
                break;
            case 3:
                str2 = "CLEAR";
                break;
            case 4:
                str2 = "DONE";
                break;
            case 5:
                str2 = "NEXT";
                break;
            case 6:
                str2 = "OPT_IN";
                break;
            case 7:
                str2 = "SKIP";
                break;
            case 8:
                str2 = "STOP";
                break;
            default:
                str2 = "OTHER";
                break;
        }
        persistableBundle.putString(concat2, str2);
        persistableBundle.putInt(str.concat("_onClickCount"), this.clickCount);
        return persistableBundle;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        View.OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            this.clickCount++;
            onClickListener.onClick(view);
        }
    }

    public final void setEnabled(boolean z) {
        FooterBarMixin footerBarMixin;
        LinearLayout linearLayout;
        this.enabled = z;
        FooterBarMixin.AnonymousClass1 anonymousClass1 = this.buttonListener;
        if (anonymousClass1 == null
                || (linearLayout = (footerBarMixin = FooterBarMixin.this).buttonContainer)
                        == null) {
            return;
        }
        int i = anonymousClass1.val$id;
        Button button = (Button) linearLayout.findViewById(i);
        if (button != null) {
            button.setEnabled(z);
            if (!footerBarMixin.applyPartnerResources || footerBarMixin.applyDynamicColor) {
                return;
            }
            int i2 = footerBarMixin.primaryButtonId;
            PartnerConfig partnerConfig =
                    (i == i2 || footerBarMixin.isSecondaryButtonInPrimaryStyle)
                            ? PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_TEXT_COLOR
                            : PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_TEXT_COLOR;
            PartnerConfig partnerConfig2 =
                    (i == i2 || footerBarMixin.isSecondaryButtonInPrimaryStyle)
                            ? PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_DISABLED_TEXT_COLOR
                            : PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_DISABLED_TEXT_COLOR;
            if (!button.isEnabled()) {
                FooterButtonStyleUtils.updateButtonTextDisabledColorWithPartnerConfig(
                        footerBarMixin.context, button, partnerConfig2);
                return;
            }
            Context context = footerBarMixin.context;
            HashMap hashMap = FooterButtonStyleUtils.defaultTextColor;
            int color = PartnerConfigHelper.get(context).getColor(context, partnerConfig);
            if (color != 0) {
                button.setTextColor(ColorStateList.valueOf(color));
            }
        }
    }

    public final void setText(Context context, int i) {
        setText(context.getText(i));
    }

    public final void setVisibility(int i) {
        FooterBarMixin footerBarMixin;
        LinearLayout linearLayout;
        Button button;
        this.visibility = i;
        FooterBarMixin.AnonymousClass1 anonymousClass1 = this.buttonListener;
        if (anonymousClass1 == null
                || (linearLayout = (footerBarMixin = FooterBarMixin.this).buttonContainer) == null
                || (button = (Button) linearLayout.findViewById(anonymousClass1.val$id)) == null) {
            return;
        }
        button.setVisibility(i);
        footerBarMixin.autoSetButtonBarVisibility();
    }

    public final void setText(CharSequence charSequence) {
        LinearLayout linearLayout;
        Button button;
        this.text = charSequence;
        FooterBarMixin.AnonymousClass1 anonymousClass1 = this.buttonListener;
        if (anonymousClass1 == null
                || (linearLayout = FooterBarMixin.this.buttonContainer) == null
                || (button = (Button) linearLayout.findViewById(anonymousClass1.val$id)) == null) {
            return;
        }
        button.setText(charSequence);
    }

    public FooterButton(
            CharSequence charSequence, View.OnClickListener onClickListener, int i, int i2) {
        this.text = charSequence;
        this.onClickListener = onClickListener;
        this.buttonType = i;
        this.theme = i2;
    }
}
