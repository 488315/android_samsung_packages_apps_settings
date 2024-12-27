package com.android.settings.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.applications.defaultapps.DefaultAutofillPicker;
import com.android.settings.core.PreferenceXmlParserUtils;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.IllustrationPreference;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RadioButtonPickerFragment extends SettingsPreferenceFragment
        implements SelectorWithWidgetPreference.OnClickListener {
    static final String EXTRA_FOR_WORK = "for_work";
    protected boolean mAppendStaticPreferences = false;
    public final Map mCandidates = new ArrayMap();
    public int mIllustrationId;
    public IllustrationType mIllustrationType;
    public int mUserId;
    public UserManager mUserManager;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class IllustrationType {
        public static final /* synthetic */ IllustrationType[] $VALUES;
        public static final IllustrationType LOTTIE_ANIMATION;

        static {
            IllustrationType illustrationType = new IllustrationType("LOTTIE_ANIMATION", 0);
            LOTTIE_ANIMATION = illustrationType;
            $VALUES = new IllustrationType[] {illustrationType};
        }

        public static IllustrationType valueOf(String str) {
            return (IllustrationType) Enum.valueOf(IllustrationType.class, str);
        }

        public static IllustrationType[] values() {
            return (IllustrationType[]) $VALUES.clone();
        }
    }

    public final void addIllustration(PreferenceScreen preferenceScreen) {
        if (this.mIllustrationType.ordinal() != 0) {
            throw new IllegalArgumentException(
                    "Invalid illustration type: " + this.mIllustrationType);
        }
        IllustrationPreference illustrationPreference = new IllustrationPreference(getContext());
        illustrationPreference.setLottieAnimationResId(this.mIllustrationId);
        preferenceScreen.addPreference(illustrationPreference);
    }

    public void bindPreference(
            SelectorWithWidgetPreference selectorWithWidgetPreference,
            String str,
            CandidateInfo candidateInfo,
            String str2) {
        selectorWithWidgetPreference.setTitle(candidateInfo.loadLabel());
        selectorWithWidgetPreference.setIcon(Utils.getSafeIcon(candidateInfo.loadIcon()));
        selectorWithWidgetPreference.setKey(str);
        if (TextUtils.equals(str2, str)) {
            selectorWithWidgetPreference.setChecked(true);
        }
        selectorWithWidgetPreference.setEnabled(candidateInfo.enabled);
        selectorWithWidgetPreference.mListener = this;
    }

    public SelectorWithWidgetPreference createPreference() {
        return new SelectorWithWidgetPreference(getPrefContext());
    }

    public abstract List getCandidates();

    public abstract String getDefaultKey();

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public abstract int getPreferenceScreenResId();

    public int getRadioButtonPreferenceCustomLayoutResId() {
        return 0;
    }

    public final void mayCheckOnlyRadioButton() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen == null || preferenceScreen.getPreferenceCount() != 1) {
            return;
        }
        Preference preference = preferenceScreen.getPreference(0);
        if (preference instanceof SelectorWithWidgetPreference) {
            ((SelectorWithWidgetPreference) preference).setChecked(true);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mUserManager = (UserManager) context.getSystemService("user");
        Bundle arguments = getArguments();
        boolean z = arguments != null ? arguments.getBoolean(EXTRA_FOR_WORK) : false;
        UserHandle managedProfile = Utils.getManagedProfile(this.mUserManager);
        this.mUserId =
                (!z || managedProfile == null)
                        ? UserHandle.myUserId()
                        : managedProfile.getIdentifier();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        try {
            this.mAppendStaticPreferences =
                    ((Bundle)
                                    ((ArrayList)
                                                    PreferenceXmlParserUtils.extractMetadata(
                                                            getContext(),
                                                            getPreferenceScreenResId(),
                                                            1025))
                                            .get(0))
                            .getBoolean("staticPreferenceLocation");
        } catch (IOException e) {
            Log.e("RadioButtonPckrFrgmt", "Error trying to open xml file", e);
        } catch (XmlPullParserException e2) {
            Log.e("RadioButtonPckrFrgmt", "Error parsing xml", e2);
        }
        updateCandidates();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        setHasOptionsMenu(true);
        return onCreateView;
    }

    public void onRadioButtonClicked(SelectorWithWidgetPreference selectorWithWidgetPreference) {
        onRadioButtonConfirmed(selectorWithWidgetPreference.getKey());
    }

    public void onRadioButtonConfirmed(String str) {
        boolean defaultKey = setDefaultKey(str);
        if (defaultKey) {
            updateCheckedState$1(str);
        }
        onSelectionPerformed(defaultKey);
    }

    public abstract boolean setDefaultKey(String str);

    public boolean shouldShowItemNone() {
        return this instanceof DefaultAutofillPicker;
    }

    public void updateCandidates() {
        ((ArrayMap) this.mCandidates).clear();
        List<CandidateInfo> candidates = getCandidates();
        if (candidates != null) {
            for (CandidateInfo candidateInfo : candidates) {
                ((ArrayMap) this.mCandidates).put(candidateInfo.getKey(), candidateInfo);
            }
        }
        String defaultKey = getDefaultKey();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        if (this.mIllustrationId != 0) {
            addIllustration(preferenceScreen);
        }
        if (!this.mAppendStaticPreferences) {
            addStaticPreferences(preferenceScreen);
        }
        int radioButtonPreferenceCustomLayoutResId = getRadioButtonPreferenceCustomLayoutResId();
        if (shouldShowItemNone()) {
            SelectorWithWidgetPreference selectorWithWidgetPreference =
                    new SelectorWithWidgetPreference(getPrefContext());
            if (radioButtonPreferenceCustomLayoutResId > 0) {
                selectorWithWidgetPreference.setLayoutResource(
                        radioButtonPreferenceCustomLayoutResId);
            }
            selectorWithWidgetPreference.setTitle(R.string.app_list_preference_none);
            selectorWithWidgetPreference.setChecked(TextUtils.isEmpty(defaultKey));
            selectorWithWidgetPreference.mListener = this;
            preferenceScreen.addPreference(selectorWithWidgetPreference);
        }
        if (candidates != null) {
            for (CandidateInfo candidateInfo2 : candidates) {
                SelectorWithWidgetPreference createPreference = createPreference();
                if (radioButtonPreferenceCustomLayoutResId > 0) {
                    createPreference.setLayoutResource(radioButtonPreferenceCustomLayoutResId);
                }
                bindPreference(
                        createPreference, candidateInfo2.getKey(), candidateInfo2, defaultKey);
                bindPreferenceExtra(
                        createPreference, candidateInfo2.getKey(), candidateInfo2, defaultKey);
                preferenceScreen.addPreference(createPreference);
            }
        }
        mayCheckOnlyRadioButton();
        if (this.mAppendStaticPreferences) {
            addStaticPreferences(preferenceScreen);
        }
    }

    public final void updateCheckedState$1(String str) {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            int preferenceCount = preferenceScreen.getPreferenceCount();
            for (int i = 0; i < preferenceCount; i++) {
                Preference preference = preferenceScreen.getPreference(i);
                if (preference instanceof SelectorWithWidgetPreference) {
                    SelectorWithWidgetPreference selectorWithWidgetPreference =
                            (SelectorWithWidgetPreference) preference;
                    if (selectorWithWidgetPreference.mChecked
                            != TextUtils.equals(preference.getKey(), str)) {
                        selectorWithWidgetPreference.setChecked(
                                TextUtils.equals(preference.getKey(), str));
                    }
                }
            }
        }
    }

    public void addStaticPreferences(PreferenceScreen preferenceScreen) {}

    public void onSelectionPerformed(boolean z) {}

    public void bindPreferenceExtra(
            SelectorWithWidgetPreference selectorWithWidgetPreference,
            String str,
            CandidateInfo candidateInfo,
            String str2) {}
}
