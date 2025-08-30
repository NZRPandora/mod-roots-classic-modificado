/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeHolder
 */
package elucent.rootsclassic.research;

import elucent.rootsclassic.Roots;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.research.ResearchPage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ResearchBase {
    public double posX = 0.0;
    public double posY = 0.0;
    private String name = "";
    private final ItemStack icon;
    private final ResearchBase req = null;
    private final List<ResearchPage> info = new ArrayList<ResearchPage>();

    public ResearchBase(String name, ItemStack icon) {
        this.name = name;
        this.icon = icon;
    }

    public ResearchBase addPage(ResearchPage page) {
        this.info.add(page);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public ResearchBase getReq() {
        return this.req;
    }

    public List<ResearchPage> getInfo() {
        return this.info;
    }

    public ResearchBase addPageOf(Optional<RecipeHolder<RitualRecipe>> optionalRitual) {
        optionalRitual.ifPresentOrElse(ritual -> this.info.add(new ResearchPage().addAltarRecipe((RecipeHolder<RitualRecipe>)ritual)), () -> Roots.LOGGER.warn("Missing altar recipe for page {}", (Object)this.name));
        return this;
    }
}

