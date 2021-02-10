package es.adm.webshop.backend.repository;

import es.adm.webshop.backend.modelo.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository  extends JpaRepository<PedidoEntity, Integer> {

    public PedidoEntity findByLocalizador(String localizador);
}
