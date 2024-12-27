package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.wifi.SecWifiProgressButtonPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiImeHelper implements TextView.OnEditorActionListener {
    public static final boolean DBG = Debug.semIsProductDev();
    public SecWifiProgressButtonPreference mActionPreference;
    public final List mEditPreferences = new ArrayList();
    public boolean mFlagShowKeyboardOnce = true;
    public final InputMethodManager mInputManager;
    public EditText mLastUpdatedEditTextView;

    public WifiImeHelper(Context context) {
        this.mInputManager = (InputMethodManager) context.getSystemService("input_method");
    }

    public static void setImeOption(EditText editText, boolean z) {
        if (editText != null) {
            editText.setImeOptions((z ? 6 : 5) | 33554432);
            if (editText.hasFocus()) {
                editText.clearFocus();
                Log.v("WifiImeHelper", "ime option was changed, clear focus");
            }
        }
    }

    public final void hideSoftKeyboard(View view) {
        if (this.mInputManager.isInputMethodShown()) {
            if (view == null) {
                Log.i("WifiImeHelper", "hideSoftKeyboard focusedView is null force hide");
                this.mInputManager.semForceHideSoftInput();
            } else {
                Log.i("WifiImeHelper", "hideSoftKeyboard");
                this.mInputManager.hideSoftInputFromWindow(view.getWindowToken(), 2);
            }
        }
    }

    public final void init(final View view, PreferenceScreen preferenceScreen) {
        ((ArrayList) this.mEditPreferences).clear();
        int i = 0;
        for (int i2 = 0; i2 < preferenceScreen.getPreferenceCount(); i2++) {
            Preference preference = preferenceScreen.getPreference(i2);
            if (preference instanceof LayoutPreference) {
                LayoutPreference layoutPreference = (LayoutPreference) preference;
                View findViewById = layoutPreference.mRootView.findViewById(R.id.edittext);
                if (findViewById instanceof EditText) {
                    ((ArrayList) this.mEditPreferences)
                            .add(Pair.create(layoutPreference, (EditText) findViewById));
                }
            }
        }
        Log.d(
                "WifiImeHelper",
                "init size:"
                        + ((ArrayList) this.mEditPreferences).size()
                        + ", showIme:"
                        + this.mFlagShowKeyboardOnce);
        updateImeOptions();
        if (this.mFlagShowKeyboardOnce) {
            this.mFlagShowKeyboardOnce = false;
            if (((ArrayList) this.mEditPreferences).isEmpty()) {
                return;
            }
            if (view == null) {
                while (true) {
                    if (i >= ((ArrayList) this.mEditPreferences).size()) {
                        break;
                    }
                    Pair pair = (Pair) ((ArrayList) this.mEditPreferences).get(i);
                    if (((LayoutPreference) pair.first).isVisible()
                            && ((LayoutPreference) pair.first).isEnabled()) {
                        view = (View) pair.second;
                        break;
                    }
                    i++;
                }
                if (view != null) {
                    view.requestFocus();
                    if (DBG) {
                        Log.v("WifiImeHelper", "start focus at idx:" + i);
                    }
                }
            }
            if (view instanceof EditText) {
                this.mLastUpdatedEditTextView = (EditText) view;
                if (view == null) {
                    return;
                }
                view.postDelayed(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.wifi.details.WifiImeHelper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                WifiImeHelper wifiImeHelper = WifiImeHelper.this;
                                View view2 = view;
                                wifiImeHelper.getClass();
                                try {
                                    Log.i("WifiImeHelper", "showSoftKeyboard");
                                    wifiImeHelper.mInputManager.showSoftInput(view2, 1);
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        200L);
            }
        }
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        SecWifiProgressButtonPreference secWifiProgressButtonPreference;
        if (i != 6
                || (secWifiProgressButtonPreference = this.mActionPreference) == null
                || !secWifiProgressButtonPreference.isEnabled()) {
            return false;
        }
        Log.i("WifiImeHelper", "action done - preference click");
        this.mActionPreference.performClick();
        return false;
    }

    public final void updateImeOptions() {
        EditText editText;
        if (((ArrayList) this.mEditPreferences).isEmpty()) {
            return;
        }
        int size = ((ArrayList) this.mEditPreferences).size() - 1;
        while (true) {
            if (size < 0) {
                editText = null;
                break;
            }
            Pair pair = (Pair) ((ArrayList) this.mEditPreferences).get(size);
            if (((LayoutPreference) pair.first).isVisible()
                    && ((LayoutPreference) pair.first).isEnabled()) {
                editText = (EditText) pair.second;
                break;
            }
            size--;
        }
        if (editText == null) {
            setImeOption(this.mLastUpdatedEditTextView, false);
            this.mLastUpdatedEditTextView = null;
            Log.v("WifiImeHelper", "clear IME option");
        } else if (editText != this.mLastUpdatedEditTextView) {
            Log.v("WifiImeHelper", "change IME option idx:" + size);
            setImeOption(this.mLastUpdatedEditTextView, false);
            setImeOption(editText, true);
            this.mLastUpdatedEditTextView = editText;
        }
    }
}
