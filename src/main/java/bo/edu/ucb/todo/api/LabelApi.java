package bo.edu.ucb.todo.api;

import bo.edu.ucb.todo.bl.AuthBl;
import bo.edu.ucb.todo.bl.LabelBl;
import bo.edu.ucb.todo.dto.LabelDto;
import bo.edu.ucb.todo.dto.ResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LabelApi {

    private LabelBl labelBl;

    public LabelApi(LabelBl labelBl) {
        this.labelBl = labelBl;
    }

    /**
     * Este endpoint retorna todas las etiquetas
     * @param token El token JWT que se obtuvo al autenticar la aplicación
     * @return
     */
    @GetMapping("/api/v1/label")
    public ResponseEntity< ResponseDto<List<LabelDto>>> getAllLabels(
            @RequestHeader("Authorization") String token) {
        AuthBl authBl = new AuthBl();
        if (!authBl.validateToken(token)) {
            ResponseDto<List<LabelDto>> response = new ResponseDto<>();
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        ResponseDto<List<LabelDto>> response = new ResponseDto<>();
        response.setCode("0000");
        response.setResponse(this.labelBl.getAllLabels());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Este endpoint obtiene el detalle de una etiqueta por ID
     * @param id La llave primaria de la etiqueta
     * @param token El token de autenticación
     * @return
     */
    @GetMapping("/api/v1/label/{idLabel}")
    public ResponseEntity<ResponseDto<LabelDto>> getLabelById(@PathVariable("idLabel") Integer id, @RequestHeader("Authorization") String token) {
        ResponseDto<LabelDto> response = new ResponseDto<>();
        AuthBl authBl = new AuthBl();
        if (!authBl.validateToken(token)) {
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        //Buscamos el elemento en la lista
        LabelDto label = this.labelBl.getLabelById(id);
        // Si no existe retornamos un error
        if (label == null) {
            //FIXME: Cambiar el codigo de error debe retornar 404
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Label not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            // Si existe retornamos el elemento
            response.setCode("0000");
            response.setResponse(label);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    /**
     * Actualiza una etiqueta por id de etiqueta.
     * @param idLabel La llave primaria de la etiqueta
     * @param newLabel Los nuevos datos para la etiqueta
     * @param token El token que se obtuvo en la autenticación
     * @return
     */
    @PutMapping("/api/v1/label/{idLabel}")
    public ResponseEntity<ResponseDto<LabelDto>> updateLabelById( @PathVariable Integer idLabel, @RequestBody LabelDto newLabel, @RequestHeader("Authorization") String token) {
        ResponseDto<LabelDto> response = new ResponseDto<>();
        AuthBl authBl = new AuthBl();
        if (!authBl.validateToken(token)) {
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        //Buscamos el elemento en la lista
        LabelDto label = this.labelBl.getLabelById(idLabel);
        // Si no existe retornamos un error
        if (label == null) {
            //FIXME: Cambiar el codigo de error debe retornar 404
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Label not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            this.labelBl.updateLabelById(idLabel, newLabel);
            // Si existe retornamos el elemento
            response.setCode("0000");
            response.setResponse(label);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    /**
     * Este metodo permite crear una etiqueta.
     * @param label Todos los datos de una etiqueta.
     * @param token El token obtenido en la autenticación
     * @return Retorna un mensaje: "Label createed" o error en su defecto.
     */
    @PostMapping("/api/v1/label")
    public ResponseEntity<ResponseDto<String>> createLabel(@RequestBody LabelDto label, @RequestHeader("Authorization") String token) {
        ResponseDto<String> response = new ResponseDto<>();
        AuthBl authBl = new AuthBl();
        if (!authBl.validateToken(token)) {
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        if (label.getName() == null || label.getName().trim().equals("")) {
            response.setCode("0002");
            response.setResponse(null);
            response.setErrorMessage("Label name is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        this.labelBl.createLabel(label);
        response.setCode("0000");
        response.setResponse("Label created");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
