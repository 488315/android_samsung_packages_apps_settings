package com.samsung.android.settings.accessibility.dexterity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.hardware.input.InputSettings;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslSwitchBar;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.accessibility.base.widget.PickerCompat;
import com.samsung.android.settings.accessibility.base.widget.PickerTestTextFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SlowKeysPreferenceFragment extends PickerTestTextFragment
        implements PickerCompat.setOnValueChangeListener, CompoundButton.OnCheckedChangeListener {
    public Context mContext;
    public long mCurrentTime;
    public final Handler mHandler;
    public boolean mKeyEventFinished;
    public final AnonymousClass1 mSlowKeysObserver;
    public SlowKeyOnKeyListener mSlowKeysOnKeyListener;
    public long mSlowKeysPeriodMilli;
    public SettingsMainSwitchBar switchBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SlowKeyOnKeyListener implements View.OnKeyListener {
        public KeyEvent mCurrentKeyEvent = null;
        public View mView = null;
        public final AnonymousClass1 mKeyEventHandler =
                new Handler() { // from class:
                                // com.samsung.android.settings.accessibility.dexterity.SlowKeysPreferenceFragment.SlowKeyOnKeyListener.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        SlowKeyOnKeyListener slowKeyOnKeyListener = SlowKeyOnKeyListener.this;
                        if (slowKeyOnKeyListener.mCurrentKeyEvent != null) {
                            Log.d(
                                    "SlowKeyOnKeyListener",
                                    "onKeyEvent : "
                                            + slowKeyOnKeyListener.mCurrentKeyEvent.getKeyCode());
                            long uptimeMillis = SystemClock.uptimeMillis();
                            slowKeyOnKeyListener.mView.onKeyDown(
                                    slowKeyOnKeyListener.mCurrentKeyEvent.getKeyCode(),
                                    new KeyEvent(
                                            uptimeMillis,
                                            uptimeMillis,
                                            slowKeyOnKeyListener.mCurrentKeyEvent.getAction(),
                                            slowKeyOnKeyListener.mCurrentKeyEvent.getKeyCode(),
                                            slowKeyOnKeyListener.mCurrentKeyEvent.getRepeatCount(),
                                            slowKeyOnKeyListener.mCurrentKeyEvent.getMetaState(),
                                            slowKeyOnKeyListener.mCurrentKeyEvent.getDeviceId(),
                                            slowKeyOnKeyListener.mCurrentKeyEvent.getScanCode(),
                                            slowKeyOnKeyListener.mCurrentKeyEvent.getFlags()));
                            slowKeyOnKeyListener.mKeyEventHandler.removeMessages(1);
                        }
                    }
                };

        /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.accessibility.dexterity.SlowKeysPreferenceFragment$SlowKeyOnKeyListener$1] */
        public SlowKeyOnKeyListener() {
            SlowKeysPreferenceFragment.this.mSlowKeysPeriodMilli =
                    InputSettings.getAccessibilitySlowKeysThreshold(
                            SlowKeysPreferenceFragment.this.mContext);
        }

        @Override // android.view.View.OnKeyListener
        public final boolean onKey(View view, int i, KeyEvent keyEvent) {
            this.mView = view;
            int action = keyEvent.getAction();
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            action, "onKey() action : ", ", keyCode :");
            m.append(keyEvent.getKeyCode());
            m.append(", mKeyEventFinished : ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    m, SlowKeysPreferenceFragment.this.mKeyEventFinished, "SlowKeyOnKeyListener");
            if (action == 1) {
                SlowKeysPreferenceFragment.this.mKeyEventFinished = true;
                long currentTimeMillis = System.currentTimeMillis();
                SlowKeysPreferenceFragment slowKeysPreferenceFragment =
                        SlowKeysPreferenceFragment.this;
                if (currentTimeMillis - slowKeysPreferenceFragment.mCurrentTime
                        < slowKeysPreferenceFragment.mSlowKeysPeriodMilli) {
                    Log.d("SlowKeyOnKeyListener", "onKey() removeMessages handler");
                    removeMessages(1);
                }
                return false;
            }
            if (4 == keyEvent.getKeyCode()) {
                return false;
            }
            if (action == 0) {
                SlowKeysPreferenceFragment slowKeysPreferenceFragment2 =
                        SlowKeysPreferenceFragment.this;
                if (slowKeysPreferenceFragment2.mKeyEventFinished) {
                    this.mCurrentKeyEvent = keyEvent;
                    slowKeysPreferenceFragment2.mCurrentTime = System.currentTimeMillis();
                    sendEmptyMessageDelayed(
                            1, SlowKeysPreferenceFragment.this.mSlowKeysPeriodMilli);
                    SlowKeysPreferenceFragment.this.mKeyEventFinished = false;
                    return true;
                }
            }
            return this.mCurrentKeyEvent != null;
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.accessibility.dexterity.SlowKeysPreferenceFragment$1] */
    public SlowKeysPreferenceFragment() {
        new Handler();
        this.mKeyEventFinished = true;
        this.mCurrentTime = 0L;
        this.mSlowKeysObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.accessibility.dexterity.SlowKeysPreferenceFragment.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        Log.d("SlowKeysPreferenceFragment", "mSlowKeysObserver onChange()");
                        SlowKeysPreferenceFragment.this.mSlowKeysPeriodMilli =
                                InputSettings.getAccessibilitySlowKeysThreshold(r3.mContext);
                        SlowKeysPreferenceFragment slowKeysPreferenceFragment =
                                SlowKeysPreferenceFragment.this;
                        boolean isAccessibilitySlowKeysEnabled =
                                InputSettings.isAccessibilitySlowKeysEnabled(
                                        slowKeysPreferenceFragment.mContext);
                        if (((SeslSwitchBar) slowKeysPreferenceFragment.switchBar)
                                        .mSwitch.isChecked()
                                != isAccessibilitySlowKeysEnabled) {
                            slowKeysPreferenceFragment.switchBar.setCheckedInternal(
                                    isAccessibilitySlowKeysEnabled);
                        }
                    }
                };
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.PickerTestTextFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5010;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        SlowKeysUtils.setSlowKeysEnable(this.mContext, z);
        setEditTextOnKeyListener(z ? null : this.mSlowKeysOnKeyListener);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getContext().getContentResolver().unregisterContentObserver(this.mSlowKeysObserver);
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getContext()
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("accessibility_slow_keys"),
                        true,
                        this.mSlowKeysObserver);
        boolean isAccessibilitySlowKeysEnabled =
                InputSettings.isAccessibilitySlowKeysEnabled(this.mContext);
        if (((SeslSwitchBar) this.switchBar).mSwitch.isChecked()
                != isAccessibilitySlowKeysEnabled) {
            this.switchBar.setCheckedInternal(isAccessibilitySlowKeysEnabled);
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.PickerCompat.setOnValueChangeListener
    public final void onValueChange(String str) {
        Context context = this.mContext;
        Settings.Secure.putFloat(
                context.getContentResolver(), "slow_keys_period", Float.parseFloat(str));
        if (InputSettings.isAccessibilitySlowKeysEnabled(this.mContext)) {
            SlowKeysUtils.setSlowKeysEnable(this.mContext, true);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        this.mContext = getContext();
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        if (settingsActivity != null) {
            this.switchBar = settingsActivity.mMainSwitch;
        }
        SettingsMainSwitchBar settingsMainSwitchBar = this.switchBar;
        if (settingsMainSwitchBar != null) {
            setSwitchBarPadding(settingsMainSwitchBar);
            this.switchBar.setChecked(InputSettings.isAccessibilitySlowKeysEnabled(this.mContext));
            this.switchBar.addOnSwitchChangeListener(this);
            this.switchBar.setOnBeforeCheckedChangeListener(
                    new SettingsMainSwitchBar
                            .OnBeforeCheckedChangeListener() { // from class:
                                                               // com.samsung.android.settings.accessibility.dexterity.SlowKeysPreferenceFragment$$ExternalSyntheticLambda0
                        @Override // com.android.settings.widget.SettingsMainSwitchBar.OnBeforeCheckedChangeListener
                        public final boolean onBeforeCheckedChanged(boolean z) {
                            final SlowKeysPreferenceFragment slowKeysPreferenceFragment =
                                    SlowKeysPreferenceFragment.this;
                            if (!z) {
                                slowKeysPreferenceFragment.getClass();
                                return false;
                            }
                            AlertDialog createExclusiveDialog =
                                    AccessibilityDialogUtils.createExclusiveDialog(
                                            slowKeysPreferenceFragment.mContext,
                                            "slow_keys",
                                            new DialogInterface
                                                    .OnClickListener() { // from class:
                                                                         // com.samsung.android.settings.accessibility.dexterity.SlowKeysPreferenceFragment$$ExternalSyntheticLambda1
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i) {
                                                    SlowKeysPreferenceFragment
                                                            slowKeysPreferenceFragment2 =
                                                                    SlowKeysPreferenceFragment.this;
                                                    slowKeysPreferenceFragment2.switchBar
                                                            .setCheckedInternal(true);
                                                    SlowKeysUtils.setSlowKeysEnable(
                                                            slowKeysPreferenceFragment2.mContext,
                                                            true);
                                                    slowKeysPreferenceFragment2
                                                            .setEditTextOnKeyListener(null);
                                                }
                                            },
                                            null);
                            if (createExclusiveDialog == null) {
                                return false;
                            }
                            slowKeysPreferenceFragment.switchBar.setCheckedInternal(false);
                            createExclusiveDialog.show();
                            return true;
                        }
                    });
            this.switchBar.setSessionDescription(getString(R.string.slow_keys));
            this.switchBar.show();
        }
        setupPicker(
                Float.toString(
                        Settings.Secure.getFloat(
                                this.mContext.getContentResolver(), "slow_keys_period", 0.3f)),
                "5.0");
        this.mPicker.mValueChangeListener = this;
        this.mPickerDescription.setText(getString(R.string.slow_keys_summary));
        this.mPickerDescription.setMovementMethod(new ScrollingMovementMethod());
        this.mPickerDescription.setClickable(false);
        this.mPickerDescription.setLongClickable(false);
        this.mTestEdit.setOnClickListener(null);
        this.mTestEditLayout.setVisibility(0);
        this.mTestEdit.setPrivateImeOptions(
                "disableLiveMessage=true;disableGifKeyboard=true;disableSticker=true;disablePrediction=true");
        this.mSlowKeysOnKeyListener = new SlowKeyOnKeyListener();
        if (InputSettings.isAccessibilitySlowKeysEnabled(this.mContext)) {
            return;
        }
        setEditTextOnKeyListener(this.mSlowKeysOnKeyListener);
    }
}
