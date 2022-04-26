package com.example.vaadinapp.component;

import com.example.vaadinapp.domain.Patient;
import com.example.vaadinapp.repo.PatientRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class PatientEditor extends VerticalLayout implements KeyNotifier {
    private final PatientRepo patientRepo;
    private Patient patient;

    //private TextField id = new TextField("", "id");
    private TextField fio = new TextField("", "ФИО");
    //private TextField age = new TextField("", "age");
    //private TextField medicalHistory = new TextField("", "medicalHistory");

    private Button save = new Button("Сохранить");
    private Button delete = new Button("Удалить");
    private HorizontalLayout buttons = new HorizontalLayout(save, delete);

    private Binder<Patient> binder = new Binder<>(Patient.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public PatientEditor(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;

        add(fio, buttons);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        //cancel.addClickListener(e -> editPatient(patient));
        setVisible(false);
    }

    private void save() {
        patientRepo.save(patient);
        changeHandler.onChange();
    }

    private void delete() {
        patientRepo.delete(patient);
        changeHandler.onChange();
    }

    public void editPatient(Patient p) {
        if (p == null) {
            setVisible(false);
            return;
        }

        if (p.getId() != null) {
            this.patient = patientRepo.findById(p.getId()).orElse(p);
        } else {
            this.patient = p;
        }

        binder.setBean(this.patient);

        setVisible(true);

        fio.focus();
    }
}
