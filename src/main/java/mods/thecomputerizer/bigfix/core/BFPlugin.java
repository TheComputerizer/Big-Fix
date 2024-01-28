package mods.thecomputerizer.bigfix.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

@SuppressWarnings("unused")
public class BFPlugin implements IFMLLoadingPlugin {

    static {
        BFRef.LOGGER.info("Initializing some big fixes");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"mods.thecomputerizer.bigfix.core.BFTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public @Nullable String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
