package com.android.settings.notification.app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Pair;
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
public class ConversationPriorityPreference extends Preference {
    public View mAlertButton;
    public final Context mContext;
    public final boolean mIsConfigurable;
    public View mPriorityButton;
    public View mSilenceButton;

    public ConversationPriorityPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsConfigurable = true;
        this.mContext = context;
        setLayoutResource(R.layout.notif_priority_conversation_preference);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(final PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setClickable(false);
        this.mSilenceButton = preferenceViewHolder.findViewById(R.id.silence);
        this.mAlertButton = preferenceViewHolder.findViewById(R.id.alert);
        this.mPriorityButton = preferenceViewHolder.findViewById(R.id.priority_group);
        if (!this.mIsConfigurable) {
            this.mSilenceButton.setEnabled(false);
            this.mAlertButton.setEnabled(false);
            this.mPriorityButton.setEnabled(false);
        }
        updateToggles(0, (ViewGroup) preferenceViewHolder.itemView, false, false);
        final int i = 0;
        this.mSilenceButton.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.notification.app.ConversationPriorityPreference$$ExternalSyntheticLambda0
                    public final /* synthetic */ ConversationPriorityPreference f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                ConversationPriorityPreference conversationPriorityPreference =
                                        this.f$0;
                                PreferenceViewHolder preferenceViewHolder2 = preferenceViewHolder;
                                conversationPriorityPreference.getClass();
                                conversationPriorityPreference.callChangeListener(
                                        new Pair(2, Boolean.FALSE));
                                conversationPriorityPreference.updateToggles(
                                        2, (ViewGroup) preferenceViewHolder2.itemView, false, true);
                                break;
                            case 1:
                                ConversationPriorityPreference conversationPriorityPreference2 =
                                        this.f$0;
                                PreferenceViewHolder preferenceViewHolder3 = preferenceViewHolder;
                                conversationPriorityPreference2.getClass();
                                int max = Math.max(0, 3);
                                conversationPriorityPreference2.callChangeListener(
                                        new Pair(Integer.valueOf(max), Boolean.FALSE));
                                conversationPriorityPreference2.updateToggles(
                                        max,
                                        (ViewGroup) preferenceViewHolder3.itemView,
                                        false,
                                        true);
                                break;
                            default:
                                ConversationPriorityPreference conversationPriorityPreference3 =
                                        this.f$0;
                                PreferenceViewHolder preferenceViewHolder4 = preferenceViewHolder;
                                conversationPriorityPreference3.getClass();
                                int max2 = Math.max(0, 3);
                                conversationPriorityPreference3.callChangeListener(
                                        new Pair(Integer.valueOf(max2), Boolean.TRUE));
                                conversationPriorityPreference3.updateToggles(
                                        max2,
                                        (ViewGroup) preferenceViewHolder4.itemView,
                                        true,
                                        true);
                                break;
                        }
                    }
                });
        final int i2 = 1;
        this.mAlertButton.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.notification.app.ConversationPriorityPreference$$ExternalSyntheticLambda0
                    public final /* synthetic */ ConversationPriorityPreference f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                ConversationPriorityPreference conversationPriorityPreference =
                                        this.f$0;
                                PreferenceViewHolder preferenceViewHolder2 = preferenceViewHolder;
                                conversationPriorityPreference.getClass();
                                conversationPriorityPreference.callChangeListener(
                                        new Pair(2, Boolean.FALSE));
                                conversationPriorityPreference.updateToggles(
                                        2, (ViewGroup) preferenceViewHolder2.itemView, false, true);
                                break;
                            case 1:
                                ConversationPriorityPreference conversationPriorityPreference2 =
                                        this.f$0;
                                PreferenceViewHolder preferenceViewHolder3 = preferenceViewHolder;
                                conversationPriorityPreference2.getClass();
                                int max = Math.max(0, 3);
                                conversationPriorityPreference2.callChangeListener(
                                        new Pair(Integer.valueOf(max), Boolean.FALSE));
                                conversationPriorityPreference2.updateToggles(
                                        max,
                                        (ViewGroup) preferenceViewHolder3.itemView,
                                        false,
                                        true);
                                break;
                            default:
                                ConversationPriorityPreference conversationPriorityPreference3 =
                                        this.f$0;
                                PreferenceViewHolder preferenceViewHolder4 = preferenceViewHolder;
                                conversationPriorityPreference3.getClass();
                                int max2 = Math.max(0, 3);
                                conversationPriorityPreference3.callChangeListener(
                                        new Pair(Integer.valueOf(max2), Boolean.TRUE));
                                conversationPriorityPreference3.updateToggles(
                                        max2,
                                        (ViewGroup) preferenceViewHolder4.itemView,
                                        true,
                                        true);
                                break;
                        }
                    }
                });
        final int i3 = 2;
        this.mPriorityButton.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.notification.app.ConversationPriorityPreference$$ExternalSyntheticLambda0
                    public final /* synthetic */ ConversationPriorityPreference f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i3) {
                            case 0:
                                ConversationPriorityPreference conversationPriorityPreference =
                                        this.f$0;
                                PreferenceViewHolder preferenceViewHolder2 = preferenceViewHolder;
                                conversationPriorityPreference.getClass();
                                conversationPriorityPreference.callChangeListener(
                                        new Pair(2, Boolean.FALSE));
                                conversationPriorityPreference.updateToggles(
                                        2, (ViewGroup) preferenceViewHolder2.itemView, false, true);
                                break;
                            case 1:
                                ConversationPriorityPreference conversationPriorityPreference2 =
                                        this.f$0;
                                PreferenceViewHolder preferenceViewHolder3 = preferenceViewHolder;
                                conversationPriorityPreference2.getClass();
                                int max = Math.max(0, 3);
                                conversationPriorityPreference2.callChangeListener(
                                        new Pair(Integer.valueOf(max), Boolean.FALSE));
                                conversationPriorityPreference2.updateToggles(
                                        max,
                                        (ViewGroup) preferenceViewHolder3.itemView,
                                        false,
                                        true);
                                break;
                            default:
                                ConversationPriorityPreference conversationPriorityPreference3 =
                                        this.f$0;
                                PreferenceViewHolder preferenceViewHolder4 = preferenceViewHolder;
                                conversationPriorityPreference3.getClass();
                                int max2 = Math.max(0, 3);
                                conversationPriorityPreference3.callChangeListener(
                                        new Pair(Integer.valueOf(max2), Boolean.TRUE));
                                conversationPriorityPreference3.updateToggles(
                                        max2,
                                        (ViewGroup) preferenceViewHolder4.itemView,
                                        true,
                                        true);
                                break;
                        }
                    }
                });
    }

    public final void setSelected(final View view, final boolean z) {
        ColorStateList colorAttr =
                Utils.getColorAttr(
                        getContext(),
                        R.attr.notification_importance_button_foreground_color_selected);
        ColorStateList colorAttr2 =
                Utils.getColorAttr(
                        getContext(),
                        R.attr.notification_importance_button_foreground_color_unselected);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        TextView textView = (TextView) view.findViewById(R.id.label);
        TextView textView2 = (TextView) view.findViewById(R.id.summary);
        imageView.setImageTintList(z ? colorAttr : colorAttr2);
        textView.setTextColor(z ? colorAttr : colorAttr2);
        if (!z) {
            colorAttr = colorAttr2;
        }
        textView2.setTextColor(colorAttr);
        textView2.setVisibility(z ? 0 : 8);
        view.setBackground(
                this.mContext.getDrawable(
                        z
                                ? R.drawable.notification_importance_button_background_selected
                                : R.drawable.notification_importance_button_background_unselected));
        view.post(
                new Runnable() { // from class:
                                 // com.android.settings.notification.app.ConversationPriorityPreference$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        view.setSelected(z);
                    }
                });
    }

    public final void updateToggles(int i, ViewGroup viewGroup, boolean z, boolean z2) {
        if (z2) {
            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(100L);
            TransitionManager.beginDelayedTransition(viewGroup, autoTransition);
        }
        if (i <= 2 && i > -1000) {
            setSelected(this.mPriorityButton, false);
            setSelected(this.mAlertButton, false);
            setSelected(this.mSilenceButton, true);
        } else if (z) {
            setSelected(this.mPriorityButton, true);
            setSelected(this.mAlertButton, false);
            setSelected(this.mSilenceButton, false);
        } else {
            setSelected(this.mPriorityButton, false);
            setSelected(this.mAlertButton, true);
            setSelected(this.mSilenceButton, false);
        }
    }

    public ConversationPriorityPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsConfigurable = true;
        this.mContext = context;
        setLayoutResource(R.layout.notif_priority_conversation_preference);
    }

    public ConversationPriorityPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsConfigurable = true;
        this.mContext = context;
        setLayoutResource(R.layout.notif_priority_conversation_preference);
    }

    public ConversationPriorityPreference(Context context) {
        super(context);
        this.mIsConfigurable = true;
        this.mContext = context;
        setLayoutResource(R.layout.notif_priority_conversation_preference);
    }
}
