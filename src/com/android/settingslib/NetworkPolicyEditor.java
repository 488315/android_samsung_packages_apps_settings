package com.android.settingslib;

import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.NetworkTemplate;
import android.os.AsyncTask;
import android.util.RecurrenceRule;

import com.android.internal.util.Preconditions;

import com.google.android.collect.Lists;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkPolicyEditor {
    public final ArrayList mPolicies = Lists.newArrayList();
    public final NetworkPolicyManager mPolicyManager;

    public NetworkPolicyEditor(NetworkPolicyManager networkPolicyManager) {
        this.mPolicyManager =
                (NetworkPolicyManager) Preconditions.checkNotNull(networkPolicyManager);
    }

    public final NetworkPolicy getOrCreatePolicy(NetworkTemplate networkTemplate) {
        RecurrenceRule buildRecurringMonthly;
        boolean z;
        NetworkPolicy policy = getPolicy(networkTemplate);
        if (policy != null) {
            return policy;
        }
        if (networkTemplate.getMatchRule() == 4) {
            buildRecurringMonthly = RecurrenceRule.buildNever();
            z = false;
        } else {
            buildRecurringMonthly =
                    RecurrenceRule.buildRecurringMonthly(
                            ZonedDateTime.now().getDayOfMonth(), ZoneId.systemDefault());
            z = true;
        }
        NetworkPolicy networkPolicy =
                new NetworkPolicy(
                        networkTemplate, buildRecurringMonthly, -1L, -1L, -1L, -1L, z, true);
        this.mPolicies.add(networkPolicy);
        return networkPolicy;
    }

    public final NetworkPolicy getPolicy(NetworkTemplate networkTemplate) {
        Iterator it = this.mPolicies.iterator();
        while (it.hasNext()) {
            NetworkPolicy networkPolicy = (NetworkPolicy) it.next();
            if (networkPolicy.template.equals(networkTemplate)) {
                return networkPolicy;
            }
        }
        return null;
    }

    public final int getPolicyCycleDay(NetworkTemplate networkTemplate) {
        NetworkPolicy policy = getPolicy(networkTemplate);
        if (policy == null || !policy.cycleRule.isMonthly()) {
            return -1;
        }
        return policy.cycleRule.start.getDayOfMonth();
    }

    public final void read() {
        NetworkPolicy[] networkPolicies = this.mPolicyManager.getNetworkPolicies();
        this.mPolicies.clear();
        boolean z = false;
        for (NetworkPolicy networkPolicy : networkPolicies) {
            if (networkPolicy.limitBytes < -1) {
                networkPolicy.limitBytes = -1L;
                z = true;
            }
            if (networkPolicy.warningBytes < -1) {
                networkPolicy.warningBytes = -1L;
                z = true;
            }
            this.mPolicies.add(networkPolicy);
        }
        if (z) {
            writeAsync();
        }
    }

    public final void setPolicyLimitBytes(NetworkTemplate networkTemplate, long j) {
        NetworkPolicy policy = getPolicy(networkTemplate);
        if ((policy != null ? policy.warningBytes : -1L) > j && j != -1) {
            NetworkPolicy orCreatePolicy = getOrCreatePolicy(networkTemplate);
            orCreatePolicy.warningBytes = j;
            orCreatePolicy.inferred = false;
            orCreatePolicy.clearSnooze();
            writeAsync();
        }
        NetworkPolicy orCreatePolicy2 = getOrCreatePolicy(networkTemplate);
        orCreatePolicy2.limitBytes = j;
        orCreatePolicy2.inferred = false;
        orCreatePolicy2.clearSnooze();
        writeAsync();
    }

    public final void setPolicyWarningBytes(NetworkTemplate networkTemplate, long j) {
        NetworkPolicy policy = getPolicy(networkTemplate);
        long j2 = policy != null ? policy.limitBytes : -1L;
        if (j2 != -1) {
            j = Math.min(j, j2);
        }
        NetworkPolicy orCreatePolicy = getOrCreatePolicy(networkTemplate);
        orCreatePolicy.warningBytes = j;
        orCreatePolicy.inferred = false;
        orCreatePolicy.clearSnooze();
        writeAsync();
    }

    public final void writeAsync() {
        ArrayList arrayList = this.mPolicies;
        final NetworkPolicy[] networkPolicyArr =
                (NetworkPolicy[]) arrayList.toArray(new NetworkPolicy[arrayList.size()]);
        new AsyncTask() { // from class: com.android.settingslib.NetworkPolicyEditor.1
            @Override // android.os.AsyncTask
            public final Object doInBackground(Object[] objArr) {
                NetworkPolicyEditor networkPolicyEditor = NetworkPolicyEditor.this;
                networkPolicyEditor.mPolicyManager.setNetworkPolicies(networkPolicyArr);
                return null;
            }
        }.execute(new Void[0]);
    }
}
