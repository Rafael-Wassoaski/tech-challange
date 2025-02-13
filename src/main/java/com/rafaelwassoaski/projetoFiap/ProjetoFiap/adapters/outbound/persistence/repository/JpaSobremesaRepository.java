package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.SobremesaEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers.SobremesaMapper;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface JpaSobremesaRepository extends JpaRepository<SobremesaEntity, Integer>, PersistenceItemRepository<Sobremesa> {
    @Override
    default Sobremesa atualizar(Sobremesa itemSalvo) {
        return salvar(itemSalvo);
    }

    @Transactional
    @Override
    default void deletarPorNome(String nome) {
        deleteByNome(nome);
    }

    @Override
    default List<Sobremesa> buscarTodos() {
        List<SobremesaEntity> sobremesaEntities = findAll();

        return sobremesaEntities.stream().map(SobremesaMapper::converterParaSobremesa).collect(Collectors.toList());
    }

    @Override
    default Optional<Sobremesa> buscarPorNome(String nome) {
        Optional<SobremesaEntity> optionalSobremesaEntity = findByNome(nome);

        if(optionalSobremesaEntity.isEmpty()){
            return Optional.empty();
        }

        SobremesaEntity sobremesaEntity = optionalSobremesaEntity.get();

        return Optional.of(SobremesaMapper.converterParaSobremesa(sobremesaEntity));
    }

    @Override
    default Sobremesa salvar(Sobremesa item) {
        SobremesaEntity sobremesaEntity = SobremesaMapper.converterParaSobremesaEntity(item);
        SobremesaEntity sobremesaSalva = saveAndFlush(sobremesaEntity);

        return SobremesaMapper.converterParaSobremesa(sobremesaSalva);
    }

    Optional<SobremesaEntity> findByNome(String nome);
    void deleteByNome(String nome);

}
