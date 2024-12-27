package com.samsung.android.settings.biometrics.fingerprint;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.TypedArrayUtils;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.R$styleable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SettingsBaseActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.security.SecurityUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintSettingsMultiSelect extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public ActionBar mActionBar;
    public FingerprintManager mFingerprintManager;
    public RelativeLayout mRemoveButtonLayout;
    public int mSelectedId;
    public int mUserId;
    public ArrayList mSelectionChecklist = null;
    public List mItems = null;
    public LockPatternUtils mLockPatternUtils = null;
    public CheckBox mSelectAllCheckbox = null;
    public TextView mSelectedFingerprintTextView = null;
    public RelativeLayout mCheckBoxRelativeLayout = null;
    public FingerprintDeleteDialog mDeleteAllDialog = null;
    public FingerprintDeleteDialog mDeleteDialog = null;
    public ArrayList mSelectedIdArray = null;
    public int mSelectedCount = 0;
    public int mEnrolledFingerCount = 0;
    public int mOriginalContentStart = -1;
    public boolean mIsSelectedIDSaved = false;
    public final AnonymousClass1 mHandler =
            new Handler() { // from class:
                            // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsMultiSelect.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    Log.secD(
                            "FpstFingerprintSettingsMultiSelect",
                            "Handler received: "
                                    .concat(
                                            FingerprintSettingsUtils.convertEvtToString(
                                                    message.what)));
                    int i = message.what;
                    if (i != 1000) {
                        Log.secD(
                                "FpstFingerprintSettingsMultiSelect",
                                "handleMessage: "
                                        .concat(FingerprintSettingsUtils.convertEvtToString(i)));
                        return;
                    }
                    FingerprintSettingsMultiSelect fingerprintSettingsMultiSelect =
                            FingerprintSettingsMultiSelect.this;
                    Iterator it = fingerprintSettingsMultiSelect.mSelectionChecklist.iterator();
                    while (it.hasNext()) {
                        ((CheckBoxPreference) it.next()).setChecked(false);
                    }
                    fingerprintSettingsMultiSelect.mSelectionChecklist.clear();
                    fingerprintSettingsMultiSelect.getActivity().onBackPressed();
                }
            };
    public final FingerprintSettingsMultiSelect$$ExternalSyntheticLambda3 checkBoxRunnable =
            new Runnable() { // from class:
                             // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsMultiSelect$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    FingerprintSettingsMultiSelect fingerprintSettingsMultiSelect =
                            FingerprintSettingsMultiSelect.this;
                    ArrayList arrayList = fingerprintSettingsMultiSelect.mSelectedIdArray;
                    if (arrayList != null) {
                        if (fingerprintSettingsMultiSelect.mEnrolledFingerCount
                                == arrayList.size()) {
                            fingerprintSettingsMultiSelect.checkAllToggle(true);
                        } else {
                            for (int i = 0;
                                    i < fingerprintSettingsMultiSelect.mSelectedIdArray.size();
                                    i++) {
                                ((CheckBoxPreference)
                                                fingerprintSettingsMultiSelect.mSelectionChecklist
                                                        .get(
                                                                ((Integer)
                                                                                fingerprintSettingsMultiSelect
                                                                                        .mSelectedIdArray
                                                                                        .get(i))
                                                                        .intValue()))
                                        .setChecked(true);
                                fingerprintSettingsMultiSelect.updateActionbarState$1();
                            }
                        }
                        fingerprintSettingsMultiSelect.mSelectedIdArray = null;
                    }
                }
            };
    public final AnonymousClass3 mRemoveCallback =
            new FingerprintManager
                    .RemovalCallback() { // from class:
                                         // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsMultiSelect.3
                public final void onRemovalError(
                        Fingerprint fingerprint, int i, CharSequence charSequence) {
                    FragmentActivity activity = FingerprintSettingsMultiSelect.this.getActivity();
                    Log.secE(
                            "FpstFingerprintSettingsMultiSelect",
                            "Remove Error : "
                                    + i
                                    + ", "
                                    + ((Object) charSequence)
                                    + ", activity="
                                    + activity);
                    if (activity != null) {
                        String string =
                                activity.getString(
                                        R.string.sec_fingerprint_error_message_sensor_error);
                        boolean z = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
                        FingerprintSettingsUtils.showSensorErrorDialog(
                                activity,
                                activity.getString(R.string.sec_fingerprint_attention),
                                string,
                                false);
                    }
                }

                public final void onRemovalSucceeded(Fingerprint fingerprint, int i) {
                    Log.secD(
                            "FpstFingerprintSettingsMultiSelect",
                            "mRemoveCallback onRemovalSucceeded called: "
                                    + fingerprint.getBiometricId()
                                    + " ,remaining : "
                                    + i);
                    Context context = FingerprintSettingsMultiSelect.this.getContext();
                    RecyclerView listView = FingerprintSettingsMultiSelect.this.getListView();
                    if (context != null && listView != null) {
                        listView.announceForAccessibility(
                                context.getString(R.string.sec_biometrics_common_removed));
                    }
                    FingerprintSettingsMultiSelect fingerprintSettingsMultiSelect =
                            FingerprintSettingsMultiSelect.this;
                    int i2 = 0;
                    int i3 = -1;
                    for (int i4 = 0;
                            i4 < fingerprintSettingsMultiSelect.mSelectionChecklist.size();
                            i4++) {
                        if (((CheckBoxPreference)
                                        fingerprintSettingsMultiSelect.mSelectionChecklist.get(i4))
                                .mChecked) {
                            if (((Fingerprint) fingerprintSettingsMultiSelect.mItems.get(i4))
                                            .getBiometricId()
                                    == fingerprint.getBiometricId()) {
                                i3 = i4;
                            } else {
                                i2++;
                                Log.secD(
                                        "FpstFingerprintSettingsMultiSelect",
                                        "checkFinishDeleteOperation remainingNum index: "
                                                + i4
                                                + ", remainingNum: "
                                                + i2);
                            }
                        }
                    }
                    if (i3 != -1) {
                        fingerprintSettingsMultiSelect.mItems.remove(i3);
                        fingerprintSettingsMultiSelect.mSelectionChecklist.remove(i3);
                    }
                    if (i2 == 0) {
                        obtainMessage(1000, fingerprint.getBiometricId(), 0).sendToTarget();
                    } else {
                        FingerprintSettingsMultiSelect.this.deleteFingerprintSequentially();
                    }
                }
            };

    public final void checkAllToggle(boolean z) {
        Iterator it = this.mSelectionChecklist.iterator();
        while (it.hasNext()) {
            ((CheckBoxPreference) it.next()).setChecked(z);
        }
        updateActionbarState$1();
    }

    public final void createActionbarLayout$1() {
        this.mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        View inflate =
                LayoutInflater.from(getActivity())
                        .inflate(
                                R.layout.sec_fingerprint_selectionutils_actionbar_tablet,
                                (ViewGroup) null);
        ActionBar actionBar = this.mActionBar;
        if (actionBar == null) {
            Log.secE("FpstFingerprintSettingsMultiSelect", "updateSelectMenu null!!");
            return;
        }
        actionBar.setDisplayOptions(16, 16);
        this.mActionBar.setCustomView(inflate, new ActionBar.LayoutParams(16));
        this.mActionBar.setDisplayHomeAsUpEnabled(false);
        this.mActionBar.setHomeButtonEnabled();
        TextView textView = (TextView) inflate.findViewById(R.id.number_selected_fingerprint_all);
        textView.setSelected(true);
        final int i = 0;
        textView.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsMultiSelect$$ExternalSyntheticLambda1
                    public final /* synthetic */ FingerprintSettingsMultiSelect f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i2 = i;
                        FingerprintSettingsMultiSelect fingerprintSettingsMultiSelect = this.f$0;
                        switch (i2) {
                            case 0:
                                fingerprintSettingsMultiSelect.mSelectAllCheckbox.setChecked(
                                        !r2.isChecked());
                                fingerprintSettingsMultiSelect.checkAllToggle(
                                        fingerprintSettingsMultiSelect.mSelectAllCheckbox
                                                .isChecked());
                                break;
                            default:
                                fingerprintSettingsMultiSelect.checkAllToggle(
                                        fingerprintSettingsMultiSelect.mSelectAllCheckbox
                                                .isChecked());
                                break;
                        }
                    }
                });
        CheckBox checkBox =
                (CheckBox) inflate.findViewById(R.id.toggle_selection_fingerprint_check);
        this.mSelectAllCheckbox = checkBox;
        final int i2 = 1;
        checkBox.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsMultiSelect$$ExternalSyntheticLambda1
                    public final /* synthetic */ FingerprintSettingsMultiSelect f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i22 = i2;
                        FingerprintSettingsMultiSelect fingerprintSettingsMultiSelect = this.f$0;
                        switch (i22) {
                            case 0:
                                fingerprintSettingsMultiSelect.mSelectAllCheckbox.setChecked(
                                        !r2.isChecked());
                                fingerprintSettingsMultiSelect.checkAllToggle(
                                        fingerprintSettingsMultiSelect.mSelectAllCheckbox
                                                .isChecked());
                                break;
                            default:
                                fingerprintSettingsMultiSelect.checkAllToggle(
                                        fingerprintSettingsMultiSelect.mSelectAllCheckbox
                                                .isChecked());
                                break;
                        }
                    }
                });
        this.mSelectedFingerprintTextView =
                (TextView) inflate.findViewById(R.id.number_selected_fingerprint_text);
        this.mCheckBoxRelativeLayout =
                (RelativeLayout) inflate.findViewById(R.id.fingerprint_select_all_wrapper);
        Toolbar toolbar = (Toolbar) inflate.getParent();
        if (toolbar != null) {
            if (Utils.isTablet()) {
                toolbar.setContentInsetsAbsolute(
                        getResources()
                                .getDimensionPixelSize(
                                        R.dimen.sec_fingerprint_actionbar_insets_marginleft),
                        0);
            } else {
                toolbar.setContentInsetsAbsolute(0, 0);
            }
        }
        if (getActivity().getResources().getConfiguration().orientation == 2) {
            if (this.mCheckBoxRelativeLayout != null && SecurityUtils.isWinnerProduct()) {
                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) this.mCheckBoxRelativeLayout.getLayoutParams();
                layoutParams.setMargins(
                        getResources()
                                .getDimensionPixelSize(
                                        R.dimen
                                                .sec_fingerprint_multi_selection_actionbar_title_paddingStart_land),
                        layoutParams.topMargin,
                        layoutParams.rightMargin,
                        layoutParams.bottomMargin);
                this.mCheckBoxRelativeLayout.setLayoutParams(layoutParams);
            }
            RelativeLayout.LayoutParams layoutParams2 =
                    (RelativeLayout.LayoutParams) this.mSelectAllCheckbox.getLayoutParams();
            layoutParams2.setMargins(
                    layoutParams2.leftMargin,
                    getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_fingerprint_actionbar_all_checkbox_margintop_port),
                    layoutParams2.rightMargin,
                    layoutParams2.bottomMargin);
            this.mSelectAllCheckbox.setLayoutParams(layoutParams2);
        }
    }

    public final void deleteFingerprintSequentially() {
        if (this.mSelectionChecklist.size() < 1 || getNumOfCheckedList() < 1) {
            return;
        }
        for (int i = 0; i < this.mSelectionChecklist.size(); i++) {
            if (((CheckBoxPreference) this.mSelectionChecklist.get(i)).mChecked) {
                Fingerprint fingerprint = (Fingerprint) this.mItems.get(i);
                StringBuilder m =
                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                i, "deleteFingerprintSequencially delete index: ", ", id: ");
                m.append(fingerprint.getBiometricId());
                Log.secD("FpstFingerprintSettingsMultiSelect", m.toString());
                Log.secD(
                        "FpstFingerprintSettingsMultiSelect",
                        "deleteFingerPrint : "
                                + fingerprint.getBiometricId()
                                + ", "
                                + ((Object) fingerprint.getName())
                                + ", "
                                + fingerprint.getGroupId());
                this.mFingerprintManager.remove(fingerprint, this.mUserId, this.mRemoveCallback);
                return;
            }
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8217;
    }

    public final int getNumOfCheckedList() {
        ArrayList arrayList = this.mSelectionChecklist;
        int i = 0;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((CheckBoxPreference) it.next()).mChecked) {
                    i++;
                }
            }
        }
        return i;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final boolean isHomeButtonEnabled(Context context) {
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        CheckBox checkBox;
        super.onConfigurationChanged(configuration);
        Log.secD("FpstFingerprintSettingsMultiSelect", "=====onConfigurationChanged");
        if (BiometricsGenericHelper.isBlockedInMultiWindowMode(getActivity(), configuration)) {
            finish();
            return;
        }
        createActionbarLayout$1();
        updateActionbarState$1();
        int i = configuration.orientation;
        if (i != 2) {
            if (i != 1 || (checkBox = this.mSelectAllCheckbox) == null) {
                return;
            }
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) checkBox.getLayoutParams();
            layoutParams.setMargins(
                    layoutParams.leftMargin,
                    getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_fingerprint_actionbar_all_checkbox_margintop_port),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
            this.mSelectAllCheckbox.setLayoutParams(layoutParams);
            return;
        }
        if (this.mCheckBoxRelativeLayout != null && SecurityUtils.isWinnerProduct()) {
            LinearLayout.LayoutParams layoutParams2 =
                    (LinearLayout.LayoutParams) this.mCheckBoxRelativeLayout.getLayoutParams();
            layoutParams2.setMargins(
                    getResources()
                            .getDimensionPixelSize(
                                    R.dimen
                                            .sec_fingerprint_multi_selection_actionbar_title_paddingStart_land),
                    layoutParams2.topMargin,
                    layoutParams2.rightMargin,
                    layoutParams2.bottomMargin);
            this.mCheckBoxRelativeLayout.setLayoutParams(layoutParams2);
        }
        CheckBox checkBox2 = this.mSelectAllCheckbox;
        if (checkBox2 != null) {
            RelativeLayout.LayoutParams layoutParams3 =
                    (RelativeLayout.LayoutParams) checkBox2.getLayoutParams();
            layoutParams3.setMargins(
                    layoutParams3.leftMargin,
                    getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_fingerprint_actionbar_all_checkbox_margintop_port),
                    layoutParams3.rightMargin,
                    layoutParams3.bottomMargin);
            this.mSelectAllCheckbox.setLayoutParams(layoutParams3);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        Log.secD("FpstFingerprintSettingsMultiSelect", "onCreate");
        if (SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                getActivity(),
                R.string.bio_fingerprint_sanner_title,
                "FpstFingerprintSettingsMultiSelect")) {
            finish();
            return;
        }
        Bundle arguments = getArguments();
        this.mSelectedIdArray = new ArrayList();
        if (bundle != null) {
            this.mSelectedIdArray = bundle.getIntegerArrayList("key_select_fingerprint");
            this.mIsSelectedIDSaved = true;
        }
        if (arguments != null) {
            this.mUserId = arguments.getInt("android.intent.extra.USER_ID", UserHandle.myUserId());
            if (arguments.containsKey("selected_id")) {
                this.mSelectedId = arguments.getInt("selected_id");
                Log.secD(
                        "FpstFingerprintSettingsMultiSelect",
                        "onCreate selectedIndex: " + this.mSelectedId);
            }
        }
        this.mFingerprintManager =
                (FingerprintManager)
                        ((AppCompatActivity) getActivity()).getSystemService("fingerprint");
        this.mLockPatternUtils = new LockPatternUtils(getActivity());
        this.mSelectionChecklist = new ArrayList();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.removeAll();
        }
        addPreferencesFromResource(R.xml.sec_fingerprint_select_list);
        if (this.mItems != null) {
            this.mItems = null;
        }
        List enrolledFingerprints = this.mFingerprintManager.getEnrolledFingerprints(this.mUserId);
        this.mItems = enrolledFingerprints;
        if (enrolledFingerprints != null) {
            this.mEnrolledFingerCount = enrolledFingerprints.size();
            int i2 = 0;
            while (true) {
                i = this.mEnrolledFingerCount;
                if (i2 >= i) {
                    break;
                }
                for (int i3 = i2; i3 > 0; i3--) {
                    int i4 = i3 - 1;
                    if (((Fingerprint) this.mItems.get(i4)).getBiometricId()
                            > ((Fingerprint) this.mItems.get(i3)).getBiometricId()) {
                        List list = this.mItems;
                        list.add(i4, (Fingerprint) list.get(i3));
                        this.mItems.remove(i3 + 1);
                    }
                }
                i2++;
            }
            if (i != this.mItems.size()) {
                Log.secE(
                        "FpstFingerprintSettingsMultiSelect",
                        "addFingerprintItemPreferences : Sort Error");
                this.mItems = null;
                List enrolledFingerprints2 =
                        this.mFingerprintManager.getEnrolledFingerprints(this.mUserId);
                this.mItems = enrolledFingerprints2;
                if (enrolledFingerprints2 != null) {
                    this.mEnrolledFingerCount = enrolledFingerprints2.size();
                }
            }
            Log.secD(
                    "FpstFingerprintSettingsMultiSelect",
                    "addFingerprintItemPreferences : mEnrolledFingerCount["
                            + this.mEnrolledFingerCount
                            + "]");
            if (this.mItems != null) {
                for (int i5 = 0; i5 < this.mEnrolledFingerCount; i5++) {
                    Fingerprint fingerprint = (Fingerprint) this.mItems.get(i5);
                    Log.secD(
                            "FpstFingerprintSettingsMultiSelect",
                            "addFingerprintItemPreferences : "
                                    + fingerprint.getBiometricId()
                                    + ", "
                                    + ((Object) fingerprint.getName())
                                    + ", "
                                    + fingerprint.getGroupId());
                }
                for (int i6 = 0; i6 < this.mEnrolledFingerCount; i6++) {
                    Fingerprint fingerprint2 = (Fingerprint) this.mItems.get(i6);
                    CheckBoxPreference checkBoxPreference =
                            (CheckBoxPreference)
                                    getPreferenceManager().findPreference("Finger " + i6);
                    if (checkBoxPreference != null) {
                        checkBoxPreference.setKey(
                                "key_fingerprint_item_" + fingerprint2.getBiometricId());
                        checkBoxPreference.setTitle(fingerprint2.getName());
                        checkBoxPreference.setPersistent();
                        checkBoxPreference.setOnPreferenceChangeListener(this);
                        this.mSelectionChecklist.add(checkBoxPreference);
                    }
                    if (!this.mIsSelectedIDSaved
                            && this.mSelectedId
                                    == ((Fingerprint) this.mItems.get(i6)).getBiometricId()) {
                        this.mSelectedIdArray.add(Integer.valueOf(i6));
                    }
                }
            } else {
                this.mEnrolledFingerCount = 0;
                Log.secE(
                        "FpstFingerprintSettingsMultiSelect",
                        "addFingerprintItemPreferences :"
                            + " mFingerprintManager.getEnrolledFingerprints() return null after"
                            + " sorting");
            }
        } else {
            this.mEnrolledFingerCount = 0;
            Log.secE(
                    "FpstFingerprintSettingsMultiSelect",
                    "addFingerprintItemPreferences : mFingerprintManager.getEnrolledFingerprints()"
                        + " return null before sorting");
        }
        for (int i7 = this.mEnrolledFingerCount; i7 < 4; i7++) {
            CheckBoxPreference checkBoxPreference2 =
                    (CheckBoxPreference) getPreferenceManager().findPreference("Finger " + i7);
            if (checkBoxPreference2 != null) {
                Log.secD(
                        "FpstFingerprintSettingsMultiSelect",
                        "updateFingerList [Select] : Remove CheckBoxPreference, key = "
                                + checkBoxPreference2.getKey());
                getPreferenceScreen().removePreference(checkBoxPreference2);
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Log.secD("FpstFingerprintSettingsMultiSelect", "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu, menuInflater);
        createActionbarLayout$1();
        menu.add(0, 2, 0, R.string.sec_fingerprint_multi_select_cancel)
                .setVisible(true)
                .setShowAsAction(2);
        ArrayList arrayList = this.mSelectedIdArray;
        if (arrayList == null || arrayList.size() == 0) {
            updateActionbarState$1();
        } else {
            postDelayed(this.checkBoxRunnable, 100L);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        Toolbar toolbar =
                (Toolbar)
                        getActivity()
                                .getWindow()
                                .getDecorView()
                                .findViewById(android.R.id.action_bar_container);
        if (toolbar != null) {
            int i = this.mOriginalContentStart;
            if (i != -1) {
                int contentInsetEnd = toolbar.getContentInsetEnd();
                toolbar.ensureContentInsets();
                toolbar.mContentInsets.setRelative(i, contentInsetEnd);
            }
        } else {
            Log.secE("FpstFingerprintSettingsMultiSelect", "setContentsStart mToolbar Null");
        }
        removeCallbacks(this.checkBoxRunnable);
        Log.secD("FpstFingerprintSettingsMultiSelect", "onDestroy");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 2) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        AlertDialog alertDialog;
        AlertDialog alertDialog2;
        super.onPause();
        Log.secD("FpstFingerprintSettingsMultiSelect", "onPause");
        if (getActivity().isChangingConfigurations()) {
            return;
        }
        Log.d("FpstFingerprintSettingsMultiSelect", "Cancel session");
        Intent intent = new Intent();
        intent.putExtra("reason", "cancelsession");
        setResult(0, intent);
        FingerprintDeleteDialog fingerprintDeleteDialog = this.mDeleteAllDialog;
        if (fingerprintDeleteDialog != null
                && (alertDialog2 = fingerprintDeleteDialog.mAlertDialog) != null) {
            alertDialog2.cancel();
        }
        FingerprintDeleteDialog fingerprintDeleteDialog2 = this.mDeleteDialog;
        if (fingerprintDeleteDialog2 != null
                && (alertDialog = fingerprintDeleteDialog2.mAlertDialog) != null) {
            alertDialog.cancel();
        }
        if (isFinishingOrDestroyed()) {
            return;
        }
        finish();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!(preference instanceof CheckBoxPreference)) {
            return super.onPreferenceTreeClick(preference);
        }
        updateActionbarState$1();
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.secD("FpstFingerprintSettingsMultiSelect", "onResume");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < this.mSelectionChecklist.size(); i++) {
            if (((CheckBoxPreference) this.mSelectionChecklist.get(i)).mChecked) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        bundle.putIntegerArrayList("key_select_fingerprint", arrayList);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Log.secD("FpstFingerprintSettingsMultiSelect", "onViewCreated");
        FragmentActivity activity = getActivity();
        this.mRemoveButtonLayout = (RelativeLayout) activity.findViewById(R.id.button_bar);
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView)
                        ((LayoutInflater) activity.getSystemService("layout_inflater"))
                                .inflate(
                                        R.layout.sec_biometrics_bottom_navigation_remove_layout,
                                        (ViewGroup) this.mRemoveButtonLayout,
                                        false);
        bottomNavigationView.selectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() { // from class:
                    // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsMultiSelect$$ExternalSyntheticLambda0
                    @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                    public final boolean onNavigationItemSelected(MenuItem menuItem) {
                        String string;
                        String string2;
                        final FingerprintSettingsMultiSelect fingerprintSettingsMultiSelect =
                                FingerprintSettingsMultiSelect.this;
                        BiometricsGenericHelper.insertSaLog(
                                fingerprintSettingsMultiSelect.getContext(), 8217, 8228);
                        if (fingerprintSettingsMultiSelect.mDeleteAllDialog == null
                                && fingerprintSettingsMultiSelect.mDeleteDialog == null) {
                            int numOfCheckedList =
                                    fingerprintSettingsMultiSelect.getNumOfCheckedList();
                            fingerprintSettingsMultiSelect.mSelectedCount = numOfCheckedList;
                            int i = -1;
                            String str = null;
                            if (numOfCheckedList
                                    == fingerprintSettingsMultiSelect.mSelectionChecklist.size()) {
                                fingerprintSettingsMultiSelect.getContext();
                                boolean fingerprintUnlockEnabled =
                                        FingerprintSettingsUtils.getFingerprintUnlockEnabled(
                                                fingerprintSettingsMultiSelect.mLockPatternUtils,
                                                fingerprintSettingsMultiSelect.mUserId);
                                int i2 = fingerprintSettingsMultiSelect.mUserId;
                                if (i2 != 0
                                        && SemPersonaManager.isKnoxId(i2)
                                        && KnoxUtils.isMultifactorAuthEnforced(
                                                fingerprintSettingsMultiSelect.getActivity(),
                                                fingerprintSettingsMultiSelect.mUserId)
                                        && fingerprintUnlockEnabled) {
                                    if (fingerprintSettingsMultiSelect.mSelectedCount == 1) {
                                        string =
                                                fingerprintSettingsMultiSelect.getString(
                                                        R.string
                                                                .knox_fp_cant_delete_popup_message_body);
                                        string2 =
                                                fingerprintSettingsMultiSelect.getString(
                                                        R.string
                                                                .knox_fp_cant_delete_popup_message_title);
                                    } else {
                                        string =
                                                fingerprintSettingsMultiSelect.getString(
                                                        R.string
                                                                .knox_fp_cant_delete_all_popup_message_body);
                                        string2 =
                                                fingerprintSettingsMultiSelect.getString(
                                                        R.string
                                                                .knox_fp_cant_delete_all_popup_message_title);
                                    }
                                    new AlertDialog.Builder(
                                                    fingerprintSettingsMultiSelect.getActivity())
                                            .setTitle(string2)
                                            .setMessage(string)
                                            .setPositiveButton(
                                                    android.R.string.ok,
                                                    (DialogInterface.OnClickListener) null)
                                            .create()
                                            .show();
                                } else {
                                    int numOfCheckedList2 =
                                            fingerprintSettingsMultiSelect.getNumOfCheckedList();
                                    Log.secD(
                                            "FpstFingerprintSettingsMultiSelect",
                                            "deleteAllFingerPrint : selectedCount "
                                                    + numOfCheckedList2);
                                    String string3 =
                                            fingerprintSettingsMultiSelect.getString(
                                                    R.string
                                                            .sec_fingerprint_delete_n_fingerprints_title,
                                                    Integer.valueOf(numOfCheckedList2));
                                    fingerprintSettingsMultiSelect.getContext();
                                    final boolean fingerprintUnlockEnabled2 =
                                            FingerprintSettingsUtils.getFingerprintUnlockEnabled(
                                                    fingerprintSettingsMultiSelect
                                                            .mLockPatternUtils,
                                                    fingerprintSettingsMultiSelect.mUserId);
                                    final boolean z =
                                            Settings.System.getInt(
                                                                    fingerprintSettingsMultiSelect
                                                                            .getActivity()
                                                                            .getContentResolver(),
                                                                    "fingerprint_secret_box",
                                                                    0)
                                                            == 1
                                                    || (!SecurityUtils.isEnabledSamsungPass(
                                                                    fingerprintSettingsMultiSelect
                                                                            .getActivity())
                                                            && Settings.Secure.getInt(
                                                                            fingerprintSettingsMultiSelect
                                                                                    .getActivity()
                                                                                    .getContentResolver(),
                                                                            "fingerprint_webpass",
                                                                            0)
                                                                    == 1)
                                                    || Settings.Secure.getInt(
                                                                    fingerprintSettingsMultiSelect
                                                                            .getActivity()
                                                                            .getContentResolver(),
                                                                    "fingerprint_samsungaccount_confirmed",
                                                                    0)
                                                            == 1;
                                    if (KnoxUtils.isKnoxOrganizationOwnedDevice(
                                                    fingerprintSettingsMultiSelect.getActivity(),
                                                    fingerprintSettingsMultiSelect.mUserId)
                                            && KnoxUtils.isMultifactorAuthEnforced(
                                                    fingerprintSettingsMultiSelect.getActivity(),
                                                    fingerprintSettingsMultiSelect.mUserId)
                                            && fingerprintUnlockEnabled2) {
                                        ArrayList arrayList =
                                                fingerprintSettingsMultiSelect.mSelectionChecklist;
                                        if (arrayList != null
                                                && numOfCheckedList2 == arrayList.size()) {
                                            string3 =
                                                    numOfCheckedList2 == 1
                                                            ? fingerprintSettingsMultiSelect
                                                                    .getString(
                                                                            R.string
                                                                                    .knox_fp_delete_1_with_warning_popup_message_body)
                                                            : fingerprintSettingsMultiSelect
                                                                    .getString(
                                                                            R.string
                                                                                    .knox_fp_delete_all_with_warning_popup_message_body);
                                            str =
                                                    fingerprintSettingsMultiSelect.getString(
                                                            R.string
                                                                    .knox_fp_delete_with_warning_popup_message_title);
                                            i = R.string.knox_fp_cant_delete_popup_positive_button;
                                        }
                                    } else if (Rune.isChinaModel()) {
                                        string3 =
                                                fingerprintSettingsMultiSelect.getString(
                                                        R.string
                                                                .sec_fingerprint_delete_case3_fingerprints_msg_chn);
                                    } else if (fingerprintUnlockEnabled2 || z) {
                                        str =
                                                numOfCheckedList2 == 1
                                                        ? fingerprintSettingsMultiSelect.getString(
                                                                R.string
                                                                        .sec_fingerprint_delete_1_fingerprint_title_feature)
                                                        : fingerprintSettingsMultiSelect.getString(
                                                                R.string
                                                                        .sec_fingerprint_delete_all_fingerprints_title);
                                        string3 =
                                                fingerprintSettingsMultiSelect.getString(
                                                        R.string
                                                                .sec_fingerprint_delete_case3_fingerprints_msg);
                                    } else {
                                        string3 =
                                                numOfCheckedList2 == 1
                                                        ? fingerprintSettingsMultiSelect.getString(
                                                                R.string
                                                                        .sec_fingerprint_delete_1_fingerprint_title)
                                                        : String.format(
                                                                fingerprintSettingsMultiSelect
                                                                        .getString(
                                                                                R.string
                                                                                        .sec_fingerprint_delete_n_fingerprints_title,
                                                                                Integer.valueOf(
                                                                                        numOfCheckedList2)),
                                                                new Object[0]);
                                    }
                                    BiometricsGenericHelper.insertSaLog(
                                            fingerprintSettingsMultiSelect.getContext(), 8229);
                                    FingerprintDeleteDialog.ConfirmationDialogFragmentListener
                                            confirmationDialogFragmentListener =
                                                    new FingerprintDeleteDialog
                                                            .ConfirmationDialogFragmentListener() { // from class: com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsMultiSelect.4
                                                        @Override // com.samsung.android.settings.biometrics.fingerprint.FingerprintDeleteDialog.ConfirmationDialogFragmentListener
                                                        public final void onDismiss() {
                                                            FingerprintSettingsMultiSelect.this
                                                                            .mDeleteAllDialog =
                                                                    null;
                                                        }

                                                        @Override // com.samsung.android.settings.biometrics.fingerprint.FingerprintDeleteDialog.ConfirmationDialogFragmentListener
                                                        public final void onNegativeClick() {
                                                            BiometricsGenericHelper.insertSaLog(
                                                                    FingerprintSettingsMultiSelect
                                                                            .this
                                                                            .getContext(),
                                                                    8229,
                                                                    8232);
                                                        }

                                                        @Override // com.samsung.android.settings.biometrics.fingerprint.FingerprintDeleteDialog.ConfirmationDialogFragmentListener
                                                        public final void onPositiveClick() {
                                                            Log.secD(
                                                                    "FpstFingerprintSettingsMultiSelect",
                                                                    "deRegisterAllFingerprint :"
                                                                        + " removeAllEnrolledFingers");
                                                            FingerprintSettingsMultiSelect
                                                                    fingerprintSettingsMultiSelect2 =
                                                                            FingerprintSettingsMultiSelect
                                                                                    .this;
                                                            SALogging.insertSALog(
                                                                    5L,
                                                                    BiometricsGenericHelper
                                                                            .getSaLogIdByDisplayType(
                                                                                    fingerprintSettingsMultiSelect2
                                                                                            .getContext(),
                                                                                    8229),
                                                                    String.valueOf(8233),
                                                                    (String) null);
                                                            boolean z2 = fingerprintUnlockEnabled2;
                                                            if (z2) {
                                                                Log.secD(
                                                                        "FpstFingerprintSettingsMultiSelect",
                                                                        "deRegisterAllFingerprint :"
                                                                            + " DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED");
                                                                Settings.System.putInt(
                                                                        fingerprintSettingsMultiSelect2
                                                                                .getActivity()
                                                                                .getContentResolver(),
                                                                        "db_lockscreen_is_smart_lock",
                                                                        0);
                                                            }
                                                            if (!z2 && !z) {
                                                                fingerprintSettingsMultiSelect2
                                                                        .deleteFingerprintSequentially();
                                                                return;
                                                            }
                                                            Settings.System.putInt(
                                                                    fingerprintSettingsMultiSelect2
                                                                            .getActivity()
                                                                            .getContentResolver(),
                                                                    "fingerprint_secret_box",
                                                                    0);
                                                            Settings.Secure.putInt(
                                                                    fingerprintSettingsMultiSelect2
                                                                            .getActivity()
                                                                            .getContentResolver(),
                                                                    "fingerprint_webpass",
                                                                    0);
                                                            Settings.System.putInt(
                                                                    fingerprintSettingsMultiSelect2
                                                                            .getActivity()
                                                                            .getContentResolver(),
                                                                    "fingerprint_fingerIndex",
                                                                    0);
                                                            Settings.Secure.putInt(
                                                                    fingerprintSettingsMultiSelect2
                                                                            .getActivity()
                                                                            .getContentResolver(),
                                                                    "fingerprint_used_samsungaccount",
                                                                    0);
                                                            Settings.Secure.putInt(
                                                                    fingerprintSettingsMultiSelect2
                                                                            .getActivity()
                                                                            .getContentResolver(),
                                                                    "fingerprint_samsungaccount_confirmed",
                                                                    0);
                                                            Log.d(
                                                                    "FpstFingerprintSettingsMultiSelect",
                                                                    "setFingerprintScreenLockDisable");
                                                            FingerprintSettingsUtils
                                                                    .removeFingerprintLock(
                                                                            fingerprintSettingsMultiSelect2
                                                                                    .mUserId,
                                                                            fingerprintSettingsMultiSelect2
                                                                                    .getActivity(),
                                                                            fingerprintSettingsMultiSelect2
                                                                                    .mLockPatternUtils);
                                                            fingerprintSettingsMultiSelect2
                                                                    .deleteFingerprintSequentially();
                                                        }
                                                    };
                                    FingerprintDeleteDialog fingerprintDeleteDialog =
                                            new FingerprintDeleteDialog();
                                    fingerprintDeleteDialog.mTitle = str;
                                    fingerprintDeleteDialog.mMsg = string3;
                                    fingerprintDeleteDialog.mButton = i;
                                    fingerprintDeleteDialog.mListener =
                                            confirmationDialogFragmentListener;
                                    fingerprintSettingsMultiSelect.mDeleteAllDialog =
                                            fingerprintDeleteDialog;
                                    fingerprintDeleteDialog.show(
                                            fingerprintSettingsMultiSelect.getFragmentManager(),
                                            "fpstDeleteAllDialog");
                                }
                            } else {
                                BiometricsGenericHelper.insertSaLog(
                                        fingerprintSettingsMultiSelect.getContext(), 8229);
                                int i3 = fingerprintSettingsMultiSelect.mSelectedCount;
                                String string4 =
                                        i3 == 1
                                                ? fingerprintSettingsMultiSelect.getString(
                                                        R.string
                                                                .sec_fingerprint_delete_case1_1_fingerprint_msg)
                                                : fingerprintSettingsMultiSelect.getString(
                                                        R.string
                                                                .sec_fingerprint_delete_case1_n_fingerprints_msg,
                                                        Integer.valueOf(i3));
                                FingerprintDeleteDialog.ConfirmationDialogFragmentListener
                                        confirmationDialogFragmentListener2 =
                                                new FingerprintDeleteDialog
                                                        .ConfirmationDialogFragmentListener() { // from class: com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsMultiSelect.2
                                                    @Override // com.samsung.android.settings.biometrics.fingerprint.FingerprintDeleteDialog.ConfirmationDialogFragmentListener
                                                    public final void onDismiss() {
                                                        FingerprintSettingsMultiSelect.this
                                                                        .mDeleteDialog =
                                                                null;
                                                    }

                                                    @Override // com.samsung.android.settings.biometrics.fingerprint.FingerprintDeleteDialog.ConfirmationDialogFragmentListener
                                                    public final void onNegativeClick() {
                                                        BiometricsGenericHelper.insertSaLog(
                                                                FingerprintSettingsMultiSelect.this
                                                                        .getContext(),
                                                                8229,
                                                                8232);
                                                    }

                                                    @Override // com.samsung.android.settings.biometrics.fingerprint.FingerprintDeleteDialog.ConfirmationDialogFragmentListener
                                                    public final void onPositiveClick() {
                                                        FingerprintSettingsMultiSelect
                                                                fingerprintSettingsMultiSelect2 =
                                                                        FingerprintSettingsMultiSelect
                                                                                .this;
                                                        int size =
                                                                fingerprintSettingsMultiSelect2
                                                                        .mSelectionChecklist.size();
                                                        if (size >= 1
                                                                && fingerprintSettingsMultiSelect2
                                                                                .getNumOfCheckedList()
                                                                        >= 1) {
                                                            for (int i4 = 0; i4 < size; i4++) {
                                                                if (((CheckBoxPreference)
                                                                                fingerprintSettingsMultiSelect2
                                                                                        .mSelectionChecklist
                                                                                        .get(i4))
                                                                        .mChecked) {
                                                                    fingerprintSettingsMultiSelect2
                                                                            .getContext();
                                                                    LoggingHelper
                                                                            .insertEventLogging(
                                                                                    8229,
                                                                                    8233,
                                                                                    0L,
                                                                                    String.valueOf(
                                                                                            i4
                                                                                                    + 1));
                                                                }
                                                            }
                                                        }
                                                        fingerprintSettingsMultiSelect2
                                                                .deleteFingerprintSequentially();
                                                    }
                                                };
                                FingerprintDeleteDialog fingerprintDeleteDialog2 =
                                        new FingerprintDeleteDialog();
                                fingerprintDeleteDialog2.mButton = -1;
                                fingerprintDeleteDialog2.mTitle = null;
                                fingerprintDeleteDialog2.mMsg = string4;
                                fingerprintDeleteDialog2.mListener =
                                        confirmationDialogFragmentListener2;
                                fingerprintSettingsMultiSelect.mDeleteDialog =
                                        fingerprintDeleteDialog2;
                                fingerprintDeleteDialog2.show(
                                        fingerprintSettingsMultiSelect.getFragmentManager(),
                                        "fpstDeleteSomeDialog");
                            }
                        }
                        return false;
                    }
                };
        this.mRemoveButtonLayout.addView(bottomNavigationView);
        FragmentActivity activity2 = getActivity();
        if (activity2 instanceof SettingsBaseActivity) {
            ((SettingsBaseActivity) activity2).disableExtendedAppBar();
        }
        Toolbar toolbar =
                (Toolbar)
                        getActivity()
                                .getWindow()
                                .getDecorView()
                                .findViewById(android.R.id.action_bar_container);
        if (toolbar != null) {
            this.mOriginalContentStart = toolbar.getContentInsetStart();
            Context context = getContext();
            boolean z = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
            int contentInsetEnd = toolbar.getContentInsetEnd();
            toolbar.ensureContentInsets();
            toolbar.mContentInsets.setRelative(
                    (int) ((context.getResources().getDisplayMetrics().densityDpi / 160.0f) * 16),
                    contentInsetEnd);
        } else {
            Log.secE("FpstFingerprintSettingsMultiSelect", "setContentsStart mToolbar Null");
        }
        super.onViewCreated(view, bundle);
        boolean z2 = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        TypedArray typedArray = null;
        try {
            try {
                typedArray =
                        getContext()
                                .obtainStyledAttributes(
                                        null,
                                        R$styleable.PreferenceFragment,
                                        TypedArrayUtils.getAttr(
                                                getContext(),
                                                R.attr.preferenceFragmentStyle,
                                                android.R.attr.preferenceFragmentStyle),
                                        0);
                Drawable drawable = typedArray.getDrawable(1);
                int dimensionPixelSize = typedArray.getDimensionPixelSize(2, -1);
                super.setDivider(
                        !z2
                                ? new InsetDrawable(
                                        drawable,
                                        getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .sec_fingerprint_checkbox_width_for_divider_inset),
                                        0,
                                        0,
                                        0)
                                : new InsetDrawable(
                                        drawable,
                                        0,
                                        0,
                                        getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .sec_fingerprint_checkbox_width_for_divider_inset),
                                        0));
                if (dimensionPixelSize != -1) {
                    super.setDividerHeight(dimensionPixelSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (typedArray == null) {
                    return;
                }
            }
            typedArray.recycle();
        } catch (Throwable th) {
            if (typedArray != null) {
                typedArray.recycle();
            }
            throw th;
        }
    }

    public final void updateActionbarState$1() {
        int numOfCheckedList = getNumOfCheckedList();
        ActionBar actionBar = this.mActionBar;
        if (actionBar == null || actionBar.getCustomView() == null) {
            Log.secE(
                    "FpstFingerprintSettingsMultiSelect",
                    "updateActionbarState: no custom actionbar");
            return;
        }
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        numOfCheckedList, "updateActionbarState checked: ", ", size: ");
        m.append(this.mSelectionChecklist.size());
        Log.secD("FpstFingerprintSettingsMultiSelect", m.toString());
        if (numOfCheckedList > 0) {
            this.mSelectedFingerprintTextView.setText(
                    String.format(
                            getActivity()
                                    .getResources()
                                    .getString(R.string.sec_fingerprint_selected_count),
                            Integer.valueOf(numOfCheckedList)));
        } else {
            this.mSelectedFingerprintTextView.setText(R.string.sec_fingerprint_select_fingerprints);
        }
        if (numOfCheckedList == this.mSelectionChecklist.size()) {
            this.mSelectAllCheckbox.setChecked(true);
        } else {
            this.mSelectAllCheckbox.setChecked(false);
        }
        if (numOfCheckedList == 0) {
            this.mRemoveButtonLayout.setVisibility(8);
        } else if (numOfCheckedList == 1
                || numOfCheckedList == 2
                || numOfCheckedList == 3
                || numOfCheckedList == 4) {
            this.mRemoveButtonLayout.setVisibility(0);
        }
    }
}
