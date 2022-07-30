package com.sussysyrup.smitheesfoundry.client.registry;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeybindRegistry {

    public static KeyBinding keyBinding;

    public static void clientInit()
    {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.smitheesfoundry.tooltipexpand", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_LEFT_SHIFT, // The keycode of the key
                "category.smitheesfoundry.keybinds" // The translation key of the keybinding's category.
        ));
    }
}
