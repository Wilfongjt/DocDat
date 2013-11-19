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
public class Coordinates_Hierarchical_Vertical extends Coordinates_Hierarchical {

  public Coordinates_Hierarchical_Vertical(PseudoElements chart) {
    super(chart);
  //  ProcessLogger pl = new ProcessLogger("debug.log");
   // pl.KillLog();
  }
public String getClassName(){return "Coordinates_Hierarchical_Vertical";}
  @Override
  public int getX(PseudoElement firstStep) {
    double summ = 0;

    String parents = "";
    summ = firstStep.getW() / 2.0;
    parents =  firstStep.getId() +"[w:"+ (firstStep.getW()/2.0) + "][s:" + (Constants.MinXOffset * 3) + "] < " + firstStep.getId();
    if (firstStep.getIdx() == 0) {
      return (int) summ;
    }

    //System.out.println("getX 2");

    PseudoElement parent = (PseudoElement) getElements().getParent(firstStep);
    //while (parent.getIdx() > 0) {
    while(parent.getIdx()>0){
      parents =  parent.getId() +"[w:"+ parent.getW() + "][s:" + (Constants.MinXOffset * 3) + "] < " + parents;

      summ = summ + parent.getW();
      summ = summ + (Constants.MinXOffset * 3);

      parent = (PseudoElement) getElements().getParent(parent);
    }
    

    summ = summ + parent.getW() ;
    summ = summ + (Constants.MinXOffset * 3);
    parents =  "[sum:" +summ +"] = "+ parent.getId() +"[w:"+ parent.getW() + "][s:" + (Constants.MinXOffset * 3) + "] < " + parents ;

    //ProcessLogger pl = new ProcessLogger("debug.log");
  //  pl.Write(parents);
    return (int) summ;
  }

  @Override
  public int getY(PseudoElement step) {
    int sum = 0;

    if (step == null) {
      return 0;
    }

    sum = getActualHeight(step) / 2;
    if (step.getIdx() == 0) {
      return sum;
    }

    for (int i = step.getIdx() - 1; i > 0; i--) {

      PseudoElement nextStep = (PseudoElement) getElements().get(i);

      if (!getElements().hasChild(nextStep)) {
        sum += nextStep.getH() + (Constants.MinYOffset / 2);
      }

    }

    return sum;
  }
}
