
// Name: Sourabh Saini
// Roll No: 2019113

// Input Type : dd/mm/yyyy

// Output:
// In starting when no check box is selected code is print all cases
// In the left text area I’m displaying active cases with patient details and recovery dates.
// In the right text area I’m displaying recovered cases with patient details.
// Recovery date notice: I’m not counting day of reporting while checking for 21 days recovery time, that is if someone report on day 1 it will recover on 23.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

// Main class

public class gui {

    public static void main(String[] args) {
        testing app = new testing();

    }
}

// Report Creating class and some helper function
class report {
    static boolean error;

    public static void helper(String date, LinkedList<?> activePatientA, LinkedList<?> activePatientB,
            LinkedList<?> activePatientC, LinkedList<?> activePatientD, LinkedList<?> recoveredPatientA,
            LinkedList<?> recoveredPatientB, LinkedList<?> recoveredPatientC, LinkedList<?> recoveredPatientD) {

        patient[] covid = new patient[20];
        objectCreater(covid);

        activeCases(date, covid, activePatientA, activePatientB, activePatientC, activePatientD);
        reCoveredCases(date, covid, recoveredPatientA, recoveredPatientB, recoveredPatientC, recoveredPatientD);

    }

    private static void objectCreater(patient[] covid) {
        covid[0] = new patient("Flora", 6, 'A', "01/04/2020");
        covid[1] = new patient("Denys", 24, 'B', "01/04/2020");
        covid[2] = new patient("Jim", 42, 'C', "18/05/2020");
        covid[3] = new patient("Hazel", 87, 'D', "23/06/2020");
        covid[4] = new patient("Caery", 72, 'A', "01/06/2020");
        covid[5] = new patient("David", 7, 'B', "14/06/2020");
        covid[6] = new patient("Kevim", 37, 'D', "05/06/2020");
        covid[7] = new patient("Tom", 67, 'D', "20/06/2020");
        covid[8] = new patient("Bob", 74, 'A', "04/07/2020");
        covid[9] = new patient("Rachel", 48, 'C', "24/07/2020");
        covid[10] = new patient("Thomas", 21, 'C', "11/06/2020");
        covid[11] = new patient("Mary", 17, 'D', "21/06/2020");
        covid[12] = new patient("Smith", 89, 'A', "07/08/2020");
        covid[13] = new patient("Pearson", 47, 'B', "04/06/2020");
        covid[14] = new patient("Anderson", 62, 'B', "27/07/2020");
        covid[15] = new patient("Johnson", 10, 'D', "01/08/2020");
        covid[16] = new patient("Robertz", 50, 'A', "09/08/2020");
        covid[17] = new patient("Julie", 86, 'B', "02/05/2020");
        covid[18] = new patient("Edith", 42, 'D', "07/06/2020");
        covid[19] = new patient("John", 95, 'D', "01/06/2020");

    }

    private static void reCoveredCases(String date, patient[] covid, LinkedList recoveredPatientA,
            LinkedList recoveredPatientB, LinkedList recoveredPatientC, LinkedList recoveredPatientD) {
        for (patient j : covid) {
            long days = durationInDays(date, j.DateOfReporting);
            // System.out.println(days);
            if (days > 21) {

                if (j.Tower == 'A') {
                    recoveredPatientA.add(j);
                } else if (j.Tower == 'B') {

                    recoveredPatientB.add(j);

                } else if (j.Tower == 'C') {
                    recoveredPatientC.add(j);

                } else if (j.Tower == 'D') {
                    recoveredPatientD.add(j);

                }
            }

        }

    }

    public static void activeCases(String date, patient[] covidArr, LinkedList activePatientA,
            LinkedList activePatientB, LinkedList activePatientC, LinkedList activePatientD) {

        for (patient j : covidArr) {
            long days = durationInDays(date, j.DateOfReporting);
            // System.out.println(days);
            if (0 < (days) && days <= 21) {

                if (j.Tower == 'A') {
                    activePatientA.add(j);
                } else if (j.Tower == 'B') {

                    activePatientB.add(j);

                } else if (j.Tower == 'C') {
                    activePatientC.add(j);

                } else if (j.Tower == 'D') {
                    activePatientD.add(j);

                }
            }

        }

    }

