package org.redcastlemedia.multitallented.civs.spells.effects;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.civs.spells.Spell;
import org.redcastlemedia.multitallented.civs.spells.SpellComponent;

import java.util.HashSet;

/**
 *
 * @author Multitallented
 */
public abstract class Effect extends SpellComponent {

    public Effect(String abilityName, String key) {
        super(abilityName, key);
    }

    public abstract void apply(Object target, Entity origin, int level, Spell spell);

    public abstract boolean meetsRequirement(Object target, Entity origin, int level, Spell ability);

    /*public void apply(Spell spell, HashSet<Object> targetSet) {
        for (Object obj : targetSet) {
            if (obj instanceof Player) {
                apply(spell, (Player) obj);
            } else if (obj instanceof Block) {
                apply(spell, (Block) obj);
            } else if (obj instanceof Entity) {
                apply(spell, (Entity) obj);
            }
        }
    }

    public abstract void apply(Spell spell, Player player);
    public abstract void apply(Spell spell, Block block);
    public abstract void apply(Spell spell, Entity entity);

    public void meetsRequirement(Spell spell, HashSet<Object> targets, int level) {
        for (Object obj : targets) {
            if (obj.getClass().equals(Player.class)) {
                meetsRequirement(spell, (Player) obj, level);
            } else if (obj.getClass().equals(Block.class)) {
                meetsRequirement(spell, (Block) obj, level);
            } else if (obj instanceof Entity) {
                meetsRequirement(spell, (Entity) obj, level);
            }
        }
    }

    abstract void meetsRequirement(Spell spell, Player player, int level);
    abstract void meetsRequirement(Spell spell, Block block, int level);
    abstract void meetsRequirement(Spell spell, Entity entity, int level);*/
}