package ru.dmartyanov.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dmartyanov.domain.Employee;
import ru.dmartyanov.repo.EmployeeRepo;

@SpringComponent
@UIScope
public class EmployeeEditor extends VerticalLayout implements KeyNotifier {
    private final EmployeeRepo employeeRepo;

    private Employee employee;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField patronymic = new TextField("Patronymic");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<Employee> binder = new Binder<>(Employee.class);

    @Setter
    private ChangeHandler changeHandler;

    @Autowired
    public EmployeeEditor(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;

        add(lastName, firstName, patronymic, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editEmployee(employee));
        setVisible(false);
    }

    private void editEmployee(Employee newEmployee) {
        if (newEmployee == null) {
            setVisible(false);
            return;
        }

        if (newEmployee.getId() != null) {
            employee = employeeRepo.findById(newEmployee.getId()).orElse(newEmployee);
        } else {
            employee = newEmployee;
        }

        binder.setBean(employee);

        setVisible(true);

        lastName.focus();
    }

    private void delete() {
        employeeRepo.delete(employee);
        changeHandler.onChange();
    }

    private void save() {
        employeeRepo.save(employee);
        changeHandler.onChange();
    }

    public interface ChangeHandler{
        void onChange();
    }


}
