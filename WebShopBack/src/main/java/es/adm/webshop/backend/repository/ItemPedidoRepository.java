package es.adm.webshop.backend.repository;

import es.adm.webshop.backend.modelo.ItemPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedidoEntity, Integer> {
}
