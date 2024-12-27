package com.android.settings.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroupAdapter;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.survey.SurveyMixin;
import com.android.settingslib.core.instrumentation.Instrumentable;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.instrumentation.SettingsJankMonitor;
import com.android.settingslib.core.instrumentation.VisibilityLoggerMixin;
import com.android.settingslib.core.lifecycle.ObservablePreferenceFragment;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.sdk.bixby2.Sbixby;
import com.samsung.android.sdk.bixby2.state.StateHandler;
import com.samsung.android.settings.bixby.AppContext;
import com.samsung.android.settings.bixby.AppContextHelper;
import com.samsung.android.settings.logging.LoggingHelper;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class InstrumentedPreferenceFragment extends ObservablePreferenceFragment
        implements Instrumentable {
    private static final String TAG = "InstrumentedPrefFrag";
    protected final int PLACEHOLDER_METRIC = EnterpriseContainerConstants.SYSTEM_SIGNED_APP;
    private final StateHandler.Callback mAppContextCallback = new AnonymousClass1();
    private boolean mAutoFlowLoggingDisabled;
    protected MetricsFeatureProvider mMetricsFeatureProvider;
    private RecyclerView.OnScrollListener mOnScrollListener;
    private VisibilityLoggerMixin mVisibilityLoggerMixin;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.core.InstrumentedPreferenceFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends StateHandler.Callback {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OnScrollListener extends RecyclerView.OnScrollListener {
        public final String mClassName;
        public final InteractionJankMonitor mMonitor = InteractionJankMonitor.getInstance();

        public OnScrollListener(String str) {
            this.mClassName = str;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public final void onScrollStateChanged(int i, RecyclerView recyclerView) {
            if (i == 0) {
                this.mMonitor.end(28);
            } else {
                if (i != 1) {
                    return;
                }
                this.mMonitor.begin(
                        InteractionJankMonitor.Configuration.Builder.withView(28, recyclerView)
                                .setTag(this.mClassName));
            }
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void addPreferencesFromResource(int i) {
        super.addPreferencesFromResource(i);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            CharSequence title = preferenceScreen.getTitle();
            if (TextUtils.isEmpty(title)) {
                Log.w(TAG, "Screen title missing for fragment ".concat(getClass().getName()));
            } else {
                getActivity().setTitle(title);
            }
        }
    }

    public void enableAutoFlowLogging(boolean z) {
        this.mAutoFlowLoggingDisabled = !z;
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public <T extends Preference> T findPreference(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return (T) super.findPreference(charSequence);
    }

    public AppContext getAppContext() {
        return null;
    }

    public String getFragmentTitle(Context context) {
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0084, code lost:

       if (0 == 0) goto L39;
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0086, code lost:

       r2.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a5, code lost:

       return 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a2, code lost:

       if (0 == 0) goto L39;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getFragmentTitleResId(android.content.Context r5) {
        /*
            r4 = this;
            java.lang.String r0 = "InstrumentedPrefFrag"
            r1 = -1
            int r4 = r4.getPreferenceScreenResId()     // Catch: java.lang.Exception -> L8
            goto Le
        L8:
            java.lang.String r4 = "can not get preference screen id without context"
            android.util.Log.w(r0, r4)
            r4 = r1
        Le:
            if (r4 >= 0) goto L11
            goto L12
        L11:
            r1 = r4
        L12:
            r4 = 0
            if (r1 >= 0) goto L16
            return r4
        L16:
            r2 = 0
            android.content.res.Resources r3 = r5.getResources()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            android.content.res.XmlResourceParser r2 = r3.getXml(r1)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
        L1f:
            int r1 = r2.next()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            r3 = 1
            if (r1 == r3) goto L2a
            r3 = 2
            if (r1 == r3) goto L2a
            goto L1f
        L2a:
            java.lang.String r1 = r2.getName()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            java.lang.String r3 = "PreferenceScreen"
            boolean r3 = r3.equals(r1)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            if (r3 != 0) goto L60
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            r5.<init>()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            java.lang.String r3 = "XML document must start with <PreferenceScreen> tag; found"
            r5.append(r3)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            r5.append(r1)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            java.lang.String r1 = " at "
            r5.append(r1)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            java.lang.String r1 = r2.getPositionDescription()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            r5.append(r1)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            android.util.Log.e(r0, r5)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e
            r2.close()
            return r4
        L5a:
            r4 = move-exception
            goto La6
        L5c:
            r5 = move-exception
            goto L6c
        L5e:
            r5 = move-exception
            goto L8a
        L60:
            android.util.AttributeSet r1 = android.util.Xml.asAttributeSet(r2)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e java.lang.Throwable -> L5e java.lang.Throwable -> L5e
            int r4 = com.android.settings.core.PreferenceXmlParserUtils.getTitleResId(r5, r1)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c java.lang.Throwable -> L5e java.lang.Throwable -> L5e java.lang.Throwable -> L5e
            r2.close()
            return r4
        L6c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5a
            r1.<init>()     // Catch: java.lang.Throwable -> L5a
            java.lang.String r3 = "Action data convert failed (xml) : "
            r1.append(r3)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r5 = r5.getMessage()     // Catch: java.lang.Throwable -> L5a
            r1.append(r5)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r5 = r1.toString()     // Catch: java.lang.Throwable -> L5a
            android.util.Log.w(r0, r5)     // Catch: java.lang.Throwable -> L5a
            if (r2 == 0) goto La5
        L86:
            r2.close()
            goto La5
        L8a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5a
            r1.<init>()     // Catch: java.lang.Throwable -> L5a
            java.lang.String r3 = "Error parsing PreferenceScreen: "
            r1.append(r3)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r5 = r5.getMessage()     // Catch: java.lang.Throwable -> L5a
            r1.append(r5)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r5 = r1.toString()     // Catch: java.lang.Throwable -> L5a
            android.util.Log.w(r0, r5)     // Catch: java.lang.Throwable -> L5a
            if (r2 == 0) goto La5
            goto L86
        La5:
            return r4
        La6:
            if (r2 == 0) goto Lab
            r2.close()
        Lab:
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.core.InstrumentedPreferenceFragment.getFragmentTitleResId(android.content.Context):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0099, code lost:

       if (r1 == null) goto L44;
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x009b, code lost:

       r1.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00ba, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b7, code lost:

       if (r1 == null) goto L44;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getHierarchicalParentFragment(android.content.Context r6) {
        /*
            r5 = this;
            java.lang.String r0 = "InstrumentedPrefFrag"
            r1 = -1
            int r5 = r5.getPreferenceScreenResId()     // Catch: java.lang.Exception -> L8
            goto Le
        L8:
            java.lang.String r5 = "can not get preference screen id without context"
            android.util.Log.w(r0, r5)
            r5 = r1
        Le:
            if (r5 >= 0) goto L11
            goto L12
        L11:
            r1 = r5
        L12:
            r5 = 0
            if (r1 >= 0) goto L16
            return r5
        L16:
            android.content.res.Resources r2 = r6.getResources()     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L7b java.lang.Throwable -> L7e
            android.content.res.XmlResourceParser r1 = r2.getXml(r1)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L7b java.lang.Throwable -> L7e
        L1e:
            int r2 = r1.next()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            r3 = 1
            if (r2 == r3) goto L29
            r3 = 2
            if (r2 == r3) goto L29
            goto L1e
        L29:
            java.lang.String r2 = r1.getName()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            java.lang.String r3 = "PreferenceScreen"
            boolean r3 = r3.equals(r2)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            if (r3 != 0) goto L5f
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            r6.<init>()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            java.lang.String r3 = "XML document must start with <PreferenceScreen> tag; found"
            r6.append(r3)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            r6.append(r2)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            java.lang.String r2 = " at "
            r6.append(r2)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            java.lang.String r2 = r1.getPositionDescription()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            r6.append(r2)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            android.util.Log.e(r0, r6)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            r1.close()
            return r5
        L59:
            r5 = move-exception
            goto Lbb
        L5b:
            r6 = move-exception
            goto L81
        L5d:
            r6 = move-exception
            goto L9f
        L5f:
            android.util.AttributeSet r2 = android.util.Xml.asAttributeSet(r1)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            int[] r3 = com.android.settings.R$styleable.PreferenceScreen     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            java.util.List r4 = com.android.settings.core.PreferenceXmlParserUtils.SUPPORTED_PREF_TYPES     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            android.content.res.TypedArray r6 = r6.obtainStyledAttributes(r2, r3)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            r2 = 0
            java.lang.String r2 = r6.getString(r2)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            r6.recycle()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            r1.close()
            return r2
        L77:
            r6 = move-exception
            r1 = r5
            r5 = r6
            goto Lbb
        L7b:
            r6 = move-exception
            r1 = r5
            goto L81
        L7e:
            r6 = move-exception
            r1 = r5
            goto L9f
        L81:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L59
            r2.<init>()     // Catch: java.lang.Throwable -> L59
            java.lang.String r3 = "Action data convert failed (xml) : "
            r2.append(r3)     // Catch: java.lang.Throwable -> L59
            java.lang.String r6 = r6.getMessage()     // Catch: java.lang.Throwable -> L59
            r2.append(r6)     // Catch: java.lang.Throwable -> L59
            java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> L59
            android.util.Log.w(r0, r6)     // Catch: java.lang.Throwable -> L59
            if (r1 == 0) goto Lba
        L9b:
            r1.close()
            goto Lba
        L9f:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L59
            r2.<init>()     // Catch: java.lang.Throwable -> L59
            java.lang.String r3 = "Error parsing PreferenceScreen: "
            r2.append(r3)     // Catch: java.lang.Throwable -> L59
            java.lang.String r6 = r6.getMessage()     // Catch: java.lang.Throwable -> L59
            r2.append(r6)     // Catch: java.lang.Throwable -> L59
            java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> L59
            android.util.Log.w(r0, r6)     // Catch: java.lang.Throwable -> L59
            if (r1 == 0) goto Lba
            goto L9b
        Lba:
            return r5
        Lbb:
            if (r1 == 0) goto Lc0
            r1.close()
        Lc0:
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.core.InstrumentedPreferenceFragment.getHierarchicalParentFragment(android.content.Context):java.lang.String");
    }

    public final Context getPrefContext() {
        return getPreferenceManager().mContext;
    }

    public int getPreferenceScreenResId() {
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0098, code lost:

       if (r1 == null) goto L43;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x009a, code lost:

       r1.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b9, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00b6, code lost:

       if (r1 == null) goto L43;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getTopLevelPreferenceKey(android.content.Context r7) {
        /*
            r6 = this;
            java.lang.String r0 = "InstrumentedPrefFrag"
            r1 = -1
            int r6 = r6.getPreferenceScreenResId()     // Catch: java.lang.Exception -> L8
            goto Le
        L8:
            java.lang.String r6 = "can not get preference screen id without context"
            android.util.Log.w(r0, r6)
            r6 = r1
        Le:
            if (r6 >= 0) goto L11
            goto L12
        L11:
            r1 = r6
        L12:
            r6 = 0
            if (r1 >= 0) goto L16
            return r6
        L16:
            android.content.res.Resources r2 = r7.getResources()     // Catch: java.lang.Throwable -> L76 java.lang.Exception -> L7a java.lang.Throwable -> L7d
            android.content.res.XmlResourceParser r1 = r2.getXml(r1)     // Catch: java.lang.Throwable -> L76 java.lang.Exception -> L7a java.lang.Throwable -> L7d
        L1e:
            int r2 = r1.next()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            r3 = 1
            r4 = 2
            if (r2 == r3) goto L29
            if (r2 == r4) goto L29
            goto L1e
        L29:
            java.lang.String r2 = r1.getName()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            java.lang.String r3 = "PreferenceScreen"
            boolean r3 = r3.equals(r2)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            if (r3 != 0) goto L5f
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            r7.<init>()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            java.lang.String r3 = "XML document must start with <PreferenceScreen> tag; found"
            r7.append(r3)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            r7.append(r2)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            java.lang.String r2 = " at "
            r7.append(r2)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            java.lang.String r2 = r1.getPositionDescription()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            r7.append(r2)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            android.util.Log.e(r0, r7)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d
            r1.close()
            return r6
        L59:
            r6 = move-exception
            goto Lba
        L5b:
            r7 = move-exception
            goto L80
        L5d:
            r7 = move-exception
            goto L9e
        L5f:
            android.util.AttributeSet r2 = android.util.Xml.asAttributeSet(r1)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            int[] r3 = com.android.settings.R$styleable.PreferenceScreen     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            java.util.List r5 = com.android.settings.core.PreferenceXmlParserUtils.SUPPORTED_PREF_TYPES     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            android.content.res.TypedArray r7 = r7.obtainStyledAttributes(r2, r3)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            java.lang.String r2 = r7.getString(r4)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            r7.recycle()     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b java.lang.Throwable -> L5d java.lang.Throwable -> L5d java.lang.Throwable -> L5d
            r1.close()
            return r2
        L76:
            r7 = move-exception
            r1 = r6
            r6 = r7
            goto Lba
        L7a:
            r7 = move-exception
            r1 = r6
            goto L80
        L7d:
            r7 = move-exception
            r1 = r6
            goto L9e
        L80:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L59
            r2.<init>()     // Catch: java.lang.Throwable -> L59
            java.lang.String r3 = "Action data convert failed (xml) : "
            r2.append(r3)     // Catch: java.lang.Throwable -> L59
            java.lang.String r7 = r7.getMessage()     // Catch: java.lang.Throwable -> L59
            r2.append(r7)     // Catch: java.lang.Throwable -> L59
            java.lang.String r7 = r2.toString()     // Catch: java.lang.Throwable -> L59
            android.util.Log.w(r0, r7)     // Catch: java.lang.Throwable -> L59
            if (r1 == 0) goto Lb9
        L9a:
            r1.close()
            goto Lb9
        L9e:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L59
            r2.<init>()     // Catch: java.lang.Throwable -> L59
            java.lang.String r3 = "Error parsing PreferenceScreen: "
            r2.append(r3)     // Catch: java.lang.Throwable -> L59
            java.lang.String r7 = r7.getMessage()     // Catch: java.lang.Throwable -> L59
            r2.append(r7)     // Catch: java.lang.Throwable -> L59
            java.lang.String r7 = r2.toString()     // Catch: java.lang.Throwable -> L59
            android.util.Log.w(r0, r7)     // Catch: java.lang.Throwable -> L59
            if (r1 == 0) goto Lb9
            goto L9a
        Lb9:
            return r6
        Lba:
            if (r1 == 0) goto Lbf
            r1.close()
        Lbf:
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.core.InstrumentedPreferenceFragment.getTopLevelPreferenceKey(android.content.Context):java.lang.String");
    }

    public boolean isHomeButtonEnabled(Context context) {
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mVisibilityLoggerMixin =
                new VisibilityLoggerMixin(getMetricsCategory(), this.mMetricsFeatureProvider);
        getSettingsLifecycle().addObserver(this.mVisibilityLoggerMixin);
        getSettingsLifecycle().addObserver(new SurveyMixin(this, getClass().getSimpleName()));
        super.onAttach(context);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.mAutoFlowLoggingDisabled) {
            return;
        }
        LoggingHelper.insertFlowLogging(getMetricsCategory());
        LoggingHelper.insertEntranceLogging(getMetricsCategory());
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        int preferenceScreenResId = getPreferenceScreenResId();
        if (preferenceScreenResId > 0) {
            addPreferencesFromResource(preferenceScreenResId);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onPause() {
        RecyclerView listView = getListView();
        RecyclerView.OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            listView.removeOnScrollListener(onScrollListener);
            this.mOnScrollListener = null;
        }
        AppContextHelper appContextHelper = AppContextHelper.LazyHolder.sAppContextHelper;
        String simpleName = getClass().getSimpleName();
        Sbixby.getStateHandler().mCallback = null;
        appContextHelper.mCurrentClassName = null;
        Log.i("AppContextHelper", "unRegisterStateHandlerCallback() by ".concat(simpleName));
        super.onPause();
    }

    @Override // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public boolean onPreferenceTreeClick(Preference preference) {
        writePreferenceClickMetric(preference);
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        Intent intent;
        VisibilityLoggerMixin visibilityLoggerMixin = this.mVisibilityLoggerMixin;
        FragmentActivity activity = getActivity();
        if (visibilityLoggerMixin.mSourceMetricsCategory == 0
                && activity != null
                && (intent = activity.getIntent()) != null) {
            visibilityLoggerMixin.mSourceMetricsCategory =
                    intent.getIntExtra(":settings:source_metrics", 0);
        }
        RecyclerView listView = getListView();
        if (listView != null) {
            OnScrollListener onScrollListener = new OnScrollListener(getClass().getName());
            this.mOnScrollListener = onScrollListener;
            listView.addOnScrollListener(onScrollListener);
        }
        AppContextHelper appContextHelper = AppContextHelper.LazyHolder.sAppContextHelper;
        StateHandler.Callback callback = this.mAppContextCallback;
        String simpleName = getClass().getSimpleName();
        StateHandler stateHandler = Sbixby.getStateHandler();
        if (callback != null) {
            stateHandler.getClass();
            callback.toString();
        }
        stateHandler.mCallback = callback;
        appContextHelper.mCurrentClassName = simpleName;
        Utils$$ExternalSyntheticOutline0.m(
                new StringBuilder("registerStateHandlerCallback() by "),
                appContextHelper.mCurrentClassName,
                "AppContextHelper");
        super.onResume();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        getPreferenceManager().mOnPreferenceTreeClickListener =
                new PreferenceManager
                        .OnPreferenceTreeClickListener() { // from class:
                                                           // com.android.settings.core.InstrumentedPreferenceFragment$$ExternalSyntheticLambda0
                    @Override // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
                    public final boolean onPreferenceTreeClick(Preference preference) {
                        RecyclerView.ViewHolder findViewHolderForAdapterPosition;
                        InstrumentedPreferenceFragment instrumentedPreferenceFragment =
                                InstrumentedPreferenceFragment.this;
                        instrumentedPreferenceFragment.getClass();
                        if (preference instanceof TwoStatePreference) {
                            TwoStatePreference preference2 = (TwoStatePreference) preference;
                            RecyclerView recyclerView =
                                    instrumentedPreferenceFragment.getListView();
                            InteractionJankMonitor interactionJankMonitor =
                                    SettingsJankMonitor.jankMonitor;
                            Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
                            Intrinsics.checkNotNullParameter(preference2, "preference");
                            RecyclerView.Adapter adapter = recyclerView.getAdapter();
                            PreferenceGroupAdapter preferenceGroupAdapter =
                                    adapter instanceof PreferenceGroupAdapter
                                            ? (PreferenceGroupAdapter) adapter
                                            : null;
                            if (preferenceGroupAdapter != null
                                    && (findViewHolderForAdapterPosition =
                                                    recyclerView.findViewHolderForAdapterPosition(
                                                            preferenceGroupAdapter
                                                                    .getPreferenceAdapterPosition(
                                                                            preference2)))
                                            != null) {
                                String key = preference2.getKey();
                                View itemView = findViewHolderForAdapterPosition.itemView;
                                Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
                                SettingsJankMonitor.detectToggleJank(itemView, key);
                            }
                        }
                        return instrumentedPreferenceFragment.onPreferenceTreeClick(preference);
                    }
                };
    }

    public void writeElapsedTimeMetric(int i, String str) {
        VisibilityLoggerMixin visibilityLoggerMixin = this.mVisibilityLoggerMixin;
        if (visibilityLoggerMixin.mMetricsFeature == null
                || visibilityLoggerMixin.mMetricsCategory == 0
                || visibilityLoggerMixin.mCreationTimestamp == 0) {
            return;
        }
        int elapsedRealtime =
                (int) (SystemClock.elapsedRealtime() - visibilityLoggerMixin.mCreationTimestamp);
        visibilityLoggerMixin.mMetricsFeature.action(
                0, i, visibilityLoggerMixin.mMetricsCategory, elapsedRealtime, str);
    }

    public void writePreferenceClickMetric(Preference preference) {
        this.mMetricsFeatureProvider.logClickedPreference(preference, getMetricsCategory());
    }
}
