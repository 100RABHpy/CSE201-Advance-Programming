import java.util.*;

public class simplify_it {
    static Scanner in;

    public static void main(String[] args) {
        Camp camp = new Camp();
        in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());

        for (int i = 0; i < n; i++) {
            String[] temp = in.nextLine().split(" ");
            Patient tempPatient = new Patient(temp[0], Double.parseDouble(temp[1]), Integer.parseInt(temp[2]),
                    Integer.parseInt(temp[3]));
            camp.addPatient(tempPatient);
        }
        resolveQuery queryResolver = new resolveQuery();
        while (camp.getNoOfPatient() != 0) {
            String[] arrCheck = in.nextLine().split(" ");
            int queryNo = Integer.parseInt(arrCheck[0]);

            if (queryNo == 1) {
                queryResolver.query1(camp);
            } else if (queryNo == 2) {
                queryResolver.query2(camp);
            } else if (queryNo == 3) {
                queryResolver.query3(camp);
            } else if (queryNo == 4) {
                queryResolver.query4(camp);
            } else if (queryNo == 5) {
                queryResolver.query5(camp);
            } else if (queryNo == 6) {

                queryResolver.query6(camp, arrCheck[1]);
            } else if (queryNo == 7) {
                int id = Integer.parseInt(arrCheck[1]);
                queryResolver.query7(camp, id);

            } else if (queryNo == 8) {
                queryResolver.query8(camp);
            } else if (queryNo == 9) {
                queryResolver.query9(camp, arrCheck[1]);
            }

        }

    }

}

class Camp {
    private int noOfAvailablePatient;
    private ArrayList<Patient> patients = new ArrayList<Patient>();
    private ArrayList<Patient> removedPatients = new ArrayList<Patient>();
    private ArrayList<HealthCareInstitute> healthCareInstitutes = new ArrayList<HealthCareInstitute>();
    private ArrayList<HealthCareInstitute> removedHealthCareInstitutes = new ArrayList<HealthCareInstitute>();

    Coordinator coordinator;

    public void coordinateInstitute(HealthCareInstitute institute) {
        coordinator = new Coordinator(patients, institute, this);
    }

    public int getNoOfPatient() {
        return noOfAvailablePatient;
    }

    public ArrayList<Patient> getPatient() {
        return patients;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
        noOfAvailablePatient++;
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
        noOfAvailablePatient--;
    }

    public void removePatientsFromList() {
        removedPatients.clear();
    }

    public void addInstitute(HealthCareInstitute institute) {
        healthCareInstitutes.add(institute);

    }

    public void removeInstitute(HealthCareInstitute institute) {
        healthCareInstitutes.remove(institute);

    }

    public void removeInstitutesFromList() {
        removedHealthCareInstitutes.clear();

    }

    public ArrayList<HealthCareInstitute> getInstitute() {
        return healthCareInstitutes;
    }

    public ArrayList<Patient> getRemovedPatients() {
        return removedPatients;
    }

    public void setRemovedPatients(Patient patient) {
        removedPatients.add(patient);
    }

    public ArrayList<HealthCareInstitute> getRemovedInstitute() {
        return removedHealthCareInstitutes;
    }

    public void setRemovedInstitute(HealthCareInstitute institute) {
        removedHealthCareInstitutes.add(institute);
    }
}

class Coordinator {
    Scanner in = new Scanner(System.in);

    public Coordinator(ArrayList<Patient> patients, HealthCareInstitute institute, Camp camp) {
        int noOfAvailablePatient = patients.size();
        int noOfCheckedPatients = noOfAvailablePatient;
        // ArrayList<Patient> copyPatients = new ArrayList<Patient>();

        checkByOxygen(noOfCheckedPatients, noOfAvailablePatient, institute, patients, camp);
        noOfAvailablePatient = patients.size();
        noOfCheckedPatients = noOfAvailablePatient;
        checkByTemperature(noOfCheckedPatients, noOfAvailablePatient, institute, patients, camp);

        if (institute.getNoOfBeds() == 0) {
            institute.changeAdmissionStatus();
            camp.setRemovedInstitute(institute);
            camp.removeInstitute(institute);
        }

        for (Patient i : institute.getPatients()) {
            String recoveryDayString = String.format("Recovery days for admitted patient ID %d : ", i.getId());
            System.out.print(recoveryDayString);
            int recoveryDay = Integer.parseInt(in.next());
            i.setRecoveryDay(recoveryDay);
        }

    }

