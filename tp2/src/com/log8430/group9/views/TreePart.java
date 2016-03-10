/*******************************************************************************
 * Copyright (c) 2010 - 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <lars.Vogel@gmail.com> - Bug 419770
 *******************************************************************************/
package com.log8430.group9.views;

import java.io.File;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreePart {
	protected Tree fileTree;

	@PostConstruct
	public void createComposite(Composite parent) {
		fileTree = new Tree(parent, SWT.SINGLE | SWT.BORDER);
		File[] roots = File.listRoots();
	    for (int i = 0; i < roots.length; i++) {
	      File file = roots[i];
	      TreeItem treeItem = new TreeItem(fileTree, SWT.NONE);
	      treeItem.setText(file.getAbsolutePath());
	      treeItem.setData(file);
	      new TreeItem(treeItem, SWT.NONE);
	    }
	    fileTree.addListener(SWT.Expand, new Listener() {
	      public void handleEvent(Event e) {
	        TreeItem item = (TreeItem) e.item;
	        if (item == null)
	          return;
	        if (item.getItemCount() == 1) {
	          TreeItem firstItem = item.getItems()[0];
	          if (firstItem.getData() != null)
	            return;
	          firstItem.dispose();
	        } else {
	          return;
	        }
	        File root = (File) item.getData();
	        File[] files = root.listFiles();
	        if (files == null)
	          return;
	        for (int i = 0; i < files.length; i++) {
	          File file = files[i];
	          if (file.isDirectory()) {
	            TreeItem treeItem = new TreeItem(item, SWT.NONE);
	            treeItem.setText(file.getName());
	            treeItem.setData(file);
	            new TreeItem(treeItem, SWT.NONE);
	          }
	        }
	      }
	    });
	    
	    fileTree.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	          TreeItem item = (TreeItem) e.item;
	          if (item == null)
	            return;
	          final File root = (File) item.getData();
	          System.out.println(root.getAbsolutePath());
	        }
	      });
		
	}

}