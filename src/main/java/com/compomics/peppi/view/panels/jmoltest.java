package com.compomics.peppi.view.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;
import org.jmol.adapter.smarter.SmarterJmolAdapter;
import org.jmol.api.JmolAdapter;
import org.jmol.api.JmolSimpleViewer;
 
 
/**public class jmoltest {
    XJmolSimpleViewer viewer;
    Structure structure; 
 
    JmolPanel jmolPanel;
    JFrame frame ;
 
 
    public jmoltest() {
        frame = new JFrame();
        frame.addWindowListener(new ApplicationCloser());
        Container contentPane = frame.getContentPane();
        jmolPanel = new JmolPanel();
 
        jmolPanel.setPreferredSize(new Dimension(200,200));
        contentPane.add(jmolPanel);
 
        frame.pack();
        frame.setVisible(true); 
 
    }
    public void setStructure(Structure s) {
 
        frame.setName(s.getPDBCode());
 
        // actually this is very simple
        // just convert the structure to a PDB file
 
        String pdb = s.toPDB();
 
        structure = s;
        JmolSimpleViewer viewer = jmolPanel.getViewer();
 
        // Jmol could also read the file directly from your file system
        //viewer.openFile("/Path/To/PDB/1tim.pdb");
 
        // send the PDB file to Jmol.
        // there are also other ways to interact with Jmol, but they require more
        // code. See the link to SPICE above...
        viewer.openStringInline(pdb);
        viewer.evalString("select *; spacefill off; wireframe off; backbone 0.4;  ");
        viewer.evalString("color chain;  ");
        this.viewer = viewer;
 
    }
 
    public void setTitle(String label){
        frame.setTitle(label);
    }
 
    public JmolSimpleViewer getViewer(){
 
        return jmolPanel.getViewer();
    }
 
 
    static class ApplicationCloser extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }*/
 
    public class jmoltest extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = -3661941083797644242L;
        JmolSimpleViewer viewer;
        JmolAdapter adapter;
        jmoltest() {
            adapter = new SmarterJmolAdapter();
            viewer = JmolSimpleViewer.allocateSimpleViewer(this, adapter);
 
        }
 
        public JmolSimpleViewer getViewer() {
            return viewer;
        }
 
        public void executeCmd(String rasmolScript){
            viewer.evalString(rasmolScript);
        }
 
 
        final Dimension currentSize = new Dimension();
        final Rectangle rectClip = new Rectangle();
 
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            viewer.renderScreenImage(g, this.getWidth(), this.getHeight());
        }
    }
 //}