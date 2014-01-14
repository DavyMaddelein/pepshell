package com.compomics.pepshell.model.drawable;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.Drawable;
import java.awt.Graphics;

/**
 *
 * @author Davy
 */
public class DrawableDomain extends Domain implements Drawable {

    public DrawableDomain(String domainName, int startPosition, int stopPosition, String source) {
        super(domainName, startPosition, stopPosition, source);
    }

    public void draw(int x, int y, Graphics g) throws UndrawableException {
        g.setColor(ProgramVariables.DOMAINCOLOR);
        g.drawRect(x, y, (int) Math.ceil((this.getStopPosition() - this.getStartPosition()) / ProgramVariables.SCALE), ProgramVariables.VERTICALSIZE);
        g.drawString(this.getDomainName(), (int) Math.ceil(x + this.getStartPosition() / ProgramVariables.SCALE), y + 7);

    }
}
