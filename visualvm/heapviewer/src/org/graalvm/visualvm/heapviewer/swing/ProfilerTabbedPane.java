/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.graalvm.visualvm.heapviewer.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import org.graalvm.visualvm.lib.ui.UIUtils;
import org.graalvm.visualvm.lib.ui.components.CloseButton;
import org.openide.util.NbBundle;

/**
 *
 * @author Jiri Sedlacek
 */
@NbBundle.Messages({
    "ProfilerTabbedPane_Close=Close",
    "ProfilerTabbedPane_CloseOther=Close Other",
    "ProfilerTabbedPane_CloseOtherRight=Close Other to the Right",
    "ProfilerTabbedPane_CloseAll=Close All"
})
class ProfilerTabbedPane extends JTabbedPane {
    
    private static final boolean IS_GTK = UIUtils.isGTKLookAndFeel();
    
    
    ProfilerTabbedPane() {
        setFocusable(false);
        
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (!mouseWheelScrolls()) return;
                
                int units = e.getWheelRotation(); // always step by 1!
                int selected = getSelectedIndex();
                
                int newSelected = selected + units;
                if (newSelected < 0) newSelected = 0;
                else if (newSelected >= getTabCount()) newSelected = getTabCount() - 1;
                
                setSelectedIndex(newSelected);
            }
        });
    }
    
    
    void addTab(String title, Icon icon, final Component component, String tip, boolean closable) {
        int tabCount = getTabCount();
        super.addTab(title, icon, component, tip);
        
        Runnable closer = closable ? new Runnable() {
            public void run() {
                closeTab(component);
            }
        } : null;
        
        setTabComponentAt(tabCount, new TabCaption(title, icon, closer));
    }
    
    
    @Override
    public void setTitleAt(int index, String title) {
        super.setTitleAt(index, title);
        tabCaptionAt(index).setTitle(title);
    }
    
    @Override
    public void setIconAt(int index, Icon icon) {
        super.setIconAt(index, icon);
        tabCaptionAt(index).setIcon(icon);
    }
    
    @Override
    public void setForegroundAt(int index, Color foreground) {
        super.setForegroundAt(index, foreground);
        tabCaptionAt(index).setForeground(foreground);
    }
    
    @Override
    public Color getForegroundAt(int index) {
        return tabCaptionAt(index).getForeground();
    }
    
    
    boolean isClosableAt(int index) {
        return tabCaptionAt(index).isClosable();
    }
    
    
    protected boolean mouseWheelScrolls() {
        return UIUtils.isAquaLookAndFeel() || getTabLayoutPolicy() == SCROLL_TAB_LAYOUT;
    }
    
    protected void closeTab(Component component) {
        remove(indexOfComponent(component));
    }
    
    
    public void close(Component component) {
        closeTab(component);
    }
    
    public void closeOther(Component component) {
        for (Object[] comp : components()) {
            if (comp[1] != component && (boolean)comp[0])
                close((Component)comp[1]);
        }
    }
    
    public void closeOtherToTheRight(Component component) {
        boolean visited = false;
        for (Object[] comp : components()) {
            if (comp[1] == component) {
                visited = true;
            } else {
                if (visited && (boolean)comp[0])
                    close((Component)comp[1]);
            }
        }
    }
    
    public void closeAll() {
        for (Object[] comp : components())
            if ((boolean)comp[0]) close((Component)comp[1]);
    }
    
    private Object[][] components() {
        int componentCount = getTabCount();
        Object[][] components = new Object[componentCount][2];
        for (int i = 0; i < getTabCount(); i++) {
            components[i][0] = isClosableAt(i);
            components[i][1] = getComponentAt(i);
        }
        return components;
    }
    
    
    private TabCaption tabCaptionAt(int index) {
        return (TabCaption)getTabComponentAt(index);
    }
    
    
    protected void processMouseEvent(MouseEvent e) {
        int index = indexAtLocation(e.getX(), e.getY());
        
        if (index != -1) {
            if (e.isPopupTrigger()) {
                // Show popup menu for the clicked tab
                final MouseEvent _e = e;
                final int _index = index;
                final Component _component = getComponentAt(index);
                
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() { showPopupMenu(_index, _component, _e); };
                });
                
                e.consume();
                return;
            } else if (e.getID() == MouseEvent.MOUSE_CLICKED && SwingUtilities.isMiddleMouseButton(e)) {
                // Close tab using middle button click
                if (isClosableAt(index)) closeTab(getComponentAt(index));
                
                e.consume();
                return;
            } else if (e.getID() == MouseEvent.MOUSE_PRESSED && !SwingUtilities.isLeftMouseButton(e)) {
                // Do not switch tabs using middle or right mouse button
                e.consume();
                return;
            }
        }
        
        super.processMouseEvent(e);
    }
    
    private void showPopupMenu(int index, Component component, MouseEvent e) {
        JPopupMenu popup = new JPopupMenu() {
//            public void setVisible(boolean visible) {
//                if (visible) popupShowing();
//                super.setVisible(visible);
//                if (!visible) popupHidden();
//            }
        };
        
        populatePopup(popup, index, component);
        
        if (popup.getComponentCount() > 0) {
            if (e == null) {
                // TODO: invoked by keyboard? handle it? 
            } else {
                popup.show(this, e.getX(), e.getY());
            }
        }
    }
    
    private void populatePopup(JPopupMenu popup, int index, Component component) {
        boolean anyClosable = false;
        boolean otherClosable = false;
        boolean otherToTheRightClosable = false;

        boolean visited = false;
        for (int i = 0; i < getTabCount(); i++) {
            Component c = getComponentAt(i);
            
            boolean invoker = c == component;
            boolean closable = isClosableAt(i);
            if (closable) {
                anyClosable = true;
                if (!invoker) otherClosable = true;
                if (visited) otherToTheRightClosable = true;
                
                if (otherToTheRightClosable) break;
            }
            
            if (invoker) visited = true;
        }
        
        final boolean _closable = isClosableAt(index);
        final boolean _anyClosable = anyClosable;
        final boolean _otherClosable = otherClosable;
        final boolean _otherToTheRightClosable = otherToTheRightClosable;
        
        popup.add(new JMenuItem(Bundle.ProfilerTabbedPane_Close()) {
            { setEnabled(_closable); }
            protected void fireActionPerformed(ActionEvent e) { close(component); }
        });
        
        popup.addSeparator();
        
        popup.add(new JMenuItem(Bundle.ProfilerTabbedPane_CloseOther()) {
            { setEnabled(_otherClosable); }
            protected void fireActionPerformed(ActionEvent e) { closeOther(component); }
        });
        popup.add(new JMenuItem(Bundle.ProfilerTabbedPane_CloseOtherRight()) {
            { setEnabled(_otherToTheRightClosable); }
            protected void fireActionPerformed(ActionEvent e) { closeOtherToTheRight(component); }
        });
        
        popup.addSeparator();
        
        popup.add(new JMenuItem(Bundle.ProfilerTabbedPane_CloseAll()) {
            { setEnabled(_anyClosable); }
            protected void fireActionPerformed(ActionEvent e) { closeAll(); }
        });
    }
    
    
    private class TabCaption extends JPanel {
        
        private JLabel caption;
    
        TabCaption(String text, Icon icon, Runnable closer) {
            setFocusable(false);
            
            setOpaque(false);
            
            if (UIUtils.isAquaLookAndFeel()) setBorder(BorderFactory.createEmptyBorder(1, 0, -1, 0)); // fix wrong layout on Aqua LaF
            else if (IS_GTK) setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0)); // extra leading space on GTK
            else if (UIUtils.isWindowsLookAndFeel()) setBorder(BorderFactory.createEmptyBorder(2, 1, -1, 0)); // improve alignment on Windows

            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

            caption = new JLabel(text, icon, JLabel.LEADING);
            add(caption);

            if (closer != null) {
                add(Box.createHorizontalStrut(5));
                add(Box.createHorizontalGlue());
                add(CloseButton.create(closer));
            }
        }
        
        boolean isClosable() {
            return getComponentCount() > 1;
        }
        
        private void setTitle(String title) {
            caption.setText(title);
        }
        
        private void setIcon(Icon icon) {
            caption.setIcon(icon);
        }
        
        @Override
        public void setForeground(Color foreground) {
            if (caption == null) super.setForeground(foreground);
            else caption.setForeground(foreground);
        }

        @Override
        public Color getForeground() {
            return caption == null ? super.getForeground() : caption.getForeground();
        }
        
        @Override
        public Dimension getPreferredSize() {
            Dimension dim = super.getPreferredSize();
            if (IS_GTK) dim.height = super.getMinimumSize().height;
            return dim;
        }

    }
    
}