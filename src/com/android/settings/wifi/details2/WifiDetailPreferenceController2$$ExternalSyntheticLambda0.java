package com.android.settings.wifi.details2;

import android.util.Pair;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiDetailPreferenceController2$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiDetailPreferenceController2 f$0;

    public /* synthetic */ WifiDetailPreferenceController2$$ExternalSyntheticLambda0(WifiDetailPreferenceController2 wifiDetailPreferenceController2, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiDetailPreferenceController2;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        WifiDetailPreferenceController2 wifiDetailPreferenceController2 = this.f$0;
        switch (i) {
            case 0:
                WifiEntry wifiEntry = wifiDetailPreferenceController2.mWifiEntry;
                if (!wifiEntry.isSubscription()) {
                    wifiEntry.forget(wifiDetailPreferenceController2);
                    FragmentActivity activity = wifiDetailPreferenceController2.mFragment.getActivity();
                    if (activity != null) {
                        wifiDetailPreferenceController2.mMetricsFeatureProvider.action(activity, 137, new Pair[0]);
                        activity.finish();
                        break;
                    }
                } else {
                    wifiDetailPreferenceController2.showConfirmForgetDialog();
                    break;
                }
                break;
            case 1:
                wifiDetailPreferenceController2.mMetricsFeatureProvider.action(wifiDetailPreferenceController2.mFragment.getActivity(), EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS, new Pair[0]);
                wifiDetailPreferenceController2.mWifiEntry.signIn();
                break;
            case 2:
                wifiDetailPreferenceController2.connectDisconnectNetwork();
                break;
            case 3:
                WifiDppUtils.showLockScreen(wifiDetailPreferenceController2.mContext, 
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x001d: INVOKE 
                      (wrap:android.content.Context:0x0000: IGET (r3v1 'wifiDetailPreferenceController2' com.android.settings.wifi.details2.WifiDetailPreferenceController2) A[WRAPPED] (LINE:1) com.android.settingslib.core.AbstractPreferenceController.mContext android.content.Context)
                      (wrap:java.lang.Runnable:0x0004: CONSTRUCTOR (r3v1 'wifiDetailPreferenceController2' com.android.settings.wifi.details2.WifiDetailPreferenceController2) A[MD:(com.android.settings.wifi.details2.WifiDetailPreferenceController2):void (m), WRAPPED] (LINE:5) call: com.android.settings.wifi.details2.WifiDetailPreferenceController2$$ExternalSyntheticLambda5.<init>(com.android.settings.wifi.details2.WifiDetailPreferenceController2):void type: CONSTRUCTOR)
                     STATIC call: com.android.settings.wifi.dpp.WifiDppUtils.showLockScreen(android.content.Context, java.lang.Runnable):void A[MD:(android.content.Context, java.lang.Runnable):void (m)] (LINE:8) in method: com.android.settings.wifi.details2.WifiDetailPreferenceController2$$ExternalSyntheticLambda0.onClick(android.view.View):void, file: classes2.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                    	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.settings.wifi.details2.WifiDetailPreferenceController2$$ExternalSyntheticLambda5, state: NOT_LOADED
                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                    	... 21 more
                    */
                /*
                    this = this;
                    int r4 = r3.$r8$classId
                    com.android.settings.wifi.details2.WifiDetailPreferenceController2 r3 = r3.f$0
                    switch(r4) {
                        case 0: goto L3b;
                        case 1: goto L25;
                        case 2: goto L21;
                        case 3: goto L1d;
                        default: goto L7;
                    }
                L7:
                    androidx.preference.PreferenceFragmentCompat r4 = r3.mFragment
                    androidx.fragment.app.FragmentActivity r4 = r4.getActivity()
                    r0 = 0
                    android.util.Pair[] r0 = new android.util.Pair[r0]
                    com.android.settingslib.core.instrumentation.MetricsFeatureProvider r1 = r3.mMetricsFeatureProvider
                    r2 = 1008(0x3f0, float:1.413E-42)
                    r1.action(r4, r2, r0)
                    com.android.wifitrackerlib.WifiEntry r3 = r3.mWifiEntry
                    r3.signIn()
                    return
                L1d:
                    com.android.settings.wifi.details2.WifiDetailPreferenceController2.$r8$lambda$6wqxssGKIlYRKwRrbjxUV7oAgKg(r3)
                    return
                L21:
                    r3.connectDisconnectNetwork()
                    return
                L25:
                    androidx.preference.PreferenceFragmentCompat r4 = r3.mFragment
                    androidx.fragment.app.FragmentActivity r4 = r4.getActivity()
                    r0 = 0
                    android.util.Pair[] r0 = new android.util.Pair[r0]
                    com.android.settingslib.core.instrumentation.MetricsFeatureProvider r1 = r3.mMetricsFeatureProvider
                    r2 = 1008(0x3f0, float:1.413E-42)
                    r1.action(r4, r2, r0)
                    com.android.wifitrackerlib.WifiEntry r3 = r3.mWifiEntry
                    r3.signIn()
                    return
                L3b:
                    com.android.wifitrackerlib.WifiEntry r4 = r3.mWifiEntry
                    boolean r0 = r4.isSubscription()
                    if (r0 == 0) goto L47
                    r3.showConfirmForgetDialog()
                    goto L5f
                L47:
                    r4.forget(r3)
                    androidx.preference.PreferenceFragmentCompat r4 = r3.mFragment
                    androidx.fragment.app.FragmentActivity r4 = r4.getActivity()
                    if (r4 == 0) goto L5f
                    r0 = 0
                    android.util.Pair[] r0 = new android.util.Pair[r0]
                    com.android.settingslib.core.instrumentation.MetricsFeatureProvider r3 = r3.mMetricsFeatureProvider
                    r1 = 137(0x89, float:1.92E-43)
                    r3.action(r4, r1, r0)
                    r4.finish()
                L5f:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.settings.wifi.details2.WifiDetailPreferenceController2$$ExternalSyntheticLambda0.onClick(android.view.View):void");
            }
        }
