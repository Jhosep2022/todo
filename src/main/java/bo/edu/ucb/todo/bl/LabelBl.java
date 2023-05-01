package bo.edu.ucb.todo.bl;

import bo.edu.ucb.todo.dto.LabelDto;
import bo.edu.ucb.todo.dto.ResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class LabelBl {
    private List<LabelDto> labels;

    public LabelBl() {
        this.labels = new ArrayList<>();
        this.labels.add(new LabelDto(1, "Universidad"));
        this.labels.add(new LabelDto(2, "Ocio"));
        this.labels.add(new LabelDto(3, "Personal"));
    }

    public List<LabelDto> getAllLabels() {
        return labels;
    }

    public LabelDto getLabelById(Integer id) {

        // Buscamos el elemento en la lista
        LabelDto label = labels.stream()
                .filter(t -> t.getLabelId().equals(id))
                .findFirst()
                .orElse(null);
        return label;
    }

    public LabelDto updateLabelById(Integer idLabel, LabelDto newLabel) {
        // Buscamos el elemento en la lista
        LabelDto label = labels.stream()
                .filter(t -> t.getLabelId().equals(idLabel))
                .findFirst()
                .orElse(null);
        label.setName(newLabel.getName());
        return label;
    }

    public void createLabel(LabelDto label) {
        // Obtenemos el ultimo elemento de la lista y le sumamos 1 para obtener el id
        // del nuevo elemento
        if (labels.size() > 0) {
            LabelDto lastLabel = labels.get(labels.size() - 1);
            label.setLabelId(lastLabel.getLabelId() + 1);
        } else {
            label.setLabelId(1);
        }
        labels.add(label);
    }
}
