package com.samsung.android.settings.inputmethod;

import android.R;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.inputmethod.InputMethodAndSubtypeUtil;
import com.android.settingslib.inputmethod.InputMethodAndSubtypeUtilCompat;
import com.android.settingslib.inputmethod.InputMethodSettingValuesWrapper;

import com.samsung.android.settings.widget.SecRestrictedSwitchPreferenceScreen;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecInputMethodPreference extends SecRestrictedSwitchPreferenceScreen
        implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog mDialog;
    public final InputMethodInfo mImi;
    public final InputMethodSettingValuesWrapper mInputMethodSettingValues;
    public final boolean mIsAllowedByOrganization;
    public final OnSavePreferenceListener mOnSaveListener;
    public final int mUserId;
    public boolean mWidgetFrameVisible;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnSavePreferenceListener {}

    public SecInputMethodPreference(
            Context context,
            InputMethodInfo inputMethodInfo,
            boolean z,
            OnSavePreferenceListener onSavePreferenceListener,
            int i) {
        this(
                context,
                inputMethodInfo,
                inputMethodInfo.loadLabel(context.getPackageManager()),
                z,
                onSavePreferenceListener,
                i);
    }

    public final boolean isTv() {
        return (getContext().getResources().getConfiguration().uiMode & 15) == 4;
    }

    @Override // com.samsung.android.settings.widget.SecRestrictedSwitchPreferenceScreen,
              // androidx.preference.SecSwitchPreferenceScreen,
              // androidx.preference.SeslSwitchPreferenceScreen,
              // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.switch_widget);
        View findViewById2 =
                preferenceViewHolder.findViewById(com.android.settings.R.id.switch_divider_normal);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.summary);
        int i = 8;
        if (findViewById != null) {
            findViewById.setVisibility(
                    (!this.mWidgetFrameVisible || this.mOnSaveListener == null) ? 8 : 0);
        }
        if (findViewById2 != null) {
            if (this.mWidgetFrameVisible && this.mOnSaveListener != null) {
                i = 0;
            }
            findViewById2.setVisibility(i);
        }
        if (textView != null) {
            textView.setTextDirection(5);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mOnSaveListener == null) {
            return false;
        }
        if (this.mChecked) {
            setCheckedInternal(false);
            return false;
        }
        if (!this.mImi.isSystem()) {
            AlertDialog alertDialog = this.mDialog;
            if (alertDialog != null && alertDialog.isShowing()) {
                this.mDialog.dismiss();
            }
            Context context = getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle(R.string.dialog_alert_title);
            builder.setMessage(
                    context.getString(
                            com.android.settings.R.string.ime_security_warning,
                            this.mImi
                                    .getServiceInfo()
                                    .applicationInfo
                                    .loadLabel(context.getPackageManager())));
            builder.setPositiveButton(
                    R.string.ok, new SecInputMethodPreference$$ExternalSyntheticLambda0(this, 0));
            builder.setNegativeButton(
                    R.string.cancel,
                    new SecInputMethodPreference$$ExternalSyntheticLambda0(this, 1));
            builder.setOnCancelListener(
                    new DialogInterface
                            .OnCancelListener() { // from class:
                                                  // com.samsung.android.settings.inputmethod.SecInputMethodPreference$$ExternalSyntheticLambda2
                        @Override // android.content.DialogInterface.OnCancelListener
                        public final void onCancel(DialogInterface dialogInterface) {
                            SecInputMethodPreference secInputMethodPreference =
                                    SecInputMethodPreference.this;
                            int i = SecInputMethodPreference.$r8$clinit;
                            secInputMethodPreference.setCheckedInternal(false);
                        }
                    });
            AlertDialog create = builder.create();
            this.mDialog = create;
            create.show();
        } else if (this.mImi.getServiceInfo().directBootAware || isTv()) {
            setCheckedInternal(true);
        } else if (!isTv()) {
            showDirectBootWarnDialog();
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        Context context = getContext();
        try {
            Intent intent = getIntent();
            if (intent != null) {
                context.startActivityAsUser(intent, UserHandle.of(this.mUserId));
            }
        } catch (ActivityNotFoundException e) {
            Log.d("SecInputMethodPreference", "IME's Settings Activity Not Found", e);
            Preference$$ExternalSyntheticOutline0.m(
                    context,
                    com.android.settings.R.string.failed_to_open_app_settings_toast,
                    new Object[] {this.mImi.loadLabel(context.getPackageManager())},
                    context,
                    1);
        }
        return true;
    }

    public final void setCheckedInternal(boolean z) {
        setChecked(z);
        VirtualKeyboardFragment virtualKeyboardFragment =
                (VirtualKeyboardFragment) this.mOnSaveListener;
        InputMethodAndSubtypeUtilCompat.saveInputMethodSubtypeListForUserInternal(
                virtualKeyboardFragment,
                virtualKeyboardFragment.mUserAwareContext.getContentResolver(),
                ((InputMethodManager)
                                virtualKeyboardFragment
                                        .getContext()
                                        .getSystemService(InputMethodManager.class))
                        .getInputMethodListAsUser(virtualKeyboardFragment.mUserId),
                virtualKeyboardFragment.getResources().getConfiguration().keyboard == 2,
                virtualKeyboardFragment.mUserId);
        virtualKeyboardFragment.mInputMethodSettingValues.refreshAllInputMethodAndSubtypes();
        Iterator it = virtualKeyboardFragment.mInputMethodPreferenceList.iterator();
        while (it.hasNext()) {
            ((SecInputMethodPreference) it.next()).updatePreferenceViews();
        }
        notifyChanged();
    }

    public final void showDirectBootWarnDialog() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setMessage(
                context.getText(com.android.settings.R.string.direct_boot_unaware_dialog_message));
        builder.setPositiveButton(
                R.string.ok, new SecInputMethodPreference$$ExternalSyntheticLambda0(this, 2));
        builder.setNegativeButton(
                R.string.cancel, new SecInputMethodPreference$$ExternalSyntheticLambda0(this, 3));
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.show();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePreferenceViews() {
        /*
            r5 = this;
            com.android.settingslib.inputmethod.InputMethodSettingValuesWrapper r0 = r5.mInputMethodSettingValues
            android.view.inputmethod.InputMethodInfo r1 = r5.mImi
            boolean r0 = r0.isAlwaysCheckedIme(r1)
            r1 = 1
            if (r0 != 0) goto L6d
            android.view.inputmethod.InputMethodInfo r0 = r5.mImi
            android.content.Context r2 = r5.getContext()
            android.content.pm.PackageManager r2 = r2.getPackageManager()
            boolean r3 = r0.isSystem()
            if (r3 == 0) goto L3a
            boolean r3 = r0.isAuxiliaryIme()
            if (r3 != 0) goto L3a
            java.lang.String r3 = r0.getPackageName()
            java.lang.String r4 = "com.samsung.android.accessibility.talkback"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L3a
            java.lang.String r3 = "android"
            java.lang.String r0 = r0.getPackageName()
            int r0 = r2.checkSignatures(r3, r0)
            if (r0 != 0) goto L3a
            goto L6d
        L3a:
            boolean r0 = r5.mIsAllowedByOrganization
            if (r0 != 0) goto L63
            android.content.Context r0 = r5.getContext()
            android.view.inputmethod.InputMethodInfo r2 = r5.mImi
            java.lang.String r2 = r2.getPackageName()
            int r3 = r5.mUserId
            com.android.settingslib.RestrictedLockUtils$EnforcedAdmin r0 = com.android.settingslib.RestrictedLockUtilsInternal.checkIfInputMethodDisallowed(r0, r3, r2)
            com.android.settingslib.RestrictedPreferenceHelper r2 = r5.mHelper
            boolean r0 = r2.setDisabledByAdmin(r0)
            if (r0 == 0) goto L59
            r5.notifyChanged()
        L59:
            boolean r0 = r5.mWidgetFrameVisible
            if (r0 == r1) goto L83
            r5.mWidgetFrameVisible = r1
            r5.notifyChanged()
            goto L83
        L63:
            boolean r0 = r5.mWidgetFrameVisible
            if (r0 == r1) goto L83
            r5.mWidgetFrameVisible = r1
            r5.notifyChanged()
            goto L83
        L6d:
            com.android.settingslib.RestrictedPreferenceHelper r0 = r5.mHelper
            r2 = 0
            boolean r0 = r0.setDisabledByAdmin(r2)
            if (r0 == 0) goto L79
            r5.notifyChanged()
        L79:
            boolean r0 = r5.mWidgetFrameVisible
            if (r0 == 0) goto L83
            r0 = 0
            r5.mWidgetFrameVisible = r0
            r5.notifyChanged()
        L83:
            com.android.settingslib.inputmethod.InputMethodSettingValuesWrapper r0 = r5.mInputMethodSettingValues
            android.view.inputmethod.InputMethodInfo r2 = r5.mImi
            boolean r0 = r0.isEnabledImi(r2)
            r5.setChecked(r0)
            com.android.settingslib.RestrictedPreferenceHelper r0 = r5.mHelper
            boolean r0 = r0.mDisabledByAdmin
            if (r0 != 0) goto Lb3
            android.content.Context r0 = r5.getContext()
            java.lang.String r2 = "input_method"
            java.lang.Object r0 = r0.getSystemService(r2)
            android.view.inputmethod.InputMethodManager r0 = (android.view.inputmethod.InputMethodManager) r0
            android.view.inputmethod.InputMethodInfo r2 = r5.mImi
            java.util.List r0 = r0.getEnabledInputMethodSubtypeList(r2, r1)
            android.content.Context r1 = r5.getContext()
            android.view.inputmethod.InputMethodInfo r2 = r5.mImi
            java.lang.String r0 = com.android.settingslib.inputmethod.InputMethodAndSubtypeUtil.getSubtypeLocaleNameListAsSentence(r0, r1, r2)
            r5.setSummary(r0)
        Lb3:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.inputmethod.SecInputMethodPreference.updatePreferenceViews():void");
    }

    @VisibleForTesting
    public SecInputMethodPreference(
            Context context,
            InputMethodInfo inputMethodInfo,
            CharSequence charSequence,
            boolean z,
            OnSavePreferenceListener onSavePreferenceListener,
            int i) {
        super(context);
        this.mDialog = null;
        this.mWidgetFrameVisible = true;
        setPersistent();
        this.mImi = inputMethodInfo;
        this.mIsAllowedByOrganization = z;
        this.mOnSaveListener = onSavePreferenceListener;
        if (onSavePreferenceListener == null) {
            setWidgetLayoutResource(0);
        }
        setKey(inputMethodInfo.getId());
        setTitle(charSequence);
        String settingsActivity = inputMethodInfo.getSettingsActivity();
        if (TextUtils.isEmpty(settingsActivity)) {
            setIntent(null);
        } else {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClassName(inputMethodInfo.getPackageName(), settingsActivity);
            intent.putExtra("from_settings", true);
            setIntent(intent);
        }
        if (i != UserHandle.myUserId()) {
            getContext().createContextAsUser(UserHandle.of(i), 0);
        }
        this.mInputMethodSettingValues =
                new InputMethodSettingValuesWrapper(getContext().getApplicationContext());
        this.mUserId = i;
        if (inputMethodInfo.isSystem()) {
            InputMethodAndSubtypeUtil.isValidNonAuxAsciiCapableIme(inputMethodInfo);
        }
        setOnPreferenceClickListener(this);
        setOnPreferenceChangeListener(this);
    }
}
