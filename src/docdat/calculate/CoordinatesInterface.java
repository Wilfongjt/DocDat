/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.calculate;

import docdat.id.PseudoElement;


/**
 *
 * @author wilfongj
 */
public interface CoordinatesInterface {

    public int getX(PseudoElement step);

    public int getY(PseudoElement step);

    public int getActualWidth(PseudoElement firstStep);

    public int getActualHeight(PseudoElement startStep);

    public void calculate();
}
