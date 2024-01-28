package mods.thecomputerizer.bigfix.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nullable;
import java.util.Map;

@SuppressWarnings("unused")
public class BFPlugin implements IFMLLoadingPlugin {

    static {
        LogManager.getLogger(BFPlugin.class.getName()).info("Initializing bigfix plugin");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public @Nullable String getSetupClass() {
        return "mods.thecomputerizer.bigfix.core.BFPluginSetup";
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return "mods.thecomputerizer.bigfix.core.BFTransformer";
    }
}
