/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
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

package org.graalvm.visualvm.lib.charts;

/**
 *
 * @author Jiri Sedlacek
 */
public interface ChartConfigurationListener {

    // Called immediately after the change
    public void offsetChanged(long oldOffsetX, long oldOffsetY,
                              long newOffsetX, long newOffsetY);

    // Called immediately after the change
    public void scaleChanged(double oldScaleX, double oldScaleY,
                             double newScaleX, double newScaleY);

    // Called immediately after the change
    public void dataBoundsChanged(long dataOffsetX, long dataOffsetY,
                                  long dataWidth, long dataHeight,
                                  long oldDataOffsetX, long oldDataOffsetY,
                                  long oldDataWidth, long oldDataHeight);

    // Called before paintContents(Graphics, Rectangle)
    public void contentsWillBeUpdated(long offsetX, long offsetY,
                            double scaleX, double scaleY,
                            long lastOffsetX, long lastOffsetY,
                            double lastScaleX, double lastScaleY);

    // Called after paintContents(Graphics, Rectangle)
    // The actual change may/not be already displayed depending on buffer type
    public void contentsUpdated(long offsetX, long offsetY,
                            double scaleX, double scaleY,
                            long lastOffsetX, long lastOffsetY,
                            double lastScaleX, double lastScaleY,
                            int shiftX, int shiftY);


    public abstract class Adapter implements ChartConfigurationListener {
        
        public void offsetChanged(long oldOffsetX, long oldOffsetY,
                                  long newOffsetX, long newOffsetY) {}

        public void scaleChanged(double oldScaleX, double oldScaleY,
                                 double newScaleX, double newScaleY) {}

        public void dataBoundsChanged(long dataOffsetX, long dataOffsetY,
                                      long dataWidth, long dataHeight,
                                      long oldDataOffsetX, long oldDataOffsetY,
                                      long oldDataWidth, long oldDataHeight) {}

        public void contentsWillBeUpdated(long offsetX, long offsetY,
                                double scaleX, double scaleY,
                                long lastOffsetX, long lastOffsetY,
                                double lastScaleX, double lastScaleY) {}

        public void contentsUpdated(long offsetX, long offsetY,
                                double scaleX, double scaleY,
                                long lastOffsetX, long lastOffsetY,
                                double lastScaleX, double lastScaleY,
                                int shiftX, int shiftY) {}

    }

}
