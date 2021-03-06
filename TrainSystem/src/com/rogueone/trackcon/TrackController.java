//Track Controller class
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rogueone.trackcon;

import com.rogueone.mainframe.MainFrame;
import com.rogueone.global.Global;
import com.rogueone.trackcon.entities.Crossing;
import com.rogueone.trackcon.entities.Light;
import com.rogueone.trackcon.entities.LogicTrackGroup;
import com.rogueone.trackcon.entities.PresenceBlock;
import com.rogueone.trackcon.entities.State;
import com.rogueone.trackcon.entities.StateSet;
import com.rogueone.trackcon.entities.SwitchState;
import com.rogueone.trackcon.entities.Switch;
import com.rogueone.trackcon.entities.TrackConnection;
import com.rogueone.trackcon.entities.UserSwitchState;
import com.rogueone.trackcon.gui.TrackControllerGUI;
import com.rogueone.trackmodel.Block;
import com.rogueone.trackmodel.TrackModel;
import com.rogueone.trainmodel.TrainModel;
import com.rogueone.trackmodel.Section;
import com.rogueone.trainsystem.TrainSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.sl.draw.geom.Guide;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author kylemonto
 */
public class TrackController {

    private TrainSystem trainSystem;
    private Global.Line controllerLine;
    private HashMap<Global.LogicGroups, LogicTrackGroup> logicGroupsArray;
    private HashMap<Integer, Switch> switchArray;
    private LinkedList<UserSwitchState> currentSwitchStates;
    private Crossing crossing;
    private LinkedList<PresenceBlock> occupiedBlocks;
    private LinkedList<String> tempIgnoreSwitch;
    private LinkedList<String> permIgnoreSwitch;
    private LinkedList<Integer> offSwitches;
    private LinkedList<String> trackFailures;

    private TrackControllerGUI trackControllerGUI = null;

    private TrackModel trackModel;
    private HashMap<Integer, TrainModel> trainArray;

    /**
     * Constructor method for track controller
     *
     * @param line - determine line that constructor represents
     * @param trainSystem - reference to trainsystem
     * @param loadFile - file to load into the system
     */
    public TrackController(Global.Line line, TrainSystem trainSystem, File loadFile) {
        this.controllerLine = line;
        //set the train System
        this.trainSystem = trainSystem;
        //load the track model
        this.trackModel = this.trainSystem.getTrackModel();
        //initialize a new track controller GUI
        this.trackControllerGUI = this.createGUIObject(this);
        //set the default PLC file
        File plcFile = null;
        if (loadFile == null) {
            plcFile = new File("src/com/rogueone/assets/wayside_fun.xlsx");
        } else {
            plcFile = loadFile;
        }
        //initialize the permant ignore switches
        this.permIgnoreSwitch = new LinkedList<String>();
        //load the plc file into track controller
        this.loadPLC(plcFile, line);
        //set the plc program field on the gui
        this.trackControllerGUI.plcProgramTextField.setText(plcFile.getName());
        //load the plc and track model data into the summary tab of track controller
//        this.updateSummaryTab();
        this.occupiedBlocks = new LinkedList<PresenceBlock>();
        this.currentSwitchStates = new LinkedList<UserSwitchState>();
        this.tempIgnoreSwitch = new LinkedList<String>();
        this.offSwitches = new LinkedList<Integer>();
        this.trackFailures = new LinkedList<String>();
        if (!permIgnoreSwitch.isEmpty()) {
            configurePermenantSwitch();
        }
    }

