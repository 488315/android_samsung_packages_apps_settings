package com.android.settings.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CardPreference extends Preference {
    public Optional mButtonsGroup;
    public View.OnClickListener mPrimaryBtnClickListener;
    public Optional mPrimaryButton;
    public String mPrimaryButtonText;
    public boolean mPrimaryButtonVisible;
    public View.OnClickListener mSecondaryBtnClickListener;
    public Optional mSecondaryButton;
    public String mSecondaryButtonText;
    public boolean mSecondaryButtonVisible;

    public CardPreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mPrimaryButton =
                Optional.ofNullable((Button) preferenceViewHolder.findViewById(R.id.button1));
        this.mSecondaryButton =
                Optional.ofNullable((Button) preferenceViewHolder.findViewById(R.id.button2));
        this.mButtonsGroup =
                Optional.ofNullable(
                        preferenceViewHolder.findViewById(
                                com.android.settings.R.id.card_preference_buttons));
        String str = this.mPrimaryButtonText;
        final int i = 0;
        this.mPrimaryButton.ifPresent(
                new Consumer() { // from class:
                                 // com.android.settings.widget.CardPreference$$ExternalSyntheticLambda2
                    public final /* synthetic */ String f$0 = null;

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i2 = i;
                        String str2 = this.f$0;
                        Button button = (Button) obj;
                        switch (i2) {
                            case 0:
                                button.setText(str2);
                                break;
                            default:
                                button.setText(str2);
                                break;
                        }
                    }
                });
        this.mPrimaryButtonText = str;
        View.OnClickListener onClickListener = this.mPrimaryBtnClickListener;
        final int i2 = 0;
        this.mPrimaryButton.ifPresent(
                new Consumer() { // from class:
                                 // com.android.settings.widget.CardPreference$$ExternalSyntheticLambda0
                    public final /* synthetic */ View.OnClickListener f$0 = null;

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i3 = i2;
                        View.OnClickListener onClickListener2 = this.f$0;
                        Button button = (Button) obj;
                        switch (i3) {
                            case 0:
                                button.setOnClickListener(onClickListener2);
                                break;
                            default:
                                button.setOnClickListener(onClickListener2);
                                break;
                        }
                    }
                });
        this.mPrimaryBtnClickListener = onClickListener;
        boolean z = this.mPrimaryButtonVisible;
        final int i3 = 1;
        this.mPrimaryButton.ifPresent(
                new Consumer() { // from class:
                                 // com.android.settings.widget.CardPreference$$ExternalSyntheticLambda1
                    public final /* synthetic */ boolean f$0 = false;

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i4 = i3;
                        boolean z2 = this.f$0;
                        Button button = (Button) obj;
                        switch (i4) {
                            case 0:
                                button.setVisibility(z2 ? 0 : 8);
                                break;
                            default:
                                button.setVisibility(z2 ? 0 : 8);
                                break;
                        }
                    }
                });
        this.mPrimaryButtonVisible = z;
        final int i4 = 0;
        final int i5 = (z || this.mSecondaryButtonVisible) ? 0 : 8;
        this.mButtonsGroup.ifPresent(
                new Consumer() { // from class:
                                 // com.android.settings.widget.CardPreference$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((View) obj).setVisibility(i5);
                    }
                });
        String str2 = this.mSecondaryButtonText;
        final int i6 = 1;
        this.mSecondaryButton.ifPresent(
                new Consumer() { // from class:
                                 // com.android.settings.widget.CardPreference$$ExternalSyntheticLambda2
                    public final /* synthetic */ String f$0 = null;

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i22 = i6;
                        String str22 = this.f$0;
                        Button button = (Button) obj;
                        switch (i22) {
                            case 0:
                                button.setText(str22);
                                break;
                            default:
                                button.setText(str22);
                                break;
                        }
                    }
                });
        this.mSecondaryButtonText = str2;
        View.OnClickListener onClickListener2 = this.mSecondaryBtnClickListener;
        final int i7 = 1;
        this.mSecondaryButton.ifPresent(
                new Consumer() { // from class:
                                 // com.android.settings.widget.CardPreference$$ExternalSyntheticLambda0
                    public final /* synthetic */ View.OnClickListener f$0 = null;

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i32 = i7;
                        View.OnClickListener onClickListener22 = this.f$0;
                        Button button = (Button) obj;
                        switch (i32) {
                            case 0:
                                button.setOnClickListener(onClickListener22);
                                break;
                            default:
                                button.setOnClickListener(onClickListener22);
                                break;
                        }
                    }
                });
        this.mSecondaryBtnClickListener = onClickListener2;
        boolean z2 = this.mSecondaryButtonVisible;
        final int i8 = 0;
        this.mSecondaryButton.ifPresent(
                new Consumer() { // from class:
                                 // com.android.settings.widget.CardPreference$$ExternalSyntheticLambda1
                    public final /* synthetic */ boolean f$0 = false;

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i42 = i8;
                        boolean z22 = this.f$0;
                        Button button = (Button) obj;
                        switch (i42) {
                            case 0:
                                button.setVisibility(z22 ? 0 : 8);
                                break;
                            default:
                                button.setVisibility(z22 ? 0 : 8);
                                break;
                        }
                    }
                });
        this.mSecondaryButtonVisible = z2;
        if (!this.mPrimaryButtonVisible && !z2) {
            i4 = 8;
        }
        this.mButtonsGroup.ifPresent(
                new Consumer() { // from class:
                                 // com.android.settings.widget.CardPreference$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((View) obj).setVisibility(i4);
                    }
                });
    }

    public CardPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, com.android.settings.R.attr.cardPreferenceStyle);
        this.mPrimaryBtnClickListener = null;
        this.mSecondaryBtnClickListener = null;
        this.mPrimaryButtonText = null;
        this.mSecondaryButtonText = null;
        this.mPrimaryButton = Optional.empty();
        this.mSecondaryButton = Optional.empty();
        this.mButtonsGroup = Optional.empty();
        this.mPrimaryButtonVisible = false;
        this.mSecondaryButtonVisible = false;
    }
}
