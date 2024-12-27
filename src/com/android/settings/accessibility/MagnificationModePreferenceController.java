package com.android.settings.accessibility;

import android.R;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.DialogCreatable;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.utils.AnnotationSpan;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MagnificationModePreferenceController extends BasePreferenceController
        implements DialogCreatable, LifecycleObserver, OnCreate, OnSaveInstanceState {
    private static final int DIALOG_ID_BASE = 10;
    static final int DIALOG_MAGNIFICATION_MODE = 11;
    static final int DIALOG_MAGNIFICATION_TRIPLE_TAP_WARNING = 12;
    static final String EXTRA_MODE = "mode";
    static final String PREF_KEY = "screen_magnification_mode";
    private static final String TAG = "MagnificationModePreferenceController";
    private DialogHelper mDialogHelper;
    private ShortcutPreference mLinkPreference;
    ListView mMagnificationModesListView;
    private int mModeCache;
    private final List<MagnificationModeInfo> mModeInfos;
    private Preference mModePreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DialogHelper extends DialogCreatable {
        void showDialog(int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class MagnificationModeInfo extends ItemInfoArrayAdapter.ItemInfo {
        public final int mMagnificationMode;

        public MagnificationModeInfo(
                CharSequence charSequence, CharSequence charSequence2, int i, int i2) {
            super(charSequence, charSequence2, i);
            this.mMagnificationMode = i2;
        }
    }

    public MagnificationModePreferenceController(Context context, String str) {
        super(context, str);
        this.mModeCache = 0;
        this.mModeInfos = new ArrayList();
        initModeInfos();
    }

    private int computeSelectionIndex() {
        int size = this.mModeInfos.size();
        for (int i = 0; i < size; i++) {
            if (this.mModeInfos.get(i).mMagnificationMode == this.mModeCache) {
                return this.mMagnificationModesListView.getHeaderViewsCount() + i;
            }
        }
        Log.w(TAG, "computeSelectionIndex failed");
        return 0;
    }

    private Dialog createMagnificationModeDialog() {
        Context context = this.mContext;
        List<MagnificationModeInfo> list = this.mModeInfos;
        AdapterView.OnItemClickListener onItemClickListener =
                new AdapterView
                        .OnItemClickListener() { // from class:
                                                 // com.android.settings.accessibility.MagnificationModePreferenceController$$ExternalSyntheticLambda0
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(
                            AdapterView adapterView, View view, int i, long j) {
                        MagnificationModePreferenceController.this.onMagnificationModeSelected(
                                adapterView, view, i, j);
                    }
                };
        ListView listView = new ListView(context);
        listView.setId(R.id.list);
        listView.setDivider(null);
        listView.setChoiceMode(1);
        listView.setAdapter(
                (ListAdapter)
                        new ItemInfoArrayAdapter(
                                context,
                                com.android.settings.R.layout.dialog_single_radio_choice_list_item,
                                com.android.settings.R.id.title,
                                list));
        listView.setOnItemClickListener(onItemClickListener);
        this.mMagnificationModesListView = listView;
        this.mMagnificationModesListView.addHeaderView(
                LayoutInflater.from(this.mContext)
                        .inflate(
                                com.android.settings.R.layout
                                        .accessibility_magnification_mode_header,
                                (ViewGroup) this.mMagnificationModesListView,
                                false),
                null,
                false);
        this.mMagnificationModesListView.setItemChecked(computeSelectionIndex(), true);
        return AccessibilityDialogUtils.createCustomDialog(
                this.mContext,
                this.mContext.getString(
                        com.android.settings.R.string
                                .accessibility_magnification_mode_dialog_title),
                this.mMagnificationModesListView,
                this.mContext.getString(com.android.settings.R.string.save),
                new MagnificationModePreferenceController$$ExternalSyntheticLambda1(this, 0),
                this.mContext.getString(com.android.settings.R.string.cancel),
                null);
    }

    private Dialog createMagnificationTripleTapWarningDialog() {
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(
                                com.android.settings.R.layout
                                        .magnification_triple_tap_warning_dialog,
                                (ViewGroup) null);
        AlertDialog createCustomDialog =
                AccessibilityDialogUtils.createCustomDialog(
                        this.mContext,
                        this.mContext.getString(
                                com.android.settings.R.string
                                        .accessibility_magnification_triple_tap_warning_title),
                        inflate,
                        this.mContext.getString(
                                com.android.settings.R.string
                                        .accessibility_magnification_triple_tap_warning_positive_button),
                        new MagnificationModePreferenceController$$ExternalSyntheticLambda1(
                                this, 1),
                        this.mContext.getString(
                                com.android.settings.R.string
                                        .accessibility_magnification_triple_tap_warning_negative_button),
                        new MagnificationModePreferenceController$$ExternalSyntheticLambda1(
                                this, 2));
        updateLinkInTripleTapWarningDialog(createCustomDialog, inflate);
        return createCustomDialog;
    }

    private void initModeInfos() {
        this.mModeInfos.add(
                new MagnificationModeInfo(
                        this.mContext.getText(
                                com.android.settings.R.string
                                        .accessibility_magnification_mode_dialog_option_full_screen),
                        null,
                        com.android.settings.R.drawable.a11y_magnification_mode_fullscreen,
                        1));
        this.mModeInfos.add(
                new MagnificationModeInfo(
                        this.mContext.getText(
                                com.android.settings.R.string
                                        .accessibility_magnification_mode_dialog_option_window),
                        null,
                        com.android.settings.R.drawable.a11y_magnification_mode_window,
                        2));
        this.mModeInfos.add(
                new MagnificationModeInfo(
                        this.mContext.getText(
                                com.android.settings.R.string
                                        .accessibility_magnification_mode_dialog_option_switch),
                        this.mContext.getText(
                                com.android.settings.R.string
                                        .accessibility_magnification_area_settings_mode_switch_summary),
                        com.android.settings.R.drawable.a11y_magnification_mode_switch,
                        3));
    }

    public static boolean isTripleTapEnabled(Context context) {
        return Settings.Secure.getInt(
                        context.getContentResolver(),
                        "accessibility_display_magnification_enabled",
                        0)
                == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$displayPreference$0(Preference preference) {
        this.mModeCache = MagnificationCapabilities.getCapabilities(this.mContext);
        this.mDialogHelper.showDialog(11);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateLinkInTripleTapWarningDialog$1(
            Dialog dialog, View view) {
        updateCapabilitiesAndSummary(this.mModeCache);
        this.mLinkPreference.performClick();
        dialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMagnificationModeSelected(AdapterView<?> adapterView, View view, int i, long j) {
        int i2 =
                ((MagnificationModeInfo) this.mMagnificationModesListView.getItemAtPosition(i))
                        .mMagnificationMode;
        if (i2 == this.mModeCache) {
            return;
        }
        this.mModeCache = i2;
    }

    private void updateCapabilitiesAndSummary(int i) {
        this.mModeCache = i;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Settings.Secure.putIntForUser(
                contentResolver,
                "accessibility_magnification_capability",
                i,
                contentResolver.getUserId());
        this.mModePreference.setSummary(
                MagnificationCapabilities.getSummary(this.mContext, this.mModeCache));
    }

    private void updateLinkInTripleTapWarningDialog(final Dialog dialog, View view) {
        TextView textView = (TextView) view.findViewById(com.android.settings.R.id.message);
        CharSequence linkify =
                AnnotationSpan.linkify(
                        this.mContext.getText(
                                com.android.settings.R.string
                                        .accessibility_magnification_triple_tap_warning_message),
                        new AnnotationSpan.LinkInfo(
                                new View
                                        .OnClickListener() { // from class:
                                                             // com.android.settings.accessibility.MagnificationModePreferenceController$$ExternalSyntheticLambda2
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view2) {
                                        MagnificationModePreferenceController.this
                                                .lambda$updateLinkInTripleTapWarningDialog$1(
                                                        dialog, view2);
                                    }
                                }));
        if (textView != null) {
            textView.setText(linkify);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        dialog.setContentView(view);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mModePreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mLinkPreference =
                (ShortcutPreference) preferenceScreen.findPreference("shortcut_preference");
        this.mModePreference.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.android.settings.accessibility.MagnificationModePreferenceController$$ExternalSyntheticLambda3
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        boolean lambda$displayPreference$0;
                        lambda$displayPreference$0 =
                                MagnificationModePreferenceController.this
                                        .lambda$displayPreference$0(preference);
                        return lambda$displayPreference$0;
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.DialogCreatable
    public int getDialogMetricsCategory(int i) {
        if (i != 11) {
            return i != 12 ? 0 : 1923;
        }
        return 1816;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return MagnificationCapabilities.getSummary(
                this.mContext, MagnificationCapabilities.getCapabilities(this.mContext));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.mModeCache = bundle.getInt(EXTRA_MODE, 0);
        }
    }

    @Override // com.android.settings.DialogCreatable
    public Dialog onCreateDialog(int i) {
        if (i == 11) {
            return createMagnificationModeDialog();
        }
        if (i != 12) {
            return null;
        }
        return createMagnificationTripleTapWarningDialog();
    }

    public void onMagnificationModeDialogPositiveButtonClicked(
            DialogInterface dialogInterface, int i) {
        int checkedItemPosition = this.mMagnificationModesListView.getCheckedItemPosition();
        if (checkedItemPosition == -1) {
            Log.w(TAG, "invalid index");
            return;
        }
        this.mModeCache =
                ((MagnificationModeInfo)
                                this.mMagnificationModesListView.getItemAtPosition(
                                        checkedItemPosition))
                        .mMagnificationMode;
        if (!isTripleTapEnabled(this.mContext) || this.mModeCache == 1) {
            updateCapabilitiesAndSummary(this.mModeCache);
        } else {
            this.mDialogHelper.showDialog(12);
        }
    }

    public void onMagnificationTripleTapWarningDialogNegativeButtonClicked(
            DialogInterface dialogInterface, int i) {
        this.mModeCache = MagnificationCapabilities.getCapabilities(this.mContext);
        this.mDialogHelper.showDialog(11);
    }

    public void onMagnificationTripleTapWarningDialogPositiveButtonClicked(
            DialogInterface dialogInterface, int i) {
        updateCapabilitiesAndSummary(this.mModeCache);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnSaveInstanceState
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(EXTRA_MODE, this.mModeCache);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setDialogHelper(DialogHelper dialogHelper) {
        this.mDialogHelper = dialogHelper;
        ((ToggleScreenMagnificationPreferenceFragment) dialogHelper).mDialogDelegate = this;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
