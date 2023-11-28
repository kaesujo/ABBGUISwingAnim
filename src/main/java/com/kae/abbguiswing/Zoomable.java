package com.kae.abbguiswing;
import java.awt.event.MouseWheelEvent;

public interface Zoomable {

    void zoomIn();

    void zoomOut();

    void zoomIn(int mouseX, int mouseY);

    void zoomOut(int mouseX, int mouseY);

    void applyZoom(double factor, int mouseX, int mouseY);

    void resetZoom();

}
