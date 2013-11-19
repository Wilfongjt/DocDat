/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.id;



/**
 *
 * @author wilfongj
 */
public class Id {

  public Id() {
  }

  // include in xml
  private String id = "";
  private String title = "";
  private String _Name = "";

  // not in xml
  private int Position=-1;
  public int getPosition() {
    return Position;
  }
  public void setPosition(int Position) {
    this.Position = Position;
  }

  public String getName() {
    return _Name;
  }

  public void setName(String _Name) {
    this._Name = _Name;
  }

/*
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
 * 
 */
  private String description = "";
  //private int x = 0;
  //private int y = 0;
 // private int h = Constants.Height;
  //private int w = Constants.Width;
  private int level = 0; // relative row
  private int childNo = 0;
  // processing
  private int type = 0; // 0=comment, 1=wbs, 2=step with id, 3=step with id and desc
  private int idx = -1;
  private int DuplicateCount = 0;

  public int getDuplicateCount() {
    return DuplicateCount;
  }

  public void setDuplicateCount(int DuplicateCount) {
    this.DuplicateCount = DuplicateCount;
  }

  public int getIdx() {
    return idx;
  }

  public void setIdx(int idx) {
    this.idx = idx;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
/**/
  public String getDescription() {
    if(description.trim().length()==0){
      return getName();
    }
    return description;

  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getChildNo() {
    return childNo;
  }

  public void setChildNo(int child) {
    this.childNo = child;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
  /*
  public int getH() {
    return h;
  }

  public void setH(int h) {
    this.h = h;
  }
 
  public int getW() {
    return w;
  }

  public void setW(int w) {
    this.w = w;
  }
 */

/*
  public int getX() {
    //return x;
    int rc = Constants.XDefault;
    Attribute att = getAttributes().getAttribute(Constants.XName);
    if (att != null) {
      rc = new Integer(att.getValue()).intValue();
    }
    return rc;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }*/
}
