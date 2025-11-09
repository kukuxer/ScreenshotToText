package org.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import org.example.hotkey.HotkeyListener;

public class Main {
    public static void main(String[] args) {//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new HotkeyListener());
            System.out.println("✅ Program started. Press Ctrl + Shift + S to capture a screenshot.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}