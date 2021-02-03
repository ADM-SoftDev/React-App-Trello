package es.adm.webshop.backend.servicio;

import es.adm.webshop.backend.common.EstadoPedido;
import es.adm.webshop.backend.dto.PedidoDTO;
import es.adm.webshop.backend.error.ExcepcionBase;

import java.util.List;

public interface IPedidoService {

    public List<PedidoDTO> getListaPedidos() throws ExcepcionBase;
    public PedidoDTO getPedidosByLocalizador(String localizador) throws ExcepcionBase;
    public PedidoDTO getPedidosById(Integer idPedido) throws ExcepcionBase;
    public PedidoDTO createPedido(PedidoDTO pedido) throws ExcepcionBase;
    public PedidoDTO updateEstadoPedido(Integer Idpedido, EstadoPedido estado) throws ExcepcionBase;
    public void deletePedido(Integer idPedido) throws ExcepcionBase;
}