    private static long durationInDays(String date, String dateOfReporting) {
        try {
            error = false;
            DateFormat dateobj = new SimpleDateFormat("dd/MM/yyyy");
            dateobj.setLenient(false);
            Date reportingDate = dateobj.parse(dateOfReporting);

            Date checkingDate = dateobj.parse(date);
            long u = checkingDate.getTime() - reportingDate.getTime();
            long days = (u / (1000 * 60 * 60 * 24)) % 365;
            return days;
        } catch (ParseException e) {
            // TODO: handle exception
            error = true;

            e.printStackTrace();

            return -1;

        }
    }
}

// Patient class work
class patient {
    String Name;
    int Age;
    char Tower;
    String DateOfReporting;

    public patient() {

    }

    public patient(String name, int age, char tower, String dateOfReporting) {
        Name = name;
        Age = age;
        Tower = tower;
        DateOfReporting = dateOfReporting;
    }
}

// Jframe class and all Gui backend
class testing extends JFrame implements ActionListener {

    static LinkedList<?> activePatientA;
    static LinkedList<?> activePatientB;
    static LinkedList<?> activePatientC;
    static LinkedList<?> activePatientD;
    static LinkedList<?> recoveredPatientA;
    static LinkedList<?> recoveredPatientB;
    static LinkedList<?> recoveredPatientC;
    static LinkedList<?> recoveredPatientD;
    // ===============+======
    private static final long serialVersionUID = 1L;
    JPanel panel;
    JLabel inputLabel;
    JLabel activeCasesLabel;
    JLabel recoveredCasesLabel;

    JTextField inputField;
    JButton findCases;
    JCheckBox towerA;
    JCheckBox towerB;
    JCheckBox towerC;
    JCheckBox towerD;
    JTextArea activeCases;
    JTextArea recoveredCases;

