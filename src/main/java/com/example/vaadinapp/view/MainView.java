package com.example.vaadinapp.view;

import com.example.vaadinapp.component.PatientEditor;
import com.example.vaadinapp.domain.Mielogram;
import com.example.vaadinapp.domain.Patient;
import com.example.vaadinapp.domain.PatientResearches;
import com.example.vaadinapp.repo.PatientRepo;
import com.example.vaadinapp.repo.PatientResearchesRepo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

@Route
public class MainView extends VerticalLayout {
    private final PatientRepo patientRepo;
    private final PatientResearchesRepo patientResearchesRepo;
    private final PatientEditor patientEditor;

    private Grid<Patient> patientGrid;
    private Grid<PatientResearches> researchesGrid;
    private Grid<Mielogram> mielogramGrid;
    private final Button addNewButton = new Button("Добавить пациента", VaadinIcon.PLUS.create());
    private final HorizontalLayout toolbar = new HorizontalLayout(addNewButton);
    @Autowired
    public MainView(PatientRepo patientRepo,
                    PatientResearchesRepo patientResearchesRepo,
                    PatientEditor patientEditor){
        this.patientRepo = patientRepo;
        this.patientResearchesRepo = patientResearchesRepo;
        this.patientEditor = patientEditor;
        this.patientGrid = new Grid<>();
        this.researchesGrid = new Grid<>();
        this.mielogramGrid = new Grid<>();

        Label label1 = new Label("Список пациентов");
        Label label2 = new Label("Исследования пациентов");
        Label label3 = new Label("Миелограмма");

        add(label1, patientGrid, toolbar, patientEditor, label2, researchesGrid, label3);
        add(mielogramGrid);

        patientGrid
                .asSingleSelect()
                .addValueChangeListener(e -> patientEditor.editPatient(e.getValue()));

        addNewButton.addClickListener(e -> {
            Patient p = new Patient();
            p.setDate(new Date(System.currentTimeMillis()));
            patientEditor.editPatient(p);
        });

        showPatient("");
        showResearches();
        showMielogram();




    }
    private void showPatient(String fio){
        patientGrid.setColumnReorderingAllowed(true);
        Grid.Column<Patient> idColumn = patientGrid
                .addColumn(Patient::getId).setHeader("ID");
        Grid.Column<Patient> fioColumn = patientGrid
                .addColumn(Patient::getFio).setHeader("ФИО");
        Grid.Column<Patient> ageColumn = patientGrid
                .addColumn(Patient::getAge).setHeader("Возраст");
        Grid.Column<Patient> medicalHistoryColumn = patientGrid
                .addColumn(Patient::getMedicalHistory).setHeader("История №");
        Grid.Column<Patient> dateColumn = patientGrid
                .addColumn(Patient::getDate).setHeader("Дата");
        Grid.Column<Patient> departmentColumn = patientGrid
                .addColumn(Patient::getDepartment).setHeader("Отделение");
        Grid.Column<Patient> diagnosisColumn = patientGrid
                .addColumn(Patient::getDiagnosis).setHeader("Диагноз");
        if (fio.isEmpty())
            patientGrid.setItems(patientRepo.findAll());
        else
            patientGrid.setItems(patientRepo.findByFio(fio));
    }

    private void showResearches(){
        researchesGrid.setColumnReorderingAllowed(true);
        Grid.Column<PatientResearches> idColumn = researchesGrid
                .addColumn(PatientResearches::getId).setHeader("ID");
        Grid.Column<PatientResearches> typeColumn = researchesGrid
                .addColumn(PatientResearches::getType).setHeader("Тип");
        Grid.Column<PatientResearches> dateColumn = researchesGrid
                .addColumn(PatientResearches::getDate).setHeader("Дата");
        researchesGrid.setItems(patientResearchesRepo.findAll());
    }

    private void showMielogram(){
        mielogramGrid.setColumnReorderingAllowed(true);
        Grid.Column<Mielogram> typeColumn = mielogramGrid
                .addColumn(Mielogram::getCellType).setHeader("Тип");
        Grid.Column<Mielogram> percentColumn = mielogramGrid
                .addColumn(Mielogram::getPercent).setHeader("Процент");
        List<Mielogram> list = patientResearchesRepo.countPercent();
        int total = 0;
        for (Mielogram m : list)
            total += m.getTotal();
        for (Mielogram m : list) {
            double d = (double) m.getTotal() * 100 / total;
            d = (double)Math.round(d * 100) / 100;
            m.setPercent(String.valueOf(d) + '%');
        }
        mielogramGrid.setItems(list);
    }
}
