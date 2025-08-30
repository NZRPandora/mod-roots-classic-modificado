/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeHolder
 */
package elucent.rootsclassic.research;

import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.research.EnumPageType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ResearchPage {
    public List<ItemStack> craftingRecipe = new ArrayList<ItemStack>();
    public RecipeHolder<ComponentRecipe> mortarRecipe = null;
    public RecipeHolder<RitualRecipe> altarRecipe = null;
    public List<ItemStack> smeltingRecipe = new ArrayList<ItemStack>();
    public EnumPageType recipe = EnumPageType.TYPE_NULL;
    public ItemStack displayItem = null;
    public List<String> info = new ArrayList<String>();
    public String title = "";

    public List<String> makeLines(String s) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> words = new ArrayList<String>();
        StringBuilder temp = new StringBuilder();
        int counter = 0;
        for (int i = 0; i < s.length(); ++i) {
            temp.append(s.charAt(i));
            if (s.charAt(i) != ' ') continue;
            words.add(temp.toString());
            temp = new StringBuilder();
        }
        words.add(temp.toString());
        temp = new StringBuilder();
        for (String word : words) {
            if ((counter += Minecraft.getInstance().font.width(word)) > 160) {
                list.add(temp.toString());
                temp = new StringBuilder(word);
                counter = Minecraft.getInstance().font.width(word);
                continue;
            }
            temp.append(word);
        }
        list.add(temp.toString());
        return list;
    }

    public ResearchPage addDisplayItem(ItemStack stack) {
        this.recipe = EnumPageType.TYPE_DISPLAY;
        this.displayItem = stack;
        return this;
    }

    public ResearchPage addSmeltingRecipe(ItemStack input, ItemStack result) {
        this.recipe = EnumPageType.TYPE_SMELTING;
        this.smeltingRecipe.add(input);
        this.smeltingRecipe.add(result);
        return this;
    }

    public ResearchPage addMortarRecipe(RecipeHolder<ComponentRecipe> component) {
        this.recipe = EnumPageType.TYPE_MORTAR;
        this.mortarRecipe = component;
        return this;
    }

    public ResearchPage addAltarRecipe(RecipeHolder<RitualRecipe> ritual) {
        this.recipe = EnumPageType.TYPE_ALTAR;
        this.altarRecipe = ritual;
        return this;
    }
}