    testing() {
        FlowLayout flowlayout = new FlowLayout();

        // Main Container
        Container pane = getContentPane();
        // JScrollPane cont = new JScrollPane(pane);

        // UpperPanel UI
        inputLabel = new JLabel("Input type ==> dd/mm/yyyy");
        inputField = new JTextField(30);
        findCases = new JButton("Find Cases");
        towerA = new JCheckBox("Tower A");
        towerB = new JCheckBox("Tower B");
        towerC = new JCheckBox("Tower C");
        towerD = new JCheckBox("Tower D");

        JPanel upperPanel = new JPanel();

        upperPanel.setLayout(flowlayout);
        upperPanel.add(inputLabel);
        upperPanel.add(inputField);
        upperPanel.add(findCases);
        upperPanel.add(towerA);
        upperPanel.add(towerB);
        upperPanel.add(towerC);
        upperPanel.add(towerD);

        pane.add(upperPanel, BorderLayout.PAGE_START);

        // ActionListener UpperPanel
        inputField.addActionListener(this);
        findCases.addActionListener(this);
        towerA.addActionListener(this);
        towerB.addActionListener(this);
        towerC.addActionListener(this);
        towerD.addActionListener(this);
        Toolkit tool = Toolkit.getDefaultToolkit();
        Dimension screeSize = tool.getScreenSize();
        // Middle UI
        GridLayout gridLayout = new GridLayout(1, 2);
        BorderLayout borderLayout = new BorderLayout();
        int textAreaWidth = 40;
        int textAreaHeight = 40;
        activeCases = new JTextArea(textAreaWidth, textAreaHeight);
        recoveredCases = new JTextArea(textAreaWidth, textAreaHeight);
        activeCases.setEditable(false);
        recoveredCases.setEditable(false);
        activeCases.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        recoveredCases.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        activeCases.setLayout(null);
        activeCases.setLineWrap(true);
        activeCases.setWrapStyleWord(true);
        recoveredCases.setLineWrap(true);
        recoveredCases.setWrapStyleWord(true);

        activeCasesLabel = new JLabel("Active Cases");
        recoveredCasesLabel = new JLabel("Recovered Cases");
        JScrollPane activeScroll = new JScrollPane(activeCases);
        JScrollPane recoverScroll = new JScrollPane(recoveredCases);

        JPanel middlePanel = new JPanel();
        // JPanel middlePanel1 = new JPanel();
        // JPanel middlePanel2 = new JPanel();

        middlePanel.setLayout(flowlayout);
        middlePanel.add(activeCasesLabel);

        middlePanel.add(activeScroll);
        middlePanel.add(recoveredCasesLabel);
        middlePanel.add(recoverScroll);
        pane.add(middlePanel, BorderLayout.CENTER);
        // Action Listener for middle pannel

        // Jframe Basics

        // setResizable(false);
        setVisible(true);
        int width = (int) ((screeSize.width) * 0.9);
        int height = (int) ((screeSize.height) * 0.8);
        setSize(width, height);
        setTitle("Covid Report");

        setLayout(flowlayout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // TODO Auto-generated method stub
        if (e.getSource() == findCases) {

            activePatientA = new LinkedList<patient>();
            activePatientB = new LinkedList<patient>();
            activePatientC = new LinkedList<patient>();
            activePatientD = new LinkedList<patient>();
            recoveredPatientA = new LinkedList<patient>();
            recoveredPatientB = new LinkedList<patient>();
            recoveredPatientC = new LinkedList<patient>();
            recoveredPatientD = new LinkedList<patient>();

            String date = inputField.getText();

            report.helper(date, activePatientA, activePatientB, activePatientC, activePatientD, recoveredPatientA,
                    recoveredPatientB, recoveredPatientC, recoveredPatientD);
            if (report.error == true) {
                JOptionPane.showMessageDialog(this, "Please Provide Correct Input \n Date Format: dd/mm/yyyy");
            }
        }

        if (towerB.isSelected() && towerC.isSelected() && towerD.isSelected() && towerA.isSelected()) {
            setTextOfArea();
            printActive(activePatientA);
            printrecovered(recoveredPatientA);
            printActive(activePatientB);
            printrecovered(recoveredPatientB);
            printActive(activePatientC);
            printrecovered(recoveredPatientC);
            printActive(activePatientD);
            printrecovered(recoveredPatientD);
        } else if (towerA.isSelected() && towerB.isSelected() && towerC.isSelected()) {
            setTextOfArea();
            printActive(activePatientA);
            printrecovered(recoveredPatientA);
            printActive(activePatientB);
            printrecovered(recoveredPatientB);
            printActive(activePatientC);
            printrecovered(recoveredPatientC);
        } else if (towerA.isSelected() && towerB.isSelected() && towerD.isSelected()) {
            setTextOfArea();
            printActive(activePatientA);
            printrecovered(recoveredPatientA);
            printActive(activePatientB);
            printrecovered(recoveredPatientB);
            printActive(activePatientD);
            printrecovered(recoveredPatientD);
        } else if (towerA.isSelected() && towerC.isSelected() && towerD.isSelected()) {
            setTextOfArea();
            printActive(activePatientA);
            printrecovered(recoveredPatientA);
            printActive(activePatientC);
            printrecovered(recoveredPatientC);
            printActive(activePatientD);
            printrecovered(recoveredPatientD);
        } else if (towerB.isSelected() && towerC.isSelected() && towerD.isSelected()) {
            setTextOfArea();
            printActive(activePatientB);
            printrecovered(recoveredPatientB);
            printActive(activePatientC);
            printrecovered(recoveredPatientC);
            printActive(activePatientD);
            printrecovered(recoveredPatientD);
        } else if (towerA.isSelected() && towerB.isSelected()) {
            setTextOfArea();
            printActive(activePatientA);
            printrecovered(recoveredPatientA);
            printActive(activePatientB);
            printrecovered(recoveredPatientB);
        } else if (towerA.isSelected() && towerC.isSelected()) {
            setTextOfArea();
            printActive(activePatientA);
            printrecovered(recoveredPatientA);
            printActive(activePatientC);
            printrecovered(recoveredPatientC);
        } else if (towerA.isSelected() && towerD.isSelected()) {
            setTextOfArea();
            printActive(activePatientA);
            printrecovered(recoveredPatientA);
            printActive(activePatientD);
            printrecovered(recoveredPatientD);
        } else if (towerB.isSelected() && towerC.isSelected()) {
            setTextOfArea();
            printActive(activePatientB);
            printrecovered(recoveredPatientB);
            printActive(activePatientC);
            printrecovered(recoveredPatientC);
        } else if (towerB.isSelected() && towerD.isSelected()) {
            setTextOfArea();
            printActive(activePatientB);
            printrecovered(recoveredPatientB);
            printActive(activePatientD);
            printrecovered(recoveredPatientD);
        } else if (towerC.isSelected() && towerD.isSelected()) {
            setTextOfArea();
            printActive(activePatientC);
            printrecovered(recoveredPatientC);
            printActive(activePatientD);
            printrecovered(recoveredPatientD);
        } else if (towerA.isSelected()) {
            setTextOfArea();
            printActive(activePatientA);
            printrecovered(recoveredPatientA);

        } else if (towerB.isSelected()) {
            setTextOfArea();
            printActive(activePatientB);
            printrecovered(recoveredPatientB);

        } else if (towerC.isSelected()) {
            setTextOfArea();
            printActive(activePatientC);
            printrecovered(recoveredPatientC);
        } else if (towerD.isSelected()) {
            setTextOfArea();
            printActive(activePatientD);
            printrecovered(recoveredPatientD);

        } else {
            setTextOfArea();
            printActive(activePatientA);
            printrecovered(recoveredPatientA);
            printActive(activePatientB);
            printrecovered(recoveredPatientB);
            printActive(activePatientC);
            printrecovered(recoveredPatientC);
            printActive(activePatientD);
            printrecovered(recoveredPatientD);
        }

    }

    public void printActive(LinkedList activePatient) {

        // activeCases.append();

        for (int h = 0; h < activePatient.size(); h++) {

            patient a = (patient) activePatient.get(h);
            String recoverDate = recoverDaTe(a.DateOfReporting);
            activeCases.append(a.Name + "\t" + a.Age + "\t" + a.Tower + "\t" + recoverDate + "\n");

        }
    }

    public void printrecovered(LinkedList recoveredPatient) {

        for (int h = 0; h < recoveredPatient.size(); h++) {

            patient a = (patient) recoveredPatient.get(h);
            String recoverDate = recoverDaTe(a.DateOfReporting);
            recoveredCases.append(a.Name + "\t" + a.Age + "\t" + a.Tower + "\t" + recoverDate + "\n");

        }
    }

    public void setTextOfArea() {
        activeCases.setText("Active Cases: \n");
        activeCases.append("Name" + "\t" + "Age" + "\t" + "Tower" + "\t" + "Recovery Date" + "\n");
        recoveredCases.setText("Recovered Cases: \n");
        recoveredCases
                .append("Name" + "                    " + "Age" + "\t" + "Tower" + "\t" + "Recovered Date" + "\n");

    }

    private static String recoverDaTe(String dateOfReporting) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        Calendar c = Calendar.getInstance();
        try {
            // Setting the date to the given date
            c.setTime(sdf.parse(dateOfReporting));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.add(Calendar.DAY_OF_MONTH, 22);

        String newDate = sdf.format(c.getTime());

        return newDate;
    }

}
