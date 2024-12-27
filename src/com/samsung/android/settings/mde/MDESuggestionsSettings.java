package com.samsung.android.settings.mde;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.OnActivityResultListener;

import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MDESuggestionsSettings extends SettingsPreferenceFragment
        implements OnActivityResultListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public MDEServiceBindingManager mMDEServiceBindingManager;
    public final List mActivityResultListeners = new ArrayList();
    public final AnonymousClass1 mMDEServiceBindingManagerCallback =
            new MDEServiceBindingManager.Callback() { // from class:
                // com.samsung.android.settings.mde.MDESuggestionsSettings.1
                @Override // com.samsung.android.settings.mde.MDEServiceBindingManager.Callback
                public final void onRequestUpdated() {
                    MDEServiceBindingManager mDEServiceBindingManager =
                            MDESuggestionsSettings.this.mMDEServiceBindingManager;
                    if (mDEServiceBindingManager != null) {
                        mDEServiceBindingManager.sendMessage(1, null);
                    }
                }

                @Override // com.samsung.android.settings.mde.MDEServiceBindingManager.Callback
                public final void onSuggestionsUpdated(List list) {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = ((ArrayList) list).iterator();
                    while (it.hasNext()) {
                        SuggestionInfo suggestionInfo = (SuggestionInfo) it.next();
                        if ("depth_in".equals(suggestionInfo.mUiType)) {
                            arrayList.add(suggestionInfo);
                        }
                    }
                    int i = MDESuggestionsSettings.$r8$clinit;
                    final MDESuggestionsSettings mDESuggestionsSettings =
                            MDESuggestionsSettings.this;
                    PreferenceScreen preferenceScreen =
                            mDESuggestionsSettings.getPreferenceScreen();
                    if (preferenceScreen == null) {
                        return;
                    }
                    preferenceScreen.removeAll();
                    ((ArrayList) mDESuggestionsSettings.mActivityResultListeners).clear();
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        final SuggestionInfo suggestionInfo2 = (SuggestionInfo) it2.next();
                        MDESuggestionPreference mDESuggestionPreference =
                                (MDESuggestionPreference)
                                        mDESuggestionsSettings.createPreference(
                                                "MDESuggestionPreference");
                        mDESuggestionPreference.setKey("key_" + suggestionInfo2.mId);
                        SecInsetCategoryPreference secInsetCategoryPreference =
                                (SecInsetCategoryPreference)
                                        mDESuggestionsSettings.createPreference(
                                                "SecInsetCategoryPreference");
                        secInsetCategoryPreference.setKey(
                                "key_inset_category_" + suggestionInfo2.mId);
                        final MDESuggestionPreferenceLayoutHelper
                                mDESuggestionPreferenceLayoutHelper =
                                        mDESuggestionPreference.mHelper;
                        mDESuggestionPreferenceLayoutHelper.mShortcutId = suggestionInfo2.mId;
                        CharSequence charSequence = suggestionInfo2.mTitle;
                        mDESuggestionPreferenceLayoutHelper.mTitleText = charSequence;
                        if (mDESuggestionPreferenceLayoutHelper.mTitle != null
                                && !TextUtils.isEmpty(charSequence)) {
                            mDESuggestionPreferenceLayoutHelper.mTitle.setText(charSequence);
                        }
                        CharSequence charSequence2 = suggestionInfo2.mSummary;
                        mDESuggestionPreferenceLayoutHelper.mSummaryText = charSequence2;
                        if (mDESuggestionPreferenceLayoutHelper.mSummary != null
                                && !TextUtils.isEmpty(charSequence2)) {
                            mDESuggestionPreferenceLayoutHelper.mSummary.setText(charSequence2);
                        }
                        Icon icon = suggestionInfo2.mIcon;
                        mDESuggestionPreferenceLayoutHelper.mIcon = icon;
                        ImageView imageView = mDESuggestionPreferenceLayoutHelper.mIconView;
                        if (imageView != null && icon != null) {
                            imageView.setImageIcon(icon);
                        }
                        mDESuggestionPreferenceLayoutHelper.setActionButtonText(
                                suggestionInfo2.mExtras.getString("buttonLabel"));
                        View.OnClickListener onClickListener =
                                new View.OnClickListener() { // from class:
                                    // com.samsung.android.settings.mde.MDESuggestionsSettings$$ExternalSyntheticLambda1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        MDESuggestionsSettings mDESuggestionsSettings2 =
                                                MDESuggestionsSettings.this;
                                        SuggestionInfo suggestionInfo3 = suggestionInfo2;
                                        int i2 = MDESuggestionsSettings.$r8$clinit;
                                        mDESuggestionsSettings2.getClass();
                                        String str = suggestionInfo3.mId;
                                        DialogFragment$$ExternalSyntheticOutline0.m(
                                                "Click exit button : ",
                                                str,
                                                "MDESuggestionsSettings");
                                        LoggingHelper.insertEventLogging(70100, 70101, str);
                                        MDESuggestionManager.getInstance()
                                                .mDismissedSuggestions
                                                .add(str);
                                        if (mDESuggestionsSettings2.mMDEServiceBindingManager
                                                != null) {
                                            mDESuggestionsSettings2.mMDEServiceBindingManager
                                                    .sendMessage(
                                                            2,
                                                            AbsAdapter$1$$ExternalSyntheticOutline0
                                                                    .m("id", str));
                                            mDESuggestionsSettings2.mMDEServiceBindingManager
                                                    .sendMessage(1, null);
                                        }
                                    }
                                };
                        mDESuggestionPreferenceLayoutHelper.mExitButtonClickListener =
                                onClickListener;
                        ImageView imageView2 = mDESuggestionPreferenceLayoutHelper.mExitView;
                        if (imageView2 != null) {
                            imageView2.setOnClickListener(onClickListener);
                        }
                        View.OnClickListener onClickListener2 =
                                new View.OnClickListener() { // from class:
                                    // com.samsung.android.settings.mde.MDESuggestionsSettings$$ExternalSyntheticLambda2
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        MDESuggestionsSettings mDESuggestionsSettings2 =
                                                MDESuggestionsSettings.this;
                                        SuggestionInfo suggestionInfo3 = suggestionInfo2;
                                        MDESuggestionPreferenceLayoutHelper
                                                mDESuggestionPreferenceLayoutHelper2 =
                                                        mDESuggestionPreferenceLayoutHelper;
                                        int i2 = MDESuggestionsSettings.$r8$clinit;
                                        mDESuggestionsSettings2.getClass();
                                        String string =
                                                suggestionInfo3.mExtras.getString("buttonType");
                                        Intent intent = suggestionInfo3.mIntent;
                                        if (intent == null) {
                                            Log.e(
                                                    "MDESuggestionsSettings",
                                                    "button intent is null");
                                            return;
                                        }
                                        string.getClass();
                                        if (string.equals("TURNON")) {
                                            Utils$$ExternalSyntheticOutline0.m(
                                                    new StringBuilder("Click TURN ON Settings : "),
                                                    suggestionInfo3.mId,
                                                    "MDESuggestionsSettings");
                                            try {
                                                int shortcutIdNumber =
                                                        MDESuggestionsSettings.getShortcutIdNumber(
                                                                suggestionInfo3.mId);
                                                if (shortcutIdNumber == 0) {
                                                    return;
                                                }
                                                if (mDESuggestionsSettings2.getActivity() != null) {
                                                    mDESuggestionsSettings2
                                                            .getActivity()
                                                            .startActivityForResult(
                                                                    intent, shortcutIdNumber);
                                                }
                                                ((ArrayList)
                                                                mDESuggestionsSettings2
                                                                        .mActivityResultListeners)
                                                        .add(mDESuggestionPreferenceLayoutHelper2);
                                                return;
                                            } catch (ActivityNotFoundException e) {
                                                Log.e(
                                                        "MDESuggestionsSettings",
                                                        "onClick: Activity Not Found Exception. e ="
                                                            + " "
                                                                + e);
                                                return;
                                            }
                                        }
                                        if (string.equals("GOTO")) {
                                            Utils$$ExternalSyntheticOutline0.m(
                                                    new StringBuilder("Click GOTO Settings : "),
                                                    suggestionInfo3.mId,
                                                    "MDESuggestionsSettings");
                                            try {
                                                if (mDESuggestionsSettings2.getActivity() != null) {
                                                    mDESuggestionsSettings2
                                                            .getActivity()
                                                            .startActivity(intent);
                                                }
                                                LoggingHelper.insertEventLogging(
                                                        70100, 70102, suggestionInfo3.mId);
                                                MDESuggestionManager.getInstance()
                                                        .mDismissedSuggestions
                                                        .add(suggestionInfo3.mId);
                                                if (mDESuggestionsSettings2
                                                                .mMDEServiceBindingManager
                                                        != null) {
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("id", suggestionInfo3.mId);
                                                    mDESuggestionsSettings2
                                                            .mMDEServiceBindingManager.sendMessage(
                                                            3, bundle);
                                                    mDESuggestionsSettings2
                                                            .mMDEServiceBindingManager.sendMessage(
                                                            1, null);
                                                }
                                            } catch (ActivityNotFoundException e2) {
                                                Log.e(
                                                        "MDESuggestionsSettings",
                                                        "onClick: Activity Not Found Exception. e ="
                                                            + " "
                                                                + e2);
                                            }
                                        }
                                    }
                                };
                        mDESuggestionPreferenceLayoutHelper.mActionButtonClickListener =
                                onClickListener2;
                        LinearLayout linearLayout =
                                mDESuggestionPreferenceLayoutHelper.mActionButtonContainer;
                        if (linearLayout != null) {
                            linearLayout.setOnClickListener(onClickListener2);
                        }
                        preferenceScreen.addPreference(mDESuggestionPreference);
                        preferenceScreen.addPreference(secInsetCategoryPreference);
                    }
                }
            };

    public static int getShortcutIdNumber(String str) {
        try {
            return Integer.parseInt(str.substring(3));
        } catch (NullPointerException e) {
            Log.e("MDESuggestionsSettings", "NullPointerException. e = " + e);
            return 0;
        } catch (NumberFormatException e2) {
            Log.e("MDESuggestionsSettings", "NumberFormatException. e = " + e2);
            return 0;
        }
    }

    public final Preference createPreference(String str) {
        if (!str.equals("SecInsetCategoryPreference")) {
            return new MDESuggestionPreference(getPrefContext());
        }
        SecInsetCategoryPreference secInsetCategoryPreference =
                new SecInsetCategoryPreference(getPrefContext());
        secInsetCategoryPreference.setHeight(
                getResources()
                        .getDimensionPixelSize(R.dimen.sec_mde_suggestions_inset_category_height));
        return secInsetCategoryPreference;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 70100;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_mde_suggestions_settings;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "onActivityResult : requestCode = ",
                        ", resultCode = ",
                        i,
                        i2,
                        ", intent = ");
        m.append(intent);
        Log.d("MDESuggestionsSettings", m.toString());
        String str = (i < 10 ? "AS-0" : "AS-") + i;
        Iterator it = ((ArrayList) this.mActivityResultListeners).iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(
                    ((MDESuggestionPreferenceLayoutHelper) it.next()).mShortcutId, str)) {
                final Bundle m2 = AbsAdapter$1$$ExternalSyntheticOutline0.m("id", str);
                if (i2 != -1) {
                    LoggingHelper.insertEventLogging(70100, 70103, 0L, str);
                    return;
                }
                LoggingHelper.insertEventLogging(70100, 70103, 1L, str);
                Iterator it2 = ((ArrayList) this.mActivityResultListeners).iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    final MDESuggestionPreferenceLayoutHelper mDESuggestionPreferenceLayoutHelper =
                            (MDESuggestionPreferenceLayoutHelper) it2.next();
                    if (TextUtils.equals(mDESuggestionPreferenceLayoutHelper.mShortcutId, str)) {
                        final int i3 =
                                mDESuggestionPreferenceLayoutHelper.mLottieAnimationView
                                        .getLayoutParams()
                                        .width;
                        mDESuggestionPreferenceLayoutHelper
                                .mActionButtonTextView
                                .animate()
                                .alpha(0.0f)
                                .setDuration(100L)
                                .setInterpolator(new LinearInterpolator())
                                .withEndAction(
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.mde.MDESuggestionPreferenceLayoutHelper$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                MDESuggestionPreferenceLayoutHelper
                                                        mDESuggestionPreferenceLayoutHelper2 =
                                                                MDESuggestionPreferenceLayoutHelper
                                                                        .this;
                                                int i4 = i3;
                                                mDESuggestionPreferenceLayoutHelper2
                                                        .setActionButtonText(
                                                                mDESuggestionPreferenceLayoutHelper2
                                                                        .mContext.getString(
                                                                        R.string
                                                                                .sec_mde_suggestions_turned_on));
                                                mDESuggestionPreferenceLayoutHelper2
                                                        .mActionButtonTextView
                                                        .animate()
                                                        .alpha(1.0f)
                                                        .setDuration(200L)
                                                        .setInterpolator(new LinearInterpolator())
                                                        .start();
                                                TextView textView =
                                                        mDESuggestionPreferenceLayoutHelper2
                                                                .mActionButtonTextView;
                                                textView.setTranslationX(
                                                        textView.getTranslationX() - i4);
                                                mDESuggestionPreferenceLayoutHelper2
                                                        .mActionButtonTextView
                                                        .animate()
                                                        .translationX(0.0f)
                                                        .setDuration(400L)
                                                        .setInterpolator(
                                                                new PathInterpolator(
                                                                        0.22f, 0.25f, 0.0f, 1.0f))
                                                        .start();
                                                mDESuggestionPreferenceLayoutHelper2
                                                        .mLottieAnimationView.setVisibility(0);
                                                mDESuggestionPreferenceLayoutHelper2
                                                        .mLottieAnimationView.setAnimation(
                                                        "sec_suggestions_done.json");
                                                mDESuggestionPreferenceLayoutHelper2
                                                        .mLottieAnimationView.setRepeatCount(0);
                                                mDESuggestionPreferenceLayoutHelper2
                                                        .mLottieAnimationView.playAnimation$1();
                                            }
                                        })
                                .start();
                        break;
                    }
                }
                MDESuggestionManager.getInstance().mDismissedSuggestions.add(str);
                getContext()
                        .getMainThreadHandler()
                        .postDelayed(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.mde.MDESuggestionsSettings$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MDESuggestionsSettings mDESuggestionsSettings =
                                                MDESuggestionsSettings.this;
                                        Bundle bundle = m2;
                                        MDEServiceBindingManager mDEServiceBindingManager =
                                                mDESuggestionsSettings.mMDEServiceBindingManager;
                                        if (mDEServiceBindingManager != null) {
                                            mDEServiceBindingManager.sendMessage(3, bundle);
                                            mDESuggestionsSettings.mMDEServiceBindingManager
                                                    .sendMessage(1, null);
                                        }
                                    }
                                },
                                550L);
                return;
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        setAutoAddFooterPreference(false);
        setAutoRemoveInsetCategory(false);
        Log.i("MDESuggestionsSettings", "onCreate");
        super.onCreate(bundle);
        getPreferenceManager().mPreferenceComparisonCallback =
                new PreferenceManager.SimplePreferenceComparisonCallback();
        this.mMDEServiceBindingManager = new MDEServiceBindingManager(getContext());
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        setDivider(null);
        TextView textView = (TextView) onCreateView.findViewById(android.R.id.empty);
        if (textView != null) {
            textView.setText(
                    getPrefContext()
                            .getResources()
                            .getString(R.string.sec_mde_suggestions_empty_card_text));
            setEmptyView(textView);
        }
        return onCreateView;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        MDEServiceBindingManager mDEServiceBindingManager = this.mMDEServiceBindingManager;
        if (mDEServiceBindingManager != null) {
            mDEServiceBindingManager.mCallbacks.add(this.mMDEServiceBindingManagerCallback);
            this.mMDEServiceBindingManager.startServiceBind();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        MDEServiceBindingManager mDEServiceBindingManager = this.mMDEServiceBindingManager;
        if (mDEServiceBindingManager != null) {
            mDEServiceBindingManager.stopServiceBind();
            MDEServiceBindingManager mDEServiceBindingManager2 = this.mMDEServiceBindingManager;
            mDEServiceBindingManager2.mCallbacks.remove(this.mMDEServiceBindingManagerCallback);
        }
    }
}
