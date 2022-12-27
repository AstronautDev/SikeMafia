package com.sikepvp.mafia.utility;

import com.sikepvp.mafia.Mafia;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Data {

    File cashFile;
    FileConfiguration cashConfig;

    File influenceFile;
    FileConfiguration influenceConfig;

    Mafia plugin;
    public Data(Mafia plugin) {
        this.plugin = plugin;

        loadDataFiles();
        loadDatabase();
    }

    private void loadDataFiles() {
        createCashConfig();
        createInfluenceConfig();
    }

    private void loadDatabase() {

    }

    private void createCashConfig() {
        cashFile = new File(plugin.getDataFolder(), "cash.yml");
        if(!cashFile.exists()) {
            cashFile.getParentFile().mkdirs();
        }

        cashConfig = new YamlConfiguration();
        try {
            cashConfig.load(cashFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void createInfluenceConfig() {
        influenceFile = new File(plugin.getDataFolder(), "influence.yml");
        if(!influenceFile.exists()) {
            influenceFile.getParentFile().mkdirs();
        }

        influenceConfig = new YamlConfiguration();
        try {
            influenceConfig.load(influenceFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveCash() {
        try {
            cashConfig.save(cashFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveInfluence() {
        try {
            influenceConfig.save(influenceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getCashConfig() {
        return this.cashConfig;
    }

    public FileConfiguration getInfluenceConfig() {
        return this.influenceConfig;
    }

}
