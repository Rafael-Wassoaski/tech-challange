package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.MapPersistenceItemForTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LancheServiceTest {

    private LancheService lancheService;

    @Test
    void deveriaCriarUmItemValido() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        lancheService = new LancheService(mapPersistenceForTests);
        String nomeLanche = "Nome 1";
        double valor = 10;
        Lanche lanche = new Lanche(nomeLanche, valor);

        lancheService.criar(lanche);
        Lanche lancheCriado = (Lanche) lancheService.buscarPorNome(nomeLanche);

        Assertions.assertNotNull(lancheCriado);
        Assertions.assertInstanceOf(Lanche.class, lancheCriado);
    }

    @Test
    public void naoDeveriaCriarDoisLanchesComOMesmoNome() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        lancheService = new LancheService(mapPersistenceForTests);
        String nomeLanche = "Nome 1";
        double valor = 10;
        Lanche lanche = new Lanche(nomeLanche, valor);

        lancheService.criar(lanche);


        Assertions.assertThrows(Exception.class, () -> {
            lancheService.criar(lanche);
        });
    }


    @Test
    void deveriaRetornarTodosOsItensSalvos() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        lancheService = new LancheService(mapPersistenceForTests);
        String nomeLanche1= "Nome 1";
        String nomeLanche2 = "Nome 2";
        double valor = 10;
        Lanche lanche1 = new Lanche(nomeLanche1, valor);
        Lanche lanche2 = new Lanche(nomeLanche2, valor);

        lancheService.criar(lanche1);
        lancheService.criar(lanche2);
        List<Lanche> lanches = lancheService.buscarTodosOsItens();

        Assertions.assertEquals(2, lanches.size());
    }

    @Test
    void deveriaAtualizarOPrecoDeUmLancheSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        lancheService = new LancheService(mapPersistenceForTests);
        String nomeLanche= "Nome 1";
        double valor = 10;
        Lanche lanche = new Lanche(nomeLanche, valor);

        lancheService.criar(lanche);
        lanche.setPreco(11);
        lancheService.atualizar(lanche);

        Lanche lancheCriado = (Lanche) lancheService.buscarPorNome(nomeLanche);

        Assertions.assertNotNull(lancheCriado);
        Assertions.assertEquals(lanche.getPreco(), lancheCriado.getPreco());
    }

    @Test
    void deveriaDeletarUmItemSalvo() throws Exception {
        MapPersistenceItemForTests mapPersistenceForTests = new MapPersistenceItemForTests();
        lancheService = new LancheService(mapPersistenceForTests);
        String nomeLanche= "Nome 1";
        double valor = 10;
        Lanche lanche = new Lanche(nomeLanche, valor);

        lancheService.criar(lanche);
        lancheService.deletarPorNome(nomeLanche);

         Assertions.assertThrows(Exception.class, ()->{
             lancheService.buscarPorNome(nomeLanche);
         });
    }
}
