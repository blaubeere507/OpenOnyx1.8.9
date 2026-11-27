package client.onyx.ui.account;

import client.onyx.Onyx;
import client.onyx.account.Account;
import client.onyx.ui.component.Component;
import client.onyx.ui.component.RenderContext;
import client.onyx.ui.component.ScrollList;
import client.onyx.ui.theme.Typography;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class AccountListView extends ScrollList {
    private static final float FLOAT_5_01 = 5.0F;
    private static final float FLOAT_9_01 = 9.0F;
    private final Consumer<AccountRow> consumer;
    private final Consumer<AccountRow> consumer2;

    public AccountListView(Consumer<AccountRow> var1, Consumer<AccountRow> var2) {
        this.consumer = var1;
        this.consumer2 = var2;
        this.method_2278();
    }

    public void method_2278() {
        var var2 = this;
        this.list.clear();
        this.floatValue5 = 0.0F;
        this.animation.method_607(0.0F);
        List var1;
        int var4 = (var1 = Onyx.accountManager.method_1837()).size() - 1;

        for (int var3 = var4; var3 >= 0; var3 = var4) {
            List var10000 = var2.list;
            Account var10003 = (Account)var1.get(var4);
            var4--;
            var10000.add(new AccountRow(var10003, var2.consumer, var2.consumer2));
        }
    }

    @Override
    public boolean method_2183(float var1, float var2, int var3) {
        return this.method_2259(var1, var2) && super.method_2183(var1, var2, var3);
    }

    @Override
    public void method_2186(RenderContext var1) {
        if (this.list.isEmpty()) {
            var1.uiRenderer
                .method_484(
                    Typography.fontStyle15,
                    "No accounts yet",
                    this.floatValue3 + this.floatValue4 / 2.0F,
                    this.floatValue + this.floatValue2 / 2.0F,
                    var1.method_2256().intValue7()
                );
        } else {
            super.method_2186(var1);
        }
    }

    @Override
    public void method_2279(RenderContext var1) {
        var var8 = this;
        float var7 = this.floatValue - this.animation.method_606();

        Iterator var2;
        for (Iterator var3 = var2 = this.list.iterator(); var3.hasNext(); var3 = var2) {
            ((Component)var2.next()).method_2258(var8.floatValue3, var7, var8.floatValue4 - 9.0F, 56.0F);
            var7 += 61.0F;
        }

        AccountListView var5;
        float var6;
        if (!var8.list.isEmpty()) {
            var6 = var8.list.size() * 61.0F - 5.0F;
            var5 = var8;
        } else {
            var6 = 0.0F;
            var5 = var8;
        }

        var5.floatValue22 = var6;
    }
}
