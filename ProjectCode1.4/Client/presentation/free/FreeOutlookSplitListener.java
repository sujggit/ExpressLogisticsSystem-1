package free;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

public class FreeOutlookSplitListener extends FreeListSplitListener {

    public FreeOutlookSplitListener(FreeOutlookHeader header) {
        super(header);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!header.isShrinked()) {
            if (lastPoint != null) {
                JComponent parent = (JComponent) header.getParent();
                Dimension size = parent.getPreferredSize();
                Point thisPoint = e.getPoint();
                int xMovement = thisPoint.x - lastPoint.x;
                size.width += xMovement;
                size.width = Math.max(size.width, FreeUtil.LIST_SHRINKED_WIDTH);
                parent.setPreferredSize(size);
                header.revalidateParent();
            }
        }
    }
}
