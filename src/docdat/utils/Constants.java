/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.utils;

/**
 *
 * @author wilfongj
 */
public class Constants {

    public static final String TRUE = "true";
    public static final String DUMMYFILENAME = "dummy";
    public static final String DELIMITERS = ",;|-+";  // delimiters for lists of values stored in attribute values

    public class MindJet {

        public static final String TitleBreak = "=============================================================";
        public static final String SectionBreak = "-------------------------------------------------------------";
        public static final String Prefix = "1."; // patch for mindjet desktop text impor
    }

    public class Summary {

        public static final String Source = "DefineASource";
        public static final String Project = "DefineAProject";
    }

    public class Padding {

        public static final int PAD_ATT = 2;
    }

    public class Default {

        public static final String Path = "";
        public static final String Source = "DefineASource";
        public static final String Project = "DefineAProject";
        public static final String MEO = "E"; // M for Manditory, E for Expected, O for optional
    }

    public class ClassNames {

        public static final String LOGS = "Logs";
        public static final String NAMETASKLOGS = "NameTaskLogs";
    }

    public class ElementTypes {

        public static final int COMMENT = -1; // comment
        public static final int FIRST = 1; // first element in sequence
        public static final int REGULAR = 2; // regular element
    }

    public class Element {

        public static final String INSTRUCTION = "instruction";
        public static final String DESCRIPTION = "description";
        public static final String QUESTION = "question";
        public static final String DEFINITION = "definition";
        public static final String ISSUE = "issue";
        // functional-requirements nonfunctional-requirements
        public static final String INPUT = "input"; // input for a functional req, nonfunctional req doesn't have an input
        public static final String FUNCTIONAL_REQUIREMENT = "functional-requirement";
        public static final String NONFUNCTIONAL_REQUIREMENT = "nonfunctional-requirement";
        public static final String USE_CASE = "USE_CASE";
        public static final String LOGS = "logs";
        public static final String WEEKLOG = "weeklog";
        public static final String WEEKNAMELOG = "weekname";
        public static final String DAYLOG = "day-log";
        public static final String DAYNAMELOG = "day-name-log";
        public static final String DAYNAMETASKLOG = "day-name-task-log";
        public static final String NAMETASKLOG = "name-task-log";
        public static final String PAYPERIODLOG = "pay-period";
        public static final String Chart = "chart";
        public static final String Leaf = "leaf";
        public static final String REQUIREMENT = "requirement";
        public static final String InvitationsTab = "Invitations Tab";
        public static final String InvitationTab = "Invitation Tab";
        public static final String InvitationScreenTab = "Invitation Screen Tab";
        public static final String InvitationsScreenTab = "Invitations Screen Tab";
        public static final String Extends = "Extends";
        public static final String API = "API";
        public static final String APIs = "APIs";
        public static final String PROPERTIES = "Properties";
        public static final String COMPONENTS = "Components";
        public static final String FUNCTIONS = "Functions";
        public static final String SCREENS = "Screens";
        public static final String ROLES = "Roles";
        public static final String ACTIVITIES = "Activities";
        public static final String IGNORE = "Ignore";
        public static final String SLIDERS = "Sliders";
        public static final String SHAREDSLIDERS = "Shared Sliders";
        public static final String SCREENCONFIGURATIONS = "Screen Configurations";
        public static final String SHAREDINTENTBARS = "Shared Intent Bars";
        public static final String INTENTBARS = "Intent-bars";
        public static final String INTENTBAR = "Intent-bar";
        public static final String SHAREDUIS = "Shared User Interfaces";
        public static final String INTENTS = "Intents";
        public static final String SLIDERINTENTS = "Slider-Intents";
    }

    public class Ids {

        public static final String FUNICTIONS = "001";
        public static final String PROPERTIES = "002";
        public static final String SCREENS = "003";
        public static final String APIs = "004";
        public static final String COMPONENTS = "005";
        public static final String ROLES = "006";
        public static final String ACTIVITIES = "007";
        public static final String SLIDERS = "008";
        public static final String SCREENCONFIGURATIONS = "009";
        public static final String INTENTBAR = "010";
        public static final String UI = "011";
        public static final String INTENT = "012";
    }

    public class AttributeTypes {

        public static final int ZERO = 0;
        public static final int NEST_BY = 1;
        public static final int GROUP_BY = 2;
        public static final int FUNCTION = 3;
    }

    public class AttributeValues {

