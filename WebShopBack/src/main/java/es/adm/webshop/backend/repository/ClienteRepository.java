package es.adm.webshop.backend.repository;

import es.adm.webshop.backend.modelo.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository  extends JpaRepository<ClienteEntity, Integer> {
}
