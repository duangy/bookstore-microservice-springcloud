package com.guangyao.bookstore.account.domain;

import com.guangyao.bookstore.domain.account.Account;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

@CacheConfig(cacheNames = "repository.account")
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Override
    Iterable<Account> findAll();

    @Cacheable(key = "#username")
    Account findByUsername(String username);

    boolean existsByUsernameOrEmailOrTelephone(String username, String email, String telephone);

    Collection<Account> findByUsernameOrEmailOrTelephone(String username, String email, String telephone);

    @Cacheable(key = "#username")
    boolean existsByUsername(String username);

    @Caching(evict = {
            @CacheEvict(key = "#entity.id"),
            @CacheEvict(key = "#entity.username")
    })
    <S extends Account> S save(S entity);

    @CacheEvict
    <S extends Account> Iterable<S> saveAll(Iterable<S> entities);

    @Cacheable(key = "#id")
    Optional<Account> findById(ID id);

    @Cacheable(key = "#id")
    boolean existsById(ID id);

    @CacheEvict(key = "#id")
    void deleteById(ID id);

    @CacheEvict(key = "#entity.id")
    void delete(Account entity);

    @CacheEvict(allEntries = true)
    void deleteAll(Iterable<? extends Account> entities);

    @CacheEvict(allEntries = true)
    void deleteAll();
}