        public static final String NEST_BY = "nest-by"; // a configuration attribute, nest-by value indicate the attribute name is used to navigate the hierarchy
        public static final String GROUP_BY = "group-by"; // a configuration attribute, group-by value indicate the attribute name is used in summary
        public static final String COUNT = "count()"; // a configuration attribute, count() value indicates the attribute name is a counter
        public static final String SUM = "sum()"; // a configuration attribute, sum() value indicates the attribute name is used in sum
        public static final String AVG = "avg()"; // a configuration attribute, avg() value indicates the attribute name is used in averages
    }

    public class AttributesTemp {
        // Summary

        public static final String CounterPrefix = "element-counter-"; // temporary counter
        public static final String SumPrefix = "element-sum-"; // temporary sum
        //public static final String AvgPrefix = "element-avg-"; // temporary avg
    }

    public class Attributes {
        // common

        public static final String DATES = "dates";
        public static final String STATUS = "status";
        public static final String ATT_NAME = "name";
        public static final String NAME = "name";
        public static final String ATT_DESCRIPTION = "description";
        public static final String ATT_QUESTION = "question";
        public static final String DEFINITION = "definition";
        // stakeholders
        public static final String ROLE = "role";
        public static final String TITLE = "title";
        public static final String ATT_USERNAME = "username";
        // business-requirements
        public static final String ELEMENT_TYPE = "element-type";
        public static final String STAKEHOLDERS = "stakeholders";
        // wbs
        public static final String ACTUAL_TIME = "actual-time";
        public static final String EFFORT_TIME = "effort-time";
        public static final String DURATION = "duration";
        public static final String DELIVERABLE_TYPE = "deliverable-type";
        public static final String DELIVERABLE = "deliverable";
        // resources
        public static final String RESOURCE_TYPE = "resource-type";
        // communication
        public static final String COMMUNICATION = "communication";
        // logs
        public static final String DATE = "date";
        public static final String START_TIME = "start-time";
        public static final String END_TIME = "end-time";
        public static final String TASK = "task";
        public static final String TRACKER_TICKET = "tracker-ticket";
        // weeklog
        public static final String WEEK = "week";
        public static final String PAYPERIOD = "pay-period";
        public static final String ORIENTATION = "orientation";
        public static final String SYMBOL_TYPE = "symbol-type";
        public static final String RUNNING_TIME = "running-time";
        // chart
        public static final String CHARTTYPE = "charttype"; // declares a chart type for a document
        // fixes
        public static final String ROOTID = "root-id";
        public static final String EXT = "ext";
        public static final String MEO = "meo";
        public static final String REF = "ref";
        public static final String FUNCTION = "function";
        public static final String PROPERTY = "property";
        public static final String COMPONENT = "component";
        public static final String API = "api";
        public static final String SCREEN = "screen";
        public static final String NO = "no";
        public static final String LASTUPDATE = "lastupdate";
        public static final String ACTIVITY = "activity";
        public static final String WORD = "word";
        public static final String SLIDER = "slider";
        public static final String CONFIG = "config";
        public static final String INTENTBAR = "intent-bar";
        public static final String UI = "ui";
        public static final String INTENT = "intent";
        public static final String IGNORE = "ignore";
    }

    public class Folders {

        public static final String EXPORT = "export"; // name of folder to which ALL exports are written /export
        public static final String TEMP = "temp"; // name of folder to which temporary files are written /export/temp
        public static final String FLASHCARDS = "flashcards"; // name of folder to which flashcards exports are written, /export/flashcards
        public static final String EXCEL = "msexcel"; // name of folder to which excel exports are written, /export/excel
        public static final String CHARTS = "charts"; // name of folder to which excel exports are written, /export/charts
        public static final String FLOW = "flow"; // name of folder to which flow and chart exports are written, under export/charts/flow
        public static final String ORG = "org"; // name of folder to which org charts are wirtten, under export/charts/org
    }

    public class ChartTypeValues {

        public static final String ORCHART = "orgchart";
        public static final String FLOWCHART = "flowchart";
    }

    public class OrientationValues {

        public static final String HORIZONTAL = "horizontal";
        public static final String VERTICAL = "vertical";
    }

    public class Directions {

        public static final int NEGATIVE = -1;
        public static final int POSITIVE = 1;
    }

    public class SymbolTypeValues {

        public static final String TASK = "task";
        public static final String CONDITION = "condition";
    }

    /*public class Filenames{
     public static final String WORKLOG="work"+Extentions.LOG;
     }*/
    public class Colors {
        // colors

