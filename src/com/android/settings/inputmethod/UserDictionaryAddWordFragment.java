package com.android.settings.inputmethod;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UserDictionaryAddWordFragment extends InstrumentedFragment {
    public UserDictionaryAddWordContents mContents;
    public boolean mIsDeleting = false;
    public View mRootView;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 62;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(0, 1, 0, R.string.delete)
                .setIcon(R.drawable.sec_tw_ic_bb_delete_mtrl)
                .setShowAsAction(5);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(
                        R.layout.user_dictionary_add_word_fullscreen, (ViewGroup) null);
        this.mRootView = inflate;
        this.mIsDeleting = false;
        UserDictionaryAddWordContents userDictionaryAddWordContents = this.mContents;
        if (userDictionaryAddWordContents == null) {
            this.mContents = new UserDictionaryAddWordContents(inflate, getArguments());
        } else {
            this.mContents =
                    new UserDictionaryAddWordContents(inflate, userDictionaryAddWordContents);
        }
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setSubtitle(
                    UserDictionarySettingsUtils.getLocaleDisplayName(
                            getActivity(), this.mContents.mLocale));
        }
        return this.mRootView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 1) {
            return false;
        }
        UserDictionaryAddWordContents userDictionaryAddWordContents = this.mContents;
        FragmentActivity activity = getActivity();
        if (userDictionaryAddWordContents.mMode == 0) {
            String str = userDictionaryAddWordContents.mOldWord;
            if (!TextUtils.isEmpty(str)) {
                UserDictionarySettings.deleteWord(
                        activity.getContentResolver(),
                        str,
                        userDictionaryAddWordContents.mOldShortcut);
            }
        }
        this.mIsDeleting = true;
        getActivity().onBackPressed();
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mIsDeleting) {
            return;
        }
        this.mContents.apply(getActivity(), null);
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        UserDictionaryAddWordContents userDictionaryAddWordContents = this.mContents;
        FragmentActivity activity = getActivity();
        userDictionaryAddWordContents.getClass();
        TreeSet<String> userDictionaryLocalesSet =
                UserDictionaryListPreferenceController.getUserDictionaryLocalesSet(activity);
        userDictionaryLocalesSet.remove(userDictionaryAddWordContents.mLocale);
        String locale = Locale.getDefault().toString();
        userDictionaryLocalesSet.remove(locale);
        userDictionaryLocalesSet.remove(ApnSettings.MVNO_NONE);
        ArrayList arrayList = new ArrayList();
        String str = userDictionaryAddWordContents.mLocale;
        if (str != null) {
            arrayList.add(new UserDictionaryAddWordContents.LocaleRenderer(activity, str));
        }
        if (!locale.equals(userDictionaryAddWordContents.mLocale)) {
            arrayList.add(new UserDictionaryAddWordContents.LocaleRenderer(activity, locale));
        }
        Iterator<String> it = userDictionaryLocalesSet.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (next != null) {
                arrayList.add(new UserDictionaryAddWordContents.LocaleRenderer(activity, next));
            }
        }
        if (!ApnSettings.MVNO_NONE.equals(userDictionaryAddWordContents.mLocale)) {
            arrayList.add(
                    new UserDictionaryAddWordContents.LocaleRenderer(
                            activity, ApnSettings.MVNO_NONE));
        }
        arrayList.add(new UserDictionaryAddWordContents.LocaleRenderer(activity, null));
        new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, arrayList)
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
