package com.samsung.android.settings.accessories;

import android.content.Context;
import android.os.Bundle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecDividerItemDecorator;
import com.samsung.android.settings.widget.SecRadioButtonPreference;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CoverScreenOrientation extends SettingsPreferenceFragment
        implements SecRadioButtonPreference.OnClickListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public SecRadioButtonPreference mBottomToTopButton;
    public LayoutPreference mCoverScreenOrientationPreviewPreference;
    public LinearLayout mImageContainer;
    public int mModeState;
    public ImageView mPreviewImageView;
    public SecRadioButtonPreference mTopToBottomButton;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessories.CoverScreenOrientation$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_cover_screen_orientation_settings;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return AccessoriesUtils.hasCoverSettingCoverOrientation(context);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1054;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_cover_screen_orientation_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mImageContainer =
                (LinearLayout)
                        this.mCoverScreenOrientationPreviewPreference.mRootView.findViewById(
                                R.id.preview_image_container);
        this.mPreviewImageView =
                (ImageView)
                        this.mCoverScreenOrientationPreviewPreference.mRootView.findViewById(
                                R.id.preview_image);
        this.mImageContainer.semSetRoundedCorners(15);
        this.mImageContainer.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.i("CoverScreenOrientation", "onCreate");
        super.onCreate(bundle);
        setAutoRemoveInsetCategory(false);
        this.mCoverScreenOrientationPreviewPreference =
                (LayoutPreference) findPreference("cover_screen_orientation_preview");
        this.mTopToBottomButton = (SecRadioButtonPreference) findPreference("top_to_bottom_button");
        SecRadioButtonPreference secRadioButtonPreference =
                (SecRadioButtonPreference) findPreference("bottom_to_top_button");
        this.mBottomToTopButton = secRadioButtonPreference;
        this.mTopToBottomButton.mListener = this;
        secRadioButtonPreference.mListener = this;
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        updateRadioButtons(secRadioButtonPreference);
        LoggingHelper.insertEventLogging(
                1054,
                1055,
                secRadioButtonPreference.equals(this.mTopToBottomButton)
                        ? "1000"
                        : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        int i = Settings.System.getInt(getContentResolver(), "cover_text_direction", 0);
        this.mModeState = i;
        if (i == 0) {
            updateRadioButtons(this.mTopToBottomButton);
        } else {
            updateRadioButtons(this.mBottomToTopButton);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecDividerItemDecorator(
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_end)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_size)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_start)),
                                getContext(),
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
    }

    public final void updateRadioButtons(SecRadioButtonPreference secRadioButtonPreference) {
        SecRadioButtonPreference secRadioButtonPreference2 = this.mTopToBottomButton;
        if (secRadioButtonPreference == secRadioButtonPreference2) {
            secRadioButtonPreference2.setChecked(true);
            this.mBottomToTopButton.setChecked(false);
            Settings.System.putInt(getContentResolver(), "cover_text_direction", 0);
            this.mModeState = 0;
        } else {
            SecRadioButtonPreference secRadioButtonPreference3 = this.mBottomToTopButton;
            if (secRadioButtonPreference == secRadioButtonPreference3) {
                secRadioButtonPreference3.setChecked(true);
                this.mTopToBottomButton.setChecked(false);
                Settings.System.putInt(getContentResolver(), "cover_text_direction", 1);
                this.mModeState = 1;
            }
        }
        int i = this.mModeState;
        int i2 = R.drawable.sec_help_top_to_bottom;
        if (i != 0 && i == 1) {
            i2 = R.drawable.sec_help_bottom_to_top;
        }
        this.mPreviewImageView.setImageResource(i2);
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("onCheckedChanged mModeState = "),
                this.mModeState,
                "CoverScreenOrientation");
    }
}
