package com.android.settings.display;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.os.AsyncTask;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsStatsLog;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.display.DisplayDensityUtils;
import com.android.settingslib.display.DisplayDensityUtils$$ExternalSyntheticLambda0;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.FooterPreference;
import com.android.settingslib.widget.IllustrationPreference;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScreenResolutionFragment extends RadioButtonPickerFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.screen_resolution_settings);
    public AccessibilityManager mAccessibilityManager;
    public Display mDefaultDisplay;
    public DisplayObserver mDisplayObserver;
    public int mFullWidth;
    public int mHighWidth;
    public IllustrationPreference mImagePreference;
    public Set mResolutions;
    public Resources mResources;
    public String[] mScreenResolutionOptions;
    public String[] mScreenResolutionSummaries;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.display.ScreenResolutionFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return new ScreenResolutionController(context, "screen_resolution")
                    .checkSupportedResolutions();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScreenResolutionCandidateInfo extends CandidateInfo {
        public final String mKey;
        public final CharSequence mLabel;
        public final CharSequence mSummary;

        public ScreenResolutionCandidateInfo(
                CharSequence charSequence, CharSequence charSequence2, String str) {
            super(true);
            this.mLabel = charSequence;
            this.mSummary = charSequence2;
            this.mKey = str;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return this.mKey;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return null;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.mLabel;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void addStaticPreferences(PreferenceScreen preferenceScreen) {
        updateIllustrationImage(this.mImagePreference);
        preferenceScreen.addPreference(this.mImagePreference);
        FooterPreference footerPreference = new FooterPreference(preferenceScreen.getContext());
        footerPreference.setTitle(R.string.screen_resolution_footer);
        footerPreference.setSelectable(false);
        footerPreference.setLayoutResource(R.layout.preference_footer);
        preferenceScreen.addPreference(footerPreference);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void bindPreferenceExtra(
            SelectorWithWidgetPreference selectorWithWidgetPreference,
            String str,
            CandidateInfo candidateInfo,
            String str2) {
        CharSequence charSequence = ((ScreenResolutionCandidateInfo) candidateInfo).mSummary;
        if (charSequence != null) {
            selectorWithWidgetPreference.setSummary(charSequence);
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            String[] strArr = this.mScreenResolutionOptions;
            if (i >= strArr.length) {
                return arrayList;
            }
            String str = strArr[i];
            arrayList.add(
                    new ScreenResolutionCandidateInfo(
                            str, this.mScreenResolutionSummaries[i], str));
            i++;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        return getKeyForResolution(getDisplayMode().getPhysicalWidth());
    }

    public Display.Mode getDisplayMode() {
        return this.mDefaultDisplay.getMode();
    }

    public String getKeyForResolution(int i) {
        if (i == this.mHighWidth) {
            return this.mScreenResolutionOptions[0];
        }
        if (i == this.mFullWidth) {
            return this.mScreenResolutionOptions[1];
        }
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1920;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.screen_resolution_settings;
    }

    public final int getWidthForResoluitonKey(String str) {
        if (this.mScreenResolutionOptions[0].equals(str)) {
            return this.mHighWidth;
        }
        if (this.mScreenResolutionOptions[1].equals(str)) {
            return this.mFullWidth;
        }
        return -1;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mDefaultDisplay =
                ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(0);
        this.mAccessibilityManager =
                (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        Resources resources = context.getResources();
        this.mResources = resources;
        this.mScreenResolutionOptions =
                resources.getStringArray(R.array.config_screen_resolution_options_strings);
        this.mImagePreference = new IllustrationPreference(context);
        this.mDisplayObserver = new DisplayObserver(context);
        ScreenResolutionController screenResolutionController =
                new ScreenResolutionController(context, "screen_resolution");
        this.mResolutions = screenResolutionController.getAllSupportedResolutions();
        this.mHighWidth = screenResolutionController.getHighWidth();
        this.mFullWidth = screenResolutionController.getFullWidth();
        Log.i(
                "ScreenResolution",
                "mHighWidth:" + this.mHighWidth + "mFullWidth:" + this.mFullWidth);
        this.mScreenResolutionSummaries =
                new String[] {
                    this.mHighWidth + " x " + screenResolutionController.getHighHeight(),
                    this.mFullWidth + " x " + screenResolutionController.getFullHeight()
                };
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public final void onRadioButtonClicked(
            SelectorWithWidgetPreference selectorWithWidgetPreference) {
        int widthForResoluitonKey = getWidthForResoluitonKey(selectorWithWidgetPreference.getKey());
        DisplayObserver displayObserver = this.mDisplayObserver;
        int currentWidth = displayObserver.getCurrentWidth();
        if (widthForResoluitonKey == currentWidth) {
            return;
        }
        if (displayObserver.mPreviousWidth.get() == -1
                || displayObserver.isResolutionChangeApplied()) {
            displayObserver.mPreviousWidth.set(currentWidth);
            if (this.mAccessibilityManager.isEnabled()) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain();
                obtain.setEventType(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
                obtain.getText()
                        .add(this.mResources.getString(R.string.screen_resolution_selected_a11y));
                this.mAccessibilityManager.sendAccessibilityEvent(obtain);
            }
            onRadioButtonConfirmed(selectorWithWidgetPreference.getKey());
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        int widthForResoluitonKey = getWidthForResoluitonKey(str);
        if (widthForResoluitonKey < 0) {
            return false;
        }
        setDisplayMode(widthForResoluitonKey);
        updateIllustrationImage(this.mImagePreference);
        return true;
    }

    public void setDisplayMode(int i) {
        Display.Mode displayMode;
        Iterator it = this.mResolutions.iterator();
        while (true) {
            if (!it.hasNext()) {
                displayMode = getDisplayMode();
                break;
            }
            Point point = (Point) it.next();
            if (point.x == i) {
                displayMode = new Display.Mode(point.x, point.y, getDisplayMode().getRefreshRate());
                break;
            }
        }
        DisplayObserver displayObserver = this.mDisplayObserver;
        if (displayObserver.mContext != null) {
            DisplayDensityUtils displayDensityUtils =
                    new DisplayDensityUtils(displayObserver.mContext);
            int i2 = displayDensityUtils.mCurrentIndex;
            int i3 = displayDensityUtils.mDefaultDensityForDefaultDisplay;
            if (displayDensityUtils.mDefaultDisplayDensityValues[displayObserver.mCurrentIndex]
                    != i3) {
                displayObserver.mDefaultDensity = i3;
                displayObserver.mCurrentIndex = i2;
                ((DisplayManager) displayObserver.mContext.getSystemService(DisplayManager.class))
                        .registerDisplayListener(displayObserver, null);
            }
        }
        Settings.System.putString(
                getContext().getContentResolver(),
                "user_selected_resolution",
                displayMode.getPhysicalWidth() + "x" + displayMode.getPhysicalHeight());
        try {
            Log.i("ScreenResolution", "setUserPreferredDisplayMode: " + displayMode);
            this.mDefaultDisplay.setUserPreferredDisplayMode(displayMode);
            SettingsStatsLog.write(
                    572,
                    this.mDefaultDisplay.getUniqueId().hashCode(),
                    displayMode.getPhysicalWidth(),
                    displayMode.getPhysicalHeight());
        } catch (Exception e) {
            Log.e("ScreenResolution", "setUserPreferredDisplayMode() failed", e);
        }
    }

    public final void updateIllustrationImage(IllustrationPreference illustrationPreference) {
        String defaultKey = getDefaultKey();
        if (TextUtils.equals(this.mScreenResolutionOptions[0], defaultKey)) {
            illustrationPreference.setLottieAnimationResId(R.drawable.screen_resolution_high);
        } else if (TextUtils.equals(this.mScreenResolutionOptions[1], defaultKey)) {
            illustrationPreference.setLottieAnimationResId(R.drawable.screen_resolution_full);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DisplayObserver implements DisplayManager.DisplayListener {
        public final Context mContext;
        public int mCurrentIndex;
        public int mDefaultDensity;
        public final AtomicInteger mPreviousWidth = new AtomicInteger(-1);

        public DisplayObserver(Context context) {
            this.mContext = context;
        }

        public final int getCurrentWidth() {
            return ((DisplayManager) this.mContext.getSystemService(DisplayManager.class))
                    .getDisplay(0)
                    .getMode()
                    .getPhysicalWidth();
        }

        public final boolean isResolutionChangeApplied() {
            if (this.mPreviousWidth.get() == getCurrentWidth()) {
                return false;
            }
            Log.i(
                    "ScreenResolution",
                    "resolution changed from "
                            + this.mPreviousWidth.get()
                            + " to "
                            + getCurrentWidth());
            return true;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            if (i == 0
                    && new DisplayDensityUtils(this.mContext).mDefaultDensityForDefaultDisplay
                            != this.mDefaultDensity
                    && isResolutionChangeApplied()) {
                DisplayDensityUtils displayDensityUtils = new DisplayDensityUtils(this.mContext);
                int[] iArr = displayDensityUtils.mDefaultDisplayDensityValues;
                if (iArr.length <= this.mCurrentIndex) {
                    this.mCurrentIndex = displayDensityUtils.mCurrentIndex;
                }
                int i2 = this.mCurrentIndex;
                if (iArr[i2] != displayDensityUtils.mDefaultDensityForDefaultDisplay) {
                    AsyncTask.execute(
                            new DisplayDensityUtils$$ExternalSyntheticLambda0(
                                    displayDensityUtils, i2, UserHandle.myUserId()));
                }
                this.mDefaultDensity = displayDensityUtils.mDefaultDensityForDefaultDisplay;
                Context context = this.mContext;
                if (context == null) {
                    return;
                }
                ((DisplayManager) context.getSystemService(DisplayManager.class))
                        .unregisterDisplayListener(this);
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {}

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {}
    }
}
