package com.android.settings.notification.app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ImportancePreference extends Preference {
    public View mAlertButton;
    public Context mContext;
    public final boolean mIsConfigurable;
    public View mSilenceButton;
    public Drawable selectedBackground;
    public Drawable unselectedBackground;

    public ImportancePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsConfigurable = true;
        init$3(context);
    }

    public final void init$3(Context context) {
        this.mContext = context;
        this.selectedBackground =
                context.getDrawable(R.drawable.notification_importance_button_background_selected);
        this.unselectedBackground =
                this.mContext.getDrawable(
                        R.drawable.notification_importance_button_background_unselected);
        setLayoutResource(R.layout.notif_importance_preference);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(final PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setClickable(false);
        this.mSilenceButton = preferenceViewHolder.findViewById(R.id.silence);
        this.mAlertButton = preferenceViewHolder.findViewById(R.id.alert);
        if (!this.mIsConfigurable) {
            this.mSilenceButton.setEnabled(false);
            this.mAlertButton.setEnabled(false);
        }
        setImportanceSummary((ViewGroup) preferenceViewHolder.itemView, 0, false);
        this.mSilenceButton.setBackground(this.unselectedBackground);
        this.mAlertButton.setBackground(this.selectedBackground);
        this.mAlertButton.setSelected(true);
        final int i = 0;
        this.mSilenceButton.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.notification.app.ImportancePreference$$ExternalSyntheticLambda0
                    public final /* synthetic */ ImportancePreference f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                final ImportancePreference importancePreference = this.f$0;
                                PreferenceViewHolder preferenceViewHolder2 = preferenceViewHolder;
                                importancePreference.callChangeListener(2);
                                importancePreference.mAlertButton.setBackground(
                                        importancePreference.unselectedBackground);
                                importancePreference.mSilenceButton.setBackground(
                                        importancePreference.selectedBackground);
                                importancePreference.setImportanceSummary(
                                        (ViewGroup) preferenceViewHolder2.itemView, 2, true);
                                final int i2 = 0;
                                preferenceViewHolder2.itemView.post(
                                        new Runnable() { // from class:
                                                         // com.android.settings.notification.app.ImportancePreference$$ExternalSyntheticLambda2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i3 = i2;
                                                ImportancePreference importancePreference2 =
                                                        importancePreference;
                                                switch (i3) {
                                                    case 0:
                                                        importancePreference2.mAlertButton
                                                                .setSelected(false);
                                                        importancePreference2.mSilenceButton
                                                                .setSelected(true);
                                                        break;
                                                    default:
                                                        importancePreference2.mSilenceButton
                                                                .setSelected(false);
                                                        importancePreference2.mAlertButton
                                                                .setSelected(true);
                                                        break;
                                                }
                                            }
                                        });
                                break;
                            default:
                                final ImportancePreference importancePreference2 = this.f$0;
                                PreferenceViewHolder preferenceViewHolder3 = preferenceViewHolder;
                                importancePreference2.callChangeListener(3);
                                importancePreference2.mSilenceButton.setBackground(
                                        importancePreference2.unselectedBackground);
                                importancePreference2.mAlertButton.setBackground(
                                        importancePreference2.selectedBackground);
                                importancePreference2.setImportanceSummary(
                                        (ViewGroup) preferenceViewHolder3.itemView, 3, true);
                                final int i3 = 1;
                                preferenceViewHolder3.itemView.post(
                                        new Runnable() { // from class:
                                                         // com.android.settings.notification.app.ImportancePreference$$ExternalSyntheticLambda2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i32 = i3;
                                                ImportancePreference importancePreference22 =
                                                        importancePreference2;
                                                switch (i32) {
                                                    case 0:
                                                        importancePreference22.mAlertButton
                                                                .setSelected(false);
                                                        importancePreference22.mSilenceButton
                                                                .setSelected(true);
                                                        break;
                                                    default:
                                                        importancePreference22.mSilenceButton
                                                                .setSelected(false);
                                                        importancePreference22.mAlertButton
                                                                .setSelected(true);
                                                        break;
                                                }
                                            }
                                        });
                                break;
                        }
                    }
                });
        final int i2 = 1;
        this.mAlertButton.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.notification.app.ImportancePreference$$ExternalSyntheticLambda0
                    public final /* synthetic */ ImportancePreference f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                final ImportancePreference importancePreference = this.f$0;
                                PreferenceViewHolder preferenceViewHolder2 = preferenceViewHolder;
                                importancePreference.callChangeListener(2);
                                importancePreference.mAlertButton.setBackground(
                                        importancePreference.unselectedBackground);
                                importancePreference.mSilenceButton.setBackground(
                                        importancePreference.selectedBackground);
                                importancePreference.setImportanceSummary(
                                        (ViewGroup) preferenceViewHolder2.itemView, 2, true);
                                final int i22 = 0;
                                preferenceViewHolder2.itemView.post(
                                        new Runnable() { // from class:
                                                         // com.android.settings.notification.app.ImportancePreference$$ExternalSyntheticLambda2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i32 = i22;
                                                ImportancePreference importancePreference22 =
                                                        importancePreference;
                                                switch (i32) {
                                                    case 0:
                                                        importancePreference22.mAlertButton
                                                                .setSelected(false);
                                                        importancePreference22.mSilenceButton
                                                                .setSelected(true);
                                                        break;
                                                    default:
                                                        importancePreference22.mSilenceButton
                                                                .setSelected(false);
                                                        importancePreference22.mAlertButton
                                                                .setSelected(true);
                                                        break;
                                                }
                                            }
                                        });
                                break;
                            default:
                                final ImportancePreference importancePreference2 = this.f$0;
                                PreferenceViewHolder preferenceViewHolder3 = preferenceViewHolder;
                                importancePreference2.callChangeListener(3);
                                importancePreference2.mSilenceButton.setBackground(
                                        importancePreference2.unselectedBackground);
                                importancePreference2.mAlertButton.setBackground(
                                        importancePreference2.selectedBackground);
                                importancePreference2.setImportanceSummary(
                                        (ViewGroup) preferenceViewHolder3.itemView, 3, true);
                                final int i3 = 1;
                                preferenceViewHolder3.itemView.post(
                                        new Runnable() { // from class:
                                                         // com.android.settings.notification.app.ImportancePreference$$ExternalSyntheticLambda2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i32 = i3;
                                                ImportancePreference importancePreference22 =
                                                        importancePreference2;
                                                switch (i32) {
                                                    case 0:
                                                        importancePreference22.mAlertButton
                                                                .setSelected(false);
                                                        importancePreference22.mSilenceButton
                                                                .setSelected(true);
                                                        break;
                                                    default:
                                                        importancePreference22.mSilenceButton
                                                                .setSelected(false);
                                                        importancePreference22.mAlertButton
                                                                .setSelected(true);
                                                        break;
                                                }
                                            }
                                        });
                                break;
                        }
                    }
                });
    }

    public final void setImportanceSummary(ViewGroup viewGroup, int i, boolean z) {
        if (z) {
            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(100L);
            TransitionManager.beginDelayedTransition(viewGroup, autoTransition);
        }
        ColorStateList colorAttr =
                Utils.getColorAttr(
                        getContext(),
                        R.attr.notification_importance_button_foreground_color_selected);
        ColorStateList colorAttr2 =
                Utils.getColorAttr(
                        getContext(),
                        R.attr.notification_importance_button_foreground_color_unselected);
        if (i >= 3) {
            viewGroup.findViewById(R.id.silence_summary).setVisibility(8);
            ((ImageView) viewGroup.findViewById(R.id.silence_icon)).setImageTintList(colorAttr2);
            ((TextView) viewGroup.findViewById(R.id.silence_label)).setTextColor(colorAttr2);
            ((ImageView) viewGroup.findViewById(R.id.alert_icon)).setImageTintList(colorAttr);
            ((TextView) viewGroup.findViewById(R.id.alert_label)).setTextColor(colorAttr);
            viewGroup.findViewById(R.id.alert_summary).setVisibility(0);
            return;
        }
        viewGroup.findViewById(R.id.alert_summary).setVisibility(8);
        ((ImageView) viewGroup.findViewById(R.id.alert_icon)).setImageTintList(colorAttr2);
        ((TextView) viewGroup.findViewById(R.id.alert_label)).setTextColor(colorAttr2);
        ((ImageView) viewGroup.findViewById(R.id.silence_icon)).setImageTintList(colorAttr);
        ((TextView) viewGroup.findViewById(R.id.silence_label)).setTextColor(colorAttr);
        viewGroup.findViewById(R.id.silence_summary).setVisibility(0);
    }

    public ImportancePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsConfigurable = true;
        init$3(context);
    }

    public ImportancePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsConfigurable = true;
        init$3(context);
    }

    public ImportancePreference(Context context) {
        super(context);
        this.mIsConfigurable = true;
        init$3(context);
    }
}
