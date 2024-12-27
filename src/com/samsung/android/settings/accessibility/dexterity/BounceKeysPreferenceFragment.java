package com.samsung.android.settings.accessibility.dexterity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.hardware.input.InputSettings;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SeslSwitchBar;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.accessibility.base.widget.PickerCompat;
import com.samsung.android.settings.accessibility.base.widget.PickerTestTextFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BounceKeysPreferenceFragment extends PickerTestTextFragment
        implements PickerCompat.setOnValueChangeListener, CompoundButton.OnCheckedChangeListener {
    public final AnonymousClass1 mBounceKeysHandler;
    public final AnonymousClass2 mBounceKeysObserver;
    public BounceKeysOnKeyListener mBounceKeysOnKeyListener;
    public long mBounceKeysPeriodMilli;
    public Context mContext;
    public final Handler mHandler;
    public boolean mIsBlocking;
    public int mOldKeyCode;
    public SettingsMainSwitchBar switchBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BounceKeysOnKeyListener implements View.OnKeyListener {
        public BounceKeysOnKeyListener() {
            BounceKeysPreferenceFragment.this.mBounceKeysPeriodMilli =
                    InputSettings.getAccessibilityBounceKeysThreshold(
                            BounceKeysPreferenceFragment.this.mContext);
            BounceKeysPreferenceFragment.this.mIsBlocking = false;
            BounceKeysPreferenceFragment.this.mOldKeyCode = -1;
        }

        @Override // android.view.View.OnKeyListener
        public final boolean onKey(View view, int i, KeyEvent keyEvent) {
            int action = keyEvent.getAction();
            int keyCode = keyEvent.getKeyCode();
            if (4 == keyCode) {
                return false;
            }
            BounceKeysPreferenceFragment bounceKeysPreferenceFragment =
                    BounceKeysPreferenceFragment.this;
            if (bounceKeysPreferenceFragment.mIsBlocking
                    && bounceKeysPreferenceFragment.mOldKeyCode == keyCode) {
                bounceKeysPreferenceFragment.mBounceKeysHandler.removeMessages(0);
                BounceKeysPreferenceFragment bounceKeysPreferenceFragment2 =
                        BounceKeysPreferenceFragment.this;
                bounceKeysPreferenceFragment2.mBounceKeysHandler.sendEmptyMessageDelayed(
                        0, bounceKeysPreferenceFragment2.mBounceKeysPeriodMilli);
                return true;
            }
            if (action == 0) {
                bounceKeysPreferenceFragment.mOldKeyCode = keyCode;
            }
            if (action == 1) {
                Log.d("BounceKeysPreferenceFragment", "BounceKeys is activated");
                BounceKeysPreferenceFragment bounceKeysPreferenceFragment3 =
                        BounceKeysPreferenceFragment.this;
                bounceKeysPreferenceFragment3.mBounceKeysHandler.sendEmptyMessageDelayed(
                        0, bounceKeysPreferenceFragment3.mBounceKeysPeriodMilli);
                BounceKeysPreferenceFragment.this.mIsBlocking = true;
            }
            return false;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.accessibility.dexterity.BounceKeysPreferenceFragment$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.accessibility.dexterity.BounceKeysPreferenceFragment$2] */
    public BounceKeysPreferenceFragment() {
        new Handler();
        this.mBounceKeysHandler =
                new Handler() { // from class:
                                // com.samsung.android.settings.accessibility.dexterity.BounceKeysPreferenceFragment.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        Log.d("BounceKeysPreferenceFragment", "Touch Blocker is deactivated");
                        BounceKeysPreferenceFragment.this.mIsBlocking = false;
                    }
                };
        this.mBounceKeysObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.accessibility.dexterity.BounceKeysPreferenceFragment.2
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        BounceKeysPreferenceFragment.this.mBounceKeysPeriodMilli =
                                InputSettings.getAccessibilityBounceKeysThreshold(r3.mContext);
                        BounceKeysPreferenceFragment bounceKeysPreferenceFragment =
                                BounceKeysPreferenceFragment.this;
                        boolean isAccessibilityBounceKeysEnabled =
                                InputSettings.isAccessibilityBounceKeysEnabled(
                                        bounceKeysPreferenceFragment.mContext);
                        if (((SeslSwitchBar) bounceKeysPreferenceFragment.switchBar)
                                        .mSwitch.isChecked()
                                != isAccessibilityBounceKeysEnabled) {
                            bounceKeysPreferenceFragment.switchBar.setCheckedInternal(
                                    isAccessibilityBounceKeysEnabled);
                        }
                    }
                };
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.PickerTestTextFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5012;
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        BounceKeysUtils.setBounceKeysEnable(this.mContext, z);
        setEditTextOnKeyListener(z ? null : this.mBounceKeysOnKeyListener);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mContext.getContentResolver().unregisterContentObserver(this.mBounceKeysObserver);
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getContext()
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("accessibility_bounce_keys"),
                        true,
                        this.mBounceKeysObserver);
        boolean isAccessibilityBounceKeysEnabled =
                InputSettings.isAccessibilityBounceKeysEnabled(this.mContext);
        if (((SeslSwitchBar) this.switchBar).mSwitch.isChecked()
                != isAccessibilityBounceKeysEnabled) {
            this.switchBar.setCheckedInternal(isAccessibilityBounceKeysEnabled);
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.PickerCompat.setOnValueChangeListener
    public final void onValueChange(String str) {
        Context context = this.mContext;
        Settings.Secure.putFloat(
                context.getContentResolver(), "bounce_keys_period", Float.parseFloat(str));
        if (InputSettings.isAccessibilityBounceKeysEnabled(this.mContext)) {
            BounceKeysUtils.setBounceKeysEnable(this.mContext, true);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        if (settingsActivity != null) {
            this.switchBar = settingsActivity.mMainSwitch;
        }
        SettingsMainSwitchBar settingsMainSwitchBar = this.switchBar;
        if (settingsMainSwitchBar != null) {
            setSwitchBarPadding(settingsMainSwitchBar);
            this.switchBar.setChecked(
                    InputSettings.isAccessibilityBounceKeysEnabled(this.mContext));
            this.switchBar.addOnSwitchChangeListener(this);
            this.switchBar.setOnBeforeCheckedChangeListener(
                    new SettingsMainSwitchBar
                            .OnBeforeCheckedChangeListener() { // from class:
                                                               // com.samsung.android.settings.accessibility.dexterity.BounceKeysPreferenceFragment$$ExternalSyntheticLambda0
                        @Override // com.android.settings.widget.SettingsMainSwitchBar.OnBeforeCheckedChangeListener
                        public final boolean onBeforeCheckedChanged(boolean z) {
                            final BounceKeysPreferenceFragment bounceKeysPreferenceFragment =
                                    BounceKeysPreferenceFragment.this;
                            if (!z) {
                                bounceKeysPreferenceFragment.getClass();
                                return false;
                            }
                            AlertDialog createExclusiveDialog =
                                    AccessibilityDialogUtils.createExclusiveDialog(
                                            bounceKeysPreferenceFragment.mContext,
                                            "bounce_keys",
                                            new DialogInterface
                                                    .OnClickListener() { // from class:
                                                                         // com.samsung.android.settings.accessibility.dexterity.BounceKeysPreferenceFragment$$ExternalSyntheticLambda1
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i) {
                                                    BounceKeysPreferenceFragment
                                                            bounceKeysPreferenceFragment2 =
                                                                    BounceKeysPreferenceFragment
                                                                            .this;
                                                    bounceKeysPreferenceFragment2.switchBar
                                                            .setCheckedInternal(true);
                                                    BounceKeysUtils.setBounceKeysEnable(
                                                            bounceKeysPreferenceFragment2.mContext,
                                                            true);
                                                    bounceKeysPreferenceFragment2
                                                            .setEditTextOnKeyListener(null);
                                                }
                                            },
                                            null);
                            if (createExclusiveDialog == null) {
                                return false;
                            }
                            createExclusiveDialog.show();
                            return true;
                        }
                    });
            this.switchBar.setSessionDescription(getString(R.string.bounce_keys));
            this.switchBar.show();
        }
        setupPicker(
                Float.toString(
                        Settings.Secure.getFloat(
                                this.mContext.getContentResolver(), "bounce_keys_period", 0.5f)),
                "5.0");
        this.mPicker.mValueChangeListener = this;
        this.mPickerDescription.setText(getString(R.string.bounce_keys_summary));
        this.mPickerDescription.setMovementMethod(new ScrollingMovementMethod());
        this.mPickerDescription.setClickable(false);
        this.mPickerDescription.setLongClickable(false);
        this.mTestEdit.setOnClickListener(null);
        this.mTestEditLayout.setVisibility(0);
        this.mTestEdit.setPrivateImeOptions(
                "disableLiveMessage=true;disableGifKeyboard=true;disableSticker=true;disablePrediction=true");
        this.mBounceKeysOnKeyListener = new BounceKeysOnKeyListener();
        if (InputSettings.isAccessibilityBounceKeysEnabled(this.mContext)) {
            return;
        }
        setEditTextOnKeyListener(this.mBounceKeysOnKeyListener);
    }
}
