package us.zonix.client.module;

import us.zonix.client.module.impl.*;
import java.util.*;

public final class ModuleManager
{
    private final Map<Class<? extends IModule>, IModule> moduleClassMap;
    private final Map<String, IModule> moduleNameMap;
    
    public ModuleManager() {
        this.moduleClassMap = new HashMap<Class<? extends IModule>, IModule>();
        this.moduleNameMap = new HashMap<String, IModule>();
        this.register(new ArmorStatus());
        this.register(new ComboDisplay());
        this.register(new Coordinates());
        this.register(new CPS());
        this.register(new DirectionHUD());
        this.register(new FPS());
        this.register(new Keystrokes());
        this.register(new FPSBoost());
        this.register(new PotionCounter());
        this.register(new PotionEffects());
        this.register(new ReachDisplay());
        this.register(new Scoreboard());
        this.register(new TimeChanger());
        this.register(new ToggleSneak());
        this.register(new ZansMinimap());
    }
    
    public List<IModule> getEnabledModules() {
        final List<IModule> modules = new ArrayList<IModule>();
        for (final IModule module : this.moduleNameMap.values()) {
            if (module.isEnabled()) {
                modules.add(module);
            }
        }
        return modules;
    }
    
    public Collection<IModule> getModules() {
        return this.moduleNameMap.values();
    }
    
    public <T extends IModule> T getModule(final Class<T> clazz) {
        return (T)this.moduleClassMap.get(clazz);
    }
    
    public <T extends IModule> T getModule(final String name) {
        return (T)this.moduleNameMap.get(name.toLowerCase());
    }
    
    private void register(final IModule module) {
        this.moduleNameMap.put(module.getName().toLowerCase(), module);
        this.moduleClassMap.put(module.getClass(), module);
    }
}
