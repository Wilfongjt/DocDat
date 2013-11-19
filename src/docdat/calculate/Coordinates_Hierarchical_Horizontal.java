/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.calculate;

import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.utils.Constants;



/**
 *
 * @author wilfongj
 */
public class Coordinates_Hierarchical_Horizontal  extends Coordinates_Hierarchical{

  public Coordinates_Hierarchical_Horizontal(PseudoElements chart) {
    super(chart);
  }
  public String getClassName(){return "Coordinates_Hierarchical_Horizontal";}

  @Override
  public int getY(PseudoElement step){
    return (50 + Constants.MinYOffset) * step.getLevel() ;
  }

  @Override
  public int getX(PseudoElement step) {
    int sum = 0;

    if (step == null) {
      return 0;
    }

    sum = getActualWidth(step) / 2;
    // already calc X
    if (step.getIdx() == 0) {
      return sum;
    }
    //
    // backup through all elements 
    // steps with no child are added to width
    // why??????????
    for (int i = step.getIdx() - 1; i > 0; i--) {

      PseudoElement prevElem = (PseudoElement) getElements().get(i);
      if ( ! getElements().hasChild(prevElem) ) {
        sum += prevElem.getW() + Constants.MinXOffset;
      }
    }
    return sum;
  }


}
