/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.assembler.text;


/**
 *
 * @author wilfongj
 */
public class TEXT_Assembler_Helper {

    public TEXT_Assembler_Helper(String project_path) {
        setProjectPath(project_path);
    }
    private String ProjectPath = "";

    public String getProjectPath() {
        return ProjectPath;
    }

    public void setProjectPath(String ProjectPath) {
        this.ProjectPath = ProjectPath;
    }

    public String getProjectName() {
        String rc = getProjectPath();

        if (!rc.contains("\\")) {
            return "no project name";
        }

        if (rc.lastIndexOf("\\") == rc.length() - 1) {
            rc = rc.substring(0, rc.length() - 1);
        }
        if (!rc.contains("\\")) {
            return "no project name";
        }
        return rc.substring(rc.lastIndexOf('\\')+1);
    }
/*
    public boolean isMockup(String line) {
        if (line.startsWith("<mockup")) {
            return true;
        }
        return false;
    }
*/
    /*
    public boolean isAsset(String line) {

        if (line.startsWith("<src>")) {
            System.out.println(" asset 1");
            return false;
        }

        if (!line.contains("/assets/")) {
            System.out.println(" asset 2");
            return false;
        }

        if (line.contains("/symbols.")) {
            System.out.println(" asset 3");
            return false;
        }

        return true;
    }
*/
    /*
    public boolean isSymbol(String line) {

        if (line.startsWith("<src>")) {
            System.out.println(" symbol 1");
            return false;
        }

        if (!line.contains("/assets/")) {
            System.out.println(" symbol 2");
            return false;
        }

        if (!line.contains("/symbols.")) {
            System.out.println(" symbol 3");
            return false;
        }

        return true;
    }
     * 
     */
    /*
     * get file name
     */
/*
    public String getControlFileName(String line) {
        String rc = "";
        int i = 0;
        for (i = 0; i < line.length() && line.charAt(i) != '>'; i++);


        for (; i < line.length() + 1 && line.charAt(i) != '#' && line.charAt(i) != '<'; i++) {
            int ch = line.charAt(i);
            if (ch != '>' && ch != '<') {
                rc += line.charAt(i);
            }
        }
        if (rc.length() > 0 && rc.startsWith("./")) {
            rc = rc.substring(2);
        }
        rc = rc.replace("/", "\\");
        return rc;
    }
*/
    /*
     * get the name of the 
     */
    /*
    public String getControName(String line) {
        String rc = "";
        int i = 0;
        for (i = 0; i < line.length() && line.charAt(i) != '#'; i++);

        for (i = i++; i < line.length() && line.charAt(i) != '<'; i++) {
            int ch = line.charAt(i);
            if (ch != '#' && ch != '<') {
                rc += line.charAt(i);
            }
        }


        return rc;
    }
     * 
     */
}
