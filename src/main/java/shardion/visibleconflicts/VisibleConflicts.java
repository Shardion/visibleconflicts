package shardion.visibleconflicts;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModDependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class VisibleConflicts implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("visibleconflicts");
    public HashMap<String, ArrayList<String>> badMods;

    // "i should not be allowed to touch an iterator for the next ten thousand years" - shardion
    @Override
    public void onInitialize() {
        FabricLoader fabric = FabricLoader.getInstance();
        badMods = new HashMap<>();
        // for every mod loaded
        for (ModContainer mod : fabric.getAllMods()) {
            // for every dependency of this mod
            for (ModDependency dependency : mod.getMetadata().getDependencies()) {
                // if there's a mod loaded that conflicts with this mod
                if (dependency.getKind().equals(ModDependency.Kind.CONFLICTS) && fabric.isModLoaded(dependency.getModId())) {
                    Optional<ModContainer> conflictingModContainer = fabric.getModContainer(dependency.getModId());
                    if (conflictingModContainer.isPresent()) {
                        // and that conflict applies to the version of the conflicting mod we have
                        if (dependency.matches(conflictingModContainer.get().getMetadata().getVersion())) {
                            // if we don't already have the conflicting mod in our list
                            if (!badMods.containsKey(dependency.getModId())) {
                                badMods.put(dependency.getModId(), new ArrayList<String>());
                            }
                            // add this mod as a mod that conflicts with said conflicting mod
                            badMods.get(dependency.getModId()).add(mod.getMetadata().getId());
                        }
                    }
                }
            }
        }
        LOGGER.info(String.valueOf(badMods));
    }
}
