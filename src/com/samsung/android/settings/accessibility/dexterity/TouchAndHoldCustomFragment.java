package com.samsung.android.settings.accessibility.dexterity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchAndHoldCustomFragment extends DashboardFragment {
    public Menu mMenu;
    public MenuInflater mMenuInflater;
    public MenuItem mPortraitSaveButton;
    public TextView mTimeTextView;
    public TouchAndHoldView mTouchAndHoldView;
    public View mTouchContainer;
    public long mStartPressTime = -1;
    public long mTotalPressedTime = 0;
    public AlertDialog mDialog = null;
    public final Handler mHandler = new Handler(Looper.myLooper());
    public final AnonymousClass1 mCalculateTouchDuration =
            new Runnable() { // from class:
                             // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldCustomFragment.1
                @Override // java.lang.Runnable
                public final void run() {
                    TouchAndHoldCustomFragment touchAndHoldCustomFragment =
                            TouchAndHoldCustomFragment.this;
                    long uptimeMillis = SystemClock.uptimeMillis();
                    TouchAndHoldCustomFragment touchAndHoldCustomFragment2 =
                            TouchAndHoldCustomFragment.this;
                    touchAndHoldCustomFragment.mTotalPressedTime =
                            uptimeMillis - touchAndHoldCustomFragment2.mStartPressTime;
                    long j = (touchAndHoldCustomFragment2.mTotalPressedTime / 10) * 10;
                    touchAndHoldCustomFragment2.mTotalPressedTime = j;
                    if (j >= 200) {
                        touchAndHoldCustomFragment2.setSaveButtonEnable(true);
                        TouchAndHoldView touchAndHoldView =
                                TouchAndHoldCustomFragment.this.mTouchAndHoldView;
                        touchAndHoldView.mCircleImageView.setImageDrawable(
                                touchAndHoldView.mHoldStandBy);
                        touchAndHoldView.mCircleImageView.setLayoutParams(
                                touchAndHoldView.getCircleImageViewLayoutParams());
                    }
                    TouchAndHoldCustomFragment.this.mTimeTextView.setText(
                            String.format(
                                    Locale.getDefault(),
                                    "%.2f",
                                    Float.valueOf(
                                            TouchAndHoldCustomFragment.this.mTotalPressedTime
                                                    / 1000.0f)));
                    if (TouchAndHoldCustomFragment.this.isResumed()) {
                        TouchAndHoldCustomFragment touchAndHoldCustomFragment3 =
                                TouchAndHoldCustomFragment.this;
                        touchAndHoldCustomFragment3.mHandler.postDelayed(
                                touchAndHoldCustomFragment3.mCalculateTouchDuration, 10L);
                    }
                }
            };
    public final AnonymousClass2 onBackPressedCallback =
            new OnBackPressedCallback() { // from class:
                                          // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldCustomFragment.2
                @Override // androidx.activity.OnBackPressedCallback
                public final void handleOnBackPressed() {
                    TouchAndHoldCustomFragment touchAndHoldCustomFragment =
                            TouchAndHoldCustomFragment.this;
                    if (touchAndHoldCustomFragment.mTotalPressedTime >= 200) {
                        touchAndHoldCustomFragment.showDialog(1);
                        return;
                    }
                    FragmentActivity activity = touchAndHoldCustomFragment.getActivity();
                    if (activity != null) {
                        activity.finish();
                    }
                }
            };
    public final AnonymousClass3 mTouchViewDelegate =
            new View
                    .AccessibilityDelegate() { // from class:
                                               // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldCustomFragment.3
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(
                        View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    accessibilityNodeInfo.addAction(
                            new AccessibilityNodeInfo.AccessibilityAction(
                                    32,
                                    TouchAndHoldCustomFragment.this.getString(
                                            R.string.touch_and_hold_delay_custom_hint)));
                }
            };

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5014;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return 0;
    }

    public final void init(View view) {
        this.mTotalPressedTime = 0L;
        TouchAndHoldView touchAndHoldView =
                (TouchAndHoldView) view.findViewById(R.id.tapandholdview);
        this.mTouchAndHoldView = touchAndHoldView;
        touchAndHoldView.semSetRoundedCorners(15);
        this.mTouchAndHoldView.semSetRoundedCornerColor(
                15, getContext().getColor(R.color.rounded_corner_color));
        this.mTouchAndHoldView.setOnTouchListener(
                new View
                        .OnTouchListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldCustomFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view2, MotionEvent motionEvent) {
                        TouchAndHoldCustomFragment touchAndHoldCustomFragment =
                                TouchAndHoldCustomFragment.this;
                        touchAndHoldCustomFragment.getClass();
                        int action = motionEvent.getAction();
                        if (action == 0) {
                            touchAndHoldCustomFragment.mStartPressTime = SystemClock.uptimeMillis();
                            TouchAndHoldView touchAndHoldView2 =
                                    touchAndHoldCustomFragment.mTouchAndHoldView;
                            float x = motionEvent.getX();
                            float y = motionEvent.getY();
                            touchAndHoldView2.mXCoordinate = x;
                            touchAndHoldView2.mYCoordinate = y;
                            TouchAndHoldView touchAndHoldView3 =
                                    touchAndHoldCustomFragment.mTouchAndHoldView;
                            touchAndHoldView3.mCircleImageView.setImageDrawable(
                                    touchAndHoldView3.mTapConfirm);
                            touchAndHoldView3.mCircleImageView.setLayoutParams(
                                    touchAndHoldView3.getCircleImageViewLayoutParams());
                            touchAndHoldCustomFragment.setSaveButtonEnable(false);
                            touchAndHoldCustomFragment.mHandler.post(
                                    touchAndHoldCustomFragment.mCalculateTouchDuration);
                            touchAndHoldCustomFragment.mTouchAndHoldView.announceForAccessibility(
                                    ApnSettings.MVNO_NONE);
                        } else if (action == 1 || action == 3) {
                            Context context = touchAndHoldCustomFragment.getContext();
                            if (context != null) {
                                if (touchAndHoldCustomFragment.mTotalPressedTime <= 200) {
                                    touchAndHoldCustomFragment.mTouchAndHoldView
                                            .announceForAccessibility(
                                                    context.getText(
                                                            R.string
                                                                    .touch_and_hold_not_enough_contentDescription));
                                } else {
                                    touchAndHoldCustomFragment.mTouchAndHoldView
                                            .announceForAccessibility(
                                                    context.getString(
                                                            touchAndHoldCustomFragment
                                                                                    .mTotalPressedTime
                                                                            == 1000
                                                                    ? R.string
                                                                            .touch_and_hold_enough_singular_contentDescription
                                                                    : R.string
                                                                            .touch_and_hold_enough_plural_contentDescription,
                                                            String.format(
                                                                    Locale.getDefault(),
                                                                    "%.2f",
                                                                    Float.valueOf(
                                                                            touchAndHoldCustomFragment
                                                                                            .mTotalPressedTime
                                                                                    / 1000.0f))));
                                }
                            }
                            touchAndHoldCustomFragment.mHandler.removeCallbacks(
                                    touchAndHoldCustomFragment.mCalculateTouchDuration);
                            touchAndHoldCustomFragment.mStartPressTime = -1L;
                        }
                        return true;
                    }
                });
        View findViewById = view.findViewById(R.id.touch_container);
        this.mTouchContainer = findViewById;
        findViewById.setAccessibilityDelegate(this.mTouchViewDelegate);
        if (getResources().getConfiguration().semMobileKeyboardCovered == 1) {
            this.mTouchAndHoldView.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            -1,
                            getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.custom_tap_and_hold_view_mkeyboard_height)));
        } else {
            this.mTouchAndHoldView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        }
        TextView textView = (TextView) view.findViewById(R.id.longpress_time);
        this.mTimeTextView = textView;
        textView.setText(
                String.format(
                        Locale.getDefault(),
                        "%.2f",
                        Float.valueOf(this.mTotalPressedTime / 1000.0f)));
    }

    public final void initEditMenu() {
        ActionMenuView actionMenuView =
                (ActionMenuView) getView().findViewById(R.id.edit_action_menu);
        if (actionMenuView != null) {
            MenuBuilder menu = actionMenuView.getMenu();
            if (menu.mItems.size() == 0) {
                this.mMenuInflater.inflate(R.menu.touch_and_hold_delay_bottom_menu, menu);
            }
            actionMenuView.mOnMenuItemClickListener =
                    new ActionMenuView
                            .OnMenuItemClickListener() { // from class:
                                                         // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldCustomFragment$$ExternalSyntheticLambda1
                        @Override // androidx.appcompat.widget.ActionMenuView.OnMenuItemClickListener
                        public final boolean onMenuItemClick(MenuItem menuItem) {
                            return TouchAndHoldCustomFragment.this.onOptionsItemSelected(menuItem);
                        }
                    };
            this.mPortraitSaveButton = menu.findItem(R.id.bottom_menu_save);
        }
        Menu menu2 = this.mMenu;
        if (menu2 != null) {
            if (getResources().getConfiguration().orientation == 1) {
                menu2.setGroupVisible(R.id.bottom_menu_group, false);
            } else {
                menu2.setGroupVisible(R.id.bottom_menu_group, true);
            }
        }
        setSaveButtonEnable(this.mTotalPressedTime >= 200);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getOnBackPressedDispatcher().addCallback(this.onBackPressedCallback);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        LayoutInflater from = LayoutInflater.from(getActivity());
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
            init(from.inflate(R.layout.fragment_touch_and_hold_delay, viewGroup));
            initEditMenu();
        }
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        removeDialog(1);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null && appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        setRetainInstance(true);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i != 1) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String string = getString(R.string.longpress_dialog_summary);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = string;
        alertParams.mCancelable = false;
        builder.setPositiveButton(
                R.string.touch_and_hold_delay_save,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldCustomFragment$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        TouchAndHoldCustomFragment touchAndHoldCustomFragment =
                                TouchAndHoldCustomFragment.this;
                        touchAndHoldCustomFragment.getClass();
                        if (i2 == -3) {
                            dialogInterface.dismiss();
                            return;
                        }
                        if (i2 == -2) {
                            touchAndHoldCustomFragment.finish();
                            return;
                        }
                        if (i2 != -1) {
                            throw new IllegalArgumentException();
                        }
                        int i3 = (int) touchAndHoldCustomFragment.mTotalPressedTime;
                        Log.d("TouchAndHoldCustomFragment", "saved : " + i3);
                        Settings.Secure.putInt(
                                touchAndHoldCustomFragment.getContentResolver(),
                                "long_press_timeout",
                                i3);
                        touchAndHoldCustomFragment.finish();
                    }
                });
        builder.setNeutralButton(
                R.string.touch_and_hold_delay_cancel,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldCustomFragment$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        TouchAndHoldCustomFragment touchAndHoldCustomFragment =
                                TouchAndHoldCustomFragment.this;
                        touchAndHoldCustomFragment.getClass();
                        if (i2 == -3) {
                            dialogInterface.dismiss();
                            return;
                        }
                        if (i2 == -2) {
                            touchAndHoldCustomFragment.finish();
                            return;
                        }
                        if (i2 != -1) {
                            throw new IllegalArgumentException();
                        }
                        int i3 = (int) touchAndHoldCustomFragment.mTotalPressedTime;
                        Log.d("TouchAndHoldCustomFragment", "saved : " + i3);
                        Settings.Secure.putInt(
                                touchAndHoldCustomFragment.getContentResolver(),
                                "long_press_timeout",
                                i3);
                        touchAndHoldCustomFragment.finish();
                    }
                });
        builder.setNegativeButton(
                R.string.touch_and_hold_delay_discard,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.TouchAndHoldCustomFragment$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        TouchAndHoldCustomFragment touchAndHoldCustomFragment =
                                TouchAndHoldCustomFragment.this;
                        touchAndHoldCustomFragment.getClass();
                        if (i2 == -3) {
                            dialogInterface.dismiss();
                            return;
                        }
                        if (i2 == -2) {
                            touchAndHoldCustomFragment.finish();
                            return;
                        }
                        if (i2 != -1) {
                            throw new IllegalArgumentException();
                        }
                        int i3 = (int) touchAndHoldCustomFragment.mTotalPressedTime;
                        Log.d("TouchAndHoldCustomFragment", "saved : " + i3);
                        Settings.Secure.putInt(
                                touchAndHoldCustomFragment.getContentResolver(),
                                "long_press_timeout",
                                i3);
                        touchAndHoldCustomFragment.finish();
                    }
                });
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.setCanceledOnTouchOutside(false);
        return this.mDialog;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        this.mMenuInflater = menuInflater;
        menuInflater.inflate(R.menu.touch_and_hold_delay_bottom_menu, menu);
        this.mMenu = menu;
        initEditMenu();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(R.layout.fragment_touch_and_hold_delay, viewGroup, false);
        init(inflate);
        setHasOptionsMenu(true);
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.bottom_menu_cancel) {
            if (this.mTotalPressedTime >= 200) {
                showDialog(1);
                return true;
            }
            finish();
        } else if (itemId == R.id.bottom_menu_save) {
            int i = (int) this.mTotalPressedTime;
            Log.d("TouchAndHoldCustomFragment", "saved : " + i);
            Settings.Secure.putInt(getContentResolver(), "long_press_timeout", i);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu != null) {
            if (getResources().getConfiguration().orientation == 1) {
                menu.setGroupVisible(R.id.bottom_menu_group, false);
            } else {
                menu.setGroupVisible(R.id.bottom_menu_group, true);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        removeDialog(1);
    }

    public final void setSaveButtonEnable(boolean z) {
        Menu menu = this.mMenu;
        if (menu != null) {
            menu.findItem(R.id.bottom_menu_save).setEnabled(z);
        }
        MenuItem menuItem = this.mPortraitSaveButton;
        if (menuItem != null) {
            menuItem.setEnabled(z);
        }
    }
}
