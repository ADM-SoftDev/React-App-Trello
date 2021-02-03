package es.adm.webshop.backend.repository;

import es.adm.webshop.backend.modelo.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository  extends JpaRepository<CategoriaEntity, Integer> {

}
