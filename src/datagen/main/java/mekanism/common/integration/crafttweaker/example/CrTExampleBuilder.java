package mekanism.common.integration.crafttweaker.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mekanism.common.integration.crafttweaker.example.component.CrTExampleComment;
import mekanism.common.integration.crafttweaker.example.component.CrTExampleRecipeComponentBuilder;
import mekanism.common.integration.crafttweaker.example.component.CrTExampleRemoveRecipesComponent;
import mekanism.common.integration.crafttweaker.example.component.ICrTExampleComponent;
import mekanism.common.integration.crafttweaker.recipe.MekanismRecipeManager;
import net.minecraft.util.ResourceLocation;

public class CrTExampleBuilder<BUILDER_TYPE extends CrTExampleBuilder<BUILDER_TYPE>> {

    protected final List<ICrTExampleComponent> contents = new ArrayList<>();
    private final BaseCrTExampleProvider exampleProvider;
    protected final String name;

    CrTExampleBuilder(BaseCrTExampleProvider exampleProvider, String name) {
        this.exampleProvider = exampleProvider;
        this.name = name;
    }

    //Helper to go up to the root
    public BaseCrTExampleProvider getExampleProvider() {
        return exampleProvider;
    }

    private BUILDER_TYPE getThis() {
        return (BUILDER_TYPE) this;
    }

    public BUILDER_TYPE addComponent(ICrTExampleComponent component) {
        contents.add(Objects.requireNonNull(component, "Example component cannot be null."));
        return getThis();
    }

    public BUILDER_TYPE comment(String... comments) {
        return addComponent(new CrTExampleComment(comments));
    }

    public CrTExampleRecipeComponentBuilder<BUILDER_TYPE> recipe(MekanismRecipeManager<?> recipeManager) {
        return recipe(recipeManager, "addRecipe");
    }

    public CrTExampleRecipeComponentBuilder<BUILDER_TYPE> recipe(MekanismRecipeManager<?> recipeManager, String... methodNames) {
        CrTExampleRecipeComponentBuilder<BUILDER_TYPE> recipeComponentBuilder = new CrTExampleRecipeComponentBuilder<>(getThis(), recipeManager, methodNames);
        contents.add(recipeComponentBuilder);
        return recipeComponentBuilder;
    }

    public BUILDER_TYPE removeRecipes(MekanismRecipeManager<?> recipeManager, ResourceLocation... recipeNames) {
        return addComponent(new CrTExampleRemoveRecipesComponent(exampleProvider, recipeManager, recipeNames));
    }

    public CrTExampleSnipComponentBuilder<BUILDER_TYPE> modLoadedSnip(String modid) {
        //Note: We don't make use of all the possible supported modloaded conditions but it doesn't really matter for now as we don't need them
        if (ResourceLocation.tryParse(modid + ":test") == null) {
            throw new IllegalArgumentException("Invalid modid: '" + modid + "' found non [a-z0-9_.-] character.");
        }
        return snip("modloaded", modid);
    }

    private CrTExampleSnipComponentBuilder<BUILDER_TYPE> snip(String snipType, String data) {
        //Note: We don't really do any extra validation currently about the snip data but there isn't really a generic way to do it
        // We also don't validate the snipType is valid either currently
        CrTExampleSnipComponentBuilder<BUILDER_TYPE> snipComponentBuilder = new CrTExampleSnipComponentBuilder<>(exampleProvider, getThis(), snipType, data);
        contents.add(snipComponentBuilder);
        return snipComponentBuilder;
    }

    public BUILDER_TYPE blankLine() {
        return blankLines(1);
    }

    public BUILDER_TYPE blankLines(int lines) {
        if (lines <= 0) {
            throw new IllegalArgumentException("Positive number of blank lines required.");
        }
        for (int line = 0; line < lines; line++) {
            contents.add(null);
        }
        return getThis();
    }

    public String build() {
        int contentLength = contents.size();
        if (contentLength == 0) {
            invalidContents();
        }
        //TODO: Trim trailing and starting blank lines and check if empty again (may be best to do this *after* the string is built??)
        // Though do we want it to just silently trim or do we want it to hard fail
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            ICrTExampleComponent component = contents.get(i);
            if (component != null) {
                //Null components are blank lines and can be ignored here as the new line afterwards will be added when appropriate
                stringBuilder.append(component.asString());
            }
            if (i < contentLength - 1) {
                //If we are not on the last line add a new line after this
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    protected void invalidContents() {
        throw new RuntimeException("Example '" + name + "' is empty and should either be implemented or removed.");
    }
}