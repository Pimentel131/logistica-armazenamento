package br.com.projeto.logistica.controller;

import br.com.projeto.logistica.model.ServicoDiario;
import br.com.projeto.logistica.service.ServicoDiarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicos")
public class ServicoDiarioController {

    private final ServicoDiarioService servicoService;

    public ServicoDiarioController(ServicoDiarioService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping("/entrada")
    public ServicoDiario iniciarEntrada(@RequestBody ServicoDiario servico) {
        return iniciarEntrada(servico);
    }
}
