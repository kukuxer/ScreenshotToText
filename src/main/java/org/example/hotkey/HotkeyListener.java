package org.example.hotkey;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import org.example.ui.SelectionOverlay;

import javax.swing.*;

public class HotkeyListener implements NativeKeyListener {

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_S &&
                (e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0 &&
                (e.getModifiers() & NativeKeyEvent.SHIFT_MASK) != 0) {
            SwingUtilities.invokeLater(SelectionOverlay::new);
        }
    }
}
