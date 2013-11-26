/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.load;

import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.utils.Attribute;
import docdat.utils.Constants;
import java.util.ArrayList;

/**
 *
 * @author wilfongj
 */
public class FixerHelper {

  public FixerHelper(PseudoElements pe) {

    setElements(pe);
    loadErrors();


  }
  private PseudoElements Elements = null;

  public PseudoElements getElements() {
    return Elements;
  }

  public void setElements(PseudoElements Elements) {
    this.Elements = Elements;
  }
  private ArrayList<Fix> Errors = new ArrayList<Fix>();
  //private ArrayList<String> Fixes = new ArrayList<String>();

  protected ArrayList getErrors() {
    return Errors;
  }

  protected void setErrors(ArrayList Errors) {
    this.Errors = Errors;
  }
  /*
  protected ArrayList getFixes() {
  return Fixes;
  }
  
  protected void setFixes(ArrayList Fixes) {
  this.Fixes = Fixes;
  }
   */
  /*
   * Errors are one to one with fixes
   */

  protected void loadErrors() {
    getErrors().add(new Fix(Constants.Errors.ActivityHistoryBaseTab, Constants.Fixes.ActivityHistoryScreenTab));
    getErrors().add(new Fix(Constants.Errors.ActivityHistoryTab, Constants.Fixes.ActivityHistoryScreenTab));

    getErrors().add(new Fix(Constants.Errors.ChangesHistoryBaseTab, Constants.Fixes.ChangeHistoryScreenTab));
    getErrors().add(new Fix(Constants.Errors.ChangesHistoryTab, Constants.Fixes.ChangeHistoryScreenTab));

    getErrors().add(new Fix(Constants.Errors.ChangesTab, Constants.Fixes.ChangeTab));

    getErrors().add(new Fix(Constants.Errors.InstructionsBaseTab, Constants.Fixes.InstructionScreenTab));
    getErrors().add(new Fix(Constants.Errors.InstructionsScreenTab, Constants.Fixes.InstructionScreenTab));
    getErrors().add(new Fix(Constants.Errors.ShowInformationBaseTab, Constants.Fixes.ShowInformationScreenTab));
    getErrors().add(new Fix(Constants.Errors.ShowScreenTab, Constants.Fixes.ShowInformationScreenTab));
    getErrors().add(new Fix(Constants.Errors.InvitationsBaseTab, Constants.Fixes.InvitationScreenTab));

    getErrors().add(new Fix(Constants.Errors.InstructionDefinitions, Constants.Fixes.InstructionDefinition));
    getErrors().add(new Fix(Constants.Errors.LoginDefinitions, Constants.Fixes.LoginDefinition));
    getErrors().add(new Fix(Constants.Errors.MemberInformationDefinitions, Constants.Fixes.MemberInformationDefinition));
    getErrors().add(new Fix(Constants.Errors.DegreesandCertificatesDefinitions, Constants.Fixes.DegreesandCertificatesDefinition));
    getErrors().add(new Fix(Constants.Errors.PortfolioDefinitions, Constants.Fixes.PortfolioDefinition));
    getErrors().add(new Fix(Constants.Errors.ResumeDefinitions, Constants.Fixes.ResumeDefinition));
    getErrors().add(new Fix(Constants.Errors.ReminderDefinitions, Constants.Fixes.ReminderDefinition));
    getErrors().add(new Fix(Constants.Errors.ExperienceandExpertiseDefinitions, Constants.Fixes.ExperienceandExpertiseDefinition));
    getErrors().add(new Fix(Constants.Errors.ShareandAssistDefinitions, Constants.Fixes.ShareandAssistDefinition));
    getErrors().add(new Fix(Constants.Errors.InstructionsTab, Constants.Fixes.InstructionTab));
    getErrors().add(new Fix(Constants.Errors.InvitationsTab, Constants.Fixes.InvitationTab));
    getErrors().add(new Fix(Constants.Errors.Modal_Dialog, Constants.Fixes.Pop_Over_Modal_Window));

  }

  /*  protected void loadFixes() {
  getFixes().add(;
  getFixes().add();
  getFixes().add();
  getFixes().add();
  getFixes().add();
  getFixes().add();
  getFixes().add(  );
  
  
  }*/
  public void fix() {
    // check all elements
    for (int i = 0; i < getElements().size(); i++) {
      // test for elements with ref because those need to be correct
      PseudoElement el = getElements().getElement(i);
      //Attribute att = el.getAttributes().getAttribute(Constants.Attributes.REF);
      ////System.out.println("  -------  fix A ");
      //if( att != null && att.getValue().equalsIgnoreCase( Constants.TRUE ) ){ 
      // check name against all the known spelling errors
      for (int k = 0; k < getErrors().size(); k++) {
        Fix fix = (Fix) getErrors().get(k);
        if (fix.getFrom().equalsIgnoreCase(el.getName().trim())) {
//System.out.println(" fix 1: " + fix.getFrom());
          el.setName(fix.getTo());

          break;
        }
        
        if (el.getName().toLowerCase().indexOf( fix.getFrom().toLowerCase() ) > -1 ) {
//System.out.println(" fix 2: " + fix.getFrom());
          el.setName(  replacePartsOfString(el.getName(),  fix.getFrom() , fix.getTo() )  );
        }
      }
      //}
    }
  }

  public class Fix {

    public Fix(String from, String to) {
      setFrom(from);
      setTo(to);
    }
    private String From = "";
    private String To = "";

    public String getFrom() {
      return From;
    }

    public void setFrom(String From) {
      this.From = From;
    }

    public String getTo() {
      return To;
    }

    public void setTo(String To) {
      this.To = To;
    }
  }

  protected String replacePartsOfString(String source,  String find_string , String replace_with) {
    String rc = source;

    while (rc.toLowerCase().indexOf( find_string.toLowerCase()) >= 0) {
      int pos1 = rc.toLowerCase().indexOf(find_string.toLowerCase());

      if (pos1 == -1) {
        break;
      }
      int pos2 = pos1 + (find_string.length() - 1);
      String one = rc.substring(0, pos1);  // get front part of string
      String two = "";
      if (rc.length() > pos2 + 1) {
        two = rc.substring(pos2 + 1);  // get back end of string
      }
      rc = one + replace_with + two ;
     
      //replaced_cnt++;
    }
    return rc;
  }
}
