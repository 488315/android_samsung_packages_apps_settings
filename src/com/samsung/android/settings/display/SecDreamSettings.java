package com.samsung.android.settings.display;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;
import com.android.settings.DisplaySettings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settingslib.dream.DreamBackend;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecButtonPreference;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDreamSettings extends SettingsPreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ArrayList mAddedDreamInfos;
    public DreamBackend mBackend;
    public Context mContext;
    public MenuItem[] mMenuItemsWhenEnabled;
    public boolean mOldDreamEnabled;
    public SecButtonPreference mPreviewButton;
    public AnonymousClass1 mScreenSaverObserver;
    public final PackageReceiver mPackageReceiver = new PackageReceiver();
    public boolean mSkipRemoveAddPreferenceUpdateFromTouch = false;
    public boolean mBlockListClickEvent = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.SecDreamSettings$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            LoggingHelper.insertEventLogging(47, 4303, 3L);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.SecDreamSettings$5, reason: invalid class name */
    public final class AnonymousClass5 implements View.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass5(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            switch (this.$r8$classId) {
                case 0:
                    SecDreamSettings secDreamSettings = (SecDreamSettings) this.this$0;
                    secDreamSettings.mBlockListClickEvent = true;
                    IDreamManager iDreamManager = secDreamSettings.mBackend.mDreamManager;
                    if (iDreamManager != null) {
                        try {
                            iDreamManager.dream();
                        } catch (RemoteException e) {
                            Log.w("DreamBackend", "Failed to dream", e);
                        }
                    }
                    LoggingHelper.insertEventLogging(47, 4301);
                    break;
                default:
                    view.setContentDescription(((NoneDreamInfoPreference) this.this$0).getTitle());
                    ((NoneDreamInfoPreference) this.this$0).performClick();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DreamInfoPreference extends SecPreference {
        public final DreamBackend.DreamInfo mInfo;
        public Boolean mLastPreference;
        public RadioButton mRadioButton;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.display.SecDreamSettings$DreamInfoPreference$1, reason: invalid class name */
        public final class AnonymousClass1 implements View.OnTouchListener {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ PreferenceViewHolder val$holder;

            public /* synthetic */ AnonymousClass1(PreferenceViewHolder preferenceViewHolder, int i) {
                this.$r8$classId = i;
                this.val$holder = preferenceViewHolder;
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                switch (this.$r8$classId) {
                    case 0:
                        this.val$holder.itemView.onTouchEvent(motionEvent);
                        break;
                    default:
                        this.val$holder.itemView.onTouchEvent(motionEvent);
                        break;
                }
                return false;
            }
        }

        public DreamInfoPreference(Context context, DreamBackend.DreamInfo dreamInfo) {
            super(context, null);
            this.mLastPreference = Boolean.FALSE;
            this.mInfo = dreamInfo;
            setLayoutResource(R.layout.sec_dream_info_row);
            setTitle(dreamInfo.caption);
            setIcon(dreamInfo.icon);
            setSingleLineTitle(true);
        }

        @Override // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            RadioButton radioButton = (RadioButton) preferenceViewHolder.findViewById(android.R.id.button1);
            this.mRadioButton = radioButton;
            radioButton.setChecked(SecDreamSettings.isDreamEnabled(SecDreamSettings.this.mContext) && this.mInfo.isActive);
            final int i = 0;
            this.mRadioButton.setOnTouchListener(new AnonymousClass1(preferenceViewHolder, i));
            preferenceViewHolder.itemView.setOnClickListener(new View.OnClickListener(this) { // from class: com.samsung.android.settings.display.SecDreamSettings.DreamInfoPreference.2
                public final /* synthetic */ DreamInfoPreference this$1;

                {
                    this.this$1 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i2 = 1;
                    switch (i) {
                        case 0:
                            DreamInfoPreference dreamInfoPreference = this.this$1;
                            ComponentName componentName = dreamInfoPreference.mInfo.componentName;
                            if (componentName != null) {
                                String flattenToString = componentName.flattenToString();
                                if (!"com.android.dreams.basic/com.android.dreams.basic.Colors".equals(flattenToString)) {
                                    if ("com.android.dreams.phototable/com.android.dreams.phototable.FlipperDream".equals(flattenToString)) {
                                        i2 = 2;
                                    } else if ("com.android.dreams.phototable/com.android.dreams.phototable.PhotoTableDream".equals(flattenToString)) {
                                        i2 = 3;
                                    } else if ("com.google.android.apps.photos/com.google.android.apps.photos.daydream.PhotosDreamService".equals(flattenToString)) {
                                        i2 = 4;
                                    } else {
                                        List dreamInfos = SecDreamSettings.this.mBackend.getDreamInfos();
                                        int i3 = 0;
                                        int i4 = 0;
                                        while (true) {
                                            ArrayList arrayList = (ArrayList) dreamInfos;
                                            if (i3 < Math.min(arrayList.size(), 13)) {
                                                String flattenToString2 = ((DreamBackend.DreamInfo) arrayList.get(i3)).componentName.flattenToString();
                                                if ("com.android.dreams.basic/com.android.dreams.basic.Colors".equals(flattenToString2) || "com.android.dreams.phototable/com.android.dreams.phototable.FlipperDream".equals(flattenToString2) || "com.android.dreams.phototable/com.android.dreams.phototable.PhotoTableDream".equals(flattenToString2) || "com.google.android.apps.photos/com.google.android.apps.photos.daydream.PhotosDreamService".equals(flattenToString2)) {
                                                    i4++;
                                                } else if (flattenToString.equals(flattenToString2)) {
                                                    i2 = ((i3 + 1) - i4) * 11;
                                                }
                                                i3++;
                                            } else {
                                                i2 = -1;
                                            }
                                        }
                                    }
                                }
                                if (i2 != -1) {
                                    LoggingHelper.insertEventLogging(47, 4304, i2);
                                }
                            }
                            view.setContentDescription(this.this$1.getTitle());
                            this.this$1.performClick();
                            break;
                        default:
                            LoggingHelper.insertEventLogging(47, 7469);
                            DreamInfoPreference dreamInfoPreference2 = this.this$1;
                            DreamBackend.DreamInfo dreamInfo = dreamInfoPreference2.mInfo;
                            if (dreamInfo != null && dreamInfo.settingsComponentName != null) {
                                try {
                                    SecDreamSettings.this.mContext.startActivity(new Intent().setComponent(this.this$1.mInfo.settingsComponentName));
                                    break;
                                } catch (Exception unused) {
                                    int i5 = SecDreamSettings.$r8$clinit;
                                    Log.w("SecDreamSettings", "Failed to start settings " + this.this$1.mInfo.settingsComponentName);
                                }
                            }
                            break;
                    }
                }
            });
            boolean z = this.mInfo.isActive && SecDreamSettings.this.mBackend.isEnabled();
            boolean z2 = this.mInfo.settingsComponentName != null;
            ImageView imageView = (ImageView) preferenceViewHolder.findViewById(android.R.id.button2);
            imageView.setVisibility(z2 ? 0 : 4);
            imageView.setAlpha(z ? 1.0f : 0.4f);
            imageView.setEnabled(z);
            imageView.setFocusable(this.mInfo.isActive);
            imageView.setVerticalScrollBarEnabled(false);
            final int i2 = 1;
            imageView.setOnClickListener(new View.OnClickListener(this) { // from class: com.samsung.android.settings.display.SecDreamSettings.DreamInfoPreference.2
                public final /* synthetic */ DreamInfoPreference this$1;

                {
                    this.this$1 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i22 = 1;
                    switch (i2) {
                        case 0:
                            DreamInfoPreference dreamInfoPreference = this.this$1;
                            ComponentName componentName = dreamInfoPreference.mInfo.componentName;
                            if (componentName != null) {
                                String flattenToString = componentName.flattenToString();
                                if (!"com.android.dreams.basic/com.android.dreams.basic.Colors".equals(flattenToString)) {
                                    if ("com.android.dreams.phototable/com.android.dreams.phototable.FlipperDream".equals(flattenToString)) {
                                        i22 = 2;
                                    } else if ("com.android.dreams.phototable/com.android.dreams.phototable.PhotoTableDream".equals(flattenToString)) {
                                        i22 = 3;
                                    } else if ("com.google.android.apps.photos/com.google.android.apps.photos.daydream.PhotosDreamService".equals(flattenToString)) {
                                        i22 = 4;
                                    } else {
                                        List dreamInfos = SecDreamSettings.this.mBackend.getDreamInfos();
                                        int i3 = 0;
                                        int i4 = 0;
                                        while (true) {
                                            ArrayList arrayList = (ArrayList) dreamInfos;
                                            if (i3 < Math.min(arrayList.size(), 13)) {
                                                String flattenToString2 = ((DreamBackend.DreamInfo) arrayList.get(i3)).componentName.flattenToString();
                                                if ("com.android.dreams.basic/com.android.dreams.basic.Colors".equals(flattenToString2) || "com.android.dreams.phototable/com.android.dreams.phototable.FlipperDream".equals(flattenToString2) || "com.android.dreams.phototable/com.android.dreams.phototable.PhotoTableDream".equals(flattenToString2) || "com.google.android.apps.photos/com.google.android.apps.photos.daydream.PhotosDreamService".equals(flattenToString2)) {
                                                    i4++;
                                                } else if (flattenToString.equals(flattenToString2)) {
                                                    i22 = ((i3 + 1) - i4) * 11;
                                                }
                                                i3++;
                                            } else {
                                                i22 = -1;
                                            }
                                        }
                                    }
                                }
                                if (i22 != -1) {
                                    LoggingHelper.insertEventLogging(47, 4304, i22);
                                }
                            }
                            view.setContentDescription(this.this$1.getTitle());
                            this.this$1.performClick();
                            break;
                        default:
                            LoggingHelper.insertEventLogging(47, 7469);
                            DreamInfoPreference dreamInfoPreference2 = this.this$1;
                            DreamBackend.DreamInfo dreamInfo = dreamInfoPreference2.mInfo;
                            if (dreamInfo != null && dreamInfo.settingsComponentName != null) {
                                try {
                                    SecDreamSettings.this.mContext.startActivity(new Intent().setComponent(this.this$1.mInfo.settingsComponentName));
                                    break;
                                } catch (Exception unused) {
                                    int i5 = SecDreamSettings.$r8$clinit;
                                    Log.w("SecDreamSettings", "Failed to start settings " + this.this$1.mInfo.settingsComponentName);
                                }
                            }
                            break;
                    }
                }
            });
            preferenceViewHolder.findViewById(R.id.config_divider).setVisibility(z2 ? 0 : 4);
            View findViewById = preferenceViewHolder.findViewById(R.id.divider);
            if (this.mLastPreference.booleanValue()) {
                findViewById.setVisibility(8);
            }
        }

        @Override // androidx.preference.Preference
        public final void performClick() {
            SecDreamSettings.this.mBlockListClickEvent = false;
            super.performClick();
            SecDreamSettings.this.mSkipRemoveAddPreferenceUpdateFromTouch = true;
            for (int i = 0; i < SecDreamSettings.this.getPreferenceScreen().getPreferenceCount(); i++) {
                Preference preference = SecDreamSettings.this.getPreferenceScreen().getPreference(i);
                if (preference instanceof DreamInfoPreference) {
                    DreamInfoPreference dreamInfoPreference = (DreamInfoPreference) preference;
                    dreamInfoPreference.mInfo.isActive = false;
                    dreamInfoPreference.notifyChanged();
                } else if (preference instanceof NoneDreamInfoPreference) {
                    ((NoneDreamInfoPreference) preference).notifyChanged();
                }
            }
            this.mInfo.isActive = true;
            if (!SecDreamSettings.isDreamEnabled(SecDreamSettings.this.mContext)) {
                DreamBackend dreamBackend = DreamBackend.getInstance(SecDreamSettings.this.mContext);
                Settings.Secure.putInt(dreamBackend.mContext.getContentResolver(), "screensaver_enabled", 1);
                dreamBackend.logDreamSettingChangeToStatsd(1);
            }
            SecDreamSettings.this.mBackend.setActiveDream(this.mInfo.componentName);
            notifyChanged();
            SecDreamSettings secDreamSettings = SecDreamSettings.this;
            boolean isDreamEnabled = SecDreamSettings.isDreamEnabled(secDreamSettings.mContext);
            if (secDreamSettings.mOldDreamEnabled != isDreamEnabled) {
                LoggingHelper.insertEventLogging(47, 4300, isDreamEnabled ? 1L : 0L);
                secDreamSettings.mOldDreamEnabled = isDreamEnabled;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NoneDreamInfoPreference extends SecPreference {
        public RadioButton mRadioButton;

        public NoneDreamInfoPreference(Context context) {
            super(context, null);
            setLayoutResource(R.layout.sec_dream_info_row);
            setTitle(SecDreamSettings.this.getResources().getString(R.string.sec_screensaver_none));
            setSingleLineTitle(true);
            setIcon(R.drawable.sec_screen_saver_none);
        }

        @Override // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            RadioButton radioButton = (RadioButton) preferenceViewHolder.findViewById(android.R.id.button1);
            this.mRadioButton = radioButton;
            radioButton.setChecked(!SecDreamSettings.isDreamEnabled(SecDreamSettings.this.mContext));
            int i = 1;
            this.mRadioButton.setOnTouchListener(new DreamInfoPreference.AnonymousClass1(preferenceViewHolder, i));
            preferenceViewHolder.itemView.setOnClickListener(new AnonymousClass5(i, this));
            ((ImageView) preferenceViewHolder.findViewById(android.R.id.button2)).setVisibility(4);
            preferenceViewHolder.findViewById(R.id.config_divider).setVisibility(4);
        }

        @Override // androidx.preference.Preference
        public final void performClick() {
            if (SecDreamSettings.this.mBlockListClickEvent) {
                return;
            }
            super.performClick();
            SecDreamSettings.this.mSkipRemoveAddPreferenceUpdateFromTouch = true;
            for (int i = 0; i < SecDreamSettings.this.getPreferenceScreen().getPreferenceCount(); i++) {
                Preference preference = SecDreamSettings.this.getPreferenceScreen().getPreference(i);
                if (preference instanceof DreamInfoPreference) {
                    DreamInfoPreference dreamInfoPreference = (DreamInfoPreference) preference;
                    dreamInfoPreference.mInfo.isActive = false;
                    dreamInfoPreference.notifyChanged();
                } else if (preference instanceof NoneDreamInfoPreference) {
                    ((NoneDreamInfoPreference) preference).notifyChanged();
                }
            }
            SecDreamSettings.this.mBackend.setActiveDream(null);
            if (SecDreamSettings.isDreamEnabled(SecDreamSettings.this.mContext)) {
                DreamBackend dreamBackend = DreamBackend.getInstance(SecDreamSettings.this.mContext);
                Settings.Secure.putInt(dreamBackend.mContext.getContentResolver(), "screensaver_enabled", 0);
                dreamBackend.logDreamSettingChangeToStatsd(1);
            }
            notifyChanged();
            SecDreamSettings secDreamSettings = SecDreamSettings.this;
            boolean isDreamEnabled = SecDreamSettings.isDreamEnabled(secDreamSettings.mContext);
            if (secDreamSettings.mOldDreamEnabled != isDreamEnabled) {
                LoggingHelper.insertEventLogging(47, 4300, isDreamEnabled ? 1L : 0L);
                secDreamSettings.mOldDreamEnabled = isDreamEnabled;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PackageReceiver extends BroadcastReceiver {
        public PackageReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int i = SecDreamSettings.$r8$clinit;
            SecDreamSettings.this.refreshFromBackend();
        }
    }

    public static boolean isDreamEnabled(Context context) {
        return DreamBackend.getInstance(context).isEnabled();
    }

    public static String toDreamInfoString(DreamBackend.DreamInfo dreamInfo) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(dreamInfo.caption);
        sb.append(',');
        sb.append(dreamInfo.componentName);
        if (dreamInfo.settingsComponentName != null) {
            sb.append("settings=");
            sb.append(dreamInfo.settingsComponentName);
        }
        sb.append(']');
        return sb.toString();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        if (i == 1) {
            return VolteConstants.ErrorCode.PRECONDITION_FAILURE;
        }
        return 0;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return DisplaySettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 47;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Resources resources = getResources();
        TextView textView = (TextView) getView().findViewById(android.R.id.empty);
        textView.setGravity(48);
        textView.setTextAlignment(5);
        textView.setTextAppearance(getActivity(), R.style.description_text);
        textView.setLinkTextColor(resources.getColor(R.color.sec_widget_description_link_text_color));
        textView.setPadding(resources.getDimensionPixelSize(R.dimen.sec_body_text_side_padding), resources.getDimensionPixelSize(R.dimen.sec_body_text_top_padding), textView.getPaddingRight(), textView.getPaddingBottom());
        String string = !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_DOCK") ? resources.getString(R.string.sec_screensaver_settings_summary) : resources.getString(R.string.sec_screensaver_settings_summary_wo_dock);
        if (Utils.isTablet()) {
            StringBuilder m = PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(string, "\n");
            m.append(resources.getString(R.string.sec_screensaver_settings_summary_charging));
            string = m.toString();
        }
        textView.setText(string);
        textView.setMovementMethod(new ScrollingMovementMethod());
        setEmptyView(textView);
        setDivider(null);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Activity activity) {
        activity.getClass();
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActivity().setTitle(R.string.screensaver_settings_title);
        this.mBackend = new DreamBackend(getActivity());
        setHasOptionsMenu(true);
        this.mOldDreamEnabled = isDreamEnabled(getActivity());
        this.mAddedDreamInfos = new ArrayList();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        int i2 = 1;
        if (i != 1) {
            return super.onCreateDialog(i);
        }
        CharSequence[] charSequenceArr = {this.mContext.getString(R.string.screensaver_settings_summary_dock), this.mContext.getString(R.string.screensaver_settings_summary_sleep), this.mContext.getString(R.string.sec_screensaver_settings_summary_either_short)};
        DreamBackend dreamBackend = this.mBackend;
        if (Settings.Secure.getInt(dreamBackend.mContext.getContentResolver(), "screensaver_activate_on_dock", dreamBackend.mDreamsActivatedOnDockByDefault ? 1 : 0) == 1) {
            DreamBackend dreamBackend2 = this.mBackend;
            if (Settings.Secure.getInt(dreamBackend2.mContext.getContentResolver(), "screensaver_activate_on_sleep", dreamBackend2.mDreamsActivatedOnSleepByDefault ? 1 : 0) == 1) {
                i2 = 2;
                return new AlertDialog.Builder(this.mContext).setTitle(R.string.screensaver_settings_when_to_dream).setSingleChoiceItems(charSequenceArr, i2, new DialogInterface.OnClickListener() { // from class: com.samsung.android.settings.display.SecDreamSettings.4
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        Settings.Secure.putInt(SecDreamSettings.this.mBackend.mContext.getContentResolver(), "screensaver_activate_on_dock", (i3 == 0 || i3 == 2) ? 1 : 0);
                        Settings.Secure.putInt(SecDreamSettings.this.mBackend.mContext.getContentResolver(), "screensaver_activate_on_sleep", (i3 == 1 || i3 == 2) ? 1 : 0);
                        dialogInterface.dismiss();
                        LoggingHelper.insertEventLogging(47, 4303, i3);
                    }
                }).setNegativeButton(android.R.string.cancel, new AnonymousClass3()).create();
            }
        }
        DreamBackend dreamBackend3 = this.mBackend;
        if (Settings.Secure.getInt(dreamBackend3.mContext.getContentResolver(), "screensaver_activate_on_dock", dreamBackend3.mDreamsActivatedOnDockByDefault ? 1 : 0) == 1) {
            i2 = 0;
        } else {
            DreamBackend dreamBackend4 = this.mBackend;
            if (Settings.Secure.getInt(dreamBackend4.mContext.getContentResolver(), "screensaver_activate_on_sleep", dreamBackend4.mDreamsActivatedOnSleepByDefault ? 1 : 0) != 1) {
                i2 = -1;
            }
        }
        return new AlertDialog.Builder(this.mContext).setTitle(R.string.screensaver_settings_when_to_dream).setSingleChoiceItems(charSequenceArr, i2, new DialogInterface.OnClickListener() { // from class: com.samsung.android.settings.display.SecDreamSettings.4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                Settings.Secure.putInt(SecDreamSettings.this.mBackend.mContext.getContentResolver(), "screensaver_activate_on_dock", (i3 == 0 || i3 == 2) ? 1 : 0);
                Settings.Secure.putInt(SecDreamSettings.this.mBackend.mContext.getContentResolver(), "screensaver_activate_on_sleep", (i3 == 1 || i3 == 2) ? 1 : 0);
                dialogInterface.dismiss();
                LoggingHelper.insertEventLogging(47, 4303, i3);
            }
        }).setNegativeButton(android.R.string.cancel, new AnonymousClass3()).create();
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [com.samsung.android.settings.display.SecDreamSettings$$ExternalSyntheticLambda0] */
    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        boolean isEnabled = this.mBackend.isEnabled();
        super.onCreateOptionsMenu(menu, menuInflater);
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_DOCK")) {
            final ?? r4 = new Runnable() { // from class: com.samsung.android.settings.display.SecDreamSettings$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SecDreamSettings secDreamSettings = SecDreamSettings.this;
                    int i = SecDreamSettings.$r8$clinit;
                    secDreamSettings.showDialog(1);
                }
            };
            MenuItem add = menu.add(R.string.screensaver_settings_when_to_dream);
            add.setShowAsAction(0);
            add.setEnabled(isEnabled);
            add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.samsung.android.settings.display.SecDreamSettings.2
                @Override // android.view.MenuItem.OnMenuItemClickListener
                public final boolean onMenuItemClick(MenuItem menuItem) {
                    r4.run();
                    return true;
                }
            });
            this.mMenuItemsWhenEnabled = new MenuItem[]{add};
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mContext.unregisterReceiver(this.mPackageReceiver);
        if (this.mScreenSaverObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mScreenSaverObserver);
            this.mScreenSaverObserver = null;
        }
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.samsung.android.settings.display.SecDreamSettings$1] */
    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mBlockListClickEvent = false;
        refreshFromBackend();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiver(this.mPackageReceiver, intentFilter);
        Handler handler = new Handler();
        try {
            if (this.mScreenSaverObserver == null) {
                this.mScreenSaverObserver = new ContentObserver(handler) { // from class: com.samsung.android.settings.display.SecDreamSettings.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        SecDreamSettings secDreamSettings = SecDreamSettings.this;
                        int i = SecDreamSettings.$r8$clinit;
                        secDreamSettings.getClass();
                        SecDreamSettings.this.refreshFromBackend();
                    }
                };
                this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("screensaver_components"), true, this.mScreenSaverObserver);
                this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("screensaver_enabled"), true, this.mScreenSaverObserver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void refreshFromBackend() {
        /*
            Method dump skipped, instructions count: 495
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.display.SecDreamSettings.refreshFromBackend():void");
    }
}
