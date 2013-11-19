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
public class Coordinates_Flow implements CoordinatesInterface {

    public Coordinates_Flow(PseudoElements chart) {
        setElements(chart);

    }
    PseudoElements elements = null;

    //public <Step> OrgChart  getElements() {
    public PseudoElements getElements() {
        return elements;
    }

    public void setElements(PseudoElements chart) {
        this.elements = chart;
    }

    @Override
    public void calculate() {
        //System.out.println(".................. ");

        if (getElements().size() == 0) {
            System.out.println("calculate reports chart has no steps.");
            return;
        }

        for (int i = 0; i < getElements().size(); i++) {

            PseudoElement step = (PseudoElement) getElements().get(i);

            switch (step.getType()) {
                case 0:
                    break;
                case 1: // top level 0
                    step.setX(getX(step));
                    step.setY(getY(step));

                    break;
                case 2:
                case 3:
                    step.setX(getX(step));

                    step.setY(getY(step));

                    break;

            }

        }
        getElements().setGraphable(true);
    }

    /*
     * targetElem is the element for which a horiziontal x value is needed
     */
    @Override
    public int getX(PseudoElement step) {
        int sum = 0;

        if (step == null) {
            return 0;
        }
        System.out.print(" [" + step.getName() + "]");
        System.out.print("d[" + isDangle(step) + "]");
        System.out.print("p[" + step.getPosition() + "] ");
        System.out.print("c[" + step.getChildNo() + "]");
        System.out.print(" w[" + step.getW() + "]");
        System.out.print(" aw[" + getActualWidth(step) + "]");
        System.out.print(" lw[" + getLeftWidth(step) + "]");
        System.out.print(" rw[" + getRightWidth(step) + "]");
        System.out.print(": ");
        System.out.println("");

        int dangleWidth = 0;
        int k = 0;
        PseudoElement parent = step; //getElements().getParent(step);
        //  if (isDangle(parent)) {
        //      parent = getElements().getParent(parent);  // dangles
        //  }
        // System.out.print("[+" + step.getName() + "  " + (getActualWidth(step) / 2) + "]");
        //sum = getActualWidth(step) / 2;
        int w = 0;
        while (parent != null && !parent.getId().equals("0")) {
            // handle conditions and tops
            System.out.print("+" + "[" + parent.getName() + ", ");


            if (isTop(parent) && isCondition(parent)) {
                int direction = getDirection(parent);

                switch (direction) {
                    case Constants.Directions.NEGATIVE:
                        w = (getRightWidth(parent));
                        sum += direction * w;
                        System.out.print("a" + w);
                        break;
                    case 0:
                        break;
                    case Constants.Directions.POSITIVE:
                        // System.out.print("+L1");
                        w = (getLeftWidth(parent));
                        sum += direction * w;
                        System.out.print("b" + w);
                        break;

                }
                /*
                if (direction == Constants.Directions.POSITIVE) {
                // System.out.print("+L1");
                w = (direction * getLeftWidth(parent));
                sum += (direction * getLeftWidth(parent));
                System.out.print("a" + w);
                } else {
                //System.out.print("-R1");
                w = (direction * getRightWidth(parent));
                sum += (direction * getRightWidth(parent));
                System.out.print("b" + w);
                }
                 */
            }

            if (isTop(parent) && !isCondition(parent)) {
                int direction = getDirection(parent);
                ;
                if (direction == Constants.Directions.POSITIVE) {
                    // System.out.print("+L2");
                    w = (direction * getLeftWidth(parent));
                    sum += (direction * getLeftWidth(parent));
                    System.out.print("c" + w);
                    //sum += (direction * Constants.MinXOffset);
                } else {
                    //  System.out.print("-R2");
                    w = (direction * getRightWidth(parent));
                    sum += (direction * getRightWidth(parent));
                    System.out.print("d" + w);
                    //sum += (direction * Constants.MinXOffset);
                }
            }


            if (!isTop(parent) && isCondition(parent)) {
                System.out.print("!top and condition");
            }
            if (!isTop(parent) && !isCondition(parent)) {
                System.out.print("!top and !condition");

            }
            System.out.print("] ");

            parent = getElements().getParent(parent);

        }
        // always add the root parent
        w = (getLeftWidth(parent));
        System.out.print("[++" + parent.getName() + "," + w + "]");
        // sum += getActualWidth(parent) / 2;
        sum += w;
        System.out.println(" = " + sum);
        System.out.println("");
        System.out.println("");

        return sum;
    }