    /**
     * Function to import the data from an excel document that contains track
     * state information, such as switch, lights, crossings, connections, etc.
     *
     * @param plcFile - excel document that contains information regarding track
     * states
     */
    public void loadPLC(File plcFile, Global.Line line) {
        //Testing in reads, if the file is there
        XSSFWorkbook plcWorkbook;
        try {
            plcWorkbook = new XSSFWorkbook(plcFile);
            //import the plc data sheet from the excel document
            XSSFSheet sheet = null;
            if (line == Global.Line.GREEN) {
                //load in the green switch information
                sheet = plcWorkbook.getSheetAt(0);
                this.switchArray = new HashMap<Integer, Switch>();
                for (int i = 1; i <= 6; i++) {
                    Row tempRow = sheet.getRow(i);
                    Light light1Default = new Light(tempRow.getCell(3).getStringCellValue());
                    Light light2Default = new Light(tempRow.getCell(4).getStringCellValue());
                    ArrayList<Light> lightsDefault = new ArrayList<Light>();
                    lightsDefault.add(light1Default);
                    lightsDefault.add(light2Default);
                    TrackConnection trackConnectionDefault = new TrackConnection(tempRow.getCell(2).getStringCellValue());
                    Light light1Alternate = new Light(tempRow.getCell(6).getStringCellValue());
                    Light light2Alternate = new Light(tempRow.getCell(7).getStringCellValue());
                    ArrayList<Light> lightsAlternate = new ArrayList<Light>();
                    lightsAlternate.add(light1Alternate);
                    lightsAlternate.add(light2Alternate);
                    TrackConnection trackConnectionAlternate = new TrackConnection(tempRow.getCell(5).getStringCellValue());
                    SwitchState switchState = new SwitchState(Global.SwitchState.DEFAULT, trackConnectionDefault, lightsDefault, trackConnectionAlternate, lightsAlternate);
                    Switch trackSwitch = new Switch((int) tempRow.getCell(0).getNumericCellValue(), tempRow.getCell(1).getStringCellValue(), switchState);
                    this.switchArray.put(trackSwitch.getSwitchID(), trackSwitch);
                }

                //initialize the green logical groups array and import the logical
                //groups from the remaining sheets from the excel document
                this.logicGroupsArray = new HashMap<Global.LogicGroups, LogicTrackGroup>();

                //load in each sheet for the green line
                for (int s = 1; s < 5; s++) {
                    sheet = plcWorkbook.getSheetAt(s);

                    int numRows = 0;
                    Global.TrackGroupsGreen trackGroup1 = null;
                    Global.TrackGroupsGreen trackGroup2 = null;
                    Global.TrackGroupsGreen trackGroup3 = null;

                    LogicTrackGroup logicTrackGroup = new LogicTrackGroup(Global.LogicGroups.valueOf(sheet.getSheetName()));
                    Row trackGroupRow = sheet.getRow(1);

                    if (s == 3 || s == 4) {
                        String[] switchIDs = trackGroupRow.getCell(0).getStringCellValue().split(",");
                        for (String ID : switchIDs) {
                            logicTrackGroup.addSwitches(this.switchArray.get(Integer.parseInt(ID)));
                        }
                        numRows = 9;
                    } else {
                        int switchID = (int) trackGroupRow.getCell(0).getNumericCellValue();
                        logicTrackGroup.addSwitches(this.switchArray.get(switchID));
                        numRows = 5;
                    }

                    StateSet currentState = new StateSet();
                    trackGroup1 = Global.TrackGroupsGreen.valueOf(trackGroupRow.getCell(3).getStringCellValue());
                    State current1 = new State(trackGroup1, Global.Presence.UNOCCUPIED);
                    currentState.addState(current1);
                    trackGroup2 = Global.TrackGroupsGreen.valueOf(trackGroupRow.getCell(4).getStringCellValue());
                    State current2 = new State(trackGroup2, Global.Presence.UNOCCUPIED);
                    currentState.addState(current2);
                    if (s == 3 || s == 4) {
                        trackGroup3 = Global.TrackGroupsGreen.valueOf(trackGroupRow.getCell(5).getStringCellValue());
                        State current3 = new State(trackGroup3, Global.Presence.UNOCCUPIED);
                        currentState.addState(current3);
                    }
                    logicTrackGroup.setCurrentTrackState(currentState);
                    for (int i = 2; i <= numRows; i++) {
                        Row tempRow = sheet.getRow(i);
                        StateSet set1 = new StateSet();
                        UserSwitchState userSwitchState = new UserSwitchState();
                        State s1 = new State(trackGroup1, tempRow.getCell(3).getNumericCellValue() == 1 ? Global.Presence.OCCUPIED : Global.Presence.UNOCCUPIED);
                        State s2 = new State(trackGroup2, tempRow.getCell(4).getNumericCellValue() == 1 ? Global.Presence.OCCUPIED : Global.Presence.UNOCCUPIED);
                        set1.addState(s1);
                        set1.addState(s2);
                        if (s == 3 || s == 4 && trackGroup3 != null) {
                            State s3 = new State(trackGroup3, tempRow.getCell(5).getNumericCellValue() == 1 ? Global.Presence.OCCUPIED : Global.Presence.UNOCCUPIED);
                            set1.addState(s3);
                            userSwitchState.addUserSwitchState(new AbstractMap.SimpleEntry<Integer, Global.SwitchState>((int) trackGroupRow.getCell(6).getNumericCellValue(), Global.SwitchState.valueOf(tempRow.getCell(6).getStringCellValue())));
                            userSwitchState.addUserSwitchState(new AbstractMap.SimpleEntry<Integer, Global.SwitchState>((int) trackGroupRow.getCell(7).getNumericCellValue(), Global.SwitchState.valueOf(tempRow.getCell(7).getStringCellValue())));
                            logicTrackGroup.addTrackState(set1, userSwitchState);
                        } else {
                            userSwitchState.addUserSwitchState(new AbstractMap.SimpleEntry<Integer, Global.SwitchState>((int) trackGroupRow.getCell(5).getNumericCellValue(), Global.SwitchState.valueOf(tempRow.getCell(5).getStringCellValue())));
                            logicTrackGroup.addTrackState(set1, userSwitchState);
                        }
                    }

                    this.logicGroupsArray.put(Global.LogicGroups.valueOf(sheet.getSheetName()), logicTrackGroup);
                }

                //load the crossing into the trackController
                sheet = plcWorkbook.getSheet("GREEN_CROSSING");
                Row greenRow = sheet.getRow(1);
                Global.Line sheetLine = Global.Line.valueOf(greenRow.getCell(0).getStringCellValue());
                Global.Section section = Global.Section.valueOf(greenRow.getCell(1).getStringCellValue());
                int block = (int) greenRow.getCell(2).getNumericCellValue();
                HashMap<Global.CrossingState, Global.LightState> crossingStates = new HashMap<Global.CrossingState, Global.LightState>();
                crossingStates.put(Global.CrossingState.ACTIVE, Global.LightState.valueOf(greenRow.getCell(3).getStringCellValue()));
                crossingStates.put(Global.CrossingState.INACTIVE, Global.LightState.valueOf(greenRow.getCell(4).getStringCellValue()));
                String blocksActive = greenRow.getCell(5).getStringCellValue();
                this.crossing = new Crossing(sheetLine, section, block, crossingStates, Global.CrossingState.INACTIVE, blocksActive);

                //load the manual switches into the trackController
                sheet = plcWorkbook.getSheet("TRACK_DETAILS");
                Row detailsRow = sheet.getRow(1);
                String manualSwitches = detailsRow.getCell(2).getStringCellValue();
                if (!manualSwitches.equals("")) {
                    List<String> blockList = Arrays.asList(manualSwitches.split(","));
                    for (String s : blockList) {
                        this.permIgnoreSwitch.add(s);
                    }
                }

            } else if (line == Global.Line.RED) {
                //Start of Red line import
                sheet = plcWorkbook.getSheet("RED_SWITCHES");
                this.switchArray = new HashMap<Integer, Switch>();
                for (int i = 1; i <= 7; i++) {
                    Row tempRow = sheet.getRow(i);
                    //import default1
                    Light light1Default1 = new Light(tempRow.getCell(3).getStringCellValue());
                    Light light2Default1 = new Light(tempRow.getCell(4).getStringCellValue());
                    Light light3Default1 = new Light(tempRow.getCell(5).getStringCellValue());
                    ArrayList<Light> lightsDefault1 = new ArrayList<Light>();
                    lightsDefault1.add(light1Default1);
                    lightsDefault1.add(light2Default1);
                    lightsDefault1.add(light3Default1);
                    TrackConnection trackConnectionDefault1 = new TrackConnection(tempRow.getCell(2).getStringCellValue());
                    //import default2
                    Light light1Default2 = new Light(tempRow.getCell(7).getStringCellValue());
                    Light light2Default2 = new Light(tempRow.getCell(8).getStringCellValue());
                    Light light3Default2 = new Light(tempRow.getCell(9).getStringCellValue());
                    ArrayList<Light> lightsDefault2 = new ArrayList<Light>();
                    lightsDefault2.add(light1Default2);
                    lightsDefault2.add(light2Default2);
                    lightsDefault2.add(light3Default2);
                    TrackConnection trackConnectionDefault2 = new TrackConnection(tempRow.getCell(6).getStringCellValue());
                    //import alternate
                    Light light1Alternate = new Light(tempRow.getCell(11).getStringCellValue());
                    Light light2Alternate = new Light(tempRow.getCell(12).getStringCellValue());
                    Light light3Alternate = new Light(tempRow.getCell(13).getStringCellValue());
                    ArrayList<Light> lightsAlternate = new ArrayList<Light>();
                    lightsAlternate.add(light1Alternate);
                    lightsAlternate.add(light2Alternate);
                    lightsAlternate.add(light3Alternate);
                    TrackConnection trackConnectionAlternate = new TrackConnection(tempRow.getCell(10).getStringCellValue());

                    SwitchState switchState = new SwitchState(Global.SwitchState.DEFAULT, trackConnectionDefault1, lightsDefault1, trackConnectionDefault2, lightsDefault2, trackConnectionAlternate, lightsAlternate);
                    Switch trackSwitch = new Switch((int) tempRow.getCell(0).getNumericCellValue(), tempRow.getCell(1).getStringCellValue(), switchState);
                    this.switchArray.put(trackSwitch.getSwitchID(), trackSwitch);
                }

                this.logicGroupsArray = new HashMap<Global.LogicGroups, LogicTrackGroup>();

                for (int s = 6; s < 8; s++) {
                    sheet = plcWorkbook.getSheetAt(s);

                    int numRows = 0;
                    Global.TrackGroupsRed trackGroup1 = null;
                    Global.TrackGroupsRed trackGroup2 = null;
                    Global.TrackGroupsRed trackGroup3 = null;

                    LogicTrackGroup logicTrackGroup = new LogicTrackGroup(Global.LogicGroups.valueOf(sheet.getSheetName()));
                    Row trackGroupRow = sheet.getRow(1);

                    if (s == 7) {
                        String[] switchIDs = trackGroupRow.getCell(0).getStringCellValue().split(",");
                        for (String ID : switchIDs) {
                            logicTrackGroup.addSwitches(this.switchArray.get(Integer.parseInt(ID)));
                        }
                        numRows = 9;
                    } else {
                        int switchID = (int) trackGroupRow.getCell(0).getNumericCellValue();
                        logicTrackGroup.addSwitches(this.switchArray.get(switchID));
                        numRows = 5;
                    }

                    StateSet currentState = new StateSet();
                    trackGroup1 = Global.TrackGroupsRed.valueOf(trackGroupRow.getCell(3).getStringCellValue());
                    State current1 = new State(trackGroup1, Global.Presence.UNOCCUPIED);
                    currentState.addState(current1);
                    trackGroup2 = Global.TrackGroupsRed.valueOf(trackGroupRow.getCell(4).getStringCellValue());
                    State current2 = new State(trackGroup2, Global.Presence.UNOCCUPIED);
                    currentState.addState(current2);
                    if (s == 7) {
                        trackGroup3 = Global.TrackGroupsRed.valueOf(trackGroupRow.getCell(5).getStringCellValue());
                        State current3 = new State(trackGroup3, Global.Presence.UNOCCUPIED);
                        currentState.addState(current3);
                    }
                    logicTrackGroup.setCurrentTrackState(currentState);
                    for (int i = 2; i <= numRows; i++) {
                        Row tempRow = sheet.getRow(i);
                        StateSet set1 = new StateSet();
                        UserSwitchState userSwitchState = new UserSwitchState();
                        State s1 = new State(trackGroup1, tempRow.getCell(3).getNumericCellValue() == 1 ? Global.Presence.OCCUPIED : Global.Presence.UNOCCUPIED);
                        State s2 = new State(trackGroup2, tempRow.getCell(4).getNumericCellValue() == 1 ? Global.Presence.OCCUPIED : Global.Presence.UNOCCUPIED);
                        set1.addState(s1);
                        set1.addState(s2);
                        if (s == 7 && trackGroup3 != null) {
                            State s3 = new State(trackGroup3, tempRow.getCell(5).getNumericCellValue() == 1 ? Global.Presence.OCCUPIED : Global.Presence.UNOCCUPIED);
                            set1.addState(s3);
                            userSwitchState.addUserSwitchState(new AbstractMap.SimpleEntry<Integer, Global.SwitchState>((int) trackGroupRow.getCell(6).getNumericCellValue(), Global.SwitchState.valueOf(tempRow.getCell(6).getStringCellValue())));
                            userSwitchState.addUserSwitchState(new AbstractMap.SimpleEntry<Integer, Global.SwitchState>((int) trackGroupRow.getCell(7).getNumericCellValue(), Global.SwitchState.valueOf(tempRow.getCell(7).getStringCellValue())));
                            logicTrackGroup.addTrackState(set1, userSwitchState);
                        } else {
                            userSwitchState.addUserSwitchState(new AbstractMap.SimpleEntry<Integer, Global.SwitchState>((int) trackGroupRow.getCell(5).getNumericCellValue(), Global.SwitchState.valueOf(tempRow.getCell(5).getStringCellValue())));
                            logicTrackGroup.addTrackState(set1, userSwitchState);
                        }
                    }

                    this.logicGroupsArray.put(Global.LogicGroups.valueOf(sheet.getSheetName()), logicTrackGroup);
                }
                //load the red crossing into the track controller
                sheet = plcWorkbook.getSheet("RED_CROSSING");
                Row greenRow = sheet.getRow(1);
                Global.Line sheetLine = Global.Line.valueOf(greenRow.getCell(0).getStringCellValue());
                Global.Section section = Global.Section.valueOf(greenRow.getCell(1).getStringCellValue());
                int block = (int) greenRow.getCell(2).getNumericCellValue();
                HashMap<Global.CrossingState, Global.LightState> crossingStates = new HashMap<Global.CrossingState, Global.LightState>();
                crossingStates.put(Global.CrossingState.ACTIVE, Global.LightState.valueOf(greenRow.getCell(3).getStringCellValue()));
                crossingStates.put(Global.CrossingState.INACTIVE, Global.LightState.valueOf(greenRow.getCell(4).getStringCellValue()));
                String blocksActive = greenRow.getCell(5).getStringCellValue();
                this.crossing = new Crossing(sheetLine, section, block, crossingStates, Global.CrossingState.INACTIVE, blocksActive);

                //load the manual switches into the trackController
                sheet = plcWorkbook.getSheet("TRACK_DETAILS");
                Row detailsRow = sheet.getRow(1);
                String manualSwitches = detailsRow.getCell(3).getStringCellValue();
                if (!manualSwitches.equals("")) {
                    List<String> blockList = Arrays.asList(manualSwitches.split(","));
                    for (String s : blockList) {
                        this.permIgnoreSwitch.add(s);
                    }
                }
            }
            System.out.println("Successful import of " + plcFile);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Function that will create a GUI object for that corresponds to the track
     * controller, allows for easy accessibility and communication between track
     * controller and GUI class
     *
     * @param trackController - serves as link between trackControllerGUI
     * @return
     */
    public TrackControllerGUI createGUIObject(TrackController trackController) {
        //Create a GUI object
        TrackControllerGUI trackControllerGUI = new TrackControllerGUI(trackController);
        return trackControllerGUI;  //Return the GUI
    }

    /**
     * Method that will update all of the tables on the summary screen of the
     * GUI and populate will up to date information from the Track Model
     */
    public void updateSummaryTab() {
        String switchColumnNames[] = {"Section", "Block ID", "Switch ID", "State", "Connection"};
        DefaultTableModel switchModel = new DefaultTableModel(switchColumnNames, 0);

        String lightColumnNames[] = {"Section", "Block ID", "Light State"};
        DefaultTableModel lightModel = new DefaultTableModel(lightColumnNames, 0);

        String crossingColumnNames[] = {"Section", "BlockID", "State"};
        DefaultTableModel crossingModel = new DefaultTableModel(crossingColumnNames, 0);

        HashMap<Integer, Switch> displaySwitchArray = null;
        Crossing displayCrossing = null;
        Global.Line displayLine = null;
        displaySwitchArray = this.switchArray;
        displayCrossing = crossing;
        displayLine = this.controllerLine;

        if (this.currentSwitchStates != null) {
            Iterator listIterator = this.currentSwitchStates.iterator();
            while (listIterator.hasNext()) {
                UserSwitchState listEntry = (UserSwitchState) listIterator.next();
                Iterator switchIterator = listEntry.getUserSwitchStates().iterator();
                while (switchIterator.hasNext()) {
                    SimpleEntry<Integer, Global.SwitchState> switchEntry = (SimpleEntry<Integer, Global.SwitchState>) switchIterator.next();
                    Integer switchID = switchEntry.getKey();
                    Switch switchInfo = this.switchArray.get(switchID);
                    TrackConnection currentTrackConnection = null;
                    ArrayList<Light> currentLights = null;
                    if (switchEntry.getValue() == Global.SwitchState.DEFAULT) {
                        currentTrackConnection = switchInfo.getSwitchState().getDefaultConnection();
                        currentLights = switchInfo.getSwitchState().getLightsDefault();
                    } else {
                        currentTrackConnection = switchInfo.getSwitchState().getAlternateConnection();
                        currentLights = switchInfo.getSwitchState().getLightsAlternate();
                    }
                    //add switch to table model
                    String switchRowData[] = {switchInfo.getSection() + "", switchInfo.getBlockID() + "", switchInfo.getSwitchID() + "", switchEntry.getValue() + "", currentTrackConnection + ""};
                    switchModel.addRow(switchRowData);
                    //add lights to table model
                    Iterator lightsIterator = currentLights.iterator();
                    while (lightsIterator.hasNext()) {
                        Light lightIteration = (Light) lightsIterator.next();
                        String lightRowData[] = {lightIteration.getSection() + "", lightIteration.getBlockID() + "", lightIteration.getLightState() + ""};
                        lightModel.addRow(lightRowData);
                    }
                }

            }

            String crossingRowData[] = {displayCrossing.getSection() + "", displayCrossing.getBlockID() + "", displayCrossing.getCurrentCrossingState() + ""};
            crossingModel.addRow(crossingRowData);
            Set<Entry<Global.CrossingState, Global.LightState>> lights = displayCrossing.getCrossingState().entrySet();
            Iterator crossingLightsIterator = lights.iterator();
            while (crossingLightsIterator.hasNext()) {
                Entry<Global.CrossingState, Global.LightState> lightState = (Entry<Global.CrossingState, Global.LightState>) crossingLightsIterator.next();
                if (displayCrossing.getCurrentCrossingState() == lightState.getKey()) {
                    String lightRowData[] = {displayCrossing.getSection() + "", displayCrossing.getBlockID() + "", lightState.getValue() + ""};
                    lightModel.addRow(lightRowData);
                }
            }

            this.trackControllerGUI.currentSwitchTable.setModel(switchModel);
            this.trackControllerGUI.currentTrackSignalsTable.setModel(lightModel);
            this.trackControllerGUI.currentCrossingTable.setModel(crossingModel);
        }
    }

    /**
     * Setup of simulation tab on GUI based on the Logic Group passed to it will
     * show or hide panels and create matching labels for each Logic Group
     *
     * @param logicGroup - determines the panels to be shown and sets their
     * labels
     * @return
     */
    public String setupSimulateTabSwitch(Global.LogicGroups logicGroup) {
        LogicTrackGroup selectedLogicGroup = logicGroupsArray.get(logicGroup);
        if (selectedLogicGroup == null) {
            System.out.println("Logic group is not in selected Track Controller");
            String message = "Logic group is not in selected Track Controller\n";
            return message;
        }
        this.trackControllerGUI.enableInputs(selectedLogicGroup);
        this.trackControllerGUI.setImage(logicGroup);
        return null;
    }

    /**
     * Determines the state of a switch given a selection from the simulation
     * tab on the Track Controller GUI
     *
     * @param selectedLogicTrackGroup - logic group the user is working with
     * @param guiStateSet - state set that the user wants to test
     * @return
     */
    public UserSwitchState updateStateMapping(LogicTrackGroup selectedLogicTrackGroup, StateSet guiStateSet) {
        String lineSelected = null;
        LogicTrackGroup ltg = logicGroupsArray.get(selectedLogicTrackGroup.getLogicGroup());
        if (ltg == null) {
            System.out.println("No valid logic group found in current track controller");
            return null;
        }
        lineSelected = this.controllerLine.toString();

        StateSet currentState = ltg.getCurrentTrackState();

        HashMap<StateSet, UserSwitchState> mappings = ltg.getStateMapping();

        //how to search for current state mapping        
        for (Map.Entry<StateSet, UserSwitchState> entry : mappings.entrySet()) {
            StateSet key = entry.getKey();
            UserSwitchState userSwitchState = entry.getValue();
            if (key.equals(guiStateSet)) {
                ltg.setPreviousTrackState(currentState);
                ltg.setCurrentTrackState(guiStateSet);
                this.trackControllerGUI.setSelectedLogicTrackGroup(ltg);
                logicGroupsArray.replace(selectedLogicTrackGroup.getLogicGroup(), ltg);
                return userSwitchState;
            } else {
//                System.out.println("Mapping does not contain current State");
            }
        }
        return null;
    }

    /**
     * Function to evaluate the logic group state. Used in the calculation of
     * actual track calculations
     *
     * @param logicGroup - current logic group
     * @param stateSet - current state set
     * @return userSwitchState - contains switch state, lights, and connection
     * information
     */
    public UserSwitchState evaluateLogicGroup(Global.LogicGroups logicGroup, StateSet stateSet) {
        LogicTrackGroup ltg = logicGroupsArray.get(logicGroup);
        if (ltg == null) {
            System.out.println("No valid logic group found in current track controller");
            return null;
        }

        StateSet currentState = ltg.getCurrentTrackState();

        HashMap<StateSet, UserSwitchState> mappings = ltg.getStateMapping();
        //how to search for current state mapping        
        for (Map.Entry<StateSet, UserSwitchState> entry : mappings.entrySet()) {
            StateSet key = entry.getKey();
            UserSwitchState userSwitchState = entry.getValue();
            if (key.equals(stateSet)) {
                ltg.setPreviousTrackState(currentState);
                ltg.setCurrentTrackState(stateSet);
                this.trackControllerGUI.setSelectedLogicTrackGroup(ltg);
                this.logicGroupsArray.replace(logicGroup, ltg);
                return userSwitchState;
            }
            // do stuff
        }
        return null;
    }

    /**
     * Initialization of the simulate tab for crossings
     *
     * @param logicGroup - current logic group
     * @return if null then successful, else signifies an error has occurred
     */
    public String setupSimulateTabCrossing(Global.LogicGroups logicGroup) {
        Crossing crossing = null;
        LogicTrackGroup selectedLogicGroup = logicGroupsArray.get(logicGroup);
        if (selectedLogicGroup == null) {
            System.out.println("Logic group is not in selected Track Controller cannot get crossing");
            String message = "Logic group is not in selected Track Controller cannot get crossing\n";
            return message;
        }
        this.trackControllerGUI.enableInputs(crossing);
        this.trackControllerGUI.setImage(crossing);
        return null;
    }

    /**
     * Update the crossing within the GUI
     *
     * @param selectedCrossing
     * @param selectedCrossState
     */
    public void updateCrossing(Crossing selectedCrossing, Global.CrossingState selectedCrossState) {
        selectedCrossing.setCurrentCrossingState(selectedCrossState);
        if (selectedCrossing.getLine() == Global.Line.GREEN) {
            this.crossing = selectedCrossing;
            this.trackControllerGUI.setSelectedCrossing(this.crossing);
        } else {
            this.crossing = selectedCrossing;
            this.trackControllerGUI.setSelectedCrossing(this.crossing);
        }

    }

    /**
     * method that will gather information from the track model and will
     * assemble in order to determine the state of switches given the presence
     * of trains on the track
     */
    public void evaluateSwitches() {
        //get mappings from handler
        HashMap<Global.Section, Global.TrackGroupsGreen> sectionMappings = this.trainSystem.getTrackControllerHandler().getSectionToGroupMapping();
        HashMap<Global.TrackGroupsGreen, Global.LogicGroups> groupMappings = this.trainSystem.getTrackControllerHandler().getGroupToLogicMapping();

        //create hashmap for sections
        HashMap<Global.Section, Global.Presence> sectionPresence = new HashMap<Global.Section, Global.Presence>();
        //get the sections for the current line
        ArrayList<Section> sectionsArray = this.trackModel.getLine(controllerLine).getSections();
        tempIgnoreSwitch.clear();
        for (Section s : sectionsArray) {
            //get the blocks for the current line
            ArrayList<Block> blockArray = s.getBlocks();
            boolean occupied = false;
            //determine whether a block is occupied on a particular line
            for (Block b : blockArray) {
                if (b.isOccupied() && b.getSwitchID() != -1) {
                    //if on switch block do not complete switch calculation, leave track in current state
                    if (!tempIgnoreSwitch.contains(String.valueOf(b.getSwitchID()))) {
                        tempIgnoreSwitch.add(String.valueOf(b.getSwitchID()));
                    }
                } else if (b.isOccupied() && b.getSwitchID() == -1) {
                    // else set section as occupied
                    occupied = true;
                }
            }
            //set the presence of a section in a hashmap
            Global.Presence sectionOccupancy = (occupied) ? Global.Presence.OCCUPIED : Global.Presence.UNOCCUPIED;
            sectionPresence.put(s.getSectionID(), sectionOccupancy);
        }

        //translate the section presence into track group presence
        HashMap<Global.TrackGroupsGreen, Global.Presence> groupPresence = new HashMap<Global.TrackGroupsGreen, Global.Presence>();
        for (Entry<Global.Section, Global.Presence> sectionEntry : sectionPresence.entrySet()) {
            //condition for potential overwrite of presence
            if (groupPresence.containsKey(sectionMappings.get(sectionEntry.getKey()))) {
                if (groupPresence.get(sectionMappings.get(sectionEntry.getKey())) == Global.Presence.OCCUPIED || sectionEntry.getValue() == Global.Presence.OCCUPIED) {
                    groupPresence.put(sectionMappings.get(sectionEntry.getKey()), Global.Presence.OCCUPIED);
                } else {
                    groupPresence.put(sectionMappings.get(sectionEntry.getKey()), Global.Presence.UNOCCUPIED);
                }
            } else if (!groupPresence.containsKey(sectionMappings.get(sectionEntry.getKey()))) {
                if (sectionEntry.getValue() == Global.Presence.OCCUPIED) {
                    groupPresence.put(sectionMappings.get(sectionEntry.getKey()), Global.Presence.OCCUPIED);
                } else {
                    groupPresence.put(sectionMappings.get(sectionEntry.getKey()), Global.Presence.UNOCCUPIED);
                }
            }

        }

        HashMap<Global.LogicGroups, StateSet> logicSets = new HashMap<Global.LogicGroups, StateSet>();
        for (Entry<Global.TrackGroupsGreen, Global.Presence> groupEntry : groupPresence.entrySet()) {
            //make new state given the current track group and its presence
            State state = new State(groupEntry.getKey(), groupEntry.getValue());
            //if the logic set hashmap does not yet contain the logic group key yet, create it and a state set,
            //add the current state to it, and put it in the hashmap
            if (!logicSets.containsKey(groupMappings.get(groupEntry.getKey()))) {
                StateSet stateSet = new StateSet();
                stateSet.addState(state);
                logicSets.put(groupMappings.get(groupEntry.getKey()), stateSet);
            } //if the logic set hashmap does contain the logic group key, get the current state set and add the 
            //state to it and put it back 
            else {
                StateSet stateSet = logicSets.get(groupMappings.get(groupEntry.getKey()));
                stateSet.addState(state);
                logicSets.replace(groupMappings.get(groupEntry.getKey()), stateSet);
            }
        }
        if (currentSwitchStates != null) {
            if (!permIgnoreSwitch.isEmpty()) {
                LinkedList<UserSwitchState> keepStates = new LinkedList<UserSwitchState>();
                for (UserSwitchState uss : this.currentSwitchStates) {
                    for (SimpleEntry se : uss.getUserSwitchStates()) {
                        for (String pis : permIgnoreSwitch) {
                            if ((Integer) se.getKey() == Integer.parseInt(pis)) {
                                keepStates.add(uss);
                            }
                        }
                    }
                }
                this.currentSwitchStates.clear();
                for (UserSwitchState uss : keepStates) {
                    this.currentSwitchStates.add(uss);
                }
            } else {
                this.currentSwitchStates.clear();
            }
        }
        if (this.controllerLine == Global.Line.RED) {
            logicSets.clear();
            logicSets.put(Global.LogicGroups.RED_12, null);
            logicSets.put(Global.LogicGroups.RED_10, null);
            logicSets.put(Global.LogicGroups.RED_6_11, null);
            logicSets.put(Global.LogicGroups.RED_7, null);
            logicSets.put(Global.LogicGroups.RED_8, null);
            logicSets.put(Global.LogicGroups.RED_9, null);
        }

        for (Entry<Global.LogicGroups, StateSet> logicSet : logicSets.entrySet()) {
            boolean evaluateSwitch = true;
            for (String s : tempIgnoreSwitch) {
                if (logicSet.getKey().toString().contains(s)) {
                    evaluateSwitch = false;
                }
            }
            for (String s : permIgnoreSwitch) {
                if (logicSet.getKey().toString().contains(s)) {
                    evaluateSwitch = false;
                }
            }
            if (evaluateSwitch) {
                //SAFETY CRITICAL
                //evaluation of switch state occurs twice, if the value is not the same, then 
                //the switch is shut down
                UserSwitchState userSwitchState1 = evaluateLogicGroup(logicSet.getKey(), logicSet.getValue());
                UserSwitchState userSwitchState2 = evaluateLogicGroup(logicSet.getKey(), logicSet.getValue());
                if (userSwitchState1.equals(userSwitchState2)) {
                    updateSwitches(userSwitchState1);
                    this.currentSwitchStates.add(userSwitchState1);
                }

            }
        }

    }

    /**
     * Check to determine if a train can leave the yard
     *
     * @return
     */
    public boolean canDispatchProceed() {
        //check if track is not broken or closed at start
        //check if track is not occupied at start 
        //check if switch is connecting the correct next block
        PresenceBlock lookaheadBlock = new PresenceBlock(this.trainSystem, controllerLine);
        lookaheadBlock.setNextBlock(lookaheadBlock.getCurrBlock().getNext(lookaheadBlock.getPrevBlock()));
        if (controllerLine == Global.Line.GREEN) {
            int[] dispatchBlocksCheck = {152, 62, 63, 64, 65};
            int lookahead = 5;
            for (int i = 0; i < lookahead; i++) {
                //check to see if first 5 blocks from leaving yard are emtpy
                Block checkBlock = this.trackModel.getBlock(controllerLine, dispatchBlocksCheck[i]);
                if (checkBlock.isOccupied() || !checkBlock.isOpen() || checkBlock.getFailureBrokenRail() || checkBlock.getFailurePowerOutage() || checkBlock.getFailureTrackCircuit()) {
                    return false;
                }
                if (checkBlock.getID() == 152) {
                    if (checkBlock.getNext(this.trackModel.getYard()) == null) {
                        return false;
                    }
                }
            }
        } else {
            int[] dispatchBlocksCheck = {77, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            int lookahead = 15;
            for (int i = 0; i < lookahead; i++) {
                //check to see if first 5 blocks from leaving yard are emtpy
                Block checkBlock = this.trackModel.getBlock(controllerLine, dispatchBlocksCheck[i]);
                if (checkBlock.isOccupied() || !checkBlock.isOpen() || checkBlock.getFailureBrokenRail() || checkBlock.getFailurePowerOutage() || checkBlock.getFailureTrackCircuit()) {
                    return false;
                }
                if (checkBlock.getID() == 77) {
                    if (checkBlock.getNext(this.trackModel.getYard()) == null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //evaluate whether train can proceed
    public void evaluateProceed() {
        this.trackFailures.clear();
        //get the sections for the current line
        ArrayList<Section> sectionsArray = this.trackModel.getLine(controllerLine).getSections();
        //BEGIN OF UPDATING PRESENCE
        for (Section s : sectionsArray) {
            //get the blocks for the current line
            ArrayList<Block> blockArray = s.getBlocks();
            //determine whether a block is occupied on a particular line
            for (Block b : blockArray) {
                //if the first block of the green line is occupied add a new PresenceBlock to list
                if (controllerLine == Global.Line.GREEN) {
                    PresenceBlock pb = new PresenceBlock(this.trainSystem, this.controllerLine);
                    if (b.isOccupied() && b.getID() == 152 && !occupiedBlocks.contains(pb)) {
                        pb.setNextBlock(pb.getCurrBlock().getNext(pb.getPrevBlock()));
                        occupiedBlocks.add(pb);
                    }
                } else if (controllerLine == Global.Line.RED) {
                    PresenceBlock pb = new PresenceBlock(this.trainSystem, this.controllerLine);
                    if (b.isOccupied() && b.getID() == 77 && !occupiedBlocks.contains(pb)) {
                        pb.setNextBlock(pb.getCurrBlock().getNext(pb.getPrevBlock()));
                        occupiedBlocks.add(pb);
                    }
                }

                //update the track failures list
                if (b.getFailureBrokenRail() || b.getFailurePowerOutage() || b.getFailureTrackCircuit()) {
                    if (!this.trackFailures.contains(b.getSection().toString() + "," + String.valueOf(b.getID()))) {
                        if (controllerLine == Global.Line.RED) {
                            if (b.getSection().getSectionID() == Global.Section.H && b.getID() >= 24 && b.getID() <= 27) {
                                this.trackFailures.add(b.getSection().toString() + "24_27" + "," + String.valueOf(b.getID()));
                            } else if (b.getSection().getSectionID() == Global.Section.H && b.getID() >= 28 && b.getID() <= 32) {
                                this.trackFailures.add(b.getSection().toString() + "28_32" + "," + String.valueOf(b.getID()));
                            } else if (b.getSection().getSectionID() == Global.Section.H && b.getID() >= 33 && b.getID() <= 38) {
                                this.trackFailures.add(b.getSection().toString() + "33_38" + "," + String.valueOf(b.getID()));
                            } else if (b.getSection().getSectionID() == Global.Section.H && b.getID() >= 39 && b.getID() <= 43) {
                                this.trackFailures.add(b.getSection().toString() + "39_43" + "," + String.valueOf(b.getID()));
                            } else if (b.getSection().getSectionID() == Global.Section.H && b.getID() >= 44 && b.getID() <= 45) {
                                this.trackFailures.add(b.getSection().toString() + "44_45" + "," + String.valueOf(b.getID()));
                            } else if (b.getSection().getSectionID() == Global.Section.J && b.getID() >= 49 && b.getID() <= 52) {
                                this.trackFailures.add(b.getSection().toString() + "49_52" + "," + String.valueOf(b.getID()));
                            } else if (b.getSection().getSectionID() == Global.Section.J && b.getID() >= 53 && b.getID() <= 54) {
                                this.trackFailures.add(b.getSection().toString() + "53_54" + "," + String.valueOf(b.getID()));
                            } else {
                                this.trackFailures.add(b.getSection().toString() + "," + String.valueOf(b.getID()));
                            }
                        } else {
                            this.trackFailures.add(b.getSection().toString() + "," + String.valueOf(b.getID()));
                        }
                    }
                }
                //update the switch Lights if track has no power
                if (b.getSwitchID() != -1) {
                    isSwitchAccessible(b.getSwitchID());
                }

                //the occupied list is the same as the occupied block move that block forward
                //move the block ahead if the currently examined block is occupied and the 
                //presence block's next block is equals to that block
                //TLDR: move the train forward
                if (!occupiedBlocks.isEmpty()) {
                    for (PresenceBlock pb : occupiedBlocks) {
                        if (b.isOccupied() && pb.getNextBlock().getID() == b.getID()) {
                            Block tempBlock = pb.getCurrBlock();
                            pb.setCurrBlock((Block) pb.getNextBlock());
                            pb.setPrevBlock(tempBlock);
                            if (pb.getCurrBlock().getNext(pb.getPrevBlock()) == null) {
                            } else {
                                pb.setNextBlock(pb.getCurrBlock().getNext(pb.getPrevBlock()));
                            }
                        }
                    }
                }
            }
        }
        //END OF UPDATE PRESENCE
        //Logic for removing trains from the track
        ArrayList<PresenceBlock> removeBlocks = new ArrayList<PresenceBlock>();
        for (PresenceBlock pb : occupiedBlocks) {
            if (controllerLine == Global.Line.GREEN && pb.getCurrBlock().getID() == 151 && !this.trackModel.getBlock(controllerLine, 151).isOccupied()) {
                removeBlocks.add(pb);
            } else if (controllerLine == Global.Line.RED && pb.getCurrBlock().getID() == 77 && !this.trackModel.getBlock(controllerLine, 77).isOccupied()) {
                removeBlocks.add(pb);
            }
        }
        for (PresenceBlock pb : removeBlocks) {
            occupiedBlocks.remove(pb);
        }

        //START OF SAFETY CHECKS
        if (!occupiedBlocks.isEmpty()) {
            for (PresenceBlock pb : occupiedBlocks) {
                //if the current block being looked at is occupied and the next block ID of the current block
                //need a check to look ahead by a specific amount of blocks or by sections

                int lookahead = 5;
                PresenceBlock lookaheadBlock = new PresenceBlock(pb);
                for (int i = 0; i < lookahead; i++) {
                    //check to see if switch is in the wrong position
                    boolean safeToLookAhead = true;
                    if (lookaheadBlock.getCurrBlock().getNext(lookaheadBlock.getPrevBlock()) == null) {
                        pb.getCurrBlock().getTrackCircuit().speed = -1;
                        pb.getCurrBlock().getTrackCircuit().authority = -1;
                        safeToLookAhead = false;
                        break;
                    } //check to see if that block ahead is occupied or not
                    //needs to look ahead by 2 blocks, because the block directly in front will
                    //always be occupied because that is how the train model sets the track
                    else if (i > 0 && lookaheadBlock.getNextBlock().getID() != 0 && this.trackModel.getBlock(controllerLine, lookaheadBlock.getNextBlock().getID()).isOccupied() && this.trackModel.getBlock(controllerLine, lookaheadBlock.getNextBlock().getID()).isOpen()) {
//                        System.out.println("Train Ahead");
                        if (this.trainSystem.getScheduler().getMode().equals("Fixed Block")) {
                            pb.getCurrBlock().getTrackCircuit().speed = -1;
                            pb.getCurrBlock().getTrackCircuit().authority = -1;
                            break;
                        }
                    } //check to see if that block ahead is closed or failed
                    else if (lookaheadBlock.getNextBlock().getID() != 0 && (!this.trackModel.getBlock(controllerLine, lookaheadBlock.getNextBlock().getID()).isOpen() || this.trackModel.getBlock(controllerLine, lookaheadBlock.getNextBlock().getID()).getFailureBrokenRail()
                            || this.trackModel.getBlock(controllerLine, lookaheadBlock.getNextBlock().getID()).getFailurePowerOutage() || this.trackModel.getBlock(controllerLine, lookaheadBlock.getNextBlock().getID()).getFailureTrackCircuit())) {
                        pb.getCurrBlock().getTrackCircuit().speed = -1;
                        pb.getCurrBlock().getTrackCircuit().authority = -1;
                        break;
                    } //otherwise set speed and authority to zero, represents no change
                    else {
                        pb.getCurrBlock().getTrackCircuit().speed = -2;
                        pb.getCurrBlock().getTrackCircuit().authority = -2;
                    }

                    //also check if switch is connected to a failed block
                    int switchID = -1;
                    if (lookaheadBlock.getNextBlock() != null) {
                        if (lookaheadBlock.getNextBlock().getType() != Global.PieceType.YARD) {
                            switchID = ((Block) lookaheadBlock.getNextBlock()).getSwitchID();
                        }
                        if (switchID != -1) {
                            if (!isSwitchAccessible(switchID)) {
                                pb.getCurrBlock().getTrackCircuit().speed = -1;
                                pb.getCurrBlock().getTrackCircuit().authority = -1;
                                break;
                            }
                        }
                    }

                    //move ahead by one block
                    if (safeToLookAhead) {
                        Block tempBlock = lookaheadBlock.getCurrBlock();
                        if (lookaheadBlock.getNextBlock().getType() != Global.PieceType.YARD) {
                            lookaheadBlock.setCurrBlock((Block) lookaheadBlock.getNextBlock());
                            lookaheadBlock.setPrevBlock(tempBlock);
                            lookaheadBlock.setNextBlock(lookaheadBlock.getCurrBlock().getNext(lookaheadBlock.getPrevBlock()));
                        } else {
//                            System.out.println("skipped lookahead");
                        }
                    }

                }
            }
        }
        //END OF SAFETY CHECKS
    }

    /**
     * method to evaluate the crossing
     */
    public void evaluateCrossing() {
        //number of blocks to lookahead
        //if presence block is between values given in the wayside sheet
        //activate the crossing
        boolean crossingActive = false;
        if (!occupiedBlocks.isEmpty()) {
            for (PresenceBlock pb : occupiedBlocks) {
                //if the ID of the current block is contained in the list of blocks in the crossing
                //object, then activate the crossing
                if (crossing.getActiveBlocks().contains(pb.getCurrBlock().getID())) {
                    crossingActive = true;
                    break;
                }

            }
        }
        if (crossingActive) {
            crossing.setCurrentCrossingState(Global.CrossingState.ACTIVE);
            this.trackModel.getBlock(controllerLine, crossing.getBlockID()).getCrossing().lower();
        } else {
            crossing.setCurrentCrossingState(Global.CrossingState.INACTIVE);
            this.trackModel.getBlock(controllerLine, crossing.getBlockID()).getCrossing().raise();
        }
    }

    /**
     * method that will check to if the blockID selected can be closed
     *
     * @param blockID
     * @return
     */
    public boolean canClose(int blockID) {
        boolean returnValue = true;
        //get the section that the block is currently in
        //check to ensure that there are no trains within range, before (or after [red]) the section
        //after checks are complete continue and close that section of track
        Block closeBlock = this.trackModel.getBlock(controllerLine, blockID);
        if (closeBlock.isOccupied() || !closeBlock.isOpen() || closeBlock.getFailureBrokenRail() || closeBlock.getFailurePowerOutage() || closeBlock.getFailureTrackCircuit()) {
            returnValue = false;
            return returnValue;
        }
        //need to be able to determine which section is before and after this section
        //in order to traverse
        closeBlock.close();
        return returnValue;
    }

    /**
     * Method to check if the selected blockID can be opened
     *
     * @param blockID
     * @return
     */
    public boolean canOpen(int blockID) {
        Block openBlock = this.trackModel.getBlock(controllerLine, blockID);
        //if not occupied, not open, not broken (3 ways) then open the track
        if (!openBlock.isOccupied() && !openBlock.isOpen() && !openBlock.getFailureBrokenRail() && !openBlock.getFailurePowerOutage() && !openBlock.getFailureTrackCircuit()) {
            openBlock.open();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Getter that will return the track controller GUI
     *
     * @return - TrackControllerGUI object
     */
    public TrackControllerGUI getTrackControllerGUI() {
        return trackControllerGUI;
    }

    /**
     * Getter for the line of the selected Track Controller
     *
     * @return
     */
    public Global.Line getControllerLine() {
        return controllerLine;
    }

    /**
     * Method that will update the Switches in the Track Model given a
     * userSwitchState
     *
     * @param userSwitchState - State the will tell the state of the switches
     */
    private void updateSwitches(UserSwitchState userSwitchState) {
        LinkedList<AbstractMap.SimpleEntry<Integer, Global.SwitchState>> switches = userSwitchState.getUserSwitchStates();
        Iterator switchIterator = switches.iterator();
        while (switchIterator.hasNext()) {
            AbstractMap.SimpleEntry<Integer, Global.SwitchState> switchState = (AbstractMap.SimpleEntry<Integer, Global.SwitchState>) switchIterator.next();
            Switch sw = switchArray.get(switchState.getKey());

            if (isSwitchAccessible(switchState.getKey())) {
                if (switchState.getValue() == Global.SwitchState.DEFAULT) {
                    this.trackModel.getSwitch(switchState.getKey()).setSwitch(false);
                } else {
                    this.trackModel.getSwitch(switchState.getKey()).setSwitch(true);
                }
            } else {
                System.out.println("Turn off lights at switch " + switchState.getKey());
            }
        }
    }

    /**
     * Function that will toggle the position of a switch that has been
     * designated as manual Requires the switch ID that is going to be toggled
     * as an input parameter
     *
     * @param switchID - Switch to be toggled as Integer
     */
    public boolean toggleSwitch(Integer switchID) {
        if (permIgnoreSwitch.contains(String.valueOf(switchID))) {
            Iterator listIterator = this.currentSwitchStates.iterator();
            while (listIterator.hasNext()) {
                UserSwitchState uss = (UserSwitchState) listIterator.next();
                Iterator switchIterator = uss.getUserSwitchStates().iterator();
                while (switchIterator.hasNext()) {
                    SimpleEntry<Integer, Global.SwitchState> switchEntry = (SimpleEntry<Integer, Global.SwitchState>) switchIterator.next();
                    if (switchID == switchEntry.getKey()) {
                        Global.SwitchState currentState = switchEntry.getValue();
                        if (currentState == Global.SwitchState.DEFAULT) {
                            switchEntry.setValue(Global.SwitchState.ALTERNATE);
                        } else {
                            switchEntry.setValue(Global.SwitchState.DEFAULT);
                        }
                        updateSwitches(uss);
                        break;
                    }
                }
            }
            updateSummaryTab();
            return true;
        }
        return false;
    }

    /**
     * Method to update speed and authority on a given block
     * @param blockID
     * @param speed
     * @param authority 
     */
    public void updateSpeedAuthority(int blockID, double speed, double authority) {
        Block b = this.trackModel.getBlock(controllerLine, blockID);
        b.getTrackCircuit().speed = (byte) speed;
        b.getTrackCircuit().authority = (short) (authority / 3.28);
    }

    /**
     * Method that will return a string of switches to be used by the CTC
     * @return 
     */
    public String getSwitches() {
        StringBuilder b = new StringBuilder();
        if (this.currentSwitchStates != null) {
            Iterator iterator = this.currentSwitchStates.iterator();
            while (iterator.hasNext()) {
                UserSwitchState uss = (UserSwitchState) iterator.next();
                Iterator switches = uss.getUserSwitchStates().iterator();
                while (switches.hasNext()) {
                    SimpleEntry<Integer, Global.SwitchState> state = (SimpleEntry<Integer, Global.SwitchState>) switches.next();
                    b.append(state.getKey()).append(":").append(state.getValue());
                }
            }
            return b.toString();
        }
        return null;
    }

    public Crossing getCrossing() {
        return this.crossing;
    }

    public LinkedList<UserSwitchState> getSwitchStates() {
        return this.currentSwitchStates;
    }

    public HashMap<Integer, Switch> getSwitchArray() {
        return this.switchArray;
    }

    public LinkedList<PresenceBlock> getOccupiedBlocks() {
        return this.occupiedBlocks;
    }

    /**
     * Method to set the permenant switches for the track based on the PLC program
     */
    private void configurePermenantSwitch() {
        for (String s : permIgnoreSwitch) {
            UserSwitchState uss = new UserSwitchState();
            AbstractMap.SimpleEntry<Integer, Global.SwitchState> permState = new AbstractMap.SimpleEntry<Integer, Global.SwitchState>(Integer.parseInt(s), Global.SwitchState.DEFAULT);
            uss.addUserSwitchState(permState);
            currentSwitchStates.add(uss);
        }
    }

    /**
     * Returns the a list of switches that are manually controlled Use in CTC
     * for changing a switches position
     *
     * @return - LinkedList of switches as Strings
     */
    public LinkedList<String> getManualSwitches() {
        return this.permIgnoreSwitch;
    }

    /**
     * Method to check whether or not a switch is accessible 
     * used to determine if a failure to a switch should allow a train
     * to continue using that switch
     * @param switchID - switch to check
     * @return 
     */
    private boolean isSwitchAccessible(int switchID) {
        com.rogueone.trackmodel.Switch switchObj = this.trackModel.getSwitch(switchID);
        Block switchBlock1 = (Block) switchObj.getPortA();
        Block switchBlock2 = (Block) switchObj.getPortB();
        Block switchBlock3 = (Block) switchObj.getPortC();
        if (!switchBlock1.getFailurePowerOutage() && !switchBlock2.getFailurePowerOutage() && !switchBlock3.getFailurePowerOutage()
                && !switchBlock1.getFailureBrokenRail() && !switchBlock2.getFailureBrokenRail() && !switchBlock3.getFailureBrokenRail()
                && !switchBlock1.getFailureTrackCircuit() && !switchBlock2.getFailureTrackCircuit() && !switchBlock3.getFailureTrackCircuit()) {
            if (this.offSwitches.contains(switchID)) {
                this.offSwitches.remove((Integer) switchID);
            }
            return true;
        }
        if (switchBlock1.getFailurePowerOutage() || switchBlock2.getFailurePowerOutage() || switchBlock3.getFailurePowerOutage()) {
            if (!this.offSwitches.contains(switchID)) {
                this.offSwitches.add(switchID);
            }
        } else if (switchBlock1.getFailureBrokenRail() || switchBlock2.getFailureBrokenRail() || switchBlock3.getFailureBrokenRail()
                || switchBlock1.getFailureTrackCircuit() || switchBlock2.getFailureTrackCircuit() || switchBlock3.getFailureTrackCircuit()) {

        }
        return false;

    }

    public LinkedList<Integer> getOffSwitches() {
        return this.offSwitches;
    }

    public LinkedList<String> getTrackFailures() {
        return this.trackFailures;
    }
}