    private void checkByOxygen(int noOfCheckedPatients, int noOfAvailablePatient, HealthCareInstitute institute,
            ArrayList<Patient> copyPatients, Camp camp) {
        while (institute.getNoOfBeds() > 0 && noOfAvailablePatient > 0 && noOfCheckedPatients > 0) {

            Patient patient = copyPatients.get(copyPatients.size() - noOfCheckedPatients);
            noOfCheckedPatients--;

            if (patient.getOxygenLevel() >= institute.getOxygenLevel()) {

                noOfAvailablePatient--;
                institute.addPatient(patient);
                patient.changeAdmission();
                camp.setRemovedPatients(patient);
                camp.removePatient(patient);
                institute.setNoOfBeds(institute.getNoOfBeds() - 1);
                patient.setAdmittingInstitute(institute.getName());
            }
        }
    }

    private void checkByTemperature(int noOfCheckedPatients, int noOfAvailablePatient, HealthCareInstitute institute,
            ArrayList<Patient> copyPatients, Camp camp) {
        while (institute.getNoOfBeds() > 0 && noOfAvailablePatient > 0 && noOfCheckedPatients > 0) {

            Patient patient = copyPatients.get(copyPatients.size() - noOfCheckedPatients);
            noOfCheckedPatients--;
            if (patient.getBodyTemperature() <= institute.getBodyTemperature()) {

                institute.addPatient(patient);
                noOfAvailablePatient--;
                institute.setNoOfBeds(institute.getNoOfBeds() - 1);
                camp.setRemovedPatients(patient);
                camp.removePatient(patient);
                patient.changeAdmission();
                patient.setAdmittingInstitute(institute.getName());
            }
        }
    }

}

class Patient {
    private static int uniqueIdCreator;
    private int uniqueId;
    private String Name;
    private int Age;
    private int OxygenLevel;
    private double BodyTemperature;
    private String admission = "Not Admitted";
    private int RecoveryDays;
    private String admittedInstitute;
    private boolean removed = false;

    public Patient(String name, double bodyTemperature, int oxygenLevel, int age) {
        uniqueIdCreator++;
        uniqueId = uniqueIdCreator;
        Name = name;
        Age = age;
        OxygenLevel = oxygenLevel;
        BodyTemperature = bodyTemperature;

    }

    public int getId() {
        return uniqueId;

    }

    public String getName() {
        return Name;
    }

    public int age() {
        return Age;

    }

    public int getOxygenLevel() {
        return OxygenLevel;

    }

    public double getBodyTemperature() {
        return BodyTemperature;

    }

    public int getRecoveryDays() {
        return RecoveryDays;
    }

    public void setRecoveryDay(int day) {
        RecoveryDays = day;
    }

    public void changeAdmission() {
        admission = "Admitted";
    }

    public String toString() {
        String returnString = String.format(
                "Name: %s\nTemperature is %.2f\nOxygen levels is %d\nAdmission Status – %s\nAdmitting Institute – %s",
                Name, BodyTemperature, OxygenLevel, admission, admittedInstitute);

        return returnString;
    }

    public void setAdmittingInstitute(String instituteName) {
        admittedInstitute = instituteName;

    }

    public boolean getRemoveStatus() {
        return removed;
    }

    public void removePatient() {
        removed = true;

    }
}

class HealthCareInstitute {

    private String Name;
    private int NoOfBeds;
    private int OxygenLevel;
    private double BodyTemperature;
    private ArrayList<Patient> listOfPatients = new ArrayList<Patient>();
    private boolean removed = false;
    private String admissionStatus = "OPEN";

    public HealthCareInstitute(String name, int noOfBeds, int oxygenLevel, double bodyTemperature) {
        Name = name;
        NoOfBeds = noOfBeds;
        OxygenLevel = oxygenLevel;
        BodyTemperature = bodyTemperature;

    }

    public void addPatient(Patient patient) {
        listOfPatients.add(patient);
    }

    public String toString() {
        String returnString = String.format(
                "Name: %s\nTemperature should be <= %.2f\nOxygen levels should be >= %d\nNumber of Available beds=%d\nAdmission Status – %s",
                Name, BodyTemperature, OxygenLevel, NoOfBeds, admissionStatus);

        return returnString;
    }

    public int getNoOfBeds() {
        return NoOfBeds;
    }

