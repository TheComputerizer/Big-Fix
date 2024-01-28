package mods.thecomputerizer.bigfix.core;

import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BFRef {

    public static final String DEPENDENCIES = "";
    public static final String MODID = "bigfix";
    public static final String NAME = "Big Fix";
    public static final String VERSION = "0.0.1";
    public static final boolean CLIENT = FMLLaunchHandler.side().isClient();
    public static final boolean DEOBF = FMLLaunchHandler.isDeobfuscatedEnvironment();
    public static final Logger LOGGER = LogManager.getLogger(MODID);
}
