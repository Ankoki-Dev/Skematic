package com.efnilite.skematic.elements.fawe.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.elements.fawe.object.FaweSchematic;
import com.efnilite.skematic.elements.fawe.object.SchematicLoader;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.event.Event;

import java.io.File;
import java.nio.file.Paths;

@Name("Fawe - FaweSchematic size")
@Description("Gets the size of a schematic.")
@Examples("set {_size} to the size of skematic \"plugins/WorldEdit/ZAS.schematic\"")
@Since("1.0")
public class ExprSchematicSize extends SimpleExpression<BlockVector3> {


    private Expression<?> schematic;

    static {
        Skript.registerExpression(ExprSchematicSize.class, BlockVector3.class, ExpressionType.PROPERTY,
                "skematic|fawe s(ch|k)em[atic] size of %fawe schematics/strings%",
                "%fawe schematics/strings%'[s] fawe s(ch|k)em[atic] size");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        schematic = expressions[0];

        return true;
    }

    @Override
    protected BlockVector3[] get(Event e) {
        FaweSchematic schematic;
        if (this.schematic.getSingle(e) instanceof String) {
            String file = (String) this.schematic.getSingle(e);
            if (SchematicLoader.getSchematics().containsKey(file)) {
                schematic = SchematicLoader.get(file);
            } else if (Paths.get(file).toFile().exists()) {
                schematic = new FaweSchematic(new File(file));
            } else {
                Skript.error("FaweSchematic " + file + " doesn't exist!");
                return null;
            }
        } else if (this.schematic.getSingle(e) instanceof FaweSchematic) {
            schematic = (FaweSchematic) this.schematic.getSingle(e);
        } else {
            return null;
        }

        return new BlockVector3[] { schematic.getClipboard().getOrigin() };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends BlockVector3> getReturnType() {
        return BlockVector3.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "size of " + schematic.toString(e, debug);
    }
}