        public static final String COLOR_DEFAULT = "#000099";
        public static final String RED = "#FF0000";
        public static final String GREY = "#808080";
    }

    public class ErrorMsgs {

        public static final String MISSING_SOURCE = "Missing source in file name.";
        public static final String ERR = "ERR"; // use to show math erro
    }

    public class Extentions {

        public static final String LOG = ".log";
        public static final String INSTRUCTIONS = ".instructions";
        public static final String EXT_CSV_FLASHCARD = ".csv"; // flash cards
        public static final String XML = "xml";
        public static final String EXCELXML = "excel.xml";
        public static final String ODT = "odt";
    }

    public class Filters {

        public static final String NUMERICS = "0123456789.-";
        public static final String NUMERICINTEGER = "0123456789-";
        public static final String NUMERICDOUBLE = "0123456789.-";
    }

    public class Source {

        public static final String PLUCK = "pluck"; // used to subset the elements of a file
        public static final String SUMMARY = "summary"; // summary source indicates this is as special load
        public static final String MASTER_REFERENCE = "master-reference"; // master reference indicate this is not a normal load, but rather it loads atts from the other elements
        public static final String PROXY = "proxy"; // proxy sources are file with one line that provides a file path and name to another file
        public static final String ISSUES = "issues";
        public static final String QUESTIONS = "questions";
        public static final String DEFINITIONS = "definitions";
        public static final String INSTRUCTIONS = "instructions";
        public static final String USE_CASES = "use-cases";
        public static final String COMMUNICATIONS = "communications";
        public static final String LOG = "log"; /* project log*/

        public static final String LOGS = "logs"; /* log for all time */

        public static final String WEEKLOGS = "weeklogs";
        public static final String DAYLOGS = "daylogs";
        public static final String DATA_SHEET = "data-sheet";
        public static final String DAYNAMELOGS = "day-name-logs";
        public static final String DAYNAMETASKLOGS = "day-name-task-logs";
        public static final String NAMETASKLOGS = "name-task-logs";
        public static final String SYSTEMAPPLICATIONS = "system-applications";
        public static final String DELIVERABLES = "deliverables";
        public static final String ACTIVITYDURATIONESTIMATES = "activity-duration-estimates";
    }

    public class Status {

        public static final String STRIKETHROUGH = "[moved][deleted][abandoned][removed][parked]";
        public static final String GRAY = "[complete][completed][finished][resolved][inactive][false][no][done][logged]";
        public static final String GRAYBOLD = "[wbs]";
    }

    public class Tags {

        public static final String LOGS = "logs";
        public static final String WEEKANALYSIS = "weekanalysis";
        public static final String DATA_SHEET = "data-sheet";
        public static final String SYSTEM = "system";
    }

    public class Titles {

        public static final String BASE_GROUP = "Graph";
        public static final String BASE = "base-graphing";
        public static final String FLASHCARD_GROUP = "FLash Cards";
        public static final String FLASHCARDING = "carding";
        public static final String XMLCOMMONGRAPH_GROUP = "Common-Graph";
        public static final String XMLCOMMONGRAPHING = "common-graphing";
        public static final String SVGFLOWGRAPH_GROUP = "Flow-Graph";
        public static final String SVGFLOWGRAPHING = "flow-graphing";
        public static final String SVGORGGRAPH_GROUP = "Org-Graph";
        public static final String SVGORGGRAPHING = "org-graphing";
    }

    public class Type {

        public static final String ODT = "odt";
        public static final String TEXT = "text";
    }

    public class Names {

