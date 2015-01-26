/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshell.view.components;

import com.compomics.pepshell.model.AnalysisGroup;
import com.compomics.pepshell.model.Experiment;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Davy Maddelein
 */
public class DragAndDropTree extends JTree implements DragSourceListener, DropTargetListener, DragGestureListener {

    private static DataFlavor localObjectFlavor;

    static {
        try {
            localObjectFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
        } catch (ClassNotFoundException ex) {
            //need better way to do 
            Logger.getLogger(DragAndDropTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static DataFlavor[] supportedFlavors = {localObjectFlavor};
    private DragSource dragSource;
    private DropTarget dropTarget;
    private TreeNode dropTargetNode = null;
    private TreeNode draggedNode = null;
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Experiments");

    public DragAndDropTree() {
        super();
        setCellRenderer(new DnDTreeCellRenderer());
        setModel(new DefaultTreeModel(rootNode));
        dragSource = new DragSource();
        DragGestureRecognizer dgr = dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, this);
        dropTarget = new DropTarget(this, this);
    }

    /**
     * Remove the currently selected node.
     */
    public void removeCurrentNode() {
        TreePath currentSelection = getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if (parent != null) {
                ((DefaultTreeModel) getModel()).removeNodeFromParent(currentNode);
            }
        }
    }

    /**
     * Add child to the currently selected node.
     */
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = rootNode;
        return addObject(parentNode, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
        return addObject(parent, child, false);
    }

    DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        if (parent == null) {
            parent = rootNode;
        }

        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        //TODO check if project is already added
        Enumeration children = parent.children();
        boolean alreadyAdded = false;
        while (children.hasMoreElements()) {
            if (childNode == children.nextElement()) {
                alreadyAdded = true;
            }
        }
        if (!alreadyAdded) {
            ((DefaultTreeModel) getModel()).insertNodeInto(childNode, parent, parent.getChildCount());
        }
        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    // DragGestureListener
    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        // find object at this x,y
        Point clickPoint = dge.getDragOrigin();
        TreePath path = getPathForLocation(clickPoint.x, clickPoint.y);
        if (path == null) {
            return;
        }
        draggedNode = (TreeNode) path.getLastPathComponent();
        Transferable trans = new RJLTransferable(draggedNode);
        dragSource.startDrag(dge, Cursor.getDefaultCursor(), trans, this);
    }
    // DragSourceListener events

    @Override
    public void dragDropEnd(DragSourceDropEvent dsde) {
        dropTargetNode = null;
        draggedNode = null;
        repaint();
    }

    @Override
    public void dragEnter(DragSourceDragEvent dsde) {
    }

    @Override
    public void dragExit(DragSourceEvent dse) {
    }

    @Override
    public void dragOver(DragSourceDragEvent dsde) {
    }

    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {
    }
    // DropTargetListener events

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {

        // figure out which cell it's over, no drag to self
        Point dragPoint = dtde.getLocation();
        TreePath path = getPathForLocation(dragPoint.x, dragPoint.y);
        if (path == null) {
            dropTargetNode = null;
        } else {
            dropTargetNode = (TreeNode) path.getLastPathComponent();
        }
        repaint();
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        Point dropPoint = dtde.getLocation();
        // int index = locationToIndex (dropPoint);
        TreePath path = getPathForLocation(dropPoint.x, dropPoint.y);
        boolean dropped = false;
        try {
            dtde.acceptDrop(DnDConstants.ACTION_MOVE);
            Object droppedObject = dtde.getTransferable().getTransferData(localObjectFlavor);
            DefaultMutableTreeNode droppedNode = null;
            if (droppedObject instanceof MutableTreeNode) {
                if (((MutableTreeNode) droppedObject).getParent() != null) {
                    // remove from old location
                    droppedNode = (DefaultMutableTreeNode) droppedObject;
                    ((DefaultTreeModel) getModel()).removeNodeFromParent(droppedNode);
                } else {
                    droppedNode = new DefaultMutableTreeNode(droppedObject);
                }
            }
            // insert into spec'd path. if dropped into a parent 
            // make it last child of that parent 
            DefaultMutableTreeNode dropNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (dropNode != null) {
                if (dropNode.isLeaf()) {
                    if (dropNode.getUserObject() instanceof AnalysisGroup && droppedNode.getUserObject() != null) {
                        if (droppedNode.getUserObject() instanceof AnalysisGroup) {
                            ((MutableTreeNode) dropNode.getParent()).insert(droppedNode, 0);
                        } else {
                            ((AnalysisGroup) dropNode.getUserObject()).getExperiments().add((Experiment) droppedNode.getUserObject());
                            dropNode.insert(droppedNode, 0);
                        }
                    } else if (dropNode.isRoot()) {
                        dropNode.insert(droppedNode, 0);
                    } else {
                        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) dropNode.getParent();
                        ((DefaultTreeModel) getModel()).insertNodeInto(droppedNode, parent, 0);
                    }
                } else {
                    if (dropNode.getUserObject() instanceof AnalysisGroup && droppedObject instanceof DefaultMutableTreeNode) {
                        if (((DefaultMutableTreeNode) droppedObject).getUserObject() instanceof AnalysisGroup) {
                            dropNode.insert((MutableTreeNode) getModel().getRoot(), 0);
                        } else {
                            ((AnalysisGroup) dropNode.getUserObject()).getExperiments().add((Experiment) droppedNode.getUserObject());
                            dropNode.insert(droppedNode, 0);
                        }
                    } else {
                        dropNode.insert(droppedNode, 0);
                    }
                }
            } else {
                ((DefaultTreeModel) getModel()).insertNodeInto(droppedNode, (MutableTreeNode) getModel().getRoot(), 0);
            }
            dropped = true;
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        dtde.dropComplete(dropped);
        repaint();
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    class RJLTransferable implements Transferable {

        Object object;

        public RJLTransferable(Object o) {
            object = o;
        }

        @Override
        public Object getTransferData(DataFlavor df) throws UnsupportedFlavorException, IOException {
            if (isDataFlavorSupported(df)) {
                return object;
            } else {
                throw new UnsupportedFlavorException(df);
            }
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor df) {
            return (df.equals(localObjectFlavor));
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return supportedFlavors;
        }
    }

    class DnDTreeCellRenderer extends DefaultTreeCellRenderer {

        boolean isTargetNode;
        boolean isTargetNodeLeaf;
        boolean isLastItem;
        int BOTTOM_PAD = 30;

        public DnDTreeCellRenderer() {
            super();
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean isExpanded, boolean isLeaf, int row, boolean hasFocus) {
            isTargetNode = (value == dropTargetNode);
            isTargetNodeLeaf = (isTargetNode && ((TreeNode) value).isLeaf());
            // isLastItem = (index == list.getModel().getSize()-1); 
            boolean showSelected = isSelected
                    & (dropTargetNode == null);
            return super.getTreeCellRendererComponent(tree, value, isSelected, isExpanded, isLeaf, row, hasFocus);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (isTargetNode) {
                g.setColor(Color.black);
                if (isTargetNodeLeaf) {
                    g.drawLine(0, 0, getSize().width, 0);
                } else {
                    g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
                }
            }
        }
    }
}
