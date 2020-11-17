package ru.dmartyanov.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dmartyanov.domain.Employee;
import ru.dmartyanov.repo.EmployeeRepo;

@Route
public class MainView extends VerticalLayout {
    private final EmployeeRepo employeeRepo;

    private Grid<Employee> grid = new Grid<>(Employee.class);

    @Autowired
    public MainView(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;

        add(grid);
        showEmployee("");
    }

    private void showEmployee(String name) {
        if (name.isEmpty()) {
            grid.setItems(employeeRepo.findAll());
        }else{
            grid.setItems(employeeRepo.findByName(name));
        }
    }

}
