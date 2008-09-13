/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.netbeans.modules.profiler.attach.panels.components;

import java.awt.Color;
import javax.swing.UIManager;
import org.netbeans.lib.profiler.ui.UIUtils;

/**
 *
 * @author  Jaroslav Bachorik
 */
public class ResizableHintPanel extends javax.swing.JPanel {

    /** Creates new form ResizableHintPanel */
    public ResizableHintPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        hintArea = new org.netbeans.lib.profiler.ui.components.HTMLTextArea();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        hintArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        hintArea.setText(org.openide.util.NbBundle.getMessage(ResizableHintPanel.class, "ResizableHintPanel.hintArea.text")); // NOI18N
        hintArea.setEnabled(false);
        hintArea.setFocusCycleRoot(false);
        hintArea.setFocusable(false);
        Color panelBackground = UIManager.getColor("Panel.background"); // NOI18N;
        Color hintBackground = UIUtils.getSafeColor(panelBackground.getRed() - 10, panelBackground.getGreen() - 10, panelBackground.getBlue() - 10);

        hintArea.setDisabledTextColor(Color.darkGray);
        hintArea.setBackground(hintBackground);
        jScrollPane1.setViewportView(hintArea);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(ResizableHintPanel.class, "ResizableHintPanel.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.netbeans.lib.profiler.ui.components.HTMLTextArea hintArea;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    /**
     * Getter for property hint.
     * @return Value of property hint.
     */
    public String getHint() {
        return hintArea.getText();
    }

    /**
     * Setter for property hint.
     * @param hint New value of property hint.
     */
    public void setHint(String hint) {
        hintArea.setText(hint);
        hintArea.setCaretPosition(0);
    }
}
