package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.*;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;

import java.util.Optional;

public class PedidoMapper {

    public static Pedido converterParaPedido(PedidoEntity pedidoEntity){
        Optional<Lanche> optionalLanche = pegarLancheOpcional(pedidoEntity.getLanche());
        Optional<Bebida> optionalBebida = pegarBebidaOpcional(pedidoEntity.getBebida());
        Optional<Acompanhamento> optionalAcompanhamento = pegarAcompanhamentoOpcional(pedidoEntity.getAcompanhamento());
        Optional<Sobremesa> optionalSobremesa = pegarSobremesaOpcional(pedidoEntity.getSobremesa());
        Usuario usuario = pegarUsuario(pedidoEntity.getUsuario());

        return new Pedido(pedidoEntity.getId(),
                optionalLanche,
                optionalBebida,
                optionalAcompanhamento,
                optionalSobremesa,
                usuario,
                pedidoEntity.getStatusPedido()
        );
    }

    private static Optional<Lanche> pegarLancheOpcional(LancheEntity lancheEntity){
        if (lancheEntity == null){
            return Optional.empty();
        }

        return Optional.of(LancheMapper.converterParaLanche(lancheEntity));
    }

    private static Optional<Bebida> pegarBebidaOpcional(BebidaEntity bebidaEntity){
        if (bebidaEntity == null){
            return Optional.empty();
        }

        return Optional.of(BebidaMapper.converterParaBebida(bebidaEntity));
    }

    private static Optional<Acompanhamento> pegarAcompanhamentoOpcional(AcompanhamentoEntity acompanhamentoEntity){
        if (acompanhamentoEntity == null){
            return Optional.empty();
        }

        return Optional.of(acompanhamentoEntity.converterParaAcompanhamento());
    }

    private static Optional<Sobremesa> pegarSobremesaOpcional(SobremesaEntity sobremesaEntity){
        if (sobremesaEntity == null){
            return Optional.empty();
        }

        return Optional.of(sobremesaEntity.converterParaSobremesa());
    }

    private static Usuario pegarUsuario(UsuarioEntity usuario){
        if (usuario == null){
            return null;
        }

        return usuario.converterParaUsuario();
    }

    public static PedidoEntity converterParaPedidoEntity(Pedido pedido) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setStatusPedido(pedido.getStatusPedido()); ;

        if(pedido.getUsuario() != null){
            //TODO esse costrutor deve virar um mapper também
            pedidoEntity.setUsuario(new UsuarioEntity(pedido.getUsuario()));
        }

        if(pedido.getLanche().isPresent()){
            pedidoEntity.setLanche(LancheMapper.converterParaLancheEntity(pedido.getLanche().get())) ;
        }

        if(pedido.getBebida().isPresent()){
            pedidoEntity.setBebida(BebidaMapper.converterParaBebidaEntity(pedido.getBebida().get()));
        }

        if(pedido.getAcompanhamento().isPresent()){
            pedidoEntity.setAcompanhamento(new AcompanhamentoEntity(pedido.getAcompanhamento().get()));
        }
        if(pedido.getSobremesa().isPresent()){
            pedidoEntity.setSobremesa(new SobremesaEntity(pedido.getSobremesa().get()));
        }

        return pedidoEntity;
    }
}