    public void setNoOfBeds(int noOfBeds) {
        NoOfBeds = noOfBeds;
    }

    public String getName() {
        return Name;
    }

    public int getOxygenLevel() {
        return OxygenLevel;
    }

    public double getBodyTemperature() {
        return BodyTemperature;
    }

    public void printPatients() {
        for (Patient i : listOfPatients) {
            String returnString = String.format("%s, recovery time is %d days", i.getName(), i.getRecoveryDays());
            System.out.println(returnString);
        }
        if (listOfPatients.size() == 0) {
            System.out.println("No Patient in this Institute");
        }

    }

    public ArrayList<Patient> getPatients() {
        return listOfPatients;
    }

    public void changeAdmissionStatus() {
        admissionStatus = "CLOSED";
    }

    public String getAdmissionStatus() {
        return admissionStatus;
    }

    public boolean getRemoveStatus() {
        return removed;
    }

    public void removeInstitute() {
        removed = true;

    }
}

class resolveQuery {
    static Scanner in = new Scanner(System.in);

    public void query1(Camp camp) {

        System.out.print("Name Of Institute: ");
        String name = in.nextLine();
        System.out.print("Temperature Criteria: ");
        double temperature = Double.parseDouble(in.nextLine());
        System.out.print("Oxygen Level: ");
        int oxygenLevel = Integer.parseInt(in.nextLine());
        System.out.print("Number of Available beds: ");
        int beds = Integer.parseInt(in.nextLine());
        HealthCareInstitute institute = new HealthCareInstitute(name, beds, oxygenLevel, temperature);
        camp.addInstitute(institute);
        if (beds == 0) {
            institute.changeAdmissionStatus();
        }
        System.out.println(institute);
        camp.coordinateInstitute(institute);

    }

    public void query2(Camp camp) {
        System.out.println("Account ID removed of admitted patients");

        for (Patient i : camp.getRemovedPatients()) {
            i.removePatient();

            System.out.println(i.getId());

        }
        if (camp.getRemovedPatients().size() == 0) {
            System.out.println("No Patient is admitted till now");
        }
        camp.removePatientsFromList();

    }

    public void query3(Camp camp) {
        System.out.println("Accounts removed of Institute whose admission is closed");
        for (HealthCareInstitute i : camp.getRemovedInstitute()) {
            i.removeInstitute();

            System.out.println(i.getName());
        }
        if (camp.getRemovedInstitute().size() == 0) {
            System.out.println("No Institute is Full till now");
        }
        camp.removeInstitutesFromList();
    }

    public void query4(Camp camp) {
        String returnString = String.format("%d Patient", camp.getPatient().size());
        System.out.println(returnString);
    }

    public void query5(Camp camp) {
        String returnString = String.format("%d institutes are admitting patients currently",
                camp.getInstitute().size());
        System.out.println(returnString);

    }

    public void query6(Camp camp, String name) {
        for (HealthCareInstitute i : camp.getInstitute()) {
            if (i.getName().equals(name)) {
                System.out.println(i);
                return;
            }
        }
        for (HealthCareInstitute i : camp.getRemovedInstitute()) {
            if (i.getName().equals(name) && !i.getRemoveStatus()) {
                System.out.println(i);
                return;
            }
        }

        System.out.println("No Institute with this name in camp");

    }

    public void query7(Camp camp, int id) {

        for (Patient i : camp.getPatient()) {
            if (i.getId() == id) {
                System.out.println(i);
                return;
            }
        }
        for (Patient i : camp.getRemovedPatients()) {
            if (i.getId() == id && !i.getRemoveStatus()) {
                System.out.println(i);
                return;
            }
        }
        System.out.println("No patient with this id in camp");

    }

    public void query8(Camp camp) {
        for (Patient i : camp.getRemovedPatients()) {
            if (!i.getRemoveStatus()) {
                System.out.print(i.getId() + "\t");
                System.out.println(i.getName());

            }
        }

        for (Patient i : camp.getPatient()) {
            System.out.print(i.getId() + "\t");
            System.out.println(i.getName());
        }

    }

    public void query9(Camp camp, String name) {

        for (HealthCareInstitute i : camp.getInstitute()) {
            if (i.getName().equals(name)) {
                i.printPatients();
            }
        }
        for (HealthCareInstitute i : camp.getRemovedInstitute()) {
            if (i.getName().equals(name) && !i.getRemoveStatus()) {
                i.printPatients();
            }
        }
    }
}