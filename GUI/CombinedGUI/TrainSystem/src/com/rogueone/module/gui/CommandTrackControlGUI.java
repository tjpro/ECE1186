/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rogueone.module.gui;

/**
 *
 * @author kylemonto
 */
public class CommandTrackControlGUI extends javax.swing.JPanel {

    /**
     * Creates new form CommandTrackControlGUI
     */
    public CommandTrackControlGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        MapPanel2 = new javax.swing.JPanel();
        MapImage2 = new javax.swing.JLabel();
        BrowserPanel2 = new javax.swing.JTabbedPane();
        jPanel19 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        TrainLineSelect2 = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel66 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        InformationPanel2 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        InformationHeader2 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        TrainNameLabel2 = new javax.swing.JLabel();
        TrainLineLabel2 = new javax.swing.JLabel();
        TrainSectionLabel2 = new javax.swing.JLabel();
        TrainBlockLabel2 = new javax.swing.JLabel();
        TrainSpeedLabel2 = new javax.swing.JLabel();
        TrainPitchLabel2 = new javax.swing.JLabel();
        TrainAuthorityLabel2 = new javax.swing.JLabel();
        TrainCurrerntCapacityLabel2 = new javax.swing.JLabel();
        TrainMaxCapacityLabel2 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        AdditionalInfoPanel2 = new javax.swing.JPanel();
        EmergencyStopButton2 = new javax.swing.JToggleButton();
        jLabel86 = new javax.swing.JLabel();
        SelectOperationMode2 = new javax.swing.JComboBox<>();
        ChangeOperationModeButton2 = new javax.swing.JButton();
        jLabel87 = new javax.swing.JLabel();
        ChangeParametersButton2 = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 651));

        MapPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Map"));
        MapPanel2.setLayout(new java.awt.GridBagLayout());

        MapImage2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MapImage2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/rogueone/module/gui/images/layout_small.jpg"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.ipady = -8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        MapPanel2.add(MapImage2, gridBagConstraints);

        BrowserPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Browser"));

        jPanel19.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel19ComponentShown(evt);
            }
        });

        jLabel63.setText("Train Line");

        TrainLineSelect2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Green", "Red" }));
        TrainLineSelect2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TrainLineSelect2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrainLineSelect2ActionPerformed(evt);
            }
        });

        jLabel64.setText("Trains");

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(3125), "A:7",  new Boolean(true)},
                { new Integer(254), "D:2",  new Boolean(true)},
                { new Integer(1190), "G:3",  new Boolean(true)},
                { new Integer(52), "G:9", null},
                { new Integer(2955), "M:1",  new Boolean(true)},
                { new Integer(842), "M:10",  new Boolean(true)},
                { new Integer(7922), "Q:3",  new Boolean(true)},
                { new Integer(103), "S:7",  new Boolean(true)},
                { new Integer(8311), "T:2",  new Boolean(true)},
                { new Integer(91), "V:9",  new Boolean(true)},
                { new Integer(1294), "X:7", null},
                { new Integer(399), "Z:2",  new Boolean(true)}
            },
            new String [] {
                "Train", "Position", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(jTable7);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TrainLineSelect2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                        .addGap(31, 31, 31)))
                .addGap(6, 6, 6)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel64)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(jLabel63))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(TrainLineSelect2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 134, Short.MAX_VALUE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        BrowserPanel2.addTab("Trains", jPanel19);

        jPanel20.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel20ComponentShown(evt);
            }
        });

        jLabel65.setText("Train Line");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Green", "Red" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel66.setText("Blocks");

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"A",  new Integer(1),  new Boolean(true)},
                {"A",  new Integer(2),  new Boolean(false)},
                {"A",  new Integer(3),  new Boolean(true)},
                {"B",  new Integer(1),  new Boolean(true)},
                {"B",  new Integer(2),  new Boolean(true)},
                {"B",  new Integer(3),  new Boolean(false)},
                {"B",  new Integer(4),  new Boolean(true)},
                {"B",  new Integer(5),  new Boolean(true)},
                {"C",  new Integer(1),  new Boolean(true)},
                {"C",  new Integer(2),  new Boolean(true)},
                {"C",  new Integer(3),  new Boolean(true)},
                {"C",  new Integer(4),  new Boolean(true)},
                {"D",  new Integer(1),  new Boolean(true)},
                {"D",  new Integer(2),  new Boolean(true)},
                {"D",  new Integer(3),  new Boolean(true)}
            },
            new String [] {
                "Section", "Block", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jTable5);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel66)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 134, Short.MAX_VALUE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        BrowserPanel2.addTab("Blocks", jPanel20);

        jTable8.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.select"));
        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Green", "A",  new Integer(2), "Power"},
                {"Green", "B",  new Integer(3), "Power"},
                {"Red", "F",  new Integer(2), "Broken Rail"},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Track", "Section", "Block", "Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane10.setViewportView(jTable8);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );

        BrowserPanel2.addTab("Failures", jPanel21);

        InformationPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));

        jLabel67.setText("Train Name");

        jLabel68.setText("Current Line");

        jLabel69.setText("Current Section");

        jLabel70.setText("Current Block");

        jLabel71.setText("Speed");

        jLabel72.setText("Authority");

        jLabel73.setText("Pitch");

        jLabel74.setText("Current Capacity");

        InformationHeader2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        InformationHeader2.setText("Selected Train Information");

        jLabel75.setText("Max Capacity");

        TrainNameLabel2.setText("Train 1");

        TrainLineLabel2.setText("Green");

        TrainSectionLabel2.setText("K");

        TrainBlockLabel2.setText("8");

        TrainSpeedLabel2.setText("23.53 km/hr");

        TrainPitchLabel2.setText("4.1 deg");

        TrainAuthorityLabel2.setText("1.2 km");

        TrainCurrerntCapacityLabel2.setText("12");

        TrainMaxCapacityLabel2.setText("45");

        jLabel76.setText("Left Door");

        jLabel77.setText("Right Door");

        jLabel78.setText("Lights");

        jLabel79.setText("Temperature");

        jLabel80.setText("71º");

        jLabel81.setText("ON");

        jLabel82.setText("A/C");

        jLabel83.setText("ON");

        jLabel84.setText("CLOSED");

        jLabel85.setText("CLOSED");

        javax.swing.GroupLayout InformationPanel2Layout = new javax.swing.GroupLayout(InformationPanel2);
        InformationPanel2.setLayout(InformationPanel2Layout);
        InformationPanel2Layout.setHorizontalGroup(
            InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InformationPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InformationPanel2Layout.createSequentialGroup()
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel67)
                            .addComponent(jLabel68)
                            .addComponent(jLabel69)
                            .addComponent(jLabel70)
                            .addComponent(jLabel71)
                            .addComponent(jLabel72)
                            .addComponent(jLabel73)
                            .addComponent(jLabel74)
                            .addComponent(jLabel75))
                        .addGap(18, 18, 18)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TrainSpeedLabel2)
                            .addComponent(TrainMaxCapacityLabel2)
                            .addComponent(TrainCurrerntCapacityLabel2)
                            .addComponent(TrainPitchLabel2)
                            .addComponent(TrainAuthorityLabel2)
                            .addGroup(InformationPanel2Layout.createSequentialGroup()
                                .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TrainNameLabel2)
                                    .addGroup(InformationPanel2Layout.createSequentialGroup()
                                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TrainBlockLabel2)
                                            .addComponent(TrainLineLabel2)
                                            .addComponent(TrainSectionLabel2))
                                        .addGap(88, 88, 88)
                                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel82)
                                            .addComponent(jLabel79)
                                            .addComponent(jLabel78)
                                            .addComponent(jLabel77)
                                            .addComponent(jLabel76))))
                                .addGap(18, 18, 18)
                                .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel85)
                                    .addComponent(jLabel84)
                                    .addComponent(jLabel83)
                                    .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel81)
                                        .addComponent(jLabel80))))))
                    .addComponent(InformationHeader2))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        InformationPanel2Layout.setVerticalGroup(
            InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InformationPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(InformationHeader2)
                .addGap(20, 20, 20)
                .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InformationPanel2Layout.createSequentialGroup()
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel67)
                            .addComponent(TrainNameLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel68)
                            .addComponent(TrainLineLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel69)
                            .addComponent(TrainSectionLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel70)
                            .addComponent(TrainBlockLabel2))
                        .addGap(26, 26, 26)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel71)
                            .addComponent(TrainSpeedLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(InformationPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel72)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel73))
                            .addGroup(InformationPanel2Layout.createSequentialGroup()
                                .addComponent(TrainAuthorityLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TrainPitchLabel2)))
                        .addGap(33, 33, 33)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(InformationPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel74)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel75))
                            .addGroup(InformationPanel2Layout.createSequentialGroup()
                                .addComponent(TrainCurrerntCapacityLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TrainMaxCapacityLabel2)))
                        .addGap(24, 24, 24))
                    .addGroup(InformationPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel76)
                            .addComponent(jLabel85))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel77)
                            .addComponent(jLabel84))
                        .addGap(13, 13, 13)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel78)
                            .addComponent(jLabel83))
                        .addGap(18, 18, 18)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel79, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel80))
                        .addGap(18, 18, 18)
                        .addGroup(InformationPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel82)
                            .addComponent(jLabel81))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        AdditionalInfoPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        EmergencyStopButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/rogueone/module/gui/images/stop_sign.png"))); // NOI18N
        EmergencyStopButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmergencyStopButton2ActionPerformed(evt);
            }
        });

        jLabel86.setText("Operation Mode");

        SelectOperationMode2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manual", "Automatic" }));
        SelectOperationMode2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectOperationMode2ActionPerformed(evt);
            }
        });

        ChangeOperationModeButton2.setText("Change");

        jLabel87.setText("Change Suggested Parameters");

        ChangeParametersButton2.setText("CHANGE ");
        ChangeParametersButton2.setEnabled(false);
        ChangeParametersButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangeParametersButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AdditionalInfoPanel2Layout = new javax.swing.GroupLayout(AdditionalInfoPanel2);
        AdditionalInfoPanel2.setLayout(AdditionalInfoPanel2Layout);
        AdditionalInfoPanel2Layout.setHorizontalGroup(
            AdditionalInfoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdditionalInfoPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EmergencyStopButton2)
                .addGap(18, 18, 18)
                .addGroup(AdditionalInfoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChangeOperationModeButton2)
                    .addGroup(AdditionalInfoPanel2Layout.createSequentialGroup()
                        .addGroup(AdditionalInfoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel86)
                            .addComponent(SelectOperationMode2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(AdditionalInfoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AdditionalInfoPanel2Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel87))
                            .addGroup(AdditionalInfoPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ChangeParametersButton2)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AdditionalInfoPanel2Layout.setVerticalGroup(
            AdditionalInfoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdditionalInfoPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(AdditionalInfoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AdditionalInfoPanel2Layout.createSequentialGroup()
                        .addGroup(AdditionalInfoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel86)
                            .addComponent(jLabel87))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AdditionalInfoPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SelectOperationMode2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ChangeParametersButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ChangeOperationModeButton2))
                    .addComponent(EmergencyStopButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MapPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AdditionalInfoPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BrowserPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InformationPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(306, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(BrowserPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(InformationPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(MapPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AdditionalInfoPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 77;
        gridBagConstraints.ipady = 77;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void TrainLineSelect2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrainLineSelect2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TrainLineSelect2ActionPerformed

    private void jPanel19ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel19ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel19ComponentShown

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jPanel20ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel20ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel20ComponentShown

    private void EmergencyStopButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmergencyStopButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmergencyStopButton2ActionPerformed

    private void SelectOperationMode2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectOperationMode2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectOperationMode2ActionPerformed

    private void ChangeParametersButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangeParametersButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChangeParametersButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdditionalInfoPanel2;
    private javax.swing.JTabbedPane BrowserPanel2;
    private javax.swing.JButton ChangeOperationModeButton2;
    private javax.swing.JButton ChangeParametersButton2;
    private javax.swing.JToggleButton EmergencyStopButton2;
    private javax.swing.JLabel InformationHeader2;
    private javax.swing.JPanel InformationPanel2;
    private javax.swing.JLabel MapImage2;
    private javax.swing.JPanel MapPanel2;
    private javax.swing.JComboBox<String> SelectOperationMode2;
    private javax.swing.JLabel TrainAuthorityLabel2;
    private javax.swing.JLabel TrainBlockLabel2;
    private javax.swing.JLabel TrainCurrerntCapacityLabel2;
    private javax.swing.JLabel TrainLineLabel2;
    private javax.swing.JComboBox<String> TrainLineSelect2;
    private javax.swing.JLabel TrainMaxCapacityLabel2;
    private javax.swing.JLabel TrainNameLabel2;
    private javax.swing.JLabel TrainPitchLabel2;
    private javax.swing.JLabel TrainSectionLabel2;
    private javax.swing.JLabel TrainSpeedLabel2;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    // End of variables declaration//GEN-END:variables
}
