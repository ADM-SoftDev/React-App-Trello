package es.adm.webshop.backend.repository;

import es.adm.webshop.backend.modelo.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LibroRepository  extends JpaRepository<LibroEntity, Integer> {
//public interface LibroRepository  extends CrudRepository<LibroEntity, Integer> {

    @Query(value = "select lib from libro lib where (:id is null OR :id = lib.idlibro)"
            + "AND (:idCategoria is null OR : idCategoria = lib.categoria.idcategoria)"
            + "AND (:titulo is null OR lib.titulo like :titulo)"
            + "AND (:isbn is null OR lib.isbn like :isbn)"
            + "AND (:autor is null OR lib.autor like :autor)"
    )
    public List<LibroEntity> findFiltered(@Param("id") Integer idlibro,
                                          @Param("idCategoria") Integer id_categoria,
                                          @Param("titulo") String titulo,
                                          @Param("isbn") String isbn,
                                          @Param("autor") String autor
    );

    @Query(value = "select lib from libro lib where lib.titulo like :valor OR lib.autor like :valor")
    public List<LibroEntity> findByTituloAutor(@Param("valor") String valor);


}
