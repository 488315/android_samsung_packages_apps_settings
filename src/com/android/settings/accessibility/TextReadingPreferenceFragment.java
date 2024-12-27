package com.android.settings.accessibility;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.animation.AndroidFlingSpline$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.window.flags.Flags;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TextReadingPreferenceFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.accessibility_text_reading_options);
    public int mEntryPoint = 0;
    public FontWeightAdjustmentPreferenceController mFontWeightAdjustmentController;
    boolean mNeedResetSettings;
    public TextReadingPreviewController mPreviewController;
    List<TextReadingResetController.ResetStateListener> mResetStateListeners;

    public DisplaySizeData createDisplaySizeData(Context context) {
        return new DisplaySizeData(context);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(final Context context) {
        int length;
        Bundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey("launched_from")) {
            FragmentActivity activity = getActivity();
            String callingPackage = activity != null ? activity.getCallingPackage() : null;
            this.mEntryPoint =
                    (callingPackage == null || !callingPackage.contains("setupwizard")) ? 0 : 2;
        } else {
            this.mEntryPoint = arguments.getInt("launched_from", 0);
        }
        ArrayList arrayList = new ArrayList();
        FontSizeData fontSizeData = new FontSizeData(context);
        Resources resources = context.getResources();
        ContentResolver contentResolver = context.getContentResolver();
        List asList = Arrays.asList(resources.getStringArray(R.array.entryvalues_font_size));
        float f =
                Flags.configurableFontScaleDefault()
                        ? Settings.System.getFloat(contentResolver, "device_font_scale", 1.0f)
                        : 1.0f;
        fontSizeData.mDefaultValue = Float.valueOf(f);
        float f2 = Settings.System.getFloat(contentResolver, "font_scale", f);
        String[] strArr = (String[]) asList.toArray(new String[0]);
        float parseFloat = Float.parseFloat(strArr[0]);
        int i = 1;
        while (true) {
            if (i >= strArr.length) {
                length = strArr.length - 1;
                break;
            }
            float parseFloat2 = Float.parseFloat(strArr[i]);
            if (f2
                    < AndroidFlingSpline$$ExternalSyntheticOutline0.m(
                            parseFloat2, parseFloat, 0.5f, parseFloat)) {
                length = i - 1;
                break;
            }
            i++;
            parseFloat = parseFloat2;
        }
        fontSizeData.mInitialIndex = length;
        fontSizeData.mValues =
                (List)
                        asList.stream()
                                .map(new FontSizeData$$ExternalSyntheticLambda0())
                                .collect(Collectors.toList());
        DisplaySizeData createDisplaySizeData = createDisplaySizeData(context);
        TextReadingPreviewController textReadingPreviewController =
                new TextReadingPreviewController(
                        context, "preview", fontSizeData, createDisplaySizeData);
        this.mPreviewController = textReadingPreviewController;
        textReadingPreviewController.setEntryPoint(this.mEntryPoint);
        arrayList.add(this.mPreviewController);
        PreviewSizeSeekBarController previewSizeSeekBarController =
                new PreviewSizeSeekBarController(
                        context,
                        "font_size",
                        fontSizeData) { // from class:
                                        // com.android.settings.accessibility.TextReadingPreferenceFragment.1
                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
                        return null;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController
                    public /* bridge */ /* synthetic */ List getBackupKeys() {
                        return super.getBackupKeys();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
                        return null;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
                        return super.getLaunchIntent();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
                        return 0;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ String getStatusText() {
                        return super.getStatusText();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController
                    public ComponentName getTileComponentName() {
                        return AccessibilityShortcutController.FONT_SIZE_COMPONENT_NAME;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController
                    public CharSequence getTileTooltipContent() {
                        return context.getText(
                                R.string.accessibility_font_scaling_auto_added_qs_tooltip_content);
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ ControlValue getValue() {
                        return super.getValue();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
                        return false;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController
                    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
                        super.ignoreUserInteraction();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ boolean isControllable() {
                        return super.isControllable();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
                        return false;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ boolean isSliceable() {
                        return false;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ Controllable$ControllableType
                            needUserInteraction(Object obj) {
                        return super.needUserInteraction(obj);
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
                        return super.runDefaultAction();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ ControlResult setValue(
                            ControlValue controlValue) {
                        return super.setValue(controlValue);
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
                        return false;
                    }
                };
        int size = fontSizeData.mValues.size();
        String[] strArr2 = new String[size];
        for (int i2 = 0; i2 < size; i2++) {
            strArr2[i2] =
                    context.getResources()
                            .getString(
                                    R.string.font_scale_percentage,
                                    Integer.valueOf(
                                            (int)
                                                    (((Float) fontSizeData.mValues.get(i2))
                                                                    .floatValue()
                                                            * 100.0f)));
        }
        previewSizeSeekBarController.setProgressStateLabels(strArr2);
        previewSizeSeekBarController.setInteractionListener(this.mPreviewController);
        getSettingsLifecycle().addObserver(previewSizeSeekBarController);
        arrayList.add(previewSizeSeekBarController);
        PreviewSizeSeekBarController previewSizeSeekBarController2 =
                new PreviewSizeSeekBarController(
                        context,
                        "display_size",
                        createDisplaySizeData) { // from class:
                                                 // com.android.settings.accessibility.TextReadingPreferenceFragment.2
                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
                        return null;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController
                    public /* bridge */ /* synthetic */ List getBackupKeys() {
                        return super.getBackupKeys();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
                        return null;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
                        return super.getLaunchIntent();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
                        return 0;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ String getStatusText() {
                        return super.getStatusText();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController
                    public ComponentName getTileComponentName() {
                        return null;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController
                    public CharSequence getTileTooltipContent() {
                        return null;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ ControlValue getValue() {
                        return super.getValue();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
                        return false;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController
                    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
                        super.ignoreUserInteraction();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ boolean isControllable() {
                        return super.isControllable();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
                        return false;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ boolean isSliceable() {
                        return false;
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ Controllable$ControllableType
                            needUserInteraction(Object obj) {
                        return super.needUserInteraction(obj);
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
                        return super.runDefaultAction();
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.core.BasePreferenceController
                    public /* bridge */ /* synthetic */ ControlResult setValue(
                            ControlValue controlValue) {
                        return super.setValue(controlValue);
                    }

                    @Override // com.android.settings.accessibility.PreviewSizeSeekBarController,
                              // com.android.settings.slices.Sliceable
                    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
                        return false;
                    }
                };
        previewSizeSeekBarController2.setInteractionListener(this.mPreviewController);
        arrayList.add(previewSizeSeekBarController2);
        FontWeightAdjustmentPreferenceController fontWeightAdjustmentPreferenceController =
                new FontWeightAdjustmentPreferenceController(context, "toggle_force_bold_text");
        this.mFontWeightAdjustmentController = fontWeightAdjustmentPreferenceController;
        fontWeightAdjustmentPreferenceController.setEntryPoint(this.mEntryPoint);
        arrayList.add(this.mFontWeightAdjustmentController);
        HighTextContrastPreferenceController highTextContrastPreferenceController =
                new HighTextContrastPreferenceController(
                        context, "toggle_high_text_contrast_preference");
        highTextContrastPreferenceController.setEntryPoint(this.mEntryPoint);
        arrayList.add(highTextContrastPreferenceController);
        TextReadingResetController textReadingResetController =
                new TextReadingResetController(
                        context,
                        UniversalCredentialManager.RESET_APPLET_FORM_FACTOR,
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.accessibility.TextReadingPreferenceFragment$$ExternalSyntheticLambda2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                TextReadingPreferenceFragment textReadingPreferenceFragment =
                                        TextReadingPreferenceFragment.this;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        TextReadingPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER;
                                textReadingPreferenceFragment.showDialog(
                                        EnterpriseContainerCallback
                                                .CONTAINER_PACKAGE_UNINSTALL_FAILURE);
                            }
                        });
        textReadingResetController.setEntryPoint(this.mEntryPoint);
        textReadingResetController.setVisible(!WizardManagerHelper.isAnySetupWizard(getIntent()));
        arrayList.add(textReadingResetController);
        return arrayList;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        if (i == 1009) {
            return 1924;
        }
        return super.getDialogMetricsCategory(i);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "TextReadingPreferenceFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return 1912;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_text_reading_options;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        View peekDecorView = getActivity().getWindow().peekDecorView();
        if (peekDecorView != null) {
            peekDecorView.setAccessibilityPaneTitle(
                    getString(R.string.accessibility_text_reading_options_title));
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        this.mNeedResetSettings = false;
        ArrayList arrayList = new ArrayList();
        getPreferenceControllers()
                .forEach(new AccessibilitySettings$$ExternalSyntheticLambda3(0, arrayList));
        this.mResetStateListeners =
                (List)
                        arrayList.stream()
                                .filter(
                                        new TextReadingPreferenceFragment$$ExternalSyntheticLambda3())
                                .map(new TextReadingPreferenceFragment$$ExternalSyntheticLambda4())
                                .collect(Collectors.toList());
        if (bundle != null) {
            if (bundle.getBoolean("need_reset_settings")) {
                this.mResetStateListeners.forEach(
                        new TextReadingPreferenceFragment$$ExternalSyntheticLambda0());
            }
            if (!bundle.containsKey("last_preview_index")
                    || (i = bundle.getInt("last_preview_index")) == -1) {
                return;
            }
            this.mPreviewController.setCurrentItem(i);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i != 1009) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Unsupported dialogId "));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getPrefContext());
        builder.setTitle(R.string.accessibility_text_reading_confirm_dialog_title);
        builder.setMessage(R.string.accessibility_text_reading_confirm_dialog_message);
        builder.setPositiveButton(
                R.string.accessibility_text_reading_confirm_dialog_reset_button,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.accessibility.TextReadingPreferenceFragment$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        TextReadingPreferenceFragment textReadingPreferenceFragment =
                                TextReadingPreferenceFragment.this;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                TextReadingPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER;
                        textReadingPreferenceFragment.removeDialog(
                                EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE);
                        if (textReadingPreferenceFragment.mFontWeightAdjustmentController
                                .getThreadEnabled()) {
                            textReadingPreferenceFragment.mNeedResetSettings = true;
                            textReadingPreferenceFragment.mFontWeightAdjustmentController
                                    .resetState();
                        } else {
                            textReadingPreferenceFragment.mResetStateListeners.forEach(
                                    new TextReadingPreferenceFragment$$ExternalSyntheticLambda0());
                        }
                        Toast.makeText(
                                        textReadingPreferenceFragment.getPrefContext(),
                                        R.string.accessibility_text_reading_reset_message,
                                        0)
                                .show();
                    }
                });
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder.create();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mNeedResetSettings) {
            bundle.putBoolean("need_reset_settings", true);
        }
        bundle.putInt("last_preview_index", this.mPreviewController.getCurrentItem());
    }
}
