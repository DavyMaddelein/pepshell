package com.compomics.peppi.view.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Davy
 */
public class projectTreePanel extends JPanel implements DragGestureListener, DropTargetListener, DragSourceListener {

    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    DragSource dragSource;
    DropTarget dropTarget;
    TreeNode dropTargetNode = null;
    TreeNode draggedNode = null;
    static DataFlavor localObjectFlavor;
    static DataFlavor[] supportedFlavors = {localObjectFlavor};

    public projectTreePanel() {
        super(new GridLayout(1, 0));

        rootNode = new DefaultMutableTreeNode("Analysis");
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);
        tree.setCellRenderer(new DnDTreeCellRenderer());
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.setDragEnabled(true);
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }

    /**
     * Remove all nodes except the root node.
     */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    /**
     * Remove the currently selected node.
     */
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
            }
        }
    }

    /**
     * Add child to the currently selected node.
     */
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = rootNode;

        return addObject(parentNode, child, true, null);
    }

    public DefaultMutableTreeNode addObject(Object child, ImageIcon anIcon) {
        DefaultMutableTreeNode parentNode = rootNode;

        return addObject(parentNode, child, true, anIcon);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
            Object child) {
        return addObject(parent, child, false, null);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible, Icon anIcon) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        if (parent == null) {
            parent = rootNode;
        }

        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        Enumeration children = parent.children();
        boolean alreadyAdded = false;
        while (children.hasMoreElements()) {
            if (childNode == children.nextElement()) {
                alreadyAdded = true;
            }
        }
        if (alreadyAdded == false) {
            treeModel.insertNodeInto(childNode, parent,
                    parent.getChildCount());
        }
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    public TreeModel getTreeModel() {
        return treeModel;
    }

    public void dragGestureRecognized(DragGestureEvent dge) {
        System.out.println("dragGestureRecognized");
        // find object at this x,y
        Point clickPoint = dge.getDragOrigin();
        TreePath path = tree.getPathForLocation(clickPoint.x, clickPoint.y);
        if (path == null) {
            return;
        }
        draggedNode = (TreeNode) path.getLastPathComponent();
        Transferable trans = new RJLTransferable(draggedNode);
        dragSource.startDrag(dge, Cursor.getDefaultCursor(),
                trans, this);
    }
    // DragSourceListener events

    public void dragDropEnd(DragSourceDropEvent dsde) {

        System.out.println("dragDropEnd()");
        dropTargetNode = null;
        draggedNode = null;
        repaint();

    }

    public void dragEnter(DropTargetDragEvent dtde) {
        dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
    }

    public void dragOver(DropTargetDragEvent dtde) {
        // figure out which cell it's over, no drag to self
        Point dragPoint = dtde.getLocation();
        TreePath path = tree.getPathForLocation(dragPoint.x, dragPoint.y);
        if (path == null) {
            dropTargetNode = null;
        } else {
            dropTargetNode = (TreeNode) path.getLastPathComponent();
        }
        repaint();
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void dragExit(DropTargetEvent dte) {
    }

    public void drop(DropTargetDropEvent dtde) {
        Point dropPoint = dtde.getLocation();
        // int index = locationToIndex (dropPoint);
        TreePath path = tree.getPathForLocation(dropPoint.x, dropPoint.y);
        System.out.println("drop path is " + path);
        boolean dropped = false;
        try {
            dtde.acceptDrop(DnDConstants.ACTION_MOVE);
            System.out.println("accepted");
            Object droppedObject =
                    dtde.getTransferable().getTransferData(localObjectFlavor);
            MutableTreeNode droppedNode = null;
            if (droppedObject instanceof MutableTreeNode) {
                // remove from old location
                droppedNode = (MutableTreeNode) droppedObject;
                treeModel.removeNodeFromParent(droppedNode);
            } else {
                droppedNode = new DefaultMutableTreeNode(droppedObject);
            }
            // insert into spec'd path. if dropped into a parent 
            DefaultMutableTreeNode dropNode =
                    (DefaultMutableTreeNode) path.getLastPathComponent();
            if (dropNode.isLeaf()) {
                DefaultMutableTreeNode parent =
                        (DefaultMutableTreeNode) dropNode.getParent();
                int index = parent.getIndex(dropNode);
                treeModel.insertNodeInto(droppedNode,
                        parent, index);
            } else {
                treeModel.insertNodeInto(droppedNode, dropNode,
                        dropNode.getChildCount());
            }
            dropped = true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        dtde.dropComplete(dropped);
    }

    public void dragEnter(DragSourceDragEvent dsde) {
    }

    public void dragOver(DragSourceDragEvent dsde) {
    }

    public void dropActionChanged(DragSourceDragEvent dsde) {
    }

    public void dragExit(DragSourceEvent dse) {
    }

    class RJLTransferable implements Transferable {

        Object object;

        public RJLTransferable(Object o) {
            object = o;
        }

        public Object getTransferData(DataFlavor df)
                throws UnsupportedFlavorException, IOException {
            if (isDataFlavorSupported(df)) {
                return object;
            } else {
                throw new UnsupportedFlavorException(df);
            }
        }

        public boolean isDataFlavorSupported(DataFlavor df) {
            return (df.equals(localObjectFlavor));
        }

        public DataFlavor[] getTransferDataFlavors() {
            return supportedFlavors;
        }
    }

    class DnDTreeCellRenderer
            extends DefaultTreeCellRenderer {

        boolean isTargetNode;
        boolean isTargetNodeLeaf;
        boolean isLastItem;
        int BOTTOM_PAD = 30;

        public DnDTreeCellRenderer() {
            super();
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree,
                Object value,
                boolean isSelected,
                boolean isExpanded,
                boolean isLeaf,
                int row,
                boolean hasFocus) {
            isTargetNode = (value == dropTargetNode);
            isTargetNodeLeaf = (isTargetNode
                    && ((TreeNode) value).isLeaf());
            // isLastItem = (index == list.getModel().getSize()-1); 
            boolean showSelected = isSelected
                    & (dropTargetNode == null);
            return super.getTreeCellRendererComponent(tree, value,
                    isSelected, isExpanded, isLeaf, row, hasFocus);
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