        public static final String LOG = "log";
        public static final String WEEKLOG = "weeklog";
    }
    // warnings
    public static final String NO_IMPORT_SOURCE_SET = "No Import Source Set";
    // file extentions
    public static final String EXT_XML_CHART = ".chart.xml";
    public static final String EXT_SVG_FLOWCHART = ".flowchart.svg";
    public static final String EXT_SVG_ORGCHART = ".orgchart.svg";
    public static final String EXT_SVG_MATRIXCHART = ".orgchart.svg";
    //
    public static final int MIN_ATT_COUNT = 3; // expect to always have this number of attribs for every element
    public static final int MIN_ID_WIDTH = 1;  // top level ids only have a widht of 1
    // status that cause display to grey
    public static final String COMPLETE = "complete"; // business requirement, tasks...
    public static final String INACTIVE = "inactive";     // stakeholders
    public static final String FALSE = "false";
    public static final String NO = "no";
    // status that cause display to be red as a call for attention
    public static final String PENDING_DETAIL = "pending-detail";
    public static final String PENDING_DETAILS = "pending-details";
    // tags
    public static final String ELM_ORGCHART = "orgchart";
    public static final String STEP = "step";
    // attributes
    // worksheet tab names
    public static final String DELIVERABLES = "Deliverables";
    public static final String RESOURCES = "Resources";
    public static final String RESOURCE = "Resource";
    // worksheet column titles and attribute names
    //public static final String SKIP_ATTRIBUTES = "[description][question]"; // space delimited list of attributes to skip during export to excel
    public static final String DELIVERABLE = "deliverable";
    //public static final String DELIVERABLE_TYPE = "deliverable-type";
    public static final String ID = "id";
    public static final String RUNNINGIDX = "running-idx";
    public static final String LBL_TITLEDESC = "Description";
    public static final String WBS_BREAKDOWN = "Breakdown";
    public static final String FILE_TYPE_XLS_XML = ".xls.xml";
    public static final String FILE_TYPE_TXT_XML = ".xml";
    public static final String FILE_TYPE_SVG = ".svg";
    public static final double EFFORT_TO_DURATION = 3.0;
    public static final String NUMBER_XLS = "Number";
    public static final String STRING_XLS = "String";
    // type

    public class Cmd_Types {

        public static final String PROJECT = "project";
        public static final String TIME = "time";
        public static final String FLASHCARDS = "flashcards";
    }

    public class ChartTypes {

        public static final String ORGCHART = "orgchart";
        public static final String FLOWCHART = "flowchart";
    }
    // public static final String PROJECT = "project";
    // sources
    public static final String SYSTEMS = "systems";
    public static final String HARDWARE = "hardware"; // hardware
    public static final String SOFTWARE = "software"; // software
    public static final String ASSUMPTIONSANDCONSTRAINTS = "assumptionsandconstraints";
    public static final String BUSINESS_REQUIREMENTS = "business-requirements";
    public static final String BUSINESS_REQUIREMENT = "business-requirement";
    public static final String FUNCTIONAL_REQUIREMENTS = "functional-requirements";
    public static final String FUNCTIONAL_REQUIREMENT = "functional-requirement";
    public static final String TECHNICAL_REQUIREMENTS = "technical-requirements";
    public static final String TECHNICAL_REQUIREMENT = "technical-requirement";
    public static final String WBS = "wbs";
    public static final String SERVER = "server";
    public static final String WORKSTATION = "workstation";
    public static final String COMPONENT = "component";
    public static final String SYSTEM = "system";
    public static final String WBSLEVEL = "wbslevel";
    public static final String ASSUMPTIONANDCONSTRAINT = "assumption-constraint";
    public static final String REQUIREMENT = "requirement";
    public static final String STAKEHOLDER = "stakeholder";
    public static final String DISCOVERY_MATRIX = "discovery-matrix";
    public static final String TBD = "TBD";
    public static final String DiagramsJAR = "bin\\DocDat.jar";
    public static final String OutputFolder = "outputs";
    //public static final String Step02XSL = "DocDat/transforms/xsl/wbs.step.01.xsl"; // not used
    //public static final String Step03XSL = "DocDat/transforms/xsl/wbs.step.02.xsl"; // not used

    public class XSL {
        // public static final String trans_odt_text_xsl = "trans.odt.text.xsl";

        public static final String ODTContextToText = "/DocDat/transforms/xsl/trans.odt.text.xsl"; // trans odt.context.xml to text
        public static final String FlowChartXSL = "DocDat/transforms/xsl/flowchart.step.02.xsl"; // transform commonXML to FlowChart.svg
        public static final String OrgChartXSL = "DocDat/transforms/xsl/orgchart.step.02.xsl";   //  transform commonXML to OrgChart.svg
        public static final String Requirement_Short_XSL = "DocDat/transforms/xsl/orgchart.step.02.xsl";   //  transform commonXML to OrgChart.svg
    }

    public class XML {

        public static final String DiagramsJAR = "bin\\ProjectChart.jar";
        public static final String ODTContent = "content.xml"; // this file is contained in a odt file which is a zip file
    }

    public class Errors {

