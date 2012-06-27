/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.highchart.utils;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Prova {
  Display d = new Display();

  Prova() {
    Shell s = new Shell(d);
    s.setSize(500, 500);
    s.open();
    ChildShell cs = new ChildShell(s);
    while (!s.isDisposed()) {
      if (!d.readAndDispatch())
        d.sleep();
    }
    d.dispose();
  }
    public static void main(String[] args){
      new Prova();
    }   
}
class ChildShell {

  ChildShell(Shell parent) {
    Shell child = new Shell(parent);
    child.setSize(200, 200);
    child.open();
  }
}