    protected int getDirection(PseudoElement elem) {
        // PseudoElement parent = getElements().getParent(elem);
        if (elem == null) {
            //System.out.print("w");
            return 1; // first elemen
        }
        if (elem.getId().equals("0")) {
            //System.out.print("x");
            return -1;  // take care of the first child of first elem
        }


        if (isDangle(elem)) { // dangles take on the direction of the parent
            PseudoElement parent = getElements().getParent(elem);
            // System.out.print("d");
            if (parent.getChildNo() == 0) {
                // System.out.print("y");
                return -1;
            }
        } else {// 0 child is left 
            if (elem.getChildNo() == 0) {
                //System.out.print("z");
                return -1;

            }
        }


        return 1;
    }
    /*
    protected int getDirection(PseudoElement elem) {
    PseudoElement parent = getElements().getParent(elem);
    if (parent == null) {
    
    return 1; // first elemen
    }
    if (parent.getLevel() == 0) {
    
    return -1;  // take care of the first child of first elem
    }
    if (elem.getChildNo() == 0) {
    
    return -1;
    
    }
    
    return 1;
    }
     */

    protected boolean isCondition(PseudoElement elem) {
        if (elem == null) {
            return false;
        }
        return elem.isCondition();
    }

    protected boolean isTop(PseudoElement elem) {
        PseudoElement parent = getElements().getParent(elem);
        if (parent == null) {

            return true; // first
        }
        if (parent.isCondition()) {

            return true;
        }
        return false;
    }

    protected boolean isDangle(PseudoElement elem) {

        PseudoElement parent = getElements().getParent(elem);
        if (parent == null) {
            // System.out.print("g ");
            return false;
        }
        if (!parent.isCondition()) {
            return true;
        }
        // System.out.print("h");
        return false;
    }

    public int getY(PseudoElement startElem) {
        int sum = 0;
        // no elem the return 0
        if (startElem == null) {
            return 0;
        }
        /*
         * find position of the startElem
         * will be working backward from there
         */
        int position = startElem.getPosition();

        // did not find
        if (position == getElements().size()) {
            return 0;
        }

        // search all items backwards
        int currLevel = startElem.getLevel();
        // System.out.print(startElem.getName() + "level(" + startElem.getLevel() + ")" + ": ");

        for (int i = position; i > 0; i--) {
            PseudoElement prevElem = (PseudoElement) getElements().get(i);
            PseudoElement parentElem = getElements().getParent(prevElem);
            // let all tasks through i.e., not Conditions
            // skip elem that are at a lesser level than start
            // force level 0 as it does not have parent
            // force the level 1 as they may be siblings of 0 not children
            // force the tasks parented by tasks to be added vertically
            if ((!parentElem.isCondition() && prevElem.getLevel() == currLevel)
                    || prevElem.getId().equalsIgnoreCase(startElem.getId())
                    || prevElem.getLevel() < currLevel) {
                currLevel = prevElem.getLevel();
                if (prevElem.isCondition()) {
                    //System.out.print("+ " + prevElem.getName() + "(" + prevElem.getLevel() + ")" + prevElem.getH() + "[a]");
                    if (i == position) {
                        sum += prevElem.getH() / 2;
                        sum += Constants.MinYOffset;
                    } else {
                        sum += prevElem.getH();
                        sum += Constants.MinYOffset;
                    }
                    // sum += 25;
                } else {
                    // if parent is conditon then parent could be task or condition

                    /* if(  parentElem == null  ){
                    sum += currElem.getH() + Constants.MinYOffset  ;
                    }*/

                    if (parentElem.isCondition()) {
                        //System.out.print("+ " + prevElem.getName() + "(" + prevElem.getLevel() + ")" + prevElem.getH() + "[b]");
                        if (i == position) {
                            sum += prevElem.getH() / 2;
                            sum += Constants.MinYOffset;
                        } else {
                            sum += prevElem.getH();
                            sum += Constants.MinYOffset;
                        }
                    } else {
                        //System.out.print("+ " + prevElem.getName() + "(" + prevElem.getLevel() + ")" + prevElem.getH() + "[c]");
                        // add current height
                        if (i == position) {
                            sum += prevElem.getH() / 2;
                            sum += Constants.MinYOffset;
                        } else {
                            sum += prevElem.getH();
                            sum += Constants.MinYOffset;
                        }
                        // add sibling heights
                    }

                }

            }

        }
        PseudoElement zeroElem = (PseudoElement) getElements().getZeroElement();
        sum += zeroElem.getH();
        sum += +Constants.MinYOffset;
        // System.out.print("+ " + zeroElem.getName() + "(" + zeroElem.getLevel() + ")" + "[d] [" + zeroElem.getH() + "]");
        //System.out.println("");
        return sum;
    }

