/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.calculate;

import docdat.id.PseudoElement;
import docdat.id.PseudoElements;


/**
 *
 * @author wilfongj
 */
public class Coordinates_Hierarchical implements CoordinatesInterface {

    public Coordinates_Hierarchical(PseudoElements chart) {
        setElements(chart);

    }
    
    public String getClassName(){return "Coordinates_Hierarchical";}
    
    PseudoElements chart = null;

    
    public PseudoElements getElements() {
        return chart;
    }

    public void setElements(PseudoElements chart) {
        this.chart = chart;
    }

    public int getX(PseudoElement step) {
        int sum = 0;
        return sum;
    }

    public int getY(PseudoElement step) {
        int sum = 0;

        return sum;
    }

    @Override
    public void calculate() {
        //System.out.println(".................. Calulate:"+ getClassName());
        //int aw = getActualWidth(getElements());
        //////System.out.println("calculate size="+getElements().getSteps().size());
        if (getElements().size() == 0) {
            //System.out.println("calculate reports chart has no steps.");
            return;
        }


        for (int i = 0; i < getElements().size(); i++) {
            //Step step = (Step) getElements().get(i);

            PseudoElement step = (PseudoElement) getElements().get(i);


            switch (step.getType()) {
                case 0:
                    break;
                case 1: // top level 0

                    step.setX(getX(step));
                    // step.setY((50 + Constants.MinYOffset) * step.getLevel() );
                    step.setY(getY(step));
                    ////System.out.println("type 1 set X and Y");
                    break;
                case 2:
                case 3:
                    step.setX(getX(step));
                    ////System.out.println(" " + (50 + Constants.MinYOffset) * step.getLevel());
                    //step.setY((50 + Constants.MinYOffset) * step.getLevel());
                    step.setY(getY(step));
                    ////System.out.println("type 2,3 set X "+step.getX()+"and Y="+step.getY());
                    break;

            }

        }
        getElements().setGraphable(true);
    }
/*
    public int getLevelOffset(PseudoElement firstStep) {
        double summ = 0;

        return (int) summ;

    }
*/
    /**
     *
     * @param firstStep
     * @return
     */
    //public int getActualWidth(Step firstStep) {
    @Override
    public int getActualWidth(PseudoElement firstStep) {
        int sum = 0;

        if (firstStep == null) {
            ////System.out.println("aw: 0");
            return 0;
        }

        // find posintion of firststep
        int position = 0;
        for (int i = 0; i < getElements().size(); i++) {
            //if ( ((Step)getElements().get(i)).getId().equals(firstStep.getId())) {
            if (((PseudoElement) getElements().get(i)).getId().equals(firstStep.getId())) {
                position = i;
            }
        }

        //position++;  // move to the very next step
        // check if step exists
        if ((position + 1) >= getElements().size()) {
            //////System.out.println("x 1 pos:"+position);
            return firstStep.getW();
        }
//////System.out.println("position: "+position);
        //int lastlevel =  Constants.MaxNumberOfSteps;

        //Step lastStep = firstStep;
        PseudoElement lastStep = firstStep;
        for (int i = position; i < getElements().size(); i++) {

            //Step currStep = (Step)getElements().get(i);
            PseudoElement currStep = (PseudoElement) getElements().get(i);
            //////System.out.println( "["+currStep.getId()+"]" );

            boolean hasChild = false;

            if (getElements().size() > i + 1) {
                //Step child = (Step)getElements().get(i + 1);
                PseudoElement child = (PseudoElement) getElements().get(i + 1);
                if (child.getLevel() > currStep.getLevel()) {
                    hasChild = true;
                    //////System.out.print(currStep.getId() + " has child " );
                }
            }

            switch (currStep.getType()) {
                case -1:
                    break;
                case 0: // comment
                    break;
                case 1: // chart
                case 2: // command and no id
                case 3: // command and id
                    if (currStep.getLevel() > firstStep.getLevel()) {
                        if (!hasChild) {
                            // ////System.out.print(" sum = " + sum + " + "+ currStep.getW());
                            sum += currStep.getW();
                        }
                    }
                    lastStep = currStep;

                    break;

                default:
                ////System.out.println("default id=" + currStep.getId());

            }
            if (!firstStep.getId().equals(lastStep.getId()) && firstStep.getLevel() >= lastStep.getLevel()) {
                //////System.out.print(" break");
                break;
            }

        }

        if (sum == 0) {

            sum = firstStep.getW();
        }
        return sum;
    }

    @Override
    public int getActualHeight(PseudoElement startStep) {
        int sum = 0;

        if (startStep == null) {
            return 0;
        }

        // find posintion of firststep
        int position = 0;
        for (int i = 0; i < getElements().size(); i++) {
            if (((PseudoElement) getElements().get(i)).getId().equals(startStep.getId())) {
                position = i;
            }
        }

        //position++;  // move to the very next step
        // check if step exists
        if ((position + 1) >= getElements().size()) {
            return startStep.getH();//.getW();
        }

        //
        PseudoElement lastStep = startStep;
        for (int i = position; i < getElements().size(); i++) {
            PseudoElement currStep = (PseudoElement) getElements().get(i);

            boolean hasChild = false;  // assume that this step is one that contributes to the actual height
            // determine if current step has child
            if (getElements().size() > i + 1) { // make sure there is a step after current step
                PseudoElement child = (PseudoElement) getElements().get(i + 1);
                if (child.getLevel() > currStep.getLevel()) { // higher level indicates that next is a child of current
                    hasChild = true; // child implies that this step is not one to consider in the total height
                }
            }
            // make sure current step is a chartable item before summing height
            switch (currStep.getType()) {
                case -1:
                    break;
                case 0: // comment
                    break;
                case 1: // chart
                case 2: // command and no id
                case 3: // command and id
                    if (currStep.getLevel() > startStep.getLevel()) {
                        if (!hasChild) {  // elements with no children contribute to the sum of the actual height
                            sum += currStep.getH(); // currStep.getH();
                        }
                    }
                    lastStep = currStep;

                    break;

                default:
                // skip

            }

            if (!startStep.getId().equals(lastStep.getId()) && startStep.getLevel() >= lastStep.getLevel()) {
                // when the last step level gets higher or equal to the starting step then there are no more childern for the start step ... so stop checking
                break;
            }

        }

        if (sum == 0) {

            sum = startStep.getH(); //.getW();
        }


        return sum;
    }
}
