package com.android.settings.inputmethod;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.Instrumentable;
import com.android.settingslib.core.instrumentation.VisibilityLoggerMixin;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UserDictionarySettings extends ListFragment
        implements Instrumentable, LoaderManager.LoaderCallbacks {
    public Cursor mCursor;
    public String mLocale;
    public VisibilityLoggerMixin mVisibilityLoggerMixin;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MyAdapter extends SimpleCursorAdapter implements SectionIndexer {
        public final AlphabetIndexer mIndexer;
        public final AnonymousClass1 mViewBinder;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.inputmethod.UserDictionarySettings$MyAdapter$1, reason: invalid class name */
        public final class AnonymousClass1 implements SimpleCursorAdapter.ViewBinder {
            @Override // android.widget.SimpleCursorAdapter.ViewBinder
            public final boolean setViewValue(View view, Cursor cursor, int i) {
                if (i != 2) {
                    return false;
                }
                String string = cursor.getString(2);
                if (TextUtils.isEmpty(string)) {
                    view.setVisibility(8);
                } else {
                    ((TextView) view).setText(string);
                    view.setVisibility(0);
                }
                view.invalidate();
                return true;
            }
        }

        public MyAdapter(
                FragmentActivity fragmentActivity, Cursor cursor, String[] strArr, int[] iArr) {
            super(fragmentActivity, R.layout.user_dictionary_item, cursor, strArr, iArr);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1();
            if (cursor != null) {
                this.mIndexer =
                        new AlphabetIndexer(
                                cursor,
                                cursor.getColumnIndexOrThrow("word"),
                                fragmentActivity.getString(
                                        android.R.string.lockscreen_glogin_account_recovery_hint));
            }
            setViewBinder(anonymousClass1);
        }

        @Override // android.widget.SectionIndexer
        public final int getPositionForSection(int i) {
            AlphabetIndexer alphabetIndexer = this.mIndexer;
            if (alphabetIndexer == null) {
                return 0;
            }
            return alphabetIndexer.getPositionForSection(i);
        }

        @Override // android.widget.SectionIndexer
        public final int getSectionForPosition(int i) {
            AlphabetIndexer alphabetIndexer = this.mIndexer;
            if (alphabetIndexer == null) {
                return 0;
            }
            return alphabetIndexer.getSectionForPosition(i);
        }

        @Override // android.widget.SectionIndexer
        public final Object[] getSections() {
            AlphabetIndexer alphabetIndexer = this.mIndexer;
            if (alphabetIndexer == null) {
                return null;
            }
            return alphabetIndexer.getSections();
        }
    }

    public static void deleteWord(ContentResolver contentResolver, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            contentResolver.delete(
                    UserDictionary.Words.CONTENT_URI,
                    "word=? AND shortcut is null OR shortcut=''",
                    new String[] {str});
        } else {
            contentResolver.delete(
                    UserDictionary.Words.CONTENT_URI,
                    "word=? AND shortcut=?",
                    new String[] {str, str2});
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.SFF;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mVisibilityLoggerMixin =
                new VisibilityLoggerMixin(
                        FileType.SFF, featureFactoryImpl.getMetricsFeatureProvider());
        Intent intent = getActivity().getIntent();
        String stringExtra =
                intent == null ? null : intent.getStringExtra(SpeechRecognitionConst.Key.LOCALE);
        Bundle arguments = getArguments();
        String string =
                arguments == null ? null : arguments.getString(SpeechRecognitionConst.Key.LOCALE);
        if (string != null) {
            stringExtra = string;
        } else if (stringExtra == null) {
            stringExtra = null;
        }
        this.mLocale = stringExtra;
        setHasOptionsMenu(true);
        getLoaderManager().initLoader(1, null, this);
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final Loader onCreateLoader(int i, Bundle bundle) {
        return new UserDictionaryCursorLoader(getContext(), this.mLocale);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(0, 1, 0, R.string.user_dict_settings_add_menu_title)
                .setIcon(R.drawable.ic_add_24dp)
                .setShowAsAction(5);
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getActivity().setTitle(R.string.user_dict_settings_title);
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.user_dict_settings_title);
            actionBar.setSubtitle(
                    UserDictionarySettingsUtils.getLocaleDisplayName(getActivity(), this.mLocale));
        }
        return layoutInflater.inflate(android.R.layout.screen_action_bar, viewGroup, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0024  */
    @Override // androidx.fragment.app.ListFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onListItemClick(android.view.View r3, int r4) {
        /*
            r2 = this;
            android.database.Cursor r3 = r2.mCursor
            r0 = 0
            if (r3 != 0) goto L7
        L5:
            r3 = r0
            goto L1f
        L7:
            r3.moveToPosition(r4)
            android.database.Cursor r3 = r2.mCursor
            boolean r3 = r3.isAfterLast()
            if (r3 == 0) goto L13
            goto L5
        L13:
            android.database.Cursor r3 = r2.mCursor
            java.lang.String r1 = "word"
            int r1 = r3.getColumnIndexOrThrow(r1)
            java.lang.String r3 = r3.getString(r1)
        L1f:
            android.database.Cursor r1 = r2.mCursor
            if (r1 != 0) goto L24
            goto L3c
        L24:
            r1.moveToPosition(r4)
            android.database.Cursor r4 = r2.mCursor
            boolean r4 = r4.isAfterLast()
            if (r4 == 0) goto L30
            goto L3c
        L30:
            android.database.Cursor r4 = r2.mCursor
            java.lang.String r0 = "shortcut"
            int r0 = r4.getColumnIndexOrThrow(r0)
            java.lang.String r0 = r4.getString(r0)
        L3c:
            if (r3 == 0) goto L41
            r2.showAddOrEditDialog(r3, r0)
        L41:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.inputmethod.UserDictionarySettings.onListItemClick(android.view.View,"
                    + " int):void");
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoadFinished(Loader loader, Object obj) {
        this.mCursor = (Cursor) obj;
        ensureList();
        this.mList.setAdapter(
                (ListAdapter)
                        new MyAdapter(
                                getActivity(),
                                this.mCursor,
                                new String[] {"word", "shortcut"},
                                new int[] {android.R.id.text1, android.R.id.text2}));
    }

    @Override // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 1) {
            return false;
        }
        showAddOrEditDialog(null, null);
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mVisibilityLoggerMixin.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mVisibilityLoggerMixin.onResume();
        getLoaderManager().restartLoader(1, null, this);
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        ensureList();
        TextView textView = (TextView) getView().findViewById(android.R.id.empty);
        textView.setText(R.string.user_dict_settings_empty_text);
        ensureList();
        ListView listView = this.mList;
        listView.setFastScrollEnabled(true);
        listView.setEmptyView(textView);
    }

    public final void showAddOrEditDialog(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putInt("mode", str == null ? 1 : 0);
        bundle.putString("word", str);
        bundle.putString("shortcut", str2);
        bundle.putString(SpeechRecognitionConst.Key.LOCALE, this.mLocale);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String name = UserDictionaryAddWordFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.user_dict_settings_add_dialog_title, null);
        launchRequest.mSourceMetricsCategory = FileType.SFF;
        subSettingLauncher.launch();
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader loader) {}
}
