package org.ball;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Player p1 = new Player("Adelin");
        PlayerRepository playerRepository = new PlayerRepository() {
            @Override
            public void flush() {

            }

            @Override
            public <S extends Player> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends Player> List<S> saveAllAndFlush(Iterable<S> entities) {
                return List.of();
            }

            @Override
            public void deleteAllInBatch(Iterable<Player> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Integer> integers) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Player getOne(Integer integer) {
                return null;
            }

            @Override
            public Player getById(Integer integer) {
                return null;
            }

            @Override
            public Player getReferenceById(Integer integer) {
                return null;
            }

            @Override
            public <S extends Player> List<S> findAll(Example<S> example) {
                return List.of();
            }

            @Override
            public <S extends Player> List<S> findAll(Example<S> example, Sort sort) {
                return List.of();
            }

            @Override
            public <S extends Player> List<S> saveAll(Iterable<S> entities) {
                return List.of();
            }

            @Override
            public List<Player> findAll() {
                return List.of();
            }

            @Override
            public List<Player> findAllById(Iterable<Integer> integers) {
                return List.of();
            }

            @Override
            public <S extends Player> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Player> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(Player entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Integer> integers) {

            }

            @Override
            public void deleteAll(Iterable<? extends Player> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public List<Player> findAll(Sort sort) {
                return List.of();
            }

            @Override
            public Page<Player> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Player> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Player> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Player> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Player> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Player, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        } ;
        PlayerService playerService = new PlayerService(playerRepository);
        System.out.println(playerService.getAllPlayers());
    }
}