        public static final String ActivityHistoryTab = "Activity History Tab";
        public static final String ActivityHistoryBaseTab = "Activity History Base Tab";
        public static final String InstructionsBaseTab = "Instructions Base Tab";
        public static final String InstructionsScreenTab = "Instructions Screen Tab";
        public static final String ShowInformationBaseTab = "Show Information Base Tab";
        public static final String ShowScreenTab = "Show Screen Tab";
        public static final String InvitationsBaseTab = " Invitations Base Tab";
        public static final String InvitationsScreenTab = " Invitations Screen Tab";
        public static final String ChangesHistoryBaseTab = "Changes History Base Tab";
        public static final String ChangesHistoryTab = "Changes History Tab";
        public static final String ChangeHistoryTab = "Change History Tab";
        public static final String ChangesTab = "Changes Tab";
        public static final String InstructionDefinitions = "Instruction Definitions";
        public static final String LoginDefinitions = "Login Definitions";
        public static final String ReminderDefinitions = "Reminder Definitions";
        public static final String MemberInformationDefinitions = "Member Information Definitions";
        public static final String DegreesandCertificatesDefinitions = "Degrees and Certificates Definitions";
        public static final String PortfolioDefinitions = "Portfolio Definitions";
        public static final String ResumeDefinitions = "Resume Definitions";
        public static final String ExperienceandExpertiseDefinitions = "Experience and Expertise Definitions";
        public static final String ShareandAssistDefinitions = "Share and Assist Definitions";
        public static final String SecurityStatementDefinitions = "Security Statement Definitions";
        public static final String InstructionsTab = "Instructions Tab";
        public static final String InvitationsTab = "Invitations Tab";
        public static final String Modal_Dialog = "Modal Dialog";
    }

    public class Fixes {

        public static final String ActivityTab = "Activity Tab";
        public static final String ActivityHistoryScreenTab = "Activity History Screen Tab";
        public static final String InstructionScreenTab = "Instruction Screen Tab";
        public static final String ShowInformationScreenTab = "Show Information Screen Tab";
        public static final String InvitationScreenTab = "Invitation Screen Tab";
        public static final String ChangeTab = "Change Tab";
        public static final String ChangeHistoryScreenTab = "Change History Screen Tab";
        //public static final String ChangeHistoryTab = "Change History Tab";
        public static final String InstructionDefinition = "Instruction Definition";
        public static final String LoginDefinition = "Login Definition";
        public static final String ReminderDefinition = "Reminder Definition";
        public static final String MemberInformationDefinition = "Member Information Definition";
        public static final String DegreesandCertificatesDefinition = "Degrees and Certificates Definition";
        public static final String PortfolioDefinition = "Portfolio Definition";
        public static final String ResumeDefinition = "Resume Definition";
        public static final String ExperienceandExpertiseDefinition = "Experience and Expertise Definition";
        public static final String ShareandAssistDefinition = "Share and Assist Definition";
        public static final String SecurityStatementDefinitions = "Security Statement Definition";
        public static final String InstructionTab = "Instruction Tab";
        public static final String InvitationTab = "Invitation Tab";
        public static final String Pop_Over_Modal_Window = "Pop-Over Modal Window";
    }

    public class Replace {

        public static final String REPLACE_FUNCTION = "replace-function";
        public static final String REPLACE_PROPERTY = "replace-property"; // trans odt.context.xml to text
        public static final String REPLACE_COMPONENT = "replace-component";
        public static final String REPLACE_API = "replace-api";
        public static final String REPLACE_SCREEN = "replace-screen";
        public static final String REPLACE_ROLE = "replace-role";
        public static final String REPLACE_WORD = "replace-word";
    }
    // types
    public static final int STRING = 0;
    public static final int INTEGER = 1;
    public static final int DOUBLE = 2;
    public static final int XDefault = 0;
    public static final int YDefault = 0;
    public static final int HeightDefault = 50;
    public static final int WidthDefault = 50;
    public static final int MinXOffset = 10;
    public static final int MinYOffset = 50;
    public static final int CharPerLine = 25;
    public static final int CharBuffer = 4;
    public static final int CharWidth = 6;
    public static final int CharHeight = 10;
    public static final String XName = "x";
    public static final String YName = "y";
    public static final String HeightName = "h"; // attribute for height
    public static final String WidthName = "w";  // attribute for width
    public static final String MinXOffsetName = "minxoffset";
    public static final String MinYOffsetName = "minyoffset";
    public static final String CharPerLineName = "charperline";
    public static final String CharBufferName = "charbuffer";
    public static final String CharWidthName = "charwidth";
    public static final String CharHeightName = "charheight";
    public static final int MaxWidthScope = 750;
}
