package br.com.projeto.logistica.repository;

import br.com.projeto.logistica.model.Carga;
import br.com.projeto.logistica.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CargaRepository extends JpaRepository<Carga, UUID> {
    Optional<Carga> findByClienteAndNotaFiscalAndAindaArmazenado(Cliente cliente, int notaFiscal,
                                                                 boolean aindaArmazenado);

    Optional<Carga> findByNotaFiscalAndAindaArmazenado(int notaFiscal, boolean aindaArmazenado);

    List<Carga> findByCliente(Cliente cliente);

}
