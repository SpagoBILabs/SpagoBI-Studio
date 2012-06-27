/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.extchart.editors.pages.editorComponent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class DragDropTable {
  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setLayout(new FillLayout());
    
    Table table = new Table(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);

    Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
    DragSource source = new DragSource(table, DND.DROP_MOVE | DND.DROP_COPY);
    source.setTransfer(types);
    
    source.addDragListener(new DragSourceAdapter() {
      public void dragSetData(DragSourceEvent event) {
        // Get the selected items in the drag source
        DragSource ds = (DragSource) event.widget;
        Table table = (Table) ds.getControl();
        TableItem[] selection = table.getSelection();

        StringBuffer buff = new StringBuffer();
        for (int i = 0, n = selection.length; i < n; i++) {
          buff.append(selection[i].getText());
        }

         event.data = buff.toString();
      }
    });

     //Create the drop target
        DropTarget target = new DropTarget(table, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT);
        target.setTransfer(types);
        target.addDropListener(new DropTargetAdapter() {
          public void dragEnter(DropTargetEvent event) {
            if (event.detail == DND.DROP_DEFAULT) {
              event.detail = (event.operations & DND.DROP_COPY) != 0 ? DND.DROP_COPY : DND.DROP_NONE;
            }
    
            // Allow dropping text only
            for (int i = 0, n = event.dataTypes.length; i < n; i++) {
              if (TextTransfer.getInstance().isSupportedType(event.dataTypes[i])) {
                event.currentDataType = event.dataTypes[i];
              }
            }
          }
    
          public void dragOver(DropTargetEvent event) {
             event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_SCROLL;
          }
          public void drop(DropTargetEvent event) {
            if (TextTransfer.getInstance().isSupportedType(event.currentDataType)) {
              // Get the dropped data
              DropTarget target = (DropTarget) event.widget;
              Table table = (Table) target.getControl();
              String data = (String) event.data;
    
              // Create a new item in the table to hold the dropped data
              TableItem item = new TableItem(table, SWT.NONE);
              item.setText(new String[] { data });
              table.redraw();
            }
          }
        });
    
    
    TableColumn column = new TableColumn(table, SWT.NONE);

    // Seed the table
    TableItem item = new TableItem(table, SWT.NONE);
    item.setText(new String[] { "A" });
    item = new TableItem(table, SWT.NONE);
    item.setText(new String[] { "B" });
    item = new TableItem(table, SWT.BORDER);
    item.setText(new String[] { "C" });

    column.pack();
    shell.open();

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
  }
}
	