    /*
    public int getLevelOffset(PseudoElement startElem) {
    double summ = 0;
    
    return (int) summ;
    
    }
     *
     *
     */
    /**
     *
     * @param startElem
     * @return
     */
    public int getActualWidth(PseudoElement startElem) {
        int sum = 0;
        // empty list
        if (startElem == null) {
            return 0;
        }
        int dw = 0;
        // find starting position of firststep
        int position = startElem.getPosition();
        int level = startElem.getLevel();

        if (!isTop(startElem)) {  // back up to top to get the widest elem
            for (; position >= 0; position--) {
                PseudoElement t = getElements().getElement(position);
                if (isTop(t)) {

                    level = t.getLevel();
                    // System.out.print(" bk ");
                    break;
                }
            }
        }

        //System.out.print(startElem.getName() + " i[" + position + "]p[" + startElem.getPosition() + "] L[" + startElem.getLevel() + "]");

        // start at top above current position and work down
        for (int i = position; i < getElements().size(); i++) {

            PseudoElement elem = getElements().getElement(i);

            // stop when the next elem of the same level is reached, this marks the end
            if (elem.getPosition() != position) {// skip first elem because it will have the same level and cause a premature stop
                if (elem.getLevel() <= level) {    // higher or equal level mean the branch has been traversed
                    //  System.out.print(" break p " + startElem.getPosition()+"  l "+ elem.getLevel());
                    break;
                }
            }
            // collect the max width
            if (dw < elem.getW()) {
                dw = elem.getW();
                dw += Constants.MinXOffset;
            }
            // boolean isTop = isTop(elem);
            // when the next top is found
            if (isTop(elem)) {
                //dw = elem.getW()/2;
                //dw += Constants.MinXOffset;
                // System.out.print("top [" + elem.getName() + "]");
                sum += dw;
                dw = 0;
            }



            // if (isDangle(elem)) {

            //}


        }
        if (dw > 0) {
            // System.out.print("top [ extra]");
        }
        sum += dw;
        // System.out.println(": " + sum);
        //System.out.println("");
        return sum;
    }

    /*
     * use with dangles to calc width
     */
    public int getStackWidth(PseudoElement startStep) {
        if (startStep == null) {
            return 0;
        }
        if (!isDangle(startStep)) {
            System.out.println("not a dangle");
            return 0;
        }
        // find the widest sibling

        // 1 go back to parent and get first child
        // 2 loop while dangle, collect widest sib
        // return the width
        int w = 0;
        PseudoElement parent = getElements().getParent(startStep);
        if (parent == null) {
            parent = startStep;
        }
        w = parent.getW();
        PseudoElement sib = getElements().getElement(parent.getPosition() + 1);
        int i = 1;
        while (isDangle(sib)) {
            if (w < sib.getW()) {
                w = sib.getW();
            }
            i++;
            sib = getElements().getElement(parent.getPosition() + i);

        }

        return w;

    }

    public PseudoElement getNextLeftElement(PseudoElement el) {
        PseudoElement lel = null;
        if (el == null) {
            return null;
        }

        for (int i = el.getPosition(); i < getElements().size(); i++) {
            PseudoElement te = getElements().getElement(i + 1);
            if (te == null) {
                return null;
            }
            // normal top
            if (isTop(te)) {
                // get left
                return getElements().getElement(i + 1);
            }

            if (isCondition(te) && !isCondition(el)) {
                return getElements().getElement(i + 1);
            }
        }
        return lel;
    }

    public int getLeftWidth(PseudoElement startStep) {
        int aw = 0;
        int stackwidth = 0;
        int width = 0;
        if (startStep == null) {
            //System.out.print(" a");
            return 0;
        }
        width = startStep.getW();
        // dangles are handled different

        // no children then half width is going to be half of step width
        if (!getElements().hasChild(startStep)) {
            //System.out.print(" b");
            return startStep.getW() / 2;
        }

        // get left child
        //PseudoElement leftElem = getElements().getElement(startStep.getPosition() + 1);
        PseudoElement leftElem = getNextLeftElement(startStep);
        /*if(isDangle( leftElem )){
        stackwidth = getStackWidth(leftElem);
        }*/

        boolean dangled = false;

        // step down the dangle to the end
        while (leftElem != null && isDangle(leftElem)) {

            dangled = true;
            if (width < leftElem.getW()) {
                width = leftElem.getW();
            }
            //leftElem = getElements().getElement(leftElem.getPosition() + 1);
            leftElem = getNextLeftElement(leftElem);
        }

        // get the left
        if (dangled) { // get the left after all the dangles
            leftElem = getElements().getElement(leftElem.getPosition() + 1);
        }

        if (leftElem != null) {
            System.out.print(" c ");
            aw = getActualWidth(leftElem);
            //aw += startStep.getW() / 2;
            aw += width / 2;
            aw += Constants.MinXOffset;
        }

        // aw += startStep.getW() / 2;
        //  aw += Constants.MinXOffset;
        return aw;
    }

    public int getRightWidth(PseudoElement startStep) {
        int aw = 0;
        aw = getActualWidth(startStep) - getLeftWidth(startStep);

        return aw;
    }

    public int getActualHeight(PseudoElement startStep) {
        int sum = 0;
        /*
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
        case 1: // elements
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
         */

        return sum;
    }
}
