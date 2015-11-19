/*
 * EmpListXMLView.java
 */

package emplistxml;

import business.EmpIO;
import business.Employee;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The application's main frame.
 */
public class EmpListXMLView extends FrameView {
    /*
        0 means not running load process
        1 means program is in load mode
    */
    int loading = 0; 
    String fileName;
    Map<Long, Employee> emps;
    Map<String, Employee> empsbyname;
    Map<String, JTextField> screenmap;
    String[] getmethods = { "getEmpNo", "getLastName", "getFirstName",
                "getMiddleName", "getSuffix", "getAddress1", "getAddress2",
                "getCity", "getState", "getZip", "getPhone", "getGender",
                "getPayCode", "getStatus", "getHireDate", "getTermDate" };
    JTextField[] fields;

    public EmpListXMLView(SingleFrameApplication app) {
        super(app);

        initComponents();
        JTextField[] fld = { jtxtEmpNo, jtxtLastName, jtxtFirstName,
                    jtxtMiddleName, jtxtSuffix, jtxtAddr1, jtxtAddr2,
                    jtxtCity, jtxtState, jtxtZip, jtxtPhone, jtxtGender,
                    jtxtPayCode, jtxtStatus, jtxtHireDate, jtxtTermDate };
        fields = fld;
        screenmap = new HashMap<>();
        for (int i=0; i < getmethods.length; i++) {
            screenmap.put(getmethods[i], fields[i]);
        }
        
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = EmpListXMLApp.getApplication().getMainFrame();
            aboutBox = new EmpListXMLAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        EmpListXMLApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jradRawHashMap = new javax.swing.JRadioButton();
        jradNameMap = new javax.swing.JRadioButton();
        jradTreeMap = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jcmbKeys = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtxtEmpNo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtxtLastName = new javax.swing.JTextField();
        jtxtFirstName = new javax.swing.JTextField();
        jtxtMiddleName = new javax.swing.JTextField();
        jtxtSuffix = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtAddr1 = new javax.swing.JTextField();
        jtxtAddr2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtxtCity = new javax.swing.JTextField();
        jtxtState = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jtxtZip = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jtxtPhone = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jtxtGender = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jtxtPayCode = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jtxtStatus = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jtxtHireDate = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jtxtTermDate = new javax.swing.JTextField();
        jbtnClear = new javax.swing.JButton();
        jbtnDelete = new javax.swing.JButton();
        jbtnUpdate = new javax.swing.JButton();
        jbtnAdd = new javax.swing.JButton();
        jbtnNextRecord = new javax.swing.JButton();
        jbtnPreviousRecord = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jmnuLoad = new javax.swing.JMenuItem();
        jmnuLoadXML = new javax.swing.JMenuItem();
        jmnuSave = new javax.swing.JMenuItem();
        jmnuSaveXML = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        buttonGroup1 = new javax.swing.ButtonGroup();

        mainPanel.setName("mainPanel"); // NOI18N

        buttonGroup1.add(jradRawHashMap);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(emplistxml.EmpListXMLApp.class).getContext().getResourceMap(EmpListXMLView.class);
        jradRawHashMap.setText(resourceMap.getString("jradRawHashMap.text")); // NOI18N
        jradRawHashMap.setEnabled(false);
        jradRawHashMap.setName("jradRawHashMap"); // NOI18N
        jradRawHashMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jradRawHashMapActionPerformed(evt);
            }
        });

        buttonGroup1.add(jradNameMap);
        jradNameMap.setText(resourceMap.getString("jradNameMap.text")); // NOI18N
        jradNameMap.setEnabled(false);
        jradNameMap.setName("jradNameMap"); // NOI18N
        jradNameMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jradNameMapActionPerformed(evt);
            }
        });

        buttonGroup1.add(jradTreeMap);
        jradTreeMap.setText(resourceMap.getString("jradTreeMap.text")); // NOI18N
        jradTreeMap.setEnabled(false);
        jradTreeMap.setName("jradTreeMap"); // NOI18N
        jradTreeMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jradTreeMapActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jcmbKeys.setName("jcmbKeys"); // NOI18N
        jcmbKeys.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcmbKeysItemStateChanged(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jtxtEmpNo.setText(resourceMap.getString("jtxtEmpNo.text")); // NOI18N
        jtxtEmpNo.setName("jtxtEmpNo"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jtxtLastName.setName("jtxtLastName"); // NOI18N

        jtxtFirstName.setName("jtxtFirstName"); // NOI18N

        jtxtMiddleName.setName("jtxtMiddleName"); // NOI18N

        jtxtSuffix.setName("jtxtSuffix"); // NOI18N

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jtxtAddr1.setName("jtxtAddr1"); // NOI18N

        jtxtAddr2.setName("jtxtAddr2"); // NOI18N

        jLabel9.setFont(resourceMap.getFont("jLabel9.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jtxtCity.setName("jtxtCity"); // NOI18N

        jtxtState.setName("jtxtState"); // NOI18N

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel11.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jtxtZip.setName("jtxtZip"); // NOI18N

        jLabel12.setFont(resourceMap.getFont("jLabel12.font")); // NOI18N
        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jtxtPhone.setName("jtxtPhone"); // NOI18N

        jLabel13.setFont(resourceMap.getFont("jLabel13.font")); // NOI18N
        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jtxtGender.setText(resourceMap.getString("jtxtGender.text")); // NOI18N
        jtxtGender.setName("jtxtGender"); // NOI18N

        jLabel14.setFont(resourceMap.getFont("jLabel14.font")); // NOI18N
        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jtxtPayCode.setName("jtxtPayCode"); // NOI18N

        jLabel15.setFont(resourceMap.getFont("jLabel15.font")); // NOI18N
        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jtxtStatus.setName("jtxtStatus"); // NOI18N

        jLabel16.setFont(resourceMap.getFont("jLabel16.font")); // NOI18N
        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jtxtHireDate.setName("jtxtHireDate"); // NOI18N

        jLabel17.setFont(resourceMap.getFont("jLabel17.font")); // NOI18N
        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jtxtTermDate.setName("jtxtTermDate"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jtxtEmpNo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxtMiddleName, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jtxtSuffix)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel3)
                        .addGap(56, 56, 56)
                        .addComponent(jLabel4)
                        .addGap(92, 92, 92)
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtAddr2)
                            .addComponent(jtxtAddr1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxtCity, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxtState, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addGap(11, 11, 11)
                                .addComponent(jtxtZip))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtxtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtxtHireDate, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtxtTermDate, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtxtPhone)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtxtGender, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtxtPayCode, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtEmpNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtMiddleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtSuffix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtxtAddr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtAddr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11)
                        .addComponent(jtxtState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxtZip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jtxtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtPayCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jtxtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtHireDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtTermDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbtnClear.setText(resourceMap.getString("jbtnClear.text")); // NOI18N
        jbtnClear.setName("jbtnClear"); // NOI18N
        jbtnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnClearActionPerformed(evt);
            }
        });

        jbtnDelete.setText(resourceMap.getString("jbtnDelete.text")); // NOI18N
        jbtnDelete.setName("jbtnDelete"); // NOI18N
        jbtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeleteActionPerformed(evt);
            }
        });

        jbtnUpdate.setText(resourceMap.getString("jbtnUpdate.text")); // NOI18N
        jbtnUpdate.setName("jbtnUpdate"); // NOI18N
        jbtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdateActionPerformed(evt);
            }
        });

        jbtnAdd.setText(resourceMap.getString("jbtnAdd.text")); // NOI18N
        jbtnAdd.setName("jbtnAdd"); // NOI18N
        jbtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddActionPerformed(evt);
            }
        });

        jbtnNextRecord.setText(resourceMap.getString("jbtnNextRecord.text")); // NOI18N
        jbtnNextRecord.setName("jbtnNextRecord"); // NOI18N
        jbtnNextRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNextRecordActionPerformed(evt);
            }
        });

        jbtnPreviousRecord.setText(resourceMap.getString("jbtnPreviousRecord.text")); // NOI18N
        jbtnPreviousRecord.setName("jbtnPreviousRecord"); // NOI18N
        jbtnPreviousRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPreviousRecordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jradRawHashMap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jradTreeMap)
                        .addGap(77, 77, 77)
                        .addComponent(jradNameMap)
                        .addGap(45, 45, 45))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jbtnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jbtnPreviousRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcmbKeys, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbtnNextRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(15, Short.MAX_VALUE))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jradRawHashMap)
                    .addComponent(jradNameMap)
                    .addComponent(jradTreeMap))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcmbKeys, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnNextRecord)
                    .addComponent(jbtnPreviousRecord))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnClear)
                    .addComponent(jbtnDelete)
                    .addComponent(jbtnUpdate)
                    .addComponent(jbtnAdd))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jmnuLoad.setText(resourceMap.getString("jmnuLoad.text")); // NOI18N
        jmnuLoad.setName("jmnuLoad"); // NOI18N
        jmnuLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuLoadActionPerformed(evt);
            }
        });
        fileMenu.add(jmnuLoad);

        jmnuLoadXML.setText(resourceMap.getString("jmnuLoadXML.text")); // NOI18N
        jmnuLoadXML.setName("jmnuLoadXML"); // NOI18N
        jmnuLoadXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuLoadXMLActionPerformed(evt);
            }
        });
        fileMenu.add(jmnuLoadXML);

        jmnuSave.setText(resourceMap.getString("jmnuSave.text")); // NOI18N
        jmnuSave.setName("jmnuSave"); // NOI18N
        jmnuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuSaveActionPerformed(evt);
            }
        });
        fileMenu.add(jmnuSave);

        jmnuSaveXML.setText(resourceMap.getString("jmnuSaveXML.text")); // NOI18N
        jmnuSaveXML.setName("jmnuSaveXML"); // NOI18N
        jmnuSaveXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuSaveXMLActionPerformed(evt);
            }
        });
        fileMenu.add(jmnuSaveXML);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(emplistxml.EmpListXMLApp.class).getContext().getActionMap(EmpListXMLView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 416, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jmnuLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuLoadActionPerformed
        statusMessageLabel.setText("");
        JFileChooser f = new JFileChooser(".");
        f.setDialogTitle("Select Employee File");
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
        f.setFileFilter(filter);
        JDialog dg = new JDialog();
        int returnValue = f.showOpenDialog(dg);
        
        if (returnValue == JFileChooser.CANCEL_OPTION) {
            statusMessageLabel.setText("Open canceled.");
        } else {
            fileName = f.getSelectedFile().getAbsolutePath();
            emps = EmpIO.getEmps(fileName);
            statusMessageLabel.setText(String.valueOf(emps.size()));
            jradRawHashMap.setEnabled(true);
            jradTreeMap.setEnabled(true);
            jradNameMap.setEnabled(true);
        }
    }//GEN-LAST:event_jmnuLoadActionPerformed

    private void jradRawHashMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jradRawHashMapActionPerformed
        if (jradRawHashMap.isSelected()) {
            cmbKeys_Build();
        }
    }//GEN-LAST:event_jradRawHashMapActionPerformed

    private void jradTreeMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jradTreeMapActionPerformed
        if (jradTreeMap.isSelected()) {
            cmbKeys_Build();
        }
    }//GEN-LAST:event_jradTreeMapActionPerformed

    private void jradNameMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jradNameMapActionPerformed
        if (jradNameMap.isSelected()) {
            empsbyname = new TreeMap<>();
            for (Map.Entry<Long, Employee> entry : emps.entrySet()) {
                Employee emp = entry.getValue();
                String nm = emp.getLastName() + ", " + emp.getFirstName() +
                            " " + emp.getMiddleName();
                empsbyname.put(nm, emp);
            }
            cmbKeys_Build();
        }
    }//GEN-LAST:event_jradNameMapActionPerformed

    private void jcmbKeysItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcmbKeysItemStateChanged
        Employee emp;
        if (loading == 1) { return; }
        if (jcmbKeys.getSelectedIndex() == -1) {
            statusMessageLabel.setText("No selected employee");
        } else {
            if (jradNameMap.isSelected()) {
                /*
                    Pulling selected item / name from combo box
                */
                emp = (Employee) empsbyname.get((String)jcmbKeys.getSelectedItem());
            } else {
                emp = (Employee) emps.get((Long)jcmbKeys.getSelectedItem());
            }
            DisplayValues(emp);
        }
    }//GEN-LAST:event_jcmbKeysItemStateChanged

    private void jbtnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnClearActionPerformed
        for (JTextField f : fields) {
            f.setText("");
        }
        jcmbKeys.setSelectedIndex(-1);
    }//GEN-LAST:event_jbtnClearActionPerformed

    private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteActionPerformed
        /* 
            How to keep hashmaps consistent if the delete originates when user
            has chosen the name key.
        
            issue: if delete originates from Name Hashmap, empsbyname is removed
            but emps is not updates in the following code...
        */
        if (jcmbKeys.getSelectedIndex() == -1) {
            statusMessageLabel.setText("No selected employee");
            return;
        }
        if (jradNameMap.isSelected()) {
            empsbyname.remove((String)jcmbKeys.getSelectedItem());
        } else {
            emps.remove((Long)jcmbKeys.getSelectedItem());
        }
        cmbKeys_Build();
        jbtnClearActionPerformed(evt);
    }//GEN-LAST:event_jbtnDeleteActionPerformed

    private void jbtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUpdateActionPerformed
        statusMessageLabel.setText("");
        Employee emp;
        if (jcmbKeys.getSelectedIndex() == -1) {
            statusMessageLabel.setText("No employee selected");
            return;
        }
        if (jradNameMap.isSelected()) {
            emp = empsbyname.get((String)jcmbKeys.getSelectedItem());
            if (UpdateValues(emp)) {
                empsbyname.remove((String)jcmbKeys.getSelectedItem());
                String key = emp.getLastName() + ", " + emp.getFirstName() + 
                        " " + emp.getMiddleName();
                // empsbyname.put((String)jcmbKeys.getSelectedItem(), emp);
                empsbyname.put(key, emp);
                cmbKeys_Build();
            }
        } else {
            emp = emps.get((Long)jcmbKeys.getSelectedItem());
            if (UpdateValues(emp)) {
                emps.remove((Long)jcmbKeys.getSelectedItem());
                emps.put((Long)emp.getEmpNo(), emp);
                cmbKeys_Build();
            } 
        }
        // sync
    }//GEN-LAST:event_jbtnUpdateActionPerformed

    private void jbtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddActionPerformed
        statusMessageLabel.setText("");
        Employee emp = new Employee();
        if (UpdateValues(emp)) {
            if (jradNameMap.isSelected()) {
                String key = emp.getLastName() + ", " + emp.getFirstName() +
                        " " + emp.getMiddleName();
                if (empsbyname.containsKey(key)) {
                    statusMessageLabel.setText("New employee already in data set");
                } else {
                    empsbyname.put(key, emp);
                    cmbKeys_Build();
                }
            } else {
                if (emps.containsKey(emp.getEmpNo())) {
                    statusMessageLabel.setText("New employee already in data set");
                } else {
                    emps.put(emp.getEmpNo(), emp);
                    cmbKeys_Build();
                }
            }
        }
        // sync
    }//GEN-LAST:event_jbtnAddActionPerformed

    private void jmnuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuSaveActionPerformed
        statusMessageLabel.setText("");
        JFileChooser f = new JFileChooser(".");
        f.setDialogTitle("Select Output File For Employee");
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
        f.setFileFilter(filter);
        JDialog dg = new JDialog();
        int returnValue = f.showSaveDialog(dg);
        
        if (returnValue == JFileChooser.CANCEL_OPTION) {
            statusMessageLabel.setText("Save canceled.");
        } else {
            fileName = f.getSelectedFile().getAbsolutePath();
            String status = EmpIO.setEmps(fileName, emps);
            statusMessageLabel.setText(status);
            jradRawHashMap.setEnabled(true);
            jradTreeMap.setEnabled(true);
            jradNameMap.setEnabled(true);
        }
    }//GEN-LAST:event_jmnuSaveActionPerformed

    private void jbtnNextRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNextRecordActionPerformed
        int length;
        int currentIndex;
        
        try {
            length = jcmbKeys.getItemCount();
            currentIndex = jcmbKeys.getSelectedIndex();
            if ((currentIndex + 1) > length) {
                throw new Exception();
            }
            currentIndex += 1;
            jcmbKeys.setSelectedIndex(currentIndex);
        } catch (Exception e) {
            statusMessageLabel.setText("Error finding next employee - " + 
                    e.getMessage());
        }
    }//GEN-LAST:event_jbtnNextRecordActionPerformed

    private void jbtnPreviousRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPreviousRecordActionPerformed
        int length;
        int currentIndex;
        
        try {
            length = jcmbKeys.getItemCount();
            currentIndex = jcmbKeys.getSelectedIndex();
            if ((currentIndex - 1) < 0) {
                throw new Exception();
            }
            currentIndex -= 1;
            jcmbKeys.setSelectedIndex(currentIndex);
        } catch (Exception e) {
            statusMessageLabel.setText("Error finding next employee - " + 
                    e.getMessage());
        }
    }//GEN-LAST:event_jbtnPreviousRecordActionPerformed

    private void jmnuSaveXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuSaveXMLActionPerformed
        statusMessageLabel.setText("");
        JFileChooser f = new JFileChooser(".");
        f.setDialogTitle("Select Output File For Employee");
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("XML Files (*.xml)", "xml");
        f.setFileFilter(filter);
        JDialog dg = new JDialog();
        int returnValue = f.showSaveDialog(dg);
        
        if (returnValue == JFileChooser.CANCEL_OPTION) {
            statusMessageLabel.setText("Save canceled.");
        } else {
            fileName = f.getSelectedFile().getAbsolutePath();
            String status = EmpIO.setEmpsXML(fileName, emps);
            statusMessageLabel.setText(status);
            jradRawHashMap.setEnabled(true);
            jradTreeMap.setEnabled(true);
            jradNameMap.setEnabled(true);
        }
    }//GEN-LAST:event_jmnuSaveXMLActionPerformed

    private void jmnuLoadXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuLoadXMLActionPerformed
        statusMessageLabel.setText("");
        JFileChooser f = new JFileChooser(".");
        f.setDialogTitle("Select Employee File");
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("XML Files (*.xml)", "xml");
        f.setFileFilter(filter);
        JDialog dg = new JDialog();
        int returnValue = f.showOpenDialog(dg);
        
        if (returnValue == JFileChooser.CANCEL_OPTION) {
            statusMessageLabel.setText("Open canceled.");
        } else {
            fileName = f.getSelectedFile().getAbsolutePath();
            emps = EmpIO.getEmpsXML(fileName);
            statusMessageLabel.setText(String.valueOf(emps.size()));
            jradRawHashMap.setEnabled(true);
            jradTreeMap.setEnabled(true);
            jradNameMap.setEnabled(true);
        }
    }//GEN-LAST:event_jmnuLoadXMLActionPerformed
    
    private boolean UpdateValues(Employee emp) {
        Class empclass = emp.getClass();
        Method m;
        boolean goodUpdate;
        
        try {
            for (String nm : getmethods) {
                JTextField f = screenmap.get(nm); // get text field for property
                String setmethod = "set" + nm.substring(3);
                switch (setmethod) {
                    /*
                        The following is not validating numbers
                    */
                    case "setEmpNo":
                        long x = Long.parseLong(f.getText());
                        if (x <= 0) { 
                            throw new NumberFormatException();
                        }
                        m = empclass.getMethod(setmethod, new Class[] {Long.class});
                        m.invoke(emp, x);
                        break;
                    case "setPhone":
                    case "setPayCode":
                        try {
                            int y = Integer.parseInt(f.getText());
                            m = empclass.getMethod(setmethod, new Class[] {Integer.class});
                            m.invoke(emp, y);
                        } catch (NumberFormatException e) {
                            // ignore update - text field on form is empty
                        }
                        break;
                    default:
                        m = empclass.getMethod(setmethod, new Class[] {String.class});
                        m.invoke(emp, f.getText());
                        break;
                } // end of switch(setmethod)
            } // end of for (String nm : getmethods)
            goodUpdate = true;
        } catch (NumberFormatException e) {
            statusMessageLabel.setText("Bad numeric value found: " + e.getMessage());
            goodUpdate = false;
        } catch (Exception e) {
            statusMessageLabel.setText("Upadate error: " + e.getMessage());
            goodUpdate = false;
        }
        return goodUpdate;
    }
    
    private void DisplayValues(Employee emp) {
        for (JTextField f : fields) {
            f.setText("");
        }
        
        Class empclass = emp.getClass();
        Method[] methods = empclass.getMethods();
        
        try {
            for (Method m : methods) {
                if (screenmap.containsKey(m.getName())) {
                    JTextField f = screenmap.get(m.getName());
                    switch (m.getName()) {
                        case "getEmpNo":
                            long x = (long)m.invoke(emp, null);
                            f.setText(String.valueOf(x));
                            break;
                        case "getPhone":
                        case "getPayCode":
                            int y = (int)m.invoke(emp, null);
                            f.setText(String.valueOf(y));
                            break;
                        default:
                            f.setText((String)m.invoke(emp, null));
                            break;
                    }
                }
            }
        } catch (Exception e) {
            statusMessageLabel.setText(e.getMessage());
        }
    }
    
    private void cmbKeys_Build() {
        loading = 1;
        jcmbKeys.removeAllItems();
        
        if (jradRawHashMap.isSelected()) {
            Set<Long> keys = emps.keySet();
            ArrayList<Long> akeys = new ArrayList<>(keys);
            for (Long k : akeys) {
                jcmbKeys.addItem(k);
            }
        } else if (jradTreeMap.isSelected()) {
            TreeMap<Long, Employee> tmap = new TreeMap<>(emps);
            for (Map.Entry<Long, Employee> entry : tmap.entrySet()) {
                Long k = entry.getKey();
                jcmbKeys.addItem(k);
            }
        } else if (jradNameMap.isSelected()) {
            for (Map.Entry<String, Employee> entry : empsbyname.entrySet()) {
                String nm = entry.getKey();
                jcmbKeys.addItem(nm);
            }
        }
        jcmbKeys.setSelectedIndex(-1);
        for (JTextField f : fields) {
            f.setText("");
        }
        loading = 0;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtnAdd;
    private javax.swing.JButton jbtnClear;
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JButton jbtnNextRecord;
    private javax.swing.JButton jbtnPreviousRecord;
    private javax.swing.JButton jbtnUpdate;
    private javax.swing.JComboBox jcmbKeys;
    private javax.swing.JMenuItem jmnuLoad;
    private javax.swing.JMenuItem jmnuLoadXML;
    private javax.swing.JMenuItem jmnuSave;
    private javax.swing.JMenuItem jmnuSaveXML;
    private javax.swing.JRadioButton jradNameMap;
    private javax.swing.JRadioButton jradRawHashMap;
    private javax.swing.JRadioButton jradTreeMap;
    private javax.swing.JTextField jtxtAddr1;
    private javax.swing.JTextField jtxtAddr2;
    private javax.swing.JTextField jtxtCity;
    private javax.swing.JTextField jtxtEmpNo;
    private javax.swing.JTextField jtxtFirstName;
    private javax.swing.JTextField jtxtGender;
    private javax.swing.JTextField jtxtHireDate;
    private javax.swing.JTextField jtxtLastName;
    private javax.swing.JTextField jtxtMiddleName;
    private javax.swing.JTextField jtxtPayCode;
    private javax.swing.JTextField jtxtPhone;
    private javax.swing.JTextField jtxtState;
    private javax.swing.JTextField jtxtStatus;
    private javax.swing.JTextField jtxtSuffix;
    private javax.swing.JTextField jtxtTermDate;
    private javax.swing.JTextField jtxtZip;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
