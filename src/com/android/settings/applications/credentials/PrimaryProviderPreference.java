package com.android.settings.applications.credentials;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.hidden_from_bootclasspath.android.credentials.flags.Flags;
import com.android.settings.R;
import com.android.settings.widget.GearPreference;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrimaryProviderPreference extends GearPreference {
    public View mButtonFrameView;
    public boolean mButtonsCompactMode;
    public Button mChangeButton;
    public DefaultCombinedPreferenceController.AnonymousClass1 mDelegate;
    public View mGearView;
    public Button mOpenButton;
    public boolean mOpenButtonVisible;

    public PrimaryProviderPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mChangeButton = null;
        this.mOpenButton = null;
        this.mButtonFrameView = null;
        this.mGearView = null;
        this.mDelegate = null;
        this.mButtonsCompactMode = false;
        this.mOpenButtonVisible = false;
        if (Flags.newSettingsUi()) {
            setLayoutResource(R.layout.preference_credential_manager_with_buttons);
        }
    }

    @VisibleForTesting
    public View getButtonFrameView() {
        return this.mButtonFrameView;
    }

    @VisibleForTesting
    public Button getChangeButton() {
        return this.mChangeButton;
    }

    @VisibleForTesting
    public View getGearView() {
        return this.mGearView;
    }

    @VisibleForTesting
    public Button getOpenButton() {
        return this.mOpenButton;
    }

    @Override // com.android.settings.widget.GearPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (!Flags.newSettingsUi()) {
            setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.applications.credentials.PrimaryProviderPreference.1
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            Context context;
                            DefaultCombinedPreferenceController.AnonymousClass1 anonymousClass1 =
                                    PrimaryProviderPreference.this.mDelegate;
                            if (anonymousClass1 == null) {
                                return false;
                            }
                            DefaultCombinedPreferenceController
                                    defaultCombinedPreferenceController =
                                            DefaultCombinedPreferenceController.this;
                            context =
                                    ((AbstractPreferenceController)
                                                    defaultCombinedPreferenceController)
                                            .mContext;
                            CombinedProviderInfo.launchSettingsActivityIntent(
                                    context,
                                    anonymousClass1.val$packageName,
                                    anonymousClass1.val$settingsActivity,
                                    defaultCombinedPreferenceController.getUser());
                            return true;
                        }
                    });
            View findViewById = preferenceViewHolder.findViewById(R.id.settings_button);
            this.mGearView = findViewById;
            findViewById.setVisibility(0);
            final int i = 0;
            this.mGearView.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.applications.credentials.PrimaryProviderPreference.2
                        public final /* synthetic */ PrimaryProviderPreference this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            Context context;
                            switch (i) {
                                case 0:
                                    DefaultCombinedPreferenceController.AnonymousClass1
                                            anonymousClass1 = this.this$0.mDelegate;
                                    if (anonymousClass1 != null) {
                                        DefaultCombinedPreferenceController
                                                defaultCombinedPreferenceController =
                                                        DefaultCombinedPreferenceController.this;
                                        defaultCombinedPreferenceController.startActivity(
                                                defaultCombinedPreferenceController
                                                        .createIntentToOpenPicker());
                                        break;
                                    }
                                    break;
                                case 1:
                                    DefaultCombinedPreferenceController.AnonymousClass1
                                            anonymousClass12 = this.this$0.mDelegate;
                                    if (anonymousClass12 != null) {
                                        DefaultCombinedPreferenceController
                                                defaultCombinedPreferenceController2 =
                                                        DefaultCombinedPreferenceController.this;
                                        context =
                                                ((AbstractPreferenceController)
                                                                defaultCombinedPreferenceController2)
                                                        .mContext;
                                        CombinedProviderInfo.launchSettingsActivityIntent(
                                                context,
                                                anonymousClass12.val$packageName,
                                                anonymousClass12.val$settingsActivity,
                                                defaultCombinedPreferenceController2.getUser());
                                        break;
                                    }
                                    break;
                                default:
                                    DefaultCombinedPreferenceController.AnonymousClass1
                                            anonymousClass13 = this.this$0.mDelegate;
                                    if (anonymousClass13 != null) {
                                        DefaultCombinedPreferenceController
                                                defaultCombinedPreferenceController3 =
                                                        DefaultCombinedPreferenceController.this;
                                        defaultCombinedPreferenceController3.startActivity(
                                                defaultCombinedPreferenceController3
                                                        .createIntentToOpenPicker());
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
            return;
        }
        Button button = (Button) preferenceViewHolder.findViewById(R.id.open_button);
        this.mOpenButton = button;
        final int i2 = 1;
        button.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.applications.credentials.PrimaryProviderPreference.2
                    public final /* synthetic */ PrimaryProviderPreference this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        Context context;
                        switch (i2) {
                            case 0:
                                DefaultCombinedPreferenceController.AnonymousClass1
                                        anonymousClass1 = this.this$0.mDelegate;
                                if (anonymousClass1 != null) {
                                    DefaultCombinedPreferenceController
                                            defaultCombinedPreferenceController =
                                                    DefaultCombinedPreferenceController.this;
                                    defaultCombinedPreferenceController.startActivity(
                                            defaultCombinedPreferenceController
                                                    .createIntentToOpenPicker());
                                    break;
                                }
                                break;
                            case 1:
                                DefaultCombinedPreferenceController.AnonymousClass1
                                        anonymousClass12 = this.this$0.mDelegate;
                                if (anonymousClass12 != null) {
                                    DefaultCombinedPreferenceController
                                            defaultCombinedPreferenceController2 =
                                                    DefaultCombinedPreferenceController.this;
                                    context =
                                            ((AbstractPreferenceController)
                                                            defaultCombinedPreferenceController2)
                                                    .mContext;
                                    CombinedProviderInfo.launchSettingsActivityIntent(
                                            context,
                                            anonymousClass12.val$packageName,
                                            anonymousClass12.val$settingsActivity,
                                            defaultCombinedPreferenceController2.getUser());
                                    break;
                                }
                                break;
                            default:
                                DefaultCombinedPreferenceController.AnonymousClass1
                                        anonymousClass13 = this.this$0.mDelegate;
                                if (anonymousClass13 != null) {
                                    DefaultCombinedPreferenceController
                                            defaultCombinedPreferenceController3 =
                                                    DefaultCombinedPreferenceController.this;
                                    defaultCombinedPreferenceController3.startActivity(
                                            defaultCombinedPreferenceController3
                                                    .createIntentToOpenPicker());
                                    break;
                                }
                                break;
                        }
                    }
                });
        this.mOpenButton.setVisibility(this.mOpenButtonVisible ? 0 : 8);
        preferenceViewHolder.itemView.setClickable(false);
        Button button2 = (Button) preferenceViewHolder.findViewById(R.id.change_button);
        this.mChangeButton = button2;
        final int i3 = 2;
        button2.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.applications.credentials.PrimaryProviderPreference.2
                    public final /* synthetic */ PrimaryProviderPreference this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        Context context;
                        switch (i3) {
                            case 0:
                                DefaultCombinedPreferenceController.AnonymousClass1
                                        anonymousClass1 = this.this$0.mDelegate;
                                if (anonymousClass1 != null) {
                                    DefaultCombinedPreferenceController
                                            defaultCombinedPreferenceController =
                                                    DefaultCombinedPreferenceController.this;
                                    defaultCombinedPreferenceController.startActivity(
                                            defaultCombinedPreferenceController
                                                    .createIntentToOpenPicker());
                                    break;
                                }
                                break;
                            case 1:
                                DefaultCombinedPreferenceController.AnonymousClass1
                                        anonymousClass12 = this.this$0.mDelegate;
                                if (anonymousClass12 != null) {
                                    DefaultCombinedPreferenceController
                                            defaultCombinedPreferenceController2 =
                                                    DefaultCombinedPreferenceController.this;
                                    context =
                                            ((AbstractPreferenceController)
                                                            defaultCombinedPreferenceController2)
                                                    .mContext;
                                    CombinedProviderInfo.launchSettingsActivityIntent(
                                            context,
                                            anonymousClass12.val$packageName,
                                            anonymousClass12.val$settingsActivity,
                                            defaultCombinedPreferenceController2.getUser());
                                    break;
                                }
                                break;
                            default:
                                DefaultCombinedPreferenceController.AnonymousClass1
                                        anonymousClass13 = this.this$0.mDelegate;
                                if (anonymousClass13 != null) {
                                    DefaultCombinedPreferenceController
                                            defaultCombinedPreferenceController3 =
                                                    DefaultCombinedPreferenceController.this;
                                    defaultCombinedPreferenceController3.startActivity(
                                            defaultCombinedPreferenceController3
                                                    .createIntentToOpenPicker());
                                    break;
                                }
                                break;
                        }
                    }
                });
        this.mButtonFrameView = preferenceViewHolder.findViewById(R.id.credman_button_frame);
        updateButtonFramePadding();
    }

    @Override // com.android.settings.widget.GearPreference,
              // com.android.settingslib.widget.TwoTargetPreference
    public final boolean shouldHideSecondTarget() {
        return Flags.newSettingsUi();
    }

    public final void updateButtonFramePadding() {
        if (this.mButtonFrameView == null) {
            return;
        }
        int dimensionPixelSize =
                this.mButtonsCompactMode
                        ? getContext()
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.credman_primary_provider_pref_left_padding)
                        : getContext()
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.credman_primary_provider_pref_left_padding_compact);
        View view = this.mButtonFrameView;
        view.setPadding(
                dimensionPixelSize,
                view.getPaddingTop(),
                dimensionPixelSize,
                this.mButtonFrameView.getPaddingBottom());
    }

    public PrimaryProviderPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mChangeButton = null;
        this.mOpenButton = null;
        this.mButtonFrameView = null;
        this.mGearView = null;
        this.mDelegate = null;
        this.mButtonsCompactMode = false;
        this.mOpenButtonVisible = false;
        if (Flags.newSettingsUi()) {
            setLayoutResource(R.layout.preference_credential_manager_with_buttons);
        }
    }
}
