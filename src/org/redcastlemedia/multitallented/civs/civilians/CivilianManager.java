package org.redcastlemedia.multitallented.civs.civilians;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.civs.Civs;
import org.redcastlemedia.multitallented.civs.ConfigManager;
import org.redcastlemedia.multitallented.civs.items.CivItem;
import org.redcastlemedia.multitallented.civs.items.ItemManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CivilianManager {

    private HashMap<UUID, Civilian> onlineCivilians = new HashMap<>();

    private static CivilianManager civilianManager = null;

    public CivilianManager() {
        civilianManager = this;
    }

    public static CivilianManager getInstance() {
        if (civilianManager == null) {
            civilianManager = new CivilianManager();
            return civilianManager;
        } else {
            return civilianManager;
        }
    }

    void loadCivilian(Player player) {
        Civilian civilian = loadFromFileCivilian(player.getUniqueId());
        onlineCivilians.put(player.getUniqueId(), civilian);
    }
    public void createDefaultCivilian(Player player) {
        onlineCivilians.put(player.getUniqueId(), createDefaultCivilian(player.getUniqueId()));
    }
    void unloadCivilian(Player player) {
        onlineCivilians.remove(player.getUniqueId());
    }
    public Civilian getCivilian(UUID uuid) {
        Civilian civilian = onlineCivilians.get(uuid);
        if (civilian == null) {
            civilian = loadFromFileCivilian(uuid);
        }
        return civilian;
    }
    private Civilian loadFromFileCivilian(UUID uuid) {
        Civs civs = Civs.getInstance();
        if (civs == null) {
            Civilian civilian = createDefaultCivilian(uuid);
            saveCivilian(civilian);
            return civilian;
        }
        File civilianFolder = new File(civs.getDataFolder(), "players");
        if (!civilianFolder.exists()) {
            Civilian civilian = createDefaultCivilian(uuid);
            saveCivilian(civilian);
            return civilian;
        }
        File civilianFile = new File(civilianFolder, uuid + ".yml");
        if (!civilianFile.exists()) {
            Civilian civilian = createDefaultCivilian(uuid);
            saveCivilian(civilian);
            return civilian;
        }
        FileConfiguration civConfig = new YamlConfiguration();
        try {
            civConfig.load(civilianFile);

            //TODO load other civilian file properties

            ArrayList<CivItem> items = ItemManager.getInstance().loadCivItems(civConfig, uuid);

            return new Civilian(uuid, civConfig.getString("locale"), items);
        } catch (Exception ex) {
            Civs.logger.severe("Unable to read/write " + uuid + ".yml");
            ex.printStackTrace();
            return createDefaultCivilian(uuid);
        }
    }
    Civilian createDefaultCivilian(UUID uuid) {
        ConfigManager configManager = ConfigManager.getInstance();
        //TODO add all attributes here
        return new Civilian(uuid, configManager.getDefaultLanguage(), ItemManager.getInstance().getNewItems());
    }
    public void saveCivilian(Civilian civilian) {
        Civs civs = Civs.getInstance();
        if (civs == null) {
            return;
        }
        File civilianFolder = new File(civs.getDataFolder(), "players");
        if (!civilianFolder.exists()) {
            if (civilianFolder.mkdir()) {
                Civs.logger.severe("Unable to create players folder");
                return;
            }
        }
        File civilianFile = new File(civilianFolder, civilian.getUuid() + ".yml");
        if (!civilianFile.exists()) {
            try {
                civilianFile.createNewFile();
            } catch (IOException ioexception) {
                Civs.logger.severe("Unable to create " + civilian.getUuid() + ".yml");
                return;
            }
        }
        FileConfiguration civConfig = new YamlConfiguration();
        try {
            civConfig.load(civilianFile);

            civConfig.set("locale", civilian.getLocale());
            //TODO save other civilian file properties
            for (CivItem civItem : civilian.getStashItems()) {
                civConfig.set("items." + civItem.getDisplayName().replace("Civs ", "").toLowerCase(), civItem.getQty());
            }

            civConfig.save(civilianFile);
        } catch (Exception ex) {
            Civs.logger.severe("Unable to read/write " + civilian.getUuid() + ".yml");
            return;
        }
    }
}