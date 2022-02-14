package com.springboot.springboot.repositories;
import com.springboot.springboot.Models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface ProductRepositories extends JpaRepository<Product,Long> {
    // JPA support feature make function auto create for customer can create anytime\
    // Note function contain Entity same Entity in table
    // nếu khong tìm thấy phương thức đã tạo sẳn, thì có thể tạo 1 câu truy vấn theo tên phương thức
    List<Product> findByProductName(String name);
    Optional<Product> findByIdAndProductName(Long id, String name);
    List<Product> findDistinctProductByProductName(String firstname);
    List<Product> findAllByProductNameAndYear(String productName,int year, Pageable pageable);
//    @Override
//    public List<Product> findAll() {
//        return null;
//    }
//
//    @Override
//    public List<Product> findAll(Sort sort) {
//        return null;
//    }
//
//    @Override
//    public Page<Product> findAll(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<Product> findAllById(Iterable<Long> longs) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(Long aLong) {
//
//    }
//
//    @Override
//    public void delete(Product entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Product> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public <S extends Product> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product> List<S> saveAll(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public Optional<Product> findById(Long aLong) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(Long aLong) {
//        return false;
//    }
//
//    @Override
//    public void flush() {
//
//    }
//
//    @Override
//    public <S extends Product> S saveAndFlush(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product> List<S> saveAllAndFlush(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<Product> entities) {
//
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//
//    }
//
//    @Override
//    public Product getOne(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public Product getById(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends Product> List<S> findAll(Example<S> example) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product> List<S> findAll(Example<S> example, Sort sort) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends Product> boolean exists(Example<S> example) {
//        return false;
//    }
//
//    @Override
//    public <S extends Product, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return null;
//    }
}
